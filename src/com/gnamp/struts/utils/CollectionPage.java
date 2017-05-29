 package com.gnamp.struts.utils;
 
 import java.io.PrintStream;
 import java.util.List;
 
 public class CollectionPage
 {
   private int page = 1;
   public int totalPages = 0;
   private int pageRecorders;
   private int totalRows = 0;
   private int pageStartRow = 0;
   private int pageEndRow = 0;
   private boolean hasNextPage = false;
   private boolean hasPreviousPage = false;
   private List<?> list;
   
   public CollectionPage(List<?> list, int pageRecorders)
   {
     init(list, pageRecorders);
   }
   
   public void init(List<?> list, int pageRecorders)
   {
     this.pageRecorders = pageRecorders;
     this.list = list;
     this.totalRows = list.size();
     if (pageRecorders == 0) {
       return;
     }
     this.hasPreviousPage = false;
     if (this.totalRows % pageRecorders == 0) {
       this.totalPages = (this.totalRows / pageRecorders);
     } else {
       this.totalPages = (this.totalRows / pageRecorders + 1);
     }
     if (this.page >= this.totalPages) {
       this.hasNextPage = false;
     } else {
       this.hasNextPage = true;
     }
     if (this.totalRows < pageRecorders)
     {
       this.pageStartRow = 0;
       this.pageEndRow = this.totalRows;
     }
     else
     {
       this.pageStartRow = 0;
       this.pageEndRow = pageRecorders;
     }
   }
   
   public boolean isNext()
   {
     return this.list.size() > 5;
   }
   
   public void setHasPreviousPage(boolean hasPreviousPage)
   {
     this.hasPreviousPage = hasPreviousPage;
   }
   
   public String toString(int temp)
   {
     String str = Integer.toString(temp);
     return str;
   }
   
   public void description()
   {
     String description = "共有数据数:" + getTotalRows() + 
       "共有页数: " + getTotalPages() + 
       "当前页数为:" + getPage() + 
       " 是否有前一页: " + isHasPreviousPage() + 
       " 是否有下一页:" + isHasNextPage() + 
       " 开始行数:" + getPageStartRow() + 
       " 终止行数:" + getPageEndRow();
     
     System.out.println(description);
   }
   
   public List<?> getNextPage()
   {
     this.page += 1;
     
     disposePage();
     
     System.out.println("用户凋用的是第" + this.page + "页");
     description();
     return getObjects(this.page);
   }
   
   private void disposePage()
   {
     if (this.page == 0) {
       this.page = 1;
     }
     if (this.page - 1 > 0) {
       this.hasPreviousPage = true;
     } else {
       this.hasPreviousPage = false;
     }
     if (this.page >= this.totalPages) {
       this.hasNextPage = false;
     } else {
       this.hasNextPage = true;
     }
   }
   
   public List<?> getPreviousPage()
   {
     this.page -= 1;
     if (this.page - 1 > 0) {
       this.hasPreviousPage = true;
     } else {
       this.hasPreviousPage = false;
     }
     if (this.page >= this.totalPages) {
       this.hasNextPage = false;
     } else {
       this.hasNextPage = true;
     }
     description();
     return getObjects(this.page);
   }
   
   public List<?> getObjects(int page)
   {
     if (page == 0) {
       setPage(1);
     } else {
       setPage(page);
     }
     disposePage();
     if (page * this.pageRecorders < this.totalRows)
     {
       this.pageEndRow = (page * this.pageRecorders);
       this.pageStartRow = (this.pageEndRow - this.pageRecorders);
     }
     else
     {
       this.pageEndRow = this.totalRows;
       this.pageStartRow = (this.pageRecorders * (this.totalPages - 1));
     }
     List<?> objects = null;
     if (!this.list.isEmpty()) {
       objects = this.list.subList(this.pageStartRow, this.pageEndRow);
     }
     return objects;
   }
   
   public List<?> getFistPage()
   {
     if (isNext()) {
       return this.list.subList(0, this.pageRecorders);
     }
     return this.list;
   }
   
   public boolean isHasNextPage()
   {
     return this.hasNextPage;
   }
   
   public void setHasNextPage(boolean hasNextPage)
   {
     this.hasNextPage = hasNextPage;
   }
   
   public List<?> getList()
   {
     return this.list;
   }
   
   public void setList(List<?> list)
   {
     this.list = list;
   }
   
   public int getPage()
   {
     return this.page;
   }
   
   public void setPage(int page)
   {
     this.page = page;
   }
   
   public int getPageEndRow()
   {
     return this.pageEndRow;
   }
   
   public void setPageEndRow(int pageEndRow)
   {
     this.pageEndRow = pageEndRow;
   }
   
   public int getPageRecorders()
   {
     return this.pageRecorders;
   }
   
   public void setPageRecorders(int pageRecorders)
   {
     this.pageRecorders = pageRecorders;
   }
   
   public int getPageStartRow()
   {
     return this.pageStartRow;
   }
   
   public void setPageStartRow(int pageStartRow)
   {
     this.pageStartRow = pageStartRow;
   }
   
   public int getTotalPages()
   {
     return this.totalPages;
   }
   
   public void setTotalPages(int totalPages)
   {
     this.totalPages = totalPages;
   }
   
   public int getTotalRows()
   {
     return this.totalRows;
   }
   
   public void setTotalRows(int totalRows)
   {
     this.totalRows = totalRows;
   }
   
   public boolean isHasPreviousPage()
   {
     return this.hasPreviousPage;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.utils.CollectionPage
 * JD-Core Version:    0.7.0.1
 */