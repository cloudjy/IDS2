 package com.gnamp.struts.admin.action;
 
 import com.gnamp.server.handle.FileHandle;
 import com.gnamp.server.model.DustbinFile;
 import com.gnamp.struts.utils.JsonUtils;
 import com.gnamp.struts.utils.MapTool;
 import com.gnamp.struts.vo.PageBean;
 import java.util.HashSet;
 import java.util.List;
 import java.util.Map;
 import java.util.Set;
 import java.util.TreeSet;
 import org.apache.log4j.Logger;
 
 public class DustbinAction
   extends JSONAction
 {
   private static final Logger log = Logger.getLogger(DustbinAction.class);
   DustbinFile file;
   List<DustbinFile> dusts;
   
   public DustbinFile getFile()
   {
     return this.file;
   }
   
   public void setFile(DustbinFile file)
   {
     this.file = file;
   }
   
   PageBean page = new PageBean();
   
   public PageBean getPage()
   {
     return this.page;
   }
   
   public void setPage(PageBean page)
   {
     this.page = page;
   }
   
   public void remove()
   {
     Set<String> errors = new TreeSet();
     try
     {
       for (DustbinFile f : this.dusts) {
         try
         {
           FileHandle.removeDustbin(f.getCstmId(), f.getFileId());
         }
         catch (Exception e)
         {
           errors.add(f.getCstmName() + "域" + f.getFileName() + " 删除失败");
         }
       }
       if (errors.size() == 0) {
         JsonUtils.writeSuccess(this.response);
       } else {
         JsonUtils.writeErrorData(this.response, errors);
       }
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorData(this.response, errors);
       log.error(e.getMessage());
     }
   }
   
   public List<DustbinFile> getDusts()
   {
     return this.dusts;
   }
   
   public void setDusts(List<DustbinFile> dusts)
   {
     this.dusts = dusts;
   }
   
   private String getSql(DustbinFile _f, boolean isPage)
   {
     String query = 
       "SELECT f.CSTM_ID AS CSTM_ID, m.NAME AS CSTM_NAME, FILE_ID, f.NAME AS NAME, f.DESCP AS DESCP, f.CAT_ID AS CAT_ID, c.NAME AS CAT_NAME, TYPE, SIZE, SUM, f.SEGMENT_NUM AS SEGMENT_NUM, f.FLAG AS FLAG, UPLOAD_TIME, UPLOAD_USER, CHECK_TIME, CHECK_USER, VENDER, USE_COUNT FROM (SELECT * FROM tb_file_dustbin ) as f LEFT JOIN tb_category as c ON f.CSTM_ID=c.CSTM_ID AND f.CAT_ID=c.CAT_ID LEFT JOIN tb_customer as m ON f.CSTM_ID=m.CSTM_ID " + (
       
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
       _f == null ? "" : new StringBuilder("WHERE ")
       .append(_f.getFileName() != null ? " f.NAME like :NAME " : "").append(_f.getType() == -1 ? "" : " and TYPE=:TYPE ").toString()) + (
       
       this.page.getPageSize() != -1 ? " LIMIT " + (this.page.getCurrentPage() - 1) * this.page.getPageSize() + "," + this.page.getPageSize() : !isPage ? "" : "");
     return query;
   }
   
   private MapTool<String, Object> getParameters(DustbinFile _f)
   {
     return _f == null ? null : new MapTool().putObject("TYPE", Integer.valueOf(_f.getType())).putObject("NAME", "%" + _f.getFileName() + "%");
   }
   
   public void list()
   {
     try
     {
       List<DustbinFile> list = FileHandle.readDustbinAll(getSql(this.file, true), getParameters(this.file));
       this.page.setTotalRows(FileHandle.readDustbinAll(getSql(this.file, false), getParameters(this.file)).size());
       if (this.page.getPageSize() == -1) {
         this.page.setPageSize(this.page.getTotalRows());
       }
       response(JSONSuccessString(JSONArrayToString(list), new Map[] { new MapTool().putObject("page", this.page) }));
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorMessage(this.response, e.getMessage());
       log.error(e.getMessage());
     }
   }
   
   public void reduction()
   {
     Set<String> errors = new HashSet();
     try
     {
       for (DustbinFile dust : this.dusts) {
         try
         {
           if (!FileHandle.dustbinRestore(dust.getCstmId(), dust.getFileId())) {
             throw new Exception(dust.getCstmId() + "域 [" + dust.getFileName() + "]文件还原失败 ");
           }
         }
         catch (Exception e)
         {
           errors.add(e.getMessage());
         }
       }
       if (errors.size() == 0) {
         JsonUtils.writeSuccess(this.response);
       } else {
         throw new Exception();
       }
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorData(this.response, errors);
       log.error(e.getMessage() + errors);
     }
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.admin.action.DustbinAction
 * JD-Core Version:    0.7.0.1
 */