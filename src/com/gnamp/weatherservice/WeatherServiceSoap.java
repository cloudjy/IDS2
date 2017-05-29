package com.gnamp.weatherservice;

import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract interface WeatherServiceSoap
  extends Remote
{
  public abstract String queryWeatherUpdateTime()
    throws RemoteException;
  
  public abstract CityWeather queryWeather(String paramString)
    throws RemoteException;
  
  public abstract CityWeather queryWeather2(String paramString1, String paramString2)
    throws RemoteException;
  
  public abstract String queryCityVersion()
    throws RemoteException;
  
  public abstract City[] queryAllCitys()
    throws RemoteException;
}


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.weatherservice.WeatherServiceSoap
 * JD-Core Version:    0.7.0.1
 */