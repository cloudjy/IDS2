<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.gnamp.server.model.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%
Terminal terminal = (Terminal)request.getAttribute("terminal");
%>


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

		function doSubmit(obj) {		
			$("#subtype").val(obj);
			
			var msg = "<s:text name="verifyspecified"/>";
			if(obj == "clearapp" || obj == "clearkel" || obj == "clearcfg")
				msg = "<s:text name="isemptied"/>";
			
			confirm(msg,function(){
				var param = $('#form1').serialize();    		
	    		$.ajax({
	    			type : "post",
	    			dataType : "json",
	    			url : "terminal!TerminalUpgrade",
	    			data : param,
	    			complete : function() {
	    				//compliteCallback();
	    			},
	    			success : function(msg) {    				
	    				if(msg.success){
	    				  //alert(msg.data);
	    			 		callParentFunction("finished", 'ok');
	    			 		//return closeIFrame();
	    					//alert('操作成功！');
	    					
	    			 		if(obj == "clearapp")
								$("#assignAppVersion").val("0");
							if(obj == "clearkel")
								$("#assignKernelVersion").val("0");
							if(obj == "clearcfg")
								$("#assignConfigId").val("0");
	    				}
	    				else{
	    					alert(msg.data);
	    				}
	    				//callback(msg);
	    			},
	    			error : function(XMLHttpRequest, textStatus, errorThrown) {
	    				 window.top.location.href='/';
	    				//errorCallback(XMLHttpRequest, textStatus, errorThrown);
	    			}
	    		});
	    		return false;
			});
			
			
		}
			
	     function cancl(){
	  		callParentFunction("finished", 'ok');
			return closeIFrame();
	    } 	
         </script>

	</head>
	<body>
	<!-- 弹出窗口 开始-->
	<div class="tcWindow">
		<div class="tcBt">
			<img src="${theme}/skins/default/images/tcBt1.gif" width="5" height="35"
				class="left" />
			<h2>
			
