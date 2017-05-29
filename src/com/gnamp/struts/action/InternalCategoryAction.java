 package com.gnamp.struts.action;
 
 import com.gnamp.server.handle.Handle;
 import com.gnamp.server.handle.SystemSourceHandle;
 import com.gnamp.server.model.MarqueeXML;
 import com.gnamp.server.model.SystemSource;
 import com.gnamp.struts.filter.Context;
 import com.gnamp.struts.tree.ITree;
 import com.gnamp.struts.tree.TreeAdapter;
 import com.gnamp.struts.utils.CollectionPage;
 import com.gnamp.struts.utils.MapTool;
 import com.gnamp.struts.vo.PageBean;
 import com.gnamp.struts.vo.SystemSourceCategoryVo;
 import common.Logger;
 import java.util.ArrayList;
 import java.util.List;
 import org.dom4j.Document;
 import org.dom4j.DocumentException;
 import org.dom4j.Element;
 import org.dom4j.io.SAXReader;
 
 public class InternalCategoryAction
   extends VersionConvertAction<SystemSourceHandle>
 {
   private static final Logger log = Logger.getLogger(InternalCategoryAction.class);
   private SystemSource internaltag;
   private PageBean page;
   private int id;
   private int sourceid;
   private int state;
   
   public SystemSource getInternaltag()
   {
     return this.internaltag;
   }
   
   public void setInternaltag(SystemSource internaltag)
   {
     this.internaltag = internaltag;
   }
   
   public PageBean getPage()
   {
     return this.page;
   }
   
   public void setPage(PageBean page)
   {
     this.page = page;
   }
   
   public int getSourceid()
   {
     return this.sourceid;
   }
   
   public void setSourceid(int sourceid)
   {
     this.sourceid = sourceid;
   }
   
   public int getState()
   {
     return this.state;
   }
   
   public void setState(int state)
   {
     this.state = state;
   }
   
   public int getId()
   {
     return this.id;
   }
   
   public void setId(int id)
   {
     this.id = id;
   }
   
   public void list()
   {
     try
     {
       response(JSONArrayToString(getTemplateList(
         Context.getResourceURL("InternalTag.xml"), getId())));
     }
     catch (Exception e)
     {
       response(JSONErrorString());
       log.error(e.getMessage(), e);
     }
   }
   
   public List showObject(List ts, int index, int length)
   {
       if(length == -1)
       {
           index = 1;
           length = ts.size();
           getPage().setPageSize(ts.size());
       }
       CollectionPage pm = new CollectionPage(ts, length);
       return pm.getObjects(index);
   }
   
   public void query()
   {
     try
     {
       List<SystemSource> tags = SystemSourceHandle.getTagsSystemSource(
         getId());
       getPage().setTotalRows(tags.size());
       response(JSONSuccessString(
         JSONArrayToString(showObject(tags, getPage()
         .getCurrentPage(), getPage().getPageSize())), 
         new MapTool().putObject("page", getPage())));
     }
     catch (Exception e)
     {
       response(JSONErrorString());
       log.error(e.getMessage(), e);
     }
   }
   
   public void categorys()
   {
     try
     {
       List<SystemSourceCategoryVo> tags = 
         SystemSourceHandle.getCategorysSystemSource(
         getId());
       getPage().setTotalRows(tags.size());
       response(JSONSuccessString(
         JSONArrayToString(showObject(tags, getPage()
         .getCurrentPage(), getPage().getPageSize())), 
         new MapTool().putObject("page", getPage())));
     }
     catch (Exception e)
     {
       response(JSONErrorString());
       log.error(e.getMessage(), e);
     }
   }
   
   public String select_text()
   {
     return "select_text";
   }
   
   public SystemSource getTagDetail(SystemSource sourceParam)
     throws DocumentException
   {
     List<SystemSource> tags = SystemSourceHandle.getTagsSystemSource(
       sourceParam.getCatId());
     for (SystemSource source : tags) {
       if ((source.getSourceId() == sourceParam.getSourceId()) && 
         (source.getCatId() == sourceParam.getCatId())) {
         return source;
       }
     }
     return null;
   }
   
   public void select_word_list()
   {
     try
     {
       List<MarqueeXML> xml = 
         SystemSourceHandle.getMarqueeXmls(getInternaltag().getSourceId());
       
       response(JSONSuccessString(JSONArrayToString(xml)));
     }
     catch (Exception e)
     {
       response(JSONErrorString());
       log.error(e.getMessage(), e);
     }
   }
   
   public String showcatgorycontext()
   {
     return "showcatgorycontext";
   }
   
   public void getTagsContextByCategoryId()
   {
     try
     {
       response(JSONSuccessString(
         SystemSourceHandle.readAllByCatId(getInternaltag().getCatId())));
     }
     catch (Exception e)
     {
       response(JSONErrorString());
       log.error(e.getMessage(), e);
     }
   }
   
   public void systemSourceCates()
   {
     response(JSONSuccessString(JSONArrayToString(
       Context.getSystemSourceCates())));
   }
   
   private List<ITree<?>> getTemplateList(String path, int id)
     throws DocumentException
   {
     List<ITree<?>> groups = new ArrayList();
     
     Document document = new SAXReader().read(path);
     if (id == 0)
     {
       groups.addAll(getTree(document.getRootElement().elements()));
     }
     else
     {
       Element element = search(document.getRootElement(), "category", 
         "id", id);
       groups.addAll(getTree(element.elements()));
     }
     return groups;
   }
   
   private List<? extends ITree<?>> getTree(List<Element> elements)
   {
     List<ITree<?>> trees = new ArrayList();
     for (Element e : elements) {
       if ("category".equals(e.getName())) {
         trees.add(new TreeAdapter(Integer.valueOf(e
           .attributeValue("id")).intValue(), e.attributeValue("name"), e
           .element("category") != null));
       }
     }
     return trees;
   }
   
   private Element search(Element element, String tagName, String attributeName, int result)
   {
     List<Element> es = element.elements();
     if (es == null) {
       return null;
     }
     for (Element e : es)
     {
       if (Integer.valueOf(e.attributeValue(attributeName)).intValue() == result) {
         return e;
       }
       if (e.element(tagName) != null)
       {
         Element elementChild = search(e, tagName, attributeName, result);
         if (elementChild != null) {
           return elementChild;
         }
       }
     }
     return null;
   }
   
   public void updateContext()
   {
     try
     {
       SystemSourceHandle.updateByCatid(getInternaltag().getCatId());
       
       response(JSONSuccessString(SystemSourceHandle.readText(getInternaltag().getCatId())));
     }
     catch (Exception e)
     {
       log.error(e);
       try
       {
         response(JSONSuccessString(SystemSourceHandle.readText(getInternaltag().getCatId())));
       }
       catch (DocumentException e1)
       {
         e1.printStackTrace();
       }
     }
   }
   
   protected Class<? extends Handle> getHandleClass()
   {
     return SystemSourceHandle.class;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.InternalCategoryAction
 * JD-Core Version:    0.7.0.1
 */