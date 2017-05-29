<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.gnamp.server.model.*"%>
<%@page import="com.gnamp.server.handle.*"%>

<%@ taglib uri="/struts-tags" prefix="s"%>

<% String uu = request.getScheme() + "://"
				  + request.getServerName() + ":" + request.getServerPort()
				  + request.getContextPath();

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="x-ua-compatible" content="ie=8" />
    
    <style type="text/css">
		body { 
		overflow-x:hidden; /*隐藏水平滚动条*/ 
		overflow-y:hidden; /*隐藏垂直滚动条*/ 
		} 
	</style>
    
    <link href="<%=uu %>/skins/default/css/css-<s:text name="langjs"/>.css" rel="stylesheet" type="text/css" />
    <link href="<%=uu %>/skins/default/css/jqtransform.css" media="all" rel="stylesheet" />
    <script type="text/javascript" src="<%=uu %>/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="<%=uu %>/js/jquery.jqtransform.js"></script>
    <script type="text/javascript" src="<%=uu %>/js/jscroll.js"></script>
    <script type="text/javascript" src="<%=uu %>/js/ipub.models.js"></script>
    
    <script src="Dialog/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
    <script src="js/deployJava.js" language="javascript"></script>
	
</head>
<body>
   <%
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ request.getContextPath();
	%>

              
		<%--    <applet width="800" height="575" archive="<%=bPath%>/applet/wjhk.jupload.jar, <%=bPath%>/applet/jakarta-commons-oro.jar, <%=bPath%>/applet/jakarta-commons-net.jar" code="wjhk.jupload2.JUploadApplet" id="uploader">
				<param name="postURL" value="" />
				<param name="allowHttpPersistent" value="true" />
				<param name="debugLevel" value="0" />
				<param name="lang" value="<s:text name="lang" /> " />
				<param name="lookAndFeel" value="system" />
				<param name="maxChunkSize" value="524288" />
				<param name="readCookieFromNavigator" value="true" />
				<param name="retryMaxNumberOf" value="8" />
				<param name="retryNbSecondsBetween" value="5" />
				<param name="showLogWindow" value="false" />
				<param name="showStatusBar" value="false" />
				<param name="allowedFileExtensions" value="log" />
				<param name="sessionCookieString" value="JSESSIONID=<%= request.getSession().getId() %>" />
				<param name="codebase_lookup" value="false" />
				<param name="httpUploadParameterName" value="upload" />
				</applet> --%>

	 <script type="text/javascript">
        var attributes = {
            width: 800,
            height: 575,
            archive: "<%=basePath%>/applet/wjhk.jupload.jar, <%=basePath%>/applet/jakarta-commons-oro.jar, <%=basePath%>/applet/jakarta-commons-net.jar",
            code: "wjhk.jupload2.JUploadApplet",
            id: "uploader"
        };
       
        var parameters = {
            postURL: "<%=basePath%>/Pages/logupload!upload",
            allowHttpPersistent: true,
            debugLevel: 0,
            lang: "<s:text name="lang" />",
            lookAndFeel: "system",
            maxChunkSize: 524288,
            readCookieFromNavigator: true,
            retryMaxNumberOf: 8,
            retryNbSecondsBetween: 5,
            showLogWindow: false,
            showStatusBar: false,
            allowedFileExtensions: "log",
            sessionCookieString: "JSESSIONID=<%= request.getSession().getId() %>",
            /* codebase_lookup: "false", */
    	    httpUploadParameterName: "upload"
        };
      
        deployJava.runApplet(attributes, parameters, '1.6'); 
        
    </script>	

</body>
</html>
