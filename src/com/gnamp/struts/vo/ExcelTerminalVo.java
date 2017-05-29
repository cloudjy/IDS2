 package com.gnamp.struts.vo;
 
 import com.gnamp.server.model.Terminal;
 import java.text.SimpleDateFormat;
 
 public class ExcelTerminalVo
 {
   private String deviceId;
   private String deviceName;
   private String curLoopTaskName;
   private String curDemandTaskName;
   private String curPluginTaskName;
   private String appUpdated;
   private String kernelUpdated;
   private String configUpdated;
   private String lastDownload;
   private String lastPatrol;
   private String aliveinterval;
   private String networkState;
   
   @Deprecated
   public ExcelTerminalVo convert(Terminal terminal)
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
     setCurLoopTaskName(terminal.getCurLoopTaskName());
     setCurDemandTaskName(terminal.getCurDemandTaskName());
     setCurPluginTaskName(terminal.getCurPluginTaskName());
     setAppUpdated(terminal.getAppUpdated() == 1 ? "已同步" : "未同步");
     setKernelUpdated(terminal.getKernelUpdated() == 1 ? "已同步" : "未同步");
     setConfigUpdated(terminal.getConfigUpdated() == 1 ? "已同步" : "未同步");
     setLastDownload(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(terminal.getLastDownload()));
     setLastPatrol(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(terminal.getLastPatrol()));
     setAliveinterval(terminal.getAliveInterval() == 1 ? "在线" : "离线");
     setNetworkState(terminal.getOnlineState() == 1 ? "在线" : "离线");
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
   
   @IExcelContentVo(columnIndex=5)
   public String getAppUpdated()
   {
     return this.appUpdated;
   }
   
   public void setAppUpdated(String appUpdated)
   {
     this.appUpdated = appUpdated;
   }
   
   @IExcelContentVo(columnIndex=6)
   public String getKernelUpdated()
   {
     return this.kernelUpdated;
   }
   
   public void setKernelUpdated(String kernelUpdated)
   {
     this.kernelUpdated = kernelUpdated;
   }
   
   @IExcelContentVo(columnIndex=7)
   public String getConfigUpdated()
   {
     return this.configUpdated;
   }
   
   public void setConfigUpdated(String configUpdated)
   {
     this.configUpdated = configUpdated;
   }
   
   @IExcelContentVo(columnIndex=8)
   public String getLastDownload()
   {
     return this.lastDownload;
   }
   
   public void setLastDownload(String lastDownload)
   {
     this.lastDownload = lastDownload;
   }
   
   @IExcelContentVo(columnIndex=9)
   public String getLastPatrol()
   {
     return this.lastPatrol;
   }
   
   public void setLastPatrol(String lastPatrol)
   {
     this.lastPatrol = lastPatrol;
   }
   
   @IExcelContentVo(columnIndex=10)
   public String getAliveinterval()
   {
     return this.aliveinterval;
   }
   
   public void setAliveinterval(String aliveinterval)
   {
     this.aliveinterval = aliveinterval;
   }
   
   @IExcelContentVo(columnIndex=11)
   public String getNetworkState()
   {
     return this.networkState;
   }
   
   public void setNetworkState(String networkState)
   {
     this.networkState = networkState;
   }
   
   @IExcelContentVo(columnIndex=2)
   public String getCurLoopTaskName()
   {
     return this.curLoopTaskName;
   }
   
   public void setCurLoopTaskName(String curLoopTaskName)
   {
     this.curLoopTaskName = curLoopTaskName;
   }
   
   @IExcelContentVo(columnIndex=3)
   public String getCurDemandTaskName()
   {
     return this.curDemandTaskName;
   }
   
   public void setCurDemandTaskName(String curDemandTaskName)
   {
     this.curDemandTaskName = curDemandTaskName;
   }
   
   @IExcelContentVo(columnIndex=4)
   public String getCurPluginTaskName()
   {
     return this.curPluginTaskName;
   }
   
   public void setCurPluginTaskName(String curPluginTaskName)
   {
     this.curPluginTaskName = curPluginTaskName;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.vo.ExcelTerminalVo
 * JD-Core Version:    0.7.0.1
 */