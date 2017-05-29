<%@ page language="java" pageEncoding="utf-8"%>
<%@ page language="java" import="java.util.*"%> 
<%@page import="com.gnamp.server.model.Terminal"%>
<%@page import="com.gnamp.struts.terminal.WatcherSetting"%> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<% 
String SelectIDs = (String)request.getAttribute("selectIDs");  
WatcherSetting watcherSetting = (WatcherSetting)request.getAttribute("watcherSetting");  
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-cache" /> 
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="1970-01-01 00:00:00" /> 

<style type="text/css">  
body {overflow-x: hidden;overflow-y: hidden;}

.configBox{overflow:hidden; margin:0 auto;}  

</style> 
<link rel="stylesheet" type="text/css" href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" />
<link rel="stylesheet" type="text/css" href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" />
<link rel="stylesheet" type="text/css" href="${theme}/skins/default/css/jqtransform.css" media="all" />
<link rel="stylesheet" type="text/css" href="${theme}/skins/default/css/zTreeStyle.css" />
<link rel="stylesheet" type="text/css" href="${theme}/skins/default/css/ipubs-dialog.css"/>
<link rel="stylesheet" type="text/css" href="${theme}/skins/default/css/niceforms-default.css" />

<script type="text/javascript" src="${theme}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${theme}/js/jquery.jqtransform.js"></script>
<script type="text/javascript" src="${theme}/js/jscroll.js"></script>
<script type="text/javascript" src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
<script type="text/javascript" src="${theme}/js/ipub.models-<s:text name="langjs"/>.js"></script>
<script type="text/javascript" src="${theme}/js/jquery.ztree.core-3.2.min.js"></script>
<script type="text/javascript" src="${theme}/js/jquery.ztree.exedit-3.2.min.js"></script>
<script type="text/javascript" src="${theme}/js/jquery.ztree.excheck-3.2.min.js"></script>
<script type="text/javascript" src="${theme}/js/niceforms.js"></script>

<script src="${theme}/Pages/Dialog/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${theme}/Pages/js/gnamp.js"></script> 


<script type="text/javascript"> 

function trimEmpty(str) {
	return str? str.replace(/(^\s*)|(\s*$)/g, "") : "";
} 

</script>




</head>
<body>
<div class="configBox">  
    <div class="tcWindow">
		<div class="tcBt">
			<img src="${theme}/skins/default/images/tcBt1.gif" width="5" height="35"
				class="left" />
			<h2> 
				<%
				Terminal terminal = (Terminal)request.getAttribute("terminal");
				if(terminal != null && terminal.getDeviceId() != 0 ) {
					String id = String.format("%012X", terminal.getDeviceId());
					String name = terminal.getDeviceName();
					boolean online = (terminal.getOnlineState() != 0);
					if (online) {
				%>  
					<s:text name="current_terminal" />:&nbsp;<%=name%>&nbsp;(<s:text name="online" />)
				<% } else {%>  
					<s:text name="current_terminal" />:&nbsp;<%=name%>&nbsp;(<s:text name="offline" />)
				<% } 
				} 
				%>
			</h2>
			<img src="${theme}/skins/default/images/tcBt3.gif" class="right" />
		</div>
		<div class="tcNr" >  
			<div style="position:relative;width:300px;padding-top:15px;"> 
				<form action="#" method="post" class="niceform2">
					<table width="100%" border="0" cellspacing="2" cellpadding="0" class="bdTab"> 
						<tr>  
							<td style="width: 40%" align="right"><s:text name="offline_alaram" /></td>
							<td style="width: 60%" align="left"> 
								<select id="offline_alaram" name="offline_alaram" style="width: 100px;">
							        <option value="0" selected><s:text name="option_off" /></option>
							        <option value="3">3<s:text name="minute" /></option>
							        <option value="5">5<s:text name="minute" /></option> 
							        <option value="10">10<s:text name="minute" /></option>
							        <option value="20">20<s:text name="minute" /></option>
							        <option value="30">30<s:text name="minute" /></option>
							        <option value="60">60<s:text name="minute" /></option> 
							        <option value="90">90<s:text name="minute" /></option>
							        <option value="120">120<s:text name="minute" /></option>
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
				</form>
			
			</div>
			<!-- div style="font-size:12px;padding-left:20px;position: absolute;top: 500px;width: 665px"></div -->
		</div>
		<div class="tcDi">
			<img src="${theme}/skins/default/images/tcDi1.gif" class="left" />
			<img src="${theme}/skins/default/images/tcDi2.gif" class="right" />
		</div>
	</div> 
</div> 
</body>
</html> 

<script type="text/javascript">
$(function () { 
	fillData();
}); 
 
function submitConfig() { 
	var params = {
			"selectIDs" : "<%=SelectIDs%>",  
			"watcherSetting.maxMinutes" : $("#offline_alaram").val(), 
			"watcherSetting.email" : "",  
	};
	$.post('/Pages/watcher!putWatcherSetting', params,  function(result) {
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
	$("#offline_alaram").val("0"); 
<%if (watcherSetting != null ) { %> 
	$("#offline_alaram").val("<%= watcherSetting.getMaxMinutes() %>"); 
<% } %>
} 

function resetConfig() {  
	$("#time_zone").val("0");  
} 
</script>
