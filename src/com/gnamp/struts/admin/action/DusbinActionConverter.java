 package com.gnamp.struts.admin.action;
 
 import com.gnamp.server.model.DustbinFile;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 import org.apache.struts2.util.StrutsTypeConverter;
 
 public class DusbinActionConverter
   extends StrutsTypeConverter
 {
   public Object convertFromString(Map arg0, String[] str, Class clazz)
   {
     if (clazz == List.class)
     {
       List<DustbinFile> list = new ArrayList();
       for (String dusts : str)
       {
         DustbinFile dbf = new DustbinFile();
         for (String dust : dusts.split("&"))
         {
           String name = dust.split("=")[0];
           String value = dust.split("=")[1];
           if ("cstmid".equalsIgnoreCase(name)) {
             dbf.setCstmId(Integer.valueOf(value).intValue());
           }
           if ("fileid".equalsIgnoreCase(name)) {
             dbf.setFileId(Long.valueOf(value).longValue());
           }
         }
         list.add(dbf);
       }
       return list;
     }
     return null;
   }
   
   public String convertToString(Map arg0, Object arg1)
   {
     return null;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.admin.action.DusbinActionConverter
 * JD-Core Version:    0.7.0.1
 */