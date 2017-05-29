<%@ page contentType="text/html; charset=utf-8" language="java" import="java.util.*" errorPage="" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="com.gnamp.server.model.File" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<%@include file="/template/standjs.jsp" %>
<%
File file = (File)request.getAttribute("file");
if(file==null){
	return;
}
%>
<script language="javascript">
var cateId = -1;
	$(function() {
		loadNew();
	});
	function subSuccess(result){
		if(result.success){
			window.close();
			return;
		}else{
			$('#file').html(result.message);
			alert(result.message);
		}
	}
	
	var fileId = <%=file.getFileId()%>;
	var fileType = <%=file.getType()%>;
	var t = n = 0, count=5;
	    
    function showAuto()
    {
        n = n >=(count - 1) ? 0 : ++n;
       	$("img[id="+n+"]").show();
       	$("img:not([id="+n+"])").hide();
    }
	
	function loadNew(){
		var context = "";
		var filetype= <%=file.getType()%>;
		var fileflag = <%=file.getFlag() %>;
		var segmentnum = <%=file.getSegmentNum() %>;
		if(filetype==<%=File.IMAGE%>){
			if(fileflag==<%=File.FLAG_PDF %>){
				for(var i=0;i<segmentnum;i++){
					context += "<img  width=\"352\" height=\"240\"  id=\""+i+"\" src=\"file!previewfilenew?file.fileId="+fileId+"&file.type="+filetype+"&previewNumber="+i+"\"/>";
				}
				$("#file").html(context);
				count=$("#file > img").length;
		        $("#file > img:not(:first-child)").hide();
		        t = setInterval("showAuto()", 1000);
			}else{
				context += "<img  id=\""+i+"\" src=\"file!previewfilenew?file.fileId="+fileId+"&file.type="+filetype+"\"/>";
				$("#file").html(context);
			}
		}else if(filetype==<%=File.VIDEO%>){
			for(var i=0;i<5;i++){
				context += "<img  width=\"352\" height=\"240\"  id=\""+i+"\" src=\"file!previewfilenew?file.fileId="+fileId+"&file.type="+filetype+"&previewNumber="+i+"\"/>";
			}
			$("#file").html(context);
			count=$("#file > img").length;
	        $("#file > img:not(:first-child)").hide();
	        t = setInterval("showAuto()", 1000);
		}else if(filetype==<%=File.FLASH%>){
			context += "<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" " + 
				"style=\"margin:0px;border:0px;padding:0px;\"" +
				"codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,40,0\" " + 
				"name=\"movieObject\" id=\"movieObject\"> " + 
				"<param name=\"movie\" value=\"file!previewfilenew?file.fileId="+fileId+"&file.type="+filetype+"\"> " + 
				"<param name=\"quality\" value=\"high\"> " +  
				"<param name=\"bgcolor\" value=\"#ffffff\"> " + 
				"<embed " + 
					"src=\"file!previewfilenew?file.fileId="+fileId+"&file.type="+filetype+"\" " + 
					"quality=\"high\" bgcolor=\"#ffffff\" " + 
					"style=\"margin:0px;border:0px;padding:0px;\"" +
					"name=\"movieEmbed\" id=\"movieEmbed\" align=\"\" type=\"application/x-shockwave-flash\" " + 
					"pluginspage=\"http://www.macromedia.com/go/getflashplayer\"> " +  
				"</embed> " + 
				"</object> ";
			$("#file").html(context);
		}
		
	}
	
</script>
</head>

<body>
	<div id="file"></div>
</body>
</html>
