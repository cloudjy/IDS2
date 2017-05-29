 package com.gnamp.struts.action;
 
 import com.gnamp.server.Storage;
 import com.gnamp.server.handle.ConflictException;
 import com.gnamp.server.handle.LoopStrategyHandle;
 import com.gnamp.server.handle.LoopStrategyProgramHandle;
 import com.gnamp.server.handle.LoopTaskHandle;
 import com.gnamp.server.handle.StrategyConvert;
 import com.gnamp.server.handle.XmlLoopDateRange;
 import com.gnamp.server.model.LoopStrategy;
 import com.gnamp.server.model.LoopStrategyProgram;
 import com.gnamp.server.model.LoopStrategyProgramComparator;
 import com.gnamp.server.model.LoopTask;
 import com.gnamp.struts.utils.JsonUtils;
 import com.gnamp.struts.utils.MapTool;
 import com.gnamp.struts.vo.PageBean;
 import java.io.BufferedInputStream;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.IOException;
 import java.io.OutputStream;
 import java.net.URLEncoder;
 import java.util.ArrayList;
 import java.util.Calendar;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import java.util.Set;
 import java.util.TreeSet;
 import javax.servlet.http.HttpServletResponse;
 
 public class RangeAction
   extends JSONAction
 {
   PageBean page = new PageBean();
   
   public PageBean getPage()
   {
     return this.page;
   }
   
   public void setPage(PageBean page)
   {
     this.page = page;
   }
   
   LoopTask looptask = null;
   
   public LoopTask getLooptask()
   {
     return this.looptask;
   }
   
   public void setLooptask(LoopTask looptask)
   {
     this.looptask = looptask;
   }
   
   public LoopStrategy strategy = null;
   
   public LoopStrategy getStrategy()
   {
     return this.strategy;
   }
   
   public void setStrategy(LoopStrategy strategy)
   {
     this.strategy = strategy;
   }
   
   LoopTaskHandle looptaskhandle = null;
   
   public LoopTaskHandle getLoopTaskhandle()
   {
     return this.looptaskhandle == null ? (this.looptaskhandle = new LoopTaskHandle(
       this)) : this.looptaskhandle;
   }
   
   List<LoopStrategy> strategylist = new ArrayList();
   List<LoopStrategyProgram> strategyprogramlist = new ArrayList();
   LoopStrategyHandle loopstrategyhandle = null;
   
   public LoopStrategyHandle getLoopStrategyHandle()
   {
     return this.loopstrategyhandle == null ? (this.loopstrategyhandle = new LoopStrategyHandle(
       this)) : this.loopstrategyhandle;
   }
   
   LoopStrategyProgramHandle loopstrategyprogramhandle = null;
   
   public LoopStrategyProgramHandle getLoopStrategyProgramHandle()
   {
     return this.loopstrategyprogramhandle == null ? (this.loopstrategyprogramhandle = new LoopStrategyProgramHandle(
       this)) : this.loopstrategyprogramhandle;
   }
   
   public String execute()
   {
     return "list";
   }
   
   public void readall()
   {
     try
     {
       String query = "SELECT CSTM_ID, TASK_ID, STRATEGY_ID, NAME, DESCP, START_DATE, STOP_DATE, WEEK_DAYS, START_TIME, STOP_TIME, CREATE_TIME, CREATE_USER FROM tb_strategy WHERE CSTM_ID=:CSTM_ID AND TASK_ID=:TASK_ID ORDER BY START_DATE, STOP_DATE, WEEK_DAYS, START_TIME, STOP_TIME ASC " + (
       
 
 
 
 
 
 
 
         this.page.getPageSize() != -1 ? "LIMIT " + 
         (this.page.getCurrentPage() - 1) * this.page.getPageSize() + 
         "," + this.page.getPageSize() : "");
       
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
       Map<String, Object> parameters = new HashMap();
       parameters.put("CSTM_ID", Integer.valueOf(getCstmId()));
       parameters.put("TASK_ID", Integer.valueOf(this.looptask.getTaskId()));
       
       int total = getLoopStrategyHandle().readAll(this.looptask.getTaskId()).size();
       
       this.page.setTotalRows(total);
       this.strategylist = getLoopStrategyHandle().readAll(query, parameters, this.looptask.getTaskId());
       response(JSONSuccessString(JSONArrayToString(this.strategylist), 
         new MapTool().putObject("page", this.page)));
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
   
   List<LoopStrategy> slist = new ArrayList();
   public int prgmid = 0;
   
   public int getPrgmid()
   {
     return this.prgmid;
   }
   
   public void setPrgmid(int prgmid)
   {
     this.prgmid = prgmid;
   }
   
   public void readallByprogramID()
   {
     try
     {
       this.strategylist = getLoopStrategyHandle()
         .readAll(this.looptask.getTaskId());
       if (this.strategylist != null)
       {
         Iterator localIterator2;
         for (Iterator localIterator1 = this.strategylist.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
         {
           LoopStrategy s = (LoopStrategy)localIterator1.next();
           LoopStrategy ls = new LoopStrategy();
           localIterator2 = s.getPrograms().iterator();
           LoopStrategyProgram p = (LoopStrategyProgram)localIterator2.next();
           Set<LoopStrategyProgram> plist = new TreeSet(
             new LoopStrategyProgramComparator());
           if (p.getProgramId() == this.prgmid)
           {
             plist.add(p);
             ls.setStartDate(s.getStartDate());
             ls.setStartTime(s.getStartTime());
             ls.setStopDate(s.getStopDate());
             ls.setStopTime(s.getStopTime());
             ls.setPrograms(plist);
             
             this.slist.add(ls);
           }
         }
       }
       response(JSONSuccessString(JSONArrayToString(this.slist)));
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
   
   public void readprograms()
   {
     try
     {
       this.strategyprogramlist = getLoopStrategyProgramHandle().readAll(
         this.strategy.getTaskId(), this.strategy.getStrategyId());
       
       response(JSONSuccessString(JSONArrayToString(this.strategyprogramlist)));
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
   
   public String toadd()
   {
     this.looptask = ((LoopTask)getLoopTaskhandle().read(this.strategy.getTaskId()));
     return "add";
   }
   
   public String week = "";
   
   public String getWeek()
   {
     return this.week;
   }
   
   public void setWeek(String week)
   {
     this.week = week;
   }
   
   public String prgms = "";
   
   public String getPrgms()
   {
     return this.prgms;
   }
   
   public void setPrgms(String prgms)
   {
     this.prgms = prgms;
   }
   
   public void add()
   {
     try
     {
       int weekdays = 0;
       this.strategy.setTaskId(this.strategy.getTaskId());
       this.strategy.setStrategyName("hello world");
       this.strategy.setCreateTime(new Date());
       this.strategy.setDescription("hello world");
       
       String[] strtt = this.startspan.split(":");
       if ((strtt != null) && (strtt.length == 3)) {
         this.strategy.setStartTime(Integer.parseInt(strtt[0]) * 60 * 60 + 
           Integer.parseInt(strtt[1]) * 60 + 
           Integer.parseInt(strtt[2]));
       }
       String[] stopt = this.stopspan.split(":");
       if ((stopt != null) && (stopt.length == 3)) {
         this.strategy.setStopTime(Integer.parseInt(stopt[0]) * 60 * 60 + 
           Integer.parseInt(stopt[1]) * 60 + 
           Integer.parseInt(stopt[2]) - 1);
       }
       if ((this.week != null) && (this.week.length() > 0))
       {
         String[] strWeek = this.week.split(",");
         if (strWeek != null) {
           for (int i = 0; i < strWeek.length; i++) {
             weekdays |= Integer.parseInt(strWeek[i]);
           }
         }
       }
       int Mask = validMask(this.strategy.getStopDate(), 
         this.strategy.getStartDate());
       weekdays &= Mask;
       if (weekdays == 0) {
         JsonUtils.writeErrorData(this.servletResponse, getText("newfail") + 
           getText("timeerror"));
       }
       this.strategy.setWeekDays(weekdays);
       String[] prgmIds = null;
       if ((this.prgms != null) && (this.prgms.length() > 0)) {
         prgmIds = this.prgms.split(",");
       }
       if (!validDateTime(this.strategy.getStartDate(), this.strategy.getStopDate(), this.strategy.getStartTime(), this.strategy.getStopTime())) {
         JsonUtils.writeErrorData(this.servletResponse, getText("newfail") + 
           getText("timeerror"));
       }
       if (getLoopStrategyHandle().create(this.strategy, prgmIds, 0)) {
         JsonUtils.writeSuccess(this.servletResponse);
       } else {
         JsonUtils.writeErrorData(this.servletResponse, getText("newfail") + 
           getText("timeisincorrect"));
       }
     }
     catch (ConflictException ex)
     {
       JsonUtils.writeErrorData(this.servletResponse, 
         JSONArrayToString(ex.getConflictStrategy()));
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorData(this.servletResponse, getText("newfail") + 
         getText("timeisincorrect"));
     }
   }
   
   public int minute = 0;
   public int second = 0;
   public int minute1 = 0;
   public int second1 = 0;
   
   public int getMinute()
   {
     return this.minute;
   }
   
   public void setMinute(int minute)
   {
     this.minute = minute;
   }
   
   public int getSecond()
   {
     return this.second;
   }
   
   public void setSecond(int second)
   {
     this.second = second;
   }
   
   public int getMinute1()
   {
     return this.minute1;
   }
   
   public void setMinute1(int minute1)
   {
     this.minute1 = minute1;
   }
   
   public int getSecond1()
   {
     return this.second1;
   }
   
   public void setSecond1(int second1)
   {
     this.second1 = second1;
   }
   
   public String strCount = "";
   
   public String getStrCount()
   {
     return this.strCount;
   }
   
   public void setStrCount(String strCount)
   {
     this.strCount = strCount;
   }
   
   public void addmore()
   {
     int playCount = 0;
     try
     {
       playCount = Integer.parseInt(this.strCount);
     }
     catch (Exception e)
     {
       playCount = 0;
     }
     try
     {
       int weekdays = 0;
       this.strategy.setTaskId(this.strategy.getTaskId());
       this.strategy.setStrategyName("hello world");
       this.strategy.setCreateTime(new Date());
       this.strategy.setDescription("hello world");
       
       String[] strtt = this.startspan.split(":");
       
       int begintime = 0;
       int endtime = 0;
       if ((strtt != null) && (strtt.length == 3)) {
         begintime = Integer.parseInt(strtt[0]) * 60 * 60 + 
           Integer.parseInt(strtt[1]) * 60 + 
           Integer.parseInt(strtt[2]);
       }
       String[] stopt = this.stopspan.split(":");
       if ((stopt != null) && (stopt.length == 3)) {
         endtime = Integer.parseInt(stopt[0]) * 60 * 60 + 
           Integer.parseInt(stopt[1]) * 60 + 
           Integer.parseInt(stopt[2]) - 1;
       }
       if ((this.week != null) && (this.week.length() > 0))
       {
         String[] strWeek = this.week.split(",");
         if (strWeek != null) {
           for (int i = 0; i < strWeek.length; i++) {
             weekdays |= Integer.parseInt(strWeek[i]);
           }
         }
       }
       int Mask = validMask(this.strategy.getStopDate(), 
         this.strategy.getStartDate());
       weekdays &= Mask;
       if (weekdays == 0) {
         JsonUtils.writeErrorData(this.servletResponse, getText("newfail") + 
           getText("timeerror"));
       }
       this.strategy.setWeekDays(weekdays);
       String[] prgmIds = null;
       if ((this.prgms != null) && (this.prgms.length() > 0)) {
         prgmIds = this.prgms.split(",");
       }
       while (begintime + (this.minute * 60 + this.second + this.minute1 * 60 + this.second1) < endtime)
       {
         int endt = 0;
         this.strategy.setStartTime(begintime);
         endt = begintime + this.minute1 * 60 + this.second1 - 1;
         this.strategy.setStopTime(endt);
         if (!validDateTime(this.strategy.getStartDate(), this.strategy.getStopDate(), this.strategy.getStartTime(), this.strategy.getStopTime())) {
           JsonUtils.writeErrorData(this.servletResponse, 
             getText("newfail") + getText("timeerror"));
         }
         if (getLoopStrategyHandle().create(this.strategy, prgmIds, playCount)) {
           begintime += this.minute * 60 + this.second;
         } else {
           JsonUtils.writeErrorData(this.servletResponse, 
             getText("newfail") + getText("timeisincorrect"));
         }
       }
     }
     catch (ConflictException ex)
     {
       JsonUtils.writeErrorData(this.servletResponse, 
         JSONArrayToString(ex.getConflictStrategy()));
     }
     catch (Exception e)
     {
       e.printStackTrace();
       JsonUtils.writeErrorData(this.servletResponse, getText("newfail") + 
         getText("timeisincorrect"));
     }
     JsonUtils.writeSuccess(this.servletResponse);
   }
   
   public void delete()
   {
     if ((this.selectIDs != null) && (this.selectIDs.length() > 0)) {
       for (int i = 0; i < this.selectIDs.split(",").length; i++) {
         try
         {
           getLoopStrategyHandle().remove(this.strategy.getTaskId(), 
             Integer.parseInt(this.selectIDs.split(",")[i]));
         }
         catch (Exception e)
         {
           e.printStackTrace();
           JsonUtils.writeErrorData(this.servletResponse, 
             getText("deletefailed"));
         }
       }
     }
     JsonUtils.writeSuccess(this.servletResponse);
   }
   
   public void clear()
   {
     try
     {
       getLoopStrategyHandle().removeAll(this.looptask.getTaskId());
     }
     catch (Exception e)
     {
       e.printStackTrace();
       JsonUtils.writeErrorData(this.servletResponse, getText("deletefailed"));
     }
     JsonUtils.writeSuccess(this.servletResponse);
   }
   
   public boolean validDateTime(Date startDate, Date stopDate, int startTime, int stopTime)
   {
     return ((startDate.equals(stopDate)) || (startDate.before(stopDate))) && (startTime < stopTime);
   }
   
   public void save()
   {
     try
     {
       LoopStrategy old = getLoopStrategyHandle().read(
         this.strategy.getTaskId(), this.strategy.getStrategyId());
       
       old.setStartDate(this.strategy.getStartDate());
       old.setStopDate(this.strategy.getStopDate());
       
       String[] strtt = this.startspan.split(":");
       if ((strtt != null) && (strtt.length == 3)) {
         old.setStartTime(Integer.parseInt(strtt[0]) * 60 * 60 + 
           Integer.parseInt(strtt[1]) * 60 + 
           Integer.parseInt(strtt[2]));
       }
       String[] stopt = this.stopspan.split(":");
       if ((stopt != null) && (stopt.length == 3)) {
         old.setStopTime(Integer.parseInt(stopt[0]) * 60 * 60 + 
           Integer.parseInt(stopt[1]) * 60 + 
           Integer.parseInt(stopt[2]) - 1);
       }
       int weekdays = 0;
       if ((this.week != null) && (this.week.length() > 0))
       {
         String[] strWeek = this.week.split(",");
         if (strWeek != null) {
           for (int i = 0; i < strWeek.length; i++) {
             weekdays |= Integer.parseInt(strWeek[i]);
           }
         }
       }
       int Mask = validMask(this.strategy.getStopDate(), 
         this.strategy.getStartDate());
       weekdays &= Mask;
       if (weekdays == 0) {
         JsonUtils.writeErrorData(this.servletResponse, getText("editfail"));
       }
       if (weekdays != old.getWeekDays()) {
         old.setWeekDays(weekdays);
       }
       if (!validDateTime(old.getStartDate(), old.getStopDate(), old.getStartTime(), old.getStopTime())) {
         JsonUtils.writeErrorData(this.servletResponse, getText("timeerror"));
       }
       if (getLoopStrategyHandle().modify(old)) {
         JsonUtils.writeSuccess(this.servletResponse);
       } else {
         JsonUtils.writeErrorData(this.servletResponse, getText("editfail"));
       }
     }
     catch (ConflictException ex)
     {
       JsonUtils.writeErrorData(this.servletResponse, 
         JSONArrayToString(ex.getConflictStrategy()));
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorData(this.servletResponse, getText("editfail"));
     }
     JsonUtils.writeSuccess(this.servletResponse);
   }
   
   private int validMask(Date stop, Date start)
   {
     int Mask = 0;
     int[] array = { 1, 2, 4, 
       8, 16, 32, 
       64 };
     
     long quot = (stop.getTime() - start.getTime()) / 1000L / 60L / 60L / 24L;
     if (quot < 7L)
     {
       Calendar cal = Calendar.getInstance();
       cal.setTime(this.strategy.getStartDate());
       int w = cal.get(7);
       if (w == 2) {
         w = 0;
       } else if (w == 3) {
         w = 1;
       } else if (w == 4) {
         w = 2;
       } else if (w == 5) {
         w = 3;
       } else if (w == 6) {
         w = 4;
       } else if (w == 7) {
         w = 5;
       } else if (w == 1) {
         w = 6;
       }
       for (int i = 0; i <= quot; i++) {
         Mask |= array[((w + i) % 7)];
       }
     }
     else
     {
       Mask = 127;
     }
     return Mask;
   }
   
   public String selectIDs = "";
   
   public String getSelectIDs()
   {
     return this.selectIDs;
   }
   
   public void setSelectIDs(String selectIDs)
   {
     this.selectIDs = selectIDs;
   }
   
   public String startspan = "";
   public String stopspan = "";
   
   public String getStartspan()
   {
     return this.startspan;
   }
   
   public void setStartspan(String startspan)
   {
     this.startspan = startspan;
   }
   
   public String getStopspan()
   {
     return this.stopspan;
   }
   
   public void setStopspan(String stopspan)
   {
     this.stopspan = stopspan;
   }
   
   public String preprgm()
   {
     this.strategy = getLoopStrategyHandle().read(this.strategy.getTaskId(), 
       this.strategy.getStrategyId());
     
     return "preprgm";
   }
   
   List<LoopStrategyProgram> prgmlist = new ArrayList();
   
   public void prgmlist()
   {
     try
     {
       this.prgmlist = getLoopStrategyProgramHandle().readAll(this.prgm.getTaskId(), 
         this.prgm.getStrategyId());
       
       this.page.setTotalRows(this.prgmlist.size());
       response(JSONSuccessString(JSONArrayToString(this.prgmlist), 
         new MapTool().putObject("page", this.page)));
     }
     catch (Exception e)
     {
       e.printStackTrace();
       response(JSONErrorString(e.getMessage()));
     }
   }
   
   public LoopStrategyProgram prgm = null;
   
   public LoopStrategyProgram getPrgm()
   {
     return this.prgm;
   }
   
   public void setPrgm(LoopStrategyProgram prgm)
   {
     this.prgm = prgm;
   }
   
   public void addfile()
   {
     if (this.selectIDs.length() > 0) {
       for (int i = 0; i < this.selectIDs.split(",").length; i++) {
         try
         {
           this.prgm.setSequenceNum(2147483647);
           
           this.prgm.setProgramId(Integer.parseInt(this.selectIDs.split(",")[i]));
           
           getLoopStrategyProgramHandle().create(this.prgm);
         }
         catch (Exception e)
         {
           e.printStackTrace();
         }
       }
     }
     JsonUtils.writeSuccess(this.servletResponse);
   }
   
   public void deleteprgm()
   {
     if ((this.selectIDs != null) && (this.selectIDs.length() > 0)) {
       for (int i = 0; i < this.selectIDs.split(",").length; i++) {
         try
         {
           getLoopStrategyProgramHandle().remove(this.prgm.getTaskId(), 
             this.prgm.getStrategyId(), 
             Integer.parseInt(this.selectIDs.split(",")[i]));
         }
         catch (Exception e)
         {
           JsonUtils.writeErrorData(this.servletResponse, 
             getText("deletefailed"));
         }
       }
     }
     JsonUtils.writeSuccess(this.servletResponse);
   }
   
   public void upprgm()
   {
     LoopStrategyProgram old = getLoopStrategyProgramHandle().read(
       this.prgm.getTaskId(), this.prgm.getStrategyId(), this.prgm.getSerialId());
     int seq = old.getSequenceNum() - 1;
     old.setSequenceNum(seq);
     try
     {
       if (getLoopStrategyProgramHandle().modify(old)) {
         JsonUtils.writeSuccess(this.servletResponse);
       } else {
         JsonUtils.writeError(this.servletResponse);
       }
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
   
   public void downprgm()
   {
     LoopStrategyProgram old = getLoopStrategyProgramHandle().read(
       this.prgm.getTaskId(), this.prgm.getStrategyId(), this.prgm.getSerialId());
     int seq = old.getSequenceNum() + 1;
     old.setSequenceNum(seq);
     try
     {
       if (getLoopStrategyProgramHandle().modify(old)) {
         JsonUtils.writeSuccess(this.servletResponse);
       } else {
         JsonUtils.writeError(this.servletResponse);
       }
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
   
   public int playcount = 0;
   
   public int getPlaycount()
   {
     return this.playcount;
   }
   
   public void setPlaycount(int playcount)
   {
     this.playcount = playcount;
   }
   
   public void changecount()
   {
     LoopStrategyProgram old = getLoopStrategyProgramHandle().read(
       this.prgm.getTaskId(), this.prgm.getStrategyId(), this.prgm.getSerialId());
     int p = this.playcount;
     old.setPlayCount(p);
     try
     {
       if (getLoopStrategyProgramHandle().modify(old)) {
         JsonUtils.writeSuccess(this.servletResponse);
       } else {
         JsonUtils.writeError(this.servletResponse);
       }
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
   
   public String toview()
   {
     this.looptask = ((LoopTask)getLoopTaskhandle().read(this.looptask.getTaskId()));
     return "viewdate";
   }
   
   public void view()
   {
     StrategyConvert sc = new StrategyConvert();
     try
     {
       List<XmlLoopDateRange> xmlLoopDateRangeList = sc
         .convertToXmlLoopDateRangeList(getLoopStrategyHandle()
         .readAll(this.looptask.getTaskId()));
       
       response(JSONSuccessString(JSONArrayToString(xmlLoopDateRangeList)));
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
   
   public void xml()
   {
     Storage storage = new Storage(getCstmId());
     String path = storage.getStrategyListPath(this.looptask.getTaskId());
     
     File download_file = new File(path);
     try
     {
       this.servletResponse.setContentType("application/x-msdownload");
       this.servletResponse.setContentLength((int)download_file.length());
       this.servletResponse.setHeader(
         "Content-Disposition", 
         "attachment;filename=" + 
         URLEncoder.encode("strategy.xml", 
         "UTF-8"));
       this.servletResponse.setHeader("windows-Target", "_blank");
       
       BufferedInputStream input = new BufferedInputStream(
         new FileInputStream(download_file));
       byte[] buffBytes = new byte[1024];
       OutputStream out = this.servletResponse.getOutputStream();
       int read = 0;
       while ((read = input.read(buffBytes)) != -1) {
         out.write(buffBytes, 0, read);
       }
       out.flush();
       out.close();
       input.close();
     }
     catch (IOException e)
     {
       e.printStackTrace();
     }
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.RangeAction
 * JD-Core Version:    0.7.0.1
 */