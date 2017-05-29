 package com.gnamp.struts.action;
 
 import com.gnamp.server.handle.CityHandle;
 import com.gnamp.server.handle.GroupHandle;
 import com.gnamp.server.handle.TerminalHandle;
 import com.gnamp.server.handle.TodoTerminalHandle;
 import com.gnamp.server.model.Group;
 import com.gnamp.server.model.Terminal;
 import com.gnamp.server.model.TodoTerminal;
 import com.gnamp.struts.utils.JsonUtils;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 
 public class TodoTerminalAction
   extends JSONAction
 {
   public String selectIDs = "";
   
   public String getSelectIDs()
   {
     return this.selectIDs;
   }
   
   public void setSelectIDs(String selectIDs)
   {
     this.selectIDs = selectIDs;
   }
   
   public TodoTerminalHandle todoterminalhandle = null;
   
   public TodoTerminalHandle getTodoTerminalhandle()
   {
     return this.todoterminalhandle == null ? (this.todoterminalhandle = new TodoTerminalHandle(
       this)) : this.todoterminalhandle;
   }
   
   public TodoTerminal todoterminal = null;
   
   public TodoTerminal getTodoterminal()
   {
     return this.todoterminal;
   }
   
   public void setTodoterminal(TodoTerminal todoterminal)
   {
     this.todoterminal = todoterminal;
   }
   
   List<TodoTerminal> todoterminallist = null;
   
   public List<TodoTerminal> getTerminallist()
   {
     return this.todoterminallist;
   }
   
   public void setTerminallist(List<TodoTerminal> todoterminallist)
   {
     this.todoterminallist = todoterminallist;
   }
   
   public void todolist()
   {
     this.todoterminallist = new ArrayList();
     
     String query = "SELECT CSTM_ID, DEV_ID, NAME, DESCP FROM tb_terminal_todo WHERE CSTM_ID=" + 
       getCstmId();
     
     this.todoterminallist = getTodoTerminalhandle().readAll(query, null);
     
     JsonUtils.writeSuccessData(this.servletResponse, this.todoterminallist);
   }
   
   public void delete()
   {
     int succ = 0;
     int fail = 0;
     if ((this.selectIDs != null) && (this.selectIDs.length() > 0)) {
       for (int i = 0; i < this.selectIDs.split(",").length; i++) {
         try
         {
           if (getTodoTerminalhandle().remove(Long.parseLong(this.selectIDs.split(",")[i]))) {
             succ++;
           } else {
             fail++;
           }
         }
         catch (Exception e)
         {
           fail++;
           e.printStackTrace();
         }
       }
     }
     response(JSONSuccessString());
   }
   
   public List<String> provincelist = null;
   
   public List<String> getProvincelist()
   {
     return this.provincelist;
   }
   
   public void setProvincelist(List<String> provincelist)
   {
     this.provincelist = provincelist;
   }
   
   Group group = null;
   
   public Group getGroup()
   {
     return this.group;
   }
   
   public void setGroup(Group group)
   {
     this.group = group;
   }
   
   public List<Group> grouppath = null;
   
   public List<Group> getGrouppath()
   {
     return this.grouppath;
   }
   
   public void setGrouppath(List<Group> grouppath)
   {
     this.grouppath = grouppath;
   }
   
   public List<Group> getBrother()
   {
     return this.brother;
   }
   
   public void setBrother(List<Group> brother)
   {
     this.brother = brother;
   }
   
   public List<Group> brother = null;
   GroupHandle grouphandle = null;
   
   public GroupHandle getGrouphandle()
   {
     return this.grouphandle == null ? (this.grouphandle = new GroupHandle(this)) : 
       this.grouphandle;
   }
   
   public String tomodify()
   {
     return ToModify();
   }
   
   public String ToModify()
   {
     this.provincelist = CityHandle.readAllProvinces();
     if ((this.selectIDs != null) && (this.selectIDs.length() > 0)) {
       if (this.selectIDs.split(",").length == 1) {
         this.todoterminal = getTodoTerminalhandle().read(
           Long.parseLong(this.selectIDs.split(",")[0]));
       } else {
         this.todoterminal = getTodoTerminalhandle().read(
           Long.parseLong(this.selectIDs.split(",")[0]));
       }
     }
     this.group = new Group();
     this.group.setParentId(0);
     if (this.grouppath == null) {
       this.grouppath = new ArrayList();
     } else {
       this.grouppath.clear();
     }
     Group newgroup = this.group;
     
     this.grouppath.add(0, this.group);
     while (newgroup.getParentId() != 0)
     {
       newgroup = getGrouphandle().read(newgroup.getParentId());
       this.grouppath.add(0, newgroup);
     }
     StringBuffer sql = new StringBuffer("SELECT A.GROUP_ID AS GROUP_ID, A.NAME AS NAME, A.DESCP AS DESCP, A.PARENT_ID AS PARENT_ID, B.NAME AS PARENT_NAME, A.DEPTH AS DEPTH, A.SUB_NUM AS SUB_NUM, A.DEV_NUM AS DEV_NUM, A.CREATE_TIME AS CREATE_TIME, A.CREATE_USER AS CREATE_USER FROM (SELECT * FROM tb_group WHERE CSTM_ID=:CSTM_ID  AND  PARENT_ID IS NULL  ) AS A LEFT JOIN tb_group AS B ON A.PARENT_ID=B.GROUP_ID");
     
 
 
 
 
 
 
 
 
 
 
 
 
 
 
     Map<String, Object> parameters = new HashMap();
     parameters.put("CSTM_ID", Integer.valueOf(getCstmId()));
     
 
     this.brother = getGrouphandle().readChildren(sql.toString(), parameters);
     
     return "edit";
   }
   
   public int cid = 0;
   
   public int getCid()
   {
     return this.cid;
   }
   
   public void setCid(int cid)
   {
     this.cid = cid;
   }
   
   public int getGid()
   {
     return this.gid;
   }
   
   public void setGid(int gid)
   {
     this.gid = gid;
   }
   
   public int gid = 0;
   TerminalHandle terminalhandle = null;
   
   public TerminalHandle getTerminalhandle()
   {
     return this.terminalhandle == null ? (this.terminalhandle = new TerminalHandle(
       this)) : this.terminalhandle;
   }
   
   public void Modify()
   {
     if ((this.selectIDs != null) && (this.selectIDs.length() > 0)) {
       if (this.selectIDs.split(",").length == 1)
       {
         Terminal old = new Terminal();
         
 
         old.setDeviceId(this.todoterminal.getDeviceId());
         old.setDeviceName(this.todoterminal.getDeviceName());
         old.setDescription(this.todoterminal.getDescription());
         old.setCityId(this.cid);
         old.setGroupId(this.gid);
         if (getTerminalhandle().createFromTodo(old)) {
           response(JSONSuccessString());
         } else {
           response(JSONErrorString());
         }
       }
       else
       {
         try
         {
           TodoTerminal todo = getTodoTerminalhandle().read(
             Long.parseLong(this.selectIDs.split(",")[0]));
           
           Terminal old = new Terminal();
           
           old.setDeviceId(todo.getDeviceId());
           old.setDeviceName(todo.getDeviceName());
           old.setDescription(todo.getDescription());
           old.setCityId(this.cid);
           old.setGroupId(this.gid);
           if (!getTerminalhandle().createFromTodo(old)) {
             response(JSONErrorString());
           }
         }
         catch (Exception e)
         {
           response(JSONErrorString());
           e.printStackTrace();
         }
         response(JSONSuccessString());
       }
     }
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.TodoTerminalAction
 * JD-Core Version:    0.7.0.1
 */