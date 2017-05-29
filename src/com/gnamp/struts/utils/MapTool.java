 package com.gnamp.struts.utils;
 
 import java.util.HashMap;
 
 public class MapTool<K, V>
   extends HashMap<K, V>
 {
   public MapTool<K, V> putObject(K key, V value)
   {
     super.put(key, value);
     return this;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.utils.MapTool
 * JD-Core Version:    0.7.0.1
 */