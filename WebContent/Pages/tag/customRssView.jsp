<%@page import="com.gnamp.server.model.SystemSource"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css"
	href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" />
<link href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${theme}/skins/default/css/jqtransform.css"
	media="all" />
<link href="${theme}/skins/default/css/ipubs-dialog.css"
	rel="stylesheet" type="text/css" />
<link href="${theme}/skins/default/css/niceforms-default.css"
	rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${theme}/js/jquery-1.7.2.min.js"></script>
<script language="javascript" type="text/javascript"
	src="${theme}/js/niceforms.js"></script>
<script type="text/javascript" src="${theme}/js/jquery.jqtransform.js"></script>
<script type="text/javascript" src="${theme}/js/jscroll.js"></script>
<script type="text/javascript"
	src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
<script type="text/javascript"
	src="${theme}/js/ipub.models-<s:text name="langjs"/>.js"></script>


<script src="js/gnamp.js" language="javascript"></script>
<script src="../Pages/js/JScript.js"></script>
	
</head>
<body>
	<!-- 弹出窗口 开始-->
	<div class="tcWindow" style="width: 400px;">
		<table border="0" cellpadding="0" cellspacing="0" class="tcBt">
			<tr>
				<td style="width: 5px;"><img
					src="${theme}/skins/default/images/tcBt1.png" width="5" height="35"
					class="left" /></td>
				<td class="bg">

					<h2></h2>
				</td>
				<td style="width: 5px;"><img
					src="${theme}/skins/default/images/tcBt3.png" width="5" height="35"
					class="right" /></td>
			</tr>
		</table>

		<div class="tcNr">
			<div
				style="height: 150px; overflow: auto; border-right: #ccc solid 2px;">
				<form class="niceform" id="form1">
					<div style="width: 350px; height: 135px; font-size: 12px"
						id="content"><%=request.getAttribute("rssContext") %></div>
				</form>
			</div>
			<div style="clear: both; height: 1px; overflow: hidden;"></div>
		</div>
		
		
		<table border="0" cellpadding="0" cellspacing="0" class="tcDi">
			<tr>
				<td style="width: 5px;"><img
					src="${theme}/skins/default/images/tcDi1.png" width="5" height="8"
					class="left" /></td>
				<td class="bg"></td>
				<td style="width: 5px;"><img
					src="${theme}/skins/default/images/tcDi2.png" width="5" height="8"
					class="right" /></td>
			</tr>
		</table>
		
	</div>
	<!-- 弹出窗口 结束-->
	<!-- 表格间隔变色 -->
	<script>
		// Give classes to even and odd table rows for zebra striping.
		$(document).ready(function() {

			$('tr:nth-child(even)').addClass('alt');

			$('td:contains(Henry)').nextAll().andSelf().addClass('highlight');

			$('th').parent().addClass('table-heading');
		});
	</script>
</body>
</html>