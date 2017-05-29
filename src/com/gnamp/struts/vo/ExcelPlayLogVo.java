 package com.gnamp.struts.vo;
 
 import com.gnamp.server.model.PlayLog;
 import java.text.SimpleDateFormat;
 
 public class ExcelPlayLogVo
 {
   String dateTime;
   String deviceId;
   String fileName;
   String size;
   String count;
   String mode;
   String playTimes;
   
   @Deprecated
   public ExcelPlayLogVo convert(PlayLog p, boolean checkhour)
   {
     if (checkhour) {
       setDateTime(new SimpleDateFormat("yyyy-MM-dd HH:00").format(p.getDateTime()));
     } else {
       setDateTime(new SimpleDateFormat("yyyy-MM-dd").format(p.getDateTime()));
     }
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
     setFileName(p.getFileName());
     setSize(String.valueOf(Math.round(p.getSize() / 1024.0D * 100.0D) / 100.0D));
     setCount(String.valueOf(p.getPlayCount()));
     setMode(p.getPlayMode() == 0 ? "自动" : "交互");
     setPlayTimes(String.valueOf(p.getPlayTimes()));
     return this;
   }
   
   @IExcelContentVo(columnIndex=0)
   public String getDateTime()
   {
     return this.dateTime;
   }
   
   public void setDateTime(String dateTime)
   {
     this.dateTime = dateTime;
   }
   
   @IExcelContentVo(columnIndex=2)
   public String getDeviceId()
   {
     return this.deviceId;
   }
   
   public void setDeviceId(String deviceId)
   {
     this.deviceId = deviceId;
   }
   
   @IExcelContentVo(columnIndex=1)
   public String getFileName()
   {
     return this.fileName;
   }
   
   public void setFileName(String fileName)
   {
     this.fileName = fileName;
   }
   
   @IExcelContentVo(columnIndex=3)
   public String getSize()
   {
     return this.size;
   }
   
   public void setSize(String size)
   {
     this.size = size;
   }
   
   @IExcelContentVo(columnIndex=4)
   public String getCount()
   {
     return this.count;
   }
   
   public void setCount(String count)
   {
     this.count = count;
   }
   
   @IExcelContentVo(columnIndex=5)
   public String getMode()
   {
     return this.mode;
   }
   
   public void setMode(String mode)
   {
     this.mode = mode;
   }
   
   @IExcelContentVo(columnIndex=6)
   public String getPlayTimes()
/* ;0:   */   {
/* ;1:91 */     return this.playTimes;
/* ;2:   */   }
/* ;3:   */   
/* ;4:   */   public void setPlayTimes(String playTimes)
/* ;5:   */   {
/* ;6:95 */     this.playTimes = playTimes;
/* ;7:   */   }
/* ;8:   */ }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.vo.ExcelPlayLogVo
 * JD-Core Version:    0.7.0.1
 */