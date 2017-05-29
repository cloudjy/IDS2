package com.gnamp.struts.admin.filter;

import com.gnamp.struts.action.FileUploadAction;
import com.gnamp.struts.admin.action.AppUploadAction;
import com.gnamp.struts.admin.action.KernelUploadAction;
import com.gnamp.struts.filter.Context;
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

public class AdminPermissionIntercept
  extends AbstractInterceptor
{
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
    if ((!sessionMap.containsKey("ADMIN_USER_ID")) || 
      (!sessionMap.containsKey("ADMIN_USER_NAME")))
    {
      Object action = invocation.getAction();
      if ((action != null) && (((action instanceof FileUploadAction)) || 
        ((action instanceof KernelUploadAction)) || 
        ((action instanceof AppUploadAction))))
      {
        writeErrorToUploader();
        return "";
      }
      return "admin-login";
    }
    return invocation.invoke();
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
