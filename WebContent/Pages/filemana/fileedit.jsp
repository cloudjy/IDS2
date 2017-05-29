<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.text.DecimalFormat"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="com.gnamp.server.model.File"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/template/standjs.jsp" %>

<%
List<Long> lists = (List<Long>)request.getAttribute("filelist");
if(lists==null){
	return;
}
Long fileId = 0L;
if(lists.size()==1){
	fileId = lists.get(0);
}
%>

<script language="javascript">
	var obj = null;
	var cateId = <%=request.getAttribute("currentCateId")==null?-1:request.getAttribute("currentCateId")%>;
	var lists =<%=lists%>;
	$(function() {
		load();
	});

	function sizeText(size) {	 
		var uint_1K = 1024;
		var uint_1M = 1024*1024;
		var uint_1G = 1024*1024*1024; 
		var uint = ""; 
		if (size > uint_1G) {
			size = (size/uint_1G).toString(); 
			uint = "GB";
		} else if (size > uint_1M) {
			size = (size/uint_1M).toString();
			uint = "MB";
		} else if (size > uint_1K) {
			size = (size/uint_1K).toString();
			uint = "KB";
		} else {
			size = (size).toString();
			uint = "B";
		} 
		var pos = size.indexOf(".");
		if (pos > 0) { 
			size = size.substr(0,pos+2);
		} 
		return "" + size + uint;
	}
	
	function sub() {
		var param = {};
		if (lists.length > 1) {
			param = {
				"file.categoryId" : (cateId == -1) ? 0 : cateId,
				"file.vender" : $("input[name='vender']").val(),
				"filelist" : []
			};
			copyParam(param.filelist, lists);
		} else {
			param = {
				"file.fileName" : $("input[name='filename']").val(),
				"file.vender" : $("input[name='vender']").val(),
				"file.description" : $("input[name='desc']").val(),
				"file.fileId" :<%=fileId%>,
				"file.categoryId" : (cateId == -1) ? 0 : cateId
			};
		}

		ajaxCallback("file!fileedit", $.param(param, true),
				function(result) {
					if (result.success) {
						callParentFunction("refreshFileAndVender");
						closeIFrame();
						return;
					} else {
						// alert(result.message);
						if (result.data  && result.message && 
								(result.data == "NameCannotNull" || 
										result.data == "NameExist" || 
										result.data == "ExtensionCannotModify") ) { 
							$("#nameErrorDiv").showError(result.message); 
						} else if (result.message) {
							alert(result.message); 
						}	else {
							alert("<s:text name="edititemfail"/>");
						}	
					}
		});
	}

	function initialSingletonData(fileid) {
		ajaxCallback("file!loadBean", {
			"file.fileId" : fileid
		}, function(result) {
			$("input[name=filename]").val(result.data.fileName);
			$("input[name=desc]").val(result.data.description);
			$("input[name=vender]").val(result.data.vender);
			$("#size").html(sizeText(result.data.size));
			$("#uploadtime").html(result.data.uploadTime);
			$("#uploaduser").html(result.data.uploadUser);
			$("#checktime").html(result.data.checkTime);
			$("#checkuser").html(result.data.checkUser);
		});
	}

	function initialMultiData() {
		$("input[name=filename]").remove();
		$("input[name=desc]").remove();
	}

	function load() {
		if (lists.length > 1) {
			initialMultiData();
		} else {
			initialSingletonData(lists[0]);
		}
		ajaxCallback("filecate!currentCate", {
			"id" : cateId
		}, loadCate);
		ajaxCallback("file!venders.action",null, function showvender(result){
			var optionHtml = "";
			for(var i=0; i<result.data.length; i++){
				var t = $('<div/>').text(result.data[i]).html();
				optionHtml += "<option value=\"" + t + "\" " + 
					"onclick=\"onVenderSelect();\">" + t + "</option>";
	  		}
			$("#selectVender").append(optionHtml);
		});	
	}
	function loadCate(result) {
		if (!result.success) {
			alert(result.message);
			return;
		}
		$("#sp").before("<a class=\"root\" href=\"#\" id=\"0\" onclick=\"nodeclick(0,this)\"><s:text name="root"/></a>");
		$(result.data).each(function(i, f) {
					$("#sp").after(
							"<span>-></span><a href=\"#\" id=\"" + f.id + "\""
									+ " onclick=\"nodeclick(" + f.id
									+ ",this)\" >" + f.name + "</a>");
		});

	}

	function showObjectList(result, obj) {
		if (!result.success) {
			alert(result.message);
			return;
		}
		if ($(obj).is('select')) {
			changeObjectTree(result);
		} else {
			loadObjectTree(obj, result);
		}

	}
	function loadObjectTree(obj, msg) {
		$(obj).nextAll().remove();
		var optionHtml = "";
		if (msg.data != null && msg.data.length > 0) {
			optionHtml += "<span>-></span><select name=\"groupSelector\" onchange=\"nodeclick(0,this)\" id=\"groupSelector\">";
			optionHtml += "<option value=\"0\"><s:text name='select'/></option>";
			$.each(msg.data, function(i, g) {
				optionHtml += "<option value=\""+g.id+"\">" + g.name
						+ "</option>";
			});

			optionHtml += "</select>";

			$(obj).after(optionHtml);
		}
	}

	function changeObjectTree(msg) {
		var groupSelector = $("#groupSelector");
		var oldId = groupSelector.val();
		cateId = oldId;
		var oldText = groupSelector.find("option:selected").text();
		groupSelector.html("");

		groupSelector.before("<a href=\"#\" id=\"" + oldId
				+ "\" onclick=\"nodeclick(" + oldId + ",this)\" >" + oldText
				+ "</a><span id=\""+oldId+"s\">-></span>");

		if (msg.data != null && msg.data.length > 0) {
			var optionHtml = "";

			optionHtml += "<option value=\"0\"><s:text name='select'/></option>";
			$.each(msg.data, function(i, g) {
				optionHtml += "<option value=\""+g.id+"\">" + g.name
						+ "</option>";
			});

			groupSelector.html(optionHtml);
		} else {
			$("#" + oldId + "s").remove();
			groupSelector.remove();
		}
	}

	function nodeclick(pid,obj) {
		cateId = obj.id;
		var selfid =<%=request.getParameter("id")%>;
		var groupSelector = $("#groupSelector");
		if ($(obj).is('select')) {
			pid = (groupSelector.length > 0) ? groupSelector.val() : 0;
		}
		var param = {
			"id" : pid,
			"selfId" : selfid
		};
		ajaxCallback("filecate!list", param, showObjectList,obj);
		return false;
	}

	function onVenderSelect() {
		//$("#vender").val($("#selectVender").find("option:selected").text()); 
		$("#vender").val($("#selectVender").val()); 
		return false;
	}
