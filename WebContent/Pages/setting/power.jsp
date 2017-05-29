<%@ page language="java" pageEncoding="utf-8"%>
<%@ page language="java" import="java.util.*"%> 
<%@ page language="java" import="com.gnamp.terminal.config.DeviceSetting"%>  
<%@ page language="java" import="com.gnamp.terminal.config.LocalConfig"%>  
<%@ page language="java" import="com.gnamp.terminal.config.WeeklyPowerConfig"%> 
<%@ taglib prefix="s" uri="/struts-tags" %>

<%
String CurrentPower = "";
String AssignPower = "";
String CurrentMode = "";
String AssignMode = "";
String SelectIDs = (String)request.getAttribute("selectIDs"); 
boolean RemoteModify = false; 

DeviceSetting currentSetting = (DeviceSetting)request.getAttribute("currentSetting"); 
if (currentSetting != null && currentSetting.mLocal != null) {
	CurrentPower = LocalConfig.powerToText(currentSetting.mLocal.mPower);
	if (currentSetting.mLocal.mPower != null) { 
		CurrentMode = currentSetting.mLocal.mPower instanceof WeeklyPowerConfig ? 
				"weekly" : "daily"; // DailyPowerConfig
	}
} 
DeviceSetting assignSetting = (DeviceSetting)request.getAttribute("assignSetting"); 
if (assignSetting != null && assignSetting.mLocal != null) {
	AssignPower = LocalConfig.powerToText(assignSetting.mLocal.mPower);
	if (assignSetting.mLocal.mPower != null) { 
		AssignMode = assignSetting.mLocal.mPower instanceof WeeklyPowerConfig ? 
				"weekly" : "daily"; // DailyPowerConfig
	}
	RemoteModify = (assignSetting.mLocal.mPower != null);
} 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
<style type="text/css">  
.timeInput { width: 40px; text-align: center;}  
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
						<table width="100%" border="0" cellspacing="2" cellpadding="0"
							class="bdTab"> 
							<tr> 
								<td colSpan="3" align="left">
									<input type="radio" name="daily" id="daily" value="daily" onclick="dailyRadioClick();"  />
									<s:text name="daily_mode" />&nbsp;<s:text name="daily_mode_prompt" />
								</td> 
							</tr>
							<tr class="trDaily"> 
								<td style="width: 40%" align="right"><s:text name="poweron_time" /></td>
								<td colSpan="2" style="width: 80%" align="left">
									<input type="text" id="start_time" value=""  class="timeInput" /> 
								</td> 
							</tr>
							<tr class="trDaily"> 
								<td style="width: 40%" align="right"><s:text name="poweroff_time" /></td>
								<td colSpan="2" style="width: 80%" align="left">
									<input type="text" id="end_time" value=""  class="timeInput" />
								</td> 
							</tr>
							<tr> 
								<td colSpan="3" align="left">
									<input type="radio" name="weekly" id="weekly" value="weekly" onclick="weeklyRadioClick();" />
									<s:text name="weekly_mode" />&nbsp;<s:text name="weekly_mode_prompt" />
								</td>   
							</tr>
							<tr class="trWeekly"> 
								<td style="width: 40%" align="center"><s:text name="prompt_monday" /></td>
								<td style="width: 30%" align="right">
									<input type="text" id="start_time1" value=""  class="timeInput" /> 
								</td>  
								<td style="width: 30%">
									~&nbsp;&nbsp;<input type="text" id="end_time1" value=""  class="timeInput" />  
								</td>  
							</tr>
							<tr class="trWeekly"> 
								<td style="width: 40%" align="center"><s:text name="prompt_tuesday" /></td>
								<td style="width: 30%" align="right">
									<input type="text" id="start_time2" value=""  class="timeInput" /> 
								</td>  
								<td style="width: 30%">
									~&nbsp;&nbsp;<input type="text" id="end_time2" value=""  class="timeInput" /> 
								</td>   
							</tr>
							<tr class="trWeekly"> 
								<td style="width: 40%" align="center"><s:text name="prompt_wednesday" /></td>
								<td style="width: 30%" align="right">
									<input type="text" id="start_time3" value=""  class="timeInput" />  
								</td>  
								<td style="width: 30%">
									~&nbsp;&nbsp;<input type="text" id="end_time3" value=""  class="timeInput" />  
								</td> 
							</tr>
							<tr class="trWeekly"> 
								<td style="width: 40%" align="center"><s:text name="prompt_thursday" /></td>
								<td style="width: 30%" align="right">
									<input type="text" id="start_time4" value=""  class="timeInput" />  
								</td>  
								<td style="width: 30%">
									~&nbsp;&nbsp;<input type="text" id="end_time4" value=""  class="timeInput" />  
								</td>   
							</tr>
							<tr class="trWeekly"> 
								<td style="width: 40%" align="center"><s:text name="prompt_friday" /></td>
								<td style="width: 30%" align="right">
									<input type="text" id="start_time5" value=""  class="timeInput" /> 
								</td>  
								<td style="width: 30%">
									~&nbsp;&nbsp;<input type="text" id="end_time5" value=""  class="timeInput" /> 
								</td>   
							</tr>	 
							<tr class="trWeekly"> 
								<td style="width: 40%" align="center"><s:text name="prompt_saturday" /></td>
								<td style="width: 30%" align="right">
									<input type="text" id="start_time6" value=""  class="timeInput" />  
								</td>  
								<td style="width: 30%">
									~&nbsp;&nbsp;<input type="text" id="end_time6" value=""  class="timeInput" />   
								</td>   
							</tr>	 
							<tr class="trWeekly"> 
								<td style="width: 40%" align="center"><s:text name="prompt_sunday" /></td> 
								<td style="width: 30%" align="right">
									<input type="text" id="start_time0" value=""  class="timeInput" />   
								</td>  
								<td style="width: 30%">
									~&nbsp;&nbsp;<input type="text" id="end_time0" value=""  class="timeInput" />   
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
								<td colSpan="3" align="left"> 
									<s:text name="daily_mode" />
								</td> 
							</tr>
							<tr> 
								<td style="width: 40%" align="right"><s:text name="poweron_time" /></td>
								<td colSpan="2" style="width: 80%" align="left"> 
									<span id="c_start_time" value="">__:__</span>
								</td> 
							</tr>
							<tr class="trDaily"> 
								<td style="width: 40%" align="right"><s:text name="poweroff_time" /></td>
								<td colSpan="2" style="width: 80%" align="left"> 
									<span id="c_end_time">__:__</span>
								</td> 
							</tr>
							<tr> 
								<td colSpan="3" align="left"> 
									<s:text name="weekly_mode" />
								</td>   
							</tr>
							<tr> 
								<td style="width: 40%" align="center"><s:text name="prompt_monday" /></td>
								<td style="width: 30%" align="right"> 
									<span id="c_start_time1">__:__</span>
								</td>  
								<td style="width: 30%">
									~&nbsp;&nbsp;
									<span id="c_end_time1">__:__</span>
								</td>   
							</tr>
							<tr> 
								<td style="width: 40%" align="center"><s:text name="prompt_tuesday" /></td>
								<td style="width: 30%" align="right"> 
									<span id="c_start_time2">__:__</span>
								</td>  
								<td style="width: 30%">
									~&nbsp;&nbsp;
									<span id="c_end_time2">__:__</span>
								</td>   
							</tr>
							<tr> 
								<td style="width: 40%" align="center"><s:text name="prompt_wednesday" /></td>
								<td style="width: 30%" align="right"> 
									<span id="c_start_time3">__:__</span>
								</td>  
								<td style="width: 30%">
									~&nbsp;&nbsp;
									<span id="c_end_time3">__:__</span>
								</td>   
							</tr>
							<tr> 
								<td style="width: 40%" align="center"><s:text name="prompt_thursday" /></td>
								<td style="width: 30%" align="right"> 
									<span id="c_start_time4">__:__</span>
								</td>  
								<td style="width: 30%">
									~&nbsp;&nbsp;
									<span id="c_end_time4">__:__</span>
								</td>   
							</tr>
							<tr> 
								<td style="width: 40%" align="center"><s:text name="prompt_friday" /></td>
								<td style="width: 30%" align="right"> 
									<span id="c_start_time5">__:__</span>
								</td>  
								<td style="width: 30%">
									~&nbsp;&nbsp;
									<span id="c_end_time5">__:__</span>
								</td>   
							</tr>	 
							<tr> 
								<td style="width: 40%" align="center"><s:text name="prompt_saturday" /></td>
								<td style="width: 30%" align="right"> 
									<span id="c_start_time6">__:__</span>
								</td>  
								<td style="width: 30%">
									~&nbsp;&nbsp;
									<span id="c_end_time6">__:__</span>
								</td>   
							</tr>	 
							<tr> 
								<td style="width: 40%" align="center"><s:text name="prompt_sunday" /></td> 
								<td style="width: 30%" align="right"> 
									<span id="c_start_time0">__:__</span>
								</td>  
								<td style="width: 30%">
									~&nbsp;&nbsp;
									<span id="c_end_time0">__:__</span>
								</td>   
							</tr>	 
							<tr> 
								<td colSpan="3" align="center">
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

	$("#li_power").addClass("curItem");
	$("#daily:radio").trigger("click"); 
	
	var inputArray = [ 
		$("#start_time"), $("#end_time"),
		$("#start_time1"), $("#end_time1"), 
		$("#start_time2"), $("#end_time2"), 
		$("#start_time3"), $("#end_time3"), 
		$("#start_time4"), $("#end_time4"), 
		$("#start_time5"), $("#end_time5"), 
		$("#start_time6"), $("#end_time6"), 
		$("#start_time0"), $("#end_time0")
	];
	for (var i=0; i<inputArray.length; ++i) {
		inputArray[i].focus(function () {
			WdatePicker({startDate:'%H:%m:s%',dateFmt:"HH:mm",lang:'cn',alwaysUseStartDate:true, 
				qsEnabled:true,quickSel:['00:00:00','08:00:00','12:00:00','21:00:00','23:59:00']});
		});  
		inputArray[i].val(i<2? "00:00" : (i%2==0? "09:00" : "18:00"))
	};  
	
	fillData();
});  

