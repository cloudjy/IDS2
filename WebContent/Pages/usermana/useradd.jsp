<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="x-ua-compatible" content="ie=8" />
<link rel="stylesheet" type="text/css" media="all" href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" />
<link rel="stylesheet" type="text/css" href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" />
<link rel="stylesheet" type="text/css" media="all" href="${theme}/skins/default/css/niceforms-default.css" />
<link href="${theme}/skins/default/css/ipubs-dialog.css" rel="stylesheet" type="text/css" />

<script src="${theme}/js/jquery-1.7.2.min.js" language="javascript"></script>
<script type="text/javascript" src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
<script language="javascript" type="text/javascript" src="${theme}/js/niceforms.js"></script>
<script type="text/javascript" src="${theme}/js/ipub.models-<s:text name="langjs"/>.js"></script>
<script src="js/gnamp.js" language="javascript"></script>


<script type="text/javascript">
	function submitForm(callfunc){
		$("#nameErrorDiv").showError("");  
		$("#passwordErrorDiv").showError("");
		$("#repasswordErrorDiv").showError("");
		
		if(!validate()){
			return false;
		}
		
		ajaxCallback("user!addUser",
				{
					"user.userName":$("input[name=username]").val(),
					"user.password" : $("input[name=password]").val()
				},
				function(result){
					if(result.success){
						callParentFunction("refreshData");
						closeIFrame();
					} else { 
						if (result.field) {
							if (result.field.user) {
								$("#nameErrorDiv").showError(result.field.user); 
							} else if (result.field.password) {
								$("#passwordErrorDiv").showError(result.field.password);
								$("#repasswordErrorDiv").showError(result.field.password);
							} else {
								alert("<s:text name="addusererror"/>");
							}
						} else if (result.data && result.message) {
							if (result.data == "NameCannotNull" || result.data == "NameExist") {
								$("#nameErrorDiv").showError(result.message); 
							} else if (result.data == "InvalidPassordFormat") {
								$("#passwordErrorDiv").showError(result.message);
								$("#repasswordErrorDiv").showError(result.message);
							} else {
								alert(result.message); 
							} 
						} else if (result.message) {
							alert(result.message); 
						} else {
							alert("<s:text name="addusererror"/>");
						}	 
					} 
				});
		
	}
	function validate(){
		return validateUserName() && validatePassword();
	}
	function validateUserName(){
		var bean = $("input[name=username]").val();
		if(bean.length==0){
			// alert("<s:text name="usernameisnull"/>");
			$("#nameErrorDiv").showError("<s:text name="usernameisnull"/>"); 
			return false;
		}
		if(bean.length<0||bean.length>30){
			// alert("<s:text name="usernamelengtherror"/>");
			$("#nameErrorDiv").showError("<s:text name="usernamelengtherror"/>");
			return false;
		}
		if(!/^[a-zA-Z0-9]+$/.test(bean)){
			//alert("<s:text name="usernamecheckerror"/>");
			$("#nameErrorDiv").showError("<s:text name="usernamecheckerror"/>");
			return false;
		}
		return true;
	}
	function validatePassword(){
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
			//alert("<s:text name="passwordthanfour"/>");
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
		return true;
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
			<td class="bt" style="width: 40%"><font color="#FF0000" size="3">&#x25B4;</font><s:text name="username"/></td>
			<td><input name="username" type="text">
				<div id="nameErrorDiv" style="color:#FF0000;"></div></td>
		</tr>
		<tr>
			<td class="bt" style="width: 40%"><font color="#FF0000" size="3">&#x25B4;</font><s:text name="password"/></td>
			<td><input name="password" type="text">
				<div id="passwordErrorDiv" style="color:#FF0000;"></div></td>
		</tr>
		<tr>
			<td class="bt" style="width: 40%"><font color="#FF0000" size="3">&#x25B4;</font><s:text name="confirmpassword"/></td>
			<td><input name="repassword" type="text">
				<div id="repasswordErrorDiv" style="color:#FF0000;"></div></td>
		</tr>
		<tr>
			<td class="bt"></td>
			<td><input type="button" onclick="submitForm()" id="sub" value="<s:text name="yes"/>"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id="close" onclick="closeIFrame()" value="<s:text name="cancel"/>"/></td>
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
				submitForm();
				return false;
				break;
	   	}
   	});
</script>

</body>	
</html>