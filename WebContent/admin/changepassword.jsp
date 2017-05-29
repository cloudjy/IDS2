<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link rel="stylesheet" type="text/css"	href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" />
<link href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" rel="stylesheet"	type="text/css" />
<link rel="stylesheet"	href="${theme}/skins/default/css/jqtransform.css" media="all" />
<link rel="stylesheet" type="text/css" media="all" href="${theme}/skins/default/css/niceforms-default.css" />
<link rel="stylesheet" type="text/css" media="all" href="${theme}/skins/default/css/ipubs-dialog.css" />


<script src="${theme}/js/jquery-1.7.2.min.js" language="javascript"></script>
<script language="javascript" type="text/javascript" src="${theme}/js/niceforms.js"></script>
<script src="js/gnamp.js" language="javascript"></script>
<script type="text/javascript"	src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
<script type="text/javascript"	src="${theme}/js/jquery.jqtransform.js"></script>
<script type="text/javascript" src="${theme}/js/ipub.models-<s:text name="langjs"/>.js"></script>

<script type="text/javascript">
	function submitForm(callfunc) {
		if($("input[name=newPassword]").val()!=$("input[name=repassword]").val()){
			alert("<s:text name="alert_passwordinputerror"/>");
			return;
		}
		ajaxCallback('changepassword!change', {
			"user.oldPassword" : $("input[name=oldpassword]").val(),
			"user.newPassword" : $("input[name=newPassword]").val(),
		}, function(txt) {
			callfunc(txt);
		});
	}

	function closewindow(txt) {
		if (txt.success) {
			closeIFrame();
		} else {
			if(txt.field!=null){
				$.each(txt.field.user,function(i,o){
					alert(o);
				});
			}
			if(txt.message!=null){
				alert(txt.message);	
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
			<td class="bt"><s:text name="text_currentpassword"/></td>
			<td><input name="oldpassword" type="password"></td>
		</tr>
		<tr>
			<td class="bt"><s:text name="text_password"/></td>
			<td><input name="newPassword" type="password"></td>
		</tr>
		<tr>
			<td class="bt"><s:text name="text_confirmpassword"/></td>
			<td><input name="repassword" type="password"></td>
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
</body>
</html>