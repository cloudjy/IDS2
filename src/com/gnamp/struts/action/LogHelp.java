 package com.gnamp.struts.action;
 
 import com.gnamp.server.Storage;
 import com.opensymphony.xwork2.ActionSupport;
 import java.io.BufferedWriter;
 import java.io.Closeable;
 import java.io.File;
 import java.io.FileWriter;
 import java.io.IOException;
 import java.util.Date;
 
 public class LogHelp
   extends ActionSupport
 {
   private static final long serialVersionUID = 1L;
   
   private static void colseCloseable(Closeable closeable)
   {
     if (closeable != null) {
       try
       {
         closeable.close();
       }
       catch (IOException localIOException) {}
     }
   }
   
   private static String endWithSeparator(String path)
   {
     return path + File.separator;
   }
   
   private static void writeText(String filePath, String text)
   {
     if ((filePath == null) || ((filePath = filePath.trim()).length() == 0) || (text == null)) {
       return;
     }
     FileWriter fw = null;
     BufferedWriter bw = null;
     try
     {
       File file = new File(filePath);
       if (!file.getParentFile().exists()) {
         file.getParentFile().mkdirs();
       }
       fw = new FileWriter(filePath, true);
       bw = new BufferedWriter(fw);
       bw.write(text);
       bw.newLine();
     }
     catch (Exception localException) {}finally
     {
       colseCloseable(bw);
       colseCloseable(fw);
     }
   }
   
   public static void userEvent(int cstmId, String userName, String options, String event)
   {
     String path = endWithSeparator(Storage.getCstmBase(cstmId)) + "UserEvent.txt";
     
     String text = new Date().toLocaleString() + "|" + userName + "|" + options + "|" + event;
     
     writeText(path, text);
   }
   
   public static void pipeLog(String logDir, String dev_id, String start_time, String end_time, String result)
   {
     String path = endWithSeparator(logDir) + "pipelog.txt";
     String text = dev_id + "|" + start_time + "|" + end_time + "|" + result;
     writeText(path, text);
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.LogHelp
 * JD-Core Version:    0.7.0.1
 */