 package com.gnamp.struts.action;
 
 import com.gnamp.server.Storage;
 import com.gnamp.server.handle.KeyLogHandle;
 import com.gnamp.server.handle.TerminalHandle;
 import com.gnamp.server.model.KeyLog;
 import com.gnamp.server.model.PipeLog;
 import com.gnamp.server.model.Terminal;
 import com.gnamp.struts.utils.MapTool;
 import com.gnamp.struts.vo.ExcelKeyLogVo;
 import com.gnamp.struts.vo.ExcelPipeLogVo;
 import com.gnamp.struts.vo.ExcelTitleVo;
 import com.gnamp.struts.vo.PageBean;
 import common.Logger;
 import java.io.BufferedReader;
 import java.io.File;
 import java.io.FileReader;
 import java.io.IOException;
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 
 public class KeyLogAction
   extends ExcelAction
 {
   public String selectIDs = "";
   
   public String getSelectIDs()
   {
     return this.selectIDs;
   }
   
   public void setSelectIDs(String selectIDs)
   {
     this.selectIDs = selectIDs;
   }
   
   List<KeyLog> keylogs = null;
   
   public List<KeyLog> getKeylogs()
   {
     return this.keylogs;
   }
   
   public void setKeylogs(List<KeyLog> keylogs)
   {
     this.keylogs = keylogs;
   }
   
   TerminalHandle terminalhandle = null;
   
   public TerminalHandle getTerminalhandle()
   {
     return this.terminalhandle == null ? (this.terminalhandle = new TerminalHandle(
       this)) : this.terminalhandle;
   }
   
   KeyLogHandle keyloghandle = null;
   Terminal terminal;
   private String sortFiled;
   private String sort;
   
   public KeyLogHandle getKeyLogHandle()
   {
     return this.keyloghandle == null ? (this.keyloghandle = new KeyLogHandle(this)) : 
       this.keyloghandle;
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
			
			return "asc";
		}
	},  DESC {
		@Override
		String value() {
			return "desc";
		}
	};
     
     abstract String value();
   }
   
   public String begindate = "";
   public String enddate = "";
   public boolean checkhour = false;
   
   private List<KeyLog> getQueryList()
   {
     List<KeyLog> list = new ArrayList();
     try
     {
       String sql = "";
       String sqltotal = "";
       if (this.checkhour)
       {
         sql = " SELECT b.NAME AS NAME, a.DEV_ID AS DEV_ID, a.KEY_COUNT,  a.DATE_TIME  FROM (SELECT DEV_ID,  SUM(KEY_COUNT) AS KEY_COUNT,   DATE_FORMAT(DATE_TIME,'%Y-%m-%d %H:00:00') AS DATE_TIME FROM tb_keylog WHERE 1=1 ";
         if ((this.begindate != null) && (this.enddate != null) && 
           (!this.begindate.equals("")) && (!this.enddate.equals(""))) {
           sql = 
             sql + " AND DATE_TIME>='" + this.begindate + "' AND " + " DATE_TIME <='" + this.enddate + "' ";
         }
         sql = 
           sql + (this.selectIDs.length() > 1 ? " AND DEV_ID IN  (" + this.selectIDs + " )" : " ");
         
         sql = sql + "  GROUP BY DEV_ID, DATE_FORMAT(DATE_TIME,'%Y-%m-%d %H:00:00') ";
         sql = sql + "  ) AS a ";
         sql = sql + " LEFT JOIN tb_terminal AS b on a.DEV_ID=b.DEV_ID  ORDER BY DATE_TIME DESC ";
         
 
         sqltotal = sql;
         
         sql = sql + (this.page.getPageSize() != -1 ? "LIMIT " + 
           (this.page.getCurrentPage() - 1) * this.page.getPageSize() + 
           "," + this.page.getPageSize() : "");
       }
       else
       {
         sql = " SELECT b.NAME AS NAME, a.DEV_ID AS DEV_ID, a.KEY_COUNT,  a.DATE_TIME  FROM (SELECT DEV_ID,  SUM(KEY_COUNT) AS KEY_COUNT,   DATE(DATE_TIME) AS DATE_TIME FROM tb_keylog WHERE 1=1 ";
         if ((this.begindate != null) && (this.enddate != null) && 
           (!this.begindate.equals("")) && (!this.enddate.equals(""))) {
           sql = 
             sql + " AND DATE_TIME>='" + this.begindate + "' AND " + " DATE_TIME <='" + this.enddate + "' ";
         }
         sql = 
           sql + (this.selectIDs.length() > 1 ? " AND DEV_ID IN  (" + this.selectIDs + " )" : " ");
         
         sql = sql + "  GROUP BY DEV_ID, DATE(DATE_TIME) ";
         sql = sql + "  ) AS a ";
         sql = sql + " LEFT JOIN tb_terminal AS b on a.DEV_ID=b.DEV_ID  ORDER BY DATE_TIME DESC ";
         
 
         sqltotal = sql;
         
         sql = sql + (this.page.getPageSize() != -1 ? "LIMIT " + 
           (this.page.getCurrentPage() - 1) * this.page.getPageSize() + 
           "," + this.page.getPageSize() : "");
       }
       Map<String, Object> param = new HashMap();
       setQueryParam(param);
       list = getKeyLogHandle().readAll(sql, null);
       int totalSize = getKeyLogHandle().readAll(sqltotal, null).size();
       this.page.setTotalRows(totalSize);
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
     return list;
   }
   
   public void exportKeyLog()
   {
     List<ExcelTitleVo> titlevos = new ArrayList();
     titlevos.add(new ExcelTitleVo(getText("terminalid"), 20));
     titlevos.add(new ExcelTitleVo(getText("terminalname"), 45));
     titlevos.add(new ExcelTitleVo(getText("period"), 20));
     titlevos.add(new ExcelTitleVo(getText("number"), 20));
     
     List<ExcelKeyLogVo> vos = new ArrayList();
     for (KeyLog p : getQueryList()) {
       vos.add(convertKeyLogVo(p, this.checkhour));
     }
     try
     {
       exportExcel(getText("statisticalinduction"), vos, titlevos);
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
   
   public void exportPipeLog()
   {
     List<ExcelTitleVo> titlevos = new ArrayList();
     titlevos.add(new ExcelTitleVo(getText("terminalid"), 20));
     titlevos.add(new ExcelTitleVo(getText("terminalname"), 45));
     titlevos.add(new ExcelTitleVo(getText("startdate"), 30));
     titlevos.add(new ExcelTitleVo(getText("enddate"), 30));
     titlevos.add(new ExcelTitleVo(getText("result"), 20));
     
     List<ExcelPipeLogVo> vos = new ArrayList();
     for (PipeLog p : getpipelist()) {
       vos.add(convertPipeLogVo(p, false));
     }
     try
     {
       exportExcel(getText("pipelog"), vos, titlevos);
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
   
   public ExcelKeyLogVo convertKeyLogVo(KeyLog p, boolean checkhour)
   {
     ExcelKeyLogVo keylog = new ExcelKeyLogVo();
     String tmp = "";
     String DeviceIDs = Long.toHexString(p.getDeviceId()).toUpperCase();
     if (DeviceIDs.length() < 12)
     {
       for (int n = 0; n < 12 - DeviceIDs.length(); n++) {
         tmp = tmp + "0";
       }
       DeviceIDs = tmp + DeviceIDs;
     }
     keylog.setDeviceId(DeviceIDs);
     if (checkhour) {
       keylog.setDateTime(new SimpleDateFormat("yyyy-MM-dd HH:00")
         .format(p.getDateTime()));
     } else {
       keylog.setDateTime(new SimpleDateFormat("yyyy-MM-dd").format(p
         .getDateTime()));
     }
     keylog.setDeviceName(p.getDeviceName());
     keylog.setKeycount(String.valueOf(p.getKeycount()));
     return keylog;
   }
   
   public ExcelPipeLogVo convertPipeLogVo(PipeLog p, boolean checkhour)
   {
     ExcelPipeLogVo keylog = new ExcelPipeLogVo();
     String tmp = "";
     String DeviceIDs = Long.toHexString(p.getDevId()).toUpperCase();
     if (DeviceIDs.length() < 12)
     {
       for (int n = 0; n < 12 - DeviceIDs.length(); n++) {
         tmp = tmp + "0";
       }
       DeviceIDs = tmp + DeviceIDs;
     }
     keylog.setDeviceId(DeviceIDs);
     keylog.setStartTime(p.getStarttime());
     keylog.setEndTime(p.getEndtime());
     
     keylog.setDeviceName(p.getDevName());
     keylog.setResult(String.valueOf(p.getResult()));
     return keylog;
   }
   
   public void query()
   {
     response(JSONSuccessString(JSONArrayToString(getQueryList()), 
       new MapTool().putObject("page", this.page)));
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
   
   public String pipelogs()
   {
     return "pipelogs";
   }
   
   public String selectterminal()
   {
     return "selectterminal";
   }
   
   PipeLog[] allevents = null;
   
   public PipeLog[] getAllevents()
   {
     return this.allevents;
   }
   
   public void setAllevents(PipeLog[] allevents)
   {
     this.allevents = allevents;
   }
   
   List<PipeLog> eventlist = new ArrayList();
   
   public List<PipeLog> getEventlist()
   {
     return this.eventlist;
   }
   
   public void setEventlist(List<PipeLog> eventlist)
   {
     this.eventlist = eventlist;
   }
   
   private List<PipeLog> getpipelist()
   {
     BufferedReader br = null;
     label435:
     try
     {
       int csid = getCstmId();
       if (csid > 0)
       {
         String path = (Storage.getCstmBase(csid).endsWith("\\") ? 
           Storage.getCstmBase(csid) : new StringBuilder(String.valueOf(Storage.getCstmBase(csid))).append("\\").toString()) + 
           "pipelog.txt";
         
         File dir = new File(path);
         if (dir.exists())
         {
           FileReader read = new FileReader(path);
           br = new BufferedReader(read);
           
           String strLine = br.readLine();
           
           Map<String, Object> param = new HashMap();
           param.put("CSTM_ID", Integer.valueOf(getCstmId()));
           
           List<Terminal> allterminal = getTerminalhandle()
             .readAll(
             "SELECT * FROM vw_terminal_detail WHERE CSTM_ID=:CSTM_ID ", 
             param);
           while ((strLine != null) && (!"".equals(strLine)) && 
             (allterminal != null))
           {
             String[] temps = strLine.split("\\|");
             
             PipeLog mod = new PipeLog();
             for (int i = 0; i < allterminal.size(); i++)
             {
               Terminal t = (Terminal)allterminal.get(i);
               if (t.getDeviceId() == Long.valueOf(temps[0]).longValue())
               {
                 mod.setDevName(t.getDeviceName());
                 break;
               }
             }
             mod.setDevId(Long.valueOf(temps[0]).longValue());
             mod.setStarttime(temps[1]);
             mod.setEndtime(temps[2]);
             mod.setResult(temps[3]);
             
             this.eventlist.add(mod);
             
             strLine = br.readLine();
           }
           this.allevents = new PipeLog[this.eventlist.size()];
           this.eventlist.toArray(this.allevents);
           break label435;
         }
       }
       else
       {
         log.error("##getpipelist##: invalid customer id");
       }
     }
     catch (Exception e)
     {
       log.error("##getpipelist##: " + e.getMessage(), e);
       if (br != null) {
         try
         {
           br.close();
         }
         catch (IOException localIOException) {}
       }
     }
     finally
     {
       if (br != null) {
         try
         {
           br.close();
         }
         catch (IOException localIOException1) {}
       }
     }
     return this.eventlist;
   }
   
   public void pipelist()
   {
     try
     {
       this.eventlist = getpipelist();
       if (this.eventlist != null)
       {
         this.allevents = new PipeLog[this.eventlist.size()];
         this.eventlist.toArray(this.allevents);
         
         this.page.setTotalRows(this.eventlist.size());
         response(JSONSuccessString(JSONArrayToString(this.eventlist), 
           new MapTool().putObject("page", this.page)));
       }
       else
       {
         this.page.setTotalRows(0);
         response(JSONSuccessString(JSONArrayToString(this.eventlist), 
           new MapTool().putObject("page", this.page)));
       }
     }
     catch (Exception e)
     {
       log.error("##pipelist##: " + e.getMessage(), e);
     }
   }
   
   public void clearevent()
   {
     int csid = getCstmId();
     
     String path = (Storage.getCstmBase(csid).endsWith("\\") ? 
       Storage.getCstmBase(csid) : new StringBuilder(String.valueOf(Storage.getCstmBase(csid))).append("\\").toString()) + 
       "pipelog.txt";
     
     File dir = new File(path);
     if (dir.exists()) {
       try
       {
         dir.delete();
       }
       catch (Exception localException) {}
     }
     response(JSONSuccessString());
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.KeyLogAction
 * JD-Core Version:    0.7.0.1
 */