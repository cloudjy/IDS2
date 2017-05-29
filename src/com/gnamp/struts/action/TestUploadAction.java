 package com.gnamp.struts.action;
 
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileNotFoundException;
 import java.io.InputStream;
 import java.util.Enumeration;
 import javax.servlet.ServletOutputStream;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.log4j.Logger;
 import org.apache.struts2.ServletActionContext;
 import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
 
 public class TestUploadAction
   extends JSONAction
 {
   private static final long serialVersionUID = 4323187846737468501L;
   private static final Logger log = Logger.getLogger(TestUploadAction.class);
   
   public void flexUpLoadFile()
   {
     MultiPartRequestWrapper multiWrapper = 
       (MultiPartRequestWrapper)ServletActionContext.getRequest();
     Enumeration<?> fileParameterNames = multiWrapper.getFileParameterNames();
     while ((fileParameterNames != null) && (fileParameterNames.hasMoreElements()))
     {
       String inputName = (String)fileParameterNames.nextElement();
       
 
       String[] contentType = multiWrapper.getContentTypes(inputName);
       if (isNonEmpty(contentType))
       {
         String[] fileName = multiWrapper.getFileNames(inputName);
         if (isNonEmpty(fileName))
         {
           File[] files = multiWrapper.getFiles(inputName);
           if (files != null)
           {
             for (int index = 0; index < files.length; index++)
             {
               String uploadPath = "D:\\upload\\" + fileName[index];
               File dstFile = new File(uploadPath);
               if (dstFile.exists()) {
                 dstFile.delete();
               }
               copy(files[index], dstFile);
             }
             try
             {
               ServletActionContext.getResponse().setContentType("application/x-json");
               ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
               ServletActionContext.getResponse().getOutputStream().print("{\"success\":true}");
             }
             catch (Exception e)
             {
               e.printStackTrace();
             }
           }
         }
       }
     }
   }
   
   private String filename = "picture.jpg";
   
   public String getFilename()
   {
     return this.filename;
   }
   
   public void setFilename(String filename)
   {
     this.filename = filename;
   }
   
   public String getFile()
     throws FileNotFoundException
   {
     return "file";
   }
   
   public InputStream getDownloadFile()
     throws FileNotFoundException
   {
     return new FileInputStream("d:/xx.jpg");
   }
   
   private int BUFFER_SIZE = 1024;
   
   private void copy(File src, File dst)
   {
     src.renameTo(dst);
     src.delete();
   }
   
   private static boolean isNonEmpty(Object[] objArray)
   {
     boolean result = false;
     for (int index = 0; (index < objArray.length) && (!result); index++) {
       if (objArray[index] != null)
       {
         result = true;
         break;
       }
     }
     return result;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.TestUploadAction
 * JD-Core Version:    0.7.0.1
 */