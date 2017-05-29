<%@page import="com.gnamp.struts.vo.Option"%>
<%@page import="com.gnamp.struts.vo.Select"%>
<%@page import="com.gnamp.struts.vo.Item"%>
<%@page import="com.gnamp.struts.vo.GroupVo"%>
<%@page language="java" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
			"yyyy-MM-dd mm:hh:ss");
	java.util.Date currentTime = new java.util.Date();//得到当前系统时间 
	String str_date1 = formatter.format(currentTime); //将日期时间格式化
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=8" />
<%@include file="/template/Public/title.jsp" %>
<style>
 html { overflow: scroll; } 
</style>
<link rel="stylesheet" type="text/css" href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" />
<link rel="stylesheet" type="text/css" href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" />
<link rel="stylesheet" type="text/css" href="${theme}/skins/default/css/jqtransform.css" media="all" />
<link rel="stylesheet" type="text/css"  href="${theme}/skins/default/css/ipubs-dialog.css"/>
<script type="text/javascript" src="${theme}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${theme}/js/jquery.jqtransform.js"></script>
<script type="text/javascript" src="${theme}/js/jscroll.js"></script>
<script type="text/javascript" src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
<script type="text/javascript" src="${theme}/js/ipub.models-<s:text name="langjs"/>.js"></script>
<script type="text/javascript" src="js/gnamp.js"></script>
<script type="text/javascript" src="js/duma.js"></script>
<script type="text/javascript" src="js/config.js"></script>


<script type="text/javascript">
	$(function() {
		$("#peizhi").addClass("hover");
		$(".peizTb td").css("width", "200px");
		
	});
	
</script>

<style type="stylesheet">
	peizTb td:first{
		width:200px;
	}
	</style>
</head>
<body>
	<!-- menu 开始-->
	<%@ include file="/header.jsp"%>
	<!-- menu 结束-->

	<div class="topBj1"></div>

	<div class="mid1">
		<div class="bt1">
		<h2 id="one1" ><s:text name="configlist" /></h2>
			<ul class="tab1">
			</ul>
		</div>
		<div class="nr1">
			<!-- 左侧 开始-->
			 <div class="left1">
			 <div class="leftMenu">
	                <div class="dumascroll" style="height: 85%;">
						<form class="jqtransform">
							 <ul id="con_one_1" class="hover leftMenu2 leftMenu3">
							</ul>
						</form>
					</div>
			</div>
				<div class="leftButtonBox">
				<s:if test="#session.current_privilege.newconfig">
					<a href="javascript:add()"><s:text name="create"/></a>
				</s:if>
				<s:if test="#session.current_privilege.editconfig">
					<a href="javascript:update()"><s:text name="edit"/></a>
				</s:if>
				<s:if test="#session.current_privilege.delconfig">
					<a href="javascript:del()"><s:text name="delete"/></a>
				</s:if>
				<s:if test="#session.current_privilege.auditconfig">
					<a href="javascript:audit()"><s:text name="audit"/></a>
				</s:if>		
				</div>
				<div  class="leftButtonBox" style="margin-top:0px;">
					<a href="javascript:sortlist()">A-Z</a>
				</div>
				
				
			</div>
			<!-- 左侧 结束-->
			<!-- 右侧 开始-->
			<div class="right1">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="shujBt">
					<tr>
						<td class="first" style="width: 232px;"><s:text name="item"/></td>
						<td style="width: 233px;"><s:text name="value"/></td>
						<td><s:text name="referencevalue"/></td>
					</tr>
				</table>
				<%!private String getItem(Item item){
	                	StringBuffer buffer = new StringBuffer();
	                	buffer.append("<tr id=\""+item.getName()+"\">");
                		buffer.append("<td style=\"width:250px\">"+item.getDesc()+"</td>");
                		buffer.append("<td style=\"width:280px\">");
                		buffer.append((item.getSelect()!=null?getSelect(item.getSelect(), item.getName()):getInput(item)));
                		buffer.append("</td>");
                		buffer.append("<td style=\"width:220px\">"+item.getCheckexpression()+"</td>");
                		buffer.append("</tr>");
	                	if(item.getItems()!=null&&item.getItems().size()>0){
	                		for(Item i : item.getItems()){
	                			buffer.append(getItem(i));
	                		}
	                	}
	                	return buffer.toString();
                	}
	private String getInput(Item item) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<input type=\"text\" name=\"" + item.getName()
				+ "\" class=\"text1\" />");
		return buffer.toString();
	}

	private String getSelect(Select select, String name) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<input type=\"text\" name=\"" + name
				+ "\"  class=\"text1\"/>");
		/*buffer.append("<div><select name=\""+name+"\" size=\"1\">");
		for(Option option : select.getOptions()){
		buffer.append("<option value=\""+option.getValue()+"\">");
		buffer.append(option.getText());
		buffer.append("</option>");	
		}
		buffer.append("</select></div>"); */
		return buffer.toString();
	}%>
				<!-- 内容区域开始 -->
				<div id="ee">
					<form class="niceform">
						<%
						List<GroupVo> groups = (List<GroupVo>)request.getAttribute("groups");
						for(GroupVo vo : groups){
						%>
						<div class="memberBt"><%=vo.getName() %></div>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="peizTb">
							<%
			                
			                for(Item item : vo.getItem()){
			              		out.print(getItem(item));
							}
			                %>
						</table>

						<% 
							}
						%>
						<!-- 内容区域结束 -->

					</form>
				</div>
				
		<!-- 右侧底部menu 开始-->
				<div class="bottomMenu">
						<s:if test="#session.current_privilege.updateconfig">
							<a href="javascript:saveBean()" class="buttonStyle" style="position:absolute;right:10px;"><s:text name="save"/></a>
						</s:if>
				</div>
				<!-- 右侧底部menu 结束-->
			</div>

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
