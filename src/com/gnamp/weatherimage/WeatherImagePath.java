 package com.gnamp.weatherimage;
 
 import com.gnamp.server.Storage;
 import com.gnamp.server.handle.WeatherHandle;
 import com.gnamp.server.model.Weather;
 import com.sun.image.codec.jpeg.JPEGCodec;
 import com.sun.image.codec.jpeg.JPEGImageEncoder;
 import java.awt.Color;
 import java.awt.Font;
 import java.awt.FontMetrics;
 import java.awt.Graphics;
 import java.awt.Graphics2D;
 import java.awt.Image;
 import java.awt.image.BufferedImage;
 import java.io.File;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.text.DecimalFormat;
 import java.text.SimpleDateFormat;
 import java.util.Calendar;
 import java.util.Date;
 import java.util.GregorianCalendar;
 import java.util.List;
 import javax.imageio.ImageIO;
 import javax.swing.JLabel;
 
 public class WeatherImagePath
 {
   public static String getWeatherImagePath(int cityId, String typeImage, String weatherDay, String fontName)
   {
     String imagePath = "";
     try
     {
       Weather weathertoday = new Weather();
       Weather weathertomorrow = new Weather();
       Weather weatherafter = new Weather();
       
       List<Weather> weathers = WeatherHandle.read(cityId);
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
         String afterString = formatter.format(date2);
         for (Weather w : weathers)
         {
           if (w != null) {
             if (formatter.format(w.getWeatherDate()).equals(todaydateString))
             {
               weathertoday.setWeatherDate(w.getWeatherDate());
               
               weathertoday.setCityId(cityId);
               weathertoday.setCityName(w.getCityName());
               weathertoday.setHigh(w.getHigh());
               weathertoday.setLow(w.getLow());
               weathertoday.setWeatherType(w.getWeatherType());
               weathertoday.setWeatherText(w.getWeatherText());
             }
           }
           if (w != null) {
             if (formatter.format(w.getWeatherDate()).equals(tomorrowdateString))
             {
               weathertomorrow.setWeatherDate(w.getWeatherDate());
               
               weathertomorrow.setCityId(cityId);
               weathertomorrow.setCityName(w.getCityName());
               weathertomorrow.setHigh(w.getHigh());
               weathertomorrow.setLow(w.getLow());
               weathertomorrow.setWeatherType(w.getWeatherType());
               weathertomorrow.setWeatherText(w.getWeatherText());
             }
           }
           if (w != null) {
             if (formatter.format(w.getWeatherDate()).equals(afterString))
             {
               weatherafter.setWeatherDate(w.getWeatherDate());
               
               weatherafter.setCityId(cityId);
               weatherafter.setCityName(w.getCityName());
               weatherafter.setHigh(w.getHigh());
               weatherafter.setLow(w.getLow());
               weatherafter.setWeatherType(w.getWeatherType());
               weatherafter.setWeatherText(w.getWeatherText());
             }
           }
         }
       }
       Font f = new Font(fontName, 0, 200);
       
       String cityPath = Storage.getWorkRootPath() + "weather" + 
         File.separatorChar + String.valueOf(cityId) + 
         File.separatorChar + fontName + 
         File.separatorChar;
       if (typeImage.equals("test"))
       {
         BufferedImage image = new BufferedImage(252, 130, 
           1);
         Graphics g = image.createGraphics();
         g.setColor(Color.WHITE);
         g.fillRect(0, 0, 252, 130);
         try
         {
           ImageIO.write(image, "png", new File(cityPath + "weather.png"));
         }
         catch (IOException ex)
         {
           ex.printStackTrace();
         }
         if (weatherDay.equals("today")) {
           imagePath = ImgYin(weathertoday, "D:\\biao.jpg", cityPath + 
             "weather.jpg");
         } else if (weatherDay.equals("tomorrow")) {
           imagePath = ImgYin(weathertomorrow, "D:\\biao.jpg", 
             cityPath + "weather.jpg");
         } else if (weatherDay.equals("aftertomorrow")) {
           imagePath = ImgYin(weatherafter, "D:\\biao.jpg", 
             cityPath + "weather.jpg");
         }
       }
       if (typeImage.equals("tmpt"))
       {
         String tpt = "";
         int w = 0;
         if (weatherDay.equals("today")) {
           tpt = 
             String.valueOf(weathertoday.getLow()) + "°~" + String.valueOf(weathertoday.getHigh()) + "°";
         } else if (weatherDay.equals("tomorrow")) {
           tpt = 
             String.valueOf(weathertomorrow.getLow()) + "°~" + String.valueOf(weathertomorrow.getHigh()) + "°";
         } else if (weatherDay.equals("aftertomorrow")) {
           tpt = 
             String.valueOf(weatherafter.getLow()) + "°~" + String.valueOf(weatherafter.getHigh()) + "°";
         }
         char[] arrays = tpt.toCharArray();
         FontMetrics fm = new JLabel().getFontMetrics(f);
         for (int i = 0; i < arrays.length; i++) {
           w += fm.charWidth(arrays[i]);
         }
         if (w <= 0) {
           w = 10;
         }
         BufferedImage buffImage = new BufferedImage(w, fm.getHeight(), 1);
         Graphics2D g = buffImage.createGraphics();
         
         g.setColor(Color.BLACK);
         g.fillRect(0, 0, buffImage.getWidth(), buffImage.getHeight());
         
         g.setColor(Color.WHITE);
         g.setFont(f);
         
         g.drawString(tpt, 0, Integer.parseInt(new DecimalFormat("0").format(200L)));
         
 
 
         String filepath = cityPath + "tmpt.png";
         
         File file = new File(filepath);
         file.getParentFile().mkdirs();
         if (!file.exists()) {
           try
           {
             file.createNewFile();
           }
           catch (IOException e)
           {
             imagePath = "";
           }
         }
         try
         {
           ImageIO.write(buffImage, "png", new File(filepath));
         }
         catch (IOException ex)
         {
           imagePath = "";
         }
         imagePath = filepath;
       }
       if (typeImage.equals("content"))
       {
         String tpt = "";
         int w = 0;
         if (weatherDay.equals("today")) {
           tpt = weathertoday.getWeatherText();
         } else if (weatherDay.equals("tomorrow")) {
           tpt = weathertomorrow.getWeatherText();
         } else if (weatherDay.equals("aftertomorrow")) {
           tpt = weatherafter.getWeatherText();
         }
         char[] arrays = tpt.toCharArray();
         FontMetrics fm = new JLabel().getFontMetrics(f);
         for (int i = 0; i < arrays.length; i++) {
           w += fm.charWidth(arrays[i]);
         }
         if (w <= 0) {
           w = 10;
         }
         BufferedImage buffImage = new BufferedImage(w, fm.getHeight(), 
           1);
         Graphics2D g = buffImage.createGraphics();
         
         g.setColor(Color.BLACK);
         g.fillRect(0, 0, buffImage.getWidth(), buffImage.getHeight());
         
         g.setColor(Color.WHITE);
         g.setFont(f);
         
         g.drawString(tpt, 0, Integer.parseInt(new DecimalFormat("0").format(200L)));
         
 
         String filepath = cityPath + "content.png";
         
         File file = new File(filepath);
         file.getParentFile().mkdirs();
         if (!file.exists()) {
           try
           {
             file.createNewFile();
           }
           catch (IOException e)
           {
             imagePath = "";
           }
         }
         try
         {
           ImageIO.write(buffImage, "png", new File(filepath));
         }
         catch (IOException ex)
         {
           imagePath = "";
         }
         imagePath = filepath;
       }
     }
     catch (NumberFormatException e)
     {
       imagePath = "";
     }
     return imagePath;
   }
   
   public static String ImgYin(Weather w, String Imagename_biao, String ImgName)
   {
     try
     {
       File _file = new File(ImgName);
       Image src = ImageIO.read(_file);
       int wideth = src.getWidth(null);
       int height = src.getHeight(null);
       BufferedImage image = new BufferedImage(wideth, height, 
         1);
       Graphics g = image.createGraphics();
       g.drawImage(src, 0, 0, wideth, height, null);
       
       File _filebiao = new File(Imagename_biao);
       Image src_biao = ImageIO.read(_filebiao);
       int wideth_biao = src_biao.getWidth(null);
       int height_biao = src_biao.getHeight(null);
       
       g.setColor(Color.RED);
       g.drawString(String.valueOf(w.getWeatherDate()), 0, height - 10);
       g.drawString(w.getWeatherText(), 0, height - 30);
       g.drawImage(src_biao, wideth - 84, height - 110, wideth_biao, 
         height_biao, null);
       g.setColor(Color.RED);
       g.drawString(
         String.valueOf(w.getLow()) + "~" + 
         String.valueOf(w.getHigh()), 0, height - 48);
       
       g.drawImage(src_biao, wideth - 173, height - 110, wideth_biao, 
         height_biao, null);
       
       g.drawImage(src_biao, wideth - 262, height - 110, wideth_biao, 
         height_biao, null);
       
       g.dispose();
       
       FileOutputStream out = new FileOutputStream(ImgName);
       JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
       encoder.encode(image);
       out.close();
     }
     catch (Exception e)
     {
       ImgName = "";
     }
     return ImgName;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.weatherimage.WeatherImagePath
 * JD-Core Version:    0.7.0.1
 */