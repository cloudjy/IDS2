 package com.gnamp.struts.action;
 
 import com.gnamp.server.handle.PlayLogHandle;
 import com.gnamp.server.handle.TerminalHandle;
 import com.gnamp.server.model.PlayLog;
 import com.gnamp.server.model.Terminal;
 import com.gnamp.struts.utils.MapTool;
 import com.gnamp.struts.vo.ExcelPlayLogVo;
 import com.gnamp.struts.vo.ExcelTitleVo;
 import com.gnamp.struts.vo.PageBean;
 import common.Logger;
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 
 public class PlayLogAction
   extends ExcelAction
 {
   public String selectIDs = "";
   public String fileIDs = "";
   
   public String getFileIDs()
   {
     return this.fileIDs;
   }
   
   public void setFileIDs(String fileIDs)
   {
     this.fileIDs = fileIDs;
   }
   
   public String getSelectIDs()
   {
     return this.selectIDs;
   }
   
   public void setSelectIDs(String selectIDs)
   {
     this.selectIDs = selectIDs;
   }
   
   List<PlayLog> playlogs = null;
   
   public List<PlayLog> getPlaylogs()
   {
     return this.playlogs;
   }
   
   public void setPlaylogs(List<PlayLog> playlogs)
   {
     this.playlogs = playlogs;
   }
   
   TerminalHandle terminalhandle = null;
   
   public TerminalHandle getTerminalhandle()
   {
     return this.terminalhandle == null ? (this.terminalhandle = new TerminalHandle(
       this)) : this.terminalhandle;
   }
   
   PlayLogHandle playloghandle = null;
   Terminal terminal;
   private String sortFiled;
   private String sort;
   
   public PlayLogHandle getPlayLogHandle()
   {
     return this.playloghandle == null ? (this.playloghandle = new PlayLogHandle(
       this)) : this.playloghandle;
   }
   
   public Terminal getTerminal()
   {
     return this.terminal;
   }
   
   public void setTerminal(Terminal terminal)
   {
     this.terminal = terminal;
   }
   
   public String getSort()
   {
     return this.sort;
   }
   
   public void setSort(String sort)
   {
     this.sort = sort;
   }
   
   public String getSortFiled()
   {
     return this.sortFiled;
   }
   
   public void setSortFiled(String sortFiled)
   {
     this.sortFiled = sortFiled;
   }
   
   private PageBean page = new PageBean();
   private String oName;
   private String oManaer;
   
   public PageBean getPage()
   {
     return this.page;
   }
   
   public void setPage(PageBean page)
   {
     this.page = page;
   }
   
   public String getoName()
   {
     return this.oName;
   }
   
   public void setOName(String oName)
   {
     this.oName = oName;
   }
   
   public String getoManaer()
   {
     return orderManner.DESC.value().equals(this.oManaer) ? orderManner.DESC
       .value() : orderManner.ASC.value();
   }
   
   public void setOManaer(String oManaer)
   {
     this.oManaer = oManaer;
   }
   
   public static  enum orderManner
   {
     ASC {
		@Override
		String value() {
			// TODO Auto-generated method stub
			return "asc";
		}
	},  DESC {
		@Override
		String value() {
			// TODO Auto-generated method stub
			return "desc";
		}
	};
     
     abstract String value();
   }
   
   public String begindate = "";
   public String enddate = "";
   public boolean checkhour = false;
   public int playstyle = -1;
   
   public int getPlaystyle()
   {
     return this.playstyle;
   }
   
   public void setPlaystyle(int playstyle)
   {
     this.playstyle = playstyle;
   }
   
   private List<PlayLog> getQueryList()
   {
     if ((this.enddate != null) && ((this.enddate = this.enddate.trim()).length() > 0) && 
       (this.enddate.matches("\\d\\d\\d\\d\\-\\d\\d\\-\\d\\d"))) {
       this.enddate += " 23:59:59";
     }
     List<PlayLog> list = new ArrayList();
     try
     {
       String sqltotal = "";
       String sql = "";
       if (this.checkhour)
       {
         sql = " SELECT a.FILE_ID AS FILE_ID, f.NAME AS FILE_NAME, f.SIZE AS SIZE, b.NAME AS NAME, a.DEV_ID AS DEV_ID, PLAY_MODE, a.PLAY_COUNT,  a.PLAY_TIMES, a.DATE_TIME  FROM (SELECT FILE_ID, DEV_ID,  PLAY_MODE, SUM(PLAY_COUNT) AS PLAY_COUNT,   SUM(PLAY_TIMES) AS PLAY_TIMES, DATE_FORMAT(DATE_TIME,'%Y-%m-%d %H:00:00') AS DATE_TIME FROM tb_playlog WHERE 1=1 ";
         if ((this.begindate != null) && (this.enddate != null) && (!this.begindate.equals("")) && (!this.enddate.equals(""))) {
           sql = sql + " AND DATE_TIME>='" + this.begindate + "' AND " + " DATE_TIME <='" + this.enddate + "' ";
         }
         sql = 
           sql + (this.selectIDs.length() > 1 ? " AND DEV_ID IN  (" + this.selectIDs + " )" : " ");
         
         sql = sql + (this.fileIDs.length() > 1 ? " AND FILE_ID IN  (" + 
           this.fileIDs + " )" : " ");
         
         sql = sql + (this.playstyle > -1 ? " AND PLAY_MODE = " + this.playstyle : " ");
         
         sql = sql + "  GROUP BY PLAY_MODE, FILE_ID, DEV_ID, DATE_FORMAT(DATE_TIME,'%Y-%m-%d %H:00:00') ";
         sql = sql + "  ) AS a ";
         sql = sql + " LEFT JOIN tb_terminal AS b on a.DEV_ID=b.DEV_ID LEFT JOIN tb_file AS f  ON a.FILE_ID = f.FILE_ID    WHERE f.NAME IS NOT NULL ORDER BY DATE_TIME DESC ";
         
 
 
 
         sqltotal = sql;
         
         sql = sql + (this.page.getPageSize() != -1 ? "LIMIT " + 
           (this.page.getCurrentPage() - 1) * this.page.getPageSize() + 
           "," + this.page.getPageSize() : "");
       }
       else
       {
         sql = " SELECT a.FILE_ID AS FILE_ID, f.NAME AS FILE_NAME, f.SIZE AS SIZE, b.NAME AS NAME, a.DEV_ID AS DEV_ID, PLAY_MODE, a.PLAY_COUNT,  a.PLAY_TIMES, a.DATE_TIME  FROM (SELECT FILE_ID, DEV_ID,  PLAY_MODE, SUM(PLAY_COUNT) AS PLAY_COUNT,   SUM(PLAY_TIMES) AS PLAY_TIMES, DATE(DATE_TIME) AS DATE_TIME FROM tb_playlog WHERE 1=1 ";
         if ((this.begindate != null) && (this.enddate != null) && (!this.begindate.equals("")) && (!this.enddate.equals(""))) {
           sql = sql + " AND DATE_TIME>='" + this.begindate + "' AND " + " DATE_TIME <='" + this.enddate + "' ";
         }
         sql = 
           sql + (this.selectIDs.length() > 1 ? " AND DEV_ID IN  (" + this.selectIDs + " )" : " ");
         
         sql = sql + (this.fileIDs.length() > 1 ? " AND FILE_ID IN  (" + 
           this.fileIDs + " )" : " ");
         
         sql = sql + (this.playstyle > -1 ? " AND PLAY_MODE = " + this.playstyle : " ");
         
         sql = sql + "  GROUP BY PLAY_MODE, FILE_ID, DEV_ID, DATE(DATE_TIME) ";
         sql = sql + "  ) AS a ";
         sql = sql + " LEFT JOIN tb_terminal AS b on a.DEV_ID=b.DEV_ID LEFT JOIN tb_file AS f  ON a.FILE_ID = f.FILE_ID    WHERE f.NAME IS NOT NULL ORDER BY DATE_TIME DESC ";
         
 
 
         sqltotal = sql;
         
         sql = sql + (this.page.getPageSize() != -1 ? "LIMIT " + 
           (this.page.getCurrentPage() - 1) * this.page.getPageSize() + 
           "," + this.page.getPageSize() : "");
       }
       Map<String, Object> param = new HashMap();
       setQueryParam(param);
       list = getPlayLogHandle().readAll(sql, null);
       
 
       int total = getPlayLogHandle().readAll(sqltotal, null).size();
       
       this.page.setTotalRows(total);
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
     return list;
   }
   
   public String getBegindate()
   {
     return this.begindate;
   }
   
   public void setBegindate(String begindate)
   {
     this.begindate = begindate;
   }
   
   public String getEnddate()
   {
     return this.enddate;
   }
   
   public void setEnddate(String enddate)
   {
     this.enddate = enddate;
   }
   
   public boolean isCheckhour()
   {
     return this.checkhour;
   }
   
   public void setCheckhour(boolean checkhour)
   {
     this.checkhour = checkhour;
   }
   
   public String upload()
   {
     return "upload";
   }
   
   public void query()
   {
     response(JSONSuccessString(JSONArrayToString(getQueryList()), 
       new MapTool().putObject("page", this.page)));
   }
   
   public void exportPlayLog()
   {
     List<ExcelTitleVo> titlevos = new ArrayList();
     titlevos.add(new ExcelTitleVo(getText("period"), 20));
     titlevos.add(new ExcelTitleVo(getText("listname"), 45));
     titlevos.add(new ExcelTitleVo(getText("terminal"), 20));
     titlevos.add(new ExcelTitleVo(getText("size"), 20));
     titlevos.add(new ExcelTitleVo(getText("number"), 20));
     titlevos.add(new ExcelTitleVo(getText("playmode"), 20));
     titlevos.add(new ExcelTitleVo(getText("playbackduration"), 20));
     
     List<ExcelPlayLogVo> vos = new ArrayList();
     this.page.setCurrentPage(1);
     this.page.setPageSize(-1);
     List<PlayLog> queryList = getQueryList();
     if (queryList != null) {
       for (PlayLog p : queryList) {
         vos.add(convertPlayLogVo(p, this.checkhour));
       }
     }
     try
     {
       exportExcel(getText("playstatistic"), vos, titlevos);
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
   
   public ExcelPlayLogVo convertPlayLogVo(PlayLog p, boolean checkhour)
   {
     ExcelPlayLogVo playlog = new ExcelPlayLogVo();
     if (checkhour) {
       playlog.setDateTime(new SimpleDateFormat("yyyy-MM-dd HH:00").format(p.getDateTime()));
     } else {
       playlog.setDateTime(new SimpleDateFormat("yyyy-MM-dd").format(p.getDateTime()));
     }
     String tmp = "";
     String DeviceIDs = Long.toHexString(p.getDeviceId()).toUpperCase();
     if (DeviceIDs.length() < 12)
     {
       for (int n = 0; n < 12 - DeviceIDs.length(); n++) {
         tmp = tmp + "0";
       }
       DeviceIDs = tmp + DeviceIDs;
     }
     playlog.setDeviceId(DeviceIDs);
     playlog.setFileName(p.getFileName());
     playlog.setSize(String.valueOf(Math.round(p.getSize() / 1024.0D * 100.0D) / 100.0D));
     playlog.setCount(String.valueOf(p.getPlayCount()));
     playlog.setMode(p.getPlayMode() == 0 ? getText("auto") : getText("mutual"));
     playlog.setPlayTimes(String.valueOf(p.getPlayTimes()));
     return playlog;
   }
   
   private void setQueryParam(Map<String, Object> param)
   {
     param.put("CSTM_ID", Integer.valueOf(getCstmId()));
     
     param.put("STAET_INDEX", 
       Integer.valueOf(this.page.getCurrentPage() > 1 ? (this.page.getCurrentPage() - 1) * 10 : 
       0));
     param.put("PAGE_SIZE", Integer.valueOf(this.page.getPageSize()));
   }
   
   public String execute()
   {
     return "list";
   }
   
   public String list()
   {
     return "list";
   }
   
   public String selectterminal()
   {
     return "selectterminal";
   }
   
   public String selectfile()
   {
     return "selectfile";
   }
   
   public String month()
   {
     return "month";
   }
   
   private List<PlayLog> queryMonthList()
   {
     if ((this.enddate != null) && ((this.enddate = this.enddate.trim()).length() > 0) && 
       (this.enddate.matches("\\d\\d\\d\\d\\-\\d\\d\\-\\d\\d"))) {
       this.enddate += " 23:59:59";
     }
     List<PlayLog> list = new ArrayList();
     try
     {
       String sqltotal = "";
       String sql = "";
       
       sql = " SELECT a.FILE_ID AS FILE_ID, f.NAME AS FILE_NAME, f.SIZE AS SIZE, b.NAME AS NAME, a.DEV_ID AS DEV_ID, PLAY_MODE, a.PLAY_COUNT,  a.PLAY_TIMES, a.DATE_TIME, b.DESCP AS DEV_DESCP , c.NAME as CITY_NAME  FROM (SELECT FILE_ID, DEV_ID,  PLAY_MODE, SUM(PLAY_COUNT) AS PLAY_COUNT,   SUM(PLAY_TIMES) AS PLAY_TIMES, DATE_FORMAT(DATE_TIME,'%Y-%m-01 00:00:00') AS DATE_TIME FROM tb_playlog WHERE 1=1 ";
       if ((this.begindate != null) && (this.enddate != null) && 
         (!this.begindate.equals("")) && (!this.enddate.equals(""))) {
         sql = sql + " AND DATE_TIME>='" + this.begindate + "' AND DATE_TIME <='" + this.enddate + "' ";
       }
       sql = sql + (this.selectIDs.length() > 1 ? " AND DEV_ID IN (" + this.selectIDs + " )" : " ");
       
       sql = sql + (this.fileIDs.length() > 1 ? " AND FILE_ID IN (" + this.fileIDs + " )" : " ");
       
       sql = sql + (this.playstyle > -1 ? " AND PLAY_MODE = " + this.playstyle : " ");
       
       sql = sql + "  GROUP BY PLAY_MODE, FILE_ID, DEV_ID, DATE(DATE_TIME) ";
       sql = sql + "  ) AS a ";
       sql = sql + " LEFT JOIN tb_terminal AS b on a.DEV_ID=b.DEV_ID  LEFT JOIN tb_file AS f ON a.FILE_ID = f.FILE_ID  LEFT JOIN tb_city AS c ON b.CITY_ID = c.CITY_ID  WHERE f.NAME IS NOT NULL ORDER BY DATE_TIME DESC ";
       
 
 
 
       sqltotal = sql;
       
       sql = sql + (this.page.getPageSize() != -1 ? "LIMIT " + 
         (this.page.getCurrentPage() - 1) * this.page.getPageSize() + 
         "," + this.page.getPageSize() : "");
       
       Map<String, Object> param = new HashMap();
       setQueryParam(param);
       list = getPlayLogHandle().readMonth(sql, null);
       
 
       int total = getPlayLogHandle().readMonth(sqltotal, null).size();
       
       this.page.setTotalRows(total);
     }
     catch (Exception e)
     {
       log.error("Query month list exception: " + e.getMessage(), e);
     }
     return list;
   }
   
   public void monthStatistic()
   {
     response(JSONSuccessString(JSONArrayToString(queryMonthList()), 
       new MapTool().putObject("page", this.page)));
   }
   
   public void exportMonthStatistic()
   {
     List<ExcelTitleVo> titlevos = new ArrayList();
     
     titlevos.add(new ExcelTitleVo(getText("period"), 30));
     titlevos.add(new ExcelTitleVo(getText("player"), 20));
     titlevos.add(new ExcelTitleVo(getText("device_descrp"), 40));
     titlevos.add(new ExcelTitleVo(getText("setcity"), 20));
     titlevos.add(new ExcelTitleVo(getText("listname"), 45));
     titlevos.add(new ExcelTitleVo(getText("size"), 20));
     titlevos.add(new ExcelTitleVo(getText("number"), 20));
     titlevos.add(new ExcelTitleVo(getText("playbackduration"), 20));
     
     List<ExcelMonthPlayLogVo> vos = new ArrayList();
     this.page.setCurrentPage(1);
     this.page.setPageSize(-1);
     List<PlayLog> queryList = queryMonthList();
     if (queryList != null)
     {
       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
       for (PlayLog p : queryList)
       {
         Date date = p.getDateTime();
         Date date1 = new Date(date.getYear(), date.getMonth(), 1, 0, 0, 0);
         date = date1.getMonth() != 11 ? new Date(date1.getYear(), date1.getMonth() + 1, 1, 0, 0, 0) : 
           new Date(date1.getYear() + 1, 0, 1, 0, 0, 0);
         Date date31 = new Date(date.getTime() - 86400000L);
         
         ExcelMonthPlayLogVo playlog = new ExcelMonthPlayLogVo();
         playlog.setDateTime(format.format(date1) + " ~ " + format.format(date31));
         playlog.setDeviceId(String.format("%012X", new Object[] { Long.valueOf(p.getDeviceId()) }));
         playlog.setCityName(p.getCityName());
         playlog.setDeviceDescription(p.getDeviceDescription());
         playlog.setFileName(p.getFileName());
         playlog.setFileSize(String.valueOf(Math.round(p.getSize() / 1024.0D * 100.0D) / 100.0D));
         playlog.setCount(String.valueOf(p.getPlayCount()));
         playlog.setPlayTimes(String.valueOf(p.getPlayTimes()));
         
         vos.add(playlog);
       }
     }
     try
     {
       exportExcel(getText("playbackmonth"), vos, titlevos);
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.PlayLogAction
 * JD-Core Version:    0.7.0.1
 */