<%@page import="com.gnamp.struts.tree.ITree"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="com.gnamp.server.model.SourceCategory"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=8" /> 
<%
 	if(request.getAttribute("cate")==null){
 		out.print("the page is not found parameter id");
 		return;
 	}
 %>
<%@include file="/template/standjs.jsp" %>

<script type="text/javascript">
var parentId= <%=((SourceCategory)request.getAttribute("cate")).getParentId()%>;
	$(function(){
		$("#sp").before("<a class='root' href='javascript:void(0)' id='0' onclick='nodeclick(0,this)'><s:text name="root"/></a>");
		load();
	});
	function changedir(callfunc){
		var dirValue = $("#dir").val();
		if(dirValue==''||dirValue== 'undefined'){
			// alert("<s:text name="name_cannot_null"/>");
			$("#nameErrorDiv").showError("<s:text name="name_cannot_null"/>");
			return;
		}
		if(dirValue.length>12){
			//alert("<s:text name="grouplengmaxerror"/>");
			$("#nameErrorDiv").showError("<s:text name="grouplengmaxerror"/>");
			return;
		}
		ajaxCallback('tagcate!edit' ,
				{"source.categoryName":$("#dir").val(),
				"source.categoryId":"<%=((SourceCategory)request.getAttribute("cate")).getCategoryId()%>",
				"source.parentId":parentId
				},
				function(txt){
					callfunc(txt);
		});
	}
	
	function closewindow(result){
		if(result.success){
			callParentFunction("treeInternal.editNoded",result.data);
			return closeIFrame();
		}else{
			// alert("<s:text name="edititemfail"/>"); 
			if (result.data  && result.message && 
					(result.data == "NameCannotNull" || result.data == "NameExist") ) { 
				$("#nameErrorDiv").showError(result.message); 
			} else if (result.message) {
				alert(result.message); 
			}	else {
				alert("<s:text name="edititemfail"/>");
			} 
		}
	}
	
	
	function load(){
		ajaxCallback("tagcate!brother", {"id":<%=((SourceCategory)request.getAttribute("cate")).getCategoryId()%>}, loadCate);
	}
	var lastID = -1;
	function loadCate(result){
		if(!result.success){
			alert(result.message);
			return;
		}
		
		$(result.data).each(function(i,f) {
			$("#sp").after("<span>-></span><a href=\"#\" id=\""+f.id+"\""+
					" onclick=\"nodeclick("+f.id+",this)\" >"+f.name+"</a>");
			lastID = f.id;
		});
		$("#parent a:last").trigger("click");
	}
	function showObjectList(result,obj){
		if(!result.success){
			alert(result.message);
			return;
		}
		 if ($(obj).is('select')) {
			changeObjectTree(result);
		 } else {
			loadObjectTree(obj,result);
		 }	
		
	}
	function loadObjectTree(obj,msg){
		 $(obj).nextAll().remove();		
		 var optionHtml = ""; 
		 if(msg.data != null && msg.data.length>0){	
				optionHtml +="<span>-></span><select name=\"groupSelector\" onchange=\"nodeclick(0,this)\" id=\"groupSelector\">";
				optionHtml +="<option value=\"0\"><s:text name="select"/></option>";
				$.each(msg.data, function(i, g) {		 							
					optionHtml += "<option value=\""+g.id+"\">"
							+ g.name + "</option>";
				});
				
				optionHtml +="</select>";
				
				$(obj).after(optionHtml);
		 }
	}
	
	function changeObjectTree(msg){
		var groupSelector = $("#groupSelector");
		var oldId = groupSelector.val();
		cateId = oldId;
		 parentId = oldId;
	    var oldText = groupSelector.find("option:selected").text();
		   groupSelector.html("");  
		
		   groupSelector.before("<a href=\"javascript:void(0)\" id=\""+oldId+"\" onclick=\"nodeclick("+oldId+",this)\" >" + oldText + "</a><span id=\""+oldId+"s\">-></span>");
			
			if(msg.data != null && msg.data.length>0){	
				  var optionHtml = "";													 		
			
					optionHtml +="<option value=\"0\"><s:text name="select"/></option>";
					$.each(msg.data, function(i, g) {		 							
						optionHtml += "<option value=\""+g.id+"\">"
								+ g.name + "</option>";
					});
					
				 groupSelector.html(optionHtml);
			}
			else
				{
				  $("#"+oldId+"s").remove();
				  groupSelector.remove();
				}
	}
	
	 function nodeclick(pid, obj){
		 parentId = obj.id;
		cateId = obj.id;
		 var selfid = <%=((SourceCategory)request.getAttribute("cate")).getCategoryId()%>;
		 var groupSelector = $("#groupSelector");
		 if ($(obj).is('select')) {
	    	pid = (groupSelector.length > 0)? groupSelector.val() : 0;
		 }
		 var param = {
				 "id" : pid,
				 "selfId" : selfid 
		 };
		 ajaxCallback("tagcate!listNotThis?source.categoryId=<%=((SourceCategory)request.getAttribute("cate")).getCategoryId()%>",param,showObjectList,obj);
		 return false;			
	}
	
</script>
</head>
<body style="min-width: 300px;min-height: 170px;">
<div class="tcWindow">
    <div class="tcBt"><img src="${theme}/skins/default/images/tcBt1.gif" width="5" height="35" class="left" /><img src="${theme}/skins/default/images/tcBt3.gif" class="right"/></div>
	<div class="tcNr">
	<form action="#" method="post" class="niceform">
	    <table width="100%" border="0" cellspacing="2" cellpadding="0" class="bdTab">
 		<tr>
			<td class="bt" style="width: 30%"><s:text name="name"/></td>
			<td>
			<input type="text" name="dir" id="dir" value="<%=((SourceCategory)request.getAttribute("cate")).getCategoryName()%>"/>
			<div id="nameErrorDiv" style="color:#FF0000;"></div>
			</td>
		</tr>
		<tr>
			<td class="bt" style="width:100px;"><s:text name="parentdir"/></td>
			<td id="parent">
			<span id="sp" style="color:blue"></span>
		     <span id="selector">
		     </span>
			</td>
		</tr>
		
		<tr>
		<td class="bt"></td>
		<td><input type="button" onclick="changedir(closewindow)" id="sub" value="<s:text name="yes"/>"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id="close" onclick="closeIFrame()" value="<s:text name="cancel"/>"/></td>
	</tr>
		</table>
    </form>
	</div>
	<div class="tcDi"><img src="${theme}/skins/default/images/tcDi1.gif" class="left" /><img src="${theme}/skins/default/images/tcDi2.gif" class="right"/></div>
</div>
<!-- 弹出窗口 结束-->
	
<script type="text/javascript">	
	$("input").keydown(function(event){
   		switch(event.keyCode) {
			case 13:
				changedir(closewindow);
				return false;
				break;
	   	}
   	});
</script>

</body>
</html>