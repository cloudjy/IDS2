 package com.gnamp.struts.action;
 
 import com.gnamp.server.handle.FileTagHandle;
 import com.gnamp.server.handle.TagHandleFactory;
 import com.gnamp.server.model.Source;
 import java.io.File;
 
 public class FlexFileTagUploadAction
   extends AbstractFlexUploadAction
 {
   private Source source;
   
   public Source getSource()
   {
     return this.source;
   }
   
   public void setSource(Source source)
   {
     this.source = source;
   }
   
   protected boolean dispatcher(String[] fileName, File[] files)
     throws Exception
   {
     for (int index = 0; index < files.length; index++) {
       ((FileTagHandle)TagHandleFactory.getSourceTagHandle(this, getSource().getType())).add(getSource(), 
         files[index], 
         fileName[index]);
     }
     return true;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.FlexFileTagUploadAction
 * JD-Core Version:    0.7.0.1
 */