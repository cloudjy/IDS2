 package com.gnamp.struts.admin.action;
 
 import com.gnamp.struts.utils.GnampXML;
 import java.io.File;
 import java.io.IOException;
 import java.io.PrintWriter;
 import java.util.Locale;
 import java.util.Map;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.log4j.Logger;
 
 public class ConfigTemplateUploadAction
   extends JSONAction
 {
   private static final Logger log = Logger.getLogger(ConfigTemplateUploadAction.class);
   private String title;
   private File upload;
   private String uploadFileName;
   private String uploadContentType;
   
   private String descFileName()
   {
     if ((this.sessionMap.get("WW_TRANS_I18N_LOCALE") != null) && ("zh_CN".equals(this.sessionMap.get("WW_TRANS_I18N_LOCALE").toString()))) {
       return GnampXML.getTemplatePath(Locale.CHINESE);
     }
     return GnampXML.getTemplatePath(Locale.ENGLISH);
   }
   
   public String getTitle()
   {
     return this.title;
   }
   
   public void setTitle(String title)
   {
     this.title = title;
   }
   
   public File getUpload()
   {
     return this.upload;
   }
   
   public void setUpload(File upload)
   {
     this.upload = upload;
   }
   
   public String getUploadFileName()
   {
     return this.uploadFileName;
   }
   
   public void setUploadFileName(String uploadFileName)
   {
     this.uploadFileName = uploadFileName;
   }
   
   public String getUploadContentType()
   {
     return this.uploadContentType;
   }
   
   public void setUploadContentType(String uploadContentType)
   {
     this.uploadContentType = uploadContentType;
   }
   
   public void upload()
   {
     try
     {
       if (getUpload() == null) {
         throw new Exception(getText("nocontent"));
       }
       File descFile = new File(descFileName());
       if (descFile.exists()) {
         descFile.delete();
       }
       if (getUpload().renameTo(descFile)) {
         getUpload().delete();
       }
       this.response.getWriter().write(JSONSuccessString());
     }
     catch (Exception e)
     {
       try
       {
         this.response.getWriter().write(JSONErrorString());
       }
       catch (IOException e1)
       {
         log.error(e1.getMessage(), e1);
       }
       log.error(e.getMessage(), e);
     }
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.admin.action.ConfigTemplateUploadAction
 * JD-Core Version:    0.7.0.1
 */