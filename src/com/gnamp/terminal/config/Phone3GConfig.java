 package com.gnamp.terminal.config;
 
 public final class Phone3GConfig
   extends NetworkConfig
 {
   public static final String WCDMA = "wcdma";
   public static final String EVDO = "evdo";
   public String mType = "";
   public String mApn = "";
   public String mNumber = "";
   public String mPassword = "";
   
   public String getType()
   {
     return this.mType;
   }
   
   public void setType(String type)
   {
     this.mType = (type == null ? "" : type.trim());
   }
   
   public String getApn()
   {
     return this.mApn;
   }
   
   public void setApn(String apn)
   {
     this.mApn = (apn == null ? "" : apn.trim());
   }
   
   public String getNumber()
   {
     return this.mNumber;
   }
   
   public void setNumber(String number)
   {
     this.mNumber = (number == null ? "" : number.trim());
   }
   
   public String getPassword()
   {
     return this.mPassword;
   }
   
   public void setmPassword(String password)
   {
     this.mPassword = (password == null ? "" : password);
   }
   
   public String toString()
   {
     return 
     
       "[3g][" + this.mType + "] apn=" + this.mApn + ", password=" + this.mPassword + ", number=" + this.mNumber;
   }
   
   public boolean equals(Object obj)
   {
     if (this == obj) {
       return true;
     }
     if ((obj == null) || (!(obj instanceof Phone3GConfig))) {
       return false;
     }
     Phone3GConfig o = (Phone3GConfig)obj;
     if (!(this.mPassword == null ? "" : this.mPassword.trim()).equals(o.mPassword == null ? "" : o.mPassword.trim())) {
       return false;
     }
     if (!(this.mNumber == null ? "" : this.mNumber.trim()).equals(o.mNumber == null ? "" : o.mNumber.trim())) {
       return false;
     }
     return true;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.terminal.config.Phone3GConfig
 * JD-Core Version:    0.7.0.1
 */