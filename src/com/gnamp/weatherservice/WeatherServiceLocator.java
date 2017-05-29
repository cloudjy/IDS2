 package com.gnamp.weatherservice;
 
 import java.net.MalformedURLException;
 import java.net.URL;
 import java.rmi.Remote;
 import java.util.HashSet;
 import java.util.Iterator;
 import javax.xml.namespace.QName;
 import javax.xml.rpc.ServiceException;
 import org.apache.axis.AxisFault;
 import org.apache.axis.EngineConfiguration;
 import org.apache.axis.client.Service;
 import org.apache.axis.client.Stub;
 
 public class WeatherServiceLocator
   extends Service
   implements WeatherService
 {
   public WeatherServiceLocator() {}
   
   public WeatherServiceLocator(EngineConfiguration config)
   {
     super(config);
   }
   
   public WeatherServiceLocator(String wsdlLoc, QName sName)
     throws ServiceException
   {
     super(wsdlLoc, sName);
   }
   
   private String WeatherServiceSoap_address = "http://ipub.teamhd.net:8080/Weather.asmx";
   
   public String getWeatherServiceSoapAddress()
   {
     return this.WeatherServiceSoap_address;
   }
   
   private String WeatherServiceSoapWSDDServiceName = "WeatherServiceSoap";
   
   public String getWeatherServiceSoapWSDDServiceName()
   {
     return this.WeatherServiceSoapWSDDServiceName;
   }
   
   public void setWeatherServiceSoapWSDDServiceName(String name)
   {
     this.WeatherServiceSoapWSDDServiceName = name;
   }
   
   public WeatherServiceSoap getWeatherServiceSoap()
     throws ServiceException
   {
	   URL endpoint;
     try
     {
    	endpoint = new URL(this.WeatherServiceSoap_address);
     }
     catch (MalformedURLException e)
     {
   
       throw new ServiceException(e);
     }
     
     return getWeatherServiceSoap(endpoint);
   }
   
   public WeatherServiceSoap getWeatherServiceSoap(URL portAddress)
     throws ServiceException
   {
     try
     {
       WeatherServiceSoapStub _stub = new WeatherServiceSoapStub(portAddress, this);
       _stub.setPortName(getWeatherServiceSoapWSDDServiceName());
       return _stub;
     }
     catch (AxisFault e) {}
     return null;
   }
   
   public void setWeatherServiceSoapEndpointAddress(String address)
   {
     this.WeatherServiceSoap_address = address;
   }
   
   public Remote getPort(Class serviceEndpointInterface)
     throws ServiceException
   {
     try
     {
       if (WeatherServiceSoap.class.isAssignableFrom(serviceEndpointInterface))
       {
         WeatherServiceSoapStub _stub = new WeatherServiceSoapStub(new URL(this.WeatherServiceSoap_address), this);
         _stub.setPortName(getWeatherServiceSoapWSDDServiceName());
         return _stub;
       }
     }
     catch (Throwable t)
     {
       throw new ServiceException(t);
     }
     throw new ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
   }
   
   public Remote getPort(QName portName, Class serviceEndpointInterface)
     throws ServiceException
   {
     if (portName == null) {
       return getPort(serviceEndpointInterface);
     }
     String inputPortName = portName.getLocalPart();
     if ("WeatherServiceSoap".equals(inputPortName)) {
       return getWeatherServiceSoap();
     }
     Remote _stub = getPort(serviceEndpointInterface);
     ((Stub)_stub).setPortName(portName);
     return _stub;
   }
   
   public QName getServiceName()
   {
     return new QName("http://ipub.tenhz.com/gnamp", "WeatherService");
   }
   
   private HashSet ports = null;
   
   public Iterator getPorts()
   {
     if (this.ports == null)
     {
       this.ports = new HashSet();
       this.ports.add(new QName("http://ipub.tenhz.com/gnamp", "WeatherServiceSoap"));
     }
     return this.ports.iterator();
   }
   
   public void setEndpointAddress(String portName, String address)
     throws ServiceException
   {
     if ("WeatherServiceSoap".equals(portName)) {
       setWeatherServiceSoapEndpointAddress(address);
     } else {
       throw new ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
     }
   }
   
   public void setEndpointAddress(QName portName, String address)
     throws ServiceException
   {
     setEndpointAddress(portName.getLocalPart(), address);
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.weatherservice.WeatherServiceLocator
 * JD-Core Version:    0.7.0.1
 */