 package com.gnamp.struts.vo;
 
 import com.gnamp.server.model.KeyLog;
 import java.text.SimpleDateFormat;
 
 public class ExcelKeyLogVo
 {
   private String deviceId;
   private String dateTime;
   private String deviceName;
   private String keycount;
   
   @Deprecated
   public ExcelKeyLogVo convert(KeyLog p, boolean checkhour)
   {
     String tmp = "";
     String DeviceIDs = Long.toHexString(p.getDeviceId()).toUpperCase();
     if (DeviceIDs.length() < 12)
     {
       for (int n = 0; n < 12 - DeviceIDs.length(); n++) {
         tmp = tmp + "0";
       }
       DeviceIDs = tmp + DeviceIDs;
     }
     setDeviceId(DeviceIDs);
     if (checkhour) {
       setDateTime(new SimpleDateFormat("yyyy-MM-dd HH:00").format(p.getDateTime()));
     } else {
       setDateTime(new SimpleDateFormat("yyyy-MM-dd").format(p.getDateTime()));
     }
     setDeviceName(p.getDeviceName());
     setKeycount(String.valueOf(p.getKeycount()));
     return this;
   }
   
   @IExcelContentVo(columnIndex=0)
   public String getDeviceId()
   {
     return this.deviceId;
   }
   
   public void setDeviceId(String deviceId)
   {
     this.deviceId = deviceId;
   }
   
   @IExcelContentVo(columnIndex=2)
   public String getDateTime()
   {
     return this.dateTime;
   }
   
   public void setDateTime(String dateTime)
   {
     this.dateTime = dateTime;
   }
   
   @IExcelContentVo(columnIndex=1)
   public String getDeviceName()
   {
     return this.deviceName;
   }
   
   public void setDeviceName(String deviceName)
   {
     this.deviceName = deviceName;
   }
   
   @IExcelContentVo(columnIndex=3)
   public String getKeycount()
   {
     return this.keycount;
   }
   
   public void setKeycount(String keycount)
   {
     this.keycount = keycount;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.vo.ExcelKeyLogVo
 * JD-Core Version:    0.7.0.1
 */