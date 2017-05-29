<%@ page contentType="text/html; charset=utf-8" language="java" import="java.util.*" errorPage="" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="com.gnamp.server.model.*" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<%-- <style type="text/css">
body { 
overflow-x:hidden; /*隐藏水平滚动条*/ 
overflow-y:hidden; /*隐藏垂直滚动条*/ 
} 
</style>
 --%>
<link href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" media="all" href="${theme}/skins/default/css/niceforms-default.css" />
<script src="${theme}/js/jquery-1.7.2.min.js" language="javascript"></script>
<script src="js/jquery.ui.core.js" language="javascript"></script>
<script src="js/jquery.ui.datepicker.js" language="javascript"></script>

<%
Terminal terminal = (Terminal)request.getAttribute("terminal");
if(terminal==null){	
	return;
} 
%>
<script language="javascript"> 
	function loadPreviewFile() {
		ajaxCallback("terminal!previewfile", {"terminal.deviceId":<%=terminal.getDeviceId()%>}, function(result){
			var context = "";
			try{
				if ( typeof(result.data) != "string"){
					return;
				}
				var tip = result.data.substring(result.data.lastIndexOf("\\")+1, result.data.length-4);   
			    var yyyyMMdd = tip.substr(0, 8);
			    var HHmmss = tip.substr(9, 6);    
			    var yyyy_MM_dd = yyyyMMdd.substr(0, 4) + "-" +  yyyyMMdd.substr(4, 2)  + "-" +  yyyyMMdd.substr(6, 2);
			    var HH_mm_ss = HHmmss.substr(0, 2) + ":" +  HHmmss.substr(2, 2) + ":" +  HHmmss.substr(4, 2);
			    tip = yyyy_MM_dd + "_"+ HH_mm_ss;
				
			    tip = "<s:text name="sctime" />"+tip;
			    
				// context = "<img onload=\"setCenter(this);\" style=\"max-width:100%; max-height: 100%;\" alt=\""+tip+"\" title=\""+tip+"\" src=\"image?filePath="+result.data+"\"/>";
				
			    var image = new Image(); 
			    image.src = 'image?filePath='+result.data+'&c='+Date.parse(new Date());	
			   
				var brosIsIE = (navigator.appName == "Microsoft Internet Explorer");
	
				var requestCallback = function () {  
				   if ((brosIsIE? (image.readyState == "complete") : (image.complete == true))){  
					   var width = image.width* 540 / image.height; 
					   var height = 540;
					   var top = ((560 - 540) / 2);
					   var left = ((980 - width) / 2); 
	
					   context += "<div style=\"position: absolute; " + 
						   "left: " + left + "px; " + "top: " + top + "px; " + 
						   "width: " + width + "px; " + "height: " + height + "px; " + 
						   "background-color: GrayText;border: thin solid Black;\">"; 
					   context += "<img style=\"left: " + left + "px; " + "top: " + top + "px; " + 
						   "width: " + width + "px; " + "height: " + height + "px;\" " + 
						   // "src=\"image?filePath="+result.data + "\" " +  
						   "src=\"" + image.src + "\" "+ 
						   "alt=\""+tip+"\" title=\""+tip+"\"/>";	
				     		    
						context += "</div>";
						$("#file").html(context);
				   } else {
						$("#file").html("<s:text name="noscreenshot"/>");
				   }  
				}
			   
				if (brosIsIE){ //IE 
					image.onreadystatechange= requestCallback; 
				} else { 
					image.onload =  requestCallback;
				}
	 
			   return; 
			}catch(e){
				$("#file").html("<s:text name="noscreenshot"/>");
			}
		});
	}
	
	function ajaxCallback(url,param,callback,obj){
		$.ajax({
			type : "post",
			dataType : "json",
			url : url,
			data : param,
			complete : function() {
				//$('img').resizeimage();
			},
			success : function(msg) {
				callback(msg,obj);
			}});
	}
</script>

<script language="javascript"> 
	(function($){
	$.fn.resizeimage = function(){
		var imgLoad = function (url, callback) {
			var img = new Image();
			img.src = url;
			if (img.complete) {
				callback(img.width, img.height);
			} else {
				img.onload = function () {
					callback(img.width, img.height);
					img.onload = null;
				};
			};
		};
		var original = {
			width:$(window).width()
		};		
		return this.each(function(i,dom){
			var image = $(this);
			imgLoad(image.attr('src'),function(){
				var img = {
					width:image.width(),
					height:image.height()
				},percentage=1;
				if(img.width<original.width){
					percentage = 1;
				}else{
					percentage = (original.width)/img.width;
				}
				image.width(img.w=img.width*percentage-1).height(img.h=img.height*percentage);
				$(window).resize(function(){
					var w = $(this).width();
					percentage = w/img.width>1?1:w/img.width;
					var newWidth = img.width*percentage-1;
					var newHeight = img.height*percentage;
					image.width(newWidth).height(newHeight);
				});
			});
		});
	};
})(jQuery);
</script>
</head>

<body style="background-color:#121212">
	<div id="file" style="height: 560px; font-family: 方正黑体简体; width: 980px; background-color: #121212">
		
	</div>
</body>
</html>

<script language="javascript"> 
<% 
Object waiteImage = request.getParameter("waiteImage");
if (waiteImage != null && waiteImage.toString().trim().equalsIgnoreCase("true")) {
	
%> 
$("body").append("<div id='shadow' style='height: 100%;width: 100%;position: absolute;top: 0px;text-align: center;'>"
		+ "<span style='position: relative;top: 45%;' ><s:text name="prompt_wait_screen"/></span>"
		+ "<img style='position: absolute;left: 50%;top: 50%;z-index: 999;' src='${theme}/skins/default/images/updating.gif'/></div>");

function checkImageValid() { 
	$.ajax({ 
		type : "post",
		dataType : "json",
		url : "terminal!topreview",
		data : { "terminal.deviceId" : <%=terminal.getDeviceId()%>},
		complete : function() { 
		},
		success : function(msg) {  
			if (msg.success) { 
				loadPreviewFile(); 
				$("#shadow").hide(); 
			} else {
				setTimeout(checkImageValid, 5000); 
			} 
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) { 
			window.top.location.href='/'; 
		}
	});
	return false;  
}
checkImageValid();

<%} else {%>
loadPreviewFile();
<%}%>
</script>
