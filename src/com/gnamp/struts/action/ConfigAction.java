 package com.gnamp.struts.action;
 
 import com.gnamp.server.handle.ConfigHandle;
 import com.gnamp.server.model.Config;
 import com.gnamp.server.query.Condition;
 import com.gnamp.server.query.ConfigColumn;
 import com.gnamp.server.query.Order;
 import com.gnamp.server.query.Result;
 import com.gnamp.struts.utils.GnampXML;
 import com.gnamp.struts.utils.MapTool;
 import com.gnamp.struts.vo.ConfigVo;
 import com.gnamp.struts.vo.GroupVo;
 import com.gnamp.struts.vo.Item;
 import com.gnamp.struts.vo.PageBean;
 import java.util.ArrayList;
 import java.util.Enumeration;
 import java.util.HashSet;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Locale;
 import java.util.Map;
 import java.util.Set;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.log4j.Logger;
 import org.dom4j.Document;
 import org.dom4j.DocumentHelper;
 import org.dom4j.Element;
 
 public class ConfigAction
   extends VersionConvertAction<ConfigHandle>
 {
   private static final Logger log = Logger.getLogger(ConfigAction.class);
   Config config;
   ConfigVo xmlConfig;
   String oName;
   
   public String getOName()
   {
     return this.oName;
   }
   
   public void setOName(String oName)
   {
     this.oName = oName;
   }
   
   List<Integer> configs = null;
   PageBean page;
   
   public List<Integer> getConfigs()
   {
     return this.configs;
   }
   
   public void setConfigs(List<Integer> configs)
   {
     this.configs = configs;
   }
   
   public PageBean getPage()
   {
     return this.page;
   }
   
   public void setPage(PageBean page)
   {
     this.page = page;
   }
   
   public ConfigVo getXmlConfig()
   {
     return this.xmlConfig;
   }
   
   public void setXmlConfig(ConfigVo xmlConfig)
   {
     this.xmlConfig = xmlConfig;
   }
   
   public Config getConfig()
   {
     return this.config;
   }
   
   public void setConfig(Config config)
   {
     this.config = config;
   }
   
   List<GroupVo> groups = new ArrayList();
   private String az;
   
   public List<GroupVo> getGroups()
   {
     return this.groups;
   }
   
   public void setGroups(List<GroupVo> groups)
   {
     this.groups = groups;
   }
   
   public String execute()
     throws Exception
   {
     try
     {
       String localeString = this.sessionMap.get("WW_TRANS_I18N_LOCALE") != null ? 
         this.sessionMap.get("WW_TRANS_I18N_LOCALE").toString() : getLocale().toString();
       if ((localeString != null) && (localeString.length() > 0))
       {
         if ((Locale.SIMPLIFIED_CHINESE.toString().equalsIgnoreCase(localeString)) || 
           (Locale.CHINESE.toString().equalsIgnoreCase(localeString)) || 
           (Locale.CHINA.toString().equalsIgnoreCase(localeString)) || 
           (Locale.PRC.toString().equalsIgnoreCase(localeString))) {
           this.groups = GnampXML.getTemplateList(GnampXML.getTemplatePath(Locale.SIMPLIFIED_CHINESE));
         } else if ((Locale.TRADITIONAL_CHINESE.toString().equalsIgnoreCase(localeString)) || 
           (Locale.TAIWAN.toString().equalsIgnoreCase(localeString))) {
           this.groups = GnampXML.getTemplateList(GnampXML.getTemplatePath(Locale.TRADITIONAL_CHINESE));
         } else {
           this.groups = GnampXML.getTemplateList(GnampXML.getTemplatePath(Locale.ENGLISH));
         }
       }
       else {
         this.groups = GnampXML.getTemplateList(GnampXML.getTemplatePath(Locale.ENGLISH));
       }
     }
     catch (Exception e)
     {
       log.error(e.getMessage());
     }
     return super.execute();
   }
   
   public String getAz()
   {
     return this.az;
   }
   
   public void setAz(String az)
   {
     this.az = az;
   }
   
   public void list()
   {
     try
     {
       Result<Config> result = ((ConfigHandle)getHandle()).readPage(new Condition("name".equals(getOName()) ? ConfigColumn.NAME : ConfigColumn.VERSION, "asc".equalsIgnoreCase(getAz()) ? Order.ASC : Order.DESC));
       
       response(JSONSuccessString(JSONArrayToString(result.getResult()), new MapTool().putObject("az", getAz())));
     }
     catch (Exception e)
     {
       log.error(e.getMessage());
       response(JSONErrorString(e.getMessage()));
     }
   }
   
   public void addBase()
   {
     try
     {
       this.config.setTodoXml(getTodoXMLByTemplate());
       if (((ConfigHandle)getHandle()).create(this.config))
       {
         ((ConfigHandle)getHandle()).check(this.config.getConfigId());
         response(JSONSuccessString());
         logEvent(getEnglish("createconfig"), getEnglish("createconfig") + ":[" + this.config.getConfigName() + "]");
       }
       else
       {
         throw new Exception(getText("createconfigfail"));
       }
     }
     catch (Exception e)
     {
       response(JSONErrorString(e.getMessage()));
       log.error(e.getMessage());
     }
   }
   
   private String getTodoXML()
   {
     StringBuffer buffer = new StringBuffer();
     try
     {
       Document document = DocumentHelper.createDocument();
       Element elementRoot = document.addElement("items");
       Enumeration<String> paramNames = this.servletRequest.getParameterNames();
       while (paramNames.hasMoreElements())
       {
         String name = (String)paramNames.nextElement();
         if (!"config.configId".equals(name))
         {
           Element element = elementRoot.addElement("item");
           element.addAttribute("name", name);
           element.addAttribute("value", this.servletRequest.getParameter(name));
         }
       }
       buffer.append(document.asXML());
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
     return buffer.toString();
   }
   
   private String getTodoXMLByTemplate()
   {
     StringBuffer buffer = new StringBuffer();
     try
     {
       Document document = DocumentHelper.createDocument();
       Element elementRoot = document.addElement("items");
       
       List<GroupVo> groups = null;
       if (this.sessionMap.get("WW_TRANS_I18N_LOCALE") == null)
       {
         if (getLocale().toString().equals(Locale.US.toString())) {
           groups = GnampXML.getTemplateList(GnampXML.getTemplatePath(Locale.ENGLISH));
         } else {
           groups = GnampXML.getTemplateList(GnampXML.getTemplatePath(Locale.CHINESE));
         }
       }
       else if (Locale.US.toString().equalsIgnoreCase(this.sessionMap.get("WW_TRANS_I18N_LOCALE").toString())) {
         groups = GnampXML.getTemplateList(GnampXML.getTemplatePath(Locale.ENGLISH));
       } else {
         groups = GnampXML.getTemplateList(GnampXML.getTemplatePath(Locale.CHINESE));
       }
       Iterator localIterator2;
       for (Iterator localIterator1 = groups.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
       {
         GroupVo gv = (GroupVo)localIterator1.next();
         localIterator2 = gv.getItem().iterator(); 
         Item item = (Item)localIterator2.next();
         setItemValue(elementRoot, item);
       }
       buffer.append(document.asXML());
     }
     catch (Exception e)
     {
       log.error(e.getMessage());
     }
     return buffer.toString();
   }
   
   private void setItemValue(Element eRoot, Item item)
   {
     Element element = eRoot.addElement("item");
     element.addAttribute("name", item.getName());
     element.addAttribute("value", item.getDefaultValue());
     if ((item.getItems() != null) && (item.getItems().size() > 0)) {
       for (Item child : item.getItems()) {
         setItemValue(eRoot, child);
       }
     }
   }
   
   public void saveBean()
   {
     try
     {
       Config configTemp = ((ConfigHandle)getHandle()).read(this.config.getConfigId());
       configTemp.setTodoXml(getTodoXML());
       if (((ConfigHandle)getHandle()).modify(configTemp))
       {
         response(JSONSuccessString());
         try
         {
           logEvent(getEnglish("saveconfig"), getEnglish("saveconfig") + ":[" + configTemp.getConfigName() + "]");
         }
         catch (Exception e)
         {
           try
           {
             e.printStackTrace(System.out);
             log.error(e.getClass().toString() + ", " + e.getMessage());
           }
           catch (Exception localException1) {}
         }
       }
       else
       {
         throw new Exception(getText("saveconfigfail"));
       }
     }
     catch (Exception e)
     {
       log.error(e.getMessage());
       response(JSONErrorString(e.getMessage()));
     }
   }
   
   public void deleteBean()
   {
     try
     {
       for (Integer i : this.configs)
       {
         Config tempConfig = ((ConfigHandle)getHandle()).read(i.intValue());
         if (!((ConfigHandle)getHandle()).remove(i.intValue())) {
           throw new Exception(getText("delconfigfail"));
         }
         logEvent(getEnglish("deleteconfig"), getEnglish("deleteconfig") + ":[" + tempConfig.getConfigName() + "]");
       }
       response(JSONSuccessString());
     }
     catch (Exception e)
     {
       response(JSONErrorString());
       log.error(e.getMessage());
     }
   }
   
   public void editbase()
   {
     Config configTemp = ((ConfigHandle)getHandle()).read(this.config.getConfigId());
     configTemp.setDescription(this.config.getDescription());
     configTemp.setConfigName(this.config.getConfigName());
     try
     {
       if (((ConfigHandle)getHandle()).modify(configTemp))
       {
         response(JSONSuccessString());
         logEvent(getEnglish("editconfig"), getEnglish("editconfig") + ":[" + configTemp.getConfigName() + "]");
       }
       else
       {
         throw new Exception(getText("editconfigfail"));
       }
     }
     catch (Exception e)
     {
       response(JSONErrorString());
       log.error(e.getMessage());
     }
   }
   
   public void checkbean()
   {
     Set<String> errors = new HashSet();
     try
     {
       for (Integer c : this.configs) {
         try
         {
           Config configTemp = ((ConfigHandle)getHandle()).read(c.intValue());
           if (((ConfigHandle)getHandle()).check(c.intValue()) == null) {
             throw new Exception();
           }
           logEvent(getEnglish("checkconfig"), getEnglish("checkconfig") + ":[" + configTemp.getConfigName() + "]");
         }
         catch (Exception e)
         {
           errors.add(String.valueOf(c));
         }
       }
       if (errors.size() > 0) {
         throw new Exception(getText("auditconfigfail"));
       }
       response(JSONSuccessString());
     }
     catch (Exception e)
     {
       response(JSONErrorString(e.getMessage()));
       log.error(e.getMessage());
     }
   }
   
   public void loadbean()
   {
     try
     {
       response(JSONSuccessString(JSONObjectToString(((ConfigHandle)getHandle()).read(this.config.getConfigId()))));
     }
     catch (Exception e)
     {
       response(JSONErrorString());
       log.error(e.getMessage());
     }
   }
   
   public String preeditbean()
   {
     return "preeditbean";
   }
   
   public String preadd()
   {
     return "preadd";
   }
   
   public String preedit()
   {
     return "preedit";
   }
   
   public String prelist()
   {
     return "prelist";
   }
   
   protected Class<ConfigHandle> getHandleClass()
   {
     return ConfigHandle.class;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.ConfigAction
 * JD-Core Version:    0.7.0.1
 */