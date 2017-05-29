<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	</head>
	<body style="border:0px;margin:0px;padding:0px;">

	<%
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ request.getContextPath();
	%>
	<applet width="640" height="480" archive="<%=basePath%>/applet/wjhk.jupload.jar, <%=basePath%>/applet/jakarta-commons-oro.jar, <%=basePath%>/applet/jakarta-commons-net.jar" code="wjhk.jupload2.JUploadApplet" id="uploader">
	<param name="postURL" value="<%=basePath%>/admin/appupload!upload">
	<param name="allowHttpPersistent" value="true">
	<param name="debugLevel" value="0">
	<param name="lang" value="<s:text name="langapplet"/>">
	<param name="lookAndFeel" value="system">
	<param name="maxChunkSize" value="524288">
	<param name="readCookieFromNavigator" value="true">
	<param name="retryMaxNumberOf" value="8">
	<param name="retryNbSecondsBetween" value="5">
	<param name="showLogWindow" value="false">
	<param name="showStatusBar" value="false">
	<param name="allowedFileExtensions" value="tenhz">
	<param name="sessionCookieString" value="JSESSIONID=<%= request.getSession().getId() %>">
	<param name="codebase_lookup" value="false">
	<param name="httpUploadParameterName" value="upload">
	</applet>
</body>
</html>