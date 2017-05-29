<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="com.gnamp.server.model.File"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/template/standjs.jsp" %>

<script language="javascript">
function mms(){
	if($("#mmsAddress").val()=='' || $("#mmsAddress").val()=='undefined'){
		alert("<s:text name="additemfail"/>");	
		return;
	}else if($("#mmsAddress").val().substring(0,6) != "mms://"){
		alert("<s:text name="additemfail"/>");	
		return;
	}else if($("#mmsAddress").val().length==6 && $("#mmsAddress").val()=="mms://"){
		alert("<s:text name="additemfail"/>");	
		return;
	}
		var param = {
			"categoryId" : <%=request.getParameter("categoryId")==null?0:request.getParameter("categoryId")%>,			
			"mmsAddress" :  $("#mmsAddress").val(),		
			"autoCheck" :  <%=request.getParameter("autoCheck")%>
		     };
		
		ajaxCallback("file!mms", $.param(param, true),
				function(result) {
					if (result.success) {
						callParentFunction("refreshFileAndVender");
						closeIFrame();
						return;
					} else {
						alert(result.message);
					}
		});
 }
</script>
</head>

<body>
	<div class="tcWindow">
		<div class="tcBt">
			<img src="${theme}/skins/default/images/tcBt1.gif" width="5" height="35"
				class="left" /><img src="${theme}/skins/default/images/tcBt3.gif"
				class="right" />
		</div>
		<div class="tcNr">
			<form action="#" method="post" class="niceform">
				<table width="100%" border="0" cellspacing="2" cellpadding="0"
					class="bdTab">
					<tr>
						<td width="101"> </td>
						<td width="309"><input style="width:250px;" 
						value="mms://" type="text" id="mmsAddress" name="mmsAddress" /></td>
					</tr>
					<tr>
						<td class="bt"></td>
						<td><input type="button" onclick="mms()"
							value="<s:text name="yes"/>" />&nbsp;&nbsp;&nbsp;&nbsp;<input
							type="button" id="close" onclick="closeIFrame()"
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
	
<script type="text/javascript">	
	$("input").keydown(function(event){
   		switch(event.keyCode) {
			case 13:
				mms();
				return false;
				break;
	   	}
   	});
</script>

</body>
</html>
