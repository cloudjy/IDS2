 package com.gnamp.struts.utils;
 
 import com.gnamp.server.model.RssXml;
 import com.gnamp.webservice.Rss;
 import java.io.InputStream;
 import java.net.HttpURLConnection;
 import java.net.URL;
 import java.util.ArrayList;
 import java.util.List;
 
 public class RssUtil
 {
   public static RssXml getContextByURL(RssXml marquee)
     throws Exception
   {
     String url = marquee.getUrl();
     URL ser = new URL(url);
     HttpURLConnection con = (HttpURLConnection)ser.openConnection();
     InputStream in = con.getInputStream();
     RssFile rssFile = new RssFile(in);
     in.close();
     con.disconnect();
     
     StringBuilder builder = new StringBuilder();
     for (RssFile.Item item : rssFile.getItems()) {
       builder.append("  *").append(item.getTitle().trim());
     }
     marquee.setText(builder.toString());
     return marquee;
   }
   
   public static List<Rss> getContextByURL(String url)
     throws Exception
   {
     List<Rss> rsses = new ArrayList();
     URL ser = new URL(url);
     HttpURLConnection con = (HttpURLConnection)ser.openConnection();
     InputStream in = con.getInputStream();
     RssFile rssFile = new RssFile(in);
     in.close();
     con.disconnect();
     
     int size = rssFile.getItems().size() > 50 ? 50 : rssFile.getItems()
       .size();
     for (int i = 0; i < size; i++)
     {
       Rss rss = new Rss();
       rss.setTitle(((RssFile.Item)rssFile.getItems().get(i)).getTitle().trim());
       rsses.add(rss);
     }
     return rsses;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.utils.RssUtil
 * JD-Core Version:    0.7.0.1
 */