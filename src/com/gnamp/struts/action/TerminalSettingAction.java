 package com.gnamp.struts.action;
 
 import com.gnamp.server.handle.MessageServer;
 import com.gnamp.server.handle.TerminalHandle;
 import com.gnamp.server.model.Terminal;
 import com.gnamp.struts.utils.JsonUtils;
 import com.gnamp.terminal.config.ApConfig;
 import com.gnamp.terminal.config.DeviceSetting;
 import com.gnamp.terminal.config.EthernetConfig;
 import com.gnamp.terminal.config.ExpertConfig;
 import com.gnamp.terminal.config.IpAddressConfig;
 import com.gnamp.terminal.config.LocalConfig;
 import com.gnamp.terminal.config.NetworkConfig;
 import com.gnamp.terminal.config.PPPOEConfig;
 import com.gnamp.terminal.config.Phone3GConfig;
 import com.gnamp.terminal.config.PowerConfig;
 import com.gnamp.terminal.config.ServerConfig;
 import com.gnamp.terminal.config.TimeBound;
 import com.gnamp.terminal.config.TimeBoundInt;
 import com.gnamp.terminal.config.WifiConfig;
 import common.Logger;
 import java.io.BufferedReader;
 import java.io.ByteArrayOutputStream;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileNotFoundException;
 import java.io.FileOutputStream;
 import java.io.FileReader;
 import java.io.InputStream;
 import java.net.URL;
 import java.net.URLDecoder;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 
 public class TerminalSettingAction
   extends JSONAction
 {
	 
	 
   private Terminal mTerminal = null;
   
   public Terminal getTerminal()
   {
     return this.mTerminal;
   }
   
   private TerminalHandle mTerminalHandle = null;
   
   private TerminalHandle getTerminalHandle()
   {
     return this.mTerminalHandle = new TerminalHandle(this);
   }
   
   private String mSelectIDs = "";
   private List<Long> mIdArray = new ArrayList();
   
   public String getSelectIDs()
   {
     return this.mSelectIDs;
   }
   
   public void setSelectIDs(String selectIDs)
   {
     this.mSelectIDs = (selectIDs != null ? selectIDs : "");
     
     this.mIdArray.clear();
     String[] idArray = this.mSelectIDs.split(",");
     if (idArray != null) {
       for (int i = 0; i < idArray.length; i++)
       {
         long id = 0L;
         try
         {
           id = Long.parseLong(idArray[i]);
         }
         catch (Exception localException) {}
         if (id != 0L) {
           this.mIdArray.add(Long.valueOf(id));
         }
       }
     }
   }
   
   private String mConfigString = "";
   
   public String getConfigString()
   {
     return this.mConfigString;
   }
   
   public void setConfigString(String configString)
   {
     this.mConfigString = (configString != null ? configString : "");
   }
   
   private boolean mRemoteModify = false;
   
   public boolean getRemoteModify()
   {
     return this.mRemoteModify;
   }
   
   public void setRemoteModify(boolean modify)
   {
     this.mRemoteModify = modify;
   }
   
   private DeviceSetting mCurrentSetting = null;
   
   public DeviceSetting getCurrentSetting()
   {
     return this.mCurrentSetting;
   }
   
   private DeviceSetting mAssignSetting = null;
   
   public DeviceSetting getAssignSetting()
   {
     return this.mAssignSetting;
   }
   
   private static final String CONFIG_ROOT =  _ConfigRoot();;
   
   private static String _ConfigRoot()
   {
     try
     {
       String classDirectory = TerminalSettingAction.class.getClassLoader().getResource("/").getPath();
       classDirectory = URLDecoder.decode(classDirectory, "UTF-8");
       File f = new File(classDirectory);
       f = f.getParentFile();
       f = f.getParentFile();
       
       return f.getAbsolutePath() + File.separatorChar + "gnamp_work" + 
         File.separatorChar + "device";
     }
     catch (Exception ex) {}
     return "";
   }
   
   private static String AssignPath(long devId)
   {
     return CONFIG_ROOT + File.separatorChar + String.format("%016X.xml", new Object[] { Long.valueOf(devId) });
   }
   
   private static String CurrentPath(long devId)
   {
     return CONFIG_ROOT + File.separatorChar + String.format("%016X_1.xml", new Object[] { Long.valueOf(devId) });
   }
   
   public static DeviceSetting readCurrentSetting(long devId)
   {
     return readDeviceSetting(CurrentPath(devId));
   }
   
   private static DeviceSetting readAssignSetting(long devId)
   {
     return readDeviceSetting(AssignPath(devId));
   }
   
   private static DeviceSetting readDeviceSetting(String path)
   {
     String text = "";
     FileInputStream in = null;
     ByteArrayOutputStream out = null;
     label250:
     try
     {
       File file = new File(path);
       long fileLength = 0L;
       if (file.exists())
       {
         if ((fileLength = file.length()) <= 0L) {
           break label250;
         }
         in = new FileInputStream(file);
         out = new ByteArrayOutputStream();
         
         byte[] data = new byte[(int)fileLength < 1024 ? (int)fileLength : 1024];
         int rlen = -1;
         while ((rlen < fileLength) && ((rlen = in.read(data)) != -1)) {
           out.write(data, 0, rlen);
         }
         text = out.toString("UTF-8");
       }
     }
     catch (FileNotFoundException localFileNotFoundException)
     {
       if (in != null) {
         try
         {
           in.close();
         }
         catch (Exception localException1) {}
       }
       if (out != null) {
         try
         {
           out.close();
         }
         catch (Exception localException2) {}
       }
     }
     catch (Exception e)
     {
       log.error("#DEV_SETTING#: read faild: " + e.getMessage(), e);
       if (in != null) {
         try
         {
           in.close();
         }
         catch (Exception localException3) {}
       }
       if (out != null) {
         try
         {
           out.close();
         }
         catch (Exception localException4) {}
       }
     }
     finally
     {
       if (in != null) {
         try
         {
           in.close();
         }
         catch (Exception localException5) {}
       }
       if (out != null) {
         try
         {
           out.close();
         }
         catch (Exception localException6) {}
       }
     }
     DeviceSetting setting = new DeviceSetting();
     if ((text != null) && ((text = text.trim()).length() > 0)) {
       setting.parseFromString(text);
     }
     if (setting.mLocal == null) {
       setting.mLocal = new LocalConfig();
     }
     return setting;
   }
   
   private static void saveCurrentSetting(long devId, DeviceSetting setting)
   {
     if (devId == 0L) {
       return;
     }
     saveDeviceSetting(CurrentPath(devId), setting);
   }
   
   private static void saveAssignSetting(long devId, DeviceSetting setting)
   {
     if (devId == 0L) {
       return;
     }
     setting.mVersion = DeviceSetting.upgradeVersion(setting.mVersion);
     saveDeviceSetting(AssignPath(devId), setting);
   }
   
   private static void saveDeviceSetting(String path, DeviceSetting setting)
   {
     String text = "";
     FileOutputStream out = null;
     try
     {
       File file = new File(path);
       file.getParentFile().mkdirs();
       out = new FileOutputStream(file);
       text = setting.convetToString();
       byte[] bytes = text != null ? text.getBytes("UTF-8") : null;
       if ((bytes != null) && (bytes.length > 0)) {
         out.write(bytes);
       }
     }
     catch (Exception e)
     {
       log.error("#DEV_SETTING#: write faild: " + e.getMessage(), e);
       if (out != null) {
         try
         {
           out.close();
         }
         catch (Exception localException1) {}
       }
     }
     finally
     {
       if (out != null) {
         try
         {
           out.close();
         }
         catch (Exception localException2) {}
       }
     }
   }
   
   private void sendNotifyToDevice(long devId)
   {
     try
     {
       if (devId == 0L) {
         return;
       }
       MessageServer messageServer = new MessageServer();
       messageServer.settingChanged(devId);
     }
     catch (Exception e)
     {
       log.error("#DEV_SETTING#: sendNotifyToDevice failed[" + devId + "]" + e.getMessage(), e);
     }
   }
   
   private String mItem = "";
   private static final String N0_10 = "\\s*([0-9]|(10))\\s*";
   private static final String HHmm = "\\s*((2[0-3])|([0-1][0-9])):[0-5][0-9]\\s*";
   private static final String HHmm_HHmm = "\\s*((2[0-3])|([0-1][0-9])):[0-5][0-9]\\s*,\\s*((2[0-3])|([0-1][0-9])):[0-5][0-9]\\s*";
   private static final String _HHmmHHmm_ = "(\\s*\\[\\s*((2[0-3])|([0-1][0-9])):[0-5][0-9]\\s*,\\s*((2[0-3])|([0-1][0-9])):[0-5][0-9]\\s*\\]\\s*[,|;]{0,1}\\s*)+";
   private static final String _HHmmHHmm_Val = "(\\s*\\[\\s*((2[0-3])|([0-1][0-9])):[0-5][0-9]\\s*,\\s*((2[0-3])|([0-1][0-9])):[0-5][0-9]\\s*,\\s*([0-9]|(10))\\s*\\]\\s*[,|;]{0,1}\\s*)+";
   private static final String N0_255 = "([0-9]|([1-9][0-9])|(1[0-9][0-9])|(2[0-4][0-9])|(25[0-5]))";
   private static final String N1_255 = "([1-9]|([1-9][0-9])|(1[0-9][0-9])|(2[0-4][0-9])|(25[0-5]))";
   private static final String IP_ADDR = "\\s*([1-9]|([1-9][0-9])|(1[0-9][0-9])|(2[0-4][0-9])|(25[0-5]))\\.([0-9]|([1-9][0-9])|(1[0-9][0-9])|(2[0-4][0-9])|(25[0-5]))\\.([0-9]|([1-9][0-9])|(1[0-9][0-9])|(2[0-4][0-9])|(25[0-5]))\\.([0-9]|([1-9][0-9])|(1[0-9][0-9])|(2[0-4][0-9])|(25[0-5]))\\s*";
   private static final String DAILY = "daily";
   private static final String WEEKLY = "weekly";
   private static final String DAILY_FORMAT = "\\s*\\[\\s*((2[0-3])|([0-1][0-9])):[0-5][0-9]\\s*,\\s*((2[0-3])|([0-1][0-9])):[0-5][0-9]\\s*\\]\\s*";
   private static final String WEEKLY_FORMAT = "(\\s*\\[\\s*[0-6]\\s*[,|;]\\s*\\s*((2[0-3])|([0-1][0-9])):[0-5][0-9]\\s*,\\s*((2[0-3])|([0-1][0-9])):[0-5][0-9]\\s*\\]\\s*[,|;]{0,1}\\s*)+";
   
   public String getItem()
   {
     return this.mItem;
   }
   
   public void setItem(String item)
   {
     this.mItem = (item == null ? "" : item.trim().toLowerCase());
   }
   
   public String device()
   {
     long id = this.mIdArray.size() == 1 ? ((Long)this.mIdArray.get(0)).longValue() : 0L;
     this.mTerminal = (id == 0L ? null : getTerminalHandle().read(id));
     this.mCurrentSetting = (id != 0L ? readCurrentSetting(id) : null);
     this.mAssignSetting = (id != 0L ? readAssignSetting(id) : null);
     if ((this.mItem == null) || (this.mItem.length() == 0)) {
       return "volume";
     }
     if ((this.mItem.equals("volume")) || 
       (this.mItem.equals("brightness")) || 
       (this.mItem.equals("power")) || 
       (this.mItem.equals("standby")) || 
       (this.mItem.equals("download")) || 
       (this.mItem.equals("demand")) || 
       (this.mItem.equals("password")) || 
       (this.mItem.equals("advanced"))) {
       return this.mItem;
     }
     return "volume";
   }
   
   public String network()
   {
     long id = this.mIdArray.size() == 1 ? ((Long)this.mIdArray.get(0)).longValue() : 0L;
     this.mTerminal = (id == 0L ? null : getTerminalHandle().read(id));
     this.mCurrentSetting = (id != 0L ? readCurrentSetting(id) : null);
     this.mAssignSetting = (id != 0L ? readAssignSetting(id) : null);
     if ((this.mItem == null) || (this.mItem.length() == 0))
     {
       NetworkConfig network = null;
       if ((this.mAssignSetting != null) && (this.mAssignSetting.mNetwork != null)) {
         network = this.mAssignSetting.mNetwork;
       } else if ((this.mCurrentSetting != null) && (this.mCurrentSetting.mNetwork != null)) {
         network = this.mCurrentSetting.mNetwork;
       }
       if (network == null) {
         return "ethernet";
       }
       if ((network instanceof EthernetConfig)) {
         return "ethernet";
       }
       if ((network instanceof WifiConfig)) {
         return "wifi";
       }
       if ((network instanceof Phone3GConfig)) {
         return "3g";
       }
       if ((network instanceof PPPOEConfig)) {
         return "pppoe";
       }
       return "ethernet";
     }
     if ((this.mItem.equals("ethernet")) || 
       (this.mItem.equals("wifi")) || 
       (this.mItem.equals("3g")) || 
       (this.mItem.equals("pppoe")) || 
       (this.mItem.equals("server")) || 
       (this.mItem.equals("ap"))) {
       return this.mItem;
     }
     return "ethernet";
   }
   
   private static boolean rangeValueEquals(List<TimeBoundInt> o1, List<TimeBoundInt> o2)
   {
     return (o1 == null) || (o2 == null) ? false : o1 == o2 ? true : 
       LocalConfig.timeBoundIntToText(o1).equals(LocalConfig.timeBoundIntToText(o2));
   }
   
   private static boolean rangeEquals(List<TimeBound> o1, List<TimeBound> o2)
   {
     return (o1 == null) || (o2 == null) ? false : o1 == o2 ? true : 
       LocalConfig.timeBoundToText(o1).equals(LocalConfig.timeBoundToText(o2));
   }
   
   private static boolean powerEquals(PowerConfig o1, PowerConfig o2)
   {
     return (o1 == null) || (o2 == null) ? false : o1 == o2 ? true : 
       LocalConfig.powerToText(o1).equals(LocalConfig.powerToText(o2));
   }
   
   private static boolean serverEquals(ServerConfig o1, ServerConfig o2)
   {
     return (o1 == null) || (o2 == null) ? false : o1 == o2 ? true : o1.equals(o2);
   }
   
   private static boolean networkEquals(NetworkConfig o1, NetworkConfig o2)
   {
     return (o1 == null) || (o2 == null) ? false : o1 == o2 ? true : o1.equals(o2);
   }
   
   private static boolean passwordEquals(String o1, String o2)
   {
     return (o1 == null) || (o2 == null) ? false : o1 == o2 ? true : o1.equals(o2);
   }
   
   private static boolean expertEquals(ExpertConfig o1, ExpertConfig o2)
   {
     return (o1 == null) || (o2 == null) ? false : o1 == o2 ? true : o1.equals(o2);
   }
   
   private static boolean apEquals(ApConfig o1, ApConfig o2)
   {
     return (o1 == null) || (o2 == null) ? false : o1 == o2 ? true : o1.equals(o2);
   }
   
   public void putVolume()
   {
     try
     {
       if ((this.mRemoteModify) && (!this.mConfigString.matches("(\\s*\\[\\s*((2[0-3])|([0-1][0-9])):[0-5][0-9]\\s*,\\s*((2[0-3])|([0-1][0-9])):[0-5][0-9]\\s*,\\s*([0-9]|(10))\\s*\\]\\s*[,|;]{0,1}\\s*)+")))
       {
         JsonUtils.writeError(this.servletResponse);
         log.error("#DEV_SETTING#: invalid volume config: " + this.mConfigString);
         return;
       }
       List<TimeBoundInt> newValue = !this.mRemoteModify ? 
         null : LocalConfig.pareTimeBoundInts(this.mConfigString);
       for (Iterator localIterator = this.mIdArray.iterator(); localIterator.hasNext();)
       {
         long id = ((Long)localIterator.next()).longValue();
         DeviceSetting setting = readAssignSetting(id);
         if ((setting != null) && 
           (!rangeValueEquals(newValue, setting.mLocal.mVolume)))
         {
           setting.mLocal.mVolume = newValue;
           saveAssignSetting(id, setting);
           if (this.mRemoteModify) {
             sendNotifyToDevice(id);
           }
         }
       }
       JsonUtils.writeSuccess(this.servletResponse);
     }
     catch (Exception ex)
     {
       JsonUtils.writeError(this.servletResponse);
       log.error("#DEV_SETTING#: [Volume]exception: " + ex.getMessage(), ex);
     }
   }
   
   public void putBrightness()
   {
     try
     {
       if ((this.mRemoteModify) && (!this.mConfigString.matches("(\\s*\\[\\s*((2[0-3])|([0-1][0-9])):[0-5][0-9]\\s*,\\s*((2[0-3])|([0-1][0-9])):[0-5][0-9]\\s*,\\s*([0-9]|(10))\\s*\\]\\s*[,|;]{0,1}\\s*)+")))
       {
         JsonUtils.writeError(this.servletResponse);
         log.error("#DEV_SETTING#: invalid brightness config: " + this.mConfigString);
         return;
       }
       List<TimeBoundInt> newValue = !this.mRemoteModify ? 
         null : LocalConfig.pareTimeBoundInts(this.mConfigString);
       for (Iterator localIterator = this.mIdArray.iterator(); localIterator.hasNext();)
       {
         long id = ((Long)localIterator.next()).longValue();
         DeviceSetting setting = readAssignSetting(id);
         if ((setting != null) && 
           (!rangeValueEquals(newValue, setting.mLocal.mBrightness)))
         {
           setting.mLocal.mBrightness = newValue;
           saveAssignSetting(id, setting);
           if (this.mRemoteModify) {
             sendNotifyToDevice(id);
           }
         }
       }
       JsonUtils.writeSuccess(this.servletResponse);
     }
     catch (Exception ex)
     {
       JsonUtils.writeError(this.servletResponse);
       log.error("#DEV_SETTING#: [Brightness]exception: " + ex.getMessage(), ex);
     }
   }
   
   public void putDemand()
   {
     try
     {
       if ((this.mRemoteModify) && (!this.mConfigString.matches("(\\s*\\[\\s*((2[0-3])|([0-1][0-9])):[0-5][0-9]\\s*,\\s*((2[0-3])|([0-1][0-9])):[0-5][0-9]\\s*\\]\\s*[,|;]{0,1}\\s*)+")))
       {
         JsonUtils.writeError(this.servletResponse);
         log.error("#DEV_SETTING#: invalid demand config:" + this.mConfigString);
         return;
       }
       List<TimeBound> newValue = !this.mRemoteModify ? 
         null : LocalConfig.pareTimeBounds(this.mConfigString);
       for (Iterator localIterator = this.mIdArray.iterator(); localIterator.hasNext();)
       {
         long id = ((Long)localIterator.next()).longValue();
         DeviceSetting setting = readAssignSetting(id);
         if ((setting != null) && 
           (!rangeEquals(newValue, setting.mLocal.mDemand)))
         {
           setting.mLocal.mDemand = newValue;
           saveAssignSetting(id, setting);
           if (this.mRemoteModify) {
             sendNotifyToDevice(id);
           }
         }
       }
       JsonUtils.writeSuccess(this.servletResponse);
     }
     catch (Exception ex)
     {
       JsonUtils.writeError(this.servletResponse);
       log.error("#DEV_SETTING#: [demand]exception: " + ex.getMessage(), ex);
     }
   }
   
   public void putDownload()
   {
     try
     {
       if ((this.mRemoteModify) && (!this.mConfigString.matches("(\\s*\\[\\s*((2[0-3])|([0-1][0-9])):[0-5][0-9]\\s*,\\s*((2[0-3])|([0-1][0-9])):[0-5][0-9]\\s*\\]\\s*[,|;]{0,1}\\s*)+")))
       {
         JsonUtils.writeError(this.servletResponse);
         log.error("#DEV_SETTING#: invalid download config:" + this.mConfigString);
         return;
       }
       List<TimeBound> newValue = !this.mRemoteModify ? 
         null : LocalConfig.pareTimeBounds(this.mConfigString);
       for (Iterator localIterator = this.mIdArray.iterator(); localIterator.hasNext();)
       {
         long id = ((Long)localIterator.next()).longValue();
         DeviceSetting setting = readAssignSetting(id);
         if ((setting != null) && 
           (!rangeEquals(newValue, setting.mLocal.mDownload)))
         {
           setting.mLocal.mDownload = newValue;
           saveAssignSetting(id, setting);
           if (this.mRemoteModify) {
             sendNotifyToDevice(id);
           }
         }
       }
       JsonUtils.writeSuccess(this.servletResponse);
     }
     catch (Exception ex)
     {
       JsonUtils.writeError(this.servletResponse);
       log.error("#DEV_SETTING#: [download]exception: " + ex.getMessage(), ex);
     }
   }
   
   public void putStandby()
   {
     try
     {
       if ((this.mRemoteModify) && 
         (!StringUtils.isBlank(this.mConfigString)) && 
         (!this.mConfigString.matches("(\\s*\\[\\s*((2[0-3])|([0-1][0-9])):[0-5][0-9]\\s*,\\s*((2[0-3])|([0-1][0-9])):[0-5][0-9]\\s*\\]\\s*[,|;]{0,1}\\s*)+")))
       {
         JsonUtils.writeError(this.servletResponse);
         log.error("#DEV_SETTING#: invalid [standby] config:" + this.mConfigString);
         return;
       }
       List<TimeBound> newValue = null;
       if (this.mRemoteModify) {
         newValue = LocalConfig.pareTimeBounds(this.mConfigString);
       }
       for (Iterator localIterator = this.mIdArray.iterator(); localIterator.hasNext();)
       {
         long id = ((Long)localIterator.next()).longValue();
         DeviceSetting setting = readAssignSetting(id);
         if ((setting != null) && 
           (!rangeEquals(newValue, setting.mLocal.mStandby)))
         {
           setting.mLocal.mStandby = newValue;
           saveAssignSetting(id, setting);
           if (this.mRemoteModify) {
             sendNotifyToDevice(id);
           }
         }
       }
       JsonUtils.writeSuccess(this.servletResponse);
     }
     catch (Exception ex)
     {
       JsonUtils.writeError(this.servletResponse);
       log.error("#DEV_SETTING#: [standby]exception: " + ex.getMessage(), ex);
     }
   }
   
   private String mPowerMode = "";
   
   public String getPowerMode()
   {
     return this.mPowerMode;
   }
   
   public void setPowerMode(String mode)
   {
     this.mPowerMode = (mode == null ? "" : mode.trim().toLowerCase());
   }
   
   public void putPower()
   {
     try
     {
       if ((this.mRemoteModify) && 
         (!this.mPowerMode.equals("daily")) && (!this.mPowerMode.equals("weekly")))
       {
         JsonUtils.writeError(this.servletResponse);
         log.error("#DEV_SETTING#: invalid power mode:" + this.mPowerMode);
         return;
       }
       if ((this.mRemoteModify) && (
         ((this.mPowerMode.equals("daily")) && (!this.mConfigString.matches("\\s*\\[\\s*((2[0-3])|([0-1][0-9])):[0-5][0-9]\\s*,\\s*((2[0-3])|([0-1][0-9])):[0-5][0-9]\\s*\\]\\s*"))) || (
         (this.mPowerMode.equals("weekly")) && (!this.mConfigString.matches("(\\s*\\[\\s*[0-6]\\s*[,|;]\\s*\\s*((2[0-3])|([0-1][0-9])):[0-5][0-9]\\s*,\\s*((2[0-3])|([0-1][0-9])):[0-5][0-9]\\s*\\]\\s*[,|;]{0,1}\\s*)+")))))
       {
         JsonUtils.writeError(this.servletResponse);
         log.error("#DEV_SETTING#: invalid [power:" + this.mPowerMode + "] config:" + this.mConfigString);
         return;
       }
       PowerConfig newValue = null;
       if ((this.mRemoteModify) && (this.mPowerMode.equals("daily"))) {
         newValue = LocalConfig.parseDailyPower(this.mConfigString);
       } else if ((this.mRemoteModify) && (this.mPowerMode.equals("weekly"))) {
         newValue = LocalConfig.parseWeeklyPower(this.mConfigString);
       }
       for (Iterator localIterator = this.mIdArray.iterator(); localIterator.hasNext();)
       {
         long id = ((Long)localIterator.next()).longValue();
         DeviceSetting setting = readAssignSetting(id);
         if ((setting != null) && 
           (!powerEquals(newValue, setting.mLocal.mPower)))
         {
           setting.mLocal.mPower = newValue;
           saveAssignSetting(id, setting);
           if (this.mRemoteModify) {
             sendNotifyToDevice(id);
           }
         }
       }
       JsonUtils.writeSuccess(this.servletResponse);
     }
     catch (Exception ex)
     {
       JsonUtils.writeError(this.servletResponse);
       log.error("#DEV_SETTING#: [power:" + this.mPowerMode + "]exception: " + ex.getMessage(), ex);
     }
   }
   
   ServerConfig mServer = null;
   
   public ServerConfig getServer()
   {
     return this.mServer;
   }
   
   public void setServer(ServerConfig server)
   {
     this.mServer = server;
   }
   
   public void putServer()
   {
     try
     {
       if ((this.mRemoteModify) && ((this.mServer == null) || 
         (StringUtils.isBlank(this.mServer.mServerAddress)) || 
         (this.mServer.mDownloadPort <= 0) || (this.mServer.mDownloadPort > 65535) || 
         (this.mServer.mControlPort <= 0) || (this.mServer.mControlPort > 65535) || 
         (this.mServer.mDownloadPort == this.mServer.mControlPort)))
       {
         JsonUtils.writeError(this.servletResponse);
         log.error("#DEV_SETTING#: invalid server config: " + this.mServer);
         return;
       }
       if (!this.mRemoteModify) {
         this.mServer = null;
       }
       for (Iterator localIterator = this.mIdArray.iterator(); localIterator.hasNext();)
       {
         long id = ((Long)localIterator.next()).longValue();
         DeviceSetting setting = readAssignSetting(id);
         if ((setting != null) && 
           (!serverEquals(this.mServer, setting.mServer)))
         {
           setting.mServer = this.mServer;
           saveAssignSetting(id, setting);
           if (this.mRemoteModify) {
             sendNotifyToDevice(id);
           }
         }
       }
       JsonUtils.writeSuccess(this.servletResponse);
     }
     catch (Exception ex)
     {
       JsonUtils.writeError(this.servletResponse);
       log.error("#DEV_SETTING#: [server]exception: " + ex.getMessage(), ex);
     }
   }
   
   private boolean validIpAddressConfig(IpAddressConfig config)
   {
     return config != null;
   }
   
   private void putNetwork(NetworkConfig network, Class<? extends NetworkConfig> clazz)
     throws Exception
   {
     if ((clazz == null) || (
       (network != null) && (!network.getClass().equals(clazz)))) {
       throw new Exception("invalid parameter");
     }
     if ((this.mRemoteModify) && (this.mServer != null) && (
       (StringUtils.isBlank(this.mServer.mServerAddress)) || 
       (this.mServer.mDownloadPort <= 0) || (this.mServer.mDownloadPort > 65535) || 
       (this.mServer.mControlPort <= 0) || (this.mServer.mControlPort > 65535) || 
       (this.mServer.mDownloadPort == this.mServer.mControlPort))) {
       throw new Exception("invalid server config:" + this.mServer);
     }
     if (!this.mRemoteModify)
     {
       this.mServer = null;
       network = null;
     }
     for (Iterator localIterator = this.mIdArray.iterator(); localIterator.hasNext();)
     {
       long id = ((Long)localIterator.next()).longValue();
       DeviceSetting setting = readAssignSetting(id);
       if ((setting != null) && (
         (network != null) || (setting.mNetwork == null) || 
         (clazz.isInstance(setting.mNetwork))))
       {
         int changed = 0;
         if (!networkEquals(network, setting.mNetwork))
         {
           setting.mNetwork = network;
           changed++;
         }
         if ((this.mServer != null) && (!serverEquals(this.mServer, setting.mServer)))
         {
           setting.mServer = this.mServer;
           changed++;
         }
         if ((setting.mAp != null) && (setting.mAp.validAp()) && 
           (setting.mNetwork != null) && ((setting.mNetwork instanceof WifiConfig)))
         {
           ApConfig ap = new ApConfig();
           ap.setSsid("");
           setting.mAp = ap;
           changed++;
         }
         if (changed > 0)
         {
           saveAssignSetting(id, setting);
           if (this.mRemoteModify) {
             sendNotifyToDevice(id);
           }
         }
       }
     }
   }
   
   EthernetConfig mEthernet = null;
   
   public EthernetConfig getEthernet()
   {
     return this.mEthernet;
   }
   
   public void setEthernet(EthernetConfig ethernet)
   {
     this.mEthernet = ethernet;
   }
   
   public void putEthernet()
   {
     try
     {
       if ((this.mRemoteModify) && ((this.mEthernet == null) || 
         (!validIpAddressConfig(this.mEthernet))))
       {
         JsonUtils.writeError(this.servletResponse);
         log.error("#DEV_SETTING#: invalid ethernet config: " + this.mEthernet);
         return;
       }
       putNetwork(this.mEthernet, EthernetConfig.class);
       
       JsonUtils.writeSuccess(this.servletResponse);
     }
     catch (Exception ex)
     {
       JsonUtils.writeError(this.servletResponse);
       log.error("#DEV_SETTING#: [ethernet]" + ex.getMessage(), ex);
     }
   }
   
   WifiConfig mWifi = null;
   
   public WifiConfig getWifi()
   {
     return this.mWifi;
   }
   
   public void setWifi(WifiConfig wifi)
   {
     this.mWifi = wifi;
   }
   
   public void putWifi()
   {
     try
     {
       if ((this.mRemoteModify) && ((this.mWifi == null) || 
         (StringUtils.isBlank(this.mWifi.mSSID)) || 
         (!validIpAddressConfig(this.mWifi))))
       {
         JsonUtils.writeError(this.servletResponse);
         log.error("#DEV_SETTING#: invalid wifi config: " + this.mWifi);
         return;
       }
       putNetwork(this.mWifi, WifiConfig.class);
       
       JsonUtils.writeSuccess(this.servletResponse);
     }
     catch (Exception ex)
     {
       JsonUtils.writeError(this.servletResponse);
       log.error("#DEV_SETTING#: [wifi]" + ex.getMessage(), ex);
     }
   }
   
   Phone3GConfig m3G = null;
   
   public Phone3GConfig getPhone3G()
   {
     return this.m3G;
   }
   
   public void setPhone3G(Phone3GConfig _3g)
   {
     this.m3G = _3g;
   }
   
   public void put3G()
   {
     try
     {
       if ((this.mRemoteModify) && (this.m3G == null))
       {
         JsonUtils.writeError(this.servletResponse);
         log.error("#DEV_SETTING#: invalid 3g config: " + this.m3G);
         return;
       }
       putNetwork(this.m3G, Phone3GConfig.class);
       
       JsonUtils.writeSuccess(this.servletResponse);
     }
     catch (Exception ex)
     {
       JsonUtils.writeError(this.servletResponse);
       log.error("#DEV_SETTING#: [3g]" + ex.getMessage(), ex);
     }
   }
   
   PPPOEConfig mPppoe = null;
   
   public PPPOEConfig getPppoe()
   {
     return this.mPppoe;
   }
   
   public void setPppoe(PPPOEConfig pppoe)
   {
     this.mPppoe = pppoe;
   }
   
   public void putPPPoE()
   {
     try
     {
       if ((this.mRemoteModify) && ((this.mPppoe == null) || 
         (StringUtils.isBlank(this.mPppoe.mUserName)) || 
         (this.mPppoe.mPassword == null)))
       {
         JsonUtils.writeError(this.servletResponse);
         log.error("#DEV_SETTING#: invalid pppoe config: " + this.mPppoe);
         return;
       }
       putNetwork(this.mPppoe, PPPOEConfig.class);
       
       JsonUtils.writeSuccess(this.servletResponse);
     }
     catch (Exception ex)
     {
       JsonUtils.writeError(this.servletResponse);
       log.error("#DEV_SETTING#: [pppoe]" + ex.getMessage(), ex);
     }
   }
   
   private String mPassword = null;
   private ExpertConfig mExpert;
   private ApConfig mAp;
   
   public String getPassword()
   {
     return this.mPassword;
   }
   
   public void setPassword(String password)
   {
     this.mPassword = password;
   }
   
   public void putPassword()
   {
     try
     {
       if ((this.mRemoteModify) && ((this.mPassword == null) || (
         (this.mPassword.length() != 0) && (!this.mPassword.matches("^[0-9]{6}$")))))
       {
         JsonUtils.writeError(this.servletResponse);
         log.error("#DEV_SETTING#: invalid password config: " + this.mPassword);
         return;
       }
       if (!this.mRemoteModify) {
         this.mPassword = null;
       }
       for (Iterator localIterator = this.mIdArray.iterator(); localIterator.hasNext();)
       {
         long id = ((Long)localIterator.next()).longValue();
         DeviceSetting setting = readAssignSetting(id);
         if ((setting != null) && 
           (!passwordEquals(this.mPassword, setting.mPassword)))
         {
           setting.mPassword = this.mPassword;
           saveAssignSetting(id, setting);
           if (this.mRemoteModify) {
             sendNotifyToDevice(id);
           }
         }
       }
       JsonUtils.writeSuccess(this.servletResponse);
     }
     catch (Exception ex)
     {
       JsonUtils.writeError(this.servletResponse);
       log.error("#DEV_SETTING#: [password]exception: " + ex.getMessage(), ex);
     }
   }
   
   public ExpertConfig getAdvanced()
   {
     return this.mExpert;
   }
   
   public void setAdvanced(ExpertConfig advanced)
   {
     this.mExpert = advanced;
   }
   
   public void putAdvanced()
   {
     try
     {
       if ((this.mRemoteModify) && ((this.mExpert == null) || (!this.mExpert.validTimeZone()) || 
         (!this.mExpert.validRotation()) || (!this.mExpert.validStorage())))
       {
         JsonUtils.writeError(this.servletResponse);
         log.error("#DEV_SETTING#: invalid advanced config: " + this.mExpert);
         return;
       }
       if (!this.mRemoteModify) {
         this.mExpert = null;
       }
       for (Iterator localIterator = this.mIdArray.iterator(); localIterator.hasNext();)
       {
         long id = ((Long)localIterator.next()).longValue();
         DeviceSetting setting = readAssignSetting(id);
         if ((setting != null) && 
           (!expertEquals(this.mExpert, setting.mExpert)))
         {
           setting.mExpert = this.mExpert;
           saveAssignSetting(id, setting);
           if (this.mRemoteModify) {
             sendNotifyToDevice(id);
           }
         }
       }
       JsonUtils.writeSuccess(this.servletResponse);
     }
     catch (Exception ex)
     {
       JsonUtils.writeError(this.servletResponse);
       log.error("#DEV_SETTING#: [advanced]exception: " + ex.getMessage(), ex);
     }
   }
   
   public ApConfig getAp()
   {
     return this.mAp;
   }
   
   public void setAp(ApConfig ap)
   {
     this.mAp = ap;
   }
   
   public void putAp()
   {
     try
     {
       if ((this.mRemoteModify) && (this.mAp == null))
       {
         JsonUtils.writeError(this.servletResponse);
         log.error("#DEV_SETTING#: invalid ap config: " + this.mAp);
         return;
       }
       if (!this.mRemoteModify) {
         this.mAp = null;
       }
       for (Iterator localIterator = this.mIdArray.iterator(); localIterator.hasNext();)
       {
         long id = ((Long)localIterator.next()).longValue();
         DeviceSetting setting = readAssignSetting(id);
         if ((setting != null) && 
           (!apEquals(this.mAp, setting.mAp)))
         {
           setting.mAp = this.mAp;
           if ((setting.mAp != null) && (setting.mAp.validAp()) && 
             (setting.mNetwork != null) && ((setting.mNetwork instanceof WifiConfig)))
           {
             EthernetConfig eth = new EthernetConfig();
             eth.setDhcp(true);
             setting.mNetwork = eth;
           }
           saveAssignSetting(id, setting);
           if (this.mRemoteModify) {
             sendNotifyToDevice(id);
           }
         }
       }
       JsonUtils.writeSuccess(this.servletResponse);
     }
     catch (Exception ex)
     {
       JsonUtils.writeError(this.servletResponse);
       log.error("#DEV_SETTING#: [ap]exception: " + ex.getMessage(), ex);
     }
   }
   
   public static String deviceRequest(long devId, InputStream configStream)
     throws Exception
   {
     DeviceSetting current = new DeviceSetting();
     current.parseFromStream(configStream);
     saveCurrentSetting(devId, current);
     
     DeviceSetting assing = readAssignSetting(devId);
/* ;00:    */     
/* ;01:866 */     return assing.convetToString();
/* ;02:    */   }
/* ;03:    */   
/* ;04:    */   public static void hardwareInformation(long devId, long totalSpace, long freeSpace, int screenWidth, int screenHeight, String currentNetwork, String currentStorage, int volume, String licenseType)
/* ;05:    */     throws Exception
/* ;06:    */   {
/* ;07:875 */     if ((totalSpace <= 0L) || (freeSpace < 0L) || 
/* ;08:876 */       (totalSpace < freeSpace)) {
/* ;09:877 */       throw new Exception("Invalid space parameter[" + freeSpace + "/" + totalSpace + "]");
/* ;10:    */     }
/* ;11:880 */     if ((screenWidth <= 0) || (screenHeight <= 0)) {
/* ;12:881 */       throw new Exception("Invalid screen parameter[" + screenWidth + "x" + screenHeight + "]");
/* ;13:    */     }
/* ;14:883 */     String text = "";
/* ;15:884 */     text = text + totalSpace + "\n";
/* ;16:885 */     text = text + freeSpace + "\n";
/* ;17:886 */     text = text + screenWidth + "\n";
/* ;18:887 */     text = text + screenHeight + "\n";
/* ;19:888 */     text = text + StringUtils.trimToEmpty(currentNetwork) + "\n";
/* ;20:889 */     text = text + volume + "\n";
/* ;21:890 */     text = text + StringUtils.trimToEmpty(currentStorage) + "\n";
/* ;22:891 */     text = text + StringUtils.trimToEmpty(licenseType) + "\n";
/* ;23:    */     
/* ;24:893 */     FileOutputStream out = null;
/* ;25:    */     try
/* ;26:    */     {
/* ;27:895 */       String path = CONFIG_ROOT + File.separatorChar + 
/* ;28:896 */         String.format("H_%016X.text", new Object[] { Long.valueOf(devId) });
/* ;29:897 */       File file = new File(path);
/* ;30:898 */       file.getParentFile().mkdirs();
/* ;31:899 */       out = new FileOutputStream(file);
/* ;32:900 */       byte[] bytes = text.getBytes("UTF-8");
/* ;33:901 */       out.write(bytes);
/* ;34:    */     }
/* ;35:    */     catch (Exception e)
/* ;36:    */     {
/* ;37:903 */       log.error("#DEV_SETTING#: write hardware information faild: " + e.getMessage(), e);
/* ;38:905 */       if (out != null) {
/* ;39:    */         try
/* ;40:    */         {
/* ;41:905 */           out.close();
/* ;42:    */         }
/* ;43:    */         catch (Exception localException1) {}
/* ;44:    */       }
/* ;45:    */     }
/* ;46:    */     finally
/* ;47:    */     {
/* ;48:905 */       if (out != null) {
/* ;49:    */         try
/* ;50:    */         {
/* ;51:905 */           out.close();
/* ;52:    */         }
/* ;53:    */         catch (Exception localException2) {}
/* ;54:    */       }
/* ;55:    */     }
/* ;56:    */   }
/* ;57:    */   
/* ;58:    */   private static long parseLong(String text)
/* ;59:    */   {
/* ;60:    */     try
/* ;61:    */     {
/* ;62:911 */       return (text == null) || ((text = text.trim()).length() <= 0) ? 
/* ;63:912 */         0L : Long.parseLong(text, 10);
/* ;64:    */     }
/* ;65:    */     catch (Exception e) {}
/* ;66:914 */     return 0L;
/* ;67:    */   }
/* ;68:    */   
/* ;69:    */   private static int parseInt(String text)
/* ;70:    */   {
/* ;71:    */     try
/* ;72:    */     {
/* ;73:920 */       return (text == null) || ((text = text.trim()).length() <= 0) ? 
/* ;74:921 */         0 : Integer.parseInt(text, 10);
/* ;75:    */     }
/* ;76:    */     catch (Exception e) {}
/* ;77:923 */     return 0;
/* ;78:    */   }
/* ;79:    */   
/* ;80:    */   public static void readHardwareInformation(Terminal terminal)
/* ;81:    */   {
/* ;82:929 */     BufferedReader reader = null;
/* ;83:    */     try
/* ;84:    */     {
/* ;85:931 */       String path = CONFIG_ROOT + File.separatorChar + 
/* ;86:932 */         String.format("H_%016X.text", new Object[] { Long.valueOf(terminal.getDeviceId()) });
/* ;87:933 */       File file = new File(path);
/* ;88:934 */       if ((!file.exists()) || (file.isDirectory())) {
/* ;89:935 */         return;
/* ;90:    */       }
/* ;91:937 */       reader = new BufferedReader(new FileReader(file));
/* ;92:    */       
/* ;93:939 */       terminal.setTotalSpace(parseLong(reader.readLine()));
/* ;94:940 */       terminal.setFreeSpace(parseLong(reader.readLine()));
/* ;95:941 */       terminal.setScreenWidth(parseInt(reader.readLine()));
/* ;96:942 */       terminal.setScreenHeigth(parseInt(reader.readLine()));
/* ;97:943 */       terminal.setNetworkType(reader.readLine().trim());
/* ;98:944 */       terminal.setVolume(parseInt(reader.readLine()));
/* ;99:945 */       terminal.setStorage(reader.readLine().trim());
/* <00:946 */       terminal.setLicenseType(reader.readLine().trim());
/* <01:    */     }
/* <02:    */     catch (Exception e)
/* <03:    */     {
/* <04:948 */       log.error("#DEV_SETTING#:read hardware information faild: " + e.getMessage(), e);
/* <05:950 */       if (reader != null) {
/* <06:    */         try
/* <07:    */         {
/* <08:950 */           reader.close();
/* <09:    */         }
/* <10:    */         catch (Exception localException2) {}
/* <11:    */       }
/* <12:    */     }
/* <13:    */     finally
/* <14:    */     {
/* <15:950 */       if (reader != null) {
/* <16:    */         try
/* <17:    */         {
/* <18:950 */           reader.close();
/* <19:    */         }
/* <20:    */         catch (Exception localException3) {}
/* <21:    */       }
/* <22:    */     }
/* <23:    */   }
/* <24:    */ }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.TerminalSettingAction
 * JD-Core Version:    0.7.0.1
 */