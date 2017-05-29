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
 
 public abstract class RECT_TYPE
 {
   public static final int INVALID_RECT = 0;
   public static final int IMAGE_RECT = 1;
   public static final int VIDEO_RECT = 2;
   public static final int DATE_RECT = 3;
   public static final int TIME_RECT = 4;
   public static final int WEEK_RECT = 5;
   public static final int WEATHER_TMP_RECT = 6;
   public static final int WEATHER_TEXT_RECT = 7;
   public static final int WEATHER_LOGO_RECT = 8;
   public static final int WEATHER_DESCRIPTION_RECT = 9;
   public static final int MQRUEE_RECT = 10;
   public static final int HYBRID_RECT = 11;
   public static final int DEVICE_RECT = 12;
   public static final int LOGO_RECT = 13;
   public static final int WEB_RECT = 14;
   public static final int FLASH_RECT = 15;
   public static final int AVIN_RECT = 16;
   public static final int CAMERA_RECT = 17;
   public static final int TYPE_NUM = 18;
   public static final String[] TypeString = initTypeString();;
   
   private static String[] initTypeString()
   {
     String[] Type = new String[18];
     
     Type[0] = "错误区域";
     Type[1] = "图片区域";
     Type[2] = "视频区域";
     Type[3] = "日期区域";
     Type[4] = "时间区域";
     Type[5] = "星期区域";
     Type[6] = "天气温度区域";
     Type[7] = "天气内容区域";
     Type[8] = "天气图标区域";
     Type[9] = "天气标题区域";
     Type[10] = "文字区域";
     Type[11] = "混播区域";
     Type[12] = "设备区域";
     Type[13] = "图标区域";
     Type[14] = "网页区域";
     Type[15] = "Flash区域";
     Type[16] = "AV区域";
     Type[17] = "摄像头区域";
     
     return Type;
   }
   
   public static String typeString(Rect rect)
   {
     if (rect == null) {
       return TypeString[0];
     }
     if ((rect instanceof ImageRect)) {
       return TypeString[1];
     }
     if ((rect instanceof VideoRect)) {
       return TypeString[2];
     }
     if ((rect instanceof DateRect)) {
       return TypeString[3];
     }
     if ((rect instanceof TimeRect)) {
       return TypeString[4];
     }
     if ((rect instanceof WeekRect)) {
       return TypeString[5];
     }
     if ((rect instanceof WeatherTemperatureRect)) {
       return TypeString[6];
     }
     if ((rect instanceof WeatherTextRect)) {
       return TypeString[7];
     }
     if ((rect instanceof WeatherLogoRect)) {
       return TypeString[8];
     }
     if ((rect instanceof WeatherTitleRect)) {
       return TypeString[9];
     }
     if ((rect instanceof SLFlipTRect)) {
       return TypeString[10];
     }
     if ((rect instanceof SLScrollTRect)) {
       return TypeString[10];
     }
     if ((rect instanceof SLStaticTRect)) {
       return TypeString[10];
     }
     if ((rect instanceof MLFlipTRect)) {
       return TypeString[10];
     }
     if ((rect instanceof MLScrollTRect)) {
       return TypeString[10];
     }
     if ((rect instanceof MLStaticTRect)) {
       return TypeString[10];
     }
     if ((rect instanceof HybridRect)) {
       return TypeString[11];
     }
     if ((rect instanceof DeviceRect)) {
       return TypeString[12];
     }
     if ((rect instanceof LogoRect)) {
       return TypeString[13];
     }
     if ((rect instanceof WebRect)) {
       return TypeString[14];
     }
     if ((rect instanceof FlashRect)) {
       return TypeString[15];
     }
     if ((rect instanceof AvInRect)) {
       return TypeString[16];
     }
     if ((rect instanceof CameraRect)) {
       return TypeString[17];
     }
     return TypeString[0];
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.RECT_TYPE
 * JD-Core Version:    0.7.0.1
 */