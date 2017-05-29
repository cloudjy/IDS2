 package com.gnamp.struts.action;
 
 import com.gnamp.server.model.AppRect;
 import com.gnamp.server.model.AvInRect;
 import com.gnamp.server.model.CameraRect;
 import com.gnamp.server.model.DateRect;
 import com.gnamp.server.model.DeviceRect;
 import com.gnamp.server.model.FlashRect;
 import com.gnamp.server.model.FontRect;
 import com.gnamp.server.model.HybridRect;
 import com.gnamp.server.model.ImageRect;
 import com.gnamp.server.model.InteractiveRect;
 import com.gnamp.server.model.InteractiveRect.Util;
 import com.gnamp.server.model.LogoRect;
 import com.gnamp.server.model.MLFlipTRect;
 import com.gnamp.server.model.MLScrollTRect;
 import com.gnamp.server.model.MLStaticTRect;
 import com.gnamp.server.model.Rect;
 import com.gnamp.server.model.SLFlipTRect;
 import com.gnamp.server.model.SLScrollTRect;
 import com.gnamp.server.model.SLStaticTRect;
 import com.gnamp.server.model.TimeRect;
 import com.gnamp.server.model.VideoRect;
 import com.gnamp.server.model.WeatherLogoRect;
 import com.gnamp.server.model.WeatherTemperatureRect;
 import com.gnamp.server.model.WeatherTextRect;
 import com.gnamp.server.model.WeatherTitleRect;
 import com.gnamp.server.model.WebRect;
 import com.gnamp.server.model.WeekRect;
 import java.awt.Color;
 import java.io.ByteArrayInputStream;
 import java.io.IOException;
 import java.io.UnsupportedEncodingException;
 import java.util.HashMap;
 import java.util.Map;
 import javax.xml.parsers.DocumentBuilder;
 import javax.xml.parsers.DocumentBuilderFactory;
 import javax.xml.parsers.ParserConfigurationException;
 import org.w3c.dom.Document;
 import org.w3c.dom.Element;
 import org.w3c.dom.Node;
 import org.w3c.dom.NodeList;
 import org.xml.sax.SAXException;
 
 class XmlToLayout
 {
   private static boolean parseRectAtrribute(Node node, Rect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     if ((!elem.hasAttribute("rectid")) || (!elem.hasAttribute("rectname")) || 
       (!elem.hasAttribute("nX")) || (!elem.hasAttribute("nY")) || 
       (!elem.hasAttribute("nW")) || (!elem.hasAttribute("nH")) || 
       (!elem.hasAttribute("layer"))) {
       return false;
     }
     String szRectId = elem.getAttribute("rectid").trim();
     String szRectName = elem.getAttribute("rectname").trim();
     String szX = elem.getAttribute("nX").trim();
     String szY = elem.getAttribute("nY").trim();
     String szW = elem.getAttribute("nW").trim();
     String szH = elem.getAttribute("nH").trim();
     String szLayer = elem.getAttribute("layer").trim();
     if ((szRectId.equals("")) || 
     
       (szX.equals("")) || (szY.equals("")) || (szW.equals("")) || 
       (szH.equals("")) || (szLayer.equals(""))) {
       return false;
     }
     int nRectId = szX.matches("^\\d+$") ? Integer.parseInt(szRectId) : -1;
     int nX = szX.matches("^\\d{1,4}$") ? Integer.parseInt(szX) : -1;
     int nY = szY.matches("^\\d{1,4}$") ? Integer.parseInt(szY) : -1;
     int nW = szW.matches("^\\d{1,4}$") ? Integer.parseInt(szW) : -1;
     int nH = szH.matches("^\\d{1,4}$") ? Integer.parseInt(szH) : -1;
     int nLayer = szLayer.matches("^\\d+$") ? Integer.parseInt(szLayer) : 
       -1;
     if ((nX < 0) || (nY < 0) || (nW <= 0) || (nH <= 0) || (nLayer <= 0)) {
       return false;
     }
     rect.setRectId(nRectId);
     rect.setRectName(szRectName);
     rect.setPosX(nX);
     rect.setPosY(nY);
     rect.setWidth(nW);
     rect.setHeight(nH);
     rect.setLayer(nLayer);
     if (((rect instanceof InteractiveRect)) && 
       (!parseInteractiveRect(elem, (InteractiveRect)rect))) {
       return false;
     }
     if (((rect instanceof AppRect)) && (!parseAppRect(elem, (AppRect)rect))) {
       return false;
     }
     if ((rect instanceof FontRect)) {
       return parseTextRectWnd(elem, (FontRect)rect);
     }
     if ((rect instanceof WeatherLogoRect)) {
       return parseWeatherLogoRect(elem, (WeatherLogoRect)rect);
     }
     if ((rect instanceof DeviceRect)) {
       return parseDeviceRect(elem, (DeviceRect)rect);
     }
     if ((rect instanceof WebRect)) {
       return parseWebRect(elem, (WebRect)rect);
     }
     if ((rect instanceof FlashRect)) {
       return parseFlashRect(elem, (FlashRect)rect);
     }
     if ((rect instanceof AvInRect)) {
       return parseAvInRect(elem, (AvInRect)rect);
     }
     if ((rect instanceof CameraRect)) {
       return parseCameraRect(elem, (CameraRect)rect);
     }
     return true;
   }
   
   private static boolean parseInteractiveRect(Node node, InteractiveRect rect)
   {
     if ((node == null) || (!(node instanceof Element)) || (rect == null)) {
       return false;
     }
     Element elem = (Element)node;
     if (!elem.hasAttribute("interactive")) {
       return false;
     }
     String interactive = elem.getAttribute("interactive").trim();
     if ((interactive == null) || ((interactive = interactive.trim()).length() == 0)) {
       return false;
     }
     if (interactive.equalsIgnoreCase("off"))
     {
       rect.setInteractiveType(0);
       return true;
     }
     if (interactive.equalsIgnoreCase("enter"))
     {
       rect.setInteractiveType(1);
       return true;
     }
     if (interactive.equalsIgnoreCase("browse"))
     {
       rect.setInteractiveType(3);
       return true;
     }
     if (interactive.equalsIgnoreCase("guide_browse"))
     {
       rect.setInteractiveType(4);
       return true;
     }
     if (interactive.equalsIgnoreCase("exit"))
     {
       rect.setInteractiveType(2);
       return true;
     }
     if (interactive.equalsIgnoreCase("last"))
     {
       rect.setInteractiveType(6);
       return true;
     }
     if (interactive.equalsIgnoreCase("jump"))
     {
       if (!elem.hasAttribute("jump")) {
         return false;
       }
       String jump = elem.getAttribute("jump").trim();
       if ((jump == null) || ((jump = jump.trim()).length() == 0)) {
         return false;
       }
       int program = 0;
       try
       {
         program = Integer.parseInt(jump);
       }
       catch (Exception ex)
       {
         return false;
       }
       if (!InteractiveRect.Util.vailidJumpProgram(program)) {
         return false;
       }
       rect.setInteractiveType(5);
       rect.setJumpProgram(program);
       
       return true;
     }
     if (interactive.equalsIgnoreCase("open_app"))
     {
       rect.setInteractiveType(7);
       return true;
     }
     if (interactive.equalsIgnoreCase("map_demand"))
     {
       rect.setInteractiveType(9);
       return true;
     }
     if (interactive.equalsIgnoreCase("full_view"))
     {
       rect.setInteractiveType(8);
       return true;
     }
     return false;
   }
   
   private static boolean parseAppRect(Node node, AppRect rect)
   {
     if ((node == null) || (!(node instanceof Element)) || (rect == null)) {
       return false;
     }
     Element elem = (Element)node;
     if (!elem.hasAttribute("package")) {
       return true;
     }
     String packageName = elem.getAttribute("package").trim();
     String className = elem.hasAttribute("class") ? elem.getAttribute("class").trim() : "";
     rect.setAppPackageName(packageName);
     rect.setAppClassName(className);
     
     String timeout = elem.hasAttribute("app_timeout") ? elem.getAttribute("app_timeout").trim() : "0";
     long timeoutValue = 0L;
     try
     {
       timeoutValue = Long.parseLong(timeout);
     }
     catch (Exception ex)
     {
       timeoutValue = 0L;
     }
     rect.setAppIdleTimeout(timeoutValue);
     
     String systemBar = elem.hasAttribute("show_systembar") ? elem.getAttribute("show_systembar").trim() : "";
     rect.setShowSystemBar((systemBar.equalsIgnoreCase("true")) || (systemBar.equalsIgnoreCase("show")));
     if (!rect.getShowSystemBar()) {
       return true;
     }
     String btnString = elem.hasAttribute("systembar_button") ? elem.getAttribute("systembar_button").trim() : "back";
     String size = elem.hasAttribute("systembar_iconsize") ? elem.getAttribute("systembar_iconsize").trim() : "";
     String postion = elem.hasAttribute("systembar_position") ? elem.getAttribute("systembar_position").trim() : "";
     
     int button = 0;
     String[] array = btnString.split("\\|");
     if (array != null) {
       for (String s : array)
       {
         s = s == null ? "" : s.trim();
         if (s.equalsIgnoreCase("back")) {
           button |= 0x1;
         } else if ((s.equalsIgnoreCase("close")) || (s.equalsIgnoreCase("exit")) || 
           (s.equalsIgnoreCase("quit"))) {
           button |= 0x2;
         }
       }
     }
     rect.setSystemBarButton(button);
     
     int icon_size = 2;
     if ((size.equalsIgnoreCase("icon_small")) || (size.equalsIgnoreCase("small"))) {
       icon_size = 1;
     } else if ((size.equalsIgnoreCase("icon_middle")) || (size.equalsIgnoreCase("middle"))) {
       icon_size = 2;
     } else if ((size.equalsIgnoreCase("icon_large")) || (size.equalsIgnoreCase("large"))) {
       icon_size = 3;
     } else {
       icon_size = 2;
     }
     rect.setSystemBarSize(icon_size);
     
     int pos = 0;
     if ((postion.equalsIgnoreCase("bottom_middle")) || (postion.equalsIgnoreCase("bottom"))) {
       pos = 0;
     } else if ((postion.equalsIgnoreCase("right_bottom")) || (postion.equalsIgnoreCase("bottom_right"))) {
       pos = 1;
     } else if ((postion.equalsIgnoreCase("right_middle")) || (postion.equalsIgnoreCase("right"))) {
       pos = 2;
     } else if ((postion.equalsIgnoreCase("right_top")) || (postion.equalsIgnoreCase("top_right"))) {
       pos = 3;
     } else if ((postion.equalsIgnoreCase("top_middle")) || (postion.equalsIgnoreCase("top"))) {
       pos = 4;
     } else if ((postion.equalsIgnoreCase("left_top")) || (postion.equalsIgnoreCase("top_left"))) {
       pos = 5;
     } else if ((postion.equalsIgnoreCase("left_middle")) || (postion.equalsIgnoreCase("left"))) {
       pos = 6;
     } else if ((postion.equalsIgnoreCase("left_bottom")) || (postion.equalsIgnoreCase("bottom_left"))) {
       pos = 7;
     } else {
       pos = 0;
     }
     rect.setSystemBarPosition(pos);
     
     return true;
   }
   
   private static boolean parseTextRectWnd(Node node, FontRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     if ((!elem.hasAttribute("fontname")) || (!elem.hasAttribute("fontsize")) || 
       (!elem.hasAttribute("fontcolor")) || 
       (!elem.hasAttribute("backcolor")) || 
       (!elem.hasAttribute("transparent"))) {
       return false;
     }
     String szFontName = elem.getAttribute("fontname").trim();
     String szFontSize = elem.getAttribute("fontsize").trim();
     String szFontColor = elem.getAttribute("fontcolor").trim();
     String szBackColor = elem.getAttribute("backcolor").trim();
     String szTranparent = elem.getAttribute("transparent").trim();
     
     String szAlignH = elem.hasAttribute("align_h") ? elem.getAttribute(
       "align_h").trim() : "center";
     String szAlignV = elem.hasAttribute("align_v") ? elem.getAttribute(
       "align_v").trim() : "center";
     if ((szFontSize.equals("")) || (szFontColor.equals("")) || 
       (szBackColor.equals("")) || (szTranparent.equals(""))) {
       return false;
     }
     if ((!szFontColor.matches("^#[0-9a-fA-F]{6}$")) || 
       (!szBackColor.matches("^#[0-9a-fA-F]{6}$"))) {
       return false;
     }
     if ((szTranparent.compareToIgnoreCase("true") != 0) && 
       (szTranparent.compareToIgnoreCase("false") != 0)) {
       return false;
     }
     int nFontSize = szFontSize.matches("^\\d+$") ? Integer.parseInt(szFontSize) : -1;
     if (nFontSize <= 0) {
       return false;
     }
     int align_h = SupportFonts.CenterH;
     int align_v = SupportFonts.CenterV;
     if (szAlignH.compareToIgnoreCase("left") == 0) {
       align_h = SupportFonts.Left;
     } else if (szAlignH.compareToIgnoreCase("right") == 0) {
       align_h = SupportFonts.Right;
     }
     if (szAlignV.compareToIgnoreCase("top") == 0) {
       align_v = SupportFonts.Top;
     } else if (szAlignV.compareToIgnoreCase("bottom") == 0) {
       align_v = SupportFonts.Bottom;
     }
     rect.setFontSize(nFontSize);
     rect.setFontName(SupportFonts.ValidFont(szFontName) ? szFontName : SupportFonts.DefaultFontName);
     rect.setFontColor(new Color(Integer.parseInt(szFontColor.substring(1), 16)));
     rect.setBackColor(new Color(Integer.parseInt(szBackColor.substring(1), 16)));
     rect.setTransparent(Boolean.parseBoolean(szTranparent));
     rect.setAlignH(align_h);
     rect.setAlignV(align_v);
     if ((rect instanceof DateRect)) {
       rect.setTransparent(true);
     } else if ((rect instanceof TimeRect)) {
       rect.setTransparent(true);
     } else if ((rect instanceof WeekRect)) {
       rect.setTransparent(true);
     } else if ((rect instanceof WeatherTemperatureRect)) {
       rect.setTransparent(true);
     } else if ((rect instanceof WeatherTextRect)) {
       rect.setTransparent(true);
     } else if ((rect instanceof WeatherTitleRect)) {
       rect.setTransparent(true);
     }
     if (rect.getTransparent()) {
       rect.setBackColor(new Color(0, 0, 0));
     }
     if ((rect instanceof DateRect)) {
       return parseDateRect(elem, (DateRect)rect);
     }
     if ((rect instanceof TimeRect)) {
       return parseTimeRect(elem, (TimeRect)rect);
     }
     if ((rect instanceof WeekRect)) {
       return parseWeekRect(elem, (WeekRect)rect);
     }
     if ((rect instanceof WeatherTextRect)) {
       return parseWeatherTextRect(elem, (WeatherTextRect)rect);
     }
     if ((rect instanceof WeatherTemperatureRect)) {
       return parseWeatherTemperatureRect(elem, 
         (WeatherTemperatureRect)rect);
     }
     if ((rect instanceof WeatherLogoRect)) {
       return parseWeatherLogoRect(elem, (WeatherLogoRect)rect);
     }
     if ((rect instanceof WeatherTitleRect)) {
       return parseWeatherTitleRect(elem, (WeatherTitleRect)rect);
     }
     if ((rect instanceof SLFlipTRect)) {
       return parseSLFlipTRect(elem, (SLFlipTRect)rect);
     }
     if ((rect instanceof SLScrollTRect)) {
       return parseSLScrollTRect(elem, (SLScrollTRect)rect);
     }
     if ((rect instanceof SLStaticTRect)) {
       return parseSLStaticTRect(elem, (SLStaticTRect)rect);
     }
     if ((rect instanceof MLFlipTRect)) {
       return parseMLFlipTRect(elem, (MLFlipTRect)rect);
     }
     if ((rect instanceof MLScrollTRect)) {
       return parseMLScrollTRect(elem, (MLScrollTRect)rect);
     }
     if ((rect instanceof MLStaticTRect)) {
       return parseMLStaticTRect(elem, (MLStaticTRect)rect);
     }
     return true;
   }
   
   private static boolean parseDateRect(Node node, DateRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     if (!elem.hasAttribute("style")) {
       return false;
     }
     String szStyle = elem.getAttribute("style").trim();
     if (szStyle.equals("")) {
       return false;
     }
     int nStyle = szStyle.matches("^\\d+$") ? Integer.parseInt(szStyle) : 
       -1;
     if (!TimeFormat.isValidDate(nStyle)) {
       return false;
     }
     rect.setShowStyle(nStyle);
     return true;
   }
   
   private static boolean parseTimeRect(Node node, TimeRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     if (!elem.hasAttribute("style")) {
       return false;
     }
     String szStyle = elem.getAttribute("style").trim();
     if (szStyle.equals("")) {
       return false;
     }
     int nStyle = szStyle.matches("^\\d+$") ? Integer.parseInt(szStyle) : 
       -1;
     if (!TimeFormat.isValidTime(nStyle)) {
       return false;
     }
     rect.setShowStyle(nStyle);
     return true;
   }
   
   private static boolean parseWeekRect(Node node, WeekRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     if (!elem.hasAttribute("style")) {
       return false;
     }
     String szStyle = elem.getAttribute("style").trim();
     if (szStyle.equals("")) {
       return false;
     }
     int nStyle = szStyle.matches("^\\d+$") ? Integer.parseInt(szStyle) : 
       -1;
     if (!TimeFormat.isValidWeek(nStyle)) {
       return false;
     }
     rect.setShowStyle(nStyle);
     return true;
   }
   
   private static String getWeatherDayStyle(Node node)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return "today";
     }
     Element elem = (Element)node;
     if (!elem.hasAttribute("style")) {
       return "today";
     }
     String szStyle = elem.getAttribute("style").trim();
     if (szStyle.equals("")) {
       return "today";
     }
     return szStyle;
   }
   
   private static boolean parseWeatherTemperatureRect(Node node, WeatherTemperatureRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     try
     {
       rect.setWeatherDay(getWeatherDayStyle(node));
       return true;
     }
     catch (Exception e) {}
     return false;
   }
   
   private static boolean parseWeatherTextRect(Node node, WeatherTextRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     try
     {
       rect.setWeatherDay(getWeatherDayStyle(node));
       return true;
     }
     catch (Exception e) {}
     return false;
   }
   
   private static boolean parseWeatherLogoRect(Node node, WeatherLogoRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     try
     {
       rect.setWeatherDay(getWeatherDayStyle(node));
       return true;
     }
     catch (Exception e) {}
     return false;
   }
   
   private static boolean parseWeatherTitleRect(Node node, WeatherTitleRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     try
     {
       rect.setWeatherDay(getWeatherDayStyle(node));
       if (rect.getWeatherDay().equals("today"))
       {
         Element elem = (Element)node;
         String szTitle = elem.getAttribute("today").trim();
         
         Map<String, String> a = new HashMap();
         a.put("today", szTitle);
         rect.setTitles(a);
       }
       if (rect.getWeatherDay().equals("tomorrow"))
       {
         Element elem = (Element)node;
         String szTitle = elem.getAttribute("tomorrow").trim();
         
         Map<String, String> a = new HashMap();
         a.put("tomorrow", szTitle);
         rect.setTitles(a);
       }
       if (rect.getWeatherDay().equals("aftertomorrow"))
       {
         Element elem = (Element)node;
         String szTitle = elem.getAttribute("aftertomorrow").trim();
         
         Map<String, String> a = new HashMap();
         a.put("aftertomorrow", szTitle);
         rect.setTitles(a);
       }
       if (rect.getWeatherDay().equals("loop"))
       {
         Element elem = (Element)node;
         String szTitletoday = elem.getAttribute("today").trim();
         String szTitletomorrow = elem.getAttribute("tomorrow").trim();
         String szTitleaftertomorrow = elem.getAttribute("aftertomorrow").trim();
         
         Map<String, String> a = new HashMap();
         a.put("today", szTitletoday);
         a.put("tomorrow", szTitletomorrow);
         a.put("aftertomorrow", szTitleaftertomorrow);
         rect.setTitles(a);
       }
       return true;
     }
     catch (Exception e) {}
     return false;
   }
   
   private static boolean parseSLFlipTRect(Node node, SLFlipTRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     if (!elem.hasAttribute("speed")) {
       return false;
     }
     String szShowTime = elem.getAttribute("speed").trim();
     if (szShowTime.equals("")) {
       return false;
     }
     int nShowTime = szShowTime.matches("^\\d+$") ? 
       Integer.parseInt(szShowTime) : -1;
     
     rect.setShowTime(nShowTime);
     if (!elem.hasAttribute("texttype")) {
       return false;
     }
     String szTextType = elem.getAttribute("texttype").trim();
     if (szTextType.equals("")) {
       return false;
     }
     rect.setTextType(szTextType);
     
     return true;
   }
   
   private static boolean parseSLScrollTRect(Node node, SLScrollTRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     if (!elem.hasAttribute("speed")) {
       return false;
     }
     String szSpeed = elem.getAttribute("speed").trim();
     if (szSpeed.equals("")) {
       return false;
     }
     int nSpeed = szSpeed.matches("^\\d+$") ? Integer.parseInt(szSpeed) : 
       -1;
     
     rect.setScrollSpeed(nSpeed);
     if (!elem.hasAttribute("texttype")) {
       return false;
     }
     String szTextType = elem.getAttribute("texttype").trim();
     if (szTextType.equals("")) {
       return false;
     }
     rect.setTextType(szTextType);
     
     return true;
   }
   
   private static boolean parseSLStaticTRect(Node node, SLStaticTRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     if (!elem.hasAttribute("texttype")) {
       return false;
     }
     String szTextType = elem.getAttribute("texttype").trim();
     if (szTextType.equals("")) {
       return false;
     }
     rect.setTextType(szTextType);
     
     return true;
   }
   
   private static boolean parseMLFlipTRect(Node node, MLFlipTRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     if (!elem.hasAttribute("speed")) {
       return false;
     }
     String szShowTime = elem.getAttribute("speed").trim();
     if (szShowTime.equals("")) {
       return false;
     }
     int nShowTime = szShowTime.matches("^\\d+$") ? 
       Integer.parseInt(szShowTime) : -1;
     
     rect.setShowTime(nShowTime);
     if (!elem.hasAttribute("texttype")) {
       return false;
     }
     String szTextType = elem.getAttribute("texttype").trim();
     if (szTextType.equals("")) {
       return false;
     }
     rect.setTextType(szTextType);
     
     return true;
   }
   
   private static boolean parseMLScrollTRect(Node node, MLScrollTRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     if (!elem.hasAttribute("speed")) {
       return false;
     }
     String szSpeed = elem.getAttribute("speed").trim();
     if (szSpeed.equals("")) {
       return false;
     }
     int nSpeed = szSpeed.matches("^\\d+$") ? Integer.parseInt(szSpeed) : 
       -1;
     
     rect.setScrollSpeed(nSpeed);
     if (!elem.hasAttribute("texttype")) {
       return false;
     }
     String szTextType = elem.getAttribute("texttype").trim();
     if (szTextType.equals("")) {
       return false;
     }
     rect.setTextType(szTextType);
     
     return true;
   }
   
   private static boolean parseMLStaticTRect(Node node, MLStaticTRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     if (!elem.hasAttribute("texttype")) {
       return false;
     }
     String szTextType = elem.getAttribute("texttype").trim();
     if (szTextType.equals("")) {
       return false;
     }
     rect.setTextType(szTextType);
     
     return true;
   }
   
   private static boolean parseWebRect(Node node, WebRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     try
     {
       Element elem = (Element)node;
       
 
 
 
 
 
 
 
 
 
 
       String toolBar = elem.hasAttribute("show_toolbar") ? elem.getAttribute("show_toolbar").trim() : "";
       rect.setShowToolbar((toolBar.equalsIgnoreCase("true")) || (toolBar.equalsIgnoreCase("show")));
       if (!rect.getShowToolbar()) {
         return true;
       }
       String size = elem.hasAttribute("toolbar_iconsize") ? elem.getAttribute("toolbar_iconsize").trim() : "";
       String postion = elem.hasAttribute("toolbar_position") ? elem.getAttribute("toolbar_position").trim() : "";
       
       int icon_size = 2;
       if ((size.equalsIgnoreCase("icon_small")) || (size.equalsIgnoreCase("small"))) {
         icon_size = 1;
       } else if ((size.equalsIgnoreCase("icon_middle")) || (size.equalsIgnoreCase("middle"))) {
         icon_size = 2;
       } else if ((size.equalsIgnoreCase("icon_large")) || (size.equalsIgnoreCase("large"))) {
         icon_size = 3;
       } else {
         icon_size = 2;
       }
       rect.setToolbarSize(icon_size);
       
       int pos = 0;
       if ((postion.equalsIgnoreCase("left_top")) || (postion.equalsIgnoreCase("top_left"))) {
         pos = 0;
       } else if ((postion.equalsIgnoreCase("right_top")) || (postion.equalsIgnoreCase("top_right"))) {
         pos = 1;
       } else {
         pos = 0;
       }
       rect.setToolbarPosition(pos);
       
       return true;
     }
     catch (Exception e) {}
     return false;
   }
   
   private static boolean parseFlashRect(Node node, FlashRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     return true;
   }
   
   private static boolean parseAvInRect(Node node, AvInRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     try
     {
       Element elem = (Element)node;
       if (!elem.hasAttribute("playTime")) {
         rect.setPlayTime(5);
       }
       if (!elem.hasAttribute("scaleStyle")) {
         rect.setScaleStyle(0);
       }
       String szPlayTime = elem.getAttribute("playTime").trim();
       String szScaleStyle = elem.getAttribute("scaleStyle").trim();
       if (szPlayTime.equals("")) {
         rect.setPlayTime(0);
       }
       if (szScaleStyle.equals("")) {
         rect.setScaleStyle(0);
       }
       int nPlayTime = szPlayTime.matches("^\\d{1,4}$") ? 
         Integer.parseInt(szPlayTime) : -1;
       int nScaleStyle = szScaleStyle.matches("^\\d{1,4}$") ? 
         Integer.parseInt(szScaleStyle) : -1;
       if ((nPlayTime < 0) || (nScaleStyle < 0)) {
         return false;
       }
       rect.setPlayTime(nPlayTime);
       rect.setScaleStyle(nScaleStyle);
       
       return true;
     }
     catch (Exception e) {}
     return false;
   }
   
   private static boolean parseCameraRect(Node node, CameraRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     try
     {
       Element elem = (Element)node;
       if (!elem.hasAttribute("playTime")) {
         rect.setPlayTime(5);
       }
       if (!elem.hasAttribute("scaleStyle")) {
         rect.setScaleStyle(0);
       }
       String szPlayTime = elem.getAttribute("playTime").trim();
       String szScaleStyle = elem.getAttribute("scaleStyle").trim();
       if (szPlayTime.equals("")) {
         rect.setPlayTime(0);
       }
       if (szScaleStyle.equals("")) {
         rect.setScaleStyle(0);
       }
       int nPlayTime = szPlayTime.matches("^\\d{1,4}$") ? 
         Integer.parseInt(szPlayTime) : -1;
       int nScaleStyle = szScaleStyle.matches("^\\d{1,4}$") ? 
         Integer.parseInt(szScaleStyle) : -1;
       if ((nPlayTime < 0) || (nScaleStyle < 0)) {
         return false;
       }
       rect.setPlayTime(nPlayTime);
       rect.setScaleStyle(nScaleStyle);
       
       return true;
     }
     catch (Exception e) {}
     return false;
   }
   
   private static boolean parseLayout(Node node, Layout layout)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     if ((!elem.hasAttribute("width")) || (!elem.hasAttribute("height"))) {
       return false;
     }
     String szWidth = elem.getAttribute("width");
     String szHeight = elem.getAttribute("height");
     if ((szWidth.equals("")) || (szHeight.equals(""))) {
       return false;
     }
     int nWidth = szWidth.matches("^\\d{1,4}$") ? Integer.parseInt(szWidth) : -1;
     int nHeight = szHeight.matches("^\\d{1,4}$") ? Integer.parseInt(szHeight) : -1;
     if ((nWidth <= 0) || (nHeight < 0)) {
       return false;
     }
     layout.W = nWidth;
     layout.H = nHeight;
     
     NodeList nodeList = null;
     
 
 
 
 
     nodeList = elem.getChildNodes();
     if ((nodeList != null) && (nodeList.getLength() > 0)) {
       for (int i = 0; i < nodeList.getLength(); i++)
       {
         Node rect_node = nodeList.item(i);
         String szNodeName = rect_node.getNodeName().trim();
         
 
 
         Rect rect = null;
         if (szNodeName.equals(RectTag.Tag[1]))
         {
           rect = new ImageRect();
         }
         else if (szNodeName.equals(RectTag.Tag[2]))
         {
           rect = new VideoRect();
         }
         else if (szNodeName.equals(RectTag.Tag[3]))
         {
           rect = new DateRect();
         }
         else if (szNodeName.equals(RectTag.Tag[4]))
         {
           rect = new TimeRect();
         }
         else if (szNodeName.equals(RectTag.Tag[5]))
         {
           rect = new WeekRect();
         }
         else if (szNodeName.equals(RectTag.Tag[6]))
         {
           rect = new WeatherTemperatureRect();
         }
         else if (szNodeName.equals(RectTag.Tag[7]))
         {
           rect = new WeatherTextRect();
         }
         else if (szNodeName.equals(RectTag.Tag[8]))
         {
           rect = new WeatherLogoRect();
         }
         else if (szNodeName.equals(RectTag.Tag[9]))
         {
           rect = new WeatherTitleRect();
         }
         else if (szNodeName.equals(RectTag.Tag[11]))
         {
           rect = new HybridRect();
         }
         else if (szNodeName.equals(RectTag.Tag[12]))
         {
           rect = new DeviceRect();
         }
         else if (szNodeName.equals(RectTag.Tag[13]))
         {
           rect = new LogoRect();
         }
         else if (szNodeName.equals(RectTag.Tag[10]))
         {
           Element el = (Element)rect_node;
           
           String marqueetype = el.getAttribute("marqueetype").trim();
           switch (Integer.parseInt(marqueetype))
           {
           case 1: 
             rect = new SLFlipTRect();
             break;
           case 2: 
             rect = new SLScrollTRect();
             break;
           case 3: 
             rect = new SLStaticTRect();
             break;
           case 4: 
             rect = new MLFlipTRect();
             break;
           case 5: 
             rect = new MLScrollTRect();
             break;
           case 6: 
             rect = new MLStaticTRect();
             break;
           default: 
             rect = new SLStaticTRect();
             
 
             break;
           }
         }
         else if (szNodeName.equals(RectTag.Tag[14]))
         {
           rect = new WebRect();
         }
         else if (szNodeName.equals(RectTag.Tag[15]))
         {
           rect = new FlashRect();
         }
         else if (szNodeName.equals(RectTag.Tag[16]))
         {
           rect = new AvInRect();
         }
         else if (szNodeName.equals(RectTag.Tag[17]))
         {
           rect = new CameraRect();
         }
         if ((rect != null) && (parseRectAtrribute(rect_node, rect))) {
           layout.Add(rect);
         }
         rect_node = null;
       }
     }
     return true;
   }
   
   private static boolean parseProgram(Node node, Program program)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     if ((!elem.hasAttribute("id")) || (!elem.hasAttribute("name"))) {
       return false;
     }
     String szId = elem.getAttribute("id");
     String szName = elem.getAttribute("name");
     if (szId.equals("")) {
       return false;
     }
     int nId = szId.matches("^\\d+$") ? Integer.parseInt(szId) : -1;
     program.setProgramId(nId);
     program.setProgramName(szName);
     
     return parseLayout(elem, program);
   }
   
   private static boolean parseTemplate(Node node, Template template)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     if ((!elem.hasAttribute("id")) || (!elem.hasAttribute("name"))) {
       return false;
     }
     String szId = elem.getAttribute("id");
     String szName = elem.getAttribute("name");
     if (szId.equals("")) {
       return false;
     }
     int nId = szId.matches("^\\d+$") ? Integer.parseInt(szId) : -1;
     template.TemplateId = nId;
     template.TemplateName = szName;
     
     return parseLayout(elem, template);
   }
   
   private static boolean parseDeviceRect(Node node, DeviceRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     try
     {
       if ((node == null) || (!(node instanceof Element)))
       {
         rect.setDevicePath("\\HDD\\");
         rect.setPlayTime(5);
         rect.setScaleStyle(0);
         rect.setSwapStyle(0);
         rect.setSwapTime(2);
       }
       Element elem = (Element)node;
       if (!elem.hasAttribute("path")) {
         rect.setDevicePath("\\HDD\\");
       }
       if (!elem.hasAttribute("playTime")) {
         rect.setPlayTime(5);
       }
       if (!elem.hasAttribute("scaleStyle")) {
         rect.setScaleStyle(0);
       }
       if (!elem.hasAttribute("swapStyle")) {
         rect.setSwapStyle(0);
       }
       if (!elem.hasAttribute("swapTime")) {
         rect.setSwapTime(2);
       }
       String szPath = elem.getAttribute("path").trim();
       String szPlayTime = elem.getAttribute("playTime").trim();
       String szScaleStyle = elem.getAttribute("scaleStyle").trim();
       String szSwapStyle = elem.getAttribute("swapStyle").trim();
       String szSwapTime = elem.getAttribute("swapTime").trim();
       if (szPath.equals("")) {
         rect.setDevicePath("\\HDD\\");
       }
       if (szPlayTime.equals("")) {
         rect.setPlayTime(5);
       }
       if (szScaleStyle.equals("")) {
         rect.setScaleStyle(0);
       }
       if (szSwapStyle.equals("")) {
         rect.setSwapStyle(0);
       }
       if (szSwapTime.equals("")) {
         rect.setSwapTime(2);
       }
       int nPlayTime = szPlayTime.matches("^\\d{1,4}$") ? 
         Integer.parseInt(szPlayTime) : -1;
       int nScaleStyle = szScaleStyle.matches("^\\d{1,4}$") ? 
         Integer.parseInt(szScaleStyle) : -1;
       int nSwapStyle = szSwapStyle.matches("^\\d{1,4}$") ? 
         Integer.parseInt(szSwapStyle) : -1;
       int nSwapTime = szSwapTime.matches("^\\d{1,4}$") ? 
         Integer.parseInt(szSwapTime) : -1;
       if ((nPlayTime < 0) || (nScaleStyle < 0) || (nSwapStyle < 0) || (nSwapTime < 0)) {
         return false;
       }
       rect.setDevicePath(szPath);
       rect.setPlayTime(nPlayTime);
       rect.setScaleStyle(nScaleStyle);
       rect.setSwapStyle(nSwapStyle);
       rect.setSwapTime(nSwapTime);
       
       return true;
     }
     catch (Exception e) {}
     return false;
   }
   
   public static Layout parseXml(String xml)
   {
     return parseXml(xml, null);
   }
   
   public static Layout parseXml(String xml, String encoding)
   {
     try
     {
       ByteArrayInputStream input = null;
       if ((encoding == null) || (encoding.trim().equals(""))) {
         input = new ByteArrayInputStream(xml.getBytes());
       } else {
         input = new ByteArrayInputStream(xml.getBytes(encoding));
       }
       DocumentBuilderFactory docBuilderFactory = 
         DocumentBuilderFactory.newInstance();
       DocumentBuilder builder = docBuilderFactory.newDocumentBuilder();
       Document doc = builder.parse(input);
       input.close();
       
       Element elem = doc.getDocumentElement();
       String szTagName = elem.getTagName().trim();
       if (szTagName.equals("program"))
       {
         Program program = new Program();
         if (parseProgram(elem, program)) {
           return program;
         }
       }
       else if (szTagName.equals("template"))
       {
         Template template = new Template();
         if (parseTemplate(elem, template)) {
           return template;
         }
       }
       return null;
     }
     catch (ParserConfigurationException e)
     {
       e.printStackTrace();
       return null;
     }
     catch (SAXException e)
     {
       e.printStackTrace();
       return null;
     }
     catch (UnsupportedEncodingException e)
     {
       e.printStackTrace();
       return null;
     }
     catch (IOException e)
     {
       e.printStackTrace();
     }
     return null;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.XmlToLayout
 * JD-Core Version:    0.7.0.1
 */