 package com.gnamp.struts.action;
 
 import com.gnamp.struts.vo.ExcelTitleVo;
 import com.gnamp.struts.vo.IExcelContentVo;
 import java.lang.reflect.Method;
 import java.text.SimpleDateFormat;
 import java.util.Date;
 import java.util.List;
 import javax.servlet.http.HttpServletResponse;
 import jxl.Workbook;
 import jxl.format.Alignment;
 import jxl.format.Colour;
 import jxl.format.UnderlineStyle;
 import jxl.format.VerticalAlignment;
 import jxl.write.Border;
 import jxl.write.BorderLineStyle;
 import jxl.write.Label;
 import jxl.write.WritableCellFormat;
 import jxl.write.WritableFont;
 import jxl.write.WritableSheet;
 import jxl.write.WritableWorkbook;
 
 public class ExcelAction
   extends JSONAction
 {
   private static final long serialVersionUID = 6111435500647989954L;
   
   protected void exportExcel(List<?> contentList, List<ExcelTitleVo> titleList)
     throws Exception
   {
     exportExcel(null, contentList, titleList);
   }
   
   protected void exportExcel(String sheetName, List<?> contentList, List<ExcelTitleVo> titleList)
     throws Exception
   {
     this.servletResponse.setHeader("Content-disposition", "attachment; filename=" + new SimpleDateFormat("yyyyMMdd+HHmmss").format(new Date()) + "-report.xls");
     this.servletResponse.setContentType("application/msexcel");
     
     WritableWorkbook wbook = Workbook.createWorkbook(this.servletResponse.getOutputStream());
     WritableSheet wsheet = wbook.createSheet(sheetName, 0);
     int offset = titleList.size() > 0 ? 1 : titleList == null ? 0 : 0;
     if (titleList != null)
     {
       WritableFont wfc8 = new WritableFont(WritableFont.ARIAL, 12, 
         WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, 
         Colour.BLACK);
       WritableCellFormat wcfFC8 = new WritableCellFormat(wfc8);
       wcfFC8.setAlignment(Alignment.CENTRE);
       wcfFC8.setVerticalAlignment(VerticalAlignment.CENTRE);
       wcfFC8.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.GRAY_25);
       wcfFC8.setWrap(true);
       for (int i = 0; i < titleList.size(); i++)
       {
         wsheet.setColumnView(i, ((ExcelTitleVo)titleList.get(i)).getWidth());
         Label labelTemp = new Label(i, 0, ((ExcelTitleVo)titleList.get(i)).getTitle(), wcfFC8);
         wsheet.addCell(labelTemp);
       }
     }
     if (contentList != null)
     {
       WritableFont wfc1 = new WritableFont(WritableFont.ARIAL, 10, 
         WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, 
         Colour.BLACK);
       WritableCellFormat wcfFC1 = new WritableCellFormat(wfc1);
       wcfFC1.setAlignment(Alignment.CENTRE);
       wcfFC1.setBorder(Border.ALL, BorderLineStyle.THIN, 
         Colour.GRAY_25);
       for (int i = offset; i < contentList.size() + offset; i++)
       {
         Object obj = contentList.get(i - offset);
         for (Method m : obj.getClass().getDeclaredMethods()) {
           if (m.getName().startsWith("get"))
           {
             Object result = m.invoke(obj, new Object[0]);
             IExcelContentVo vo = (IExcelContentVo)m.getAnnotation(IExcelContentVo.class);
             if (vo == null) {
               throw new Exception(m.getName() + "没有注解  IExcelContentVo");
             }
             wsheet.addCell(new Label(vo.columnIndex(), i, result == null ? "" : result.toString(), wcfFC1));
           }
         }
       }
     }
     wbook.write();
     wbook.close();
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.ExcelAction
 * JD-Core Version:    0.7.0.1
 */