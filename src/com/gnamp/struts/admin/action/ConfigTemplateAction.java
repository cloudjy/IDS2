 package com.gnamp.struts.admin.action;
 
 import com.gnamp.struts.utils.GnampXML;
 import com.gnamp.struts.vo.GroupVo;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Locale;
 import java.util.Map;
 import org.apache.log4j.Logger;
 
 public class ConfigTemplateAction
   extends AdminBaseAction
 {
   Logger log = Logger.getLogger(ConfigTemplateAction.class);
   List<GroupVo> groups = new ArrayList();
   
   public void load()
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
       this.log.error(e.getMessage());
     }
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
       this.log.error(e.getMessage());
     }
     return super.execute();
   }
   
   public List<GroupVo> getGroups()
   {
     return this.groups;
   }
   
   public void setGroups(List<GroupVo> groups)
   {
     this.groups = groups;
   }
   
   public String upload()
   {
     return "upload";
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.admin.action.ConfigTemplateAction
 * JD-Core Version:    0.7.0.1
 */