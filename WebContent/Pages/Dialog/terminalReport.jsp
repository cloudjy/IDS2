<%@page import="com.gnamp.server.model.*"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=8" /> 



<link href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" rel="stylesheet" type="text/css" />
<script src="${theme}/js/jquery-1.7.2.min.js"></script>
<script language="javascript" type="text/javascript" src="${theme}/js/niceforms.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="${theme}/skins/default/css/niceforms-default.css" />
 
<script src="${theme}/js/ipub.models-<s:text name="langjs"/>.js" type="text/javascript"></script>
<script src="js/gnamp.js"></script>


<script>
$(function(){
	var param = {"selectIDs" : "<%=request.getAttribute("selectIDs") %>"
	};
	
	$p = new page("terminal!reportlist",param,showlist);	
	$p.ajax();
	
	$("#first").bind("click",$p.first);
	$("#previous").bind("click",$p.previous);
	$("#next").bind("click",$p.next);
	$("#last").bind("click",$p.last);
});

function reportlist(){	
	
	var param = {"selectIDs" : "<%=request.getAttribute("selectIDs") %>"
	};	
	
	   $p.ajax(param);
  		return false;
}

function report(){
	var param = {"selectIDs" : "<%=request.getAttribute("selectIDs") %>"
	};	
	location.href = "terminal!reportselect?"+$.param(param);
	return false;
}
function showlist(result){
	try {
		var content = "";
		$.each(
						result.data,
						function(i, f) {
							
							var tmp="";
							if((f.deviceId).toString(16).length<12){
								for(var i=0;i<(12-(f.deviceId).toString(16).length);i++)
								tmp +="0"; 
							}								
							
							content += "<tr>"
									+ "<td style=\"width: 70px;\">"
									+ ((f.deviceId).toString(16).length<12? tmp+(f.deviceId).toString(16).toUpperCase():(f.deviceId).toString(16).toUpperCase())
									+ "</td>"
									+ "<td style=\"width: 100px;\"><label title=\""+f.deviceName+"\">"
									+ (f.deviceName.length > 7 ? f.deviceName.substr(0,
											7)
											+ "..." : f.deviceName)
									+ "</label></td>"
									+ "<td style=\"width: 70px;\">"
									+ f.curLoopTaskName
									+ "</td>"
									+ "<td style=\"width: 70px;\">"
									+ f.curDemandTaskName
									+ "</td>"
									+ "<td style=\"width: 70px;\">"
									+ f.curPluginTaskName
									+ "</td>"
									+ "<td style=\"width: 70px;\">"
									+ (( (f.appUpdated==1 || f.appUpdated==0) ) ? "<s:text name="synchronized"/>" : "<s:text name="unsynchronized"/>")
									+ "</td>"
									+ "<td style=\"width: 70px;\">"
									+ (( (f.kernelUpdated==1 || f.kernelUpdated==0) ) ? "<s:text name="synchronized"/>" : "<s:text name="unsynchronized"/>")
									+ "</td>"
									+ "<td style=\"width: 70px;\">"
									+ (( (f.configUpdated==1 || f.configUpdated==0) ) ? "<s:text name="synchronized"/>" : "<s:text name="unsynchronized"/>")
									+ "</td>"
									+ "<td style=\"width: 150px;\">"
									+ f.lastDownload
									+ "</td>"							
									+ "<td style=\"width: 70px;\">";
									if(f.downloadTotal>0){																		
										content+= ( (f.downloadFinished / f.downloadTotal) == 1 ? "<s:text name="finished"/>" : ( (Math.round((f.downloadFinished / 1024 / 1024)*100)/100) +"/"+ (Math.round((f.downloadTotal / 1024 / 1024)*100)/100) ) );
										}
										else{
										content+=(( (f.loopTaskUpdated==1 || f.loopTaskUpdated==0)  && (f.demandTaskUpdated==1 || f.demandTaskUpdated==0) && (f.pluginTaskUpdated==1 ||f.pluginTaskUpdated==0) ) ? "<s:text name="finished"/>" : "");
										}
									content+= "</td>"
									+ "<td style=\"width: 150px;\">"
									+ f.lastPatrol
									+ "</td>"	
									+ "<td style=\"word-break: keep-all; width:70px; white-space:nowrap\">"
									+ (f.aliveInterval == 1 ? "<s:text name="normal"/>" : "<s:text name="abnormal"/>")
									+ "</td>"
									+ "<td style=\"width: 70px;\">"
									+ (f.onlineState == 0 ? "<img src=\"${theme}/skins/default/images/btn5.png\" height=\"16px\" width=\"16px\" />"
											: "<img src=\"${theme}/skins/default/images/btn1.png\" height=\"16px\" width=\"16px\" />")
									+ "</td>"
									+ "</tr>";
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
</script>

</head>
<body>
	
<!-- 弹出窗口 开始-->
<div class="tcWindow" style="width:150%" >
    <table border="0" cellpadding="0" cellspacing="0" class="tcBt">
      <tr><td style="width:5px;"><img src="${theme}/skins/default/images/tcBt1.png" width="5" height="35" class="left" /></td>
    <td class="bg"><h2></h2></td><td  style="width:5px;"><img src="${theme}/skins/default/images/tcBt3.png" width="5" height="35" class="right"/></td>
    </tr></table>
	<div class="tcNr">
	<form action="" method="post" class="niceform">
	 
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="shujBt">
            <tr>
                <td style="width:70px" class="first"><s:text name="terminalid"/></td>
				<td style="width:100px"><s:text name="terminalname"/></td>
				<td style="width:70px"><s:text name="text_terminalreport_loop"/></td>
				<td style="width:70px"><s:text name="text_terminalreport_demand"/></td>
				<td style="width:70px"><s:text name="text_terminalreport_insert"/></td>
				<td style="width:70px"><s:text name="text_terminalreport_software"/></td>
				<td style="width:70px"><s:text name="text_terminalreport_firmware"/></td>
				<td style="width:70px"><s:text name="text_terminalreport_config"/></td>
				<td style="width:150px"><s:text name="text_terminalreport_download"/></td>
				<td style="width:70px"><s:text name="text_terminalreport_progress"/></td>
				<td style="width:150px"><s:text name="text_terminalreport_recentcheck"/></td>
				<td style="width:70px"><s:text name="pipe"/></td>
				<td style="width:70px"><s:text name="text_terminalreport_network"/></td>
            </tr>
          </table>
	    <table id="mytab" width="100%" border="0" cellspacing="0" cellpadding="0" class="shujTb">
               
                    </table>
					</form>
					<div class="page1 page2"
							 style="width: 570px; overflow: hidden; padding-top: 5px; margin-left:740px;">
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
			<form  method="post" class="niceform">
				<div style="height: 40px; text-align: left; padding-top: 10px;">
					<table style="width: 500px;" border="0" cellspacing="0"
						cellpadding="0">
						<tr>
							<td width="117" align="right">
							<input type="button" onclick="return report();" value="<s:text name="exportreport"/>" />
							</td>
							<td width="200" style="width: 170px;"></td>
							<td width="183"></td>
						</tr>
					</table>
				</div>
			</form>

		</div>
	<table border="0" cellpadding="0" cellspacing="0" class="tcDi">
  <tr><td style="width:5px;"><img src="${theme}/skins/default/images/tcDi1.png" width="5" height="8" class="left" /></td>
  <td class="bg"></td><td  style="width:5px;"><img src="${theme}/skins/default/images/tcDi2.png" width="5" height="8" class="right"/></td>
  </tr></table>
   
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