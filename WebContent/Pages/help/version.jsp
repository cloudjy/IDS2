<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">


<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=8" />
<%@include file="/build.jsp" %>
<%@include file="/template/Public/title.jsp" %>

<style>
 html {
 	overflow-x:hidden; 
 	font-family: "微软雅黑", "微軟雅黑", "Calibri", "Arial"; 
 } 
.version_div { 
	position: absolute;
	top: 120px; 
	width: 779px; 
	text-align: center;
	height: 50px;
	/*line-height: 50px;*/ 
	color:#272727;
	clear: both;
	padding-top: 10px;
	font-family: "微软雅黑", "微軟雅黑", "Calibri", "Arial"; 
	font-size: 10pt;
} 
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
	}); 
</script>   
</head>

<body style="background:url('');">
	<div class="version_div">
        <img src="${theme}/skins/default/images/versionFlag.png" style="width: 100px;height: 100px;"></img>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p><%=com.gnamp.struts.filter.ContextListener.product %><s:text name="logo_a"/>&nbsp;<%=com.gnamp.struts.filter.ContextListener.version %>&nbsp;<%=RELEASE_DATE_SVN %></p>
        <p style="color:#ADADAD;"><s:text name="proposal"/></p>
	</div>
</body>
</html>
