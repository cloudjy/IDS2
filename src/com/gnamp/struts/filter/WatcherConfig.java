 package com.gnamp.struts.filter;
 
 import com.gnamp.server.Storage;
 import com.gnamp.struts.utils.IniFile;
 import java.io.File;
 import java.io.IOException;
 import java.net.URL;
 import java.net.URLDecoder;
 
 final class WatcherConfig
 {
   public static String configPath()
   {
     String path = "";
     try
     {
       String classDirectory = 
         Storage.class.getClassLoader().getResource("/").getPath();
       classDirectory = URLDecoder.decode(classDirectory, "UTF-8");
       File f = new File(classDirectory);
       f = f.getParentFile();
       f = f.getParentFile();
       f = f.getParentFile();
       f = f.getParentFile();
       f = f.getParentFile();
       path = f.getAbsolutePath() + File.separatorChar + "monitor.ini";
     }
     catch (Exception ex)
     {
       path = "";
     }
     return path;
   }
   
   private static String readString(String file, String section, String variable, String defaultValue)
   {
     String text = "";
     try
     {
       text = IniFile.getProfileString(file, section, variable, "");
     }
     catch (IOException e)
     {
       text = "";
     }
     return (text != null) && ((text = text.trim()).length() > 0) ? text : defaultValue;
   }
   
   private static int readInteger(String file, String section, String variable, int defaultValue)
   {
     String text = "";
     try
     {
       text = IniFile.getProfileString(file, section, variable, "");
     }
     catch (IOException e)
     {
       text = "";
     }
     try
     {
       return (text != null) && ((text = text.trim()).length() > 0) ? 
         Integer.parseInt(text) : defaultValue;
     }
     catch (Exception e) {}
     return defaultValue;
   }
   
   public static int readMonitorInterval()
   {
     return readInteger(configPath(), "Monitor", "Interval", 180);
   }
   
   public static String readSmtpHost()
   {
     return readString(configPath(), "SendEmail", "Smtp", "");
   }
   
   public static String readSendEmail()
   {
     return readString(configPath(), "SendEmail", "Email", "");
   }
   
   public static String readSmtpUser()
   {
     return readString(configPath(), "SendEmail", "User", "");
   }
   
   public static String readSmtpPassowrd()
   {
     return readString(configPath(), "SendEmail", "Password", "");
   }
   
   public static int readSmtpPort()
   {
     int port = readInteger(configPath(), "SendEmail", "Port", 25);
     return (port >= 0) || (port <= 65535) ? port : 25;
   }
   
   public static String readRecvEmail()
   {
     return readString(configPath(), "RecvEmail", "Email", "");
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.filter.WatcherConfig
 * JD-Core Version:    0.7.0.1
 */