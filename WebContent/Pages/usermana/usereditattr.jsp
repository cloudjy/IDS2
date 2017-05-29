<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
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
		ajaxCallback('user!getBean', {
			"user.userId" : id,
		}, function(txt) {
			if(txt.success){
				var result = txt.data;
				$("#username").html(result.userName);
			}
		});
	}
	function submitForm(callfunc) {
		$("#nameErrorDiv").showError("");  
		$("#passwordErrorDiv").showError("");
		$("#repasswordErrorDiv").showError("");
		
		if(!$("input[name=password]").val()){
			//alert("<s:text name="passwordisempty"/>");
			$("#passwordErrorDiv").showError("<s:text name="passwordisempty"/>");
			return false;
		}
		if(!$("input[name=repassword]").val()){
			//alert("<s:text name="confirmpasswordisempty"/>");
			$("#repasswordErrorDiv").showError("<s:text name="confirmpasswordisempty"/>");
			return false;
		}
		
		if($("input[name=password]").val().length<4){
			// alert("<s:text name="passwordthanfour"/>");
			$("#passwordErrorDiv").showError("<s:text name="passwordthanfour"/>");
			return false;
		}
		if($("input[name=repassword]").val().length<4){
			//alert("<s:text name="passwordthanfour"/>");
			$("#repasswordErrorDiv").showError("<s:text name="passwordthanfour"/>");
			return false;
		}
		
		if($("input[name=password]").val()!=$("input[name=repassword]").val()){
			// alert("<s:text name="passwordnomatch"/>");
			$("#passwordErrorDiv").showError("<s:text name="passwordnomatch"/>");
			$("#repasswordErrorDiv").showError("<s:text name="passwordnomatch"/>");
			return false;
		}
		ajaxCallback('user!editattr', {
			"user.userId" : id,
			"user.password" : $("input[name=password]").val()
		}, function(result) {
			callfunc(result);
		});
	}

	function closewindow(result) {
		if(result.success){
			callParentFunction("refreshData");
			closeIFrame();
		}else{
			// var messages = "";
			// $.each(result.data,function(n,obj) { 
			//       $(obj).each(function(c,co){
			// 			messages = co;
			//       });
			//    });
			//alert(messages); 
			
			if (result.field) {
				if (result.field.password) {
					$("#passwordErrorDiv").showError(result.field.password);
					$("#repasswordErrorDiv").showError(result.field.password);
				} else {
					alert("<s:text name="passwordchangeerror"/>");
				}
			} else if (result.data && result.message) {
				if (result.data == "NameCannotNull" || result.data == "NameExist") {
					$("#nameErrorDiv").showError(result.message); 
				} else if (result.data == "InvalidPassord" || result.data == "InvalidPassordFormat") {
					$("#passwordErrorDiv").showError(result.message);
					$("#repasswordErrorDiv").showError(result.message);
				} else {
					alert(result.message); 
				} 
			} else if (result.message) {
				alert(result.message); 
			} else {
				alert("<s:text name="passwordchangeerror"/>");
			}	 
			return false;
		}
	}

	function closedir() {
		window.close();
		return false;
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
			<td class="bt"><s:text name="username"/> </td>
			<td><span id="username" ></span></td>
		</tr>
		<tr>
			<td class="bt"><s:text name="password"/></td>
			<td><input name="password" type="text">
				<div id="passwordErrorDiv" style="color:#FF0000;"></div></td>
		</tr>
		<tr>
			<td class="bt"><s:text name="confirmpassword"/></td>
			<td><input name="repassword" type="text">
				<div id="repasswordErrorDiv" style="color:#FF0000;"></div></td>
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