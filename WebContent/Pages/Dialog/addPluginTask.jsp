<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

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
		function changedir(callfunc){	
			$("#nameErrorDiv").showError("");
			$("#startTimeErrorDiv").showError("");
			$("#stopTimeErrorDiv").showError("");
			$("#countErrorDiv").showError("");
			
			if($("#taskName").val() == '' || $("#taskName").val() == 'undefined'){
				// alert("<s:text name="namenull" />");
				$("#nameErrorDiv").showError("<s:text name="namenull"/>");
				return;
			}	
			if($("#startTime").val() == '' || $("#startTime").val() == 'undefined'){
				// alert("<s:text name="starttimenull" />");
				$("#startTimeErrorDiv").showError("<s:text name="starttimenull"/>");
				return;
			}
			if($("#stopTime").val() == '' || $("#stopTime").val() == 'undefined'){
				// alert("<s:text name="endtimenull" />");
				$("#stopTimeErrorDiv").showError("<s:text name="endtimenull"/>");
				return;
			}
			if($("#pluginCount").val() == '' || $("#pluginCount").val() == 'undefined'
				|| $("#pluginCount").val() <=0){
				//alert("<s:text name="countnull" />");
				$("#countErrorDiv").showError("<s:text name="countnull"/>");
				return;
			}
			
		
			
	/* 		$.post('plugintask!AddPluginTask',
					{
				"plugintask.taskName":$("#taskName").val(),
				"plugintask.startTime":$("#startTime").val(),
				"plugintask.stopTime":$("#stopTime").val(),
				"plugintask.pluginStyle":$("#pluginStyle").val(),
				"plugintask.pluginCount":$("#pluginCount").val(),
			    "plugintask.description":$("#description").val()
		            },
	    	function(txt) {
		      callfunc(txt);
		}); */
		
		
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
			
	  // (new Date(Date.parse(($("#startTime").val()).replace(/-/g,   "/")))).format('yyyy-MM-dd hh:mm:ss') 
			ajaxCallback('plugintask!AddPluginTask',
					{
				"plugintask.taskName":$("#taskName").val(),
				"plugintask.startTime":  (new Date(Date.parse(($("#startTime").val()).replace(/-/g,   "/")))).format('yyyy-MM-dd hh:mm:ss'),
				"plugintask.stopTime":  (new Date(Date.parse(($("#stopTime").val()).replace(/-/g,   "/")))).format('yyyy-MM-dd hh:mm:ss') ,
				"plugintask.pluginStyle":$("#pluginStyle").val(),
				"plugintask.pluginCount":$("#pluginCount").val(),
			    "plugintask.description":$("#description").val()
		            },
		         callfunc);
	}

		function executeFunction(result) {
			if (result.success) {
				callParentFunction("addtasked", 'ok');
				return closeIFrame();
			} else {
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
					alert("<s:text name="additemfail"/>");
				}	 
			}
		}

		function closedir() {
			callParentFunction("addtasked", 'ok');
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
			<h2></h2>
			<img src="${theme}/skins/default/images/tcBt3.gif" class="right" />
		</div>
		<div class="tcNr">
			<form action="#" method="post" class="niceform">
				<table width="100%" border="0" cellspacing="2" cellpadding="0"
					class="bdTab">
		<tr>
			<td class="bt" style="width: 40%"><font color="#FF0000" size="3">&#x25B4;</font><s:text name="text_task_taskname" /></td>
			<td align="left">
				<input type="text" name="taskName" id="taskName" /> 
				<div id="nameErrorDiv" style="color:#FF0000;"></div>
			</td> 
		</tr>
		<tr>
			<td class="bt" style="width: 40%"><s:text name="desc" /></td>
			<td align="left"><input type="text" 
				name="description" id="description" /></td>
		</tr>
		<tr>
			<td class="bt" style="width: 40%"><s:text name="start" /></td>
			<td align="left">			
				<input type="text" name="startTime" id="startTime" /> 
				<div id="startTimeErrorDiv" style="color:#FF0000;"></div>
			</td> 
		</tr>
		<tr>
			<td class="bt" style="width: 40%" ><s:text name="end" /></td>
			<td align="left">
				<input type="text" name="stopTime" id="stopTime" /> 
				<div id="stopTimeErrorDiv" style="color:#FF0000;"></div>
			</td> 
		</tr>		
		<tr>
			<td class="bt" style="width: 40%" ><s:text name="style" /></td>
			<td align="left"> <select size="1" id="pluginStyle" name="pluginStyle">
                  <option value="0"><s:text name="always" /></option>
                  <option value="1"><s:text name="interval" /></option>
               </select>
            </td>
		</tr>
		<tr>
			<td class="bt" style="width: 40%" ><s:text name="count" /></td>
			<td align="left">
				<input name="pluginCount" id="pluginCount" onpaste="return false" style="ime-mode:disabled"                 
             		onkeypress="if ((event.keyCode<48 || event.keyCode>57) && event.keyCode!='.') event.returnValue=false;" />
             	<div id="countErrorDiv" style="color:#FF0000;"></div>
             </td> 
		</tr>
		<tr>
				<td class="bt">&nbsp;</td>
						<td><input type="button" value="<s:text name="yes"/>"
							onclick="changedir(executeFunction)" style="line-height: 13px;" />&nbsp;&nbsp;&nbsp;&nbsp;<input
							type="button" onclick="closeIFrame();" value="<s:text name="cancel" />" /></td>
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
					changedir(executeFunction);
					return false;
					break;
		   	}
	   	});
	</script>

</body>
</html>