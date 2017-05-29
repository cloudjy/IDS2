 package com.gnamp.struts.vo;
 
 import com.gnamp.struts.action.UploadFile;
 
 public class UploadModel
 {
   String[] fileNames;
   byte[][] fileContents;
   
   public UploadModel(String fileName, byte[] content)
   {
     this.fileNames = new String[] { fileName };
     this.fileContents = new byte[][] { content };
   }
   
   public UploadModel(UploadFile... files)
   {
     this.fileNames = new String[files.length];
     this.fileContents = new byte[files.length][];
     for (int i = 0; i < files.length; i++)
     {
       this.fileContents[i] = files[i].getContent();
       this.fileNames[i] = files[i].getFileName();
     }
   }
   
   public UploadModel(String[] fileNames, byte[][] content)
   {
     this.fileContents = content;
     this.fileNames = fileNames;
   }
   
   public String[] getFileNames()
   {
     return this.fileNames;
   }
   
   public void setFileNames(String[] fileNames)
   {
     this.fileNames = fileNames;
   }
   
   public byte[][] getFileContents()
   {
     return this.fileContents;
   }
   
   public void setFileContents(byte[][] fileContents)
   {
     this.fileContents = fileContents;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.vo.UploadModel
 * JD-Core Version:    0.7.0.1
 */