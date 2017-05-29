 package com.gnamp.struts.vo;
 
 import com.gnamp.server.handle.UserHandle;
 import com.gnamp.server.model.Role;
 import com.gnamp.server.model.User;
 import java.util.List;
 
 public class UserListVo
 {
   private int id;
   private String roleNames;
   private String userName;
   
   public int getId()
   {
     return this.id;
   }
   
   public void setId(int id)
   {
     this.id = id;
   }
   
   public String getRoleNames()
   {
     return this.roleNames;
   }
   
   public void setRoleNames(String roleNames)
   {
     this.roleNames = roleNames;
   }
   
   public String getUserName()
   {
     return this.userName;
   }
   
   public void setUserName(String userName)
   {
     this.userName = userName;
   }
   
   public UserListVo convert(User user, UserHandle h)
   {
     setId(user.getUserId());
     setUserName(user.getUserName());
     List<Role> rs = h.readUserRoles(user.getUserId());
     if ((rs != null) && (rs.size() > 0)) {
       setRoleNames(getRoleNames(h.readUserRoles(user.getUserId())));
     }
     return this;
   }
   
   String getRoleNames(List<Role> roles)
   {
     StringBuffer buffer = new StringBuffer();
     for (Role r : roles) {
       buffer.append(r.getRoleName()).append(",");
     }
     if (buffer.length() > 0) {
       buffer.deleteCharAt(buffer.length() - 1);
     }
     return buffer.toString();
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.vo.UserListVo
 * JD-Core Version:    0.7.0.1
 */