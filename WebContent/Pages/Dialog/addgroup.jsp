<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">


<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" rel="stylesheet" type="text/css" />
<script src="${theme}/js/jquery-1.7.2.min.js"></script>
<script language="javascript" type="text/javascript" src="${theme}/js/niceforms.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="${theme}/skins/default/css/niceforms-default.css" />

<link rel="stylesheet" type="text/css"
	href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" /> 
<script src="${theme}/js/ipub.models-<s:text name="langjs"/>.js" type="text/javascript"></script>
<script type="text/javascript"
	src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>   
<link href="${theme}/skins/default/css/ipubs-dialog.css" rel="stylesheet" type="text/css" />
<script src="js/gnamp.js" language="javascript"></script>
<script type="text/javascript">
		function changedir(callfunc){			 
			
			if($("#groupName").val() == '' || $("#groupName").val() == 'undefined'){
				// alert("<s:text name="dirnameisnull"/>");
				$("#nameErrorDiv").showError("<s:text name="name_cannot_null"/>");
				return;
			}
			if($("#groupName").val().length<0||$("#groupName").val().length>12){
				// alert("<s:text name="grouplengmaxerror"/>");
				$("#nameErrorDiv").showError("<s:text name="grouplengmaxerror"/>");
				return;
			}
			$.post('group!add',
					{"group.groupName":$("#groupName").val(),
					 "group.parentId":"<%=request.getParameter("id").toString()%>"
		            },
	    	function(txt) {
		      callfunc(txt);
		});
	} 

	function executeFunction(result){
		if(result.success){
			callParentFunction("addNodeed", result.data);
			return closeIFrame();
		}else{
			if (result.data  && result.message && 
					(result.data == "NameCannotNull" || result.data == "NameExist")) { 
				$("#nameErrorDiv").showError(result.message); 
			} else if (result.message) {
				alert(result.message); 
			}	else {
				alert("<s:text name="additemfail"/>");
			}	 	
		}
	}
</script>
</head>
<body style="min-width: 300px; min-height: 100px;">

	<!-- 弹出窗口 开始-->
	<div class="tcWindow">
		<div class="tcBt">
			<img src="${theme}/skins/default/images/tcBt1.gif" width="5" height="35"
				class="left" />
			<h2></h2>
			<img src="${theme}/skins/default/images/tcBt3.gif" class="right" />
		</div>
		<div class="tcNr">
			<form action="#" method="post" class="niceform">
				<table width="100%" border="0" cellspacing="2" cellpadding="0"
					class="bdTab">
					<tr>
						<td class="bt" style="width: 30%"><font color="#FF0000" size="3">&#x25B4;</font><s:text name="name" /></td>
						<td><input type="text" name="groupName" id="groupName" />
							<div id="nameErrorDiv" style="color:#FF0000;"></div></td>
					</tr>
					<tr>
						<td class="bt">
						 &nbsp;
						</td>
			          	<td><input type="button"
							onclick="changedir(executeFunction)" id="sub"
							value="<s:text name="yes"/>" style="line-height: 13px;" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" id="close" onclick="closeIFrame()"
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
	<!-- 弹出窗口 结束-->
	<!-- 表格间隔变色 -->
	<script>
// Give classes to even and odd table rows for zebra striping.
$(document).ready(function() {
  // $('tr:odd').addClass('alt');
  $('tr:nth-child(even)').addClass('alt');

  $('td:contains(Henry)').nextAll().andSelf().addClass('highlight');//.siblings()
  $('th').parent().addClass('table-heading');
});
</script>
	
	<script type="text/javascript">	
		$("input").keydown(function(event){
	   		switch(event.keyCode) {
				case 13:
					changedir(executeFunction);
					return false;
					break;
		   	}
	   	});
	</script>

</body>
</html>

