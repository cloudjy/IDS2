<%@page import="java.util.Locale"%>
<%@page import="com.gnamp.struts.filter.Context"%>
<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%
	response.sendRedirect(request.getContextPath()+"/Pages/login?domain_login=true" + 
			(Context.isAlcoholVersion()? "&request_locale=en_US" : ""));
%>