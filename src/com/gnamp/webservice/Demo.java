 package com.gnamp.webservice;
 
 import java.io.PrintStream;
 import java.net.MalformedURLException;
 import java.net.URL;
 import org.apache.axis.client.Call;
 
 public class Demo
 {
   public static void main(String[] args)
     throws Exception
   {
     Object result = new WebService()
     {
       protected URL getURL()
         throws MalformedURLException
       {
         return new URL("http://192.168.1.2:9527/RssService.asmx?WSDL");
       }
       
       protected String getSoapactionURI()
       {
         return "http://tempuri.org/getRss";
       }
       
       protected String getRemoteMethod()
       {
         return "getRss";
       }
       
       protected String getNamespaceURI()
       {
         return getHost();
       }
       
       protected String getHost()
       {
         return "http://192.168.1.2:9527/";
       }
       
       protected void addParameter(Call call) {}
     }
     
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
       .invoke(new Object[0]);
     System.out.println(result);
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.webservice.Demo
 * JD-Core Version:    0.7.0.1
 */