 package com.gnamp.webservice;
 
 import com.gnamp.server.Storage;
 import com.gnamp.server.handle.DemandTaskHandle;
 import com.gnamp.server.handle.GroupHandle;
 import com.gnamp.server.handle.LoginHandle;
 import com.gnamp.server.handle.LoopTaskHandle;
 import com.gnamp.server.handle.PluginTaskHandle;
 import com.gnamp.server.handle.TerminalHandle;
 import com.gnamp.server.model.Customer;
 import com.gnamp.server.model.DemandTask;
 import com.gnamp.server.model.Group;
 import com.gnamp.server.model.LoopTask;
 import com.gnamp.server.model.PluginTask;
 import com.gnamp.server.model.Terminal;
 import com.gnamp.server.model.User;
 import com.gnamp.server.utils.DomUtils;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.w3c.dom.Attr;
 import org.w3c.dom.Document;
 import org.w3c.dom.NodeList;
 
 public class TaskLoad
 {
   public LoopTask[] GetTaskDownLoadList(String szDomain, String szUid, String szPwd)
   {
     Customer cust = LoginHandle.getCustomer(szDomain);
     if (cust == null) {
       return null;
     }
     User custuser = null;
     try
     {
       custuser = LoginHandle.Authenticate(cust.getCstmId(), szUid, szPwd);
       if (custuser == null) {
         return null;
       }
     }
     catch (Exception e)
     {
       return null;
     }
     LoginUser lu = new LoginUser(custuser, cust);
     
     LoopTaskHandle taskhandle = new LoopTaskHandle(lu);
     
     LoopTask[] arraytask = null;
     
     List<LoopTask> p_List = null;
     if (taskhandle != null) {
       p_List = taskhandle.readAll();
     }
     if (p_List != null)
     {
       arraytask = new LoopTask[p_List.size()];
       p_List.toArray(arraytask);
     }
     return arraytask;
   }
   
   public DemandTask[] GetDemandTaskDownLoadList(String szDomain, String szUid, String szPwd)
   {
     Customer cust = LoginHandle.getCustomer(szDomain);
     if (cust == null) {
       return null;
     }
     User custuser = null;
     try
     {
       custuser = LoginHandle.Authenticate(cust.getCstmId(), szUid, szPwd);
       if (custuser == null) {
         return null;
       }
     }
     catch (Exception e)
     {
       return null;
     }
     LoginUser lu = new LoginUser(custuser, cust);
     
     DemandTaskHandle taskhandle = new DemandTaskHandle(lu);
     
     DemandTask[] arraytask = null;
     
     List<DemandTask> p_List = null;
     if (taskhandle != null) {
       p_List = taskhandle.readAll();
     }
     if (p_List != null)
     {
       arraytask = new DemandTask[p_List.size()];
       p_List.toArray(arraytask);
     }
     return arraytask;
   }
   
   public PluginTask[] GetPlugInTaskDownLoadList(String szDomain, String szUid, String szPwd)
   {
     Customer cust = LoginHandle.getCustomer(szDomain);
     if (cust == null) {
       return null;
     }
     User custuser = null;
     try
     {
       custuser = LoginHandle.Authenticate(cust.getCstmId(), szUid, szPwd);
       if (custuser == null) {
         return null;
       }
     }
     catch (Exception e)
     {
       return null;
     }
     LoginUser lu = new LoginUser(custuser, cust);
     
     PluginTaskHandle taskhandle = new PluginTaskHandle(lu);
     
     PluginTask[] arraytask = null;
     
     List<PluginTask> p_List = null;
     if (taskhandle != null) {
       p_List = taskhandle.readAll();
     }
     if (p_List != null)
     {
       arraytask = new PluginTask[p_List.size()];
       p_List.toArray(arraytask);
     }
     return arraytask;
   }
   
   public com.gnamp.server.model.File[] GetTaskAllFiles(String szDomain, String szUid, String szPwd, int task_id)
   {
     Customer cust = LoginHandle.getCustomer(szDomain);
     if (cust == null) {
       return null;
     }
     User custuser = null;
     try
     {
       custuser = LoginHandle.Authenticate(cust.getCstmId(), szUid, szPwd);
       if (custuser == null) {
         return null;
       }
     }
     catch (Exception e)
     {
       return null;
     }
     Storage storage = new Storage(cust.getCstmId());
     
     java.io.File filelist = new java.io.File(
       storage.getFileListPath(task_id));
     if (!filelist.exists()) {
       return null;
     }
     Document filedoc = null;
     NodeList xmlNlist = null;
     
     List<com.gnamp.server.model.File> modefile = new ArrayList();
     try
     {
       filedoc = DomUtils.load(storage.getFileListPath(task_id));
       
       xmlNlist = DomUtils.selectNodes(filedoc, 
         "/config/filelist/file/@size");
       for (int i = 0; i < xmlNlist.getLength(); i++)
       {
         String value = ((Attr)xmlNlist.item(i)).getValue();
         
         long size = Long.parseLong(value);
         com.gnamp.server.model.File f = new com.gnamp.server.model.File();
         f.setSize(size);
         modefile.add(f);
       }
       if ((modefile == null) || (modefile.size() <= 0)) {
         return null;
       }
       return (com.gnamp.server.model.File[])modefile.toArray(new com.gnamp.server.model.File[0]);
     }
     catch (Exception localException1) {}finally
     {
       xmlNlist = null;
       filedoc = null;
     }
     return null;
   }
   
   public com.gnamp.server.model.File[] GetDemandTaskAllFiles(String szDomain, String szUid, String szPwd, int task_id)
   {
     Customer cust = LoginHandle.getCustomer(szDomain);
     if (cust == null) {
       return null;
     }
     User custuser = null;
     try
     {
       custuser = LoginHandle.Authenticate(cust.getCstmId(), szUid, szPwd);
       if (custuser == null) {
         return null;
       }
     }
     catch (Exception e)
     {
       return null;
     }
     Storage storage = new Storage(cust.getCstmId());
     
     java.io.File filelist = new java.io.File(
       storage.getFileListPath(task_id));
     if (!filelist.exists()) {
       return null;
     }
     Document filedoc = null;
     NodeList xmlNlist = null;
     
     List<com.gnamp.server.model.File> modefile = new ArrayList();
     try
     {
       filedoc = DomUtils.load(storage.getFileListPath(task_id));
       
       xmlNlist = DomUtils.selectNodes(filedoc, 
         "/config/filelist/file/@size");
       for (int i = 0; i < xmlNlist.getLength(); i++)
       {
         String value = ((Attr)xmlNlist.item(i)).getValue();
         
         long size = Long.parseLong(value);
         com.gnamp.server.model.File f = new com.gnamp.server.model.File();
         f.setSize(size);
         modefile.add(f);
       }
       if ((modefile == null) || (modefile.size() <= 0)) {
         return null;
       }
       return (com.gnamp.server.model.File[])modefile.toArray(new com.gnamp.server.model.File[0]);
     }
     catch (Exception localException1) {}finally
     {
       xmlNlist = null;
       filedoc = null;
     }
     return null;
   }
   
   public com.gnamp.server.model.File[] GetPlugInTaskAllFiles(String szDomain, String szUid, String szPwd, int task_id)
   {
     Customer cust = LoginHandle.getCustomer(szDomain);
     if (cust == null) {
       return null;
     }
     User custuser = null;
     try
     {
       custuser = LoginHandle.Authenticate(cust.getCstmId(), szUid, szPwd);
       if (custuser == null) {
         return null;
       }
     }
     catch (Exception e)
     {
       return null;
     }
     Storage storage = new Storage(cust.getCstmId());
     
     java.io.File filelist = new java.io.File(
       storage.getFileListPath(task_id));
     if (!filelist.exists()) {
       return null;
     }
     Document filedoc = null;
     NodeList xmlNlist = null;
     
     List<com.gnamp.server.model.File> modefile = new ArrayList();
     try
     {
       filedoc = DomUtils.load(storage.getFileListPath(task_id));
       
       xmlNlist = DomUtils.selectNodes(filedoc, 
         "/config/filelist/file/@size");
       for (int i = 0; i < xmlNlist.getLength(); i++)
       {
         String value = ((Attr)xmlNlist.item(i)).getValue();
         
         long size = Long.parseLong(value);
         com.gnamp.server.model.File f = new com.gnamp.server.model.File();
         f.setSize(size);
         modefile.add(f);
       }
       if ((modefile == null) || (modefile.size() <= 0)) {
         return null;
       }
       return (com.gnamp.server.model.File[])modefile.toArray(new com.gnamp.server.model.File[0]);
     }
     catch (Exception localException1) {}finally
     {
       xmlNlist = null;
       filedoc = null;
     }
     return null;
   }
   
   public Terminal[] GetGroupAllTerminals(String szDomain, String szUid, String szPwd, int groupid, boolean child)
   {
     Customer cust = LoginHandle.getCustomer(szDomain);
     if (cust == null) {
       return null;
     }
     User custuser = null;
     try
     {
       custuser = LoginHandle.Authenticate(cust.getCstmId(), szUid, szPwd);
       if (custuser == null) {
         return null;
       }
     }
     catch (Exception e)
     {
       return null;
     }
     LoginUser lu = new LoginUser(custuser, cust);
     
     TerminalHandle terminalhandle = new TerminalHandle(lu);
     
     Terminal[] arrayterminal = null;
     List<Terminal> t_List = null;
     if (terminalhandle != null)
     {
       String queryTable = "SELECT * FROM vw_terminal_detail WHERE CSTM_ID=:CSTM_ID " + (
       
 
 
         groupid == 0 ? " AND GROUP_ID IS NULL " : child ? " AND GROUP_ID IN(SELECT :GROUP_ID AS GROUP_ID UNION SELECT GROUP_ID FROM tb_group_tree WHERE PARENT_ID=:GROUP_ID) " : groupid == 0 ? "" : 
         " AND GROUP_ID=:GROUP_ID ");
       
       String query = "SELECT f.CSTM_ID AS CSTM_ID, DEV_ID, f.NAME AS NAME, f.DESCP AS DESCP, f.GROUP_ID AS GROUP_ID, c.NAME AS GROUP_NAME, f.CREATE_TIME AS CREATE_TIME, f.CREATE_USER AS CREATE_USER, CITY_ID, CITY_NAME, ASSIGN_KERNEL, ASSIGN_APP, CUR_KERNEL, CUR_APP, ASSIGN_CONFIG_ID, ASSIGN_CONFIG_NAME, ASSIGN_CONFIG_VERSION, CUR_CONFIG_ID, CUR_CONFIG_NAME, CUR_CONFIG_VERSION, ASSIGN_LOOPTASK_ID, ASSIGN_LOOPTASK_NAME, ASSIGN_LOOPTASK_VERSION, ASSIGN_LOOPFILE_VERSION, ASSIGN_LOOPPLAY_VERSION, ASSIGN_LOOPSTRATEGY_VERSION, CUR_LOOPTASK_ID, CUR_LOOPTASK_NAME, CUR_LOOPTASK_VERSION, CUR_LOOPFILE_VERSION, CUR_LOOPPLAY_VERSION, CUR_LOOPSTRATEGY_VERSION, ASSIGN_DEMANDTASK_ID, ASSIGN_DEMANDTASK_NAME, ASSIGN_DEMANDTASK_VERSION, ASSIGN_DEMANDFILE_VERSION, ASSIGN_DEMANDPLAY_VERSION, CUR_DEMANDTASK_ID, CUR_DEMANDTASK_NAME, CUR_DEMANDTASK_VERSION, CUR_DEMANDFILE_VERSION, CUR_DEMANDPLAY_VERSION, ASSIGN_PLUGINTASK_ID, ASSIGN_PLUGINTASK_NAME, ASSIGN_PLUGINTASK_VERSION, ASSIGN_PLUGINFILE_VERSION, ASSIGN_PLUGINPLAY_VERSION, CUR_PLUGINTASK_ID, CUR_PLUGINTASK_NAME, CUR_PLUGINTASK_VERSION, CUR_PLUGINFILE_VERSION, CUR_PLUGINPLAY_VERSION, REST_SCHEDULE, STANDBY_SCHEDULE, CAPTURE_SCHEDULE, DEMAND_SCHEDULE, SCHEDULE_VERSION, SUBTITLE, SUBTITLE_VERSION, CUR_SUBTITLE_VERSION, ONLINE_STATE, LOGON_TIME, LOGOFF_TIME, DOWNLOAD_TOTAL, DOWNLOAD_FINISHED, DOWNLOAD_TYPE, POWERON, POWEROFF, ALIVE_INTERVAL, LAST_ALIVE, LAST_PATROL, LAST_DOWNLOAD,  KERNEL_UPDATED, APP_UPDATED, CONFIG_UPDATED, LOOPTASK_UPDATED, DEMANDTASK_UPDATED, PLUGINTASK_UPDATED, SUBTITLE_UPDATED FROM (" + 
       
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
         queryTable + 
         ") as f " + 
         " LEFT JOIN tb_group as c on f.CSTM_ID=c.CSTM_ID AND f.GROUP_ID=c.GROUP_ID ";
       
       Map<String, Object> param = new HashMap();
       param.put("CSTM_ID", Integer.valueOf(cust.getCstmId()));
       if (groupid != 0) {
         param.put("GROUP_ID", Integer.valueOf(groupid));
       }
       t_List = terminalhandle.readAll(query, param);
     }
     if (t_List != null)
     {
       arrayterminal = new Terminal[t_List.size()];
       t_List.toArray(arrayterminal);
     }
     return arrayterminal;
   }
   
   public Group[] GetAllGroups(String szDomain, String szUid, String szPwd)
   {
     Customer cust = LoginHandle.getCustomer(szDomain);
     if (cust == null) {
       return null;
     }
     User custuser = null;
     try
     {
       custuser = LoginHandle.Authenticate(cust.getCstmId(), szUid, szPwd);
       if (custuser == null) {
         return null;
       }
     }
     catch (Exception e)
     {
       return null;
     }
     LoginUser lu = new LoginUser(custuser, cust);
     
     GroupHandle grouphandle = new GroupHandle(lu);
     
     Group[] arraygrup = null;
     List<Group> t_List = null;
     if (grouphandle != null) {
       t_List = grouphandle.readChildren(0);
     }
     if (t_List != null)
     {
       arraygrup = new Group[t_List.size()];
       t_List.toArray(arraygrup);
     }
     return arraygrup;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.webservice.TaskLoad
 * JD-Core Version:    0.7.0.1
 */