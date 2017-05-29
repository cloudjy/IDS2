package com.gnamp.struts.filter;

import com.gnamp.server.Storage;
import com.gnamp.server.handle.CityHandle;
import com.gnamp.server.handle.MessageServer;
import com.gnamp.server.handle.WeatherHandle;
import com.gnamp.server.model.City;
import com.gnamp.server.model.Weather;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class YahooWeather
  extends Thread
{
  static class WeatherType
  {
    int picCode;
    int code;
    String description;
    
    public WeatherType(int code, String description, int picCode)
    {
      this.code = code;
      this.description = description;
      this.picCode = picCode;
    }
    
    public int getPicCode()
    {
      return this.picCode;
    }
    
    public void setPicCode(int picCode)
    {
      this.picCode = picCode;
    }
    
    public int getCode()
    {
      return this.code;
    }
    
    public void setCode(int code)
    {
      this.code = code;
    }
    
    public String getDescription()
    {
      return this.description;
    }
    
    public void setDescription(String description)
    {
      this.description = description;
    }
  }
  
  static List<WeatherType> weatherTypes = new ArrayList();
  
  static
  {
    if (Context.isEnglishVersion())
    {
      weatherTypes.add(new WeatherType(0, "Tornado", 16));
      weatherTypes.add(new WeatherType(1, "Tropical Storm", 16));
      weatherTypes.add(new WeatherType(2, "Hurricane", 16));
      weatherTypes.add(new WeatherType(3, "Severe Thunderstorms", 4));
      weatherTypes.add(new WeatherType(4, "Thunderstorms", 4));
      weatherTypes.add(new WeatherType(5, "Mixed Rain and Snow", 5));
      weatherTypes.add(new WeatherType(6, "Mixed Rain and Sleet", 5));
      weatherTypes.add(new WeatherType(7, "Mixed Snow and Sleet", 5));
      weatherTypes.add(new WeatherType(8, "Freezing Drizzle", 165));
      weatherTypes.add(new WeatherType(9, "Drizzle", 6));
      weatherTypes.add(new WeatherType(10, "Freezing Rain", 12));
      weatherTypes.add(new WeatherType(11, "Showers", 3));
      weatherTypes.add(new WeatherType(12, "Showers", 3));
      weatherTypes.add(new WeatherType(13, "Snow Flurries", 13));
      weatherTypes.add(new WeatherType(14, "Light Snow Showers", 13));
      weatherTypes.add(new WeatherType(15, "Blowing Snow", 13));
      weatherTypes.add(new WeatherType(16, "Snow", 13));
      weatherTypes.add(new WeatherType(17, "Hail", 12));
      weatherTypes.add(new WeatherType(18, "Sleet", 5));
      weatherTypes.add(new WeatherType(19, "Dust", 15));
      weatherTypes.add(new WeatherType(20, "Foggy", 17));
      weatherTypes.add(new WeatherType(21, "Haze", 14));
      weatherTypes.add(new WeatherType(22, "Smoky", 17));
      weatherTypes.add(new WeatherType(23, "Breezy", 15));
      weatherTypes.add(new WeatherType(24, "Windy", 15));
      weatherTypes.add(new WeatherType(25, "Cold", 13));
      weatherTypes.add(new WeatherType(26, "Cloudy", 2));
      weatherTypes.add(new WeatherType(27, "Mostly Cloudy (Night)", 1));
      weatherTypes.add(new WeatherType(28, "Mostly Cloudy (Day)", 1));
      weatherTypes.add(new WeatherType(29, "Partly Cloudy (Night)", 1));
      weatherTypes.add(new WeatherType(30, "Partly Cloudy (Day)", 1));
      weatherTypes.add(new WeatherType(31, "Clear (night)", 0));
      weatherTypes.add(new WeatherType(32, "Sunny", 0));
      weatherTypes.add(new WeatherType(33, "Fair (Night)", 0));
      weatherTypes.add(new WeatherType(34, "Fair (Day)", 0));
      weatherTypes.add(new WeatherType(35, "Mixed Rain and Hail", 12));
      weatherTypes.add(new WeatherType(36, "Hot", 0));
      weatherTypes.add(new WeatherType(37, "Isolated Thunderstorms", 4));
      weatherTypes.add(new WeatherType(38, "Scattered Thunderstorms", 4));
      weatherTypes.add(new WeatherType(39, "Scattered Thunderstorms", 4));
      weatherTypes.add(new WeatherType(40, "Scattered Showers", 6));
      weatherTypes.add(new WeatherType(41, "Heavy Snow", 13));
      weatherTypes.add(new WeatherType(42, "Scattered Snow Showers", 13));
      weatherTypes.add(new WeatherType(43, "Heavy Snow", 13));
      weatherTypes.add(new WeatherType(44, "Partly Cloudy", 1));
      weatherTypes.add(new WeatherType(45, "Thundershowers", 4));
      weatherTypes.add(new WeatherType(46, "Snow Showers", 13));
      weatherTypes.add(new WeatherType(47, "Isolated Thundershowers", 4));
      weatherTypes.add(new WeatherType(3200, "Not Available", -1));
    }
    else if (Context.isTraditionalChineseVersion())
    {
      weatherTypes.add(new WeatherType(0, "龍捲風 ", 16));
      weatherTypes.add(new WeatherType(1, "熱帶風暴 ", 16));
      weatherTypes.add(new WeatherType(2, "颶風 ", 16));
      weatherTypes.add(new WeatherType(3, "強雷暴 ", 4));
      weatherTypes.add(new WeatherType(4, "雷暴 ", 4));
      weatherTypes.add(new WeatherType(5, "雨雪混合 ", 5));
      weatherTypes.add(new WeatherType(6, "混合雨和雨夾雪 ", 5));
      weatherTypes.add(new WeatherType(7, "混合雪和雨夾雪 ", 5));
      weatherTypes.add(new WeatherType(8, "冷凍小雨 ", 165));
      weatherTypes.add(new WeatherType(9, "小雨 ", 6));
      weatherTypes.add(new WeatherType(10, "凍雨 ", 12));
      weatherTypes.add(new WeatherType(11, "陣雨 ", 3));
      weatherTypes.add(new WeatherType(12, "陣雨 ", 3));
      weatherTypes.add(new WeatherType(13, "小雪 ", 13));
      weatherTypes.add(new WeatherType(14, "小陣雪 ", 13));
      weatherTypes.add(new WeatherType(15, "吹雪 ", 13));
      weatherTypes.add(new WeatherType(16, "雪 ", 13));
      weatherTypes.add(new WeatherType(17, "冰雹 ", 12));
      weatherTypes.add(new WeatherType(18, "雨夾雪 ", 5));
      weatherTypes.add(new WeatherType(19, "揚塵 ", 15));
      weatherTypes.add(new WeatherType(20, "霧 ", 17));
      weatherTypes.add(new WeatherType(21, "陰霾 ", 14));
      weatherTypes.add(new WeatherType(22, "煙 ", 17));
      weatherTypes.add(new WeatherType(23, "微風", 15));
      weatherTypes.add(new WeatherType(24, "有風 ", 15));
      weatherTypes.add(new WeatherType(25, "冷 ", 13));
      weatherTypes.add(new WeatherType(26, "多雲 ", 2));
      weatherTypes.add(new WeatherType(27, "晴間多雲（夜） ", 1));
      weatherTypes.add(new WeatherType(28, "晴間多雲 ", 1));
      weatherTypes.add(new WeatherType(29, "晴間多雲（夜） ", 1));
      weatherTypes.add(new WeatherType(30, "晴間多雲 ", 1));
      weatherTypes.add(new WeatherType(31, "晴朗（夜） ", 0));
      weatherTypes.add(new WeatherType(32, "晴朗 ", 0));
      weatherTypes.add(new WeatherType(33, "晴朗（夜） ", 0));
      weatherTypes.add(new WeatherType(34, "晴朗 ", 0));
      weatherTypes.add(new WeatherType(35, "混合雨和冰雹 ", 12));
      weatherTypes.add(new WeatherType(36, "熱 ", 0));
      weatherTypes.add(new WeatherType(37, "局部雷暴 ", 4));
      weatherTypes.add(new WeatherType(38, "零星雷暴 ", 4));
      weatherTypes.add(new WeatherType(39, "零星雷暴 ", 4));
      weatherTypes.add(new WeatherType(40, "零星陣雨 ", 6));
      weatherTypes.add(new WeatherType(41, "大雪 ", 13));
      weatherTypes.add(new WeatherType(42, "零星陣雪 ", 13));
      weatherTypes.add(new WeatherType(43, "大雪 ", 13));
      weatherTypes.add(new WeatherType(44, "晴間多雲 ", 1));
      weatherTypes.add(new WeatherType(45, "雷陣雨 ", 4));
      weatherTypes.add(new WeatherType(46, "陣雪 ", 13));
      weatherTypes.add(new WeatherType(47, "局部雷陣雨 ", 4));
      weatherTypes.add(new WeatherType(3200, "不可用 ", -1));
    }
    else
    {
      weatherTypes.add(new WeatherType(0, "龙卷风 ", 16));
      weatherTypes.add(new WeatherType(1, "热带风暴 ", 16));
      weatherTypes.add(new WeatherType(2, "飓风 ", 16));
      weatherTypes.add(new WeatherType(3, "强雷暴 ", 4));
      weatherTypes.add(new WeatherType(4, "雷暴 ", 4));
      weatherTypes.add(new WeatherType(5, "雨雪混合 ", 5));
      weatherTypes.add(new WeatherType(6, "混合雨和雨夹雪 ", 5));
      weatherTypes.add(new WeatherType(7, "混合雪和雨夹雪 ", 5));
      weatherTypes.add(new WeatherType(8, "冷冻小雨 ", 165));
      weatherTypes.add(new WeatherType(9, "小雨 ", 6));
      weatherTypes.add(new WeatherType(10, "冻雨 ", 12));
      weatherTypes.add(new WeatherType(11, "阵雨 ", 3));
      weatherTypes.add(new WeatherType(12, "阵雨 ", 3));
      weatherTypes.add(new WeatherType(13, "小雪 ", 13));
      weatherTypes.add(new WeatherType(14, "小阵雪 ", 13));
      weatherTypes.add(new WeatherType(15, "吹雪 ", 13));
      weatherTypes.add(new WeatherType(16, "雪 ", 13));
      weatherTypes.add(new WeatherType(17, "冰雹 ", 12));
      weatherTypes.add(new WeatherType(18, "雨夹雪 ", 5));
      weatherTypes.add(new WeatherType(19, "扬尘 ", 15));
      weatherTypes.add(new WeatherType(20, "雾 ", 17));
      weatherTypes.add(new WeatherType(21, "阴霾 ", 14));
      weatherTypes.add(new WeatherType(22, "烟 ", 17));
      weatherTypes.add(new WeatherType(23, "微风", 15));
      weatherTypes.add(new WeatherType(24, "有风 ", 15));
      weatherTypes.add(new WeatherType(25, "冷 ", 13));
      weatherTypes.add(new WeatherType(26, "多云 ", 2));
      weatherTypes.add(new WeatherType(27, "晴间多云（夜） ", 1));
      weatherTypes.add(new WeatherType(28, "晴间多云 ", 1));
      weatherTypes.add(new WeatherType(29, "晴间多云（夜） ", 1));
      weatherTypes.add(new WeatherType(30, "晴间多云 ", 1));
      weatherTypes.add(new WeatherType(31, "晴朗（夜） ", 0));
      weatherTypes.add(new WeatherType(32, "晴朗 ", 0));
      weatherTypes.add(new WeatherType(33, "晴朗（夜） ", 0));
      weatherTypes.add(new WeatherType(34, "晴朗 ", 0));
      weatherTypes.add(new WeatherType(35, "混合雨和冰雹 ", 12));
      weatherTypes.add(new WeatherType(36, "热 ", 0));
      weatherTypes.add(new WeatherType(37, "局部雷暴 ", 4));
      weatherTypes.add(new WeatherType(38, "零星雷暴 ", 4));
      weatherTypes.add(new WeatherType(39, "零星雷暴 ", 4));
      weatherTypes.add(new WeatherType(40, "零星阵雨 ", 6));
      weatherTypes.add(new WeatherType(41, "大雪 ", 13));
      weatherTypes.add(new WeatherType(42, "零星阵雪 ", 13));
      weatherTypes.add(new WeatherType(43, "大雪 ", 13));
      weatherTypes.add(new WeatherType(44, "晴间多云 ", 1));
      weatherTypes.add(new WeatherType(45, "雷阵雨 ", 4));
      weatherTypes.add(new WeatherType(46, "阵雪 ", 13));
      weatherTypes.add(new WeatherType(47, "局部雷阵雨 ", 4));
      weatherTypes.add(new WeatherType(3200, "不可用 ", -1));
    }
  }
  
  private static WeatherType getWeather(int code)
  {
    for (WeatherType type : weatherTypes) {
      if (type.getCode() == code) {
        return type;
      }
    }
    return null;
  }
  
  public static String getBaseUrl(String woeid)
  {
    String FILE_NAME = Storage.getWorkRootPath() + "weather.txt";
    String u = "c";
    String input = "";
    
    FileReader fr = null;
    BufferedReader br = null;
    label186:
    try
    {
      File dir = new File(FILE_NAME);
      if (dir.exists())
      {
        fr = new FileReader(FILE_NAME);
        br = new BufferedReader(fr);
        if ((input = br.readLine()) != null)
        {
          u = input;
          break label186;
        }
      }
      else
      {
        u = (Context.isMultiLanguageVersion()) || (Context.isTraditionalChineseVersion()) ? "c" : "f";
      }
    }
    catch (Exception localException)
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
    }
    finally
    {
      if (br != null) {
        try
        {
          br.close();
        }
        catch (IOException localIOException2) {}
      }
      if (fr != null) {
        try
        {
          fr.close();
        }
        catch (IOException localIOException3) {}
      }
    }
    String baseurl = "";
    Context.isMultiLanguageVersion();
    



    baseurl = "https://query.yahooapis.com/v1/public/yql?q=" + encodeYqlParam(woeid, u) + 
      "&format=xml";
    return baseurl;
  }
  
  public static String encodeYqlParam(String woeid, String u)
  {
    try
    {
      String yql = "select * from weather.forecast where woeid=" + woeid + 
        " and u='" + u + "'";
      return URLEncoder.encode(yql, "UTF-8");
    }
    catch (UnsupportedEncodingException e) {}
    return "";
  }
  
  public static List convertToCitys(List elements, List citys)
  {
      for(Iterator iterator = elements.iterator(); iterator.hasNext();)
      {
          Element element = (Element)iterator.next();
          String province = element.attributeValue("name");
          List elementProvinces = _ListElement(element.elements());
          for(Iterator iterator1 = elementProvinces.iterator(); iterator1.hasNext();)
          {
              Element elementProvince = (Element)iterator1.next();
              String cityName = elementProvince.attributeValue("name");
              City city = new City();
              city.setCityName(cityName);
              city.setProvince(province);
              if(!existCity(city, citys))
                  CityHandle.create(city);
          }

      }

      return CityHandle.readAll();
  }
  
  private static List _ListElement(List list)
  {
      return list;
  }
  
  private static boolean existCity(City city, List<City> citys)
  {
    for (City tempCity : citys) {
      if ((city.getProvince().equals(tempCity.getProvince())) && 
        (city.getCityName().equals(tempCity.getCityName()))) {
        return true;
      }
    }
    return false;
  }
  
  static DateFormat format = new SimpleDateFormat("dd MMM yyyy", 
    Locale.ENGLISH);
  
  public void run()
  {
    long sleepTime = 7200000L;
    for (;;)
    {
      try
      {
        Document documentCitys = getDocumentCitys();
        
        xmlCitysToDataBase(documentCitys);
        
        List<Weather> weatherList = new ArrayList();
        
        List<City> errorWeatherList = new ArrayList();
        

        traverseAccessCity(_ListElement(documentCitys.selectNodes("//state/city")), 
          CityHandle.readTerminalCitys(), weatherList, 
          errorWeatherList, this.traverseCount, false);
        
        sleepTime = 7200000L;
      }
      catch (Exception e)
      {
        sleepTime = 600000L;
        e.printStackTrace();
      }
      try
      {
        sleep(sleepTime);
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }
  }
  
  private void xmlCitysToDataBase(Document documentCitys)
  {
    List<City> oldcitys = CityHandle.readAll();
    
    convertToCitys(_ListElement(documentCitys.selectNodes("//state")), oldcitys);
    
    WeatherHandle.removeExpired();
  }
  
  private int traverseCount = 3;
  
  private void traverseAccessCity(List<Element> weatherElements, List<City> mods, List<Weather> weatherXMLList, List<City> errorCityList, int traverseCount, boolean isForceReresh)
  {
    for (City mod : mods) {
      updateWeather(weatherElements, mod, weatherXMLList, errorCityList, isForceReresh);
    }
    traverseCount--;
    if ((traverseCount > 0) && (errorCityList.size() > 0)) {
      traverseAccessCity(weatherElements, errorCityList, weatherXMLList, 
        new ArrayList(), traverseCount, isForceReresh);
    }
  }
  
  public void updateCitys(City city)
    throws DocumentException
  {
    Document documentCitys = getDocumentCitys();
    
    List<Element> elements = _ListElement(documentCitys.selectNodes("//state/city"));
    
    List<City> citys = new ArrayList();
    
    citys.add(city);
    
    traverseAccessCity(elements, citys, new ArrayList(), 
      new ArrayList(), this.traverseCount, true);
  }
  
  private static void updateWeather(List<Element> elements, City mod, List<Weather> weatherXMLList, List<City> errorWeatherList, boolean isForceReresh)
  {
    List<Element> weatherElements = new ArrayList();
    for (Element element : elements) {
      if (mod.getCityName().equals(element.attributeValue("name"))) {
        weatherElements.add(element);
      }
    }
    for (Element element : weatherElements) {
      try
      {
        saveWeather(mod, weatherXMLList, 
          getBaseUrl(element.attributeValue("woeid")), isForceReresh);
        
        MessageServer messageServer = new MessageServer();
        List<Long> list = CityHandle.readCityTerminals(mod.getCityId());
        if (list != null) {
          for (Long i : list) {
            try
            {
              messageServer.noticeAssignChanged(i.longValue());
            }
            catch (Exception localException1) {}
          }
        }
      }
      catch (Exception e)
      {
        if (errorWeatherList != null) {
          errorWeatherList.add(mod);
        }
      }
    }
    if (weatherXMLList.size() > 0)
    {
      WeatherHandle.add(weatherXMLList);
      weatherXMLList.clear();
    }
  }
  
  private static void saveWeather(City mod, List weatherList, String url, boolean isForceReresh)
	        throws Exception
	    {
	        int low = -1;
	        int high = -1;
	        int tp = -1;
	        Date date = null;
	        Document documentWeb = getWeather(url);
	        if(documentWeb == null)
	            return;
	        String versionText = ((Element)documentWeb.selectNodes("//channel/lastBuildDate").get(0)).getTextTrim();
	        if(isForceReresh || isUpdate(versionText))
	        {
	            List elementsWeather = documentWeb.selectNodes("//channel/item/yweather:forecast");
	            for(Iterator iterator = elementsWeather.iterator(); iterator.hasNext();)
	            {
	                Element eWeather = (Element)iterator.next();
	                String text;
	                try
	                {
	                    WeatherType weatherType = getWeather(Integer.valueOf(eWeather.attributeValue("code")).intValue());
	                    text = weatherType.getDescription();
	                    tp = weatherType.getCode();
	                    low = Integer.valueOf(eWeather.attributeValue("low")).intValue();
	                    high = Integer.valueOf(eWeather.attributeValue("high")).intValue();
	                    date = format.parse(eWeather.attributeValue("date"));
	                }
	                catch(Exception e)
	                {
	                    tp = -1;
	                    low = -100;
	                    high = 100;
	                    text = "\u6682\u65E0\u5B9E\u51B5";
	                    date = new Date();
	                }
	                Weather weather = new Weather();
	                weather.setCityId(mod.getCityId());
	                weather.setWeatherDate(date);
	                weather.setWeatherType(tp);
	                weather.setHigh(high);
	                weather.setLow(low);
	                weather.setWeatherText(text);
	                weatherList.add(weather);
	                if(weatherList.size() >= 25)
	                {
	                    WeatherHandle.add(weatherList);
	                    weatherList.clear();
	                }
	            }

	        }
	    }
  
  private static void writeContext(String fileName, String context)
  {
    BufferedWriter bw1 = null;
    try
    {
      FileWriter fw1 = new FileWriter(fileName, false);
      bw1 = new BufferedWriter(fw1);
      bw1.write(context);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      if (bw1 != null) {
        try
        {
          bw1.close();
        }
        catch (Exception e2)
        {
          e2.printStackTrace();
        }
      }
    }
    finally
    {
      if (bw1 != null) {
        try
        {
          bw1.close();
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    }
  }
  
  private static boolean isUpdate(String version)
    throws Exception
  {
    String FILE_NAME = Storage.getWorkRootPath() + "cityversion.txt";
    String input = "";
    
    FileReader fr = null;
    BufferedReader br = null;
    try
    {
      File dir = new File(FILE_NAME);
      if (!dir.exists())
      {
        Storage.createFileDirectory(FILE_NAME);
        
        writeContext(FILE_NAME, version);
        return true;
      }
      fr = new FileReader(FILE_NAME);
      br = new BufferedReader(fr);
      if ((input = br.readLine()) != null)
      {
        if (!version.equals(input))
        {
          writeContext(FILE_NAME, version);
          return true;
        }
        return false;
      }
    }
    catch (Exception e)
    {
      throw e;
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
    }
    if (br != null) {
      try
      {
        br.close();
      }
      catch (IOException localIOException8) {}
    }
    if (fr != null) {
      try
      {
        fr.close();
      }
      catch (IOException localIOException9) {}
    }
    return false;
  }
  
  private static Document getWeather(String url)
    throws Exception
  {
    SAXReader reader = new SAXReader();
    
    Map<String, String> map = new HashMap();
    
    map.put("yweather", "http://xml.weather.yahoo.com/ns/rss/1.0");
    
    reader.getDocumentFactory().setXPathNamespaceURIs(map);
    
    return new SAXReader().read(getStream(url));
  }
  
  private static Document getDocumentCitys()
    throws DocumentException
  {
    if (Context.isAlcoholVersion())
    {
      Document document = DocumentHelper.createDocument();
      document.addElement("cities");
      return document;
    }
    SAXReader saxReader = new SAXReader();
    if (Context.isMultiLanguageVersion()) {
      return saxReader.read(Context.getResourceURL("/chinacity.xml"));
    }
    if (Context.isTraditionalChineseVersion()) {
      return saxReader.read(Context.getResourceURL("/taiwan_city.xml"));
    }
    return saxReader.read(Context.getResourceURL("/globalcity.xml"));
  }
  
  private static InputStream getStream(String url)
    throws MalformedURLException, IOException
  {
    return new URL(url).openStream();
  }
}
