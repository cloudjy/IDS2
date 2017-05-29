 package com.gnamp.struts.action;
 
 import com.gnamp.struts.filter.Context;
 import java.io.File;
 import java.util.Enumeration;
 import org.apache.log4j.Logger;
 import org.apache.struts2.ServletActionContext;
 import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
 
 public abstract class AbstractFlexUploadAction
   extends JSONAction
 {
   public void upload()
   {
     try
     {
       MultiPartRequestWrapper multiWrapper = 
         (MultiPartRequestWrapper)ServletActionContext.getRequest();
       Enumeration<?> fileParameterNames = multiWrapper.getFileParameterNames();
       do
       {
         String inputName = (String)fileParameterNames.nextElement();
         
         String[] contentType = multiWrapper.getContentTypes(inputName);
         if (isNonEmpty(contentType))
         {
           String[] fileName = multiWrapper.getFileNames(inputName);
           if (isNonEmpty(fileName))
           {
             File[] files = multiWrapper.getFiles(inputName);
             if (files != null) {
               if (dispatcher(fileName, files)) {
                 response(JSONSuccessString());
               } else {
                 response(JSONErrorString());
               }
             }
           }
         }
         if (fileParameterNames == null) {
           break;
         }
       } while (fileParameterNames.hasMoreElements());
     }
     catch (Exception e)
     {
       response(JSONErrorString(getText("uploadfail")));
       Logger log = Logger.getLogger(getClass());
       log.error(e.getMessage(), e);
     }
   }
   
   protected abstract boolean dispatcher(String[] paramArrayOfString, File[] paramArrayOfFile)
     throws Exception;
   
   protected String getRootDirectory()
   {
     return Context.getWebRootPath();
   }
   
   protected String getTempDirectory()
   {
     return 
     
 
       getRootDirectory() + File.separatorChar + "temp" + File.separatorChar + getCstmId() + File.separatorChar;
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
 * Qualified Name:     com.gnamp.struts.action.AbstractFlexUploadAction
 * JD-Core Version:    0.7.0.1
 */