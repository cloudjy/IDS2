 package com.gnamp.terminal.config;
 
 public final class PPPOEConfig
   extends NetworkConfig
 {
   public String mUserName = "";
   public String mPassword = "";
   
   public String getUserName()
   {
     return this.mUserName;
   }
   
   public void setUserName(String name)
   {
     this.mUserName = (name == null ? "" : name.trim());
   }
   
   public String getPassword()
   {
     return this.mPassword;
   }
   
   public void setPassword(String password)
   {
     this.mPassword = (password == null ? "" : password);
   }
   
   public String toString()
   {
     return "[pppoe] name=" + this.mUserName + ", password=" + this.mPassword;
   }
   
   public boolean equals(Object obj)
   {
     if (this == obj) {
       return true;
     }
     if ((obj == null) || (!(obj instanceof PPPOEConfig))) {
       return false;
     }
     PPPOEConfig o = (PPPOEConfig)obj;
     if (!(this.mUserName == null ? "" : this.mUserName.trim()).equals(o.mUserName == null ? "" : o.mUserName.trim())) {
       return false;
     }
     if (!(this.mPassword == null ? "" : this.mPassword.trim()).equals(o.mPassword == null ? "" : o.mPassword.trim())) {
       return false;
     }
     return true;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.terminal.config.PPPOEConfig
 * JD-Core Version:    0.7.0.1
 */