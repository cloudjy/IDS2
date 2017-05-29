 package com.gnamp.struts.vo;
 
 import com.gnamp.server.model.PipeLog;
 import java.text.SimpleDateFormat;
 
 public class ExcelPipeLogVo
 {
   private String deviceId;
   private String startTime;
   private String deviceName;
   private String result;
   private String endTime;
   
   @Deprecated
   public ExcelPipeLogVo convert(PipeLog p, boolean checkhour)
   {
     String tmp = "";
     String DeviceIDs = Long.toHexString(p.getDevId()).toUpperCase();
     if (DeviceIDs.length() < 12)
     {
       for (int n = 0; n < 12 - DeviceIDs.length(); n++) {
         tmp = tmp + "0";
       }
       DeviceIDs = tmp + DeviceIDs;
     }
     setDeviceId(DeviceIDs);
     setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(p.getStarttime()));
     setEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(p.getEndtime()));
     
 
 
 
 
 
 
     setDeviceName(p.getDevName());
     setResult(String.valueOf(p.getResult()));
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
   
   @IExcelContentVo(columnIndex=1)
   public String getDeviceName()
   {
     return this.deviceName;
   }
   
   public void setDeviceName(String deviceName)
   {
     this.deviceName = deviceName;
   }
   
   @IExcelContentVo(columnIndex=2)
   public String getStartTime()
   {
     return this.startTime;
   }
   
   public void setStartTime(String startTime)
   {
     this.startTime = startTime;
   }
   
   @IExcelContentVo(columnIndex=4)
   public String getResult()
   {
     return this.result;
   }
   
   public void setResult(String result)
   {
     this.result = result;
   }
   
   @IExcelContentVo(columnIndex=3)
   public String getEndTime()
   {
     return this.endTime;
   }
   
   public void setEndTime(String endTime)
   {
     this.endTime = endTime;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.vo.ExcelPipeLogVo
 * JD-Core Version:    0.7.0.1
 */