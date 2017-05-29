 package com.gnamp.struts.action;
 
 import java.io.Serializable;
 
 public class UploadFile
   implements Serializable
 {
   private String fileName;
   private byte[] content;
   
   public UploadFile() {}
   
   public UploadFile(String fileName, byte[] content)
   {
     this.fileName = fileName;
     this.content = content;
   }
   
   public String getFileName()
   {
     return this.fileName;
   }
   
   public void setFileName(String fileName)
   {
     this.fileName = fileName;
   }
   
   public byte[] getContent()
   {
     return this.content;
   }
   
   public void setContent(byte[] content)
   {
     this.content = content;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.UploadFile
 * JD-Core Version:    0.7.0.1
 */