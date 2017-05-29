 package com.gnamp.struts.action;
 
 import com.gnamp.server.handle.TerminalHandle;
 import com.gnamp.server.model.Terminal;
 import com.gnamp.struts.filter.TerminalWatcher;
 import com.gnamp.struts.terminal.WatcherSetting;
 import com.gnamp.struts.utils.JsonUtils;
 import common.Logger;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.List;
 
 public class TerminalWatcherAction
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
   
   private WatcherSetting mWatcherSetting = null;
   
   public WatcherSetting getWatcherSetting()
   {
     return this.mWatcherSetting;
   }
   
   public void setWatcherSetting(WatcherSetting setting)
   {
     this.mWatcherSetting = setting;
   }
   
   public String watcher()
   {
     long id = this.mIdArray.size() == 1 ? ((Long)this.mIdArray.get(0)).longValue() : 0L;
     this.mTerminal = (id == 0L ? null : getTerminalHandle().read(id));
     this.mWatcherSetting = (id == 0L ? null : TerminalWatcher.getWatcherSetting(id));
     
     return "watcher";
   }
   
   public void putWatcherSetting()
   {
     try
     {
       if (this.mWatcherSetting == null)
       {
         JsonUtils.writeError(this.servletResponse);
         log.error("#DEV_WATCHER#: invalid watcher setting");
         return;
       }
       for (Iterator localIterator = this.mIdArray.iterator(); localIterator.hasNext();)
       {
         long id = ((Long)localIterator.next()).longValue();
         WatcherSetting setting = TerminalWatcher.getWatcherSetting(id);
         if (setting != null)
         {
           this.mWatcherSetting.setDeviceId(id);
           if (!setting.equals(this.mWatcherSetting)) {
             TerminalWatcher.setWatcherSetting(id, this.mWatcherSetting);
           }
         }
       }
       JsonUtils.writeSuccess(this.servletResponse);
     }
     catch (Exception ex)
     {
       JsonUtils.writeError(this.servletResponse);
       log.error("#DEV_WATCHER#: [put]exception: " + ex.getMessage(), ex);
     }
/* ;0:   */   }
/* ;1:   */ }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.TerminalWatcherAction
 * JD-Core Version:    0.7.0.1
 */