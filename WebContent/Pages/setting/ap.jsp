<%@ page language="java" pageEncoding="utf-8"%>
<%@ page language="java" import="java.util.*"%> 
<%@ page language="java" import="com.gnamp.terminal.config.DeviceSetting"%> 
<%@ page language="java" import="com.gnamp.terminal.config.ApConfig"%> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<%
ApConfig CurrentAp = null;
ApConfig AssignAp = null; 
String SelectIDs = (String)request.getAttribute("selectIDs");  

DeviceSetting currentSetting = (DeviceSetting)request.getAttribute("currentSetting"); 
if (currentSetting != null && currentSetting.mAp != null ) {
	CurrentAp = currentSetting.mAp;
} 
DeviceSetting assignSetting = (DeviceSetting)request.getAttribute("assignSetting"); 
if (assignSetting != null && assignSetting.mAp != null) {
	AssignAp = assignSetting.mAp; 
} 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
<style type="text/css">  
.dynamicKey {}
.staticKey {}
.c_dynamicKey {}
.c_staticKey {}
</style> 
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
									<input type="checkbox" name="ap_enable_ap" id="ap_enable_ap" onchange="apConfigEnable();"  />
									<s:text name="ap_enable_ap" />
								</td>  
							</tr> 
							<tr> 
								<td style="width: 40%" align="right"><font color="#FF0000" size="3">&#x25B4;</font><s:text name="ap_ssid" /></td> 
								<td style="width: 60%">
									<input type="text" name="ap_ssid" id="ap_ssid" value="" maxlength="8" style="width: 120px;"/> 
								</td>   
							</tr>   
							<tr> 
								<td style="width: 40%" align="right"><font color="#FF0000" size="3">&#x25B4;</font><s:text name="ap_password_mode" /></td> 
								<td style="width: 60%">
									<select id="ap_password_mode" name="ap_password_mode" onchange="onPasswordMode();" style="width: 124px;">
				                  		<option value="dynamic" selected><s:text name="ap_dynamic_password" /></option>
				                  		<option value="static"><s:text name="ap_static_password" /></option> 
				               		</select> 
								</td>   
							</tr>  
							<tr> 
								<td class="dynamicKey" style="width: 40%" align="right"><font color="#FF0000" size="3">&#x25B4;</font><s:text name="ap_random_time" /></td> 
								<td class="dynamicKey" style="width: 60%">
									<select id="ap_random_time" name="ap_random_time" style="width: 124px;">
				                  		<option value="1800" selected><s:text name="ap_random_password_30m" /></option>
				                  		<option value="3600"><s:text name="ap_random_password_1h" /></option> 
				                  		<option value="7200"><s:text name="ap_random_password_2h" /></option> 
				                  		<option value="14400"><s:text name="ap_random_password_4h" /></option> 
				                  		<option value="28800"><s:text name="ap_random_password_8h" /></option> 
				                  		<option value="86400"><s:text name="ap_random_password_24h" /></option> 
				               		</select> 
								</td> 
								<td class="staticKey" style="width: 40%" align="right"><font color="#FF0000" size="3">&#x25B4;</font><s:text name="ap_input_password" /></td> 
								<td class="staticKey" style="width: 60%">
									<input type="text" name="ap_password" id="ap_password" value="" maxlength="8" onkeypress="return 0x30<=event.keyCode && event.keyCode<=0x39;" style="width: 120px;" /> 
								</td>     
							</tr>     
							<tr> 
								<td style="width: 40%" align="right"><font color="#FF0000" size="3">&#x25B4;</font><s:text name="ap_samll_icon" /></td> 
								<td style="width: 60%">
									<select id="ap_samll_icon" name="ap_samll_icon" onchange="onSmallIcon();" style="width: 124px;">
				                  		<option value="<%=ApConfig.ICON_NONE%>" selected><s:text name="ap_icon_none" /></option>
				                  		<option value="<%=ApConfig.SMALL_SOLID_RED%>"><s:text name="ap_icon_small_solid_red" /></option> 
										<option value="<%=ApConfig.SMALL_SOLID_ORANGE%>"><s:text name="ap_icon_small_solid_orange" /></option> 
										<option value="<%=ApConfig.SMALL_SOLID_YELLOW%>"><s:text name="ap_icon_small_solid_yellow" /></option> 
										<option value="<%=ApConfig.SMALL_SOLID_GREEN%>"><s:text name="ap_icon_small_solid_green" /></option> 
										<option value="<%=ApConfig.SMALL_SOLID_CYAN%>"><s:text name="ap_icon_small_solid_cyan" /></option>    
										<option value="<%=ApConfig.SMALL_SOLID_BLUE%>"><s:text name="ap_icon_small_solid_blue" /></option>    
										<option value="<%=ApConfig.SMALL_SOLID_PURPLE%>"><s:text name="ap_icon_small_solid_purple" /></option>   
										<option value="<%=ApConfig.SMALL_SOLID_BLACK%>"><s:text name="ap_icon_small_solid_black" /></option>  
										<option value="<%=ApConfig.SMALL_TRANS_RED%>"><s:text name="ap_icon_small_trans_red" /></option>   
										<option value="<%=ApConfig.SMALL_TRANS_ORANGE%>"><s:text name="ap_icon_small_trans_orange" /></option> 
										<option value="<%=ApConfig.SMALL_TRANS_YELLOW%>"><s:text name="ap_icon_small_trans_yellow" /></option> 
										<option value="<%=ApConfig.SMALL_TRANS_GREEN%>"><s:text name="ap_icon_small_trans_green" /></option> 
										<option value="<%=ApConfig.SMALL_TRANS_CYAN%>"><s:text name="ap_icon_small_trans_cyan" /></option>  
										<option value="<%=ApConfig.SMALL_TRANS_BLUE%>"><s:text name="ap_icon_small_trans_blue" /></option>    
										<option value="<%=ApConfig.SMALL_TRANS_PURPLE%>"><s:text name="ap_icon_small_trans_purple" /></option>   
										<option value="<%=ApConfig.SMALL_TRANS_BLACK%>"><s:text name="ap_icon_small_trans_black" /></option> 
				               		</select>  
								</td>   
							</tr>    
							<tr> 
								<td style="width: 40%" align="right"><font color="#FF0000" size="3">&#x25B4;</font><s:text name="ap_samll_icon_pos" /></td> 
								<td style="width: 60%">
									<select id="ap_samll_icon_pos" name="ap_samll_icon_pos" style="width: 124px;">
				                  		<option value="<%=ApConfig.LEFT_TOP%>"><s:text name="ap_icon_pos_left_top" /></option>
				                  		<option value="<%=ApConfig.RIGHT_TOP%>" selected><s:text name="ap_icon_pos_right_top" /></option> 
										<option value="<%=ApConfig.LEFT_BOTTOM%>"><s:text name="ap_icon_pos_left_bottom" /></option> 
										<option value="<%=ApConfig.RIGHT_BOTTOM%>"><s:text name="ap_icon_pos_right_bottom" /></option> 
				               		</select>  
								</td>   
							</tr>    
							<tr> 
								<td style="width: 40%" align="right"><font color="#FF0000" size="3">&#x25B4;</font><s:text name="ap_large_icon" /></td> 
								<td style="width: 60%">
									<select id="ap_large_icon" name="ap_large_icon" style="width: 124px;">
				                  		<option value="<%=ApConfig.ICON_NONE%>" selected><s:text name="ap_icon_none" /></option>
				                  		<option value="<%=ApConfig.LARGE_SOLID_RED%>"><s:text name="ap_icon_large_solid_red" /></option>  
										<option value="<%=ApConfig.LARGE_TRANS_ORANGE%>"><s:text name="ap_icon_large_trans_orange" /></option>
										<option value="<%=ApConfig.LARGE_TRANS_BLUE%>"><s:text name="ap_icon_large_trans_blue" /></option>   
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
							<tr> 
								<td colSpan="2"></td> 
							</tr>
						</table>
					</td>
					<td style="width:40%">
						<table width="100%" border="0" cellspacing="2" cellpadding="0" class="bdTab"> 
							<tr> 
								<td colSpan="2" align="left">
									<!-- <s:text name="ap_enable_ap" /> -->
								</td>  
							</tr> 
							<tr> 
								<td style="width: 40%" align="right"><s:text name="ap_ssid" /></td> 
								<td style="width: 60%"> 
									<span id="c_ap_ssid"></span>
								</td>   
							</tr>   
							<tr> 
								<td style="width: 40%" align="right"><s:text name="ap_password_mode" /></td> 
								<td style="width: 60%"> 
									<span id="c_ap_password_mode"></span>
								</td>   
							</tr>  
							<tr> 
								<td class="c_dynamicKey" style="width: 40%" align="right"><s:text name="ap_random_time" /></td> 
								<td class="c_dynamicKey" style="width: 60%"> 
									<span id="c_ap_random_time"></span>
								</td>
								<td class="c_staticKey" style="width: 40%" align="right"><s:text name="ap_input_password" /></td> 
								<td class="c_staticKey" style="width: 60%"> 
									<span id="c_ap_password"></span>
								</td>   
							</tr>  
							<tr> 
								<td style="width: 40%" align="right"><s:text name="ap_samll_icon" /></td> 
								<td style="width: 60%"> 
									<span id="c_ap_samll_icon"></span>
								</td>   
							</tr>  
							<tr> 
								<td style="width: 40%" align="right"><s:text name="ap_samll_icon_pos" /></td> 
								<td style="width: 60%"> 
									<span id="c_ap_samll_icon_pos"></span>
								</td>   
							</tr>  
							<tr> 
								<td style="width: 40%" align="right"><s:text name="ap_large_icon" /></td> 
								<td style="width: 60%"> 
									<span id="c_ap_large_icon"></span>
								</td>   
							</tr> 
							<tr> 
								<td colSpan="2" align="center">
									<input type="button" value="<s:text name="reload_current_button" />" onclick="reloadCurrent();" />
								</td> 
							</tr>
							<tr> 
								<td colSpan="2"></td> 
							</tr>
						</table>
					</td>
				</tr> 
				</table>
			</form>
			<s:text name="ap_function_prompt" />		
			
