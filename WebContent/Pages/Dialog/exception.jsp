<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="0" />
 

<% 
	Object attrObject = null;
	attrObject = request.getAttribute("exception");
	Exception mException = null;
	if (attrObject != null && attrObject instanceof Exception) 
		mException = (Exception)attrObject;
	else 
		mException = new Exception("getAttribute null or type mismatch"); 
%>
 
</head>

<body>  
	<%= mException.getMessage() %> <br/>
	<% mException.printStackTrace(response.getWriter()); %> 
</body>
</html>
