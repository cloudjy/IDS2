 package com.gnamp.struts.vo;
 
 public class TreeToTerminalParameter
 {
   int groupId;
   String searchKey;
   boolean includeChildren = true;
   
   public int getGroupId()
   {
     return this.groupId;
   }
   
   public void setGroupId(int groupId)
   {
     this.groupId = groupId;
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
 * Qualified Name:     com.gnamp.struts.vo.TreeToTerminalParameter
 * JD-Core Version:    0.7.0.1
 */