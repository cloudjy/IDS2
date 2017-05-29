 package com.gnamp.struts.vo;
 
 public class ExcelTitleVo
 {
   String title;
   int width;
   
   public String getTitle()
   {
     return this.title;
   }
   
   public void setTitle(String title)
   {
     this.title = title;
   }
   
   public int getWidth()
   {
     return this.width;
   }
   
   public void setWidth(int width)
   {
     this.width = width;
   }
   
   public ExcelTitleVo(String title, int width)
   {
     this.title = title;
     this.width = width;
   }
   
   public ExcelTitleVo() {}
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.vo.ExcelTitleVo
 * JD-Core Version:    0.7.0.1
 */