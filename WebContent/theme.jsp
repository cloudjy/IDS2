<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	  + request.getServerName() + ":" + request.getServerPort()
	  + path + "/";
	session.setAttribute("theme", "default");
	String themeName = session.getAttribute("theme").toString();
	String themepath=basePath+"themes/"+themeName;
%>
