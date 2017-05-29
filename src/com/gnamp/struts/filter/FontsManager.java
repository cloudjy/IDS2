 package com.gnamp.struts.filter;
 
 import com.gnamp.server.handle.font.FontTool;
 
 public final class FontsManager
 {
   public static String[] getFontNames()
   {
     return FontTool.getFontNames();
   }
   
   public static String getFontFilePath(String fontName)
   {
     return FontTool.getFontFilePath(fontName);
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.filter.FontsManager
 * JD-Core Version:    0.7.0.1
 */