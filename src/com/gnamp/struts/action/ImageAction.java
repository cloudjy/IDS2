 package com.gnamp.struts.action;
 
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import javax.servlet.ServletOutputStream;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.log4j.Logger;
 
 public class ImageAction
   extends JSONAction
 {
   private static Logger log = Logger.getLogger(ImageAction.class);
   String filePath;
   
   public String getFilePath()
   {
     return this.filePath;
   }
   
   public void setFilePath(String filePath)
   {
     this.filePath = filePath;
   }
   
   public void showfile()
   {
     ServletOutputStream sos = null;
     InputStream input = null;
     try
     {
       sos = this.servletResponse.getOutputStream();
       this.servletResponse.setContentType("image/jpeg");
       
       File download_file = new File(getFilePath());
       input = new FileInputStream(download_file);
       
       byte[] bytes = new byte[1024];
       int len = -1;
       while ((len = input.read(bytes)) != -1) {
         sos.write(bytes, 0, len);
       }
     }
     catch (IOException e)
     {
       log.info(e.getMessage());
       try
       {
         if (sos != null) {
           sos.close();
         }
       }
       catch (IOException e2)
       {
         log.info(e2.getMessage());
       }
       try
       {
         if (input != null) {
           input.close();
         }
       }
       catch (IOException e1)
       {
         log.info(e1.getMessage());
       }
     }
     finally
     {
       try
       {
         if (sos != null) {
           sos.close();
         }
       }
       catch (IOException e)
       {
         log.info(e.getMessage());
       }
       try
       {
         if (input != null) {
           input.close();
         }
       }
       catch (IOException e)
       {
         log.info(e.getMessage());
       }
     }
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.ImageAction
 * JD-Core Version:    0.7.0.1
 */