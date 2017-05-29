 package com.gnamp.struts.action;
 
 import com.gnamp.server.InnerException;
 import com.gnamp.server.NameExistException;
 import com.gnamp.server.handle.CategoryHandle;
 import com.gnamp.server.model.Category;
 import com.gnamp.struts.tree.ITree;
 import com.gnamp.struts.tree.TreeAdapter;
 import com.gnamp.struts.utils.JsonUtils;
 import com.gnamp.struts.vo.FilterCategory;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.commons.lang.StringUtils;
 import org.apache.log4j.Logger;
 
 public class CategoryAction
   extends VersionConvertAction<CategoryHandle>
 {
   private static final Logger log = Logger.getLogger(CategoryAction.class);
   private boolean az;
   Category cate = null;
   int id;
   
   public void add()
   {
     try
     {
       if (StringUtils.isBlank(getCate().getCategoryName()))
       {
         JsonUtils.writeError(this.servletResponse, getText("name_cannot_null"), "NameCannotNull");
         return;
       }
       if (((CategoryHandle)getHandle()).create(getCate())) {
         response(JSONSuccessString(JSONObjectToString(getCate())));
       } else {
         throw new Exception(getText("adddirfaild"));
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
   
   public void parentCategory()
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
   
   private List<ITree<Category>> brotherLogic()
   {
     List<ITree<Category>> cates = getCategoryInCateEdit();
     ITree<Category> currCategoryTree = new TreeAdapter(getId());
     if (cates.contains(currCategoryTree)) {
       cates.remove(currCategoryTree);
     }
     return cates;
   }
   
   public void currentCate()
   {
     try
     {
       response(JSONSuccessString(JSONArrayToString(getCategoryInCateEdit())));
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
       if ((getCate().getCategoryName() == null) || 
         ("".equals(getCate().getCategoryName())))
       {
         JsonUtils.writeError(this.servletResponse, getText("name_cannot_null"), "NameCannotNull");
         return;
       }
       if (((CategoryHandle)getHandle()).modify(getCate())) {
         response(JSONSuccessString(JSONObjectToString(getCate())));
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
   
   public Category getCate()
   {
     return this.cate;
   }
   
   private List<ITree<Category>> getCategoryInCateEdit()
   {
     List<ITree<Category>> cates = new ArrayList();
     Category categoryTemp = new Category();
     if (getId() > 0) {
       categoryTemp = haveParent(cates);
     }
     getCategoryParents(categoryTemp, cates);
     return cates;
   }
   
 
   private List getCategoryList()
   {
       return getCategorys(new FilterCategory() {

           public void execute(List catevos, ITree vo)
           {
               if(vo != null)
                   catevos.add(vo);
           }
       });
   }
   
   private List getCategoryNotThis()
   {
       return getCategorys(new FilterCategory() {

           public void execute(List catevos, ITree vo)
           {
               if(vo != null && vo.getId() != getCate().getCategoryId())
                   catevos.add(vo);
           }
       }
);
   }
   
   private void getCategoryParents(Category category, List<ITree<Category>> cates)
   {
     if ((category == null) || (category.getParentId() == 0)) {
       return;
     }
     Category parent = ((CategoryHandle)getHandle()).read(category.getParentId());
     if (parent != null)
     {
       cates.add(new TreeAdapter(parent.getCategoryId(), parent.getCategoryName(), parent.getChildNum() > 0));
       getCategoryParents(parent, cates);
     }
   }
   
   private List<ITree<Category>> getCategorys(FilterCategory<Category> source)
   {
     List<Category> categorys = ((CategoryHandle)getHandle()).readChildren(getListSql(), 
       setParameter(new HashMap()));
     List<ITree<Category>> catevos = new ArrayList();
     for (Category cate : categorys) {
       source.execute(catevos, new TreeAdapter(cate.getCategoryId(), cate.getCategoryName(), cate.getChildNum() > 0));
     }
     return catevos;
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
     sql.append("FROM (SELECT * FROM tb_category WHERE CSTM_ID=:CSTM_ID ");
     sql.append("AND  " + (
       getId() == 0 ? "PARENT_ID IS NULL " : "PARENT_ID=:PARENT_ID"));
     sql.append(" ) AS A ");
     sql.append("LEFT JOIN tb_category AS B ON A.PARENT_ID=B.CAT_ID ");
     sql.append(isAz() ? " order BY A.NAME ASC " : " order BY A.NAME DESC ");
     return sql.toString();
   }
   
   private Category haveParent(List<ITree<Category>> cates)
   {
     Category cateTemp = ((CategoryHandle)getHandle()).read(getId());
     if (cateTemp != null) {
       cates.add(new TreeAdapter(cateTemp.getCategoryId(), cateTemp.getCategoryName(), cateTemp.getChildNum() > 0));
     }
     return cateTemp;
   }
   
   public boolean isAz()
   {
     return this.az;
   }
   
   public void list()
   {
     try
     {
       response(JSONSuccessString(JSONArrayToString(getCategoryList())));
     }
     catch (Exception e)
     {
       log.error(e.getMessage());
       response(JSONErrorString());
     }
   }
   
   public void tree()
   {
     try
     {
       response(JSONArrayToString(getCategoryList()));
     }
     catch (Exception e)
     {
       log.error(e.getMessage());
       response(JSONErrorString());
     }
   }
   
   public void listNotThis()
   {
     try
     {
       response(JSONSuccessString(JSONArrayToString(getCategoryNotThis())));
     }
     catch (Exception e)
     {
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
       Category cateTemp = ((CategoryHandle)getHandle()).read(getId());
       this.servletRequest.setAttribute("cate", cateTemp);
       this.servletRequest.setAttribute("brotherList", getCategoryInCateEdit());
     }
     catch (Exception e)
     {
       log.error(e.getMessage());
     }
     return "edit";
   }
   
   public void remove()
   {
     try
     {
       if (((CategoryHandle)getHandle()).remove(getCate().getCategoryId())) {
         response(JSONSuccessString());
       }
     }
     catch (Exception e)
     {
       response(JSONErrorString(getText("deldirfaild")));
       log.error(e.getMessage());
     }
   }
   
   public void setAz(boolean az)
   {
     this.az = az;
   }
   
   public void setCate(Category cate)
   {
     this.cate = cate;
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
   
   public void validateAdd()
   {
     if (getCate() == null)
     {
       addFieldError("cate", getText("catemodifyisnull"));
       return;
     }
     String cateName = getCate().getCategoryName().trim();
     if ((cateName.length() < 0) || (cateName.length() > 60)) {
       addFieldError("cate", getText("catelengtherror"));
     }
   }
   
   public void validateEdit()
   {
     if (getCate() == null)
     {
       addFieldError("cate", getText("catemodifyisnull"));
       return;
     }
     String cateName = getCate().getCategoryName().trim();
     if ((cateName.length() < 0) || (cateName.length() > 60)) {
       addFieldError("cate", getText("catelengtherror"));
     }
   }
   
   protected Class<CategoryHandle> getHandleClass()
   {
     return CategoryHandle.class;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.CategoryAction
 * JD-Core Version:    0.7.0.1
 */