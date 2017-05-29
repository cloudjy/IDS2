<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=8" />
<%@include file="/template/Admin/admintitle.jsp" %>
<style>
 html { overflow: scroll; } 
</style>
<link rel="stylesheet" type="text/css"	href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" />
<link href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" rel="stylesheet"	type="text/css" />
<link rel="stylesheet"	href="${theme}/skins/default/css/jqtransform.css" media="all" />
<link rel="stylesheet" type="text/css" media="all" href="${theme}/skins/default/css/niceforms-default.css" />
<link rel="stylesheet" type="text/css" media="all" href="${theme}/skins/default/css/ipubs-dialog.css" />


<script src="${theme}/js/jquery-1.7.2.min.js" language="javascript"></script>
<script language="javascript" type="text/javascript" src="${theme}/js/niceforms.js"></script>
<script src="js/gnamp.js" language="javascript"></script>
<script type="text/javascript"	src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
<script type="text/javascript"	src="${theme}/js/jquery.jqtransform.js"></script>
<script type="text/javascript" src="${theme}/js/ipub.models-<s:text name="langjs"/>.js"></script>
<script type="text/javascript" src="js/app.js"></script>
<script type="text/javascript">
        $(function () {
            $('form').jqTransform({ imgPatd: 'skins/default/images/'});
            $("#app").addClass("hover");
	});
</script>
</head>

<body>
	<!-- menu 开始-->
	<%@ include file="/admin/header.jsp" %>
	<!-- menu 结束-->

	<div class="topBj1"></div>

	<div class="mid1">
		<div class="bt1">
			<h2><s:text name="programlist"/></h2>
		</div>
		<div class="nr1">
			<!-- 右侧 开始-->
			<div class="right1">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="shujBt" id="shujBt">
				</table>
				<div id="ee">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="shujTb" id="mytab">
                    </table>
				</div>


				<div class="rightMenu">

					<ul class="left rightMenuLong">
						<%--<li><a href="javascript:jupload()"><s:text name="upload"/>(J)</a></li> --%>
						<li><a href="javascript:upload()"><s:text name="upload"/></a></li>
						<li><a href="javascript:edit()"><s:text name="edit"/></a></li>
						<li><a href="javascript:remove()"><s:text name="delete"/></a></li>
					</ul>
				</div>

				<!-- 右侧底部menu 开始-->
				<div class="bottomMenu">
					<div class="rightSelect" style="width:200px;">
						
							<label onclick="selectedAll()" for="chk_sel_all" ><s:text name="selectall"/></label> | 
							<label onclick="deselect()" for="chk_sel_invert" >
									<s:text name="invertselection"/>
							</label> | 
							<label onclick="cancel()">
									<s:text name="cancel" />
							</label>
					</div>
					<a href="#"> <img src="${theme}/skins/default/images/refresh3.gif" border="0" class="ref" /></a>
					<div class="page1 page2" style="width:520px;">
						<%@ include file="/template/Public/page.jsp" %>
					</div>
				</div>
				
				<!-- 右侧底部menu 结束-->
			</div>
			<!-- 右侧 结束-->
			<div class="clearit"></div>
		</div>
		<div class="di1"></div>
	</div>
	<%@ include file="/buttom.jsp" %>
</body>
</html>
