<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" />
<link href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" media="all" href="${theme}/skins/default/css/niceforms-default.css" />
<link rel="stylesheet" type="text/css"  href="${theme}/skins/default/css/ipubs-dialog.css"/>

<script src="${theme}/js/jquery-1.7.2.min.js" language="javascript"></script>
<script type="text/javascript" src="${theme}/js/ipub.models-<s:text name="langjs"/>.js"></script>
<script type="text/javascript" src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
<script language="javascript" type="text/javascript" src="${theme}/js/niceforms.js"></script>
<script src="js/gnamp.js" language="javascript"></script>

<script type="text/javascript">
	$(function(){
		load();
	});
	var id = <%=request.getParameter("id")%>;
	function load(){
		ajaxCallback("config!loadbean",{"config.configId":id}, loadbean);
	}
	function loadbean(result){
		if(result.success){
			var data = result.data;
			$("input[name=name]").val(data.configName);
			$("input[name=description]").val(data.description);
			$("#createuser").html(data.createUser);
			$("#createtime").html(data.createTime);
		}
		
	}
	function copyParam(destination, source) {
	    for (var property in source)  
	    { 
	    	destination[property] = source[property];  
	    }
	    return destination;  
	} 
	function updateRole(){
		var param = {'config.configId':id,
				"config.configName":$("input[name=name]").val(),
				"config.description":$("input[name=description]").val()
				};
		ajaxCallback("config!editbase",param,function(result){
			issuccess = result.success;
			if(issuccess){
				window.returnValue = issuccess;
				callParentFunction("refreshData");
				closeIFrame();
			}
			else{
				if(typeof(result.message)!="undefined")
				{
					alert(result.message);
				}else{
					alert("<s:text name="edititemfail"/>");
				}
			}
				
		});
	}
	
	
	

</script>
</head>
<body>

<div class="tcWindow">
    <div class="tcBt"><img src="${theme}/skins/default/images/tcBt1.gif" width="5" height="35" class="left" />
    <img src="${theme}/skins/default/images/tcBt3.gif" class="right"/></div>
	<div class="tcNr">
	<form action="#" method="post" class="niceform">
	    <table width="100%" border="0" cellspacing="2" cellpadding="0" class="bdTab">
 		<tr>
			<td class="bt"><s:text name="configName"/></td>
			<td><input name="name" type="text"/></td>
		</tr>
		<tr>
			<td class="bt"><s:text name="description"/></td>
			<td><input name="description" type="text"/></td>
		</tr>
		<tr>
			<td class="bt"><s:text name="createuser"/></td>
			<td><span id="createuser"></span></td>
		</tr>
		<tr>
			<td class="bt"><s:text name="createtime"/></td>
			<td><span id="createtime"></span></td>
		</tr>
		<tr>
			<td class="bt"></td>
			<td><input type="button" onclick="updateRole()" id="sub" value="<s:text name="yes"/>"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id="close" onclick="closeIFrame()" value="<s:text name="cancel"/>"/></td>
		</tr>
		</table>
    </form>
	</div>
	<div class="tcDi"><img src="${theme}/skins/default/images/tcDi1.gif" class="left" /><img src="${theme}/skins/default/images/tcDi2.gif" class="right"/></div>
</div>
	
<script type="text/javascript">	
	$("input").keydown(function(event){
   		switch(event.keyCode) {
			case 13:
				updateRole();
				return false;
				break;
	   	}
   	});
</script>

</body>
</html>