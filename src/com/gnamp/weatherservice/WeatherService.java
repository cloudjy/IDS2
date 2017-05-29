package com.gnamp.weatherservice;

import java.net.URL;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

public abstract interface WeatherService
  extends Service
{
  public abstract String getWeatherServiceSoapAddress();
  
  public abstract WeatherServiceSoap getWeatherServiceSoap()
    throws ServiceException;
  
  public abstract WeatherServiceSoap getWeatherServiceSoap(URL paramURL)
    throws ServiceException;
}


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.weatherservice.WeatherService
 * JD-Core Version:    0.7.0.1
 */