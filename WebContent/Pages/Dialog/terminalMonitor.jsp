<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8"%>
	<%@page import="com.gnamp.server.model.Terminal"%>
<%@ taglib uri="/struts-tags" prefix="s"%>


<%!public String GetSelected(Boolean f) {
		return f ? "selected" : "";
	}%>
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

<script src="../Pages/Dialog/My97DatePicker/WdatePicker.js" language="javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#start1").focus(function () {
		 WdatePicker({startDate:'%y-%M-01',dateFmt:'yyyy-MM-dd',lang:'<s:text name="lang" />',alwaysUseStartDate:true}); 
	});
	$("#end1").focus(function () {
		 WdatePicker({startDate:'%y-%M-01',dateFmt:'yyyy-MM-dd',lang:'<s:text name="lang" />',alwaysUseStartDate:true}); 
	});
	$("#starttime1").focus(function () {
		WdatePicker({startDate:'%H:%m:s%',dateFmt:'HH:mm:ss',lang:'<s:text name="lang" />',alwaysUseStartDate:true});
	});
	
	$("#start2").focus(function () {
		 WdatePicker({startDate:'%y-%M-01',dateFmt:'yyyy-MM-dd',lang:'<s:text name="lang" />',alwaysUseStartDate:true}); 
	});
	$("#end2").focus(function () {
		 WdatePicker({startDate:'%y-%M-01',dateFmt:'yyyy-MM-dd',lang:'<s:text name="lang" />',alwaysUseStartDate:true}); 
	});
	$("#starttime2").focus(function () {
		WdatePicker({startDate:'%H:%m:s%',dateFmt:'HH:mm:ss',lang:'<s:text name="lang" />',alwaysUseStartDate:true});
	});
	
	$("#start3").focus(function () {
		 WdatePicker({startDate:'%y-%M-01',dateFmt:'yyyy-MM-dd',lang:'<s:text name="lang" />',alwaysUseStartDate:true}); 
	});
	$("#end3").focus(function () {
		 WdatePicker({startDate:'%y-%M-01',dateFmt:'yyyy-MM-dd',lang:'<s:text name="lang" />',alwaysUseStartDate:true}); 
	});
	$("#starttime3").focus(function () {
		WdatePicker({startDate:'%H:%m:s%',dateFmt:'HH:mm:ss',lang:'<s:text name="lang" />',alwaysUseStartDate:true});
	});
	$("#starttime33").focus(function () {
		WdatePicker({startDate:'%H:%m:s%',dateFmt:'HH:mm:ss',lang:'<s:text name="lang" />',alwaysUseStartDate:true});
	});
	
	$("#start4").focus(function () {
		 WdatePicker({startDate:'%y-%M-01',dateFmt:'yyyy-MM-dd',lang:'<s:text name="lang" />',alwaysUseStartDate:true}); 
	});
	$("#end4").focus(function () {
		 WdatePicker({startDate:'%y-%M-01',dateFmt:'yyyy-MM-dd',lang:'<s:text name="lang" />',alwaysUseStartDate:true}); 
	});
	$("#starttime4").focus(function () {
		WdatePicker({startDate:'%H:%m:s%',dateFmt:'HH:mm:ss',lang:'<s:text name="lang" />',alwaysUseStartDate:true});
	});
});

	function doSubmit(obj) {	
		
		if (obj == "reset"){
			 if($("#starttime1").val()== 'undefined' || $("#starttime1").val()==''
				 || $("#start1").val()== 'undefined' || $("#start1").val()==''
				 || $("#end1").val()== 'undefined' || $("#end1").val()=='' ){
					 alert('<s:text name="notnull" />');
					 return;
				 } 
		 }					 					 
		 if (obj == "demand"){
			 if($("#starttime4").val()== 'undefined' || $("#starttime4").val()==''
			 || $("#start4").val()== 'undefined' || $("#start4").val()==''
			 || $("#end4").val()== 'undefined' || $("#end4").val()==''
			 || $("#Keyvalue").val()== 'undefined' || $("#Keyvalue").val()=='00' ){
				 alert('<s:text name="notnull" />');
				 return;
			 }
		 }					 	
		 if (obj == "capture"){
			 if($("#starttime2").val()== 'undefined' || $("#starttime2").val()==''
				 || $("#start2").val()== 'undefined' || $("#start2").val()==''
				 || $("#end2").val()== 'undefined' || $("#end2").val()=='' ){
					 alert('<s:text name="notnull" />');
					 return;
				 }
		 }					 	
		 if (obj == "standby"){
			 if($("#starttime3").val()== 'undefined' || $("#starttime3").val()==''
				 || $("#starttime33").val()== 'undefined' || $("#starttime33").val()==''				 
				 || $("#start3").val()== 'undefined' || $("#start3").val()==''
				 || $("#end3").val()== 'undefined' || $("#end3").val()=='' ){
					 alert('<s:text name="notnull" />');
					 return;
				 }
		 }				
		
		var msg = "";
		
		switch(obj){
		 case "reset":
		       msg = "<s:text name="isreboot" />";
		       break;
		 case "clearreset":
		 case "cleardemand":
		 case "clearcapture":
		 case "clearstandby":
			   msg = "<s:text name="isclear" />";
			   break;
		 case "capture":
			   msg = "<s:text name="isscreenshot" />";
			   break;
		 case "standby":
			   msg = "<s:text name="isstandby" />";
			   break;
		 case "demand":
			   msg = "<s:text name="isdemand" />";
			   break;
		 default:
			   msg = "";
			   break;
		}
		
		confirm(msg,function(){
			$("#subtype").val(obj);
			var param = $('#form1').serialize();    		
			$.ajax({
				type : "post",
				dataType : "json",
				url : "terminal!Monitor",
				data : param,
				complete : function() {
					//compliteCallback();
				},
				success : function(msg) {    				
					if(msg.success){
					  //alert(msg.data);
	    			 		callParentFunction("finished", 'ok');	
	    			 		
			         
			         
					 if (obj == "clearreset"){
						 $("#starttime1").val(""); 
						 $("#start1").val(""); 
						 $("#end1").val(""); 
					 }					 					 
					 if (obj == "cleardemand"){
						 $("#starttime4").val(""); 
						 $("#start4").val(""); 
						 $("#end4").val(""); 
						 $("#KeyValue").val("00"); 
					 }					 	
					 if (obj == "clearcapture"){
						 $("#starttime2").val(""); 
						 $("#start2").val(""); 
						 $("#end2").val(""); 
					 }					 	
					 if (obj == "clearstandby"){
						 $("#starttime3").val(""); 
						 $("#starttime33").val(""); 
						 $("#start3").val(""); 
						 $("#end3").val(""); 
					 }					 	
			         
					}
					else{
						alert(msg.data);
					}
					//callback(msg);
					

					//return closeIFrame();
					//alert('操作成功！');
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					 window.top.location.href='/';
					//errorCallback(XMLHttpRequest, textStatus, errorThrown);
				}
			});
			return false;
		});
	}

	function standby(msg) {		
	 	confirm(msg,function(){
	 		var param = {
	 				"selectIDs": "<%= request.getAttribute("selectIDs").toString() %>"
				     };
				$.ajax({
					type : "post",
					dataType : "json",
					url : "terminal!standby",
					data : param,
					complete : function() {
					},
					success : function(msg) {
						if (msg.success) {
							//return closeIFrame();
							// alert('操作成功！');
						} else {
							alert(msg.data);
						}
					},
					error : function(XMLHttpRequest, textStatus,
							errorThrown) {
						window.top.location.href='/';
					}
				});
				$("#starttime3").val("00:00:00"); 
				 $("#starttime33").val("23:59:59"); 
				 $("#start3").val("1970-01-01"); 
				 $("#end3").val("2038-01-01"); 
			return false;
		}); 
	}
	
	function wakeup(msg) {		
	 	confirm(msg,function(){
	 		var param = {
	 				"selectIDs": "<%= request.getAttribute("selectIDs").toString() %>"
				     };
				$.ajax({
					type : "post",
					dataType : "json",
					url : "terminal!wakeup",
					data : param,
					complete : function() {
					},
					success : function(msg) {
						if (msg.success) {
							//return closeIFrame();
							// alert('操作成功！');
						} else {
							alert(msg.data);
						}
					},
					error : function(XMLHttpRequest, textStatus,
							errorThrown) {
						window.top.location.href='/';
					}
				});
			     $("#starttime3").val(""); 
				 $("#starttime33").val(""); 
				 $("#start3").val(""); 
				 $("#end3").val(""); 
			return false;
		}); 
	}
	
	function reboot(msg) {		
	 	confirm(msg,function(){
	 		var param = {
	 				"selectIDs": "<%= request.getAttribute("selectIDs").toString() %>"
				     };
				$.ajax({
					type : "post",
					dataType : "json",
					url : "terminal!reboot",
					data : param,
					complete : function() {
					},
					success : function(msg) {
						if (msg.success) {
							//return closeIFrame();
							// alert('操作成功！');
						} else {
							alert(msg.data);
						}
					},
					error : function(XMLHttpRequest, textStatus,
							errorThrown) {
						window.top.location.href='/';
					}
				});
			return false;
		}); 
	}
	
	function clearall(msg) {		
	 	confirm(msg,function(){
	 		var param = {
	 				"selectIDs": "<%= request.getAttribute("selectIDs").toString() %>"
				     };
				$.ajax({
					type : "post",
					dataType : "json",
					url : "terminal!clear",
					data : param,
					complete : function() {
					},
					success : function(msg) {
						if (msg.success) {
							//return closeIFrame();
							// alert('操作成功！');
						} else {
							alert(msg.data);
						}
					},
					error : function(XMLHttpRequest, textStatus,
							errorThrown) {
						window.top.location.href='/';
					}
				});
			return false;
		}); 
	}
	
	function screenshot(msg) {		
	 	confirm(msg,function(){
	 		var param = {
	 				"selectIDs": "<%= request.getAttribute("selectIDs").toString() %>"
				     };
				$.ajax({
					type : "post",
					dataType : "json",
					url : "terminal!screenshot",
					data : param,
					complete : function() {
					},
					success : function(msg) {
						if (msg.success) {
							//return closeIFrame();
							//alert('操作成功！');
						} else {
							alert(msg.data);
						}
					},
					error : function(XMLHttpRequest, textStatus,
							errorThrown) {
						window.top.location.href='/';
					}
				});
			return false;
		}); 
	}
	function demand(msg) {		
	 	confirm(msg,function(){
	 		var param = {
					"selectIDs": "<%= request.getAttribute("selectIDs").toString() %>",
					"num": $("#pn").val()
				     };
				$.ajax({
					type : "post",
					dataType : "json",
					url : "terminal!demand",
					data : param,
					complete : function() {
					},
					success : function(msg) {
						if (msg.success) {
							//return closeIFrame();
							//alert('操作成功！');
						} else {
							alert(msg.data);
						}
					},
					error : function(XMLHttpRequest, textStatus,
							errorThrown) {
						window.top.location.href='/';
					}
				});
			return false;
		}); 
	}
	
	function cancl() {
 		callParentFunction("finished", 'ok');
		return closeIFrame();
	}
	
	function run(obj)
	{		
		/* if(obj.value !=""){
			var reg = /^[0-2][0-3]:[0-5][0-9]:[0-5][0-9]$/;
			var s = obj.value;
			if(reg.test(s)){}         
			else{
			  alert("<s:text name="timeerror" />");
			  obj.value="01:01:01";
			  obj.focus();
			  return;
			}	
	    } */
	}
	function dd()
	{
		//alert('dd');
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
				<% if(terminal != null && terminal.getDeviceId()>0 ){ %> 
					<s:text name="curterminal" />:&nbsp;${terminal.deviceName };&nbsp;
					ID:&nbsp;<%= String.format("%012X", terminal.getDeviceId())  %>  
					<input type="hidden" value="${terminal.deviceId }" name="terminal.deviceId" />
				<%} %>	
		    </h2>
			<img src="${theme}/skins/default/images/tcBt3.gif" class="right" />
		</div>
		<div class="tcNr">		
		
	<!-- <input onfocus="alert('dd')" />    -->
	<form id="form1" class="niceform">  
				 <input type="hidden" value="${terminal.deviceId }" name="terminal.deviceId" />
				 <input type="hidden" id="subtype" name="monitortype" />
				 <input value="<%= request.getAttribute("selectIDs") %>" name="selectIDs" type="hidden" />	
			  <div class="tcBt2"><!--  style="display:none;" --><h2><s:text name="realtime" /></h2></div>						
				<div style="height:40px; text-align:center; padding-top:10px;"><!-- display:none -->
					<!--
					<input type="button" onclick="standby('<s:text name="isstandby" />');" value="<s:text name="standby" />" style="display:none;" />
					&nbsp; &nbsp;
					<input type="button" onclick="wakeup('<s:text name="iswakeup" />');" value="<s:text name="wakeup" />" style="display:none;" />
					&nbsp; &nbsp;
					<input type="button" onclick="clearall('<s:text name="isclear" />');" value="<s:text name="clear" />" style="display:none;" />
					&nbsp; &nbsp;
					<input type="button" onclick="reboot('<s:text name="isreboot" />');" value="<s:text name="reboot" />" style="display:none;" />
					&nbsp; &nbsp;
					<input type="button" onclick="screenshot('<s:text name="isscreenshot" />');" value="<s:text name="screenshot" />" style="display:none;" />
					&nbsp; &nbsp;
					-->
					<input type="button" onclick="demand('<s:text name="isdemand" />');" value="<s:text name="text_remote_demand" />" />
						<div style="text-align: left; display: block;display:inline-block;">						
						<select size="1" id="pn" name="pn">
								<option value="00">00</option>
								<%
									for (int i = 1; i < 100; i++) {

										String s = "";
										if (i < 10) {
											s = "0" + String.valueOf(i);
										} else {
											s = String.valueOf(i);
										}
								%>
								<option value='<%=s%>'>
									<%=s%>
								</option>
								<%
									}
								%>
						</select>
						</div>
				</div>
			 <div class="tcBt2"><!--  style="display:none;" --><h2><s:text name="text_schedule_monitor" /></h2></div>		
				<table width="100%" border="0" cellspacing="2" cellpadding="0"
					class="bdTab">	
					<tr>
						<td style="width: 25%"><s:text name="startdate" /></td>
						<td style="width: 25%"><s:text name="enddate" /></td>
						<td style="width: 25%"><s:text name="timing" /></td>
						<td style="width: 25%"><s:text name="control" /></td>
					</tr>
					<tr> <!--  style="display:none;" -->
						<td><input class="focus" type="text" name="ResetStartDate" id="start1" 
							value="${ResetStartDate }" /></td>
						<td><input type="text" name="ResetStopDate" id="end1"  
							value="${ResetStopDate }" /></td>
						<td><input type="text" name="ResetStartTime" id="starttime1" 
							value="${ResetStartTime }" onblur="run(this)" /></td>
						<td><input type="button" onclick="doSubmit('reset')"
							 value="<s:text name="text_schedule_reboot" />" /> &nbsp; &nbsp; <input
							type="button" onclick="doSubmit('clearreset')"  value="<s:text name="text_clear_setting" />" /></td>
					</tr>
					<tr>
						<td><input type="text" name="CaptureStartDate" id="start2"  
							value="${CaptureStartDate }" /></td>
						<td><input type="text" id="end2" name="CaptureStopDate"  
							value="${CaptureStopDate }" /></td>
						<td><input type="text" name="CaptureStartTime" 
							id="starttime2" value="${CaptureStartTime }" onblur="run(this)" /></td>
						<td><input type="button" onclick="doSubmit('capture')"
							 value="<s:text name="text_scheduled_capturescreen" />" /> &nbsp; &nbsp; <input
							 type="button" onclick="doSubmit('clearcapture')"  value="<s:text name="text_clear_setting" />" /></td>
					</tr>
					<tr style="display:none;">
						<td><input type="text" name="StandStartDate" id="start3"  
							value="${StandStartDate }" /></td>
						<td><input type="text" id="end3" name="StandStopDate"  
							value="${StandStopDate }" /></td> 
						<td>	
						<p>				
						<input style="width: 55px" type="text" 
							name="StandStartTime" id="starttime3" value="${StandStartTime }" onblur="run(this)" />
							 						
							<input style="width: 55px" type="text" name="StandStopTime" 
							id="starttime33" value="${StandStopTime }" onblur="run(this)" />
							</p>	
							</td>
						<td><input type="button" onclick="doSubmit('standby')"
							 value="<s:text name="standby" />" /> &nbsp; &nbsp; <input 
							type="button"  onclick="doSubmit('clearstandby')" value="<s:text name="text_clear_setting" />" /></td>
					</tr>
					<tr style="display:none;">
						<td><input type="text" name="DemandStartDate" id="start4"  
							value="${DemandStartDate }" /></td>
						<td><input type="text" name="DemandStopDate" id="end4" 
							value="${DemandStopDate }" /></td>
						<td>
						<input type="text"
							name="DemandStartTime" id="starttime4" 
							value="${DemandStartTime }"  onblur="run(this)" />
							<%-- <s:text name="program" />  --%>
						
						  
						</td>
						<td><input type="button"
							 value="<s:text name="text_control_demand" />" onclick="doSubmit('demand')" /> &nbsp; &nbsp; <input
							 type="button" onclick="doSubmit('cleardemand')"  value="<s:text name="text_clear_setting" />" />
							  <div style="display: block;display:inline-block;">
							  <select size="1" id="KeyValue" name="KeyValue">
								<option value="00">00</option>
								<%
								   String selec="00"; 
								   if(request.getAttribute("keyValue") != null)
								   {
									    selec = request.getAttribute("keyValue").toString();
								   }
									for (int i = 1; i < 100; i++) {

										String s = "";
										if (i < 10) {
											s = "0" + String.valueOf(i);
										} else {
											s = String.valueOf(i);
										}
								%>
								<option value='<%=s%>' <% if(s.equals(selec)){ %> selected <%} %>>
									<%=s%>
								</option>
								<%
									}
								%>
						</select>
						</div>
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
</body>

</html>
