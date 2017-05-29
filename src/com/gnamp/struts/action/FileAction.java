 package com.gnamp.struts.action;
 
 import com.gnamp.server.InnerException;
 import com.gnamp.server.Storage;
 import com.gnamp.server.handle.CategoryHandle;
 import com.gnamp.server.handle.FileHandle;
 import com.gnamp.struts.utils.JsonUtils;
 import com.gnamp.struts.utils.MapTool;
 import com.gnamp.struts.vo.PageBean;
 import com.gnamp.struts.vo.TreeToFileParameter;
 import com.sun.image.codec.jpeg.JPEGCodec;
 import com.sun.image.codec.jpeg.JPEGImageEncoder;
 import java.awt.image.BufferedImage;
 import java.io.BufferedInputStream;
 import java.io.BufferedWriter;
 import java.io.FileInputStream;
 import java.io.FileNotFoundException;
 import java.io.FileWriter;
 import java.io.IOException;
 import java.io.OutputStream;
 import java.net.URL;
 import java.net.URLDecoder;
 import java.net.URLEncoder;
 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.HashMap;
 import java.util.HashSet;
 import java.util.List;
 import java.util.Map;
 import java.util.Set;
 import java.util.Stack;
 import java.util.TreeSet;
 import javax.imageio.ImageIO;
 import javax.servlet.ServletContext;
 import javax.servlet.ServletOutputStream;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.log4j.Logger;
 
 public class FileAction
   extends VersionConvertAction<FileHandle>
 {
   private static final String FILE_ERROR = "file";
   private static final int FILE_VEDIO_NUMBER = 5;
   private static final String FILELIST_ERROR = "filelist";
   private static final Logger log = Logger.getLogger(FileAction.class);
   private static final String[] SORTFIELD = { "check_state", 
     "name", "size", "check_time" };
   private static final String[] SORTMANAER = { "asc", "desc" };
   int currentCateId = -1;
   private com.gnamp.server.model.File file;
   private List<Long> filelist = new ArrayList();
   private TreeToFileParameter fileparam;
   private List<String> filesFileName;
   private String oManaer;
   private String oName;
   private PageBean page = new PageBean();
   private String sort;
   private int previewNumber;
   private String sortFiled;
   
   public int getPreviewNumber()
   {
     return this.previewNumber;
   }
   
   public void setPreviewNumber(int previewNumber)
   {
     this.previewNumber = previewNumber;
   }
   
   public void auditfile()
   {
     Set<String> errors = new TreeSet();
     try
     {
       for (Long i : this.filelist)
       {
         com.gnamp.server.model.File f = ((FileHandle)getHandle()).read(i.longValue());
         try
         {
           if (f == null) {
             throw new NullPointerException("文件" + i + "不存在");
           }
           if (f.getCheckState() == 0)
           {
             ((FileHandle)getHandle()).check(f);
             logEvent(getEnglish("audit"), getEnglish("audit") + ":[" + f.getFileName() + "]");
           }
         }
         catch (Exception e)
         {
           errors.add(e.getMessage());
         }
       }
       if (errors.size() == 0) {
         response(JSONSuccessString());
       } else {
         throw new Exception();
       }
     }
     catch (Exception e)
     {
       response(JSONErrorString(getText("auditfailure")));
       log.error(e.getMessage());
     }
   }
   
   public void auditlist()
   {
     List<com.gnamp.server.model.File> file = ((FileHandle)getHandle()).readAll(
       "select * from tb_file where type = :type", getAuditParam());
     response(JSONSuccessString(JSONArrayToString(file)));
   }
   
   public void checkfile()
   {
     Stack<String> errors = new Stack();
     try
     {
       for (Long l : this.filelist)
       {
         String path = new Storage(getCstmId()).getFilePath(l.longValue());
         if (!new java.io.File(path).exists()) {
           errors.add(((FileHandle)getHandle()).read(l.longValue()).getFileName());
         }
       }
       if (errors.size() == 0) {
         response(JSONSuccessString(getText("checkSuccess")));
       } else {
         throw new Exception();
       }
     }
     catch (Exception e)
     {
       log.error(e.getMessage());
       response(JSONErrorString(getText("checkFaild") + errors.toString()));
     }
   }
   
   public void deletefile()
   {
     com.gnamp.server.model.File delFile = ((FileHandle)getHandle()).read(this.file.getFileId());
     try
     {
       if (!((FileHandle)getHandle()).remove(delFile.getFileId()))
       {
         response(JSONErrorString(getText("deletefailed")));
       }
       else
       {
         response(JSONSuccessString());
         logEvent(getEnglish("deletefile"), getEnglish("deletefile") + ":[" + delFile.getFileName() + "]");
       }
     }
     catch (Exception e)
     {
       response(JSONErrorString(getText("deletefailed")));
       log.error(e.getMessage());
     }
   }
   
   public void download()
   {
     this.file = ((FileHandle)getHandle()).read(this.file.getFileId());
     String path = this.file.getCheckState() == 1 ? new Storage(getCstmId()).getFilePath(this.file.getFileId()) : this.file.getFlag() == 1 ? new Storage(getCstmId()).getPdfPath(this.file.getFileId()) : 
       new Storage(getCstmId()).getTodoFilePath(this.file.getFileId());
     
     java.io.File download_file = new java.io.File(path);
     try
     {
       this.servletResponse.setContentType("application/x-msdownload");
       this.servletResponse.setContentLength((int)download_file.length());
       this.servletResponse.setHeader("Content-Disposition", "attachment;filename=" + 
         URLEncoder.encode(this.file.getFileName(), "UTF-8"));
       this.servletResponse.setHeader("windows-Target", "_blank");
       
       BufferedInputStream input = new BufferedInputStream(
         new FileInputStream(download_file));
       byte[] buffBytes = new byte[1024];
       OutputStream out = this.servletResponse.getOutputStream();
       int read = 0;
       while ((read = input.read(buffBytes)) != -1) {
         out.write(buffBytes, 0, read);
       }
       out.flush();
       out.close();
       input.close();
     }
     catch (IOException e)
     {
       response(JSONErrorString(getText("canceldownload")));
       log.error(e.getMessage());
     }
   }
   
   public String execute()
   {
     return "success";
   }
   
   public void fileedit()
   {
     try
     {
       if ((this.filelist != null) && (this.filelist.size() > 1))
       {
         for (Long fileId : this.filelist)
         {
           com.gnamp.server.model.File tempf = ((FileHandle)getHandle()).read(fileId.longValue());
           tempf.setVender(this.file.getVender());
           tempf.setCategoryId(this.file.getCategoryId());
           ((FileHandle)getHandle()).modify(tempf);
           logEvent(getEnglish("editfile"), getEnglish("editfile") + ":[" + this.file.getFileName() + "]");
         }
       }
       else
       {
         ((FileHandle)getHandle()).modify(this.file);
         logEvent(getEnglish("auditfile"), getEnglish("auditfile") + ":[" + this.file.getFileName() + "]");
       }
       response(JSONSuccessString());
     }
     catch (InnerException e)
     {
       String innerType = e.getInnerType();
       if (innerType.equals("name cannot null")) {
         JsonUtils.writeError(this.servletResponse, 
           getText("name_cannot_null"), "NameCannotNull");
       } else if (innerType.equals("extension cannot modify")) {
         JsonUtils.writeError(this.servletResponse, 
           getText("extension_cannot_change"), "ExtensionCannotModify");
       } else {
         response(JSONErrorString(getText("edititemfail")));
       }
       log.error(e.getMessage());
     }
     catch (Exception e)
     {
       response(JSONErrorString(getText("edititemfail")));
       log.error(e.getMessage());
     }
   }
   
   public void fileUsed()
   {
     try
     {
       for (Long id : this.filelist) {
         if (((FileHandle)getHandle()).fileUsed(id.longValue())) {
           throw new Exception();
         }
       }
       response(JSONSuccessString());
     }
     catch (Exception e)
     {
       response(JSONErrorString(getText("fileusedisdelete")));
       log.error(e.getMessage());
     }
   }
   
   private Map<String, Object> getAuditParam()
   {
     Map<String, Object> param = new HashMap();
     param.put("type", Integer.valueOf(this.file.getType()));
     return param;
   }
   
   private String getCategoryId()
   {
     return 
     
 
       this.fileparam.getCategoryId() == 0 ? " AND CAT_ID IS NULL " : this.fileparam.isIncludeChildren() ? " AND CAT_ID IN(SELECT :CAT_ID AS CAT_ID UNION SELECT CAT_ID FROM tb_category_tree WHERE PARENT_ID=:CAT_ID) " : this.fileparam.getCategoryId() == 0 ? "" : 
       " AND CAT_ID=:CAT_ID ";
   }
   
   private String getCheckState()
   {
     return (this.fileparam.getCheckState() == 0) || 
       (this.fileparam.getCheckState() == 1) ? " AND CHECK_STATE=:CHECK_STATE " : 
       "";
   }
   
   public int getCurrentCateId()
   {
     return this.currentCateId;
   }
   
   public com.gnamp.server.model.File getFile()
   {
     return this.file;
   }
   
   public List<Long> getFilelist()
   {
     return this.filelist;
   }
   
   public TreeToFileParameter getFileparam()
   {
     return this.fileparam;
   }
   
   public List<String> getFilesFileName()
   {
     return this.filesFileName;
   }
   
   private String getFileType()
   {
     return com.gnamp.server.model.File.validType(this.fileparam.getFileType()) ? "AND TYPE=:TYPE " : "";
   }
   
   protected Class<FileHandle> getHandleClass()
   {
     return FileHandle.class;
   }
   
   private List<com.gnamp.server.model.File> getObjectList(Map<String, Object> param)
   {
     return ((FileHandle)getHandle()).readAll(queryString(), param);
   }
   
   public String getoManaer()
   {
     if (Arrays.asList(SORTMANAER).contains(this.oManaer)) {
       return this.oManaer;
     }
     throw new IllegalArgumentException("[" + getText("sortbyerror") + ":" + this.oManaer + "]");
   }
   
   public String getoName()
   {
     if (Arrays.asList(SORTFIELD).contains(this.oName)) {
       return this.oName;
     }
     return SORTFIELD[3];
   }
   
   public PageBean getPage()
   {
     return this.page;
   }
   
   private int getQueryCount(Map<String, Object> param)
   {
     int totalSize = ((FileHandle)getHandle()).queryInteger(queryStringTotal(), param);
     return totalSize;
   }
   
   private String getQueryFileTable()
   {
     StringBuffer buffer = new StringBuffer();
     buffer.append("SELECT * FROM tb_file WHERE CSTM_ID=:CSTM_ID ");
     buffer.append(getFileType());
     buffer.append(getCheckState());
     buffer.append(getCategoryId());
     buffer.append(getVender());
     buffer.append(getSearchFilter());
     return buffer.toString();
   }
   
   private List<com.gnamp.server.model.File> getQueryList()
   {
     Map<String, Object> param = getQueryParam();
     List<com.gnamp.server.model.File> list = getObjectList(param);
     int count = getQueryCount(param);
     if (this.page.getPageSize() == -1) {
       setPage(count);
     } else {
       this.page.setTotalRows(count);
     }
     return list;
   }
   
   private Map<String, Object> getQueryParam()
   {
     Map<String, Object> param = new HashMap();
     param.put("CSTM_ID", Integer.valueOf(getCstmId()));
     if (com.gnamp.server.model.File.validType(this.fileparam.getFileType())) {
       param.put("TYPE", Integer.valueOf(this.fileparam.getFileType()));
     }
     if (this.fileparam.getCategoryId() != 0) {
       param.put("CAT_ID", Integer.valueOf(this.fileparam.getCategoryId()));
     }
     if ((this.fileparam.getCheckState() == 0) || 
       (this.fileparam.getCheckState() == 1)) {
       param.put("CHECK_STATE", Integer.valueOf(this.fileparam.getCheckState()));
     }
     if ((this.fileparam.getFileVendor() != null) && 
       (!"全部".equals(this.fileparam.getFileVendor())) && 
       (!"".equals(this.fileparam.getFileVendor()))) {
       param.put("VENDER", this.fileparam.getFileVendor());
     }
     if ((this.fileparam.getSearchKey() != null) && 
       (!"".equals(this.fileparam.getSearchKey()))) {
       param.put("NAME", "%" + this.fileparam.getSearchKey() + "%");
     }
     if (this.page.getPageSize() != -1)
     {
       param.put("STAET_INDEX", 
         Integer.valueOf(this.page.getCurrentPage() > 1 ? (this.page.getCurrentPage() - 1) * this.page.getPageSize() : 
         0));
       param.put("PAGE_SIZE", Integer.valueOf(this.page.getPageSize()));
     }
     return param;
   }
   
   private String getSearchFilter()
   {
     return (this.fileparam.getSearchKey() == null) || 
       ("".equals(this.fileparam.getSearchKey())) ? "" : "AND NAME LIKE :NAME COLLATE utf8_unicode_ci";
   }
   
   public String getSort()
   {
     return this.sort;
   }
   
   public String getSortFiled()
   {
     return this.sortFiled;
   }
   
   private String getVender()
   {
     return (this.fileparam.getFileVendor() != null) && 
       (!"全部".equals(this.fileparam.getFileVendor())) && 
       (!"".equals(this.fileparam.getFileVendor())) ? " AND VENDER=:VENDER " : "";
   }
   
   public void imgPreview()
   {
     try
     {
       Storage storage = new Storage(getCstmId());
       java.io.File download_file = new java.io.File(storage.getFilePath(this.file.getFileId()));
       BufferedInputStream inputimage = new BufferedInputStream(
         new FileInputStream(download_file));
       BufferedImage image = null;
       image = ImageIO.read(inputimage);
       ServletOutputStream sos = this.servletResponse.getOutputStream();
       ImageIO.write(image, "jpg", sos);
       JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(sos);
       encoder.encode(image);
       inputimage.close();
       sos.flush();
     }
     catch (FileNotFoundException e)
     {
       log.error(e.getMessage());
     }
     catch (IOException e)
     {
       log.error(e.getMessage());
     }
   }
   
   public void loadBean()
   {
     try
     {
       response(JSONSuccessString(JSONObjectToString(((FileHandle)getHandle()).read(this.file.getFileId()))));
     }
     catch (Exception e)
     {
       response(JSONErrorString());
       log.error(e.getMessage());
     }
   }
   
   public String preedit()
   {
     return "fileedit";
   }
   
   public String preuploadfile()
   {
     return "upload";
   }
   
   public String prejuploadfile()
   {
     return "jupload";
   }
   
   public String premms()
   {
     return "mms";
   }
   
   public String previewSwf()
   {
     return "swf";
   }
   
   public String preview()
   {
     try
     {
       long id = this.file.getFileId();
       this.file = ((FileHandle)getHandle()).read(this.file.getFileId());
       if (this.file == null) {
         log.error(getText("notexist") + " ID:" + id);
       }
     }
     catch (Exception e)
     {
       log.error(e.getMessage());
     }
     return "preview";
   }
   
   public void previewfile()
   {
     try
     {
       this.file = ((FileHandle)getHandle()).read(this.file.getFileId());
       Storage storage = new Storage(getCstmId());
       if (this.file.getType() == 0)
       {
         if (this.file.getFlag() == 1) {
           response(JSONSuccessString(JSONArrayToString(Arrays.asList(storage.getPdfPreviewPathes(this.file.getFileId(), this.file.getSegmentNum())))));
         } else {
           response(
             JSONSuccessString(
             this.servletResponse.encodeURL(storage.getFilePath(this.file.getFileId()))));
         }
       }
       else if (this.file.getType() == 1) {
         response(JSONSuccessString(JSONArrayToString(Arrays.asList(storage.getVideoPreviewPathes(this.file.getFileId(), 5)))));
       } else if (this.file.getType() == 3) {
         response(JSONSuccessString(this.servletResponse.encodeURL(storage.getFilePath(this.file.getFileId()))));
       }
     }
     catch (Exception e)
     {
       log.error(e.getMessage());
     }
   }
   
   public void previewfilenew()
   {
     String address = "";
     try
     {
       this.file = ((FileHandle)getHandle()).read(this.file.getFileId());
       this.servletResponse.setContentType("image/jpeg");
       Storage storage = new Storage(getCstmId());
       if (this.file.getType() == 0)
       {
         if (this.file.getFlag() == 1)
         {
           String[] thumbs = storage.getPdfPreviewPathes(this.file.getFileId(), this.file.getSegmentNum());
           
 
           address = thumbs[getPreviewNumber()];
         }
         else
         {
           address = storage.getFilePath(this.file.getFileId());
         }
       }
       else if (this.file.getType() == 1)
       {
         String[] thumbs = storage.getVideoPreviewPathes(this.file.getFileId(), 5);
         
 
         address = thumbs[getPreviewNumber()];
       }
       else if (this.file.getType() == 2)
       {
         address = getDefaultAutojpg();
       }
       else if (this.file.getType() == 3)
       {
         address = storage.getFilePath(this.file.getFileId());
       }
     }
     catch (Exception e)
     {
       log.error(e.getMessage());
     }
     int BUF_SIZE = 1048576;
     byte[] buf = null;
     FileInputStream fileStream = null;
     OutputStream outStream = null;
     java.io.File download_file = null;
     
     long size = 0L;
     try
     {
       download_file = new java.io.File(address);
       if (!download_file.exists()) {
         throw new HttpException(
           500, 
           "Cannot find file: " + address);
       }
       size = download_file.length();
       try
       {
         fileStream = new FileInputStream(download_file);
         outStream = this.servletResponse.getOutputStream();
         
         int send_len = 0;
         while (send_len < (int)size)
         {
           int data_size = (int)size - send_len < 1048576 ? (int)size - send_len : 
             1048576;
           if (buf == null) {
             buf = new byte[data_size];
           }
           int read_len = 0;
           while (read_len < data_size)
           {
             int len = fileStream.read(buf, read_len, data_size - 
               read_len);
             if (len <= 0) {
               throw new HttpException(
                 500, 
                 "read file failed(" + len + "): " + address);
             }
             read_len += len;
           }
           outStream.write(buf, 0, data_size);
           outStream.flush();
           
           send_len += data_size;
         }
       }
       finally
       {
         try
         {
           if (outStream != null)
           {
             outStream.close();
             outStream = null;
           }
           if (fileStream != null)
           {
             fileStream.close();
             fileStream = null;
           }
         }
         catch (IOException localIOException) {}
         if (buf != null)
         {
           buf = null;
           System.gc();
         }
       }
     }
     catch (Exception localException1) {}
   }
   
   protected String getDefaultAutojpg()
   {
     try
     {
       String classDirectory = Storage.class.getClassLoader()
         .getResource("/").getPath();
       classDirectory = 
         URLDecoder.decode(classDirectory, "UTF-8");
       java.io.File f = new java.io.File(classDirectory);
       f = f.getParentFile().getParentFile();
       return f.getAbsolutePath() + java.io.File.separatorChar + "skins" + java.io.File.separatorChar + "default" + java.io.File.separatorChar + 
         "images" + java.io.File.separatorChar + "audio_icon.png";
     }
     catch (Exception ex) {}
     return null;
   }
   
   protected String getDefaultFlashJpg()
   {
     try
     {
       String classDirectory = Storage.class.getClassLoader().getResource("/").getPath();
       classDirectory = URLDecoder.decode(classDirectory, "UTF-8");
       java.io.File f = new java.io.File(classDirectory);
       f = f.getParentFile().getParentFile();
       return f.getAbsolutePath() + java.io.File.separatorChar + "skins" + java.io.File.separatorChar + "default" + java.io.File.separatorChar + 
         "images" + java.io.File.separatorChar + "swf_icon.png";
     }
     catch (Exception ex) {}
     return null;
   }
   
   protected String getDefaultMmsjpg()
   {
     try
     {
       String classDirectory = Storage.class.getClassLoader()
         .getResource("/").getPath();
       classDirectory = 
         URLDecoder.decode(classDirectory, "UTF-8");
       java.io.File f = new java.io.File(classDirectory);
       f = f.getParentFile().getParentFile();
       return f.getAbsolutePath() + java.io.File.separatorChar + "skins" + java.io.File.separatorChar + "default" + java.io.File.separatorChar + 
         "images" + java.io.File.separatorChar + "mms_icon.png";
     }
     catch (Exception ex) {}
     return null;
   }
   
   public void query()
   {
     try
     {
       List<com.gnamp.server.model.File> list = getQueryList();
       if (list == null)
       {
         response(JSONErrorString());
         return;
       }
       response(JSONSuccessString(JSONArrayToString(list), 
         new MapTool().putObject("page", this.page)));
     }
     catch (Exception error)
     {
       response(JSONErrorString());
       log.error(error.getMessage(), error);
     }
   }
   
   private String queryString()
   {
     StringBuffer buffer = new StringBuffer();
     buffer.append("SELECT ");
     buffer.append("FILE_ID, ");
     buffer.append("f.NAME AS NAME, ");
     buffer.append("f.DESCP AS DESCP, ");
     buffer.append("f.CAT_ID AS CAT_ID, ");
     buffer.append("c.NAME AS CAT_NAME, ");
     buffer.append("TYPE, ");
     buffer.append("SIZE, ");
     buffer.append("SUM, ");
     buffer.append("f.SEGMENT_NUM as SEGMENT_NUM, ");
     buffer.append("f.FLAG as FLAG, ");
     buffer.append("UPLOAD_TIME, ");
     buffer.append("UPLOAD_USER, ");
     buffer.append("CHECK_TIME, ");
     buffer.append("CHECK_USER, ");
     buffer.append("VENDER, ");
     buffer.append("USE_COUNT, ");
     buffer.append("CHECK_STATE ");
     buffer.append("FROM (");
     buffer.append(getQueryFileTable());
     buffer.append(") as f ");
     buffer.append("LEFT JOIN tb_category as c on f.CSTM_ID=c.CSTM_ID AND f.CAT_ID=c.CAT_ID ");
     buffer.append(" ORDER BY ");
     buffer.append(getoName());
     buffer.append(" ");
     buffer.append(getoManaer());
     if (this.page.getPageSize() != -1) {
       buffer.append(" LIMIT :STAET_INDEX,:PAGE_SIZE");
     }
     return buffer.toString();
   }
   
   private String queryStringTotal()
   {
     StringBuffer buffer = new StringBuffer();
     buffer.append("SELECT ");
     buffer.append("count(1) ");
     buffer.append("FROM (");
     buffer.append(getQueryFileTable());
     buffer.append(") as f ");
     buffer.append("LEFT JOIN tb_category as c on f.CSTM_ID=c.CSTM_ID AND f.CAT_ID=c.CAT_ID");
     return buffer.toString();
   }
   
   public void remove()
   {
     Set<String> errors = new TreeSet();
     try
     {
       for (Long id : this.filelist) {
         try
         {
           if (!((FileHandle)getHandle()).remove(id.longValue())) {
             errors.add(id + getText("deletefailed"));
           } else {
             logEvent(getEnglish("deletefile"), getEnglish("deletefile") + ":[" + ((FileHandle)getHandle()).read(id.longValue()).getFileName() + "]");
           }
         }
         catch (Exception e)
         {
           errors.add(id + getText("deletefailed"));
         }
       }
       if (errors.size() > 0) {
         throw new Exception(errors.toString());
       }
       response(JSONSuccessString());
     }
     catch (Exception e)
     {
       response(JSONErrorString(getText("deletefailed")));
       log.error(e.getMessage());
     }
   }
   
   public String selectfile()
   {
     return "selectfile";
   }
   
   public void setCurrentCateId(int currentCateId)
   {
     this.currentCateId = currentCateId;
   }
   
   public void setFile(com.gnamp.server.model.File file)
   {
     this.file = file;
   }
   
   public void setFilelist(List<Long> filelist)
   {
     this.filelist = filelist;
   }
   
   public void setFileparam(TreeToFileParameter fileparam)
   {
     this.fileparam = fileparam;
   }
   
   public void setFilesFileName(List<String> filesFileName)
   {
     this.filesFileName = filesFileName;
   }
   
   public void setOManaer(String oManaer)
   {
     this.oManaer = oManaer;
   }
   
   public void setOName(String oName)
   {
     this.oName = oName;
   }
   
   private void setPage(int totalSize)
   {
     this.page.setTotalRows(totalSize);
     this.page.setPageSize(totalSize);
   }
   
   public void setPage(PageBean page)
   {
     this.page = page;
   }
   
   public void setSort(String sort)
   {
     this.sort = sort;
   }
   
   public void setSortFiled(String sortFiled)
   {
     this.sortFiled = sortFiled;
   }
   
   public void showview()
   {
     Storage storage = new Storage(getCstmId());
     
     String address = "";
     
     com.gnamp.server.model.File f = ((FileHandle)getHandle()).read(this.file.getFileId());
     if (f.getType() == 2) {
       address = getDefaultAutojpg();
     } else if (f.getType() == 3) {
       address = getDefaultFlashJpg();
     } else if (f.getFlag() == 2) {
       address = getDefaultMmsjpg();
     } else {
       address = storage.getThumbnailPath(this.file.getFileId());
     }
     int BUF_SIZE = 1048576;
     byte[] buf = null;
     FileInputStream fileStream = null;
     OutputStream outStream = null;
     java.io.File download_file = null;
     
     long size = 0L;
     try
     {
       download_file = new java.io.File(address);
       if (!download_file.exists()) {
         throw new HttpException(
           500, 
           "Cannot find file: " + address);
       }
       size = download_file.length();
       
       this.servletResponse.setContentType("image/jpeg");
       try
       {
         fileStream = new FileInputStream(download_file);
         outStream = this.servletResponse.getOutputStream();
         
         int send_len = 0;
         while (send_len < (int)size)
         {
           int data_size = (int)size - send_len < 1048576 ? (int)size - send_len : 
             1048576;
           if (buf == null) {
             buf = new byte[data_size];
           }
           int read_len = 0;
           while (read_len < data_size)
           {
             int len = fileStream.read(buf, read_len, data_size - 
               read_len);
             if (len <= 0) {
               throw new HttpException(
                 500, 
                 "read file failed(" + len + "): " + address);
             }
             read_len += len;
           }
           outStream.write(buf, 0, data_size);
           outStream.flush();
           
           send_len += data_size;
         }
       }
       finally
       {
         try
         {
           if (outStream != null)
           {
             outStream.close();
             outStream = null;
           }
           if (fileStream != null)
           {
             fileStream.close();
             fileStream = null;
           }
         }
         catch (IOException localIOException) {}
         if (buf != null)
         {
           buf = null;
           System.gc();
         }
       }
     }
     catch (Exception localException) {}
   }
   
   public void validateCheckfile()
   {
     if (this.filelist == null)
     {
       addFieldError("filelist", getText("checkFaild"));
       return;
     }
     if (this.filelist.size() == 0) {
       addFieldError("filelist", getText("auditfilenotexist"));
     }
   }
   
   public void validateFileedit()
   {
     if (this.file == null)
     {
       addFieldError("file", getText("fileisnull"));
       return;
     }
     String description = this.file.getDescription();
     if (description.length() > 250) {
       addFieldError("file", getText("descriptionlengtherror"));
     }
   }
   
   public void validatePreview()
   {
     if (this.file == null)
     {
       addFieldError("field", getText("fileisnull"));
       return;
     }
     this.file.getFileId();
   }
   
   public void venders()
   {
     try
     {
       List<com.gnamp.server.model.File> files = ((FileHandle)getHandle()).readAll();
       Set<String> vender = new HashSet();
       for (com.gnamp.server.model.File f : files) {
         if ((f.getVender() != null) && (!"".equals(f.getVender().trim()))) {
           vender.add(f.getVender());
         }
       }
       response(JSONSuccessString(JSONArrayToString(vender)));
     }
     catch (Exception e)
     {
       response(JSONErrorString());
       log.error(e.getMessage());
     }
   }
   
   public String getRootDirectory()
   {
     return this.servletContext.getRealPath("");
   }
   
   public String getTempDirectory()
   {
     return 
     
 
       getRootDirectory() + java.io.File.separatorChar + "temp" + java.io.File.separatorChar + getCstmId() + java.io.File.separatorChar;
   }
   
   int categoryId = -1;
   boolean autoCheck = false;
   
   public boolean isAutoCheck()
   {
     return this.autoCheck;
   }
   
   public void setAutoCheck(boolean autoCheck)
   {
     this.autoCheck = autoCheck;
   }
   
   public void setCategoryId(int categoryId)
   {
     this.categoryId = categoryId;
   }
   
   public String mmsAddress = "mms://192.168.1.2/13";
   
   public String getMmsAddress()
   {
     return this.mmsAddress;
   }
   
   public void setMmsAddress(String mmsAddress)
   {
     this.mmsAddress = mmsAddress;
   }
   
   public void mms()
   {
     try
     {
       String path = getTempDirectory() + getUserId() + 
         java.io.File.separatorChar + "upload" + 
         java.io.File.separatorChar + this.mmsAddress.replace("://", "_").replace("/", "_") + ".asx";
       
       String content = "";
       content = content + "<asx><entry><ref href = \"" + 
       
         this.mmsAddress + "\"/>" + 
         "</entry>" + 
         "</asx>";
       
       FileWriter fw = null;
       BufferedWriter bw = null;
       
       java.io.File dir = new java.io.File(path);
       if (!dir.exists()) {
         Storage.createFileDirectory(path);
       }
       try
       {
         fw = new FileWriter(path, false);
         bw = new BufferedWriter(fw);
         fw.write(content);
       }
       catch (IOException localIOException)
       {
         if (bw != null) {
           try
           {
             bw.close();
           }
           catch (IOException localIOException1) {}
         }
         if (fw != null) {
           try
           {
             fw.close();
           }
           catch (IOException localIOException2) {}
         }
       }
       finally
       {
         if (bw != null) {
           try
           {
             bw.close();
           }
           catch (IOException localIOException3) {}
         }
         if (fw != null) {
           try
           {
             fw.close();
           }
           catch (IOException localIOException4) {}
         }
       }
       FileHandle fileHandle = new FileHandle(this);
       if ((this.categoryId > 0) && 
         (new CategoryHandle(this).read(this.categoryId) == null)) {
         this.categoryId = 0;
       }
       com.gnamp.server.model.File file = fileHandle.upload(
         path, 
         this.mmsAddress.replace("://", "_").replace("/", "_") + ".asx", 
         this.mmsAddress.replace("://", "_").replace("/", "_") + ".asx", 
         this.categoryId, 1, "", 2);
       
       logEvent(getEnglish("uploadfile"), getEnglish("uploadfile") + ":[" + this.mmsAddress.replace("://", "_").replace("/", "_") + ".asx" + "]");
       if (this.autoCheck) {
         fileHandle.check(file);
       }
     }
     catch (Exception ee)
     {
       ee.printStackTrace();
     }
     response(JSONSuccessString());
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.FileAction
 * JD-Core Version:    0.7.0.1
 */