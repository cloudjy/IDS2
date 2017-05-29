<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.gnamp.struts.vo.Item"%>
<%@page import="com.gnamp.struts.vo.GroupVo"%>
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
<link rel="stylesheet" type="text/css" href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" />
<link rel="stylesheet" type="text/css" href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css"  />
<link rel="stylesheet" type="text/css" href="${theme}/skins/default/css/jqtransform.css" media="all" />
<link rel="stylesheet" type="text/css" media="all" href="${theme}/skins/default/css/niceforms-default.css" />
<link rel="stylesheet" type="text/css" media="all" href="${theme}/skins/default/css/ipubs-dialog.css" />

<script src="${theme}/js/jquery-1.7.2.min.js" language="javascript"></script>
<script language="javascript" type="text/javascript" src="${theme}/js/niceforms.js"></script>
<script type="text/javascript" src="${theme}/js/jscroll.js"></script>
<script src="js/gnamp.js" language="javascript"></script>
<script type="text/javascript"	src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
<script type="text/javascript"	src="${theme}/js/jquery.jqtransform.js"></script>
<script type="text/javascript" src="${theme}/js/ipub.models-<s:text name="langjs"/>.js"></script>

<script type="text/javascript">
        $(function () {
            $('form').jqTransform({ imgPath: 'skins/default/images/'});
            $("#configtemp").addClass("hover");
		});
        function upload(){
        	showIPUBDialogStand("template!upload.action",{title:"<s:text name="uploadconfig"/>",width:650,height:230});
        }
</script>


</head>

<body>

	<!-- menu 开始-->
	<%@ include file="/admin/header.jsp" %>
	<!-- menu 结束-->

	<div class="topBj1"></div>

	<div class="mid1">
		<div class="bt1">
			<h2><s:text name="configlist"/></h2>
		</div>
		<div class="nr1">
			<!-- 左侧 开始-->
			<!-- 左侧 结束-->
			<!-- 右侧 开始-->
			<div class="right1"  style="position: static;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="shujBt" id="shujBt">
					<tr>
						<td class="first" style="width:220px"><s:text name="name"/></td>
						<td style="width:220px"><s:text name="description"/></td>
						<td style="width:220px"><s:text name="refvalue"/></td>
					</tr>
				</table>
				<div id="ee">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="shujTb" id="mytab">
						<%
						List<GroupVo> groups = (List<GroupVo>)request.getAttribute("groups");
						for(GroupVo vo : groups){
						%>
			                <%
			                
			                for(Item item : vo.getItem()){
			               %>
			                <tr id="<%=item.getName()%>">
											<td style="width:250px"><%=item.getName()%></td>
											<td style="width:280px"><%=item.getDesc()%></td>
											<td style="width:220px"><%=item.getCheckexpression()%></td>
							</tr>
							<%
								if(item.getItems()!=null&&item.getItems().size()>0){
			                		 for(Item item1 : item.getItems()){
			                			%> 
			                			<tr id="<%=item.getName()%>">
											<td style="width:250px"><%=item1.getName()%></td>
											<td style="width:280px"><%=item1.getDesc()%></td>
											<td style="width:220px"><%=item1.getCheckexpression()%></td>
										</tr>
			                			 <%
			                		 }
			                	}
							}
			                %>
						 	
						<% 
							}
						%>
	
				</table>
				</div>

				<div class="rightMenu">

					<ul class="left rightMenuLong">
						<li><a href="javascript:upload()"><s:text name="upload"/></a></li>
					</ul>
				</div>

				<!-- 右侧底部menu 开始-->
				
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

