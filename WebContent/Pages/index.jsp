<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">


<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=8" />
<%@include file="/template/Public/title.jsp" %>

<style>
 html { overflow: scroll; } 
</style>

<link rel="stylesheet" type="text/css" 
	href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" />
<link href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet"
	href="${theme}/skins/default/css/jqtransform.css" media="all" />
    
<script type="text/javascript" src="${theme}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${theme}/js/jquery.jqtransform.js"></script>
<script type="text/javascript" src="${theme}/js/jscroll.js"></script> 
<script type="text/javascript" src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
<script type="text/javascript" src="${theme}/js/ipub.models-<s:text name="langjs"/>.js"></script>
<link href="${theme}/skins/default/css/ipubs-dialog.css" rel="stylesheet" type="text/css" />
  
<script src="js/JScript.js"></script> 
<script src="js/gnamp.js"></script>

<script type="text/javascript" src="js/duma.js"></script>
<script type="text/javascript"> 
$(function () {  
    $("#shouye").addClass("hover");
    queryStatistics();
});

<%
Enumeration<java.net.NetworkInterface> netInterfaces = java.net.NetworkInterface.getNetworkInterfaces(); 
java.net.InetAddress inetAddr = null; 
String CSTM_SHORT = "" + request.getAttribute("shortName");
String LOCAL_IP = "";
String WEB_PORT = "" + request.getServerPort();
String MESSAGE_PORT = "" + request.getAttribute("devicePort");// "19099";  
boolean CONTORL_ALIVE = (Boolean)request.getAttribute("contorlServerAlive");
/*
while (netInterfaces.hasMoreElements()) { 
	java.net.NetworkInterface networkInterface = (java.net.NetworkInterface)netInterfaces.nextElement(); 
	Enumeration<java.net.InetAddress> addrEnum = networkInterface.getInetAddresses(); 
	inetAddr = addrEnum != null && addrEnum.hasMoreElements()? addrEnum.nextElement() : null;  
	if (inetAddr != null && !inetAddr.isLoopbackAddress() && 
			inetAddr.getHostAddress() != null && inetAddr.getHostAddress().indexOf(":") == -1) {  
		LOCAL_IP = inetAddr.getHostAddress(); 
		break;
    } 
}
*/
LOCAL_IP = request.getLocalAddr();
WEB_PORT = "" + request.getLocalPort(); //"" + request.getAttribute("webPort"); // request.getLocalPort()
%>

function colorNumber(num) {  
	return num? "<font color=\"#FF0000\">" + num + "</font>" : num;
}
function queryStatistics() {  
	ajaxCallback("index!statistics", null, function(result) {
		if (!result || !result.success || !result.data) {
			return false;
		}

		var info = result.data; 
		var CONTORL_ALIVE = <%=CONTORL_ALIVE%>;
		
		var htm = "<p><strong style=\"font-size: 10pt;font-weight: bold;\"><s:text name="server_status"/></strong></p>" + 
				"<p style=\"line-height: 8px\">&nbsp;</p>" + 
				"<p><s:text name="server_ip"/><%=LOCAL_IP%></p>"  + 
				"<p><s:text name="web_port"/><%=WEB_PORT%></p>"  + 
				"<p><s:text name="control_port"/><%=MESSAGE_PORT%></p>"  + 
				"<p><s:text name="customer"/><%=CSTM_SHORT%></p>"  + 
				"<p style=\"line-height: 8px\">&nbsp;</p>" + 
				"<p><s:text name="control_service"/>" + (CONTORL_ALIVE? "<s:text name="state_running"/>" : "<s:text name="state_unknown"/>") + "</p>"  + 
				"<p><s:text name="web_service"/></p>"  + 
				"<p><s:text name="database_service"/></p>"  + 
				"<p style=\"line-height: 8px\">&nbsp;</p>" + 
				"<p><strong style=\"font-size: 10pt;font-weight: bold;\"><s:text name="program_player_status"/></strong></p>"  + 
				"<p style=\"line-height: 8px\">&nbsp;</p>" + 
				"<p><s:text name="text_total_player"/>" + info.terminalCount + "<s:text name="text_online_player"/>" + info.onlineTerminalCount + "<s:text name="text_number_online"/></p>" + 
				"<p style=\"line-height: 8px\">&nbsp;</p>" + 
				"<p><s:text name="text_loop_task"/>" + info.loopTaskCount + "<s:text name="text_number_in"/>" + colorNumber(info.uncheckLoopTaskCount) + "<s:text name="text_number_unaudite"/></p>"  + 
				"<p><s:text name="text_demand_task"/>" + info.demandTaskCount + "<s:text name="text_number_in"/>" + colorNumber(info.uncheckDemandTaskCount) + "<s:text name="text_number_unaudite"/></p>"  + 
				"<p><s:text name="text_insert_task"/>" + info.pluginTaskCount + "<s:text name="text_number_in"/>" + colorNumber(info.uncheckPluginTaskCount) + "<s:text name="text_number_unaudite"/></p>" + 
				"<p style=\"line-height: 8px\">&nbsp;</p>" + 
				"<p><s:text name="text_player_not_assgign_head"/>" + colorNumber(info.taskNullTerminalCount) + "<s:text name="text_player_not_assgign_tail"/></p>" + 
				"<p style=\"line-height: 8px\">&nbsp;</p>" + 
				"<p><s:text name="text_player_not_synch_head"/>" + colorNumber(info.taskNotUpdateTerminalCount) + "<s:text name="text_player_not_synch_tail"/></p>";

		$("#statistics").hide();
		$("#statistics").html(htm);
		$("#statistics").toggle("fast"); 
	});
	return false;
}

