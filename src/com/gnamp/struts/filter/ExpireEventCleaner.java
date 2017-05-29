 package com.gnamp.struts.filter;
 
 import com.gnamp.server.db.DbShell;
 import java.util.Date;
 import java.util.HashMap;
 import org.apache.log4j.Logger;
 
 public class ExpireEventCleaner
   extends Thread
 {
   private static final Logger log = Logger.getLogger(ExpireEventCleaner.class);
   private volatile boolean mThreadRun = true;
   
   public void stopThread()
   {
     this.mThreadRun = false;
   }
   
   public void run()
   {
     long interval = 600000L;
     
     log.error("[EVENT_CLEANER] ExpireEventCleaner[" + new Date() + "] run");
     
     long nextTime = 0L;
     long curTime = 0L;
     while (this.mThreadRun)
     {
       curTime = System.currentTimeMillis();
       if (curTime >= nextTime) {
         try
         {
           deleteExpireEventLog();
           deleteExceedThresholdEventLog();
           nextTime = curTime + interval;
         }
         catch (Exception e)
         {
           nextTime = curTime + 600000L;
           log.error("[EVENT_CLEANER] [Run]deleteExpireEventLog failed: " + e.getMessage(), e);
         }
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
         } while (slept < 10000L);
       }
       catch (InterruptedException localInterruptedException) {}
     }
   }
   
   private void deleteExpireEventLog()
     throws Exception
   {
     DbShell shell = new DbShell();
     String sql = "DELETE FROM tb_terminal_event WHERE DATE_TIME<DATE_SUB(CURRENT_DATE(), INTERVAL 1 MONTH)";
     
     shell.delete(sql, new HashMap());
   }
   
   private void deleteExceedThresholdEventLog()
     throws Exception
   {
     int THRESHOLD = 200000;
     DbShell shell = new DbShell();
     
     String sql = "";
     sql = "SELECT COUNT(*) FROM tb_terminal_event";
     int rows = shell.queryForInt(sql, new HashMap());
     if (rows <= 200000) {
       return;
     }
     sql = 
       "DELETE FROM tb_terminal_event ORDER BY DATE_TIME ASC LIMIT " + (rows - 200000);
     
     shell.delete(sql, new HashMap());
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.filter.ExpireEventCleaner
 * JD-Core Version:    0.7.0.1
 */