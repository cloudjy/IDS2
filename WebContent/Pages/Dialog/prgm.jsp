<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="com.gnamp.server.model.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%
LoopStrategy strategy = (LoopStrategy)request.getAttribute("strategy");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=8" /> 

<style type="text/css">
body { 
overflow-x:hidden; /*隐藏水平滚动条*/ 
overflow-y:hidden; /*隐藏垂直滚动条*/ 
} 
</style>
<link href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" /> 
<script src="${theme}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript"
	src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>   
<script language="javascript" type="text/javascript" src="${theme}/js/niceforms.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="${theme}/skins/default/css/niceforms-default.css" />
<script src="${theme}/js/ipub.models-<s:text name="langjs"/>.js" type="text/javascript"></script>
<link href="${theme}/skins/default/css/ipubs-dialog.css" rel="stylesheet" type="text/css" />


 
<script src="js/gnamp.js" language="javascript"></script>   
<script src="../Pages/js/JScript.js"></script>
<script type="text/javascript">	
$(function() {
	queryprgm();
	queryright();
});

function queryprgm() {	
	var param = {
			"loopprogram.taskId" : <%= strategy.getTaskId() %>
		};
	
  	$.ajax({
		type : "post",
		dataType : "json",
		url : "loopprogram!looplist",
		data : param,
		complete : function() {
		},
		success : function(msg) {
			try {
				var content = "";	
				content += "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"shujBt shujBt2\">";
				content += "<tr>";
					content += "<td  id=\"namesort\" onclick='namesort();' class=\"first\" style=\"cursor: pointer;width: 150px;\"><s:text name="name" /></td>";
				
					content += "</tr>";
						content += "</table>";
							content += "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"shujTb shujTb2\">";
				
			 			$.each(
								msg.data,
								function(i, f) {
										
											content += "<tr id=\""
												+ f.programId
												+ "\">"
												+ "<td style=\"width:12px;\"><lable><input type=\"checkbox\" value=\""+f.programId+"\" name=\""+f.programId+"\" id=\"" 
												+  f.programId
												+  "\" class=\"programId\" />"
												+ "</label></td>"
												+ "<td title=\""+f.programName+"\" style=\"width:200px;\">"
												+ (f.programName.length > 15 ? f.programName
														.substr(0, 15)
														+ "..."
														: f.programName)
												+ "</td>"
				                        +"</tr> ";

											
								});

                  content+="</table>";     
                  
				$("#mytab").html(content);
			} catch (e) {
				alert(e.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			window.top.location.href='/';
		}
	});		  
  	return false;
  }	
  
function view(TaskID, ProgramID, W, H) {
    showIPUBDialogStand('looprect!showprogram?TaskID=' + TaskID + '&ProgramID=' + ProgramID + '&RequestW=' + W +
            '&RequestH=' + H,{title:"<s:text name="programpreview"/>",width:1028,height:638});		

}

function addfile() {			
var arr = $("input[class='programId']");  //document.getElementsByTagName('input');
var ok = 0;
var selectID = 0;
var selectIDs = "";
for ( var i = 0; i < arr.length; i++) {
	var abc = arr[i];
	if (abc.type == "checkbox") {
		if (abc.checked == true) {
			ok++;					
		    selectID = abc.value;
			selectIDs += selectID.toString() + ",";
		}
	}
}

if (ok > 0) {
			var param = {
				"selectIDs" : selectIDs.substring(0, selectIDs.length - 1),
				"prgm.taskId" : <%= strategy.getTaskId() %>,
				"prgm.strategyId" : <%= strategy.getStrategyId() %>
			     };
			
				$.ajax({
				type : "post",
				dataType : "json",
				url : "date!addfile",
				data : param,
				complete : function() {
				},
				success : function(msg) {
					if (msg.success) {								
						queryright();
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
else
 alert('<s:text name="items" />');
}

function queryright(remember) {		         	
var param = {
		"prgm.taskId" : <%= strategy.getTaskId() %>,
		"prgm.strategyId" : <%= strategy.getStrategyId() %>
	};

$.ajax({
	type : "post",
	dataType : "json",
	url : "date!prgmlist",
	data : param,
	complete : function() {
	},
	success : function(msg) {
		try {
			var content = "";	
		
		 			$.each(
							msg.data,
							function(i, f) {
								var checked = false;
								if (remember) {
									for ( var i = 0; i < remember.length; i++) {
										if (remember[i] == f.serialId) {
											checked = true;
											break;
										}
									}
								}		
									
										content += "<tr id=\""
											+ f.serialId
											+ "\">"
											+ "<td style=\"width:12px;\"><lable><input type=\"checkbox\" value=\""+f.serialId+"\" name=\""+f.serialId+"\"  " 
											+ (checked? " checked ":" ") + " id=\"" 
											+  f.serialId
											+  "\" class=\"serialId\" />"
											+ "</label></td>"
											+ "<td title=\""+f.programName+"\" style=\"width:225px;\">"
											+ (f.programName.length > 15 ? f.programName
													.substr(0, 15)
													+ "..."
													: f.programName)
											+ "</td>"
											+ "<td style=\"width:60px;\">"
											+ " <input onkeypress=\"if ((event.keyCode<48 || event.keyCode>57) && event.keyCode!='.') event.returnValue=false;\" onblur=\"changecount("+f.taskId+","+f.strategyId+","+f.serialId+","+i.toString()+");\"  value=\""+getCount(f.playCount)+"\" id=\"txt"+i+"\" style=\"width: 30px; background:transparent;border:1px solid #ffffff;\" />"
											/* + "<a style=\"cursor: pointer;\" id=\"save"+i.toString()+"\" onclick=\"changecount("+f.taskId+","+f.strategyId+","+f.serialId+","+i.toString()+");\" value=\""+(f.playCount+1)+"\" >保存</a>" */
											+ "</td>"	
											+ "<td style=\"width:60px;\">"
											+ f.sequenceNum
											+ "</td>"	
			                        +"</tr> ";

										
							});   
             
			$("#righttab").html(content);
			$('.shujTb >tr:odd').addClass('alt');
		} catch (e) {
			alert(e.message);
		}
	},
	error : function(XMLHttpRequest, textStatus, errorThrown) {
		window.top.location.href='/';
	}
});		  
	return false;

}

function getCount(playcount){
	if(playcount>0){
		return playcount;
	}else{
		return "";
	}
}

function changecount(taskId, strategyId, serialId, id){
	 var param={
			 "prgm.taskId": taskId,
			 "prgm.strategyId": strategyId,
			 "prgm.serialId": serialId,
			 "playcount": $("#txt"+id+"").val()
	 };
	 
	  $.ajax({
			type : "post",
			dataType : "json",
			url : "date!changecount",
			data : param,
			complete : function() {
				//compliteCallback();
			},
			success : function(msg) {    				
				if(msg.success){
					queryright();
				}
				else{
					alert(msg.data);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				 window.top.location.href='/';
			}
		});
		return false;
}






var remember = new Array();
function del(msg) {

var arr = $("input[class='serialId']");  //document.getElementsByTagName('input');
var ok = 0;
var selectID = 0;
var selectIDs = "";
for ( var i = 0; i < arr.length; i++) {
	var abc = arr[i];
	if (abc.type == "checkbox") {
		if (abc.checked == true) {
			ok++;					
		    selectID = abc.value;
			selectIDs += selectID.toString() + ",";
			remember.push(abc.value);
		}
	}
}

if (ok > 0) {
	if (msg != null && msg.length > 0 && msg != "up" && msg != "down") {   	
		
		confirm(msg,function(){
			var param = {
					"selectIDs" : selectIDs.substring(0, selectIDs.length - 1),
					"prgm.taskId" : <%= strategy.getTaskId() %>,
					"prgm.strategyId" : <%= strategy.getStrategyId() %>
				     };
		       
				$.ajax({
					type : "post",
					dataType : "json",
					url : "date!deleteprgm",
					data : param,
					complete : function() {
					},
					success : function(msg) {
						if (msg.success) {    								
							queryright();
						} else {
							alert(msg.data);
						}
					},
					error : function(XMLHttpRequest, textStatus,
							errorThrown) {
						window.top.location.href='/';
					}
				});
		});
	}    			
	if (msg == "up") {
		var param = {
				"prgm.serialId" : selectID,
				"prgm.taskId" : <%= strategy.getTaskId() %>,
				"prgm.strategyId" : <%= strategy.getStrategyId() %>
			     };
		 
			$.ajax({
				type : "post",
				dataType : "json",
				url : "date!upprgm",
				data : param,
				complete : function() {
				},
				success : function(msg) {
					if (msg.success) {
						queryright(remember);
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

	if (msg == "down") {
		var param = {
				"prgm.serialId" : selectID,
				"prgm.taskId" : <%= strategy.getTaskId() %>,
				"prgm.strategyId" : <%= strategy.getStrategyId() %>
			     };
		
			$.ajax({
				type : "post",
				dataType : "json",
				url : "date!downprgm",
				data : param,
				complete : function() {
				},
				success : function(msg) {
					if (msg.success) { 
						queryright(remember);
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
	
} else {
	alert("<s:text name="items" />");
	return false;
}		

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
				<td class="bg">
				  <h2></h2>
					</td>
				<td style="width: 5px;"><img
					src="${theme}/skins/default/images/tcBt3.png" width="5"
					height="35" class="right" /></td>
			</tr>
		</table>
		<div class="tcNr">
				<div>
				<div style="width: 320px; height: 430px; float: left;">
					<div
						style="width: 330px; height: 420px; overflow: auto; border-right: #ccc solid 2px;">
						<ul class="picLb2" id="mytab" style=" overflow:hidden;zoom:1">

						</ul>
					</div>
					<div class="rightMenu"
						style="height: 20px; margin: 0px; padding-top: 10px; padding-left: 20px;">
						<ul style="overflow:hidden;zoom:1" class="left rightMenuShort">
					<li> <a onclick="return CheckHelper.checkAll('programId');return false;" class="CheckLink"><s:text name="selectall" /></a> </li>
                    <li> <a onclick="return CheckHelper.reverseAll('programId');return false;" class="CheckLink"><s:text name="invertselection" /></a> </li> 
                    <li> <a onclick="return CheckHelper.cleanAll('programId');return false;" class="CheckLink"><s:text name="cancel" /></a></li>
						</ul>
					</div>
				</div>				
				
				<div style="width: 588px; float: right; height: 455px; overflow: auto; border-right: #ccc solid 2px;">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						class="shujBt shujBt2">
						<tr>
							<td class="first" style="width: 250px;"><s:text name="name" /></td>
							<td style="width: 60px;"><s:text name="playcount" /></td>
							<td style="width: 60px;"><s:text name="serialnumber" /></td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
					  id="righttab"	class="shujTb shujTb2">
						
					</table>
					<div style="height: 30px; background: #d5dcde;">
						<div class="page1 page2"
							 style="width: 450px; overflow: hidden; padding-top: 5px;">
							
						</div>
					</div>
					<div class="rightMenu"
						style="height: 20px; margin: 0px; padding: 5px;">
						<ul class="left rightMenuShort">
							<li>
							  <a onclick="return CheckHelper.checkAll('serialId');return false;" class="CheckLink"><s:text name="selectall" /></a> 
							  </li>
							  <li>
                                 <a onclick="return CheckHelper.reverseAll('serialId');return false;" class="CheckLink"><s:text name="invertselection" /></a>  
                                 </li>
                       <li>  <a onclick="return CheckHelper.cleanAll('serialId');return false;" class="CheckLink"><s:text name="cancel" /></a></li>
						</ul>
					</div>
					<div class="rightMenu"
						style="height: 20px; margin: 0px; padding: 5px;">
						<ul style="overflow:hidden;zoom:1;" class="left rightMenuShort">
						        
						<li>
						 <a href="javascript:void(0)" onclick="addfile()"><s:text name="add" /></a>
						</li>
							 <li><a
							onclick="del('<s:text name="isdelete" />')"
							style="cursor: hand; cursor: pointer;"><s:text name="delete" /></a> 
 							</li>
 							<li><a
							onclick="del('up')"
							style="cursor: hand; cursor: pointer;"><s:text name="up" /></a> 
						   </li>
						   <li><a
							onclick="del('down')"
							style="cursor: hand; cursor: pointer;"><s:text name="down" /></a>
						 </li>
						</ul>
					</div>

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

