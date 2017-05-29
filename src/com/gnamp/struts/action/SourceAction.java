 package com.gnamp.struts.action;
 
 import com.gnamp.server.InnerException;
 import com.gnamp.server.NameExistException;
 import com.gnamp.server.handle.FileTagHandle;
 import com.gnamp.server.handle.RssTagHandle;
 import com.gnamp.server.handle.SourceTagHandle;
 import com.gnamp.server.handle.SourceToTerminalHandle;
 import com.gnamp.server.handle.TagHandleFactory;
 import com.gnamp.server.handle.TextTagHandle;
 import com.gnamp.server.model.FileTagData;
import com.gnamp.server.model.ITag;
import com.gnamp.server.model.ITag.SourceTypeProxy;
 import com.gnamp.server.model.ITag.TagType;
 import com.gnamp.server.model.ITextTag;
 import com.gnamp.server.model.MarqueeXML;
 import com.gnamp.server.model.RssCustom;
 import com.gnamp.server.model.RssXml;
 import com.gnamp.server.model.Source;
 import com.gnamp.struts.filter.LoginUser;
 import com.gnamp.struts.utils.CollectionPage;
 import com.gnamp.struts.utils.JsonUtils;
 import com.gnamp.struts.utils.MapTool;
 import com.gnamp.struts.vo.PageBean;
 import java.io.PrintWriter;
 import java.util.ArrayList;
 import java.util.Collections;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import java.util.Stack;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.commons.lang.StringUtils;
 import org.apache.log4j.Logger;
 import org.dom4j.Document;
 import org.dom4j.DocumentHelper;
 import org.dom4j.Element;
 import org.springframework.util.Assert;
 
 public class SourceAction
   extends VersionConvertAction<SourceTagHandle>
 {
   private static final Logger log = Logger.getLogger(SourceAction.class);
   private boolean includeChildren;
   private MarqueeXML marquee;
   private RssXml rss;
   private PageBean page = new PageBean();
   private Source source = new Source();
   private List<Integer> sourcelist = new ArrayList();
   private boolean upordown;
   List<Integer> marqueeids = new ArrayList();
   List<Integer> rssids = new ArrayList();
   FileTagData fileTagData;
   
   public List<Integer> getRssids()
   {
     return this.rssids;
   }
   
   public void setRssids(List<Integer> rssids)
   {
     this.rssids = rssids;
   }
   
   List<String> filetagnames = new ArrayList();
   String sourceType;
   
   public void add()
   {
     try
     {
       boolean createValid = ((SourceTagHandle)TagHandleFactory.getSourceTagHandle(
         this, getSource().getType())).create(getSource());
       if (createValid) {
         response(JSONSuccessString());
       } else {
         throw new Exception("执行添加标签失败");
       }
     }
     catch (NameExistException e)
     {
       JsonUtils.writeError(this.servletResponse, getText("name_existed"), "NameExist");
       log.error(e.getMessage());
     }
     catch (InnerException e)
     {
       String innerType = e.getInnerType();
       if (innerType.equals("name cannot null")) {
         JsonUtils.writeError(this.servletResponse, getText("name_cannot_null"), "NameCannotNull");
       } else {
         response(JSONErrorString());
       }
       log.error(e.getMessage(), e);
     }
     catch (Exception e)
     {
       response(JSONErrorString());
       log.error(e.getMessage(), e);
     }
   }
   
   public String addRss()
   {
     return "addRss";
   }
   
   public void addRssTag()
   {
     try
     {
       if ((getSource() == null) && (getSource().getSourceId() == 0)) {
         throw new IllegalArgumentException("必须从网页传入source.sourceId");
       }
       if (getRss() == null) {
         throw new IllegalArgumentException("rss对象为空");
       }
       RssXml rssXml = ((RssTagHandle)getHandle(RssTagHandle.class)).addRss(getSource(), getRss());
       
 
 
 
 
       response(JSONSuccessString(rssXml != null ? JSONObjectToString(rssXml) : ""));
     }
     catch (Exception e)
     {
       response(JSONErrorString());
       log.error(e.getMessage(), e);
     }
   }
   
   public String addtag()
   {
     return "addtag";
   }
   
   public void updateRssXml()
   {
     try
     {
       ((RssTagHandle)getHandle(RssTagHandle.class)).updateRssXmlText(this.source, this.rssids);
       response(JSONSuccessString());
     }
     catch (Exception e)
     {
       response(JSONErrorString());
       log.error(e);
     }
   }
   
   public void queryRssTagList()
   {
     try
     {
       Assert.notNull(getSource(), "source对象不存在");
       if (getSource().getSourceId() == 0) {
         throw new IllegalArgumentException("必须从网页传入source.sourceId");
       }
       RssCustom source = ((RssTagHandle)getHandle(RssTagHandle.class)).getRssCustom(
         getSource());
       
       getPage().setTotalRows(source.getRsses().size());
       
       response(JSONSuccessString(
         JSONArrayToString(showObject(source.getRsses(), getPage()
         .getCurrentPage(), getPage().getPageSize())), 
         new MapTool().putObject("page", getPage())
         .putObject("sequence", 
         Integer.valueOf((getPage().getCurrentPage() - 1) * 10 + 1))));
     }
     catch (Exception e)
     {
       response(JSONErrorString());
       log.error(e.getMessage(), e);
     }
   }
   
   public String showCustomRss()
   {
     try
     {
       RssCustom source = ((RssTagHandle)getHandle(RssTagHandle.class)).getRssCustom(
         getSource());
       this.servletRequest.setAttribute("rssContext", source.getRssById(this.rss.getId()).getText());
     }
     catch (Exception e)
     {
       log.error(e);
     }
     return "showCustomRss";
   }
   
   public void addTextTag()
   {
     try
     {
       if ((getSource() == null) && (getSource().getSourceId() == 0)) {
         throw new IllegalArgumentException("必须从网页传入source.sourceId");
       }
       ((TextTagHandle)getHandle(TextTagHandle.class)).addMarquee(getSource(), getMarquee());
       
       response(JSONSuccessString());
     }
     catch (Exception e)
     {
       response(JSONErrorString());
       log.error(e.getMessage(), e);
     }
   }
   
   public void changestate()
   {
     try
     {
       ((SourceTagHandle)getHandle()).modifySourceState(getSource());
       response(JSONSuccessString());
     }
     catch (Exception e)
     {
       response(JSONErrorString());
       log.error(e.getMessage(), e);
     }
   }
   
   public void edit()
   {
     try
     {
       ((SourceTagHandle)getHandle()).modifySourceName(getSource());
       response(JSONSuccessString());
     }
     catch (NameExistException e)
     {
       JsonUtils.writeError(this.servletResponse, getText("name_existed"), "NameExist");
       log.error(e.getMessage());
     }
     catch (InnerException e)
     {
       String innerType = e.getInnerType();
       if (innerType.equals("name cannot null")) {
         JsonUtils.writeError(this.servletResponse, getText("name_cannot_null"), "NameCannotNull");
       } else {
         response(JSONErrorString());
       }
       log.error(e.getMessage(), e);
     }
     catch (Exception e)
     {
       response(JSONErrorString());
       log.error(e.getMessage(), e);
     }
   }
   
   public String edittag()
   {
     try
     {
       if (getSourcelist().size() >= 1) {
         setSource(((SourceTagHandle)getHandle()).read(((Integer)getSourcelist().get(0)).intValue()));
       }
     }
     catch (Exception e)
     {
       log.error(e.getMessage(), e);
     }
     return "edittag";
   }
   
   public String editTag()
   {
     String forward = "";
     switch (getSource().getType())
     {
     case 3: 
       forward = "textSelect";
       break;
     case 5: 
       forward = "rssSelect";
       break;
     case 1: 
     case 2: 
     case 4: 
       forward = "imageSelect";
     }
     return forward;
   }
   
   public void editTextTag()
   {
     try
     {
       if ((getSource() == null) || (getSource().getSourceId() == 0)) {
         throw new IllegalArgumentException("必须从网页传入source.sourceId");
       }
       ((TextTagHandle)getHandle(TextTagHandle.class)).editMarquee(getSource(), 
         getMarquee());
       
       response(JSONSuccessString());
     }
     catch (Exception e)
     {
       response(JSONErrorString());
       log.error(e.getMessage());
     }
   }
   
   public void editRssXmlTag()
   {
     try
     {
       if ((getSource() == null) || (getSource().getSourceId() == 0)) {
         throw new IllegalArgumentException("必须从网页传入source.sourceId");
       }
       ((RssTagHandle)getHandle(RssTagHandle.class)).editRssXml(getSource(), getRss());
       
       response(JSONSuccessString());
     }
     catch (Exception e)
     {
       response(JSONErrorString());
       log.error(e.getMessage());
     }
   }
   
   public void exportXML()
   {
     try
     {
       if (getSourcelist().size() == 0) {
         return;
       }
       Document document = DocumentHelper.createDocument();
       LoginUser user = getCurrentUser();
       Element rootElement = document.addElement("tags");
       rootElement.addAttribute("domain", user.getCstm());
       rootElement.addAttribute("user", user.getUsername());
       rootElement.addAttribute("password", user.getPassword());
       rootElement.addAttribute("url", getBasePath() + 
         "/services/TagWebServices");
       for (Integer sourceID : getSourcelist())
       {
         Source _source = ((SourceTagHandle)getHandle()).read(sourceID.intValue());
         Element childElement = rootElement.addElement("tag");
         childElement.addAttribute("id", 
           String.valueOf(_source.getSourceId()));
         childElement.addAttribute("name", _source.getName());
         childElement.addAttribute("type", 
           ITag.SourceTypeProxy.convertTagType(_source).stringValue());
       }
       this.servletResponse.setHeader("Content-Disposition", 
         "attachment;filename=iPub4TagConfig.xml");
       
       this.servletResponse.getWriter().write(document.asXML());
     }
     catch (Exception e)
     {
       log.error(e.getMessage(), e);
     }
   }
   
   private Source genericSource(int sourceId)
   {
     Source source = new Source();
     source.setSourceId(sourceId);
     return source;
   }
   
   public FileTagData getFileTagData()
   {
     return this.fileTagData;
   }
   
   public List<String> getFiletagnames()
   {
     return this.filetagnames;
   }
   
   private String getFilterCategoryId()
   {
     return 
     
 
       getSource().getCatId() == 0 ? " AND CAT_ID IS NULL " : isIncludeChildren() ? " AND CAT_ID IN(SELECT :CAT_ID AS CAT_ID UNION SELECT CAT_ID FROM tb_source_category_tree WHERE PARENT_ID=:CAT_ID) " : getSource().getCatId() == 0 ? "" : 
       " AND CAT_ID=:CAT_ID ";
   }
   
   private String getFilterName()
   {
     return StringUtils.isBlank(this.source.getName()) ? "" : 
       " AND NAME like :NAME ";
   }
   
   private String getFilterType()
   {
     if ((getSourceType() == null) || (getSourceType().length() == 0)) {
       return "";
     }
     StringBuilder builder = new StringBuilder();
     builder.append(" AND ");
     if (ITag.TagType.TEXT.toString().equalsIgnoreCase(getSourceType())) {
       builder.append(" type in (").append(3).append(",").append(5).append(")");
     } else if (ITag.TagType.IMAGE.toString().equalsIgnoreCase(getSourceType())) {
       builder.append(" type = ").append(1);
     } else if (ITag.TagType.VIDEO.toString().equalsIgnoreCase(getSourceType())) {
       builder.append(" type = ").append(2);
     }
     return builder.toString();
   }
   
   protected Class<SourceTagHandle> getHandleClass()
   {
     return SourceTagHandle.class;
   }
   
   public MarqueeXML getMarquee()
   {
     return this.marquee;
   }
   
   public List<Integer> getMarqueeids()
   {
     return this.marqueeids;
   }
   
   public String getSourceType()
   {
     return this.sourceType;
   }
   
   public void setSourceType(String sourceType)
   {
     this.sourceType = sourceType;
   }
   
   private List<Source> getObjectList()
   {
     String query = "SELECT f.CSTM_ID,f.CAT_ID,SOURCE_ID, f.NAME AS NAME, f.DESCP AS DESCP, f.VERSION AS VERSION, c.NAME AS CAT_NAME, TYPE, STATE, f.CREATE_TIME, f.CREATE_USER FROM (SELECT * FROM tb_source WHERE CSTM_ID=:CSTM_ID " + 
     
 
 
 
 
 
 
 
 
 
 
 
       getFilterCategoryId() + 
       getFilterType() + 
       getFilterName() + 
       " ORDER BY CREATE_TIME DESC " + 
       ") as f " + 
       "LEFT JOIN tb_source_category as c ON f.CSTM_ID=c.CSTM_ID AND f.CAT_ID=c.CAT_ID ";
     
     Map<String, Object> parameters = new HashMap();
     parameters.put("CSTM_ID", Integer.valueOf(getCstmId()));
     parameters.put("CAT_ID", Integer.valueOf(getSource().getCatId()));
     parameters.put("NAME", "%" + getSource().getName() + "%");
     return ((SourceTagHandle)getHandle()).readChildren(query, parameters);
   }
   
   public PageBean getPage()
   {
     return this.page;
   }
   
   private List<Source> getQueryList()
   {
     List<Source> list = getObjectList();
     
     CollectionPage pm = new CollectionPage(list, 
       getPage().getPageSize() == -1 ? list.size() : getPage()
       .getPageSize());
     
     List<Source> result = (List<Source>) pm.getObjects(getPage().getCurrentPage());
     if (result == null) {
       result = Collections.emptyList();
     }
     this.page.setTotalRows(list.size());
     return result;
   }
   
   public RssXml getRss()
   {
     return this.rss;
   }
   
   public Source getSource()
   {
     return this.source;
   }
   
   public List<Integer> getSourcelist()
   {
     return this.sourcelist;
   }
   
   public String imageSelect()
   {
     return "imageSelect";
   }
   
   public boolean isIncludeChildren()
   {
     return this.includeChildren;
   }
   
   public boolean isUpordown()
   {
     return this.upordown;
   }
   
   public void moveFileTag()
   {
     try
     {
       ((FileTagHandle)TagHandleFactory.getSourceTagHandle(this, getSource().getType())).move(getSource(), getFileTagData(), 
         isUpordown());
       response(JSONSuccessString());
     }
     catch (Exception e)
     {
       response(JSONErrorString());
       log.error("移动文件标签出错,moveFileTag方法", e);
     }
   }
   
   public void moveTextTag()
   {
     try
     {
       if ((getSource() == null) || (getSource().getSourceId() == 0)) {
         throw new IllegalArgumentException(
           "必须从网页传入source.catId和source.sourceId");
       }
       ((TextTagHandle)getHandle(TextTagHandle.class)).move(getSource(), getMarquee(), 
         isUpordown());
       
       response(JSONSuccessString());
     }
     catch (Exception e)
     {
       response(JSONErrorString());
       log.error(e.getMessage());
     }
   }
   
   public void moveRssTag()
   {
     try
     {
       if ((getSource() == null) || (getSource().getSourceId() == 0)) {
         throw new IllegalArgumentException(
           "必须从网页传入source.catId和source.sourceId");
       }
       ((RssTagHandle)getHandle(RssTagHandle.class)).move(getSource(), getRss(), 
         isUpordown());
       
       response(JSONSuccessString());
     }
     catch (Exception e)
     {
       response(JSONErrorString());
       log.error(e.getMessage());
     }
   }
   
   public String preEditTextTag()
   {
     try
     {
       if ((getSource() == null) || (getSource().getSourceId() == 0)) {
         throw new IllegalArgumentException("必须从网页传入source.sourceId");
       }
       ITextTag sourceXml = ((TextTagHandle)getHandle(TextTagHandle.class)).getMarquees(
         getSource());
       int index = -1;
       if ((index = sourceXml.getMarquees().indexOf(getMarquee())) != -1) {
         setMarquee((MarqueeXML)sourceXml.getMarquees().get(index));
       }
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
     return "preEditTextTag";
   }
   
   public String preEditRssTag()
   {
     try
     {
       if ((getSource() == null) || (getSource().getSourceId() == 0)) {
         throw new IllegalArgumentException("必须从网页传入source.sourceId");
       }
       RssCustom sourceXml = ((RssTagHandle)getHandle(RssTagHandle.class)).getRssCustom(
         getSource());
       int index = -1;
       if ((index = sourceXml.getRsses().indexOf(getRss())) != -1) {
         setRss((RssXml)sourceXml.getRsses().get(index));
       }
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
     return "preEditRssTag";
   }
   
   public void query()
   {
     try
     {
       List<Source> list = getQueryList();
       if (list == null)
       {
         response(JSONErrorString());
         return;
       }
       if (getPage().getPageSize() == -1) {
         getPage().setPageSize(list.size());
       }
       response(JSONSuccessString(JSONArrayToString(list), 
         new MapTool().putObject("page", getPage())));
     }
     catch (Exception e)
     {
       response(JSONErrorString());
       log.error("SourceAction!query出错", e);
     }
   }
   
   public void queryAllByCateogoryIdAndType()
   {
     try
     {
       List<Source> list = ((SourceTagHandle)getHandle()).readAll(getSource().getCatId(), 
         getSource().getType());
       
       CollectionPage pm = new CollectionPage(list, getPage()
         .getPageSize() == -1 ? list.size() : getPage()
         .getPageSize());
       
       List<Source> result = (List<Source>) pm.getObjects(getPage().getCurrentPage());
       if (result == null) {
         result = Collections.emptyList();
       }
       this.page.setTotalRows(result.size());
       if (getPage().getPageSize() == -1) {
         getPage().setPageSize(list.size());
       }
       response(JSONSuccessString(JSONArrayToString(result), 
         new MapTool().putObject("page", getPage())));
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
   
   public void queryFileTagList()
   {
     try
     {
       List<FileTagData> tagDatas = 
         ((FileTagHandle)TagHandleFactory.getSourceTagHandle(this, 
         getSource().getType()))
         .queryFileTagList(getSource());
       getPage().setTotalRows(tagDatas.size());
       response(JSONSuccessString(
         JSONArrayToString(showObject(tagDatas, getPage()
         .getCurrentPage(), getPage().getPageSize())), 
         new MapTool().putObject("page", getPage())));
     }
     catch (Exception e)
     {
       response(JSONErrorString());
       log.error("查询文件标签出错,queryFileTagList方法", e);
     }
   }
   
   public void queryTextTagList()
   {
     try
     {
       Assert.notNull(getSource(), "source对象不存在");
       if (getSource().getSourceId() == 0) {
         throw new IllegalArgumentException("必须从网页传入source.sourceId");
       }
       ITextTag source = ((TextTagHandle)getHandle(TextTagHandle.class)).getMarquees(
         getSource());
       
       getPage().setTotalRows(source.getMarquees().size());
       
       response(JSONSuccessString(
         JSONArrayToString(showObject(source.getMarquees(), 
         getPage().getCurrentPage(), getPage().getPageSize())), 
         new MapTool().putObject("page", getPage())
         .putObject("sequence", 
         Integer.valueOf((getPage().getCurrentPage() - 1) * 10 + 1))));
     }
     catch (Exception e)
     {
       response(JSONErrorString());
       log.error(e.getMessage(), e);
     }
   }
   
   public void queryRssList()
   {
     try
     {
       Assert.notNull(getSource(), "source对象不存在");
       if (getSource().getSourceId() == 0) {
         throw new IllegalArgumentException("必须从网页传入source.sourceId");
       }
       RssCustom source = ((RssTagHandle)getHandle(RssTagHandle.class)).getRssCustom(
         getSource());
       
       getPage().setTotalRows(source.getRsses().size());
       
       response(JSONSuccessString(
         JSONArrayToString(showObject(source.getRsses(), getPage()
         .getCurrentPage(), getPage().getPageSize())), 
         new MapTool().putObject("page", getPage())
         .putObject("sequence", 
         Integer.valueOf((getPage().getCurrentPage() - 1) * 10 + 1))));
     }
     catch (Exception e)
     {
       response(JSONErrorString());
       log.error(e.getMessage(), e);
     }
   }
   
   public void remove()
   {
     Stack<String> errors = new Stack();
     try
     {
       for (Integer s : getSourcelist()) {
         try
         {
           if (!((SourceTagHandle)getHandle()).remove(s.intValue())) {
             throw new Exception(String.valueOf(s));
           }
         }
         catch (Exception e)
         {
           log.error(e.getMessage(), e);
           errors.add(e.getMessage());
         }
       }
       if (errors.size() > 0) {
         throw new Exception("有错误存在errors.size>0");
       }
       response(JSONSuccessString());
     }
     catch (Exception e)
     {
       log.error(errors, e);
       response(JSONErrorString(JSONArrayToString(errors)));
     }
   }
   
   public void removeFileTag()
   {
     try
     {
       for (String name : this.filetagnames)
       {
         FileTagData f = new FileTagData();
         f.setName(name);
         
         ((FileTagHandle)TagHandleFactory.getSourceTagHandle(this, 
           getSource().getType()))
           .removeTagSequence(getSource(), f);
       }
       response(JSONSuccessString());
     }
     catch (Exception e)
     {
       response(JSONErrorString());
       log.error("删除文件标签出错,removeFileTag方法", e);
     }
   }
   
   public void removeTextTag()
   {
     try
     {
       if ((getSource() == null) || (getSource().getSourceId() == 0)) {
         throw new IllegalArgumentException("必须从网页传入source.sourceId");
       }
       for (Iterator localIterator = this.marqueeids.iterator(); localIterator.hasNext();)
       {
         int mid = ((Integer)localIterator.next()).intValue();
         this.marquee = new MarqueeXML();
         this.marquee.setId(mid);
         ((TextTagHandle)getHandle(TextTagHandle.class)).removeMarquee(getSource(), 
           this.marquee);
       }
       response(JSONSuccessString());
     }
     catch (Exception e)
     {
       response(JSONErrorString());
       log.error(e.getMessage());
     }
   }
   
   public void removeRssTag()
   {
     try
     {
       if ((getSource() == null) || (getSource().getSourceId() == 0)) {
         throw new IllegalArgumentException("必须从网页传入source.sourceId");
       }
       for (Iterator localIterator = this.rssids.iterator(); localIterator.hasNext();)
       {
         int mid = ((Integer)localIterator.next()).intValue();
         RssXml rssXml = new RssXml();
         rssXml.setId(mid);
         ((RssTagHandle)getHandle(RssTagHandle.class)).removeRssXml(getSource(), rssXml);
       }
       response(JSONSuccessString());
     }
     catch (Exception e)
     {
       response(JSONErrorString());
       log.error(e.getMessage());
     }
   }
   
   public void setFileTagData(FileTagData fileTagData)
   {
     this.fileTagData = fileTagData;
   }
   
   public void setFiletagnames(List<String> filetagnames)
   {
     this.filetagnames = filetagnames;
   }
   
   public void setIncludeChildren(boolean includeChildren)
   {
     this.includeChildren = includeChildren;
   }
   
   public void setMarquee(MarqueeXML marquee)
   {
     this.marquee = marquee;
   }
   
   public void setMarqueeids(List<Integer> marqueeids)
   {
     this.marqueeids = marqueeids;
   }
   
   public void setPage(PageBean page)
   {
     this.page = page;
   }
   
   public void setRss(RssXml rss)
   {
     this.rss = rss;
   }
   
   public void setSource(Source source)
   {
     this.source = source;
   }
   
   public void setSourcelist(List<Integer> sourcelist)
   {
     this.sourcelist = sourcelist;
   }
   
   public void setUpordown(boolean upordown)
   {
     this.upordown = upordown;
   }
   
   public  <T> List showObject(List<T> ts, int index, int length)
   {
     CollectionPage pm = new CollectionPage(ts, length == -1 ? ts.size() : 
       length);
     if (length == -1) {
       getPage().setPageSize(ts.size());
     }
     return pm.getObjects(index);
   }
   
   public void source_isused()
   {
     try
     {
       boolean flag = false;
       for (Integer sourceId : getSourcelist()) {
         if (((SourceToTerminalHandle)getHandle(SourceToTerminalHandle.class)).isUsed(genericSource(sourceId.intValue())))
         {
           flag = true;
           break;
         }
       }
       response(JSONSuccessString(String.valueOf(flag)));
     }
     catch (Exception e)
     {
       log.error(e.getMessage(), e);
       response(JSONErrorString());
     }
   }
   
   public String uploadFileTagUpload()
   {
     return "uploadFileTagUpload";
   }
   
   public String viewTextTagEdit()
   {
     this.source = ((SourceTagHandle)getHandle()).read(this.source.getSourceId());
     return "addword";
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.SourceAction
 * JD-Core Version:    0.7.0.1
 */