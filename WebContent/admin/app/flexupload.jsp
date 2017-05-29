<%@page import="java.net.URLEncoder"%>
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
	
	
		String redirectURL = basePath+"/admin/appupload!upload?UPLOAD_SESSIONID="+session.getId();
		String encodeURL = URLEncoder.encode(redirectURL, "utf-8");/// response.encodeRedirectURL(redirectURL);// response.encodeURL(redirectURL);
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
                "../flex/upload/<s:text name="langjs"/>/ipubupload.swf?filterType=TENHZ&requestUrl=<%=encodeURL%>", "flashContent", 
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