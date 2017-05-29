 package com.gnamp.struts.action;
 
 import java.text.SimpleDateFormat;
 import java.util.Date;
 import java.util.Locale;
 
 public class TimeFormat
 {
   private static Locale ifpVariant = Locale.US;
   private static Locale ifpZh = Locale.CHINA;
   private static Locale ifpZH_TW = Locale.TRADITIONAL_CHINESE;
   private static Locale ifpEn = Locale.US;
   public static TimeFormat[] Date = {
     new TimeFormat(0, "2009-12-08", "yyyy-MM-dd", ifpVariant), 
     new TimeFormat(1, "2009年12月08日", "yyyy年MM月dd日", ifpVariant), 
     new TimeFormat(2, "2009/12/08", "yyyy/MM/dd", ifpVariant), 
     new TimeFormat(3, "12/08/2009", "MM/dd/yyyy", ifpVariant), 
     new TimeFormat(4, "08-Dec-2009", "dd-MMM-yyyy", ifpEn), 
     new TimeFormat(5, "Dec 08, 2009", "MMM dd yyyy", ifpEn), 
     new TimeFormat(6, "31/08/2009", "dd/MM/yyyy", ifpVariant) };
   public static TimeFormat[] Time = {
     new TimeFormat(0, "13:04", "HH:mm", ifpVariant), 
     new TimeFormat(1, "13时04分", "HH时mm分", ifpZh), 
     new TimeFormat(2, "01:04 PM", "h:mm a", ifpEn), 
     new TimeFormat(3, "下午1时4分", "ah时m分", ifpZh), 
     new TimeFormat(4, "13時04分", "HH時mm分", ifpZH_TW), 
     new TimeFormat(5, "下午1時4分", "ah時m分", ifpZH_TW), 
     
     new TimeFormat(6, "13:04:09", "HH:mm:ss", ifpVariant), 
     new TimeFormat(7, "13时04分09秒", "HH时mm分ss秒", ifpZh), 
     new TimeFormat(8, "01:04:09 PM", "h:mm:ss a", ifpEn), 
     new TimeFormat(9, "下午1时4分9秒", "ah时m分ss秒", ifpZh), 
     new TimeFormat(10, "13時04分9秒", "HH時mm分ss秒", ifpZH_TW), 
     new TimeFormat(11, "下午1時4分9秒", "ah時m分ss秒", ifpZH_TW) };
   public static TimeFormat[] Week = {
     new TimeFormat(0, "星期三", "EEEE", ifpZh), 
     new TimeFormat(1, "Wed", "EEE", ifpVariant) };
   protected int tag;
   protected String demo;
   protected SimpleDateFormat format;
   
   public static int defaultDate()
   {
     return Date[0].Style();
   }
   
   public static int defaultTime()
   {
     return Time[0].Style();
   }
   
   public static int defaultWeek()
   {
     return Week[0].Style();
   }
   
   public static boolean isValidDate(int style)
   {
     return (style >= Date[0].Style()) && (
       style <= Date[(Date.length - 1)].Style());
   }
   
   public static boolean isValidTime(int style)
   {
     return (style >= Time[0].Style()) && (
       style <= Time[(Time.length - 1)].Style());
   }
   
   public static boolean isValidWeek(int style)
   {
     return (style >= Week[0].Style()) && (
       style <= Week[(Week.length - 1)].Style());
   }
   
   public static TimeFormat getDateFormat(int style)
   {
     return Date[style];
   }
   
   public static TimeFormat getTimeFormat(int style)
   {
     return Time[style];
   }
   
   public static TimeFormat getWeekFormat(int style)
   {
     return Week[style];
   }
   
   private TimeFormat() {}
   
   protected TimeFormat(int t, String d, String pattern, Locale locale)
   {
     this.tag = t;
     this.demo = d;
     if (pattern != null)
     {
       if (locale != null) {
         this.format = new SimpleDateFormat(pattern, locale);
       } else {
         this.format = new SimpleDateFormat(pattern);
       }
     }
     else {
       this.format = new SimpleDateFormat();
     }
   }
   
   public int Style()
   {
     return this.tag;
   }
   
   public String Demo()
   {
     return this.demo;
   }
   
   public String Display()
   {
     Date dateNow = new Date();
     return this.format.format(dateNow);
   }
   
   public String toString()
   {
     Date dateNow = new Date();
     return this.format.format(dateNow);
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.TimeFormat
 * JD-Core Version:    0.7.0.1
 */