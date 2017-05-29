 package com.gnamp.struts.admin.action;
 
 import com.gnamp.server.ISystemUserInfo;
 import com.gnamp.struts.filter.MySessionContext;
 import com.opensymphony.xwork2.ActionSupport;
 import com.opensymphony.xwork2.Preparable;
 import java.util.Enumeration;
 import java.util.HashMap;
 import java.util.Map;
 import javax.servlet.ServletContext;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import javax.servlet.http.HttpSession;
 import org.apache.struts2.ServletActionContext;
 import org.apache.struts2.interceptor.ServletRequestAware;
 import org.apache.struts2.interceptor.ServletResponseAware;
 import org.apache.struts2.interceptor.SessionAware;
 import org.apache.struts2.util.ServletContextAware;
 
 public class AdminBaseAction
   extends ActionSupport
   implements ServletRequestAware, ServletResponseAware, SessionAware, Preparable, ServletContextAware, ISystemUserInfo
 {
   private static final long serialVersionUID = 2752327779434251326L;
   protected int userId = 0;
   protected String userName = null;
   protected String shortName = null;
   protected HttpServletRequest request = null;
   protected HttpServletResponse response = null;
   protected Map<String, Object> sessionMap = null;
   protected ServletContext servletContext;
   public static final String SESSION_USER_NAME = "ADMIN_USER_NAME";
   public static final String SESSION_USER_ID = "ADMIN_USER_ID";
   
   public String getTheme()
   {
     return getBasePath();
   }
   
   private String getBasePath()
   {
     return 
     
       this.request.getScheme() + "://" + this.request.getServerName() + ":" + this.request.getServerPort() + this.request.getContextPath();
   }
   
   public void prepare()
     throws Exception
   {
     if (this.sessionMap != null)
     {
       if ((!this.sessionMap.containsKey("ADMIN_USER_ID")) || 
         (!this.sessionMap.containsKey("ADMIN_USER_NAME"))) {
         return;
       }
       this.userId = ((Integer)this.sessionMap.get("ADMIN_USER_ID")).intValue();
       this.userName = ((String)this.sessionMap.get("ADMIN_USER_NAME"));
     }
     else
     {
       HttpSession session = ServletActionContext.getRequest().getSession();
       
       this.userId = ((Integer)session.getAttribute("ADMIN_USER_ID")).intValue();
       this.userName = ((String)session.getAttribute("ADMIN_USER_NAME"));
     }
   }
   
   protected HttpSession getSession()
   {
     return this.request.getSession();
   }
   
   public void setServletResponse(HttpServletResponse httpServletResponse)
   {
     this.response = httpServletResponse;
   }
   
   public void setServletRequest(HttpServletRequest httpServletRequest)
   {
     this.request = httpServletRequest;
   }
   
   public void setSession(Map<String, Object> session)
   {
     String uploadSessionid = this.request.getParameter("UPLOAD_SESSIONID");
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
   
   public int getUserId()
   {
     return ((Integer)this.sessionMap.get("ADMIN_USER_ID")).intValue();
   }
   
   public String getUserName()
   {
     return (String)this.sessionMap.get("ADMIN_USER_NAME");
   }
   
   public void setServletContext(ServletContext servletContext)
   {
     this.servletContext = servletContext;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.admin.action.AdminBaseAction
 * JD-Core Version:    0.7.0.1
 */