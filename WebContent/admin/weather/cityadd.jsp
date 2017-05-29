<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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
	function submitForm(callfunc){
		if(!validate()){
			return false;
		}
		$.post('weather!add' ,
				{"city.cityName":$("input[name=cityname]").val(),
				"city.province":$("input[name=province]").val()
				},
				function(txt){
					callfunc(txt);
				}
		);
	}
	
	
	function closewindow(txt){
		if(txt.success){
			callParentFunction("refresh");
			closeIFrame();
		}else{
			alert(txt.message);
			return false;
		}
	}
	
	function validate(){
		if(validateName()&&validateProvince()){
			return true;
		}
		return false;
	}
	
	function validateName(){
		var name = $("input[name=cityname]").val();
		
		if(name.length==0){
			alert("<s:text name="alert_cityempty"/>");
			return false;
		}
		if(name.length<0||name.length>60){
			alert("<s:text name="alert_citylengtherror"/>");
			return false;
		}else{
			return true;
		}
	}
	function validateProvince(){
		var name = $("input[name=province]").val();
		if(name.length==0){
			alert("<s:text name="alert_provincesemtpy"/>");
			return false;
		}
		if(name.length<0||name.length>60){
			alert("<s:text name="alert_provinceslengtherror"/>");
			return false;
		}else{
			return true;
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
			<td class="bt"><s:text name="text_city"/></td>
			<td><input name="cityname" type="text"></td>
		</tr>
		<tr>
			<td class="bt"><s:text name="text_province"/></td>
			<td><input name="province" type="text"></td>
		</tr>
		<tr>
			<td class="bt"></td>
			<td><input type="button" onclick="submitForm(closewindow)" id="sub"
			 value="<s:text name="yes"/>"/>&nbsp;&nbsp;&nbsp;&nbsp;
			 <input type="button" id="close" onclick="closeIFrame()" 
			 value="<s:text name="cancel"/>"/></td>
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
				submitForm(closewindow);
				return false;
				break;
	   	}
   	});
</script>

</body>	
</html>