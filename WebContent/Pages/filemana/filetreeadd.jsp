<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=8" /> 
<%@include file="/template/standjs.jsp" %>
<script type="text/javascript">	 
<%
	try{
		if(request.getParameter("id")==null||Integer.valueOf(request.getParameter("id"))<0){
			throw new Exception();
		}
	}catch(Exception e){
		return;
	}
	
%>
	function changedir(callfunc){
		var dirValue = $("#dir").val();
		if(dirValue==''||dirValue== 'undefined'){
			// alert("<s:text name="name_cannot_null"/>");
			$("#nameErrorDiv").showError("<s:text name="name_cannot_null"/>");
			return;
		}
		if(dirValue.length>12){
			// alert("<s:text name="grouplengmaxerror"/>");
			$("#nameErrorDiv").showError("<s:text name="grouplengmaxerror"/>");
			return;
		}
		ajaxCallback('filecate!add' ,
				{"cate.categoryName":$("#dir").val(),
				"cate.parentId":"<%=request.getParameter("id").toString()%>"
				},
				function(txt){
					callfunc(txt);
				}
		);
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
<body style="min-width: 300px;min-height: 100px;">
<!-- 弹出窗口 开始-->
<div class="tcWindow">
    <div class="tcBt"><img src="${theme}/skins/default/images/tcBt1.gif" width="5" height="35" class="left" /><img src="${theme}/skins/default/images/tcBt3.gif" class="right"/></div>
	<div class="tcNr">
	<form action="#" method="post" class="niceform">
	    <table width="100%" border="0" cellspacing="2" cellpadding="0" class="bdTab">
 		<tr>
		<td class="bt" style="width: 30%"><font color="#FF0000" size="3">&#x25B4;</font><s:text name="name"/></td>
		<td><input type="text" name="dir" id="dir"/>
			<div id="nameErrorDiv" style="color:#FF0000;"></div></td>
		</tr>
		<tr>
				<td  class="bt"></td>
				<td><input type="button" onclick="changedir(executeFunction)" id="sub" value="<s:text name="yes"/>"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id="close" onclick="closeIFrame()" value="<s:text name="cancel"/>"/></td>
		</tr>
		</table>
    </form>
	</div>
	<div class="tcDi"><img src="${theme}/skins/default/images/tcDi1.gif" class="left" /><img src="${theme}/skins/default/images/tcDi2.gif" class="right"/></div>
</div>
<!-- 弹出窗口 结束-->

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
