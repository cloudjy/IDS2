 package com.gnamp.terminal.config;
 
 import com.gnamp.server.utils.DomUtils;
 import org.apache.commons.lang.StringUtils;
 import org.w3c.dom.Document;
 import org.w3c.dom.Element;
 import org.w3c.dom.NodeList;
 
 public final class ServerConfig
 {
   public String mServerAddress = "";
   public int mDownloadPort = -1;
   public int mControlPort = -1;
   public String mServerBaseDirectory = "";
   public static final String NODE_SERVER_ROOT = "server_config";
   public static final String NODE_SERVER = "server";
   public static final String NODE_DOMAIN = "domain";
   public static final String NODE_DOWNLOAD = "port";
   public static final String NODE_CONTROL = "control";
   public static final String NODE_SERVER_BASE_DIRECTORY = "activedir";
   
   public String getServerAddress()
   {
     return this.mServerAddress;
   }
   
   public void setServerAddress(String serverAddress)
   {
     this.mServerAddress = serverAddress;
   }
   
   public int getDownloadPort()
   {
     return this.mDownloadPort;
   }
   
   public void setDownloadPort(int downloadPort)
   {
     this.mDownloadPort = downloadPort;
   }
   
   public int getControlPort()
   {
     return this.mControlPort;
   }
   
   public void setControlPort(int controlPort)
   {
     this.mControlPort = controlPort;
   }
   
   public String getServerBaseDirectory()
   {
     return this.mServerBaseDirectory;
   }
   
   public void setServerBaseDirectory(String serverBaseDirectory)
   {
     this.mServerBaseDirectory = serverBaseDirectory;
   }
   
   public String toString()
   {
     return 
     
 
       "addr=" + this.mServerAddress + ", downlad=" + this.mDownloadPort + ", control=" + this.mControlPort + ", base" + this.mServerBaseDirectory + ".";
   }
   
   public boolean equals(Object obj)
   {
     if (this == obj) {
       return true;
     }
     if ((obj == null) || (!(obj instanceof ServerConfig))) {
       return false;
     }
     ServerConfig o = (ServerConfig)obj;
     if (!(this.mServerAddress == null ? "" : this.mServerAddress.trim()).equals(o.mServerAddress == null ? "" : o.mServerAddress.trim())) {
       return false;
     }
     if ((this.mDownloadPort != o.mDownloadPort) || 
       (this.mControlPort != o.mControlPort)) {
       return false;
     }
     return true;
   }
   
   public static void fillToDOM(Element elemRoot, ServerConfig config)
   {
     if ((elemRoot != null) && (config != null)) {
       config._fillToDOM(elemRoot);
     }
   }
   
   private void _fillToDOM(Element elemRoot)
   {
     NodeList nodeList = elemRoot.getElementsByTagName("server_config");
     int nodeNum = nodeList != null ? nodeList.getLength() : 0;
     for (int i = 0; i < nodeNum; i++) {
       elemRoot.removeChild(nodeList.item(i));
     }
     Element elemServerRoot = (Element)elemRoot.appendChild(
       elemRoot.getOwnerDocument().createElement("server_config"));
     Element elemServer = (Element)elemServerRoot.appendChild(
       elemServerRoot.getOwnerDocument().createElement("server"));
     
     DomUtils.putStringContent(elemServer, "domain", StringUtils.trimToEmpty(this.mServerAddress));
     DomUtils.putIntContent(elemServer, "port", 
       this.mDownloadPort > 0 ? this.mDownloadPort : 80);
     DomUtils.putIntContent(elemServer, "control", 
       this.mControlPort > 0 ? this.mControlPort : 9090);
     DomUtils.putStringContent(elemServer, "activedir", 
       StringUtils.trimToEmpty(this.mServerBaseDirectory));
   }
   
   public static ServerConfig parseFromDOM(Element elemRoot)
   {
     if (elemRoot == null) {
       return null;
     }
     Element elemServerRoot = (Element)DomUtils.selectSingleNode(elemRoot, "server_config");
     if (elemServerRoot == null) {
       return null;
     }
     Element elemServer = (Element)DomUtils.selectSingleNode(elemServerRoot, "server");
     if (elemServer == null) {
       return null;
     }
     ServerConfig serverConfig = new ServerConfig();
     serverConfig._parseFromDOM(elemRoot);
     return serverConfig;
   }
   
   private void _parseFromDOM(Element elemRoot)
   {
     Element elemServerRoot = (Element)DomUtils.selectSingleNode(elemRoot, "server_config");
     if (elemServerRoot == null) {
       return;
     }
     Element elemServer = (Element)DomUtils.selectSingleNode(elemServerRoot, "server");
     if (elemServer == null) {
       return;
     }
     this.mServerAddress = DomUtils.getStringContent(elemServer, "domain", "192.168.1.2");
     this.mDownloadPort = DomUtils.getIntContent(elemServer, "port", 80);
     this.mControlPort = DomUtils.getIntContent(elemServer, "control", 9090);
     this.mServerBaseDirectory = DomUtils.getStringContent(elemServer, "activedir", "");
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.terminal.config.ServerConfig
 * JD-Core Version:    0.7.0.1
 */