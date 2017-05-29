<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" />
<link rel="stylesheet" type="text/css" media="all" href="${theme}/skins/default/css/niceforms-default.css" />
<link rel="stylesheet" type="text/css" media="all" href="${theme}/skins/default/css/ipubs-dialog.css" />

<script src="${theme}/js/jquery-1.7.2.min.js" language="javascript"></script>
<script type="text/javascript" src="${theme}/js/ipub.models-<s:text name="langjs"/>.js"></script>
  <script type="text/javascript" src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
<script language="javascript" type="text/javascript" src="${theme}/js/niceforms.js"></script>
<script src="js/gnamp.js" language="javascript"></script>
<script src="js/jquery.upload-1.0.2.min.js" language="javascript"></script>

<script type="text/javascript">

$(function() {
    $('#sub').click(function() {
    	$("body").append("<div id='shadow' style='height: 100%;width: 100%;position: absolute;top: 0px;'>"
				+"<img style='position: absolute;left: 50%;top: 50%;z-index: 999;' src='../skins/default/images/updating.gif'/></div>");
    	
    	$("input[name=upload]").upload('templateupload!upload.action', function(data) {
    		$("#shadow").remove();
    		var success = JSON.parse($(data).html()); 
    		if(success.success){
    			closeIFrame();
    		}
        }, 'text');
    });
    
    
});

</script>
</head>

<body style="border:0px;margin:0px;padding:0px;">

		
	<div class="tcWindow">
    <div class="tcBt"><img src="${theme}/skins/default/images/tcBt1.gif" width="5" height="35" class="left" /><img src="${theme}/skins/default/images/tcBt3.gif" class="right"/></div>
	<div class="tcNr">
	<form action="#" method="post" class="niceform">
	    <table width="100%" border="0" cellspacing="2" cellpadding="0" class="bdTab">
 		<tr>
			<td class="bt"><s:text name="selectfile"/></td>
			<td><input id="template" type="file" name="upload" size="50" value="<s:text name="select"/>"/></td>
		</tr>
		<tr>
		<td class="bt"></td>
		<td><input type="button" id="sub" value="<s:text name="yes"/>"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id="close" onclick="closeIFrame()" value="<s:text name="cancel"/>"/></td>
	</tr>
		</table>
    </form>
	</div>
	<div class="tcDi"><img src="${theme}/skins/default/images/tcDi1.gif" class="left" /><img src="${theme}/skins/default/images/tcDi2.gif" class="right"/></div>
</div>
<!-- 弹出窗口 结束-->
</body>	
</html>