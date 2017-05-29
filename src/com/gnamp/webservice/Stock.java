 package com.gnamp.webservice;
 
 import java.io.Serializable;
 
 public class Stock
   implements Serializable
 {
   private static final long serialVersionUID = -2872809168838777960L;
   private String changeRate;
   private String currentPoint;
   private String currentPrice;
   private String list;
   private String name;
   
   public String getChangeRate()
   {
     return this.changeRate;
   }
   
   public void setChangeRate(String changeRate)
   {
     this.changeRate = changeRate;
   }
   
   public String getCurrentPoint()
   {
     return this.currentPoint;
   }
   
   public void setCurrentPoint(String currentPoint)
   {
     this.currentPoint = currentPoint;
   }
   
   public String getCurrentPrice()
   {
     return this.currentPrice;
   }
   
   public void setCurrentPrice(String currentPrice)
   {
     this.currentPrice = currentPrice;
   }
   
   public String getList()
   {
     return this.list;
   }
   
   public void setList(String list)
   {
     this.list = list;
   }
   
   public String getName()
   {
     return this.name;
   }
   
   public void setName(String name)
   {
     this.name = name;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.webservice.Stock
 * JD-Core Version:    0.7.0.1
 */