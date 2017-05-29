 package com.gnamp.struts.utils;
 
 import com.gnamp.server.IDCreator;
 import com.gnamp.server.model.MarqueeXML;
 import com.gnamp.server.model.RssXml;
 import java.io.UnsupportedEncodingException;
 import java.util.zip.CRC32;
 
 public class CrcUtil
 {
   public static MarqueeXML build(String text)
   {
     MarqueeXML marquee = new MarqueeXML();
     byte[] byteArray = null;
     try
     {
       byteArray = text.getBytes("UTF-8");
     }
     catch (UnsupportedEncodingException ex)
     {
       byteArray = text.getBytes();
     }
     CRC32 crc32 = new CRC32();
     crc32.update(byteArray);
     long crcValue = crc32.getValue() & 0xFFFFFFFF;
     
     StringBuilder builder = new StringBuilder();
     for (int i = 0; i < byteArray.length; i++) {
       builder.append(String.format("%02X", new Object[] { Byte.valueOf(byteArray[i]) }));
     }
     marquee.setId(IDCreator.GenerateInt());
     marquee.setText(builder.toString());
     marquee.setSum(crcValue);
     return marquee;
   }
   
   public static RssXml buildRss(String text)
   {
     RssXml marquee = new RssXml();
     byte[] byteArray = null;
     if (text == null) {
       text = "";
     }
     try
     {
       byteArray = text.getBytes("UTF-8");
     }
     catch (UnsupportedEncodingException ex)
     {
       byteArray = text.getBytes();
     }
     CRC32 crc32 = new CRC32();
     crc32.update(byteArray);
     long crcValue = crc32.getValue() & 0xFFFFFFFF;
     
     StringBuilder builder = new StringBuilder();
     for (int i = 0; i < byteArray.length; i++) {
       builder.append(String.format("%02X", new Object[] { Byte.valueOf(byteArray[i]) }));
     }
     marquee.setId(IDCreator.GenerateInt());
     marquee.setUrl(text);
     marquee.setSum(crcValue);
     return marquee;
   }
   
   public static RssXml buildRssText(String text, int id)
   {
     RssXml marquee = new RssXml();
     byte[] byteArray = null;
     if (text == null) {
       text = "";
     }
     try
     {
       byteArray = text.getBytes("UTF-8");
     }
     catch (UnsupportedEncodingException ex)
     {
       byteArray = text.getBytes();
     }
     CRC32 crc32 = new CRC32();
     crc32.update(byteArray);
     long crcValue = crc32.getValue() & 0xFFFFFFFF;
     
     StringBuilder builder = new StringBuilder();
     for (int i = 0; i < byteArray.length; i++) {
       builder.append(String.format("%02X", new Object[] { Byte.valueOf(byteArray[i]) }));
     }
     marquee.setId(id);
     marquee.setText(builder.toString());
     marquee.setSum(crcValue);
     return marquee;
   }
   
   public static RssXml buildRssText(RssXml rssXml)
   {
     RssXml marquee = new RssXml();
     byte[] byteArray = null;
     if (rssXml.getText() == null) {
       rssXml.setText("");
     }
     try
     {
       byteArray = rssXml.getText().getBytes("UTF-8");
     }
     catch (UnsupportedEncodingException ex)
     {
       byteArray = rssXml.getText().getBytes();
     }
     CRC32 crc32 = new CRC32();
     crc32.update(byteArray);
     long crcValue = crc32.getValue() & 0xFFFFFFFF;
     
     StringBuilder builder = new StringBuilder();
     for (int i = 0; i < byteArray.length; i++) {
       builder.append(String.format("%02X", new Object[] { Byte.valueOf(byteArray[i]) }));
     }
     marquee.setText(builder.toString());
     marquee.setSum(crcValue);
     return marquee;
   }
   
   public static long buildText(String text)
   {
     byte[] byteArray = null;
     try
     {
       byteArray = text.getBytes("UTF-8");
     }
     catch (UnsupportedEncodingException ex)
     {
       byteArray = text.getBytes();
     }
     CRC32 crc32 = new CRC32();
     crc32.update(byteArray);
     long crcValue = crc32.getValue() & 0xFFFFFFFF;
     
     StringBuilder builder = new StringBuilder();
     for (int i = 0; i < byteArray.length; i++) {
       builder.append(String.format("%02X", new Object[] { Byte.valueOf(byteArray[i]) }));
     }
     return crcValue;
   }
   
   public static String rebuildCrc32(String crcValue)
     throws UnsupportedEncodingException
   {
     return new Crc32Parse(crcValue).rebuildCrc32();
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.utils.CrcUtil
 * JD-Core Version:    0.7.0.1
 */