<% if(terminal != null && terminal.getDeviceId()>0 ){ %>  <s:text name="curterminal" />
			          <%
                         String tmp="";
						if( String.valueOf(terminal.getDeviceId()).length() <12){
							for(int i=0;i<(12-String.valueOf(terminal.getDeviceId()).length());i++)
							   tmp +="0"; 
						}	
					  %>
						<%= (Long.toHexString(Long.parseLong(String.valueOf((terminal.getDeviceId())))).toUpperCase().length()<12 ?
								(tmp+Long.toHexString(Long.parseLong(String.valueOf((terminal.getDeviceId())))).toUpperCase())
								: Long.toHexString(Long.parseLong(String.valueOf((terminal.getDeviceId())))).toUpperCase())
						%>		(${terminal.deviceName })	
				<input type="hidden" name="terminal.deviceId" value="${terminal.deviceId }" />	<%} %></h2>
			<img src="${theme}/skins/default/images/tcBt3.gif" class="right" />
		</div>
		<div class="tcNr">
		<form id="form1" class="niceform">
				 <input type="hidden" value="${terminal.deviceId }" name="terminal.deviceId" />
					<input type="hidden" id="subtype" name="upgradetype" />		
				 <input value="<%= request.getAttribute("selectIDs") %>" name="selectIDs" type="hidden" />		
				<table width="100%" border="0" cellspacing="2" cellpadding="0"
					class="bdTab">	
					<tr >
						<td class="bt">						
							<s:text name="setapp" />
						</td>
						<td  >
								<select size="1" id="assignAppVersion" name="terminal.assignAppVersion">
								<option value="0"><s:text name="select" /></option>
						<% if(request.getAttribute("applist") !=null){ 
							  List<App> list = (List<App>)request.getAttribute("applist");							  
							  for(int i=0;i<list.size();i++){
							%>				
								<option <%if(terminal != null && terminal.getAssignAppVersion()!=null && terminal.getAssignAppVersion().equals(list.get(i).getAppVersion())){%> selected <%} %> value="<%=list.get(i).getAppVersion() %>"><%=list.get(i).getAppVersion()  %></option>
							<%}
						  } %>
							</select>
						</td>
						<td class="bt">
							<s:text name="text_upgrade_reported" />
						</td>
						<td>
						 <%if(terminal != null){ %>	${terminal.curAppVersion } <%} %>
						</td>						
						<td  >
						<s:if test="#session.current_privilege.specprog">
							<input type="button" onclick="doSubmit('app')" class="sub" value="<s:text name="set" />" />
							&nbsp; &nbsp; 
							<input value="<s:text name="clear" />" onclick="doSubmit('clearapp')" type="button" class="sub" />							  
						</s:if>
						</td>
					</tr>
					<tr>
						<td class="bt">
							<s:text name="text_upgrade_assignedfirmware" />
						</td>
						<td >
							<select size="1" id="assignKernelVersion" name="terminal.assignKernelVersion">
								<option value="0"><s:text name="select" /></option>
						<% if(request.getAttribute("kernellist") !=null){ 
							  List<Kernel> list = (List<Kernel>)request.getAttribute("kernellist");							  
							  for(int i=0;i<list.size();i++){
							%>				
								<option <%if(terminal != null && terminal.getAssignKernelVersion()!=null && terminal.getAssignKernelVersion().equals(list.get(i).getKernelVersion()) ){%> selected <%} %> value="<%=list.get(i).getKernelVersion() %>"><%=list.get(i).getKernelVersion()  %></option>
							<%}
						  } %>
							</select>
						</td>
						<td class="bt">
							<s:text name="text_upgrade_reportedfirmware" />
						</td>
						<td >
						 <%if(terminal != null){ %>	${terminal.curKernelVersion }<%} %>
						</td>					
						<td >
						<s:if test="#session.current_privilege.specfirmware">
							<input type="button" onclick="doSubmit('kel')" class="sub" value="<s:text name="set" />" />
							&nbsp; &nbsp; 
							<input value="<s:text name="clear" />" onclick="doSubmit('clearkel')" type="button" class="sub" />	
						</s:if>
						</td>
					</tr>
					<tr style="display:none" >
						<td class="bt">
							<s:text name="text_upgrade_assignsetting" />
						</td>
						<td >
						    <select size="1" id="assignConfigId" name="terminal.assignConfigId" >
								<option value="0"><s:text name="select" /></option>
						<% if(request.getAttribute("configlist") !=null){ 
							  List<Config> list = (List<Config>)request.getAttribute("configlist");							  
							  for(int i=0;i<list.size();i++){
							%>				
								<option <%if(terminal != null && terminal.getAssignConfigId()==list.get(i).getConfigId()){%> selected <%} %> value="<%=list.get(i).getConfigId() %>"><%=list.get(i).getConfigName() %></option>
							<%}
						  } %>
							</select>
						</td>
						<td class="bt">
						<s:text name="text_upgrade_reportconfig" />
						</td>
						<td >
							 <%if(terminal != null){ %> ${terminal.curConfigName }<%} %>
						</td>			
						<td >
						<s:if test="#session.current_privilege.specconfig">
							<input type="button" onclick="doSubmit('cfg')" class="sub" value="<s:text name="set" />" />
							&nbsp; &nbsp; 
							<input value="<s:text name="clear" />" onclick="doSubmit('clearcfg')" type="button" class="sub" />	
						</s:if>
						</td>
					</tr>	
					<tr  style="display:none" >
						<td class="bt">
							<s:text name="text_upgrade_assignedversion" />
						</td>
						<td >
						  <%if(terminal != null && terminal.getAssignConfigVersion() != null){ %><%=terminal.getAssignConfigVersion().toString().equals("1970-01-01 00:00:00.0") ? "" : terminal.getAssignConfigVersion().toLocaleString()%><%} %>
						</td>
						<td class="bt">
						 <s:text name="text_upgrade_reportversion" />
						</td>
						<td >						
							<%if(terminal != null && terminal.getCurConfigVersion() != null){ %><%=terminal.getCurConfigVersion().toString().equals("1970-01-01 00:00:00.0") ? "" : terminal.getCurConfigVersion().toLocaleString()%><%} %>
						</td>	
						<td></td>
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
	</body>
</html>

