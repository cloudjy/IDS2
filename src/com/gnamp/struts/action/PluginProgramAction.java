 package com.gnamp.struts.action;
 
 import com.gnamp.server.InnerException;
 import com.gnamp.server.NameExistException;
 import com.gnamp.server.handle.PluginProgramHandle;
 import com.gnamp.server.handle.PluginRectContentHandle;
 import com.gnamp.server.handle.PluginRectHandle;
 import com.gnamp.server.handle.PluginTaskHandle;
 import com.gnamp.server.handle.TemplateHandle;
 import com.gnamp.server.handle.TemplateRectHandle;
 import com.gnamp.server.handle.TemplateRectImageHandle;
 import com.gnamp.server.model.PluginProgram;
 import com.gnamp.server.model.PluginTask;
 import com.gnamp.server.model.Rect;
 import com.gnamp.server.model.RectImage;
 import com.gnamp.server.model.SystemTemplate;
 import com.gnamp.server.model.SystemTemplateRectImage;
 import com.gnamp.server.model.Template;
 import com.gnamp.server.model.TemplateRectImage;
 import com.gnamp.struts.utils.JsonUtils;
 import com.gnamp.struts.vo.PageBean;
 import java.util.ArrayList;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 
 public class PluginProgramAction
   extends BaseAction
 {
   PluginTask plugintask = null;
   
   public PluginTask getPlugintask()
   {
     return this.plugintask;
   }
   
   public void setPlugintask(PluginTask plugintask)
   {
     this.plugintask = plugintask;
   }
   
   PluginProgram pluginprogram = null;
   
   public PluginProgram getPluginprogram()
   {
     return this.pluginprogram;
   }
   
   public void setPluginprogram(PluginProgram pluginprogram)
   {
     this.pluginprogram = pluginprogram;
   }
   
   public List<PluginProgram> getPluginprogramlist()
   {
     return this.pluginprogramlist;
   }
   
   public void setPluginprogramlist(List<PluginProgram> pluginprogramlist)
   {
     this.pluginprogramlist = pluginprogramlist;
   }
   
   List<PluginProgram> pluginprogramlist = null;
   List<PluginTask> plugintasklist = null;
   
   public List<PluginTask> getPlugintasklist()
   {
     return this.plugintasklist;
   }
   
   public void setPlugintasklist(List<PluginTask> plugintasklist)
   {
     this.plugintasklist = plugintasklist;
   }
   
   PluginTaskHandle plugintaskhandle = null;
   
   public PluginTaskHandle getPluginTaskhandle()
   {
     return this.plugintaskhandle == null ? (this.plugintaskhandle = new PluginTaskHandle(
       this)) : this.plugintaskhandle;
   }
   
   PluginProgramHandle pluginprogramhandle = null;
   
   public PluginProgramHandle getPluginProgramandle()
   {
     return this.pluginprogramhandle == null ? (this.pluginprogramhandle = new PluginProgramHandle(
       this)) : this.pluginprogramhandle;
   }
   
   PluginRectHandle pluginrecthandle = null;
   
   public PluginRectHandle getPluginRectHandle()
   {
     return this.pluginrecthandle == null ? (this.pluginrecthandle = new PluginRectHandle(
       this)) : this.pluginrecthandle;
   }
   
   TemplateRectHandle templaterecthandle = null;
   
   public TemplateRectHandle getTemplateRectHandle()
   {
     return this.templaterecthandle == null ? (this.templaterecthandle = new TemplateRectHandle(
       this)) : this.templaterecthandle;
   }
   
   TemplateRectImageHandle imagerecthandle = null;
   
   public TemplateRectImageHandle getTemplateRectImageHandle()
   {
     return this.imagerecthandle == null ? (this.imagerecthandle = new TemplateRectImageHandle(
       this)) : this.imagerecthandle;
   }
   
   TemplateHandle templatehandle = null;
   
   public TemplateHandle getTemplateHandle()
   {
     return this.templatehandle == null ? (this.templatehandle = new TemplateHandle(
       this)) : this.templatehandle;
   }
   
   PluginRectContentHandle pluginrectcontenthandle = null;
   private String sortFiled;
   private String sort;
   
   public PluginRectContentHandle getPluginRectContentHandle()
   {
     return this.pluginrectcontenthandle == null ? (this.pluginrectcontenthandle = new PluginRectContentHandle(
       this)) : this.pluginrectcontenthandle;
   }
   
   public String getSort()
   {
     return this.sort;
   }
   
   public void setSort(String sort)
   {
     this.sort = sort;
   }
   
   public String getSortFiled()
   {
     return this.sortFiled;
   }
   
   public void setSortFiled(String sortFiled)
   {
     this.sortFiled = sortFiled;
   }
   
   private PageBean page = new PageBean();
   private String oName;
   private String oManaer;
   
   public PageBean getPage()
   {
     return this.page;
   }
   
   public void setPage(PageBean page)
   {
     this.page = page;
   }
   
   public String getoName()
   {
     return this.oName;
   }
   
   public void setOName(String oName)
   {
     this.oName = oName;
   }
   
   public String getoManaer()
   {
     return orderManner.DESC.value().equals(this.oManaer) ? orderManner.DESC
       .value() : orderManner.ASC.value();
   }
   
   public void setOManaer(String oManaer)
   {
     this.oManaer = oManaer;
   }
   
   public static  enum orderManner
   {
     ASC {
		@Override
		String value() {
			return "asc";
		}
	},  DESC {
		@Override
		String value() {
			return "decs";
		}
	};
     
     abstract String value();
   }
   
   public String execute()
   {
     return "list";
   }
   
   public void pluginlist()
   {
     this.pluginprogramlist = new ArrayList();
     
     this.pluginprogramlist = getPluginProgramandle()
       .readAll(this.pluginprogram.getTaskId());
     
 
     JsonUtils.writeSuccessData(this.servletResponse, this.pluginprogramlist);
   }
   
   public List<String> templetcategorys = null;
   
   public List<String> getTempletcategorys()
   {
     return this.templetcategorys;
   }
   
   public void setTempletcategorys(List<String> templetcategorys)
   {
     this.templetcategorys = templetcategorys;
   }
   
   public List<String> systemtempletcategorys = null;
   
   public List<String> getSystemtempletcategorys()
   {
     return this.systemtempletcategorys;
   }
   
   public void setSystemtempletcategorys(List<String> systemtempletcategorys)
   {
     this.systemtempletcategorys = systemtempletcategorys;
   }
   
   public String ToAdd()
   {
     this.templetcategorys = getTemplateHandle().readAllCategorys();
     this.systemtempletcategorys = getTemplateHandle().readAllCategorysSystem();
     this.plugintask = ((PluginTask)getPluginTaskhandle().read(this.pluginprogram.getTaskId()));
     return "add";
   }
   
   public boolean istemplet = false;
   public boolean isbuildin = false;
   
   public boolean isIsbuildin()
   {
     return this.isbuildin;
   }
   
   public void setIsbuildin(boolean isbuildin)
   {
     this.isbuildin = isbuildin;
   }
   
   public boolean isIstemplet()
   {
     return this.istemplet;
   }
   
   public void setIstemplet(boolean istemplet)
   {
     this.istemplet = istemplet;
   }
   
   public int getTempID()
   {
     return this.tempID;
   }
   
   public void setTempID(int tempID)
   {
     this.tempID = tempID;
   }
   
   public int tempID = 0;
   
   public void Add()
   {
     try
     {
       if (!this.istemplet)
       {
         this.pluginprogram.setSequenceNum(2147483647);
         if (getPluginProgramandle().create(this.pluginprogram))
         {
           LogHelp.userEvent(this.cstmId, this.userName, 
             getText("addprgm"), getText("addprgm") + "[" + 
             this.pluginprogram.getProgramName() + "]");
           
           JsonUtils.writeSuccess(this.servletResponse);
         }
         else
         {
           JsonUtils.writeErrorMessage(this.servletResponse, getText("additemfail"));
         }
       }
       else if (!this.isbuildin)
       {
         Template template = getTemplateHandle().read(this.tempID);
         
         List<Rect> list = getTemplateRectHandle().readAll(this.tempID);
         
         int mainrect = 0;
         
         this.pluginprogram.setHeight(template.getHeight());
         this.pluginprogram.setWidth(template.getWidth());
         try
         {
           this.pluginprogram.setSequenceNum(2147483647);
           if (getPluginProgramandle().create(this.pluginprogram))
           {
             if (list != null) {
               for (int i = 0; i < list.size(); i++)
               {
                 Rect rect = (Rect)list.get(i);
                 int old = ((Rect)list.get(i)).getRectId();
                 rect.setTaskId(this.pluginprogram.getTaskId());
                 rect.setProgramId(this.pluginprogram.getProgramId());
                 getPluginRectHandle().create(rect);
                 if (template.getMainRect() == old) {
                   mainrect = rect.getRectId();
                 }
                 if (rect.getRecType() == 1)
                 {
                   List<TemplateRectImage> rectimages = 
                     getTemplateRectImageHandle().readAll(this.tempID, old);
                   if (rectimages != null) {
                     for (int j = 0; j < rectimages.size(); j++)
                     {
                       TemplateRectImage t = (TemplateRectImage)rectimages.get(j);
                       RectImage r = new RectImage();
                       r.setTaskId(this.pluginprogram.getTaskId());
                       r.setProgramId(this.pluginprogram.getProgramId());
                       r.setRectId(rect.getRectId());
                       r.setFileId(t.getFileId());
                       r.setPlayTime(t.getPlayTime());
                       r.setScaleStyle(t.getScaleStyle());
                       r.setSequenceNum(t.getSeq());
                       r.setSwapTime(t.getSwapTime());
                       r.setSwapStyle(t.getSwapStyle());
                       
                       getPluginRectContentHandle().create(r);
                     }
                   }
                 }
               }
             }
             this.pluginprogram.setMainRectId(mainrect);
             
             getPluginProgramandle().modify(this.pluginprogram);
             
 
             LogHelp.userEvent(this.cstmId, this.userName, 
               getText("addprgm"), getText("addprgm") + "[" + 
               this.pluginprogram.getProgramName() + "]");
             
             JsonUtils.writeSuccess(this.servletResponse);
           }
           else
           {
             JsonUtils.writeErrorMessage(this.servletResponse, getText("additemfail"));
           }
         }
         catch (Exception e)
         {
           try
           {
             getPluginProgramandle().remove(this.pluginprogram.getTaskId(), this.pluginprogram.getProgramId());
           }
           catch (Exception localException1) {}
           JsonUtils.writeErrorMessage(this.servletResponse, getText("additemfail"));
         }
       }
       else
       {
         SystemTemplate template = getTemplateHandle().readSystem(this.tempID);
         
         List<Rect> list = getTemplateRectHandle().readAllSystem(this.tempID);
         
         int mainrect = 0;
         
         this.pluginprogram.setHeight(template.getHeight());
         this.pluginprogram.setWidth(template.getWidth());
         try
         {
           this.pluginprogram.setSequenceNum(2147483647);
           if (getPluginProgramandle().create(this.pluginprogram))
           {
             if (list != null) {
               for (int i = 0; i < list.size(); i++)
               {
                 Rect rect = (Rect)list.get(i);
                 int old = ((Rect)list.get(i)).getRectId();
                 rect.setTaskId(this.pluginprogram.getTaskId());
                 rect.setProgramId(this.pluginprogram.getProgramId());
                 getPluginRectHandle().create(rect);
                 if (template.getMainRect() == old) {
                   mainrect = rect.getRectId();
                 }
                 if (rect.getRecType() == 1)
                 {
                   List<SystemTemplateRectImage> rectimages = 
                     getTemplateRectImageHandle().readAllSystem(this.tempID, old);
                   if (rectimages != null) {
                     for (int j = 0; j < rectimages.size(); j++)
                     {
                       SystemTemplateRectImage t = (SystemTemplateRectImage)rectimages.get(j);
                       RectImage r = new RectImage();
                       r.setTaskId(this.pluginprogram.getTaskId());
                       r.setProgramId(this.pluginprogram.getProgramId());
                       r.setRectId(rect.getRectId());
                       r.setFileId(t.getFileId());
                       r.setPlayTime(t.getPlayTime());
                       r.setScaleStyle(t.getScaleStyle());
                       r.setSequenceNum(t.getSeq());
                       r.setSwapTime(t.getSwapTime());
                       r.setSwapStyle(t.getSwapStyle());
                       
                       getPluginRectContentHandle().create(r);
                     }
                   }
                 }
               }
             }
             this.pluginprogram.setMainRectId(mainrect);
             
             getPluginProgramandle().modify(this.pluginprogram);
             
 
             LogHelp.userEvent(this.cstmId, this.userName, 
               getText("addprgm"), getText("addprgm") + "[" + 
               this.pluginprogram.getProgramName() + "]");
             
             JsonUtils.writeSuccess(this.servletResponse);
           }
           else
           {
             JsonUtils.writeErrorMessage(this.servletResponse, getText("additemfail"));
           }
         }
         catch (Exception e)
         {
           try
           {
             getPluginProgramandle().remove(this.pluginprogram.getTaskId(), this.pluginprogram.getProgramId());
           }
           catch (Exception localException2) {}
           JsonUtils.writeErrorMessage(this.servletResponse, getText("additemfail"));
         }
       }
      
       return;
     }
     catch (NameExistException e)
     {
       JsonUtils.writeError(this.servletResponse, getText("name_existed"), "NameExist");
     }
     catch (InnerException e)
     {
    	 String innerType = e.getInnerType();
       if (innerType.equals("name cannot null")) {
         JsonUtils.writeError(this.servletResponse, 
           getText("name_cannot_null"), "NameCannotNull");
       } else {
         JsonUtils.writeErrorMessage(this.servletResponse, getText("additemfail"));
       }
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorMessage(this.servletResponse, getText("additemfail"));
     }
   }
   
   public String ToClone()
   {
     this.pluginprogram = ((PluginProgram)getPluginProgramandle().read(this.pluginprogram.getTaskId(), 
       this.pluginprogram.getProgramId()));
     
     this.plugintasklist = new ArrayList();
     
     this.plugintasklist = getPluginTaskhandle().readAll();
     
     return "clone";
   }
   
   public int purpose = 0;
   
   public int getPurpose()
   {
     return this.purpose;
   }
   
   public void setPurpose(int purpose)
   {
     this.purpose = purpose;
   }
   
   public void Clone()
   {
     PluginProgram newp = new PluginProgram();
     try
     {
       if (StringUtils.isBlank(this.pluginprogram.getProgramName()))
       {
         JsonUtils.writeError(this.servletResponse, getText("name_cannot_null"), "NameCannotNull");
         return;
       }
       PluginProgram old = (PluginProgram)getPluginProgramandle().read(this.pluginprogram.getTaskId(), 
         this.pluginprogram.getProgramId());
       
       newp.setTaskId(this.purpose);
       newp.setProgramName(this.pluginprogram.getProgramName());
       newp.setDescription(this.pluginprogram.getDescription());
       if (old != null)
       {
         newp.setHeight(old.getHeight());
         newp.setWidth(old.getWidth());
       }
       newp.setSequenceNum(2147483647);
       if (!getPluginProgramandle().create(newp))
       {
         JsonUtils.writeErrorMessage(this.servletResponse, getText("clonefailed"));
         return;
       }
       if (getPluginProgramandle().copyProgram(this.pluginprogram.getTaskId(), this.pluginprogram.getProgramId(), this.purpose, newp.getProgramId()))
       {
         LogHelp.userEvent(this.cstmId, this.userName, getText("cloneprgm"), getText("cloneprgm") + "[" + newp.getProgramName() + "]");
         
         JsonUtils.writeSuccess(this.servletResponse);
       }
       else
       {
         JsonUtils.writeErrorMessage(this.servletResponse, getText("clonefailed"));
       }
     }
     catch (NameExistException e)
     {
       JsonUtils.writeError(this.servletResponse, getText("name_existed"), "NameExist");
     }
     catch (InnerException e)
     {
       String innerType = e.getInnerType();
       if (innerType.equals("name cannot null")) {
         JsonUtils.writeError(this.servletResponse, 
           getText("name_cannot_null"), "NameCannotNull");
       } else {
         JsonUtils.writeErrorMessage(this.servletResponse, getText("clonefailed"));
       }
     }
     catch (Exception e)
     {
       try
       {
         if (newp.getProgramId() != 0) {
           getPluginProgramandle().remove(newp.getTaskId(), newp.getProgramId());
         }
       }
       catch (Exception localException1) {}
       JsonUtils.writeErrorMessage(this.servletResponse, getText("clonefailed"));
     }
   }
   
   public String ToModify()
   {
     this.pluginprogram = ((PluginProgram)getPluginProgramandle().read(this.pluginprogram.getTaskId(), 
       this.pluginprogram.getProgramId()));
     
     return "edit";
   }
   
   public void Modify()
   {
     try
     {
       if (StringUtils.isBlank(this.pluginprogram.getProgramName()))
       {
         JsonUtils.writeError(this.servletResponse, getText("name_cannot_null"), "NameCannotNull");
         return;
       }
       PluginProgram old = (PluginProgram)getPluginProgramandle().read(this.pluginprogram.getTaskId(), 
         this.pluginprogram.getProgramId());
       
       old.setProgramName(this.pluginprogram.getProgramName());
       old.setDescription(this.pluginprogram.getDescription());
       if (getPluginProgramandle().modify(old))
       {
         LogHelp.userEvent(this.cstmId, this.userName, getText("editprgm"), getText("editprgm") + "[" + old.getProgramName() + "]");
         
         JsonUtils.writeSuccess(this.servletResponse);
       }
       else
       {
         JsonUtils.writeErrorMessage(this.servletResponse, getText("edititemfail"));
       }
     }
     catch (NameExistException e)
     {
       JsonUtils.writeError(this.servletResponse, getText("name_existed"), "NameExist");
     }
     catch (InnerException e)
     {
       String innerType = e.getInnerType();
       if (innerType.equals("name cannot null")) {
         JsonUtils.writeError(this.servletResponse, 
           getText("name_cannot_null"), "NameCannotNull");
       } else {
         JsonUtils.writeErrorMessage(this.servletResponse, getText("edititemfail"));
       }
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorMessage(this.servletResponse, getText("edititemfail"));
     }
   }
   
   public String selectIDs = "";
   
   public String getSelectIDs()
   {
     return this.selectIDs;
   }
   
   public void setSelectIDs(String selectIDs)
   {
     this.selectIDs = selectIDs;
   }
   
   public void changestate()
   {
     try
     {
       PluginProgram old = (PluginProgram)getPluginProgramandle().read(this.pluginprogram.getTaskId(), 
         this.pluginprogram.getProgramId());
       
       old.setState(this.pluginprogram.getState());
       if (getPluginProgramandle().modify(old))
       {
         LogHelp.userEvent(this.cstmId, this.userName, getText("editprgm"), getText("editprgm") + "[" + old.getProgramName() + "]");
         
         JsonUtils.writeSuccess(this.servletResponse);
       }
       else
       {
         JsonUtils.writeError(this.servletResponse);
       }
     }
     catch (Exception e)
     {
       JsonUtils.writeError(this.servletResponse);
     }
   }
   
   public void changemainrect()
   {
     try
     {
       PluginProgram old = (PluginProgram)getPluginProgramandle().read(this.pluginprogram.getTaskId(), 
         this.pluginprogram.getProgramId());
       old.setMainRectId(this.pluginprogram.getMainRectId());
       if (getPluginProgramandle().modify(old)) {
         JsonUtils.writeSuccessData(this.servletResponse, String.valueOf(old.getMainRectId()));
       } else {
         JsonUtils.writeError(this.servletResponse);
       }
     }
     catch (Exception e)
     {
       JsonUtils.writeError(this.servletResponse);
     }
   }
   
   public void up()
   {
     try
     {
       PluginProgram old = (PluginProgram)getPluginProgramandle().read(this.pluginprogram.getTaskId(), 
         this.pluginprogram.getProgramId());
       int seq = old.getSequenceNum() - 1;
       old.setSequenceNum(seq);
       if (getPluginProgramandle().modify(old))
       {
         LogHelp.userEvent(this.cstmId, this.userName, getText("editprgm"), getText("editprgm") + "[" + old.getProgramName() + "]");
         
         JsonUtils.writeSuccess(this.servletResponse);
       }
       else
       {
         JsonUtils.writeErrorData(this.servletResponse, getText("edititemfail"));
       }
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorData(this.servletResponse, getText("edititemfail"));
     }
   }
   
   public void down()
   {
     try
     {
       PluginProgram old = (PluginProgram)getPluginProgramandle().read(this.pluginprogram.getTaskId(), 
         this.pluginprogram.getProgramId());
       int seq = old.getSequenceNum() + 1;
       old.setSequenceNum(seq);
       if (getPluginProgramandle().modify(old))
       {
         LogHelp.userEvent(this.cstmId, this.userName, getText("editprgm"), getText("editprgm") + "[" + old.getProgramName() + "]");
         
         JsonUtils.writeSuccess(this.servletResponse);
       }
       else
       {
         JsonUtils.writeErrorData(this.servletResponse, getText("edititemfail"));
       }
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorData(this.servletResponse, getText("edititemfail"));
     }
   }
   
   public void delete()
   {
     if ((this.selectIDs != null) && (this.selectIDs.length() > 0)) {
       for (int i = 0; i < this.selectIDs.split(",").length; i++) {
         try
         {
           String n = ((PluginProgram)getPluginProgramandle().read(this.pluginprogram.getTaskId(), 
             Integer.parseInt(this.selectIDs.split(",")[i]))).getProgramName();
           if (getPluginProgramandle().remove(this.pluginprogram.getTaskId(), Integer.parseInt(this.selectIDs.split(",")[i]))) {
             LogHelp.userEvent(this.cstmId, this.userName, getText("deleteprgm"), getText("deleteprgm") + "[" + n + "]");
           }
         }
         catch (Exception e)
         {
           JsonUtils.writeErrorData(this.servletResponse, getText("deletefailed"));
         }
       }
     }
     JsonUtils.writeSuccess(this.servletResponse);
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.PluginProgramAction
 * JD-Core Version:    0.7.0.1
 */