 package com.gnamp.struts.utils;
 
 import java.io.IOException;
 import java.io.PrintWriter;
 import java.sql.Time;
 import java.sql.Timestamp;
 import java.text.SimpleDateFormat;
 import javax.servlet.ServletResponse;
 import net.sf.json.JSONObject;
 import net.sf.json.JsonConfig;
 import net.sf.json.processors.JsonValueProcessor;
 
 public final class JsonUtils
 {
   protected static JsonConfig jsonConfig = getDefaultConfig();
   
   protected static JsonConfig getDefaultConfig()
   {
     JsonConfig config = new JsonConfig();
     JsonValueProcessor jsonValueProcessor = new JsonValueProcessor()
     {
       protected SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       
       public Object processArrayValue(Object value, JsonConfig jsonConfig)
       {
         return (value != null) && ((value instanceof java.util.Date)) ? 
           this.simpleDateFormat.format(value) : "";
       }
       
       public Object processObjectValue(String key, Object value, JsonConfig jsonConfig)
       {
         return (value != null) && ((value instanceof java.util.Date)) ? 
           this.simpleDateFormat.format(value) : "";
       }
     };
     config.registerJsonValueProcessor(java.util.Date.class, jsonValueProcessor);
     config.registerJsonValueProcessor(java.sql.Date.class, jsonValueProcessor);
     config.registerJsonValueProcessor(Timestamp.class, jsonValueProcessor);
     config.registerJsonValueProcessor(Time.class, jsonValueProcessor);
     
     return config;
   }
   
   protected static String successString(String message, Object data)
   {
     JSONObject json = new JSONObject();
     
     json.put("success", Boolean.valueOf(true));
     if (message != null) {
       json.element("message", message);
     }
     if (data != null) {
       json.element("data", data, jsonConfig);
     }
     return json.toString();
   }
   
   protected static String errorString()
   {
     return "{'success':false}";
   }
   
   protected static String errorString(String message, Object data)
   {
     JSONObject json = new JSONObject();
     
     json.put("success", Boolean.valueOf(false));
     if (message != null) {
       json.element("message", message, jsonConfig);
     }
     if (data != null) {
       json.element("data", data, jsonConfig);
     }
     return json.toString();
   }
   
   protected static void write(ServletResponse servletResponse, String text)
   {
     if ((servletResponse == null) || (text == null)) {
       throw new IllegalArgumentException("[JsonUtils]: invalid parameter!");
     }
     PrintWriter writer = null;
     try
     {
       servletResponse.setContentType("application/x-json");
       servletResponse.setCharacterEncoding("UTF-8");
       writer = servletResponse.getWriter();
       writer.write(text);
     }
     catch (IOException e)
     {
       e.printStackTrace();
     }
     finally
     {
       if (writer != null) {
         writer.close();
       }
     }
   }
   
   public static void writeSuccess(ServletResponse servletResponse)
   {
     write(servletResponse, successString(null, null));
   }
   
   public static void writeSuccess(ServletResponse servletResponse, String message, Object data)
   {
     write(servletResponse, successString(message, data));
   }
   
   public static void writeSuccessMessage(ServletResponse servletResponse, String message)
   {
     write(servletResponse, successString(message, null));
   }
   
   public static void writeSuccessData(ServletResponse servletResponse, Object data)
   {
     write(servletResponse, successString(null, data));
   }
   
   public static void writeError(ServletResponse servletResponse)
   {
     write(servletResponse, errorString(null, null));
   }
   
   public static void writeError(ServletResponse servletResponse, String message, Object data)
   {
     write(servletResponse, errorString(message, data));
   }
   
   public static void writeErrorMessage(ServletResponse servletResponse, String message)
   {
     write(servletResponse, errorString(message, null));
   }
   
   public static void writeErrorData(ServletResponse servletResponse, Object data)
   {
     write(servletResponse, errorString(null, data));
   }
   
   public static void writeFieldMessage(ServletResponse servletResponse, Object data)
   {
     write(servletResponse, fieldString(data));
   }
   
   protected static String fieldString(Object data)
   {
     JSONObject json = new JSONObject();
     
     json.put("success", Boolean.valueOf(false));
     if (data != null) {
       json.element("field", data, jsonConfig);
     }
     return json.toString();
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.utils.JsonUtils
 * JD-Core Version:    0.7.0.1
 */