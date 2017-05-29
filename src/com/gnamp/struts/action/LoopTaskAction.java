 package com.gnamp.struts.action;
 
 import com.gnamp.server.InnerException;
 import com.gnamp.server.NameExistException;
 import com.gnamp.server.Storage;
 import com.gnamp.server.handle.FileHandle;
 import com.gnamp.server.handle.LoopProgramHandle;
 import com.gnamp.server.handle.LoopTaskHandle;
 import com.gnamp.server.handle.VersionUtils;
 import com.gnamp.server.handle.tts.TTSNetworkException;
 import com.gnamp.server.model.LoopProgram;
 import com.gnamp.server.model.LoopTask;
 import com.gnamp.server.model.Task;
 import com.gnamp.server.query.Condition;
 import com.gnamp.server.query.Order;
 import com.gnamp.server.query.Result;
 import com.gnamp.server.query.TaskColumn;
 import com.gnamp.server.utils.DomUtils;
 import com.gnamp.struts.utils.JsonUtils;
 import java.util.ArrayList;
 import java.util.List;
 import javax.servlet.http.Cookie;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.commons.lang.StringUtils;
 import org.w3c.dom.Attr;
 import org.w3c.dom.Document;
 import org.w3c.dom.Element;
 import org.w3c.dom.Node;
 import org.w3c.dom.NodeList;
 
 public class LoopTaskAction
   extends TaskAction
 {
   LoopTask looptask = null;
   
   public LoopTask getLooptask()
   {
     return this.looptask;
   }
   
   public void setLooptask(LoopTask looptask)
   {
     this.looptask = looptask;
   }
   
   List<LoopTask> looptasklist = null;
   
   public List<LoopTask> getLooptasklist()
   {
     return this.looptasklist;
   }
   
   public void setLooptasklist(List<LoopTask> looptasklist)
   {
     this.looptasklist = looptasklist;
   }
   
   LoopTaskHandle looptaskhandle = null;
   int id;
   
   public LoopTaskHandle getLoopTaskhandle()
   {
     return this.looptaskhandle == null ? (this.looptaskhandle = new LoopTaskHandle(
       this)) : this.looptaskhandle;
   }
   
   public int getId()
   {
     return this.id;
   }
   
   public void setId(int id)
   {
     this.id = id;
   }
   
   public String getTouchUri()
   {
     return "~/Pages/TouchAddress.ashx";
   }
   
   public String getSessionCookieString()
   {
     return CookiesToString(this.servletRequest.getCookies());
   }
   
   public static String CookiesToString(Cookie[] cc)
   {
     return "";
   }
   
   public String execute()
   {
     return "list";
   }
   
   public boolean az = true;
   
   public boolean isAz()
   {
     return this.az;
   }
   
   public void setAz(boolean az)
   {
     this.az = az;
   }
   
   public boolean isSort = false;
   
   public boolean isSort()
   {
     return this.isSort;
   }
   
   public void setSort(boolean isSort)
   {
     this.isSort = isSort;
   }
   
   public void looplist()
   {
     Condition<TaskColumn> condition = null;
     if (this.isSort) {
       condition = new Condition(TaskColumn.NAME, 
         this.az ? Order.ASC : Order.DESC);
     } else {
       condition = new Condition(TaskColumn.TASK_VERSION, 
         Order.DESC);
     }
     this.looptasklist = new ArrayList();
     Result<LoopTask> result = getLoopTaskhandle().readPage(condition);
     
     this.looptasklist = result.getResult();
     
     JsonUtils.writeSuccessData(this.servletResponse, this.looptasklist);
   }
   
   public String ToAddLoopTask()
   {
     return "add";
   }
   
   public void AddLoopTask()
   {
     try
     {
       if ((this.looptask.getTaskName() == null) || ("".equals(this.looptask.getTaskName())))
       {
         JsonUtils.writeError(this.servletResponse, getText("name_cannot_null"), "NameCannotNull");
         return;
       }
       if (getLoopTaskhandle().create(this.looptask))
       {
         getLoopTaskhandle().check(this.looptask.getTaskId());
         
         LogHelp.userEvent(this.cstmId, this.userName, 
           getText("addtask"), 
           getText("addtask") + "[" + this.looptask.getTaskName() + "]");
         
         JsonUtils.writeSuccess(this.servletResponse);
       }
       else
       {
         JsonUtils.writeErrorMessage(this.servletResponse, getText("additemfail"));
       }
     }
     catch (NameExistException e)
     {
       JsonUtils.writeError(this.servletResponse, getText("name_existed"), "NameExist");
     }
     catch (InnerException e)
     {
       String innerType = e.getInnerType();
       if (innerType.equals("name cannot null")) {
         JsonUtils.writeError(this.servletResponse, 
           getText("name_cannot_null"), "NameCannotNull");
       } else {
         JsonUtils.writeErrorMessage(this.servletResponse, getText("additemfail"));
       }
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorMessage(this.servletResponse, getText("additemfail"));
     }
   }
   
   public String ToModifyLoopTask()
   {
     this.looptask = ((LoopTask)getLoopTaskhandle().read(this.looptask.getTaskId()));
     
     List<com.gnamp.server.model.File> files = getLoopTaskhandle().getTaskCurrentFiles(
       this.looptask.getTaskId());
     if (files != null)
     {
       long temp = 0L;
       for (com.gnamp.server.model.File f : files) {
         temp += f.getSize();
       }
       this.tasksize = String.format("%.2f MB", new Object[] { Float.valueOf((float)temp / 1024.0F / 1024.0F) });
     }
     return "edit";
   }
   
   List<LoopProgram> loopprogramlist = null;
   
   public List<LoopProgram> getLoopprogramlist()
   {
     return this.loopprogramlist;
   }
   
   public void setLoopprogramlist(List<LoopProgram> loopprogramlist)
   {
     this.loopprogramlist = loopprogramlist;
   }
   
   LoopProgramHandle loopprogramhandle = null;
   
   public LoopProgramHandle getLoopProgramandle()
   {
     return this.loopprogramhandle == null ? (this.loopprogramhandle = new LoopProgramHandle(
       this)) : this.loopprogramhandle;
   }
   
   public String celue()
   {
     this.loopprogramlist = new ArrayList();
     try
     {
       this.loopprogramlist = getLoopProgramandle().readAll(
         this.looptask.getTaskId());
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
     return "celue";
   }
   
   public String tasksize = "0 KB";
   
   public String getTasksize()
   {
     return this.tasksize;
   }
   
   public void setTasksize(String tasksize)
   {
     this.tasksize = tasksize;
   }
   
   public void ModifyLoopTask()
   {
     try
     {
       LoopTask old = (LoopTask)getLoopTaskhandle().read(this.looptask.getTaskId());
       if ((this.looptask.getTaskName() == null) || ("".equals(this.looptask.getTaskName())))
       {
         JsonUtils.writeError(this.servletResponse, getText("name_cannot_null"), "NameCannotNull");
         return;
       }
       old.setTaskName(this.looptask.getTaskName());
       old.setDescription(this.looptask.getDescription());
       if (getLoopTaskhandle().modify(old))
       {
         LogHelp.userEvent(this.cstmId, this.userName, 
           getText("edittask"), 
           getText("edittask") + "[" + old.getTaskName() + "]");
         
         JsonUtils.writeSuccess(this.servletResponse);
       }
       else
       {
         JsonUtils.writeErrorMessage(this.servletResponse, getText("edititemfail"));
       }
     }
     catch (NameExistException e)
     {
       JsonUtils.writeError(this.servletResponse, getText("name_existed"), "NameExist");
     }
     catch (InnerException e)
     {
       String innerType = e.getInnerType();
       if (innerType.equals("name cannot null")) {
         JsonUtils.writeError(this.servletResponse, 
           getText("name_cannot_null"), "NameCannotNull");
       } else {
         JsonUtils.writeErrorMessage(this.servletResponse, getText("edititemfail"));
       }
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorMessage(this.servletResponse, getText("edititemfail"));
     }
   }
   
   public String ToCloneLoopTask()
   {
     this.looptask = ((LoopTask)getLoopTaskhandle().read(this.looptask.getTaskId()));
     
     return "clone";
   }
   
   public void CloneLoopTask()
   {
     LoopTask newTask = new LoopTask();
     try
     {
       if ((this.looptask.getTaskName() == null) || ("".equals(this.looptask.getTaskName())))
       {
         JsonUtils.writeError(this.servletResponse, getText("name_cannot_null"), "NameCannotNull");
         return;
       }
       newTask.setTaskName(this.looptask.getTaskName());
       newTask.setDescription(this.looptask.getDescription());
       if (!getLoopTaskhandle().create(newTask))
       {
         JsonUtils.writeErrorMessage(this.servletResponse, getText("clonefailed"));
         return;
       }
       if (getLoopTaskhandle().copyTask(this.looptask.getTaskId(), newTask.getTaskId()))
       {
         LogHelp.userEvent(this.cstmId, this.userName, 
           getText("clonetask"), getText("clonetask") + "[" + 
           newTask.getTaskName() + "]");
         
         JsonUtils.writeSuccess(this.servletResponse);
       }
       else
       {
         getLoopTaskhandle().remove(newTask.getTaskId());
         JsonUtils.writeErrorMessage(this.servletResponse, getText("clonefailed"));
       }
     }
     catch (NameExistException e)
     {
       JsonUtils.writeError(this.servletResponse, getText("name_existed"), "NameExist");
     }
     catch (InnerException e)
     {
       String innerType = e.getInnerType();
       if (innerType.equals("name cannot null")) {
         JsonUtils.writeError(this.servletResponse, 
           getText("name_cannot_null"), "NameCannotNull");
       } else {
         JsonUtils.writeErrorMessage(this.servletResponse, getText("clonefailed"));
       }
     }
     catch (Exception e)
     {
       if (newTask.getTaskId() != 0) {
         getLoopTaskhandle().remove(newTask.getTaskId());
       }
       JsonUtils.writeErrorMessage(this.servletResponse, getText("clonefailed"));
     }
   }
   
   public String content()
   {
     return "content";
   }
   
   public String selectIDs = "";
   
   public String getSelectIDs()
   {
     return this.selectIDs;
   }
   
   public void setSelectIDs(String selectIDs)
   {
     this.selectIDs = selectIDs;
   }
   
   public void delete()
   {
     if ((this.selectIDs != null) && (this.selectIDs.length() > 0)) {
       for (int i = 0; i < this.selectIDs.split(",").length; i++) {
         try
         {
           String n = ((LoopTask)getLoopTaskhandle().read(
             Integer.parseInt(this.selectIDs.split(",")[i])))
             .getTaskName();
           if (getLoopTaskhandle().remove(Integer.parseInt(this.selectIDs.split(",")[i]))) {
             LogHelp.userEvent(this.cstmId, this.userName, 
               getText("deletetask"), getText("deletetask") + 
               "[" + n + "]");
           }
         }
         catch (Exception e)
         {
           e.printStackTrace();
           JsonUtils.writeErrorData(this.servletResponse, 
             getText("deletefailed"));
         }
       }
     }
     JsonUtils.writeSuccess(this.servletResponse);
   }
   
   public void check()
   {
     try
     {
       if ((this.selectIDs != null) && (this.selectIDs.length() > 0))
       {
         String[] idArray = this.selectIDs.split(",");
         if (idArray == null) {
           idArray = new String[0];
         }
         for (int i = 0; i < idArray.length; i++) {
           try
           {
             int task_id = Integer.parseInt(idArray[i]);
             Task task = task_id == 0 ? null : (LoopTask)getLoopTaskhandle().read(task_id);
             if ((task != null) && (task.getState() != 0) && 
               (getLoopTaskhandle().check(task_id) != null)) {
               LogHelp.userEvent(this.cstmId, this.userName, getText("audittask"), 
                 getText("audittask") + "[" + task.getTaskName() + "]");
             }
           }
           catch (TTSNetworkException e)
           {
             e.printStackTrace();
             JsonUtils.writeErrorData(this.servletResponse, getText("checkfailed_ttsnetwork"));
           }
           catch (Exception e)
           {
             e.printStackTrace();
             JsonUtils.writeErrorData(this.servletResponse, getText("checkfailed"));
           }
         }
       }
     }
     catch (Exception exception)
     {
       exception.printStackTrace();
       JsonUtils.writeErrorData(this.servletResponse, getText("checkfailed"));
     }
     JsonUtils.writeSuccess(this.servletResponse);
   }
   
   FileHandle filehandle = null;
   
   public FileHandle getFileHandle()
   {
     return this.filehandle == null ? (this.filehandle = new FileHandle(this)) : 
       this.filehandle;
   }
   
   public void jiaoyan()
   {
     String msg = getText("verifysuccess");
     Storage storage = new Storage(getCstmId());
     if (storage != null)
     {
       java.io.File tasklist = new java.io.File(
         storage.getTaskListPath(this.looptask.getTaskId()));
       
       java.io.File prgmlist = new java.io.File(
         storage.getProgramListPath(this.looptask.getTaskId()));
       java.io.File filelist = new java.io.File(
         storage.getFileListPath(this.looptask.getTaskId()));
       if ((!tasklist.exists()) || (!prgmlist.exists()) || (!filelist.exists()))
       {
         msg = getText("incomplete");
         JsonUtils.writeSuccessData(this.servletResponse, msg);
         return;
       }
       Document doc = null;
       
       doc = DomUtils.loadFromUri(storage.getTaskListPath(this.looptask
         .getTaskId()));
       Node node = DomUtils.selectSingleNode(doc, "config/tasklist");
       
       String xmlVer = " ";
       xmlVer = DomUtils.getAttributeString((Element)node, "ver");
       
       LoopTask t = (LoopTask)getLoopTaskhandle().read(this.looptask.getTaskId());
       
       String taskVersion = VersionUtils.toString(t.getTaskVersion()) + 
         "." + VersionUtils.toString(t.getFileVersion()) + "." + 
         VersionUtils.toString(t.getPlayVersion());
       if ((t instanceof LoopTask)) {
         taskVersion = taskVersion + "." + VersionUtils.toString(t
           .getStrategyVersion());
       }
       if (!taskVersion.equals(xmlVer))
       {
         msg = getText("mismatch");
         JsonUtils.writeSuccessData(this.servletResponse, msg);
         return;
       }
       Document filedoc = null;
       Document playdoc = null;
       NodeList xmlNlist = null;
       try
       {
         filedoc = DomUtils.loadFromUri(storage
           .getFileListPath(this.looptask.getTaskId()));
         
         xmlNlist = DomUtils.selectNodes(filedoc, 
           "/config/filelist/file/@name");
         for (int i = 0; i < xmlNlist.getLength(); i++)
         {
           String value = ((Attr)xmlNlist.item(i))
             .getValue();
           if (!StringUtils.isNotBlank(value))
           {
             msg = 
             
 
 
               getText("taskerror") + getFileHandle().read(Long.parseLong(value)).getFileName() + getText("noexist");
             
             JsonUtils.writeSuccessData(this.servletResponse, msg);
             return;
           }
           long fileid = Long.parseLong(value);
           if (getFileHandle().read(fileid) == null)
           {
             msg = 
             
               getText("taskerror") + String.valueOf(fileid) + getText("noexist");
             
             JsonUtils.writeSuccessData(this.servletResponse, msg);
             return;
           }
           java.io.File filepath = new java.io.File(
             storage.getFilePath(fileid));
           if (!filepath.exists())
           {
             msg = 
             
 
               getText("taskerror") + getFileHandle().read(fileid).getFileName() + getText("noexist");
             
             JsonUtils.writeSuccessData(this.servletResponse, msg);
             return;
           }
           if (filepath.exists())
           {
             long len = filepath.length();
             if (len <= 0L)
             {
               msg = 
                 getText("fileerror") + getFileHandle().read(fileid)
                 .getFileName();
               JsonUtils.writeSuccessData(this.servletResponse, 
                 msg);
               return;
             }
           }
         }
         xmlNlist = null;
         
         playdoc = DomUtils.loadFromUri(storage
           .getProgramListPath(this.looptask.getTaskId()));
         
         xmlNlist = 
           DomUtils.selectNodes(
           playdoc, 
           "/config/program/videorect/video/@path | /config/program/imagerect/img/@path | /config/program/musiclist/music/@path | /config/program/@backfilepath");
         for (int i = 0; i < xmlNlist.getLength(); i++)
         {
           String value = ((Attr)xmlNlist.item(i))
             .getValue();
           if (!StringUtils.isNotBlank(value))
           {
             msg = 
             
 
 
               getText("taskerror") + getFileHandle().read(Long.parseLong(value)).getFileName() + getText("noexistinlist");
             
             JsonUtils.writeSuccessData(this.servletResponse, msg);
             return;
           }
           if (DomUtils.selectSingleNode(filedoc, 
             "/config/filelist/file[@name=\"" + value + 
             "\"]") == null)
           {
             msg = 
             
 
 
               getText("taskerror") + getFileHandle().read(Long.parseLong(value)).getFileName() + getText("noexistinlist");
             
             JsonUtils.writeSuccessData(this.servletResponse, msg);
             return;
           }
         }
         msg = getText("verifysuccess");
         JsonUtils.writeSuccessData(this.servletResponse, msg);
       }
       catch (Exception e)
       {
         msg = getText("verifyfailed");
         JsonUtils.writeSuccessData(this.servletResponse, msg);
         return;
       }
       finally
       {
         xmlNlist = null;
         playdoc = null;
         filedoc = null;
       }
       xmlNlist = null;
       playdoc = null;
       filedoc = null;
     }
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.LoopTaskAction
 * JD-Core Version:    0.7.0.1
 */