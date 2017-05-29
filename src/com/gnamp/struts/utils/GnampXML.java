 package com.gnamp.struts.utils;
 
 import com.gnamp.struts.filter.Context;
 import com.gnamp.struts.vo.GroupVo;
 import com.gnamp.struts.vo.Item;
 import com.gnamp.struts.vo.Option;
 import com.gnamp.struts.vo.Select;
 import common.Logger;
 import java.io.PrintStream;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Locale;
 import org.dom4j.Attribute;
 import org.dom4j.Document;
 import org.dom4j.DocumentException;
 import org.dom4j.Element;
 import org.dom4j.Node;
 import org.dom4j.io.SAXReader;
 
 public class GnampXML
 {
   private static final String TEMPLATE_PATH = "/gnamp/config-template-";
   private static final Logger LOG = Logger.getLogger(GnampXML.class);
   private static final Document DOCUMENT = getDocument();
   
   private static String getElement(String elementPath)
   {
     return Context.getWebRootPath() + DOCUMENT.selectSingleNode(elementPath).getText();
   }
   
   private static Document getDocument()
   {
     try
     {
       return new SAXReader().read(Context.getResourceURL("gnamp.xml"));
     }
     catch (DocumentException e)
     {
       LOG.error(e.getMessage(), e);
     }
     return null;
   }
   
   public static String getTemplatePath(Locale locale)
   {
     return getElement("/gnamp/config-template-" + locale.toString());
   }
   
   public static List<GroupVo> getTemplateList(String path)
   {
     List<GroupVo> groups = new ArrayList();
     try
     {
       Document document = new SAXReader().read(path);
       List<?> groupList = document.getRootElement().elements();
       for (Object s : groupList) {
         if ((s instanceof Element))
         {
           GroupVo group = new GroupVo();
           Element groupElement = (Element)s;
           group.setName(groupElement.attribute("name").getText());
           List<Element> es = getChildElement(s, null);
           List<Item> items = new ArrayList();
           for (Element e : es) {
             items.add(getItem(e));
           }
           group.setItem(items);
           groups.add(group);
         }
         else
         {
           System.out.println("not element");
         }
       }
     }
     catch (DocumentException e)
     {
       e.printStackTrace();
     }
     return groups;
   }
   
   private static Item getItem(Element e)
   {
     Item item = new Item();
     item.setName(e.attribute("name").getText());
     item.setDesc(e.attribute("desc").getText());
     item.setDefaultValue(e.attribute("defaultvalue").getText());
     item.setCheckexpression(e.attribute("checkexpression").getText());
     item.setSamplevalue(e.attribute("samplevalue").getText());
     if (e.elements().size() > 0)
     {
       List<Item> items = new ArrayList();
       for (Object old : e.elements()) {
         if ((old instanceof Element))
         {
           Element o = (Element)old;
           if ("select".equals(o.getName()))
           {
             Select select = new Select();
             List<Option> options = new ArrayList();
             for (Object eselect : o.elements())
             {
               Option option = new Option();
               if ((eselect instanceof Element))
               {
                 Element elementSelect = (Element)eselect;
                 option.setValue(elementSelect.attributeValue("value"));
                 option.setText(elementSelect.getText());
               }
               options.add(option);
             }
             select.setOptions(options);
             item.setSelect(select);
           }
           else if ("item".equals(o.getName()))
           {
             items.add(getItem(o));
           }
         }
       }
       item.setItems(items);
     }
     return item;
   }
   
   private static List<Element> getChildElement(Object e, String childName)
   {
     List<Element> elements = new ArrayList();
     Element el = null;
     if ((e instanceof Element)) {
       el = (Element)e;
     }
     if (el == null) {
       return elements;
     }
     for (Object l : el.elements()) {
       if ((l instanceof Element)) {
         elements.add((Element)l);
       }
     }
     return elements;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.utils.GnampXML
 * JD-Core Version:    0.7.0.1
 */