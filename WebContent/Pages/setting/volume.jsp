<%@ page language="java" pageEncoding="utf-8"%>
<%@ page language="java" import="java.util.*"%> 
<%@ page language="java" import="com.gnamp.terminal.config.DeviceSetting"%>  
<%@ page language="java" import="com.gnamp.terminal.config.LocalConfig"%>  
<%@ taglib prefix="s" uri="/struts-tags" %>

<%
String CurrentVolume = "";
String AssignVolume = "";
String SelectIDs = (String)request.getAttribute("selectIDs"); 
boolean RemoteModify = false; 

DeviceSetting currentSetting = (DeviceSetting)request.getAttribute("currentSetting"); 
if (currentSetting != null && currentSetting.mLocal != null) {
	CurrentVolume = LocalConfig.timeBoundIntToText(currentSetting.mLocal.mVolume);
} 
DeviceSetting assignSetting = (DeviceSetting)request.getAttribute("assignSetting"); 
if (assignSetting != null && assignSetting.mLocal != null) {
	AssignVolume = LocalConfig.timeBoundIntToText(assignSetting.mLocal.mVolume);
	RemoteModify = (assignSetting.mLocal.mVolume != null);
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
									<s:text name="volume_time_range"/>1:   
								</td>
								<td style="width: 20%" align="right">
									<input type="text" id="start_time1" value="" class="timeInput" />   
								</td>  
								<td style="width: 20%">
									~&nbsp;&nbsp;<input type="text" id="end_time1" value="" class="timeInput" />   
								</td>  
								<td style="width: 30%">
									<s:text name="prompt_volume" /><select size="1" id="volume1">
					                  <option value="0">0</option>
					                  <option value="1">1</option> 
					                  <option value="2">2</option> 
					                  <option value="3">3</option> 
					                  <option value="4">4</option> 
					                  <option value="5">5</option> 
					                  <option value="6">6</option> 
					                  <option value="7">7</option> 
					                  <option value="8">8</option> 
					                  <option value="9">9</option> 
					                  <option value="10">10</option> 
					                </select>  
								</td> 
							</tr> 
							<tr> 
								<td style="width: 30%" align="center">
									<s:text name="volume_time_range"/>2:   
								</td>
								<td style="width: 20%" align="right">
									<input type="text" id="start_time2" value=""  class="timeInput" />  
								</td>  
								<td style="width: 20%">
									~&nbsp;&nbsp;<input type="text" id="end_time2" value=""  class="timeInput" />  
								</td>  
								<td style="width: 30%">
									<s:text name="prompt_volume" /><select size="1" id="volume2">
					                  <option value="0">0</option>
					                  <option value="1">1</option> 
					                  <option value="2">2</option> 
					                  <option value="3">3</option> 
					                  <option value="4">4</option> 
					                  <option value="5">5</option> 
					                  <option value="6">6</option> 
					                  <option value="7">7</option> 
					                  <option value="8">8</option> 
					                  <option value="9">9</option> 
					                  <option value="10">10</option> 
					                </select>  
								</td> 
							</tr>
							<tr> 
								<td style="width: 30%" align="center">
									<s:text name="volume_time_range"/>3:   
								</td>
								<td style="width: 20%" align="right">
									<input type="text" id="start_time3" value=""  class="timeInput" />  
								</td>  
								<td style="width: 20%">
									~&nbsp;&nbsp;<input type="text" id="end_time3" value=""  class="timeInput" />  
								</td>  
								<td style="width: 30%">
									<s:text name="prompt_volume" /><select size="1" id="volume3">
					                  <option value="0">0</option>
					                  <option value="1">1</option> 
					                  <option value="2">2</option> 
					                  <option value="3">3</option> 
					                  <option value="4">4</option> 
					                  <option value="5">5</option> 
					                  <option value="6">6</option> 
					                  <option value="7">7</option> 
					                  <option value="8">8</option> 
					                  <option value="9">9</option> 
					                  <option value="10">10</option> 
					                </select>  
								</td> 
							</tr>
							<tr> 
								<td style="width: 30%" align="center">
									<s:text name="volume_time_range"/>4:   
								</td>
								<td style="width: 20%" align="right">
									<input type="text" id="start_time4" value=""  class="timeInput" />  
								</td>  
								<td style="width: 20%">
									~&nbsp;&nbsp;<input type="text" id="end_time4" value=""  class="timeInput" />  
								</td>  
								<td style="width: 30%">
									<s:text name="prompt_volume" /><select size="1" id="volume4">
					                  <option value="0">0</option>
					                  <option value="1">1</option> 
					                  <option value="2">2</option> 
					                  <option value="3">3</option> 
					                  <option value="4">4</option> 
					                  <option value="5">5</option> 
					                  <option value="6">6</option> 
					                  <option value="7">7</option> 
					                  <option value="8">8</option> 
					                  <option value="9">9</option> 
					                  <option value="10">10</option> 
					                </select>  
								</td> 
							</tr>
							<tr> 
								<td style="width: 30%" align="center">
									<s:text name="volume_time_range"/>5:   
								</td>
								<td style="width: 20%" align="right">
									<input type="text" id="start_time5" value="" class="timeInput" />  
								</td>  
								<td style="width: 20%">
									~&nbsp;&nbsp;<input type="text" id="end_time5" value=""  class="timeInput" />  
								</td>  
								<td style="width: 30%">
									<s:text name="prompt_volume" /><select size="1" id="volume5">
					                  <option value="0">0</option>
					                  <option value="1">1</option> 
					                  <option value="2">2</option> 
					                  <option value="3">3</option> 
					                  <option value="4">4</option> 
					                  <option value="5">5</option> 
					                  <option value="6">6</option> 
					                  <option value="7">7</option> 
					                  <option value="8">8</option> 
					                  <option value="9">9</option> 
					                  <option value="10">10</option> 
					                </select>  
								</td> 
							</tr>
							<tr> 
								<td colSpan="4" align="center">
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
								<td style="width: 30%" align="right"> 
									<span id="c_start_time1">__:__</span>
								</td>  
								<td style="width: 30%">
									~&nbsp;&nbsp; 
									<span id="c_end_time1">__:__</span>
								</td>  
								<td style="width: 40%"> 
									<s:text name="prompt_volume" /> 
									<span id="c_volume1"></span>
								</td> 
							</tr> 
							<tr> 
								<td style="width: 30%" align="right"> 
									<span id="c_start_time2">__:__</span>
								</td>  
								<td style="width: 30%">
									~&nbsp;&nbsp; 
									<span id="c_end_time2">__:__</span>
								</td>  
								<td style="width: 40%">
									<s:text name="prompt_volume" /> 
									<span id="c_volume2"></span>
								</td> 
							</tr>
							<tr> 
								<td style="width: 30%" align="right"> 
									<span id="c_start_time3">__:__</span>
								</td>  
								<td style="width: 30%">
									~&nbsp;&nbsp; 
									<span id="c_end_time3">__:__</span>
								</td>  
								<td style="width: 40%">
									<s:text name="prompt_volume" /> 
									<span id="c_volume3"></span>
								</td> 
							</tr>
							<tr> 
								<td style="width: 30%" align="right"> 
									<span id="c_start_time4">__:__</span>
								</td>  
								<td style="width: 30%">
									~&nbsp;&nbsp; 
									<span id="c_end_time4">__:__</span>
								</td>  
								<td style="width: 40%">
									<s:text name="prompt_volume" /> 
									<span id="c_volume4"></span>
								</td> 
							</tr>
							<tr> 
								<td style="width: 30%" align="right"> 
									<span id="c_start_time5">__:__</span>
								</td>  
								<td style="width: 30%">
									~&nbsp;&nbsp; 
									<span id="c_end_time5">__:__</span>
								</td>  
								<td style="width: 40%">
									<s:text name="prompt_volume" /> 
									<span id="c_volume5"></span>
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
	$("#li_volume").addClass("curItem"); 
	
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
		$("#start_time1"), $("#end_time1"), $("#volume1"), 
    	$("#start_time2"), $("#end_time2"), $("#volume2"), 
    	$("#start_time3"), $("#end_time3"), $("#volume3"), 
    	$("#start_time4"), $("#end_time4"), $("#volume4"), 
    	$("#start_time5"), $("#end_time5"), $("#volume5") 
    ]; 
	              	
	if (enable) {
		for (var i in configItemArray) {
			//configItemArray[i].removeAttr("disabled");
			configItemArray[i].prop("disabled", false);
		}   

		// setTimeout(function(){$(":button").focus();}, 50); 
	} else {
		for (var i in configItemArray) {
			//configItemArray[i].attr("disabled", "disabled");
			configItemArray[i].prop("disabled", true);
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
	var selectArray = [
        $("#volume1"), 
        $("#volume2"), 
        $("#volume3"), 
        $("#volume4"), 
        $("#volume5")
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
	for (var i=0; i<selectArray.length; ++i) { 
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
			rangeArray.push({start:startSecond, end:endSecond, value:selectArray[i].val()});
		}
	}  
	
	if (rangeArray.length <= 0) {
		// $("#end_time1").focus();
		setTimeout(function(){$("#end_time1").focus();}, 50); 
		return null;
	}

	var text = "";
	for (var i=0; i<rangeArray.length; ++i) { 
		var range = rangeArray[i];
		text += ("[" +  formatHHmm(range.start) + ","  +  formatHHmm(range.end) + 
				"," +  range.value + "]");
	} 
	return text;
}
 
function submitConfig() { 
	var remoteModify = false;
	var configText = "";
	
	if ($("#radio_remote:radio").is(":checked")) {
		configText = configString();
		if (!configText || configText.length <= 0) {
			return;
		} 
		remoteModify = true;
	} 
	 
	var params = {
		"selectIDs" : "<%=SelectIDs%>", 
		"remoteModify" : remoteModify, 
		"configString" : configText
	}; 
	$.post('/Pages/setting!putVolume', params,  function(result) {
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
	var assignText= "<%=AssignVolume != null && AssignVolume.length()>0? AssignVolume : "[00:00,23:59,8]"%>";
	var currentText= "<%=CurrentVolume%>";
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
		{start : $("#start_time1"), end : $("#end_time1"), value : $("#volume1")}, 
		{start : $("#start_time2"), end : $("#end_time2"), value : $("#volume2")}, 
		{start : $("#start_time3"), end : $("#end_time3"), value : $("#volume3")}, 
		{start : $("#start_time4"), end : $("#end_time4"), value : $("#volume4")}, 
		{start : $("#start_time5"), end : $("#end_time5"), value : $("#volume5")}
	]; 

	timeBounds = parseTimeBoundInts(assignText, function(val){return 0 <= val && val <= 10;});
	for (var i=0; i<inputArray.length; ++i) {
		var boundInput = inputArray[i];
		boundInput.start.val(formatHHmm(i < timeBounds.length? timeBounds[i].start : 0));
		boundInput.end.val(formatHHmm(i < timeBounds.length? timeBounds[i].end : 0));
		boundInput.value.val(i < timeBounds.length? timeBounds[i].value : 0); 
	}
	
	var spanArray = [
		{start : $("#c_start_time1"), end : $("#c_end_time1"), value : $("#c_volume1")}, 
		{start : $("#c_start_time2"), end : $("#c_end_time2"), value : $("#c_volume2")}, 
		{start : $("#c_start_time3"), end : $("#c_end_time3"), value : $("#c_volume3")}, 
		{start : $("#c_start_time4"), end : $("#c_end_time4"), value : $("#c_volume4")}, 
		{start : $("#c_start_time5"), end : $("#c_end_time5"), value : $("#c_volume5")}
	]; 
	timeBounds = parseTimeBoundInts(currentText, function(val){return 0 <= val && val <= 10;});
	for (var i=0; i<spanArray.length && i < timeBounds.length; ++i) {
		var boundSpan = spanArray[i]; 
		boundSpan.start.text(formatHHmm(timeBounds[i].start));
		boundSpan.end.text(formatHHmm(timeBounds[i].end));
		boundSpan.value.text(timeBounds[i].value);
	}
} 

function resetConfig() { 

	if ($("#radio_remote:radio").is(":checked")) {   
		var inputArray = [
			{start : $("#start_time1"), end : $("#end_time1"), value : $("#volume1")}, 
			{start : $("#start_time2"), end : $("#end_time2"), value : $("#volume2")}, 
			{start : $("#start_time3"), end : $("#end_time3"), value : $("#volume3")}, 
			{start : $("#start_time4"), end : $("#end_time4"), value : $("#volume4")}, 
			{start : $("#start_time5"), end : $("#end_time5"), value : $("#volume5")}
		]; 

		var defaultText = "[00:00,23:59,8]";
		var timeBounds = parseTimeBoundInts(defaultText, function(val){return 0 <= val && val <= 10;});
		for (var i=0; i<inputArray.length; ++i) {
			var boundInput = inputArray[i];
			boundInput.start.val(formatHHmm(i < timeBounds.length? timeBounds[i].start : 0));
			boundInput.end.val(formatHHmm(i < timeBounds.length? timeBounds[i].end : 0));
			boundInput.value.val(i < timeBounds.length? timeBounds[i].value : 0); 
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