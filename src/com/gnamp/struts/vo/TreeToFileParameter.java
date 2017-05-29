 package com.gnamp.struts.vo;
 
 public class TreeToFileParameter
 {
   int categoryId;
   int fileType;
   int checkState;
   String fileVendor;
   String searchKey;
   boolean includeChildren = true;
   
   public int getCategoryId()
   {
     return this.categoryId;
   }
   
   public void setCategoryId(int categoryId)
   {
     this.categoryId = categoryId;
   }
   
   public int getFileType()
   {
     return this.fileType;
   }
   
   public void setFileType(int fileType)
   {
     this.fileType = fileType;
   }
   
   public int getCheckState()
   {
     return this.checkState;
   }
   
   public void setCheckState(int checkState)
   {
     this.checkState = checkState;
   }
   
   public String getFileVendor()
   {
     return this.fileVendor;
   }
   
   public void setFileVendor(String fileVendor)
   {
     this.fileVendor = fileVendor;
   }
   
   public String getSearchKey()
   {
     return this.searchKey;
   }
   
   public void setSearchKey(String searchKey)
   {
     this.searchKey = searchKey;
   }
   
   public boolean isIncludeChildren()
   {
     return this.includeChildren;
   }
   
   public void setIncludeChildren(boolean includeChildren)
   {
     this.includeChildren = includeChildren;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.vo.TreeToFileParameter
 * JD-Core Version:    0.7.0.1
 */