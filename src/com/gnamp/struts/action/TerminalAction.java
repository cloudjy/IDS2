package com.gnamp.struts.action;

import com.gnamp.server.InnerException;
import com.gnamp.server.Storage;
import com.gnamp.server.handle.AppHandle;
import com.gnamp.server.handle.CityHandle;
import com.gnamp.server.handle.ConfigHandle;
import com.gnamp.server.handle.DemandTaskHandle;
import com.gnamp.server.handle.GroupHandle;
import com.gnamp.server.handle.KernelHandle;
import com.gnamp.server.handle.LoopTaskHandle;
import com.gnamp.server.handle.MessageServer;
import com.gnamp.server.handle.PluginTaskHandle;
import com.gnamp.server.handle.TerminalEventHandle;
import com.gnamp.server.handle.TerminalExceptionHandle;
import com.gnamp.server.handle.TerminalHandle;
import com.gnamp.server.model.App;
import com.gnamp.server.model.City;
import com.gnamp.server.model.Config;
import com.gnamp.server.model.DemandTask;
import com.gnamp.server.model.Group;
import com.gnamp.server.model.Kernel;
import com.gnamp.server.model.LoopTask;
import com.gnamp.server.model.PluginTask;
import com.gnamp.server.model.Terminal;
import com.gnamp.server.model.TerminalEvent;
import com.gnamp.server.model.TerminalExp;
import com.gnamp.server.query.Condition;
import com.gnamp.server.query.Order;
import com.gnamp.server.query.Result;
import com.gnamp.server.query.TaskColumn;
import com.gnamp.struts.utils.JsonUtils;
import com.gnamp.struts.utils.MapTool;
import com.gnamp.struts.vo.ExcelTerminalExpVo;
import com.gnamp.struts.vo.ExcelTerminalListVo;
import com.gnamp.struts.vo.ExcelTerminalVo;
import com.gnamp.struts.vo.ExcelTitleVo;
import com.gnamp.struts.vo.PageBean;
import com.gnamp.struts.vo.TreeToTerminalParameter;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import common.Logger;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class TerminalAction extends ExcelAction {
	public int count = 0;

	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String selectIDs = "";

	public String getSelectIDs() {
		return this.selectIDs;
	}

	public void setSelectIDs(String selectIDs) {
		this.selectIDs = selectIDs;
	}

	List<Terminal> terminalreport = null;
	List<TerminalExp> terminalexp = null;
	List<TerminalEvent> terminalevent = null;

	public List<TerminalEvent> getTerminalevent() {
		return this.terminalevent;
	}

	public void setTerminalevent(List<TerminalEvent> terminalevent) {
		this.terminalevent = terminalevent;
	}

	public List<TerminalExp> getTerminalexp() {
		return this.terminalexp;
	}

	public void setTerminalexp(List<TerminalExp> terminalexp) {
		this.terminalexp = terminalexp;
	}

	public List<Terminal> getTerminalreport() {
		return this.terminalreport;
	}

	public void setTerminalreport(List<Terminal> terminalreport) {
		this.terminalreport = terminalreport;
	}

	TerminalHandle terminalhandle = null;

	public TerminalHandle getTerminalhandle() {
		return this.terminalhandle == null ? (this.terminalhandle = new TerminalHandle(this)) : this.terminalhandle;
	}

	TerminalEventHandle terminaleventhandle = null;

	public TerminalEventHandle getTerminalEventHandle() {
		return this.terminaleventhandle == null ? (this.terminaleventhandle = new TerminalEventHandle(this))
				: this.terminaleventhandle;
	}

	TerminalExceptionHandle terminalexceptionhandle = null;
	List<String> terminalsDeviceName;
	Terminal terminal;
	public List<Terminal> terminallist;
	TreeToTerminalParameter terminalparam;
	private String sortFiled;
	private String sort;

	public TerminalExceptionHandle getTerminalExceptionHandle() {
		return this.terminalexceptionhandle == null ? (this.terminalexceptionhandle = new TerminalExceptionHandle(this))
				: this.terminalexceptionhandle;
	}

	public List<String> getTerminalsDeviceName() {
		return this.terminalsDeviceName;
	}

	public void setTerminalsDeviceName(List<String> terminalsDeviceName) {
		this.terminalsDeviceName = terminalsDeviceName;
	}

	public Terminal getTerminal() {
		return this.terminal;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}

	public List<Terminal> getTerminallist() {
		return this.terminallist;
	}

	public void setTerminallist(List<Terminal> terminallist) {
		this.terminallist = terminallist;
	}

	public TreeToTerminalParameter getTerminalparam() {
		return this.terminalparam;
	}

	public void setTerminalparam(TreeToTerminalParameter terminalparam) {
		this.terminalparam = terminalparam;
	}

	public void query() {
		response(JSONSuccessString(JSONArrayToString(getQueryList()), new MapTool().putObject("page", this.page)));
	}

	public String getSort() {
		return this.sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getSortFiled() {
		return this.sortFiled;
	}

	public void setSortFiled(String sortFiled) {
		this.sortFiled = sortFiled;
	}

	private PageBean page = new PageBean();
	private String oName;
	private String oManaer;

	public PageBean getPage() {
		return this.page;
	}

	public void setPage(PageBean page) {
		this.page = page;
	}

	public String getoName() {
		return this.oName;
	}

	public void setOName(String oName) {
		this.oName = oName;
	}

	public String getoManaer() {
		return orderManner.DESC.value().equals(this.oManaer) ? orderManner.DESC.value() : orderManner.ASC.value();
	}

	public void setOManaer(String oManaer) {
		this.oManaer = oManaer;
	}

	public static enum orderManner {
		ASC {
			@Override
			String value() {

				return "asc";
			}
		},
		DESC {
			@Override
			String value() {
				return "desc";
			}
		};

		abstract String value();
	}

	private List<Terminal> getQueryList() {
		List<Terminal> list = new ArrayList();
		try {
			Map<String, Object> param = new HashMap();
			setQueryParam(param);
			list = getTerminalhandle().readAll(queryString(), param);
			int totalSize = getTerminalhandle().queryInteger(queryStringTotal(), param);
			this.page.setTotalRows(totalSize);

			String hardware = this.servletRequest.getParameter("hardware");
			if ((list != null) && (hardware != null) && (hardware.equalsIgnoreCase("true"))) {
				for (Terminal t : list) {
					TerminalSettingAction.readHardwareInformation(t);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	private void setQueryParam(Map<String, Object> param) {
		param.put("CSTM_ID", Integer.valueOf(getCstmId()));
		if (this.terminalparam.getGroupId() != 0) {
			param.put("GROUP_ID", Integer.valueOf(this.terminalparam.getGroupId()));
		}
		if ((this.terminalparam.getSearchKey() != null) && (!"".equals(this.terminalparam.getSearchKey()))) {
			param.put("SEARCH_KEY", "%" + this.terminalparam.getSearchKey() + "%");
		}
		param.put("STAET_INDEX", Integer.valueOf(
				this.page.getCurrentPage() > 1 ? (this.page.getCurrentPage() - 1) * this.page.getPageSize() : 0));
		param.put("PAGE_SIZE", Integer.valueOf(this.page.getPageSize()));
	}

	private String queryStringTotal() {
		String queryTable = "SELECT * FROM vw_terminal_detail WHERE CSTM_ID=:CSTM_ID " + (

		this.terminalparam.getGroupId() == 0 ? " AND GROUP_ID IS NULL "
				: this.terminalparam.isIncludeChildren()
						? " AND GROUP_ID IN(SELECT :GROUP_ID AS GROUP_ID UNION SELECT GROUP_ID FROM tb_group_tree WHERE PARENT_ID=:GROUP_ID) "
						: this.terminalparam.getGroupId() == 0 ? "" : " AND GROUP_ID=:GROUP_ID ")
				+ ((this.terminalparam.getSearchKey() != null) && (!"".equals(this.terminalparam.getSearchKey()))
						? " AND (LPAD(CAST(HEX(DEV_ID) AS CHAR(100)),12,'0') LIKE :SEARCH_KEY OR NAME LIKE :SEARCH_KEY) "
						: "");

		String query = "SELECT count(1) FROM (" +

				queryTable + ") as f " + "LEFT JOIN tb_group as c on f.CSTM_ID=c.CSTM_ID AND f.GROUP_ID=c.GROUP_ID";
		return query;
	}

	private String queryString() {
		String queryTable = "SELECT * FROM vw_terminal_detail WHERE CSTM_ID=:CSTM_ID " + (

		this.terminalparam.getGroupId() == 0 ? " AND GROUP_ID IS NULL "
				: this.terminalparam.isIncludeChildren()
						? " AND GROUP_ID IN(SELECT :GROUP_ID AS GROUP_ID UNION SELECT GROUP_ID FROM tb_group_tree WHERE PARENT_ID=:GROUP_ID) "
						: this.terminalparam.getGroupId() == 0 ? "" : " AND GROUP_ID=:GROUP_ID ")
				+ ((this.terminalparam.getSearchKey() != null) && (!"".equals(this.terminalparam.getSearchKey()))
						? " AND (LPAD(CAST(HEX(DEV_ID) AS CHAR(100)),12,'0') LIKE :SEARCH_KEY OR NAME LIKE :SEARCH_KEY)  "
						: "");

		String query = "SELECT f.CSTM_ID AS CSTM_ID, DEV_ID, f.NAME AS NAME, f.DESCP AS DESCP, f.GROUP_ID AS GROUP_ID, c.NAME AS GROUP_NAME, f.CREATE_TIME AS CREATE_TIME, f.CREATE_USER AS CREATE_USER, CITY_ID, CITY_NAME, ASSIGN_KERNEL, ASSIGN_APP, CUR_KERNEL, CUR_APP, ASSIGN_CONFIG_ID, ASSIGN_CONFIG_NAME, ASSIGN_CONFIG_VERSION, CUR_CONFIG_ID, CUR_CONFIG_NAME, CUR_CONFIG_VERSION, ASSIGN_LOOPTASK_ID, ASSIGN_LOOPTASK_NAME, ASSIGN_LOOPTASK_VERSION, ASSIGN_LOOPFILE_VERSION, ASSIGN_LOOPPLAY_VERSION, ASSIGN_LOOPSTRATEGY_VERSION, CUR_LOOPTASK_ID, CUR_LOOPTASK_NAME, CUR_LOOPTASK_VERSION, CUR_LOOPFILE_VERSION, CUR_LOOPPLAY_VERSION, CUR_LOOPSTRATEGY_VERSION, ASSIGN_DEMANDTASK_ID, ASSIGN_DEMANDTASK_NAME, ASSIGN_DEMANDTASK_VERSION, ASSIGN_DEMANDFILE_VERSION, ASSIGN_DEMANDPLAY_VERSION, CUR_DEMANDTASK_ID, CUR_DEMANDTASK_NAME, CUR_DEMANDTASK_VERSION, CUR_DEMANDFILE_VERSION, CUR_DEMANDPLAY_VERSION, ASSIGN_PLUGINTASK_ID, ASSIGN_PLUGINTASK_NAME, ASSIGN_PLUGINTASK_VERSION, ASSIGN_PLUGINFILE_VERSION, ASSIGN_PLUGINPLAY_VERSION, CUR_PLUGINTASK_ID, CUR_PLUGINTASK_NAME, CUR_PLUGINTASK_VERSION, CUR_PLUGINFILE_VERSION, CUR_PLUGINPLAY_VERSION, REST_SCHEDULE, STANDBY_SCHEDULE, CAPTURE_SCHEDULE, DEMAND_SCHEDULE, SCHEDULE_VERSION, SUBTITLE, SUBTITLE_VERSION, CUR_SUBTITLE_VERSION, ONLINE_STATE, LOGON_TIME, LOGOFF_TIME, DOWNLOAD_TOTAL, DOWNLOAD_FINISHED, DOWNLOAD_TYPE, POWERON, POWEROFF, ALIVE_INTERVAL, LAST_ALIVE, LAST_PATROL, LAST_DOWNLOAD,  KERNEL_UPDATED, APP_UPDATED, CONFIG_UPDATED, LOOPTASK_UPDATED, DEMANDTASK_UPDATED, PLUGINTASK_UPDATED, SUBTITLE_UPDATED FROM ("
				+

				queryTable + ") as f " + " LEFT JOIN tb_group as c on f.CSTM_ID=c.CSTM_ID AND f.GROUP_ID=c.GROUP_ID "
				+ " ORDER BY " + getoName() + " " + getoManaer() + " LIMIT :STAET_INDEX,:PAGE_SIZE";

		return query;
	}

	private String queryNoLimitString() {
		String queryTable = "SELECT * FROM vw_terminal_detail WHERE CSTM_ID=:CSTM_ID " + (

		this.terminalparam.getGroupId() == 0 ? " AND GROUP_ID IS NULL "
				: this.terminalparam.isIncludeChildren()
						? " AND GROUP_ID IN(SELECT :GROUP_ID AS GROUP_ID UNION SELECT GROUP_ID FROM tb_group_tree WHERE PARENT_ID=:GROUP_ID) "
						: this.terminalparam.getGroupId() == 0 ? "" : " AND GROUP_ID=:GROUP_ID ")
				+ ((this.terminalparam.getSearchKey() != null) && (!"".equals(this.terminalparam.getSearchKey()))
						? " AND LPAD(CAST(HEX(DEV_ID) AS CHAR(100)),12,'0') LIKE :DEV_ID " : "");

		String query = "SELECT f.CSTM_ID AS CSTM_ID, DEV_ID, f.NAME AS NAME, f.DESCP AS DESCP, f.GROUP_ID AS GROUP_ID, c.NAME AS GROUP_NAME, f.CREATE_TIME AS CREATE_TIME, f.CREATE_USER AS CREATE_USER, CITY_ID, CITY_NAME, ASSIGN_KERNEL, ASSIGN_APP, CUR_KERNEL, CUR_APP, ASSIGN_CONFIG_ID, ASSIGN_CONFIG_NAME, ASSIGN_CONFIG_VERSION, CUR_CONFIG_ID, CUR_CONFIG_NAME, CUR_CONFIG_VERSION, ASSIGN_LOOPTASK_ID, ASSIGN_LOOPTASK_NAME, ASSIGN_LOOPTASK_VERSION, ASSIGN_LOOPFILE_VERSION, ASSIGN_LOOPPLAY_VERSION, ASSIGN_LOOPSTRATEGY_VERSION, CUR_LOOPTASK_ID, CUR_LOOPTASK_NAME, CUR_LOOPTASK_VERSION, CUR_LOOPFILE_VERSION, CUR_LOOPPLAY_VERSION, CUR_LOOPSTRATEGY_VERSION, ASSIGN_DEMANDTASK_ID, ASSIGN_DEMANDTASK_NAME, ASSIGN_DEMANDTASK_VERSION, ASSIGN_DEMANDFILE_VERSION, ASSIGN_DEMANDPLAY_VERSION, CUR_DEMANDTASK_ID, CUR_DEMANDTASK_NAME, CUR_DEMANDTASK_VERSION, CUR_DEMANDFILE_VERSION, CUR_DEMANDPLAY_VERSION, ASSIGN_PLUGINTASK_ID, ASSIGN_PLUGINTASK_NAME, ASSIGN_PLUGINTASK_VERSION, ASSIGN_PLUGINFILE_VERSION, ASSIGN_PLUGINPLAY_VERSION, CUR_PLUGINTASK_ID, CUR_PLUGINTASK_NAME, CUR_PLUGINTASK_VERSION, CUR_PLUGINFILE_VERSION, CUR_PLUGINPLAY_VERSION, REST_SCHEDULE, STANDBY_SCHEDULE, CAPTURE_SCHEDULE, DEMAND_SCHEDULE, SCHEDULE_VERSION, SUBTITLE, SUBTITLE_VERSION, CUR_SUBTITLE_VERSION, ONLINE_STATE, LOGON_TIME, LOGOFF_TIME, DOWNLOAD_TOTAL, DOWNLOAD_FINISHED, DOWNLOAD_TYPE, POWERON, POWEROFF, ALIVE_INTERVAL, LAST_ALIVE, LAST_PATROL, LAST_DOWNLOAD,  KERNEL_UPDATED, APP_UPDATED, CONFIG_UPDATED, LOOPTASK_UPDATED, DEMANDTASK_UPDATED, PLUGINTASK_UPDATED, SUBTITLE_UPDATED FROM ("
				+

				queryTable + ") as f " + " LEFT JOIN tb_group as c on f.CSTM_ID=c.CSTM_ID AND f.GROUP_ID=c.GROUP_ID "
				+ " ORDER BY " + getoName() + " " + getoManaer();

		return query;
	}

	private void setQueryNoLimitParam(Map<String, Object> param) {
		param.put("CSTM_ID", Integer.valueOf(getCstmId()));
		if (this.terminalparam.getGroupId() != 0) {
			param.put("GROUP_ID", Integer.valueOf(this.terminalparam.getGroupId()));
		}
		if ((this.terminalparam.getSearchKey() != null) && (!"".equals(this.terminalparam.getSearchKey()))) {
			param.put("DEV_ID", "%" + this.terminalparam.getSearchKey() + "%");
		}
	}

	public void sort() {
		List<Terminal> list = getQueryList();
		if (list == null) {
			response(JSONErrorString());
			return;
		}
		response(JSONSuccessString(JSONArrayToString(list), new MapTool().putObject("page", this.page)));
	}

	public String execute() {
		return "list";
	}

	String sTree = "";

	public String getsTree() {
		return this.sTree;
	}

	public void setsTree(String sTree) {
		this.sTree = sTree;
	}

	int con = 0;

	public String ToAddTerminal() {
		this.provincelist = CityHandle.readAllProvinces();
		return "add";
	}

	public void AddTerminal() {
		try {
			if (StringUtils.isBlank(this.terminal.getDeviceName())) {
				JsonUtils.writeError(this.servletResponse, getText("name_cannot_null"), "NameCannotNull");
				return;
			}
			if (getTerminalhandle().create(this.terminal)) {
				LogHelp.userEvent(this.cstmId, this.userName, getText("addterminal"),
						getText("addterminal") + "[" + this.terminal.getDeviceName() + "]");
				JsonUtils.writeSuccess(this.servletResponse);
			} else {
				JsonUtils.writeErrorMessage(this.servletResponse, getText("additemfail"));
			}
		} catch (InnerException e) {
			String innerType = e.getInnerType();
			if (innerType.equals("device already exists")) {
				JsonUtils.writeError(this.servletResponse, getText("already_existed"), "AlreadyExists");
			} else if (innerType.equals("name cannot null")) {
				JsonUtils.writeError(this.servletResponse, getText("name_cannot_null"), "NameCannotNull");
			} else {
				JsonUtils.writeErrorMessage(this.servletResponse, getText("additemfail"));
			}
		} catch (Exception e) {
			JsonUtils.writeErrorMessage(this.servletResponse, getText("additemfail"));
		}
	}

	public String ToAddMore() {
		return "more";
	}

	public void AddMore() {
		long deviceId = this.terminal.getDeviceId();
		String deviceName = this.terminal.getDeviceName();
		try {
			if (this.count > 0) {
				for (int i = 1; i <= this.count; i++) {
					this.terminal.setDeviceId(deviceId);
					this.terminal.setDeviceName(deviceName + i);
					getTerminalhandle().create(this.terminal);

					LogHelp.userEvent(this.cstmId, this.userName, getText("addterminal"),
							getText("addterminal") + "[" + deviceName + i + "]");

					deviceId += 1L;
				}
			}
			response(JSONSuccessString());
		} catch (Exception e) {
			JsonUtils.writeErrorData(this.servletResponse, getText("batchfailed"));
		}
	}

	public List<Group> grouppath = null;
	public List<Group> brother = null;
	Group group = null;

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	GroupHandle grouphandle = null;

	public GroupHandle getGrouphandle() {
		return this.grouphandle == null ? (this.grouphandle = new GroupHandle(this)) : this.grouphandle;
	}

	public String ToModifyTerminal() {
		this.provincelist = CityHandle.readAllProvinces();
		if ((this.selectIDs != null) && (this.selectIDs.length() > 0) && (this.selectIDs.split(",").length == 1)) {
			this.terminal = getTerminalhandle().read(Long.parseLong(this.selectIDs.split(",")[0]));
		}
		if (this.terminal.getCityId() > 0) {
			this.province = (CityHandle.read(this.terminal.getCityId()) == null ? ""
					: CityHandle.read(this.terminal.getCityId()).getProvince());
		}
		this.servletRequest.setAttribute("city_id", Integer.valueOf(this.terminal.getCityId()));
		if (this.terminal.getGroupId() > 0) {
			this.group = getGrouphandle().read(this.terminal.getGroupId());
		} else {
			this.group = new Group();
			this.group.setParentId(0);
		}
		if (this.grouppath == null) {
			this.grouppath = new ArrayList();
		} else {
			this.grouppath.clear();
		}
		Group newgroup = this.group;

		this.grouppath.add(0, this.group);
		while (newgroup.getParentId() != 0) {
			newgroup = getGrouphandle().read(newgroup.getParentId());
			this.grouppath.add(0, newgroup);
		}
		StringBuffer sql = new StringBuffer(
				"SELECT A.GROUP_ID AS GROUP_ID, A.NAME AS NAME, A.DESCP AS DESCP, A.PARENT_ID AS PARENT_ID, B.NAME AS PARENT_NAME, A.DEPTH AS DEPTH, A.SUB_NUM AS SUB_NUM, A.DEV_NUM AS DEV_NUM, A.CREATE_TIME AS CREATE_TIME, A.CREATE_USER AS CREATE_USER FROM (SELECT * FROM tb_group WHERE CSTM_ID=:CSTM_ID  AND  "
						+ (

						this.terminal.getGroupId() == 0 ? "PARENT_ID IS NULL " : "PARENT_ID=:PARENT_ID") + " ) AS A " + "LEFT JOIN tb_group AS B ON A.PARENT_ID=B.GROUP_ID");

		Map<String, Object> parameters = new HashMap();
		parameters.put("CSTM_ID", Integer.valueOf(getCstmId()));
		if (this.terminal.getGroupId() != 0) {
			parameters.put("PARENT_ID", Integer.valueOf(this.terminal.getGroupId()));
		}
		this.brother = getGrouphandle().readChildren(sql.toString(), parameters);

		return "edit";
	}

	public int selfid = 0;

	public int getSelfid() {
		return this.selfid;
	}

	public void setSelfid(int selfid) {
		this.selfid = selfid;
	}

	public void groupSelector() {
		List<Group> groups = new ArrayList();
		StringBuffer sql = new StringBuffer(
				"SELECT A.GROUP_ID AS GROUP_ID, A.NAME AS NAME, A.DESCP AS DESCP, A.PARENT_ID AS PARENT_ID, B.NAME AS PARENT_NAME, A.DEPTH AS DEPTH, A.SUB_NUM AS SUB_NUM, A.DEV_NUM AS DEV_NUM, A.CREATE_TIME AS CREATE_TIME, A.CREATE_USER AS CREATE_USER FROM (SELECT * FROM tb_group WHERE CSTM_ID=:CSTM_ID AND  "
						+ (

						this.terminal.getGroupId() == 0 ? "PARENT_ID IS NULL " : "PARENT_ID=:PARENT_ID") + " ) AS A " + "LEFT JOIN tb_group AS B ON A.PARENT_ID=B.GROUP_ID");

		Map<String, Object> parameters = new HashMap();
		parameters.put("CSTM_ID", Integer.valueOf(getCstmId()));
		if (this.terminal.getGroupId() != 0) {
			parameters.put("PARENT_ID", Integer.valueOf(this.terminal.getGroupId()));
		}
		try {
			groups = getGrouphandle().readChildren(sql.toString(), parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response(JSONSuccessString(JSONArrayToString(groups)));
	}

	public void ModifyTerminal() {
		if (StringUtils.isBlank(this.selectIDs)) {
			return;
		}
		String[] idArray = this.selectIDs.split(",");
		if ((idArray.length == 1) && (StringUtils.isBlank(this.terminal.getDeviceName()))) {
			JsonUtils.writeError(this.servletResponse, getText("name_cannot_null"), "NameCannotNull");
			return;
		}
		for (int i = 0; i < idArray.length; i++) {
			try {
				Terminal old = getTerminalhandle().read(Long.parseLong(idArray[i]));
				if (idArray.length == 1) {
					old.setDeviceName(this.terminal.getDeviceName());
					old.setDescription(this.terminal.getDescription());
				}
				old.setCityId(this.terminal.getCityId());
				old.setGroupId(this.terminal.getGroupId());
				if (getTerminalhandle().modify(old)) {
					LogHelp.userEvent(this.cstmId, this.userName, getText("editterminal"),
							getText("editTerminal") + "[" + this.terminal.getDeviceName() + "]");
				} else {
					JsonUtils.writeErrorMessage(this.servletResponse, getText("edititemfail"));
					return;
				}
			} catch (InnerException e) {
				String innerType = e.getInnerType();
				if (innerType.equals("name cannot null")) {
					JsonUtils.writeError(this.servletResponse, getText("name_cannot_null"), "NameCannotNull");
				} else {
					JsonUtils.writeErrorMessage(this.servletResponse, getText("edititemfail"));
				}
				log.error("##:[InnerException]" + e.getMessage(), e);
				return;
			} catch (Exception e) {
				JsonUtils.writeErrorMessage(this.servletResponse, getText("edititemfail"));
				log.error("##:[UnkownException]" + e.getMessage(), e);
				return;
			}
		}
		JsonUtils.writeSuccess(this.servletResponse);
	}

	public String tasktype = "";

	public String getTasktype() {
		return this.tasktype;
	}

	public void setTasktype(String tasktype) {
		this.tasktype = tasktype;
	}

	List<LoopTask> looptasklist = null;

	public List<LoopTask> getLooptasklist() {
		return this.looptasklist;
	}

	public void setLooptasklist(List<LoopTask> looptasklist) {
		this.looptasklist = looptasklist;
	}

	LoopTaskHandle looptaskhandle = null;

	public LoopTaskHandle getLoopTaskhandle() {
		return this.looptaskhandle == null ? (this.looptaskhandle = new LoopTaskHandle(this)) : this.looptaskhandle;
	}

	List<DemandTask> demandtasklist = null;

	public List<DemandTask> getDemandtasklist() {
		return this.demandtasklist;
	}

	public void setDemandtasklist(List<DemandTask> demandtasklist) {
		this.demandtasklist = demandtasklist;
	}

	DemandTaskHandle demandtaskhandle = null;

	public DemandTaskHandle getDemandTaskhandle() {
		return this.demandtaskhandle == null ? (this.demandtaskhandle = new DemandTaskHandle(this))
				: this.demandtaskhandle;
	}

	List<PluginTask> plugintasklist = null;

	public List<PluginTask> getPlugintasklist() {
		return this.plugintasklist;
	}

	public void setPlugintasklist(List<PluginTask> plugintasklist) {
		this.plugintasklist = plugintasklist;
	}

	PluginTaskHandle plugintaskhandle = null;

	public PluginTaskHandle getPluginTaskhandle() {
		return this.plugintaskhandle == null ? (this.plugintaskhandle = new PluginTaskHandle(this))
				: this.plugintaskhandle;
	}

	public String ToTerminalTask() {
		Condition<TaskColumn> condition = null;

		condition = new Condition(TaskColumn.NAME, Order.ASC);

		this.looptasklist = new ArrayList();
		Result<LoopTask> result = getLoopTaskhandle().readPage(condition);

		this.looptasklist = result.getResult();

		Condition<TaskColumn> demandcondition = null;

		demandcondition = new Condition(TaskColumn.NAME, Order.ASC);

		this.demandtasklist = new ArrayList();
		Result<DemandTask> demnandresult = getDemandTaskhandle().readPage(demandcondition);

		this.demandtasklist = demnandresult.getResult();

		Condition<TaskColumn> plugincondition = null;

		plugincondition = new Condition(TaskColumn.NAME, Order.ASC);

		this.plugintasklist = new ArrayList();
		Result<PluginTask> pluginresult = getPluginTaskhandle().readPage(plugincondition);

		this.plugintasklist = pluginresult.getResult();
		if ((this.selectIDs != null) && (this.selectIDs.length() > 0) && (this.selectIDs.split(",").length == 1)) {
			this.terminal = getTerminalhandle().read(Long.parseLong(this.selectIDs.split(",")[0]));
		}
		return "task";
	}

	public void TerminalTask() {
		MessageServer messageServer = new MessageServer();
		if ((this.selectIDs != null) && (this.selectIDs.length() > 0)) {
			if (this.selectIDs.split(",").length == 1) {
				Date strversion = null;

				Terminal old = getTerminalhandle().read(this.terminal.getDeviceId());

				String op = "";
				if (this.servletRequest.getParameter("tasktype").equals("looptask")) {
					old.setAssignLoopTaskId(this.terminal.getAssignLoopTaskId());

					op = getText("assigntask");
					strversion = ((LoopTask) getLoopTaskhandle().read(this.terminal.getAssignLoopTaskId()))
							.getTaskVersion();
				}
				if (this.servletRequest.getParameter("tasktype").equals("demandtask")) {
					old.setAssignDemandTaskId(this.terminal.getAssignDemandTaskId());

					op = getText("assigntask");
					strversion = ((DemandTask) getDemandTaskhandle().read(this.terminal.getAssignDemandTaskId()))
							.getTaskVersion();
				}
				if (this.servletRequest.getParameter("tasktype").equals("plugintask")) {
					old.setAssignPluginTaskId(this.terminal.getAssignPluginTaskId());

					op = getText("assigntask");
					strversion = ((PluginTask) getPluginTaskhandle().read(this.terminal.getAssignPluginTaskId()))
							.getTaskVersion();
				}
				if (this.servletRequest.getParameter("tasktype").equals("clearlooptask")) {
					old.setAssignLoopTaskId(0);

					op = getText("cleartask");
				}
				if (this.servletRequest.getParameter("tasktype").equals("cleardemandtask")) {
					old.setAssignDemandTaskId(0);

					op = getText("cleartask");
				}
				if (this.servletRequest.getParameter("tasktype").equals("clearplugintask")) {
					old.setAssignPluginTaskId(0);

					op = getText("cleartask");
				}
				try {
					if (getTerminalhandle().modify(old)) {
						LogHelp.userEvent(this.cstmId, this.userName, op, "[" + old.getDeviceName() + "]" + op);

						messageServer.noticeAssignChanged(old.getDeviceId());

						JsonUtils.writeSuccessData(this.servletResponse, strversion == null ? "" : strversion);
						return;
					}
					throw new Exception("modify failed");
				} catch (Exception e) {
					response(JSONErrorString());
					log.error("#[TerminalTask]#: " + e.getMessage(), e);
				}
			} else {
				String op = "";
				Date strversion = null;
				if (this.servletRequest.getParameter("tasktype").equals("looptask")) {
					strversion = ((LoopTask) getLoopTaskhandle().read(this.terminal.getAssignLoopTaskId()))
							.getTaskVersion();
				}
				if (this.servletRequest.getParameter("tasktype").equals("demandtask")) {
					strversion = ((DemandTask) getDemandTaskhandle().read(this.terminal.getAssignDemandTaskId()))
							.getTaskVersion();
				}
				if (this.servletRequest.getParameter("tasktype").equals("plugintask")) {
					strversion = ((PluginTask) getPluginTaskhandle().read(this.terminal.getAssignPluginTaskId()))
							.getTaskVersion();
				}
				for (int i = 0; i < this.selectIDs.split(",").length; i++) {
					try {
						Terminal old = getTerminalhandle().read(Long.parseLong(this.selectIDs.split(",")[i]));
						if (this.servletRequest.getParameter("tasktype").equals("looptask")) {
							old.setAssignLoopTaskId(this.terminal.getAssignLoopTaskId());

							op = getText("assigntask");
						}
						if (this.servletRequest.getParameter("tasktype").equals("demandtask")) {
							old.setAssignDemandTaskId(this.terminal.getAssignDemandTaskId());

							op = getText("assigntask");
						}
						if (this.servletRequest.getParameter("tasktype").equals("plugintask")) {
							old.setAssignPluginTaskId(this.terminal.getAssignPluginTaskId());

							op = getText("assigntask");
						}
						if (this.servletRequest.getParameter("tasktype").equals("clearlooptask")) {
							old.setAssignLoopTaskId(0);

							op = getText("cleartask");
						}
						if (this.servletRequest.getParameter("tasktype").equals("cleardemandtask")) {
							old.setAssignDemandTaskId(0);

							op = getText("cleartask");
						}
						if (this.servletRequest.getParameter("tasktype").equals("clearplugintask")) {
							old.setAssignPluginTaskId(0);

							op = getText("cleartask");
						}
						if (getTerminalhandle().modify(old)) {
							LogHelp.userEvent(this.cstmId, this.userName, op, "[" + old.getDeviceName() + "]" + op);

							messageServer.noticeAssignChanged(old.getDeviceId());
						} else {
							response(JSONErrorString());
						}
					} catch (Exception e) {
						response(JSONErrorString());
						e.printStackTrace();
					}
				}
				JsonUtils.writeSuccessData(this.servletResponse, strversion == null ? "" : strversion);
			}
		}
	}

	public String upgradetype = "";

	public String getUpgradetype() {
		return this.upgradetype;
	}

	public void setUpgradetype(String upgradetype) {
		this.upgradetype = upgradetype;
	}

	List<Config> configlist = null;

	public List<Config> getConfiglist() {
		return this.configlist;
	}

	public void setConfiglist(List<Config> configlist) {
		this.configlist = configlist;
	}

	ConfigHandle confighandle = null;

	public ConfigHandle getConfigHandle() {
		return this.confighandle == null ? (this.confighandle = new ConfigHandle(this)) : this.confighandle;
	}

	List<App> applist = null;

	public List<App> getApplist() {
		return this.applist;
	}

	public void setApplist(List<App> applist) {
		this.applist = applist;
	}

	List<Kernel> kernellist = null;

	public List<Kernel> getKernellist() {
		return this.kernellist;
	}

	public void setKernellist(List<Kernel> kernellist) {
		this.kernellist = kernellist;
	}

	public String ToTerminalUpgrade() {
		this.configlist = new ArrayList();
		this.configlist = getConfigHandle().readAll();

		this.applist = new ArrayList();
		this.applist = AppHandle.readAll();

		this.kernellist = new ArrayList();
		this.kernellist = KernelHandle.readAll();
		if ((this.selectIDs != null) && (this.selectIDs.length() > 0) && (this.selectIDs.split(",").length == 1)) {
			this.terminal = getTerminalhandle().read(Long.parseLong(this.selectIDs.split(",")[0]));
		}
		return "upgrade";
	}

	public void TerminalUpgrade() {
		MessageServer messageServer = new MessageServer();
		if ((this.selectIDs != null) && (this.selectIDs.length() > 0)) {
			if (this.selectIDs.split(",").length == 1) {
				Terminal old = getTerminalhandle().read(this.terminal.getDeviceId());

				String op = "";
				if (this.servletRequest.getParameter("upgradetype").equals("app")) {
					old.setAssignAppVersion(this.terminal.getAssignAppVersion().equals("0") ? null
							: this.terminal.getAssignAppVersion());

					op = getText("assignapp");
				}
				if (this.servletRequest.getParameter("upgradetype").equals("kel")) {
					old.setAssignKernelVersion(this.terminal.getAssignKernelVersion().equals("0") ? null
							: this.terminal.getAssignKernelVersion());

					op = getText("assignfirmware");
				}
				if (this.servletRequest.getParameter("upgradetype").equals("cfg")) {
					old.setAssignConfigId(this.terminal.getAssignConfigId());

					op = getText("assignsetting");
				}
				if (this.servletRequest.getParameter("upgradetype").equals("clearapp")) {
					old.setAssignAppVersion(null);

					op = getText("clearapp");
				}
				if (this.servletRequest.getParameter("upgradetype").equals("clearkel")) {
					old.setAssignKernelVersion(null);

					op = getText("clearkernel");
				}
				if (this.servletRequest.getParameter("upgradetype").equals("clearcfg")) {
					old.setAssignConfigId(0);

					op = getText("clearconfig");
				}
				try {
					if (getTerminalhandle().modify(old)) {
						LogHelp.userEvent(this.cstmId, this.userName, op, "[" + old.getDeviceName() + "]" + op);
						messageServer.noticeAssignChanged(old.getDeviceId());
						response(JSONSuccessString());
						return;
					}
					throw new Exception("modify failed");
				} catch (Exception e) {
					response(JSONErrorString());
					log.error("#[TerminalUpgrade]#: " + e.getMessage(), e);
				}
			} else {
				String op = "";
				for (int i = 0; i < this.selectIDs.split(",").length; i++) {
					try {
						Terminal old = getTerminalhandle().read(Long.parseLong(this.selectIDs.split(",")[i]));
						if (this.servletRequest.getParameter("upgradetype").equals("app")) {
							old.setAssignAppVersion(this.terminal.getAssignAppVersion());

							op = getText("assignapp");
						}
						if (this.servletRequest.getParameter("upgradetype").equals("kel")) {
							old.setAssignKernelVersion(this.terminal.getAssignKernelVersion());

							op = getText("assignfirmware");
						}
						if (this.servletRequest.getParameter("upgradetype").equals("cfg")) {
							old.setAssignConfigId(this.terminal.getAssignConfigId());

							op = getText("assignsetting");
						}
						if (this.servletRequest.getParameter("upgradetype").equals("clearapp")) {
							old.setAssignAppVersion(null);

							op = getText("clearapp");
						}
						if (this.servletRequest.getParameter("upgradetype").equals("clearkel")) {
							old.setAssignKernelVersion(null);

							op = getText("clearkernel");
						}
						if (this.servletRequest.getParameter("upgradetype").equals("clearcfg")) {
							old.setAssignConfigId(0);

							op = getText("clearconfig");
						}
						if (getTerminalhandle().modify(old)) {
							LogHelp.userEvent(this.cstmId, this.userName, op, "[" + old.getDeviceName() + "]" + op);
							messageServer.noticeAssignChanged(old.getDeviceId());
						} else {
							response(JSONErrorString());
						}
					} catch (Exception e) {
						response(JSONErrorString());
						e.printStackTrace();
					}
				}
				response(JSONSuccessString());
			}
		}
	}

	public String ToSubtitle() {
		if ((this.selectIDs != null) && (this.selectIDs.length() > 0) && (this.selectIDs.split(",").length == 1)) {
			this.terminal = getTerminalhandle().read(Long.parseLong(this.selectIDs.split(",")[0]));
		}
		return "subtitle";
	}

	public void Subtitle() {
		MessageServer messageServer = new MessageServer();
		if ((this.selectIDs != null) && (this.selectIDs.length() > 0)) {
			if (this.selectIDs.split(",").length == 1) {
				Terminal old = getTerminalhandle().read(this.terminal.getDeviceId());
				old.setSubtitle(this.terminal.getSubtitle());
				old.setSubtitleVersion(new Date());
				try {
					if (getTerminalhandle().modify(old)) {
						LogHelp.userEvent(this.cstmId, this.userName, getText("subtitle"),
								"[" + old.getDeviceName() + "]" + getText("subtitle"));
						messageServer.noticeAssignChanged(old.getDeviceId());
						response(JSONSuccessString());
						return;
					}
					throw new Exception("modify failed");
				} catch (Exception e) {
					response(JSONErrorString());
					log.error("#[Subtite]#: " + e.getMessage(), e);
				}
			} else {
				for (int i = 0; i < this.selectIDs.split(",").length; i++) {
					try {
						Terminal old = getTerminalhandle().read(Long.parseLong(this.selectIDs.split(",")[i]));
						old.setSubtitle(this.terminal.getSubtitle());
						old.setSubtitleVersion(new Date());
						if (getTerminalhandle().modify(old)) {
							LogHelp.userEvent(this.cstmId, this.userName, getText("subtitle"),
									"[" + old.getDeviceName() + "]" + getText("subtitle"));
							messageServer.noticeAssignChanged(old.getDeviceId());
						} else {
							response(JSONErrorString());
						}
					} catch (Exception e) {
						response(JSONErrorString());
						e.printStackTrace();
					}
				}
				response(JSONSuccessString());
			}
		}
	}

	public String ToMonitor() {
		if ((this.selectIDs != null) && (this.selectIDs.length() > 0) && (this.selectIDs.split(",").length == 1)) {
			this.terminal = getTerminalhandle().read(Long.parseLong(this.selectIDs.split(",")[0]));
			if ((this.terminal.getRestSchedule() != null) && (this.terminal.getRestSchedule() != "")
					&& (this.terminal.getRestSchedule().length() > 1)) {
				dom4jMethod(this.terminal.getRestSchedule(), "reset");
			}
			if ((this.terminal.getDemandSchedule() != null) && (this.terminal.getDemandSchedule() != "")
					&& (this.terminal.getDemandSchedule().length() > 1)) {
				dom4jMethod(this.terminal.getDemandSchedule(), "demand");
			}
			if ((this.terminal.getCaptureSchedule() != null) && (this.terminal.getCaptureSchedule() != "")
					&& (this.terminal.getCaptureSchedule().length() > 1)) {
				dom4jMethod(this.terminal.getCaptureSchedule(), "capture");
			}
			if ((this.terminal.getStandBySchedule() != null) && (this.terminal.getStandBySchedule() != "")
					&& (this.terminal.getStandBySchedule().length() > 1)) {
				dom4jMethod(this.terminal.getStandBySchedule(), "standby");
			}
		}
		if ((this.KeyValue == null) || (this.KeyValue == "")) {
			this.KeyValue = "00";
		}
		return "monitor";
	}

	public void dom4jMethod(String xml, String type) {
		SAXReader sr = new SAXReader();

		Document doc = null;
		try {
			doc = sr.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Element root = doc.getRootElement();

		Iterator<Element> ir = root.elementIterator();
		while (ir.hasNext()) {
			Element element = (Element) ir.next();
			if (element.getName().equals("date")) {
				if (type == "reset") {
					this.ResetStartDate = element.attributeValue("start");
					this.ResetStopDate = element.attributeValue("stop");
				}
				if (type == "demand") {
					this.DemandStartDate = element.attributeValue("start");
					this.DemandStopDate = element.attributeValue("stop");
				}
				if (type == "capture") {
					this.CaptureStartDate = element.attributeValue("start");
					this.CaptureStopDate = element.attributeValue("stop");
				}
				if (type == "standby") {
					this.StandStartDate = element.attributeValue("start");
					this.StandStopDate = element.attributeValue("stop");
				}
				Iterator<Element> ir1 = element.elementIterator();
				while (ir1.hasNext()) {
					Element time = (Element) ir1.next();
					if (time.getName().equals("time")) {
						if (type == "reset") {
							this.ResetStartTime = time.attributeValue("value");
						}
						if (type == "demand") {
							this.DemandStartTime = time.attributeValue("value");

							this.KeyValue = time.attributeValue("key");
						}
						if (type == "capture") {
							this.CaptureStartTime = time.attributeValue("value");
						}
						if (type == "standby") {
							this.StandStartTime = time.attributeValue("start");
							this.StandStopTime = time.attributeValue("stop");
						}
					}
				}
			}
		}
	}

	public String monitortype = "";

	public String getMonitortype() {
		return this.monitortype;
	}

	public void setMonitortype(String monitortype) {
		this.monitortype = monitortype;
	}

	public void Monitor() {
		String strReset = "<reset><date start=\"" + this.ResetStartDate + "\" stop=\"" + this.ResetStopDate + "\">"
				+ "<time value=\"" + this.ResetStartTime + "\"/>" + "</date></reset>";

		String strCapture = "<capture><date start=\"" + this.CaptureStartDate + "\" stop=\"" + this.CaptureStopDate
				+ "\">" + "<time value=\"" + this.CaptureStartTime + "\"/>" + "</date></capture>";

		String strStandby = "<standby><date start=\"" + this.StandStartDate + "\" stop=\"" + this.StandStopDate + "\">"
				+ "<time start=\"" + this.StandStartTime + "\" stop=\"" + this.StandStopTime + "\"/>"
				+ "</date></standby>";

		String strDemand = "<demand><date start=\"" + this.DemandStartDate + "\" stop=\"" + this.DemandStopDate + "\">"
				+ "<time value=\"" + this.DemandStartTime + "\" key=\"" + this.KeyValue + "\"/>" + "</date></demand>";

		MessageServer messageServer = new MessageServer();
		if ((this.selectIDs != null) && (this.selectIDs.length() > 0)) {
			if (this.selectIDs.split(",").length == 1) {
				Terminal old = getTerminalhandle().read(this.terminal.getDeviceId());

				String op = "";
				if (this.servletRequest.getParameter("monitortype").equals("reset")) {
					old.setRestSchedule(strReset);
					op = "Reboot";
				}
				if (this.servletRequest.getParameter("monitortype").equals("capture")) {
					old.setCaptureSchedule(strCapture);
					op = "Screenshot";
				}
				if (this.servletRequest.getParameter("monitortype").equals("standby")) {
					old.setStandBySchedule(strStandby);

					op = "StandBy";
				}
				if (this.servletRequest.getParameter("monitortype").equals("demand")) {
					old.setDemandSchedule(strDemand);
					op = "Demand";
				}
				if (this.servletRequest.getParameter("monitortype").equals("clearreset")) {
					old.setRestSchedule("");
				}
				if (this.servletRequest.getParameter("monitortype").equals("clearcapture")) {
					old.setCaptureSchedule("");
				}
				if (this.servletRequest.getParameter("monitortype").equals("clearstandby")) {
					old.setStandBySchedule("");
				}
				if (this.servletRequest.getParameter("monitortype").equals("cleardemand")) {
					old.setDemandSchedule("");
				}
				try {
					if (getTerminalhandle().modify(old)) {
						LogHelp.userEvent(this.cstmId, this.userName, getText("terminalmonitor"),
								"[" + old.getDeviceName() + "]" + op);

						messageServer.noticeAssignChanged(old.getDeviceId());

						response(JSONSuccessString());
						return;
					}
					throw new Exception("modify failed");
				} catch (Exception e) {
					response(JSONErrorString());
					log.error("#[Monitor]#: " + e.getMessage(), e);
				}
			} else {
				String op = "";
				for (int i = 0; i < this.selectIDs.split(",").length; i++) {
					try {
						Terminal old = getTerminalhandle().read(Long.parseLong(this.selectIDs.split(",")[i]));
						if (this.servletRequest.getParameter("monitortype").equals("reset")) {
							old.setRestSchedule(strReset);
							op = "Reboot";
						}
						if (this.servletRequest.getParameter("monitortype").equals("capture")) {
							old.setCaptureSchedule(strCapture);
							op = "Screenshot";
						}
						if (this.servletRequest.getParameter("monitortype").equals("standby")) {
							old.setStandBySchedule(strStandby);
							op = "StandBy";
						}
						if (this.servletRequest.getParameter("monitortype").equals("demand")) {
							old.setDemandSchedule(strDemand);
							op = "Demand";
						}
						if (this.servletRequest.getParameter("monitortype").equals("clearreset")) {
							old.setRestSchedule("");
						}
						if (this.servletRequest.getParameter("monitortype").equals("clearcapture")) {
							old.setCaptureSchedule("");
						}
						if (this.servletRequest.getParameter("monitortype").equals("clearstandby")) {
							old.setStandBySchedule("");
						}
						if (this.servletRequest.getParameter("monitortype").equals("cleardemand")) {
							old.setDemandSchedule("");
						}
						if (getTerminalhandle().modify(old)) {
							LogHelp.userEvent(this.cstmId, this.userName, getText("terminalmonitor"),
									"[" + old.getDeviceName() + "]" + op);
							messageServer.noticeAssignChanged(old.getDeviceId());
						} else {
							response(JSONErrorString());
						}
					} catch (Exception e) {
						response(JSONErrorString());
						e.printStackTrace();
					}
				}
				response(JSONSuccessString());
			}
		}
	}

	public String ToExp() {
		return "exp";
	}

	public boolean checkhour = false;
	public String begindate = "";
	public String enddate = "";

	public boolean isCheckhour() {
		return this.checkhour;
	}

	public void setCheckhour(boolean checkhour) {
		this.checkhour = checkhour;
	}

	public String getBegindate() {
		return this.begindate;
	}

	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}

	public String getEnddate() {
		return this.enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public void exportExp() {
		try {
			List<ExcelTitleVo> titleList = new ArrayList();
			titleList.add(new ExcelTitleVo(getText("period"), 35));
			titleList.add(new ExcelTitleVo(getText("terminalid"), 20));
			titleList.add(new ExcelTitleVo(getText("terminalname"), 35));
			titleList.add(new ExcelTitleVo(getText("text_terminalexp_playback"), 20));
			titleList.add(new ExcelTitleVo(getText("text_terminalexp_rtc"), 20));
			titleList.add(new ExcelTitleVo(getText("text_terminalexp_reboot"), 20));
			titleList.add(new ExcelTitleVo(getText("text_terminalexp_shutdown"), 20));

			String sql = exceptionStatisticSql();
			List<TerminalExp> terminalTemp = getTerminalExceptionHandle().readAllExp(sql, null);
			List<ExcelTerminalExpVo> expvos = new ArrayList();
			for (TerminalExp t : terminalTemp) {
				expvos.add(new ExcelTerminalExpVo().convert(t, this.checkhour));
			}
			exportExcel(getText("action_anomalyanalysis"), expvos, titleList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int resetcount = 1;

	public int getResetcount() {
		return this.resetcount;
	}

	public void setResetcount(int resetcount) {
		this.resetcount = resetcount;
	}

	public void explist() {
		try {
			String sql = exceptionStatisticSql();

			this.terminalexp = getTerminalExceptionHandle().readAllExp(sql, null);
			this.page.setTotalRows(this.terminalexp != null ? this.terminalexp.size() : 0);
			if ((this.page.getPageSize() > 0) && (this.page.getCurrentPage() > 0)) {
				int fromIndex = (this.page.getCurrentPage() - 1) * this.page.getPageSize();
				int toIndex = fromIndex + this.page.getPageSize();
				if (fromIndex >= this.terminalexp.size()) {
					this.terminalexp.clear();
				} else {
					if (toIndex > this.terminalexp.size()) {
						toIndex = this.terminalexp.size();
					}
					this.terminalexp = this.terminalexp.subList(fromIndex, toIndex);
				}
			}
			response(
					JSONSuccessString(JSONArrayToString(this.terminalexp), new MapTool().putObject("page", this.page)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String exceptionStatisticSql() {
		String sql = "";

		String filter = "";
		if ((this.selectIDs != null) && (this.selectIDs.length() > 1)) {
			filter = filter + " AND DEV_ID IN (" + this.selectIDs + ") ";
		}
		if ((this.begindate != null) && (this.enddate != null)
				&& ((this.begindate = this.begindate.trim()).matches("\\d{4}\\-\\d{2}-\\d{2}"))
				&& ((this.enddate = this.enddate.trim()).matches("\\d{4}\\-\\d{2}-\\d{2}"))) {
			filter = filter + " AND DATE(DATE_TIME)>='" + this.begindate + "' " + "AND DATE(DATE_TIME)<='"
					+ this.enddate + "' ";
		}
		String timeFiled = !this.checkhour ? "DATE(DATE_TIME)" : "DATE_FORMAT(DATE_TIME,'%Y-%m-%d %H:00:00')";

		String tt = "(SELECT DEV_ID, DATE_TIME FROM (SELECT DEV_ID, " + timeFiled + " AS DATE_TIME FROM tb_exception "
				+ "WHERE EXCEPTION = 1 or EXCEPTION = 2 or EXCEPTION = 3 " + filter + "UNION " + "SELECT DEV_ID, "
				+ timeFiled + " AS DATE_TIME FROM tb_terminal_event " + "WHERE EVENT = 1 " + filter + ") AS T "
				+ "GROUP BY DEV_ID, DATE_TIME)";
		String tm = "(SELECT DEV_ID, DATE_TIME, COUNT(TIME_EXP) AS TIME_EXP FROM (SELECT DEV_ID, " + timeFiled
				+ " AS DATE_TIME, 1 AS TIME_EXP FROM tb_exception " + "WHERE EXCEPTION = 2 " + filter + ") AS T "
				+ "GROUP BY DEV_ID, DATE_TIME)";
		String tp = "(SELECT DEV_ID, DATE_TIME, COUNT(PLAY_EXP) AS PLAY_EXP FROM (SELECT DEV_ID, " + timeFiled
				+ " AS DATE_TIME, 1 AS PLAY_EXP FROM tb_exception " + "WHERE EXCEPTION = 3 " + filter + ") AS T "
				+ "GROUP BY DEV_ID, DATE_TIME)";
		String tw = " (SELECT DEV_ID, DATE_TIME, COUNT(POWER_EXP) AS POWER_EXP FROM (SELECT DEV_ID, " + timeFiled
				+ " AS DATE_TIME, 1 AS POWER_EXP FROM tb_exception " + "WHERE EXCEPTION = 1 " + filter + ") AS T "
				+ "GROUP BY DEV_ID, DATE_TIME)";
		String tr = "(SELECT DEV_ID, DATE_TIME, COUNT(RESET_EXP) AS RESET_EXP FROM (SELECT DEV_ID, " + timeFiled
				+ " AS DATE_TIME, 1 AS RESET_EXP FROM tb_terminal_event " + "WHERE EVENT = 1 " + filter + ") AS T "
				+ "GROUP BY DEV_ID, DATE_TIME)";
		String tbt = "tb_terminal";

		sql = " SELECT tt.DEV_ID AS DEV_ID, tbt.NAME AS NAME, tt.DATE_TIME AS DATE_TIME, TIME_EXP, PLAY_EXP, POWER_EXP, RESET_EXP FROM "
				+ tt + " AS tt  " + "LEFT JOIN " + tm + " AS tm ON (tt.DEV_ID=tm.DEV_ID AND tt.DATE_TIME=tm.DATE_TIME) "
				+ "LEFT JOIN " + tp + " AS tp ON (tt.DEV_ID=tp.DEV_ID AND tt.DATE_TIME=tp.DATE_TIME) " + "LEFT JOIN "
				+ tw + " AS tw ON (tt.DEV_ID=tw.DEV_ID AND tt.DATE_TIME=tw.DATE_TIME) " + "LEFT JOIN " + tr
				+ " AS tr ON (tt.DEV_ID=tr.DEV_ID AND tt.DATE_TIME=tr.DATE_TIME) " + "LEFT JOIN " + tbt
				+ " AS tbt ON tbt.DEV_ID=tt.DEV_ID ";

		sql = sql + "WHERE reset_exp>=" + (

		this.resetcount < 3 ? 3 : this.resetcount) + " ";

		return sql;
	}

	public String ToEvent() {
		this.terminal = getTerminalhandle().read(this.terminal.getDeviceId());

		return "event";
	}

	public void eventlist() {
		String sql = "SELECT * FROM tb_terminal_event WHERE 1=1 ";
		try {
			sql = sql + " AND DEV_ID =" + Long.valueOf(this.terminal.getDeviceId());
		} catch (Exception e) {
			sql = sql + " ";
		}
		sql = sql + " ORDER BY DATE_TIME DESC " + (this.page.getPageSize() != -1
				? "LIMIT " + (this.page.getCurrentPage() - 1) * this.page.getPageSize() + "," + this.page.getPageSize()
				: "");

		int total = getTerminalEventHandle().readAll(
				"SELECT * FROM tb_terminal_event WHERE 1=1  AND DEV_ID =" + Long.valueOf(this.terminal.getDeviceId()),
				null).size();

		this.page.setTotalRows(total);

		this.terminalevent = getTerminalEventHandle().readAll(sql, null);
		this.terminal = getTerminalhandle().read(this.terminal.getDeviceId());

		response(JSONSuccessString(JSONArrayToString(this.terminalevent), new MapTool().putObject("page", this.page)));
	}

	public String ToReport() {
		return "report";
	}

	public void reportselect() {
		List<ExcelTitleVo> titleList = new ArrayList();
		titleList.add(new ExcelTitleVo(getText("terminalid"), 20));
		titleList.add(new ExcelTitleVo(getText("terminalname"), 35));
		titleList.add(new ExcelTitleVo(getText("text_terminalreport_loop"), 20));
		titleList.add(new ExcelTitleVo(getText("text_terminalreport_demand"), 20));
		titleList.add(new ExcelTitleVo(getText("text_terminalreport_insert"), 20));
		titleList.add(new ExcelTitleVo(getText("text_terminalreport_software"), 20));
		titleList.add(new ExcelTitleVo(getText("text_terminalreport_firmware"), 20));
		titleList.add(new ExcelTitleVo(getText("text_terminalreport_config"), 20));
		titleList.add(new ExcelTitleVo(getText("text_terminalreport_download"), 20));
		titleList.add(new ExcelTitleVo(getText("text_terminalreport_recentcheck"), 20));
		titleList.add(new ExcelTitleVo(getText("pipe"), 20));
		titleList.add(new ExcelTitleVo(getText("text_terminalreport_network"), 20));
		try {
			String sql = "SELECT * FROM vw_terminal_detail WHERE 1=1 ";
			try {
				sql = sql + " AND DEV_ID IN( " + this.selectIDs + ") ";
			} catch (Exception e) {
				sql = sql + " ";
			}
			List<Terminal> terminalTemp = getTerminalhandle().readChildren(sql, null);
			List<ExcelTerminalVo> terminalListTemp = new ArrayList();
			for (Terminal t : terminalTemp) {
				terminalListTemp.add(convertTerminalVo(t));
			}
			exportExcel(getText("text_terminalreport_title"), terminalListTemp, titleList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ExcelTerminalVo convertTerminalVo(Terminal terminal) {
		ExcelTerminalVo vo = new ExcelTerminalVo();
		String tmp = "";
		String DeviceIDs = Long.toHexString(terminal.getDeviceId()).toUpperCase();
		if (DeviceIDs.length() < 12) {
			for (int n = 0; n < 12 - DeviceIDs.length(); n++) {
				tmp = tmp + "0";
			}
			DeviceIDs = tmp + DeviceIDs;
		}
		vo.setDeviceId(DeviceIDs);
		vo.setDeviceName(terminal.getDeviceName());
		vo.setCurLoopTaskName(terminal.getCurLoopTaskName());
		vo.setCurDemandTaskName(terminal.getCurDemandTaskName());
		vo.setCurPluginTaskName(terminal.getCurPluginTaskName());
		vo.setAppUpdated(terminal.getAppUpdated() == 1 ? getText("synchronized") : getText("unsynchronized"));
		vo.setKernelUpdated(terminal.getKernelUpdated() == 1 ? getText("synchronized") : getText("unsynchronized"));
		vo.setConfigUpdated(terminal.getConfigUpdated() == 1 ? getText("synchronized") : getText("unsynchronized"));
		vo.setLastDownload(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(terminal.getLastDownload()));
		vo.setLastPatrol(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(terminal.getLastPatrol()));
		vo.setAliveinterval(terminal.getAliveInterval() == 1 ? getText("normal") : getText("abnormal"));
		vo.setNetworkState(terminal.getOnlineState() == 1 ? getText("online") : getText("offline"));
		return vo;
	}

	public void reportlist() {
		String sql = "SELECT * FROM vw_terminal_detail WHERE 1=1 ";
		try {
			sql = sql + " AND DEV_ID IN( " + this.selectIDs + ") ";
		} catch (Exception e) {
			sql = sql + " ";
		}
		sql =

				sql + (this.page.getPageSize() != -1 ? "LIMIT "
						+ (this.page.getCurrentPage() - 1) * this.page.getPageSize() + "," + this.page.getPageSize()
						: "");

		int total = getTerminalhandle()
				.readChildren("SELECT * FROM vw_terminal_detail WHERE 1=1 AND DEV_ID IN( " + this.selectIDs + ")", null)
				.size();

		this.page.setTotalRows(total);

		this.terminalreport = getTerminalhandle().readChildren(sql, null);

		response(JSONSuccessString(JSONArrayToString(this.terminalreport), new MapTool().putObject("page", this.page)));
	}

	public void standby() {
		String strStandby = "<standby><date start=\"1970-01-01\" stop=\"2038-01-01\"><time start=\"00:00:00\" stop=\"23:59:59\"/></date></standby>";
		try {
			MessageServer messageServer = new MessageServer();
			if ((this.selectIDs != null) && (this.selectIDs.split(",").length > 0)) {
				for (int i = 0; i < this.selectIDs.split(",").length; i++) {
					this.terminal = getTerminalhandle().read(Long.parseLong(this.selectIDs.split(",")[i]));
					this.terminal.setStandBySchedule(strStandby);
					if (getTerminalhandle().modify(this.terminal)) {
						messageServer.noticeAssignChanged(this.terminal.getDeviceId());
						LogHelp.userEvent(this.cstmId, this.userName, getText("terminalmonitor"),
								"[" + this.terminal.getDeviceName() + "]" + "StandBy");
					}
				}
			}
			JsonUtils.writeSuccess(this.servletResponse);
		} catch (Exception localException) {
		}
	}

	public void wakeup() {
		try {
			MessageServer messageServer = new MessageServer();
			if ((this.selectIDs != null) && (this.selectIDs.split(",").length > 0)) {
				for (int i = 0; i < this.selectIDs.split(",").length; i++) {
					this.terminal = getTerminalhandle().read(Long.parseLong(this.selectIDs.split(",")[i]));
					this.terminal.setStandBySchedule("");
					if (getTerminalhandle().modify(this.terminal)) {
						messageServer.noticeAssignChanged(this.terminal.getDeviceId());
						LogHelp.userEvent(this.cstmId, this.userName, getText("terminalmonitor"),
								"[" + this.terminal.getDeviceName() + "]" + "WakeUp");
					}
				}
			}
			JsonUtils.writeSuccess(this.servletResponse);
		} catch (Exception localException) {
		}
	}

	public void reboot() {
		try {
			MessageServer messageServer = new MessageServer();
			if ((this.selectIDs != null) && (this.selectIDs.split(",").length > 0)) {
				for (int i = 0; i < this.selectIDs.split(",").length; i++) {
					messageServer.reboot(Long.parseLong(this.selectIDs.split(",")[i]));

					this.terminal = getTerminalhandle().read(Long.parseLong(this.selectIDs.split(",")[i]));

					LogHelp.userEvent(this.cstmId, this.userName, getText("scheduled"),
							"[" + this.terminal.getDeviceName() + "]" + "Reboot");
				}
			}
			JsonUtils.writeSuccess(this.servletResponse);
		} catch (Exception localException) {
		}
	}

	public void clear() {
		try {
			MessageServer messageServer = new MessageServer();
			if ((this.selectIDs != null) && (this.selectIDs.split(",").length > 0)) {
				for (int i = 0; i < this.selectIDs.split(",").length; i++) {
					messageServer.clearContents(Long.parseLong(this.selectIDs.split(",")[i]));

					this.terminal = getTerminalhandle().read(Long.parseLong(this.selectIDs.split(",")[i]));

					LogHelp.userEvent(this.cstmId, this.userName, getText("scheduled"),
							"[" + this.terminal.getDeviceName() + "]" + "Reboot");
				}
			}
			JsonUtils.writeSuccess(this.servletResponse);
		} catch (Exception localException) {
		}
	}

	public void screenshot() {
		try {
			String[] idArray = this.selectIDs != null ? this.selectIDs.split(",") : null;
			if ((idArray != null) && (idArray.length > 0)) {
				MessageServer messageServer = new MessageServer();
				for (int i = 0; i < idArray.length; i++) {
					Terminal dev = getTerminalhandle().read(Long.parseLong(idArray[i]));
					try {
						File baseDir = new File(Storage.getDeviceScreenDirctory(dev.getDeviceId()));
						if (baseDir.exists()) {
							File[] children = baseDir.listFiles();
							if ((children != null) && (children.length > 0)) {
								for (File f : children) {
									if (f.isFile()) {
										f.delete();
									}
								}
							}
						}
					} catch (Exception localException1) {
					}
					messageServer.captureScreen(dev.getDeviceId());
					LogHelp.userEvent(this.cstmId, this.userName, getText("scheduled"),
							"[" + dev.getDeviceName() + "]" + "Screenshot");
				}
			}
			JsonUtils.writeSuccess(this.servletResponse);
		} catch (Exception ex) {
			JsonUtils.writeError(this.servletResponse);
			log.error("#screenshot#: " + ex.getMessage(), ex);
		}
	}

	public String num = "0";

	public String getNum() {
		return this.num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public void demand() {
		try {
			MessageServer messageServer = new MessageServer();
			int n = Integer.parseInt(this.num);
			if ((this.selectIDs != null) && (this.selectIDs.split(",").length > 0)) {
				for (int i = 0; i < this.selectIDs.split(",").length; i++) {
					messageServer.demandProgram(Long.parseLong(this.selectIDs.split(",")[i]), n);

					this.terminal = getTerminalhandle().read(Long.parseLong(this.selectIDs.split(",")[i]));

					LogHelp.userEvent(this.cstmId, this.userName, getText("scheduled"),
							"[" + this.terminal.getDeviceName() + "]" + "Demand");
				}
			}
			JsonUtils.writeSuccess(this.servletResponse);
		} catch (Exception localException) {
		}
	}

	public void removeevent() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = sdf.parse(this.begindate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			getTerminalEventHandle().remove(this.terminal.getDeviceId(), date);

			this.terminal = getTerminalhandle().read(this.terminal.getDeviceId());

			LogHelp.userEvent(this.cstmId, this.userName, getText("deleteterminalevent"),
					"[" + this.terminal.getDeviceName() + "]" + getText("deleteterminalevent"));

			response(JSONSuccessString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String msg = "";

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void DeleteTerminal() {
		int succ = 0;
		int fail = 0;
		if ((this.selectIDs != null) && (this.selectIDs.length() > 0)) {
			for (int i = 0; i < this.selectIDs.split(",").length; i++) {
				try {
					String n = getTerminalhandle().read(Long.parseLong(this.selectIDs.split(",")[i])).getDeviceName();
					if (getTerminalhandle().remove(Long.parseLong(this.selectIDs.split(",")[i]))) {
						LogHelp.userEvent(this.cstmId, this.userName, getText("deleteterminal"),
								getText("deleteterminal") + "[" + n + "]");

						succ++;
					} else {
						fail++;
					}
				} catch (Exception e) {
					fail++;
					JsonUtils.writeErrorData(this.servletResponse, getText("deletefailed"));
				}
			}
		}
		response(JSONSuccessString());
	}

	public List<String> provincelist = null;

	public List<String> getProvincelist() {
		return this.provincelist;
	}

	public void setProvincelist(List<String> provincelist) {
		this.provincelist = provincelist;
	}

	public void QuerCityByProvince() {
		List<City> city = CityHandle.readAll(this.province);
		String returnStr = "";
		if ((city == null) || (city.size() <= 0)) {
			returnStr = "[{\"cityId\":0,\"cityName\":\"" + getText("select") + "\",\"province\":\"" + getText("select")
					+ "\"}]";
		} else {
			returnStr = JSONArrayToString(city);
		}
		response(JSONSuccessString(returnStr));
	}

	public String GroupName = getText("root");

	public String getGroupName() {
		return this.GroupName;
	}

	public void setGroupName(String groupName) {
		this.GroupName = groupName;
	}

	public String ToGroupCity() {
		if (this.terminalparam.getGroupId() > 0) {
			this.GroupName = getGrouphandle().read(this.terminalparam.getGroupId()).getGroupName();
		}
		this.provincelist = CityHandle.readAllProvinces();

		return "groupcity";
	}

	public String ToGroupSubtitle() {
		if (this.terminalparam.getGroupId() > 0) {
			this.GroupName = getGrouphandle().read(this.terminalparam.getGroupId()).getGroupName();
		}
		return "groupsubtitle";
	}

	public String groupsubtitle = "";

	public String getGroupsubtitle() {
		return this.groupsubtitle;
	}

	public void setGroupsubtitle(String groupsubtitle) {
		this.groupsubtitle = groupsubtitle;
	}

	public void setGroupSubtitle() {
		MessageServer messageServer = new MessageServer();
		List<Terminal> list = new ArrayList();

		Map<String, Object> param = new HashMap();

		param.put("CSTM_ID", Integer.valueOf(getCstmId()));
		if (this.terminalparam.getGroupId() != 0) {
			param.put("GROUP_ID", Integer.valueOf(this.terminalparam.getGroupId()));
		}
		list = getTerminalhandle().readAll(querysql(), param);
		if ((list != null) && (list.size() > 0)) {
			for (int i = 0; i < list.size(); i++) {
				Terminal t = getTerminalhandle().read(((Terminal) list.get(i)).getDeviceId());
				t.setSubtitle(this.terminal.getSubtitle());
				t.setSubtitleVersion(new Date());
				try {
					getTerminalhandle().modify(t);

					messageServer.noticeAssignChanged(t.getDeviceId());

					LogHelp.userEvent(this.cstmId, this.userName, getText("groupsubtitle"),
							getText("groupsubtitle") + "[" + t.getGroupName() + "]");
				} catch (Exception e) {
					log.error("#[setGroupSubtitle]#: " + e.getMessage(), e);
				}
			}
		}
		response(JSONSuccessString());
	}

	public void setGroupCity() {
		List<Terminal> list = new ArrayList();

		Map<String, Object> param = new HashMap();

		param.put("CSTM_ID", Integer.valueOf(getCstmId()));
		if (this.terminalparam.getGroupId() != 0) {
			param.put("GROUP_ID", Integer.valueOf(this.terminalparam.getGroupId()));
		}
		list = getTerminalhandle().readAll(querysql(), param);
		if ((list != null) && (list.size() > 0)) {
			for (int i = 0; i < list.size(); i++) {
				Terminal t = getTerminalhandle().read(((Terminal) list.get(i)).getDeviceId());
				t.setCityId(this.terminal.getCityId());
				try {
					getTerminalhandle().modify(t);
					LogHelp.userEvent(this.cstmId, this.userName, getText("groupcity"),
							getText("groupcity") + "[" + t.getGroupName() + "]");
				} catch (Exception e) {
					log.error("#[setGroupCity]#: " + e.getMessage(), e);
				}
			}
		}
		response(JSONSuccessString());
	}

	private String querysql() {
		String queryTable = "SELECT * FROM vw_terminal_detail WHERE CSTM_ID=:CSTM_ID "
				+ (this.terminalparam.getGroupId() == 0 ? ""
						: " AND GROUP_ID IN(SELECT :GROUP_ID AS GROUP_ID UNION SELECT GROUP_ID FROM tb_group_tree WHERE PARENT_ID=:GROUP_ID) ");

		String query = "SELECT f.CSTM_ID AS CSTM_ID, DEV_ID, f.NAME AS NAME, f.DESCP AS DESCP, f.GROUP_ID AS GROUP_ID, c.NAME AS GROUP_NAME, f.CREATE_TIME AS CREATE_TIME, f.CREATE_USER AS CREATE_USER, CITY_ID, CITY_NAME, ASSIGN_KERNEL, ASSIGN_APP, CUR_KERNEL, CUR_APP, ASSIGN_CONFIG_ID, ASSIGN_CONFIG_NAME, ASSIGN_CONFIG_VERSION, CUR_CONFIG_ID, CUR_CONFIG_NAME, CUR_CONFIG_VERSION, ASSIGN_LOOPTASK_ID, ASSIGN_LOOPTASK_NAME, ASSIGN_LOOPTASK_VERSION, ASSIGN_LOOPFILE_VERSION, ASSIGN_LOOPPLAY_VERSION, ASSIGN_LOOPSTRATEGY_VERSION, CUR_LOOPTASK_ID, CUR_LOOPTASK_NAME, CUR_LOOPTASK_VERSION, CUR_LOOPFILE_VERSION, CUR_LOOPPLAY_VERSION, CUR_LOOPSTRATEGY_VERSION, ASSIGN_DEMANDTASK_ID, ASSIGN_DEMANDTASK_NAME, ASSIGN_DEMANDTASK_VERSION, ASSIGN_DEMANDFILE_VERSION, ASSIGN_DEMANDPLAY_VERSION, CUR_DEMANDTASK_ID, CUR_DEMANDTASK_NAME, CUR_DEMANDTASK_VERSION, CUR_DEMANDFILE_VERSION, CUR_DEMANDPLAY_VERSION, ASSIGN_PLUGINTASK_ID, ASSIGN_PLUGINTASK_NAME, ASSIGN_PLUGINTASK_VERSION, ASSIGN_PLUGINFILE_VERSION, ASSIGN_PLUGINPLAY_VERSION, CUR_PLUGINTASK_ID, CUR_PLUGINTASK_NAME, CUR_PLUGINTASK_VERSION, CUR_PLUGINFILE_VERSION, CUR_PLUGINPLAY_VERSION, REST_SCHEDULE, STANDBY_SCHEDULE, CAPTURE_SCHEDULE, DEMAND_SCHEDULE, SCHEDULE_VERSION, SUBTITLE, SUBTITLE_VERSION, CUR_SUBTITLE_VERSION, ONLINE_STATE, LOGON_TIME, LOGOFF_TIME, DOWNLOAD_TOTAL, DOWNLOAD_FINISHED, DOWNLOAD_TYPE, POWERON, POWEROFF, ALIVE_INTERVAL, LAST_ALIVE, LAST_PATROL, LAST_DOWNLOAD,  KERNEL_UPDATED, APP_UPDATED, CONFIG_UPDATED, LOOPTASK_UPDATED, DEMANDTASK_UPDATED, PLUGINTASK_UPDATED, SUBTITLE_UPDATED FROM ("
				+

				queryTable + ") as f " + " LEFT JOIN tb_group as c on f.CSTM_ID=c.CSTM_ID AND f.GROUP_ID=c.GROUP_ID ";

		return query;
	}

	public String province = "";

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Terminal getModel() {
		return this.terminal;
	}

	public String ResetStartDate = "";
	public String ResetStopDate = "";
	public String ResetStartTime = "";
	public String CaptureStartDate = "";
	public String CaptureStopDate = "";
	public String CaptureStartTime = "";
	public String StandStartDate = "";
	public String StandStopDate = "";
	public String StandStartTime = "";
	public String StandStopTime = "";
	public String DemandStartDate = "";
	public String DemandStopDate = "";
	public String DemandStartTime = "";

	public String getResetStartDate() {
		return this.ResetStartDate;
	}

	public void setResetStartDate(String resetStartDate) {
		this.ResetStartDate = resetStartDate;
	}

	public String getResetStopDate() {
		return this.ResetStopDate;
	}

	public void setResetStopDate(String resetStopDate) {
		this.ResetStopDate = resetStopDate;
	}

	public String getResetStartTime() {
		return this.ResetStartTime;
	}

	public void setResetStartTime(String resetStartTime) {
		this.ResetStartTime = resetStartTime;
	}

	public String getCaptureStartDate() {
		return this.CaptureStartDate;
	}

	public void setCaptureStartDate(String captureStartDate) {
		this.CaptureStartDate = captureStartDate;
	}

	public String getCaptureStopDate() {
		return this.CaptureStopDate;
	}

	public void setCaptureStopDate(String captureStopDate) {
		this.CaptureStopDate = captureStopDate;
	}

	public String getCaptureStartTime() {
		return this.CaptureStartTime;
	}

	public void setCaptureStartTime(String captureStartTime) {
		this.CaptureStartTime = captureStartTime;
	}

	public String getStandStartDate() {
		return this.StandStartDate;
	}

	public void setStandStartDate(String standStartDate) {
		this.StandStartDate = standStartDate;
	}

	public String getStandStopDate() {
		return this.StandStopDate;
	}

	public void setStandStopDate(String standStopDate) {
		this.StandStopDate = standStopDate;
	}

	public String getStandStartTime() {
		return this.StandStartTime;
	}

	public void setStandStartTime(String standStartTime) {
		this.StandStartTime = standStartTime;
	}

	public String getStandStopTime() {
		return this.StandStopTime;
	}

	public void setStandStopTime(String standStopTime) {
		this.StandStopTime = standStopTime;
	}

	public String getDemandStartDate() {
		return this.DemandStartDate;
	}

	public void setDemandStartDate(String demandStartDate) {
		this.DemandStartDate = demandStartDate;
	}

	public String getDemandStopDate() {
		return this.DemandStopDate;
	}

	public void setDemandStopDate(String demandStopDate) {
		this.DemandStopDate = demandStopDate;
	}

	public String getDemandStartTime() {
		return this.DemandStartTime;
	}

	public void setDemandStartTime(String demandStartTime) {
		this.DemandStartTime = demandStartTime;
	}

	public String getKeyValue() {
		return this.KeyValue;
	}

	public void setKeyValue(String keyValue) {
		this.KeyValue = keyValue;
	}

	public String KeyValue = "";

	public String preview() {
		this.terminal = getTerminalhandle().read(this.terminal.getDeviceId());
		return "preview";
	}

	public void topreview() {
		try {
			Storage storage = new Storage(getCstmId());

			File baseDir = new File(Storage.getDeviceScreenDirctory(this.terminal.getDeviceId()));
			if (baseDir.exists()) {
				String[] filelist = baseDir.list();

				String path = Storage.getDeviceScreenDirctory(this.terminal.getDeviceId()) + "\\" + filelist[0];

				File file = new File(path);
				if (file.exists()) {
					JsonUtils.writeSuccess(this.servletResponse);
				} else {
					JsonUtils.writeErrorData(this.servletResponse, getText("noscreenshot"));
				}
			} else {
				JsonUtils.writeErrorData(this.servletResponse, getText("noscreenshot"));
			}
		} catch (Exception e) {
			JsonUtils.writeErrorData(this.servletResponse, getText("noscreenshot"));
		}
	}

	public String tooltips = "";

	public String getTooltips() {
		return this.tooltips;
	}

	public void setTooltips(String tooltips) {
		this.tooltips = tooltips;
	}

	public void previewfile() {
		try {
			Storage storage = new Storage(getCstmId());

			File baseDir = new File(Storage.getDeviceScreenDirctory(this.terminal.getDeviceId()));
			if (baseDir.exists()) {
				String[] filelist = baseDir.list();

				String path = Storage.getDeviceScreenDirctory(this.terminal.getDeviceId()) + "\\" + filelist[0];

				this.tooltips = path.substring(path.lastIndexOf("\\") + 1);

				this.servletResponse.encodeRedirectURL(path);
				this.servletContext.getResource(path);

				response(JSONSuccessString(this.servletResponse.encodeURL(path)));
			}
		} catch (Exception localException) {
		}
	}

	public void crExcel() {
		try {
			List<ExcelTitleVo> lists = new ArrayList();
			lists.add(new ExcelTitleVo(getText("terminalid"), 20));
			lists.add(new ExcelTitleVo(getText("terminalname"), 30));
			lists.add(new ExcelTitleVo(getText("terminaldescription"), 40));
			List<ExcelTerminalListVo> terminalvos = new ArrayList();

			Map<String, Object> param = new HashMap();
			setQueryNoLimitParam(param);
			List<Terminal> terminallist = getTerminalhandle().readAll(queryNoLimitString(), param);
			for (int i = 0; i < terminallist.size(); i++) {
				terminalvos.add(new ExcelTerminalListVo().convert((Terminal) terminallist.get(i)));
			}
			exportExcel(getText("action_terminallist"), terminalvos, lists);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String selectterminal() {
		return "selectterminal";
	}
}

/*
 * Location: C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * 
 * Qualified Name: com.gnamp.struts.action.TerminalAction
 * 
 * JD-Core Version: 0.7.0.1
 * 
 */