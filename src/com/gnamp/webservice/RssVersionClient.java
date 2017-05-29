 package com.gnamp.webservice;
 
 import java.net.MalformedURLException;
 import java.net.URL;
 import javax.xml.namespace.QName;
 import javax.xml.rpc.ParameterMode;
 import org.apache.axis.client.Call;
 import org.apache.axis.encoding.XMLType;
 
 public class RssVersionClient
   extends WebService
 {
   protected URL getURL()
     throws MalformedURLException
   {
     return new URL(getHost() + "/RssService.asmx?WSDL");
   }
   
   protected String getSoapactionURI()
   {
     return "http://tempuri.org/getRssVersion";
   }
   
   protected String getRemoteMethod()
   {
     return "getRssVersion";
   }
   
   protected String getNamespaceURI()
   {
     return "http://tempuri.org/";
   }
   
   protected void addParameter(Call call)
   {
     call.addParameter(new QName(getNamespaceURI(), "list"), 
       XMLType.XSD_STRING, 
       ParameterMode.IN);
   }
   
   public String getResult(String version)
     throws Exception
   {
     return String.valueOf(invoke(new Object[] { version }));
   }
   
   protected QName returnType()
   {
     return XMLType.XSD_STRING;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.webservice.RssVersionClient
 * JD-Core Version:    0.7.0.1
 */