 package com.gnamp.struts.action;
 
 import com.gnamp.server.IUserInfo;
 import com.gnamp.server.db.DbShell;
 import com.gnamp.server.db.RolePrivilegeTable;
 import com.gnamp.server.db.UserRoleTable;
 import com.gnamp.server.model.Privilege;
 import com.gnamp.server.model.Role;
 import com.gnamp.struts.filter.LoginUser;
 import com.gnamp.struts.filter.MySessionContext;
 import com.gnamp.struts.vo.PrivilegeVo;
 import com.opensymphony.xwork2.ActionSupport;
 import com.opensymphony.xwork2.Preparable;
 import common.Logger;
 import java.io.FileNotFoundException;
 import java.io.IOException;
 import java.lang.reflect.Method;
 import java.util.Enumeration;
 import java.util.HashMap;
 import java.util.HashSet;
 import java.util.List;
 import java.util.Map;
 import java.util.Properties;
 import java.util.Set;
 import javax.servlet.ServletContext;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import javax.servlet.http.HttpSession;
 import org.apache.commons.beanutils.PropertyUtils;
 import org.apache.struts2.ServletActionContext;
 import org.apache.struts2.interceptor.ApplicationAware;
 import org.apache.struts2.interceptor.RequestAware;
 import org.apache.struts2.interceptor.ServletRequestAware;
 import org.apache.struts2.interceptor.ServletResponseAware;
 import org.apache.struts2.interceptor.SessionAware;
 import org.apache.struts2.util.ServletContextAware;
 
 public abstract class BaseAction
   extends ActionSupport
   implements IUserInfo, Preparable, ServletContextAware, ServletRequestAware, ServletResponseAware, ApplicationAware, RequestAware, SessionAware
 {
   public static final String CURRENT_PRIVILEGE = "current_privilege";
   protected static final Logger log = Logger.getLogger(BaseAction.class);
   PrivilegeVo privilege = null;
   private static final long serialVersionUID = 2752327779434251326L;
   
   public String getTheme()
   {
     return getBasePath();
   }
   
   protected String getBasePath()
   {
     return 
     
 
       this.servletRequest.getScheme() + "://" + this.servletRequest.getServerName() + ":" + this.servletRequest.getServerPort() + this.servletRequest.getContextPath();
   }
   
   public PrivilegeVo getPrivilege()
   {
     return this.privilege;
   }
   
   protected int userId = 0;
   protected String userName = null;
   protected int cstmId = 0;
   protected String shortName = null;
   protected ServletContext servletContext = null;
   protected HttpServletRequest servletRequest = null;
   protected HttpServletResponse servletResponse = null;
   protected Map<String, Object> applicationMap = null;
   protected Map<String, Object> requestMap = null;
   protected Map<String, Object> sessionMap = null;
   public static final String SESSION_CURRENT_LOGINUSER = "current.user";
   public static final String SESSION_LOGIN_DOMAIN = "login.domain";
   public static final String SESSION_USER_ID = "USER_ID";
   public static final String SESSION_USER_NAME = "USER_NAME";
   public static final String SESSION_CSTM_ID = "CSTM_ID";
   public static final String SESSION_SHORT_NAME = "SHORT_NAME";
   
   public LoginUser getCurrentUser()
   {
     return (LoginUser)this.sessionMap.get("current.user");
   }
   
   public void prepare()
     throws Exception
   {
     if (this.sessionMap != null)
     {
       if ((!this.sessionMap.containsKey("USER_ID")) || 
         (!this.sessionMap.containsKey("USER_NAME")) || 
         (!this.sessionMap.containsKey("CSTM_ID")) || 
         (!this.sessionMap.containsKey("SHORT_NAME"))) {
         return;
       }
       this.userId = ((Integer)this.sessionMap.get("USER_ID")).intValue();
       this.userName = ((String)this.sessionMap.get("USER_NAME"));
       this.cstmId = ((Integer)this.sessionMap.get("CSTM_ID")).intValue();
       this.shortName = ((String)this.sessionMap.get("SHORT_NAME"));
     }
     else
     {
       HttpSession session = ServletActionContext.getRequest().getSession();
       
       this.userId = ((Integer)session.getAttribute("USER_ID")).intValue();
       this.userName = ((String)session.getAttribute("USER_NAME"));
       this.cstmId = ((Integer)session.getAttribute("CSTM_ID")).intValue();
       this.shortName = ((String)session.getAttribute("SHORT_NAME"));
     }
   }
   
   public String getEnglish(String key)
   {
     Properties p = new Properties();
     try
     {
       p.load(getClass().getResourceAsStream(getClass().getSimpleName() + "_en_US.properties"));
       return p.getProperty(key);
     }
     catch (FileNotFoundException e)
     {
       e.printStackTrace();
     }
     catch (IOException e)
     {
       e.printStackTrace();
     }
     return "";
   }
   
   private static final Map<Integer, String> map = getMap();
   
   private static Map<Integer, String> getMap()
   {
     Map<Integer, String> maptemp = new HashMap();
     maptemp.put(Integer.valueOf(10), "aditdemand");
     maptemp.put(Integer.valueOf(53), "assignpermission");
     maptemp.put(Integer.valueOf(49), "assignroles");
     maptemp.put(Integer.valueOf(9), "auditcarousel");
     maptemp.put(Integer.valueOf(43), "auditconfig");
     maptemp.put(Integer.valueOf(5), "auditfile");
     maptemp.put(Integer.valueOf(11), "auditspot");
     maptemp.put(Integer.valueOf(42), "delconfig");
     maptemp.put(Integer.valueOf(6), "delfiles");
     maptemp.put(Integer.valueOf(24), "delgroup");
     maptemp.put(Integer.valueOf(15), "delprog");
     maptemp.put(Integer.valueOf(52), "delrole");
     maptemp.put(Integer.valueOf(20), "delstrategy");
     maptemp.put(Integer.valueOf(13), "deltask");
     maptemp.put(Integer.valueOf(34), "delterminal");
     maptemp.put(Integer.valueOf(48), "deluser");
     maptemp.put(Integer.valueOf(44), "editconfig");
     maptemp.put(Integer.valueOf(18), "editcontent");
     maptemp.put(Integer.valueOf(1), "editdir");
     maptemp.put(Integer.valueOf(7), "editfile");
     maptemp.put(Integer.valueOf(22), "editgroup");
     maptemp.put(Integer.valueOf(17), "editlayout");
     maptemp.put(Integer.valueOf(16), "editprog");
     maptemp.put(Integer.valueOf(51), "editrole");
     maptemp.put(Integer.valueOf(21), "taskstrategy");
     maptemp.put(Integer.valueOf(8), "edittask");
     maptemp.put(Integer.valueOf(32), "editterminal");
     maptemp.put(Integer.valueOf(46), "edituser");
     maptemp.put(Integer.valueOf(25), "groupcarousel");
     maptemp.put(Integer.valueOf(28), "groupconfig");
     maptemp.put(Integer.valueOf(26), "groupdamand");
     maptemp.put(Integer.valueOf(29), "groupfirmware");
     maptemp.put(Integer.valueOf(30), "groupproc");
     maptemp.put(Integer.valueOf(27), "groupspots");
     maptemp.put(Integer.valueOf(2), "newcatalog");
     maptemp.put(Integer.valueOf(41), "newconfig");
     maptemp.put(Integer.valueOf(23), "newgroup");
     maptemp.put(Integer.valueOf(14), "newprog");
     maptemp.put(Integer.valueOf(50), "newrole");
     maptemp.put(Integer.valueOf(19), "newstrategy");
     maptemp.put(Integer.valueOf(12), "newtask");
     maptemp.put(Integer.valueOf(33), "newterminal");
     maptemp.put(Integer.valueOf(47), "newuser");
     maptemp.put(Integer.valueOf(3), "deldir");
     maptemp.put(Integer.valueOf(31), "setweather");
     maptemp.put(Integer.valueOf(35), "speccarousel");
     maptemp.put(Integer.valueOf(40), "specconfig");
     maptemp.put(Integer.valueOf(36), "specdemand");
     maptemp.put(Integer.valueOf(39), "specfirmware");
     maptemp.put(Integer.valueOf(38), "specprog");
     maptemp.put(Integer.valueOf(37), "specspots");
     maptemp.put(Integer.valueOf(45), "updateconfig");
     maptemp.put(Integer.valueOf(4), "uploadfile");
     return maptemp;
   }
   
   protected PrivilegeVo getPrivilegeToUser(int cstmid, int userid)
   {
     Set<Integer> privileges = getUserPrivilege(cstmid, userid);
     
     PrivilegeVo pinstance = new PrivilegeVo();
     for (Integer p : privileges) {
       try
       {
         if (map.containsKey(p)) {
           PropertyUtils.setProperty(pinstance, (String)map.get(p), Boolean.valueOf(true));
         }
       }
       catch (Exception e)
       {
         log.error(e.getMessage());
       }
     }
     return pinstance;
   }
   
   protected PrivilegeVo getPrivilegeIsAdmin()
   {
     PrivilegeVo pvo = new PrivilegeVo();
     for (Method m : PrivilegeVo.class.getDeclaredMethods()) {
       if (m.getName().startsWith("set")) {
         try
         {
           m.invoke(pvo, new Object[] { Boolean.valueOf(true) });
         }
         catch (Exception e)
         {
           log.error(e.getMessage(), e);
         }
       }
     }
     return pvo;
   }
   
   public Set<Integer> getUserPrivilege(int cstmid, int userid)
   {
     Set<Integer> privilegeKeys = new HashSet();
     
     List<Role> roles = UserRoleTable.readUserAllRole(new DbShell(cstmid), userid);
     for (Role r : roles)
     {
       Set<Integer> ps = convertPrivilegeToString(
         RolePrivilegeTable.readRoleAllPrivilege(new DbShell(cstmid), r.getRoleId()));
       
       privilegeKeys.addAll(ps);
     }
     return privilegeKeys;
   }
   
   private Set<Integer> convertPrivilegeToString(List<Privilege> privileges)
   {
     Set<Integer> set = new HashSet();
     for (Privilege p : privileges) {
       set.add(Integer.valueOf(p.getPrivilegeId()));
     }
     return set;
   }
   
   public void logEvent(String options, String event)
   {
     try
     {
       LogHelp.userEvent(getCstmId(), getUserName(), options, event);
     }
     catch (Exception localException) {}
   }
   
   public int getUserId()
   {
     return this.userId;
   }
   
   public String getUserName()
   {
     return this.userName;
   }
   
   public int getCstmId()
   {
     return this.cstmId;
   }
   
   public String getShortName()
   {
     return this.shortName;
   }
   
   public void setServletContext(ServletContext context)
   {
     this.servletContext = context;
   }
   
   public void setServletRequest(HttpServletRequest request)
   {
     this.servletRequest = request;
   }
   
   public void setServletResponse(HttpServletResponse response)
   {
     this.servletResponse = response;
   }
   
   public void setApplication(Map<String, Object> application)
   {
     this.applicationMap = application;
   }
   
   public void setRequest(Map<String, Object> request)
   {
     this.requestMap = request;
   }
   
   public void setSession(Map<String, Object> session)
   {
     String uploadSessionid = this.servletRequest.getParameter("UPLOAD_SESSIONID");
     if (uploadSessionid != null)
     {
       HttpSession httpSession = MySessionContext.getInstance().getSession(uploadSessionid);
       session = new HashMap();
       Enumeration<?> enumRation = httpSession.getAttributeNames();
       while (enumRation.hasMoreElements())
       {
         String name = enumRation.nextElement().toString();
         session.put(name, httpSession.getAttribute(name));
       }
     }
     this.sessionMap = session;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.BaseAction
 * JD-Core Version:    0.7.0.1
 */