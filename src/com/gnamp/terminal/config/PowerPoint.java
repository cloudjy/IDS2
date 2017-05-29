 package com.gnamp.terminal.config;
 
 public class PowerPoint
 {
   public int wPonHour;
   public int wPonMinute;
   public int wPonSecond;
   public int wPoffHour;
   public int wPoffMinute;
   public int wPoffSecond;
   
   public PowerPoint() {}
   
   public PowerPoint(int onHour, int onMin, int onSec, int offHour, int offMin, int offSec)
   {
     if ((onHour < 0) || (onHour > 23) || 
       (onMin < 0) || (onMin > 59) || 
       (onSec < 0) || (onSec > 59) || 
       (offHour < 0) || (offHour > 23) || 
       (offMin < 0) || (offMin > 59) || 
       (offSec < 0) || (offSec > 59)) {
       throw new IllegalArgumentException("invalid power point");
     }
     this.wPonHour = onHour;
     this.wPonMinute = onMin;
     this.wPonSecond = onSec;
     
     this.wPoffHour = offHour;
     this.wPoffMinute = offMin;
     this.wPoffSecond = offSec;
   }
   
   public boolean valid()
   {
     return (this.wPonHour >= 0) && (this.wPonHour <= 23) && 
       (this.wPonMinute >= 0) && (this.wPonMinute <= 59) && 
       (this.wPonSecond >= 0) && (this.wPonSecond <= 59) && 
       (this.wPoffHour >= 0) && (this.wPoffHour <= 23) && 
       (this.wPoffMinute >= 0) && (this.wPoffMinute <= 59) && 
       (this.wPoffSecond >= 0) && (this.wPoffSecond <= 59);
   }
   
   public int getPowerOn()
   {
     return this.wPonHour * 3600 + this.wPonMinute * 60 + this.wPonSecond;
   }
   
   public int getPowerOff()
   {
     return this.wPoffHour * 3600 + this.wPoffMinute * 60 + this.wPoffSecond;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.terminal.config.PowerPoint
 * JD-Core Version:    0.7.0.1
 */