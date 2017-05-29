 package com.gnamp.struts.terminal;
 
 public class WatcherSetting
 {
   private long mDeviceId;
   private int mMaxMinutes;
   private String mEmail;
   
   public long getDeviceId()
   {
     return this.mDeviceId;
   }
   
   public void setDeviceId(long deviceId)
   {
     this.mDeviceId = deviceId;
   }
   
   public int getMaxMinutes()
   {
     return this.mMaxMinutes;
   }
   
   public void setMaxMinutes(int maxMinutes)
   {
     this.mMaxMinutes = maxMinutes;
   }
   
   public String getEmail()
   {
     return this.mEmail;
   }
   
   public void setEmail(String email)
   {
     this.mEmail = email;
   }
   
   public boolean equals(Object obj)
   {
     if (this == obj) {
       return true;
     }
     if ((obj == null) || (!(obj instanceof WatcherSetting))) {
       return false;
     }
     WatcherSetting o = (WatcherSetting)obj;
     if (this.mDeviceId != o.mDeviceId) {
       return false;
     }
     if (this.mMaxMinutes != o.mMaxMinutes) {
       return false;
     }
     if (!(this.mEmail == null ? "" : this.mEmail.trim()).equals(o.mEmail == null ? "" : o.mEmail.trim())) {
       return false;
     }
     return true;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.terminal.WatcherSetting
 * JD-Core Version:    0.7.0.1
 */