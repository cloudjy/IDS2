 package com.gnamp.struts.action;
 
 import com.gnamp.struts.filter.Context;
 import java.awt.Color;
 
 public class SupportFonts
 {
   public static String[] FontName = initFonts();
   public static Color FontColor = Color.YELLOW;
   public static Color BackColor = Color.BLUE;
   public static boolean Transparent = false;
   public static int CenterH = 0;
   public static int Left = 1;
   public static int Right = 2;
   public static int AlignHNum = 3;
   public static int CenterV = 0;
   public static int Top = 1;
   public static int Bottom = 2;
   public static int AlignVNum = 3;
   public static String DefaultFontName = "";
   
   private static String[] initFonts()
   {
     return Context.getFonts();
   }
   
   public static boolean ValidFont(String font_name)
   {
     if (font_name == null) {
       return false;
     }
     if (font_name.equals("")) {
       return true;
     }
     if (FontName == null) {
       FontName = initFonts();
     }
     for (int i = 0; i < FontName.length; i++) {
       if (font_name.equals(FontName[i])) {
         return true;
       }
     }
     return false;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.SupportFonts
 * JD-Core Version:    0.7.0.1
 */