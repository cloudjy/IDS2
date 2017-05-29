 package com.gnamp.struts.filter;
 
 import com.gnamp.server.Storage;
 import com.gnamp.server.handle.SystemSourceHandle;
 import com.gnamp.server.model.SystemSource;
 import java.io.File;
 import java.util.List;
 import java.util.Timer;
 import java.util.TimerTask;
 
 public class ContextInternalImpl
   implements ContextTask
 {
   class InternalHelp
     extends TimerTask
   {
     InternalHelp() {}
     
     public void run()
     {
       try
       {
         
       }
       catch (Exception e)
       {
         e.printStackTrace();
       }
       try
       {
         SystemSourceHandle.updateAllRate();
       }
       catch (Exception localException1) {}
     }
   }
   
   class RssTask
     extends TimerTask
   {
     RssTask() {}
     
     public void run()
     {
       try
       {
         
       }
       catch (Exception e)
       {
         e.printStackTrace();
       }
     }
   }
   
   class RssCustomTask
     extends TimerTask
   {
     RssCustomTask() {}
     
     public void run()
     {
       try
       {
         
       }
       catch (Exception e)
       {
         e.printStackTrace();
       }
     }
   }
   
   public void execute()
   {
     Timer timer = new Timer(true);
     timer.schedule(new RssCustomTask(), 15000L, 7200000L);
     if (!Context.isMultiLanguageVersion()) {
       return;
     }
     saveDataToDB();
     generateDynamicDir();
     
 
 
     timer.schedule(new InternalHelp(), 15000L, 1800000L);
     timer.schedule(new RssTask(), 15000L, 7200000L);
   }
   
   private void generateDynamicDir()
   {
     File file = new File(Storage.getWorkRootPath() + "dynamic");
     if (!file.exists()) {
       file.mkdirs();
     }
   }
   
   private void saveDataToDB()
   {
     try
     {
       List<SystemSource> systemSources = 
         SystemSourceHandle.getTagsSystemSources(
         Context.getResourceURL("InternalTag.xml"));
       SystemSourceHandle.saveOrUpdate(systemSources);
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.filter.ContextInternalImpl
 * JD-Core Version:    0.7.0.1
 */