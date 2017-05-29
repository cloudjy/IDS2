 package com.gnamp.struts.filter;
 
 import com.gnamp.struts.action.TerminalSettingAction;
 import com.gnamp.struts.terminal.EmailLog;
 import com.gnamp.struts.terminal.QueryHelp;
 import com.gnamp.struts.terminal.Status;
 import com.gnamp.struts.terminal.WatcherSetting;
 import com.gnamp.terminal.config.DailyPowerConfig;
 import com.gnamp.terminal.config.DeviceSetting;
 import com.gnamp.terminal.config.LocalConfig;
 import com.gnamp.terminal.config.PowerConfig;
 import com.gnamp.terminal.config.PowerPoint;
 import com.gnamp.terminal.config.WeeklyPowerConfig;
 import java.io.BufferedReader;
 import java.io.Closeable;
 import java.io.File;
 import java.io.FileFilter;
 import java.io.FileOutputStream;
 import java.io.FileReader;
 import java.net.URL;
 import java.net.URLDecoder;
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Calendar;
 import java.util.Collection;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.apache.log4j.Logger;
 
 public class TerminalWatcher
   extends Thread
 {
   private static final Logger log = Logger.getLogger(TerminalWatcher.class);
   private static Map<Long, WatcherSetting> mSettingMap = new HashMap();
   private static Map<Long, EmailLog> mEmailLogMap = new HashMap();
   private static Map<Long, PowerConfig> mPowerConfigMap = new HashMap();
   private volatile boolean mThreadRun = true;
   
   public void stopThread()
   {
     this.mThreadRun = false;
   }
   
   public void run()
   {
     long interval = WatcherConfig.readMonitorInterval();
     interval = (interval < 60L) || (interval > 1800L) ? 300000L : interval * 1000L;
     if (!EmailHelper.valide())
     {
       log.error("[EMAIL] Invalid config: " + EmailHelper.configText());
       return;
     }
     log.error("[EMAIL] TerminalAliveMonitor[" + interval + "][" + 
       dateToString(new Date()) + "] run: " + EmailHelper.configText());
     scanWatcherDirectory();
     
     long nextTime = 0L;
     long curTime = 0L;
     while (this.mThreadRun)
     {
       curTime = System.currentTimeMillis();
       if (curTime >= nextTime)
       {
         doMonitor();
         nextTime = curTime + interval;
       }
       try
       {
         long slept = 0L;
         do
         {
           Thread.sleep(50L);
           slept += 50L;
           if (!this.mThreadRun) {
             break;
           }
         } while (slept < 2000L);
       }
       catch (InterruptedException localInterruptedException) {}
     }
   }
   
   private void doMonitor()
   {
     Collection<Long> idCollection = mSettingMap.keySet();
     
     Calendar calendar = Calendar.getInstance();
     Date now = new Date();
     
     List<Status> allStatus = QueryHelp.readAll(idCollection);
     
     List<Status> alarmStatus = new ArrayList();
     for (Status status : allStatus) {
       if ((status != null) && (0L != status.getDeviceId())) {
         if (status.getOnlineState() > 0) {
           mPowerConfigMap.remove(Long.valueOf(status.getDeviceId()));
         } else if ((offlineTimeout(now, status)) && 
           (workTimeRange(calendar, status)) && 
           (needSendEmail(now, status))) {
           alarmStatus.add(status);
         }
       }
     }
     if (alarmStatus.size() > 0)
     {
       int count = 0;
       String idString = "";
       for (Status status : alarmStatus) {
         if ((status != null) && (0L != status.getDeviceId()))
         {
           idString = 
           
 
             idString + (idString.length() > 0 ? "\r\n" : "") + status.getDeviceName() + "[" + String.format("%012X", new Object[] { Long.valueOf(status.getDeviceId()) }) + "] " + "offline time: " + dateToString(status.getLogoffTime()) + ", " + timeOffsetString(now, status.getLogoffTime()) + " ago";
           count++;
         }
       }
       String subject = "Player Offline Notification(" + dateToString(now) + ")";
       String message = "Offline player nubmer: " + count + ",\r\n" + 
         "Offline player list: \r\n" + idString;
       if ((count > 0) && (EmailHelper.sendEmail("", subject, message)))
       {
         for (Status status : alarmStatus) {
           setEmailLog(status, now);
         }
         log.error("[EMAIL][" + dateToString(now) + "] send ok: " + message);
       }
       else if (count > 0)
       {
         log.error("[EMAIL][" + dateToString(now) + "] send failed: " + message);
       }
     }
   }
   
   private boolean offlineTimeout(Date now, Status status)
   {
     WatcherSetting setting = getWatcherSetting(status.getDeviceId());
     long tolerance = setting.getMaxMinutes();
     Date offline = status.getLogoffTime();
     if ((tolerance <= 0L) || (offline == null)) {
       return false;
     }
     return now.getTime() - offline.getTime() > tolerance;
   }
   
   private boolean workTimeRange(Calendar now, Status status)
   {
     PowerConfig powerConfig = getPowerConfig(status.getDeviceId());
     if (powerConfig == null) {
       return true;
     }
     if ((powerConfig instanceof DailyPowerConfig)) {
       return inWorkTimeRange((DailyPowerConfig)powerConfig, now);
     }
     if ((powerConfig instanceof WeeklyPowerConfig)) {
       return inWorkTimeRange((WeeklyPowerConfig)powerConfig, now);
     }
     return true;
   }
   
   private boolean needSendEmail(Date now, Status status)
   {
     Date offline = status.getLogoffTime();
     if (offline == null) {
       return false;
     }
     EmailLog emailLog = getEmailLog(status.getDeviceId());
     
     Date emailoffline = emailLog.getLogoffTime();
     if ((emailoffline == null) || (emailoffline.getTime() / 1000L != offline.getTime() / 1000L)) {
       return true;
     }
     Date send = emailLog.getSendTime();
     if ((send == null) || (now.getTime() / 1000L - send.getTime() / 1000L > 1800L)) {
       return true;
     }
     return false;
   }
   
   private static String timeOffsetString(Date date1, Date date2)
   {
     if ((date1 == null) || (date2 == null)) {
       return "";
     }
     long offset = date1.getTime() - date2.getTime();
     offset /= 1000L;
     if (offset <= 0L) {
       return "";
     }
     long day = offset / 86400L;
     long hour = offset % 86400L / 3600L;
     long minute = offset % 3600L / 60L;
     
     String text = "";
     if (day > 0L) {
       text = text + day + " day";
     }
     if ((hour > 0L) || (text.length() > 0)) {
       text = text + (text.length() > 0 ? " " : "") + hour + " hour";
     }
     if ((minute > 0L) || (text.length() > 0)) {
       text = text + (text.length() > 0 ? " " : "") + minute + " minute";
     }
     return text;
   }
   
   private static int parseInt(String text)
   {
     try
     {
       return (text == null) || ((text = text.trim()).length() <= 0) ? 
         0 : Integer.parseInt(text, 10);
     }
     catch (Exception e) {}
     return 0;
   }
   
   private static String dateToString(Date date)
   {
     return date == null ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
   }
   
   private static Date parseDate(String text)
   {
     try
     {
       return (text == null) || ((text = text.trim()).length() <= 0) ? 
         null : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(text);
     }
     catch (Exception e) {}
     return null;
   }
   
   private static String parseValue(String variable, String lineString)
   {
     String[] strArray = lineString == null ? null : lineString.split("=");
     if ((strArray != null) && (strArray.length >= 2) && (strArray[0] != null))
     {
       String valueString = strArray[0].trim();
       if (valueString.equalsIgnoreCase(variable))
       {
         valueString = lineString.substring(lineString.indexOf("=") + 1);
         return valueString;
       }
     }
     return null;
   }
   
   private static void close(Closeable reader)
   {
     if (reader != null) {
       try
       {
         reader.close();
       }
       catch (Exception localException) {}
     }
   }
   
   private static final String WATCHER_ROOT = _WatcherRoot();
   
   private static String _WatcherRoot()
   {
     try
     {
       String classDirectory = TerminalWatcher.class.getClassLoader().getResource("/").getPath();
       classDirectory = URLDecoder.decode(classDirectory, "UTF-8");
       File f = new File(classDirectory);
       f = f.getParentFile();
       f = f.getParentFile();
       
       return f.getAbsolutePath() + File.separatorChar + "gnamp_work" + 
         File.separatorChar + "watcher";
     }
     catch (Exception ex) {}
     return "";
   }
   
   private static File watcherDirectory(long deviceId)
   {
     return new File(WATCHER_ROOT, String.format("%016X", new Object[] { Long.valueOf(deviceId) }));
   }
   
   private static File watcherSettingFile(long deviceId)
   {
     return new File(watcherDirectory(deviceId), "setting.ini");
   }
   
   private static File emailLogFile(long deviceId)
   {
     return new File(watcherDirectory(deviceId), "email.log");
   }
   
   public static synchronized WatcherSetting getWatcherSetting(long deviceId)
   {
     if (0L == deviceId) {
       return null;
     }
     WatcherSetting setting = null;
     if ((setting = (WatcherSetting)mSettingMap.get(Long.valueOf(deviceId))) == null)
     {
       setting = readWatcherSetting(deviceId);
       if ((setting != null) && (setting.getMaxMinutes() > 0)) {
         mSettingMap.put(Long.valueOf(deviceId), setting);
       }
     }
     return setting;
   }
   
   public static synchronized void setWatcherSetting(long deviceId, WatcherSetting setting)
   {
     if ((0L == deviceId) || (setting == null)) {
       return;
     }
     WatcherSetting o = null;
     if (((o = (WatcherSetting)mSettingMap.get(Long.valueOf(deviceId))) != null) && (o.equals(setting))) {
       return;
     }
     if (setting.getMaxMinutes() > 0)
     {
       mSettingMap.put(Long.valueOf(deviceId), setting);
       writeWatcherSetting(deviceId, setting);
     }
     else
     {
       mSettingMap.remove(Long.valueOf(deviceId));
       mEmailLogMap.remove(Long.valueOf(deviceId));
       mPowerConfigMap.remove(Long.valueOf(deviceId));
       deleteFile(watcherDirectory(deviceId));
     }
   }
   
   private static synchronized EmailLog getEmailLog(long deviceId)
   {
     if (0L == deviceId) {
       return null;
     }
     EmailLog emailLog = null;
     if ((emailLog = (EmailLog)mEmailLogMap.get(Long.valueOf(deviceId))) == null)
     {
       emailLog = readEmailLog(deviceId);
       if (emailLog != null) {
         mEmailLogMap.put(Long.valueOf(deviceId), emailLog);
       }
     }
     return emailLog;
   }
   
   private static PowerConfig getPowerConfig(long deviceId)
   {
     if (0L == deviceId) {
       return null;
     }
     PowerConfig powerConfig = null;
     if ((powerConfig = (PowerConfig)mPowerConfigMap.get(Long.valueOf(deviceId))) == null)
     {
       DeviceSetting setting = TerminalSettingAction.readCurrentSetting(deviceId);
       powerConfig = (setting != null) && (setting.mLocal != null) ? setting.mLocal.mPower : null;
       if (powerConfig != null) {
         mPowerConfigMap.put(Long.valueOf(deviceId), powerConfig);
       }
     }
     return powerConfig;
   }
   
   private static synchronized void setEmailLog(Status status, Date sendtime)
   {
     long deviceId = status == null ? 0L : status.getDeviceId();
     if ((0L == deviceId) || (status.getLogoffTime() == null)) {
       return;
     }
     EmailLog emailLog = null;
     if ((emailLog = (EmailLog)mEmailLogMap.get(Long.valueOf(deviceId))) == null) {
       emailLog = new EmailLog();
     }
     emailLog.setDeviceId(deviceId);
     emailLog.setLogoffTime(status.getLogoffTime());
     emailLog.setSendTime(sendtime != null ? sendtime : new Date());
     mEmailLogMap.put(Long.valueOf(deviceId), emailLog);
     writeEmailLog(deviceId, emailLog);
   }
   
   private static synchronized WatcherSetting readWatcherSetting(long deviceId)
   {
     WatcherSetting setting = new WatcherSetting();
     setting.setDeviceId(deviceId);
     setting.setMaxMinutes(0);
     setting.setEmail("");
     
     BufferedReader reader = null;
     try
     {
       File file = watcherSettingFile(deviceId);
       if ((!file.exists()) || (file.isDirectory()))
       {
         WatcherSetting localWatcherSetting1 = setting;return localWatcherSetting1;
       }
       reader = new BufferedReader(new FileReader(file));
       
       setting.setMaxMinutes(parseInt(parseValue("offline_max_minutes", reader.readLine())));
       setting.setEmail(parseValue("email", reader.readLine()));
     }
     catch (Exception e)
     {
       log.error("#OFFLINE_CONFIG#:read offline monitor config faild: " + e.getMessage(), e);
     }
     finally
     {
       close(reader);
     }
     return setting;
   }
   
   public static synchronized void writeWatcherSetting(long deviceId, WatcherSetting setting)
   {
     String text = "";
     text = text + "offline_max_minutes=" + setting.getMaxMinutes() + "\n";
     text = text + "email=" + setting.getEmail() + "\n";
     
     FileOutputStream out = null;
     try
     {
       File file = watcherSettingFile(deviceId);
       file.getParentFile().mkdirs();
       out = new FileOutputStream(file);
       byte[] bytes = text.getBytes("UTF-8");
       out.write(bytes);
     }
     catch (Exception e)
     {
       log.error("#OFFLINE_CONFIG#: write offline monitor config faild: " + e.getMessage(), e);
     }
     finally
     {
       close(out);
     }
   }
   
   private static EmailLog readEmailLog(long deviceId)
   {
     EmailLog emailLog = new EmailLog();
     emailLog.setDeviceId(deviceId);
     
     BufferedReader reader = null;
     try
     {
       File file = watcherSettingFile(deviceId);
       if ((!file.exists()) || (file.isDirectory()))
       {
         EmailLog localEmailLog1 = emailLog;return localEmailLog1;
       }
       reader = new BufferedReader(new FileReader(file));
       
       emailLog.setLogoffTime(parseDate(parseValue("offline", reader.readLine())));
       emailLog.setSendTime(parseDate(parseValue("send", reader.readLine())));
     }
     catch (Exception e)
     {
       log.error("#OFFLINE_CONFIG#:read offline email log faild: " + e.getMessage(), e);
     }
     finally
     {
       close(reader);
     }
     return emailLog;
   }
   
   private static void writeEmailLog(long deviceId, EmailLog emailLog)
   {
     Date offline = emailLog.getLogoffTime();
     if (offline == null) {
       return;
     }
     Date send = emailLog.getSendTime();
     if (send == null) {
       send = new Date();
     }
     String text = "";
     text = text + "offline=" + dateToString(offline) + "\n";
     text = text + "send=" + dateToString(send) + "\n";
     
     FileOutputStream out = null;
     try
     {
       File file = emailLogFile(deviceId);
       file.getParentFile().mkdirs();
       out = new FileOutputStream(file);
       byte[] bytes = text.getBytes("UTF-8");
       out.write(bytes);
     }
     catch (Exception e)
     {
       log.error("#OFFLINE_CONFIG#: write offline email log faild: " + e.getMessage(), e);
     }
     finally
     {
       close(out);
     }
   }
   
   private static void scanWatcherDirectory()
   {
     File filesDirectory = new File(WATCHER_ROOT);
     if (!filesDirectory.exists()) {
       return;
     }
     if (!filesDirectory.isDirectory())
     {
       filesDirectory.delete();
       return;
     }
     File[] allDirs = filesDirectory.listFiles(new FileFilter()
     {
       public boolean accept(File pathname)
       {
         return (pathname.isDirectory()) && (pathname.getName().matches("^[0-9a-fA-F]{12,16}$"));
       }
     });
     if ((allDirs == null) || (allDirs.length == 0)) {
       return;
     }
     for (File idDir : allDirs)
     {
       long deviceId = Long.parseLong(idDir.getName(), 16);
       if (0L != deviceId)
       {
         WatcherSetting setting = readWatcherSetting(deviceId);
         if ((setting != null) && (setting.getMaxMinutes() > 0)) {
           mSettingMap.put(Long.valueOf(deviceId), setting);
         } else {
           deleteFile(idDir);
         }
       }
     }
   }
   
   private static boolean deleteFile(File file)
   {
     if ((file == null) || (!file.exists())) {
       return true;
     }
     if (file.isDirectory())
     {
       File[] children = file.listFiles();
       if (children != null) {
         for (File f : children) {
           if (f.isFile())
           {
             if (!f.delete()) {
               return false;
             }
           }
           else if ((f.isDirectory()) && 
             (!deleteFile(f))) {
             return false;
           }
         }
       }
     }
     return file.delete();
   }
   
   boolean inWorkTimeRange(DailyPowerConfig config, Calendar calendar)
   {
     int daySecond = calendar.get(11) * 3600 + 
       calendar.get(12) * 60 + 
       calendar.get(13);
     
     boolean workingTime = true;
     if ((config != null) && (config.mPoint != null))
     {
       int dailyOn = config.mPoint.getPowerOn();
       int dailyOff = config.mPoint.getPowerOff();
       if (dailyOn < dailyOff)
       {
         if (daySecond < dailyOn) {
           workingTime = false;
         } else if (daySecond < dailyOff) {
           workingTime = true;
         } else {
           workingTime = false;
         }
       }
       else if (dailyOn > dailyOff) {
         if (daySecond < dailyOff) {
           workingTime = true;
         } else if (daySecond < dailyOn) {
           workingTime = false;
         } else {
           workingTime = true;
         }
       }
     }
     return workingTime;
   }
   
   boolean inWorkTimeRange(WeeklyPowerConfig config, Calendar calendar)
   {
     int day = calendar.get(7) - 1;
     int daySecond = calendar.get(11) * 3600 + 
       calendar.get(12) * 60 + 
       calendar.get(13);
     
     boolean workingTime = false;
     if ((config != null) && (config.mPoints != null) && (config.mPoints.length < 7))
     {
       int todayOn = config.mPoints[day].getPowerOn();
       int todayOff = config.mPoints[day].getPowerOff();
       if ((todayOn < todayOff) && (daySecond < todayOff)) {
         workingTime = true;
       }
     }
     return workingTime;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.filter.TerminalWatcher
 * JD-Core Version:    0.7.0.1
 */