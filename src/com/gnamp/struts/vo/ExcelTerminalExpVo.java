 package com.gnamp.struts.vo;
 
 import com.gnamp.server.model.TerminalExp;
 import java.text.SimpleDateFormat;
 
 public class ExcelTerminalExpVo
 {
   private String deviceId;
   private String deviceName;
   private String eventDate;
   
   @IExcelContentVo(columnIndex=0)
   public String getEventDate()
   {
     return this.eventDate;
   }
   
   public void setEventDate(String eventDate)
   {
     this.eventDate = eventDate;
   }
   
   private String playExp = "0";
   private String timeExp = "0";
   private String restartExp = "0";
   private String closeState = "正常";
   
   public ExcelTerminalExpVo convert(TerminalExp t, boolean checkhour)
   {
     String tmp = "";
     String DeviceIDs = Long.toHexString(t.getDeviceId()).toUpperCase();
     if (DeviceIDs.length() < 12)
     {
       for (int n = 0; n < 12 - DeviceIDs.length(); n++) {
         tmp = tmp + "0";
       }
       DeviceIDs = tmp + DeviceIDs;
     }
     setDeviceId(DeviceIDs);
     setDeviceName(t.getDeviceName());
     if (checkhour) {
       setEventDate(new SimpleDateFormat("yyyy-MM-dd HH:00").format(t.getEventTime()));
     } else {
       setEventDate(new SimpleDateFormat("yyyy-MM-dd").format(t.getEventTime()));
     }
     return this;
   }
   
   @IExcelContentVo(columnIndex=1)
   public String getDeviceId()
   {
     return this.deviceId;
   }
   
   public void setDeviceId(String deviceId)
   {
     this.deviceId = deviceId;
   }
   
   @IExcelContentVo(columnIndex=2)
   public String getDeviceName()
   {
     return this.deviceName;
   }
   
   public void setDeviceName(String deviceName)
   {
     this.deviceName = deviceName;
   }
   
   @IExcelContentVo(columnIndex=3)
   public String getPlayExp()
   {
     return this.playExp;
   }
   
   public void setPlayExp(String playExp)
   {
     this.playExp = playExp;
   }
   
   @IExcelContentVo(columnIndex=4)
   public String getTimeExp()
   {
     return this.timeExp;
   }
   
   public void setTimeExp(String timeExp)
   {
     this.timeExp = timeExp;
   }
   
   @IExcelContentVo(columnIndex=5)
   public String getRestartExp()
   {
     return this.restartExp;
   }
   
   public void setRestartExp(String restartExp)
   {
     this.restartExp = restartExp;
   }
   
   @IExcelContentVo(columnIndex=6)
   public String getCloseState()
   {
     return this.closeState;
   }
   
/* ;0:   */   public void setCloseState(String closeState)
/* ;1:   */   {
/* ;2:81 */     this.closeState = closeState;
/* ;3:   */   }
/* ;4:   */ }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.vo.ExcelTerminalExpVo
 * JD-Core Version:    0.7.0.1
 */