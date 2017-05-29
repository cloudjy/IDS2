 package com.gnamp.struts.admin.action;
 
 import java.util.Map;
 import org.apache.log4j.Logger;
 
 public class LogoutAction
   extends AdminBaseAction
 {
   private static final Logger log = Logger.getLogger(LogoutAction.class);
   
   public String execute()
     throws Exception
   {
     try
     {
       this.sessionMap.remove("ADMIN_USER_ID");
       this.sessionMap.remove("ADMIN_USER_NAME");
     }
     catch (Exception e)
     {
       log.error(e.getMessage());
     }
     return "success";
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.admin.action.LogoutAction
 * JD-Core Version:    0.7.0.1
 */