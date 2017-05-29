 package com.gnamp.struts.filter;
 
 import java.util.HashMap;
 import javax.servlet.http.HttpSession;
 
 public class MySessionContext
 {
   private static MySessionContext instance;
   private HashMap<Object, Object> mymap;
   
   private MySessionContext()
   {
     this.mymap = new HashMap();
   }
   
   public static MySessionContext getInstance()
   {
     if (instance == null) {
       instance = new MySessionContext();
     }
     return instance;
   }
   
   public synchronized void AddSession(HttpSession session)
   {
     if (session != null) {
       this.mymap.put(session.getId(), session);
     }
   }
   
   public synchronized void DelSession(HttpSession session)
   {
     if (session != null) {
       this.mymap.remove(session.getId());
     }
   }
   
   public synchronized HttpSession getSession(String session_id)
   {
     if (session_id == null) {
       return null;
     }
     return (HttpSession)this.mymap.get(session_id);
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.filter.MySessionContext
 * JD-Core Version:    0.7.0.1
 */