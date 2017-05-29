package com.gnamp.struts.admin.action;

import com.gnamp.server.Storage;
import com.gnamp.server.handle.CityHandle;
import com.gnamp.server.handle.MessageServer;
import com.gnamp.server.handle.WeatherHandle;
import com.gnamp.struts.filter.Context;
import com.gnamp.struts.filter.YahooWeather;
import com.gnamp.struts.tree.ITree;
import com.gnamp.struts.treeimpl.CityTree;
import com.gnamp.struts.utils.JsonUtils;
import com.gnamp.struts.utils.MapTool;
import com.gnamp.struts.vo.PageBean;
import com.gnamp.weatherservice.CityWeather;
import com.gnamp.weatherservice.WeatherServiceLocator;
import com.gnamp.weatherservice.WeatherServiceSoap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;
import net.sf.json.JSONArray;

public class WeatherAction
  extends JSONAction
{
  public int id = 0;
  
  public int getId()
  {
    return this.id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public com.gnamp.server.model.City city = null;
  public List<Integer> citys;
  public String province;
  
  public com.gnamp.server.model.City getCity()
  {
    return this.city;
  }
  
  public void setCity(com.gnamp.server.model.City city)
  {
    this.city = city;
  }
  
  public List<Integer> getCitys()
  {
    return this.citys;
  }
  
  public void setCitys(List<Integer> citys)
  {
    this.citys = citys;
  }
  
  public String getProvince()
  {
    return this.province;
  }
  
  public void setProvince(String province)
  {
    this.province = province;
  }
  
  PageBean page = new PageBean();
  
  public PageBean getPage()
  {
    return this.page;
  }
  
  public void setPage(PageBean page)
  {
    this.page = page;
  }
  
  public void list()
  {
    try
    {
      this.response.setContentType("application/x-json");
      this.response.setCharacterEncoding("UTF-8");
      PrintWriter writer = this.response.getWriter();
      writer.print(JSONArray.fromObject(convertProvinces(
        CityHandle.readAllProvinces())));
      
      writer.flush();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  private List<ITree<com.gnamp.server.model.City>> convertCity(List<com.gnamp.server.model.City> list)
  {
    List<ITree<com.gnamp.server.model.City>> citys = new ArrayList();
    for (com.gnamp.server.model.City c : list) {
      citys.add(new CityTree().convert(c));
    }
    return citys;
  }
  
  public List<ITree<String>> convertProvinces(List<String> provinces)
  {
    List<ITree<String>> lists = new ArrayList();
    for (int i = 0; i < provinces.size(); i++) {
      lists.add(new ProvincesVo(i, (String)provinces.get(i)));
    }
    return lists;
  }
  
  public void updateCity()
  {
    try
    {
      YahooWeather yahooWeather = new YahooWeather();
      yahooWeather.updateCitys(CityHandle.read(getId()));
      response(JSONSuccessString(JSONArrayToString(
        WeatherHandle.read(getId()))));
    }
    catch (Exception e)
    {
      e.printStackTrace();
      response(JSONErrorString());
    }
  }
  
  public void readU()
  {
    String FILE_NAME = Storage.getWorkRootPath() + "weather.txt";
    this.u = ((Context.isMultiLanguageVersion()) || (Context.isTraditionalChineseVersion()) ? "c" : "f");
    
    String input = "";
    
    FileReader fr = null;
    BufferedReader br = null;
    try
    {
      File dir = new File(FILE_NAME);
      if (dir.exists())
      {
        fr = new FileReader(FILE_NAME);
        br = new BufferedReader(fr);
        if ((input = br.readLine()) != null) {
          this.u = input;
        }
      }
      response(JSONSuccessString(this.u));
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
  }
  
  public void citys()
  {
    try
    {
      String sql = "";
      if ((this.province != null) && (!this.province.equals("")))
      {
        this.province = URLDecoder.decode(this.province, "utf-8");
        sql = "SELECT CITY_ID, NAME, PROVINCE FROM tb_city WHERE PROVINCE=:PROVINCE " + (
        

          this.page.getPageSize() != 2147483647 ? "LIMIT " + 
          (this.page.getCurrentPage() - 1) * 
          this.page.getPageSize() + "," + this.page.getPageSize() : 
          "");
        
        this.page.setTotalRows(CityHandle.readAll(this.province).size());
        response(JSONSuccessString(
          JSONArrayToString(CityHandle.readAll(sql, 
          getParameters(this.province))), new Map[] {
          new MapTool().putObject("page", this.page) }));
      }
      else
      {
        sql = 
        

          "SELECT CITY_ID, NAME, PROVINCE FROM tb_city WHERE 1=1 " + (this.page.getPageSize() != 2147483647 ? "LIMIT " + 
          (this.page.getCurrentPage() - 1) * 
          this.page.getPageSize() + "," + this.page.getPageSize() : 
          "");
        
        this.page.setTotalRows(CityHandle.readAll().size());
        response(JSONSuccessString(
          JSONArrayToString(CityHandle.readAll(sql, null)), new Map[] {
          new MapTool().putObject("page", this.page) }));
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  private MapTool<String, Object> getParameters(String province)
  {
    return (province.equals("")) || (province == null) ? null : 
      new MapTool().putObject("PROVINCE", province);
  }
  
  public void add()
  {
    try
    {
      if (CityHandle.create(this.city)) {
        JsonUtils.writeSuccess(this.response);
      } else {
        JsonUtils.writeErrorMessage(this.response, getText("additemfail"));
      }
    }
    catch (Exception e)
    {
      JsonUtils.writeErrorMessage(this.response, getText("additemfail"));
    }
  }
  
  public String preadd()
  {
    return "add";
  }
  
  public com.gnamp.server.model.Weather wtoday = new com.gnamp.server.model.Weather();
  public com.gnamp.server.model.Weather wtomorrow = new com.gnamp.server.model.Weather();
  public com.gnamp.server.model.Weather wafter = new com.gnamp.server.model.Weather();
  
  public com.gnamp.server.model.Weather getWtoday()
  {
    return this.wtoday;
  }
  
  public void setWtoday(com.gnamp.server.model.Weather wtoday)
  {
    this.wtoday = wtoday;
  }
  
  public com.gnamp.server.model.Weather getWtomorrow()
  {
    return this.wtomorrow;
  }
  
  public void setWtomorrow(com.gnamp.server.model.Weather wtomorrow)
  {
    this.wtomorrow = wtomorrow;
  }
  
  public com.gnamp.server.model.Weather getWafter()
  {
    return this.wafter;
  }
  
  public void setWafter(com.gnamp.server.model.Weather wafter)
  {
    this.wafter = wafter;
  }
  
  public String preedit()
  {
    this.city = CityHandle.read(this.id);
    
    List<com.gnamp.server.model.Weather> weathers = WeatherHandle.read(this.id);
    if (weathers != null)
    {
      Date date = new Date();
      Calendar calendar = new GregorianCalendar();
      calendar.setTime(date);
      calendar.add(5, 0);
      date = calendar.getTime();
      
      Date date1 = new Date();
      Calendar calendar1 = new GregorianCalendar();
      calendar1.setTime(date1);
      calendar1.add(5, 1);
      date1 = calendar1.getTime();
      

      Date date2 = new Date();
      Calendar calendar2 = new GregorianCalendar();
      calendar2.setTime(date2);
      calendar2.add(5, 2);
      date2 = calendar2.getTime();
      
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      
      String todaydateString = formatter.format(date);
      String tomorrowdateString = formatter.format(date1);
      String afterdateString = formatter.format(date2);
      for (com.gnamp.server.model.Weather w : weathers)
      {
        if (w != null) {
          if (formatter.format(w.getWeatherDate()).equals(todaydateString))
          {
            this.wtoday.setCityId(this.id);
            this.wtoday.setCityName(this.city.getCityName());
            this.wtoday.setHigh(w.getHigh());
            this.wtoday.setLow(w.getLow());
            this.wtoday.setWeatherDate(w.getWeatherDate());
            this.wtoday.setWeatherType(w.getWeatherType());
            this.wtoday.setWeatherText(w.getWeatherText());
          }
        }
        if (w != null) {
          if (formatter.format(w.getWeatherDate()).equals(tomorrowdateString))
          {
            this.wtomorrow.setCityId(this.id);
            this.wtomorrow.setCityName(this.city.getCityName());
            this.wtomorrow.setHigh(w.getHigh());
            this.wtomorrow.setLow(w.getLow());
            this.wtomorrow.setWeatherDate(w.getWeatherDate());
            this.wtomorrow.setWeatherType(w.getWeatherType());
            this.wtomorrow.setWeatherText(w.getWeatherText());
          }
        }
        if (w != null) {
          if (formatter.format(w.getWeatherDate()).equals(afterdateString))
          {
            this.wafter.setCityId(this.id);this.wafter.setCityName(this.city.getCityName());
            this.wafter.setHigh(w.getHigh());this.wafter.setLow(w.getLow());
            this.wafter.setWeatherDate(w.getWeatherDate());
            this.wafter.setWeatherType(w.getWeatherType());
            this.wafter.setWeatherText(w.getWeatherText());
          }
        }
      }
    }
    return "edit";
  }
  
  public String u = "";
  
  public String getU()
  {
    return this.u;
  }
  
  public void setU(String u)
  {
    this.u = u;
  }
  
  public void writeu()
  {
    String FILE_NAME = Storage.getWorkRootPath() + "weather.txt";
    FileWriter fw = null;
    BufferedWriter bw = null;
    try
    {
      File dir = new File(FILE_NAME);
      if (!dir.exists())
      {
        Storage.createFileDirectory(FILE_NAME);
        
        fw = new FileWriter(FILE_NAME, false);
        bw = new BufferedWriter(fw);
        bw.write(this.u);
      }
      else
      {
        fw = new FileWriter(FILE_NAME, false);
        bw = new BufferedWriter(fw);
        bw.write(this.u);
      }
    }
    catch (Exception localException)
    {
      if (bw != null) {
        try
        {
          bw.close();
        }
        catch (IOException localIOException) {}
      }
      if (fw != null) {
        try
        {
          fw.close();
        }
        catch (IOException localIOException1) {}
      }
    }
    finally
    {
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
    }
    JsonUtils.writeSuccess(this.response);
  }
  
  public void edit()
  {
    List<com.gnamp.server.model.Weather> weathers = new ArrayList();
    
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.add(6, 1);
    Date tomorrowdate = calendar.getTime();
    

    GregorianCalendar calendar1 = new GregorianCalendar();
    calendar1.add(6, 2);Date afterdate = 
      calendar1.getTime();
    

    this.wtoday.setCityId(this.city.getCityId());
    this.wtoday.setCityName(this.city.getCityName());
    this.wtoday.setWeatherDate(new Date());
    
    this.wtomorrow.setCityId(this.city.getCityId());
    this.wtomorrow.setCityName(this.city.getCityName());
    this.wtomorrow.setWeatherDate(tomorrowdate);
    

    this.wafter.setCityId(this.city.getCityId());
    this.wafter.setCityName(this.city.getCityName());
    this.wafter.setWeatherDate(afterdate);
    

    weathers.add(this.wtoday);
    weathers.add(this.wtomorrow);
    weathers.add(this.wafter);
    Iterator localIterator;

    try
    {
      if (WeatherHandle.add(weathers) > 0) {
        JsonUtils.writeSuccess(this.response);
      } else {
        JsonUtils.writeErrorMessage(this.response, 
          getText("createclienterror"));
      }
    }
    catch (Exception e)
    {
      JsonUtils.writeErrorMessage(this.response, getText("createclienterror"));
    }
    finally
    {
      
      MessageServer messageServer = new MessageServer();
      List<Long> list = CityHandle.readCityTerminals(this.city.getCityId());
      if (list != null) {
        for (Long i : list) {
          try
          {
            messageServer.noticeAssignChanged(i.longValue());
          }
          catch (Exception localException2) {}
        }
      }
    }
  }
  
  public void remove()
  {
    Set<Integer> errors = new TreeSet();
    try
    {
      for (Integer i : this.citys) {
        try
        {
          if (!CityHandle.remove(i.intValue())) {
            errors.add(i);
          }
        }
        catch (Exception e)
        {
          errors.add(i);
          throw new Exception();
        }
      }
      JsonUtils.writeSuccess(this.response);
    }
    catch (Exception e)
    {
      JsonUtils.writeErrorMessage(this.response, getText("deleteerror") + 
        " ID:" + errors);
    }
  }
  
  public void queryWeather2()
  {
    WeatherServiceLocator weatherSrviceLocator = new WeatherServiceLocator();
    String result = "";
    try
    {
      WeatherServiceSoap serviceSoap = weatherSrviceLocator
        .getWeatherServiceSoap();
      
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
      if ((mods != null) && (mods.size() > 0)) {
        for (int j = 0; j < mods.size(); j++)
        {
          String Wtext = "暂无实况";
          int Tp = -1;
          int Low = -100;
          int High = 100;
          Date date = new Date();
          






          CityWeather CityWeather = serviceSoap.queryWeather2(
            ((com.gnamp.server.model.City)mods.get(j)).getCityName(), ((com.gnamp.server.model.City)mods.get(j)).getProvince());
          if ((CityWeather != null) && 
            (CityWeather.getWeathers() != null) && 
            (CityWeather.getWeathers().length > 0)) {
            if ((CityWeather.getCityName().equals(((com.gnamp.server.model.City)mods.get(j)).getCityName())) || 
            
              (CityWeather.getStateName().equals(((com.gnamp.server.model.City)mods.get(j)).getProvince()))) {
              for (int i = 0; i < CityWeather.getWeathers().length; i++)
              {
                try
                {
                  Wtext = 
                    CityWeather.getWeathers()[i].getText();
                  Tp = CityWeather.getWeathers()[i].getType();
                  Low = CityWeather.getWeathers()[i].getLow();
                  High = CityWeather.getWeathers()[i]
                    .getHigh();
                  date = CityWeather.getWeathers()[i]
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
        }
      }
      WeatherHandle.add((List)weatherList);
      

























      response(JSONSuccessString(result));
    }
    catch (ServiceException e)
    {
      e.printStackTrace();
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }
  
  class ProvincesVo
    implements ITree<String>
  {
    private int id;
    private String name;
    
    ProvincesVo(int id, String name)
    {
      this.id = id;
      this.name = name;
    }
    
    public int getId()
    {
      return this.id;
    }
    
    public String getName()
    {
      return this.name;
    }
    
    public boolean getIsParent()
    {
      return false;
    }
  }
}
