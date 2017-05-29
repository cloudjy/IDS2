 package com.gnamp.struts.action;
 
 import com.gnamp.server.InnerException;
 import com.gnamp.server.NameExistException;
 import com.gnamp.server.handle.LoopProgramHandle;
 import com.gnamp.server.handle.LoopRectContentHandle;
 import com.gnamp.server.handle.LoopRectHandle;
 import com.gnamp.server.handle.LoopStrategyHandle;
 import com.gnamp.server.handle.LoopStrategyProgramHandle;
 import com.gnamp.server.handle.LoopTaskHandle;
 import com.gnamp.server.handle.TemplateHandle;
 import com.gnamp.server.handle.TemplateRectHandle;
 import com.gnamp.server.handle.TemplateRectImageHandle;
 import com.gnamp.server.model.LoopProgram;
 import com.gnamp.server.model.LoopStrategyProgram;
 import com.gnamp.server.model.LoopTask;
 import com.gnamp.server.model.Rect;
 import com.gnamp.server.model.RectImage;
 import com.gnamp.server.model.SystemTemplate;
 import com.gnamp.server.model.SystemTemplateRectImage;
 import com.gnamp.server.model.Template;
 import com.gnamp.server.model.TemplateRectImage;
 import com.gnamp.struts.utils.JsonUtils;
 import com.gnamp.struts.utils.MapTool;
 import com.gnamp.struts.vo.PageBean;
 import java.util.ArrayList;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 
 public class LoopProgramAction
   extends JSONAction
 {
   LoopTask looptask = null;
   
   public LoopTask getLooptask()
   {
     return this.looptask;
   }
   
   public void setLooptask(LoopTask looptask)
   {
     this.looptask = looptask;
   }
   
   LoopProgram loopprogram = null;
   
   public LoopProgram getLoopprogram()
   {
     return this.loopprogram;
   }
   
   public void setLoopprogram(LoopProgram loopprogram)
   {
     this.loopprogram = loopprogram;
   }
   
   public List<LoopProgram> getLoopprogramlist()
   {
     return this.loopprogramlist;
   }
   
   public void setLoopprogramlist(List<LoopProgram> loopprogramlist)
   {
     this.loopprogramlist = loopprogramlist;
   }
   
   List<LoopProgram> loopprogramlist = null;
   List<LoopTask> looptasklist = null;
   
   public List<LoopTask> getLooptasklist()
   {
     return this.looptasklist;
   }
   
   public void setLooptasklist(List<LoopTask> looptasklist)
   {
     this.looptasklist = looptasklist;
   }
   
   LoopTaskHandle looptaskhandle = null;
   
   public LoopTaskHandle getLoopTaskhandle()
   {
     return this.looptaskhandle == null ? (this.looptaskhandle = new LoopTaskHandle(
       this)) : this.looptaskhandle;
   }
   
   LoopRectHandle looprecthandle = null;
   
   public LoopRectHandle getLoopRectHandle()
   {
     return this.looprecthandle == null ? (this.looprecthandle = new LoopRectHandle(
       this)) : this.looprecthandle;
   }
   
   LoopProgramHandle loopprogramhandle = null;
   
   public LoopProgramHandle getLoopProgramandle()
   {
     return this.loopprogramhandle == null ? (this.loopprogramhandle = new LoopProgramHandle(
       this)) : this.loopprogramhandle;
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
   
   LoopRectContentHandle looprectcontenthandle = null;
   
   public LoopRectContentHandle getLoopRectContentHandle()
   {
     return this.looprectcontenthandle == null ? (this.looprectcontenthandle = new LoopRectContentHandle(
       this)) : this.looprectcontenthandle;
   }
   
   private PageBean page = new PageBean();
   
   public PageBean getPage()
   {
     return this.page;
   }
   
   public void setPage(PageBean page)
   {
     this.page = page;
   }
   
   public String execute()
   {
     return "list";
   }
   
   public void looplist()
   {
     this.loopprogramlist = new ArrayList();
     try
     {
       this.loopprogramlist = getLoopProgramandle().readAll(this.loopprogram.getTaskId());
       
       this.page.setTotalRows(this.loopprogramlist.size());
       response(JSONSuccessString(JSONArrayToString(this.loopprogramlist), 
         new MapTool().putObject("page", this.page)));
     }
     catch (Exception e)
     {
       e.printStackTrace();
       response(JSONErrorString(e.getMessage()));
     }
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
     
     this.looptask = ((LoopTask)getLoopTaskhandle().read(this.loopprogram.getTaskId()));
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
         this.loopprogram.setSequenceNum(2147483647);
         if (getLoopProgramandle().create(this.loopprogram))
         {
           LogHelp.userEvent(this.cstmId, this.userName, 
             getText("addprgm"), getText("addprgm") + "[" + 
             this.loopprogram.getProgramName() + "]");
           
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
         
         this.loopprogram.setHeight(template.getHeight());
         this.loopprogram.setWidth(template.getWidth());
         try
         {
           this.loopprogram.setSequenceNum(2147483647);
           if (getLoopProgramandle().create(this.loopprogram))
           {
             if (list != null) {
               for (int i = 0; i < list.size(); i++)
               {
                 Rect rect = (Rect)list.get(i);
                 int old = ((Rect)list.get(i)).getRectId();
                 rect.setTaskId(this.loopprogram.getTaskId());
                 rect.setProgramId(this.loopprogram.getProgramId());
                 getLoopRectHandle().create(rect);
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
                       r.setTaskId(this.loopprogram.getTaskId());
                       r.setProgramId(this.loopprogram.getProgramId());
                       r.setRectId(rect.getRectId());
                       r.setFileId(t.getFileId());
                       r.setPlayTime(t.getPlayTime());
                       r.setScaleStyle(t.getScaleStyle());
                       r.setSequenceNum(t.getSeq());
                       r.setSwapTime(t.getSwapTime());
                       r.setSwapStyle(t.getSwapStyle());
                       
                       getLoopRectContentHandle().create(r);
                     }
                   }
                 }
               }
             }
             this.loopprogram.setMainRectId(mainrect);
             
             getLoopProgramandle().modify(this.loopprogram);
             
 
             LogHelp.userEvent(this.cstmId, this.userName, 
               getText("addprgm"), getText("addprgm") + "[" + 
               this.loopprogram.getProgramName() + "]");
             
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
             getLoopProgramandle().remove(this.loopprogram.getTaskId(), this.loopprogram.getProgramId());
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
         
         this.loopprogram.setHeight(template.getHeight());
         this.loopprogram.setWidth(template.getWidth());
         try
         {
           this.loopprogram.setSequenceNum(2147483647);
           if (getLoopProgramandle().create(this.loopprogram))
           {
             if (list != null) {
               for (int i = 0; i < list.size(); i++)
               {
                 Rect rect = (Rect)list.get(i);
                 int old = ((Rect)list.get(i)).getRectId();
                 rect.setTaskId(this.loopprogram.getTaskId());
                 rect.setProgramId(this.loopprogram.getProgramId());
                 getLoopRectHandle().create(rect);
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
                       r.setTaskId(this.loopprogram.getTaskId());
                       r.setProgramId(this.loopprogram.getProgramId());
                       r.setRectId(rect.getRectId());
                       r.setFileId(t.getFileId());
                       r.setPlayTime(t.getPlayTime());
                       r.setScaleStyle(t.getScaleStyle());
                       r.setSequenceNum(t.getSeq());
                       r.setSwapTime(t.getSwapTime());
                       r.setSwapStyle(t.getSwapStyle());
                       
                       getLoopRectContentHandle().create(r);
                     }
                   }
                 }
               }
             }
             this.loopprogram.setMainRectId(mainrect);
             
             getLoopProgramandle().modify(this.loopprogram);
             
 
             LogHelp.userEvent(this.cstmId, this.userName, 
               getText("addprgm"), getText("addprgm") + "[" + 
               this.loopprogram.getProgramName() + "]");
             
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
             getLoopProgramandle().remove(this.loopprogram.getTaskId(), this.loopprogram.getProgramId());
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
    	  String  innerType = e.getInnerType();
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
     this.loopprogram = ((LoopProgram)getLoopProgramandle().read(this.loopprogram.getTaskId(), 
       this.loopprogram.getProgramId()));
     
     this.looptasklist = new ArrayList();
     
     this.looptasklist = getLoopTaskhandle().readAll();
     
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
     LoopProgram newp = new LoopProgram();
     try
     {
       if (StringUtils.isBlank(this.loopprogram.getProgramName()))
       {
         JsonUtils.writeError(this.servletResponse, getText("name_cannot_null"), "NameCannotNull");
         return;
       }
       LoopProgram old = (LoopProgram)getLoopProgramandle().read(this.loopprogram.getTaskId(), 
         this.loopprogram.getProgramId());
       
       newp.setTaskId(this.purpose);
       newp.setProgramName(this.loopprogram.getProgramName());
       newp.setDescription(this.loopprogram.getDescription());
       if (old != null)
       {
         newp.setHeight(old.getHeight());
         newp.setWidth(old.getWidth());
       }
       newp.setSequenceNum(2147483647);
       if (!getLoopProgramandle().create(newp))
       {
         JsonUtils.writeErrorMessage(this.servletResponse, getText("clonefailed"));
         return;
       }
       if (getLoopProgramandle().copyProgram(this.loopprogram.getTaskId(), this.loopprogram.getProgramId(), this.purpose, newp.getProgramId()))
       {
         LogHelp.userEvent(this.cstmId, this.userName, 
           getText("cloneprgm"), 
           getText("cloneprgm") + "[" + newp.getProgramName() + "]");
         
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
           getLoopProgramandle().remove(newp.getTaskId(), newp.getProgramId());
         }
       }
       catch (Exception localException1) {}
       JsonUtils.writeErrorMessage(this.servletResponse, getText("clonefailed"));
     }
   }
   
   public String ToModify()
   {
     this.loopprogram = ((LoopProgram)getLoopProgramandle().read(this.loopprogram.getTaskId(), 
       this.loopprogram.getProgramId()));
     
     return "edit";
   }
   
   public void Modify()
   {
     try
     {
       if (StringUtils.isBlank(this.loopprogram.getProgramName()))
       {
         JsonUtils.writeError(this.servletResponse, getText("name_cannot_null"), "NameCannotNull");
         return;
       }
       LoopProgram old = (LoopProgram)getLoopProgramandle().read(
         this.loopprogram.getTaskId(), this.loopprogram.getProgramId());
       
       old.setProgramName(this.loopprogram.getProgramName());
       old.setDescription(this.loopprogram.getDescription());
       if (getLoopProgramandle().modify(old))
       {
         LogHelp.userEvent(this.cstmId, this.userName, 
           getText("editprgm"), 
           getText("editprgm") + "[" + old.getProgramName() + "]");
         
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
       LoopProgram old = (LoopProgram)getLoopProgramandle().read(this.loopprogram.getTaskId(), 
         this.loopprogram.getProgramId());
       
       old.setState(this.loopprogram.getState());
       if (getLoopProgramandle().modify(old))
       {
         LogHelp.userEvent(this.cstmId, this.userName, getText("editprgm"), 
           getText("editprgm") + "[" + old.getProgramName() + "]");
         
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
       LoopProgram old = (LoopProgram)getLoopProgramandle().read(this.loopprogram.getTaskId(), 
         this.loopprogram.getProgramId());
       
       old.setMainRectId(this.loopprogram.getMainRectId());
       if (getLoopProgramandle().modify(old)) {
         JsonUtils.writeSuccessData(this.servletResponse, 
           String.valueOf(old.getMainRectId()));
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
       LoopProgram old = (LoopProgram)getLoopProgramandle().read(this.loopprogram.getTaskId(), 
         this.loopprogram.getProgramId());
       
       int seq = old.getSequenceNum() - 1;
       old.setSequenceNum(seq);
       if (getLoopProgramandle().modify(old))
       {
         LogHelp.userEvent(this.cstmId, this.userName, getText("editprgm"), 
           getText("editprgm") + "[" + old.getProgramName() + "]");
         
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
       LoopProgram old = (LoopProgram)getLoopProgramandle().read(this.loopprogram.getTaskId(), 
         this.loopprogram.getProgramId());
       
       int seq = old.getSequenceNum() + 1;
       old.setSequenceNum(seq);
       if (getLoopProgramandle().modify(old))
       {
         LogHelp.userEvent(this.cstmId, this.userName, getText("editprgm"), 
           getText("editprgm") + "[" + old.getProgramName() + "]");
         
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
           String n = ((LoopProgram)getLoopProgramandle().read(
             this.loopprogram.getTaskId(), 
             Integer.parseInt(this.selectIDs.split(",")[i])))
             .getProgramName();
           if (getLoopProgramandle().remove(this.loopprogram.getTaskId(), Integer.parseInt(this.selectIDs.split(",")[i]))) {
             LogHelp.userEvent(this.cstmId, this.userName, 
               getText("deleteprgm"), getText("deleteprgm") + 
               "[" + n + "]");
           }
         }
         catch (Exception e)
         {
           JsonUtils.writeErrorData(this.servletResponse, 
             getText("deletefailed"));
         }
       }
     }
     JsonUtils.writeSuccess(this.servletResponse);
   }
   
   LoopStrategyHandle loopstrategyhandle = null;
   
   public LoopStrategyHandle getLoopStrategyHandle()
   {
     return this.loopstrategyhandle == null ? (this.loopstrategyhandle = new LoopStrategyHandle(
       this)) : this.loopstrategyhandle;
   }
   
   LoopStrategyProgramHandle loopstrategyprogramhandle = null;
   
   public LoopStrategyProgramHandle getLoopStrategyProgramHandle()
   {
     return this.loopstrategyprogramhandle == null ? (this.loopstrategyprogramhandle = new LoopStrategyProgramHandle(
       this)) : this.loopstrategyprogramhandle;
   }
   
   public void clear()
   {
     if ((this.selectIDs != null) && (this.selectIDs.length() > 0)) {
       for (int i = 0; i < this.selectIDs.split(",").length; i++) {
         try
         {
           List<LoopStrategyProgram> list = getLoopStrategyProgramHandle().readAllWithProgramID(this.loopprogram.getTaskId(), 
             Integer.parseInt(this.selectIDs.split(",")[i]));
           if ((list != null) && (list.size() > 0)) {
             for (int j = 0; j < list.size(); j++) {
               getLoopStrategyProgramHandle().clear(this.loopprogram.getTaskId(), 
                 ((LoopStrategyProgram)list.get(j)).getStrategyId(), ((LoopStrategyProgram)list.get(j)).getSerialId(), 
                 Integer.parseInt(this.selectIDs.split(",")[i]));
             }
           }
         }
         catch (Exception e)
         {
           e.printStackTrace();
           JsonUtils.writeErrorData(this.servletResponse, 
             getText("clearfailed"));
         }
       }
     }
     JsonUtils.writeSuccess(this.servletResponse);
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.LoopProgramAction
 * JD-Core Version:    0.7.0.1
 */