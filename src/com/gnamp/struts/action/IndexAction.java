 package com.gnamp.struts.action;
 
 import com.gnamp.server.Storage;
 import com.gnamp.server.handle.DemandTaskHandle;
 import com.gnamp.server.handle.LoopTaskHandle;
 import com.gnamp.server.handle.PluginTaskHandle;
 import com.gnamp.server.handle.TerminalHandle;
 import com.gnamp.struts.utils.IniFile;
 import com.gnamp.struts.utils.JsonUtils;
 import com.gnamp.struts.vo.IndexBean;
 import common.Logger;
 import java.io.File;
 import java.io.IOException;
 import java.io.OutputStream;
 import java.net.Socket;
 import java.net.URL;
 import java.net.URLDecoder;
 
 public class IndexAction
   extends BaseAction
 {
   public IndexAction()
   {
     if ((mDevicePort == -1) || (mWebPort == -1)) {
       readPort();
     }
   }
   
   TerminalHandle mTerminalHandle = null;
   
   public void getTerminalCount() {}
   
   public TerminalHandle getTerminalhandle()
   {
     return this.mTerminalHandle = new TerminalHandle(this);
   }
   
   LoopTaskHandle mLoopTaskHandle = null;
   
   public LoopTaskHandle getLoopTaskHandle()
   {
     return this.mLoopTaskHandle = new LoopTaskHandle(this);
   }
   
   DemandTaskHandle mDemandTaskHandle = null;
   
   public DemandTaskHandle getDemandTaskHandle()
   {
     return this.mDemandTaskHandle = new DemandTaskHandle(this);
   }
   
   PluginTaskHandle mPluginTaskHandle = null;
   
   public PluginTaskHandle getPluginTaskHandle()
   {
     return this.mPluginTaskHandle = new PluginTaskHandle(this);
   }
   
   public void statistics()
   {
     int terminalCount = 0;
     int onlineTerminalCount = 0;
     int taskNullTerminalCount = 0;
     int taskNotUpdateTerminalCount = 0;
     terminalCount = getTerminalhandle().getTerminalCount();
     onlineTerminalCount = getTerminalhandle().getOnlineTerminalCount();
     taskNullTerminalCount = getTerminalhandle().getTaskNullTerminalCount();
     taskNotUpdateTerminalCount = getTerminalhandle().getTaskNotUpdateTerminalCount();
     
     int loopTaskCount = 0;
     int uncheckLoopTaskCount = 0;
     loopTaskCount = getLoopTaskHandle().getTaskCount();
     uncheckLoopTaskCount = getLoopTaskHandle().getUncheckTaskCount();
     
     int pluginTaskCount = 0;
     int uncheckPluginTaskCount = 0;
     pluginTaskCount = getPluginTaskHandle().getTaskCount();
     uncheckPluginTaskCount = getPluginTaskHandle().getUncheckTaskCount();
     
 
     int demandTaskCount = 0;
     int uncheckDemandTaskCount = 0;
     demandTaskCount = getDemandTaskHandle().getTaskCount();
     uncheckDemandTaskCount = getDemandTaskHandle().getUncheckTaskCount();
     
 
     IndexBean bean = new IndexBean();
     
     bean.setTerminalCount(terminalCount);
     bean.setOnlineTerminalCount(onlineTerminalCount);
     bean.setTaskNullTerminalCount(taskNullTerminalCount);
     bean.setTaskNotUpdateTerminalCount(taskNotUpdateTerminalCount);
     bean.setLoopTaskCount(loopTaskCount);
     bean.setUncheckLoopTaskCount(uncheckLoopTaskCount);
     bean.setDemandTaskCount(demandTaskCount);
     bean.setUncheckDemandTaskCount(uncheckDemandTaskCount);
     bean.setPluginTaskCount(pluginTaskCount);
     bean.setUncheckPluginTaskCount(uncheckPluginTaskCount);
     
     JsonUtils.writeSuccess(this.servletResponse, "OK", bean);
   }
   
   public String help()
   {
     return "help";
   }
   
   private static String gnampIni()
   {
     try
     {
       String classDirectory = Storage.class.getClassLoader().getResource("/").getPath();
       classDirectory = URLDecoder.decode(classDirectory, "UTF-8");
       File f = new File(classDirectory);
       f = f.getParentFile();
       f = f.getParentFile();
       f = f.getParentFile();
       f = f.getParentFile();
       f = f.getParentFile();
       
       return f.getAbsolutePath() + File.separatorChar + "control-server" + 
         File.separatorChar + "gnamp.ini";
     }
     catch (Exception ex) {}
     return null;
   }
   
   private static int mDevicePort = -1;
   private static int mWebPort = -1;
   
   public int getDevicePort()
   {
     return mDevicePort;
   }
   
   public int getWebPort()
   {
     return mWebPort;
   }
   
   public static void readPort()
   {
     String path = gnampIni();
     if ((path != null) && ((path = path.trim()).length() > 0))
     {
       try
       {
         String devicePort = IniFile.getProfileString(path, "listenport", "device", "0");
         mDevicePort = Integer.parseInt(devicePort);
       }
       catch (Exception e)
       {
         log.error("#INDEX#: get device listen port failed : " + e.getMessage(), e);
       }
       try
       {
         String webPort = IniFile.getProfileString(path, "listenport", "web", "0");
         mWebPort = Integer.parseInt(webPort);
       }
       catch (Exception e)
       {
         log.error("#INDEX#: get web listen port failed : " + e.getMessage(), e);
       }
     }
     if ((mDevicePort < 0) || (mDevicePort > 65535)) {
       mDevicePort = 0;
     }
     if ((mWebPort < 0) || (mWebPort > 65535)) {
       mWebPort = 0;
     }
   }
   
   private static long mContorlServerTime = 0L;
   private static boolean mContorlServerAlive = false;
   
   public boolean getContorlServerAlive()
   {
     if ((mWebPort > 0) && (mWebPort <= 65535))
     {
       long offset = System.currentTimeMillis() - mContorlServerTime;
       if ((offset > 10000L) || (offset < 0L)) {
         mContorlServerAlive = contorlServerAlive(mWebPort);
       }
       return mContorlServerAlive;
     }
     return false;
   }
   
   public static boolean contorlServerAlive(int webPort)
   {
     Socket socket = null;
     OutputStream outputStream = null;
     try
     {
       socket = new Socket("127.0.0.1", webPort);
       outputStream = socket.getOutputStream();
       return outputStream != null;
     }
     catch (Exception e)
     {
       log.error("#INDEX#: connect to contorl server failed : " + e.getMessage(), e);
       return false;
     }
     finally
     {
       if (outputStream != null) {
         try
         {
           outputStream.close();
         }
         catch (IOException localIOException4) {}
       }
       if (socket != null) {
         try
         {
           socket.close();
         }
         catch (IOException localIOException5) {}
       }
     }
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.IndexAction
 * JD-Core Version:    0.7.0.1
 */