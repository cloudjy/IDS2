 package com.gnamp.struts.action;
 
 import com.gnamp.server.Storage;
 import com.gnamp.server.handle.FileTagHandle;
 import com.gnamp.server.handle.TagHandleFactory;
 import com.gnamp.server.model.Source;
 import java.io.FileInputStream;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.io.PrintWriter;
 import javax.servlet.ServletContext;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.struts2.ServletActionContext;
 
 public class FileTagUploadAction
   extends BaseAction
 {
   Source source;
   private static final long serialVersionUID = 4985656516000660659L;
   
   public Source getSource()
   {
     return this.source;
   }
   
   public void setSource(Source source)
   {
     this.source = source;
   }
   
   java.io.File upload0 = null;
   String upload0ContentType = null;
   String upload0FileName = null;
   int jufinal = -1;
   int jupart = -1;
   
   public java.io.File getUpload0()
   {
     return this.upload0;
   }
   
   public void setUpload0(java.io.File value)
   {
     this.upload0 = value;
   }
   
   public String getUpload0ContentType()
   {
     return this.upload0ContentType;
   }
   
   public void setUpload0ContentType(String value)
   {
     this.upload0ContentType = value;
   }
   
   public String getUpload0FileName()
   {
     return this.upload0FileName;
   }
   
   public void setUpload0FileName(String value)
   {
     this.upload0FileName = value;
   }
   
   public int getJufinal()
   {
     return this.jufinal;
   }
   
   public void setJufinal(int value)
   {
     this.jufinal = value;
   }
   
   public int getJupart()
   {
     return this.jupart;
   }
   
   public void setJupart(int value)
   {
     this.jupart = value;
   }
   
   public String getRootDirectory()
   {
     return this.servletContext.getRealPath("");
   }
   
   public String getTempDirectory()
   {
     return 
     
 
       getRootDirectory() + java.io.File.separatorChar + "temp" + java.io.File.separatorChar + getCstmId() + java.io.File.separatorChar;
   }
   
   public String getPartDirectory()
   {
     return 
     
 
       getTempDirectory() + getUserId() + java.io.File.separatorChar + "part" + java.io.File.separatorChar + getUpload0FileName() + java.io.File.separatorChar;
   }
   
   public String getPartFilePath(int part)
   {
     return getPartDirectory() + String.format("%010d", new Object[] { Integer.valueOf(part) });
   }
   
   public String getWholeFilePath()
   {
     return 
     
       getTempDirectory() + getUserId() + java.io.File.separatorChar + "upload" + java.io.File.separatorChar + getUpload0FileName();
   }
   
   public void prepare()
     throws Exception
   {
     try
     {
       super.prepare();
     }
     catch (Exception ex)
     {
       try
       {
         HttpServletResponse response = ServletActionContext.getResponse();
         response.setContentType("text/html");
         response.setCharacterEncoding("UTF-8");
         PrintWriter writer = response.getWriter();
         
         writer.write("ERROR: not login");
       }
       catch (IOException ioex)
       {
         ioex.printStackTrace(System.out);
       }
       throw ex;
     }
   }
   
   public void upload()
   {
     String outputString = "ERROR: server error";
     try
     {
       if ((this.upload0 == null) || (this.upload0FileName == null)) {
         throw new IllegalStateException("no file upload");
       }
       if (((this.jufinal >= 0) && (this.jupart < 0)) || 
         ((this.jufinal < 0) && (this.jupart >= 0)) || (
         (this.jufinal == 0) && (this.jupart == 0))) {
         throw new IllegalArgumentException("invalid upload parameter");
       }
       int file_type = UploadUtils.getFileType(this.upload0FileName);
       if (!com.gnamp.server.model.File.validType(file_type)) {
         throw new IllegalArgumentException("invalid file extension");
       }
       if ((this.jufinal < 0) && (this.jupart < 0))
       {
         java.io.File wholeFile = new java.io.File(getWholeFilePath());
         wholeFile.getParentFile().mkdirs();
         this.upload0.renameTo(wholeFile);
       }
       if (this.jupart > 0)
       {
         java.io.File partFile = new java.io.File(getPartFilePath(this.jupart));
         partFile.getParentFile().mkdirs();
         this.upload0.renameTo(partFile);
       }
       if (this.jufinal > 0) {
         CombineFile();
       }
       if ((this.jufinal > 0) || ((this.jufinal < 0) && (this.jupart < 0))) {
         ((FileTagHandle)TagHandleFactory.getSourceTagHandle(this, getSource().getType())).add(getSource(), 
           new java.io.File(getWholeFilePath()), 
           this.upload0FileName);
       }
       outputString = "SUCCESS";
     }
     catch (Exception ex)
     {
       if ((this.upload0 != null) && (this.upload0.exists())) {
         this.upload0.delete();
       }
       if (this.upload0FileName != null) {
         Storage.deleteDirectory(getPartDirectory());
       }
       outputString = "ERROR: Exception e = " + ex.getMessage();
     }
     try
     {
       this.servletResponse.setContentType("text/html");
       this.servletResponse.setCharacterEncoding("UTF-8");
       PrintWriter writer = this.servletResponse.getWriter();
       
       writer.write(outputString);
     }
     catch (IOException ex)
     {
       ex.printStackTrace(System.out);
     }
   }
   
   protected long CombineFile()
     throws IOException
   {
     int numChunk = this.jupart;
     for (int i = 1; i <= numChunk; i++)
     {
       java.io.File chunkFile = new java.io.File(getPartFilePath(i));
       if ((!chunkFile.exists()) || (!chunkFile.isFile())) {
         throw new IOException("[" + this.upload0FileName + "] part[" + i + "] not found");
       }
     }
     java.io.File wholeFile = new java.io.File(getWholeFilePath());
     wholeFile.getParentFile().mkdirs();
     if (!wholeFile.exists()) {
       wholeFile.createNewFile();
     }
     FileOutputStream wholeFileStream = new FileOutputStream(wholeFile);
     try
     {
       long precessed = 0L;
       long readlen = 0L;
       
       int BUF_SIZE = 1048576;
       byte[] buf = new byte[1048576];
       for (int i = 1; i <= numChunk; i++)
       {
         java.io.File chuckFile = new java.io.File(getPartFilePath(i));
         long length = chuckFile.length();
         FileInputStream chunkStream = new FileInputStream(chuckFile);
         try
         {
           precessed = 0L;
           readlen = 0L;
           while (precessed < length)
           {
             readlen = length - precessed;
             if (readlen > 1048576L) {
               readlen = 1048576L;
             }
             chunkStream.read(buf, 0, (int)readlen);
             wholeFileStream.write(buf, 0, (int)readlen);
             precessed += readlen;
           }
         }
         finally
         {
           chunkStream.close();
         }
       }
     }
     finally
     {
       wholeFileStream.close();
     }
     Storage.deleteDirectory(getPartDirectory());
     
     return wholeFile.length();
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.FileTagUploadAction
 * JD-Core Version:    0.7.0.1
 */