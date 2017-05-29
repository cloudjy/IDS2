 package com.gnamp.struts.action;
 
 import com.gnamp.server.IUserInfo;
 import com.gnamp.server.handle.Handle;
 import common.Logger;
 import org.apache.commons.beanutils.ConstructorUtils;
 
 public abstract class VersionConvertAction<C>
   extends JSONAction
 {
   protected <T> T getHandle(Class<T> clazz)
   {
     try
     {
       return (T) ConstructorUtils.invokeExactConstructor(clazz, new Object[] { this }, new Class[] { IUserInfo.class });
     }
     catch (Exception e)
     {
       log.error("得到Handle失败", e);
     }
     return null;
   }
   
   private C handle = null;
   
   protected abstract Class<? extends Handle> getHandleClass();
   
   protected C getHandle()
   {
     try
     {
       return this.handle != null ? this.handle : (C) ConstructorUtils.invokeExactConstructor(getHandleClass(), new Object[] { this }, new Class[] { IUserInfo.class });
     }
     catch (Exception e)
     {
       log.error("得到Handle失败");
     }
     return null;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.VersionConvertAction
 * JD-Core Version:    0.7.0.1
 */