<%@ page language="java" pageEncoding="utf-8"%>
<%@ page language="java" import="java.util.*"%> 
<%@ page language="java" import="com.gnamp.terminal.config.DeviceSetting"%> 
<%@ page language="java" import="com.gnamp.terminal.config.ServerConfig"%> 
<%@ page language="java" import="com.gnamp.terminal.config.Phone3GConfig"%> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<%
Phone3GConfig Current3g = null;
Phone3GConfig Assign3g = null;
ServerConfig CurrentServer = null;
ServerConfig AssignServer = null;
String SelectIDs = (String)request.getAttribute("selectIDs");  

DeviceSetting currentSetting = (DeviceSetting)request.getAttribute("currentSetting"); 
if (currentSetting != null && currentSetting.mNetwork != null && 
		currentSetting.mNetwork instanceof Phone3GConfig) {
	Current3g = (Phone3GConfig)currentSetting.mNetwork;
} 
DeviceSetting assignSetting = (DeviceSetting)request.getAttribute("assignSetting"); 
if (assignSetting != null && assignSetting.mNetwork != null && 
		assignSetting.mNetwork instanceof Phone3GConfig) {
	Assign3g = (Phone3GConfig)assignSetting.mNetwork; 
} 
if (currentSetting != null && currentSetting.mServer != null ) {
	CurrentServer = currentSetting.mServer;
}  
if (assignSetting != null && assignSetting.mServer != null ) {
	AssignServer = assignSetting.mServer;
} 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
<%@include file="/Pages/setting/network_head.jsp" %>
</head>
<body>
<%@include file="/Pages/setting/network_body1.jsp" %> 
			<input type="radio" name="radio_remote" id="radio_remote" value="remote" onclick="remoteRadioClick();"  />
			<s:text name="server_first" />
			<input type="radio" name="radio_terminal" id="radio_terminal" value="terminal" onclick="terminalRadioClick();" />
			<s:text name="terminal_first" />
			<span style="float: right;"><s:text name="gray_prompt" /></span>
			<form action="#" method="post" class="niceform2"><table width="100%" border="0" cellspacing="2" cellpadding="0" class="bdTab">
				<tr>
					<td style="width:60%">
						<table width="100%" border="0" cellspacing="2" cellpadding="0" class="bdTab">
							<tr> 
								<td colSpan="2" align="left">
									<input type="checkbox" name="phone3g_enable" id="phone3g_enable" onchange="phone3gConfgiEnable();" checked="checked" style="display:none"  />
									<s:text name="phone3g_enable" /> 
								</td> 
							</tr> 
							<tr> 
								<td colSpan="2" align="left">
									<input type="checkbox" name="server" id="server" value="server" onclick="serverConfigShow();"  />
									<s:text name="modify_server_setting" />
								</td>  
							</tr> 
							<tr class="trServer"> 
								<td style="width: 40%" align="right"><font color="#FF0000" size="3">&#x25B4;</font><s:text name="prompt_server_address" /></td> 
								<td style="width: 60%">
									<input type="text" name="server_addr" id="server_addr" value=""  class="inputServer" /> 
								</td>   
							</tr>   
							<tr class="trServer"> 
								<td style="width: 40%" align="right"><font color="#FF0000" size="3">&#x25B4;</font><s:text name="prompt_control_port" /></td> 
								<td style="width: 60%">
									<input type="text" name="control_port" id="control_port" value=""  class="inputServer" /> 
								</td>   
							</tr>  
							<tr class="trServer"> 
								<td style="width: 40%" align="right"><font color="#FF0000" size="3">&#x25B4;</font><s:text name="prompt_download_port" /></td> 
								<td style="width: 60%">
									<input type="text" name="download_port" id="download_port" value=""  class="inputServer" /> 
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
								<td colSpan="2" align="left">
									<s:text name="phone3g_enable" />
									<span id="c_phone3g_enable"></span>
								</td> 
							</tr>   
							<tr> 
								<td colSpan="2" align="left">
									<!-- <s:text name="modify_server_setting" /> -->
								</td>  
							</tr> 
							<tr class="trServer"> 
								<td style="width: 40%" align="right"><s:text name="prompt_server_address" /></td> 
								<td style="width: 60%"> 
									<span id="c_server_addr"></span>
								</td>   
							</tr>   
							<tr class="trServer"> 
								<td style="width: 40%" align="right"><s:text name="prompt_control_port" /></td> 
								<td style="width: 60%"> 
									<span id="c_control_port"></span>
								</td>   
							</tr>  
							<tr class="trServer"> 
								<td style="width: 40%" align="right"><s:text name="prompt_download_port" /></td> 
								<td style="width: 60%"> 
									<span id="c_download_port"></span>
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
			
<%@include file="/Pages/setting/network_body2.jsp" %>
</body>
</html>

<script type="text/javascript">
$(function () { 
	$("#li_3g").addClass("curItem"); 
	$("#wcdma:radio").trigger("click");
	
	serverConfigShow(); 
	fillData();
});  

