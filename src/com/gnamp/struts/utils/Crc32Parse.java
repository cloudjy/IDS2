 package com.gnamp.struts.utils;
 
 import java.io.UnsupportedEncodingException;
 
 public class Crc32Parse
 {
   String crcValue;
   
   public Crc32Parse(String crcValue)
   {
     this.crcValue = crcValue;
   }
   
   public String rebuildCrc32()
     throws UnsupportedEncodingException
   {
     byte[] bytz = new byte[this.crcValue.length() / 2];
     for (int i = 0; i < bytz.length; i++) {
       bytz[i] = ((byte)(Integer.valueOf(this.crcValue.substring(i * 2, i * 2 + 2), 16).intValue() & 0xFF));
     }
     return new String(bytz, "utf-8");
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.utils.Crc32Parse
 * JD-Core Version:    0.7.0.1
 */