<%@ page language="java" pageEncoding="utf-8"%>
<%@ page language="java" import="java.util.*"%> 
<%@ page language="java" import="com.gnamp.terminal.config.DeviceSetting"%>  
<%@ page language="java" import="com.gnamp.terminal.config.ExpertConfig"%>  
<%@ taglib prefix="s" uri="/struts-tags" %>

<%
ExpertConfig CurrentAdvanced = null;
ExpertConfig AssignAdvanced = null; 
String SelectIDs = (String)request.getAttribute("selectIDs");  

DeviceSetting currentSetting = (DeviceSetting)request.getAttribute("currentSetting"); 
if (currentSetting != null && currentSetting.mExpert != null) {
	CurrentAdvanced = currentSetting.mExpert;
} 
DeviceSetting assignSetting = (DeviceSetting)request.getAttribute("assignSetting"); 
if (assignSetting != null && assignSetting.mExpert != null) {
	AssignAdvanced = assignSetting.mExpert; 
} 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
<style type="text/css">  
.trDaily {}
.trWeekly {}
</style> 
<%@include file="/Pages/setting/device_head.jsp" %>
</head>
<body>
<%@include file="/Pages/setting/device_body1.jsp" %>  
			<input type="radio" name="radio_remote" id="radio_remote" value="remote" onclick="remoteRadioClick();"  />
			<s:text name="server_first" />
			<input type="radio" name="radio_terminal" id="radio_terminal" value="terminal" onclick="terminalRadioClick();" />
			<s:text name="terminal_first" />
			<span style="float: right;"><s:text name="gray_prompt" /></span>
			<form action="#" method="post" class="niceform2">
				<table width="100%" border="0" cellspacing="2" cellpadding="0" class="bdTab">
				<tr>
					<td style="width:60%">
						<table width="100%" border="0" cellspacing="2" cellpadding="0" class="bdTab"> 
							<tr>  
								<td style="width: 40%" align="right"><s:text name="rotation_degree" /></td>
								<td style="width: 60%" align="left"> 
									<select id="rotation" name="rotation" style="width: 100px;">
				                  		<option value="0" selected >0</option>
				                  		<option value="90" >90</option>
				                  		<option value="180" >180</option>
				                  		<option value="270" >270</option>
				               		</select> 
								</td> 
							</tr>
							<tr>  
								<td style="width: 40%" align="right"><s:text name="multi_sync" /></td>
								<td style="width: 60%" align="left"> 
									<select id="multi_sync" name="multi_sync" style="width: 100px;">
				                  		<option value="false" selected ><s:text name="option_off" /></option> 
				                  		<option value="true" ><s:text name="option_on" /></option>
				               		</select> 
								</td> 
							</tr>
							<tr>  
								<td style="width: 40%" align="right"><s:text name="storage_path" /></td>
								<td style="width: 60%" align="left"> 
									<select id="storage" name="storage" style="width: 100px;">
				                  		<option value="<%= ExpertConfig.STORAGE_MEMORY %>" selected><s:text name="storage_memory" /></option>
				                  		<option value="<%= ExpertConfig.STORAGE_SDCARD %>"><s:text name="storage_sdcard" /></option>
				                  		<option value="<%= ExpertConfig.STORAGE_UDISK %>" ><s:text name="storage_udisk" /></option> 
				                  		<!-- option value="<%= ExpertConfig.STORAGE_HDDISK %>" ><s:text name="storage_hddisk" /></option --> 
				               		</select> 
								</td> 
							</tr> 
							<tr>  
								<td style="width: 40%" align="right"><s:text name="time_zone" /></td>
								<td style="width: 60%" align="left"> 
									<select id="time_zone" name="time_zone" style="width: 100px;">
								        <option value="12">+12</option>
								        <option value="11">+11</option>
								        <option value="10">+10</option>
								        <option value="9">+9</option>
								        <option value="8" selected>+8</option>
								        <option value="7">+7</option>
								        <option value="6">+6</option>
								        <option value="5">+5</option>
								        <option value="4">+4</option>
								        <option value="3">+3</option>
								        <option value="2">+2</option>
								        <option value="1">+1</option>
								        <option value="0">0</option>
								        <option value="-1">-1</option>
								        <option value="-2">-2</option>
								        <option value="-3">-3</option>
								        <option value="-5">-4</option>
								        <option value="-5">-5</option>
								        <option value="-6">-6</option>
								        <option value="-7">-7</option>
								        <option value="-8">-8</option>
								        <option value="-9">-9</option>
								        <option value="-10">-10</option>
								        <option value="-11">-11</option>
								        <option value="-12">-12</option>
				               		</select> 
								</td> 
							</tr>
							<tr> 
								<td colSpan="2" align="center">
									<input type="button" value="<s:text name="submit_button" />" onclick="submitConfig();" />
										&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="button" value="<s:text name="default_button" />" onclick="resetConfig();" />
								</td> 
							</tr>
						</table>
					</td>
					<td style="width:40%">
						<table width="100%" border="0" cellspacing="2" cellpadding="0" class="bdTab"> 
							<tr>  
								<td style="width: 40%" align="right"><s:text name="rotation_degree" /></td>
								<td style="width: 60%" align="left"> 
				               		<span id="c_rotation"></span>
								</td> 
							</tr>
							<tr>  
								<td style="width: 40%" align="right"><s:text name="multi_sync" /></td>
								<td style="width: 60%" align="left"> 
				               		<span id="c_multi_sync"></span>
								</td> 
							</tr>
							<tr>  
								<td style="width: 40%" align="right"><s:text name="storage_path" /></td>
								<td style="width: 60%" align="left">  
				               		<span id="c_storage"></span>
								</td> 
							</tr> 
							<tr>  
								<td style="width: 40%" align="right"><s:text name="time_zone" /></td>
								<td style="width: 60%" align="left"> 
				               		<span id="c_time_zone"></span>
								</td> 
							</tr>
							<tr> 
								<td colSpan="2" align="center">
									<input type="button" value="<s:text name="reload_current_button" />" onclick="reloadCurrent();" />
								</td> 
							</tr>
						</table>
					</td>
				</tr>
				</table>
			</form>
			
