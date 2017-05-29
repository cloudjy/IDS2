 package com.gnamp.struts.action;
 
 import com.gnamp.server.model.AppRect;
 import com.gnamp.server.model.AvInRect;
 import com.gnamp.server.model.CameraRect;
 import com.gnamp.server.model.DateRect;
 import com.gnamp.server.model.DeviceRect;
 import com.gnamp.server.model.FlashRect;
 import com.gnamp.server.model.FontRect;
 import com.gnamp.server.model.ImageRect;
 import com.gnamp.server.model.InteractiveRect;
 import com.gnamp.server.model.InteractiveRect.Util;
 import com.gnamp.server.model.MLFlipTRect;
 import com.gnamp.server.model.MLScrollTRect;
 import com.gnamp.server.model.MLStaticTRect;
 import com.gnamp.server.model.Rect;
 import com.gnamp.server.model.SLFlipTRect;
 import com.gnamp.server.model.SLScrollTRect;
 import com.gnamp.server.model.SLStaticTRect;
 import com.gnamp.server.model.TimeRect;
 import com.gnamp.server.model.WeatherLogoRect;
 import com.gnamp.server.model.WeatherTemperatureRect;
 import com.gnamp.server.model.WeatherTextRect;
 import com.gnamp.server.model.WeatherTitleRect;
 import com.gnamp.server.model.WebRect;
 import com.gnamp.server.model.WeekRect;
 import java.awt.Color;
 import java.io.ByteArrayOutputStream;
 import java.io.IOException;
 import java.io.UnsupportedEncodingException;
 import java.util.Map;
 import javax.xml.parsers.DocumentBuilder;
 import javax.xml.parsers.DocumentBuilderFactory;
 import javax.xml.parsers.ParserConfigurationException;
 import javax.xml.transform.Transformer;
 import javax.xml.transform.TransformerConfigurationException;
 import javax.xml.transform.TransformerException;
 import javax.xml.transform.TransformerFactory;
 import javax.xml.transform.dom.DOMSource;
 import javax.xml.transform.stream.StreamResult;
 import org.w3c.dom.Document;
 import org.w3c.dom.Element;
 import org.w3c.dom.Node;
 
 class LayoutToXml
 {
   private static boolean transformRectAtrribute(Node node, Rect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     elem.setAttribute("rectid", Integer.toString(rect.getRectId()));
     elem.setAttribute("rectname", rect.getRectName());
     elem.setAttribute("nX", Integer.toString(rect.getPosX()));
     elem.setAttribute("nY", Integer.toString(rect.getPosY()));
     elem.setAttribute("nW", Integer.toString(rect.getWidth()));
     elem.setAttribute("nH", Integer.toString(rect.getHeight()));
     elem.setAttribute("layer", Integer.toString(rect.getLayer()));
     if (((rect instanceof InteractiveRect)) && 
       (!transformInteractiveRect(elem, (InteractiveRect)rect))) {
       return false;
     }
     if (((rect instanceof AppRect)) && (!transformAppRect(elem, (AppRect)rect))) {
       return false;
     }
     if ((rect instanceof FontRect)) {
       return transformTextRectWnd(elem, (FontRect)rect);
     }
     if ((rect instanceof WeatherLogoRect)) {
       return transformWeatherLogoRect(elem, (WeatherLogoRect)rect);
     }
     if ((rect instanceof DeviceRect)) {
       return transformDeviceRect(elem, (DeviceRect)rect);
     }
     if ((rect instanceof WebRect)) {
       return transformWebRect(elem, (WebRect)rect);
     }
     if ((rect instanceof FlashRect)) {
       return transformFlashRect(elem, (FlashRect)rect);
     }
     if ((rect instanceof AvInRect)) {
       return transformAvInRect(elem, (AvInRect)rect);
     }
     if ((rect instanceof CameraRect)) {
       return transformCameraRect(elem, (CameraRect)rect);
     }
     return true;
   }
   
   private static boolean transformRectAtrribute(Node node, Rect rect, String src)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     elem.setAttribute("rectid", Integer.toString(rect.getRectId()));
     elem.setAttribute("rectname", rect.getRectName());
     elem.setAttribute("nX", Integer.toString(rect.getPosX()));
     elem.setAttribute("nY", Integer.toString(rect.getPosY()));
     elem.setAttribute("nW", Integer.toString(rect.getWidth()));
     elem.setAttribute("nH", Integer.toString(rect.getHeight()));
     elem.setAttribute("layer", Integer.toString(rect.getLayer()));
     if (((rect instanceof InteractiveRect)) && 
       (!transformInteractiveRect(elem, (InteractiveRect)rect))) {
       return false;
     }
     if (((rect instanceof AppRect)) && (!transformAppRect(elem, (AppRect)rect))) {
       return false;
     }
     if ((rect instanceof ImageRect)) {
       elem.setAttribute("src", rect.getRectSrc());
     }
     if ((rect instanceof FontRect)) {
       return transformTextRectWnd(elem, (FontRect)rect);
     }
     if ((rect instanceof WeatherLogoRect)) {
       return transformWeatherLogoRect(elem, (WeatherLogoRect)rect);
     }
     if ((rect instanceof DeviceRect)) {
       return transformDeviceRect(elem, (DeviceRect)rect);
     }
     if ((rect instanceof WebRect)) {
       return transformWebRect(elem, (WebRect)rect);
     }
     if ((rect instanceof FlashRect)) {
       return transformFlashRect(elem, (FlashRect)rect);
     }
     if ((rect instanceof AvInRect)) {
       return transformAvInRect(elem, (AvInRect)rect);
     }
     if ((rect instanceof CameraRect)) {
       return transformCameraRect(elem, (CameraRect)rect);
     }
     return true;
   }
   
   private static boolean transformInteractiveRect(Node node, InteractiveRect rect)
   {
     if ((node == null) || (!(node instanceof Element)) || (rect == null)) {
       return false;
     }
     Element elem = (Element)node;
     
     int interactiveType = rect.getInteractiveType();
     int program = rect.getJumpProgram();
     if (interactiveType == 0)
     {
       elem.setAttribute("interactive", "off");
     }
     else if (interactiveType == 1)
     {
       elem.setAttribute("interactive", "enter");
     }
     else if (interactiveType == 2)
     {
       elem.setAttribute("interactive", "exit");
     }
     else if (interactiveType == 3)
     {
       elem.setAttribute("interactive", "browse");
     }
     else if (interactiveType == 4)
     {
       elem.setAttribute("interactive", "guide_browse");
     }
     else if (interactiveType == 5)
     {
       if (InteractiveRect.Util.vailidJumpProgram(program))
       {
         elem.setAttribute("interactive", "jump");
         elem.setAttribute("jump", String.valueOf(program));
       }
       else
       {
         return false;
       }
     }
     else if (interactiveType == 6)
     {
       elem.setAttribute("interactive", "last");
     }
     else
     {
       if (interactiveType == 7)
       {
         elem.setAttribute("interactive", "open_app");
         return true;
       }
       if (interactiveType == 8)
       {
         elem.setAttribute("interactive", "full_view");
       }
       else
       {
         if (interactiveType == 9)
         {
           elem.setAttribute("interactive", "map_demand");
           return true;
         }
         elem.setAttribute("interactive", "off");
       }
     }
     return true;
   }
   
   private static boolean transformAppRect(Node node, AppRect rect)
   {
     if ((node == null) || (!(node instanceof Element)) || (rect == null)) {
       return false;
     }
     Element elem = (Element)node;
     String packageName = rect.getAppPackageName();
     if ((packageName == null) || ((packageName = packageName.trim()).length() <= 0)) {
       return true;
     }
     elem.setAttribute("package", packageName);
     elem.setAttribute("class", rect.getAppClassName());
     elem.setAttribute("app_timeout", Long.toString(rect.getAppIdleTimeout()));
     if (!rect.getShowSystemBar()) {
       return true;
     }
     elem.setAttribute("show_systembar", rect.getShowSystemBar() ? "true" : "false");
     
     int btnType = rect.getSystemBarButton();
     String button = "";
     if ((btnType & 0x1) != 0) {
       button = button + (button.length() > 0 ? "|" : "") + "back";
     }
     if ((btnType & 0x2) != 0) {
       button = button + (button.length() > 0 ? "|" : "") + "close";
     }
     elem.setAttribute("systembar_button", button);
     
     int barSize = rect.getSystemBarSize();
     String icon_size = "middle";
     if (1 == barSize) {
       icon_size = "small";
     } else if (2 == barSize) {
       icon_size = "middle";
     } else if (3 == barSize) {
       icon_size = "large";
     } else {
       icon_size = "middle";
     }
     elem.setAttribute("systembar_iconsize", icon_size);
     
 
     int position = rect.getSystemBarPosition();
     String pos = "bottom_middle";
     if (position == 0) {
       pos = "bottom_middle";
     } else if (1 == position) {
       pos = "right_bottom";
     } else if (2 == position) {
       pos = "right_middle";
     } else if (3 == position) {
       pos = "right_top";
     } else if (4 == position) {
       pos = "top_middle";
     } else if (5 == position) {
       pos = "left_top";
     } else if (6 == position) {
       pos = "left_middle";
     } else if (7 == position) {
       pos = "left_bottom";
     } else {
       pos = "bottom_middle";
     }
     elem.setAttribute("systembar_position", pos);
     
     return true;
   }
   
   private static boolean transformTextRectWnd(Node node, FontRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     
 
 
     elem.setAttribute("fontname", SupportFonts.ValidFont(rect.getFontName()) ? rect.getFontName() : "");
     elem.setAttribute("fontsize", Integer.toString(rect.getFontSize()));
     elem.setAttribute("fontcolor", String.format("#%02X%02X%02X", new Object[] {
       Integer.valueOf(rect.getFontColor().getRed()), Integer.valueOf(rect.getFontColor().getGreen()), 
       Integer.valueOf(rect.getFontColor().getBlue()) }));
     elem.setAttribute("backcolor", String.format("#%02X%02X%02X", new Object[] {
       Integer.valueOf(rect.getBackColor().getRed()), Integer.valueOf(rect.getBackColor().getGreen()), 
       Integer.valueOf(rect.getBackColor().getBlue()) }));
     elem.setAttribute("transparent", Boolean.toString(rect.getTransparent()));
     
     elem.setAttribute("align_v", 
       rect
       .getAlignV() == SupportFonts.Bottom ? "bottom" : rect.getAlignV() == SupportFonts.Top ? "top" : 
       "center");
     elem.setAttribute("align_h", 
       rect
       .getAlignH() == SupportFonts.Right ? "right" : rect.getAlignH() == SupportFonts.Left ? "left" : 
       "center");
     if ((rect instanceof DateRect)) {
       return transformDateRect(elem, (DateRect)rect);
     }
     if ((rect instanceof TimeRect)) {
       return transformTimeRect(elem, (TimeRect)rect);
     }
     if ((rect instanceof WeekRect)) {
       return transformWeekRect(elem, (WeekRect)rect);
     }
     if ((rect instanceof WeatherLogoRect)) {
       return transformWeatherLogoRect(elem, (WeatherLogoRect)rect);
     }
     if ((rect instanceof WeatherTextRect)) {
       return transformWeatherTextRect(elem, (WeatherTextRect)rect);
     }
     if ((rect instanceof WeatherTemperatureRect)) {
       return transformWeatherTemperatureRect(elem, (WeatherTemperatureRect)rect);
     }
     if ((rect instanceof WeatherTitleRect)) {
       return transformWeatherTitleRect(elem, (WeatherTitleRect)rect);
     }
     if ((rect instanceof SLFlipTRect)) {
       return transformSLFlipTRect(elem, (SLFlipTRect)rect);
     }
     if ((rect instanceof SLScrollTRect)) {
       return transformSLScrollTRect(elem, (SLScrollTRect)rect);
     }
     if ((rect instanceof SLStaticTRect)) {
       return transformSLStaticTRect(elem, (SLStaticTRect)rect);
     }
     if ((rect instanceof MLFlipTRect)) {
       return transformMLFlipTRect(elem, (MLFlipTRect)rect);
     }
     if ((rect instanceof MLScrollTRect)) {
       return transformMLScrollTRect(elem, (MLScrollTRect)rect);
     }
     if ((rect instanceof MLStaticTRect)) {
       return transformMLStaticTRect(elem, (MLStaticTRect)rect);
     }
     return true;
   }
   
   private static boolean transformDateRect(Node node, DateRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     
     elem.setAttribute("style", Integer.toString(rect.getShowStyle()));
     
     return true;
   }
   
   private static boolean transformTimeRect(Node node, TimeRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     
     elem.setAttribute("style", Integer.toString(rect.getShowStyle()));
     
     return true;
   }
   
   private static boolean transformWeekRect(Node node, WeekRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     
     elem.setAttribute("style", Integer.toString(rect.getShowStyle()));
     
     return true;
   }
   
   private static boolean setWeatherDayStyle(Node node, String weatherDay)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     
     elem.setAttribute("style", weatherDay);
     
     return true;
   }
   
   private static boolean transformWeatherTemperatureRect(Node node, WeatherTemperatureRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     return setWeatherDayStyle(node, rect.getWeatherDay());
   }
   
   private static boolean transformWeatherTextRect(Node node, WeatherTextRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     return setWeatherDayStyle(node, rect.getWeatherDay());
   }
   
   private static boolean transformWeatherLogoRect(Node node, WeatherLogoRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     
     elem.setAttribute("fontname", SupportFonts.ValidFont(rect.getFontName()) ? rect.getFontName() : "");
     elem.setAttribute("fontsize", Integer.toString(rect.getFontSize()));
     elem.setAttribute("fontcolor", String.format("#%02X%02X%02X", new Object[] {
       Integer.valueOf(rect.getFontColor().getRed()), 
       Integer.valueOf(rect.getFontColor().getGreen()), 
       Integer.valueOf(rect.getFontColor().getBlue()) }));
     elem.setAttribute("backcolor", String.format("#%02X%02X%02X", new Object[] {
       Integer.valueOf(rect.getBackColor().getRed()), 
       Integer.valueOf(rect.getBackColor().getGreen()), 
       Integer.valueOf(rect.getBackColor().getBlue()) }));
     elem.setAttribute("transparent", Boolean.toString(rect.getTransparent()));
     
     elem.setAttribute("align_v", 
       rect.getAlignV() == SupportFonts.Bottom ? "bottom" : rect.getAlignV() == SupportFonts.Top ? "top" : "center");
     elem.setAttribute("align_h", 
       rect.getAlignH() == SupportFonts.Right ? "right" : rect.getAlignH() == SupportFonts.Left ? "left" : "center");
     
     return setWeatherDayStyle(node, rect.getWeatherDay());
   }
   
   private static boolean transformDeviceRect(Node node, DeviceRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     
     elem.setAttribute("path", rect.getDevicePath());
     elem.setAttribute("playTime", String.valueOf(rect.getPlayTime()));
     elem.setAttribute("scaleStyle", String.valueOf(rect.getScaleStyle()));
     elem.setAttribute("swapStyle", String.valueOf(rect.getSwapStyle()));
     elem.setAttribute("swapTime", String.valueOf(rect.getSwapTime()));
     
     return true;
   }
   
   private static boolean transformWeatherTitleRect(Node node, WeatherTitleRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     setWeatherDayStyle(node, rect.getWeatherDay());
     
     Element elem = (Element)node;
     if (rect.getWeatherDay().equals("today"))
     {
       elem.setAttribute("today", (String)rect.getTitles().get("today"));
     }
     else if (rect.getWeatherDay().equals("tomorrow"))
     {
       elem.setAttribute("tomorrow", (String)rect.getTitles().get("tomorrow"));
     }
     else if (rect.getWeatherDay().equals("aftertomorrow"))
     {
       elem.setAttribute("aftertomorrow", (String)rect.getTitles().get("aftertomorrow"));
     }
     else if (rect.getWeatherDay().equals("loop"))
     {
       elem.setAttribute("today", (String)rect.getTitles().get("today"));
       elem.setAttribute("tomorrow", (String)rect.getTitles().get("tomorrow"));
       elem.setAttribute("aftertomorrow", (String)rect.getTitles().get("aftertomorrow"));
     }
     else
     {
       elem.setAttribute("today", (String)rect.getTitles().get("today"));
     }
     return true;
   }
   
   private static boolean transformSLFlipTRect(Node node, SLFlipTRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     
     elem.setAttribute("speed", Integer.toString(rect.getShowTime()));
     elem.setAttribute("marqueetype", "1");
     elem.setAttribute("texttype", rect.getTextType());
     
     return true;
   }
   
   private static boolean transformSLScrollTRect(Node node, SLScrollTRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     
 
     elem.setAttribute("speed", Integer.toString(rect.getScrollSpeed()));
     elem.setAttribute("marqueetype", "2");
     elem.setAttribute("texttype", rect.getTextType());
     
     return true;
   }
   
   private static boolean transformSLStaticTRect(Node node, SLStaticTRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     
 
 
 
 
     elem.setAttribute("marqueetype", "3");
     elem.setAttribute("speed", "0");
     elem.setAttribute("texttype", rect.getTextType());
     
     return true;
   }
   
   private static boolean transformMLFlipTRect(Node node, MLFlipTRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     
     elem.setAttribute("speed", Integer.toString(rect.getShowTime()));
     elem.setAttribute("marqueetype", "4");
     elem.setAttribute("texttype", rect.getTextType());
     
     return true;
   }
   
   private static boolean transformMLScrollTRect(Node node, MLScrollTRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     
     elem.setAttribute("speed", Integer.toString(rect.getScrollSpeed()));
     elem.setAttribute("marqueetype", "5");
     elem.setAttribute("texttype", rect.getTextType());
     
     return true;
   }
   
   private static boolean transformMLStaticTRect(Node node, MLStaticTRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     
 
 
     elem.setAttribute("marqueetype", "6");
     elem.setAttribute("speed", "0");
     elem.setAttribute("texttype", rect.getTextType());
     
     return true;
   }
   
   private static boolean transformWebRect(Node node, WebRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     if (!rect.getShowToolbar()) {
       return true;
     }
     elem.setAttribute("show_toolbar", "true");
     
     int barSize = rect.getToolbarSize();
     String icon_size = "middle";
     if (1 == barSize) {
       icon_size = "small";
     } else if (2 == barSize) {
       icon_size = "middle";
     } else if (3 == barSize) {
       icon_size = "large";
     } else {
       icon_size = "middle";
     }
     elem.setAttribute("toolbar_iconsize", icon_size);
     
     int position = rect.getToolbarPosition();
     String pos = "left_top";
     if (position == 0) {
       pos = "left_top";
     } else if (1 == position) {
       pos = "right_top";
     } else {
       pos = "left_top";
     }
     elem.setAttribute("toolbar_position", pos);
     return true;
   }
   
   private static boolean transformFlashRect(Node node, FlashRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     return true;
   }
   
   private static boolean transformAvInRect(Node node, AvInRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     
     elem.setAttribute("playTime", String.valueOf(rect.getPlayTime()));
     elem.setAttribute("scaleStyle", String.valueOf(rect.getScaleStyle()));
     return true;
   }
   
   private static boolean transformCameraRect(Node node, CameraRect rect)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     
     elem.setAttribute("playTime", String.valueOf(rect.getPlayTime()));
     elem.setAttribute("scaleStyle", String.valueOf(rect.getScaleStyle()));
     return true;
   }
   
   private static boolean transformLayout(Node node, Layout layout, Document doc)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     
     elem.setAttribute("width", Integer.toString(layout.W));
     elem.setAttribute("height", Integer.toString(layout.H));
     elem.setAttribute("mainRectId", Integer.toString(layout.getMainRectId()));
     
     Rect[] rects = layout.RectWnds();
     if ((rects != null) && (rects.length > 0)) {
       for (int i = 0; i < rects.length; i++)
       {
         String tagName = RectTag.tagString(rects[i]);
         Element rect_elem = null;
         if ((tagName != null) && (!tagName.equals(""))) {
           rect_elem = doc.createElement(tagName);
         }
         if (rect_elem != null)
         {
           if (!transformRectAtrribute(rect_elem, rects[i])) {
             return false;
           }
           elem.appendChild(rect_elem);
         }
       }
     }
     return true;
   }
   
   private static boolean transformLayout(Node node, Layout layout, Document doc, String src)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     
     elem.setAttribute("width", Integer.toString(layout.W));
     elem.setAttribute("height", Integer.toString(layout.H));
     
     Rect[] rects = layout.RectWnds();
     if ((rects != null) && (rects.length > 0)) {
       for (int i = 0; i < rects.length; i++)
       {
         String tagName = RectTag.tagString(rects[i]);
         Element rect_elem = null;
         if ((tagName != null) && (!tagName.equals(""))) {
           rect_elem = doc.createElement(tagName);
         }
         if (rect_elem != null)
         {
           if (!transformRectAtrribute(rect_elem, rects[i], src)) {
             return false;
           }
           elem.appendChild(rect_elem);
         }
       }
     }
     return true;
   }
   
   private static boolean transformProgram(Node node, Program program, Document doc)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     elem.setAttribute("id", Integer.toString(program.getProgramId()));
     elem.setAttribute("name", program.getProgramName());
     elem.setAttribute("interactive", program.getInteractive() ? "true" : "false");
     
     return transformLayout(elem, program, doc);
   }
   
   private static boolean transformProgram(Node node, Program program, Document doc, String src)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     elem.setAttribute("id", Integer.toString(program.getProgramId()));
     elem.setAttribute("name", program.getProgramName());
     
     return transformLayout(elem, program, doc, src);
   }
   
   private static boolean transformTemplate(Node node, Template template, Document doc)
   {
     if ((node == null) || (!(node instanceof Element))) {
       return false;
     }
     Element elem = (Element)node;
     elem.setAttribute("id", Integer.toString(template.TemplateId));
     elem.setAttribute("name", template.TemplateName);
     
     return transformLayout(elem, template, doc);
   }
   
   public static String transform(Layout layout)
   {
     return transform(layout, null);
   }
   
   public static String transform(Layout layout, String encoding)
   {
     if (layout == null) {
       return null;
     }
     try
     {
       DocumentBuilderFactory docBuilderFactory = 
         DocumentBuilderFactory.newInstance();
       DocumentBuilder builder = docBuilderFactory.newDocumentBuilder();
       
       Document doc = builder.newDocument();
       Element elem = null;
       if ((layout instanceof Program))
       {
         elem = doc.createElement("program");
         transformProgram(elem, (Program)layout, doc);
         doc.appendChild(elem);
       }
       else if ((layout instanceof Template))
       {
         elem = doc.createElement("template");
         transformTemplate(elem, (Template)layout, doc);
         doc.appendChild(elem);
       }
       TransformerFactory transformFactory = 
         TransformerFactory.newInstance();
       Transformer transform = transformFactory.newTransformer();
       if ((encoding != null) && (!encoding.trim().equals(""))) {
         transform.setOutputProperty("encoding", encoding);
       }
       ByteArrayOutputStream out = new ByteArrayOutputStream();
       transform.transform(new DOMSource(doc), new StreamResult(out));
       
       String xml = null;
       if ((encoding == null) || (encoding.trim().equals(""))) {
         xml = out.toString();
       } else {
         xml = out.toString(encoding);
       }
       out.close();
       return xml;
     }
     catch (ParserConfigurationException e)
     {
       e.printStackTrace();
       return null;
     }
     catch (TransformerConfigurationException e)
     {
       e.printStackTrace();
       return null;
     }
     catch (TransformerException e)
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
   
   public static String transform(Layout layout, String encoding, String src)
   {
     if (layout == null) {
       return null;
     }
     try
     {
       DocumentBuilderFactory docBuilderFactory = 
         DocumentBuilderFactory.newInstance();
       DocumentBuilder builder = docBuilderFactory.newDocumentBuilder();
       
       Document doc = builder.newDocument();
       Element elem = null;
       if ((layout instanceof Program))
       {
         elem = doc.createElement("program");
         transformProgram(elem, (Program)layout, doc, src);
         doc.appendChild(elem);
       }
       else if ((layout instanceof Template))
       {
         elem = doc.createElement("template");
         transformTemplate(elem, (Template)layout, doc);
         doc.appendChild(elem);
       }
       TransformerFactory transformFactory = 
         TransformerFactory.newInstance();
       Transformer transform = transformFactory.newTransformer();
       if ((encoding != null) && (!encoding.trim().equals(""))) {
         transform.setOutputProperty("encoding", encoding);
       }
       ByteArrayOutputStream out = new ByteArrayOutputStream();
       transform.transform(new DOMSource(doc), new StreamResult(out));
       
       String xml = null;
       if ((encoding == null) || (encoding.trim().equals(""))) {
         xml = out.toString();
       } else {
         xml = out.toString(encoding);
       }
       out.close();
       return xml;
     }
     catch (ParserConfigurationException e)
     {
       e.printStackTrace();
       return null;
     }
     catch (TransformerConfigurationException e)
     {
       e.printStackTrace();
       return null;
     }
     catch (TransformerException e)
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
 * Qualified Name:     com.gnamp.struts.action.LayoutToXml
 * JD-Core Version:    0.7.0.1
 */