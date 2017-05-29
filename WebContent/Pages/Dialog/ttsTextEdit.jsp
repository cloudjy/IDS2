<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page language="java" import="com.gnamp.server.model.TTSText"%>
<%@ page language="java" import="com.gnamp.server.handle.TTSUtil"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%
	String _actionName = "looptts";
	String _taskType = request.getAttribute("taskType").toString();
	if (_taskType.equalsIgnoreCase("loop")) {
		_actionName = "looptts";
	} else if ( _taskType.equalsIgnoreCase("demand")) {
		_actionName = "demandtts";
	} else if ( _taskType.equalsIgnoreCase("plugin")) {
		_actionName = "plugintts";
	}
	TTSText ttsText = (TTSText)request.getAttribute("ttsText");
	
	int playCount = ttsText.getPlayCount(); 
	boolean repeatMode = playCount<=0 || playCount == Integer.MAX_VALUE;  
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<meta http-equiv="Cache-Control" content="no-cache" /> 
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="1970-01-01 00:00:00" /> 

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
 
<script type="text/javascript" src="${theme}/Pages/js/gnamp.js"></script> 


<script type="text/javascript">
	$(function () { 
		var escapeJQueryDiv =  $("<div/>"); 
		var optionHtml = "";
		for (var categoryIndex in CategeroyArray){
			var item = CategeroyArray[categoryIndex]; 
			if (item && item.display && item.map) {
				var value = escapeJQueryDiv.text(categoryIndex).html();
				var display = escapeJQueryDiv.text(item.display).html();
				optionHtml += "<option value=\"" + value + "\">" + display + "</option>"; 
			}
		} 
		$("#voiceScreening").html(optionHtml);
		onVoiceScreeningChange(); 
		//onPlayRepeatRadioChange();

		var text = "<%= StringEscapeUtils.escapeJavaScript(ttsText.getText()) %>";
		$("#text").val(text);
		
		var voiceName = "<%=StringEscapeUtils.escapeJavaScript(ttsText.getVoiceName())%>";
		for (var categoryIndex in CategeroyArray){  
			var item = CategeroyArray[categoryIndex]; 
			if (item && item.map && item.map[voiceName]) {
				$("#voiceScreening").val(categoryIndex);
				onVoiceScreeningChange(); 
				$("#voiceName").val(voiceName);
				break;
			} 
		} 

		var speed = "<%= ttsText.getSpeed() %>";
        if ((!speed && speed !== 0) || !(0<=speed && speed <= 100)) { 
        	speed = 50;
        }
		$("#speed").val("" + speed); 
		  
		var playCount = "<%= ttsText.getPlayCount() %>"; 
		if (!playCount || playCount<=0 ) {
			playCount == <%= Integer.MAX_VALUE %>;
		} 
		if (playCount == <%= Integer.MAX_VALUE %>) {
			onPlayRepeatRadioChange();
		} else {
			$("#playCount").val("" + playCount);   
			onPlayCountRadioChange();
		}	
	}); 
	
	function trimEmpty(str) {
		return str? str.replace(/(^\s*)|(\s*$)/g, "") : "";
	}
	
	var CategeroyArray = {
		// "lang_0" : { display : "普通话", map : [
		////		{vaule:"xiaoyan":dispaly:"[普通话][小燕][青年女声]"},
		//		"xiaoyan":"[普通话][小燕][青年女声]",
		//	]};
	
		<%		
		java.util.Locale locale = null;
		Object i18nLocle = session.getAttribute(com.gnamp.struts.filter.Context.SESSION_LOCALE);
		java.util.Locale requestLocale = request.getLocale();
		if (null != i18nLocle && i18nLocle instanceof java.util.Locale) {
			locale = (java.util.Locale)i18nLocle;
		} else {
			locale = null != requestLocale? requestLocale : java.util.Locale.CHINA;
		}
		java.util.Collection<String> ttsCategory = TTSUtil.category(locale); 
		String jsCategory = "";
		int categoryIndex = 0;
		for (String category : ttsCategory) {
			String jsMap = "";
			java.util.Map<String, String> map = TTSUtil.voiceNames(category, locale);  
			java.util.Set<java.util.Map.Entry<String, String>> entrySet = map.entrySet();
			for(java.util.Map.Entry<String, String> pair : entrySet){
				String optionValue = StringEscapeUtils.escapeJavaScript(pair.getKey());
				String optionDisplay = StringEscapeUtils.escapeJavaScript(pair.getValue()); 
				jsMap += "\t\t\t\t\"" + optionValue + "\" : \"" + optionDisplay + "\", \n";  
			} 
			if (jsMap.length() > 0) {
				String optionDisplay = StringEscapeUtils.escapeJavaScript(category);
				jsCategory += ("\t\t\"category_" + categoryIndex + "\" : {display: \"" + optionDisplay + "\", map : {" + jsMap + "\t\t\t}}, \n");
				categoryIndex ++ ;
			}
		}
		%> 
		<%=jsCategory%>
	};
	
	function onVoiceScreeningChange() { 
		var categroyKey = $("#voiceScreening").val();
		var map = categroyKey && CategeroyArray[categroyKey] && CategeroyArray[categroyKey].map? 
				CategeroyArray[categroyKey].map : [];  
	
		var escapeJQueryDiv =  $("<div/>"); 
		var optionHtml = "";
		for (var i in map){
			var value = escapeJQueryDiv.text(i).html();
			var display = escapeJQueryDiv.text(map[i]).html();
			optionHtml += "<option value=\"" + value + "\">" + display + "</option>";
		} 
		$("#voiceName").html(optionHtml); 
  
	    return false;
	} 
	
	function onPlayRepeatRadioChange() { 
		$("#repeat_mode:radio").attr("checked", "checked"); 
		$("#count_mode:radio").removeAttr("checked");
		$("#playCount").attr("disabled", "disabled");  
	    return false;
	}
	function onPlayCountRadioChange() { 
		$("#repeat_mode:radio").removeAttr("checked");
		$("#count_mode:radio").attr("checked", "checked");  
		$("#playCount").removeAttr("disabled");  
	    return false;
	}
	
	function editTTSText() { 
		var text = trimEmpty($("#text").val());
		if(!text){
			alert("<s:text name="tts_content_null" />");
			return;
		}
		
        var speed = parseInt($("#speed").val(), 10); ;	 
        if ((!speed && speed !== 0) || !(0<=speed && speed <= 100)) { 
        	speed = 50;
        	$("#speed").val(""+speed);
    	} 
		
        var playCount = <%= Integer.MAX_VALUE %>;	 
        if ($("#repeat_mode:radio").is(":checked")) {  
    	} 
    	if ($("#count_mode:radio").is(":checked")) { 
    		playCount = parseInt($("#playCount").val(), 10); 
	    	if (!playCount || playCount <= 0) {
	    		$("#playCount").focus();
	    		$("#playCountErrorDiv").showError("<s:text name="tts_playcount_input_error"/>");
	        	return;
	    	}
    	} else { // if ($("#repeat_mode:radio").is(":checked"))
    	} 
    	
		var param = { 
				"ttsText.taskId" : <%=ttsText.getTaskId()%>,
				"ttsText.programId" : <%=ttsText.getProgramId()%>, 
				"ttsText.textId":<%=ttsText.getTextId() %>, 
				"ttsText.text" : text,  
				"ttsText.voiceName" : $("#voiceName").val(), 
				"ttsText.speed" : speed,
				"ttsText.playCount" : playCount,
		};   		
		
		var surl = "<%= _actionName %>!ttsTextEdit";  
		$.ajax({
			type : "post",
			dataType : "json",
			url : surl,
			data : param,
			complete : function() { },
			success : function(msg) {    				
				if(msg.success){
					callParentFunction("edited", 'ok');
					// eval("window.parent.edited(\"ok\")");
					return closeIFrame();
				} else {
					alert(msg.data);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				 window.top.location.href='/';
			}
		});
		return false;
	}
		
	function cancl(){
		 callParentFunction("edited", 'ok');
		return closeIFrame();
	} 	 		   
</script>
<style>
	.NFTextareaRight{
		padding-right:3px;
		padding-bottom:0px;
	}
</style>
</head>

<body> 
  	<!-- 弹出窗口 开始-->
	<div class="tcWindow">
		<div class="tcBt">
			<img src="${theme}/skins/default/images/tcBt1.gif" width="5" height="35"
				class="left" />
			<h2></h2>
			<img src="${theme}/skins/default/images/tcBt3.gif" class="right" />
		</div>
		<div class="tcNr">
			<form action="#" method="post" class="niceform">
			<table width="100%" border="0" cellspacing="2" cellpadding="0"
					class="bdTab">
				<tr>
					<td class="bt" style="width: 20%"><s:text name="tts_content" /></td>
					<td>
					<!-- <input type="text" style="width: 250px;" name="text" id="text" /> -->
						<textarea name="text" id="text" cols="50" rows="6"></textarea>
					</td>
				</tr> 
				<tr>
					<td class="bt" style="width: 20%"><s:text name="tts_voicename" /></td>
					<td align="left"> 
		             	<table border="0" cellspacing="0" cellpadding="0" >
							<tr>
								<td style="padding-left: 0px;">
									<s:text name="tts_voicename_screening1"/>
								</td>
								<td style="padding-left: 3px;">
								   <select size="1" id="voiceScreening" name="voiceScreening" onchange="onVoiceScreeningChange();">
					               </select>
								</td> 
								<td style="padding-left: 3px;">
									<s:text name="tts_voicename_screening2"/>
								</td>
								<td style="padding-left: 3px;">
								   <select size="1" id="voiceName" name="voiceName">
					               </select>
								</td> 
				             </tr>
		             	</table> 
		            </td>
				</tr> 
				<tr>
					<td class="bt" style="width: 20%"><s:text name="tts_speed" /></td>
					<td align="left">  
		             	<table border="0" cellspacing="0" cellpadding="0" >
							<tr>
								<td style="padding-left: 0px;padding-right: 3px;">
								 	<input type="text" name="speed" id="speed" value="50" /> 
								</td>
								<td style="padding-left: 0px;">
								 	[0-100]
								</td> 
				             </tr>
		             	</table> 
		            </td>
				</tr> 
				<tr>
					<td class="bt" style="width: 20%"><s:text name="tts_playcount" /></td>
					<td align="left">  
		             	<table border="0" cellspacing="0" cellpadding="0" >
							<tr>
								<td style="padding-left: 0px;padding-right: 3px;">
								 	<input type="radio" name="play_mode" id="repeat_mode" value="repeat"
								 		<%= repeatMode? "checked" : ""%>  style="margin: 0;" onchange="onPlayRepeatRadioChange();" /> 
								</td>
								<td style="padding-left: 0px;">
								 	<s:text name="tts_playmode_repeat"/>
								</td> 
				             </tr>
		             	</table>
		            </td>
				</tr>  
				<tr>
					<td class="bt" style="width: 20%"> </td>
					<td align="left">
						<table border="0" cellspacing="0" cellpadding="0" >
							<tr>
								<td style="padding-left: 0px;padding-right: 3px;">
								 	<input type="radio" name="play_mode" id="count_mode" value="count" 
								 		<%= repeatMode? "" : "checked"%> style="margin: 0;" onchange="onPlayCountRadioChange();"/>
								</td>
								<td style="padding-left: 0px;">
								 	<s:text name="tts_playmode_count1"/>
								</td>
								<td style="padding-left: 0px;">
									 <input id="playCount" name="playCount" value="1" maxLength="5" 
									   <%= repeatMode? "disabled" : ""%>
									 	onpaste="return false" style="ime-mode:disabled;width:35px;text-align:center;"               
						             	onkeypress="if ((event.keyCode<48 || event.keyCode>57) && event.keyCode!='.') event.returnValue=false;" />
					             </td>
					             <td style="padding-left: 0px;">
					             	<s:text name="tts_playmode_count2"/>
					             </td>  
					             <td style="padding-left: 0px;">
					             	<div id="playCountErrorDiv" style="color:#FF0000;"></div>
					             </td> 
				             </tr>
		             	</table> 
		            </td>
				</tr> 
				<tr>
					<td class="bt">&nbsp;</td>
					<td><input type="button" value="<s:text name="yes"/>"
						onclick="editTTSText()" style="line-height: 13px;" />&nbsp;&nbsp;&nbsp;&nbsp;<input
						type="button" onclick="closeIFrame();" value="<s:text name="cancel" />" /></td>
				</tr>
			</table>
			</form>
	
		</div>
		<div class="tcDi">
			<img src="${theme}/skins/default/images/tcDi1.gif" class="left" /><img
				src="${theme}/skins/default/images/tcDi2.gif" class="right" />
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

