 package com.gnamp.terminal.config;
 
 public class DailyPowerConfig
   implements PowerConfig
 {
   public PowerPoint mPoint = new PowerPoint();
   
   public void setPowerPoint(PowerPoint point)
   {
     if ((point == null) || (!point.valid())) {
       throw new IllegalArgumentException("invalid power point");
     }
     this.mPoint.wPonHour = point.wPonHour;
     this.mPoint.wPonMinute = point.wPonMinute;
     this.mPoint.wPonSecond = point.wPonSecond;
     
     this.mPoint.wPoffHour = point.wPoffHour;
     this.mPoint.wPoffMinute = point.wPoffMinute;
     this.mPoint.wPoffSecond = point.wPoffSecond;
   }
   
   public boolean valid()
   {
     return this.mPoint.valid();
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.terminal.config.DailyPowerConfig
 * JD-Core Version:    0.7.0.1
 */