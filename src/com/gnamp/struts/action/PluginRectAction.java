 package com.gnamp.struts.action;
 
 import com.gnamp.server.Storage;
 import com.gnamp.server.handle.CategoryHandle;
 import com.gnamp.server.handle.CombinFileHandle;
 import com.gnamp.server.handle.FileHandle;
 import com.gnamp.server.handle.ImageUtil;
 import com.gnamp.server.handle.PluginProgramHandle;
 import com.gnamp.server.handle.PluginProgramMusicHandle;
 import com.gnamp.server.handle.PluginRectContentHandle;
 import com.gnamp.server.handle.PluginRectHandle;
 import com.gnamp.server.handle.PluginTaskHandle;
 import com.gnamp.server.handle.SourceCategoryHandle;
 import com.gnamp.server.handle.SourceTagHandle;
 import com.gnamp.server.handle.SystemSourceHandle;
 import com.gnamp.server.model.AvInRect;
 import com.gnamp.server.model.CameraRect;
 import com.gnamp.server.model.Category;
 import com.gnamp.server.model.CombinFile;
 import com.gnamp.server.model.ContentFlash;
 import com.gnamp.server.model.DateRect;
 import com.gnamp.server.model.DeviceRect;
 import com.gnamp.server.model.FlashRect;
 import com.gnamp.server.model.FontRect;
 import com.gnamp.server.model.HybridRect;
 import com.gnamp.server.model.ImageRect;
 import com.gnamp.server.model.LogoRect;
 import com.gnamp.server.model.MLFlipTRect;
 import com.gnamp.server.model.MLScrollTRect;
 import com.gnamp.server.model.MLStaticTRect;
 import com.gnamp.server.model.PluginProgram;
 import com.gnamp.server.model.ProgramMusic;
 import com.gnamp.server.model.Rect;
 import com.gnamp.server.model.RectContent;
 import com.gnamp.server.model.RectFlash;
 import com.gnamp.server.model.RectFlashSource;
 import com.gnamp.server.model.RectImage;
 import com.gnamp.server.model.RectImageSource;
 import com.gnamp.server.model.RectSource;
 import com.gnamp.server.model.RectSystemSource;
 import com.gnamp.server.model.RectText;
 import com.gnamp.server.model.RectVideo;
 import com.gnamp.server.model.RectVideoSource;
 import com.gnamp.server.model.RectWebUrl;
 import com.gnamp.server.model.SLFlipTRect;
 import com.gnamp.server.model.SLScrollTRect;
 import com.gnamp.server.model.SLStaticTRect;
 import com.gnamp.server.model.Source;
 import com.gnamp.server.model.SourceCategory;
 import com.gnamp.server.model.SystemSource;
 import com.gnamp.server.model.TextRect;
 import com.gnamp.server.model.TimeRect;
 import com.gnamp.server.model.VideoRect;
 import com.gnamp.server.model.WeatherLogoRect;
 import com.gnamp.server.model.WeatherTemperatureRect;
 import com.gnamp.server.model.WeatherTextRect;
 import com.gnamp.server.model.WeatherTitleRect;
 import com.gnamp.server.model.WebRect;
 import com.gnamp.server.model.WeekRect;
 import com.gnamp.struts.filter.Context;
 import com.gnamp.struts.utils.JsonUtils;
 import com.gnamp.struts.utils.MapTool;
 import com.gnamp.struts.vo.CategoryXMLConfig;
 import com.gnamp.struts.vo.PageBean;
 import common.Logger;
 import java.awt.Color;
 import java.awt.Dimension;
 import java.awt.Font;
 import java.awt.FontMetrics;
 import java.awt.Graphics;
 import java.awt.image.BufferedImage;
 import java.io.FileInputStream;
 import java.io.IOException;
 import java.io.OutputStream;
 import java.io.UnsupportedEncodingException;
 import java.net.URLEncoder;
 import java.text.DecimalFormat;
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Calendar;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.imageio.ImageIO;
 import javax.servlet.ServletOutputStream;
 import javax.servlet.http.HttpServletResponse;
 
 public class PluginRectAction
   extends JSONAction
 {
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
   
   SourceTagHandle sourcehandle = null;
   
   public SourceTagHandle getSourceHandle()
   {
     return this.sourcehandle == null ? (this.sourcehandle = new SourceTagHandle(this)) : 
       this.sourcehandle;
   }
   
   PluginRectContentHandle pluginrectcontenthandle = null;
   
   public PluginRectContentHandle getPluginRectContentHandle()
   {
     return this.pluginrectcontenthandle == null ? (this.pluginrectcontenthandle = new PluginRectContentHandle(
       this)) : this.pluginrectcontenthandle;
   }
   
   PluginProgramMusicHandle pluginprogrammusichandle = null;
   
   public PluginProgramMusicHandle getPluginProgramMusicHandle()
   {
     return this.pluginprogrammusichandle == null ? (this.pluginprogrammusichandle = new PluginProgramMusicHandle(
       this)) : this.pluginprogrammusichandle;
   }
   
   FileHandle filehandle = null;
   
   public FileHandle getFileHandle()
   {
     return this.filehandle == null ? (this.filehandle = new FileHandle(this)) : 
       this.filehandle;
   }
   
   CombinFileHandle combinfilehandle = null;
   
   public CombinFileHandle getCombinFileHandle()
   {
     return this.combinfilehandle == null ? (this.combinfilehandle = new CombinFileHandle(this)) : 
       this.combinfilehandle;
   }
   
   public int taskID = 0;
   
   public int getTaskID()
   {
     return this.taskID;
   }
   
   public void setTaskID(int taskID)
   {
     this.taskID = taskID;
   }
   
   public int getProgramID()
   {
     return this.programID;
   }
   
   public void setProgramID(int programID)
   {
     this.programID = programID;
   }
   
   public int programID = 0;
   Rect[] allrects = null;
   
   public void GetTodoProgramLayout()
   {
     String xml = "";
     try
     {
       List<Rect> list = getPluginRectHandle().readAll(this.taskID, this.programID);
       if (list != null)
       {
         this.allrects = new Rect[list.size()];
         list.toArray(this.allrects);
       }
       PluginProgram pluginprogram = (PluginProgram)getPluginProgramandle().read(this.taskID, 
         this.programID);
       
       Program layout = new Program();
       
       layout.setProgramId(pluginprogram.getProgramId());
       layout.setProgramName(pluginprogram.getProgramName());
       layout.setDesciption(pluginprogram.getDescription());
       layout.setTaskId(pluginprogram.getTaskId());
       
       layout.setH(pluginprogram.getHeight());
       layout.setW(pluginprogram.getWidth());
       layout.setMainRectId(pluginprogram.getMainRectId());
       layout.setRectWnds(this.allrects);
       
       xml = LayoutToXml.transform(layout, "utf-8");
     }
     catch (Exception e)
     {
       JsonUtils.writeError(this.servletResponse);
     }
     JsonUtils.writeSuccessData(this.servletResponse, xml);
   }
   
   public String xmldata = "";
   
   public String getXmldata()
   {
     return this.xmldata;
   }
   
   public void setXmldata(String xmldata)
   {
     this.xmldata = xmldata;
   }
   
   Rect[] allmainrects = null;
   List<Rect> mainrectlist = new ArrayList();
   
   public List<Rect> getNewlist()
   {
     return this.mainrectlist;
   }
   
   public void setNewlist(List<Rect> mainrectlist)
   {
     this.mainrectlist = mainrectlist;
   }
   
   public void getAllMainrects()
   {
     List<Rect> list = getPluginRectHandle().readAll(this.taskID, this.programID);
     if (list != null) {
       for (int i = 0; i < list.size(); i++)
       {
         if ((((Rect)list.get(i)).getRecType() == 1) || 
           (((Rect)list.get(i)).getRecType() == 2) || 
           (((Rect)list.get(i)).getRecType() == 15) || 
           (((Rect)list.get(i)).getRecType() == 16) || 
           (((Rect)list.get(i)).getRecType() == 14) || 
           (((Rect)list.get(i)).getRecType() == 12) || 
           (((Rect)list.get(i)).getRecType() == 13) || 
           (((Rect)list.get(i)).getRecType() == 11) || 
           (((Rect)list.get(i)).getRecType() == 17) || 
           (((Rect)list.get(i)).getRecType() == 18) || 
           (((Rect)list.get(i)).getRecType() == 20) || 
           (((Rect)list.get(i)).getRecType() == 21) || 
           (((Rect)list.get(i)).getRecType() == 22) || 
           (((Rect)list.get(i)).getRecType() == 23)) {
           this.mainrectlist.add((Rect)list.get(i));
         }
         this.allmainrects = new Rect[this.mainrectlist.size()];
         this.mainrectlist.toArray(this.allmainrects);
       }
     }
     JsonUtils.writeSuccessData(this.servletResponse, this.mainrectlist);
   }
   
   public void SaveTodoProgramLayout()
   {
     String xml = this.xmldata;
     
     Program p = (Program)XmlToLayout.parseXml(xml, "utf-8");
     
     List<Rect> list = getPluginRectHandle().readAll(this.taskID, this.programID);
     if ((list != null) && (list.size() > 0))
     {
       this.allrects = new Rect[list.size()];
       list.toArray(this.allrects);
     }
     List<Integer> nowlist = new ArrayList();
     if ((p != null) && (p.getRectWnds() != null) && (p.getRectWnds().length > 0)) {
       for (int i = 0; i < p.getRectWnds().length; i++) {
         nowlist.add(Integer.valueOf(p.getRectWnds()[i].getRectId()));
       }
     }
     if (this.allrects != null) {
       for (int j = 0; j < this.allrects.length; j++) {
         if (!nowlist.contains(Integer.valueOf(this.allrects[j].getRectId()))) {
           getPluginRectHandle().remove(this.taskID, this.programID, 
             this.allrects[j].getRectId());
         }
       }
     } else if ((p.getRectWnds() != null) && (p.getRectWnds().length > 0)) {
       for (int k = 0; k < p.getRectWnds().length; k++) {
         try
         {
           p.getRectWnds()[k].setTaskId(this.taskID);
           p.getRectWnds()[k].setProgramId(this.programID);
           
           getPluginRectHandle().create(p.getRectWnds()[k]);
         }
         catch (Exception e)
         {
           e.printStackTrace();
         }
       }
     }
     if ((p.getRectWnds() != null) && (p.getRectWnds().length > 0)) {
       for (int n = 0; n < p.getRectWnds().length; n++) {
         if (p.getRectWnds()[n].getRectId() > 0) {
           try
           {
             p.getRectWnds()[n].setTaskId(this.taskID);
             p.getRectWnds()[n].setProgramId(this.programID);
             
             getPluginRectHandle().modify(p.getRectWnds()[n]);
           }
           catch (Exception e)
           {
             e.printStackTrace();
           }
         } else {
           try
           {
             p.getRectWnds()[n].setTaskId(this.taskID);
             p.getRectWnds()[n].setProgramId(this.programID);
             
             getPluginRectHandle().create(p.getRectWnds()[n]);
           }
           catch (Exception e)
           {
             e.printStackTrace();
           }
         }
       }
     }
     GetTodoProgramLayout();
   }
   
   public int rectID = 0;
   
   public int getRectID()
   {
     return this.rectID;
   }
   
   public void setRectID(int rectID)
   {
     this.rectID = rectID;
   }
   
   CategoryHandle categoryhandle = null;
   
   public CategoryHandle getCategoryHandle()
   {
     return this.categoryhandle == null ? (this.categoryhandle = new CategoryHandle(
       this)) : this.categoryhandle;
   }
   
   SourceCategoryHandle sourcecategoryhandle = null;
   
   public SourceCategoryHandle getSourceCategoryHandle()
   {
     return this.sourcecategoryhandle == null ? (this.sourcecategoryhandle = new SourceCategoryHandle(
       this)) : this.sourcecategoryhandle;
   }
   
   public List<Category> brother = null;
   
   public List<Category> getBrother()
   {
     return this.brother;
   }
   
   public void setBrother(List<Category> brother)
   {
     this.brother = brother;
   }
   
   Category category = null;
   
   public Category getCategory()
   {
     return this.category;
   }
   
   public void setGroup(Category category)
   {
     this.category = category;
   }
   
   public List<Category> categorypath = null;
   
   public List<Category> getCategorypath()
   {
     return this.categorypath;
   }
   
   public void setCategorypath(List<Category> categorypath)
   {
     this.categorypath = categorypath;
   }
   
   public String taskType = "plugin";
   
   public String getTaskType()
   {
     return this.taskType;
   }
   
   public void setTaskType(String taskType)
   {
     this.taskType = taskType;
   }
   
   public String logo = "normal";
   
   public String getLogo()
   {
     return this.logo;
   }
   
   public void setLogo(String logo)
   {
     this.logo = logo;
   }
   
   public String imageSelect()
   {
     this.category = new Category();
     this.category.setParentId(0);
     if (this.categorypath == null) {
       this.categorypath = new ArrayList();
     } else {
       this.categorypath.clear();
     }
     Category newgroup = this.category;
     
     this.categorypath.add(0, this.category);
     while (newgroup.getParentId() != 0)
     {
       newgroup = getCategoryHandle().read(newgroup.getParentId());
       this.categorypath.add(0, newgroup);
     }
     StringBuffer sql = new StringBuffer("SELECT A.CAT_ID AS CAT_ID, A.NAME AS NAME, A.DESCP AS DESCP, A.PARENT_ID AS PARENT_ID, B.NAME AS PARENT_NAME, A.DEPTH AS DEPTH, A.SUB_NUM AS SUB_NUM,   A.CREATE_TIME AS CREATE_TIME, A.CREATE_USER AS CREATE_USER FROM (SELECT * FROM tb_category WHERE CSTM_ID=:CSTM_ID  AND   PARENT_ID IS NULL  ) AS A LEFT JOIN tb_category AS B ON A.PARENT_ID=B.CAT_ID");
     
 
 
 
 
 
 
 
 
     Map<String, Object> parameters = new HashMap();
     parameters.put("CSTM_ID", Integer.valueOf(getCstmId()));
     
     this.brother = getCategoryHandle().readChildren(sql.toString(), parameters);
     
     return "imageselect";
   }
   
   public int categoryId = 0;
   
   public int getCategoryId()
   {
     return this.categoryId;
   }
   
   public void setCategoryId(int categoryId)
   {
     this.categoryId = categoryId;
   }
   
   public int selfid = 0;
   
   public int getSelfid()
   {
     return this.selfid;
   }
   
   public void setSelfid(int selfid)
   {
     this.selfid = selfid;
   }
   
   public void categorySelector()
   {
     List<Category> categorys = new ArrayList();
     StringBuffer sql = new StringBuffer("SELECT A.CAT_ID AS CAT_ID, A.NAME AS NAME, A.DESCP AS DESCP, A.PARENT_ID AS PARENT_ID, B.NAME AS PARENT_NAME, A.DEPTH AS DEPTH, A.SUB_NUM AS SUB_NUM,  A.CREATE_TIME AS CREATE_TIME, A.CREATE_USER AS CREATE_USER FROM (SELECT * FROM tb_category WHERE CSTM_ID=:CSTM_ID AND  " + (
     
 
 
 
 
 
 
 
 
 
 
 
       this.categoryId == 0 ? "PARENT_ID IS NULL " : 
       "PARENT_ID=:PARENT_ID") + " ) AS A " + 
       "LEFT JOIN tb_category AS B ON A.PARENT_ID=B.CAT_ID");
     
     Map<String, Object> parameters = new HashMap();
     parameters.put("CSTM_ID", Integer.valueOf(getCstmId()));
     if (this.categoryId != 0) {
       parameters.put("PARENT_ID", Integer.valueOf(this.categoryId));
     }
     try
     {
       categorys = getCategoryHandle().readChildren(sql.toString(), 
         parameters);
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
     JsonUtils.writeSuccessData(this.servletResponse, categorys);
   }
   
   public void sourcecategorySelector()
   {
     List<SourceCategory> categorys = new ArrayList();
     StringBuffer sql = new StringBuffer(
       "SELECT A.CAT_ID AS CAT_ID, A.NAME AS NAME, A.DESCP AS DESCP, A.PARENT_ID AS PARENT_ID, B.NAME AS PARENT_NAME, A.DEPTH AS DEPTH, A.SUB_NUM AS SUB_NUM,  A.CREATE_TIME AS CREATE_TIME, A.CREATE_USER AS CREATE_USER FROM (SELECT * FROM tb_source_category WHERE CSTM_ID=:CSTM_ID AND  " + (
       
 
 
 
 
 
 
 
 
 
 
 
       this.categoryId == 0 ? "PARENT_ID IS NULL " : 
       "PARENT_ID=:PARENT_ID") + 
       " ) AS A " + 
       "LEFT JOIN tb_source_category AS B ON A.PARENT_ID=B.CAT_ID");
     
     Map<String, Object> parameters = new HashMap();
     parameters.put("CSTM_ID", Integer.valueOf(getCstmId()));
     if (this.categoryId != 0) {
       parameters.put("PARENT_ID", Integer.valueOf(this.categoryId));
     }
     try
     {
       categorys = getSourceCategoryHandle().readChildren(sql.toString(), 
         parameters);
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
     JsonUtils.writeSuccessData(this.servletResponse, categorys);
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
   
   public void addimagefile()
   {
     if (this.istag) {
       for (int i = 0; i < this.selectIDs.split(",").length; i++) {
         try
         {
           this.sourceID = Integer.parseInt(this.selectIDs.split(",")[i]);
           
 
           Source source = getSourceHandle().read(this.sourceID);
           
           RectImageSource rectSource = new RectImageSource();
           
           rectSource.setTaskId(this.taskID);
           rectSource.setProgramId(this.programID);
           rectSource.setRectId(this.rectID);
           
           rectSource.setPlayTime(5);
           rectSource.setScaleStyle(3);
           rectSource.setSwapStyle(0);
           rectSource.setSwapTime(2);
           
           rectSource.setSequenceNum(2147483647);
           if ((source != null) && (source.getSourceId() > 0))
           {
             rectSource.setFileId(this.sourceID);
             rectSource.setFileName(source.getName());
           }
           else
           {
             JsonUtils.writeError(this.servletResponse);
           }
           if (!getPluginRectContentHandle().create(rectSource)) {
             JsonUtils.writeError(this.servletResponse);
           }
         }
         catch (Exception localException1) {}
       }
     } else if ((this.selectIDs != null) && (this.selectIDs.length() > 0)) {
       for (int i = 0; i < this.selectIDs.split(",").length; i++) {
         try
         {
           RectImage rectimage = new RectImage();
           com.gnamp.server.model.File file = new com.gnamp.server.model.File();
           file = getFileHandle().read(
             Long.parseLong(this.selectIDs.split(",")[i]));
           if (file != null)
           {
             rectimage.setFileName(file.getFileName());
             rectimage.setFileId(file.getFileId());
           }
           rectimage.setPlayTime(5);
           rectimage.setProgramId(this.programID);
           rectimage.setRectId(this.rectID);
           rectimage.setScaleStyle(3);
           rectimage.setSwapStyle(0);
           rectimage.setSequenceNum(2147483647);
           rectimage.setTaskId(this.taskID);
           rectimage.setSwapTime(2);
           
           getPluginRectContentHandle().create(rectimage);
         }
         catch (Exception e)
         {
           e.printStackTrace();
         }
       }
     }
     JsonUtils.writeSuccess(this.servletResponse);
   }
   
   List<RectImage> imagefilelist = null;
   
   public List<RectImage> getImagefilelist()
   {
     return this.imagefilelist;
   }
   
   public void setImagefilelist(List<RectImage> imagefilelist)
   {
     this.imagefilelist = imagefilelist;
   }
   
   List<RectVideo> videofilelist = null;
   
   public List<RectVideo> getRectVideofilelist()
   {
     return this.videofilelist;
   }
   
   public void setRectVideofilelist(List<RectVideo> videofilelist)
   {
     this.videofilelist = videofilelist;
   }
   
   List<RectContent> rectcontentlist = null;
   
   public List<RectContent> getRectcontentlist()
   {
     return this.rectcontentlist;
   }
   
   public void setRectcontentlist(List<RectContent> rectcontentlist)
   {
     this.rectcontentlist = rectcontentlist;
   }
   
   List<ProgramMusic> musicfilelist = null;
   
   public List<ProgramMusic> getRectMusicfilelist()
   {
     return this.musicfilelist;
   }
   
   public void setRectMusicfilelist(List<ProgramMusic> musicfilelist)
   {
     this.musicfilelist = musicfilelist;
   }
   
   List<RectContent> contentfilelist = null;
   
   public List<RectContent> getContentfilelist()
   {
     return this.contentfilelist;
   }
   
   public void setContentfilelist(List<RectContent> contentfilelist)
   {
     this.contentfilelist = contentfilelist;
   }
   
   PageBean page = new PageBean();
   
   public PageBean getPage()
   {
     return this.page;
   }
   
   public void setPage(PageBean page)
   {
     this.page = page;
   }
   
   private MapTool<String, Object> getParameters()
   {
     MapTool<String, Object> parameters = new MapTool();
     parameters.put("CSTM_ID", Integer.valueOf(this.cstmId));
     parameters.put("TASK_ID", Integer.valueOf(this.taskID));
     parameters.put("PRGM_ID", Integer.valueOf(this.programID));
     parameters.put("RECT_ID", Integer.valueOf(this.rectID));
     parameters.put("CONTENT_IMAGE", Integer.valueOf(1));
     parameters.put("CONTENT_VIDEO", Integer.valueOf(2));
     parameters.put("CONTENT_FLASH", Integer.valueOf(6));
     parameters.put("CONTENT_CUSTOM_SOURCE", Integer.valueOf(4));
     parameters.put("CONTENT_SYSTEM_SOURCE", Integer.valueOf(5));
     
     return parameters;
   }
   
   private MapTool<String, Object> getMusicParameters()
   {
     MapTool<String, Object> parameters = new MapTool();
     parameters.put("CSTM_ID", Integer.valueOf(this.cstmId));
     parameters.put("TASK_ID", Integer.valueOf(this.taskID));
     parameters.put("PRGM_ID", Integer.valueOf(this.programID));
     
     return parameters;
   }
   
   public String videoSelect()
   {
     this.category = new Category();
     this.category.setParentId(0);
     if (this.categorypath == null) {
       this.categorypath = new ArrayList();
     } else {
       this.categorypath.clear();
     }
     Category newgroup = this.category;
     
     this.categorypath.add(0, this.category);
     while (newgroup.getParentId() != 0)
     {
       newgroup = getCategoryHandle().read(newgroup.getParentId());
       this.categorypath.add(0, newgroup);
     }
     StringBuffer sql = new StringBuffer("SELECT A.CAT_ID AS CAT_ID, A.NAME AS NAME, A.DESCP AS DESCP, A.PARENT_ID AS PARENT_ID, B.NAME AS PARENT_NAME, A.DEPTH AS DEPTH, A.SUB_NUM AS SUB_NUM,   A.CREATE_TIME AS CREATE_TIME, A.CREATE_USER AS CREATE_USER FROM (SELECT * FROM tb_category WHERE CSTM_ID=:CSTM_ID  AND   PARENT_ID IS NULL  ) AS A LEFT JOIN tb_category AS B ON A.PARENT_ID=B.CAT_ID");
     
 
 
 
 
 
 
 
 
     Map<String, Object> parameters = new HashMap();
     parameters.put("CSTM_ID", Integer.valueOf(getCstmId()));
     
     this.brother = getCategoryHandle().readChildren(sql.toString(), parameters);
     
     return "videoselect";
   }
   
   public void addvideofile()
   {
     if (this.istag) {
       for (int i = 0; i < this.selectIDs.split(",").length; i++) {
         try
         {
           this.sourceID = Integer.parseInt(this.selectIDs.split(",")[i]);
           
 
           Source source = getSourceHandle().read(this.sourceID);
           
           RectVideoSource rectSource = new RectVideoSource();
           
           rectSource.setTaskId(this.taskID);
           rectSource.setProgramId(this.programID);
           rectSource.setRectId(this.rectID);
           rectSource.setSequenceNum(2147483647);
           rectSource.setRotation(0);
           rectSource.setScaleStyle(3);
           if ((source != null) && (source.getSourceId() > 0))
           {
             rectSource.setFileId(this.sourceID);
             rectSource.setFileName(source.getName());
           }
           else
           {
             JsonUtils.writeError(this.servletResponse);
           }
           if (!getPluginRectContentHandle().create(rectSource)) {
             JsonUtils.writeError(this.servletResponse);
           }
         }
         catch (Exception localException1) {}
       }
     } else if ((this.selectIDs != null) && (this.selectIDs.length() > 0)) {
       for (int i = 0; i < this.selectIDs.split(",").length; i++) {
         try
         {
           RectVideo rectvideo = new RectVideo();
           com.gnamp.server.model.File file = new com.gnamp.server.model.File();
           file = getFileHandle().read(
             Long.parseLong(this.selectIDs.split(",")[i]));
           if (file != null)
           {
             rectvideo.setFileName(file.getFileName());
             rectvideo.setFileId(file.getFileId());
           }
           rectvideo.setProgramId(this.programID);
           rectvideo.setRectId(this.rectID);
           rectvideo.setSequenceNum(2147483647);
           rectvideo.setTaskId(this.taskID);
           rectvideo.setScaleStyle(3);
           rectvideo.setRotation(0);
           
           getPluginRectContentHandle().create(rectvideo);
         }
         catch (Exception e)
         {
           e.printStackTrace();
         }
       }
     }
     JsonUtils.writeSuccess(this.servletResponse);
   }
   
   public String musicSelect()
   {
     this.category = new Category();
     this.category.setParentId(0);
     if (this.categorypath == null) {
       this.categorypath = new ArrayList();
     } else {
       this.categorypath.clear();
     }
     Category newgroup = this.category;
     
     this.categorypath.add(0, this.category);
     while (newgroup.getParentId() != 0)
     {
       newgroup = getCategoryHandle().read(newgroup.getParentId());
       this.categorypath.add(0, newgroup);
     }
     StringBuffer sql = new StringBuffer("SELECT A.CAT_ID AS CAT_ID, A.NAME AS NAME, A.DESCP AS DESCP, A.PARENT_ID AS PARENT_ID, B.NAME AS PARENT_NAME, A.DEPTH AS DEPTH, A.SUB_NUM AS SUB_NUM,   A.CREATE_TIME AS CREATE_TIME, A.CREATE_USER AS CREATE_USER FROM (SELECT * FROM tb_category WHERE CSTM_ID=:CSTM_ID  AND   PARENT_ID IS NULL  ) AS A LEFT JOIN tb_category AS B ON A.PARENT_ID=B.CAT_ID");
     
 
 
 
 
 
 
 
 
     Map<String, Object> parameters = new HashMap();
     parameters.put("CSTM_ID", Integer.valueOf(getCstmId()));
     
     this.brother = getCategoryHandle().readChildren(sql.toString(), parameters);
     
     return "musicselect";
   }
   
   public String contentSelect()
   {
     this.category = new Category();
     this.category.setParentId(0);
     if (this.categorypath == null) {
       this.categorypath = new ArrayList();
     } else {
       this.categorypath.clear();
     }
     Category newgroup = this.category;
     
     this.categorypath.add(0, this.category);
     while (newgroup.getParentId() != 0)
     {
       newgroup = getCategoryHandle().read(newgroup.getParentId());
       this.categorypath.add(0, newgroup);
     }
     StringBuffer sql = new StringBuffer("SELECT A.CAT_ID AS CAT_ID, A.NAME AS NAME, A.DESCP AS DESCP, A.PARENT_ID AS PARENT_ID, B.NAME AS PARENT_NAME, A.DEPTH AS DEPTH, A.SUB_NUM AS SUB_NUM,   A.CREATE_TIME AS CREATE_TIME, A.CREATE_USER AS CREATE_USER FROM (SELECT * FROM tb_category WHERE CSTM_ID=:CSTM_ID  AND   PARENT_ID IS NULL  ) AS A LEFT JOIN tb_category AS B ON A.PARENT_ID=B.CAT_ID");
     
 
 
 
 
 
 
 
 
     Map<String, Object> parameters = new HashMap();
     parameters.put("CSTM_ID", Integer.valueOf(getCstmId()));
     
     this.brother = getCategoryHandle().readChildren(sql.toString(), parameters);
     
     return "contentselect";
   }
   
   public void querycontentfiles()
   {
     this.contentfilelist = new ArrayList();
     
     String query = "SELECT * FROM (SELECT TASK_ID, PRGM_ID, RECT_ID, CONTENT_ID, CONTENT_TYPE, SEQ, t.FILE_ID AS FILE_ID, f.NAME AS FILE_NAME, DETAIL FROM  tb_plugin_rect_content  AS t, tb_file AS f WHERE t.CSTM_ID=:CSTM_ID AND TASK_ID=:TASK_ID AND PRGM_ID=:PRGM_ID AND RECT_ID=:RECT_ID AND f.CSTM_ID=:CSTM_ID AND t.FILE_ID > 0x7FFFFFFF AND f.FILE_ID=t.FILE_ID UNION SELECT TASK_ID, PRGM_ID, RECT_ID, CONTENT_ID, CONTENT_TYPE, SEQ, t.FILE_ID AS FILE_ID, f.NAME AS FILE_NAME, DETAIL FROM  tb_plugin_rect_content  AS t, tb_system_file AS f WHERE t.CSTM_ID=:CSTM_ID AND TASK_ID=:TASK_ID AND PRGM_ID=:PRGM_ID AND RECT_ID=:RECT_ID  AND t.FILE_ID < 0x7FFFFFFF AND f.FILE_ID=t.FILE_ID ) AS tb ORDER BY SEQ ASC " + (
     
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
       this.page.getPageSize() != -1 ? "LIMIT " + 
       (this.page.getCurrentPage() - 1) * this.page.getPageSize() + 
       "," + this.page.getPageSize() : "");
     try
     {
       int total = getPluginRectContentHandle().readAll(this.taskID, this.programID, 
         this.rectID).size();
       
       this.contentfilelist = getPluginRectContentHandle().readAll(query, 
         getParameters());
       
       this.page.setTotalRows(total);
       response(JSONSuccessString(JSONArrayToString(this.contentfilelist), 
         new MapTool().putObject("page", this.page)));
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
   
   public void addcontentfile()
   {
     if ((this.selectIDs != null) && (this.selectIDs.length() > 0)) {
       for (int i = 0; i < this.selectIDs.split(",").length; i++) {
         try
         {
           com.gnamp.server.model.File file = new com.gnamp.server.model.File();
           file = getFileHandle().read(
             Long.parseLong(this.selectIDs.split(",")[i]));
           if (file.getType() == 0)
           {
             RectImage rectimage = new RectImage();
             if (file != null)
             {
               rectimage.setFileName(file.getFileName());
               rectimage.setFileId(file.getFileId());
             }
             rectimage.setPlayTime(5);
             rectimage.setProgramId(this.programID);
             rectimage.setRectId(this.rectID);
             rectimage.setScaleStyle(3);
             rectimage.setSwapStyle(0);
             rectimage.setSequenceNum(2147483647);
             rectimage.setTaskId(this.taskID);
             rectimage.setSwapTime(2);
             
             getPluginRectContentHandle().create(rectimage);
           }
           else if (file.getType() == 1)
           {
             RectVideo rectvideo = new RectVideo();
             if (file != null)
             {
               rectvideo.setFileName(file.getFileName());
               rectvideo.setFileId(file.getFileId());
             }
             rectvideo.setProgramId(this.programID);
             rectvideo.setRectId(this.rectID);
             rectvideo.setSequenceNum(2147483647);
             rectvideo.setTaskId(this.taskID);
             
             getPluginRectContentHandle().create(rectvideo);
           }
         }
         catch (Exception e)
         {
           e.printStackTrace();
         }
       }
     }
     JsonUtils.writeSuccess(this.servletResponse);
   }
   
   public void deletecontentfile()
   {
     if ((this.selectIDs != null) && (this.selectIDs.length() > 0)) {
       for (int i = 0; i < this.selectIDs.split(",").length; i++) {
         try
         {
           getPluginRectContentHandle().remove(this.taskID, this.programID, 
             this.rectID, Integer.parseInt(this.selectIDs.split(",")[i]));
         }
         catch (Exception e)
         {
           e.printStackTrace();
         }
       }
     }
     JsonUtils.writeSuccess(this.servletResponse);
   }
   
   public void querymusicfiles()
   {
     this.musicfilelist = new ArrayList();
     
     String query = "SELECT TASK_ID, PRGM_ID, MUSIC_ID, SEQ, t.FILE_ID AS FILE_ID, f.NAME AS FILE_NAME FROM  tb_plugin_program_music  AS t, tb_file AS f WHERE t.CSTM_ID=:CSTM_ID AND TASK_ID=:TASK_ID AND PRGM_ID=:PRGM_ID AND f.CSTM_ID=:CSTM_ID AND f.FILE_ID=t.FILE_ID ORDER BY SEQ ASC " + (
     
 
 
 
 
 
 
 
       this.page.getPageSize() != -1 ? "LIMIT " + 
       (this.page.getCurrentPage() - 1) * this.page.getPageSize() + 
       "," + this.page.getPageSize() : "");
     try
     {
       int total = getPluginProgramMusicHandle()
         .readAll(this.taskID, this.programID).size();
       
       this.musicfilelist = getPluginProgramMusicHandle().readAll(query, 
         getMusicParameters());
       
       this.page.setTotalRows(total);
       response(JSONSuccessString(JSONArrayToString(this.musicfilelist), 
         new MapTool().putObject("page", this.page)));
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
   
   public void addmusicfile()
   {
     if ((this.selectIDs != null) && (this.selectIDs.length() > 0)) {
       for (int i = 0; i < this.selectIDs.split(",").length; i++) {
         try
         {
           ProgramMusic rectmusic = new ProgramMusic();
           com.gnamp.server.model.File file = new com.gnamp.server.model.File();
           file = getFileHandle().read(
             Long.parseLong(this.selectIDs.split(",")[i]));
           if (file != null)
           {
             rectmusic.setFileName(file.getFileName());
             rectmusic.setFileId(file.getFileId());
           }
           rectmusic.setProgramId(this.programID);
           rectmusic.setSequenceNum(2147483647);
           rectmusic.setTaskId(this.taskID);
           
           getPluginProgramMusicHandle().create(rectmusic);
         }
         catch (Exception e)
         {
           e.printStackTrace();
         }
       }
     }
     JsonUtils.writeSuccess(this.servletResponse);
   }
   
   public void deletemusicfile()
   {
     if ((this.selectIDs != null) && (this.selectIDs.length() > 0)) {
       for (int i = 0; i < this.selectIDs.split(",").length; i++) {
         try
         {
           getPluginProgramMusicHandle().remove(this.taskID, this.programID, 
             Integer.parseInt(this.selectIDs.split(",")[i]));
         }
         catch (Exception e)
         {
           e.printStackTrace();
         }
       }
     }
     JsonUtils.writeSuccess(this.servletResponse);
   }
   
   public void upmusic()
   {
     ProgramMusic old = getPluginProgramMusicHandle().read(
       this.rectMusic.getTaskId(), this.rectMusic.getProgramId(), 
       this.rectMusic.getMusicId());
     int seq = old.getSequenceNum() - 1;
     old.setSequenceNum(seq);
     if (getPluginProgramMusicHandle().modify(old)) {
       JsonUtils.writeSuccess(this.servletResponse);
     } else {
       JsonUtils.writeError(this.servletResponse);
     }
   }
   
   public void downmusic()
   {
     ProgramMusic old = getPluginProgramMusicHandle().read(
       this.rectMusic.getTaskId(), this.rectMusic.getProgramId(), 
       this.rectMusic.getMusicId());
     int seq = old.getSequenceNum() + 1;
     old.setSequenceNum(seq);
     if (getPluginProgramMusicHandle().modify(old)) {
       JsonUtils.writeSuccess(this.servletResponse);
     } else {
       JsonUtils.writeError(this.servletResponse);
     }
   }
   
   public void musicToFirst()
   {
     ProgramMusic old = getPluginProgramMusicHandle().read(
       this.rectMusic.getTaskId(), this.rectMusic.getProgramId(), 
       this.rectMusic.getMusicId());
     old.setSequenceNum(0);
     if (getPluginProgramMusicHandle().modify(old)) {
       JsonUtils.writeSuccess(this.servletResponse);
     } else {
       JsonUtils.writeError(this.servletResponse);
     }
   }
   
   public void musicToLast()
   {
     ProgramMusic old = getPluginProgramMusicHandle().read(
       this.rectMusic.getTaskId(), this.rectMusic.getProgramId(), 
       this.rectMusic.getMusicId());
     old.setSequenceNum(2147483647);
     if (getPluginProgramMusicHandle().modify(old)) {
       JsonUtils.writeSuccess(this.servletResponse);
     } else {
       JsonUtils.writeError(this.servletResponse);
     }
   }
   
   public void upcontent()
   {
     RectContent old = getPluginRectContentHandle().read(
       this.rectImage.getTaskId(), this.rectImage.getProgramId(), 
       this.rectImage.getRectId(), this.rectImage.getContentId());
     int seq = old.getSequenceNum() - 1;
     old.setSequenceNum(seq);
     if (getPluginRectContentHandle().modify(old)) {
       JsonUtils.writeSuccess(this.servletResponse);
     } else {
       JsonUtils.writeError(this.servletResponse);
     }
   }
   
   public void downcontent()
   {
     RectContent old = getPluginRectContentHandle().read(
       this.rectImage.getTaskId(), this.rectImage.getProgramId(), 
       this.rectImage.getRectId(), this.rectImage.getContentId());
     int seq = old.getSequenceNum() + 1;
     old.setSequenceNum(seq);
     if (getPluginRectContentHandle().modify(old)) {
       JsonUtils.writeSuccess(this.servletResponse);
     } else {
       JsonUtils.writeError(this.servletResponse);
     }
   }
   
   public TextRect rect = null;
   
   public TextRect getRect()
   {
     return this.rect;
   }
   
   public void setRect(TextRect rect)
   {
     this.rect = rect;
   }
   
   public String textSelect()
   {
     this.rect = ((TextRect)getPluginRectHandle().read(this.taskID, this.programID, this.rectID));
     return "textselect";
   }
   
   public void querycontentlist()
   {
     this.rectcontentlist = new ArrayList();
     
     String query = "SELECT TASK_ID, PRGM_ID, RECT_ID, CONTENT_ID, CONTENT_TYPE, SEQ, t.FILE_ID AS FILE_ID, (CASE WHEN (CONTENT_TYPE=:CONTENT_IMAGE OR CONTENT_TYPE=:CONTENT_VIDEO OR CONTENT_TYPE=:CONTENT_FLASH) AND t.FILE_ID>0x7FFFFFFF THEN f.NAME WHEN (CONTENT_TYPE=:CONTENT_IMAGE OR CONTENT_TYPE=:CONTENT_VIDEO OR CONTENT_TYPE=:CONTENT_FLASH) AND 0<t.FILE_ID AND t.FILE_ID<=0x7FFFFFFF THEN sf.NAME WHEN CONTENT_TYPE=:CONTENT_CUSTOM_SOURCE THEN s.NAME WHEN CONTENT_TYPE=:CONTENT_SYSTEM_SOURCE THEN ss.NAME ELSE '' END)AS FILE_NAME, DETAIL FROM (SELECT * FROM  tb_plugin_rect_content  WHERE CSTM_ID=:CSTM_ID AND TASK_ID=:TASK_ID AND PRGM_ID=:PRGM_ID AND RECT_ID=:RECT_ID ) AS t LEFT JOIN tb_file AS f ON (CONTENT_TYPE=:CONTENT_IMAGE OR CONTENT_TYPE=:CONTENT_VIDEO OR CONTENT_TYPE=:CONTENT_FLASH) AND t.FILE_ID>0x7FFFFFFF AND t.FILE_ID=f.FILE_ID LEFT JOIN tb_system_file AS sf ON (CONTENT_TYPE=:CONTENT_IMAGE OR CONTENT_TYPE=:CONTENT_VIDEO OR CONTENT_TYPE=:CONTENT_FLASH) AND  0<t.FILE_ID AND t.FILE_ID<=0x7FFFFFFF AND t.FILE_ID=sf.FILE_ID LEFT JOIN tb_source AS s ON CONTENT_TYPE=:CONTENT_CUSTOM_SOURCE AND t.FILE_ID=s.SOURCE_ID LEFT JOIN tb_system_source AS ss ON CONTENT_TYPE=:CONTENT_SYSTEM_SOURCE AND t.FILE_ID=ss.SOURCE_ID ORDER BY SEQ ASC " + (
     
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
       this.page.getPageSize() != -1 ? "LIMIT " + 
       (this.page.getCurrentPage() - 1) * this.page.getPageSize() + 
       "," + this.page.getPageSize() : "");
     try
     {
       int total = getPluginRectContentHandle().readAll(this.taskID, this.programID, 
         this.rectID).size();
       
       this.rectcontentlist = getPluginRectContentHandle().readAll(query, 
         getParameters());
       
       this.page.setTotalRows(total);
       response(JSONSuccessString(JSONArrayToString(this.rectcontentlist), 
         new MapTool().putObject("page", this.page)));
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
   
   public int contentID = 0;
   
   public int getContentID()
   {
     return this.contentID;
   }
   
   public void setContentID(int contentID)
   {
     this.contentID = contentID;
   }
   
   public void up()
   {
     RectContent old = getPluginRectContentHandle().read(this.taskID, this.programID, 
       this.rectID, this.contentID);
     int seq = old.getSequenceNum() - 1;
     old.setSequenceNum(seq);
     if (getPluginRectContentHandle().modify(old)) {
       JsonUtils.writeSuccess(this.servletResponse);
     } else {
       JsonUtils.writeError(this.servletResponse);
     }
   }
   
   public void down()
   {
     RectContent old = getPluginRectContentHandle().read(this.taskID, this.programID, 
       this.rectID, this.contentID);
     int seq = old.getSequenceNum() + 1;
     old.setSequenceNum(seq);
     if (getPluginRectContentHandle().modify(old)) {
       JsonUtils.writeSuccess(this.servletResponse);
     } else {
       JsonUtils.writeError(this.servletResponse);
     }
   }
   
   public void toFirst()
   {
     RectContent old = getPluginRectContentHandle().read(this.taskID, this.programID, this.rectID, this.contentID);
     old.setSequenceNum(0);
     if (getPluginRectContentHandle().modify(old)) {
       JsonUtils.writeSuccess(this.servletResponse);
     } else {
       JsonUtils.writeError(this.servletResponse);
     }
   }
   
   public void toLast()
   {
     RectContent old = getPluginRectContentHandle().read(this.taskID, this.programID, this.rectID, this.contentID);
     old.setSequenceNum(2147483647);
     if (getPluginRectContentHandle().modify(old)) {
       JsonUtils.writeSuccess(this.servletResponse);
     } else {
       JsonUtils.writeError(this.servletResponse);
     }
   }
   
   public void deletecontent()
   {
     if ((this.selectIDs != null) && (this.selectIDs.length() > 0)) {
       for (int i = 0; i < this.selectIDs.split(",").length; i++) {
         try
         {
           getPluginRectContentHandle().remove(this.taskID, this.programID, 
             this.rectID, Integer.parseInt(this.selectIDs.split(",")[i]));
         }
         catch (Exception e)
         {
           e.printStackTrace();
         }
       }
     }
     JsonUtils.writeSuccess(this.servletResponse);
   }
   
   List<Source> tags = null;
   
   public List<Source> getTags()
   {
     return this.tags;
   }
   
   public void setTags(List<Source> tags)
   {
     this.tags = tags;
   }
   
   List<SystemSource> systags = null;
   
   public List<SystemSource> getSystags()
   {
     return this.systags;
   }
   
   public void setSystags(List<SystemSource> systags)
   {
     this.systags = systags;
   }
   
   public String ToAddText()
   {
     String sql = "SELECT f.CSTM_ID,f.CAT_ID,SOURCE_ID, f.NAME AS NAME, f.DESCP AS DESCP, f.VERSION AS VERSION, TYPE, STATE, f.CREATE_TIME, f.CREATE_USER FROM (SELECT * FROM tb_source WHERE CSTM_ID=:CSTM_ID  ) as f ";
     
 
 
 
 
 
 
 
 
 
 
 
 
     this.tags = getSourceHandle().readAll(sql);
     this.systags = SystemSourceHandle.readAll(0);
     
     this.taskID = this.rectText.getTaskId();
     this.programID = this.rectText.getProgramId();
     this.rectID = this.rectText.getRectId();
     
     return "addtext";
   }
   
   SourceCategory sourcecategory = null;
   public List<SourceCategory> sourcecategorypath = null;
   public List<SourceCategory> sourcebrother = null;
   
   public List<SourceCategory> getSourcebrother()
   {
     return this.sourcebrother;
   }
   
   public void setSourcebrother(List<SourceCategory> sourcebrother)
   {
     this.sourcebrother = sourcebrother;
   }
   
   public SourceCategory getSourcecategory()
   {
     return this.sourcecategory;
   }
   
   public void setSourcecategory(SourceCategory sourcecategory)
   {
     this.sourcecategory = sourcecategory;
   }
   
   public List<SourceCategory> getSourcecategorypath()
   {
     return this.sourcecategorypath;
   }
   
   public void setSourcecategorypath(List<SourceCategory> sourcecategorypath)
   {
     this.sourcecategorypath = sourcecategorypath;
   }
   
   public List<CategoryXMLConfig> systemsourcecates = null;
   
   public List<CategoryXMLConfig> getSystemsourcecates()
   {
     return this.systemsourcecates;
   }
   
   public void setSystemsourcecates(List<CategoryXMLConfig> systemsourcecates)
   {
     this.systemsourcecates = systemsourcecates;
   }
   
   public int type = 3;
   
   public int getType()
   {
     return this.type;
   }
   
   public void setType(int type)
   {
     this.type = type;
   }
   
   public String ToAddTag()
   {
     this.sourcecategory = new SourceCategory();
     this.sourcecategory.setParentId(0);
     if (this.sourcecategorypath == null) {
       this.sourcecategorypath = new ArrayList();
     } else {
       this.sourcecategorypath.clear();
     }
     SourceCategory newgroup = this.sourcecategory;
     
     this.sourcecategorypath.add(0, this.sourcecategory);
     while (newgroup.getParentId() != 0)
     {
       newgroup = getSourceCategoryHandle().read(newgroup.getParentId());
       this.sourcecategorypath.add(0, newgroup);
     }
     StringBuffer sql = new StringBuffer(
       "SELECT A.CAT_ID AS CAT_ID, A.NAME AS NAME, A.DESCP AS DESCP, A.PARENT_ID AS PARENT_ID, B.NAME AS PARENT_NAME, A.DEPTH AS DEPTH, A.SUB_NUM AS SUB_NUM,   A.CREATE_TIME AS CREATE_TIME, A.CREATE_USER AS CREATE_USER FROM (SELECT * FROM tb_source_category WHERE CSTM_ID=:CSTM_ID  AND   PARENT_ID IS NULL  ) AS A LEFT JOIN tb_source_category AS B ON A.PARENT_ID=B.CAT_ID");
     
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
     Map<String, Object> parameters = new HashMap();
     parameters.put("CSTM_ID", Integer.valueOf(getCstmId()));
     
     this.sourcebrother = getSourceCategoryHandle().readChildren(sql.toString(), 
       parameters);
     
     this.taskID = this.rectText.getTaskId();
     this.programID = this.rectText.getProgramId();
     this.rectID = this.rectText.getRectId();
     
     this.systemsourcecates = Context.getSystemSourceCates();
     
     String forward = "";
     switch (this.type)
     {
     case 3: 
     case 5: 
       forward = "addtag";
       break;
     case 2: 
       forward = "addvideotag";
       break;
     case 1: 
     case 4: 
       forward = "addimagetag";
     }
     return forward;
   }
   
   public String ToModifyText()
   {
     this.taskID = this.rectText.getTaskId();
     this.programID = this.rectText.getProgramId();
     this.rectID = this.rectText.getRectId();
     
     RectContent rectContent = (RectText)getPluginRectContentHandle().read(
       this.taskID, this.programID, this.rectID, this.rectText.getContentId());
     this.rectText = ((RectText)rectContent);
     
     return "edittext";
   }
   
   public boolean istag = false;
   
   public boolean isIstag()
   {
     return this.istag;
   }
   
   public void setIstag(boolean istag)
   {
     this.istag = istag;
   }
   
   public boolean isbuidin = false;
   
   public boolean isIsbuidin()
   {
     return this.isbuidin;
   }
   
   public void setIsbuidin(boolean isbuidin)
   {
     this.isbuidin = isbuidin;
   }
   
   public int sourceID = 0;
   
   public int getSourceID()
   {
     return this.sourceID;
   }
   
   public void setSourceID(int sourceID)
   {
     this.sourceID = sourceID;
   }
   
   public void AddText()
   {
     this.rectText.setSequenceNum(2147483647);
     if (!this.istag) {
       try
       {
         if (getPluginRectContentHandle().create(this.rectText)) {
           JsonUtils.writeSuccess(this.servletResponse);
         } else {
           JsonUtils.writeError(this.servletResponse);
         }
       }
       catch (Exception e)
       {
         JsonUtils.writeError(this.servletResponse);
         e.printStackTrace();
       }
     } else {
       try
       {
         if (!this.isbuidin)
         {
           if ((this.selectIDs != null) && (this.selectIDs.length() > 0))
           {
             for (int i = 0; i < this.selectIDs.split(",").length; i++)
             {
               this.sourceID = 
                 Integer.parseInt(this.selectIDs.split(",")[i]);
               
               Source source = getSourceHandle().read(this.sourceID);
               
               RectSource rectSource = new RectSource();
               
               rectSource.setTaskId(this.rectText.getTaskId());
               rectSource.setProgramId(this.rectText.getProgramId());
               rectSource.setRectId(this.rectText.getRectId());
               rectSource.setSequenceNum(2147483647);
               if ((source != null) && (source.getSourceId() > 0)) {
                 rectSource.setFileId(this.sourceID);
               } else {
                 JsonUtils.writeError(this.servletResponse);
               }
               if (!getPluginRectContentHandle().create(rectSource)) {
                 JsonUtils.writeError(this.servletResponse);
               }
             }
             JsonUtils.writeSuccess(this.servletResponse);
           }
         }
         else if ((this.selectIDs != null) && (this.selectIDs.length() > 0))
         {
           for (int i = 0; i < this.selectIDs.split(",").length; i++)
           {
             this.sourceID = 
               Integer.parseInt(this.selectIDs.split(",")[i]);
             SystemSource syssource = 
               SystemSourceHandle.read(this.sourceID);
             
             RectSystemSource rectSource = new RectSystemSource();
             
             rectSource.setTaskId(this.rectText.getTaskId());
             rectSource.setProgramId(this.rectText.getProgramId());
             rectSource.setRectId(this.rectText.getRectId());
             rectSource.setSequenceNum(2147483647);
             if ((syssource != null) && 
               (syssource.getSourceId() > 0)) {
               rectSource.setFileId(this.sourceID);
             } else {
               JsonUtils.writeError(this.servletResponse);
             }
             if (!getPluginRectContentHandle().create(rectSource)) {
               JsonUtils.writeError(this.servletResponse);
             }
           }
           JsonUtils.writeSuccess(this.servletResponse);
         }
       }
       catch (Exception e)
       {
         JsonUtils.writeError(this.servletResponse);
         e.printStackTrace();
       }
     }
   }
   
   public void ModifyText()
   {
     RectContent rectContent = getPluginRectContentHandle().read(this.taskID, 
       this.programID, this.rectID, this.rectText.getContentId());
     if (rectContent.getContentType() == 3)
     {
       RectText old = (RectText)rectContent;
       old.setText(this.rectText.getText());
       if (getPluginRectContentHandle().modify(old)) {
         JsonUtils.writeSuccess(this.servletResponse);
       } else {
         JsonUtils.writeError(this.servletResponse);
       }
     }
   }
   
   public void ModifyImage()
   {
     if ((this.selectIDs != null) && (this.selectIDs.length() > 0)) {
       if (this.selectIDs.split(",").length == 1)
       {
         RectImage old = null;
         RectImageSource imageSource = null;
         
         RectContent rectContent = getPluginRectContentHandle().read(this.rectImage.getTaskId(), 
           this.rectImage.getProgramId(), this.rectImage.getRectId(), 
           Integer.parseInt(this.selectIDs.split(",")[0]));
         if (rectContent.getContentType() == 4)
         {
           imageSource = (RectImageSource)getPluginRectContentHandle()
             .read(this.rectImage.getTaskId(), 
             this.rectImage.getProgramId(), this.rectImage.getRectId(), 
             Integer.parseInt(this.selectIDs.split(",")[0]));
           
           imageSource.setPlayTime(this.rectImage.getPlayTime());
           imageSource.setScaleStyle(this.rectImage.getScaleStyle());
           imageSource.setSwapStyle(this.rectImage.getSwapStyle());
           imageSource.setSwapTime(this.rectImage.getSwapTime());
           if (getPluginRectContentHandle().modify(imageSource)) {
             JsonUtils.writeSuccess(this.servletResponse);
           } else {
             JsonUtils.writeError(this.servletResponse);
           }
         }
         else
         {
           old = (RectImage)getPluginRectContentHandle().read(this.rectImage.getTaskId(), 
             this.rectImage.getProgramId(), this.rectImage.getRectId(), 
             Integer.parseInt(this.selectIDs.split(",")[0]));
           
 
           old.setPlayTime(this.rectImage.getPlayTime());
           old.setScaleStyle(this.rectImage.getScaleStyle());
           old.setSwapStyle(this.rectImage.getSwapStyle());
           old.setSwapTime(this.rectImage.getSwapTime());
           if (getPluginRectContentHandle().modify(old)) {
             JsonUtils.writeSuccess(this.servletResponse);
           } else {
             JsonUtils.writeError(this.servletResponse);
           }
         }
       }
       else
       {
         for (int i = 0; i < this.selectIDs.split(",").length; i++) {
           try
           {
             RectImage old = null;
             RectImageSource imageSource = null;
             
             RectContent rectContent = getPluginRectContentHandle().read(this.rectImage.getTaskId(), 
               this.rectImage.getProgramId(), this.rectImage.getRectId(), 
               Integer.parseInt(this.selectIDs.split(",")[i]));
             if (rectContent.getContentType() == 4)
             {
               imageSource = (RectImageSource)getPluginRectContentHandle()
                 .read(this.rectImage.getTaskId(), 
                 this.rectImage.getProgramId(), this.rectImage.getRectId(), 
                 Integer.parseInt(this.selectIDs.split(",")[i]));
               
               imageSource.setPlayTime(this.rectImage.getPlayTime());
               imageSource.setScaleStyle(this.rectImage.getScaleStyle());
               imageSource.setSwapStyle(this.rectImage.getSwapStyle());
               imageSource.setSwapTime(this.rectImage.getSwapTime());
               if (getPluginRectContentHandle().modify(imageSource)) {
                 JsonUtils.writeSuccess(this.servletResponse);
               } else {
                 JsonUtils.writeError(this.servletResponse);
               }
             }
             else
             {
               old = (RectImage)getPluginRectContentHandle().read(this.rectImage.getTaskId(), 
                 this.rectImage.getProgramId(), this.rectImage.getRectId(), 
                 Integer.parseInt(this.selectIDs.split(",")[i]));
               
 
               old.setPlayTime(this.rectImage.getPlayTime());
               old.setScaleStyle(this.rectImage.getScaleStyle());
               old.setSwapStyle(this.rectImage.getSwapStyle());
               old.setSwapTime(this.rectImage.getSwapTime());
               if (!getPluginRectContentHandle().modify(old)) {
                 JsonUtils.writeError(this.servletResponse);
               }
             }
           }
           catch (Exception e)
           {
             JsonUtils.writeError(this.servletResponse);
             e.printStackTrace();
           }
         }
         JsonUtils.writeSuccess(this.servletResponse);
       }
     }
   }
   
   public void ModifyVideo()
   {
     if ((this.selectIDs != null) && (this.selectIDs.length() > 0)) {
       if (this.selectIDs.split(",").length == 1)
       {
         RectVideo old = null;
         RectVideoSource videoSource = null;
         
 
 
         RectContent rectContent = getPluginRectContentHandle().read(this.rectVideo.getTaskId(), 
           this.rectVideo.getProgramId(), this.rectVideo.getRectId(), 
           Integer.parseInt(this.selectIDs.split(",")[0]));
         if (rectContent.getContentType() == 4)
         {
           videoSource = (RectVideoSource)getPluginRectContentHandle()
             .read(this.rectVideo.getTaskId(), 
             this.rectVideo.getProgramId(), this.rectVideo.getRectId(), 
             Integer.parseInt(this.selectIDs.split(",")[0]));
           
           videoSource.setRotation(this.rectVideo.getRotation());
           videoSource.setScaleStyle(this.rectVideo.getScaleStyle());
           videoSource.setPlayTime(this.rectVideo.getPlayTime());
           if (getPluginRectContentHandle().modify(videoSource)) {
             JsonUtils.writeSuccess(this.servletResponse);
           } else {
             JsonUtils.writeError(this.servletResponse);
           }
         }
         else
         {
           old = (RectVideo)getPluginRectContentHandle().read(this.rectVideo.getTaskId(), 
             this.rectVideo.getProgramId(), this.rectVideo.getRectId(), 
             Integer.parseInt(this.selectIDs.split(",")[0]));
           
 
           old.setRotation(this.rectVideo.getRotation());
           old.setScaleStyle(this.rectVideo.getScaleStyle());
           old.setPlayTime(this.rectVideo.getPlayTime());
           if (getPluginRectContentHandle().modify(old)) {
             JsonUtils.writeSuccess(this.servletResponse);
           } else {
             JsonUtils.writeError(this.servletResponse);
           }
         }
       }
       else
       {
         for (int i = 0; i < this.selectIDs.split(",").length; i++) {
           try
           {
             RectVideo old = null;
             RectVideoSource videoSource = null;
             
             RectContent rectContent = getPluginRectContentHandle().read(this.rectVideo.getTaskId(), 
               this.rectVideo.getProgramId(), this.rectVideo.getRectId(), 
               Integer.parseInt(this.selectIDs.split(",")[i]));
             if (rectContent.getContentType() == 4)
             {
               videoSource = (RectVideoSource)getPluginRectContentHandle()
                 .read(this.rectVideo.getTaskId(), 
                 this.rectVideo.getProgramId(), this.rectVideo.getRectId(), 
                 Integer.parseInt(this.selectIDs.split(",")[i]));
               
               videoSource.setRotation(this.rectVideo.getRotation());
               videoSource.setScaleStyle(this.rectVideo.getScaleStyle());
               videoSource.setPlayTime(this.rectVideo.getPlayTime());
               if (getPluginRectContentHandle().modify(videoSource)) {
                 JsonUtils.writeSuccess(this.servletResponse);
               } else {
                 JsonUtils.writeError(this.servletResponse);
               }
             }
             else
             {
               old = (RectVideo)getPluginRectContentHandle().read(this.rectVideo.getTaskId(), 
                 this.rectVideo.getProgramId(), this.rectVideo.getRectId(), 
                 Integer.parseInt(this.selectIDs.split(",")[i]));
               
 
               old.setRotation(this.rectVideo.getRotation());
               old.setScaleStyle(this.rectVideo.getScaleStyle());
               old.setPlayTime(this.rectVideo.getPlayTime());
               if (!getPluginRectContentHandle().modify(old)) {
                 JsonUtils.writeError(this.servletResponse);
               }
             }
           }
           catch (Exception e)
           {
             JsonUtils.writeError(this.servletResponse);
             e.printStackTrace();
           }
         }
         JsonUtils.writeSuccess(this.servletResponse);
       }
     }
   }
   
   public void ModifyContent()
   {
     if ((this.selectIDs != null) && (this.selectIDs.length() > 0)) {
       if (this.selectIDs.split(",").length == 1)
       {
         RectImage old = (RectImage)getPluginRectContentHandle().read(
           this.rectImage.getTaskId(), this.rectImage.getProgramId(), 
           this.rectImage.getRectId(), this.rectImage.getContentId());
         
         old.setPlayTime(this.rectImage.getPlayTime());
         old.setScaleStyle(this.rectImage.getScaleStyle());
         old.setSwapStyle(this.rectImage.getSwapStyle());
         old.setSwapTime(this.rectImage.getSwapTime());
         if (getPluginRectContentHandle().modify(old)) {
           JsonUtils.writeSuccess(this.servletResponse);
         } else {
           JsonUtils.writeError(this.servletResponse);
         }
       }
       else
       {
         for (int i = 0; i < this.selectIDs.split(",").length; i++) {
           try
           {
             RectImage old = (RectImage)getPluginRectContentHandle()
               .read(this.rectImage.getTaskId(), 
               this.rectImage.getProgramId(), 
               this.rectImage.getRectId(), 
               Integer.parseInt(this.selectIDs.split(",")[i]));
             
             old.setPlayTime(this.rectImage.getPlayTime());
             old.setScaleStyle(this.rectImage.getScaleStyle());
             old.setSwapStyle(this.rectImage.getSwapStyle());
             old.setSwapTime(this.rectImage.getSwapTime());
             if (!getPluginRectContentHandle().modify(old)) {
               JsonUtils.writeError(this.servletResponse);
             }
           }
           catch (Exception e)
           {
             JsonUtils.writeError(this.servletResponse);
           }
         }
         JsonUtils.writeSuccess(this.servletResponse);
       }
     }
   }
   
   public String filelocation = "";
   
   public String getFilelocation()
   {
     return this.filelocation;
   }
   
   public void setFilelocation(String filelocation)
   {
     this.filelocation = filelocation;
   }
   
   public String filesize = "";
   
   public String getFilesize()
   {
     return this.filesize;
   }
   
   public void setFilesize(String filesize)
   {
     this.filesize = filesize;
   }
   
   public String fileIDs = "";
   
   public String getFileIDs()
   {
     return this.fileIDs;
   }
   
   public void setFileIDs(String fileIDs)
   {
     this.fileIDs = fileIDs;
   }
   
   public String ToModifyImage()
   {
     FileHandle filehandle = new FileHandle(this);
     CategoryHandle categoryhandle = new CategoryHandle(this);
     if ((this.selectIDs != null) && (this.selectIDs.length() > 0)) {
       if (this.selectIDs.split(",").length == 1)
       {
         RectContent rectContent = getPluginRectContentHandle().read(
           this.taskID, this.programID, this.rectID, 
           Integer.parseInt(this.selectIDs.split(",")[0]));
         if (rectContent.getContentType() == 4)
         {
           RectImageSource imageSource = (RectImageSource)getPluginRectContentHandle()
             .read(this.taskID, this.programID, this.rectID, 
             Integer.parseInt(this.selectIDs.split(",")[0]));
           
           this.rectImage.setFileName(imageSource.getFileName());
           this.rectImage.setPlayTime(imageSource.getPlayTime());
           this.rectImage.setScaleStyle(imageSource.getScaleStyle());
           this.rectImage.setSwapStyle(imageSource.getSwapStyle());
           this.rectImage.setSwapTime(imageSource.getSwapTime());
           this.rectImage.setTaskId(imageSource.getTaskId());
           this.rectImage.setProgramId(imageSource.getProgramId());
           this.rectImage.setRectId(imageSource.getRectId());
           this.rectImage.setContentId(imageSource.getContentId());
           
           this.filelocation = getText("tag");
         }
         else
         {
           this.rectImage = ((RectImage)getPluginRectContentHandle().read(
             this.taskID, this.programID, this.rectID, 
             Integer.parseInt(this.selectIDs.split(",")[0])));
           if ((this.rectImage.getFileId() > 0L) && 
             (this.rectImage.getFileId() < 2147483647L))
           {
             this.filelocation = getText("buildinfile");
           }
           else
           {
             com.gnamp.server.model.File f = filehandle.read(this.rectImage.getFileId());
             this.filesize = 
             
               (String.valueOf(Math.round((float)(f.getSize() / 1024L * 100L)) / 100) + " KB");
             
             List<Category> capath = categoryhandle.readPath(f
               .getCategoryId());
             if ((capath != null) && (capath.size() > 0))
             {
               for (int i = 0; i < capath.size(); i++) {
                 this.filelocation = (this.filelocation + "->" + ((Category)capath.get(i)).getCategoryName());
               }
               this.filelocation = (getText("root") + this.filelocation);
             }
             else
             {
               this.filelocation = getText("root");
             }
           }
         }
       }
       else
       {
         RectContent rectContent = getPluginRectContentHandle().read(
           this.taskID, this.programID, this.rectID, 
           Integer.parseInt(this.selectIDs.split(",")[0]));
         if (rectContent.getContentType() == 4)
         {
           RectImageSource imageSource = (RectImageSource)getPluginRectContentHandle()
             .read(this.taskID, this.programID, this.rectID, 
             Integer.parseInt(this.selectIDs.split(",")[0]));
           
 
           this.rectImage.setTaskId(imageSource.getTaskId());
           this.rectImage.setProgramId(imageSource.getProgramId());
           this.rectImage.setRectId(imageSource.getRectId());
           this.rectImage.setContentId(imageSource.getContentId());
           
           this.rectImage.setPlayTime(imageSource.getPlayTime());
           this.rectImage.setScaleStyle(imageSource.getScaleStyle());
           this.rectImage.setSwapStyle(imageSource.getSwapStyle());
           this.rectImage.setSwapTime(imageSource.getSwapTime());
         }
         else if ((Long.parseLong(this.fileIDs.split(",")[0]) > 0L) && 
           (Long.parseLong(this.fileIDs.split(",")[0]) < 2147483647L))
         {
           this.rectImage = 
             ((RectImage)getPluginRectContentHandle().read(this.taskID, 
             this.programID, 
             this.rectID, 
             Integer.parseInt(this.selectIDs.split(",")[0])));
         }
         else
         {
           this.rectImage = 
             ((RectImage)getPluginRectContentHandle().read(this.taskID, 
             this.programID, 
             this.rectID, 
             Integer.parseInt(this.selectIDs.split(",")[0])));
         }
       }
     }
     return "editimage";
   }
   
   public String ToModifyContent()
   {
     String str = "editcontent";
     FileHandle filehandle = new FileHandle(this);
     CategoryHandle categoryhandle = new CategoryHandle(this);
     if ((this.selectIDs != null) && (this.selectIDs.length() > 0)) {
       if (this.selectIDs.split(",").length == 1)
       {
         RectContent rectcontent = getPluginRectContentHandle().read(
           this.taskID, this.programID, this.rectID, 
           Integer.parseInt(this.selectIDs.split(",")[0]));
         if (rectcontent.getContentType() == 1)
         {
           this.rectImage = ((RectImage)getPluginRectContentHandle().read(
             this.taskID, this.programID, this.rectID, 
             Integer.parseInt(this.selectIDs.split(",")[0])));
         }
         else
         {
           str = "editvideo";
           this.rectVideo = ((RectVideo)getPluginRectContentHandle().read(
             this.taskID, this.programID, this.rectID, 
             Integer.parseInt(this.selectIDs.split(",")[0])));
         }
         if ((this.rectImage.getFileId() > 0L) && 
           (this.rectImage.getFileId() < 2147483647L))
         {
           this.filelocation = getText("buildinfile");
         }
         else
         {
           com.gnamp.server.model.File f = null;
           if (rectcontent.getContentType() == 1) {
             f = filehandle.read(this.rectImage.getFileId());
           } else {
             f = filehandle.read(this.rectVideo.getFileId());
           }
           this.filesize = 
           
             (String.valueOf(Math.round((float)(f.getSize() / 1024L * 100L)) / 100) + " KB");
           
           List<Category> capath = categoryhandle.readPath(f
             .getCategoryId());
           if ((capath != null) && (capath.size() > 0))
           {
             for (int i = 0; i < capath.size(); i++) {
               this.filelocation = (this.filelocation + "->" + ((Category)capath.get(i)).getCategoryName());
             }
             this.filelocation = (getText("root") + this.filelocation);
           }
           else
           {
             this.filelocation = getText("root");
           }
         }
       }
       else
       {
         RectContent rectcontent = getPluginRectContentHandle().read(
           this.taskID, this.programID, this.rectID, 
           Integer.parseInt(this.selectIDs.split(",")[0]));
         if (rectcontent.getContentType() == 1)
         {
           this.rectImage = ((RectImage)getPluginRectContentHandle().read(
             this.taskID, this.programID, this.rectID, 
             Integer.parseInt(this.selectIDs.split(",")[0])));
         }
         else
         {
           str = "editvideo";
           this.rectVideo = ((RectVideo)getPluginRectContentHandle().read(
             this.taskID, this.programID, this.rectID, 
             Integer.parseInt(this.selectIDs.split(",")[0])));
         }
       }
     }
     return str;
   }
   
   public String ToModifyVideo()
   {
     FileHandle filehandle = new FileHandle(this);
     CategoryHandle categoryhandle = new CategoryHandle(this);
     if ((this.selectIDs != null) && (this.selectIDs.length() > 0)) {
       if (this.selectIDs.split(",").length == 1)
       {
         RectContent rectContent = getPluginRectContentHandle().read(
           this.taskID, this.programID, this.rectID, 
           Integer.parseInt(this.selectIDs.split(",")[0]));
         if (rectContent.getContentType() == 4)
         {
           RectVideoSource videoSource = (RectVideoSource)getPluginRectContentHandle()
             .read(this.taskID, this.programID, this.rectID, 
             Integer.parseInt(this.selectIDs.split(",")[0]));
           
           this.rectVideo.setFileName(videoSource.getFileName());
           this.rectVideo.setRotation(videoSource.getRotation());
           this.rectVideo.setScaleStyle(videoSource.getScaleStyle());
           this.rectVideo.setTaskId(videoSource.getTaskId());
           this.rectVideo.setProgramId(videoSource.getProgramId());
           this.rectVideo.setRectId(videoSource.getRectId());
           this.rectVideo.setContentId(videoSource.getContentId());
           
           this.filelocation = getText("tag");
         }
         else
         {
           this.rectVideo = ((RectVideo)getPluginRectContentHandle().read(this.taskID, this.programID, 
             this.rectID, Integer.parseInt(this.selectIDs.split(",")[0])));
           if (this.rectVideo.getFileId() == 201314L)
           {
             this.filelocation = getText("av");
             this.rectVideo.setFileName(getText("avinformation"));
           }
           else
           {
             com.gnamp.server.model.File f = filehandle.read(this.rectVideo.getFileId());
             this.filesize = 
             
               (String.valueOf(Math.round((float)(f.getSize() / 1024L * 100L)) / 100) + " KB");
             
             List<Category> capath = categoryhandle.readPath(f
               .getCategoryId());
             if ((capath != null) && (capath.size() > 0))
             {
               for (int i = 0; i < capath.size(); i++) {
                 this.filelocation = (this.filelocation + "->" + ((Category)capath.get(i)).getCategoryName());
               }
               this.filelocation = (getText("root") + this.filelocation);
             }
             else
             {
               this.filelocation = getText("root");
             }
           }
         }
       }
       else
       {
         RectContent rectContent = getPluginRectContentHandle().read(
           this.taskID, this.programID, this.rectID, 
           Integer.parseInt(this.selectIDs.split(",")[0]));
         if (rectContent.getContentType() == 4)
         {
           RectVideoSource videoSource = (RectVideoSource)getPluginRectContentHandle()
             .read(this.taskID, this.programID, this.rectID, 
             Integer.parseInt(this.selectIDs.split(",")[0]));
           
 
           this.rectVideo.setRotation(videoSource.getRotation());
           this.rectVideo.setScaleStyle(videoSource.getScaleStyle());
           this.rectVideo.setTaskId(videoSource.getTaskId());
           this.rectVideo.setProgramId(videoSource.getProgramId());
           this.rectVideo.setRectId(videoSource.getRectId());
           this.rectVideo.setContentId(videoSource.getContentId());
         }
         else
         {
           this.rectVideo = ((RectVideo)getPluginRectContentHandle().read(this.taskID, this.programID, 
             this.rectID, Integer.parseInt(this.selectIDs.split(",")[0])));
         }
       }
     }
     return "editvideo";
   }
   
   public String ToModifyMusic()
   {
     FileHandle filehandle = new FileHandle(this);
     CategoryHandle categoryhandle = new CategoryHandle(this);
     if ((this.selectIDs != null) && (this.selectIDs.length() > 0)) {
       if (this.selectIDs.split(",").length == 1)
       {
         this.rectMusic = getPluginProgramMusicHandle().read(this.taskID, 
           this.programID, Integer.parseInt(this.selectIDs.split(",")[0]));
         
         com.gnamp.server.model.File f = filehandle.read(this.rectMusic.getFileId());
         this.filesize = 
         
           (String.valueOf(Math.round((float)(f.getSize() / 1024L * 100L)) / 100) + " KB");
         
         List<Category> capath = categoryhandle.readPath(f
           .getCategoryId());
         if ((capath != null) && (capath.size() > 0))
         {
           for (int i = 0; i < capath.size(); i++) {
             this.filelocation = (this.filelocation + "->" + ((Category)capath.get(i)).getCategoryName());
           }
           this.filelocation = (getText("root") + this.filelocation);
         }
         else
         {
           this.filelocation = getText("root");
         }
       }
       else
       {
         this.rectMusic = getPluginProgramMusicHandle().read(this.taskID, 
           this.programID, Integer.parseInt(this.selectIDs.split(",")[0]));
       }
     }
     return "editmusic";
   }
   
   public String filename = "";
   
   public String getFilename()
   {
     return this.filename;
   }
   
   public void setFilename(String filename)
   {
     this.filename = filename;
   }
   
   public String view()
   {
     FileHandle filehandle = new FileHandle(this);
     CategoryHandle categoryhandle = new CategoryHandle(this);
     if ((this.selectIDs != null) && (this.selectIDs.length() > 0) && 
       (this.selectIDs.split(",").length == 1))
     {
       com.gnamp.server.model.File f = filehandle
         .read(Long.parseLong(this.selectIDs.split(",")[0]));
       this.filesize = 
       
         (String.valueOf(Math.round((float)(f.getSize() / 1024L * 100L)) / 100) + " KB");
       this.filename = f.getFileName();
       
       List<Category> capath = categoryhandle.readPath(f
         .getCategoryId());
       if ((capath != null) && (capath.size() > 0))
       {
         for (int i = 0; i < capath.size(); i++) {
           this.filelocation = (this.filelocation + "->" + ((Category)capath.get(i)).getCategoryName());
         }
         this.filelocation = (getText("root") + this.filelocation);
       }
       else
       {
         this.filelocation = getText("root");
       }
     }
     return "viewfile";
   }
   
   public void av()
   {
     try
     {
       RectVideo rectvideo = new RectVideo();
       
       rectvideo.setFileName(getText("avinformation"));
       rectvideo.setFileId(201314L);
       
       rectvideo.setProgramId(this.programID);
       rectvideo.setRectId(this.rectID);
       rectvideo.setSequenceNum(2147483647);
       rectvideo.setTaskId(this.taskID);
       rectvideo.setScaleStyle(3);
       rectvideo.setRotation(0);
       rectvideo.setAv(1);
       
       getPluginRectContentHandle().create(rectvideo);
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
     JsonUtils.writeSuccess(this.servletResponse);
   }
   
   public Integer RequestW = Integer.valueOf(0);
   
   public Integer getRequestW()
   {
     return this.RequestW;
   }
   
   public void setRequestW(Integer requestW)
   {
     this.RequestW = requestW;
   }
   
   public Integer getRequestH()
   {
     return this.RequestH;
   }
   
   public void setRequestH(Integer requestH)
   {
     this.RequestH = requestH;
   }
   
   public Integer RequestH = Integer.valueOf(0);
   public String innerHtml = "";
   public int bid;
   public int rectid;
   
   public String getInnerHtml()
   {
     return this.innerHtml;
   }
   
   public void setInnerHtml(String innerHtml)
   {
     this.innerHtml = innerHtml;
   }
   
   public String showprogram()
   {
     this.servletResponse.setHeader("Pragma", "No-cache");
     this.servletResponse.setHeader("Cache-Control", "no-cache");
     this.servletResponse.setHeader("Cache-Control", "no-store");
     this.servletResponse.setDateHeader("Expires", 0L);
     try
     {
       List<Rect> allArects = getPluginRectHandle().readAll(this.taskID, 
         this.programID);
       
       ImageRect[] imageRect = null;
       VideoRect[] videorect = null;
       DateRect[] dateRect = null;
       WeatherTemperatureRect[] temprect = null;
       WeatherTextRect[] wtrect = null;
       WeatherLogoRect[] wlRect = null;
       WeatherTitleRect[] wdrect = null;
       TimeRect[] timerect = null;
       WeekRect[] weekrect = null;
       SLScrollTRect[] slscroolrect = null;
       SLStaticTRect[] slstaticrect = null;
       SLFlipTRect[] slfliprect = null;
       MLFlipTRect[] mlfliprect = null;
       MLScrollTRect[] mlscroolrect = null;
       MLStaticTRect[] mlstaticrect = null;
       HybridRect[] hybridrect = null;
       DeviceRect[] devicerect = null;
       LogoRect[] logorect = null;
       
       List<ImageRect> imagelist = new ArrayList();
       List<VideoRect> videolist = new ArrayList();
       List<DateRect> datelist = new ArrayList();
       List<WeatherTemperatureRect> wtmplist = new ArrayList();
       List<WeatherTextRect> wtextlist = new ArrayList();
       List<WeatherLogoRect> wlogolist = new ArrayList();
       List<WeatherTitleRect> wtitlelist = new ArrayList();
       List<TimeRect> timelist = new ArrayList();
       List<WeekRect> weeklist = new ArrayList();
       List<SLScrollTRect> slscrolllist = new ArrayList();
       List<SLStaticTRect> slstaticlist = new ArrayList();
       List<SLFlipTRect> slfliplist = new ArrayList();
       List<MLFlipTRect> mlfliplist = new ArrayList();
       List<MLScrollTRect> mlscrolllist = new ArrayList();
       List<MLStaticTRect> mlstaticlist = new ArrayList();
       List<HybridRect> hybridlist = new ArrayList();
       List<DeviceRect> devicelist = new ArrayList();
       List<LogoRect> logolist = new ArrayList();
       List<WebRect> weblist = new ArrayList();
       List<FlashRect> flashlist = new ArrayList();
       List<AvInRect> avinlist = new ArrayList();
       List<CameraRect> cameralist = new ArrayList();
       for (int i = 0; i < allArects.size(); i++) {
         switch (((Rect)allArects.get(i)).getRecType())
         {
         case 1: 
           imagelist.add((ImageRect)allArects.get(i));
           break;
         case 2: 
           videolist.add((VideoRect)allArects.get(i));
           break;
         case 3: 
           datelist.add((DateRect)allArects.get(i));
           break;
         case 4: 
           timelist.add((TimeRect)allArects.get(i));
           break;
         case 5: 
           weeklist.add((WeekRect)allArects.get(i));
           break;
         case 8: 
           wlogolist.add((WeatherLogoRect)allArects.get(i));
           break;
         case 7: 
           wtextlist.add((WeatherTextRect)allArects.get(i));
           break;
         case 9: 
           wtitlelist.add((WeatherTitleRect)allArects.get(i));
           break;
         case 6: 
           wtmplist.add((WeatherTemperatureRect)allArects.get(i));
           break;
         case 12: 
           slfliplist.add((SLFlipTRect)allArects.get(i));
           break;
         case 13: 
           slscrolllist.add((SLScrollTRect)allArects.get(i));
           break;
         case 11: 
           slstaticlist.add((SLStaticTRect)allArects.get(i));
           break;
         case 15: 
           mlfliplist.add((MLFlipTRect)allArects.get(i));
           break;
         case 16: 
           mlscrolllist.add((MLScrollTRect)allArects.get(i));
           break;
         case 14: 
           mlstaticlist.add((MLStaticTRect)allArects.get(i));
           break;
         case 17: 
           hybridlist.add((HybridRect)allArects.get(i));
           break;
         case 18: 
           devicelist.add((DeviceRect)allArects.get(i));
           break;
         case 19: 
           logolist.add((LogoRect)allArects.get(i));
           break;
         case 20: 
           if ((allArects.get(i) instanceof WebRect)) {
             weblist.add((WebRect)allArects.get(i));
           }
           break;
         case 21: 
           if ((allArects.get(i) instanceof FlashRect)) {
             flashlist.add((FlashRect)allArects.get(i));
           }
           break;
         case 22: 
           if ((allArects.get(i) instanceof AvInRect)) {
             avinlist.add((AvInRect)allArects.get(i));
           }
           break;
         case 23: 
           if ((allArects.get(i) instanceof CameraRect)) {
             cameralist.add((CameraRect)allArects.get(i));
           }
           break;
         }
       }
       imageRect = new ImageRect[imagelist.size()];
       imagelist.toArray(imageRect);
       
       videorect = new VideoRect[videolist.size()];
       videolist.toArray(videorect);
       
       dateRect = new DateRect[datelist.size()];
       datelist.toArray(dateRect);
       
       weekrect = new WeekRect[weeklist.size()];
       weeklist.toArray(weekrect);
       
       timerect = new TimeRect[timelist.size()];
       timelist.toArray(timerect);
       
       wdrect = new WeatherTitleRect[wtitlelist.size()];
       wtitlelist.toArray(wdrect);
       
       wlRect = new WeatherLogoRect[wlogolist.size()];
       wlogolist.toArray(wlRect);
       
       wtrect = new WeatherTextRect[wtextlist.size()];
       wtextlist.toArray(wtrect);
       
       temprect = new WeatherTemperatureRect[wtmplist.size()];
       wtmplist.toArray(temprect);
       
       slfliprect = new SLFlipTRect[slfliplist.size()];
       slfliplist.toArray(slfliprect);
       
       slscroolrect = new SLScrollTRect[slscrolllist.size()];
       slscrolllist.toArray(slscroolrect);
       
       slstaticrect = new SLStaticTRect[slstaticlist.size()];
       slstaticlist.toArray(slstaticrect);
       
       mlfliprect = new MLFlipTRect[mlfliplist.size()];
       mlfliplist.toArray(mlfliprect);
       
       mlscroolrect = new MLScrollTRect[mlscrolllist.size()];
       mlscrolllist.toArray(mlscroolrect);
       
       mlstaticrect = new MLStaticTRect[mlstaticlist.size()];
       mlstaticlist.toArray(mlstaticrect);
       
       logorect = new LogoRect[logolist.size()];
       logolist.toArray(logorect);
       
 
 
 
 
 
       this.innerHtml = (this.innerHtml + "<div style=\"border: thin solid Black; width:" + this.RequestW.intValue() * 540 / this.RequestH.intValue() + "px; background-color: GrayText; height:" + 540 + "px; top: " + 10 + "px; left: " + (980 - this.RequestW.intValue() * 540 / this.RequestH.intValue()) / 2 + "px; position: absolute;\">");
       int W;
       if ((imageRect != null) && (imageRect.length > 0)) {
         for (int i = 0; i < imageRect.length; i++)
         {
           this.innerHtml = (this.innerHtml + "<div style=\"width:" + imageRect[i].getWidth() * 540 / this.RequestH.intValue() + "px; height:" + imageRect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + imageRect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + imageRect[i].getPosX() * 540 / this.RequestH.intValue() + "px; position: absolute;\">");
           
           RectImage[] imagefiles = null;
           List<RectContent> contentlist = getPluginRectContentHandle()
             .readAll(this.taskID, this.programID, 
             imageRect[i].getRectId());
           
           List<RectImage> templist = new ArrayList();
           List<RectImage> removelist = new ArrayList();
           if ((contentlist != null) && (contentlist.size() > 0))
           {
             for (RectContent r : contentlist) {
               if (r.getContentType() == 1)
               {
                 if (r.getFileId() > 2147483647L)
                 {
                   com.gnamp.server.model.File f = getFileHandle().read(r.getFileId());
                   if (f.getFlag() == 1)
                   {
                     List<CombinFile> combinlist = getCombinFileHandle().readAll(r.getFileId());
                     for (int b = 0; b < combinlist.size(); b++)
                     {
                       RectImage rc = new RectImage();
                       RectImage newrc = (RectImage)r;
                       rc.setFileId(((CombinFile)combinlist.get(b)).getSegmentId());
                       rc.setFileName(((CombinFile)combinlist.get(b)).getFileName());
                       rc.setPlayTime(newrc.getPlayTime());
                       rc.setScaleStyle(newrc.getScaleStyle());
                       rc.setSwapStyle(newrc.getSwapStyle());
                       rc.setSwapTime(newrc.getSwapTime());
                       
                       templist.add(rc);
                     }
                     removelist.add((RectImage)r);
                   }
                 }
                 templist.add((RectImage)r);
               }
             }
             templist.removeAll(removelist);
           }
           imagefiles = new RectImage[templist.size()];
           templist.toArray(imagefiles);
           if ((imagefiles != null) && (imagefiles.length > 0))
           {
             Storage mgr = new Storage(getCstmId());
             
             String pic = "<script language=\"javascript\" type=\"text/javascript\">var bannerAD" + 
               i + "=new Array();" + 
               "var bannerWIDTH" + i + "=new Array();" + 
               "var bannerHEIGHT" + i + "=new Array();" + 
               "var bannerSCALE" + i + "=new Array();" + 
               "var adNum" + i + "=0;";
             for (int l = 0; l < imagefiles.length; l++)
             {
               String path = "";
               if ((imagefiles[l].getFileId() > 0L) && 
                 (imagefiles[l].getFileId() < 2147483647L)) {
                 path = 
                 
 
 
                   Storage.getWorkRootPath() + "buildin" + java.io.File.separatorChar + "watermark" + java.io.File.separatorChar + this.fileId + ".jpg";
               } else if (mgr != null) {
                 try
                 {
                   int flag = getFileHandle().read(imagefiles[l].getFileId()).getFlag();
                   path = mgr.getFilePath(imagefiles[l].getFileId());
                 }
                 catch (Exception w)
                 {
                   path = mgr.getCombinFilePath(imagefiles[l].getFileId());
                 }
               }
               int FW = imageRect[i].getWidth() * 540 / this.RequestH.intValue();
               int FH = imageRect[i].getHeight() * 540 / this.RequestH.intValue();
               int oldW = FW;
               int oldH = FH;
               try
               {
                 Dimension size = ImageUtil.getPix(path);
                 oldW = size.width;
                 oldH = size.height;
               }
               catch (Exception localException1) {}
               if ((imagefiles[l].getFileId() > 0L) && 
                 (imagefiles[l].getFileId() < 2147483647L)) {
                 pic = 
                 
 
 
 
 
 
 
                   pic + "bannerAD" + i + "[" + l + "]=\"templet!watermark?fileId=" + imagefiles[l].getFileId() + "\";" + "bannerWIDTH" + i + "[" + l + "]=" + oldW + ";" + "bannerSCALE" + i + "[" + l + "]=" + imagefiles[l].getScaleStyle() + ";" + "bannerHEIGHT" + i + "[" + l + "]=" + oldH + ";";
               } else {
                 pic = 
                 
 
 
 
 
 
 
                   pic + "bannerAD" + i + "[" + l + "]=\"pluginrect!preview?fileId=" + imagefiles[l].getFileId() + "\";" + "bannerWIDTH" + i + "[" + l + "]=" + oldW + ";" + "bannerSCALE" + i + "[" + l + "]=" + imagefiles[l].getScaleStyle() + ";" + "bannerHEIGHT" + i + "[" + l + "]=" + oldH + ";";
               }
               pic = 
               
 
 
 
                 pic + "if(bannerSCALE" + i + "[" + l + "]==3){" + "bannerWIDTH" + i + "[" + l + "]=" + FW + ";" + "bannerHEIGHT" + i + "[" + l + "]=" + FH + ";" + "}";
               
               pic = pic + "if(bannerSCALE" + i + 
                 "[" + 
                 l + 
                 "] == 2){" + 
                 
                 "bannerHEIGHT" + i + "[" + l + 
                 "]=bannerHEIGHT" + i + "[" + l + "]*540/" + 
                 this.RequestH + ";" + "bannerWIDTH" + i + "[" + 
                 l + "]=bannerWIDTH" + i + "[" + l + 
                 "]*540/" + this.RequestH + ";" + "}";
             }
             pic = 
             
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
               pic + "function setTransition(){if (document.all){bannerADrotator" + i + ".filters.revealTrans.Transition=Math.floor(Math.random()*23);" + "bannerADrotator" + i + ".filters.revealTrans.apply();" + "}" + "}" + "function playTransition(){" + " if (document.all)" + "bannerADrotator" + i + ".filters.revealTrans.play();" + "}" + "function nextAd" + i + "(){" + "if(adNum" + i + "< 0 || adNum" + i + ">=bannerAD" + i + ".length)" + "adNum" + i + "=0;" + "setTransition();";
             
             W = imageRect[i].getWidth() * 540 / this.RequestH.intValue();
             int H = imageRect[i].getHeight() * 540 / this.RequestH.intValue();
             
             pic = pic + "var width" + i + " = " + W + ";" + "var height" + 
               i + 
               " = " + 
               H + 
               ";" + 
               
 
               "if(bannerSCALE" + 
               i + 
               "[adNum" + 
               i + 
               "] == 1){" + 
               
               "if (bannerWIDTH" + i + "[adNum" + i + "] / " + 
               W + "> bannerHEIGHT" + i + "[adNum" + i + 
               "] / " + H + ")" + "{" + "height" + i + 
               " = (bannerHEIGHT" + i + "[adNum" + i + 
               "] * " + W + ") / bannerWIDTH" + i + "[adNum" + 
               i + "]" + ";" + "}" + "else" + "{" + "width" + 
               i + " = (bannerWIDTH" + i + "[adNum" + i + 
               "] * " + H + ") / bannerHEIGHT" + i + 
               "[adNum" + i + "]" + ";" + "}";
             
             pic = pic + "width" + i + " = (width" + i + " >= " + W + 
               " ? " + W + " : width" + i + ");" + "height" + 
               i + " = (height" + i + " >= " + H + " ? " + H + 
               " : height" + i + ");" + "}";
             
             pic = pic + "if (bannerSCALE" + 
               i + 
               "[adNum" + 
               i + 
               "] == 2)" + 
               
               "{" + "document.images.bannerADrotator" + i + 
               ".src=bannerAD" + i + "[adNum" + i + "];" + 
               "document.images.bannerADrotator" + i + 
               ".width=bannerWIDTH" + i + "[adNum" + 
               i + 
               "];" + 
               "document.images.bannerADrotator" + 
               i + 
               ".height=bannerHEIGHT" + 
               i + 
               "[adNum" + 
               i + 
               "];" + 
               "document.images.bannerADrotator" + 
               i + 
               ".style.position=\"absolute\";" + 
               "document.images.bannerADrotator" + 
               i + 
               ".style.top=(-(bannerHEIGHT" + 
               i + 
               "[adNum" + 
               i + 
               "]-" + 
               H + 
               ")/2)+\"px\";" + 
               "document.images.bannerADrotator" + 
               i + 
               ".style.left=(-(bannerWIDTH" + 
               i + 
               "[adNum" + 
               i + 
               "]-" + 
               W + 
               ")/2)+\"px\";" + 
               
 
               "}else if(bannerSCALE" + 
               i + 
               "[adNum" + 
               i + 
               "] == 3){" + 
               
               "document.images.bannerADrotator" + i + 
               ".src=bannerAD" + i + "[adNum" + i + "];" + 
               "document.images.bannerADrotator" + i + 
               ".width=bannerWIDTH" + i + 
               "[adNum" + 
               i + 
               "];" + 
               "document.images.bannerADrotator" + 
               i + 
               ".height=bannerHEIGHT" + 
               i + 
               "[adNum" + 
               i + 
               "];" + 
               "document.images.bannerADrotator" + 
               i + 
               ".style.top=0+\"px\";" + 
               "document.images.bannerADrotator" + 
               i + 
               ".style.left=0+\"px\";" + 
               
 
               "}else if(bannerSCALE" + 
               i + 
               "[adNum" + 
               i + 
               "] == 1){" + 
               
               "document.images.bannerADrotator" + i + 
               ".src=bannerAD" + i + "[adNum" + i + "];" + 
               "document.images.bannerADrotator" + i + 
               ".width=width" + i + ";" + 
               "document.images.bannerADrotator" + i + 
               ".height=height" + i + ";" + 
               "document.images.bannerADrotator" + i + 
               ".style.position=\"absolute\";" + 
               "document.images.bannerADrotator" + i + 
               ".style.top=(height" + i + " < " + H + 
               " ? (((" + H + " - height" + i + 
               ") / 2)) : 0)+\"px\";" + 
               "document.images.bannerADrotator" + i + 
               ".style.left=(width" + i + " < " + W + 
               " ? (((" + W + " - width" + i + 
               ") / 2)) : 0)+\"px\";" + "}";
             
             pic = pic + "playTransition();adNum" + 
             
               i + 
               "++;" + 
               "theTimer=setTimeout(\"nextAd" + 
               i + 
               "()\", 5000);" + 
               
               "}" + 
               "</script>" + 
               "<div id=\"Div" + 
               i + 
               "\" style=\"text-align:center; background-color: Black; position:relative; overflow:hidden; vertical-align:middle; width: " + 
               imageRect[i].getWidth() * 
               540 / 
               this.RequestH.intValue() + 
               "px; height: " + 
               imageRect[i].getHeight() * 
               540 / 
               this.RequestH.intValue() + 
               "px;\">" + 
               "<img style=\"filter: revealTrans(duration=0,transition=20);\" src=\"\" name=\"bannerADrotator" + 
               i + 
               "\" />" + 
               "</div>" + 
               "<script language=\"javascript\" type=\"text/javascript\">nextAd" + 
               i + "()</script>";
             
             this.innerHtml = (this.innerHtml + pic + "</div>");
           }
           else
           {
             this.innerHtml += "</div>";
           }
         }
       }
       String pathTodoFile;
       if ((videorect != null) && (videorect.length > 0)) {
         for (int i = 0; i < videorect.length; i++)
         {
           long fileid = 0L;
           RectVideo[] videofiles = null;
           
           List<RectContent> contentlist = getPluginRectContentHandle()
             .readAll(this.taskID, this.programID, 
             videorect[i].getRectId());
           
           List<RectVideo> templist = new ArrayList();
           if ((contentlist != null) && (contentlist.size() > 0)) {
             for (RectContent r : contentlist) {
               if (r.getContentType() == 2) {
                 templist.add((RectVideo)r);
               }
             }
           }
           videofiles = new RectVideo[templist.size()];
           templist.toArray(videofiles);
           if ((videofiles != null) && (videofiles.length > 0)) {
             fileid = videofiles[0].getFileId();
           }
           Storage mgr = new Storage(getCstmId());
           
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
           this.innerHtml = (this.innerHtml + "<div id=\"Divv" + i + "\" style=\"width:" + videorect[i].getWidth() * 540 / this.RequestH.intValue() + "px; height:" + videorect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + videorect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + videorect[i].getPosX() * 540 / this.RequestH.intValue() + "px; background-color: Black; position: absolute;\">");
           if (fileid > 0L)
           {
             this.innerHtml = (this.innerHtml + "<table id=\"Tablev" + i + "\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">" + "<tr>");
             
 
             getFileHandle();pathTodoFile = mgr.getVideoPreviewPathes(fileid, FileHandle.VIDEO_PREVIEW_NUM)[0];
             
             int FW = videorect[i].getWidth() * 540 / this.RequestH.intValue();
             int FH = videorect[i].getHeight() * 540 / this.RequestH.intValue();
             
             int width = FW;
             int height = FH;
             
             Dimension size = new Dimension();
             try
             {
               size = ImageUtil.getPix(pathTodoFile);
               if (size.width / FW > size.height / FH) {
                 height = size.height * FW / size.width;
               } else {
                 width = size.width * FH / size.height;
               }
             }
             catch (Exception localException2) {}
             for (int p = 0; p < 5; p++)
             {
               this.innerHtml += "<td>";
               if (videofiles[0].getScaleStyle() == 1)
               {
                 getFileHandle();
                 this.innerHtml = (this.innerHtml + "<img style=\"margin-top:" + (height < FH ? String.valueOf((FH - height) / 2 + 1) : "0") + "px; margin-left:" + (width < FW ? String.valueOf((FW - width) / 2) : "0") + "px\" width=\"" + width + "px\" height=\"" + height + "px\" src=\"looprect!play?getAddress=" + mgr.getVideoPreviewPathes(fileid, FileHandle.VIDEO_PREVIEW_NUM)[p] + "\" />");
               }
               else
               {
                 getFileHandle();
                 this.innerHtml = (this.innerHtml + "<img style=\"margin-top:0px; margin-left:0px\" width=\"" + FW + "px\" height=\"" + FH + "px\" src=\"looprect!play?getAddress=" + mgr.getVideoPreviewPathes(fileid, FileHandle.VIDEO_PREVIEW_NUM)[p] + "\" />");
               }
               this.innerHtml += "</td>";
             }
             this.innerHtml += "</tr></table>";
           }
           this.innerHtml += "</div>";
           if (fileid > 0L) {
             this.innerHtml = (this.innerHtml + " <script type=\"text/javascript\"> new SlideTransv(\"Divv" + i + "\", \"Tablev" + i + "\", " + 5 + ", { Vertical: false }).Run(); " + "</script>");
           }
         }
       }
       if ((logorect != null) && (logorect.length > 0)) {
         for (int i = 0; i < logorect.length; i++)
         {
           long fileid = 0L;
           RectImage[] logofiles = null;
           List<RectContent> contentlist = getPluginRectContentHandle()
             .readAll(this.taskID, this.programID, 
             logorect[i].getRectId());
           
           List<RectImage> templist = new ArrayList();
           if ((contentlist != null) && (contentlist.size() > 0)) {
             for (RectContent r : contentlist) {
               if (r.getContentType() == 1) {
                 templist.add((RectImage)r);
               }
             }
           }
           logofiles = new RectImage[templist.size()];
           templist.toArray(logofiles);
           if ((logofiles != null) && (logofiles.length > 0)) {
             fileid = logofiles[0].getFileId();
           }
           Storage mgr = new Storage(getCstmId());
           
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
           this.innerHtml = (this.innerHtml + "<div id=\"Divv" + i + "\" style=\"width:" + logorect[i].getWidth() * 540 / this.RequestH.intValue() + "px; height:" + logorect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + logorect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + logorect[i].getPosX() * 540 / this.RequestH.intValue() + "px; position: absolute;\">");
           if (fileid > 0L)
           {
             String path = mgr.getFilePath(fileid);
             
             int FW = logorect[i].getWidth() * 540 / this.RequestH.intValue();
             int FH = logorect[i].getHeight() * 540 / this.RequestH.intValue();
             
             int width = FW;
             int height = FH;
             
             Dimension size = new Dimension();
             try
             {
               size = ImageUtil.getPix(path);
               if (size.width / FW > size.height / FH) {
                 height = size.height * FW / size.width;
               } else {
                 width = size.width * FH / size.height;
               }
             }
             catch (Exception localException3) {}
             if (logofiles[0].getScaleStyle() == 1) {
               this.innerHtml = (this.innerHtml + "<img style=\"margin-top:" + (height < FH ? String.valueOf((FH - height) / 2 + 1) : "0") + "px; margin-left:" + (width < FW ? String.valueOf((FW - width) / 2) : "0") + "px\" width=\"" + width + "px\" height=\"" + height + "px\" src=\"looprect!play?getAddress=" + path + "\" />");
             } else if (logofiles[0].getScaleStyle() == 3) {
               this.innerHtml = (this.innerHtml + "<img style=\"margin-top:0px; margin-left:0px\" width=\"" + FW + "px\" height=\"" + FH + "px\" src=\"looprect!play?getAddress=" + path + "\" />");
             } else {
               this.innerHtml = (this.innerHtml + "<img style=\"margin-top:" + (height < FH ? String.valueOf((FH - height) / 2 + 1) : "0") + "px; margin-left:" + (width < FW ? String.valueOf((FW - width) / 2) : "0") + "px\" width=\"" + width + "px\" height=\"" + height + "px\" src=\"looprect!play?getAddress=" + path + "\" />");
             }
           }
           this.innerHtml += "</div>";
         }
       }
       if ((slfliprect != null) && (slfliprect.length > 0)) {
         for (int i = 0; i < slfliprect.length; i++)
         {
           int count = NextPicCount(0, slfliprect[i].getRectId(), slfliprect[i].getTextType());
           
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
           this.innerHtml = (this.innerHtml + "<div style=\"width:" + slfliprect[i].getWidth() * 540 / this.RequestH.intValue() + "px; height:" + slfliprect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + slfliprect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + slfliprect[i].getPosX() * 540 / this.RequestH.intValue() + "px;\">" + "<div id=\"Marquee" + i + "\" style=\"margin:0px; width:" + slfliprect[i].getWidth() * 540 / this.RequestH.intValue() + "px; padding:0px; height:" + slfliprect[i].getHeight() * 540 / this.RequestH.intValue() + "px; font-size:" + slfliprect[i].getHeight() * 540 / this.RequestH.intValue() * 0.85D + "px;\">");
           for (int c = 0; c < count; c++) {
             this.innerHtml = (this.innerHtml + "<img style=\"width:" + slfliprect[i].getWidth() * 540 / this.RequestH.intValue() + "px; height:" + slfliprect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + slfliprect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + slfliprect[i].getPosX() * 540 / this.RequestH.intValue() + "px;\" src=\"pluginrect!NextPic?bid=" + (c + 1) + "&taskID=" + this.taskID + "&programID=" + this.programID + "&rectid=" + slfliprect[i].getRectId() + "&texttype=" + slfliprect[i].getTextType() + "&date=" + new Date() + "\" />");
           }
           this.innerHtml += "</div></div>";
           
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
           this.innerHtml = (this.innerHtml + "<script type=\"text/javascript\"> new Marquee({MSClassID: \"Marquee" + i + "\"," + " Direction: \"top\"," + " Step: " + slfliprect[i].getHeight() * 540 / this.RequestH.intValue() + "," + " Width: " + slfliprect[i].getWidth() * 540 / this.RequestH.intValue() + "," + " Height: " + slfliprect[i].getHeight() * 540 / this.RequestH.intValue() + "," + " Timer: 50," + " DelayTime: " + slfliprect[i].getShowTime() * 1000 + "," + " WaitTime: " + slfliprect[i].getShowTime() * 1000 + "," + " ScrollStep: " + slfliprect[i].getHeight() * 540 / this.RequestH.intValue() + "," + " AutoStart: 1" + "}); " + "</script>");
         }
       }
       if ((slscroolrect != null) && (slscroolrect.length > 0)) {
         for (int i = 0; i < slscroolrect.length; i++)
         {
           int count = NextPicCount(0, slscroolrect[i].getRectId(), slscroolrect[i].getTextType());
           String strHexBClr = "";
           strHexBClr = String.format("#%06X", new Object[] {
             Integer.valueOf(slscroolrect[i].getBackColor().getRGB() & 0xFFFFFF) });
           
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
           this.innerHtml = (this.innerHtml + "<div style=\"width:" + slscroolrect[i].getWidth() * 540 / this.RequestH.intValue() + "px; height:" + slscroolrect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + slscroolrect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + slscroolrect[i].getPosX() * 540 / this.RequestH.intValue() + "px; position: absolute;" + (slscroolrect[i].getBackColor().equals(Color.BLACK) ? " " : new StringBuilder(" background-color:").append(strHexBClr).toString()) + " \">" + "<marquee speed=\"" + slscroolrect[i].getScrollSpeed() + "\" behavior=\"scroll\" direction=\"left\"><div style=\"margin:0px; width:" + slscroolrect[i].getWidth() * 540 / this.RequestH.intValue() + "px; padding:0px; height:" + slscroolrect[i].getHeight() * 540 / this.RequestH.intValue() + "px; font-size:" + slscroolrect[i].getHeight() * 540 / this.RequestH.intValue() * 0.85D + "px;\">");
           for (int c = 0; c < count; c++) {
             this.innerHtml = (this.innerHtml + "<img style=\"width:" + slscroolrect[i].getWidth() * 540 / this.RequestH.intValue() + "px; height:" + slscroolrect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + slscroolrect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + slscroolrect[i].getPosX() * 540 / this.RequestH.intValue() + "px;\" src=\"pluginrect!NextPic?bid=" + (c + 1) + "&taskID=" + this.taskID + "&programID=" + this.programID + "&rectid=" + slscroolrect[i].getRectId() + "&texttype=" + slscroolrect[i].getTextType() + "&date=" + new Date() + "\" />");
           }
           this.innerHtml += "</div></marquee></div>";
         }
       }
       if ((slstaticrect != null) && (slstaticrect.length > 0)) {
         for (int i = 0; i < slstaticrect.length; i++) {
           this.innerHtml = (this.innerHtml + "<div style=\"width:" + slstaticrect[i].getWidth() * 540 / this.RequestH.intValue() + "px; height:" + slstaticrect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + slstaticrect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + slstaticrect[i].getPosX() * 540 / this.RequestH.intValue() + "px; position: absolute;\">" + "<div style=\"margin:0px; overflow: hidden; width:" + slstaticrect[i].getWidth() * 540 / this.RequestH.intValue() + "px; padding:0px; height:" + slstaticrect[i].getHeight() * 540 / this.RequestH.intValue() + "px; font-size:" + slstaticrect[i].getHeight() * 540 / this.RequestH.intValue() * 0.85D + "px;\">" + "<img style=\"margin:0px; overflow: hidden; width:" + slstaticrect[i].getWidth() * 540 / this.RequestH.intValue() + "px; padding:0px; height:" + slstaticrect[i].getHeight() * 540 / this.RequestH.intValue() + "px;\" src=\"pluginrect!NextPic?bid=" + 1 + "&taskID=" + this.taskID + "&programID=" + this.programID + "&rectid=" + slstaticrect[i].getRectId() + "&texttype=" + slstaticrect[i].getTextType() + "&date=" + new Date() + "\" />" + "</div>" + "</div>");
         }
       }
       if ((mlfliprect != null) && (mlfliprect.length > 0)) {
         for (int i = 0; i < mlfliprect.length; i++)
         {
           int count = stringToPicCount(0, mlfliprect[i].getRectId(), mlfliprect[i].getTextType());
           
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
           this.innerHtml = (this.innerHtml + "<div id=\"demo\" style=\"width:" + mlfliprect[i].getWidth() * 540 / this.RequestH.intValue() + "px; height:" + mlfliprect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + mlfliprect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + mlfliprect[i].getPosX() * 540 / this.RequestH.intValue() + "px; overflow:hidden; position: relative;\">" + "<div id=\"demo1\" style=\" position: relative; font-size:" + mlfliprect[i].getFontSize() + "px\">");
           for (int c = 0; c < count; c++) {
             this.innerHtml = (this.innerHtml + "<img style=\"width:" + mlfliprect[i].getWidth() * 540 / this.RequestH.intValue() + "px; height:" + mlfliprect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + mlfliprect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + mlfliprect[i].getPosX() * 540 / this.RequestH.intValue() + "px;\" src=\"pluginrect!stringToPic?bid=" + (c + 1) + "&taskID=" + this.taskID + "&programID=" + this.programID + "&rectid=" + mlfliprect[i].getRectId() + "&texttype=" + mlfliprect[i].getTextType() + "&date=" + new Date() + "\" />");
           }
           this.innerHtml = (this.innerHtml + "</div></div><script type=\"text/javascript\">var speed=" + mlfliprect[i].getShowTime() * 1000 + ";" + "function scrollit()" + "{" + "var obj=document.getElementById(\"demo1\");" + "obj.style.posTop=obj.style.posTop-" + mlfliprect[i].getHeight() * 540 / this.RequestH.intValue() + ";" + "var offH=obj.style.posTop+obj.offsetHeight;" + "if (offH<0)" + "{" + "obj.style.posTop=0;" + "}" + "}" + "setInterval(scrollit,speed);" + "</script>");
         }
       }
       if ((mlscroolrect != null) && (mlscroolrect.length > 0)) {
         for (int i = 0; i < mlscroolrect.length; i++)
         {
           int count = stringToPicCount(0, mlscroolrect[i].getRectId(), mlscroolrect[i].getTextType());
           String strHexBClr = "";
           strHexBClr = String.format("#%06X", new Object[] {
             Integer.valueOf(mlscroolrect[i].getBackColor().getRGB() & 0xFFFFFF) });
           
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
           this.innerHtml = (this.innerHtml + "<div id=\"maq" + (i + 1) + "\" style=\"width:" + mlscroolrect[i].getWidth() * 540 / this.RequestH.intValue() + "px; height:" + mlscroolrect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + mlscroolrect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + mlscroolrect[i].getPosX() * 540 / this.RequestH.intValue() + "px; overflow:hidden; position: absolute;\">" + "<div id=\"mtext" + (i + 1) + "\" style=\"top: " + mlscroolrect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + mlscroolrect[i].getPosX() * 540 / this.RequestH.intValue() + "px; font-size:" + mlscroolrect[i].getFontSize() + "px; " + (mlscroolrect[i].getBackColor().equals(Color.BLACK) ? " " : new StringBuilder(" background-color:").append(strHexBClr).toString()) + " \">");
           for (int c = 0; c < count; c++) {
             this.innerHtml = (this.innerHtml + "<img style=\"width:" + mlscroolrect[i].getWidth() * 540 / this.RequestH.intValue() + "px; height:" + mlscroolrect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + mlscroolrect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + mlscroolrect[i].getPosX() * 540 / this.RequestH.intValue() + "px;\" src=\"pluginrect!stringToPic?bid=" + (c + 1) + "&taskID=" + this.taskID + "&programID=" + this.programID + "&rectid=" + mlscroolrect[i].getRectId() + "&texttype=" + mlscroolrect[i].getTextType() + "&date=" + new Date() + "\" />");
           }
           this.innerHtml = (this.innerHtml + "</div><div id=\"mv" + (i + 1) + "\" style=\"top: " + mlscroolrect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + mlscroolrect[i].getPosX() * 540 / this.RequestH.intValue() + "px; font-size:" + mlscroolrect[i].getFontSize() + "px\"></div>" + "</div>" + "<script>" + "var speed=" + (5 - mlscroolrect[i].getScrollSpeed() + 1) * 10 + ";" + "document.getElementById('mv" + (i + 1) + "').innerHTML=document.getElementById('mtext" + (i + 1) + "').innerHTML;" + "function Marquee" + (i + 1) + "(){" + "if(document.getElementById('mv" + (i + 1) + "').offsetTop-document.getElementById('maq" + (i + 1) + "').scrollTop<=0){" + "document.getElementById('maq" + (i + 1) + "').scrollTop-=document.getElementById('mtext" + (i + 1) + "').offsetHeight;" + "}else{" + "document.getElementById('maq" + (i + 1) + "').scrollTop++;" + "}" + "}" + "var MyMar" + (i + 1) + "=setInterval('Marquee" + (i + 1) + "()',speed);" + "document.getElementById('maq" + (i + 1) + "').onmouseover=function() {clearInterval(MyMar" + (i + 1) + ")};" + "document.getElementById('maq" + (i + 1) + "').onmouseout=function() {MyMar=setInterval('Marquee" + (i + 1) + "()',speed)};" + "</script>");
         }
       }
       if ((mlstaticrect != null) && (mlstaticrect.length > 0)) {
         for (int i = 0; i < mlstaticrect.length; i++)
         {
           stringToPicCount(1, mlstaticrect[i].getRectId(), mlstaticrect[i].getTextType());
           
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
           this.innerHtml = (this.innerHtml + "<div style=\"width:" + mlstaticrect[i].getWidth() * 540 / this.RequestH.intValue() + "px; height:" + mlstaticrect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + mlstaticrect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + mlstaticrect[i].getPosX() * 540 / this.RequestH.intValue() + "px; position: absolute;\">" + "<img  style=\"width:" + mlstaticrect[i].getWidth() * 540 / this.RequestH.intValue() + "px; height:" + mlstaticrect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + mlstaticrect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + mlstaticrect[i].getPosX() * 540 / this.RequestH.intValue() + "px;\" src=\"pluginrect!stringToPic?bid=" + 1 + "&taskID=" + this.taskID + "&programID=" + this.programID + "&rectid=" + mlstaticrect[i].getRectId() + "&texttype=" + mlstaticrect[i].getTextType() + "&date=" + new Date() + "\" />" + "</div>");
         }
       }
       if ((wtrect != null) && (wtrect.length > 0)) {
         for (int i = 0; i < wtrect.length; i++) {
           this.innerHtml = (this.innerHtml + "<div style=\"width:" + wtrect[i].getWidth() * 540 / this.RequestH.intValue() + "px; text-align: justify; height:" + wtrect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + wtrect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + wtrect[i].getPosX() * 540 / this.RequestH.intValue() + "px; position: absolute;\">" + "<img style=\"width:" + wtrect[i].getWidth() * 540 / this.RequestH.intValue() + "px; text-align: justify; height:" + wtrect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + wtrect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + wtrect[i].getPosX() * 540 / this.RequestH.intValue() + "px;\" src=\"pluginrect!drawWord?str=" + URLEncoder.encode(getText("simple"), "utf-8") + "&taskID=" + this.taskID + "&programID=" + this.programID + "&rectid=" + wtrect[i].getRectId() + "&date=" + new Date() + "\" />" + "</div>");
         }
       }
       if ((wlRect != null) && (wlRect.length > 0)) {
         for (int i = 0; i < wlRect.length; i++) {
           this.innerHtml = (this.innerHtml + "<div style=\"width:" + wlRect[i].getWidth() * 540 / this.RequestH.intValue() + "px; text-align: justify; height:" + wlRect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + wlRect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + wlRect[i].getPosX() * 540 / this.RequestH.intValue() + "px; position: absolute;\"><img src=\"img/48.png\" width=\"" + wlRect[i].getWidth() * 540 / this.RequestH.intValue() + "\" height=\"" + wlRect[i].getHeight() * 540 / this.RequestH.intValue() + "\" /></div>");
         }
       }
       if ((wdrect != null) && (wdrect.length > 0)) {
         for (int i = 0; i < wdrect.length; i++) {
           if (wdrect[i].getWeatherDay() != null) {
             if (wdrect[i].getWeatherDay().equals("today"))
             {
               this.innerHtml = (this.innerHtml + "<div style=\"width:" + wdrect[i].getWidth() * 540 / this.RequestH.intValue() + "px; text-align: justify; height:" + wdrect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + wdrect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + wdrect[i].getPosX() * 540 / this.RequestH.intValue() + "px; position: absolute;\">" + "<img style=\"width:" + wdrect[i].getWidth() * 540 / this.RequestH.intValue() + "px; text-align: justify; height:" + wdrect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + wdrect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + wdrect[i].getPosX() * 540 / this.RequestH.intValue() + "px;\" src=\"pluginrect!drawWord?str=" + URLEncoder.encode((String)wdrect[i].getTitles().get("today"), "utf-8") + "&taskID=" + this.taskID + "&programID=" + this.programID + "&rectid=" + wdrect[i].getRectId() + "&date=" + new Date() + "\" />" + "</div>");
             }
             else if (wdrect[i].getWeatherDay().equals("tomorrow"))
             {
               this.innerHtml = (this.innerHtml + "<div style=\"width:" + wdrect[i].getWidth() * 540 / this.RequestH.intValue() + "px; text-align: justify; height:" + wdrect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + wdrect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + wdrect[i].getPosX() * 540 / this.RequestH.intValue() + "px; position: absolute;\">" + "<img style=\"width:" + wdrect[i].getWidth() * 540 / this.RequestH.intValue() + "px; text-align: justify; height:" + wdrect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + wdrect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + wdrect[i].getPosX() * 540 / this.RequestH.intValue() + "px;\" src=\"pluginrect!drawWord?str=" + URLEncoder.encode((String)wdrect[i].getTitles().get("tomorrow"), "utf-8") + "&taskID=" + this.taskID + "&programID=" + this.programID + "&rectid=" + wdrect[i].getRectId() + "&date=" + new Date() + "\" />" + "</div>");
             }
             else if (wdrect[i].getWeatherDay().equals("aftertomorrow"))
             {
               this.innerHtml = (this.innerHtml + "<div style=\"width:" + wdrect[i].getWidth() * 540 / this.RequestH.intValue() + "px; text-align: justify; height:" + wdrect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + wdrect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + wdrect[i].getPosX() * 540 / this.RequestH.intValue() + "px; position: absolute;\">" + "<img style=\"width:" + wdrect[i].getWidth() * 540 / this.RequestH.intValue() + "px; text-align: justify; height:" + wdrect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + wdrect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + wdrect[i].getPosX() * 540 / this.RequestH.intValue() + "px;\" src=\"pluginrect!drawWord?str=" + URLEncoder.encode((String)wdrect[i].getTitles().get("aftertomorrow"), "utf-8") + "&taskID=" + this.taskID + "&programID=" + this.programID + "&rectid=" + wdrect[i].getRectId() + "&date=" + new Date() + "\" />" + "</div>");
             }
             else if (wdrect[i].getWeatherDay().equals("loop"))
             {
               this.innerHtml = (this.innerHtml + "<div id=\"Divd" + i + "\" style=\"width: " + wdrect[i].getWidth() * 540 / this.RequestH.intValue() + "px; height: " + wdrect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + wdrect[i].getPosY() * 540 / this.RequestH.intValue() + "px; text-align: justify; left: " + wdrect[i].getPosX() * 540 / this.RequestH.intValue() + "px; position: absolute;\">" + "<table id=\"Tabled" + i + "\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">" + "<tr>");
               this.innerHtml += "<td>";
               
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
               this.innerHtml = (this.innerHtml + "<div style=\"margin:0px; text-align: " + (wdrect[i].getAlignH() == 1 ? "left" : wdrect[i].getAlignH() == 0 ? "center" : "right") + "; overflow: hidden; width:" + wdrect[i].getWidth() * 540 / this.RequestH.intValue() + "px; padding:0px; height:" + wdrect[i].getHeight() * 540 / this.RequestH.intValue() + "px; font-size:" + wdrect[i].getHeight() * 540 / this.RequestH.intValue() * 0.85D + "px;\">" + "<img style=\"margin:0px; text-align: " + (wdrect[i].getAlignH() == 1 ? "left" : wdrect[i].getAlignH() == 0 ? "center" : "right") + "; overflow: hidden; width:" + wdrect[i].getWidth() * 540 / this.RequestH.intValue() + "px; padding:0px; height:" + wdrect[i].getHeight() * 540 / this.RequestH.intValue() + "px;\" src=\"pluginrect!drawWord?str=" + URLEncoder.encode((String)wdrect[i].getTitles().get("today"), "utf-8") + "&taskID=" + this.taskID + "&programID=" + this.programID + "&rectid=" + wdrect[i].getRectId() + "&date=" + new Date() + "\" />" + "</div>");
               this.innerHtml += "</td>";
               
               this.innerHtml += "<td>";
               
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
               this.innerHtml = (this.innerHtml + "<div style=\"margin:0px; text-align: " + (wdrect[i].getAlignH() == 1 ? "left" : wdrect[i].getAlignH() == 0 ? "center" : "right") + "; overflow: hidden; width:" + wdrect[i].getWidth() * 540 / this.RequestH.intValue() + "px; padding:0px; height:" + wdrect[i].getHeight() * 540 / this.RequestH.intValue() + "px; font-size:" + wdrect[i].getHeight() * 540 / this.RequestH.intValue() * 0.85D + "px;\">" + "<img style=\"margin:0px; text-align: " + (wdrect[i].getAlignH() == 1 ? "left" : wdrect[i].getAlignH() == 0 ? "center" : "right") + "; overflow: hidden; width:" + wdrect[i].getWidth() * 540 / this.RequestH.intValue() + "px; padding:0px; height:" + wdrect[i].getHeight() * 540 / this.RequestH.intValue() + "px;\" src=\"pluginrect!drawWord?str=" + URLEncoder.encode((String)wdrect[i].getTitles().get("tomorrow"), "utf-8") + "&taskID=" + this.taskID + "&programID=" + this.programID + "&rectid=" + wdrect[i].getRectId() + "&date=" + new Date() + "\" />" + "</div>");
               this.innerHtml += "</td>";
               
               this.innerHtml += "<td>";
               
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
               this.innerHtml = (this.innerHtml + "<div style=\"margin:0px; text-align: " + (wdrect[i].getAlignH() == 1 ? "left" : wdrect[i].getAlignH() == 0 ? "center" : "right") + "; overflow: hidden; width:" + wdrect[i].getWidth() * 540 / this.RequestH.intValue() + "px; padding:0px; height:" + wdrect[i].getHeight() * 540 / this.RequestH.intValue() + "px; font-size:" + wdrect[i].getHeight() * 540 / this.RequestH.intValue() * 0.85D + "px;\">" + "<img style=\"margin:0px; text-align: " + (wdrect[i].getAlignH() == 1 ? "left" : wdrect[i].getAlignH() == 0 ? "center" : "right") + "; overflow: hidden; width:" + wdrect[i].getWidth() * 540 / this.RequestH.intValue() + "px; padding:0px; height:" + wdrect[i].getHeight() * 540 / this.RequestH.intValue() + "px;\" src=\"pluginrect!drawWord?str=" + URLEncoder.encode((String)wdrect[i].getTitles().get("aftertomorrow"), "utf-8") + "&taskID=" + this.taskID + "&programID=" + this.programID + "&rectid=" + wdrect[i].getRectId() + "&date=" + new Date() + "\" />" + "</div>");
               this.innerHtml += "</td>";
               
 
 
 
 
               this.innerHtml = (this.innerHtml + "</tr></table></div> <script type=\"text/javascript\"> new SlideTrans(\"Divd" + i + "\", \"Tabled" + i + "\", " + 2 + ", { Vertical: false }).Run(); " + "</script>");
             }
           }
         }
       }
       if ((temprect != null) && (temprect.length > 0)) {
         for (int i = 0; i < temprect.length; i++) {
           this.innerHtml = (this.innerHtml + "<div style=\"width:" + temprect[i].getWidth() * 540 / this.RequestH.intValue() + "px; text-align: justify; height:" + temprect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + temprect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + temprect[i].getPosX() * 540 / this.RequestH.intValue() + "px; position: absolute;\">" + "<img  style=\"width:" + temprect[i].getWidth() * 540 / this.RequestH.intValue() + "px; text-align: justify; height:" + temprect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + temprect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + temprect[i].getPosX() * 540 / this.RequestH.intValue() + "px;\" src=\"pluginrect!drawWord?str=" + URLEncoder.encode("15°~22°", "utf-8") + "&taskID=" + this.taskID + "&programID=" + this.programID + "&rectid=" + temprect[i].getRectId() + "&date=" + new Date() + "\" />" + "</div>");
         }
       }
       if ((timerect != null) && (timerect.length > 0)) {
         for (int i = 0; i < timerect.length; i++)
         {
           Date d = new Date();
           int hour = d.getHours();
           int minute = d.getMinutes();
           int second = d.getSeconds();
           
 
 
 
 
 
 
 
 
 
 
 
 
 
 
           String tmft0 = new SimpleDateFormat("HH:mm").format(d);
           String tmft1 = new SimpleDateFormat("HH时mm分").format(d);
           String tmft2 = "";
           String tmft3 = "";
           String tmft4 = new SimpleDateFormat("HH時mm分").format(d);
           String tmft5 = "";
           String tmft6 = new SimpleDateFormat("HH:mm:ss").format(d);
           String tmft7 = new SimpleDateFormat("HH时mm分ss秒").format(d);
           String tmft8 = "";
           String tmft9 = "";
           String tmft10 = new SimpleDateFormat("HH時mm分ss秒").format(d);
           String tmft11 = "";
           if (hour > 12)
           {
             tmft2 = String.format("%02d:%02d PM", new Object[] { Integer.valueOf(hour - 12), Integer.valueOf(minute) });
             tmft8 = String.format("%02d:%02d:%02d PM", new Object[] { Integer.valueOf(hour - 12), Integer.valueOf(minute), Integer.valueOf(second) });
           }
           else if (hour == 12)
           {
             tmft2 = String.format("12:%02d PM", new Object[] { Integer.valueOf(minute) });
             tmft8 = String.format("12:%02d:%02d PM", new Object[] { Integer.valueOf(minute), Integer.valueOf(second) });
           }
           else if (hour > 0)
           {
             tmft2 = String.format("%02d:%02d AM", new Object[] { Integer.valueOf(hour), Integer.valueOf(minute) });
             tmft8 = String.format("%02d:%02d:%02d AM", new Object[] { Integer.valueOf(hour), Integer.valueOf(minute), Integer.valueOf(second) });
           }
           else
           {
             tmft2 = String.format("12:%02d AM", new Object[] { Integer.valueOf(minute) });
             tmft8 = String.format("12:%02d:%02d AM", new Object[] { Integer.valueOf(minute), Integer.valueOf(second) });
           }
           if (hour > 12)
           {
             tmft3 = String.format("下午%02d时%02d分", new Object[] { Integer.valueOf(hour - 12), Integer.valueOf(minute) });
             tmft5 = String.format("下午%02d時%02d分", new Object[] { Integer.valueOf(hour - 12), Integer.valueOf(minute) });
             tmft9 = String.format("下午%02d时%02d分%02d秒", new Object[] { Integer.valueOf(hour - 12), Integer.valueOf(minute), Integer.valueOf(second) });
             tmft11 = String.format("下午%02d時%02d分%02d秒", new Object[] { Integer.valueOf(hour - 12), Integer.valueOf(minute), Integer.valueOf(second) });
           }
           else
           {
             tmft3 = String.format("上午%02d时%02d分", new Object[] { Integer.valueOf(hour), Integer.valueOf(minute) });
             tmft5 = String.format("上午%02d時%02d分", new Object[] { Integer.valueOf(hour), Integer.valueOf(minute) });
             tmft9 = String.format("下午%02d时%02d分%02d秒", new Object[] { Integer.valueOf(hour), Integer.valueOf(minute), Integer.valueOf(second) });
             tmft11 = String.format("上午%02d時%02d分%02d秒", new Object[] { Integer.valueOf(hour), Integer.valueOf(minute), Integer.valueOf(second) });
           }
           String[] s = { tmft0, tmft1, tmft2, tmft3, tmft4, tmft5, 
             tmft6, tmft7, tmft8, tmft9, tmft10, tmft11 };
           String time = s[timerect[i].getShowStyle()];
           
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
           this.innerHtml = (this.innerHtml + "<div style=\"width:" + timerect[i].getWidth() * 540 / this.RequestH.intValue() + "px; text-align: justify; height:" + timerect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + timerect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + timerect[i].getPosX() * 540 / this.RequestH.intValue() + "px; position: absolute;\">" + "<img style=\"width:" + timerect[i].getWidth() * 540 / this.RequestH.intValue() + "px; text-align: justify; height:" + timerect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + timerect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + timerect[i].getPosX() * 540 / this.RequestH.intValue() + "px;\" src=\"pluginrect!drawWord?str=" + URLEncoder.encode(time, "utf-8") + "&taskID=" + this.taskID + "&programID=" + this.programID + "&rectid=" + timerect[i].getRectId() + "&date=" + new Date() + "\" />" + "</div>");
         }
       }
       if ((dateRect != null) && (dateRect.length > 0)) {
         for (int i = 0; i < dateRect.length; i++)
         {
           Date datenow = new Date();
           SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
           SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日");
           SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy/MM/dd");
           SimpleDateFormat sdf4 = new SimpleDateFormat("MM/dd/yyyy");
           SimpleDateFormat sdf5 = new SimpleDateFormat("dd-MMMM-yyyy");
           SimpleDateFormat sdf6 = new SimpleDateFormat("MMMM dd yyyy");
           SimpleDateFormat sdf7 = new SimpleDateFormat("dd/MM/yyyy");
           
           String[] s = {
             sdf1.format(datenow), 
             sdf2.format(datenow), 
             sdf3.format(datenow), 
             sdf4.format(datenow), 
             sdf5.format(datenow), 
             sdf6.format(datenow), 
             sdf7.format(datenow) };
           
           String date = s[dateRect[i].getShowStyle()];
           
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
           this.innerHtml = (this.innerHtml + "<div style=\"width:" + dateRect[i].getWidth() * 540 / this.RequestH.intValue() + "px; text-align: justify; height:" + dateRect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + dateRect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + dateRect[i].getPosX() * 540 / this.RequestH.intValue() + "px; position: absolute;\">" + "<div style=\"margin:0px; text-align: " + (dateRect[i].getAlignH() == 1 ? "left" : dateRect[i].getAlignH() == 0 ? "center" : "right") + "; overflow: hidden; width:" + dateRect[i].getWidth() * 540 / this.RequestH.intValue() + "px; padding:0px; height:" + dateRect[i].getHeight() * 540 / this.RequestH.intValue() + "px; font-size:" + dateRect[i].getHeight() * 540 / this.RequestH.intValue() * 0.85D + "px;\">" + "<img style=\"width:" + dateRect[i].getWidth() * 540 / this.RequestH.intValue() + "px; text-align: justify; height:" + dateRect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + dateRect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + dateRect[i].getPosX() * 540 / this.RequestH.intValue() + "px;\" src=\"pluginrect!drawWord?str=" + URLEncoder.encode(date, "utf-8") + "&taskID=" + this.taskID + "&programID=" + this.programID + "&rectid=" + dateRect[i].getRectId() + "&date=" + new Date() + "\" />" + "</div>" + "</div>");
         }
       }
       if ((weekrect != null) && (weekrect.length > 0)) {
         for (int i = 0; i < weekrect.length; i++)
         {
           String week = "";
           Calendar c = Calendar.getInstance();
           if (weekrect[i].getShowStyle() == 0)
           {
             String[] Day = { "星期日", "星期一", "星期二", 
               "星期三", "星期四", "星期五", "星期六" };
             
             int w = c.get(7) - 1;
             if (w < 0) {
               w = 0;
             }
             week = Day[w];
           }
           else
           {
             String[] Day = { "Sun", "Mon", "Tue", 
               "Wed", "Thu", "Fri", "Sat" };
             
             int w = c.get(7) - 1;
             if (w < 0) {
               w = 0;
             }
             week = Day[w];
           }
           this.innerHtml = (this.innerHtml + "<div style=\"width:" + weekrect[i].getWidth() * 540 / this.RequestH.intValue() + "px; text-align: justify; height:" + weekrect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + weekrect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + weekrect[i].getPosX() * 540 / this.RequestH.intValue() + "px; position: absolute;\">" + "<img style=\"width:" + weekrect[i].getWidth() * 540 / this.RequestH.intValue() + "px; text-align: justify; height:" + weekrect[i].getHeight() * 540 / this.RequestH.intValue() + "px; top: " + weekrect[i].getPosY() * 540 / this.RequestH.intValue() + "px; left: " + weekrect[i].getPosX() * 540 / this.RequestH.intValue() + "px;\" src=\"pluginrect!drawWord?str=" + URLEncoder.encode(week, "utf-8") + "&taskID=" + this.taskID + "&programID=" + this.programID + "&rectid=" + weekrect[i].getRectId() + "&date=" + new Date() + "\" />" + "</div>");
         }
       }
       if ((weblist != null) && (weblist.size() > 0))
       {
         int rectSize = weblist.size();
         for (int i = 0; i < rectSize; i++)
         {
           WebRect rect = (WebRect)weblist.get(i);
           
 
 
 
 
 
           this.innerHtml = (this.innerHtml + "<div style=\"width:" + rect.getWidth() * 540 / this.RequestH.intValue() + "px;" + "height:" + rect.getHeight() * 540 / this.RequestH.intValue() + "px;" + "top: " + rect.getPosY() * 540 / this.RequestH.intValue() + "px;" + "left: " + rect.getPosX() * 540 / this.RequestH.intValue() + "px;" + "position: absolute;" + "\">");
           
           List<RectContent> contentLists = getPluginRectContentHandle().readAll(
             rect.getTaskId(), rect.getProgramId(), rect.getRectId());
           if ((contentLists != null) && (contentLists.size() > 0))
           {
             RectContent content = (RectContent)contentLists.get(0);
             if ((content != null) && ((content instanceof RectWebUrl))) {
               this.innerHtml = (this.innerHtml + "<iframe style=\"width:99%;height:98%;border:0px;\" scrolling=\"no\" src=\"" + ((RectWebUrl)content).getUrl() + "\"></iframe>");
             }
           }
           this.innerHtml += "</div>";
         }
       }
       if ((flashlist != null) && (flashlist.size() > 0))
       {
         int rectSize = flashlist.size();
         for (int i = 0; i < rectSize; i++)
         {
           FlashRect rect = (FlashRect)flashlist.get(i);
           
 
 
 
 
 
           this.innerHtml = (this.innerHtml + "<div style=\"width:" + rect.getWidth() * 540 / this.RequestH.intValue() + "px;" + "height:" + rect.getHeight() * 540 / this.RequestH.intValue() + "px;" + "top: " + rect.getPosY() * 540 / this.RequestH.intValue() + "px;" + "left: " + rect.getPosX() * 540 / this.RequestH.intValue() + "px;" + "position: absolute;" + "\">");
           
           List<RectContent> contentLists = getPluginRectContentHandle().readAll(
             rect.getTaskId(), rect.getProgramId(), rect.getRectId());
           if ((contentLists != null) && (contentLists.size() > 0))
           {
             RectContent content = (RectContent)contentLists.get(0);
             if ((content != null) && ((content instanceof RectFlash)))
             {
               RectFlash flash = (RectFlash)content;
               String srcUrl = "file!previewfilenew?file.fileId=" + flash.getFileId() + 
                 "&file.type=" + 3;
               
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
               this.innerHtml = (this.innerHtml + "<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" style=\"margin:0px;border:0px;padding:0px;text-align:center;vertical-align:middle;\" width=\"" + rect.getWidth() * 540 / this.RequestH.intValue() + "\" " + "height=\"" + rect.getHeight() * 540 / this.RequestH.intValue() + "\" " + "codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,40,0\" " + "name=\"movieObject\" id=\"movieObject\"> " + "<param name=\"movie\" value=\"" + srcUrl + "\"> " + "<param name=\"quality\" value=\"high\"> " + "<param name=\"bgcolor\" value=\"#ffffff\"> " + "<embed " + "src=\"" + srcUrl + "\" " + "quality=\"high\" bgcolor=\"#ffffff\" " + "width=\"" + rect.getWidth() * 540 / this.RequestH.intValue() + "\" " + "height=\"" + rect.getHeight() * 540 / this.RequestH.intValue() + "\" " + "style=\"margin:0px;border:0px;padding:0px;text-align:center;vertical-align:middle;\"" + "name=\"movieEmbed\" id=\"movieEmbed\" align=\"\" type=\"application/x-shockwave-flash\" " + "pluginspage=\"http://www.macromedia.com/go/getflashplayer\"> " + "</embed> " + "</object> ");
             }
           }
           this.innerHtml += "</div>";
         }
       }
       this.innerHtml += "</div>";
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
     return "view";
   }
   
   public int NextPicCount(int id, int rect_id, String textType)
   {
     List<RectContent> textfiles = getPluginRectContentHandle()
       .readAll(this.taskID, this.programID, 
       rect_id);
     String str = "";
     if ((textfiles != null) && (textfiles.size() > 0)) {
       for (int j = 0; j < textfiles.size(); j++) {
         if (((RectContent)textfiles.get(j)).getContentType() == 3) {
           str = str + ((RectText)textfiles.get(j)).getText();
         } else if (((RectContent)textfiles.get(j)).getContentType() == 4) {
           str = str + ((RectSource)textfiles.get(j)).getFileName();
         } else {
           str = str + ((RectSystemSource)textfiles.get(j)).getFileName();
         }
       }
     }
     Rect rect = getPluginRectHandle().read(this.taskID, this.programID, rect_id);
     FontRect frect = (FontRect)rect;
     int w = rect.getWidth();
     int h = rect.getHeight();
     Font f = null;
     if (textType.equals("tag")) {
       f = new Font("方正黑体简体", 0, 
         Integer.parseInt(new DecimalFormat("0").format(h * 0.85D)));
     } else {
       f = new Font(frect.getFontName(), 0, 
         Integer.parseInt(new DecimalFormat("0").format(h * 0.85D)));
     }
     Color color = frect.getFontColor();
     Color bcolor = frect.getBackColor();
     
     BufferedImage newbuffImage = new BufferedImage(w, h, 
       1);
     
     Graphics newg = newbuffImage.getGraphics();
     if (rect.getRecType() == 11) {
       newg.setColor(Color.BLACK);
     } else {
       newg.setColor(bcolor);
     }
     newg.fillRect(0, 0, newbuffImage.getWidth(), h);
     
     newg.setColor(color);
     newg.setFont(f);
     
     int linewidth = 0;
     int start = 0;
     int index = start;
     int strLength = str.length();
     int singlcharWidth = 0;
     while (start < strLength)
     {
       linewidth = 0;
       for (index = start; index < strLength; index++)
       {
         char singlchar = str.charAt(index);
         if (str.charAt(index) == '\n')
         {
           index++;
         }
         else
         {
           singlcharWidth = newg.getFontMetrics(f).charWidth(singlchar);
           linewidth += singlcharWidth;
           if (linewidth > w) {
             break;
           }
         }
       }
       if (index > start)
       {
         String newString = "";
         newString = str.substring(start, index);
         
         newbuffImage = new BufferedImage(w, h, 
           1);
         
         newg = newbuffImage.getGraphics();
         if (rect.getRecType() == 11) {
           newg.setColor(Color.BLACK);
         } else {
           newg.setColor(bcolor);
         }
         newg.fillRect(0, 0, newbuffImage.getWidth(), h);
         
         newg.setColor(color);
         newg.setFont(f);
         
         int strWidth = newg.getFontMetrics(f).charsWidth(
           newString.toCharArray(), 0, newString.length());
         
         int pos = 0;
         if (frect.getAlignH() == 0) {
           pos = Integer.parseInt(new DecimalFormat("0")
             .format((w - strWidth) / 2));
         } else if (frect.getAlignH() != 1) {
           if (frect.getAlignH() == 2) {
             pos = w - strWidth;
           }
         }
         if (rect.getRecType() != 11) {
           if (linewidth > w) {
             newbuffImage = newbuffImage.getSubimage(0, 0, linewidth - 
               singlcharWidth, h);
           } else {
             newbuffImage = newbuffImage.getSubimage(0, 0, linewidth, h);
           }
         }
         id++;
       }
       else
       {
         index++;
       }
       start = index;
     }
     return id;
   }
   
   public int getBid()
   {
     return this.bid;
   }
   
   public void setBid(int bid)
   {
     this.bid = bid;
   }
   
   public int getRectid()
   {
     return this.rectid;
   }
   
   public void setRectid(int rectid)
   {
     this.rectid = rectid;
   }
   
   public String texttype = "";
   
   public String getTexttype()
   {
     return this.texttype;
   }
   
   public void setTexttype(String texttype)
   {
     this.texttype = texttype;
   }
   
   public void NextPic()
   {
     List<RectContent> textfiles = getPluginRectContentHandle()
       .readAll(this.taskID, this.programID, 
       this.rectid);
     String str = "";
     if ((textfiles != null) && (textfiles.size() > 0)) {
       for (int j = 0; j < textfiles.size(); j++) {
         if (((RectContent)textfiles.get(j)).getContentType() == 3) {
           str = str + ((RectText)textfiles.get(j)).getText();
         } else if (((RectContent)textfiles.get(j)).getContentType() == 4) {
           str = str + ((RectSource)textfiles.get(j)).getFileName();
         } else {
           str = str + ((RectSystemSource)textfiles.get(j)).getFileName();
         }
       }
     }
     Rect rect = getPluginRectHandle().read(this.taskID, this.programID, this.rectid);
     FontRect frect = (FontRect)rect;
     int w = rect.getWidth();
     int h = rect.getHeight();
     Font f = null;
     if (this.texttype.equals("tag")) {
       f = new Font("方正黑体简体", 0, 
         Integer.parseInt(new DecimalFormat("0").format(h * 0.85D)));
     } else {
       f = new Font(frect.getFontName(), 0, 
         Integer.parseInt(new DecimalFormat("0").format(h * 0.85D)));
     }
     Color color = frect.getFontColor();
     Color bcolor = frect.getBackColor();
     
     int begin = 0;
     BufferedImage newbuffImage = new BufferedImage(w, h, 
       2);
     
     Graphics newg = newbuffImage.getGraphics();
     if ((rect.getRecType() == 11) || (bcolor.equals(Color.BLACK))) {
       newg.setColor(new Color(0, 0, 0, 0));
     } else {
       newg.setColor(bcolor);
     }
     newg.fillRect(0, 0, newbuffImage.getWidth(), h);
     
     newg.setColor(color);
     newg.setFont(f);
     
     int linewidth = 0;
     int start = 0;
     int index = start;
     int strLength = str.length();
     int singlcharWidth = 0;
     while (start < strLength)
     {
       linewidth = 0;
       for (index = start; index < strLength; index++)
       {
         char singlchar = str.charAt(index);
         if (str.charAt(index) == '\n')
         {
           index++;
         }
         else
         {
           singlcharWidth = newg.getFontMetrics(f).charWidth(singlchar);
           linewidth += singlcharWidth;
           if (linewidth > w) {
             break;
           }
         }
       }
       if (index > start)
       {
         String newString = "";
         newString = str.substring(start, index);
         
         newbuffImage = new BufferedImage(w, h, 
           2);
         
         newg = newbuffImage.getGraphics();
         if ((rect.getRecType() == 11) || (bcolor.equals(Color.BLACK))) {
           newg.setColor(new Color(0, 0, 0, 0));
         } else {
           newg.setColor(bcolor);
         }
         newg.fillRect(0, 0, newbuffImage.getWidth(), h);
         
         newg.setColor(color);
         newg.setFont(f);
         
         int strWidth = newg.getFontMetrics(f).charsWidth(
           newString.toCharArray(), 0, newString.length());
         
         int pos = 0;
         if (frect.getAlignH() == 0) {
           pos = Integer.parseInt(new DecimalFormat("0")
             .format((w - strWidth) / 2));
         } else if (frect.getAlignH() != 1) {
           if (frect.getAlignH() == 2) {
             pos = w - strWidth;
           }
         }
         if (rect.getRecType() == 11)
         {
           newg.drawString(newString, pos, 
             Integer.parseInt(new DecimalFormat("0")
             .format(h * 0.85D)));
         }
         else
         {
           newg.drawString(newString, 0, 
             Integer.parseInt(new DecimalFormat("0")
             .format(h * 0.85D)));
           if (linewidth > w) {
             newbuffImage = newbuffImage.getSubimage(0, 0, linewidth - 
               singlcharWidth, h);
           } else {
             newbuffImage = newbuffImage.getSubimage(0, 0, w, h);
           }
         }
         begin++;
         if (begin == this.bid) {
           playview(newbuffImage);
         }
       }
       else
       {
         index++;
       }
       start = index;
     }
   }
   
   public int stringToPicCount(int id, int rect_id, String textType)
   {
     List<RectContent> textfiles = getPluginRectContentHandle()
       .readAll(this.taskID, this.programID, 
       rect_id);
     String str = "";
     if ((textfiles != null) && (textfiles.size() > 0)) {
       for (int j = 0; j < textfiles.size(); j++) {
         if (((RectContent)textfiles.get(j)).getContentType() == 3) {
           str = str + ((RectText)textfiles.get(j)).getText();
         } else if (((RectContent)textfiles.get(j)).getContentType() == 4) {
           str = str + ((RectSource)textfiles.get(j)).getFileName();
         } else {
           str = str + ((RectSystemSource)textfiles.get(j)).getFileName();
         }
       }
     }
     Rect rect = getPluginRectHandle().read(this.taskID, this.programID, rect_id);
     FontRect frect = (FontRect)rect;
     int w = rect.getWidth();
     int h = rect.getHeight();
     Font f = null;
     if (textType.equals("tag")) {
       f = new Font("方正黑体简体", 0, frect.getFontSize());
     } else {
       f = new Font(frect.getFontName(), 0, frect.getFontSize());
     }
     Color color = frect.getFontColor();
     Color bcolor = frect.getBackColor();
     
     int start = 0;
     int ypos = 0;
     
     int linewidth = 0;
     int index = start;
     int strLength = str.length();
     int fontHeight = 0;
     
     BufferedImage newbuffImage = new BufferedImage(w, h, 
       1);
     
     Graphics newg = newbuffImage.getGraphics();
     if (rect.getRecType() == 14) {
       newg.setColor(Color.BLACK);
     } else {
       newg.setColor(bcolor);
     }
     newg.fillRect(0, 0, w, newbuffImage.getHeight());
     
     newg.setColor(color);
     newg.setFont(f);
     fontHeight = newg.getFontMetrics(f).getHeight();
     ypos = fontHeight;
     while (start < strLength)
     {
       linewidth = 0;
       for (index = start; index < strLength; index++)
       {
         char singlchar = str.charAt(index);
         if (str.charAt(index) == '\n')
         {
           index++;
         }
         else
         {
           linewidth += newg.getFontMetrics(f).charWidth(singlchar);
           if (linewidth > w) {
             break;
           }
         }
       }
       if (index <= start) {
         index++;
       }
       ypos += fontHeight;
       if (ypos >= h + fontHeight)
       {
         id++;
         
 
         newbuffImage = new BufferedImage(w, h, 
           1);
         newg = newbuffImage.getGraphics();
         if (rect.getRecType() == 14) {
           newg.setColor(Color.BLACK);
         } else {
           newg.setColor(bcolor);
         }
         newg.fillRect(0, 0, w, newbuffImage.getHeight());
         
         newg.setColor(color);
         newg.setFont(f);
         
         ypos -= h;
         if (ypos <= fontHeight) {}
       }
       start = index;
     }
     if ((ypos > 0) && (ypos < h))
     {
       newbuffImage = newbuffImage.getSubimage(0, 0, w, h);
       
       id++;
     }
     return id;
   }
   
   public void stringToPic()
   {
     List<RectContent> textfiles = getPluginRectContentHandle()
       .readAll(this.taskID, this.programID, this.rectid);
     String str = "";
     if ((textfiles != null) && (textfiles.size() > 0)) {
       for (int j = 0; j < textfiles.size(); j++) {
         if (((RectContent)textfiles.get(j)).getContentType() == 3) {
           str = str + ((RectText)textfiles.get(j)).getText();
         } else if (((RectContent)textfiles.get(j)).getContentType() == 4) {
           str = str + ((RectSource)textfiles.get(j)).getFileName();
         } else {
           str = str + ((RectSystemSource)textfiles.get(j)).getFileName();
         }
       }
     }
     Rect rect = getPluginRectHandle().read(this.taskID, this.programID, this.rectid);
     FontRect frect = (FontRect)rect;
     int w = rect.getWidth();
     int h = rect.getHeight();
     Font f = null;
     if (this.texttype.equals("tag")) {
       f = new Font("方正黑体简体", 0, frect.getFontSize());
     } else {
       f = new Font(frect.getFontName(), 0, frect.getFontSize());
     }
     Color color = frect.getFontColor();
     Color bcolor = frect.getBackColor();
     
     int begin = 0;
     
     int start = 0;
     int ypos = 0;
     
     int linewidth = 0;
     int index = start;
     int strLength = str.length();
     int fontHeight = 0;
     
     BufferedImage newbuffImage = new BufferedImage(w, h, 
       2);
     
     Graphics newg = newbuffImage.getGraphics();
     if ((rect.getRecType() == 14) || (bcolor.equals(Color.BLACK))) {
       newg.setColor(new Color(0, 0, 0, 0));
     } else {
       newg.setColor(bcolor);
     }
     newg.fillRect(0, 0, w, newbuffImage.getHeight());
     
     newg.setColor(color);
     newg.setFont(f);
     fontHeight = newg.getFontMetrics(f).getHeight();
     ypos = fontHeight;
     while (start < strLength)
     {
       linewidth = 0;
       for (index = start; index < strLength; index++)
       {
         char singlchar = str.charAt(index);
         if (str.charAt(index) == '\n')
         {
           index++;
         }
         else
         {
           linewidth += newg.getFontMetrics(f).charWidth(singlchar);
           if (linewidth > w) {
             break;
           }
         }
       }
       if (index > start)
       {
         String newString = str.substring(start, index);
         newg.drawString(newString, 0, ypos);
       }
       else
       {
         index++;
       }
       ypos += fontHeight;
       if (ypos >= h + fontHeight)
       {
         begin++;
         if (begin == this.bid) {
           playview(newbuffImage);
         }
         newbuffImage = new BufferedImage(w, h, 
           2);
         newg = newbuffImage.getGraphics();
         if ((rect.getRecType() == 14) || (bcolor.equals(Color.BLACK))) {
           newg.setColor(new Color(0, 0, 0, 0));
         } else {
           newg.setColor(bcolor);
         }
         newg.fillRect(0, 0, w, newbuffImage.getHeight());
         
         newg.setColor(color);
         newg.setFont(f);
         
         ypos -= h;
         if ((ypos > fontHeight) && 
           (index > start))
         {
           String newString = str.substring(start, index);
           newg.drawString(newString, 0, ypos - fontHeight);
         }
       }
       start = index;
     }
     if ((ypos > 0) && (ypos < h))
     {
       newbuffImage = newbuffImage.getSubimage(0, 0, w, h);
       
       begin++;
       if (begin == this.bid) {
         playview(newbuffImage);
       }
     }
   }
   
   public String str = "";
   
   public String getStr()
   {
     return this.str;
   }
   
   public void setStr(String str)
   {
     try
     {
       this.str = new String(str.getBytes("ISO-8859-1"), "utf-8");
     }
     catch (UnsupportedEncodingException e)
     {
       this.str = str;
     }
   }
   
   public void drawWord()
   {
     this.servletResponse.setHeader("Pragma", "No-cache");
     this.servletResponse.setHeader("Cache-Control", "no-cache");
     this.servletResponse.setHeader("Cache-Control", "no-store");
     this.servletResponse.setDateHeader("Expires", 0L);
     
     Rect rect = getPluginRectHandle().read(this.taskID, this.programID, this.rectid);
     FontRect frect = (FontRect)rect;
     int w = rect.getWidth();
     int h = rect.getHeight();
     Font f = new Font(frect.getFontName(), 0, 
       Integer.parseInt(new DecimalFormat("0").format(h * 0.85D)));
     Color color = frect.getFontColor();
     
 
 
     BufferedImage newbuffImage = new BufferedImage(w, h, 
       2);
     
     Graphics newg = newbuffImage.getGraphics();
     
     newg.setColor(new Color(0, 0, 0, 0));
     newg.fillRect(0, 0, newbuffImage.getWidth(), h);
     
     newg.setColor(color);
     newg.setFont(f);
     
     int strWidth = newg.getFontMetrics(f).charsWidth(this.str.toCharArray(), 0, 
       this.str.length());
     
     int pos = 0;
     if (frect.getAlignH() == 0) {
       pos = Integer.parseInt(new DecimalFormat("0")
         .format((w - strWidth) / 2));
     } else if (frect.getAlignH() != 1) {
       if (frect.getAlignH() == 2) {
         pos = w - strWidth;
       }
     }
     newg.drawString(this.str.toString(), pos, 
       Integer.parseInt(new DecimalFormat("0").format(h * 0.85D)));
     
     playview(newbuffImage);
   }
   
   public void playview(BufferedImage image)
   {
     try
     {
       ServletOutputStream sos = this.servletResponse.getOutputStream();
       this.servletResponse.setContentType("image/png");
       ImageIO.write(image, "png", sos);
       
 
       sos.close();
     }
     catch (IOException e)
     {
       log.info(e.getMessage());
     }
   }
   
   public void saveImg(BufferedImage bfimg, int id, Rect rect)
   {
     String path = "c:\\";
     String filepath = path + String.valueOf(id) + ".png";
     
     java.io.File file = new java.io.File(filepath);
     file.getParentFile().mkdirs();
     if (!file.exists()) {
       try
       {
         file.createNewFile();
       }
       catch (IOException localIOException1) {}
     }
     try
     {
       ImageIO.write(bfimg, "png", new java.io.File(filepath));
     }
     catch (IOException ex)
     {
       ex.printStackTrace();
     }
   }
   
   String getAddress = "";
   
   public String getGetAddress()
   {
     return this.getAddress;
   }
   
   public void setGetAddress(String getAddress)
   {
     this.getAddress = getAddress;
   }
   
   public String imagepreview()
   {
     this.category = new Category();
     this.category.setParentId(0);
     if (this.categorypath == null) {
       this.categorypath = new ArrayList();
     } else {
       this.categorypath.clear();
     }
     Category newgroup = this.category;
     
     this.categorypath.add(0, this.category);
     while (newgroup.getParentId() != 0)
     {
       newgroup = getCategoryHandle().read(newgroup.getParentId());
       this.categorypath.add(0, newgroup);
     }
     StringBuffer sql = new StringBuffer("SELECT A.CAT_ID AS CAT_ID, A.NAME AS NAME, A.DESCP AS DESCP, A.PARENT_ID AS PARENT_ID, B.NAME AS PARENT_NAME, A.DEPTH AS DEPTH, A.SUB_NUM AS SUB_NUM,   A.CREATE_TIME AS CREATE_TIME, A.CREATE_USER AS CREATE_USER FROM (SELECT * FROM tb_category WHERE CSTM_ID=:CSTM_ID  AND   PARENT_ID IS NULL  ) AS A LEFT JOIN tb_category AS B ON A.PARENT_ID=B.CAT_ID");
     
 
 
 
 
 
 
 
 
     Map<String, Object> parameters = new HashMap();
     parameters.put("CSTM_ID", Integer.valueOf(getCstmId()));
     
     this.brother = getCategoryHandle().readChildren(sql.toString(), parameters);
     
     return "imagepreviewview";
   }
   
   public String videopreview()
   {
     this.category = new Category();
     this.category.setParentId(0);
     if (this.categorypath == null) {
       this.categorypath = new ArrayList();
     } else {
       this.categorypath.clear();
     }
     Category newgroup = this.category;
     
     this.categorypath.add(0, this.category);
     while (newgroup.getParentId() != 0)
     {
       newgroup = getCategoryHandle().read(newgroup.getParentId());
       this.categorypath.add(0, newgroup);
     }
     StringBuffer sql = new StringBuffer("SELECT A.CAT_ID AS CAT_ID, A.NAME AS NAME, A.DESCP AS DESCP, A.PARENT_ID AS PARENT_ID, B.NAME AS PARENT_NAME, A.DEPTH AS DEPTH, A.SUB_NUM AS SUB_NUM,   A.CREATE_TIME AS CREATE_TIME, A.CREATE_USER AS CREATE_USER FROM (SELECT * FROM tb_category WHERE CSTM_ID=:CSTM_ID  AND   PARENT_ID IS NULL  ) AS A LEFT JOIN tb_category AS B ON A.PARENT_ID=B.CAT_ID");
     
 
 
 
 
 
 
 
 
     Map<String, Object> parameters = new HashMap();
     parameters.put("CSTM_ID", Integer.valueOf(getCstmId()));
     
     this.brother = getCategoryHandle().readChildren(sql.toString(), parameters);
     
     return "videopreviewview";
   }
   
   public void play()
   {
     int BUF_SIZE = 1048576;
     byte[] buf = null;
     
     FileInputStream fileStream = null;
     OutputStream outStream = null;
     try
     {
       long size = 0L;
       String address = this.getAddress;
       
       java.io.File download_file = new java.io.File(address);
       if (!download_file.exists()) {
         throw new HttpException(
           500, 
           "Cannot find file: " + address);
       }
       size = download_file.length();
       
       this.servletResponse.setHeader("Pragma", "No-cache");
       this.servletResponse.setHeader("Cache-Control", "no-cache");
       this.servletResponse.setHeader("Cache-Control", "no-store");
       this.servletResponse.setDateHeader("Expires", 0L);
       
       this.servletResponse.setHeader("Content-Length", Long.toString(size));
       this.servletResponse.addHeader("Image-Type", "Normal");
       try
       {
         fileStream = new FileInputStream(download_file);
         outStream = this.servletResponse.getOutputStream();
         
         int send_len = 0;
         while (send_len < (int)size)
         {
           int data_size = (int)size - send_len < 1048576 ? (int)size - send_len : 
             1048576;
           if (buf == null) {
             buf = new byte[data_size];
           }
           int read_len = 0;
           while (read_len < data_size)
           {
             int len = fileStream.read(buf, read_len, data_size - 
               read_len);
             if (len <= 0) {
               throw new HttpException(
                 500, 
                 "read file failed(" + len + "): " + address);
             }
             read_len += len;
           }
           outStream.write(buf, 0, data_size);
           outStream.flush();
           
           send_len += data_size;
         }
       }
       finally
       {
         try
         {
           if (outStream != null)
           {
             outStream.close();
             outStream = null;
           }
           if (fileStream != null)
           {
             fileStream.close();
             fileStream = null;
           }
         }
         catch (IOException localIOException) {}
         if (buf != null)
         {
           buf = null;
           System.gc();
         }
       }
     }
     catch (Exception localException) {}
   }
   
   public long fileId = 0L;
   
   public long getFileId()
   {
     return this.fileId;
   }
   
   public void setFileId(long fileId)
   {
     this.fileId = fileId;
   }
   
   public void preview()
   {
     int BUF_SIZE = 1048576;
     byte[] buf = null;
     
     FileInputStream fileStream = null;
     OutputStream outStream = null;
     try
     {
       long size = 0L;
       Storage storage = new Storage(getCstmId());
       String address = "";
       String imageType = "";
       if ((this.fileId > 0L) && (this.fileId < 2147483647L))
       {
         address = 
         
           Storage.getWorkRootPath() + "buildin" + java.io.File.separatorChar + "files" + java.io.File.separatorChar + this.fileId;
         imageType = "Scrambled";
       }
       else
       {
         try
         {
           int flag = getFileHandle().read(this.fileId).getFlag();
           address = storage.getFilePath(this.fileId);
         }
         catch (Exception w)
         {
           address = storage.getCombinFilePath(this.fileId);
         }
         imageType = "Normal";
       }
       java.io.File download_file = new java.io.File(address);
       if (!download_file.exists()) {
         throw new HttpException(
           500, 
           "Cannot find file: " + address);
       }
       size = download_file.length();
       
       this.servletResponse.setHeader("Pragma", "No-cache");
       this.servletResponse.setHeader("Cache-Control", "no-cache");
       this.servletResponse.setHeader("Cache-Control", "no-store");
       this.servletResponse.setDateHeader("Expires", 0L);
       
       this.servletResponse.setHeader("Content-Length", Long.toString(size));
       this.servletResponse.addHeader("Image-Type", imageType);
       try
       {
         fileStream = new FileInputStream(download_file);
         outStream = this.servletResponse.getOutputStream();
         
         int send_len = 0;
         while (send_len < (int)size)
         {
           int data_size = (int)size - send_len < 1048576 ? (int)size - send_len : 
             1048576;
           if (buf == null) {
             buf = new byte[data_size];
           }
           int read_len = 0;
           while (read_len < data_size)
           {
             int len = fileStream.read(buf, read_len, data_size - 
               read_len);
             if (len <= 0) {
               throw new HttpException(
                 500, 
                 "read file failed(" + len + "): " + address);
             }
             read_len += len;
           }
           outStream.write(buf, 0, data_size);
           outStream.flush();
           
           send_len += data_size;
         }
       }
       finally
       {
         try
         {
           if (outStream != null)
           {
             outStream.close();
             outStream = null;
           }
           if (fileStream != null)
           {
             fileStream.close();
             fileStream = null;
           }
         }
         catch (IOException localIOException) {}
         if (buf != null)
         {
           buf = null;
           System.gc();
         }
       }
     }
     catch (Exception localException1) {}
   }
   
   public RectImage rectImage = new RectImage();
   public RectVideo rectVideo = new RectVideo();
   public ProgramMusic rectMusic = new ProgramMusic();
   public RectText rectText = new RectText();
   
   public RectText getRectText()
   {
     return this.rectText;
   }
   
   public void setRectText(RectText rectText)
   {
     this.rectText = rectText;
   }
   
   public ProgramMusic getRectMusic()
   {
     return this.rectMusic;
   }
   
   public void setRectMusic(ProgramMusic rectMusic)
   {
     this.rectMusic = rectMusic;
   }
   
   public RectImage getRectImage()
   {
     return this.rectImage;
   }
   
   public void setRectImage(RectImage rectImage)
   {
     this.rectImage = rectImage;
   }
   
   public RectVideo getRectVideo()
   {
     return this.rectVideo;
   }
   
   public void setRectVideo(RectVideo rectVideo)
   {
     this.rectVideo = rectVideo;
   }
   
   private Exception mException = new Exception("null");
   private ContentFlash mContentFlash = new RectFlash();
   private RectWebUrl mRectWebUrl = new RectWebUrl();
   
   public RectWebUrl getRectWebUrl()
   {
     return this.mRectWebUrl;
   }
   
   public Exception getException()
   {
     return this.mException;
   }
   
   public void setException(Exception exception)
   {
     this.mException = exception;
   }
   
   public void setRectWebUrl(RectWebUrl mRectWebUrl)
   {
     this.mRectWebUrl = mRectWebUrl;
   }
   
   public ContentFlash getContentFlash()
   {
     return this.mContentFlash;
   }
   
   public void setContentFlash(ContentFlash mContentFlash)
   {
     this.mContentFlash = mContentFlash;
   }
   
   public boolean getRootCategoryChildren()
   {
     this.category = new Category();
     this.category.setParentId(0);
     if (this.categorypath == null) {
       this.categorypath = new ArrayList();
     } else {
       this.categorypath.clear();
     }
     StringBuffer sql = new StringBuffer("SELECT A.CAT_ID AS CAT_ID, A.NAME AS NAME, A.DESCP AS DESCP, A.PARENT_ID AS PARENT_ID, B.NAME AS PARENT_NAME, A.DEPTH AS DEPTH, A.SUB_NUM AS SUB_NUM,   A.CREATE_TIME AS CREATE_TIME, A.CREATE_USER AS CREATE_USER FROM (SELECT * FROM tb_category WHERE CSTM_ID=:CSTM_ID  AND   PARENT_ID IS NULL  ) AS A LEFT JOIN tb_category AS B ON A.PARENT_ID=B.CAT_ID");
     
 
 
 
 
 
 
 
 
     Map<String, Object> parameters = new HashMap();
     parameters.put("CSTM_ID", Integer.valueOf(getCstmId()));
     
     this.brother = getCategoryHandle().readChildren(sql.toString(), parameters);
     
     return true;
   }
   
   public String flashSelect()
   {
     getRootCategoryChildren();
     return "flashselect";
   }
   
   private void setFilePathSize(RectContent rectContent)
   {
     if ((rectContent instanceof RectFlashSource))
     {
       this.filelocation = getText("tag");
     }
     else
     {
       long fileId = rectContent != null ? rectContent.getFileId() : 0L;
       if ((0L < fileId) && (fileId < 2147483647L))
       {
         this.filelocation = getText("buildinfile");
       }
       else if (fileId != 0L)
       {
         com.gnamp.server.model.File file = getFileHandle().read(fileId);
         if (file != null)
         {
           this.filesize = (String.valueOf(file.getSize() / 1024L) + " KB");
           
           this.filelocation = getText("root");
           List<Category> capath = file.getCategoryId() == 0 ? null : 
             getCategoryHandle().readPath(file.getCategoryId());
           if ((capath != null) && (capath.size() > 0)) {
             for (Category category : capath) {
               if (category != null) {
                 this.filelocation = (this.filelocation + "->" + category.getCategoryName());
               }
             }
           }
         }
       }
     }
   }
   
   public void addFlashFiles()
   {
     String[] idArrary = null;
     if ((this.selectIDs != null) && ((this.selectIDs = this.selectIDs.trim()).length() > 0)) {
       idArrary = this.selectIDs.split(",");
     }
     if ((idArrary != null) && (idArrary.length > 0)) {
       for (int i = 0; i < idArrary.length; i++) {
         try
         {
           com.gnamp.server.model.File file = new com.gnamp.server.model.File();
           file = getFileHandle().read(Long.parseLong(idArrary[i]));
           if ((file != null) && (file.getType() == 3))
           {
             RectFlash rectFlash = new RectFlash();
             
             rectFlash.setFileName(file.getFileName());
             rectFlash.setFileId(file.getFileId());
             rectFlash.setPlayTime(0);
             rectFlash.setProgramId(this.programID);
             rectFlash.setRectId(this.rectID);
             rectFlash.setScaleStyle(3);
             rectFlash.setSwapStyle(0);
             rectFlash.setSequenceNum(2147483647);
             rectFlash.setTaskId(this.taskID);
             rectFlash.setSwapTime(2);
             
             getPluginRectContentHandle().create(rectFlash);
           }
         }
         catch (Exception e)
         {
           e.printStackTrace();
         }
       }
     }
     JsonUtils.writeSuccess(this.servletResponse);
   }
   
   public String toEditFlash()
   {
     String[] idArrary = null;
     if ((this.selectIDs != null) && ((this.selectIDs = this.selectIDs.trim()).length() > 0)) {
       idArrary = this.selectIDs.split(",");
     }
     if ((idArrary != null) && (idArrary.length > 0))
     {
       int contentId0 = Integer.parseInt(idArrary[0]);
       
       RectContent rectContent = getPluginRectContentHandle().read(
         this.taskID, this.programID, this.rectID, contentId0);
       if ((rectContent != null) && ((rectContent instanceof ContentFlash)))
       {
         this.mContentFlash = ((ContentFlash)rectContent);
         if (idArrary.length == 1) {
           setFilePathSize(rectContent);
         }
       }
     }
     return "editflash";
   }
   
   public void saveFlashPlayProperty()
   {
     String[] idArrary = null;
     if ((this.selectIDs != null) && ((this.selectIDs = this.selectIDs.trim()).length() > 0)) {
       idArrary = this.selectIDs.split(",");
     }
     if ((idArrary != null) && (idArrary.length > 0))
     {
       for (int i = 0; i < idArrary.length; i++) {
         try
         {
           ContentFlash contentFlash = null;
           
           int contentId = Integer.parseInt(idArrary[i]);
           
           RectContent rectContent = getPluginRectContentHandle().read(
             this.mContentFlash.getTaskId(), 
             this.mContentFlash.getProgramId(), 
             this.mContentFlash.getRectId(), 
             contentId);
           if ((rectContent != null) && ((rectContent instanceof ContentFlash)))
           {
             contentFlash = (ContentFlash)rectContent;
             
             contentFlash.setPlayTime(this.mContentFlash.getPlayTime());
             contentFlash.setScaleStyle(this.mContentFlash.getScaleStyle());
             contentFlash.setSwapStyle(this.mContentFlash.getSwapStyle());
             contentFlash.setSwapTime(this.mContentFlash.getSwapTime());
             if (!getPluginRectContentHandle().modify(contentFlash))
             {
               JsonUtils.writeError(this.servletResponse);
               return;
             }
           }
         }
         catch (Exception e)
         {
           JsonUtils.writeError(this.servletResponse);
           e.printStackTrace();
           return;
         }
       }
       JsonUtils.writeSuccess(this.servletResponse);
     }
   }
   
   public String webUrlSelect()
   {
     return "weburlselect";
   }
   
   public String toAddWebUrl()
   {
     return "addweburl";
   }
   
   public void addWebUrl()
   {
     this.mRectWebUrl.setSequenceNum(2147483647);
     try
     {
       if (getPluginRectContentHandle().create(this.mRectWebUrl)) {
         JsonUtils.writeSuccess(this.servletResponse);
       } else {
         JsonUtils.writeError(this.servletResponse);
       }
     }
     catch (Exception e)
     {
       JsonUtils.writeError(this.servletResponse);
       e.printStackTrace();
     }
   }
   
   public String toEditWebUrl()
   {
     try
     {
       this.taskID = this.mRectWebUrl.getTaskId();
       this.programID = this.mRectWebUrl.getProgramId();
       this.rectID = this.mRectWebUrl.getRectId();
       
       RectContent rectContent = getPluginRectContentHandle().read(
         this.taskID, this.programID, this.rectID, this.mRectWebUrl.getContentId());
       if ((rectContent != null) && ((rectContent instanceof RectWebUrl))) {
         this.mRectWebUrl = ((RectWebUrl)rectContent);
       }
       return "editweburl";
     }
     catch (Exception ex)
     {
       this.mException = ex;
     }
     return "exception";
   }
   
   public void saveWebUrl()
   {
     try
     {
       RectContent rectContent = getPluginRectContentHandle().read(
         this.mRectWebUrl.getTaskId(), 
         this.mRectWebUrl.getProgramId(), 
         this.mRectWebUrl.getRectId(), 
         this.mRectWebUrl.getContentId());
       if ((rectContent != null) && ((rectContent instanceof RectWebUrl)))
       {
         RectWebUrl old = (RectWebUrl)rectContent;
         old.setRefreshTime(this.mRectWebUrl.getRefreshTime());
         old.setPlayTime(this.mRectWebUrl.getPlayTime());
         old.setUrl(this.mRectWebUrl.getUrl());
         if (getPluginRectContentHandle().modify(old))
         {
           JsonUtils.writeSuccess(this.servletResponse);
           return;
         }
       }
     }
     catch (Exception ex)
     {
       ex.printStackTrace();
       this.mException = ex;
       
       JsonUtils.writeError(this.servletResponse);
     }
   }
   
   public void rectPreviewImage()
   {
     BufferedImage bufferedImage = null;
     try
     {
       RectContent content = getPluginRectContentHandle().readFirst(
         this.taskID, this.programID, this.rectID);
       if (content != null) {
         bufferedImage = ImageOutputUtils.getOutputPreviewImage(
           content, getFileHandle(), this.RequestW.intValue(), this.RequestH.intValue());
       }
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
     ImageOutputUtils.writeBufferedImage(this.servletResponse, bufferedImage);
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.PluginRectAction
 * JD-Core Version:    0.7.0.1
 */