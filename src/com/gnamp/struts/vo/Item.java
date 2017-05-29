 package com.gnamp.struts.vo;
 
 import java.util.List;
 
 public class Item
 {
   private String name;
   private String desc;
   private String defaultValue;
   private String checkexpression;
   private String samplevalue;
   private List<Item> items;
   private Select select;
   
   public String getSamplevalue()
   {
     return this.samplevalue;
   }
   
   public void setSamplevalue(String samplevalue)
   {
     this.samplevalue = samplevalue;
   }
   
   public String getName()
   {
     return this.name;
   }
   
   public void setName(String name)
   {
     this.name = name;
   }
   
   public String getDesc()
   {
     return this.desc;
   }
   
   public void setDesc(String desc)
   {
     this.desc = desc;
   }
   
   public String getDefaultValue()
   {
     return this.defaultValue;
   }
   
   public void setDefaultValue(String defaultValue)
   {
     this.defaultValue = defaultValue;
   }
   
   public String getCheckexpression()
   {
     return this.checkexpression;
   }
   
   public void setCheckexpression(String checkexpression)
   {
     this.checkexpression = checkexpression;
   }
   
   public List<Item> getItems()
   {
     return this.items;
   }
   
   public void setItems(List<Item> items)
   {
     this.items = items;
   }
   
   public Select getSelect()
   {
     return this.select;
   }
   
   public void setSelect(Select select)
   {
     this.select = select;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.vo.Item
 * JD-Core Version:    0.7.0.1
 */