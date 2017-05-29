<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="com.gnamp.server.model.PluginTask"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%
PluginTask task = (PluginTask)request.getAttribute("plugintask");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<script src="../Pages/Dialog/My97DatePicker/WdatePicker.js" language="javascript"></script>
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
<% 
Object onlyTaskSize = request.getParameter("onlyTaskSize");
if (onlyTaskSize != null && onlyTaskSize.toString().trim().equalsIgnoreCase("true")) {
} else {
	onlyTaskSize = null;
} 
%>

<script src="js/gnamp.js" language="javascript"></script>

		<script type="text/javascript">	
		$(document).ready(function() {
			$("#startTime").focus(function () {
				 WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'<s:text name="lang" />',alwaysUseStartDate:true}); 
			});
			$("#stopTime").focus(function () {
				 WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'<s:text name="lang" />',alwaysUseStartDate:true}); 
			});
		});
        function doSubmit() {   	
			$("#nameErrorDiv").showError("");
			$("#startTimeErrorDiv").showError("");
			$("#stopTimeErrorDiv").showError("");
			$("#countErrorDiv").showError("");
			
    		var param = $('#form1').serialize();    		
    		$.ajax({
    			type : "post",
    			dataType : "json",
    			url : "plugintask!ModifyPluginTask",
    			data : param,
    			complete : function() {
    				//compliteCallback();
    			},
    			success : function(result) {    				
    				if(result.success){
    				  //alert(msg.data);
    					callParentFunction("finished", 'ok');
    					return closeIFrame();
    				} else{ 
    					if (result.data  && result.message ) { 
    						if (result.data == "NameCannotNull" || result.data == "NameExist") {
    							$("#nameErrorDiv").showError(result.message); 
    						} else if (result.data == "InvalidDateTime") {
    							$("#startTimeErrorDiv").showError(result.message);
    							$("#stopTimeErrorDiv").showError(result.message);
    						} else if (result.data == "InvalidCount") {
    							$("#countErrorDiv").showError(result.message); 
    						} else {
    							alert(result.message); 
    						} 
    					} else if (result.message) {
    						alert(result.message); 
    					} else {
    						alert("<s:text name="edititemfail"/>");
    					}	 
    				}
    				//callback(msg);
    			},
    			error : function(XMLHttpRequest, textStatus, errorThrown) {
    				 window.top.location.href='/';
    				//errorCallback(XMLHttpRequest, textStatus, errorThrown);
    			}
    		});
    		return false;
    	}
			
        function cancl(){
			callParentFunction("finished", 'ok');
			return closeIFrame();
    } 		   
         </script>

	</head>
	<body>
	<%
	
	if(task==null){
		return;
	}
  %><!-- 弹出窗口 开始-->
	<div class="tcWindow">
		<div class="tcBt">
			<img src="${theme}/skins/default/images/tcBt1.gif" width="5" height="35"
				class="left" />
			<h2></h2>
			<img src="${theme}/skins/default/images/tcBt3.gif" class="right" />
		</div>
		<div class="tcNr">
		<form id="form1" class="niceform">		
		  <input type="hidden" value="<%=task.getTaskId() %>" name="plugintask.taskId" />	
			<table width="100%" border="0" cellspacing="2" cellpadding="0"
					class="bdTab">
				<tr>
					<td class="bt" style="width: 40%">
						<s:text name="text_task_taskname" />
					</td>
					<td align="left">
					 <% if (onlyTaskSize == null ) {%>   
						<input type="text" value="${plugintask.taskName }" id="plugintask.taskName" name="plugintask.taskName" />
						<div id="nameErrorDiv" style="color:#FF0000;"></div>
					 <% } else { %> 
					    ${plugintask.taskName }
					 <% } %>
					</td> 
				</tr>
				<tr>
					<td class="bt" style="width: 40%">
						<s:text name="desc" />
					</td>
					<td align="left">
					 <% if (onlyTaskSize == null ) {%>   
						<input type="text" value="${plugintask.description}"  id="plugintask.description" name="plugintask.description" />
					 <% } else { %> 
					 	${plugintask.description}
					 <% } %>
					</td>
				</tr>
				<tr>
					<td class="bt" style="width: 40%"><s:text name="start" /></td>
					<td align="left">
					 <% if (onlyTaskSize == null ) {%>   
						<input type="text" value="<%=task.getStartTime().toLocaleString() %>" name="plugintask.startTime" id="startTime" />
						<div id="startTimeErrorDiv" style="color:#FF0000;"></div>
					 <% } else { %> 
					 	<%= task.getStartTime().toLocaleString() %>
					 <% } %>
					</td>
				</tr>
				<tr>
					<td class="bt" style="width: 40%"><s:text name="end" /></td>
					<td align="left">
					 <% if (onlyTaskSize == null ) {%>   
						<input type="text" value="<%=task.getStopTime().toLocaleString() %>" name="plugintask.stopTime" id="stopTime" />
					 	<div id="stopTimeErrorDiv" style="color:#FF0000;"></div>
					 <% } else { %> 
					 	<%=task.getStopTime().toLocaleString() %>
					 <% } %>
					 </td>
				</tr>
				<tr>
					<td class="bt" style="width: 40%"><s:text name="style" /></td>
					<td align="left"> 
						<select size="1" id="plugintask.pluginStyle" name="plugintask.pluginStyle"
					 	<%= onlyTaskSize == null? "" : "disabled"  %>  >
		                  <option value="0" <%if(task.getPluginStyle()==0){ %> selected <%} %> ><s:text name="always" /></option>
		                  <option value="1" <%if(task.getPluginStyle()==1){ %> selected <%} %> ><s:text name="interval" /></option>
		               </select>
		            </td>
				</tr>
				<tr>
					<td class="bt" style="width: 40%"><s:text name="count" /></td>
					<td align="left"> 
					 <% if (onlyTaskSize == null ) {%>   
					 	<input id="pluginCount" value="${plugintask.pluginCount}" name="plugintask.pluginCount" onpaste="return false" style="ime-mode:disabled"                 
		             		onkeypress="if ((event.keyCode<48 || event.keyCode>57) && event.keyCode!='.') event.returnValue=false;" /> 
		         		<div id="countErrorDiv" style="color:#FF0000;"></div>
					 <% } else { %> 
					 	${plugintask.pluginCount}
					 <% } %>
		         	</td> 
				</tr>
				<tr>
					<td class="bt" style="width: 40%">
						<s:text name="version" />
					</td>
					<td align="left">
						<%=task.getTaskVersion() %>
					</td>
				</tr>
				<tr>
					<td class="bt" style="width: 40%">
						<s:text name="state" />
					</td>
					<td align="left">
						<% if(task.getState()==0) {%> <s:text name="task_status_checked" /><% }else{ %><s:text name="task_status_unchecked" /><%} %>
					</td>
				</tr>						
				<tr>
					<td class="bt" style="width: 40%">
						<s:text name="tasksize"/>
					</td>
					<td align="left">
						<%=request.getAttribute("tasksize") %>
					</td>
				</tr>				
				<tr>
					<td class="bt" style="width: 40%">
						<s:text name="task_createuser" />
					</td>
					<td align="left">
						<%=task.getCreateUser() %>
					</td>
				</tr>
				<tr>
					<td class="bt">&nbsp;</td>
					<td>
					 <% if (onlyTaskSize == null ) {%>   
						<input type="button" value="<s:text name="yes"/>"
							onclick="doSubmit();" style="line-height: 13px;" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" onclick="cancl();" value="<s:text name="cancel" />" />
					 <% } else { %> 
					    <input type="button" onclick="cancl();" value="<s:text name="yes" />" />
					 <% } %>
					</td>
				</tr>

			</table>
		</form>
		</div>
		<div class="tcDi">
			<img src="${theme}/skins/default/images/tcDi1.gif" class="left" /><img
				src="${theme}/skins/default/images/tcDi2.gif" class="right" />
		</div>
	</div>
	<!-- 弹出窗口 结束-->
	<!-- 表格间隔变色 -->
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
					doSubmit();
					return false;
					break;
		   	}
	   	});
	</script>

	</body>
</html>

