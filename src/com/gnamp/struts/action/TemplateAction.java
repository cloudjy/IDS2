 package com.gnamp.struts.action;
 
 import com.gnamp.server.InnerException;
 import com.gnamp.server.NameExistException;
 import com.gnamp.server.Storage;
 import com.gnamp.server.handle.CategoryHandle;
 import com.gnamp.server.handle.FileHandle;
 import com.gnamp.server.handle.TemplateHandle;
 import com.gnamp.server.handle.TemplateRectHandle;
 import com.gnamp.server.handle.TemplateRectImageHandle;
 import com.gnamp.server.model.Category;
 import com.gnamp.server.model.Rect;
 import com.gnamp.server.model.SystemTemplate;
 import com.gnamp.server.model.SystemTemplateRectImage;
 import com.gnamp.server.model.Template;
 import com.gnamp.server.model.TemplateRectImage;
 import com.gnamp.struts.tree.ITree;
 import com.gnamp.struts.utils.JsonUtils;
 import com.gnamp.struts.utils.MapTool;
 import com.gnamp.struts.vo.PageBean;
 import common.Logger;
 import java.io.FileInputStream;
 import java.io.IOException;
 import java.io.OutputStream;
 import java.io.PrintWriter;
 import java.net.URLDecoder;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import java.util.Set;
 import java.util.TreeSet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.sf.json.JSONArray;
 import org.apache.commons.lang.StringUtils;
 
 public class TemplateAction
   extends JSONAction
 {
   TemplateHandle templatehandle = null;
   
   public TemplateHandle getTemplateHandle()
   {
     return this.templatehandle == null ? (this.templatehandle = new TemplateHandle(
       this)) : this.templatehandle;
   }
   
   TemplateRectHandle templaterecthandle = null;
   
   public TemplateRectHandle getTemplateRectHandle()
   {
     return this.templaterecthandle == null ? (this.templaterecthandle = new TemplateRectHandle(
       this)) : this.templaterecthandle;
   }
   
   CategoryHandle categoryhandle = null;
   
   public CategoryHandle getCategoryHandle()
   {
     return this.categoryhandle == null ? (this.categoryhandle = new CategoryHandle(
       this)) : this.categoryhandle;
   }
   
   TemplateRectImageHandle imagerecthandle = null;
   
   public TemplateRectImageHandle getTemplateRectImageHandle()
   {
     return this.imagerecthandle == null ? (this.imagerecthandle = new TemplateRectImageHandle(
       this)) : this.imagerecthandle;
   }
   
   FileHandle filehandle = null;
   Template template;
   
   public FileHandle getFileHandle()
   {
     return this.filehandle == null ? (this.filehandle = new FileHandle(this)) : 
       this.filehandle;
   }
   
   public Template getTemplate()
   {
     return this.template;
   }
   
   public void setTemplate(Template template)
   {
     this.template = template;
   }
   
   public String leftstr = "";
   
   public String getLeftstr()
   {
     return this.leftstr;
   }
   
   public void setLeftstr(String leftstr)
   {
     this.leftstr = leftstr;
   }
   
   public int id = 0;
   
   public int getId()
   {
     return this.id;
   }
   
   public void setId(int id)
   {
     this.id = id;
   }
   
   PageBean page = new PageBean();
   PageBean page1 = new PageBean();
   public List<Integer> ts;
   
   public PageBean getPage1()
   {
     return this.page1;
   }
   
   public void setPage1(PageBean page1)
   {
     this.page1 = page1;
   }
   
   public PageBean getPage()
   {
     return this.page;
   }
   
   public void setPage(PageBean page)
   {
     this.page = page;
   }
   
   public String execute()
   {
     return "list";
   }
   
   public void list()
   {
     try
     {
       this.servletResponse.setContentType("application/x-json");
       this.servletResponse.setCharacterEncoding("UTF-8");
       PrintWriter writer = this.servletResponse.getWriter();
       writer.print(JSONArray.fromObject(convertCategorys(getTemplateHandle()
         .readAllCategorys())));
       writer.flush();
     }
     catch (IOException e)
     {
       e.printStackTrace();
     }
   }
   
   public List<ITree<String>> convertCategorys(List<String> categorys)
   {
     List<ITree<String>> lists = new ArrayList();
     for (int i = 0; i < categorys.size(); i++) {
       lists.add(new CategorysVo(i, (String)categorys.get(i)));
     }
     return lists;
   }
   
   public void templets()
   {
     try
     {
       String sql = "";
       if ((this.leftstr != null) && (!this.leftstr.equals("")))
       {
         this.leftstr = URLDecoder.decode(this.leftstr, "utf-8");
         sql = "SELECT TMPLT_ID, NAME, DESCP, CATEGORY, WIDTH, HEIGHT, MAIN_RECT, STATE, CREATE_TIME, CREATE_USER  FROM tb_template WHERE CSTM_ID=" + 
           getCstmId() + " AND CATEGORY=:CATEGORY " + 
           "ORDER BY NAME " + (
           this.page.getPageSize() != -1 ? "LIMIT " + 
           (this.page.getCurrentPage() - 1) * 
           this.page.getPageSize() + "," + this.page.getPageSize() : 
           "");
         
         this.page.setTotalRows(getTemplateHandle().readAll("SELECT TMPLT_ID, NAME, DESCP, CATEGORY, WIDTH, HEIGHT, MAIN_RECT, STATE, CREATE_TIME, CREATE_USER  FROM tb_template  WHERE CSTM_ID=" + 
           getCstmId() + " AND CATEGORY='" + this.leftstr + "' " + 
           " ORDER BY NAME ", null).size());
         response(JSONSuccessString(
           JSONArrayToString(getTemplateHandle().readAll(sql, 
           getParameters(this.leftstr))), 
           new MapTool().putObject("page", this.page)));
       }
       else
       {
         sql = 
         
 
           "SELECT TMPLT_ID, NAME, DESCP, CATEGORY, WIDTH, HEIGHT, MAIN_RECT, STATE, CREATE_TIME, CREATE_USER  FROM tb_template WHERE CSTM_ID=" + getCstmId() + " " + " ORDER BY NAME " + (this.page.getPageSize() != -1 ? "LIMIT " + 
           (this.page.getCurrentPage() - 1) * 
           this.page.getPageSize() + "," + this.page.getPageSize() : 
           "");
         
         this.page.setTotalRows(getTemplateHandle().readAll("SELECT TMPLT_ID, NAME, DESCP, CATEGORY, WIDTH, HEIGHT, MAIN_RECT, STATE, CREATE_TIME, CREATE_USER  FROM tb_template  WHERE CSTM_ID=" + 
           getCstmId() + " " + 
           " ORDER BY NAME ", null).size());
         response(JSONSuccessString(
           JSONArrayToString(getTemplateHandle().readAll(sql, null)), 
           new MapTool().putObject("page", this.page)));
       }
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
   
   public void systemtemplets()
   {
     try
     {
       String sql = "";
       this.page1.setPageSize(2147483647);
       if ((this.leftstr != null) && (!this.leftstr.equals("")))
       {
         this.leftstr = URLDecoder.decode(this.leftstr, "utf-8");
         sql = "SELECT TMPLT_ID, NAME, DESCP, CATEGORY, WIDTH, HEIGHT, MAIN_RECT, STATE, CREATE_TIME, CREATE_USER  FROM tb_system_template WHERE CATEGORY=:CATEGORY ORDER BY NAME " + (
         
 
           this.page1.getPageSize() != -1 ? "LIMIT " + 
           (this.page1.getCurrentPage() - 1) * 
           this.page1.getPageSize() + "," + this.page1.getPageSize() : 
           "");
         
         this.page1.setTotalRows(getTemplateHandle().readAllSystem("SELECT TMPLT_ID, NAME, DESCP, CATEGORY, WIDTH, HEIGHT, MAIN_RECT, STATE, CREATE_TIME, CREATE_USER  FROM tb_system_template  WHERE CATEGORY='" + 
           this.leftstr + "' " + 
           " ORDER BY NAME ", null).size());
         response(JSONSuccessString(
           JSONArrayToString(getTemplateHandle().readAllSystem(sql, 
           getParameters(this.leftstr))), 
           new MapTool().putObject("page", this.page1)));
       }
       else
       {
         sql = 
         
 
           "SELECT TMPLT_ID, NAME, DESCP, CATEGORY, WIDTH, HEIGHT, MAIN_RECT, STATE, CREATE_TIME, CREATE_USER  FROM tb_system_template WHERE 1=1 ORDER BY NAME " + (this.page1.getPageSize() != -1 ? "LIMIT " + 
           (this.page1.getCurrentPage() - 1) * 
           this.page1.getPageSize() + "," + this.page1.getPageSize() : 
           "");
         
         this.page1.setTotalRows(getTemplateHandle().readAllSystem("SELECT TMPLT_ID, NAME, DESCP, CATEGORY, WIDTH, HEIGHT, MAIN_RECT, STATE, CREATE_TIME, CREATE_USER  FROM tb_system_template  WHERE 1=1  ORDER BY NAME ", 
         
           null).size());
         response(JSONSuccessString(
           JSONArrayToString(getTemplateHandle().readAllSystem(sql, null)), 
           new MapTool().putObject("page", this.page1)));
       }
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
   
   public List<Integer> getTs()
   {
     return this.ts;
   }
   
   public void setTs(List<Integer> ts)
   {
     this.ts = ts;
   }
   
   public void remove()
   {
     Set<Integer> errors = new TreeSet();
     try
     {
       for (Integer i : this.ts) {
         try
         {
           if (!getTemplateHandle().remove(i.intValue())) {
             errors.add(i);
           }
         }
         catch (Exception e)
         {
           errors.add(i);
           throw new Exception();
         }
       }
       response(JSONSuccessString());
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorMessage(this.servletResponse, getText("deleteerror") + 
         " ID:" + errors);
     }
   }
   
   private MapTool<String, Object> getParameters(String category)
   {
     return ("".equals(category)) || (category == null) ? null : 
       new MapTool().putObject("CATEGORY", category);
   }
   
   public List<String> systemtempletcategorys = null;
   
   public List<String> getSystemtempletcategorys()
   {
     return this.systemtempletcategorys;
   }
   
   public void setSystemtempletcategorys(List<String> systemtempletcategorys)
   {
     this.systemtempletcategorys = systemtempletcategorys;
   }
   
   public String ToAdd()
   {
     this.systemtempletcategorys = getTemplateHandle().readAllCategorysSystem();
     return "add";
   }
   
   public boolean istemplet = false;
   
   public boolean isIstemplet()
   {
     return this.istemplet;
   }
   
   public void setIstemplet(boolean istemplet)
   {
     this.istemplet = istemplet;
   }
   
   public void add()
   {
     try
     {
       if (StringUtils.isBlank(this.template.getTempName()))
       {
         JsonUtils.writeError(this.servletResponse, getText("name_cannot_null"), "NameCannotNull");
         return;
       }
       if (StringUtils.isBlank(this.template.getCategory()))
       {
         JsonUtils.writeError(this.servletResponse, getText("category_cannot_null"), "CategoryCannotNull");
         return;
       }
       if (!this.istemplet) {
         addBaseOnNull();
       } else {
         addBaseOnInternal();
       }
       JsonUtils.writeSuccess(this.servletResponse);
     }
     catch (NameExistException e)
     {
       JsonUtils.writeError(this.servletResponse, getText("name_existed"), "NameExist");
       log.error(e.getMessage());
     }
     catch (InnerException e)
     {
       String innerType = e.getInnerType();
       if (innerType.equals("name cannot null")) {
         JsonUtils.writeError(this.servletResponse, getText("name_cannot_null"), "NameCannotNull");
       } else if (innerType.equals("category cannot null")) {
         JsonUtils.writeError(this.servletResponse, getText("category_cannot_null"), "CategoryCannotNull");
       } else {
         JsonUtils.writeErrorMessage(this.servletResponse, getText("additemfail"));
       }
       log.error(e.getMessage());
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorMessage(this.servletResponse, getText("additemfail"));
       log.error(e.getMessage());
     }
   }
   
   private void addBaseOnNull()
     throws Exception
   {
     if (!getTemplateHandle().create(this.template)) {
       throw new Exception(getText("additemfail"));
     }
   }
   
   private void addBaseOnInternal()
     throws Exception
   {
     SystemTemplate source = getTemplateHandle().readSystem(this.tempID);
     if (source == null) {
       throw new Exception(getText("additemfail"));
     }
     this.template.setWidth(source.getWidth());
     this.template.setHeight(source.getHeight());
     if (!getTemplateHandle().create(this.template)) {
       throw new Exception(getText("additemfail"));
     }
     int rectNumber = 0;
     List<Rect> list = getTemplateRectHandle().readAllSystem(this.tempID);
     if ((list == null) || ((rectNumber = list.size()) <= 0)) {
       return;
     }
     try
     {
       int mainrect = 0;
       for (int i = 0; i < rectNumber; i++)
       {
         Rect rect = (Rect)list.get(i);
         rect.setTemplateId(this.template.getTempId());
         getTemplateRectHandle().create(rect);
         if (source.getMainRect() == rect.getRectId()) {
           mainrect = rect.getRectId();
         }
         List<SystemTemplateRectImage> rectimages = null;
         if (rect.getRecType() == 1) {
           rectimages = getTemplateRectImageHandle().readAllSystem(
             this.tempID, rect.getRectId());
         }
         if (rectimages != null) {
           for (int j = 0; j < rectimages.size(); j++)
           {
             SystemTemplateRectImage t = (SystemTemplateRectImage)rectimages.get(j);
             
             TemplateRectImage r = new TemplateRectImage();
             r.setTempId(this.template.getTempId());
             r.setRectId(rect.getRectId());
             r.setFileId(t.getFileId());
             r.setPlayTime(t.getPlayTime());
             r.setScaleStyle(t.getScaleStyle());
             r.setSeq(t.getSeq());
             r.setSwapTime(t.getSwapTime());
             r.setSwapStyle(t.getSwapStyle());
             
             getTemplateRectImageHandle().create(r);
           }
         }
       }
       if (mainrect != this.template.getMainRect())
       {
         this.template.setMainRect(mainrect);
         getTemplateHandle().modify(this.template);
       }
     }
     catch (Exception e)
     {
       if (this.template.getTempId() != 0) {
         try
         {
           getTemplateHandle().remove(this.template.getTempId());
         }
         catch (Exception e1)
         {
           log.error("##: " + e1.getMessage(), e1);
         }
       }
       log.error("## copy system template failed ##: " + e, e);
       throw e;
     }
   }
   
   public String innerHtml = "";
   public int RequestW = 0;
   public int RequestH = 0;
   
   public String getInnerHtml()
   {
     return this.innerHtml;
   }
   
   public void setInnerHtml(String innerHtml)
   {
     this.innerHtml = innerHtml;
   }
   
   public int getRequestW()
   {
     return this.RequestW;
   }
   
   public void setRequestW(int requestW)
   {
     this.RequestW = requestW;
   }
   
   public int getRequestH()
   {
     return this.RequestH;
   }
   
   public void setRequestH(int requestH)
   {
     this.RequestH = requestH;
   }
   
   public String showtemplate()
   {
     this.innerHtml = (this.innerHtml + "<div style=\"border: thin solid Black; width:" + this.RequestW * 540 / this.RequestH + "px;  background-color: GrayText; height:" + 540 + "px; top: " + 10 + "px; left: " + (980 - this.RequestW * 540 / this.RequestH) / 2 + "px; position: absolute;\">");
     
 
 
 
 
     this.innerHtml = (this.innerHtml + "<img style=\"top: 10px; left: " + (980 - this.RequestW * 540 / this.RequestH) / 2 + "px;\" src=\"" + "templet!preview?fileId=" + this.tempID + "\"" + "  width=\"" + this.RequestW * 540 / this.RequestH + "px\"" + "  height=\"" + 540 + "px\" />");
     
     this.innerHtml += "</div>";
     
     return "showtemplate";
   }
   
   public long fileId = 0L;
   
   public long getFileId()
   {
     return this.fileId;
   }
   
   public void setFileId(long fileId)
   {
     this.fileId = fileId;
   }
   
   public void preview()
   {
     int BUF_SIZE = 1048576;
     byte[] buf = null;
     
     FileInputStream fileStream = null;
     OutputStream outStream = null;
     try
     {
       long size = 0L;
       Storage storage = new Storage(getCstmId());
       String address = "";
       String imageType = "";
       if ((this.fileId > 0L) && (this.fileId < 2147483647L))
       {
         address = 
           Storage.getWorkRootPath() + "buildin" + java.io.File.separatorChar + "preview" + java.io.File.separatorChar + this.fileId + ".jpg";
         
         imageType = "Scrambled";
       }
       else
       {
         address = storage.getFilePath(this.fileId);
         imageType = "Normal";
       }
       java.io.File download_file = new java.io.File(address);
       if (!download_file.exists()) {
         throw new HttpException(
           500, 
           "Cannot find file: " + address);
       }
       size = download_file.length();
       
       this.servletResponse.setHeader("Pragma", "No-cache");
       this.servletResponse.setHeader("Cache-Control", "no-cache");
       this.servletResponse.setHeader("Cache-Control", "no-store");
       this.servletResponse.setDateHeader("Expires", 0L);
       
       this.servletResponse.setHeader("Content-Length", Long.toString(size));
       this.servletResponse.addHeader("Image-Type", imageType);
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
   
   public void watermark()
   {
     int BUF_SIZE = 1048576;
     byte[] buf = null;
     
     FileInputStream fileStream = null;
     OutputStream outStream = null;
     try
     {
       long size = 0L;
       Storage storage = new Storage(getCstmId());
       String address = "";
       String imageType = "";
       if ((this.fileId > 0L) && (this.fileId < 2147483647L))
       {
         address = 
           Storage.getWorkRootPath() + "buildin" + java.io.File.separatorChar + "watermark" + java.io.File.separatorChar + this.fileId + ".jpg";
         
         imageType = "Scrambled";
       }
       else
       {
         address = storage.getFilePath(this.fileId);
         imageType = "Normal";
       }
       java.io.File download_file = new java.io.File(address);
       if (!download_file.exists()) {
         throw new HttpException(
           500, 
           "Cannot find file: " + address);
       }
       size = download_file.length();
       
       this.servletResponse.setHeader("Pragma", "No-cache");
       this.servletResponse.setHeader("Cache-Control", "no-cache");
       this.servletResponse.setHeader("Cache-Control", "no-store");
       this.servletResponse.setDateHeader("Expires", 0L);
       
       this.servletResponse.setHeader("Content-Length", Long.toString(size));
       this.servletResponse.addHeader("Image-Type", imageType);
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
   
   public String ToEdit()
   {
     this.template = getTemplateHandle().read(this.template.getTempId());
     
     return "edit";
   }
   
   public String content()
   {
     return "content";
   }
   
   public String systemcontent()
   {
     return "systemcontent";
   }
   
   public void edit()
   {
     try
     {
       if (StringUtils.isBlank(this.template.getTempName()))
       {
         JsonUtils.writeError(this.servletResponse, getText("name_cannot_null"), "NameCannotNull");
         return;
       }
       if (getTemplateHandle().modify(this.template.getTempId(), this.template.getTempName(), this.template.getTempDesc())) {
         JsonUtils.writeSuccess(this.servletResponse);
       } else {
         throw new Exception("edititemfail");
       }
     }
     catch (NameExistException e)
     {
       JsonUtils.writeError(this.servletResponse, getText("name_existed"), "NameExist");
       log.error(e.getMessage());
     }
     catch (InnerException e)
     {
       String innerType = e.getInnerType();
       if (innerType.equals("name cannot null")) {
         JsonUtils.writeError(this.servletResponse, getText("name_cannot_null"), "NameCannotNull");
       } else {
         JsonUtils.writeErrorMessage(this.servletResponse, getText("edititemfail"));
       }
       log.error(e.getMessage());
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorMessage(this.servletResponse, getText("edititemfail"));
       log.error(e.getMessage());
     }
   }
   
   public int tempID = 0;
   
   public int getTempID()
   {
     return this.tempID;
   }
   
   public void setTempID(int tempID)
   {
     this.tempID = tempID;
   }
   
   List<Rect> mainrectlist = new ArrayList();
   
   public List<Rect> getNewlist()
   {
     return this.mainrectlist;
   }
   
   public void setNewlist(List<Rect> mainrectlist)
   {
     this.mainrectlist = mainrectlist;
   }
   
   Rect[] allmainrects = null;
   
   public void getAllMainrects()
   {
     List<Rect> list = getTemplateRectHandle().readAll(this.tempID);
     if (list != null) {
       for (int i = 0; i < list.size(); i++)
       {
         if ((((Rect)list.get(i)).getRecType() == 1) || 
           (((Rect)list.get(i)).getRecType() == 2) || 
           (((Rect)list.get(i)).getRecType() == 15) || 
           (((Rect)list.get(i)).getRecType() == 16) || 
           (((Rect)list.get(i)).getRecType() == 14) || 
           (((Rect)list.get(i)).getRecType() == 12) || 
           (((Rect)list.get(i)).getRecType() == 13) || 
           (((Rect)list.get(i)).getRecType() == 11) || 
           (((Rect)list.get(i)).getRecType() == 17) || 
           (((Rect)list.get(i)).getRecType() == 18)) {
           this.mainrectlist.add((Rect)list.get(i));
         }
         this.allmainrects = new Rect[this.mainrectlist.size()];
         this.mainrectlist.toArray(this.allmainrects);
       }
     }
     JsonUtils.writeSuccessData(this.servletResponse, this.mainrectlist);
   }
   
   public void getAllMainrectsSystem()
   {
     List<Rect> list = getTemplateRectHandle().readAllSystem(this.tempID);
     if (list != null) {
       for (int i = 0; i < list.size(); i++)
       {
         if ((((Rect)list.get(i)).getRecType() == 1) || 
           (((Rect)list.get(i)).getRecType() == 2) || 
           (((Rect)list.get(i)).getRecType() == 15) || 
           (((Rect)list.get(i)).getRecType() == 16) || 
           (((Rect)list.get(i)).getRecType() == 14) || 
           (((Rect)list.get(i)).getRecType() == 12) || 
           (((Rect)list.get(i)).getRecType() == 13) || 
           (((Rect)list.get(i)).getRecType() == 11) || 
           (((Rect)list.get(i)).getRecType() == 17) || 
           (((Rect)list.get(i)).getRecType() == 18)) {
           this.mainrectlist.add((Rect)list.get(i));
         }
         this.allmainrects = new Rect[this.mainrectlist.size()];
         this.mainrectlist.toArray(this.allmainrects);
       }
     }
     JsonUtils.writeSuccessData(this.servletResponse, this.mainrectlist);
   }
   
   Rect[] allrects = null;
   
   public void GetTodoTemplateLayout()
   {
     String xml = "";
     try
     {
       List<Rect> list = getTemplateRectHandle().readAll(this.tempID);
       if (list != null)
       {
         this.allrects = new Rect[list.size()];
         list.toArray(this.allrects);
       }
       Template temp = getTemplateHandle().read(this.tempID);
       
       Program layout = new Program();
       
 
       layout.setProgramName(temp.getTempName());
       layout.setDesciption(temp.getTempDesc());
       
       layout.setTemplateId(temp.getTempId());
       
       layout.setH(temp.getHeight());
       layout.setW(temp.getWidth());
       layout.setRectWnds(this.allrects);
       
       String str = "";
       if (list != null) {
         for (Rect re : list) {
           if (re.getRecType() == 1)
           {
             List<TemplateRectImage> rectimages = 
               getTemplateRectImageHandle().readAll(this.tempID, re.getRectId());
             if ((rectimages != null) && (rectimages.size() > 0))
             {
               long fileId = 0L;
               for (int j = 0; j < rectimages.size(); j++)
               {
                 fileId = ((TemplateRectImage)rectimages.get(0)).getFileId();
                 
                 String src = this.servletRequest.getScheme() + "://" + 
                   this.servletRequest.getServerName() + ":" + this.servletRequest.getServerPort() + 
                   "/Pages/looprect!preview?fileId=" + fileId;
                 
                 re.setRectSrc(src);
               }
             }
           }
         }
       }
       xml = LayoutToXml.transform(layout, "utf-8", str);
     }
     catch (Exception e)
     {
       JsonUtils.writeError(this.servletResponse);
     }
     JsonUtils.writeSuccessData(this.servletResponse, xml);
   }
   
   public void GetTodoTemplateLayoutSystem()
   {
     String xml = "";
     try
     {
       List<Rect> list = getTemplateRectHandle().readAllSystem(this.tempID);
       if (list != null)
       {
         this.allrects = new Rect[list.size()];
         list.toArray(this.allrects);
       }
       SystemTemplate temp = getTemplateHandle().readSystem(this.tempID);
       
       Program layout = new Program();
       
 
       layout.setProgramName(temp.getTempName());
       layout.setDesciption(temp.getTempDesc());
       
       layout.setTemplateId(temp.getTempId());
       
       layout.setH(temp.getHeight());
       layout.setW(temp.getWidth());
       layout.setRectWnds(this.allrects);
       
       String src = "";
       if (list != null) {
         for (Rect re : list) {
           if (re.getRecType() == 1)
           {
             List<SystemTemplateRectImage> rectimages = 
               getTemplateRectImageHandle().readAllSystem(this.tempID, re.getRectId());
             if (rectimages != null)
             {
               long fileId = 0L;
               for (int j = 0; j < rectimages.size(); j++)
               {
                 fileId = ((SystemTemplateRectImage)rectimages.get(0)).getFileId();
                 src = "looprect!preview?fileId=" + fileId;
               }
             }
           }
         }
       }
       xml = LayoutToXml.transform(layout, "utf-8", src);
     }
     catch (Exception e)
     {
       JsonUtils.writeError(this.servletResponse);
     }
     JsonUtils.writeSuccessData(this.servletResponse, xml);
   }
   
   public String xmldata = "";
   
   public String getXmldata()
   {
     return this.xmldata;
   }
   
   public void setXmldata(String xmldata)
   {
     this.xmldata = xmldata;
   }
   
   public void SaveTodoTemplateLayout()
   {
     String xml = this.xmldata;
     
     Program p = (Program)XmlToLayout.parseXml(xml, "utf-8");
     
     List<Rect> list = getTemplateRectHandle().readAll(this.tempID);
     if (list != null)
     {
       this.allrects = new Rect[list.size()];
       list.toArray(this.allrects);
     }
     List<Integer> nowlist = new ArrayList();
     if ((p != null) && (p.getRectWnds() != null) && (p.getRectWnds().length > 0)) {
       for (int i = 0; i < p.getRectWnds().length; i++) {
         nowlist.add(Integer.valueOf(p.getRectWnds()[i].getRectId()));
       }
     }
     if (this.allrects != null) {
       for (int j = 0; j < this.allrects.length; j++) {
         if (!nowlist.contains(Integer.valueOf(this.allrects[j].getRectId()))) {
           getTemplateRectHandle().remove(this.tempID, 
             this.allrects[j].getRectId());
         }
       }
     } else {
       for (int k = 0; k < p.getRectWnds().length; k++) {
         try
         {
           p.getRectWnds()[k].setTemplateId(this.tempID);
           
           getTemplateRectHandle().create(p.getRectWnds()[k]);
         }
         catch (Exception e)
         {
           e.printStackTrace();
         }
       }
     }
     for (int n = 0; n < p.getRectWnds().length; n++) {
       if (p.getRectWnds()[n].getRectId() > 0) {
         try
         {
           p.getRectWnds()[n].setTemplateId(this.tempID);
           
           getTemplateRectHandle().modify(p.getRectWnds()[n]);
         }
         catch (Exception e)
         {
           e.printStackTrace();
         }
       } else {
         try
         {
           p.getRectWnds()[n].setTemplateId(this.tempID);
           
           getTemplateRectHandle().create(p.getRectWnds()[n]);
         }
         catch (Exception e)
         {
           e.printStackTrace();
         }
       }
     }
     GetTodoTemplateLayout();
   }
   
   public void changemainrect()
   {
     try
     {
       Template old = getTemplateHandle().read(this.template.getTempId());
       old.setMainRect(this.template.getMainRect());
       if (getTemplateHandle().modify(old)) {
         JsonUtils.writeSuccessData(this.servletResponse, String.valueOf(old.getMainRect()));
       } else {
         throw new Exception("failed");
       }
     }
     catch (Exception e)
     {
       JsonUtils.writeError(this.servletResponse);
       log.error("# modify template main rect failed #", e);
     }
   }
   
   public List<Category> brother = null;
   
   public List<Category> getBrother()
   {
     return this.brother;
   }
   
   public void setBrother(List<Category> brother)
   {
     this.brother = brother;
   }
   
   Category category = null;
   
   public Category getCategory()
   {
     return this.category;
   }
   
   public void setGroup(Category category)
   {
     this.category = category;
   }
   
   public List<Category> categorypath = null;
   
   public List<Category> getCategorypath()
   {
     return this.categorypath;
   }
   
   public void setCategorypath(List<Category> categorypath)
   {
     this.categorypath = categorypath;
   }
   
   public String imagepreview()
   {
     this.category = new Category();
     this.category.setParentId(0);
     if (this.categorypath == null) {
       this.categorypath = new ArrayList();
     } else {
       this.categorypath.clear();
     }
     Category newgroup = this.category;
     
     this.categorypath.add(0, this.category);
     while (newgroup.getParentId() != 0)
     {
       newgroup = getCategoryHandle().read(newgroup.getParentId());
       this.categorypath.add(0, newgroup);
     }
     StringBuffer sql = new StringBuffer("SELECT A.CAT_ID AS CAT_ID, A.NAME AS NAME, A.DESCP AS DESCP, A.PARENT_ID AS PARENT_ID, B.NAME AS PARENT_NAME, A.DEPTH AS DEPTH, A.SUB_NUM AS SUB_NUM,   A.CREATE_TIME AS CREATE_TIME, A.CREATE_USER AS CREATE_USER FROM (SELECT * FROM tb_category WHERE CSTM_ID=:CSTM_ID  AND   PARENT_ID IS NULL  ) AS A LEFT JOIN tb_category AS B ON A.PARENT_ID=B.CAT_ID");
     
 
 
 
 
 
 
 
 
     Map<String, Object> parameters = new HashMap();
     parameters.put("CSTM_ID", Integer.valueOf(getCstmId()));
     
     this.brother = getCategoryHandle().readChildren(sql.toString(), parameters);
     
     return "imagepreviewview";
   }
   
   public String videopreview()
   {
     this.category = new Category();
     this.category.setParentId(0);
     if (this.categorypath == null) {
       this.categorypath = new ArrayList();
     } else {
       this.categorypath.clear();
     }
     Category newgroup = this.category;
     
     this.categorypath.add(0, this.category);
     while (newgroup.getParentId() != 0)
     {
       newgroup = getCategoryHandle().read(newgroup.getParentId());
       this.categorypath.add(0, newgroup);
     }
     StringBuffer sql = new StringBuffer("SELECT A.CAT_ID AS CAT_ID, A.NAME AS NAME, A.DESCP AS DESCP, A.PARENT_ID AS PARENT_ID, B.NAME AS PARENT_NAME, A.DEPTH AS DEPTH, A.SUB_NUM AS SUB_NUM,   A.CREATE_TIME AS CREATE_TIME, A.CREATE_USER AS CREATE_USER FROM (SELECT * FROM tb_category WHERE CSTM_ID=:CSTM_ID  AND   PARENT_ID IS NULL  ) AS A LEFT JOIN tb_category AS B ON A.PARENT_ID=B.CAT_ID");
     
 
 
 
 
 
 
 
 
     Map<String, Object> parameters = new HashMap();
     parameters.put("CSTM_ID", Integer.valueOf(getCstmId()));
     
     this.brother = getCategoryHandle().readChildren(sql.toString(), parameters);
     
     return "videopreviewview";
   }
   
   public int categoryId = 0;
   
   public int getCategoryId()
   {
     return this.categoryId;
   }
   
   public void setCategoryId(int categoryId)
   {
     this.categoryId = categoryId;
   }
   
   public String taskType = "templatetype";
   
   public String getTaskType()
   {
     return this.taskType;
   }
   
   public void setTaskType(String taskType)
   {
     this.taskType = taskType;
   }
   
   public void categorySelector()
   {
     List<Category> categorys = new ArrayList();
     StringBuffer sql = new StringBuffer("SELECT A.CAT_ID AS CAT_ID, A.NAME AS NAME, A.DESCP AS DESCP, A.PARENT_ID AS PARENT_ID, B.NAME AS PARENT_NAME, A.DEPTH AS DEPTH, A.SUB_NUM AS SUB_NUM,  A.CREATE_TIME AS CREATE_TIME, A.CREATE_USER AS CREATE_USER FROM (SELECT * FROM tb_category WHERE CSTM_ID=:CSTM_ID AND  " + (
     
 
 
 
 
 
 
 
 
 
 
 
       this.categoryId == 0 ? "PARENT_ID IS NULL " : 
       "PARENT_ID=:PARENT_ID") + " ) AS A " + 
       "LEFT JOIN tb_category AS B ON A.PARENT_ID=B.CAT_ID");
     
     Map<String, Object> parameters = new HashMap();
     parameters.put("CSTM_ID", Integer.valueOf(getCstmId()));
     if (this.categoryId != 0) {
       parameters.put("PARENT_ID", Integer.valueOf(this.categoryId));
     }
     try
     {
       categorys = getCategoryHandle().readChildren(sql.toString(), 
         parameters);
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
     JsonUtils.writeSuccessData(this.servletResponse, categorys);
   }
   
   public String filelocation = "";
   
   public String getFilelocation()
   {
     return this.filelocation;
   }
   
   public void setFilelocation(String filelocation)
   {
     this.filelocation = filelocation;
   }
   
   public String filesize = "";
   
   public String getFilesize()
   {
     return this.filesize;
   }
   
   public void setFilesize(String filesize)
   {
     this.filesize = filesize;
   }
   
   public String filename = "";
   
   public String getFilename()
   {
     return this.filename;
   }
   
   public void setFilename(String filename)
   {
     this.filename = filename;
   }
   
   public String selectIDs = "";
   
   public String getSelectIDs()
   {
     return this.selectIDs;
   }
   
   public void setSelectIDs(String selectIDs)
   {
     this.selectIDs = selectIDs;
   }
   
   public String view()
   {
     FileHandle filehandle = new FileHandle(this);
     CategoryHandle categoryhandle = new CategoryHandle(this);
     if ((this.selectIDs != null) && (this.selectIDs.length() > 0) && 
       (this.selectIDs.split(",").length == 1))
     {
       com.gnamp.server.model.File f = filehandle.read(Long.parseLong(this.selectIDs.split(",")[0]));
       this.filesize = (String.valueOf(Math.round((float)f.getSize() / 1024.0F * 100.0F) / 100) + " KB");
       this.filename = f.getFileName();
       
       List<Category> capath = categoryhandle.readPath(f.getCategoryId());
       if ((capath != null) && (capath.size() > 0))
       {
         for (int i = 0; i < capath.size(); i++) {
           this.filelocation = (this.filelocation + "->" + ((Category)capath.get(i)).getCategoryName());
         }
         this.filelocation = (getText("root") + this.filelocation);
       }
       else
       {
         this.filelocation = getText("root");
       }
     }
     return "viewfile";
   }
   
   public String imageSelect()
   {
     this.category = new Category();
     this.category.setParentId(0);
     if (this.categorypath == null) {
       this.categorypath = new ArrayList();
     } else {
       this.categorypath.clear();
     }
     Category newgroup = this.category;
     
     this.categorypath.add(0, this.category);
     while (newgroup.getParentId() != 0)
     {
       newgroup = getCategoryHandle().read(newgroup.getParentId());
       this.categorypath.add(0, newgroup);
     }
     StringBuffer sql = new StringBuffer("SELECT A.CAT_ID AS CAT_ID, A.NAME AS NAME, A.DESCP AS DESCP, A.PARENT_ID AS PARENT_ID, B.NAME AS PARENT_NAME, A.DEPTH AS DEPTH, A.SUB_NUM AS SUB_NUM,   A.CREATE_TIME AS CREATE_TIME, A.CREATE_USER AS CREATE_USER FROM (SELECT * FROM tb_category WHERE CSTM_ID=:CSTM_ID  AND   PARENT_ID IS NULL  ) AS A LEFT JOIN tb_category AS B ON A.PARENT_ID=B.CAT_ID");
     
 
 
 
 
 
 
 
 
     Map<String, Object> parameters = new HashMap();
     parameters.put("CSTM_ID", Integer.valueOf(getCstmId()));
     
     this.brother = getCategoryHandle().readChildren(sql.toString(), parameters);
     
     return "imageselect";
   }
   
   public int rectID = 0;
   
   public int getRectID()
   {
     return this.rectID;
   }
   
   public void setRectID(int rectID)
   {
     this.rectID = rectID;
   }
   
   List<TemplateRectImage> imagefilelist = null;
   
   public List<TemplateRectImage> getImagefilelist()
   {
     return this.imagefilelist;
   }
   
   public void setImagefilelist(List<TemplateRectImage> imagefilelist)
   {
     this.imagefilelist = imagefilelist;
   }
   
   public void queryimagefiles()
   {
     this.imagefilelist = new ArrayList();
     
 
     String query = "SELECT * FROM (SELECT TMPLT_ID, RECT_ID, IMAGE_ID, SEQ, t.FILE_ID AS FILE_ID, f.NAME AS FILE_NAME, PLAY_TIME, SWAP_STYLE, SWAP_TIME, SCALE_STYLE FROM tb_template_rect_image AS t, tb_file AS f WHERE t.CSTM_ID=:CSTM_ID AND TMPLT_ID=:TMPLT_ID AND RECT_ID=:RECT_ID AND f.CSTM_ID=:CSTM_ID AND t.FILE_ID > 0x7FFFFFFF AND f.FILE_ID=t.FILE_ID  UNION SELECT TMPLT_ID, RECT_ID, IMAGE_ID, SEQ, t.FILE_ID AS FILE_ID, f.NAME AS FILE_NAME, PLAY_TIME, SWAP_STYLE, SWAP_TIME, SCALE_STYLE FROM tb_template_rect_image AS t, tb_system_file AS f WHERE t.CSTM_ID=:CSTM_ID AND TMPLT_ID=:TMPLT_ID AND RECT_ID=:RECT_ID AND t.FILE_ID < 0x7FFFFFFF AND f.FILE_ID=t.FILE_ID  ) AS tb ORDER BY SEQ ASC " + (
     
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
       this.page.getPageSize() != -1 ? "LIMIT " + (this.page.getCurrentPage() - 1) * this.page.getPageSize() + "," + this.page.getPageSize() : "");
     try
     {
       int total = getTemplateRectImageHandle().readAll(this.tempID, this.rectID).size();
       
       this.imagefilelist = getTemplateRectImageHandle().readAll(query, getParameters());
       
       this.page.setTotalRows(total);
       response(JSONSuccessString(JSONArrayToString(this.imagefilelist), new MapTool().putObject("page", this.page)));
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
   
   private MapTool<String, Object> getParameters()
   {
     MapTool<String, Object> parameters = new MapTool();
     parameters.put("CSTM_ID", Integer.valueOf(this.cstmId));
     parameters.put("TMPLT_ID", Integer.valueOf(this.tempID));
     parameters.put("RECT_ID", Integer.valueOf(this.rectID));
     
     return parameters;
   }
   
   public void addimagefile()
   {
     if ((this.selectIDs != null) && (this.selectIDs.length() > 0)) {
       for (int i = 0; i < this.selectIDs.split(",").length; i++) {
         try
         {
           TemplateRectImage rectimage = new TemplateRectImage();
           com.gnamp.server.model.File file = new com.gnamp.server.model.File();
           file = getFileHandle().read(
             Long.parseLong(this.selectIDs.split(",")[i]));
           if (file != null)
           {
             rectimage.setFileName(file.getFileName());
             rectimage.setFileId(file.getFileId());
           }
           rectimage.setPlayTime(5);
           rectimage.setRectId(this.rectID);
           rectimage.setScaleStyle(3);
           rectimage.setSwapStyle(0);
           rectimage.setSeq(2147483647);
           rectimage.setTempId(this.tempID);
           rectimage.setSwapTime(2);
           
           getTemplateRectImageHandle().create(rectimage);
         }
         catch (Exception e)
         {
           e.printStackTrace();
         }
       }
     }
     JsonUtils.writeSuccess(this.servletResponse);
   }
   
   public void deleteimagefile()
   {
     if ((this.selectIDs != null) && (this.selectIDs.length() > 0)) {
       for (int i = 0; i < this.selectIDs.split(",").length; i++) {
         try
         {
           getTemplateRectImageHandle().remove(this.tempID, 
             this.rectID, Integer.parseInt(this.selectIDs.split(",")[i]));
         }
         catch (Exception e)
         {
           e.printStackTrace();
         }
       }
     }
     JsonUtils.writeSuccess(this.servletResponse);
   }
   
   TemplateRectImage rectImage = new TemplateRectImage();
   
   public TemplateRectImage getRectImage()
   {
     return this.rectImage;
   }
   
   public void setRectImage(TemplateRectImage rectImage)
   {
     this.rectImage = rectImage;
   }
   
   public void upimage()
   {
     TemplateRectImage old = getTemplateRectImageHandle().read(this.rectImage.getTempId(), 
       this.rectImage.getRectId(), 
       this.rectImage.getImageId());
     int seq = old.getSeq() - 1;
     old.setSeq(seq);
     if (getTemplateRectImageHandle().modify(old)) {
       JsonUtils.writeSuccess(this.servletResponse);
     } else {
       JsonUtils.writeError(this.servletResponse);
     }
   }
   
   public void downimage()
   {
     TemplateRectImage old = getTemplateRectImageHandle().read(this.rectImage.getTempId(), 
       this.rectImage.getRectId(), 
       this.rectImage.getImageId());
     int seq = old.getSeq() + 1;
     old.setSeq(seq);
     if (getTemplateRectImageHandle().modify(old)) {
       JsonUtils.writeSuccess(this.servletResponse);
     } else {
       JsonUtils.writeError(this.servletResponse);
     }
   }
   
   public String ToModifyImage()
   {
     FileHandle filehandle = new FileHandle(this);
     CategoryHandle categoryhandle = new CategoryHandle(this);
     if ((this.selectIDs != null) && (this.selectIDs.length() > 0)) {
       if (this.selectIDs.split(",").length == 1)
       {
         this.rectImage = getTemplateRectImageHandle().read(this.tempID, this.rectID, 
           Integer.parseInt(this.selectIDs.split(",")[0]));
         
         com.gnamp.server.model.File f = filehandle.read(this.rectImage.getFileId());
         this.filesize = (String.valueOf(Math.round((float)f.getSize() / 1024.0F * 100.0F) / 100) + " KB");
         
         List<Category> capath = categoryhandle.readPath(f.getCategoryId());
         if ((capath != null) && (capath.size() > 0))
         {
           for (int i = 0; i < capath.size(); i++) {
             this.filelocation = (this.filelocation + "->" + ((Category)capath.get(i)).getCategoryName());
           }
           this.filelocation = (getText("root") + this.filelocation);
         }
         else
         {
           this.filelocation = getText("root");
         }
       }
       else
       {
         this.rectImage = getTemplateRectImageHandle().read(this.tempID, this.rectID, 
           Integer.parseInt(this.selectIDs.split(",")[0]));
       }
     }
     return "editimage";
   }
   
   public void ModifyImage()
   {
     if ((this.selectIDs != null) && (this.selectIDs.length() > 0)) {
       if (this.selectIDs.split(",").length == 1)
       {
         TemplateRectImage old = getTemplateRectImageHandle().read(this.rectImage.getTempId(), 
           this.rectImage.getRectId(), 
           this.rectImage.getImageId());
         
         old.setPlayTime(this.rectImage.getPlayTime());
         old.setScaleStyle(this.rectImage.getScaleStyle());
         old.setSwapStyle(this.rectImage.getSwapStyle());
         old.setSwapTime(this.rectImage.getSwapTime());
         if (getTemplateRectImageHandle().modify(old)) {
           JsonUtils.writeSuccess(this.servletResponse);
         } else {
           JsonUtils.writeError(this.servletResponse);
         }
       }
       else
       {
         for (int i = 0; i < this.selectIDs.split(",").length; i++) {
           try
           {
             TemplateRectImage old = getTemplateRectImageHandle().read(this.rectImage.getTempId(), 
               this.rectImage.getRectId(), 
               Integer.parseInt(this.selectIDs.split(",")[i]));
             
             old.setPlayTime(this.rectImage.getPlayTime());
             old.setScaleStyle(this.rectImage.getScaleStyle());
             old.setSwapStyle(this.rectImage.getSwapStyle());
             old.setSwapTime(this.rectImage.getSwapTime());
             if (!getTemplateRectImageHandle().modify(old)) {
               JsonUtils.writeError(this.servletResponse);
             }
           }
           catch (Exception e)
           {
             JsonUtils.writeError(this.servletResponse);
             e.printStackTrace();
           }
         }
         JsonUtils.writeSuccess(this.servletResponse);
       }
     }
   }
   
   public void QueryTemplateByCategory()
   {
     List<Template> template = getTemplateHandle().readAll(this.leftstr);
     String returnStr = "";
     if ((template == null) || (template.size() <= 0)) {
       returnStr = 
         "[{\"tempId\":0,\"tempName\":\"" + getText("select") + "\",\"category\":\"" + getText("select") + "\"}]";
     } else {
       returnStr = JSONArrayToString(template);
     }
     response(JSONSuccessString(returnStr));
   }
   
   public void QuerySystemTemplateByCategory()
   {
     List<SystemTemplate> template = getTemplateHandle().readAllSystem(this.leftstr);
     String returnStr = "";
     if ((template == null) || (template.size() <= 0)) {
       returnStr = 
         "[{\"tempId\":0,\"tempName\":\"" + getText("select") + "\",\"category\":\"" + getText("select") + "\"}]";
     } else {
       returnStr = JSONArrayToString(template);
     }
     response(JSONSuccessString(returnStr));
   }
   
   class CategorysVo
     implements ITree<String>
   {
     private int id;
     private String name;
     
     CategorysVo(int id, String name)
     {
       this.id = id;
       this.name = name;
     }
     
     public int getId()
     {
       return this.id;
     }
     
     public String getName()
     {
       return this.name;
     }
     
     public boolean getIsParent()
     {
       return false;
     }
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.TemplateAction
 * JD-Core Version:    0.7.0.1
 */