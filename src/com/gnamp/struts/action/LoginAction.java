 package com.gnamp.struts.action;
 
 import com.gnamp.server.Storage;
 import com.gnamp.server.handle.CustomerHandle;
 import com.gnamp.server.handle.FileHandle;
 import com.gnamp.server.handle.LoginHandle;
 import com.gnamp.server.handle.TerminalHandle;
 import com.gnamp.server.model.Customer;
 import com.gnamp.server.model.User;
 import com.gnamp.server.utils.DomUtils;
 import com.gnamp.struts.filter.Context;
 import com.gnamp.struts.filter.FontsManager;
 import com.gnamp.struts.filter.LoginUser;
 import com.gnamp.struts.utils.JsonUtils;
 import com.gnamp.struts.utils.VerifyCode;
 import com.opensymphony.xwork2.ActionContext;
 import java.io.FileInputStream;
 import java.io.IOException;
 import java.io.OutputStream;
 import java.io.PrintStream;
 import java.io.UnsupportedEncodingException;
 import java.util.Locale;
 import java.util.Map;
 import java.util.Random;
 import javax.imageio.ImageIO;
 import javax.servlet.ServletOutputStream;
 import javax.servlet.http.Cookie;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import javax.servlet.http.HttpSession;
 import org.apache.log4j.Logger;
 import org.apache.struts2.ServletActionContext;
 import org.w3c.dom.Document;
 import org.w3c.dom.Element;
 import org.w3c.dom.Node;
 import org.w3c.dom.NodeList;
 
 public class LoginAction
   extends JSONAction
 {
   private static final Logger log = Logger.getLogger(LoginAction.class);
   private static final String LOGIN_VERIFY = "LOGIN_CODE";
   private User mUser = null;
   private String mLoginVerifyCode;
   private String mShortName;
   private boolean mAutoSave;
   private boolean mAutoLogin;
   
   public User getUser()
   {
     return this.mUser;
   }
   
   public void setUser(User user)
   {
     this.mUser = user;
   }
   
   public String getLoginVerifyCode()
   {
     return this.mLoginVerifyCode;
   }
   
   public void setLoginVerifyCode(String loginVerifyCode)
   {
     this.mLoginVerifyCode = loginVerifyCode;
   }
   
   public String getShortName()
   {
     return this.mShortName;
   }
   
   public void setShortName(String shortName)
   {
     this.mShortName = shortName;
   }
   
   public boolean isAutoSave()
   {
     return this.mAutoSave;
   }
   
   public void setAutoSave(boolean autoSave)
   {
     this.mAutoSave = autoSave;
   }
   
   public boolean isAutoLogin()
   {
     return this.mAutoLogin;
   }
   
   public void setAutoLogin(boolean autoLogin)
   {
     this.mAutoLogin = autoLogin;
   }
   
   private boolean mAllowAutoLogin = true;
   
   public boolean getAllowAutoLogin()
   {
     return this.mAllowAutoLogin;
   }
   
   public void setAllowAutoLogin(boolean allowAutoLogin)
   {
     this.mAllowAutoLogin = allowAutoLogin;
   }
   
   public void validateUserValidate()
   {
     if (this.mUser == null)
     {
       addFieldError("user", getText("loginerror"));
       return;
     }
     if ((getLoginVerifyCode() == null) || 
       ("".equals(getLoginVerifyCode().trim())))
     {
       addFieldError("loginVerifyCode", getText("verifyisempty"));
     }
     else if ((getLoginVerifyCode().length() != 4) || 
       (!getLoginVerifyCode().equalsIgnoreCase(loginVerifyCode())))
     {
       addFieldError("loginVerifyCode", getText("verifyiserror"));
       
       this.sessionMap.put("LOGIN_CODE", "error");
     }
   }
   
   private String loginVerifyCode()
   {
     return this.sessionMap.get("LOGIN_CODE") == null ? "" : this.sessionMap.get("LOGIN_CODE").toString();
   }
   
   public void userValidate()
   {
     try
     {
       Customer cust = LoginHandle.getCustomer(this.mShortName);
       if (cust == null)
       {
         JsonUtils.writeError(this.servletResponse, 
           getText("cstmnotfound"), "InvalidCustomer");
         return;
       }
       User custuser = null;
       try
       {
         custuser = LoginHandle.Authenticate(cust.getCstmId(), 
           this.mUser.getUserName(), this.mUser.getPassword());
       }
       catch (Exception e)
       {
         JsonUtils.writeError(this.servletResponse, 
           getText("usernameorpassworderror"), 
           "InvalidUserPassord");
         return;
       }
       if (custuser != null)
       {
         HttpSession session = ServletActionContext.getRequest().getSession();
         session.setAttribute("CSTM_ID", Integer.valueOf(cust.getCstmId()));
         session.setAttribute("SHORT_NAME", this.mShortName);
         session.setAttribute("USER_ID", Integer.valueOf(custuser.getUserId()));
         session.setAttribute("USER_NAME", custuser.getUserName());
         
         this.sessionMap.put("CSTM_ID", Integer.valueOf(cust.getCstmId()));
         this.sessionMap.put("SHORT_NAME", this.mShortName);
         this.sessionMap.put("USER_ID", Integer.valueOf(custuser.getUserId()));
         this.sessionMap.put("USER_NAME", custuser.getUserName());
         
         this.sessionMap.put("current_privilege", 
           "admin".equals(custuser.getUserName()) ? getPrivilegeIsAdmin() : 
           getPrivilegeToUser(cust.getCstmId(), 
           custuser.getUserId()));
         
         String domain_login = this.servletRequest.getParameter("domain_login");
         domain_login = domain_login != null ? domain_login.trim() : "";
         this.sessionMap.put("login.domain", domain_login);
         if (isAutoSave()) {
           saveCookieOfName(cust, getUser());
         } else {
           clearCookieSave();
         }
         LoginUser _loginUser = new LoginUser();
         _loginUser.setCstm(this.mShortName);
         _loginUser.setUsername(this.mUser.getUserName());
         _loginUser.setPassword(this.mUser.getPassword());
         _loginUser.setAdmin("admin".equals(custuser.getUserName()));
         this.sessionMap.put("current.user", _loginUser);
         
         clearCache();
         
         response(JSONSuccessString());
       }
       else
       {
         JsonUtils.writeError(this.servletResponse, 
           getText("usernameorpassworderror"), 
           "InvalidUserPassord");
       }
     }
     catch (Exception e)
     {
       log.error(e.getMessage());
     }
   }
   
   private void clearCache()
   {
     this.servletResponse.setHeader("Cache-Control", "no-cache");
     this.servletResponse.setHeader("Pragma", "no-cache");
   }
   
   private void saveCookieOfName(Customer cust, User userParam)
   {
     Cookie cookie = new Cookie("cookie.cstm", 
       cust.getShortName());
     cookie.setMaxAge(31536000);
     this.servletResponse.addCookie(cookie);
     
     Cookie usernameCookie = new Cookie("cookie.username", 
       userParam.getUserName());
     usernameCookie.setMaxAge(31536000);
     this.servletResponse.addCookie(usernameCookie);
     
     Cookie passwordCookie = new Cookie("cookie.password", 
       userParam.getPassword());
     passwordCookie.setMaxAge(31536000);
     this.servletResponse.addCookie(passwordCookie);
     
     Cookie autoSaveCookie = new Cookie("cookie.autosave", 
       String.valueOf(isAutoSave()));
     autoSaveCookie.setMaxAge(31536000);
     this.servletResponse.addCookie(autoSaveCookie);
     
     Cookie autoLoginCookie = new Cookie("cookie.autologin", 
       String.valueOf(isAutoLogin()));
     autoLoginCookie.setMaxAge(31536000);
     this.servletResponse.addCookie(autoLoginCookie);
     
     logEvent(getEnglish("userlogin"), getEnglish("userlogin") + "：" + 
       userParam.getUserName());
     
     this.sessionMap.put("LOGIN_CODE", "error");
   }
   
   private void clearCookieSave()
   {
     Cookie[] cookies = this.servletRequest.getCookies();
     for (Cookie cookie : cookies) {
       if ((cookie.getName().equals("cookie.username")) || 
         (cookie.getName().equals("cookie.cstm")) || 
         (cookie.getName().equals("cookie.autosave")) || 
         (cookie.getName().equals("cookie.autologin")) || 
         (cookie.getName().equals("cookie.password")))
       {
         cookie.setMaxAge(0);
         this.servletResponse.addCookie(cookie);
       }
     }
   }
   
   public void verifyCode()
   {
     VerifyCode verifyCode = new VerifyCode();
     try
     {
       this.sessionMap.put("LOGIN_CODE", verifyCode.getVerifyCode());
       ImageIO.write(verifyCode.getVerifyImage(), "JPG", this.servletResponse.getOutputStream());
     }
     catch (IOException e)
     {
       log.error(e.getMessage());
     }
   }
   
   public void autoLoginVerifyCode()
   {
     try
     {
       String code = String.format("%04d", new Object[] { Integer.valueOf(new Random().nextInt(9999)) });
       this.sessionMap.put("LOGIN_CODE", code);
       JsonUtils.writeSuccess(this.servletResponse, code, code);
     }
     catch (Exception e)
     {
       log.error(e.getMessage());
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
   
   private int mPipeStatus = 0;
   
   public int getStatus()
   {
     return this.mPipeStatus;
   }
   
   public void setStatus(int status)
   {
     this.mPipeStatus = status;
   }
   
   private long mDevId = 0L;
   private int mTaskId;
   
   public long getDevId()
   {
     return this.mDevId;
   }
   
   public void setDevId(long devId)
   {
     this.mDevId = devId;
   }
   
   public void PipeLogs()
   {
     try
     {
       Customer cust = CustomerHandle.getDeviceCustomer(this.mDevId);
       if (cust == null) {
         throw new HttpException("invalid terminal[" + this.mDevId + "] !");
       }
       String dir = Storage.getCstmBase(cust.getCstmId());
       
       Document logdoc = null;
       NodeList xmlNlist = null;
       Node node = null;
       try
       {
         logdoc = DomUtils.load(this.servletRequest.getInputStream());
         
         xmlNlist = DomUtils.selectNodes(logdoc, 
           "/winelog/check[@dev_id and @start_time and @end_time and @result]");
         for (int i = 0; i < xmlNlist.getLength(); i++)
         {
           node = xmlNlist.item(i);
           String dev_id = DomUtils.getAttributeString((Element)node, "dev_id");
           String start_time = DomUtils.getAttributeString((Element)node, "start_time");
           String end_time = DomUtils.getAttributeString((Element)node, "end_time");
           String result = DomUtils.getAttributeString((Element)node, "result");
           
           LogHelp.pipeLog(dir, dev_id, start_time, end_time, result);
         }
       }
       catch (IOException e)
       {
         System.out.print(e.getMessage());
       }
       this.servletResponse.setStatus(200);
       this.servletResponse.addHeader("Tenhz", "OK");
     }
     catch (HttpException e)
     {
       this.servletResponse.reset();
       this.servletResponse.addHeader("Tenhz", "ERROR");
       this.servletResponse.setStatus(400);
     }
   }
   
   public void PipeStatus()
   {
     try
     {
       TerminalHandle.updateAliveInterval(this.mDevId, this.mPipeStatus);
       
       this.servletResponse.setStatus(200);
       this.servletResponse.addHeader("Tenhz", "OK");
     }
     catch (Exception e)
     {
       this.servletResponse.reset();
       this.servletResponse.addHeader("Tenhz", "ERROR");
       this.servletResponse.setStatus(400);
     }
   }
   
   public int getTaskId()
   {
     return this.mTaskId;
   }
   
   public void setTaskId(int taskId)
   {
     this.mTaskId = taskId;
   }
   
   private String mListType = "";
   
   public String getListType()
   {
     return this.mListType;
   }
   
   public void setListType(String listType)
   {
     this.mListType = listType;
   }
   
   private long mFileId = 0L;
   
   public long getFileId()
   {
     return this.mFileId;
   }
   
   public void setFileId(long fileId)
   {
     this.mFileId = fileId;
   }
   
   private String mFileName = "";
   
   public String getFileName()
   {
     return this.mFileName;
   }
   
   public void setFileName(String name)
   {
     try
     {
       this.mFileName = new String(name.getBytes("ISO-8859-1"), "utf-8");
     }
     catch (UnsupportedEncodingException e)
     {
       this.mFileName = name;
     }
   }
   
   private String mFontName = "";
   
   public String getFontName()
   {
     return this.mFontName;
   }
   
   public void setFontName(String fontName)
   {
     try
     {
       this.mFontName = new String(fontName.getBytes("ISO-8859-1"), "utf-8");
     }
     catch (UnsupportedEncodingException e)
     {
       this.mFontName = fontName;
     }
   }
   
   private String mFontChar = "";
   
   public String getfKey()
   {
     return this.mFontChar;
   }
   
   public void setfKey(String fKey)
   {
     this.mFontChar = fKey;
   }
   
   public void GetLogin()
   {
     try
     {
       Customer cust = LoginHandle.getCustomer(this.mShortName);
       User custuser = cust == null ? null : LoginHandle.Authenticate(cust.getCstmId(), 
         this.mUser.getUserName(), this.mUser.getPassword());
       if (custuser != null)
       {
         this.sessionMap.put("CSTM_ID", Integer.valueOf(cust.getCstmId()));
         this.sessionMap.put("SHORT_NAME", this.mShortName);
         this.sessionMap.put("USER_ID", Integer.valueOf(custuser.getUserId()));
         this.sessionMap.put("USER_NAME", custuser.getUserName());
         
 
 
 
         this.userId = ((Integer)this.sessionMap.get("USER_ID")).intValue();
         this.userName = ((String)this.sessionMap.get("USER_NAME"));
         this.cstmId = ((Integer)this.sessionMap.get("CSTM_ID")).intValue();
         this.mShortName = ((String)this.sessionMap.get("SHORT_NAME"));
         
         this.servletResponse.setStatus(200);
       }
       else
       {
         this.servletResponse.setStatus(400);
       }
       this.servletResponse.flushBuffer();
       this.servletResponse.getOutputStream().close();
     }
     catch (Exception e)
     {
       log.error("[EXP_D] GetLogin Exception: " + e.getMessage());
       this.servletResponse.setStatus(500);
     }
   }
   
   public void GetPathList()
   {
     try
     {
       Customer cust = LoginHandle.getCustomer(this.mShortName);
       if (cust == null) {
         throw new HttpException("invalid customer[" + this.mShortName + "] !");
       }
       Storage storage = new Storage(cust.getCstmId());
       
 
 
 
 
 
 
       String filepath = "";
       if (this.mListType.equals("FILELIST_PATH")) {
         filepath = storage.getFileListPath(this.mTaskId);
       }
       if (this.mListType.equals("PRGMLIST_PATH")) {
         filepath = storage.getProgramListPath(this.mTaskId);
       }
       if (this.mListType.equals("TASKLIST_PATH")) {
         filepath = storage.getTaskListPath(this.mTaskId);
       }
       if (this.mListType.equals("TACTICLIST_PATH")) {
         filepath = storage.getStrategyListPath(this.mTaskId);
       }
       java.io.File f = new java.io.File(filepath);
       if (!f.exists()) {
         throw new HttpException("Server error, customer[" + 
           cust.getCstmId() + "]  download file[" + this.mFileId + 
           "]  but not find[" + filepath + "] !");
       }
       processFileDowload(filepath);
     }
     catch (Exception ex)
     {
       processDowloadException(ex);
       
       log.error("[EXP_D] GetPathList Exception: " + ex.getMessage());
       ex.printStackTrace();
     }
   }
   
   public void GetTaskFile()
   {
     String filepath = "";
     try
     {
       Customer cust = LoginHandle.getCustomer(this.mShortName);
       if (cust == null) {
         throw new HttpException("invalid customer[" + this.mShortName + "] !");
       }
       Storage storage = new Storage(cust.getCstmId());
       if ((this.mFileId > 0L) && (this.mFileId < 2147483647L)) {
         filepath = 
         
           Storage.getWorkRootPath() + "buildin" + java.io.File.separatorChar + "files" + java.io.File.separatorChar + this.mFileId;
       } else {
         try
         {
           int flag = FileHandle.readExport(this.mFileId).getFlag();
           
 
 
 
           filepath = storage.getFilePath(this.mFileId);
         }
         catch (Exception w)
         {
           filepath = storage.getCombinFilePath(this.mFileId);
         }
       }
       java.io.File f = new java.io.File(filepath);
       if (!f.exists()) {
         throw new HttpException("Server error, customer[" + 
           cust.getCstmId() + "]  download file[" + this.mFileId + 
           "]  but not find[" + filepath + "] !");
       }
       processFileDowload(filepath);
     }
     catch (Exception ex)
     {
       processDowloadException(ex);
       
       log.error("[EXP_D] GetTaskFile Exception: " + ex.getMessage());
       ex.printStackTrace();
     }
   }
   
   public void GetTextFile()
   {
     try
     {
       Customer cust = LoginHandle.getCustomer(this.mShortName);
       if (cust == null) {
         throw new HttpException("invalid customer[" + this.mShortName + "] !");
       }
       Storage storage = new Storage(cust.getCstmId());
       
       String filepath = storage.getProgramTextFileDic(this.mTaskId) + this.mFileId;
       java.io.File f = new java.io.File(filepath);
       if (!f.exists()) {
         throw new HttpException("Server error, customer[" + 
           cust.getCstmId() + "]  download file[" + this.mFileId + 
           "]  but not find[" + filepath + "] !");
       }
       processFileDowload(filepath);
     }
     catch (Exception ex)
     {
       processDowloadException(ex);
       
       log.error("[EXP_D] GetTextFile Exception: " + ex.getMessage());
       ex.printStackTrace();
     }
   }
   
   public void GetFont()
   {
     try
     {
       Customer cust = LoginHandle.getCustomer(this.mShortName);
       if (cust == null) {
         throw new HttpException("invalid customer[" + this.mShortName + "] !");
       }
       String filepath = Storage.getWorkRootPath() + "font" + 
         java.io.File.separatorChar + this.mFontName + 
         java.io.File.separatorChar + this.mFontChar;
       
       java.io.File f = new java.io.File(filepath);
       if (!f.exists()) {
         throw new HttpException("Server error, customer[" + 
           cust.getCstmId() + "]  download file[" + this.mFileId + 
           "]  but not find[" + filepath + "] !");
       }
       processFileDowload(filepath);
     }
     catch (Exception ex)
     {
       processDowloadException(ex);
       
       log.error("[EXP_D] GetFont Exception: " + ex.getMessage());
       ex.printStackTrace();
     }
   }
   
   public void GetFontFile()
   {
     try
     {
       Customer cust = LoginHandle.getCustomer(this.mShortName);
       if (cust == null) {
         throw new HttpException("invalid customer[" + this.mShortName + "] !");
       }
       String filepath = FontsManager.getFontFilePath(this.mFontName);
       java.io.File f = new java.io.File(filepath);
       if (!f.exists()) {
         throw new HttpException("Server error, customer[" + 
           cust.getCstmId() + "]  download file[" + this.mFileId + 
           "]  but not find[" + filepath + "] !");
       }
       processFileDowload(filepath);
     }
     catch (Exception ex)
     {
       processDowloadException(ex);
       
       log.error("[EXP_D] GetFontFile Exception: " + ex.getMessage());
       ex.printStackTrace();
     }
   }
   
   public void GetTTSFile()
   {
     try
     {
       Customer cust = LoginHandle.getCustomer(this.mShortName);
       if (cust == null) {
         throw new HttpException("invalid customer[" + this.mShortName + "] !");
       }
       Storage storage = new Storage(cust.getCstmId());
       java.io.File file = new java.io.File(storage.getTaskTTSDirectory(this.mTaskId), this.mFileName);
       if (!file.exists()) {
         throw new HttpException("[EXP_D][" + cust.getCstmId() + "] tts file[" + 
           this.mFileName + "] not found:" + file.getAbsolutePath());
       }
       processFileDowload(file.getAbsolutePath());
     }
     catch (Exception ex)
     {
       processDowloadException(ex);
       
       log.error("[EXP_D] GetTTSFile Exception: " + ex.getMessage());
       ex.printStackTrace();
     }
   }
   
   protected void processFileDowload(String filepath)
     throws IOException, HttpException
   {
     long offset = 0L;
     long size = 0L;
     
     java.io.File file = new java.io.File(filepath);
     if (!file.exists()) {
       throw new HttpException(500, 
         "Cannot find file: " + filepath);
     }
     size = file.length();
     
     String range = this.servletRequest.getHeader("Range");
     if (range != null)
     {
       String strlen = range.replace("bytes=", "");
       int t = strlen.indexOf('-');
       if (t > 0) {
         strlen = strlen.substring(0, t);
       }
       strlen = strlen.trim();
       if (strlen != null) {
         offset = Long.parseLong(strlen);
       }
     }
     if (offset > 0L)
     {
       if (offset > size) {
         throw new HttpException(416, 
           "Invalid offset=" + offset + " rang=" + range);
       }
       size -= offset;
       this.servletResponse.setStatus(206);
       this.servletResponse.addHeader("Content-Length", Long.toString(size));
       this.servletResponse.addHeader("Content-Range", 
         "bytes " + Long.toString(offset) + "-" + 
         Long.toString(size - 1L) + "/" + Long.toString(offset));
     }
     else
     {
       this.servletResponse.addHeader("Content-Length", Long.toString(size));
     }
     this.servletResponse.setContentType("application/octet-stream");
     
 
     FileInputStream fileStream = null;
     OutputStream outStream = null;
     
     int BUF_SIZE = 1048576;
     byte[] buf = null;
     try
     {
       fileStream = new FileInputStream(file);
       outStream = this.servletResponse.getOutputStream();
       
       fileStream.skip(offset);
       
       long send_len = 0L;
       while (send_len < size)
       {
         int data_size = (int)(size - send_len < 1048576L ? 
           size - send_len : 1048576L);
         if (buf == null) {
           buf = new byte[data_size];
         }
         int read_len = 0;
         while (read_len < data_size)
         {
           int len = fileStream.read(buf, read_len, data_size - read_len);
           if (len <= 0) {
             throw new HttpException(500, 
               "read file failed(" + len + "): " + filepath);
           }
           read_len += len;
         }
         outStream.write(buf, 0, data_size);
         outStream.flush();
         
         send_len += data_size;
       }
     }
     finally
     {
       if (outStream != null)
       {
         try
         {
           outStream.close();
         }
         catch (IOException localIOException) {}
         outStream = null;
       }
       if (fileStream != null)
       {
         try
         {
           fileStream.close();
         }
         catch (IOException localIOException1) {}
         fileStream = null;
       }
       if (buf != null)
       {
         buf = null;
         System.gc();
       }
     }
   }
   
   protected void processDowloadException(Exception ex)
   {
     try
     {
       int statusCode = (ex instanceof HttpException) ? 
         ((HttpException)ex).getStatusCode() : 400;
       this.servletResponse.reset();
       this.servletResponse.setStatus(statusCode);
       this.servletResponse.sendError(statusCode, ex.getMessage());
     }
     catch (Exception localException) {}
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.LoginAction
 * JD-Core Version:    0.7.0.1
 */