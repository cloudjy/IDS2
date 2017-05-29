<%@page import="com.gnamp.server.model.*"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	Terminal terminal = (Terminal) request.getAttribute("terminal");
%>




<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />



<link href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" rel="stylesheet"
	type="text/css" />
<script src="${theme}/js/jquery-1.7.2.min.js"></script>
<script language="javascript" type="text/javascript"
	src="${theme}/js/niceforms.js"></script>
<link rel="stylesheet" type="text/css" media="all"
	href="${theme}/skins/default/css/niceforms-default.css" />
<script src="${theme}/js/ipub.models-<s:text name="langjs"/>.js" type="text/javascript"></script>
<script src="js/gnamp.js"></script>


<script src="../Pages/Dialog/My97DatePicker/WdatePicker.js" language="javascript"></script>
<script>
$(function(){
	var param = {"terminal.deviceId" : <%=terminal.getDeviceId() %>
	};
			
	$p = new page("terminal!eventlist",param,showlist);	
	$p.ajax();
	/* //queryimagefiles();  */
	
	$("#first").bind("click",$p.first);
	$("#previous").bind("click",$p.previous);
	$("#next").bind("click",$p.next);
	$("#last").bind("click",$p.last);
});

function eventlist(){	
	$p.ajax();
	return false;
}
function showlist(result){
	try {
		var content = "";
		$.each(
						result.data,
						function(i, f) {
							content += "<tr>"
							+"<td width=\"55%\">"+f.eventTime+"</td>"
							+"<td>"+getEvent(f.event)+"</td>"
						+"</tr>";
						});
	
		$("#mytab").html(content);
		
		$('.shujTb > tbody >tr:odd').addClass('alt');
		
		/**
		 * 重置滚动条
		 */
		ipubs.models.jscroll_ee();
		
	} catch (e) {
		alert(e.message);
	}
}
  function removeevent(){	  
	  if($("#begindate").val()=='' || $("#begindate").val()=='undefined'){
		  return;
	  }
	  
		var param = {
				"begindate" : $("#begindate").val(),
				"terminal.deviceId" : <%=terminal.getDeviceId() %>
			     };
			$.ajax({
				type : "post",
				dataType : "json",
				url : "terminal!removeevent",
				data : param,
				complete : function() {
				},
				success : function(msg) {
					if (msg.success) {
						eventlist();
					} else {
						alert(msg.data);
					}
				},
				error : function(XMLHttpRequest, textStatus,
						errorThrown) {
					window.top.location.href='/';
				}
			});
  }
</script>
</head>
<body>
	<!-- 弹出窗口 开始-->
	<div class="tcWindow">
		<table border="0" cellpadding="0" cellspacing="0" class="tcBt">
			<tr>
				<td style="width: 5px;"><img
					src="${theme}/skins/default/images/tcBt1.png" width="5"
					height="35" class="left" /></td>
				<td class="bg"><h2><s:text name="text_terminalEvent_currentterminal"/><%=terminal.getDeviceName() %></h2></td>
				<td style="width: 5px;"><img
					src="${theme}/skins/default/images/tcBt3.png" width="5"
					height="35" class="right" /></td>
			</tr>
		</table>
		<div class="tcNr">
			<form class="niceform">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="shujBt">
					<tr>
						<td class="first" width="55%"><s:text name="text_terminalEvent_time"/></td>
						<td><s:text name="text_terminalEvent_event"/></td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="shujTb" id="mytab">
				
				</table>
			 </form>
			<div class="page1 page2" style="font-size: 12px;">
				  <%@ include file="/template/pagehy.jsp" %>
			</div>
			<form class="niceform">
				<div style="height: 40px; text-align: left; padding-top: 10px;">
					<table style="width: 500px;" border="0" cellspacing="0"
						cellpadding="0">
						<tr>
							<td width="117" align="right"><s:text name="text_terminalEvent_deleterecord"/> &nbsp;</td>
							<td width="200" style="width: 170px;"> 							
							 <input name="begindate"  id="begindate" type="text" /></td>
							<td width="183"><input type="button" onclick="removeevent();" name="submit"
								id="submit" value="<s:text name="text_terminalEvent_deleterecord"/>" /></td>
						</tr>
					</table>
				</div>
			</form>

		</div>
		<table border="0" cellpadding="0" cellspacing="0" class="tcDi">
			<tr>
				<td style="width: 5px;"><img
					src="${theme}/skins/default/images/tcDi1.png" width="5"
					height="8" class="left" /></td>
				<td class="bg"></td>
				<td style="width: 5px;"><img
					src="${theme}/skins/default/images/tcDi2.png" width="5"
					height="8" class="right" /></td>
			</tr>
		</table>
	</div>
	<!-- 弹出窗口 结束-->
	<!-- 表格间隔变色 -->
	<script>
		// Give classes to even and odd table rows for zebra striping.
		$(document).ready(function() {
		    //$('tr:odd').addClass('alt');
			
			$('.shujTb > tbody >tr:odd').addClass('alt');
			$('tr:nth-child(even)').addClass('alt');

			$('td:contains(Henry)').nextAll().andSelf().addClass('highlight');//.siblings()
			$('th').parent().addClass('table-heading');
			
			
			$("#begindate").focus(function () {
				 WdatePicker({startDate:'%y-%M-01',dateFmt:'yyyy-MM-dd',lang:'<s:text name="lang" />',alwaysUseStartDate:true}); 
            });
           

		});
	</script>

</body>
<script type="text/javascript">
function  getEvent(event) {
		var temp = "<s:text name="terminalonline" />";
		switch (event) {
			case 1 :
				temp = "<s:text name="terminalboot" />";
				break;
			case 2 :
				temp = "<s:text name="terminalonline" />";
				break;
			case 3 :
				temp = "<s:text name="terminaloffline" />";
				break;
			case 4 :
				temp = "<s:text name="terminalupload" />";
				break;
			case 5 :
				temp = "<s:text name="terminaldownload" />";
				break;
			default :
				temp = "<s:text name="terminalonline" />";
				break;
		}
		return temp;
	}

</script>
</html>