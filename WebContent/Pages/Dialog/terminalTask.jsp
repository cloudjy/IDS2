<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="com.gnamp.server.model.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%
Terminal terminal = (Terminal)request.getAttribute("terminal");
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<link href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" media="all"
	href="${theme}/skins/default/css/niceforms-default.css" />
<script src="${theme}/js/jquery-1.7.2.min.js" language="javascript"></script>
<script language="javascript" type="text/javascript"
	src="${theme}/js/niceforms.js"></script>


<link rel="stylesheet" type="text/css"
	href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" />
<script src="${theme}/js/ipub.models-<s:text name="langjs"/>.js" type="text/javascript"></script>
<script type="text/javascript"
	src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
<link href="${theme}/skins/default/css/ipubs-dialog.css"
	rel="stylesheet" type="text/css" />

<script src="js/gnamp.js" language="javascript"></script>

<script type="text/javascript">
	function doSubmit(obj) {
	debugger;
		if ( (obj == 'looptask' && $("#assignLoopTaskId").val() == 0) 
				|| (obj == 'demandtask' && $("#assignDemandTaskId").val() == 0) 
				|| (obj == 'plugintask' && $("#assignPluginTaskId").val() == 0) ) { 
			alert("<s:text name="please_select_task" />");
			return false;
		}
				
		$("#subtype").val(obj);
		var msg = "<s:text name="verify_assign_task"/>";  

		if(obj == "clearlooptask") { 
			// msg = "<s:text name="isempty_1"/><s:text name="text_terminaltask_loop"/><s:text name="isempty_2"/>";
			msg = "<s:text name="verify_unassign_task"/>";  
		} else if(obj == "cleardemandtask" ) {
			// msg = "<s:text name="isempty_1"/><s:text name="text_terminaltask_demand"/><s:text name="isempty_2"/>";
			msg = "<s:text name="verify_unassign_task"/>";  
		} else if(obj == "clearplugintask") {
			// msg = "<s:text name="isempty_1"/><s:text name="text_terminaltask_insert"/><s:text name="isempty_2"/>";
			msg = "<s:text name="verify_unassign_task"/>";  
		}

		confirm(msg,function(){
			var param = $('#form1').serialize();
			$.ajax({
				type : "post",
				dataType : "json",
				url : "terminal!TerminalTask",
				data : param,
				complete : function() {
					//compliteCallback();
				},
				success : function(msg) {
					if (msg.success) {
						//alert(msg.data);
					    //window.location.reload(); 
						
						//alert('操作成功');
						//return closeIFrame();
						
						 
						    if(obj == "looptask" || obj == "clearlooptask")
						    	$("#assignlooptaskversion").html(""+(msg.data == null ? "" : msg.data)+"");							
						    else if(obj == "demandtask" || obj == "cleardemandtask")
								$("#assigndemandtaskversion").html(""+(msg.data == null ? "" : msg.data)+"");							
						    else if(obj == "plugintask" || obj == "clearplugintask")
								$("#assignplugintaskversion").html(""+(msg.data == null ? "" : msg.data)+"");							
						 
						if(obj == "clearlooptask")
							$("#assignLoopTaskId").val("0");
						else if(obj == "cleardemandtask")
							$("#assignDemandTaskId").val("0");
						else if(obj == "clearplugintask")
							$("#assignPluginTaskId").val("0");
						
						callParentFunction("finished", 'ok');
						
					} else {
						alert(msg.data);
					}
					//callback(msg);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					window.top.location.href='/';
					//errorCallback(XMLHttpRequest, textStatus, errorThrown);
				}
			});
			return false;
			
		});
		

	}

	function cancl() {
		callParentFunction("finished", 'ok');
		return closeIFrame();
	}
</script>

