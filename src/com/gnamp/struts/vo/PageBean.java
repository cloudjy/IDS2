 package com.gnamp.struts.vo;
 
 public class PageBean
 {
   private int currentPage = 1;
   private int pageSize = 20;
   private int totalRows = 0;
   
   public int getCurrentPage()
   {
     return this.currentPage;
   }
   
   public void setCurrentPage(int currentPage)
   {
     if (currentPage <= 1) {
       this.currentPage = 1;
     } else if (currentPage < getTotalPages()) {
       this.currentPage = getTotalPages();
     } else {
       this.currentPage = currentPage;
     }
   }
   
   public int getTotalPages()
   {
     if (this.totalRows == 0) {
       return 1;
     }
     return this.totalRows % this.pageSize == 0 ? this.totalRows / this.pageSize : this.totalRows / this.pageSize + 1;
   }
   
   public int getPageSize()
   {
     return this.pageSize;
   }
   
   public void setPageSize(int pageSize)
   {
     this.pageSize = pageSize;
   }
   
   public int getTotalRows()
   {
     return this.totalRows;
   }
   
   public void setTotalRows(int totalRows)
   {
     this.totalRows = totalRows;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.vo.PageBean
 * JD-Core Version:    0.7.0.1
 */