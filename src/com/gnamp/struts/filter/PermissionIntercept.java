 package com.gnamp.struts.filter;
 
 import com.gnamp.struts.action.FileFlexUploadAction;
 import com.gnamp.struts.admin.action.FlexAppUploadAction;
 import com.gnamp.struts.admin.action.FlexKernelUploadAction;
 import com.opensymphony.xwork2.ActionContext;
 import com.opensymphony.xwork2.ActionInvocation;
 import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
 import java.io.IOException;
 import java.io.PrintWriter;
 import java.util.Locale;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.struts2.ServletActionContext;
 
 public class PermissionIntercept
   extends AbstractInterceptor
 {
   private static final long serialVersionUID = -8963978834977836027L;
   
   public String intercept(ActionInvocation invocation)
     throws Exception
   {
     try
     {
       HttpServletResponse response = ServletActionContext.getResponse();
       
       response.setHeader("Cache-Control", "no-cache");
       response.setHeader("Pragma", "no-cache");
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
     if (ServletActionContext.getRequest().getParameter("UPLOAD_SESSIONID") != null) {
       return invocation.invoke();
     }
     Map<String, Object> sessionMap = ActionContext.getContext().getSession();
     if (Context.isEnglishVersion()) {
       sessionMap.put("WW_TRANS_I18N_LOCALE", Locale.US);
     } else if (Context.isTraditionalChineseVersion()) {
       sessionMap.put("WW_TRANS_I18N_LOCALE", Locale.TRADITIONAL_CHINESE);
     }
     if ((!sessionMap.containsKey("USER_ID")) || 
       (!sessionMap.containsKey("USER_NAME")) || 
       (!sessionMap.containsKey("CSTM_ID")) || 
       (!sessionMap.containsKey("SHORT_NAME")))
     {
       Object action = invocation.getAction();
       if ((action != null) && (((action instanceof FileFlexUploadAction)) || 
         ((action instanceof FlexKernelUploadAction)) || 
         ((action instanceof FlexAppUploadAction))))
       {
         writeErrorToUploader();
         return "";
       }
       return "login";
     }
     String result = invocation.invoke();
     return result;
   }
   
   private void writeErrorToUploader()
   {
     PrintWriter writer = null;
     try
     {
       HttpServletResponse response = ServletActionContext.getResponse();
       response.setContentType("text/html");
       response.setCharacterEncoding("UTF-8");
       writer = response.getWriter();
       writer.write("ERROR: not login");
     }
     catch (IOException ioex1)
     {
       ioex1.printStackTrace(System.out);
     }
     finally
     {
       if (writer != null) {
         writer.close();
       }
     }
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.filter.PermissionIntercept
 * JD-Core Version:    0.7.0.1
 */