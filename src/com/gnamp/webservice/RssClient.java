 package com.gnamp.webservice;
 
 import com.gnamp.server.IDCreator;
 import com.gnamp.struts.filter.Context;
 import com.gnamp.struts.utils.RssUtil;
 import java.lang.reflect.InvocationTargetException;
 import java.net.MalformedURLException;
 import java.net.URL;
 import java.util.ArrayList;
 import java.util.List;
 import org.apache.axis.client.Call;
 import org.apache.axis.message.MessageElement;
 import org.apache.axis.types.Schema;
 import org.apache.commons.lang.reflect.MethodUtils;
 import org.dom4j.Attribute;
 import org.dom4j.Document;
 import org.dom4j.Element;
 import org.dom4j.io.SAXReader;
 
 public class RssClient
   extends WebService
 {
   protected URL getURL()
     throws MalformedURLException
   {
     return new URL(getHost() + "/RssService.asmx?WSDL");
   }
   
   protected String getSoapactionURI()
   {
     return "http://tempuri.org/getRss";
   }
   
   protected String getRemoteMethod()
   {
     return "getRss";
   }
   
   protected String getNamespaceURI()
   {
     return "http://tempuri.org/";
   }
   
   protected void addParameter(Call call) {}
   
   public List<Rss> getResultWebservice(String systemSourceName)
     throws Exception
   {
     Schema schemaResult = (Schema)invoke(new Object[] { systemSourceName });
     if (schemaResult == null) {
       throw new UnsupportedOperationException("从中转服务器取得数据失败");
     }
     List<Rss> result = new ArrayList();
     MessageElement[] messageElements = schemaResult.get_any();
     for (MessageElement element : messageElements) {
       result.add(parse(element.getRealElement().getChildren()));
     }
     return result;
   }
   
   public List<Rss> getResult(String systemSourceName)
     throws Exception
   {
     List<Rss> result = getAllNewsRss();
     for (Rss rss : result)
     {
       String title = rss.getCategory() + "-" + rss.getTitle() + "-标题";
       if (title.equals(systemSourceName)) {
         return RssUtil.getContextByURL(rss.getLink());
       }
     }
     return null;
   }
   
   private Rss parse(List<MessageElement> elements)
     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
   {
     Rss rss = new Rss();
     for (int i = 0; i < elements.size(); i++)
     {
       String methodName = "set" + ((MessageElement)elements.get(i)).getName();
       String value = ((MessageElement)elements.get(i)).getValue();
       MethodUtils.invokeExactMethod(rss, methodName, 
         new Object[] { value }, new Class[] { String.class });
     }
     rss.setId(IDCreator.GenerateInt());
     
     return rss;
   }
   
   public static List<Rss> getAllNewsRss()
   {
     List<Rss> rss = new ArrayList();
     
     String BaseDir = "";
     
     String strnews = BaseDir + "news\\sina_news_opml.xml";
     String strauto = BaseDir + "news\\sina_auto_opml.xml";
     String streladies = BaseDir + "news\\sina_eladies_opml.xml";
     String strent = BaseDir + "news\\sina_ent_opml.xml";
     String finance = BaseDir + "news\\sina_finance_opml.xml";
     String jczs = BaseDir + "news\\sina_jczs_opml.xml";
     String sports = BaseDir + "news\\sina_sports_opml.xml";
     String tech = BaseDir + "news\\sina_tech_opml.xml";
     
     List<NewsCategory> newsCategorys = new ArrayList();
     newsCategorys.add(new NewsCategory("新闻中心", strnews));
     newsCategorys.add(new NewsCategory("汽车新闻", strauto));
     newsCategorys.add(new NewsCategory("女性新闻", streladies));
     newsCategorys.add(new NewsCategory("娱乐新闻", strent));
     newsCategorys.add(new NewsCategory("财经新闻", finance));
     newsCategorys.add(new NewsCategory("军事新闻", jczs));
     newsCategorys.add(new NewsCategory("体育新闻", sports));
     newsCategorys.add(new NewsCategory("科技新闻", tech));
     try
     {
       for (NewsCategory ca : newsCategorys) {
         rss.addAll(getRss(ca.getXmlUrl(), ca.getCat()));
       }
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
     return rss;
   }
   
   public static List<Rss> getRss(String path, String category)
   {
     List<Rss> rsses = new ArrayList();
     try
     {
       Document document = new SAXReader().read(Context.getResourceURL(path));
       List<?> nodes = document.selectNodes("/opml/body/outline/outline");
       for (Object n : nodes)
       {
         Rss news = new Rss();
         
         news.setCategory(category);
         news.setTitle(((Element)n).attribute("title").getValue());
         news.setLink(((Element)n).attribute("xmlUrl").getValue());
         
         rsses.add(news);
       }
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
     return rsses;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.webservice.RssClient
 * JD-Core Version:    0.7.0.1
 */