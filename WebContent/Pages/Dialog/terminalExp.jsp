<%@page import="com.gnamp.server.model.Terminal"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%
	Terminal terminal = (Terminal) request.getAttribute("terminal");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=8" />



	
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

<script type="text/javascript">
	$(document).ready(function() {
		$("#startdate").focus(function() {
			WdatePicker({
				startDate : '%y-%M-01',
				dateFmt : 'yyyy-MM-dd',
				lang : '<s:text name="lang" />',
				alwaysUseStartDate : true
			});
		});
		$("#enddate").focus(function() {
			WdatePicker({
				startDate : '%y-%M-01',
				dateFmt : 'yyyy-MM-dd',
				lang : '<s:text name="lang" />',
				alwaysUseStartDate : true
			});
		});
	});

	
	$(document).ready(function() {
		
		var param = {
				"selectIDs" : "<%=request.getAttribute("selectIDs") %>",
				"resetcount" : $("#recount").val(),
				"checkhour" : $("#checkhour").attr("checked") == "checked" ? "true" : "false"     
		};
		
		alert($("#recount").val());
		
		$p = new page("terminal!explist",param,showlist);	
		$p.ajax();
		
		$("#first").bind("click",$p.first);
		$("#previous").bind("click",$p.previous);
		$("#next").bind("click",$p.next);
		$("#last").bind("click",$p.last);

		// 
		$(".NFCheck").attr("style", "top:53px; left:297px;");  
	});

	
	function exportExp(){
		var param = {
				"selectIDs" : "<%=request.getAttribute("selectIDs") %>",
				"resetcount" : $("#recount").val(),
				"checkhour" : $("#checkhour").attr("checked") == "checked" ? "true" : "false", 
						
				"begindate" : $("#startdate").val(),
				"enddate" : $("#enddate").val()
		};	
		location.href="terminal!exportExp?"+$.param(param);
	}
	
	function queryexplist(){			
		var param = {
				"selectIDs" : "<%=request.getAttribute("selectIDs") %>",
				"resetcount" : $("#recount").val() ,
				"checkhour" : $("#checkhour").attr("checked") == "checked" ? "true" : "false", 

				"begindate" : $("#startdate").val(),
				"enddate" : $("#enddate").val()
		};
		
	 $p.ajax(param);
     return false;
	}
	function showlist(result){
		try {
			var content = "";
			
			Date.prototype.format = function(format)
		       {
		        var o = {
		        "M+" : this.getMonth()+1, //month
		        "d+" : this.getDate(),    //day
		        "h+" : this.getHours(),   //hour
		        "m+" : this.getMinutes(), //minute
		        "s+" : this.getSeconds(), //second
		        "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
		        "S" : this.getMilliseconds() //millisecond
		        };
		        if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
		        (this.getFullYear()+"").substr(4 - RegExp.$1.length));
		        for(var k in o)if(new RegExp("("+ k +")").test(format))
		        format = format.replace(RegExp.$1,
		        RegExp.$1.length==1 ? o[k] :
		        ("00"+ o[k]).substr((""+ o[k]).length));
		        return format;
		       };
			
			if(result != null){
			$.each(
							result.data,
							function(i, f) {
								
								var tmp="";
								if((f.deviceId).toString(16).length<12){
									for(var i=0;i<(12-(f.deviceId).toString(16).length);i++)
									tmp +="0"; 
								}								
								
								content += "<tr>"
										+ "<td style=\"width: 240px;\">"
										+ ($("#checkhour").attr("checked") != "checked" ? (new Date(Date.parse(f.eventTime.replace(/-/g,   "/")))).format('yyyy-MM-dd') : (new Date(Date.parse(f.eventTime.replace(/-/g,   "/")))).format('yyyy-MM-dd hh:00'))
										+ "</td>"
										+ "<td style=\"width: 110px;\">"
										+ ((f.deviceId).toString(16).length<12? tmp+(f.deviceId).toString(16).toUpperCase():(f.deviceId).toString(16).toUpperCase())
										+ "</td>"
										+ "<td style=\"width: 200px;\">"
										+ f.deviceName
										+ "</td>"
										+ "<td style=\"width: 60px;\">"
										+ (f.playExp > 0 ? "<s:text name="abnormal"/>" : "<s:text name="normal"/>")
										+ "</td>"
										+ "<td style=\"width: 60px;\">"
										+ (f.timeExp > 0 ? f.timeExp : "0")
										+ "</td>"
										+ "<td style=\"width: 60px;\">"
										+ (f.resetExp > 0 ? f.resetExp : "")
										+ "</td>"
										+ "<td style=\"width: 60px;\">"
										+ (f.offExp > 0 ? "<s:text name="abnormal"/>" : "<s:text name="normal"/>")
										+ "</td>"
										+ "</tr>";
							});
			}
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
	
	
	
</script>
</head>
<body>

	<!-- 弹出窗口 开始-->
	<div class="tcWindow" style="width: 900px;">
		<table border="0" cellpadding="0" cellspacing="0" class="tcBt">
			<tr>
				<td style="width: 5px;"><img
					src="${theme}/skins/default/images/tcBt1.png" width="5"
					height="35" class="left" /></td>
				<td class="bg">
					<h2></h2>
				</td>
				<td style="width: 5px;"><img
					src="${theme}/skins/default/images/tcBt3.png" width="5"
					height="35" class="right" /></td>
			</tr>
		</table>
		<div class="tcNr">
			<form action="" method="post" class="niceform">
				<div class="tcBt2">
					<table width="48%" border="0" cellspacing="0" cellpadding="0"
						style="width: 650px;">
						<tr>
							<td width="78">&nbsp;<s:text name="text_terminalexp_date"/></td>
							<td width="77"><input type="text" name="startdate"
							  style="width:67px;" id="startdate" /></td>
							<td width="17">～</td>
							<td width="77"><input type="text" name="enddate" 
							  style="width:67px;" id="enddate" /></td>
							<td width="29"><input type="checkbox" name="checkhour"
								id="checkhour" value="News" /></td>
							<td width="103"><s:text name="text_terminalexp_inhour"/></td>
							<td width="109"><s:text name="text_terminalexp_rebootthresold"/></td>
							<td width="80"><select size="1" name="recount" id="recount">
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5" selected>5</option>
									<option value="6">6</option>
									<option value="7">7</option>
									<option value="8">8</option>
									<option value="9">9</option>
									<option value="10">10</option>
							</select></td>
						</tr>
					</table>
				</div>
			</form>

			<div>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="shujBt shujBt2">
					<tr>
				    	<td class="first" style="width: 240px;"><s:text name="period"/></td>
						<td style="width: 110px;"><s:text name="terminalid"/></td>
						<td style="width: 200px;"><s:text name="terminalname"/></td>
						<td style="width: 60px;"><s:text name="text_terminalexp_playback"/></td>
						<td style="width: 60px;"><s:text name="text_terminalexp_rtc"/></td>
						<td style="width: 60px;"><s:text name="text_terminalexp_reboot"/></td>
						<td style="width: 60px;"><s:text name="text_terminalexp_shutdown"/></td>
					</tr>
				</table>
				<table id="mytab" width="100%" border="0" cellspacing="0" cellpadding="0"
					class="shujTb shujTb2">
					<!-- <tr>						
						<td style="width: 110px;"></td>
						<td style="width: 250px;"></td>
						<td style="width: 110px;">&nbsp;</td>
						<td style="width: 110px;"></td>
						<td style="width: 110px;"></td>
						<td>&nbsp;</td>
					</tr> -->
				</table>
				<div style="height: 30px; background: #d5dcde;">
					<div class="page1 page2"
						style="width: 570px; overflow: hidden; padding-top: 5px; float: right;">
							<span><s:text name="p_altogether"/><b class="orange1"><span id="totalData"></span></b><s:text name="p_article"/></span> 
						<span><s:text name="p_first"/><b id="currentWithTotal"></b><s:text name="p_page"/></span>
						<span><s:text name="p_everypage"/>
						   <select name="pagesize" onchange="$p.changepagesize(this.value)">
						     <!-- option value="10">10</option -->
						     <option value="20">20</option>
						     <option value="50">50</option>
						     <option value="<%=Integer.MAX_VALUE %>"><s:text name="p_all"/></option>
						   </select> 
					  	 </span>
					   	<span><s:text name="p_goto"/>
						   <select name="pageIndex" id="pageIndex" onchange="$p.gotoPage(this.value)">
						     <option value="1">1</option>
						   </select> 
					  	 </span>
                        <img src="${theme}/skins/default/images/pageB1.gif" id="first" />
                        <img src="${theme}/skins/default/images/pageB2.gif" id="previous"/>
                        <img src="${theme}/skins/default/images/pageB3.gif" id="next"/>
                        <img src="${theme}/skins/default/images/pageB4.gif" id="last"/>
					</div>
				</div>
				<div class="rightMenu"
					style="height: 20px; margin: 0px; padding: 5px; padding-left: 350px;">
					<ul class="left rightMenuLong">
						<li><a onclick="queryexplist(); return false;"><s:text name="text_terminalexp_retrieve"/></a></li>
						<li> <a href="javascript:exportExp();"><s:text name="text_terminalexp_export"/></a></li>
					</ul>
				</div>



			</div>
			<div style="clear: both; height: 1px; overflow: hidden;"></div>

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
			// $('tr:odd').addClass('alt');
			$('tr:nth-child(even)').addClass('alt');

			$('td:contains(Henry)').nextAll().andSelf().addClass('highlight');//.siblings()
			$('th').parent().addClass('table-heading');
		});
	</script>
</body>
</html>