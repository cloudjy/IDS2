<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<% String uu = request.getScheme() + "://"
				  + request.getServerName() + ":" + request.getServerPort()
				  + request.getContextPath();

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		
	<%@ include file="/theme.jsp" %>
	
		<link href="<%=uu %>/skins/default/css/css-<s:text name="langjs"/>.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" media="all" href="<%=uu %>/skins/default/css/niceforms-default.css" />
		<script src="<%=uu %>/js/jquery-1.7.2.min.js" language="javascript"></script>
		<script language="javascript" type="text/javascript" src="<%=uu %>/js/niceforms.js"></script>
		<script src="<%=uu %>/js/gnamp.js" language="javascript"></script>
        <script src="js/deployJava.js" language="javascript"></script>
	</head>
	<body style="margin:0px;padding:0px;border:0px;width:640px;height:480px;">

<%-- 	<applet width="640" height="480" archive="<%=basePath%>/applet/wjhk.jupload.jar, <%=basePath%>/applet/jakarta-commons-oro.jar, <%=basePath%>/applet/jakarta-commons-net.jar" code="wjhk.jupload2.JUploadApplet" id="uploader">
	<param name="postURL" value="<%=basePath%>/Pages/fileupload!upload?categoryId=<%=request.getParameter("categoryId")==null?0:request.getParameter("categoryId")%>&autoCheck=<%=request.getParameter("autoCheck")%>">
	<param name="allowHttpPersistent" value="true">
	<param name="debugLevel" value="0">
	<param name="lang" value="zh-CN">
	<param name="lookAndFeel" value="system">
	<param name="maxChunkSize" value="524288">
	<param name="readCookieFromNavigator" value="true">
	<param name="retryMaxNumberOf" value="8">
	<param name="retryNbSecondsBetween" value="5">
	<param name="showLogWindow" value="false">
	<param name="showStatusBar" value="false">
	<param name="allowedFileExtensions" value="jpg/jpeg/bmp/png/gif/avi/wmv/rm/mp2/mpg/mpeg/vob/mp4/mov/rmvb/flv/f4v/asx/asf/midi/wma/mp3">
	<param name="sessionCookieString" value="JSESSIONID=<%= request.getSession().getId() %>">
	<param name="codebase_lookup" value="false">
	<param name="httpUploadParameterName" value="upload">
	</applet> --%>
	
	    <script type="text/javascript">
        var attributes = {
            width: 640,
            height: 480,
            archive: "<%=basePath%>/applet/wjhk.jupload.jar, <%=basePath%>/applet/jakarta-commons-oro.jar, <%=basePath%>/applet/jakarta-commons-net.jar",
            code: "wjhk.jupload2.JUploadApplet",
            id: "uploader"
        };

        var parameters = {
            postURL: "<%=basePath%>/Pages/jfileupload!upload?categoryId=<%=request.getParameter("categoryId")==null?0:request.getParameter("categoryId")%>&autoCheck=<%=request.getParameter("autoCheck")%>",
            allowHttpPersistent: true,
            debugLevel: 0,
            lang: "<s:text name="lang"/>",
            lookAndFeel: "system",
            maxChunkSize: 524288,
            readCookieFromNavigator: true,
            retryMaxNumberOf: 8,
            retryNbSecondsBetween: 5,
            showLogWindow: false,
            showStatusBar: false,
            allowedFileExtensions: "jpg/jpeg/bmp/png/gif/pdf/avi/wmv/rm/mp2/mpg/mpeg/vob/mp4/mov/rmvb/flv/f4v/asx/asf/midi/wma/mp3",
            sessionCookieString: "JSESSIONID=<%= request.getSession().getId() %>",
            /* codebase_lookup: "false", */
    	    httpUploadParameterName: "upload"
        };
      
        deployJava.runApplet(attributes, parameters, '1.6'); 
        
    </script>	
    
</body>
</html>