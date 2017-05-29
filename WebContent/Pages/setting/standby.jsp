<%@ page language="java" pageEncoding="utf-8"%>
<%@ page language="java" import="java.util.*"%> 
<%@ page language="java" import="com.gnamp.terminal.config.DeviceSetting"%>  
<%@ page language="java" import="com.gnamp.terminal.config.LocalConfig"%>  
<%@ taglib prefix="s" uri="/struts-tags" %>

<%
String CurrentStandby = "";
String AssignStandby = "";
String SelectIDs = (String)request.getAttribute("selectIDs"); 
boolean RemoteModify = false; 

DeviceSetting currentSetting = (DeviceSetting)request.getAttribute("currentSetting"); 
if (currentSetting != null && currentSetting.mLocal != null) {
	CurrentStandby = LocalConfig.timeBoundToText(currentSetting.mLocal.mStandby);
} 
DeviceSetting assignSetting = (DeviceSetting)request.getAttribute("assignSetting"); 
if (assignSetting != null && assignSetting.mLocal != null) {
	AssignStandby = LocalConfig.timeBoundToText(assignSetting.mLocal.mStandby);
	RemoteModify = (assignSetting.mLocal.mStandby != null);
} 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
<style type="text/css">  
.timeInput { width: 40px; text-align: center;}  
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
								<td style="width: 30%" align="center">
									<s:text name="standby_time_range"/>1:   
								</td>
								<td style="width: 35%" align="right">
									<input type="text" id="start_time1" value=""  class="timeInput" /> 
								</td>  
								<td style="width: 35%">
									~&nbsp;&nbsp;<input type="text" id="end_time1" value=""  class="timeInput" /> 
								</td>  
							</tr>
							<tr> 
								<td style="width: 30%" align="center">
									<s:text name="standby_time_range"/>2:   
								</td>
								<td style="width: 35%" align="right">
									<input type="text" id="start_time2" value=""  class="timeInput" /> 
								</td>  
								<td style="width: 35%">
									~&nbsp;&nbsp;<input type="text" id="end_time2" value=""  class="timeInput" /> 
								</td>   
							</tr>
							<tr> 
								<td style="width: 30%" align="center">
									<s:text name="standby_time_range"/>3:   
								</td>
								<td style="width: 35%" align="right">
									<input type="text" id="start_time3" value=""  class="timeInput" /> 
								</td>  
								<td style="width: 35%">
									~&nbsp;&nbsp;<input type="text" id="end_time3" value=""  class="timeInput" /> 
								</td> 
							</tr>
							<tr> 
								<td style="width: 30%" align="center">
									<s:text name="standby_time_range"/>4:   
								</td>
								<td style="width: 35%" align="right">
									<input type="text" id="start_time4" value=""  class="timeInput" /> 
								</td>  
								<td style="width: 35%">
									~&nbsp;&nbsp;<input type="text" id="end_time4" value=""  class="timeInput" /> 
								</td>   
							</tr>
							<tr> 
								<td style="width: 30%" align="center">
									<s:text name="standby_time_range"/>5:   
								</td>
								<td style="width: 35%" align="right">
									<input type="text" id="start_time5" value=""  class="timeInput" /> 
								</td>  
								<td style="width: 35%">
									~&nbsp;&nbsp;<input type="text" id="end_time5" value=""  class="timeInput" /> 
								</td>   
							</tr> 
							<tr> 
								<td colSpan="3" align="center">
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
								<td style="width: 50%" align="right"> 
									<span id="c_start_time1">__:__</span>
								</td>  
								<td style="width: 50%">
									~&nbsp;&nbsp; 
									<span id="c_end_time1">__:__</span>
								</td>  
							</tr>
							<tr> 
								<td style="width: 50%" align="right"> 
									<span id="c_start_time2">__:__</span>
								</td>  
								<td style="width: 50%">
									~&nbsp;&nbsp; 
									<span id="c_end_time2">__:__</span>
								</td>  
							</tr>
							<tr> 
								<td style="width: 50%" align="right"> 
									<span id="c_start_time3">__:__</span>
								</td>  
								<td style="width: 50%">
									~&nbsp;&nbsp; 
									<span id="c_end_time3">__:__</span>
								</td>  
							</tr>
							<tr> 
								<td style="width: 50%" align="right"> 
									<span id="c_start_time4">__:__</span>
								</td>  
								<td style="width: 50%">
									~&nbsp;&nbsp; 
									<span id="c_end_time4">__:__</span>
								</td>  
							</tr>
							<tr> 
								<td style="width: 50%" align="right"> 
									<span id="c_start_time5">__:__</span>
								</td>  
								<td style="width: 50%">
									~&nbsp;&nbsp; 
									<span id="c_end_time5">__:__</span>
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
	$("#li_standby").addClass("curItem"); 
	
	var inputArray = [
		$("#start_time1"), $("#end_time1"), 
		$("#start_time2"), $("#end_time2"), 
		$("#start_time3"), $("#end_time3"), 
		$("#start_time4"), $("#end_time4"), 
		$("#start_time5"), $("#end_time5")
	];
	for (var i=0; i<inputArray.length; ++i) {
		inputArray[i].focus(function () {
			WdatePicker({startDate:'%H:%m:s%',dateFmt:"HH:mm",lang:'cn',alwaysUseStartDate:true, 
				qsEnabled:true,quickSel:['06:00:00','09:00:00','12:00:00','18:00:00','21:00:00']});
		});
	};  
	fillData();
}); 

