<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%> 
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="0" />

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

<% 
	String taskType = request.getAttribute("taskType").toString();
	String taskID = request.getAttribute("taskID").toString();
	String programID = request.getAttribute("programID").toString();
	String rectID = request.getAttribute("rectID").toString();

	String addWebUrlUrl = ""; 
				
	if (taskType.equals("loop")) { 
		addWebUrlUrl = "looprect!addWebUrl";  
	} else if (taskType.equals("demand")) { 
		addWebUrlUrl = "demandrect!addWebUrl";   
	} else if (taskType.equals("plugin")) { 
		addWebUrlUrl = "pluginrect!addWebUrl"; 
	}
%>

<script type="text/javascript"> 
	function onClickOk(){	 
		if($("#text").val() == '' || $("#text").val() == 'undefined'){ 
			$("#urlErrorDiv").showError("<s:text name="invalidUrl"/>"); 
			return false;
		}

		var html = $("#text").val().replace(/(^\s*)|(\s*$)/g, "");;  
		if (!html.match(/http:\/\/.+/) && !html.match(/https:\/\/.+/)){ 
			$("#urlErrorDiv").showError("<s:text name="invalidUrl"/>"); 
			return false;
		}  
	 
		$.post("<%= addWebUrlUrl %>", {
			"rectWebUrl.taskId":<%= taskID %>,
			"rectWebUrl.programId":<%= programID %>,
			"rectWebUrl.rectId":<%= rectID %>, 
			"rectWebUrl.playTime":$("#playTime").val(),
			"rectWebUrl.refreshTime":$("#refreshTime").val(),
			"rectWebUrl.url":html
            },
	    	function(txt) { 
				if(txt.success){
					callParentFunction("added", 'ok');
					return closeIFrame();
				}else{
					alert(txt.message);
				} 
			}
		); 
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
			<td><s:text name="webUrl" /></td>
			<td>
				<input type="text" style="width: 250px;" name="text" id="text" value="http://"/>
				<div id="urlErrorDiv" style="color:#FF0000;"></div>
			</td>
		</tr> 
		<tr>
			<td><s:text name="playTime" /></td>
			<td>
				<input type="text" style="width: 250px;" name="playTime" id="playTime" value="0"/>
			</td>
		</tr> 
		<tr>
			<td><s:text name="refreshTime" /></td>
			<td>
				<input type="text" style="width: 250px;" name="refreshTime" id="refreshTime"  value="0"/>
			</td>
		</tr>
		<tr>
				<td class="bt">&nbsp;</td>
						<td><input type="button" value="<s:text name="yes"/>"
							onclick="onClickOk()" style="line-height: 13px;" />&nbsp;&nbsp;&nbsp;&nbsp;<input
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

