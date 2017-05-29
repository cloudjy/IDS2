 package com.gnamp.terminal.config;
 
 import com.gnamp.server.utils.DomUtils;
 import org.apache.commons.lang.StringUtils;
 import org.w3c.dom.Document;
 import org.w3c.dom.Element;
 import org.w3c.dom.NodeList;
 
 public abstract class NetworkConfig
 {
   private static final String NODE_NETWORK_ROOT = "net_config";
   private static final String ATT_IP_MODE = "mode";
   private static final String IP_STATIC = "static";
   private static final String IP_DHCP = "dhcp";
   private static final String NODE_IP = "ip";
   private static final String NODE_MASK = "mask";
   private static final String NODE_GATE = "gate";
   private static final String NODE_DNS = "dns";
   private static final String NODE_ETHERNET = "ethernet";
   private static final String NODE_WIFI = "wifi";
   private static final String NODE_SSID = "ssid";
   private static final String NODE_PASSWD = "key";
   private static final String NODE_AUTHEN = "auth";
   private static final String NODE_ENCRYPT = "encrypt";
   private static final String NODE_PHONE3G = "phone3g";
   private static final String ATTR_PHONE3GTYPE = "type";
   private static final String NODE_APN = "apn";
   private static final String NODE_CODE = "code";
   private static final String PHONE3G_WCDMA = "wcdma";
   private static final String PHONE3G_EVDO = "evdo";
   private static final String NODE_PPPOE = "pppoe";
   private static final String NODE_PPPOE_USERNAME = "name";
   private static final String NODE_PPPOE_PASSWORD = "password";
   private static final String ENCRYPT_FORBID_STR = "NONE";
   private static final String ENCRYPT_TKIP_STR = "TKIP";
   private static final String ENCRYPT_AES_STR = "AES";
   private static final String ENCRYPT_WEP_STR = "WEP";
   private static final String AUTHEN_OPEN_STR = "OPEN";
   private static final String AUTHEN_SHARED_STR = "SHARED";
   private static final String AUTHEN_WPA_STR = "WPA";
   private static final String AUTHEN_WPA2_STR = "WPA2";
   private static final String AUTHEN_WPAPSK_STR = "WPAPSK";
   private static final String AUTHEN_WPA2PSK_STR = "WPA2PSK";
   
   public static void fillToDOM(Element elemRoot, NetworkConfig config)
   {
     if ((elemRoot != null) && (config != null))
     {
       NodeList nodeList = elemRoot.getElementsByTagName("net_config");
       int nodeNum = nodeList != null ? nodeList.getLength() : 0;
       for (int i = 0; i < nodeNum; i++) {
         elemRoot.removeChild(nodeList.item(i));
       }
       Element elemNetworkRoot = (Element)elemRoot.appendChild(
         elemRoot.getOwnerDocument().createElement("net_config"));
       if ((config instanceof EthernetConfig)) {
         writeEthernet(elemNetworkRoot, (EthernetConfig)config);
       } else if ((config instanceof WifiConfig)) {
         writeWifi(elemNetworkRoot, (WifiConfig)config);
       } else if ((config instanceof Phone3GConfig)) {
         writePhone3G(elemNetworkRoot, (Phone3GConfig)config);
       } else if ((config instanceof PPPOEConfig)) {
         writePPPOE(elemNetworkRoot, (PPPOEConfig)config);
       }
     }
   }
   
   public static NetworkConfig parseFromDOM(Element elemRoot)
   {
     if (elemRoot == null) {
       return null;
     }
     Element elemNetworkRoot = (Element)DomUtils.selectSingleNode(elemRoot, "net_config");
     if (elemNetworkRoot == null) {
       return null;
     }
     Element elemEth = (Element)DomUtils.selectSingleNode(elemNetworkRoot, "ethernet");
     if (elemEth != null) {
       return readEthernet(elemEth);
     }
     Element elemWifi = (Element)DomUtils.selectSingleNode(elemNetworkRoot, "wifi");
     if (elemWifi != null) {
       return readWifi(elemWifi);
     }
     Element elemPhone3G = (Element)DomUtils.selectSingleNode(elemNetworkRoot, "phone3g");
     if (elemPhone3G != null) {
       return readPhone3G(elemPhone3G);
     }
     Element elemPPPOE = (Element)DomUtils.selectSingleNode(elemNetworkRoot, "pppoe");
     if (elemPPPOE != null) {
       return readPPPOE(elemPPPOE);
     }
     return null;
   }
   
   private static void writeIpAddress(Element elem, IpAddressConfig config)
   {
     if ((elem == null) || (config == null)) {
       return;
     }
     if (config.mDhcp)
     {
       elem.setAttribute("mode", "dhcp");
     }
     else
     {
       elem.setAttribute("mode", "static");
       
       DomUtils.putStringContent(elem, "ip", StringUtils.trimToEmpty(config.mIp));
       DomUtils.putStringContent(elem, "mask", StringUtils.trimToEmpty(config.mMask));
       DomUtils.putStringContent(elem, "gate", StringUtils.trimToEmpty(config.mGate));
       DomUtils.putStringContent(elem, "dns", StringUtils.trimToEmpty(config.mDns));
     }
   }
   
   private static void writeEthernet(Element elemRoot, EthernetConfig conifg)
   {
     Element elem = (Element)elemRoot.appendChild(
       elemRoot.getOwnerDocument().createElement("ethernet"));
     writeIpAddress(elem, conifg);
   }
   
   static EthernetConfig readEthernet(Element elem)
   {
     EthernetConfig ethernet = new EthernetConfig();
     readAddress(elem, ethernet);
     return ethernet;
   }
   
   private static void writeWifi(Element elemRoot, WifiConfig conifg)
   {
     Element elem = (Element)elemRoot.appendChild(
       elemRoot.getOwnerDocument().createElement("wifi"));
     
     DomUtils.putStringContent(elem, "ssid", StringUtils.trimToEmpty(conifg.mSSID));
     DomUtils.putStringContent(elem, "key", conifg.mPassword == null ? "" : conifg.mPassword);
     
     String authen = StringUtils.trimToEmpty(conifg.mAuthen);
     if ((authen.equalsIgnoreCase("OPEN")) || 
       (authen.equalsIgnoreCase("SHARED")) || 
       (authen.equalsIgnoreCase("WPA")) || 
       (authen.equalsIgnoreCase("WPA2")) || 
       (authen.equalsIgnoreCase("WPAPSK")) || 
       (authen.equalsIgnoreCase("WPA2PSK"))) {
       DomUtils.putStringContent(elem, "auth", authen);
     }
     String encrypt = StringUtils.trimToEmpty(conifg.mEncrypt);
     if ((encrypt.equalsIgnoreCase("NONE")) || 
       (encrypt.equalsIgnoreCase("WEP")) || 
       (encrypt.equalsIgnoreCase("TKIP")) || 
       (encrypt.equalsIgnoreCase("AES"))) {
       DomUtils.putStringContent(elem, "encrypt", encrypt);
     }
     writeIpAddress(elem, conifg);
   }
   
   static WifiConfig readWifi(Element elem)
   {
     String ssid = DomUtils.getStringContent(elem, "ssid", "");
     String password = DomUtils.getStringContent(elem, "key", "");
     String authen = DomUtils.getStringContent(elem, "auth", "");
     String encrypt = DomUtils.getStringContent(elem, "encrypt", "");
     
     ssid = ssid == null ? "" : ssid.trim();
     password = password == null ? "" : password.trim();
     authen = authen == null ? "" : authen.trim();
     encrypt = encrypt == null ? "" : encrypt.trim();
     if (ssid != null)
     {
       WifiConfig wifi = new WifiConfig();
       wifi.mSSID = ssid;
       wifi.mPassword = password;
       
       wifi.mAuthen = authen;
       wifi.mEncrypt = encrypt;
       
       readAddress(elem, wifi);
       
       return wifi;
     }
     return null;
   }
   
   private static void writePhone3G(Element elemRoot, Phone3GConfig config)
   {
     Element elem = (Element)elemRoot.appendChild(
       elemRoot.getOwnerDocument().createElement("phone3g"));
     
 
 
 
 
 
 
 
 
     DomUtils.putStringContent(elem, "apn", StringUtils.trimToEmpty(config.mApn));
     DomUtils.putStringContent(elem, "code", StringUtils.trimToEmpty(config.mNumber));
   }
   
   private static void writePPPOE(Element elemRoot, PPPOEConfig config)
   {
     Element elem = (Element)elemRoot.appendChild(
       elemRoot.getOwnerDocument().createElement("pppoe"));
     
     DomUtils.putStringContent(elem, "name", StringUtils.trimToEmpty(config.mUserName));
     DomUtils.putStringContent(elem, "password", config.mPassword == null ? "" : config.mPassword);
   }
   
   static Phone3GConfig readPhone3G(Element elem)
   {
     String type = DomUtils.getAttributeString(elem, "type");
     String apn = DomUtils.getStringContent(elem, "apn", "");
     String code = DomUtils.getStringContent(elem, "code", "");
     
     type = type == null ? "" : type.trim();
     apn = apn == null ? "" : apn.trim();
     code = code == null ? "" : code.trim();
     
     Phone3GConfig config = new Phone3GConfig();
     
 
 
 
 
 
     return config;
   }
   
   static PPPOEConfig readPPPOE(Element elem)
   {
     String name = DomUtils.getStringContent(elem, "name", "");
     String password = DomUtils.getStringContent(elem, "password", "");
     
     name = name == null ? "" : name.trim();
     password = password == null ? "" : password.trim();
     
     PPPOEConfig config = new PPPOEConfig();
     config.setUserName(name);
     config.setPassword(password);
     
     return config;
   }
   
   static void readAddress(Element elem, IpAddressConfig config)
   {
     if ((elem == null) || (config == null)) {
       return;
     }
     String mode = DomUtils.getAttributeString(elem, "mode", "DHCP");
     if (mode.equalsIgnoreCase("STATIC"))
     {
       config.mDhcp = false;
       config.mIp = DomUtils.getStringContent(elem, "ip", "");
       config.mMask = DomUtils.getStringContent(elem, "mask", "");
       config.mGate = DomUtils.getStringContent(elem, "gate", "");
       config.mDns = DomUtils.getStringContent(elem, "dns", "");
     }
     else
     {
       config.mDhcp = true;
     }
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.terminal.config.NetworkConfig
 * JD-Core Version:    0.7.0.1
 */