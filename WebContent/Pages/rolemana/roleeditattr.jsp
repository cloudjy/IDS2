<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" media="all" href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" />
<link rel="stylesheet" type="text/css" media="all" href="${theme}/skins/default/css/niceforms-default.css" />
<script src="${theme}/js/jquery-1.7.2.min.js" language="javascript"></script>
<script language="javascript" type="text/javascript" src="${theme}/js/niceforms.js"></script>
<script src="js/gnamp.js" language="javascript"></script>
<script type="text/javascript">
	$(function() {
		load();
	});
	var id = <%=request.getParameter("id")%>;
	function load() {
		ajaxCallback('role!getBean', {
			"role.roleId" : id,
		}, function(txt) {
			if(txt.success){
				
				var result = txt.data;
				$("input[name=rolename]").val(result.roleName);
				$("input[name=description]").val(result.description);
			}
		});
	}
	function submitForm(callfunc) {
		if (!$("input[name=rolename]").val()) {
			$("#nameErrorDiv").showError("<s:text name="name_cannot_null"/>"); 
			return false;
		}
		ajaxCallback('role!editattr', {
			"role.roleId" : id,
			"role.roleName" : $("input[name=rolename]").val(),
			"role.description" : $("input[name=description]").val()
		}, function(txt) {
			callfunc(txt);
		});
	}

	function closewindow(result) {
		if (result.success) {
			callParentFunction("refreshData");
			closeIFrame();
		} else { 
			if (result.data && result.message) {
				if (result.data == "NameCannotNull" || result.data == "NameExist") {
					$("#nameErrorDiv").showError(result.message); 
				} else {
					alert(result.message); 
				} 
			} else if (result.message) {
				alert(result.message); 
			} else {
				alert("<s:text name="edititemfail"/>");
			}	 	
			return false; 
		}
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
			<td class="bt" style="width: 30%"><s:text name="rolename"/></td>
			<td><input name="rolename" type="text">
				<div id="nameErrorDiv" style="color:#FF0000;"></div></td>
		</tr>
		<tr>
			<td class="bt"><s:text name="description"/></td>
			<td><input name="description" type="text"></td>
		</tr>
		<tr>
			<td class="bt"></td>
			<td><input type="button" onclick="submitForm(closewindow)" id="sub" value="<s:text name="yes"/>"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id="close" onclick="closeIFrame()" value="<s:text name="cancel"/>"/></td>
		</tr>
		</table>
    </form>
	</div>
	<div class="tcDi"><img src="${theme}/skins/default/images/tcDi1.gif" class="left" /><img src="${theme}/skins/default/images/tcDi2.gif" class="right"/></div>
</div>
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
					submitForm(closewindow);
					return false;
					break;
		   	}
	   	});
	</script>

</body>
</html>