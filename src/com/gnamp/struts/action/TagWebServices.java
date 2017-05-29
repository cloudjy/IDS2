 package com.gnamp.struts.action;
 
 import com.gnamp.server.handle.FileTagHandle;
 import com.gnamp.server.handle.LoginException;
 import com.gnamp.server.handle.LoginHandle;
 import com.gnamp.server.handle.TagHandleFactory;
 import com.gnamp.server.handle.TextTagHandle;
 import com.gnamp.server.model.Customer;
 import com.gnamp.server.model.MarqueeXML;
 import com.gnamp.server.model.Source;
 import com.gnamp.server.model.User;
 import com.gnamp.struts.utils.CrcUtil;
 import com.gnamp.struts.vo.UploadModel;
 import java.util.ArrayList;
 import java.util.List;
 import org.springframework.util.Assert;
 
 public class TagWebServices
 {
   public static final String NO_CONTENT = "NO_CONTENT";
   public static final String NOT_EXIST_CROP = "NOT_EXIST_CROP";
   public static final String USERNAME_OR_PASSWORD_ERROR = "USERNAME_OR_PASSWORD_ERROR";
   public static final String NOT_FOUNT_TAG = "NOT_FOUNT_TAG";
   public static final String SUCCESS = "SUCCESS";
   public static final String ERROR = "ERROR";
   
   public String addTextOfTag(String szDomain, String szUid, String szPwd, int id, String... text)
   {
     try
     {
       Assert.notNull(text, "NO_CONTENT");
       
       LoginUser user = validateLogin(szUid, szPwd, szDomain);
       
       TextTagHandle handle = new TextTagHandle(user);
       
 
       Source source = new Source();
       source.setSourceId(id);
       
       List<MarqueeXML> marquees = new ArrayList();
       for (String str : text)
       {
         MarqueeXML marquee = CrcUtil.build(str);
         TextTagHandle.genarateId(marquees, marquee);
         marquees.add(marquee);
       }
       try
       {
         handle.addMarquees(source, true, (MarqueeXML[])marquees.toArray(new MarqueeXML[0]));
       }
       catch (Exception e)
       {
         e.printStackTrace();
         throw new Exception("NOT_FOUNT_TAG");
       }
     }
     catch (LoginException e)
     {
       return "USERNAME_OR_PASSWORD_ERROR";
     }
     catch (Exception e)
     {
       e.printStackTrace();
       return e.getMessage();
     }
     return "SUCCESS";
   }
   
   private LoginUser validateLogin(String szUid, String szPwd, String szDomain)
     throws LoginException
   {
     Customer cust = LoginHandle.getCustomer(szDomain);
     
     Assert.notNull(cust, "NOT_EXIST_CROP");
     
     User custuser = null;
     
     custuser = LoginHandle.Authenticate(cust.getCstmId(), szUid, szPwd);
     
     Assert.notNull(custuser, "USERNAME_OR_PASSWORD_ERROR");
     
     return new LoginUser(custuser, cust);
   }
   
   public String addFileOfTag(String szDomain, String szUid, String szPwd, int id, UploadFile... file)
   {
     try
     {
       System.gc();
       
       LoginUser user = validateLogin(szUid, szPwd, szDomain);
       
       FileTagHandle fileHandle = (FileTagHandle)TagHandleFactory.getSourceTagHandleById(user, id);
       if (file == null) {
         file = new UploadFile[0];
       }
       fileHandle.add(id, new UploadModel(file));
     }
     catch (LoginException e)
     {
       return "USERNAME_OR_PASSWORD_ERROR";
     }
     catch (Exception e)
     {
       return "ERROR";
     }
     return "SUCCESS";
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.TagWebServices
 * JD-Core Version:    0.7.0.1
 */