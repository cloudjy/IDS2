 package com.gnamp.struts.action;
 
 import com.gnamp.server.InnerException;
 import com.gnamp.server.NameExistException;
 import com.gnamp.server.handle.SourceCategoryHandle;
 import com.gnamp.server.model.SourceCategory;
 import com.gnamp.struts.tree.ITree;
 import com.gnamp.struts.tree.TreeAdapter;
 import com.gnamp.struts.utils.JsonUtils;
 import com.gnamp.struts.vo.FilterCategory;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.log4j.Logger;
 
 public class SourceCategoryAction
   extends JSONAction
 {
   private static final Logger log = Logger.getLogger(SourceCategoryAction.class);
   private boolean az;
   SourceCategoryHandle handle = null;
   private int id;
   private SourceCategory source;
   
   public void add()
   {
     try
     {
       if ((this.source.getCategoryName() == null) || ("".equals(this.source.getCategoryName())))
       {
         JsonUtils.writeError(this.servletResponse, getText("name_cannot_null"), "NameCannotNull");
         return;
       }
       if (getHandle().create(this.source)) {
         response(JSONSuccessString(JSONObjectToString(this.source)));
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
         response(JSONErrorString(getText("adddirfaild")));
       }
       log.error(e.getMessage());
     }
     catch (Exception e)
     {
       response(JSONErrorString(getText("adddirfaild")));
       log.error(e.getMessage());
     }
   }
   
   public void brother()
   {
     try
     {
       response(JSONSuccessString(JSONArrayToString(brotherLogic())));
     }
     catch (Exception e)
     {
       log.error(e.getMessage());
       response(JSONErrorString());
     }
   }
   
   private List<ITree<SourceCategory>> brotherLogic()
   {
     List<ITree<SourceCategory>> cates = getSourceInCateEdit();
     ITree<SourceCategory> currCategoryTree = new TreeAdapter(getId());
     if (cates.contains(currCategoryTree)) {
       cates.remove(currCategoryTree);
     }
     return cates;
   }
   
   public void currentCate()
   {
     try
     {
       response(JSONSuccessString(JSONArrayToString(getCate())));
     }
     catch (Exception e)
     {
       log.error(e.getMessage());
       response(JSONErrorString());
     }
   }
   
   public void edit()
   {
     try
     {
       if ((getSource().getCategoryName() == null) || 
         ("".equals(getSource().getCategoryName())))
       {
         JsonUtils.writeError(this.servletResponse, getText("name_cannot_null"), "NameCannotNull");
         return;
       }
       if (getHandle().modify(getSource())) {
         response(JSONSuccessString(JSONObjectToString(getSource())));
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
         response(JSONErrorString(getText("updatefaild")));
       }
       log.error(e.getMessage());
     }
     catch (Exception e)
     {
       response(JSONErrorString(getText("updatefaild")));
       log.error(e.getMessage());
     }
   }
   
   private List<ITree<SourceCategory>> getCate()
   {
     SourceCategory categoryTemp = new SourceCategory();
     List<ITree<SourceCategory>> cates = new ArrayList();
     if (getId() > 0) {
       categoryTemp = haveParent(cates);
     }
     getCategoryParents(categoryTemp, cates);
     return cates;
   }
   
   private List getCategoryNotThis()
   {
       return getCategorys(new FilterCategory() {

           public void execute(List catevos, ITree vo)
           {
               if(vo != null && getSource() != null && vo.getId() != getSource().getCategoryId())
                   catevos.add(vo);
           }
       });
   }
   
   private void getCategoryParents(SourceCategory category, List<ITree<SourceCategory>> cates)
   {
     if ((category == null) || (category.getParentId() == 0)) {
       return;
     }
     SourceCategory parent = getHandle().read(category.getParentId());
     if (parent != null)
     {
       cates.add(new TreeAdapter(parent.getCategoryId(), parent.getCategoryName(), parent.getChildNum() > 0));
       getCategoryParents(parent, cates);
     }
   }
   
   private <T> List<ITree<T>> getCategorys(FilterCategory<T> source)
   {
     List<SourceCategory> categorys = getHandle().readChildren(getListSql(), 
       setParameter(new HashMap()));
     List<ITree<T>> catevos = new ArrayList();
     for (SourceCategory cate : categorys) {
       source.execute(catevos, new TreeAdapter(cate.getCategoryId(), cate.getCategoryName(), cate.getChildNum() > 0));
     }
     return catevos;
   }
   
   private SourceCategoryHandle getHandle()
   {
     return this.handle == null ? new SourceCategoryHandle(this) : this.handle;
   }
   
   public int getId()
   {
     return this.id;
   }
   
   private String getListSql()
   {
     StringBuffer sql = new StringBuffer();
     sql.append("SELECT ");
     sql.append("A.CAT_ID AS CAT_ID, ");
     sql.append("A.NAME AS NAME, ");
     sql.append("A.DESCP AS DESCP, ");
     sql.append("A.PARENT_ID AS PARENT_ID, ");
     sql.append("B.NAME AS PARENT_NAME, ");
     sql.append("A.DEPTH AS DEPTH, ");
     sql.append("A.SUB_NUM AS SUB_NUM, ");
     sql.append("A.CREATE_TIME AS CREATE_TIME, ");
     sql.append("A.CREATE_USER AS CREATE_USER ");
     sql.append("FROM (SELECT * FROM tb_source_category WHERE CSTM_ID=:CSTM_ID ");
     sql.append("AND  " + (
       getId() == 0 ? "PARENT_ID IS NULL " : "PARENT_ID=:PARENT_ID"));
     sql.append(" ) AS A ");
     sql.append("LEFT JOIN tb_source_category AS B ON A.PARENT_ID=B.CAT_ID ");
     sql.append(isAz() ? " ORDER BY A.NAME ASC " : " ORDER BY A.NAME DESC ");
     return sql.toString();
   }
   
   public SourceCategory getSource()
   {
     return this.source;
   }
   
   private List<ITree<SourceCategory>> getSourceInCateEdit()
   {
     List<ITree<SourceCategory>> cates = new ArrayList();
     SourceCategory categoryTemp = new SourceCategory();
     if (getId() > 0) {
       categoryTemp = haveParent(cates);
     }
     getCategoryParents(categoryTemp, cates);
     return cates;
   }
   
   private List getSourceList()
   {
       return getCategorys(new FilterCategory() {

           public void execute(List catevos, ITree vo)
           {
               if(vo != null)
                   catevos.add(vo);
           }
       });
   }
   
   private SourceCategory haveParent(List<ITree<SourceCategory>> cates)
   {
     SourceCategory vo = getHandle().read(getId());
     cates.add(new TreeAdapter(vo.getCategoryId(), vo.getCategoryName(), vo.getChildNum() > 0));
     return vo;
   }
   
   public boolean isAz()
   {
     return this.az;
   }
   
   public void listNotThis()
   {
     try
     {
       response(JSONSuccessString(JSONArrayToString(getCategoryNotThis())));
     }
     catch (Exception e)
     {
       e.printStackTrace();
       log.error(e.getMessage());
       response(JSONErrorString());
     }
   }
   
   public String pre_add()
   {
     return "add";
   }
   
   public String pre_edit()
   {
     try
     {
       SourceCategory cateTemp = getHandle().read(getId());
       this.servletRequest.setAttribute("cate", cateTemp);
       this.servletRequest.setAttribute("brotherList", getCate());
     }
     catch (Exception e)
     {
       log.error(e.getMessage());
       response(JSONErrorString());
     }
     return "edit";
   }
   
   public void remove()
   {
     try
     {
       if (getHandle().remove(getSource().getCategoryId())) {
         response(JSONSuccessString());
       }
     }
     catch (Exception e)
     {
       response(JSONErrorString(getText("deldirfaild")));
       log.error(e.getMessage(), e);
     }
   }
   
   public void setAz(boolean az)
   {
     this.az = az;
   }
   
   public void setId(int id)
   {
     this.id = id;
   }
   
   private Map<String, Object> setParameter(Map<String, Object> parameters)
   {
     parameters.put("CSTM_ID", Integer.valueOf(getCstmId()));
     if (getId() != 0) {
       parameters.put("PARENT_ID", Integer.valueOf(getId()));
     }
     return parameters;
   }
   
   public void setSource(SourceCategory source)
   {
     this.source = source;
   }
   
   public void tags()
   {
     try
     {
       response(JSONArrayToString(getSourceList()));
     }
     catch (Exception e)
     {
       e.printStackTrace();
       response(JSONErrorString());
     }
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.SourceCategoryAction
 * JD-Core Version:    0.7.0.1
 */