 package com.gnamp.struts.action;
 
 import com.gnamp.server.InnerException;
 import com.gnamp.server.NameExistException;
 import com.gnamp.server.handle.RoleHandle;
 import com.gnamp.server.handle.UserHandle;
 import com.gnamp.server.model.Role;
 import com.gnamp.server.model.User;
 import com.gnamp.server.query.Condition;
 import com.gnamp.server.query.Order;
 import com.gnamp.server.query.Result;
 import com.gnamp.server.query.UserColumn;
 import com.gnamp.struts.utils.JsonUtils;
 import com.gnamp.struts.utils.MapTool;
 import com.gnamp.struts.vo.PageBean;
 import com.gnamp.struts.vo.UserEditVo;
 import com.gnamp.struts.vo.UserListVo;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Stack;
 import java.util.Vector;
 import org.apache.log4j.Logger;
 
 public class UserAction
   extends VersionConvertAction<UserHandle>
 {
   private static final String DEFAULT_PASSWORD = "123456";
   private static final Logger log = Logger.getLogger(UserAction.class);
   private List<Integer> roles = new ArrayList();
   private PageBean page = new PageBean();
   private User user;
   List<Integer> userid;
   private String az;
   
   public List<Integer> getUserid()
   {
     return this.userid;
   }
   
   public void setUserid(List<Integer> userid)
   {
     this.userid = userid;
   }
   
   public void list()
   {
     try
     {
       response(JSONSuccessString(JSONArrayToString(getUserRoleVO())));
     }
     catch (Exception e)
     {
       log.error(e.getMessage());
       response(JSONErrorString());
     }
   }
   
   public void validateUpdateRole()
   {
     if (this.user == null)
     {
       addFieldError("user", getText("userisnull"));
       return;
     }
     if (this.user.getUserId() < 0) {
       addFieldError("user", getText("usererror"));
     }
   }
   
   public void updateRole()
   {
     UserHandle h = new UserHandle(this);
     try
     {
       List<Integer> currRoles = toIntegers(h.readUserRoles(this.user.getUserId()));
       List<Integer> intersection = new ArrayList(this.roles);
       intersection.retainAll(currRoles);
       
       this.roles.removeAll(intersection);
       currRoles.removeAll(intersection);
       for (Integer r : currRoles)
       {
         h.cancelRole(this.user.getUserId(), r.intValue());
         LogHelp.userEvent(getCstmId(), getUserName(), getEnglish("assignroleWithUser"), getEnglish("assignroleWithUser") + "：[" + r + "]");
       }
       for (Integer r : this.roles)
       {
         h.assignRole(this.user.getUserId(), r.intValue());
         LogHelp.userEvent(getCstmId(), getUserName(), getEnglish("assignroleWithUser"), getEnglish("assignroleWithUser") + "：[" + r + "]");
       }
       response(JSONSuccessString(JSONArrayToString(getRoles())));
     }
     catch (Exception e)
     {
       log.error(e.getMessage());
       response(JSONErrorString(getText("alterroleerror")));
     }
   }
   
   private List<Integer> toIntegers(List<Role> roles)
   {
     List<Integer> list = new ArrayList();
     for (Role i : roles) {
       list.add(Integer.valueOf(i.getRoleId()));
     }
     return list;
   }
   
   public String preedit()
   {
     return "preedit";
   }
   
   public void roleList()
   {
     try
     {
       response(JSONSuccessString(JSONArrayToString(getRoles())));
     }
     catch (Exception e)
     {
       log.error(e.getMessage());
     }
   }
   
   public String preeditattr()
   {
     return "editattr";
   }
   
   public void validateEditattr()
   {
     if (this.user == null)
     {
       addFieldError("user", getText("usernameisnull"));
       return;
     }
     if (this.user.getUserId() < 0) {
       addFieldError("user", getText("useriderror"));
     }
     String password = this.user.getPassword();
     if ((password.length() < 4) || (password.length() > 30)) {
       addFieldError("password", getText("passwordcheckerror"));
     } else if (!password.matches("^[0-9a-zA-Z]+$")) {
       addFieldError("password", getText("passwordconserror"));
     }
   }
   
   public void editattr()
   {
     UserHandle handle = new UserHandle(this);
     try
     {
       if (handle.resetUserPassword(this.user.getUserId(), this.user.getPassword()))
       {
         response(JSONSuccessString(JSONObjectToString(this.user)));
         logEvent(getEnglish("resetpassword"), getEnglish("resetpassword") + ":[" + handle.read(this.user.getUserId()).getUserName() + "]");
       }
       else
       {
         throw new Exception(getText("passwordchangeerror"));
       }
     }
     catch (InnerException e)
     {
       String innerType = e.getInnerType();
       if (innerType.equals("Invalid passord")) {
         JsonUtils.writeError(this.servletResponse, getText("passwordcheckerror"), "InvalidPassord");
       } else if (innerType.equals("Invalid passord format")) {
         JsonUtils.writeError(this.servletResponse, getText("passwordcheckerror"), "InvalidPassordFormat");
       } else {
         JsonUtils.writeErrorMessage(this.servletResponse, getText("passwordchangeerror"));
       }
       log.error(e.getMessage());
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorMessage(this.servletResponse, getText("passwordchangeerror"));
       log.error(e.getMessage());
     }
   }
   
   public void del()
   {
     Stack<Integer> errors = new Stack();
     UserHandle handle = new UserHandle(this);
     try
     {
       for (Integer uid : this.userid)
       {
         User tempUser = handle.read(uid.intValue());
         if (tempUser == null)
         {
           errors.add(uid);
         }
         else
         {
           List<Role> roles = null;
           if ((roles = handle.readUserRoles(uid.intValue())) != null) {
             for (Role role : roles) {
               handle.cancelRole(uid.intValue(), role.getRoleId());
             }
           }
           if (!handle.remove(uid.intValue())) {
             errors.add(uid);
           } else {
             logEvent(getEnglish("deleteuser"), getEnglish("deleteuser") + ":[" + tempUser.getUserName() + "]");
           }
         }
       }
       if (errors.size() == 0) {
         response(JSONSuccessString());
       } else {
         response(JSONErrorString(getText("deleteusererror") + " ID:" + errors.toString()));
       }
     }
     catch (Exception e)
     {
       log.error(e.getMessage());
       response(JSONErrorString(getText("deleteusererror") + " ID:" + errors.toString()));
     }
   }
   
   public String preadd()
   {
     return "add";
   }
   
   private boolean existsUserByName(String userName)
   {
     return new UserHandle(this).queryInteger(
       "SELECT count(1) FROM tb_user WHERE NAME = :NAME", 
       new MapTool().putObject("NAME", userName)) > 0;
   }
   
   public void validateAdd()
     throws Exception
   {
     if (this.user == null) {
       addFieldError("user", getText("usernameisnull"));
     }
     String userName = this.user.getUserName().trim();
     if (userName.length() == 0) {
       addFieldError("user", getText("usernameisnull"));
     }
     if ((userName.length() < 0) || (userName.length() > 30)) {
       addFieldError("user", getText("usernamelengtherror"));
     }
     if (!userName.matches("^[0-9a-zA-Z]+$")) {
       addFieldError("user", getText("usernamecheckerror"));
     }
     if (("admin".equalsIgnoreCase(this.user.getUserName())) || ("root".equalsIgnoreCase(this.user.getUserName())))
     {
       addFieldError("user", getText("notisadminorroot"));
       return;
     }
     if (existsUserByName(this.user.getUserName())) {
       addFieldError("user", getText("existsusername"));
     }
     String password = this.user.getPassword();
     if ((password.length() < 4) || (password.length() > 30)) {
       addFieldError("password", getText("passwordcheckerror"));
     }
   }
   
   public void addUser()
   {
     UserHandle handle = new UserHandle(this);
     try
     {
       String password = this.user.getPassword();
       if ((password == null) || (password.length() < 4) || (password.length() > 30))
       {
         JsonUtils.writeError(this.servletResponse, getText("passwordcheckerror"), "InvalidPassordFormat");
         return;
       }
       if (handle.create(this.user.getUserName(), password, ""))
       {
         JsonUtils.writeSuccess(this.servletResponse);
         logEvent(getEnglish("createuser"), getEnglish("createuser") + ":[" + this.user.getUserName() + "]");
       }
       else
       {
         throw new Exception(getText("addusererror"));
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
       } else if (innerType.equals("Invalid passord format")) {
         JsonUtils.writeError(this.servletResponse, getText("passwordcheckerror"), "InvalidPassordFormat");
       } else {
         JsonUtils.writeErrorMessage(this.servletResponse, getText("addusererror"));
       }
       log.error(e.getMessage());
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorMessage(this.servletResponse, getText("addusererror"));
       log.error(e.getMessage());
     }
   }
   
   public void getBean()
   {
     UserHandle uh = new UserHandle(this);
     try
     {
       response(JSONSuccessString(JSONObjectToString(uh.read(this.user.getUserId()))));
     }
     catch (Exception e)
     {
       response(JSONErrorString(getText("queryusererror")));
       log.error(e.getMessage());
     }
   }
   
   private List<UserEditVo> getRoles()
   {
     RoleHandle handle = new RoleHandle(this);
     UserHandle uhandle = new UserHandle(this);
     List<UserEditVo> roles = new Vector();
     List<Role> rolees = handle.readAll();
     for (Role role : rolees) {
       roles.add(new UserEditVo().convert(role, containsRole(uhandle.readUserRoles(this.user.getUserId()), role)));
     }
     return roles;
   }
   
   public String getAz()
   {
     return this.az;
   }
   
   public void setAz(String az)
   {
     this.az = az;
   }
   
   private List<User> getPageUsers(UserHandle u)
   {
     Result<User> usersz = u.readPage(new Condition(UserColumn.NAME, "asc".equalsIgnoreCase(getAz()) ? Order.ASC : Order.DESC));
     this.page.setTotalRows(usersz.getTotal());
     return usersz.getResult();
   }
   
   private boolean containsRole(List<Role> roles, Role role)
   {
     if ((roles == null) || (roles.size() == 0)) {
       return false;
     }
     for (Role r : roles) {
       if (role.getRoleId() == r.getRoleId()) {
         return true;
       }
     }
     return false;
   }
   
   private List<UserListVo> getUserRoleVO()
   {
     List<UserListVo> userz = new ArrayList();
     List<User> users = getPageUsers((UserHandle)getHandle());
     if (users != null) {
       for (User user : users) {
         userz.add(new UserListVo().convert(user, (UserHandle)getHandle()));
       }
     }
     return userz;
   }
   
   public PageBean getPage()
   {
     return this.page;
   }
   
   public void setPage(PageBean page)
   {
     this.page = page;
   }
   
   public void setRoles(List<Integer> roles)
   {
     this.roles = roles;
   }
   
   public User getUser()
   {
     return this.user;
   }
   
   public void setUser(User user)
   {
     this.user = user;
   }
   
   protected Class<UserHandle> getHandleClass()
   {
     return UserHandle.class;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.UserAction
 * JD-Core Version:    0.7.0.1
 */