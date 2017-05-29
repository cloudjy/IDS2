 package com.gnamp.struts.filter;
 
 import com.gnamp.server.handle.SystemSourceHandle;
 import com.gnamp.server.model.SystemSource;
 import com.gnamp.struts.vo.CategoryXMLConfig;
 import common.Logger;
 import java.io.File;
 import java.util.ArrayList;
 import java.util.List;
 import javax.servlet.ServletContext;
 import javax.servlet.ServletContextEvent;
 import org.apache.commons.lang.StringUtils;
 import org.dom4j.Attribute;
 import org.dom4j.Document;
 import org.dom4j.Element;
 import org.dom4j.QName;
 import org.dom4j.io.SAXReader;
 
 public class Context
 {
   private static List<SystemSource> SYSTEM_SOURCES;
   private static List<CategoryXMLConfig> SYSTEM_SOURCE_TYPES;
   public static final String USER_LOGIN = "user_login";
   public static final String GNAMP_WORK = "gnamp_work";
   public static final String DYNAMIC = "dynamic";
   public static final String GNAMP_XML_FILE = "gnamp.xml";
   public static final String INTERNAL_TAG_FILE = "InternalTag.xml";
   public static final String CURRENT_PRIVILEGE = "current_privilege";
   public static final String SESSION_LOCALE = "WW_TRANS_I18N_LOCALE";
   public static ServletContextEvent EVENT;
   protected static final Logger LOG = Logger.getLogger(Context.class);
   
   public static ServletContext getServletContext()
   {
     return EVENT.getServletContext();
   }
   
   public static String getWebRootPath()
   {
     return getServletContext().getRealPath("/");
   }
   
   public static String getProjectSaveFilePath()
   {
     return getServletContext().getRealPath("/") + "gnamp_work" + File.separator;
   }
   
   public static String getResourceURL(String fileName)
   {
     return getServletContext().getRealPath("/WEB-INF/classes/" + fileName);
   }
   
   public static List<SystemSource> getSystemSources()
   {
     if (SYSTEM_SOURCES == null) {
       SYSTEM_SOURCES = SystemSourceHandle.readAll(0);
     }
     return SYSTEM_SOURCES;
   }
   
   public static List<CategoryXMLConfig> getSystemSourceCates()
   {
     if (SYSTEM_SOURCE_TYPES == null)
     {
       SYSTEM_SOURCE_TYPES = new ArrayList();
       try
       {
         String path = getResourceURL("InternalTag.xml");
         SAXReader reader = new SAXReader();
         Document document = reader.read(path);
         List<Element> elements = document.selectNodes("internal/category");
         for (Element element : elements)
         {
           CategoryXMLConfig category = new CategoryXMLConfig();
           
           category.setId(Integer.valueOf(element.attribute(new QName("id")).getValue()).intValue());
           category.setName(element.attribute(new QName("name")).getValue());
           
           SYSTEM_SOURCE_TYPES.add(category);
         }
       }
       catch (Exception e)
       {
         LOG.error(e.getMessage(), e);
       }
     }
     return SYSTEM_SOURCE_TYPES;
   }
   
   public static String[] getFonts()
   {
     String[] nameArray = null;
     try
     {
       nameArray = FontsManager.getFontNames();
     }
     catch (Exception localException) {}
     return nameArray != null ? nameArray : new String[0];
   }
   
   public static boolean isEnglishVersion()
   {
     if (StringUtils.isBlank(ContextListener.language)) {
       return false;
     }
     return "EN".equalsIgnoreCase(ContextListener.language);
   }
   
   public static boolean isTraditionalChineseVersion()
   {
     if (StringUtils.isBlank(ContextListener.language)) {
       return false;
     }
     return "TW".equalsIgnoreCase(ContextListener.language);
   }
   
   public static boolean isMultiLanguageVersion()
   {
     if (StringUtils.isBlank(ContextListener.language)) {
       return true;
     }
     return (!"EN".equalsIgnoreCase(ContextListener.language)) && 
       (!"TW".equalsIgnoreCase(ContextListener.language));
   }
   
   public static boolean isAlcoholVersion()
   {
     return (!StringUtils.isBlank(ContextListener.Alcohol)) && 
       ("TRUE".equalsIgnoreCase(ContextListener.Alcohol));
   }
   
   public static String getProduct()
   {
     return ContextListener.product != null ? ContextListener.product : "";
   }
   
   public static boolean watcherEnable()
   {
     return ContextListener.WatcherEnable;
   }
   
   public static boolean showBuiltinResource()
   {
     return (ContextListener.ShowBuiltinResource) && (isMultiLanguageVersion());
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.filter.Context
 * JD-Core Version:    0.7.0.1
 */