function tdJump(url) {
	if (url) {
		location.href = url;
	}
	return false;
}

</script>

	<style type="text/css"> 
		.index_background {
			position: absolute;
			left: 70px;
			top: 102px;
			width: 540px;
			height: 266px; 
			margin: 0 auto; 
			background: #FFFFFF;
			background-image: url(${theme}/skins/default/images/indexBg-<s:text name="langjs"/>.png); 
			background-size: contain; 
			background-repeat: no-repeat;
			/* background-position: center; */
		}
		.statistics_div{
			position: absolute; 
			left: 20px;
			top: 50px; 
			width: 269px;
			height: 340px;
			margin: 0 auto;
			line-height: 20px;
			font-size: 12px; 
			font-family: "微软雅黑", "微軟雅黑", "Calibri", "Arial"; 
			color: #555555;
			/*text-shadow: 1px 1px #FFFFFF;*/
			background: #FFFFFF;
		} 
	</style>
</head>
<body> 
	<!-- menu 开始-->
	<%@ include file="/header.jsp" %>
	<!-- menu 结束-->

	<div class="topBj1"></div> 
	
	<div class="mid1">
		<div class="bt1">
			<h2 id="one1" ><s:text name="index" /></h2>
			<ul class="tab1">
			</ul>
		</div> 
		<div class="nr1">
			<!-- 左侧 开始-->
			 <!-- div class="left1">
				 <div class="leftMenu">
		                <div class="dumascroll" style="height: 85%;">
							<form class="jqtransform"> 
								<ul id="con_one_1" class="hover leftMenu2 leftMenu3">  
								</ul>
							</form>
						</div>
				</div>
				<div  class="leftButtonBox" style="margin-top:0px;"> 
				</div> 
			</div -->
			<!-- 左侧 结束-->
	
			<!-- 右侧 开始class="right1"-->
			<div >   
				 <div style="position: absolute; width: 680px; height: 470px;background: #FFFFFF;">
				 	<div class="index_background">
				 	<table style="width: 100%; height: 100%; border-spacing: 0px; border: 0px;">
				 		<tr style="background:rgba(0,0,0,0);">
				 			<td style="width:33.33%;"></td>
				 			<td style="width:33.33%;cursor:pointer;" onclick="tdJump('file.action')"></td>
				 			<td style="width:33.33%;"></td>
				 		</tr>
				 		<tr style="background:rgba(0,0,0,0);">
				 			<td style="cursor:pointer;" onclick="tdJump('monitor.action')"></td>
				 			<td></td>
				 			<td style="cursor:pointer;" onclick="tdJump('looptask.action')"></td>
				 		</tr>
				 		<tr style="background:rgba(0,0,0,0);">
				 			<td></td>
				 			<td style="cursor:pointer;" onclick="tdJump('terminal.action')"></td>
				 			<td></td>
				 		</tr>
				 	</table>
				 	</div>
				 </div>
				 <div style="position: absolute; left: 680px;width: 319px; height: 470px;background: #FFFFFF;">
					<div  id="statistics" class="statistics_div"></div>
				 </div> 
			</div>
			<div style="font-size:12px;padding-left:20px;position: absolute;top: 415px;"><s:text name="bottom_prompt_homepage_1"/></div> 
			<div style="font-size:12px;padding-left:20px;position: absolute;top: 435px;"><s:text name="bottom_prompt_homepage_2"/></div> 

			<!-- 右侧 结束-->
			<div class="clearit"></div>
		</div>
		<div class="di1"></div>
	</div>

	<%@ include file="/buttom.jsp"%>
	<!-- 导航hover-->
	<!-- 导航二级菜单-->
</body>
</html>

<script type="text/javascript"> 
$(function() {
			$('tr:nth-child(even)').removeClass('alt');
			$('td:contains(Henry)').nextAll().andSelf().removeClass('highlight');
			$('th').parent().removeClass('table-heading');
		});
</script> 
