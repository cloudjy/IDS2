 package com.gnamp.struts.admin.action;
 
 import com.gnamp.server.Storage;
 import com.gnamp.server.handle.CustomerHandle;
 import com.gnamp.server.model.Customer;
 import com.gnamp.server.model.UserEvent;
 import com.gnamp.struts.utils.MapTool;
 import com.gnamp.struts.vo.PageBean;
 import java.io.BufferedReader;
 import java.io.File;
 import java.io.FileReader;
 import java.io.IOException;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 
 public class UserLogAction
   extends JSONAction
 {
   public String getStrCstmID()
   {
     return this.strCstmID;
   }
   
   public void setStrCstmID(String strCstmID)
   {
     this.strCstmID = strCstmID;
   }
   
   public String strCstmID = "0";
   UserEvent[] allevents = null;
   
   public UserEvent[] getAllevents()
   {
     return this.allevents;
   }
   
   public void setAllevents(UserEvent[] allevents)
   {
     this.allevents = allevents;
   }
   
   List<UserEvent> eventlist = new ArrayList();
   
   public List<UserEvent> getEventlist()
   {
     return this.eventlist;
   }
   
   public void setEventlist(List<UserEvent> eventlist)
   {
     this.eventlist = eventlist;
   }
   
   public List<Customer> cstmlist = null;
   
   public List<Customer> getCstmlist()
   {
     return this.cstmlist;
   }
   
   public void setCstmlist(List<Customer> cstmlist)
   {
     this.cstmlist = cstmlist;
   }
   
   PageBean page = new PageBean();
   
   public PageBean getPage()
   {
     return this.page;
   }
   
   public void setPage(PageBean page)
   {
     this.page = page;
   }
   
   public String prelist()
   {
     CustomerHandle handle = new CustomerHandle(this);
     this.cstmlist = handle.readAll();
     return "success";
   }
   
   private static String endWithSeparator(String path)
   {
     return path + File.separator;
   }
   
   public void list()
   {
     BufferedReader br = null;
     try
     {
       int csid = Integer.parseInt(this.strCstmID);
       if (csid > 0)
       {
         String path = endWithSeparator(Storage.getCstmBase(csid)) + "UserEvent.txt";
         
         File dir = new File(path);
         if (dir.exists())
         {
           FileReader read = new FileReader(path);
           br = new BufferedReader(read);
           
           String strLine = br.readLine();
           while ((strLine != null) && (!"".equals(strLine)))
           {
             String[] temps = strLine.split("\\|");
             
             UserEvent mod = new UserEvent();
             
             mod.setTime(temps[0]);
             mod.setUserName(temps[1]);
             mod.setOption(temps[2]);
             mod.setEvent(temps[3]);
             
             this.eventlist.add(mod);
             
             strLine = br.readLine();
           }
           this.allevents = new UserEvent[this.eventlist.size()];
           this.eventlist.toArray(this.allevents);
           
           this.page.setTotalRows(this.eventlist.size());
           response(JSONSuccessString(JSONArrayToString(this.eventlist), new Map[] { new MapTool().putObject("page", this.page) }));
         }
         else
         {
           this.page.setTotalRows(0);
           response(JSONSuccessString(JSONArrayToString(this.eventlist), new Map[] { new MapTool().putObject("page", this.page) }));
         }
       }
       else
       {
         this.page.setTotalRows(0);
         response(JSONSuccessString(JSONArrayToString(this.eventlist), new Map[] { new MapTool().putObject("page", this.page) }));
       }
     }
     catch (Exception localException)
     {
       if (br != null) {
         try
         {
           br.close();
         }
         catch (IOException localIOException) {}
       }
     }
     finally
     {
       if (br != null) {
         try
         {
           br.close();
         }
         catch (IOException localIOException1) {}
       }
     }
   }
   
   public void clearevent()
   {
     int csid = Integer.parseInt(this.strCstmID);
     
     String path = endWithSeparator(Storage.getCstmBase(csid)) + "UserEvent.txt";
     
     File dir = new File(path);
     if (dir.exists()) {
       try
       {
         dir.delete();
       }
       catch (Exception localException) {}
     }
     response(JSONSuccessString());
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.admin.action.UserLogAction
 * JD-Core Version:    0.7.0.1
 */