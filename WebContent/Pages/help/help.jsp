<%@page import="com.gnamp.struts.filter.Context"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">


<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=8" />
<%@include file="/template/Public/title.jsp" %>

<style>
 html { overflow: scroll; } 
</style>

<link rel="stylesheet" type="text/css" 
	href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" />
<link href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet"
	href="${theme}/skins/default/css/jqtransform.css" media="all" />
    
<script type="text/javascript" src="${theme}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${theme}/js/jquery.jqtransform.js"></script>
<script type="text/javascript" src="${theme}/js/jscroll.js"></script> 
<script type="text/javascript" src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
<script type="text/javascript" src="${theme}/js/ipub.models-<s:text name="langjs"/>.js"></script>
<link href="${theme}/skins/default/css/ipubs-dialog.css" rel="stylesheet" type="text/css" />
  
<script src="js/JScript.js"></script> 
<script src="js/gnamp.js"></script>

<script type="text/javascript" src="js/duma.js"></script>
<script type="text/javascript"> 
	$(function () {   
	    $("#bangzhu").addClass("hover");  
	    onPageLoad();
	}); 

	function onPageLoad() {
		var image_dir = "${theme}/skins/default/images/";
		var items = {
			"quick_start" : {name : "<s:text name="text_quick_start" />", url : "/Pages/help/quick_start-<s:text name="langjs"/>.htm", image: image_dir + "quick_start.png"}, 
			"faq" : {name : "<s:text name="text_faq"/>", url : "/Pages/help/faq-<s:text name="langjs"/>.htm", image: image_dir + "faq.png"},
			<% if (Context.getProduct().equalsIgnoreCase("iDS")) { %>
			"instructions" : {name : "<s:text name="text_instructions"/>", url : "/Pages/help/instructions-<s:text name="langjs"/>.htm", image: image_dir + "instructions.png"},
			<% } %>
			"version" : {name : "<s:text name="text_version"/>", url : "/Pages/help!version", image: image_dir + "version.png"}
		};
        var escapeJQueryDiv =  $("<div/>"); 
		var content = "";
		$.each(items, function(i, v) { 
			var param = escapeJQueryDiv.text(v.url).html();	
			var name = escapeJQueryDiv.text(v.name).html();	  
			var image = escapeJQueryDiv.text(v.image).html();	
			content += "<li class=\"relative\" onclick=\"onItemSelect(this, &quot;" + param + "&quot;);\" " + 
					"title=\"" + name + "\">" + 
					"<image src=\"" + image + "\"/>" + 
					name + "</li>";
		});
		$("#con_one_1").html(content);

		//$("#con_one_1 > li.relative:first").click();
		$("#con_one_1 > li.relative:first").trigger("onclick");
		
	}
	
	function onItemSelect(li, url) {
		$("#con_one_1 > li.relative").each(function(){ 
			 $(this).removeClass("hover"); 
		}); 
		
		if (li && $(li).length > 0) {
			$(li).addClass("hover");
		}
		$("#right_content").attr("src", url? url : "");
		return false;
	}
</script>

</head>
<body>		
	<!-- menu 开始-->
	<%@ include file="/header.jsp" %>
	<!-- menu 结束-->

	<div class="topBj1"></div> 
	
	<div class="mid1">
		<div class="bt1">
			<h2 id="one1" ><s:text name="header_help" /></h2>
			<ul class="tab1">
			</ul>
		</div>
		<div class="nr1">
			<!-- 左侧 开始-->
			<div class="left1">
                <div>
                    <ul id="con_one_1" class="leftMenu2 leftMenu3 padd"> 
                    </ul>
                    <ul id="con_one_2" style="display: none"></ul>
                    <ul id="con_one_3" style="display: none"></ul>
                </div>
                <div class="leftButtonBox2"> 
                </div>
            </div>
			<!-- 左侧 结束-->
	
			<!-- 右侧 开始-->
			<div class="right1"> 
				<iframe id="right_content" name="right_content" 
					style="overflow-x:hidden;overflow-y:auto;width:100%;height:100%;border:0px;" 
					src="#"></iframe>
			</div>

			<!-- 右侧 结束-->
			<div class="clearit"></div>
		</div>
		<div class="di1"></div>
	</div>

	<%@ include file="/buttom.jsp"%>
	<!-- 导航hover-->
	<!-- 导航二级菜单-->
</body>
</html>
