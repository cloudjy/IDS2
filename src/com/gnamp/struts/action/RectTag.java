 package com.gnamp.struts.action;
 
 import com.gnamp.server.model.AvInRect;
 import com.gnamp.server.model.CameraRect;
 import com.gnamp.server.model.DateRect;
 import com.gnamp.server.model.DeviceRect;
 import com.gnamp.server.model.FlashRect;
 import com.gnamp.server.model.HybridRect;
 import com.gnamp.server.model.ImageRect;
 import com.gnamp.server.model.LogoRect;
 import com.gnamp.server.model.MLFlipTRect;
 import com.gnamp.server.model.MLScrollTRect;
 import com.gnamp.server.model.MLStaticTRect;
 import com.gnamp.server.model.Rect;
 import com.gnamp.server.model.SLFlipTRect;
 import com.gnamp.server.model.SLScrollTRect;
 import com.gnamp.server.model.SLStaticTRect;
 import com.gnamp.server.model.TimeRect;
 import com.gnamp.server.model.VideoRect;
 import com.gnamp.server.model.WeatherLogoRect;
 import com.gnamp.server.model.WeatherTemperatureRect;
 import com.gnamp.server.model.WeatherTextRect;
 import com.gnamp.server.model.WeatherTitleRect;
 import com.gnamp.server.model.WebRect;
 import com.gnamp.server.model.WeekRect;
 
 abstract class RectTag
 {
   public static final String[] Tag = initTagString();
   
   private static String[] initTagString()
   {
     String[] TagStr = new String[18];
     
     TagStr[0] = "";
     TagStr[1] = "imagerect";
     TagStr[2] = "videorect";
     TagStr[3] = "daterect";
     TagStr[4] = "timerect";
     TagStr[5] = "weekrect";
     TagStr[6] = "weathertemprect";
     TagStr[7] = "weathertextrect";
     TagStr[8] = "weatherlogorect";
     TagStr[9] = "weatherdescriptionrect";
     TagStr[10] = "marqueerect";
     TagStr[11] = "hybridrect";
     TagStr[12] = "devicerect";
     TagStr[13] = "logorect";
     TagStr[14] = "webrect";
     TagStr[15] = "flashrect";
     TagStr[16] = "avinrect";
     TagStr[17] = "camerarect";
     return TagStr;
   }
   
   public static String tagString(Rect rect)
   {
     if (rect == null) {
       return Tag[0];
     }
     if ((rect instanceof ImageRect)) {
       return Tag[1];
     }
     if ((rect instanceof VideoRect)) {
       return Tag[2];
     }
     if ((rect instanceof DateRect)) {
       return Tag[3];
     }
     if ((rect instanceof TimeRect)) {
       return Tag[4];
     }
     if ((rect instanceof WeekRect)) {
       return Tag[5];
     }
     if ((rect instanceof WeatherTemperatureRect)) {
       return Tag[6];
     }
     if ((rect instanceof WeatherTextRect)) {
       return Tag[7];
     }
     if ((rect instanceof WeatherLogoRect)) {
       return Tag[8];
     }
     if ((rect instanceof WeatherTitleRect)) {
       return Tag[9];
     }
     if ((rect instanceof MLStaticTRect)) {
       return Tag[10];
     }
     if ((rect instanceof MLScrollTRect)) {
       return Tag[10];
     }
     if ((rect instanceof MLFlipTRect)) {
       return Tag[10];
     }
     if ((rect instanceof SLScrollTRect)) {
       return Tag[10];
     }
     if ((rect instanceof SLStaticTRect)) {
       return Tag[10];
     }
     if ((rect instanceof SLFlipTRect)) {
       return Tag[10];
     }
     if ((rect instanceof HybridRect)) {
       return Tag[11];
     }
     if ((rect instanceof DeviceRect)) {
/* ;0:78 */       return Tag[12];
/* ;1:   */     }
/* ;2:79 */     if ((rect instanceof LogoRect)) {
/* ;3:80 */       return Tag[13];
/* ;4:   */     }
/* ;5:81 */     if ((rect instanceof WebRect)) {
/* ;6:82 */       return Tag[14];
/* ;7:   */     }
/* ;8:83 */     if ((rect instanceof FlashRect)) {
/* ;9:84 */       return Tag[15];
/* <0:   */     }
/* <1:85 */     if ((rect instanceof AvInRect)) {
/* <2:86 */       return Tag[16];
/* <3:   */     }
/* <4:87 */     if ((rect instanceof CameraRect)) {
/* <5:88 */       return Tag[17];
/* <6:   */     }
/* <7:90 */     return Tag[0];
/* <8:   */   }
/* <9:   */ }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.RectTag
 * JD-Core Version:    0.7.0.1
 */