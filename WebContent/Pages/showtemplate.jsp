<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/template/Public/title.jsp" %>

<style type="text/css">
body { 
overflow-x:hidden; /*隐藏水平滚动条*/ 
overflow-y:hidden; /*隐藏垂直滚动条*/ 
} 
</style>

<script src="js/SlideTrans.js" type="text/javascript"></script>
<script src="js/MSClass.js" type="text/javascript"></script>
<script src="SlideTransv.js" type="text/javascript"></script>

</head>

<body style="background-color:#121212">
	<div style="height: 560px; font-family: 方正黑体简体; width: 980px; background-color: #121212">
		<%=request.getAttribute("innerHtml")==null ? "no view" : request.getAttribute("innerHtml").toString() %>
	</div>
</body>
</html>