</head>
<body style="overflow-x: hidden;overflow-y: hidden;">
	<%
	
	if(terminal==null){
		
		return;
	}
	%>

	<!-- 弹出窗口 开始-->
	<div class="tcWindow">
		<div class="tcBt">
			<img src="${theme}/skins/default/images/tcBt1.gif" width="5" height="35"
				class="left" />
			<h2>
				<% if(terminal != null && terminal.getDeviceId()>0 ){ %>
				<s:text name="curterminal" />:&nbsp;${terminal.deviceName };&nbsp;
				ID:&nbsp;<%= String.format("%012X", terminal.getDeviceId())  %>  
				<input type="hidden" value="${terminal.deviceId }" name="terminal.deviceId" />
				<%} %>
			</h2>
			<img src="${theme}/skins/default/images/tcBt3.gif" class="right" />
		</div>
		<div class="tcNr">
			<form id="form1" class="niceform">
				<input type="hidden" value="${terminal.deviceId }"
					name="terminal.deviceId" /> <input type="hidden"
					value="${terminal.groupId }" name="terminal.groupId" /> <input
					type="hidden" id="subtype" name="tasktype" /> <input
					value="<%= request.getAttribute("selectIDs") %>" name="selectIDs"
					type="hidden" />
				<table width="100%" border="0" cellspacing="2" cellpadding="0"
					class="bdTab">
					<tr>
						<td></td>
						<td><s:text name="text_terminaltask_loop" /></td>
						<td><s:text name="text_terminaltask_demand" /></td>
						<td><s:text name="text_terminaltask_insert" /></td>
					</tr>
					<tr>
						<td><s:text name="assigntaskname" /></td>
						<td><select size="1" id="assignLoopTaskId"
							name="terminal.assignLoopTaskId">
								<option value="0">
									<s:text name="select" />
								</option>
								<% if(request.getAttribute("looptasklist") !=null){ 
							  List<LoopTask> list = (List<LoopTask>)request.getAttribute("looptasklist");							  
							  for(int i=0;i<list.size();i++){
							%>
								<option
									<%if(terminal != null && terminal.getAssignLoopTaskId()==list.get(i).getTaskId()){%>
									selected <%} %> value="<%=list.get(i).getTaskId() %>" title="<%=list.get(i).getTaskName() %>" ><%=(list.get(i).getTaskName().length()>8 ? (list.get(i).getTaskName().substring(0, 8)+"...") : list.get(i).getTaskName()) %></option>
								<%}
						  } %>
						</select></td>
						<td><select  size="1" id="assignDemandTaskId"
							name="terminal.assignDemandTaskId">
								<option value="0">
									<s:text name="select" />
								</option>
								<% if(request.getAttribute("demandtasklist") !=null){ 
							  List<DemandTask> list = (List<DemandTask>)request.getAttribute("demandtasklist");							  
							  for(int i=0;i<list.size();i++){
							%>
								<option
									<%if(terminal != null && terminal.getAssignDemandTaskId()==list.get(i).getTaskId()){%>
									selected <%} %> value="<%=list.get(i).getTaskId() %>" title="<%=list.get(i).getTaskName() %>" ><%=(list.get(i).getTaskName().length()>8 ? (list.get(i).getTaskName().substring(0, 8)+"...") : list.get(i).getTaskName()) %></option>
								<%}
						  } %>
						</select></td>
						<td><select size="1" id="assignPluginTaskId"
							name="terminal.assignPluginTaskId">
								<option value="0">
									<s:text name="select" />
								</option>
								<% if(request.getAttribute("plugintasklist") !=null){ 
							  List<PluginTask> list = (List<PluginTask>)request.getAttribute("plugintasklist");							  
							  for(int i=0;i<list.size();i++){
							%>
								<option
									<%if(terminal != null && terminal.getAssignPluginTaskId()==list.get(i).getTaskId()){%>
									selected <%} %> value="<%=list.get(i).getTaskId() %>" title="<%=list.get(i).getTaskName() %>" ><%=(list.get(i).getTaskName().length()>8 ? (list.get(i).getTaskName().substring(0, 8)+"...") : list.get(i).getTaskName()) %></option>
								<%}
						  } %>
						</select></td>
					</tr>
					<tr>
						<td><s:text name="assigntaskversion" /></td>
						<td id="assignlooptaskversion">
							<%if(terminal != null && terminal.getAssignLoopTaskVersion() != null){ %><%=terminal.getAssignLoopTaskVersion().toLocaleString() %><%} %>
						</td>
						<td id="assigndemandtaskversion">
							<%if(terminal != null && terminal.getAssignDemandTaskVersion() != null){ %><%=terminal.getAssignDemandTaskVersion().toLocaleString() %><%} %>
						</td>
						<td id="assignplugintaskversion">
							<%if(terminal != null && terminal.getAssignPluginTaskVersion() != null){ %><%=terminal.getAssignPluginTaskVersion().toLocaleString() %><%} %>
						</td>
					</tr>
					<tr>
						<td><s:text name="currenttaskname" /></td>
						<td>
							<%if(terminal != null){ %>${terminal.curLoopTaskName}<%} %>
						</td>
						<td>
							<%if(terminal != null){ %>${terminal.curDemandTaskName}<%} %>
						</td>
						<td>
							<%if(terminal != null){ %>${terminal.curPluginTaskName}<%} %>
						</td>
					</tr>
					<tr>
						<td><s:text name="currenttaskversion" /></td>
						<td>
							<%if(terminal != null && terminal.getCurLoopTaskVersion() != null) {%>
							<%=terminal.getCurLoopTaskVersion().toString().equals("1970-01-01 00:00:00.0") ? "" : terminal.getCurLoopTaskVersion().toLocaleString()%>
							<%} %>
						</td>
						<td>
							<%if(terminal != null && terminal.getCurDemandTaskVersion() != null) {%><%=terminal.getCurDemandTaskVersion().toString().equals("1970-01-01 00:00:00.0") ? "" : terminal.getCurDemandTaskVersion().toLocaleString()%>
							<%} %>
						</td>
						<td>
							<%if(terminal != null && terminal.getCurPluginTaskVersion() != null) {%><%=terminal.getCurPluginTaskVersion().toString().equals("1970-01-01 00:00:00.0") ? "" : terminal.getCurPluginTaskVersion().toLocaleString()%>
							<%} %>
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<s:if test="#session.current_privilege.speccarousel">
							<input type="button" onclick="doSubmit('looptask')"
								class="sub" value="<s:text name="button_assign_task"/>" /> &nbsp; &nbsp; 
							<input type="button" class="sub" onclick="doSubmit('clearlooptask')"
								value="<s:text name="button_unassign_task"/>" />
							</s:if>
						</td>
						<td>
							<s:if test="#session.current_privilege.specdemand">
							<input type="button" onclick="doSubmit('demandtask')"
								class="sub" value="<s:text name="button_assign_task"/>" /> &nbsp; &nbsp; 
							<input type="button" class="sub" onclick="doSubmit('cleardemandtask')"
								value="<s:text name="button_unassign_task"/>" />
							</s:if>
						</td>
						<td>
							<s:if test="#session.current_privilege.specspots">
							<input type="button" onclick="doSubmit('plugintask')"
								class="sub" value="<s:text name="button_assign_task"/>" /> &nbsp; &nbsp; 
							<input type="button" class="sub" onclick="doSubmit('clearplugintask')"
								value="<s:text name="button_unassign_task"/>" />
							</s:if>
						</td>
					</tr>
				</table>
				<div style="font-size:12px;padding-left:20px;padding-top: 10px;"><s:text name="prompt_assign_task"/></div>
				<div style="font-size:12px;padding-left:20px;"><s:text name="prompt_unassign_task"/></div>
			</form>
		</div>
		<div class="tcDi">
			<img src="${theme}/skins/default/images/tcDi1.gif" class="left" />
			<img src="${theme}/skins/default/images/tcDi2.gif" class="right" />
		</div>
	</div>
	<!-- 弹出窗口 结束-->
	<!-- 表格间隔变色 -->
	<script>
		// Give classes to even and odd table rows for zebra striping.
		$(document).ready(function() {
			// $('tr:odd').addClass('alt');
			$('tr:nth-child(even)').addClass('alt');

			$('td:contains(Henry)').nextAll().andSelf().addClass('highlight');//.siblings()
			$('th').parent().addClass('table-heading');
		});
	</script>
</body>
</html>

