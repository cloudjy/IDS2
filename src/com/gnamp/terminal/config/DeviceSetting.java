 package com.gnamp.terminal.config;
 
 import com.gnamp.server.utils.DomUtils;
 import java.io.InputStream;
 import java.text.SimpleDateFormat;
 import java.util.Date;
 import org.w3c.dom.Document;
 import org.w3c.dom.Element;
 import org.w3c.dom.Node;
 
 public final class DeviceSetting
 {
   public LocalConfig mLocal = null;
   public NetworkConfig mNetwork = null;
   public ServerConfig mServer = null;
   public ExpertConfig mExpert = null;
   public ApConfig mAp = null;
   public String mPassword = null;
   public Date mVersion = null;
   public static final String NODE_XML_ROOT = "setting";
   public static final String ATTR_VERSION = "version";
   public static final String NODE_PASSWORD = "password";
   
   public String convetToString()
   {
     Document doc = DomUtils.createUTF_8("setting");
     Element elemRoot = doc == null ? null : doc.getDocumentElement();
     if (elemRoot == null) {
       return "";
     }
     if (this.mLocal != null) {
       LocalConfig.fillToDOM(elemRoot, this.mLocal);
     }
     if (this.mNetwork != null) {
       NetworkConfig.fillToDOM(elemRoot, this.mNetwork);
     }
     if (this.mServer != null) {
       ServerConfig.fillToDOM(elemRoot, this.mServer);
     }
     if (this.mExpert != null) {
       ExpertConfig.fillToDOM(elemRoot, this.mExpert);
     }
     if (this.mAp != null) {
       ApConfig.fillToDOM(elemRoot, this.mAp);
     }
     if (this.mPassword != null)
     {
       Node nodePassword = elemRoot.appendChild(doc.createElement("password"));
       nodePassword.appendChild(doc.createCDATASection(this.mPassword));
     }
     elemRoot.setAttribute("version", stringVersion(this.mVersion));
     
     return DomUtils.toXmlString(doc, "UTF-8");
   }
   
   public void parseFromString(String configString)
   {
     this.mLocal = null;
     this.mNetwork = null;
     this.mServer = null;
     this.mExpert = null;
     this.mAp = null;
     this.mPassword = null;
     if ((configString == null) || ((configString = configString.trim()).length() == 0)) {
       return;
     }
     Document doc = DomUtils.parseXmlString(configString, "UTF-8");
     Element elemRoot = doc == null ? null : doc.getDocumentElement();
     if (elemRoot == null) {
       return;
     }
     this.mLocal = LocalConfig.parseFromDOM(elemRoot);
     this.mNetwork = NetworkConfig.parseFromDOM(elemRoot);
     this.mServer = ServerConfig.parseFromDOM(elemRoot);
     this.mExpert = ExpertConfig.parseFromDOM(elemRoot);
     this.mAp = ApConfig.parseFromDOM(elemRoot);
     
     Node nodePassword = DomUtils.selectSingleNode(elemRoot, "password");
     if (nodePassword != null)
     {
       try
       {
         this.mPassword = nodePassword.getTextContent();
       }
       catch (Exception localException) {}
       if (this.mPassword == null) {
         this.mPassword = "";
       }
     }
     this.mVersion = dateVersion(DomUtils.getAttributeString(elemRoot, 
       "version", ""));
   }
   
   public void parseFromStream(InputStream configStream)
   {
     this.mLocal = null;
     this.mNetwork = null;
     this.mServer = null;
     this.mExpert = null;
     this.mAp = null;
     this.mPassword = null;
     if (configStream == null) {
       return;
     }
     Document doc = DomUtils.load(configStream);
     Element elemRoot = doc == null ? null : doc.getDocumentElement();
     if (elemRoot == null) {
       return;
     }
     this.mLocal = LocalConfig.parseFromDOM(elemRoot);
     this.mNetwork = NetworkConfig.parseFromDOM(elemRoot);
     this.mServer = ServerConfig.parseFromDOM(elemRoot);
     this.mExpert = ExpertConfig.parseFromDOM(elemRoot);
     this.mAp = ApConfig.parseFromDOM(elemRoot);
     
     Node nodePassword = DomUtils.selectSingleNode(elemRoot, "password");
     if (nodePassword != null)
     {
       try
       {
         this.mPassword = nodePassword.getTextContent();
       }
       catch (Exception localException) {}
       if (this.mPassword == null) {
         this.mPassword = "";
       }
     }
     this.mVersion = dateVersion(DomUtils.getAttributeString(elemRoot, 
       "version", ""));
   }
   
   private static final SimpleDateFormat versionDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   public static final Date date1970 = dateVersion("1970-01-01 00:00:00");
   
   public static Date defaultVersion()
   {
     return date1970;
   }
   
   public static Date upgradeVersion(Date timeVersion)
   {
     if (timeVersion == null) {
       timeVersion = date1970;
     }
     long now = System.currentTimeMillis();
     now -= now % 1000L;
     long version = timeVersion.getTime();
     version -= version % 1000L;
     timeVersion.setTime(now > version ? now : version + 1000L);
     return timeVersion;
   }
   
   public static synchronized Date dateVersion(String dateString)
   {
     if ((dateString == null) || (dateString.length() <= 0)) {
       return date1970 != null ? date1970 : new Date(0L);
     }
     try
     {
       return versionDateFormat.parse(dateString);
     }
     catch (Exception e) {}
     return date1970;
   }
   
   public static synchronized String stringVersion(Date date)
   {
     return date != null ? versionDateFormat.format(date) : "";
   }
   
   private static String toHexString(String text)
   {
     String hex = "";
     int length = text != null ? text.length() : 0;
     for (int i = 0; i < length; i++)
     {
       byte c = (byte)text.charAt(i);
       byte h = (byte)(0xF & c >> 4);
       byte l = (byte)(0xF & c);
       
       hex = hex + (char)(h > 9 ? 65 + (h - 10) : 48 + h);
       hex = hex + (char)(l > 9 ? 65 + (l - 10) : 48 + l);
     }
     return hex;
   }
   
   private static String parseHexString(String hex)
   {
     int length = hex != null ? hex.length() : 0;
     length = (length - length % 2) / 2;
     if (length < 1) {
       return "";
     }
     byte[] byteArray = new byte[length];
     for (int i = 0; i < length; i++)
     {
       byte h = (byte)hex.charAt(i * 2);
       byte l = (byte)hex.charAt(i * 2 + 1);
       
       l = (byte)
       
         ((97 <= l) && (l <= 102) ? l - 97 + 10 : (65 <= l) && (l <= 70) ? l - 65 + 10 : (48 <= l) && (l <= 57) ? l - 48 : 0);
       h = (byte)
       
         ((97 <= h) && (h <= 102) ? h - 97 + 10 : (65 <= h) && (h <= 70) ? h - 65 + 10 : (48 <= h) && (h <= 57) ? h - 48 : 3);
       byteArray[i] = ((byte)(0xFF & (h << 4 | l)));
     }
     return new String(byteArray);
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.terminal.config.DeviceSetting
 * JD-Core Version:    0.7.0.1
 */