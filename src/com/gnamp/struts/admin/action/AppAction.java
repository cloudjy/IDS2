 package com.gnamp.struts.admin.action;
 
 import com.gnamp.server.handle.AppHandle;
 import com.gnamp.server.model.App;
 import com.gnamp.server.query.Condition;
 import com.gnamp.server.query.Result;
 import com.gnamp.struts.utils.JsonUtils;
 import com.gnamp.struts.utils.MapTool;
 import com.gnamp.struts.vo.PageBean;
 import java.util.HashSet;
 import java.util.List;
 import java.util.Map;
 import java.util.Set;
 import org.apache.log4j.Logger;
 
 public class AppAction
   extends JSONAction
 {
   Logger log = Logger.getLogger(AppAction.class);
   List<String> versions;
   PageBean page = new PageBean();
   protected Set<String> errors = new HashSet();
   App app;
   List<App> apps;
   private static final long serialVersionUID = 1L;
   
   public void appUsed()
   {
     try
     {
       AppHandle handle = new AppHandle(this);
       for (String version : this.versions) {
         if (handle.appUsed(version)) {
           throw new Exception();
         }
       }
       response(JSONSuccessString());
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorMessage(this.response, getText("usedisdelete"));
       this.log.error(e.getMessage());
     }
   }
   
   public void edit()
   {
     try
     {
       AppHandle handle = new AppHandle(this);
       App temp = AppHandle.read(this.app.getAppVersion());
       if (temp == null) {
         throw new Exception(getText("notexists"));
       }
       temp.setDescription(this.app.getDescription());
       if (handle.modify(temp)) {
         JsonUtils.writeSuccess(this.response);
       } else {
         JsonUtils.writeErrorMessage(this.response, getText("editerror"));
       }
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorMessage(this.response, getText("editerror"));
       this.log.error(e.getMessage());
     }
   }
   
   public App getApp()
   {
     return this.app;
   }
   
   public List<App> getApps()
   {
     return this.apps;
   }
   
   public PageBean getPage()
   {
     return this.page;
   }
   
   public List<String> getVersions()
   {
     return this.versions;
   }
   
   public void jsonlist()
   {
     try
     {
       if (this.page.getPageSize() == -1)
       {
         List<App> apps = AppHandle.readAll();
         this.page.setTotalRows(apps.size());
         this.page.setPageSize(apps.size());
         response(JSONSuccessString(JSONArrayToString(apps), new Map[] { new MapTool().putObject("page", this.page) }));
       }
       else
       {
         response(JSONSuccessString(JSONArrayToString(readPage()), new Map[] { new MapTool().putObject("page", this.page) }));
       }
     }
     catch (Exception e)
     {
       this.log.error(e.getMessage());
       JsonUtils.writeErrorData(this.response, getText("queryerror"));
     }
   }
   
   public String preedit()
   {
     return "preedit";
   }
   
   private List<App> readPage()
   {
     Result<App> apps = AppHandle.readPage(new Condition((this.page.getCurrentPage() - 1) * this.page.getPageSize(), this.page.getPageSize()));
     this.page.setTotalRows(apps.getTotal());
     return apps.getResult();
   }
   
   public void remove()
   {
     try
     {
       for (String version : this.versions) {
         if (!new AppHandle(this).remove(version)) {
           this.errors.add(version);
         }
       }
       if (this.errors.size() > 0) {
         JsonUtils.writeErrorData(this.response, getText("removeerror") + "version:" + this.errors);
       } else {
         JsonUtils.writeSuccess(this.response);
       }
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorData(this.response, getText("removeerror") + "version:" + this.errors);
       this.log.error(e.getMessage());
     }
   }
   
   public void search()
   {
     try
     {
       JsonUtils.writeSuccessData(this.response, 
         AppHandle.read(this.app.getAppVersion()));
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorMessage(this.response, getText("queryerror"));
     }
   }
   
   public void setApp(App app)
   {
     this.app = app;
   }
   
   public void setApps(List<App> apps)
   {
     this.apps = apps;
   }
   
   public void setPage(PageBean page)
   {
     this.page = page;
   }
   
   public void setVersions(List<String> versions)
   {
     this.versions = versions;
   }
   
   public void validateEdit()
   {
     if (this.app == null)
     {
       addFieldError("versions", getText("versionisnull"));
       return;
     }
     if (this.app.getAppVersion() == null) {
       addFieldError("versions", getText("appversionisnull"));
     } else if (this.app.getAppVersion().length() == 0) {
       addFieldError("versions", getText("appversionerror"));
     }
   }
   
   public void validateRemove()
   {
     if (this.versions == null) {
       addFieldError("versions", getText("versionisnull"));
     } else if (this.versions.size() == 0) {
       addFieldError("versions", getText("versionlengtherror"));
     }
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.admin.action.AppAction
 * JD-Core Version:    0.7.0.1
 */