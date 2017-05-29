 package com.gnamp.struts.filter;
 
 import com.gnamp.server.Storage;
 import com.gnamp.server.handle.CityHandle;
 import com.gnamp.server.handle.WeatherHandle;
 import com.gnamp.weatherservice.CityWeather;
 import com.gnamp.weatherservice.WeatherServiceLocator;
 import com.gnamp.weatherservice.WeatherServiceSoap;
 import java.io.BufferedReader;
 import java.io.BufferedWriter;
 import java.io.File;
 import java.io.FileReader;
 import java.io.FileWriter;
 import java.io.IOException;
 import java.net.URL;
 import java.rmi.RemoteException;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.List;
 import java.util.TimerTask;
 
 public class WeatheHelp
   extends TimerTask
 {
   public WeatheHelp(String url)
   {
     this.url = url;
   }
   
   protected String url = "http://ipub.teamhd.net:8080/Weather.asmx";
   
   public void run()
   {
     try
     {
       WeatherServiceLocator weatherSrviceLocator = new WeatherServiceLocator();
       WeatherServiceSoap serviceSoap = weatherSrviceLocator
         .getWeatherServiceSoap(new URL(this.url));
       
       com.gnamp.weatherservice.City[] querycitys = serviceSoap
         .queryAllCitys();
       
       List<com.gnamp.server.model.City> oldcitys = CityHandle.readAll();
       
       String FILE_NAME = Storage.getWorkRootPath() + "cityversion.txt";
       String version = serviceSoap.queryCityVersion();
       
       String input = "";
       String localversion = "";
       
       FileWriter fw = null;
       BufferedWriter bw = null;
       FileReader fr = null;
       BufferedReader br = null;
       
       FileWriter fw1 = null;
       BufferedWriter bw1 = null;
       try
       {
         File dir = new File(FILE_NAME);
         if (!dir.exists())
         {
           Storage.createFileDirectory(FILE_NAME);
           
           fw = new FileWriter(FILE_NAME, false);
           bw = new BufferedWriter(fw);
           bw.write(version);
         }
         else
         {
           fr = new FileReader(FILE_NAME);
           br = new BufferedReader(fr);
           if ((input = br.readLine()) != null) {
             localversion = input;
           }
         }
         fw1 = new FileWriter(FILE_NAME, false);
         bw1 = new BufferedWriter(fw1);
         bw1.write(version);
       }
       catch (Exception localException1)
       {
         if (br != null) {
           try
           {
             br.close();
           }
           catch (IOException localIOException) {}
         }
         if (fr != null) {
           try
           {
             fr.close();
           }
           catch (IOException localIOException1) {}
         }
         if (bw != null) {
           try
           {
             bw.close();
           }
           catch (IOException localIOException2) {}
         }
         if (fw != null) {
           try
           {
             fw.close();
           }
           catch (IOException localIOException3) {}
         }
         if (bw1 != null) {
           try
           {
             bw1.close();
           }
           catch (IOException localIOException4) {}
         }
         if (fw1 != null) {
           try
           {
             fw1.close();
           }
           catch (IOException localIOException5) {}
         }
       }
       finally
       {
         if (br != null) {
           try
           {
             br.close();
           }
           catch (IOException localIOException6) {}
         }
         if (fr != null) {
           try
           {
             fr.close();
           }
           catch (IOException localIOException7) {}
         }
         if (bw != null) {
           try
           {
             bw.close();
           }
           catch (IOException localIOException8) {}
         }
         if (fw != null) {
           try
           {
             fw.close();
           }
           catch (IOException localIOException9) {}
         }
         if (bw1 != null) {
           try
           {
             bw1.close();
           }
           catch (IOException localIOException10) {}
         }
         if (fw1 != null) {
           try
           {
             fw1.close();
           }
           catch (IOException localIOException11) {}
         }
       }
       List<com.gnamp.server.model.City> mods = new ArrayList();
       if ((version != null) && (!version.equals("")) && (localversion != null) && 
         (!localversion.equals("")) && (version.equals(localversion)))
       {
         mods = oldcitys;
       }
       else
       {
         boolean isTrue = true;
         if ((querycitys != null) && (querycitys.length > 0)) {
           for (int i = 0; i < querycitys.length; i++)
           {
             if ((oldcitys != null) && (oldcitys.size() > 0)) {
               for (int j = 0; j < oldcitys.size(); j++) {
                 if (querycitys[i].getCityName().equals(((com.gnamp.server.model.City)oldcitys.get(j)).getCityName()))
                 {
                   isTrue = false;
                   break;
                 }
               }
             }
             if (isTrue)
             {
               com.gnamp.server.model.City city = new com.gnamp.server.model.City();
               city.setCityName(querycitys[i].getCityName());
               city.setProvince(querycitys[i].getStateName());
               
               CityHandle.create(city);
             }
             isTrue = true;
           }
         }
         mods = CityHandle.readAll();
       }
       WeatherHandle.removeExpired();
       
 
 
 
       Object weatherList = new ArrayList();
       if ((mods != null) && (mods.size() > 0))
       {
         for (int j = 0; j < mods.size(); j++)
         {
           String Wtext = "暂无实况";
           int Tp = -1;
           int Low = -100;
           int High = 100;
           Date date = new Date();
           
 
 
 
 
 
 
           CityWeather cityWeather = serviceSoap.queryWeather2(
             ((com.gnamp.server.model.City)mods.get(j)).getCityName(), ((com.gnamp.server.model.City)mods.get(j)).getProvince());
           if ((cityWeather != null) && 
             (cityWeather.getWeathers() != null) && 
             (cityWeather.getWeathers().length > 0)) {
             if ((cityWeather.getCityName().equals(((com.gnamp.server.model.City)mods.get(j)).getCityName())) || 
             
               (cityWeather.getStateName().equals(((com.gnamp.server.model.City)mods.get(j)).getProvince()))) {
               for (int i = 0; i < cityWeather.getWeathers().length; i++)
               {
                 try
                 {
                   Wtext = 
                     cityWeather.getWeathers()[i].getText();
                   Tp = cityWeather.getWeathers()[i].getType();
                   Low = cityWeather.getWeathers()[i].getLow();
                   High = cityWeather.getWeathers()[i]
                     .getHigh();
                   date = cityWeather.getWeathers()[i]
                     .getDate();
                 }
                 catch (Exception e)
                 {
                   Tp = -1;
                   Low = -100;
                   High = 100;
                   Wtext = "暂无实况";
                   date = new Date();
                 }
                 com.gnamp.server.model.Weather weather = new com.gnamp.server.model.Weather();
                 weather.setCityId(((com.gnamp.server.model.City)mods.get(j)).getCityId());
                 weather.setWeatherDate(date);
                 weather.setWeatherType(Tp);
                 weather.setHigh(High);
                 weather.setLow(Low);
                 weather.setWeatherText(Wtext);
                 
                 ((List)weatherList).add(weather);
               }
             }
           }
           if (((List)weatherList).size() >= 25)
           {
             WeatherHandle.add((List)weatherList);
             ((List)weatherList).clear();
           }
         }
         if (((List)weatherList).size() > 0)
         {
           WeatherHandle.add((List)weatherList);
           ((List)weatherList).clear();
         }
       }
     }
     catch (RemoteException e)
     {
       e.printStackTrace();
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.filter.WeatheHelp
 * JD-Core Version:    0.7.0.1
 */