 package com.gnamp.struts.utils;
 
 import java.io.File;
 import java.io.InputStream;
 import java.util.ArrayList;
 import java.util.List;
 import org.dom4j.Document;
 import org.dom4j.DocumentHelper;
 import org.dom4j.Element;
 import org.dom4j.Node;
 import org.dom4j.io.SAXReader;
 
 public class RssFile
 {
   private String channelName;
   private String channelLink;
   private String channelDes;
   private ArrayList<Item> items = new ArrayList();
   
   public RssFile(File f)
   {
     parseFile(f);
   }
   
   public RssFile(String context)
     throws Exception
   {
     parseContext(context);
   }
   
   public RssFile(InputStream stream)
     throws Exception
   {
     parseContext(stream);
   }
   
   public String getChannelName()
   {
     return this.channelName;
   }
   
   public String getChannelLink()
   {
     return this.channelLink;
   }
   
   public String getDescription()
   {
     return this.channelDes;
   }
   
   public ArrayList<Item> getItems()
   {
     return this.items;
   }
   
   private void parseContext(InputStream inputStream)
     throws Exception
   {
     parseDocument(new SAXReader().read(inputStream));
   }
   
   private void parseContext(String context)
     throws Exception
   {
     parseDocument(DocumentHelper.parseText(context));
   }
   
   private void parseDocument(Document document)
     throws Exception
   {
     List<?> l = document.selectNodes("rss/channel");
     if (l.size() == 0) {
       throw new Exception("not a good rss-xml file");
     }
     Node channel = (Node)l.get(0);
     this.channelName = ((Element)channel.selectNodes("title").get(0))
       .getText();
     this.channelLink = ((Element)channel.selectNodes("link").get(0))
       .getText();
     this.channelDes = ((Element)channel.selectNodes("description").get(0))
       .getText();
     
     List<?> itemList = channel.selectNodes("item");
     for (Object item : itemList) {
       if ((item instanceof Node))
       {
         Item i = new Item((Node)item);
         this.items.add(i);
       }
     }
   }
   
   private void parseFile(File f)
   {
     try
     {
       SAXReader reader = new SAXReader();
       parseDocument(reader.read(f));
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
   
   public class Item
   {
     private String title;
     private String link;
     private String des;
     private String pubTime;
     
     public Item() {}
     
     private String getNodeText(Node itemNode, String nodeName)
     {
       List<?> nodes = itemNode.selectNodes(nodeName);
       if (nodes.size() > 0) {
         return ((Element)nodes.get(0)).getText();
       }
       return "";
     }
     
     public Item(Node itemNode)
     {
       this.title = getNodeText(itemNode, "title");
       this.link = getNodeText(itemNode, "link");
       this.des = getNodeText(itemNode, "description");
     }
     
     public String getTitle()
     {
       return this.title;
     }
     
     public void setTitle(String title)
     {
       this.title = title;
     }
     
     public String getLink()
     {
       return this.link;
     }
     
     public void setLink(String link)
     {
       this.link = link;
     }
     
     public String getPubTime()
     {
       return this.pubTime;
     }
     
     public void setPubTime(String pubTime)
     {
       this.pubTime = pubTime;
     }
     
     public String getDes()
     {
       return this.des;
     }
     
     public void setDes(String des)
     {
       this.des = des;
     }
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.utils.RssFile
 * JD-Core Version:    0.7.0.1
 */