 package com.gnamp.struts.vo;
 
 import java.util.ArrayList;
 import java.util.List;
 
 public class ConfigVo
   implements Cloneable
 {
   List<GroupVo> groups = new ArrayList();
   
   public List<GroupVo> getGroups()
   {
     return this.groups;
   }
   
   public void setGroups(List<GroupVo> groups)
   {
     this.groups = groups;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.vo.ConfigVo
 * JD-Core Version:    0.7.0.1
 */