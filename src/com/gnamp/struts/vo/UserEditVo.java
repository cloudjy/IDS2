 package com.gnamp.struts.vo;
 
 import com.gnamp.server.model.Role;
 
 public class UserEditVo
 {
   private int id;
   private boolean role;
   private String roleName;
   private String description;
   
   public boolean isRole()
   {
     return this.role;
   }
   
   public void setRole(boolean role)
   {
     this.role = role;
   }
   
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
   
   public UserEditVo convert(Role role, boolean hasRole)
   {
     setId(role.getRoleId());
     setRoleName(role.getRoleName());
     setDescription(role.getDescription());
     setRole(hasRole);
     return this;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.vo.UserEditVo
 * JD-Core Version:    0.7.0.1
 */