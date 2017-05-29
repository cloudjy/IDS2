 package com.gnamp.webservice;
 
 public class Rss
 {
   private String version;
   private String link;
   private String title;
   private String category;
   private int id;
   
   public int getId()
   {
     return this.id;
   }
   
   public void setId(int id)
   {
     this.id = id;
   }
   
   public String getVersion()
   {
     return this.version;
   }
   
   public void setVersion(String version)
   {
     this.version = version;
   }
   
   public String getLink()
   {
     return this.link;
   }
   
   public void setLink(String link)
   {
     this.link = link;
   }
   
   public String getTitle()
   {
     return this.title;
   }
   
   public void setTitle(String title)
   {
     this.title = title;
   }
   
   public String getCategory()
   {
     return this.category;
   }
   
   public void setCategory(String category)
   {
     this.category = category;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.webservice.Rss
 * JD-Core Version:    0.7.0.1
 */