function configItemEnable(enable) {	 
	var configItemArray = [
		$("#start_time1"), $("#end_time1"), 
    	$("#start_time2"), $("#end_time2"), 
    	$("#start_time3"), $("#end_time3"), 
    	$("#start_time4"), $("#end_time4"), 
    	$("#start_time5"), $("#end_time5") 
    ]; 
	              	
	if (enable) {
		for (var i in configItemArray) {
			configItemArray[i].removeAttr("disabled");
		}   
	} else {
		for (var i in configItemArray) {
			configItemArray[i].attr("disabled", "disabled");
		} 
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

function configString() { 
	var inputArray = [
		$("#start_time1"), $("#end_time1"), 
		$("#start_time2"), $("#end_time2"), 
		$("#start_time3"), $("#end_time3"), 
		$("#start_time4"), $("#end_time4"), 
		$("#start_time5"), $("#end_time5")
	]; 
	
	var pattern = /^\s*(([0-1]{0,1}[0-9])|(2[0-3])):[0-5]{0,1}[0-9]\s*$/;
	for (var i=0; i<inputArray.length; ++i) { 
		var hhmm = trimEmpty(inputArray[i].val());
		if (!hhmm) {
			hhmm = "00:00";
			inputArray[i].val(hhmm);
		} 
		if (!hhmm.match(pattern)) {
			// inputArray[i].focus();
			setTimeout(function(){inputArray[i].focus();}, 50); 
			return null;
		}
	};
	
	var rangeArray = new Array();
	for (var i=0; i<(inputArray.length/2); ++i) { 
		var startArray = inputArray[i*2].val().split(":");
		var startSecond = parseInt(startArray[0], 10)*3600 +  parseInt(startArray[1], 10)*60; 
		var endArray = inputArray[i*2+1].val().split(":");
		var endSecond = parseInt(endArray[0], 10)*3600 +  parseInt(endArray[1], 10)*60;  
		if (startSecond != 0 || endSecond != 0) {
			if (startSecond >= endSecond) {
				// inputArray[i*2+1].focus();
				setTimeout(function(){inputArray[i*2+1].focus();}, 50); 
				return null;
			}
			
			for (var j=0; j<rangeArray.length; ++j) { 
				if (startSecond < rangeArray[j].end && endSecond > rangeArray[j].start) {
					//inputArray[i*2].focus();
					setTimeout(function(){inputArray[i*2].focus();}, 50); 
					return null;
				}
			}
			rangeArray.push({start:startSecond, end:endSecond});
		}
	}   

	var text = "";
	for (var i=0; i<rangeArray.length; ++i) { 
		var range = rangeArray[i];
		text += ("[" +  formatHHmm(range.start) + ","  +  formatHHmm(range.end) + "]");
	}  
	return text;
}
 
function submitConfig() { 
	var remoteModify = false;
	var configText = "";
	
	if ($("#radio_remote:radio").is(":checked")) {
		var configText = configString();
		if (configText === null) {
			return;
		}
		remoteModify = true;
	} 
	 
	var params = {
		"selectIDs" : "<%=SelectIDs%>", 
		"remoteModify" : remoteModify, 
		"configString" : configText
	}; 
	$.post('/Pages/setting!putStandby', params,  function(result) {
		if (result && result.success) {
			alert("<s:text name="setup_ok" />");
		} else {
			alert("<s:text name="setup_failed" />");
		}
	});
    return false;
} 

function fillData() { 
	var timeBounds = null;
	var assignText= "<%=AssignStandby%>";
	var currentText= "<%=CurrentStandby%>";
	var remoteModify= <%=RemoteModify%>; 

	if (remoteModify) {
		$("#radio_remote:radio").attr("checked", "checked"); 
		$("#radio_terminal:radio").removeAttr("checked"); 
	} else {
		$("#radio_remote:radio").removeAttr("checked");  
		$("#radio_terminal:radio").attr("checked", "checked");  
	}
	configItemEnable(remoteModify);
	
	var inputArray = [
		{start : $("#start_time1"), end : $("#end_time1")}, 
		{start : $("#start_time2"), end : $("#end_time2")}, 
		{start : $("#start_time3"), end : $("#end_time3")}, 
		{start : $("#start_time4"), end : $("#end_time4")}, 
		{start : $("#start_time5"), end : $("#end_time5")}
	]; 

	timeBounds = parseTimeBounds(assignText);
	for (var i=0; i<inputArray.length; ++i) {
		var boundInput = inputArray[i];
		boundInput.start.val(formatHHmm(i < timeBounds.length? timeBounds[i].start : 0));
		boundInput.end.val(formatHHmm(i < timeBounds.length? timeBounds[i].end : 0)); 
	}
	
	var spanArray = [
		{start : $("#c_start_time1"), end : $("#c_end_time1")}, 
		{start : $("#c_start_time2"), end : $("#c_end_time2")}, 
		{start : $("#c_start_time3"), end : $("#c_end_time3")}, 
		{start : $("#c_start_time4"), end : $("#c_end_time4")}, 
		{start : $("#c_start_time5"), end : $("#c_end_time5")}
	];
	
	timeBounds = parseTimeBounds(currentText);
	for (var i=0; i<spanArray.length && i < timeBounds.length; ++i) {
		var boundSpan = spanArray[i]; 
		boundSpan.start.text(formatHHmm(timeBounds[i].start));
		boundSpan.end.text(formatHHmm(timeBounds[i].end)); 
	}
} 

function resetConfig() {   
	if ($("#radio_remote:radio").is(":checked")) { 
		var inputArray = [
			{start : $("#start_time1"), end : $("#end_time1")}, 
			{start : $("#start_time2"), end : $("#end_time2")}, 
			{start : $("#start_time3"), end : $("#end_time3")}, 
			{start : $("#start_time4"), end : $("#end_time4")}, 
			{start : $("#start_time5"), end : $("#end_time5")}
		];   
		for (var i=0; i<inputArray.length; ++i) { 
			var boundInput = inputArray[i];
			boundInput.start.val(formatHHmm(0));
			boundInput.end.val(formatHHmm(0)); 
		}
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
