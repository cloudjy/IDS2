 package com.gnamp.terminal.config;
 
 import com.gnamp.server.utils.DomUtils;
 import org.w3c.dom.Document;
 import org.w3c.dom.Element;
 import org.w3c.dom.NodeList;
 
 public class ExpertConfig
 {
   public static final int DEFAULT_TIMEZONE = 8;
   public int mScreenRotation = 0;
   public int mHeartBeat = 60;
   public int mWeatherInterval = 5;
   public int mTimeZone = 8;
   public int mPlayLogDays = 0;
   public int mNetDogs = 0;
   public boolean mPlayPoint = false;
   public boolean mMultiDevSynch = false;
   public String mStorage = "memory";
   public static final String STORAGE_SDCARD = "sdcard";
   public static final String STORAGE_MEMORY = "memory";
   public static final String STORAGE_UDISK = "udisk";
   public static final String STORAGE_HDDISK = "hddisk";
   private static final String NODE_EXPERT_ROOT = "expert";
   private static final String NODE_SCREENROTATION = "rotation";
   private static final String NODE_HEARTBEAT = "heartbeat";
   private static final String NODE_WEATHERINTERVAL = "weather_interval";
   private static final String NODE_TIMEZONE = "time_zone";
   private static final String NODE_PLAYLOGDAYS = "play_log";
   private static final String NODE_NETDOGTIMES = "net_dog";
   private static final String NODE_PLAYPOINT = "play_point";
   private static final String NODE_MULTIDEVSYNCH = "play_sync";
   private static final String NODE_STORAGE = "storage";
   private static final String PLAYLOG_OFF = "off";
   private static final String PLAYLOG_FOREVER = "forever";
   private static final String NETDOG_OFF = "off";
   private static final String PLAYPOINT_ON = "on";
   private static final String PLAYPOINT_OFF = "off";
   private static final String MULTIDEVSYNCH_ON = "on";
   private static final String MULTIDEVSYNCH_OFF = "off";
   
   public int getScreenRotation()
   {
     return this.mScreenRotation;
   }
   
   public void setScreenRotation(int screenRotation)
   {
     this.mScreenRotation = screenRotation;
   }
   
   public int getHeartBeat()
   {
     return this.mHeartBeat;
   }
   
   public void setHeartBeat(int heartBeat)
   {
     this.mHeartBeat = heartBeat;
   }
   
   public int getWeatherInterval()
   {
     return this.mWeatherInterval;
   }
   
   public void setWeatherInterval(int weatherInterval)
   {
     this.mWeatherInterval = weatherInterval;
   }
   
   public int getTimeZone()
   {
     return this.mTimeZone;
   }
   
   public void setTimeZone(int timeZone)
   {
     this.mTimeZone = timeZone;
   }
   
   public int getPlayLogDays()
   {
     return this.mPlayLogDays;
   }
   
   public void setPlayLogDays(int playLogDays)
   {
     this.mPlayLogDays = playLogDays;
   }
   
   public int getNetDogs()
   {
     return this.mNetDogs;
   }
   
   public void setNetDogs(int netDogs)
   {
     this.mNetDogs = netDogs;
   }
   
   public boolean getPlayPoint()
   {
     return this.mPlayPoint;
   }
   
   public void setPlayPoint(boolean playPoint)
   {
     this.mPlayPoint = playPoint;
   }
   
   public boolean getMultiDevSynch()
   {
     return this.mMultiDevSynch;
   }
   
   public void setMultiDevSynch(boolean mMultiDevSynch)
   {
     this.mMultiDevSynch = mMultiDevSynch;
   }
   
   public String getStorage()
   {
     return this.mStorage;
   }
   
   public void setStorage(String storage)
   {
     this.mStorage = (storage == null ? "" : storage.trim());
   }
   
   public boolean validTimeZone()
   {
     return (-12 <= this.mTimeZone) && (this.mTimeZone <= 12);
   }
   
   public boolean validRotation()
   {
     return (this.mScreenRotation == 0) || (this.mScreenRotation == 90) || 
       (this.mScreenRotation == 180) || (this.mScreenRotation == 270);
   }
   
   public boolean validStorage()
   {
     return (this.mStorage != null) && ((this.mStorage.equals("sdcard")) || 
       (this.mStorage.equals("memory")) || 
       (this.mStorage.equals("udisk")) || 
       (this.mStorage.equals("hddisk")));
   }
   
   public boolean equals(Object obj)
   {
     if (this == obj) {
       return true;
     }
     if ((obj == null) || (!(obj instanceof ExpertConfig))) {
       return false;
     }
     ExpertConfig o = (ExpertConfig)obj;
     if ((this.mScreenRotation != o.mScreenRotation) || 
       (this.mHeartBeat != o.mHeartBeat) || 
       (this.mWeatherInterval != o.mWeatherInterval) || 
       (this.mTimeZone != o.mTimeZone) || 
       (this.mPlayLogDays != o.mPlayLogDays) || 
       (this.mNetDogs != o.mNetDogs) || 
       (this.mPlayPoint != o.mPlayPoint) || 
       (this.mMultiDevSynch != o.mMultiDevSynch)) {
       return false;
     }
     if (!(this.mStorage == null ? "" : this.mStorage.trim()).equals(o.mStorage == null ? "" : o.mStorage.trim())) {
       return false;
     }
     return true;
   }
   
   public String toString()
   {
     return 
     
 
 
 
 
 
 
       "Rotation=" + this.mScreenRotation + ", " + "HeartBeat=" + this.mHeartBeat + ", " + "WeatherInterval=" + this.mWeatherInterval + ", " + "TimeZone=" + this.mTimeZone + ", " + "PlayLogDays=" + (this.mPlayLogDays == 0 ? "off" : this.mPlayLogDays < 0 ? "forever" : Integer.valueOf(this.mPlayLogDays)) + ", " + "NetDog=" + (this.mNetDogs <= 0 ? "off" : Integer.valueOf(this.mNetDogs)) + ", " + "PlayPoint=" + (this.mPlayPoint ? "on" : "off") + ", " + "MultiDevSynch=" + (this.mMultiDevSynch ? "on" : "off") + ", " + "Storage=" + this.mStorage + ".";
   }
   
   public static void fillToDOM(Element elemRoot, ExpertConfig config)
   {
     if ((elemRoot == null) || (config == null)) {
       return;
     }
     NodeList nodeList = elemRoot.getElementsByTagName("expert");
     int nodeNum = nodeList != null ? nodeList.getLength() : 0;
     for (int i = 0; i < nodeNum; i++) {
       elemRoot.removeChild(nodeList.item(i));
     }
     Element elemExpertRoot = (Element)elemRoot.appendChild(
       elemRoot.getOwnerDocument().createElement("expert"));
     
     DomUtils.putIntContent(elemExpertRoot, "rotation", config.mScreenRotation);
     DomUtils.putIntContent(elemExpertRoot, "heartbeat", config.mHeartBeat);
     DomUtils.putIntContent(elemExpertRoot, "weather_interval", config.mWeatherInterval);
     
     DomUtils.putStringContent(elemExpertRoot, "time_zone", 
       (config.mTimeZone < 0 ? "" : "+") + config.mTimeZone);
     
     DomUtils.putStringContent(elemExpertRoot, "play_log", 
     
       config.mPlayLogDays == 0 ? "off" : config.mPlayLogDays < 0 ? "forever" : Integer.toString(config.mPlayLogDays));
     DomUtils.putStringContent(elemExpertRoot, "net_dog", 
       config.mNetDogs <= 0 ? "off" : Integer.toString(config.mNetDogs));
     DomUtils.putStringContent(elemExpertRoot, "play_point", 
       config.mPlayPoint ? "on" : "off");
     DomUtils.putStringContent(elemExpertRoot, "play_sync", 
       config.mMultiDevSynch ? "on" : "off");
     
     String storage = config.mStorage == null ? "" : config.mStorage.trim();
     DomUtils.putStringContent(elemExpertRoot, "storage", storage);
   }
   
   public static ExpertConfig parseFromDOM(Element elemRoot)
   {
     if (elemRoot == null) {
       return null;
     }
     Element elemExpertRoot = (Element)DomUtils.selectSingleNode(elemRoot, "expert");
     if (elemExpertRoot == null) {
       return null;
     }
     ExpertConfig expertConfig = new ExpertConfig();
     expertConfig._parseFromDOM(elemRoot);
     return expertConfig;
   }
   
   private void _parseFromDOM(Element elemRoot)
   {
     Element elemExpertRoot = (Element)DomUtils.selectSingleNode(elemRoot, "expert");
     if (elemExpertRoot == null) {
       return;
     }
     int rotation = DomUtils.getIntContent(elemExpertRoot, "rotation", 0);
     int heartBeat = DomUtils.getIntContent(elemExpertRoot, "heartbeat", 60);
     int weatherInterval = DomUtils.getIntContent(elemExpertRoot, "weather_interval", 5);
     int timeZone = DomUtils.getIntContent(elemExpertRoot, "time_zone", 8);
     
     String textPlayLogDays = DomUtils.getStringContent(elemExpertRoot, "play_log", "off");
     textPlayLogDays = textPlayLogDays != null ? textPlayLogDays.trim() : "off";
     int playLogDays = 0;
     if (textPlayLogDays.equalsIgnoreCase("off"))
     {
       playLogDays = 0;
     }
     else if (textPlayLogDays.equalsIgnoreCase("forever"))
     {
       playLogDays = -1;
     }
     else if (textPlayLogDays.matches("\\d{1,4}"))
     {
       playLogDays = Integer.parseInt(textPlayLogDays);
       if (playLogDays > 7300) {
         playLogDays = -1;
       }
     }
     int netLogTimes = DomUtils.getIntContent(elemExpertRoot, "net_dog", 0);
     if (netLogTimes < 0) {
       netLogTimes = 0;
     }
     String textPlayPoint = DomUtils.getStringContent(elemExpertRoot, "play_point", "off");
     boolean playPoint = (textPlayPoint != null) && (textPlayPoint.trim().equalsIgnoreCase("on"));
     
     String textPlaySync = DomUtils.getStringContent(elemExpertRoot, "play_sync", "off");
     boolean playSync = (textPlaySync != null) && (textPlaySync.trim().equalsIgnoreCase("on"));
     
     String textStorage = DomUtils.getStringContent(elemExpertRoot, "storage", "sdcard");
     textStorage = textStorage == null ? "sdcard" : textStorage.trim();
     
     this.mScreenRotation = ((rotation == 0) || (rotation == 90) || 
       (rotation == 180) || (rotation == 270) ? rotation : 0);
     this.mHeartBeat = ((30 <= heartBeat) && (heartBeat < 3600) ? heartBeat : 60);
     this.mWeatherInterval = ((weatherInterval > 0) && (weatherInterval <= 9) ? 
       weatherInterval : 5);
     this.mTimeZone = ((-12 <= timeZone) && (timeZone <= 13) ? timeZone : 8);
     this.mPlayLogDays = playLogDays;
     this.mNetDogs = netLogTimes;
     this.mPlayPoint = playPoint;
     this.mMultiDevSynch = playSync;
     
     this.mStorage = textStorage;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.terminal.config.ExpertConfig
 * JD-Core Version:    0.7.0.1
 */