<%@include file="/Pages/setting/network_body2.jsp" %>
</body>
</html>

<script type="text/javascript">
$(function () { 
	$("#li_ap").addClass("curItem");  
 
	fillData();
});  

function configItemEnable(enable) {	 
	if (enable) {    
		$("#ap_enable_ap:checkbox").removeAttr("disabled");  
	} else { 
		$("#ap_enable_ap:checkbox").attr("disabled", "disabled");  
	} 
	apConfigEnable(); 
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

function apConfigEnable(){	
	if ($("#radio_remote:radio").is(":checked") && 
			$("#ap_enable_ap:checkbox").is(":checked") ) { 
		$("#ap_ssid").removeAttr("disabled"); 
		$("#ap_password_mode").removeAttr("disabled"); 
		$("#ap_random_time").removeAttr("disabled"); 
		$("#ap_password").removeAttr("disabled"); 
		$("#ap_samll_icon").removeAttr("disabled"); 
		//$("#ap_samll_icon_pos").removeAttr("disabled"); 
		if ($("#ap_samll_icon").val() == "<%=ApConfig.ICON_NONE%>") {
			$("#ap_samll_icon_pos").attr("disabled", "disabled"); 
		} else { 
			$("#ap_samll_icon_pos").removeAttr("disabled");   
		}
		$("#ap_large_icon").removeAttr("disabled"); 
	} else { 
		$("#ap_ssid").attr("disabled", "disabled"); 
		$("#ap_password_mode").attr("disabled", "disabled"); 
		$("#ap_random_time").attr("disabled", "disabled"); 
		$("#ap_password").attr("disabled", "disabled"); 
		$("#ap_samll_icon").attr("disabled", "disabled"); 
		$("#ap_samll_icon_pos").attr("disabled", "disabled"); 
		$("#ap_large_icon").attr("disabled", "disabled"); 
	} 
	onPasswordMode();
	onSmallIcon();
	return false;
} 

function onPasswordMode(){	
	if ( $("#ap_password_mode").val() == "dynamic") { 
		$(".dynamicKey").show(); 
		$(".staticKey").hide(); 
	} else {  
		$(".dynamicKey").hide(); 
		$(".staticKey").show();  
	} 
	return false;
} 

function onSmallIcon(){ 
	if ($("#radio_remote:radio").is(":checked") && 
			$("#ap_enable_ap:checkbox").is(":checked") && 
			$("#ap_samll_icon").val() != "<%=ApConfig.ICON_NONE%>" ) {
		$("#ap_samll_icon_pos").removeAttr("disabled");  
	} else {   
		$("#ap_samll_icon_pos").attr("disabled", "disabled"); 
	} 
	return false;
} 

function apConfig() {  
	var ssid = "";
	var passwordTime = 0;
	var password = ""; 
	var smallIcon = "<%=ApConfig.ICON_NONE%>"; 
	var smallIconPosition = "<%=ApConfig.RIGHT_BOTTOM%>";
	var largeIcon = "<%=ApConfig.ICON_NONE%>";
	
	if ($("#ap_enable_ap:checkbox").is(":checked")) {  
		if ( $("#storage").val() ) {
			$("#ip").focus();
			return null;
		}
		
		var pattern = /^\s*[0-9a-zA-Z]+\s*$/g;
		
		ssid = $("#ap_ssid").val().replace(/\s*/g, ""); 
		$("#ap_ssid").val(ssid);
		if (!ssid ||  ssid.length == 0 || ssid.length > 8 || !ssid.match(pattern)) {
			$("#ap_ssid").focus();
			return null;
		}
		if ($("#ap_password_mode").val() == "dynamic") { 
			passwordTime = $("#ap_random_time").val();
			password = "";
		} else {
			password = $("#ap_password").val().replace(/\s*/g, ""); 
			if (!password || password.length != 8 || !password.match( /^\d{8}$/g)) {
				$("#ap_password").focus();
				return null;
			} 
			passwordTime = 0;
		}
		smallIcon = $("#ap_samll_icon").val();
		smallIconPosition = $("#ap_samll_icon_pos").val();
		largeIcon = $("#ap_large_icon").val();
	}

	return { ssid : ssid, passwordTime : passwordTime, password : password,  
			smallIcon : smallIcon,  smallIconPosition : smallIconPosition, largeIcon : largeIcon};
} 

function submitConfig() {  
	var remoteModify = false;  
	var ap;  

	if ($("#radio_remote:radio").is(":checked")) { 
		ap = apConfig();
		if (!ap) { 
			return; 
		} 
		remoteModify = true;
	} 
	
	var params = {
		"selectIDs" : "<%=SelectIDs%>", 
		"remoteModify" : remoteModify
	}; 
	if (ap) {
		params["ap.ssid"] = ap.ssid;
		params["ap.passwordTime"] = ap.passwordTime; 
		params["ap.password"] = ap.password;
		params["ap.smallIcon"] = ap.smallIcon;
		params["ap.smallIconPosition"] = ap.smallIconPosition;
		params["ap.largeIcon"] = ap.largeIcon;
	} 
	$.post('/Pages/setting!putAp', params,  function(result) {
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
	$("#ap_enable_ap:checkbox").removeAttr("checked"); 
	$("#ap_ssid").val("");  
	$("#ap_password_mode").val("dynamic");  
	$("#ap_random_time").val("86400"); 
	$("#ap_password").val(""); 
	$("#ap_samll_icon").val("<%=ApConfig.ICON_NONE%>"); 
	$("#ap_samll_icon_pos").val("<%=ApConfig.RIGHT_BOTTOM%>");
	$("#ap_large_icon").val("<%=ApConfig.ICON_NONE%>"); 

<%if (AssignAp != null) { %> 
	$("#radio_remote:radio").attr("checked", "checked"); 
	$("#radio_terminal:radio").removeAttr("checked");  
	<%String ssid = AssignAp.getSsid();
	ssid = null == ssid? "" : ssid.trim(); 
	if (ssid.length() > 0) {  %> // 
		$("#ap_enable_ap:checkbox").attr("checked", "checked");
		$("#ap_ssid").val("<%=AssignAp.getSsid()%>");  
		$("#ap_random_time").val("<%=AssignAp.getPasswordTime()>0? AssignAp.getPasswordTime() : 86400%>"); 
		$("#ap_password").val("<%=AssignAp.getPassword()%>"); 
		$("#ap_samll_icon").val("<%=AssignAp.getSmallIcon()%>"); 
		$("#ap_samll_icon_pos").val("<%=AssignAp.getSmallIconPosition()%>");
		$("#ap_large_icon").val("<%=AssignAp.getLargeIcon()%>");  
	<%} %>   
	configItemEnable(true);
<%} else { %> // 
	$("#radio_remote:radio").removeAttr("checked");  
	$("#radio_terminal:radio").attr("checked", "checked"); 
	configItemEnable(false);  
<%} %>   

$(".c_dynamicKey").hide(); 
$(".c_staticKey").show();  
<% 
String c_ssid = CurrentAp == null? "" : CurrentAp.getSsid(); 
c_ssid = null == c_ssid? "" : c_ssid.trim(); 
if (c_ssid.length() > 0) { %>  
	$("#c_ap_ssid").text("<%=CurrentAp.getSsid()%>");  
	$("#c_ap_password_mode").text(
			$("#ap_password_mode option[value='<%= CurrentAp.getPasswordTime()>0? "dynamic" : "static" %>']").text());  
	$("#c_ap_random_time").text(
			$("#ap_random_time option[value='<%=CurrentAp.getPasswordTime()%>']").text());
	$("#c_ap_password").text("<%=CurrentAp.getPassword()%>"); 
	$("#c_ap_samll_icon").text(
			$("#ap_samll_icon option[value='<%=CurrentAp.getSmallIcon()%>']").text()); 
	$("#c_ap_samll_icon_pos").text(
			$("#ap_samll_icon_pos option[value='<%=CurrentAp.getSmallIconPosition()%>']").text());
	$("#c_ap_large_icon").text(
			$("#ap_samll_icon option[value='<%=CurrentAp.getLargeIcon()%>']").text()); 
	<% if (CurrentAp.getPasswordTime() > 0 ) { %> 
		$(".c_dynamicKey").show(); 
		$(".c_staticKey").hide(); 
	<% } else { %> 
		$(".c_dynamicKey").hide(); 
		$(".c_staticKey").show();  
	<% } %>  
<% }%> 
}

function resetConfig() {  
	if ($("#radio_remote:radio").is(":checked")) {  
		$("#radio_terminal:radio").removeAttr("checked"); 
		$("#ap_ssid").val("");  
		$("#ap_password_mode").val("dynamic");  
		$("#ap_random_time").val("86400"); 
		$("#ap_password").val(""); 
		$("#ap_samll_icon").val("<%=ApConfig.ICON_NONE%>"); 
		$("#ap_samll_icon_pos").val("<%=ApConfig.RIGHT_BOTTOM%>");
		$("#ap_large_icon").val("<%=ApConfig.ICON_NONE%>");
		
		apConfigEnable();  
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
