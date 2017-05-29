<%@ page language="java" pageEncoding="utf-8"%>
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
<link rel="stylesheet" type="text/css" media="all" href="${theme}/skins/default/css/niceforms-default.css" />
<script src="${theme}/js/jquery-1.7.2.min.js" language="javascript"></script>
<script language="javascript" type="text/javascript" src="${theme}/js/niceforms.js"></script>
<script src="js/gnamp.js" language="javascript"></script>

		<script type="text/javascript">
		 function doSubmit() {
			 var param = $('#form1').serialize();    		
	    		$.ajax({
	    			type : "post",
	    			dataType : "json",
	    			url : "terminal!Subtitle",
	    			data : param,
	    			complete : function() {
	    				//compliteCallback();
	    			},
	    			success : function(msg) {    				
	    				if(msg.success){
	    			 		callParentFunction("finished", 'ok');
	    					return closeIFrame();
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
	    	} 	
			
	     function cancl(){
	 		callParentFunction("finished", 'ok');
			return closeIFrame();
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
			<h2><% if(terminal != null && terminal.getDeviceId()>0 ){ %> <s:text name="curterminal" />
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
						%>	
						(${terminal.deviceName })	
			<input type="hidden" value="${terminal.deviceId }" name="terminal.deviceId" />	 <%} %></h2>
			<img src="${theme}/skins/default/images/tcBt3.gif" class="right" />
		</div>
		<div class="tcNr">
		<form id="form1" class="niceform">    
		        <input type="hidden" value="${terminal.deviceId }" name="terminal.deviceId" />			 
		        <input type="hidden" value="${terminal.groupId }" name="terminal.groupId" />
				 <input value="<%= request.getAttribute("selectIDs") %>" name="selectIDs" type="hidden" />		
				<table width="100%" border="0" cellspacing="2" cellpadding="0"
					class="bdTab">				
					<tr>
						<td  class="bt">
							<s:text name="subtitle" />
						</td>
						<td>
							 <%-- <input type="text" style="height:100px; width:250px" value="${terminal.subtitle }" name="terminal.subtitle" />	 --%>							
						<textarea value="${terminal.subtitle }" name="terminal.subtitle"
                              cols="57" rows="3">${terminal.subtitle }</textarea>
						</td>						
					</tr>								
					<tr>
						<td  class="bt">
							<s:text name="terminalversion" />
						</td>
						<td>
						   <%--  ${terminal.subtitleVersion }	 --%>
						    <s:date name="terminal.subtitleVersion" format="yyyy-MM-dd HH:mm:ss" />							
						</td>
					</tr>					
					<tr>
						<td class="bt">
							<s:text name="subtitleversion" />
						</td>
						<td>		
					       <s:date name="terminal.curSubtitleVersion" format="yyyy-MM-dd HH:mm:ss" />	
						</td>
					</tr>	
					<tr>
						<td class="bt">&nbsp;</td>
						<td><input type="button" value="<s:text name="yes"/>"
							onclick="doSubmit();" style="line-height: 13px;" />&nbsp;&nbsp;&nbsp;&nbsp;<input
							type="button" onclick="cancl();" value="<s:text name="cancel" />" /></td>
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

