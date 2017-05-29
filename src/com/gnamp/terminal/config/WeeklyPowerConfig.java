 package com.gnamp.terminal.config;
 
 public class WeeklyPowerConfig
   implements PowerConfig
 {
   public static final int WEEK_DAYS = 7;
   public PowerPoint[] mPoints;
   
   public WeeklyPowerConfig()
   {
     this.mPoints = new PowerPoint[7];
     for (int i = 0; i < 7; i++) {
       this.mPoints[i] = new PowerPoint();
     }
   }
   
   public static boolean validPowerPoint(PowerPoint powerPoint)
   {
     return (powerPoint != null) && (powerPoint.valid()) && (
       powerPoint.getPowerOn() < powerPoint.getPowerOff());
   }
   
   public void setPowerPoint(int day, PowerPoint point)
   {
     if ((day < 0) || (day >= 7)) {
       throw new IllegalArgumentException("invalid day");
     }
     if ((point == null) || (!point.valid())) {
       throw new IllegalArgumentException("invalid power point");
     }
     PowerPoint dayPoint = this.mPoints[day];
     
     dayPoint.wPonHour = point.wPonHour;
     dayPoint.wPonMinute = point.wPonMinute;
     dayPoint.wPonSecond = point.wPonSecond;
     
     dayPoint.wPoffHour = point.wPoffHour;
     dayPoint.wPoffMinute = point.wPoffMinute;
     dayPoint.wPoffSecond = point.wPoffSecond;
   }
   
   public boolean valid()
   {
     for (int i = 0; i < 7; i++) {
       if (!this.mPoints[i].valid()) {
         return false;
       }
     }
     return true;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.terminal.config.WeeklyPowerConfig
 * JD-Core Version:    0.7.0.1
 */