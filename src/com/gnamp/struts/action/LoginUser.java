 package com.gnamp.struts.action;
 
 import com.gnamp.server.IUserInfo;
 import com.gnamp.server.model.Customer;
 import com.gnamp.server.model.User;
 
 class LoginUser
   implements IUserInfo
 {
   User user = null;
   Customer cust = null;
   
   LoginUser(User u, Customer c)
   {
     this.user = u;
     this.cust = c;
   }
   
   public int getUserId()
   {
     return this.user.getUserId();
   }
   
   public String getUserName()
   {
     return this.user.getUserName();
   }
   
   public int getCstmId()
   {
     return this.cust.getCstmId();
   }
   
   public String getShortName()
   {
     return this.cust.getShortName();
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.LoginUser
 * JD-Core Version:    0.7.0.1
 */