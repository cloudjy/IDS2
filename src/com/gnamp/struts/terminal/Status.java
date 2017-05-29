 package com.gnamp.struts.terminal;
 
 import java.util.Date;
 
 public class Status
 {
   private long mDeviceId = 0L;
   private String mDeviceName = "";
   private int mOnlineState = 0;
   private Date mLogonTime = null;
   private Date mLogoffTime = null;
   
   public long getDeviceId()
   {
     return this.mDeviceId;
   }
   
   public void setDeviceId(long deviceId)
   {
     this.mDeviceId = deviceId;
   }
   
   public String getDeviceName()
   {
     return this.mDeviceName != null ? this.mDeviceName : "";
   }
   
   public void setDeviceName(String deviceName)
   {
     this.mDeviceName = (deviceName != null ? deviceName : "");
   }
   
   public int getOnlineState()
   {
     return this.mOnlineState;
   }
   
   public void setOnlineState(int lnlineState)
   {
     this.mOnlineState = lnlineState;
   }
   
   public Date getLogonTime()
   {
     return this.mLogonTime;
   }
   
   public void setLogonTime(Date logonTime)
   {
     this.mLogonTime = logonTime;
   }
   
   public Date getLogoffTime()
   {
     return this.mLogoffTime;
   }
   
   public void setLogoffTime(Date logoffTime)
   {
     this.mLogoffTime = logoffTime;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.terminal.Status
 * JD-Core Version:    0.7.0.1
 */