 package com.gnamp.struts.filter;
 
 import java.util.HashMap;
 import java.util.Map;
 import javax.servlet.http.HttpSession;
 import javax.servlet.http.HttpSessionEvent;
 import javax.servlet.http.HttpSessionListener;
 
 public class UploadSessionListener
   implements HttpSessionListener
 {
   public static Map<String, Object> userMap = new HashMap();
   private MySessionContext myc = MySessionContext.getInstance();
   
   public void sessionCreated(HttpSessionEvent httpSessionEvent)
   {
     this.myc.AddSession(httpSessionEvent.getSession());
   }
   
   public void sessionDestroyed(HttpSessionEvent httpSessionEvent)
   {
     HttpSession session = httpSessionEvent.getSession();
     this.myc.DelSession(session);
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.filter.UploadSessionListener
 * JD-Core Version:    0.7.0.1
 */