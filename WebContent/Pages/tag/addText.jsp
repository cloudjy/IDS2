<%@page import="com.gnamp.server.model.Source"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

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
<%Source source = (Source)request.getAttribute("source");%>
		function changedir(callfunc){	
			ajaxCallback("tag!addTextTag.action",{
				"source.catId":'<%=source.getCatId()%>',
				"source.sourceId":'<%=source.getSourceId()%>',
				"marquee.text":$("#text").val()
			},
	    	function(txt){
		      callfunc(txt);
		});
	}

		function executeFunction(txt) {
			if(txt.success){
				callParentFunction("added", 'ok');
				return closeIFrame();
			}else{
				if(txt&&txt.message){
					alert(txt.message);	
				}
			}
		}
	
</script>
 <style>
 .NFTextareaRight{
    padding-right:3px;
    padding-bottom:0px;
 }
 </style>
</head>
<body>
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
			<td><s:text name="content" /></td>
			<td>
			<!-- <input type="text" style="width: 250px;" name="text" id="text" /> -->
				<textarea name="text" id="text"
                              cols="50" rows="3"></textarea>
				</td>
		</tr>
		<tr>
				<td class="bt">&nbsp;</td>
						<td><input type="button" value="<s:text name="yes"/>"
							onclick="changedir(executeFunction)" style="line-height: 13px;" />&nbsp;&nbsp;&nbsp;&nbsp;<input
							type="button" onclick="closeIFrame();" value="<s:text name="cancel" />" /></td>
		</tr>
	</table>
	</form>
	
		</div>
		<div class="tcDi">
			<img src="${theme}/skins/default/images/tcDi1.gif" class="left" /><img
				src="${theme}/skins/default/images/tcDi2.gif" class="right" />
		</div>
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
				changedir(executeFunction);
				return false;
				break;
	   	}
   	});
</script>

</body>
</html>

