<%@page import="net.sf.json.util.JSONUtils"%>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="java.util.*" %>
<%@ page import="com.gnamp.struts.utils.JsonUtils" %>
<%

Map<String,List<String>> errors = (Map<String,List<String>>)request.getAttribute("fieldErrors");
JsonUtils.writeFieldMessage(response, request.getAttribute("fieldErrors"));

%>