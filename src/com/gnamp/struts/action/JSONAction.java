 package com.gnamp.struts.action;
 
 import java.io.IOException;
 import java.io.PrintWriter;
 import java.text.SimpleDateFormat;
 import java.util.Collection;
 import java.util.Date;
 import java.util.Map;
 import java.util.Map.Entry;
 import java.util.Set;
 import javax.servlet.http.HttpServletResponse;
 import net.sf.json.JSONArray;
 import net.sf.json.JSONObject;
 import net.sf.json.JsonConfig;
 import net.sf.json.processors.JsonValueProcessor;
 
 public class JSONAction
   extends BaseAction
 {
   JsonConfig config = null;
   
   protected JsonConfig getDefaultConfig()
   {
     JsonConfig config = new JsonConfig();
     config.registerJsonValueProcessor(Date.class, 
       new DateJsonValueProcessImpl());
     return config;
   }
   
   private String JSONTypeString(boolean type)
   {
     JSONObject jsonObject = new JSONObject();
     jsonObject.put("success", Boolean.valueOf(type));
     return jsonObject.toString();
   }
   
   protected String JSONSuccessString(String result)
   {
     StringBuffer buffer = new StringBuffer();
     if ((result != null) && (result.length() > 0))
     {
       JSONObject json = new JSONObject();
       json.put("success", Boolean.valueOf(true));
       json.put("data", result);
       buffer.append(json.toString());
     }
     else
     {
       buffer.append(JSONSuccessString());
     }
     return buffer.toString();
   }
   
   protected String JSONSuccessString(String result, Map<String, Object> map)
   {
     StringBuffer buffer = new StringBuffer();
     if ((result != null) && (result.length() > 0))
     {
       JSONObject json = new JSONObject();
       json.put("success", Boolean.valueOf(true));
       json.put("data", result);
       
       Set<Map.Entry<String, Object>> entrySet = map.entrySet();
       for (Map.Entry<String, Object> pair : entrySet)
       {
         String key = (String)pair.getKey();
         Object vaule = pair.getValue();
         if ((vaule instanceof String)) {
           json.put(key, vaule);
         } else {
           json.put(key, JSONObject.fromObject(vaule));
         }
       }
       buffer.append(json.toString());
     }
     else
     {
       buffer.append(JSONSuccessString());
     }
     return buffer.toString();
   }
   
   protected String JSONSuccessString()
   {
     return JSONTypeString(true);
   }
   
   protected void response(String string)
   {
     response(string, "application/x-json");
   }
   
   protected void response(String string, String contentType)
   {
     try
     {
       this.servletResponse.setContentType(contentType);
       this.servletResponse.setCharacterEncoding("UTF-8");
       PrintWriter writer = this.servletResponse.getWriter();
       
       writer.write(string);
       
       writer.flush();
     }
     catch (IOException e)
     {
       e.printStackTrace();
     }
   }
   
   protected String JSONObjectToString(Object obj)
   {
     if (this.config == null) {
       this.config = getDefaultConfig();
     }
     StringBuffer buffer = new StringBuffer();
     buffer.append(JSONObject.fromObject(obj, this.config).toString());
     return buffer.toString();
   }
   
   protected String JSONArrayToString(Collection<?> collection)
   {
     if (this.config == null) {
       this.config = getDefaultConfig();
     }
     if (collection == null) {
       return "";
     }
     StringBuffer buffer = new StringBuffer();
     String result = JSONArray.fromObject(collection, this.config).toString();
     if ((result != null) && (!"".equals(result))) {
       buffer.append(result);
     } else {
       buffer.append("[]");
     }
     return buffer.toString();
   }
   
   protected String JSONErrorString(String error)
   {
     if (this.config == null) {
       this.config = getDefaultConfig();
     }
     StringBuffer buffer = new StringBuffer();
     if ((error != null) || (!"".equals(error)))
     {
       JSONObject json = new JSONObject();
       json.put("success", Boolean.valueOf(false));
       json.put("message", error);
       buffer.append(json.toString());
     }
     else
     {
       buffer.append(JSONSuccessString());
     }
     return buffer.toString();
   }
   
   protected String JSONErrorString()
   {
     return JSONTypeString(false);
   }
   
   protected String JSONNewString(String string)
   {
     return "\"" + string + "\"";
   }
   
   class DateJsonValueProcessImpl
     implements JsonValueProcessor
   {
     String format;
     
     public DateJsonValueProcessImpl()
     {
       this.format = "yyyy-MM-dd' 'HH:mm:ss";
     }
     
     public DateJsonValueProcessImpl(String format)
     {
       this.format = format;
     }
     
     public Object processArrayValue(Object arg0, JsonConfig arg1)
     {
       return "";
     }
     
     public Object processObjectValue(String key, Object value, JsonConfig config)
     {
       String result = "";
       if ((value instanceof Date))
       {
         SimpleDateFormat sdf = new SimpleDateFormat(this.format);
         result = sdf.format(value);
       }
       return result;
     }
     
     public String getFormat()
     {
       return this.format;
     }
     
     public void setFormat(String format)
     {
       this.format = format;
     }
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.JSONAction
 * JD-Core Version:    0.7.0.1
 */