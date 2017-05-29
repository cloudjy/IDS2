 package com.gnamp.struts.action;
 
 import com.gnamp.server.Storage;
 import com.gnamp.server.handle.LoginHandle;
 import com.gnamp.server.model.Customer;
 import com.gnamp.server.model.User;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.IOException;
 import java.io.OutputStream;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 
 public class DownloadAction
   extends BaseAction
 {
   private User user = null;
   private long fileId = 0L;
   private int taskId;
   
   public long getFileId()
   {
     return this.fileId;
   }
   
   public void setFileId(long fileId)
   {
     this.fileId = fileId;
   }
   
   public int getTaskId()
   {
     return this.taskId;
   }
   
   public void setTaskId(int taskId)
   {
     this.taskId = taskId;
   }
   
   private String listType = "";
   private String shortName;
   
   public String getListType()
   {
     return this.listType;
   }
   
   public void setListType(String listType)
   {
     this.listType = listType;
   }
   
   public User getUser()
   {
     return this.user;
   }
   
   public void setUser(User user)
   {
     this.user = user;
   }
   
   public String getShortName()
   {
     return this.shortName;
   }
   
   public void setShortName(String shortName)
   {
     this.shortName = shortName;
   }
   
   public void GetTaskFile()
   {
     try
     {
       Customer cust = LoginHandle.getCustomer(this.shortName);
       if (cust == null) {
         throw new HttpException("invalid customer[" + this.shortName + "] !");
       }
       User custuser = null;
       try
       {
         custuser = LoginHandle.Authenticate(cust.getCstmId(), "admin", "123456");
       }
       catch (Exception localException1) {}
       Storage storage = new Storage(cust.getCstmId());
       
       String filepath = storage.getFilePath(this.fileId);
       File f = new File(filepath);
       if (!f.exists()) {
         throw new HttpException("Server error, customer[" + 
           cust.getCstmId() + "]  download file[" + this.fileId + 
           "]  but not find[" + filepath + "] !");
       }
       processFileDowload(filepath);
     }
     catch (Exception ex)
     {
       try
       {
         int statusCode = (ex instanceof HttpException) ? 
           ((HttpException)ex).getStatusCode() : 
           400;
         
         this.servletResponse.reset();
         this.servletResponse.setStatus(statusCode);
         this.servletResponse.sendError(statusCode, ex.getMessage());
       }
       catch (Exception localException2) {}
       return;
     }
   }
   
   public void GetPathList()
   {
     try
     {
       Customer cust = LoginHandle.getCustomer(this.shortName);
       if (cust == null) {
         throw new HttpException("invalid customer[" + this.shortName + "] !");
       }
       Storage storage = new Storage(cust.getCstmId());
       
 
 
 
 
       User custuser = null;
       try
       {
         custuser = LoginHandle.Authenticate(cust.getCstmId(), "admin", "123456");
       }
       catch (Exception localException1) {}
       String filepath = "";
       if (this.listType == "FILELIST_PATH") {
         filepath = storage.getFileListPath(this.taskId);
       }
       if (this.listType == "PRGMLIST_PATH") {
         filepath = storage.getProgramListPath(this.taskId);
       }
       if (this.listType == "TASKLIST_PATH") {
         filepath = storage.getTaskListPath(this.taskId);
       }
       if (this.listType == "TACTICLIST_PATH") {
         filepath = storage.getStrategyListPath(this.taskId);
       }
       File f = new File(filepath);
       if (!f.exists()) {
         throw new HttpException("Server error, customer[" + 
           cust.getCstmId() + "]  download file[" + this.fileId + 
           "]  but not find[" + filepath + "] !");
       }
       processFileDowload(filepath);
     }
     catch (Exception ex)
     {
       try
       {
         int statusCode = (ex instanceof HttpException) ? 
           ((HttpException)ex).getStatusCode() : 
           400;
         
         this.servletResponse.reset();
         this.servletResponse.setStatus(statusCode);
         this.servletResponse.sendError(statusCode, ex.getMessage());
       }
       catch (Exception localException2) {}
       return;
     }
   }
   
   protected void processFileDowload(String filepath)
     throws IOException, HttpException
   {
     long offset = 0L;
     long size = 0L;
     
     File file = new File(filepath);
     if (!file.exists()) {
       throw new HttpException(500, 
         "Cannot find file: " + filepath);
     }
     size = file.length();
     
     String range = this.servletRequest.getHeader("Range");
     if (range != null)
     {
       String strlen = range.replace("bytes=", "");
       int t = strlen.indexOf('-');
       if (t > 0) {
         strlen = strlen.substring(0, t);
       }
       strlen = strlen.trim();
       if (strlen != null) {
         offset = Long.parseLong(strlen);
       }
     }
     if (offset > 0L)
     {
       if (offset > size) {
         throw new HttpException(416, 
           "Invalid offset=" + offset + " rang=" + range);
       }
       size -= offset;
       this.servletResponse.setStatus(206);
       this.servletResponse.addHeader("Content-Length", Long.toString(size));
       this.servletResponse.addHeader("Content-Range", 
         "bytes " + Long.toString(offset) + "-" + 
         Long.toString(size - 1L) + "/" + Long.toString(offset));
     }
     else
     {
       this.servletResponse.addHeader("Content-Length", Long.toString(size));
     }
     this.servletResponse.setContentType("application/octet-stream");
     
 
     FileInputStream fileStream = null;
     OutputStream outStream = null;
     
     int BUF_SIZE = 1048576;
     byte[] buf = null;
     try
     {
       fileStream = new FileInputStream(file);
       outStream = this.servletResponse.getOutputStream();
       
       fileStream.skip(offset);
       
       long send_len = 0L;
       while (send_len < size)
       {
         int data_size = (int)(size - send_len < 1048576L ? 
           size - send_len : 1048576L);
         if (buf == null) {
           buf = new byte[data_size];
         }
         int read_len = 0;
         while (read_len < data_size)
         {
           int len = fileStream.read(buf, read_len, data_size - read_len);
           if (len <= 0) {
             throw new HttpException(500, 
               "read file failed(" + len + "): " + filepath);
           }
           read_len += len;
         }
         outStream.write(buf, 0, data_size);
         outStream.flush();
         
         send_len += data_size;
       }
     }
     finally
     {
       if (outStream != null)
       {
         try
         {
           outStream.close();
         }
         catch (IOException localIOException) {}
         outStream = null;
       }
       if (fileStream != null)
       {
         try
         {
           fileStream.close();
         }
         catch (IOException localIOException1) {}
         fileStream = null;
       }
       if (buf != null)
       {
         buf = null;
         System.gc();
       }
     }
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.DownloadAction
 * JD-Core Version:    0.7.0.1
 */