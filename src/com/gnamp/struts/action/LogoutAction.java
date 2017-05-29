 package com.gnamp.struts.action;
 
 import java.util.Map;
 import org.apache.log4j.Logger;
 
 public class LogoutAction
   extends BaseAction
 {
   private static final Logger log = Logger.getLogger(LogoutAction.class);
   private static final String DOMAIN_LOGIN = "domain_login";
   
   public String execute()
     throws Exception
   {
     boolean domain = false;
     try
     {
       this.sessionMap.remove("USER_ID");
       this.sessionMap.remove("CSTM_ID");
       this.sessionMap.remove("SHORT_NAME");
       this.sessionMap.remove("USER_NAME");
       
       Object obj = this.sessionMap.remove("login.domain");
       domain = (obj != null) && (obj.toString().trim().equalsIgnoreCase("true"));
     }
     catch (Exception e)
     {
       log.error(e.getMessage());
     }
     return !domain ? "success" : "domain_login";
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.LogoutAction
 * JD-Core Version:    0.7.0.1
 */