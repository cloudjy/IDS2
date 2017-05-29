 package com.gnamp.struts.action;
 
 import javax.servlet.ServletException;
 
 class HttpException
   extends ServletException
 {
   public HttpException() {}
   
   public HttpException(String message)
   {
     super(message);
   }
   
   public HttpException(String message, Throwable rootCause)
   {
     super(message, rootCause);
   }
   
   public HttpException(Throwable rootCause)
   {
     super(rootCause);
   }
   
   public HttpException(int statusCode, String message)
   {
     super(message);
     
     this.statusCode = statusCode;
   }
   
   public HttpException(int statusCode, String message, Throwable rootCause)
   {
     super(message, rootCause);
     
     this.statusCode = statusCode;
   }
   
   protected int statusCode = 400;
   
   public int getStatusCode()
   {
     return this.statusCode;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.HttpException
 * JD-Core Version:    0.7.0.1
 */