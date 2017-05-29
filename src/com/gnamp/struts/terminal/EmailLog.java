 package com.gnamp.struts.terminal;
 
 import java.util.Date;
 
 public class EmailLog
 {
   private long mDeviceId;
   private Date mSendTime = null;
   private Date mLogoffTime = null;
   
   public long getDeviceId()
   {
     return this.mDeviceId;
   }
   
   public void setDeviceId(long deviceId)
   {
     this.mDeviceId = deviceId;
   }
   
   public Date getLogoffTime()
   {
     return this.mLogoffTime;
   }
   
   public void setLogoffTime(Date logoffTime)
   {
     this.mLogoffTime = logoffTime;
   }
   
   public Date getSendTime()
   {
     return this.mSendTime;
   }
   
   public void setSendTime(Date logonTime)
   {
     this.mSendTime = logonTime;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.terminal.EmailLog
 * JD-Core Version:    0.7.0.1
 */