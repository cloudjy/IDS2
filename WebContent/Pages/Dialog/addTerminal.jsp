<%@page import="com.gnamp.server.model.*"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
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
	
	jQuery(function () { 
		try { 
			var beijing = $("#ddlprovince > option[value=\"北京\"]");
			if (beijing.length > 0) { 
				beijing.attr("selected", true);
				QuerCityByProvince();
			} 
		} catch (err) { 
		}
	});
      
	function doSubmit() {
		$("#idErrorDiv").showError(""); 
		$("#nameErrorDiv").showError(""); 

		if (isNaN(parseInt($("#deviceId").val(), 16))) {
			// alert("<s:text name="deviderror"/>");
			$("#idErrorDiv").showError("<s:text name="deviderror"/>"); 
			return;
		}

		if (!$("#deviceName").val()) {
			// alert("<s:text name="devnamenull"/>");
			$("#nameErrorDiv").showError("<s:text name="devnamenull"/>"); 
			return;
		}
        var hexDeviceId = $("#deviceId").val();
        var decimalDeviceId = "";
		try { 
			decimalDeviceId = ""+parseInt(hexDeviceId, 16); 
		} catch (e) {
			$("#idErrorDiv").showError("<s:text name="deviderror"/>"); 
			return false;
		}

		$("#deviceId").val(decimalDeviceId); 
		var param = $('#form1').serialize();
		$("#deviceId").val(hexDeviceId);
		$.ajax({
			type : "post",
			dataType : "json",
			url : "terminal!AddTerminal",
			data : param,
			complete : function() {
				//compliteCallback();
			},
			success : function(result) {
				if (result.success) {
					//alert(result.data);
					callParentFunction("added", 'ok');
					return closeIFrame();
				} else {  
					if (result.data  && result.message) {
						if (result.data == "AlreadyExists") {
							$("#idErrorDiv").showError(result.message); 
						} else if ( "NameCannotNull" || result.data == "NameExist") {
							$("#nameErrorDiv").showError(result.message); 
						} else {
							alert(result.message); 
						} 
					} else if (result.message) {
						alert(result.message); 
					} else {
						alert("<s:text name="additemfail"/>");
					}	 	
				} 
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				window.top.location.href='/';
				//errorCallback(XMLHttpRequest, textStatus, errorThrown);
			}
		});
		return false;
	}

	function cancl() {
		closeIFrame();
	}

	function QuerCityByProvince() {
		var param = {
			"province" : $("#ddlprovince ").val()
		};

		$.ajax({
			type : "post",
			dataType : "json",
			url : "terminal!QuerCityByProvince",
			data : param,
			complete : function() {
			},
			success : function(msg) {
				if (msg.success) {
					try {
						var content = "";
						$.each(msg.data, function(i, city) {
							content += "<option value=\""+city.cityId+"\">"
									+ city.cityName + "</option>";
						});
						$("#ddlcity").html(content);
					} catch (e) {
						alert(e.message);
					}
				} else {
					alert(msg.success);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				window.top.location.href='/';
			}
		});
		return false;
	}
</script>

</head>
<body>
	<!-- 弹出窗口 开始-->
	<div class="tcWindow">
		<div class="tcBt">
			<img src="${theme}/skins/default/images/tcBt1.gif" width="5"
				height="35" class="left" />
			<h2></h2>
			<img src="${theme}/skins/default/images/tcBt3.gif"
				class="right" />
		</div>

		<div class="tcNr">
			<form id="form1" class="niceform">
				<input type="hidden" value="${ terminal.groupId}"
					name="terminal.groupId" />
				<table width="100%" border="0" cellspacing="2" cellpadding="0"
					class="bdTab">
					<tr>
						<td class="bt" style="width: 30%"><font color="#FF0000" size="3">&#x25B4;</font><s:text name="devid" /></td>
						<td>
							<input size="32" maxlength="12" 
								id="deviceId" type="text" name="terminal.deviceId" />
							<div id="idErrorDiv" style="color:#FF0000;"></div>
						</td>
					</tr>
					<tr>
						<td class="bt" style="width: 30%"><font color="#FF0000" size="3">&#x25B4;</font><s:text name="devicename" /></td>
						<td>
							<input size="32" maxlength="32" 
								id="deviceName" type="text" name="terminal.deviceName" />
							<div id="nameErrorDiv" style="color:#FF0000;"></div>
						</td>
					</tr>
					<tr>
						<td class="bt"><s:text name="desc" /></td>
						<td><input size="32" maxlength="32" 
							id="description" type="text" name="terminal.description" /></td>
					</tr>
					<tr>
						<td class="bt"><s:text name="weatherlocation" /></td>
						<td><s:text name="province" />
						<select size="1"
							name="province" onchange="QuerCityByProvince();" id="ddlprovince"
							style="margin-right: 10px;">
								<option value="" >
									<s:text name="select" />
								</option>
								<%
									if (request.getAttribute("provincelist") != null) {
										List<String> list = (List<String>) request
												.getAttribute("provincelist");
		
										for (int i = 0; i < list.size(); i++) {
								%>
								<option value="<%=list.get(i)%>" ><%=list.get(i)%></option>
								<%
									}
									}
								%>
						</select> 
						<s:text name="city" /> 						
						<select  size="1" id="ddlcity" 
							name="terminal.cityId">
								<option value="0">
									<s:text name="select" />
								</option>
						</select>
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

