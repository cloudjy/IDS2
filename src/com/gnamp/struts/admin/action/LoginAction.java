 package com.gnamp.struts.admin.action;
 
 import com.gnamp.server.handle.LoginHandle;
 import com.gnamp.server.model.SystemUser;
 import com.gnamp.server.model.User;
 import com.gnamp.struts.filter.Context;
 import com.gnamp.struts.utils.VerifyCode;
 import com.opensymphony.xwork2.ActionContext;
 import common.Logger;
 import java.io.IOException;
 import java.util.Locale;
 import java.util.Map;
 import javax.imageio.ImageIO;
 import javax.servlet.http.Cookie;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import javax.servlet.http.HttpSession;
 import org.apache.struts2.ServletActionContext;
 
 public class LoginAction
   extends JSONAction
 {
   private static final Logger log = Logger.getLogger(LoginAction.class);
   User user = new User();
   String loginVerifyCode;
   private static final String LOGIN_VERIFY = "admin_login_code";
   private boolean autoSave;
   
   public String getLoginVerifyCode()
   {
     return this.loginVerifyCode;
   }
   
   public void setLoginVerifyCode(String loginVerifyCode)
   {
     this.loginVerifyCode = loginVerifyCode;
   }
   
   public User getUser()
   {
     return this.user;
   }
   
   public void setUser(User user)
   {
     this.user = user;
   }
   
   public void verifyCode()
   {
     VerifyCode verifyCode = new VerifyCode();
     try
     {
       this.sessionMap.put("admin_login_code", verifyCode.getVerifyCode());
       ImageIO.write(verifyCode.getVerifyImage(), "JPG", this.response.getOutputStream());
     }
     catch (IOException e)
     {
       e.printStackTrace();
     }
   }
   
   public void validateUserValidate()
   {
     if (this.user == null)
     {
       addFieldError("user", getText("loginerror"));
       return;
     }
     if ((getLoginVerifyCode() == null) || ("".equals(getLoginVerifyCode().trim())))
     {
       addFieldError("loginVerifyCode", getText("verifyisempty"));
     }
     else if ((getLoginVerifyCode().length() != 4) || (!getLoginVerifyCode().equalsIgnoreCase(this.sessionMap.get("admin_login_code").toString())))
     {
       addFieldError("loginVerifyCode", getText("verifyiserror"));
       this.sessionMap.put("admin_login_code", "error");
     }
   }
   
   public String execute()
     throws Exception
   {
     if (Context.isEnglishVersion())
     {
       ServletActionContext.getContext().setLocale(Locale.US);
       this.sessionMap.put("WW_TRANS_I18N_LOCALE", Locale.US);
     }
     else if (Context.isTraditionalChineseVersion())
     {
       ServletActionContext.getContext().setLocale(Locale.TRADITIONAL_CHINESE);
       this.sessionMap.put("WW_TRANS_I18N_LOCALE", Locale.TRADITIONAL_CHINESE);
     }
     return super.execute();
   }
   
   public void userValidate()
   {
     try
     {
       SystemUser usersystem = null;
       try
       {
         usersystem = LoginHandle.Authenticate(this.user.getUserName(), this.user.getPassword());
       }
       catch (Exception e)
       {
         response(JSONErrorString(getText("usernameorpassworderror")));
         return;
       }
       if (usersystem != null)
       {
         HttpSession session = ServletActionContext.getRequest().getSession();
         session.setAttribute("ADMIN_USER_ID", Integer.valueOf(usersystem.getUserId()));
         session.setAttribute("ADMIN_USER_NAME", usersystem.getUserName());
         
         this.sessionMap.put("ADMIN_USER_ID", Integer.valueOf(usersystem.getUserId()));
         this.sessionMap.put("ADMIN_USER_NAME", usersystem.getUserName());
         if (isAutoSave()) {
           saveCookieOfName(getUser());
         } else {
           clearCookieSave();
         }
         response(JSONSuccessString());
       }
       else
       {
         response(JSONErrorString(getText("usernameorpassworderror")));
       }
     }
     catch (Exception e)
     {
       log.error(e.getMessage());
     }
   }
   
   public boolean isAutoSave()
   {
     return this.autoSave;
   }
   
   public void setAutoSave(boolean autoSave)
   {
     this.autoSave = autoSave;
   }
   
   private void saveCookieOfName(User userParam)
   {
     Cookie usernameCookie = new Cookie("cookie.admin.username", userParam.getUserName());
     usernameCookie.setMaxAge(31536000);
     this.response.addCookie(usernameCookie);
     
     Cookie passwordCookie = new Cookie("cookie.admin.password", userParam.getPassword());
     passwordCookie.setMaxAge(31536000);
     this.response.addCookie(passwordCookie);
     
     Cookie autoSaveCookie = new Cookie("cookie.admin.autosave", String.valueOf(isAutoSave()));
     autoSaveCookie.setMaxAge(31536000);
     this.response.addCookie(autoSaveCookie);
     
 
     this.sessionMap.put("admin_login_code", "error");
   }
   
   private void clearCookieSave()
   {
     Cookie[] cookies = this.request.getCookies();
     for (Cookie cookie : cookies) {
       if ((cookie.getName().equals("cookie.admin.username")) || (cookie.getName().equals("cookie.admin.autosave")))
       {
         cookie.setMaxAge(0);
         this.response.addCookie(cookie);
       }
     }
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.admin.action.LoginAction
 * JD-Core Version:    0.7.0.1
 */