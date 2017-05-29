 package com.gnamp.struts.action;
 
 import com.gnamp.server.handle.TerminalHandle;
 import com.gnamp.server.model.Terminal;
 import com.gnamp.struts.admin.action.ConfigTemplateUploadAction;
 import com.gnamp.struts.vo.ExcelTerminalListVo;
 import common.Logger;
 import java.io.File;
 import java.io.IOException;
 import java.io.PrintWriter;
 import java.util.ArrayList;
 import java.util.List;
 import javax.servlet.http.HttpServletResponse;
 import jxl.Cell;
 import jxl.Sheet;
 import jxl.Workbook;
 
 public class TerminalImportAction
   extends JSONAction
 {
   private static final Logger log = Logger.getLogger(ConfigTemplateUploadAction.class);
   private String title;
   private File upload;
   private String uploadFileName;
   private String uploadContentType;
   private int groupId;
   
   public int getGroupId()
   {
     return this.groupId;
   }
   
   public void setGroupId(int groupId)
   {
     this.groupId = groupId;
   }
   
   public String getTitle()
   {
     return this.title;
   }
   
   public void setTitle(String title)
   {
     this.title = title;
   }
   
   public File getUpload()
   {
     return this.upload;
   }
   
   public void setUpload(File upload)
   {
     this.upload = upload;
   }
   
   public String getUploadFileName()
   {
     return this.uploadFileName;
   }
   
   public void setUploadFileName(String uploadFileName)
   {
     this.uploadFileName = uploadFileName;
   }
   
   public String getUploadContentType()
   {
     return this.uploadContentType;
   }
   
   public void setUploadContentType(String uploadContentType)
   {
     this.uploadContentType = uploadContentType;
   }
   
   public void uploadTerminal()
   {
     try
     {
       Workbook book = Workbook.getWorkbook(getUpload());
       
       Sheet sheet = book.getSheet(0);
       int rows = sheet.getRows();
       int columns = sheet.getColumns();
       List<ExcelTerminalListVo> list = new ArrayList();
       for (int i = 1; i < rows; i++)
       {
         ExcelTerminalListVo vo = new ExcelTerminalListVo();
         for (int c = 0; c < columns; c++) {
           switch (c)
           {
           case 0: 
             vo.setDeviceId(sheet.getCell(c, i).getContents());
             break;
           case 1: 
             vo.setDeviceName(sheet.getCell(c, i).getContents());
             break;
           case 2: 
             vo.setDescription(sheet.getCell(c, i).getContents());
           }
         }
         list.add(vo);
       }
       importTerminalVO(list);
       book.close();
       this.servletResponse.getWriter().print("{\"success\":true}");
     }
     catch (Exception e)
     {
       try
       {
         this.servletResponse.getWriter().print("{\"success\":false}");
       }
       catch (IOException localIOException) {}
     }
   }
   
   private void importTerminalVO(List<ExcelTerminalListVo> list)
   {
     try
     {
       TerminalHandle handle = new TerminalHandle(this);
       for (int i = 0; i < list.size(); i++)
       {
         ExcelTerminalListVo vo = (ExcelTerminalListVo)list.get(i);
         Terminal terminal = new Terminal();
         terminal.setGroupId(getGroupId());
         try
         {
           terminal.setDeviceId(Long.valueOf(vo.getDeviceId(), 16).longValue());
         }
         catch (Exception e1)
         {
           log.error("错误的ID:" + vo.getDeviceId() + ",处理方式:跳过执行");
           continue;
         }
         terminal.setDeviceName(vo.getDeviceName());
         terminal.setDescription(vo.getDescription());
         try
         {
           if (handle.create(terminal)) {
             LogHelp.userEvent(this.cstmId, this.userName, getText("addterminal"), getText("addterminal") + "[" + 
               vo.getDeviceName() + "]");
           }
         }
         catch (Exception e)
         {
           Terminal tTemp = handle.read(terminal.getDeviceId());
           if (tTemp != null)
           {
             tTemp.setDeviceName(vo.getDeviceName());
             tTemp.setDescription(vo.getDescription());
             try
             {
               if (!handle.modify(tTemp)) {
                 continue;
               }
               LogHelp.userEvent(this.cstmId, this.userName, getText("editterminal"), getText("editterminal") + "[" + 
                 terminal.getDeviceName() + "]");
             }
             catch (Exception ex)
             {
               log.error(ex.getMessage());
             }
           }
           else
           {
             log.error(e.getMessage());
           }
         }
       }
     }
     catch (NumberFormatException ef)
     {
       ef.printStackTrace();
     }
   }
   
   public String importTer()
   {
     return "importter";
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.TerminalImportAction
 * JD-Core Version:    0.7.0.1
 */