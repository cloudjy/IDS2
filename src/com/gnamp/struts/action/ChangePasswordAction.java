 package com.gnamp.struts.action;
 
 import com.gnamp.server.handle.UserHandle;
 import com.gnamp.struts.utils.JsonUtils;
 import com.gnamp.struts.vo.UserVo;
 import org.apache.log4j.Logger;
 
 public class ChangePasswordAction
   extends VersionConvertAction<UserHandle>
 {
   private static final Logger log = Logger.getLogger(ChangePasswordAction.class);
   UserVo user = new UserVo();
   
   public UserVo getUser()
   {
     return this.user;
   }
   
   public void setUser(UserVo user)
   {
     this.user = user;
   }
   
   public void validateChange()
   {
     if (this.user.getOldPassword().equals(this.user.getNewPassword())) {
       addFieldError("user", "旧密码与新密码相同");
     }
   }
   
   public void change()
   {
     try
     {
       ((UserHandle)getHandle()).modifyPassword(this.user.getNewPassword(), this.user.getOldPassword());
       JsonUtils.writeSuccess(this.servletResponse);
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorMessage(this.servletResponse, "更改密码失败");
       log.error(e.getMessage());
     }
   }
   
   protected Class<UserHandle> getHandleClass()
   {
     return UserHandle.class;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.ChangePasswordAction
 * JD-Core Version:    0.7.0.1
 */