function configItemEnable(enable) {	 
	if (enable) {    
		$("#phone3g_enable:checkbox").removeAttr("disabled");    
		$("#server:checkbox").removeAttr("disabled");   
		
		$(".trServer input").removeAttr("disabled");  
	} else { 
		$("#phone3g_enable:checkbox").attr("disabled", "disabled"); 
		$("#server:checkbox").attr("disabled", "disabled");  
		
		$(".trServer input").attr("disabled", "disabled");  
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

function phone3gConfgiEnable() {  
	return false;
} 

function serverConfigShow(){		
	if ($("#server:checkbox").is(":checked")) {
		$(".trServer > td").show("fast"); 
	} else {
		$(".trServer > td").hide(); 
	}  
	return false;
} 

function serverConfig() {  
	var domain;
	var download;
	var control; 

	var domainPattern = /^\s*([A-Za-z0-9\-\_\~]+\.)+[A-Za-z0-9\-\_\~]+\s*$/g;  
	var portPattern = /^\s*\d{1,5}\s*$/g; 
	
	domain = $("#server_addr").val();
	domain = domain? domain.replace(/(^\s*)|(\s*$)/g, "") : ""; 
	if (!domain || domain.length > 62 || !domain.match(domainPattern)) {  
		$("#server_addr").focus();
		return null;
	}  

	control = $("#control_port").val();
	if (!control || !control.match(portPattern) || 
			(control=parseInt(control, 10)) <=0 || control>65535 ) {  
		$("#control_port").focus();
		return null;
	} 
	download = $("#download_port").val();
	if (!download || !download.match(portPattern) || 
			(download=parseInt(download, 10)) <=0 || download>65535 ) {  
		$("#download_port").focus();
		return null;
	} 

	if (control == download){  
		$("#download_port").focus();
		return null;
	}
	
	return { serverAddress : domain, downloadPort : download, controlPort : control};
}

var RESET_TO_ETH = <%= Assign3g != null? "true" : "false"%>;

function submitConfig() {  

	var remoteModify = false;  
	var phone3g; 
	var server; 

	if ($("#radio_remote:radio").is(":checked")) {  
		if ($("#server:checkbox").is(":checked")) {
			server = serverConfig();
			if (!server) { 
				return; 
			}
		}
		remoteModify = true;
	} 
	
	var actionUrl = "/Pages/setting!put3G";
	var params = {
		"selectIDs" : "<%=SelectIDs%>", 
		"remoteModify" : remoteModify
	};
	if (remoteModify) {
		if ($("#phone3g_enable:checkbox").is(":checked")) {
			params["phone3G.type"] = ""; 
		} else { 
			if (RESET_TO_ETH) {
				params["ethernet.dhcp"] = true;
				actionUrl = "/Pages/setting!putEthernet";
			} else {
				alert("<s:text name="setup_failed" />");
				return;
			}
		}
	} 
	if (server) {
		params["server.serverAddress"] = server.serverAddress;
		params["server.downloadPort"] = server.downloadPort;
		params["server.controlPort"] = server.controlPort; 
	} 
	$.post(actionUrl, params,  function(result) {
		if (result && result.success) {
			alert("<s:text name="setup_ok" />"); 
			RESET_TO_ETH = true;
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

	$("#phone3g_enable:checkbox").attr("checked", "checked"); 
<%if (Assign3g != null ) {  %> // evdo  
	$("#phone3g_enable:checkbox").attr("checked", "checked"); 
<%} else { %> 
	// $("#phone3g_enable:checkbox").removeAttr("checked");   
<%} %>

<%if (AssignServer != null ) { %>
	$("#server_addr").val("<%= AssignServer.mServerAddress %>"); 
	$("#control_port").val("<%= AssignServer.mControlPort %>"); 
	$("#download_port").val("<%= AssignServer.mDownloadPort %>"); 
<% } %>

<% if (Assign3g != null ) {  %> 
	$("#radio_remote:radio").attr("checked", "checked"); 
	$("#radio_terminal:radio").removeAttr("checked"); 
	configItemEnable(true);
<%} else { %> 
	$("#radio_remote:radio").removeAttr("checked");  
	$("#radio_terminal:radio").attr("checked", "checked");   
	configItemEnable(false);
<%}%>   

$("#c_phone3g_enable").text(<%=Current3g != null? "true" : "false"%>? "[√]" : "[X]"); 

<%if (CurrentServer != null ) { %>
	$("#c_server_addr").text("<%= CurrentServer.mServerAddress %>"); 
	$("#c_control_port").text("<%= CurrentServer.mControlPort %>"); 
	$("#c_download_port").text("<%= CurrentServer.mDownloadPort %>"); 
<% } %>

}

function resetConfig() {  
	if ($("#radio_remote:radio").is(":checked")) {  
		$("#phone3g_enable:checkbox").attr("checked", "checked");

		$("#server_addr").val("192.168.1.2"); 
		$("#control_port").val("9090"); 
		$("#download_port").val("80"); 
	
		$("#server:checkbox").removeAttr("checked");   
		$(".trServer > td").hide();  
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