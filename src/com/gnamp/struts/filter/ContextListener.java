 package com.gnamp.struts.filter;
 
 import com.gnamp.server.Storage;
 import com.gnamp.struts.utils.IniFile;
 import common.Logger;
 import java.io.File;
 import java.io.IOException;
 import java.net.URL;
 import java.net.URLDecoder;
 import java.util.ArrayList;
 import java.util.List;
 import javax.servlet.ServletContextEvent;
 import javax.servlet.ServletContextListener;
 
 public class ContextListener
   implements ServletContextListener
 {
   private static final Logger log = Logger.getLogger(ContextListener.class);
   public static final Boolean DEBUG_MODE = Boolean.FALSE;
   private static final List<ContextTask> CONTEXTS = new ArrayList();
   
   public static boolean addContext(ContextTask context)
   {
     return CONTEXTS.add(context);
   }
   
   public static boolean remove(ContextTask context)
   {
     return CONTEXTS.remove(context);
   }
   
   public static void publish()
   {
     for (ContextTask c : CONTEXTS) {
       try
       {
         c.execute();
       }
       catch (Exception e)
       {
         log.error(e.getMessage(), e);
       }
     }
   }
   
   public static String product = "";
   public static String language = "";
   public static String version = "";
   public static String Alcohol = "";
   public static boolean WatcherEnable = false;
   public static boolean ShowBuiltinResource = true;
   
   public void contextDestroyed(ServletContextEvent event)
   {
     CONTEXTS.clear();
   }
   
   public void contextInitialized(ServletContextEvent event)
   {
     Context.EVENT = event;
     
 
 
 
 
 
 
     addContext(new ContextTask()
     {
       public void execute()
       {
         String path = "";
         try
         {
           String classDirectory = 
             Storage.class.getClassLoader().getResource("/").getPath();
           classDirectory = URLDecoder.decode(classDirectory, "UTF-8");
           File f = new File(classDirectory);
           f = f.getParentFile();
           path = f.getAbsolutePath() + File.separatorChar + "Product.ini";
         }
         catch (Exception ex)
         {
           path = "";
         }
         try
         {
           ContextListener.product = IniFile.getProfileString(path, "InstallSettings", "Product", "IPUB");
         }
         catch (IOException e)
         {
           ContextListener.product = "IPUB";
         }
         ContextListener.product = ContextListener.product.trim();
         try
         {
           ContextListener.language = IniFile.getProfileString(path, "InstallSettings", "Language", "");
         }
         catch (IOException e)
         {
           ContextListener.language = "";
         }
         ContextListener.language = ContextListener.language.trim();
         try
         {
           ContextListener.version = IniFile.getProfileString(path, "InstallSettings", "Version", "V5.6");
         }
         catch (IOException e)
         {
           ContextListener.version = "V5.6";
         }
         ContextListener.version = ContextListener.version.trim();
         try
         {
           ContextListener.Alcohol = IniFile.getProfileString(path, "InstallSettings", "Alcohol", "FALSE");
         }
         catch (IOException e)
         {
           ContextListener.Alcohol = "FALSE";
         }
         ContextListener.Alcohol = ContextListener.Alcohol.trim();
         
         String text = "SHOW";
         try
         {
           text = IniFile.getProfileString(path, "InstallSettings", "ShowBuiltIn", "SHOW");
         }
         catch (IOException e)
         {
           text = "SHOW";
         }
         text = text == null ? "SHOW" : text.trim();
         ContextListener.ShowBuiltinResource = (!text.equalsIgnoreCase("false")) && 
           (!text.equalsIgnoreCase("hide")) && (!text.equalsIgnoreCase("hidden"));
       }
     });
     addContext(new ContextTask()
     {
       public void execute()
       {
         new YahooWeather().start();
       }
     });
     addContext(new ContextInternalImpl());
     
     addContext(new ContextTask()
     {
       public void execute()
       {
         EmailHelper.initialize();
         ContextListener.WatcherEnable = EmailHelper.valide();
         if (!ContextListener.WatcherEnable)
         {
           ContextListener.log.error("[EMAIL] Invalid config: " + EmailHelper.configText());
           return;
         }
         new TerminalWatcher().start();
       }
     });
     addContext(new ContextTask()
     {
       public void execute()
       {
         new ExpireEventCleaner().start();
       }
     });
     publish();
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.filter.ContextListener
 * JD-Core Version:    0.7.0.1
 */