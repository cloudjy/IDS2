 package com.gnamp.struts.action;
 
 import com.gnamp.struts.filter.Context;
 import com.gnamp.struts.utils.MapTool;
 import common.Logger;
 import java.awt.Font;
 import java.util.Arrays;
 
 public class FontAction
   extends JSONAction
 {
   private static final long serialVersionUID = 4152519486489125974L;
   
   public void getFonts()
   {
     try
     {
       response(JSONSuccessString(JSONArrayToString(Arrays.asList(Context.getFonts())), 
         new MapTool().putObject("fontName", new Font(null, 0, 0).getFamily())));
     }
     catch (Exception e)
     {
       log.error(e);
       response(JSONErrorString(e.getMessage()));
     }
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.FontAction
 * JD-Core Version:    0.7.0.1
 */