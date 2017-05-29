 package com.gnamp.weatherservice;
 
 import java.rmi.RemoteException;
 import javax.xml.rpc.ServiceException;
 import javax.xml.rpc.Stub;
 
 public class WeatherServiceSoapProxy
   implements WeatherServiceSoap
 {
   private String _endpoint = null;
   private WeatherServiceSoap weatherServiceSoap = null;
   
   public WeatherServiceSoapProxy()
   {
     _initWeatherServiceSoapProxy();
   }
   
   public WeatherServiceSoapProxy(String endpoint)
   {
     this._endpoint = endpoint;
     _initWeatherServiceSoapProxy();
   }
   
   private void _initWeatherServiceSoapProxy()
   {
     try
     {
       this.weatherServiceSoap = new WeatherServiceLocator().getWeatherServiceSoap();
       if (this.weatherServiceSoap != null) {
         if (this._endpoint != null) {
           ((Stub)this.weatherServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", this._endpoint);
         } else {
           this._endpoint = ((String)((Stub)this.weatherServiceSoap)._getProperty("javax.xml.rpc.service.endpoint.address"));
         }
       }
     }
     catch (ServiceException localServiceException) {}
   }
   
   public String getEndpoint()
   {
     return this._endpoint;
   }
   
   public void setEndpoint(String endpoint)
   {
     this._endpoint = endpoint;
     if (this.weatherServiceSoap != null) {
       ((Stub)this.weatherServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", this._endpoint);
     }
   }
   
   public WeatherServiceSoap getWeatherServiceSoap()
   {
     if (this.weatherServiceSoap == null) {
       _initWeatherServiceSoapProxy();
     }
     return this.weatherServiceSoap;
   }
   
   public String queryWeatherUpdateTime()
     throws RemoteException
   {
     if (this.weatherServiceSoap == null) {
       _initWeatherServiceSoapProxy();
     }
     return this.weatherServiceSoap.queryWeatherUpdateTime();
   }
   
   public CityWeather queryWeather(String city)
     throws RemoteException
   {
     if (this.weatherServiceSoap == null) {
       _initWeatherServiceSoapProxy();
     }
     return this.weatherServiceSoap.queryWeather(city);
   }
   
   public CityWeather queryWeather2(String city, String province)
     throws RemoteException
   {
     if (this.weatherServiceSoap == null) {
       _initWeatherServiceSoapProxy();
     }
     return this.weatherServiceSoap.queryWeather2(city, province);
   }
   
   public String queryCityVersion()
     throws RemoteException
   {
     if (this.weatherServiceSoap == null) {
       _initWeatherServiceSoapProxy();
     }
     return this.weatherServiceSoap.queryCityVersion();
   }
   
   public City[] queryAllCitys()
     throws RemoteException
   {
     if (this.weatherServiceSoap == null) {
       _initWeatherServiceSoapProxy();
     }
     return this.weatherServiceSoap.queryAllCitys();
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.weatherservice.WeatherServiceSoapProxy
 * JD-Core Version:    0.7.0.1
 */