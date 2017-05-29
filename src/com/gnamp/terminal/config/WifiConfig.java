 package com.gnamp.terminal.config;
 
 public final class WifiConfig
   extends IpAddressConfig
 {
   public String mSSID = "";
   public String mPassword = "";
   public String mAuthen = "";
   public String mEncrypt = "";
   
   public String getSsid()
   {
     return this.mSSID;
   }
   
   public void setSsid(String ssid)
   {
     this.mSSID = (ssid == null ? "" : ssid.trim());
   }
   
   public String getPassword()
   {
     return this.mPassword;
   }
   
   public void setPassword(String password)
   {
     this.mPassword = (password == null ? "" : password);
   }
   
   public String getAuthen()
   {
     return this.mAuthen;
   }
   
   public void setAuthen(String authen)
   {
     this.mAuthen = (authen == null ? "" : authen.trim());
   }
   
   public String getEncrypt()
   {
     return this.mEncrypt;
   }
   
   public void setEncrypt(String encrypt)
   {
     this.mEncrypt = (encrypt == null ? "" : encrypt.trim());
   }
   
   public String toString()
   {
     return 
     
 
       "[wifi] ssid=" + this.mSSID + ", password=" + this.mPassword + ", authen=" + this.mAuthen + ", encrypt=" + this.mEncrypt + " | " + super.toString();
   }
   
   public boolean equals(Object obj)
   {
     if (this == obj) {
       return true;
     }
     if ((obj == null) || (!(obj instanceof WifiConfig))) {
       return false;
     }
     WifiConfig o = (WifiConfig)obj;
     if (!(this.mSSID == null ? "" : this.mSSID.trim()).equals(o.mSSID == null ? "" : o.mSSID.trim())) {
       return false;
     }
     if (!(this.mPassword == null ? "" : this.mPassword.trim()).equals(o.mPassword == null ? "" : o.mPassword.trim())) {
       return false;
     }
     if (!(this.mAuthen == null ? "" : this.mAuthen.trim()).equals(o.mAuthen == null ? "" : o.mAuthen.trim())) {
       return false;
     }
     if (!(this.mEncrypt == null ? "" : this.mEncrypt.trim()).equals(o.mEncrypt == null ? "" : o.mEncrypt.trim())) {
       return false;
     }
     return super.equals(obj);
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.terminal.config.WifiConfig
 * JD-Core Version:    0.7.0.1
 */