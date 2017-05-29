 package com.gnamp.struts.admin.action;
 
 import com.gnamp.server.handle.AppHandle;
 import com.gnamp.server.handle.PackageFile;
 import java.io.File;
 
 public class FlexAppUploadAction
   extends AbstractFlexUploadAction
 {
   protected boolean dispatcher(String[] fileName, File[] files)
     throws Exception
   {
     AppHandle appHandle = new AppHandle(this);
     int error = 0;
     for (int index = 0; index < files.length; index++) {
       if (!appHandle.exists(PackageFile.getVersion(files[index].getAbsolutePath()))) {
         appHandle.upload(files[index].getAbsolutePath(), 
           PackageFile.getVersion(files[index].getAbsolutePath()), fileName[index]);
       } else {
         error++;
       }
     }
     if (error > 0) {
       throw new Exception(getText("uploadfail"));
     }
     return true;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.admin.action.FlexAppUploadAction
 * JD-Core Version:    0.7.0.1
 */