</script>
</head>

<body>
	<div class="tcWindow">
		<div class="tcBt">
			<img src="${theme}/skins/default/images/tcBt1.gif" width="5" height="35"
				class="left" /><img src="${theme}/skins/default/images/tcBt3.gif"
				class="right" />
		</div>
		<div class="tcNr">
			<form action="#" method="post" class="niceform">
				<table width="100%" border="0" cellspacing="2" cellpadding="0"
					class="bdTab">
					<%if(lists.size()==1){%>
					<tr>
						<td class="bt" width="101"><s:text name='fileedit.filename' /></td>
						<td width="309"><input style="width:250px;"  type="text" name="filename" /> 
							<div id="nameErrorDiv" style="color:#FF0000;"></div></td>
					</tr>
					<tr>
						<td class="bt" width="101"><s:text name='fileedit.desc' /></td>
						<td width="309"><input style="width:250px;" type="text" name="desc" /></td>
					</tr>
					<%} %>
					<tr>
						<td class="bt" width="101"><s:text name='fileedit.vender' /></td>
						<td width="309">
							<input style="width:250px;" type="text" name="vender" />
							<!--   
							<div style="position:relative;">
								<span style="width:18px;overflow:hidden;">
								<select id="selectVender" name="selectVender" style="width:268px;"  
									onchange="onVenderSelect();" ondblclick="onVenderSelect();"> 
								</select>
								</span>
								<input id="vender" name="vender" type="text" style="width:250px;position:absolute;left:0px;" />
							</div>
							 -->
						</td>
					</tr>
					<tr>
						<td class="bt" width="101"><s:text name='fileedit.location' /></td>
						<td width="309"><span id="sp" style="color: blue"> <span
								id="selector"></span>
						</span></td>
					</tr>
					<%if(lists.size()==1){%>
					<tr>
						<td class="bt" width="101"><s:text name='fileedit.size' /></td>
						<td width="309"><span id="size"></span></td>
					</tr>

					<tr>
						<td class="bt" width="101"><s:text name='fileedit.uploadtime' /></td>
						<td width="309"><span id="uploadtime"></span></td>
					</tr>

					<tr>
						<td class="bt" width="101"><s:text name='fileedit.uploaduser' /></td>
						<td width="309"><span id="uploaduser"></span></td>
					</tr>

					<tr>
						<td class="bt" width="101"><s:text name='fileedit.checktime' /></td>
						<td width="309"><span id="checktime"></span></td>
					</tr>

					<tr>
						<td class="bt" width="101"><s:text name='fileedit.checkuser' /></td>
						<td width="309"><span id="checkuser"></span></td>
					</tr>
					<%} %>
					<tr>
						<td class="bt"></td>
						<td><input type="button" onclick="sub()"
							value="<s:text name="yes"/>" />&nbsp;&nbsp;&nbsp;&nbsp;<input
							type="button" id="close" onclick="closeIFrame()"
							value="<s:text name="cancel"/>" /></td>
					</tr>
				</table>
			</form>
		</div>
		<div class="tcDi">
			<img src="${theme}/skins/default/images/tcDi1.gif" class="left" /><img
				src="${theme}/skins/default/images/tcDi2.gif" class="right" />
		</div>
	</div>

<script type="text/javascript">	
	$("input").keydown(function(event){
   		switch(event.keyCode) {
			case 13:
				sub();
				return false;
				break;
	   	}
   	});
</script>

</body>
</html>
