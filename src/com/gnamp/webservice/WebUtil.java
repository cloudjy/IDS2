 package com.gnamp.webservice;
 
 import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
 
 public class WebUtil
 {
   public static String getWebCon(String domain)
   {
     StringBuffer sb = new StringBuffer();
     try
     {
       URL url = new URL(domain);
       BufferedReader in = new BufferedReader(new InputStreamReader(url
         .openStream(), "GBK"));
       String line;
       while ((line = in.readLine()) != null)
       {
         sb.append(line);
       }
       in.close();
     }
     catch (Exception e)
     {
       sb.append(e.toString());
       System.err.println(e);
     }
     return sb.toString();
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.webservice.WebUtil
 * JD-Core Version:    0.7.0.1
 */