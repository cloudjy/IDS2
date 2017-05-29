 package com.gnamp.webservice;
 
 class NewsCategory
 {
   private String cat;
   private String xmlUrl;
   
   NewsCategory(String cat, String xmlUrl)
   {
     this.cat = cat;
     this.xmlUrl = xmlUrl;
   }
   
   public String getCat()
   {
     return this.cat;
   }
   
   public void setCat(String cat)
   {
     this.cat = cat;
   }
   
   public String getXmlUrl()
   {
     return this.xmlUrl;
   }
   
   public void setXmlUrl(String xmlUrl)
   {
     this.xmlUrl = xmlUrl;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.webservice.NewsCategory
 * JD-Core Version:    0.7.0.1
 */