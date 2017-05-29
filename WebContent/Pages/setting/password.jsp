<%@ page language="java" pageEncoding="utf-8"%>
<%@ page language="java" import="java.util.*"%> 
<%@ page language="java" import="com.gnamp.terminal.config.DeviceSetting"%> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<% 
String CurrentPassword = null;
String AssignPassword = null;
String SelectIDs = (String)request.getAttribute("selectIDs");  

DeviceSetting currentSetting = (DeviceSetting)request.getAttribute("currentSetting");  
if (currentSetting != null && currentSetting.mPassword != null ) {
	CurrentPassword = currentSetting.mPassword;
}  
DeviceSetting assignSetting = (DeviceSetting)request.getAttribute("assignSetting");  
if (assignSetting != null && assignSetting.mPassword != null ) {
	AssignPassword = assignSetting.mPassword;
} 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
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
							<tr > 
								<td style="width: 40%" align="right"><s:text name="prompt_newpassword"/></td>
								<td style="width: 60%" align="left">
									<input type="text" id="password" value="" maxlength="6" onkeypress="return 0x30<=event.keyCode && event.keyCode<=0x39;" />  
								</td> 
							</tr>
							<!--  tr> 
								<td style="width: 40%" align="right"><s:text name="prompt_repassword"/></td>
								<td style="width: 60%" align="left">
									<input type="text" id="repassword" value="" /> 
								</td> 
							</tr --> 
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
							<tr > 
								<td style="width: 50%" align="right"><s:text name="prompt_curpassword"/></td>
								<td colSpan="2" style="width: 50%" align="left"> 
									<span id="c_password"></span>
								</td> 
							</tr>
							<!--  tr> 
								<td style="width: 50%" align="right"><s:text name="prompt_repassword"/></td>
								<td colSpan="2" style="width: 50%" align="left"> 
								</td> 
							</tr --> 
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
			<s:text name="termial_password_length_prompt" />
			
<%@include file="/Pages/setting/device_body2.jsp" %>
</body>
</html>

<script type="text/javascript">
$(function () { 
	$("#li_password").addClass("curItem"); 

	fillData();
});  


function configItemEnable(enable) {	 
	if (enable) {    
		$("#password").removeAttr("disabled"); 
		$("#repassword").removeAttr("disabled"); 
	} else { 
		$("#password").attr("disabled", "disabled");  
		$("#repassword").attr("disabled", "disabled");    
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
	var password;  
	var repassword; 

	if ($("#radio_remote:radio").is(":checked")) {  

		var passwordPattern = /^\d{6}$/g;   
		
		password = $("#password").val(); 
		if (password && !password.match(passwordPattern)) {  
			$("#password").focus();
			return; 
		}  
		
		repassword = password; // $("#repassword").val();
		if (repassword != password) {  
			$("#repassword").focus();
			return;
		} 
		remoteModify = true;
	} 
	
	var params = {
		"selectIDs" : "<%=SelectIDs%>", 
		"remoteModify" : remoteModify, 
		"password" : password? password : "",
		"repassword" : repassword? repassword : ""
	};  
	$.post('/Pages/setting!putPassword', params,  function(result) {
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
	$("#password").val(""); 
	$("#repassword").val("");   

	<%if (AssignPassword != null ) { %>  
		$("#radio_remote:radio").attr("checked", "checked"); 
		$("#radio_terminal:radio").removeAttr("checked");  
	<%} else { %>  
		$("#radio_remote:radio").removeAttr("checked");  
		$("#radio_terminal:radio").attr("checked", "checked");   
	<%}%>    
	
	$("#password").val("<%= AssignPassword != null? AssignPassword : "" %>");  
	configItemEnable(<%= AssignPassword != null %>);  

	<%if (CurrentPassword == null) { %>     
		$("#c_password").text("");  
	<%} else if (CurrentPassword.length() > 0) { %>     
		$("#c_password").text("<%= CurrentPassword %>");   
	<%} else {%>  
		$("#c_password").text("<s:text name="password_null" />");   
	<%} %>      
	
}

function resetConfig() { 

	if ($("#radio_remote:radio").is(":checked")) {  
		$("#password").val(""); 
		$("#repassword").val("");  
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
