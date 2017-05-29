 package com.gnamp.struts.action;
 
 import com.gnamp.struts.utils.JsonUtils;
 import java.io.PrintStream;
 import java.util.Collection;
 import java.util.Map;
 
 public class InputErrors
   extends BaseAction
 {
   public void writeErrors()
   {
     System.out.println(getFieldErrors().size());
     System.out.println(getActionErrors().size());
     System.out.println(getActionMessages().size());
     JsonUtils.writeErrorData(this.servletResponse, getFieldErrors());
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.InputErrors
 * JD-Core Version:    0.7.0.1
 */