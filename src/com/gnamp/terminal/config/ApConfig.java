 package com.gnamp.terminal.config;
 
 import com.gnamp.server.utils.DomUtils;
 import org.apache.commons.lang.StringUtils;
 import org.w3c.dom.Document;
 import org.w3c.dom.Element;
 import org.w3c.dom.NodeList;
 
 public class ApConfig
   extends IpAddressConfig
 {
   public static final String ICON_NONE = "none";
   public static final String SMALL_SOLID_RED = "solid_red";
   public static final String SMALL_SOLID_ORANGE = "solid_orange";
   public static final String SMALL_SOLID_YELLOW = "solid_yellow";
   public static final String SMALL_SOLID_GREEN = "solid_green";
   public static final String SMALL_SOLID_CYAN = "solid_cyan";
   public static final String SMALL_SOLID_BLUE = "solid_blue";
   public static final String SMALL_SOLID_PURPLE = "solid_purple";
   public static final String SMALL_SOLID_BLACK = "solid_black";
   public static final String SMALL_TRANS_RED = "trans_red";
   public static final String SMALL_TRANS_ORANGE = "trans_orange";
   public static final String SMALL_TRANS_YELLOW = "trans_yellow";
   public static final String SMALL_TRANS_GREEN = "trans_green";
   public static final String SMALL_TRANS_CYAN = "trans_cyan";
   public static final String SMALL_TRANS_BLUE = "trans_blue";
   public static final String SMALL_TRANS_PURPLE = "trans_purple";
   public static final String SMALL_TRANS_BLACK = "trans_black";
   public static final String LARGE_SOLID_RED = "solid_red";
   public static final String LARGE_TRANS_ORANGE = "trans_orange";
   public static final String LARGE_TRANS_BLUE = "trans_blue";
   public static final int CENTER = 1;
   public static final int LEFT_TOP = 2;
   public static final int RIGHT_TOP = 3;
   public static final int LEFT_BOTTOM = 4;
   public static final int RIGHT_BOTTOM = 5;
   
   public static boolean validSmallStyle(String style)
   {
     return false;
   }
   
   public static boolean validPosition(int position)
   {
     return (1 <= position) && (position <= 5);
   }
   
   public String mSSID = "";
   public int mPasswordTime = 3600;
   public String mPassword = "";
   public String mAuthen = "";
   public String mSmallIcon = "";
   public String mLargeIcon = "";
   public int mSmallIconPosition = 4;
   public int mLargeIconPosition = 1;
   private static final String NODE_AP_ROOT = "ap";
   private static final String NODE_SSID = "ssid";
   private static final String NODE_RANDOM_TIME = "random";
   private static final String NODE_PASSWD = "key";
   private static final String NODE_AUTHEN = "auth";
   private static final String NODE_SMALL_ICON = "small";
   private static final String NODE_LARGE_ICON = "large";
   private static final String ATTR_POSITION = "position";
   
   public String getSsid()
   {
     return this.mSSID;
   }
   
   public void setSsid(String ssid)
   {
     this.mSSID = (ssid == null ? "" : ssid.trim());
   }
   
   public int getPasswordTime()
   {
     return this.mPasswordTime < 0 ? 0 : this.mPasswordTime;
   }
   
   public void setPasswordTime(int time)
   {
     this.mPasswordTime = (this.mPasswordTime < 0 ? 0 : time);
   }
   
   public String getPassword()
   {
     return this.mPassword;
   }
   
   public void setPassword(String password)
   {
     this.mPassword = (password == null ? "" : password);
   }
   
   public String getAuthen()
   {
     return this.mAuthen;
   }
   
   public void setAuthen(String authen)
   {
     this.mAuthen = (authen == null ? "" : authen.trim());
   }
   
   public String getSmallIcon()
   {
     return this.mSmallIcon == null ? "" : this.mSmallIcon.trim();
   }
   
   public void setSmallIcon(String icon)
   {
     this.mSmallIcon = (icon == null ? "" : icon.trim());
   }
   
   public int getSmallIconPosition()
   {
     return validPosition(this.mSmallIconPosition) ? this.mSmallIconPosition : 1;
   }
   
   public void setSmallIconPosition(int position)
   {
     if (!validPosition(position)) {
       throw new IllegalArgumentException("invalid position");
     }
     this.mSmallIconPosition = position;
   }
   
   public String getLargeIcon()
   {
     return this.mLargeIcon == null ? "" : this.mLargeIcon.trim();
   }
   
   public void setLargeIcon(String icon)
   {
     this.mLargeIcon = (icon == null ? "" : icon.trim());
   }
   
   public int getLargeIconPosition()
   {
     return validPosition(this.mLargeIconPosition) ? this.mLargeIconPosition : 1;
   }
   
   public void setLargeIconPosition(int position)
   {
     if (!validPosition(position)) {
       throw new IllegalArgumentException("invalid position");
     }
     this.mLargeIconPosition = position;
   }
   
   public boolean validAp()
   {
     return (this.mSSID != null) && (this.mSSID.length() > 0);
   }
   
   public String toString()
   {
     return 
     
 
       "[ap] ssid=" + this.mSSID + ", time=" + this.mPasswordTime + ", password=" + this.mPassword + ", authen=" + this.mAuthen + " | " + super.toString();
   }
   
   public boolean equals(Object obj)
   {
     if (this == obj) {
       return true;
     }
     if ((obj == null) || (!(obj instanceof ApConfig))) {
       return false;
     }
     ApConfig o = (ApConfig)obj;
     if (!(this.mSSID == null ? "" : this.mSSID.trim()).equals(o.mSSID == null ? "" : o.mSSID.trim())) {
       return false;
     }
     if (this.mPasswordTime != o.mPasswordTime) {
       return false;
     }
     if (!(this.mPassword == null ? "" : this.mPassword.trim()).equals(o.mPassword == null ? "" : o.mPassword.trim())) {
       return false;
     }
     if (!(this.mSmallIcon == null ? "" : this.mSmallIcon.trim()).equals(o.mSmallIcon == null ? "" : o.mSmallIcon.trim())) {
       return false;
     }
     if (this.mSmallIconPosition != o.mSmallIconPosition) {
       return false;
     }
     if (!(this.mLargeIcon == null ? "" : this.mLargeIcon.trim()).equals(o.mLargeIcon == null ? "" : o.mLargeIcon.trim())) {
       return false;
     }
     return super.equals(obj);
   }
   
   private static String position2String(int position)
   {
     return position2String(position, "left_bottom");
   }
   
   private static String position2String(int position, String defaultString)
   {
     if (position == 1) {
       return "center";
     }
     if (position == 2) {
       return "left_top";
     }
     if (position == 3) {
       return "right_top";
     }
     if (position == 4) {
       return "left_bottom";
     }
     if (position == 5) {
       return "right_bottom";
     }
     return defaultString;
   }
   
   private static int string2position(String text)
   {
     return string2position(text, 4);
   }
   
   private static int string2position(String text, int defaultInt)
   {
     text = text == null ? "" : text.trim();
     if (text.equalsIgnoreCase("center")) {
       return 1;
     }
     if ((text.equalsIgnoreCase("left_top")) || (text.equalsIgnoreCase("top_left"))) {
       return 2;
     }
     if ((text.equalsIgnoreCase("right_top")) || (text.equalsIgnoreCase("top_right"))) {
       return 3;
     }
     if ((text.equalsIgnoreCase("left_bottom")) || (text.equalsIgnoreCase("bottom_left"))) {
       return 4;
     }
     if ((text.equalsIgnoreCase("right_bottom")) || (text.equalsIgnoreCase("bottom_right"))) {
       return 5;
     }
     return defaultInt;
   }
   
   public static void fillToDOM(Element elemRoot, ApConfig config)
   {
     if ((elemRoot != null) && (config != null))
     {
       NodeList nodeList = elemRoot.getElementsByTagName("ap");
       int nodeNum = nodeList != null ? nodeList.getLength() : 0;
       for (int i = 0; i < nodeNum; i++) {
         elemRoot.removeChild(nodeList.item(i));
       }
       Element elem = (Element)elemRoot.appendChild(
         elemRoot.getOwnerDocument().createElement("ap"));
       
       DomUtils.putStringContent(elem, "ssid", StringUtils.trimToEmpty(config.mSSID));
       if ((config.mPassword != null) && (config.mPassword.length() >= 8)) {
         DomUtils.putStringContent(elem, "key", config.mPassword);
       } else {
         DomUtils.putIntContent(elem, "random", 
           config.mPasswordTime <= 0 ? 86400 : config.mPasswordTime);
       }
       DomUtils.putStringContent(elem, "auth", StringUtils.trimToEmpty(config.mAuthen));
       Element elemSmall;
       if (!StringUtils.isBlank(config.mSmallIcon))
       {
         DomUtils.putStringContent(elem, "small", config.mSmallIcon.trim().toLowerCase());
         elemSmall = (Element)DomUtils.selectSingleNode(elem, "small");
         if (elemSmall != null) {
           elemSmall.setAttribute("position", position2String(config.mSmallIconPosition));
         }
       }
       if (!StringUtils.isBlank(config.mLargeIcon))
       {
         DomUtils.putStringContent(elem, "large", config.mLargeIcon.trim().toLowerCase());
         elemSmall = (Element)DomUtils.selectSingleNode(elem, "large");
       }
     }
   }
   
   public static ApConfig parseFromDOM(Element elemRoot)
   {
     if (elemRoot == null) {
       return null;
     }
     Element elemExpertRoot = (Element)DomUtils.selectSingleNode(elemRoot, "ap");
     if (elemExpertRoot == null) {
       return null;
     }
     ApConfig apConfig = new ApConfig();
     apConfig._parseFromDOM(elemRoot);
     return apConfig;
   }
   
   private void _parseFromDOM(Element elemRoot)
   {
     Element elem = elemRoot == null ? null : 
       (Element)DomUtils.selectSingleNode(elemRoot, "ap");
     if (elem == null) {
       return;
     }
     String ssid = DomUtils.getStringContent(elem, "ssid", "");
     String password = DomUtils.getStringContent(elem, "key", "");
     int random = DomUtils.getIntContent(elem, "random", 0);
     String authen = DomUtils.getStringContent(elem, "auth", "");
     String small = DomUtils.getStringContent(elem, "small", "");
     String large = DomUtils.getStringContent(elem, "large", "");
     
     String smallPosition = DomUtils.getAttributeString(
       (Element)DomUtils.selectSingleNode(elem, "small"), "position", "");
     String largePosition = DomUtils.getAttributeString(
       (Element)DomUtils.selectSingleNode(elem, "large"), "position", "");
     
     ssid = ssid == null ? "" : ssid.trim();
     password = password == null ? "" : password.trim();
     authen = authen == null ? "" : authen.trim();
     small = small == null ? "" : small.trim().toLowerCase();
     large = large == null ? "" : large.trim().toLowerCase();
     smallPosition = smallPosition == null ? "" : smallPosition.trim().toLowerCase();
     largePosition = largePosition == null ? "" : largePosition.trim().toLowerCase();
     
     this.mSSID = ssid;
     this.mPassword = password;
     this.mPasswordTime = (random <= 0 ? 86400 : password.length() >= 8 ? 0 : random);
     this.mAuthen = authen;
     
     this.mSmallIcon = small;
     this.mSmallIconPosition = string2position(smallPosition);
     this.mLargeIcon = large;
     this.mLargeIconPosition = 1;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.terminal.config.ApConfig
 * JD-Core Version:    0.7.0.1
 */