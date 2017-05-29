 package com.gnamp.struts.vo;
 
 import com.gnamp.server.model.Role;
 
 public class RoleVo
 {
   private int id;
   private String roleName;
   private String description;
   
   public int getId()
   {
     return this.id;
   }
   
   public void setId(int id)
   {
     this.id = id;
   }
   
   public String getRoleName()
   {
     return this.roleName;
   }
   
   public void setRoleName(String roleName)
   {
     this.roleName = roleName;
   }
   
   public String getDescription()
   {
     return this.description;
   }
   
   public void setDescription(String description)
   {
     this.description = description;
   }
   
   public RoleVo convert(Role role)
   {
     setRoleName(role.getRoleName());
     setDescription(role.getDescription());
     setId(role.getRoleId());
     return this;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.vo.RoleVo
 * JD-Core Version:    0.7.0.1
 */