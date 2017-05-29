<%@page import="java.net.URLEncoder"%>
<%@page import="com.gnamp.server.model.SourceType"%>
<%@page import="com.gnamp.server.model.Source"%>
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
	</head>
	<body style="margin:0px;padding:0px;border:0px;width:640px;height:480px;">
	<%
	Source source = (Source)request.getAttribute("source");
	
		String redirectURL = basePath+"/Pages/flexfileTagUpload!upload?UPLOAD_SESSIONID="+session.getId()+"&source.sourceId="+source.getSourceId()+"&source.type="+source.getType();
		String encodeURL = URLEncoder.encode(redirectURL, "gbk");/// response.encodeRedirectURL(redirectURL);// response.encodeURL(redirectURL);
	%>
	<%!
		private String getSourceType(int type){
			switch(type){
			case SourceType.IMAGE:
				return "IMAGE";
			case SourceType.VIDEO:
				return "VIDEO";
			}
			return "IMAGE";
		}
	
	%>
	<script type="text/javascript" src="../flex/layout/<s:text name="langjs"/>/swfobject.js"></script>
        <script type="text/javascript">
            // For version detection, set to min. required Flash Player version, or 0 (or 0.0.0), for no version detection. 
            var swfVersionStr = "11.1.0";
            // To use express install, set to playerProductInstall.swf, otherwise the empty string. 
            var xiSwfUrlStr = "playerProductInstall.swf";
            var flashvars = {};
            var params = {};
            params.quality = "high";
            params.bgcolor = "#ffffff";
            params.allowscriptaccess = "sameDomain";
            params.allowfullscreen = "true";
            params.wmode="transparent";
            var attributes = {};
            attributes.id = "uploadFlex";
            attributes.name = "uploadFlex";
            attributes.align = "middle";
            swfobject.embedSWF(
                "../flex/upload/<s:text name="langjs"/>/ipubupload.swf?requestUrl=<%=encodeURL%>&filterType=<%=getSourceType(source.getType())%>", "flashContent", 
                "640", "480", 
                swfVersionStr, xiSwfUrlStr, 
                flashvars, params, attributes);
            // JavaScript enabled so display the flashContent div in case it is not replaced with a swf object.
            swfobject.createCSS("#flashContent", "display:block;text-align:left;");
        </script>
    <div id="flashContent">
			<a href="http://get.adobe.com/flashplayer" target="_black"><img src="./../skins/default/images/flash_128.jpg"/></a>
	</div>
    
</body>
</html>