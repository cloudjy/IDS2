 package com.gnamp.struts.filter;
 
 public class LoginUser
 {
   private String username;
   private String password;
   private boolean admin;
   private String cstm;
   
   public String getUsername()
   {
     return this.username;
   }
   
   public void setUsername(String username)
   {
     this.username = username;
   }
   
   public String getPassword()
   {
     return this.password;
   }
   
   public void setPassword(String password)
   {
     this.password = password;
   }
   
   public boolean isAdmin()
   {
     return this.admin;
   }
   
   public void setAdmin(boolean admin)
   {
     this.admin = admin;
   }
   
   public String getCstm()
   {
     return this.cstm;
   }
   
   public void setCstm(String cstm)
   {
     this.cstm = cstm;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.filter.LoginUser
 * JD-Core Version:    0.7.0.1
 */