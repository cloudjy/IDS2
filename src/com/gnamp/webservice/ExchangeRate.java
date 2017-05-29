 package com.gnamp.webservice;
 
 import java.io.Serializable;
 
 public class ExchangeRate
   implements Serializable
 {
   private static final long serialVersionUID = 1495243513082154446L;
   private String symbol = "";
   private String name = "";
   private double mBuyPrice;
   private double fBuyPrice;
   private double SellPrice;
   private double BasePrice;
   
   public String getName()
   {
     return this.name;
   }
   
   public void setName(String name)
   {
     this.name = name;
   }
   
   public double getMBuyPrice()
   {
     return this.mBuyPrice;
   }
   
   public void setMBuyPrice(double mBuyPrice)
   {
     this.mBuyPrice = mBuyPrice;
   }
   
   public double getFBuyPrice()
   {
     return this.fBuyPrice;
   }
   
   public void setFBuyPrice(double fBuyPrice)
   {
     this.fBuyPrice = fBuyPrice;
   }
   
   public double getSellPrice()
   {
     return this.SellPrice;
   }
   
   public void setSellPrice(double sellPrice)
   {
     this.SellPrice = sellPrice;
   }
   
   public double getBasePrice()
   {
     return this.BasePrice;
   }
   
   public void setBasePrice(double basePrice)
   {
     this.BasePrice = basePrice;
   }
   
   public String getSymbol()
   {
     return this.symbol;
   }
   
   public void setSymbol(String symbol)
   {
     this.symbol = symbol;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.webservice.ExchangeRate
 * JD-Core Version:    0.7.0.1
 */