<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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
		if(!validate()){
			return false;
		}
		ajaxCallback("customer!add",{"customer.cstmName":$("input[name=name]").val(),
			"customer.description":$("input[name=description]").val(),
			"customer.shortName":$("input[name=shortname]").val()
			},function(result){
				if(result.success){
					callParentFunction("refresh");
					closeIFrame();
				}else{
					if(result.message!=null){
						alert("<s:text name="additemfail"/>");	
					}
					return false;
				}
			}
		);
	}
	
	function validate(){
		if(validateName()&&validateShortName()){
			return true;
		}
		return false;
	}
	
	function validateName(){
		var name = $("input[name=name]").val();
		if(name.length==0){
			alert("<s:text name="nameemtpyerror"/>");
			return false;
		}
		if(name.length<0||name.length>60){
			alert("<s:text name="namelengtherror"/>");
			return false;
		}else{
			return true;
		}
	}
	

	function validateShortName(){
		var bean = $("input[name=shortname]").val();
		if(bean.length==0){
			alert("<s:text name="domainempty"/>");
			return false;
		}
		if(bean.length<0||bean.length>30){
			alert("<s:text name="domainlengtherror"/>");
			return false;
		}
		if(!/^[a-zA-Z0-9]+$/.test(bean)){
			alert("<s:text name="domainill"/> ");
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
			<td class="bt"><s:text name="cropname"/></td>
			<td><input name="name" type="text"></td>
		</tr>
		<tr>
			<td class="bt"><s:text name="description"/></td>
			<td><input name="description" type="text"></td>
		</tr>
		<tr>
			<td class="bt"><s:text name="text_cstmadd_domain"/></td>
			<td><input name="shortname" type="text"></td>
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