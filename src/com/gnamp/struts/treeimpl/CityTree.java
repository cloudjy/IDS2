 package com.gnamp.struts.treeimpl;
 
 import com.gnamp.server.model.City;
 import com.gnamp.struts.tree.ITree;
 import com.gnamp.struts.tree.ITreeConvert;
 
 public class CityTree
   implements ITreeConvert<City>
 {
   City city;
   
   public int getId()
   {
     return this.city.getCityId();
   }
   
   public String getName()
   {
     return this.city.getCityName();
   }
   
   public boolean getIsParent()
   {
     return false;
   }
   
   public ITree<City> convert(City t)
   {
     this.city = t;
     return this;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.treeimpl.CityTree
 * JD-Core Version:    0.7.0.1
 */