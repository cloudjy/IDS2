 package com.gnamp.struts.action;
 
 import com.gnamp.server.handle.GroupHandle;
 import com.gnamp.server.model.Group;
 import com.gnamp.struts.tree.ITree;
 import com.gnamp.struts.treeimpl.GroupTree;
 import com.gnamp.struts.utils.JsonUtils;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 
 public class GroupAction
   extends JSONAction
 {
   Group group = null;
   GroupTree groupvo = null;
   GroupHandle grouphandle = null;
   int id;
   
   public GroupHandle getGrouphandle()
   {
     return this.grouphandle == null ? (this.grouphandle = new GroupHandle(this)) : 
       this.grouphandle;
   }
   
   public void terminal_tree_default()
   {
     t();
   }
   
   public int getId()
   {
     return this.id;
   }
   
   public void setId(int id)
   {
     this.id = id;
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
   
   private void t()
   {
     List<Group> groups = new ArrayList();
     StringBuffer sql = new StringBuffer("SELECT A.GROUP_ID AS GROUP_ID, A.NAME AS NAME, A.DESCP AS DESCP, A.PARENT_ID AS PARENT_ID, B.NAME AS PARENT_NAME, A.DEPTH AS DEPTH, A.SUB_NUM AS SUB_NUM, A.DEV_NUM AS DEV_NUM, A.CREATE_TIME AS CREATE_TIME, A.CREATE_USER AS CREATE_USER FROM (SELECT * FROM tb_group WHERE CSTM_ID=:CSTM_ID AND  " + (
     
 
 
 
 
 
 
 
       this.id == 0 ? "PARENT_ID IS NULL " : "PARENT_ID=:PARENT_ID") + 
       " ) AS A " + 
       "LEFT JOIN tb_group AS B ON A.PARENT_ID=B.GROUP_ID " + (
       this.az ? " GROUP BY A.NAME ASC " : " GROUP BY A.NAME DESC "));
     
     Map<String, Object> parameters = new HashMap();
     parameters.put("CSTM_ID", Integer.valueOf(getCstmId()));
     if (this.id != 0) {
       parameters.put("PARENT_ID", Integer.valueOf(this.id));
     }
     try
     {
       groups = getGrouphandle().readChildren(sql.toString(), parameters);
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
     List<ITree<Group>> groupvos = new ArrayList();
     for (Group group : groups)
     {
       ITree<Group> vo = new GroupTree().convert(group);
       if (vo != null) {
         groupvos.add(vo);
       }
     }
     StringBuffer buffer = new StringBuffer(JSONArrayToString(groupvos));
     
 
 
 
 
     response(buffer.toString());
   }
   
   public void list()
   {
     List<Group> groups = new ArrayList();
     StringBuffer sql = new StringBuffer("SELECT A.GROUP_ID AS GROUP_ID, A.NAME AS NAME, A.DESCP AS DESCP, A.PARENT_ID AS PARENT_ID, B.NAME AS PARENT_NAME, A.DEPTH AS DEPTH, A.SUB_NUM AS SUB_NUM, A.DEV_NUM AS DEV_NUM, A.CREATE_TIME AS CREATE_TIME, A.CREATE_USER AS CREATE_USER FROM (SELECT * FROM tb_group WHERE CSTM_ID=:CSTM_ID AND  " + (
     
 
 
 
 
 
 
 
       this.id == 0 ? "PARENT_ID IS NULL " : "PARENT_ID=:PARENT_ID") + 
       " ) AS A " + 
       "LEFT JOIN tb_group AS B ON A.PARENT_ID=B.GROUP_ID " + (
       this.az ? " GROUP BY A.NAME ASC " : " GROUP BY A.NAME DESC "));
     
     Map<String, Object> parameters = new HashMap();
     parameters.put("CSTM_ID", Integer.valueOf(getCstmId()));
     if (this.id != 0) {
       parameters.put("PARENT_ID", Integer.valueOf(this.id));
     }
     groups = getGrouphandle().readChildren(sql.toString(), parameters);
     List<ITree<Group>> groupvos = new ArrayList();
     for (Group g : groups)
     {
       ITree<Group> vo = new GroupTree().convert(g);
       if (vo != null) {
         groupvos.add(vo);
       }
     }
     response(JSONSuccessString(JSONArrayToString(groupvos)));
   }
   
   public int selfid = 0;
   
   public int getSelfid()
   {
     return this.selfid;
   }
   
   public void setSelfid(int selfid)
   {
     this.selfid = selfid;
   }
   
   public void groupSelector()
   {
     List<Group> groups = new ArrayList();
     StringBuffer sql = new StringBuffer("SELECT A.GROUP_ID AS GROUP_ID, A.NAME AS NAME, A.DESCP AS DESCP, A.PARENT_ID AS PARENT_ID, B.NAME AS PARENT_NAME, A.DEPTH AS DEPTH, A.SUB_NUM AS SUB_NUM, A.DEV_NUM AS DEV_NUM, A.CREATE_TIME AS CREATE_TIME, A.CREATE_USER AS CREATE_USER FROM (SELECT * FROM tb_group WHERE CSTM_ID=:CSTM_ID AND  " + (
     
 
 
 
 
 
 
 
       this.id == 0 ? "PARENT_ID IS NULL " : "PARENT_ID=:PARENT_ID") + 
       " ) AS A " + 
       "LEFT JOIN tb_group AS B ON A.PARENT_ID=B.GROUP_ID" + (
       this.az ? " GROUP BY A.NAME ASC " : " GROUP BY A.NAME DESC "));
     
     Map<String, Object> parameters = new HashMap();
     parameters.put("CSTM_ID", Integer.valueOf(getCstmId()));
     if (this.id != 0) {
       parameters.put("PARENT_ID", Integer.valueOf(this.id));
     }
     try
     {
       groups = getGrouphandle().readChildren(sql.toString(), parameters);
       
 
 
 
 
       int c = groups.size();
       for (int i = 0; i < c; i++) {
         if (((Group)groups.get(i)).getGroupId() == this.selfid)
         {
           groups.remove(groups.get(i));
           
           i--;
           c--;
         }
       }
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
     response(JSONSuccessString(JSONArrayToString(groups)));
   }
   
   public void add()
   {
     if ((this.group.getGroupName() == null) || ("".equals(this.group.getGroupName())))
     {
       JsonUtils.writeSuccessData(this.servletResponse, getText("namenull"));
       return;
     }
     try
     {
       if (getGrouphandle().create(this.group))
       {
         LogHelp.userEvent(this.cstmId, this.userName, getText("addgroup"), getText("addgroup") + "[" + this.group.getGroupName() + "]");
         
         response(JSONSuccessString(JSONObjectToString(this.group)));
       }
       else
       {
         JsonUtils.writeErrorData(this.servletResponse, getText("additemfail"));
       }
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorData(this.servletResponse, getText("additemfail"));
     }
   }
   
   public String ToAddGroup()
   {
     return "addgroup";
   }
   
   public List<Group> grouppath = null;
   public List<Group> brother = null;
   
   public String ToModifyGroup()
   {
     this.group = getGrouphandle().read(this.id);
     if (this.grouppath == null) {
       this.grouppath = new ArrayList();
     } else {
       this.grouppath.clear();
     }
     Group newgroup = this.group;
     while (newgroup.getParentId() != 0)
     {
       newgroup = getGrouphandle().read(newgroup.getParentId());
       this.grouppath.add(0, newgroup);
     }
     int pid = this.group.getParentId();
     
     StringBuffer sql = new StringBuffer("SELECT A.GROUP_ID AS GROUP_ID, A.NAME AS NAME, A.DESCP AS DESCP, A.PARENT_ID AS PARENT_ID, B.NAME AS PARENT_NAME, A.DEPTH AS DEPTH, A.SUB_NUM AS SUB_NUM, A.DEV_NUM AS DEV_NUM, A.CREATE_TIME AS CREATE_TIME, A.CREATE_USER AS CREATE_USER FROM (SELECT * FROM tb_group WHERE CSTM_ID=:CSTM_ID  AND GROUP_ID !=:GROUP_ID AND  " + (
     
 
 
 
 
 
 
 
       pid == 0 ? "PARENT_ID IS NULL " : "PARENT_ID=:PARENT_ID") + 
       " ) AS A " + 
       "LEFT JOIN tb_group AS B ON A.PARENT_ID=B.GROUP_ID");
     
     Map<String, Object> parameters = new HashMap();
     parameters.put("CSTM_ID", Integer.valueOf(getCstmId()));
     if (pid != 0) {
       parameters.put("PARENT_ID", Integer.valueOf(pid));
     }
     parameters.put("GROUP_ID", Integer.valueOf(this.id));
     
     this.brother = getGrouphandle().readChildren(sql.toString(), parameters);
     
     return "edit";
   }
   
   public void edit()
   {
     if ((this.group.getGroupName() == null) || ("".equals(this.group.getGroupName())))
     {
       response(JSONErrorString(getText("namenull")));
       return;
     }
     try
     {
       Group temp = getGrouphandle().read(this.group.getGroupId());
       temp.setGroupName(this.group.getGroupName());
       temp.setParentId(this.group.getParentId());
       setGroup(temp);
       if (getGrouphandle().modify(this.group))
       {
         LogHelp.userEvent(this.cstmId, this.userName, getText("editgroup"), getText("editgroup") + "[" + temp.getGroupName() + "]");
         
         response(JSONSuccessString(JSONObjectToString(this.group)));
       }
       else
       {
         JsonUtils.writeErrorData(this.servletResponse, getText("edititemfail"));
       }
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorData(this.servletResponse, getText("edititemfail"));
     }
   }
   
   public void remove()
   {
     try
     {
       String n = getGrouphandle().read(this.group.getGroupId()).getGroupName();
       
       getGrouphandle().remove(this.group.getGroupId());
       
       LogHelp.userEvent(this.cstmId, this.userName, getText("deletegroup"), getText("deletegroup") + "[" + n + "]");
       
 
       response(JSONSuccessString());
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorData(this.servletResponse, getText("deletefailed"));
     }
   }
   
   private String queryString()
   {
     String queryTable = "SELECT * FROM vw_terminal_detail WHERE CSTM_ID=:CSTM_ID " + (
       this.id == 0 ? "" : 
       " AND GROUP_ID IN(SELECT :GROUP_ID AS GROUP_ID UNION SELECT GROUP_ID FROM tb_group_tree WHERE PARENT_ID=:GROUP_ID) ");
     
 
 
 
     String query = "SELECT f.CSTM_ID AS CSTM_ID, DEV_ID, f.NAME AS NAME, f.DESCP AS DESCP, f.GROUP_ID AS GROUP_ID, c.NAME AS GROUP_NAME, f.CREATE_TIME AS CREATE_TIME, f.CREATE_USER AS CREATE_USER, CITY_ID, CITY_NAME, ASSIGN_KERNEL, ASSIGN_APP, CUR_KERNEL, CUR_APP, ASSIGN_CONFIG_ID, ASSIGN_CONFIG_NAME, ASSIGN_CONFIG_VERSION, CUR_CONFIG_ID, CUR_CONFIG_NAME, CUR_CONFIG_VERSION, ASSIGN_LOOPTASK_ID, ASSIGN_LOOPTASK_NAME, ASSIGN_LOOPTASK_VERSION, ASSIGN_LOOPFILE_VERSION, ASSIGN_LOOPPLAY_VERSION, ASSIGN_LOOPSTRATEGY_VERSION, CUR_LOOPTASK_ID, CUR_LOOPTASK_NAME, CUR_LOOPTASK_VERSION, CUR_LOOPFILE_VERSION, CUR_LOOPPLAY_VERSION, CUR_LOOPSTRATEGY_VERSION, ASSIGN_DEMANDTASK_ID, ASSIGN_DEMANDTASK_NAME, ASSIGN_DEMANDTASK_VERSION, ASSIGN_DEMANDFILE_VERSION, ASSIGN_DEMANDPLAY_VERSION, CUR_DEMANDTASK_ID, CUR_DEMANDTASK_NAME, CUR_DEMANDTASK_VERSION, CUR_DEMANDFILE_VERSION, CUR_DEMANDPLAY_VERSION, ASSIGN_PLUGINTASK_ID, ASSIGN_PLUGINTASK_NAME, ASSIGN_PLUGINTASK_VERSION, ASSIGN_PLUGINFILE_VERSION, ASSIGN_PLUGINPLAY_VERSION, CUR_PLUGINTASK_ID, CUR_PLUGINTASK_NAME, CUR_PLUGINTASK_VERSION, CUR_PLUGINFILE_VERSION, CUR_PLUGINPLAY_VERSION, REST_SCHEDULE, STANDBY_SCHEDULE, CAPTURE_SCHEDULE, DEMAND_SCHEDULE, SCHEDULE_VERSION, SUBTITLE, SUBTITLE_VERSION, CUR_SUBTITLE_VERSION, ONLINE_STATE, LOGON_TIME, LOGOFF_TIME, DOWNLOAD_TOTAL, DOWNLOAD_FINISHED, DOWNLOAD_TYPE, POWERON, POWEROFF, ALIVE_INTERVAL, LAST_ALIVE, LAST_PATROL, LAST_DOWNLOAD,  KERNEL_UPDATED, APP_UPDATED, CONFIG_UPDATED, LOOPTASK_UPDATED, DEMANDTASK_UPDATED, PLUGINTASK_UPDATED, SUBTITLE_UPDATED FROM (" + 
     
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
       queryTable + 
       ") as f " + 
       " LEFT JOIN tb_group as c on f.CSTM_ID=c.CSTM_ID AND f.GROUP_ID=c.GROUP_ID ";
     
     return query;
   }
   
   public Group getGroup()
   {
     return this.group;
   }
   
   public void setGroup(Group group)
   {
     this.group = group;
   }
   
   public GroupTree getGroupvo()
   {
     return this.groupvo;
   }
   
   public void setGroupvo(GroupTree groupvo)
   {
     this.groupvo = groupvo;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.GroupAction
 * JD-Core Version:    0.7.0.1
 */