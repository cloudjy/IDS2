<%@page import="com.gnamp.server.model.SourceType"%>
<%@page import="com.gnamp.server.model.Source"%>
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

<%
Source source = (Source)request.getAttribute("source");
%>
<script type="text/javascript">
	function submitForm(callfunc){
		var catid = <%=source.getCatId()<=0?0:source.getCatId()%>;
		ajaxCallback("tag!edit",
				{
					"source.sourceId":<%=source.getSourceId()<=0?0:source.getSourceId()%>+"",
					"source.name":$("input[name=name]").val(),
					"source.description":$("input[name=desc]").val(),
					 "source.type":$("select[name=type]").val(),
					"source.catId":catid
				},
				function(result){
					if(result.success){
						callParentFunction("queryfiles");
						closeIFrame();
					}else{ 
						if (result.data  && result.message && 
								(result.data == "NameCannotNull" || result.data == "NameExist") ) { 
							$("#nameErrorDiv").showError(result.message); 
						} else if (result.message) {
							alert(result.message); 
						}	else {
							alert("<s:text name="edititemfail"/>");
						} 
					}
				});
		
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
			<td class="bt" style="width: 30%"><s:text name="name"/></td>
			<td><input name="name" type="text" value="<%=source.getName()%>">
				<div id="nameErrorDiv" style="color:#FF0000;"></div></td>
		</tr>
		<tr>
			<td class="bt"><s:text name="desc"/></td>
			<td><input name="desc" type="text" value="<%=source.getDescription()%>"></td>
		</tr>
		<tr>
			<td class="bt"><s:text name="type"/></td>
			<td><select name="type"  disabled="disabled">
			<%
			if(source.getType()== SourceType.TEXT){
			%>
					<option value="<%=SourceType.TEXT%>" ><s:text name="text"/></option>
			<%
			}
			else if(source.getType()== SourceType.IMAGE){
			%>
					<option value="<%=SourceType.IMAGE%>" ><s:text name="image"/></option>
			<%
			}
			else if(source.getType()== SourceType.VIDEO){
			%>
					<option value="<%=SourceType.VIDEO%>" ><s:text name="vedio"/></option>
			<%
			}
			else if(source.getType()== SourceType.RSS){
			%>
					<option value="<%=SourceType.RSS%>"><s:text name="rss"/></option>
			<%
			}
			%>
			</select></td>
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