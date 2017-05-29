 package com.gnamp.struts.action;
 
 import com.gnamp.server.InnerException;
 import com.gnamp.server.NameExistException;
 import com.gnamp.server.handle.DemandProgramHandle;
 import com.gnamp.server.handle.DemandRectContentHandle;
 import com.gnamp.server.handle.DemandRectHandle;
 import com.gnamp.server.handle.DemandTaskHandle;
 import com.gnamp.server.handle.TemplateHandle;
 import com.gnamp.server.handle.TemplateRectHandle;
 import com.gnamp.server.handle.TemplateRectImageHandle;
 import com.gnamp.server.model.DemandProgram;
 import com.gnamp.server.model.DemandTask;
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
 
 public class DemandProgramAction
   extends BaseAction
 {
   DemandTask demandtask = null;
   
   public DemandTask getDemandtask()
   {
     return this.demandtask;
   }
   
   public void setDemandtask(DemandTask demandtask)
   {
     this.demandtask = demandtask;
   }
   
   DemandProgram demandprogram = null;
   
   public DemandProgram getDemandprogram()
   {
     return this.demandprogram;
   }
   
   public void setDemandprogram(DemandProgram demandprogram)
   {
     this.demandprogram = demandprogram;
   }
   
   public List<DemandProgram> getDemandprogramlist()
   {
     return this.demandprogramlist;
   }
   
   public void setDemandprogramlist(List<DemandProgram> demandprogramlist)
   {
     this.demandprogramlist = demandprogramlist;
   }
   
   List<DemandProgram> demandprogramlist = null;
   List<DemandTask> demandtasklist = null;
   
   public List<DemandTask> getDemandtasklist()
   {
     return this.demandtasklist;
   }
   
   public void setDemandtasklist(List<DemandTask> demandtasklist)
   {
     this.demandtasklist = demandtasklist;
   }
   
   DemandTaskHandle demandtaskhandle = null;
   
   public DemandTaskHandle getDemandTaskhandle()
   {
     return this.demandtaskhandle == null ? (this.demandtaskhandle = new DemandTaskHandle(
       this)) : this.demandtaskhandle;
   }
   
   DemandProgramHandle demandprogramhandle = null;
   
   public DemandProgramHandle getDemandProgramandle()
   {
     return this.demandprogramhandle == null ? (this.demandprogramhandle = new DemandProgramHandle(
       this)) : this.demandprogramhandle;
   }
   
   DemandRectHandle demandrecthandle = null;
   
   public DemandRectHandle getDemandRectHandle()
   {
     return this.demandrecthandle == null ? (this.demandrecthandle = new DemandRectHandle(
       this)) : this.demandrecthandle;
   }
   
   DemandRectContentHandle demandrectcontenthandle = null;
   
   public DemandRectContentHandle getDemandRectContentHandle()
   {
     return this.demandrectcontenthandle == null ? (this.demandrectcontenthandle = new DemandRectContentHandle(
       this)) : this.demandrectcontenthandle;
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
   private String sortFiled;
   private String sort;
   
   public TemplateHandle getTemplateHandle()
   {
     return this.templatehandle == null ? (this.templatehandle = new TemplateHandle(
       this)) : this.templatehandle;
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
			 return "desc";
		}
	};
     
     abstract String value();
   }
   
   public String execute()
   {
     return "list";
   }
   
   public void demandlist()
   {
     this.demandprogramlist = new ArrayList();
     
     this.demandprogramlist = getDemandProgramandle()
       .readAll(this.demandprogram.getTaskId());
     
 
     JsonUtils.writeSuccessData(this.servletResponse, this.demandprogramlist);
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
     this.demandtask = ((DemandTask)getDemandTaskhandle().read(this.demandprogram.getTaskId()));
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
         this.demandprogram.setSequenceNum(2147483647);
         if (getDemandProgramandle().create(this.demandprogram))
         {
           LogHelp.userEvent(this.cstmId, this.userName, 
             getText("addprgm"), getText("addprgm") + "[" + 
             this.demandprogram.getProgramName() + "]");
           
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
         
         this.demandprogram.setHeight(template.getHeight());
         this.demandprogram.setWidth(template.getWidth());
         try
         {
           this.demandprogram.setSequenceNum(2147483647);
           if (getDemandProgramandle().create(this.demandprogram))
           {
             if (list != null) {
               for (int i = 0; i < list.size(); i++)
               {
                 Rect rect = (Rect)list.get(i);
                 int old = ((Rect)list.get(i)).getRectId();
                 rect.setTaskId(this.demandprogram.getTaskId());
                 rect.setProgramId(this.demandprogram.getProgramId());
                 getDemandRectHandle().create(rect);
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
                       r.setTaskId(this.demandprogram.getTaskId());
                       r.setProgramId(this.demandprogram.getProgramId());
                       r.setRectId(rect.getRectId());
                       r.setFileId(t.getFileId());
                       r.setPlayTime(t.getPlayTime());
                       r.setScaleStyle(t.getScaleStyle());
                       r.setSequenceNum(t.getSeq());
                       r.setSwapTime(t.getSwapTime());
                       r.setSwapStyle(t.getSwapStyle());
                       
                       getDemandRectContentHandle().create(r);
                     }
                   }
                 }
               }
             }
             this.demandprogram.setMainRectId(mainrect);
             
             getDemandProgramandle().modify(this.demandprogram);
             
 
             LogHelp.userEvent(this.cstmId, this.userName, 
               getText("addprgm"), getText("addprgm") + "[" + 
               this.demandprogram.getProgramName() + "]");
             
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
             getDemandProgramandle().remove(this.demandprogram.getTaskId(), this.demandprogram.getProgramId());
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
         
         this.demandprogram.setHeight(template.getHeight());
         this.demandprogram.setWidth(template.getWidth());
         try
         {
           this.demandprogram.setSequenceNum(2147483647);
           if (getDemandProgramandle().create(this.demandprogram))
           {
             if (list != null) {
               for (int i = 0; i < list.size(); i++)
               {
                 Rect rect = (Rect)list.get(i);
                 int old = ((Rect)list.get(i)).getRectId();
                 rect.setTaskId(this.demandprogram.getTaskId());
                 rect.setProgramId(this.demandprogram.getProgramId());
                 getDemandRectHandle().create(rect);
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
                       r.setTaskId(this.demandprogram.getTaskId());
                       r.setProgramId(this.demandprogram.getProgramId());
                       r.setRectId(rect.getRectId());
                       r.setFileId(t.getFileId());
                       r.setPlayTime(t.getPlayTime());
                       r.setScaleStyle(t.getScaleStyle());
                       r.setSequenceNum(t.getSeq());
                       r.setSwapTime(t.getSwapTime());
                       r.setSwapStyle(t.getSwapStyle());
                       
                       getDemandRectContentHandle().create(r);
                     }
                   }
                 }
               }
             }
             this.demandprogram.setMainRectId(mainrect);
             
             getDemandProgramandle().modify(this.demandprogram);
             
 
             LogHelp.userEvent(this.cstmId, this.userName, 
               getText("addprgm"), getText("addprgm") + "[" + 
               this.demandprogram.getProgramName() + "]");
             
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
             getDemandProgramandle().remove(this.demandprogram.getTaskId(), this.demandprogram.getProgramId());
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
     this.demandprogram = ((DemandProgram)getDemandProgramandle().read(this.demandprogram.getTaskId(), 
       this.demandprogram.getProgramId()));
     
     this.demandtasklist = new ArrayList();
     
     this.demandtasklist = getDemandTaskhandle().readAll();
     
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
     DemandProgram newp = new DemandProgram();
     try
     {
       if (StringUtils.isBlank(this.demandprogram.getProgramName()))
       {
         JsonUtils.writeError(this.servletResponse, getText("name_cannot_null"), "NameCannotNull");
         return;
       }
       DemandProgram old = (DemandProgram)getDemandProgramandle().read(this.demandprogram.getTaskId(), 
         this.demandprogram.getProgramId());
       
       newp.setTaskId(this.purpose);
       newp.setProgramName(this.demandprogram.getProgramName());
       newp.setDescription(this.demandprogram.getDescription());
       if (old != null)
       {
         newp.setHeight(old.getHeight());
         newp.setWidth(old.getWidth());
       }
       newp.setSequenceNum(2147483647);
       if (!getDemandProgramandle().create(newp))
       {
         JsonUtils.writeErrorMessage(this.servletResponse, getText("clonefailed"));
         return;
       }
       if (getDemandProgramandle().copyProgram(this.demandprogram.getTaskId(), this.demandprogram.getProgramId(), this.purpose, newp.getProgramId()))
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
           getDemandProgramandle().remove(newp.getTaskId(), newp.getProgramId());
         }
       }
       catch (Exception localException1) {}
       JsonUtils.writeErrorMessage(this.servletResponse, getText("clonefailed"));
     }
   }
   
   public String ToModify()
   {
     this.demandprogram = ((DemandProgram)getDemandProgramandle().read(this.demandprogram.getTaskId(), 
       this.demandprogram.getProgramId()));
     
     return "edit";
   }
   
   public void Modify()
   {
     try
     {
       if (StringUtils.isBlank(this.demandprogram.getProgramName()))
       {
         JsonUtils.writeError(this.servletResponse, getText("name_cannot_null"), "NameCannotNull");
         return;
       }
       DemandProgram old = (DemandProgram)getDemandProgramandle().read(this.demandprogram.getTaskId(), 
         this.demandprogram.getProgramId());
       
       old.setProgramName(this.demandprogram.getProgramName());
       old.setDescription(this.demandprogram.getDescription());
       
       old.setPlayCount(this.demandprogram.getPlayCount());
       old.setPlayTime(this.demandprogram.getPlayTime());
       old.setPlayStyle(this.demandprogram.getPlayStyle());
       if (getDemandProgramandle().modify(old))
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
       DemandProgram old = (DemandProgram)getDemandProgramandle().read(this.demandprogram.getTaskId(), 
         this.demandprogram.getProgramId());
       
       old.setState(this.demandprogram.getState());
       if (getDemandProgramandle().modify(old))
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
       DemandProgram old = (DemandProgram)getDemandProgramandle().read(this.demandprogram.getTaskId(), 
         this.demandprogram.getProgramId());
       
       old.setMainRectId(this.demandprogram.getMainRectId());
       if (getDemandProgramandle().modify(old)) {
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
       DemandProgram old = (DemandProgram)getDemandProgramandle().read(this.demandprogram.getTaskId(), 
         this.demandprogram.getProgramId());
       int seq = old.getSequenceNum() - 1;
       old.setSequenceNum(seq);
       if (getDemandProgramandle().modify(old))
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
       DemandProgram old = (DemandProgram)getDemandProgramandle().read(this.demandprogram.getTaskId(), 
         this.demandprogram.getProgramId());
       int seq = old.getSequenceNum() + 1;
       old.setSequenceNum(seq);
       if (getDemandProgramandle().modify(old))
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
           String n = ((DemandProgram)getDemandProgramandle().read(this.demandprogram.getTaskId(), 
             Integer.parseInt(this.selectIDs.split(",")[i]))).getProgramName();
           if (getDemandProgramandle().remove(this.demandprogram.getTaskId(), Integer.parseInt(this.selectIDs.split(",")[i]))) {
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
 * Qualified Name:     com.gnamp.struts.action.DemandProgramAction
 * JD-Core Version:    0.7.0.1
 */