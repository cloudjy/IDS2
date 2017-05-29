 package com.gnamp.struts.action;
 
 import com.gnamp.server.Storage;
 import com.gnamp.server.db.ProgramTable;
 import com.gnamp.server.db.TaskTable;
 import com.gnamp.server.handle.ProgramTTSHandle;
 import com.gnamp.server.handle.TTSUtil;
 import com.gnamp.server.model.TTSText;
 import com.gnamp.struts.utils.JsonUtils;
 import com.gnamp.struts.utils.MapTool;
 import com.gnamp.struts.vo.PageBean;
 import java.io.Closeable;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 
 public abstract class ProgramTTSAction
   extends JSONAction
 {
   private static final long serialVersionUID = 8916933806680168715L;
   private int mTaskId = 0;
   
   public abstract String getTaskType();
   
   public int getTaskId()
   {
     return this.mTaskId;
   }
   
   public void setTaskId(int taskId)
   {
     this.mTaskId = taskId;
   }
   
   private int mProgramId = 0;
   
   public int getProgramId()
   {
     return this.mProgramId;
   }
   
   public void setProgramId(int programId)
   {
     this.mProgramId = programId;
   }
   
   private String mSelectIds = "";
   
   public String getSelectIds()
   {
     return this.mSelectIds;
   }
   
   public void setSelectIds(String selectIds)
   {
     this.mSelectIds = selectIds;
   }
   
   private PageBean mPage = new PageBean();
   
   public PageBean getPage()
   {
     return this.mPage;
   }
   
   public void setPage(PageBean page)
   {
     this.mPage = page;
   }
   
   protected TTSText mTTSText = new TTSText();
   
   public TTSText getTtsText()
   {
     return this.mTTSText;
   }
   
   public void setTtsText(TTSText ttsText)
   {
     this.mTTSText = ttsText;
   }
   
   private List<Integer> selectIntIds()
   {
     List<Integer> idList = new ArrayList();
     String[] array = this.mSelectIds != null ? this.mSelectIds.split(",") : null;
     if (array != null) {
       for (int i = 0; i < array.length; i++)
       {
         int id = 0;
         try
         {
           id = Integer.parseInt(array[i]);
         }
         catch (Exception localException) {}
         if (id != 0) {
           idList.add(Integer.valueOf(id));
         }
       }
     }
     return idList;
   }
   
   public String ttsTextSelect()
   {
     return "ttsTextSelect";
   }
   
   protected abstract ProgramTTSHandle<? extends TaskTable<?>, ? extends ProgramTable<?>> _ProgramTTSHandle();
   
   protected abstract String _ProgramTTSTableName();
   
   public void queryTTSTextList()
   {
     List<TTSText> ttsTextList = new ArrayList();
     
     String pageSql = "";
     int pageSize = this.mPage.getPageSize();
     if (-1 != pageSize) {
       pageSql = "LIMIT " + (this.mPage.getCurrentPage() - 1) * pageSize + "," + pageSize;
     }
     String query = "SELECT TASK_ID, PRGM_ID, TEXT_ID, SEQ, FILE_NAME, TEXT, DETAIL FROM " + 
       _ProgramTTSTableName() + " " + 
       "WHERE CSTM_ID=:CSTM_ID " + 
       "AND TASK_ID=:TASK_ID AND PRGM_ID=:PRGM_ID " + 
       "ORDER BY SEQ ASC " + pageSql;
     Map<String, Object> parameters = new HashMap();
     parameters.put("CSTM_ID", Integer.valueOf(this.cstmId));
     parameters.put("TASK_ID", Integer.valueOf(this.mTaskId));
     parameters.put("PRGM_ID", Integer.valueOf(this.mProgramId));
     try
     {
       int total = _ProgramTTSHandle().readAll(this.mTaskId, this.mProgramId).size();
       ttsTextList = _ProgramTTSHandle().readAll(query, parameters);
       this.mPage.setTotalRows(total);
       
       response(JSONSuccessString(JSONArrayToString(ttsTextList), 
         new MapTool().putObject("page", this.mPage)));
     }
     catch (Exception e)
     {
       e.printStackTrace(System.err);
       JsonUtils.writeError(this.servletResponse);
     }
   }
   
   public String ttsTextToAdd()
   {
     this.mTaskId = this.mTTSText.getTaskId();
     this.mProgramId = this.mTTSText.getProgramId();
     return "ttsTextAdd";
   }
   
   public void ttsTextAdd()
   {
     this.mTTSText.setSequenceNum(2147483647);
     try
     {
       if (_ProgramTTSHandle().create(this.mTTSText)) {
         JsonUtils.writeSuccess(this.servletResponse);
       } else {
         JsonUtils.writeError(this.servletResponse);
       }
     }
     catch (Exception e)
     {
       JsonUtils.writeError(this.servletResponse);
       e.printStackTrace(System.err);
     }
   }
   
   public String ttsTextToEdit()
   {
     this.mTaskId = this.mTTSText.getTaskId();
     this.mProgramId = this.mTTSText.getProgramId();
     
     this.mTTSText = _ProgramTTSHandle().read(this.mTTSText.getTaskId(), this.mTTSText.getProgramId(), 
       this.mTTSText.getTextId());
     
     return "ttsTextEdit";
   }
   
   public void ttsTextEdit()
   {
     try
     {
       TTSText old = _ProgramTTSHandle().read(this.mTTSText.getTaskId(), 
         this.mTTSText.getProgramId(), this.mTTSText.getTextId());
       if (old != null)
       {
         old.setText(this.mTTSText.getText());
         old.setVoiceName(this.mTTSText.getVoiceName());
         old.setSpeed(this.mTTSText.getSpeed());
         old.setPlayCount(this.mTTSText.getPlayCount());
         if (_ProgramTTSHandle().modify(old)) {
           JsonUtils.writeSuccess(this.servletResponse);
         } else {
           JsonUtils.writeError(this.servletResponse);
         }
       }
     }
     catch (Exception e)
     {
       JsonUtils.writeError(this.servletResponse);
       e.printStackTrace(System.err);
     }
   }
   
   public void ttsTextDelete()
   {
     List<Integer> idList = selectIntIds();
     for (Iterator localIterator = idList.iterator(); localIterator.hasNext();)
     {
       int textId = ((Integer)localIterator.next()).intValue();
       try
       {
         _ProgramTTSHandle().remove(this.mTaskId, this.mProgramId, textId);
       }
       catch (Exception e)
       {
         e.printStackTrace();
       }
     }
     JsonUtils.writeSuccess(this.servletResponse);
   }
   
   public void ttsTextUp()
   {
     try
     {
       TTSText old = _ProgramTTSHandle().read(this.mTTSText.getTaskId(), this.mTTSText.getProgramId(), 
         this.mTTSText.getTextId());
       int seq = old.getSequenceNum() - 1;
       old.setSequenceNum(seq);
       if (_ProgramTTSHandle().modify(old)) {
         JsonUtils.writeSuccess(this.servletResponse);
       } else {
         JsonUtils.writeError(this.servletResponse);
       }
     }
     catch (Exception e)
     {
       JsonUtils.writeError(this.servletResponse);
       e.printStackTrace(System.err);
     }
   }
   
   public void ttsTextdown()
   {
     try
     {
       TTSText old = _ProgramTTSHandle().read(this.mTTSText.getTaskId(), this.mTTSText.getProgramId(), 
         this.mTTSText.getTextId());
       int seq = old.getSequenceNum() + 1;
       old.setSequenceNum(seq);
       if (_ProgramTTSHandle().modify(old)) {
         JsonUtils.writeSuccess(this.servletResponse);
       } else {
         JsonUtils.writeError(this.servletResponse);
       }
     }
     catch (Exception e)
     {
       JsonUtils.writeError(this.servletResponse);
       e.printStackTrace(System.err);
     }
   }
   
   public void ttsTextToFirst()
   {
     try
     {
       TTSText old = _ProgramTTSHandle().read(this.mTTSText.getTaskId(), this.mTTSText.getProgramId(), 
         this.mTTSText.getTextId());
       old.setSequenceNum(0);
       if (_ProgramTTSHandle().modify(old)) {
         JsonUtils.writeSuccess(this.servletResponse);
       } else {
         JsonUtils.writeError(this.servletResponse);
       }
     }
     catch (Exception e)
     {
       JsonUtils.writeError(this.servletResponse);
       e.printStackTrace(System.err);
     }
   }
   
   public void ttsTextToLast()
   {
     try
     {
       TTSText old = _ProgramTTSHandle().read(this.mTTSText.getTaskId(), this.mTTSText.getProgramId(), 
         this.mTTSText.getTextId());
       old.setSequenceNum(2147483647);
       if (_ProgramTTSHandle().modify(old)) {
         JsonUtils.writeSuccess(this.servletResponse);
       } else {
         JsonUtils.writeError(this.servletResponse);
       }
     }
     catch (Exception e)
     {
       JsonUtils.writeError(this.servletResponse);
       e.printStackTrace(System.err);
     }
   }
   
   public String ttsTextAudtion()
   {
     try
     {
       this.mTTSText = _ProgramTTSHandle().read(this.mTTSText.getTaskId(), this.mTTSText.getProgramId(), 
         this.mTTSText.getTextId());
       File file = TTSUtil.mp3File(new Storage(getCstmId()), this.mTTSText);
       file.exists();
       
 
       this.servletRequest.setAttribute("ttsAudio", file.getAbsolutePath());
     }
     catch (Exception e)
     {
       e.printStackTrace(System.err);
     }
     return "ttsTextAudtion";
   }
   
   private static void closeCloseable(Closeable closeable)
   {
     if (closeable != null) {
       try
       {
         closeable.close();
       }
       catch (IOException localIOException) {}
     }
   }
   
   public void ttsTextAudioFile()
   {
     int BUF_SIZE = 1048576;
     byte[] buf = null;
     
     InputStream inputStream = null;
     OutputStream outStream = null;
     try
     {
       TTSText ttsText = _ProgramTTSHandle().read(this.mTTSText.getTaskId(), this.mTTSText.getProgramId(), 
         this.mTTSText.getTextId());
       
       File file = TTSUtil.mp3File(new Storage(getCstmId()), ttsText);
       if (!file.exists()) {
         throw new HttpException(404, "Cannot find file: " + file.getAbsolutePath());
       }
       long fileSize = file.length();
       
       this.servletResponse.setHeader("Pragma", "No-cache");
       this.servletResponse.setHeader("Cache-Control", "no-cache");
       this.servletResponse.setHeader("Cache-Control", "no-store");
       this.servletResponse.setDateHeader("Expires", 0L);
       
       this.servletResponse.setContentLength((int)fileSize);
       this.servletResponse.setContentType("audio/mpeg");
       
       inputStream = new FileInputStream(file);
       outStream = this.servletResponse.getOutputStream();
       
       long sendLen = 0L;
       while (sendLen < fileSize)
       {
         int blockSize = fileSize - sendLen < 1048576L ? (int)(fileSize - sendLen) : 1048576;
         if (buf == null) {
           buf = new byte[blockSize];
         }
         int readLen = 0;
         while (readLen < blockSize)
         {
           int len = inputStream.read(buf, readLen, blockSize - readLen);
           if (len <= 0) {
             throw new HttpException(500, "read file failed(" + len + "): " + file.getAbsolutePath());
           }
           readLen += len;
         }
         outStream.write(buf, 0, blockSize);
         outStream.flush();
         
         sendLen += blockSize;
       }
     }
     catch (Exception e)
     {
       e.printStackTrace(System.err);
     }
     finally
     {
       closeCloseable(outStream);
       closeCloseable(inputStream);
       if (buf != null)
       {
         buf = null;
         System.gc();
       }
     }
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.ProgramTTSAction
 * JD-Core Version:    0.7.0.1
 */