<%@include file="/Pages/setting/device_body2.jsp" %>
</body>
</html>

<script type="text/javascript">
$(function () { 
	$("#li_advanced").addClass("curItem");   
	
	fillData();
});  

function configItemEnable(enable) {  
	if (enable) {    
		$("#rotation").removeAttr("disabled"); 
		$("#multi_sync").removeAttr("disabled"); 
		$("#storage").removeAttr("disabled");  
		$("#time_zone").removeAttr("disabled");  
	} else { 
		$("#rotation").attr("disabled", "disabled");
		$("#multi_sync").attr("disabled", "disabled");
		$("#storage").attr("disabled", "disabled"); 
		$("#time_zone").attr("disabled", "disabled"); 
	}  
}

function terminalRadioClick() {	 
	
	$("#radio_terminal:radio").attr("checked", "checked"); 
	$("#radio_remote:radio").removeAttr("checked"); 
	
	configItemEnable(false);  
	
	return false;
}

function remoteRadioClick() {	 
	$("#radio_terminal:radio").removeAttr("checked"); 
	$("#radio_remote:radio").attr("checked", "checked"); 
	
	configItemEnable(true);   
	return false;
}
 
function submitConfig() {  
	var remoteModify = false;
	var rotation = "";
	var multi_sync = "";
	var storage = "";
	var time_zone = <%= ExpertConfig.DEFAULT_TIMEZONE %>;
	
	if ($("#radio_remote:radio").is(":checked")) {  
		rotation = $("#rotation").val();
		multi_sync = $("#multi_sync").val();
		storage = $("#storage").val(); 
		time_zone = $("#time_zone").val(); 

		remoteModify = true;
	}   

	var params = {
			"selectIDs" : "<%=SelectIDs%>", 
			"remoteModify" : remoteModify, 
			"advanced.screenRotation" : rotation, 
			"advanced.multiDevSynch" : multi_sync, 
			"advanced.storage" : storage, 
			"advanced.timeZone" : time_zone, 
	};
	$.post('/Pages/setting!putAdvanced', params,  function(result) {
		if (result && result.success) {
			alert("<s:text name="setup_ok" />");
		} else {
			alert("<s:text name="setup_failed" />");
		}
	});
    return false;
} 

 
function fillData() {   
	// default
	$("#server_addr").val("192.168.1.2"); 
	$("#control_port").val("9090"); 
	$("#download_port").val("80"); 
	
<%if (AssignAdvanced != null ) { %> 
	$("#rotation").val("<%= AssignAdvanced.mScreenRotation %>");
	$("#multi_sync").val("<%= AssignAdvanced.mMultiDevSynch? "true" : "false" %>");
	$("#storage").val("<%= AssignAdvanced.mStorage %>"); 
	$("#time_zone").val("<%= AssignAdvanced.mTimeZone %>"); 
	
	$("#radio_remote:radio").attr("checked", "checked"); 
	$("#radio_terminal:radio").removeAttr("checked"); 
	configItemEnable(true); 
<%} else { %>  
	$("#rotation").val("0");
	$("#multi_sync").val("false");
	$("#storage").val("memory"); 
	$("#time_zone").val("<%= ExpertConfig.DEFAULT_TIMEZONE %>"); 

	$("#radio_remote:radio").removeAttr("checked");  
	$("#radio_terminal:radio").attr("checked", "checked");   
	configItemEnable(false);
<%}%>   
 
<%if (CurrentAdvanced != null ) { %>
	$("#c_rotation").text(
			$("#rotation option[value='<%= CurrentAdvanced.mScreenRotation %>']").text());  
	$("#c_multi_sync").text(
			$("#multi_sync option[value='<%= CurrentAdvanced.mMultiDevSynch? "true" : "false" %>']").text()); 
	$("#c_storage").text(
			$("#storage option[value='<%= CurrentAdvanced.mStorage %>']").text()); 
	$("#c_time_zone").text(
			$("#time_zone option[value='<%= CurrentAdvanced.mTimeZone %>']").text()); 
<% } %>
} 

function resetConfig() {  
	if ($("#radio_remote:radio").is(":checked")) {  
		$("#rotation").val("0");
		$("#multi_sync").val("false");
		$("#storage").val("memory");
		$("#time_zone").val("<%= ExpertConfig.DEFAULT_TIMEZONE %>"); 
	}
}

function reloadCurrent() {
	try {
		var a = $(".curItem a");
		if (a && a.length > 0 && a.get(0)) {
			a.get(0).click();
		} 
	} catch(exp) {
	};
}

</script>
