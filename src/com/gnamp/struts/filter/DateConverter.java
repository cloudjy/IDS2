 package com.gnamp.struts.filter;
 
 import java.text.ParseException;
 import java.text.SimpleDateFormat;
 import java.util.Date;
 import java.util.Map;
 import ognl.DefaultTypeConverter;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.time.DateUtils;
 import org.apache.log4j.Logger;
 
 public class DateConverter
   extends DefaultTypeConverter
 {
   private static final Logger logger = Logger.getLogger(DateConverter.class);
   private static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
   private static final String DATE_PATTERN = "yyyy-MM-dd";
   private static final String MONTH_PATTERN = "yyyy-MM";
   
   public Object convertValue(Map ognlContext, Object value, Class toType)
   {
     Object result = null;
     if (toType == Date.class) {
       try
       {
         result = doConvertToDate(value);
       }
       catch (ParseException e)
       {
         e.printStackTrace();
       }
     } else if (toType == String.class) {
       result = doConvertToString(value);
     }
     return result;
   }
   
   private Date doConvertToDate(Object value)
     throws ParseException
   {
     Date result = null;
     if ((value instanceof String))
     {
       result = DateUtils.parseDate((String)value, new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM" });
       if ((result == null) && (StringUtils.isNotEmpty((String)value))) {
         try
         {
           result = new Date(new Long((String)value).longValue());
         }
         catch (Exception e)
         {
           logger.error("Converting from milliseconds to Date fails!");
           e.printStackTrace();
         }
       }
     }
     else if ((value instanceof Object[]))
     {
       Object[] array = (Object[])value;
       if ((array != null) && (array.length >= 1))
       {
         value = array[0];
         result = doConvertToDate(value);
       }
     }
     else if (Date.class.isAssignableFrom(value.getClass()))
     {
       result = (Date)value;
     }
     return result;
   }
   
   private String doConvertToString(Object value)
   {
     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     String result = null;
     if ((value instanceof Date)) {
       result = simpleDateFormat.format(value);
     }
     return result;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.filter.DateConverter
 * JD-Core Version:    0.7.0.1
 */