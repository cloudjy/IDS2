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
	String srcUrl = _actionName + "!ttsTextAudioFile" + 
			"?ttsText.taskId="+ttsText.getTaskId() + 
			"&ttsText.programId="+ttsText.getProgramId() + 
			"&ttsText.textId=" + ttsText.getTextId();
	 
	java.io.File file = new java.io.File(request.getAttribute("ttsAudio").toString());
	if (!file.exists() || file.isDirectory()) { //
		// throw new HttpException(404, "Cannot find file: " + file.getAbsolutePath());
	} 
	String rootPath = this.getServletContext().getRealPath("/");
	String audioPath = file.getAbsolutePath(); 
	audioPath = audioPath.substring(rootPath.length());
	audioPath = audioPath.replace('\\', '/');
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<link href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" rel="stylesheet" type="text/css" />
<script src="${theme}/js/jquery-1.7.2.min.js"></script>
<script language="javascript" type="text/javascript" src="${theme}/js/niceforms.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="${theme}/skins/default/css/niceforms-default.css" />

<link rel="stylesheet" type="text/css"
	href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" /> 
<script src="${theme}/js/ipub.models-<s:text name="langjs"/>.js" type="text/javascript"></script>
<script type="text/javascript"
	src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>   
<link href="${theme}/skins/default/css/ipubs-dialog.css" rel="stylesheet" type="text/css" />
<script src="js/gnamp.js" language="javascript"></script>
<script type="text/javascript">  

	$(function () {  
		loadPlayer();
	}); 

	function loadPlayer(){ 
 
		var playerHtml = "";
		var activeX = "<object classid=\"clsid:22D6F312-B0F6-11D0-94AB-0080C74C7E95\" " + 
			"style=\"width:100%;height:45px;margin:-3px 0px 0px 0px;border:0px;padding:0px;outline-offset:0px;\" " + 
			"codebase=\"http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701\" " + 
			"name=\"wmObject\" id=\"wmObject\"> " + 
			"<param name=\"FileName\" value=\"${theme}/<%=audioPath%>\"> " + 
			"<param name=\"autoStart\" value=\"true\"> " +  
			"<param name=\"bgcolor\" value=\"#ffffff\"> " + 
			"<param name=\"AudioStream\" value=\"-1\"> " + 
			"<param name=\"fullScreen\" value=\"0\"> " +  
			"<embed " + 
				"src=\"${theme}/<%=audioPath%>\" " +  
				"autoStart=\"true\" bgcolor=\"#ffffff\" " + 
				"style=\"width:100%;height:45px;margin:-3px 0px 0px 0px;border:0px;padding:0px;outline-offset:0px;\" " + 					
				"name=\"wmEmbed\" id=\"wmEmbed\" align=\"\" type=\"application/x-mplayer2\" " + 
				"pluginspage=\"http://www.microsoft.com/Windows/MediaPlayer/\"> " +  
			"</embed> " + 
			"</object> "; 					
		var html5audio = "<audio  controls=\"controls\" autoplay=\"autoplay\" style=\"width:100%;\">" + 
				"<source src=\"${theme}/<%=audioPath%>\"  type=\"audio/mpeg\" />" +
				"<p>Your browser does not support the audio element.</p>" + 
				"</audio>";
		if ($.browser.msie && $.browser.version && $.browser.version < "9.0") {
			playerHtml = activeX;
		} else {
			playerHtml = html5audio ;
		}
		$("#playerDiv").html(playerHtml); 
		return false;
	}
</script>
 <style>
 .NFTextareaRight{
    padding-right:3px;
    padding-bottom:0px;
 }
 </style>
</head>
<body style="overflow-y:hidden;">
  	<!-- 弹出窗口 开始-->
	<div class="tcWindow">
		<div style="display:table;position: absolute;left:0px;top:0px;width:100%;height:50px;margin: 0px;">
			<div id="playerDiv" name="playerDiv" style="display: table-cell; vertical-align:middle;"> 	
				<!-- 
				<audio controls="controls" autoplay="autoplay" style="width:100%;">  
					<source src="${theme}/<%=audioPath%>"  type="audio/mpeg" />
					Your browser does not support the audio element.
				</audio> 
				 -->
			</div>
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

