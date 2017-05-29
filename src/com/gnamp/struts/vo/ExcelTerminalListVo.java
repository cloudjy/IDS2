 package com.gnamp.struts.vo;
 
 import com.gnamp.server.model.Terminal;
 import org.apache.commons.lang.StringUtils;
 
 public class ExcelTerminalListVo
 {
   private String deviceId;
   private String deviceName;
   private String description;
   
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
   public String getDescription()
   {
     return this.description;
   }
   
   public void setDescription(String description)
   {
     this.description = description;
   }
   
   public ExcelTerminalListVo convert(Terminal terminal)
   {
     String tmp = "";
     String DeviceIDs = Long.toHexString(terminal.getDeviceId()).toUpperCase();
     if (DeviceIDs.length() < 12)
     {
       for (int n = 0; n < 12 - DeviceIDs.length(); n++) {
         tmp = tmp + "0";
       }
       DeviceIDs = tmp + DeviceIDs;
     }
     setDeviceId(DeviceIDs);
     setDeviceName(terminal.getDeviceName());
     setDescription(terminal.getDescription());
     return this;
   }
   
   public boolean equals(Object obj)
   {
     if ((obj instanceof ExcelTerminalListVo))
     {
       ExcelTerminalListVo o = (ExcelTerminalListVo)obj;
       if ((o.getDeviceId() == null) && (getDeviceId() == null)) {
         return true;
       }
       if ((getDeviceId() != null) && (getDeviceId().equals(o.getDeviceId()))) {
         return true;
       }
     }
     return super.equals(obj);
   }
   
   public int hashCode()
   {
     if (StringUtils.isBlank(getDeviceId())) {
       return 0;
     }
     return getDeviceId().hashCode();
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.vo.ExcelTerminalListVo
 * JD-Core Version:    0.7.0.1
 */