function configItemEnable(enable) { 
	              	
	if (enable) {  
		$("#daily:radio").removeAttr("disabled"); 
		$("#weekly:radio").removeAttr("disabled"); 

		if ($("#weekly:radio").is(":checked")) {
			$("#daily:radio").removeAttr("checked"); 
			$("#weekly:radio").attr("checked", "checked"); 
			
			$(".trDaily input").attr("disabled", "disabled");  
			$(".trWeekly input").removeAttr("disabled");  
		} else { // if ($("#daily:radio").is(":checked"))
			$("#daily:radio").attr("checked", "checked"); 
			$("#weekly:radio").removeAttr("checked"); 
			
			$(".trDaily input").removeAttr("disabled"); 
			$(".trWeekly input").attr("disabled", "disabled"); 
		}  
	} else { 
		$(".trDaily input").attr("disabled", "disabled");
		$(".trWeekly input").attr("disabled", "disabled");
		$("#daily:radio").attr("disabled", "disabled");
		$("#weekly:radio").attr("disabled", "disabled");
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

function dailyRadioClick() {	 
	
	$("#daily:radio").attr("checked", "checked"); 
	$("#weekly:radio").removeAttr("checked"); 
	
	$(".trDaily input").removeAttr("disabled"); 
	$(".trWeekly input").attr("disabled", "disabled");  
	
	return false;
}

function weeklyRadioClick() {	 
	$("#daily:radio").removeAttr("checked"); 
	$("#weekly:radio").attr("checked", "checked"); 
	  
	$(".trDaily input").attr("disabled", "disabled");  
	$(".trWeekly input").removeAttr("disabled"); 
	return false;
} 

function dailyConfigString() {  
	var range = {on:0, off:0}; 
	var pattern = /^\s*(([0-1]{0,1}[0-9])|(2[0-3])):[0-5]{0,1}[0-9]\s*$/;

	var inputArray = [ $("#start_time"), $("#end_time")];  
	for (var i=0; i<inputArray.length; ++i) { 
		var hhmm = trimEmpty(inputArray[i].val());
		if (!hhmm) {
			hhmm = "00:00";
			inputArray[i].val(hhmm);
		} 
		if (!hhmm.match(pattern)) {
			//inputArray[i].focus();
			setTimeout(function(){inputArray[i].focus();}, 50);
			return null;
		} 
		var hhmmArray = hhmm.split(":");
		var second = parseInt(hhmmArray[0], 10)*3600 +  parseInt(hhmmArray[1], 10)*60;  
		if (i == 0) {
			range.on = second;
		} else {
			range.off = second;
		} 
	}; 

	return "[" +  formatHHmm(range.on) + ","  +  formatHHmm(range.off) + "]";
}

function weeklyConfigString() { 
	var inputArray = [
		$("#start_time0"), $("#end_time0"), 
		$("#start_time1"), $("#end_time1"), 
		$("#start_time2"), $("#end_time2"), 
		$("#start_time3"), $("#end_time3"), 
		$("#start_time4"), $("#end_time4"), 
		$("#start_time5"), $("#end_time5"), 
		$("#start_time6"), $("#end_time6")
	];
	
	var pattern = /^\s*(([0-1]{0,1}[0-9])|(2[0-3])):[0-5]{0,1}[0-9]\s*$/;
	for (var i=0; i<inputArray.length; ++i) { 
		var hhmm = trimEmpty(inputArray[i].val());
		if (!hhmm) {
			hhmm = "00:00";
			inputArray[i].val(hhmm);
		} 
		if (!hhmm.match(pattern)) {
			//inputArray[i].focus();
			setTimeout(function(){inputArray[i].focus();}, 50);
			return null;
		}
	};
	
	var rangeArray = new Array();
	for (var i=0; i<(inputArray.length-1); i+=2) { 
		var startArray = inputArray[i].val().split(":");
		var startSecond = parseInt(startArray[0], 10)*3600 +  parseInt(startArray[1], 10)*60; 
		var endArray = inputArray[i+1].val().split(":");
		var endSecond = parseInt(endArray[0], 10)*3600 +  parseInt(endArray[1], 10)*60;  
		if (startSecond != 0 || endSecond != 0) {
			if (startSecond >= endSecond) {
				// inputArray[i+1].focus();
				setTimeout(function(){inputArray[i+1].focus();}, 50);
				return null;
			} 
			rangeArray.push({day:i/2,on:startSecond, off:endSecond});
		}
	}  
	
	if (rangeArray.length <= 0) {
		// $("#end_time1").focus();
		setTimeout(function(){$("#end_time1").focus();}, 50); 
		return false;
	}

	var text = "";
	for (var i=0; i<rangeArray.length; ++i) { 
		var range = rangeArray[i];
		text += ("[" + range.day + "," + formatHHmm(range.on) + ","  + 
				formatHHmm(range.off) + "]");
	} 
	return text;
}
 
function submitConfig() {  
	var remoteModify = false;
	var powerMode = "";
	var configText = "";
	
	if ($("#radio_remote:radio").is(":checked")) { 
		if ($("#daily:radio").is(":checked")) {
			configText = dailyConfigString();
			powerMode = "daily";
		} else if ($("#weekly:radio").is(":checked")) {
			configText = weeklyConfigString();
			powerMode = "weekly";
		}  
		if (!configText || configText.length <= 0) {
			return;
		} 
		remoteModify = true;
	}   

	var params = {
			"selectIDs" : "<%=SelectIDs%>", 
			"remoteModify" : remoteModify, 
			"powerMode" : powerMode,
			"configString" : configText
	};
	$.post('/Pages/setting!putPower', params,  function(result) {
		if (result && result.success) {
			alert("<s:text name="setup_ok" />");
		} else {
			alert("<s:text name="setup_failed" />");
		}
	});
    return false;
} 


//[00:00,18:00]
function parsDailyString(configText) {

	configText = configText.replace(/\s*/g, "");
	configText = configText.replace(/\[/g, ""); 
	configText = configText.replace(/\]/g, ""); 

	configText = configText.replace(/;/g, ","); 
	configText = configText.replace(/\-/g, ","); 

	try {
		var cols = configText.split(/\,/g); 
		if (!cols || cols.length != 2 || !cols[0] || !cols[1]) {
			return {on:0, off:0};
		}
		var on = parseHHmm(cols[0]);
		var off = parseHHmm(cols[1]); 
		return {on:on, off:off};
	} catch (exp) {
		return {on:0, off:0};
	}
}

//[0, 00:00,15:00][2,15:00,18:00]
function parseWeeklyString(configText) {
	
	var weeklyPower = new Array();
	
	if (!configText)
		return timeBoundInts;
	
	configText = configText.replace(/\s*/g, "");
	configText = configText.replace(/\]\[/g, "|"); 
	configText = configText.replace(/\];\[/g, "|"); 
	configText = configText.replace(/\[/g, ""); 
	configText = configText.replace(/\]/g, ""); 

	configText = configText.replace(/;/g, ","); 
	configText = configText.replace(/\-/g, ","); 
	
	var allBounds = configText.split(/\|/g);
	if (allBounds) { 
		for (var i=0; i<allBounds.length; ++i) {
			try {
				var cols = allBounds[i].split(/\,/g);
				var day = parseInt(cols[0], 10);
				var on = parseHHmm(cols[1]);
				var off = parseHHmm(cols[2]);

				if (on >= 0 && off > on && (0 <= day && day <= 6)) {
					weeklyPower.push({day:day, on:on, off:off});
				}
			} catch (exp) {
			}
		}
	}

	return weeklyPower;
} 


function fillData() { 
	var power = null;
	var assignText= "<%=AssignPower%>";
	var currentText= "<%=CurrentPower%>";
	var assignMode= "<%=AssignMode%>";
	var currentMode= "<%=CurrentMode%>";
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
		{start : $("#start_time0"), end : $("#end_time0")},
		{start : $("#start_time1"), end : $("#end_time1")}, 
		{start : $("#start_time2"), end : $("#end_time2")}, 
		{start : $("#start_time3"), end : $("#end_time3")}, 
		{start : $("#start_time4"), end : $("#end_time4")}, 
		{start : $("#start_time5"), end : $("#end_time5")}, 
		{start : $("#start_time6"), end : $("#end_time6")}
	]; 

	// set default 
	$("#start_time").val(formatHHmm(0)); 
	$("#end_time").val(formatHHmm(0));
	for (var i=0; i<inputArray.length; ++i) {
		var boundInput = inputArray[i];
		boundInput.start.val(formatHHmm(9*3600));
		boundInput.end.val(formatHHmm(18*3600)); 
	}

	if (assignMode && assignMode == "daily") { 
		power = parsDailyString(assignText);
		$("#start_time").val(formatHHmm(power.on)); 
		$("#end_time").val(formatHHmm(power.off));
		
		$("#daily:radio").trigger("click"); 
	} else if (assignMode && assignMode == "weekly") {  
		for (var i=0; i<inputArray.length; ++i) {
			var boundInput = inputArray[i];
			boundInput.start.val(formatHHmm(0));
			boundInput.end.val(formatHHmm(0)); 
		}
		
		power = parseWeeklyString(assignText);
		for (var i=0; i<power.length; ++i) {
			if (0 <= power[i].day && power[i].day <= 6) {
				var boundInput = inputArray[power[i].day];
				boundInput.start.val(formatHHmm(power[i].on));
				boundInput.end.val(formatHHmm(power[i].off)); 
			}
		}
		$("#weekly:radio").trigger("click"); 
	}
	if (currentMode && currentMode == "daily") { 
		power = parsDailyString(currentText);
		$("#c_start_time").text(formatHHmm(power.on)); 
		$("#c_end_time").text(formatHHmm(power.off)); 
	} else if (currentMode && currentMode == "weekly") { 
		var spanArray = [
			{start : $("#c_start_time0"), end : $("#c_end_time0")},
			{start : $("#c_start_time1"), end : $("#c_end_time1")}, 
			{start : $("#c_start_time2"), end : $("#c_end_time2")}, 
			{start : $("#c_start_time3"), end : $("#c_end_time3")}, 
			{start : $("#c_start_time4"), end : $("#c_end_time4")}, 
			{start : $("#c_start_time5"), end : $("#c_end_time5")}, 
			{start : $("#c_start_time6"), end : $("#c_end_time6")}
		]; 
		power = parseWeeklyString(currentText, function(val){return 0 <= val && val <= 10;}); 
		for (var i=0; i<power.length; ++i) {
			if (0 <= power[i].day && power[i].day <= 6) {
				var boundSpan = spanArray[power[i].day];
				boundSpan.start.text(formatHHmm(power[i].on));
				boundSpan.end.text(formatHHmm(power[i].off)); 
			}
		}
		
	}
} 

function resetConfig() {  
	if ($("#radio_remote:radio").is(":checked")) { 

		var inputArray = [
			{start : $("#start_time0"), end : $("#end_time0")},
			{start : $("#start_time1"), end : $("#end_time1")}, 
			{start : $("#start_time2"), end : $("#end_time2")}, 
			{start : $("#start_time3"), end : $("#end_time3")}, 
			{start : $("#start_time4"), end : $("#end_time4")}, 
			{start : $("#start_time5"), end : $("#end_time5")}, 
			{start : $("#start_time6"), end : $("#end_time6")}
		]; 

		// set default 
		$("#start_time").val(formatHHmm(0)); 
		$("#end_time").val(formatHHmm(0));
		for (var i=0; i<inputArray.length; ++i) {
			var boundInput = inputArray[i];
			boundInput.start.val(formatHHmm(9*3600));
			boundInput.end.val(formatHHmm(18*3600)); 
		}

		$("#daily:radio").trigger("click"); 
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
