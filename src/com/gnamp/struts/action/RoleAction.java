 package com.gnamp.struts.action;
 
 import com.gnamp.server.InnerException;
 import com.gnamp.server.NameExistException;
 import com.gnamp.server.handle.RoleHandle;
 import com.gnamp.server.model.Privilege;
 import com.gnamp.server.model.Role;
 import com.gnamp.server.query.Condition;
 import com.gnamp.server.query.Order;
 import com.gnamp.server.query.Result;
 import com.gnamp.server.query.RoleColumn;
 import com.gnamp.struts.utils.JsonUtils;
 import com.gnamp.struts.vo.IDVO;
 import com.gnamp.struts.vo.PageBean;
 import com.gnamp.struts.vo.RoleVo;
 import java.util.ArrayList;
 import java.util.List;
 import org.apache.log4j.Logger;
 
 public class RoleAction
   extends JSONAction
 {
   private static final Logger log = Logger.getLogger(RoleAction.class);
   List<Role> roles = new ArrayList();
   PageBean page = new PageBean();
   Role role;
   List<Integer> privileges = new ArrayList();
   int id;
   private String az;
   
   public List<Integer> getPrivileges()
   {
     return this.privileges;
   }
   
   public void setPrivileges(List<Integer> privileges)
   {
     this.privileges = privileges;
   }
   
   public Role getRole()
   {
     return this.role;
   }
   
   public void setRole(Role role)
   {
     this.role = role;
   }
   
   public PageBean getPage()
   {
     return this.page;
   }
   
   public void setPage(PageBean page)
   {
     this.page = page;
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
       response(JSONSuccessString(
         JSONArrayToString(rolesList())));
     }
     catch (Exception e)
     {
       response(JSONErrorString(getText("searchpermissionserror")));
       log.error(e.getMessage());
     }
   }
   
   public String preeditattr()
   {
     return "editattr";
   }
   
   public void getBean()
   {
     try
     {
       response(
         JSONSuccessString(
         JSONObjectToString(
         new RoleHandle(this).read(this.role.getRoleId()))));
     }
     catch (Exception e)
     {
       response(JSONErrorString(getText("getbeanerror")));
       log.error(e.getMessage());
     }
   }
   
   public void editattr()
   {
     RoleHandle handle = new RoleHandle(this);
     try
     {
       Role editRole = handle.read(this.role.getRoleId());
       editRole.setRoleName(this.role.getRoleName());
       editRole.setDescription(this.role.getDescription());
       if (handle.modify(editRole))
       {
         JsonUtils.writeSuccess(this.servletResponse);
         logEvent(getEnglish("editrole"), getEnglish("editrole") + ":[" + this.role.getRoleName() + "]");
       }
       else
       {
         throw new Exception(getText("adderror"));
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
         JsonUtils.writeErrorMessage(this.servletResponse, getText("updatefail"));
       }
       log.error(e.getMessage());
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorMessage(this.servletResponse, getText("updatefail"));
       log.error(e.getMessage());
     }
   }
   
   public String getAz()
   {
     return this.az;
   }
   
   public void setAz(String az)
   {
     this.az = az;
   }
   
   private List<RoleVo> rolesList()
   {
     RoleHandle rh = new RoleHandle(this);
     List<Role> roles = rh.readPage(new Condition(RoleColumn.NAME, "asc".equals(getAz()) ? Order.ASC : Order.DESC)).getResult();
     return getCurrentRoles(roles);
   }
   
   public void permissions()
   {
     try
     {
       response(JSONSuccessString(JSONArrayToString(convertPrivilegeList(getCurrentPrivilege(this.role
         .getRoleId())))));
     }
     catch (Exception e)
     {
       response(JSONErrorString(getText("searchpermissionserror")));
       log.error(e.getMessage());
     }
   }
   
   private List<Privilege> getCurrentPrivilege(int role_id)
   {
     return new RoleHandle(this).readRolePrivileges(role_id);
   }
   
   private List<IDVO> convertPrivilegeList(List<Privilege> privileges)
   {
     if (privileges == null) {
       return new ArrayList();
     }
     List<IDVO> list = new ArrayList();
     for (Privilege p : privileges) {
       list.add(new IDVO(p.getPrivilegeId()));
     }
     return list;
   }
   
   public void add()
   {
     RoleHandle h = new RoleHandle(this);
     try
     {
       if (h.create(this.role.getRoleName(), this.role.getDescription()))
       {
         JsonUtils.writeSuccess(this.servletResponse);
         logEvent(getEnglish("createrole"), getEnglish("createrole") + ":[" + this.role.getRoleName() + "]");
       }
       else
       {
         throw new Exception(getText("adderror"));
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
         JsonUtils.writeErrorMessage(this.servletResponse, getText("adderror"));
       }
       log.error(e.getMessage());
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorMessage(this.servletResponse, getText("adderror"));
       log.error(e.getMessage());
     }
   }
   
   public String preadd()
   {
     return "preadd";
   }
   
   public void del()
   {
     RoleHandle h = new RoleHandle(this);
     try
     {
       for (Integer role : this.privileges)
       {
         List<Privilege> ps = h.readRolePrivileges(role.intValue());
         for (Privilege p : ps) {
           try
           {
             h.removePrivilege(role.intValue(), p.getPrivilegeId());
           }
           catch (Exception e)
           {
             log.error(e.getMessage());
           }
         }
         try
         {
           Role tempRole = h.read(role.intValue());
           if (h.remove(role.intValue())) {
             logEvent(getEnglish("deleterole"), getEnglish("deleterole") + ":[" + tempRole.getRoleName() + "]");
           }
         }
         catch (Exception e)
         {
           log.error(e.getMessage());
         }
       }
       response(JSONSuccessString());
     }
     catch (Exception e)
     {
       response(JSONErrorString(getText("deleteerror")));
       log.error(e.getMessage());
     }
   }
   
   public void updatePermissions()
   {
     RoleHandle r = new RoleHandle(this);
     try
     {
       List<Integer> currentRoles = convertPrivilege(r.readRolePrivileges(this.role.getRoleId()));
       List<Integer> intersection = new ArrayList(this.privileges);
       intersection.retainAll(currentRoles);
       
 
 
 
       this.privileges.removeAll(intersection);
       currentRoles.removeAll(intersection);
       for (Integer p : this.privileges) {
         if (r.assignPrivilege(this.role.getRoleId(), p.intValue())) {
           logEvent(getEnglish("assignpermission"), getEnglish("assignpermission") + ":[" + p + "]");
         }
       }
       for (Integer p : currentRoles) {
         if (r.removePrivilege(this.role.getRoleId(), p.intValue())) {
           logEvent(getEnglish("assignpermission"), getEnglish("assignpermission") + ":[" + p + "]");
         }
       }
       permissions();
     }
     catch (Exception e)
     {
       response(JSONErrorString(getText("updatepermissionserror")));
       log.error(e.getMessage());
     }
   }
   
   private List<Integer> convertPrivilege(List<Privilege> privileges)
   {
     List<Integer> list = new ArrayList();
     for (Privilege i : privileges) {
       list.add(Integer.valueOf(i.getPrivilegeId()));
     }
     return list;
   }
   
   public String preedit()
   {
     try
     {
       RoleHandle rh = new RoleHandle(this);
       this.role = rh.read(this.id);
     }
     catch (Exception e)
     {
       log.error(e.getMessage());
     }
     return "preedit";
   }
   
   private List<RoleVo> getCurrentRoles(List<Role> roles)
   {
     if (roles == null) {
       return new ArrayList();
     }
     List<RoleVo> vos = new ArrayList();
     for (Role r : roles) {
       vos.add(new RoleVo().convert(r));
     }
     return vos;
   }
   
   public String editRole()
   {
     return "edit";
   }
   
   public List<Role> getRoles()
   {
     return this.roles;
   }
   
   public void setRoles(List<Role> roles)
   {
     this.roles = roles;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.RoleAction
 * JD-Core Version:    0.7.0.1
 */