 package com.gnamp.webservice;
 
 import java.net.MalformedURLException;
 import java.net.URL;
 import javax.xml.namespace.QName;
 import org.apache.axis.client.Call;
 import org.apache.axis.client.Service;
 import org.apache.axis.encoding.XMLType;
 
 public abstract class WebService
 {
   protected abstract URL getURL()
     throws MalformedURLException;
   
   protected abstract String getNamespaceURI();
   
   protected abstract String getSoapactionURI();
   
   protected abstract String getRemoteMethod();
   
   protected Object invoke(Object... obj)
     throws Exception
   {
     Service service = new Service();
     
     Call call = (Call)service.createCall();
     
     call.setTargetEndpointAddress(getURL());
     addParameter(call);
     call.setReturnType(returnType());
     call.setUseSOAPAction(true);
     call.setSOAPActionURI(getSoapactionURI());
     call.setOperationName(new QName(getNamespaceURI(), getRemoteMethod()));
     
     return call.invoke(obj);
   }
   
   protected abstract void addParameter(Call paramCall);
   
   protected QName returnType()
   {
     return XMLType.XSD_SCHEMA;
   }
   
   protected String getHost()
   {
     return "http://ipub.teamhd.net:8080/";
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.webservice.WebService
 * JD-Core Version:    0.7.0.1
 */