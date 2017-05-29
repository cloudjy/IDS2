<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="com.gnamp.server.model.DemandProgram"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%
DemandProgram p = (DemandProgram)request.getAttribute("demandprogram");
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
	function doSubmit() { 
		$("#nameErrorDiv").showError(""); 
		$("#playTimeErrorDiv").showError(""); 
		
        var playcount = 0;	
    	if($("#playCount").val()) {
    		playcount = parseInt($("#playCount").val(), 10);	 
    	} 
    	if ($("#idle_forever:radio").is(":checked")) { 
    		playcount = <%= Integer.MAX_VALUE %>; 
        	$("#playCount").attr("maxLength", "64");
        	$("#playCount").removeAttr("disabled");
    	}  
    	if ($("#idle_default:radio").is(":checked")) { 
    		playcount = 0; 
        	$("#playCount").removeAttr("disabled");
    	} else { 
	    	if (!playcount) {
	    		$("#playCount").focus();
	    		$("#playTimeErrorDiv").showError("<s:text name="playtime_input_error"/>");
	        	return;
	    	}
    	}

	    $("#playCount").val(playcount && playcount > 0? playcount : 1);
	    $("#demandprogram\\.playStyle").val(playcount && playcount > 0? 1 : 0);// 0-count, 1-duration          	
        	
		var param = $('#form1').serialize(); 


		if (!$("#playCount:radio").is(":checked")) { 
			$("#playCount").val("");
        	$("#playCount").attr("maxLength", "5");
        	$("#playCount").attr("disabled", "disabled");
    	} 
    	
		$.ajax({
			type : "post",
			dataType : "json",
			url : "demandprogram!Modify",
			data : param,
			complete : function() {
				//compliteCallback();
			},
			success : function(result) {   
				if (result.success) {
					callParentFunction("pedited", 'ok');
					return closeIFrame();
				} else {
			    	
					if (result.data  && result.message && 
							(result.data == "NameCannotNull" || result.data == "NameExist")) { 
						$("#nameErrorDiv").showError(result.message); 
					} else if (result.message) {
						alert(result.message); 
					}	else {
						alert("<s:text name="edititemfail"/>");
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
			
    function cancl(){
    	 callParentFunction("pedited", 'ok');
		return closeIFrame();
    }

    function onIdleAwhileRadioChange() { 
    	$("#idle_awhile:radio").attr("checked", "checked"); 
    	$("#idle_default:radio").removeAttr("checked"); 
    	$("#idle_forever:radio").removeAttr("checked"); 
    	$("#playCount").removeAttr("disabled");  
        return false;
    }

    function onIdleDefaultRadioChange() { 
    	$("#idle_awhile:radio").removeAttr("checked");
    	$("#idle_default:radio").attr("checked", "checked"); 
    	$("#idle_forever:radio").removeAttr("checked");  
    	$("#playCount").attr("disabled", "disabled");
        return false;
    }

    function onIdleForeverRadioChange() { 
    	$("#idle_awhile:radio").removeAttr("checked");
    	$("#idle_default:radio").removeAttr("checked"); 
    	$("#idle_forever:radio").attr("checked", "checked");   
    	$("#playCount").attr("disabled", "disabled");
        return false;
    }
</script>

	</head>
	<body>
	<%
	
	if(p==null){
		return;
	}
  %>
		
	<!-- 弹出窗口 开始-->
	<div class="tcWindow">
		<div class="tcBt">
			<img src="${theme}/skins/default/images/tcBt1.gif" width="5" height="35"
				class="left" />
			<h2></h2>
			<img src="${theme}/skins/default/images/tcBt3.gif" class="right" />
		</div>
		<div class="tcNr">
		<form id="form1" class="niceform">	
	
		  <input type="hidden" value="<%=p.getTaskId() %>" name="demandprogram.taskId" />
		  <input type="hidden" value="<%=p.getProgramId() %>" name="demandprogram.programId" />	
			<table width="100%" border="0" cellspacing="2" cellpadding="0"
					class="bdTab">
				<tr>
					<td class="bt" style="width: 40%">
						<font color="#FF0000" size="3">&#x25B4;</font><s:text name="pname" />
					</td>
					<td>
						<input type="text" value="${demandprogram.programName }" name="demandprogram.programName" />
					 	<div id="nameErrorDiv" style="color:#FF0000;"></div>
					 </td>
				</tr>
				<tr>
					<td class="bt" style="width: 40%">
					<s:text name="desc" />
					</td>
					<td align="left">
						<input type="text" value="${demandprogram.description}"  name="demandprogram.description" />
					</td>
				</tr>	
				<tr style="display:none;">
					<td class="bt" style="width: 40%"><s:text name="style" /></td>
					<td align="left"> <select size="1" id="demandprogram.playStyle" name="demandprogram.playStyle">
		                  <option value="0" <%if(p.getPlayStyle()==0){ %> selected <%} %> ><s:text name="text_adddemandprogram_count" /></option>
		                  <option value="1" <%if(p.getPlayStyle()==1){ %> selected <%} %> ><s:text name="text_adddemandprogram_duration" /></option>
		               </select>
		            </td>
				</tr> 
				
				<% 
					boolean PlayWhile = p.getPlayStyle() == DemandProgram.STYLE_TIME && 
							p.getPlayTime() > 0 && p.getPlayTime() != Integer.MAX_VALUE;
					boolean PlayDefault = p.getPlayStyle() == DemandProgram.STYLE_COUNT && 
							p.getPlayCount() == 1;
					boolean PlayForever = p.getPlayStyle() == DemandProgram.STYLE_TIME && 
							p.getPlayTime() == Integer.MAX_VALUE;
				%> 
				<tr>
					<td class="bt" style="width: 40%"><s:text name="playTime" /></td>
					<td align="left"> 
						<table border="0" cellspacing="0" cellpadding="0" >
							<tr>
								<td style="padding-left: 0px;padding-right: 3px;">
								 	<input type="radio" name="idle" id="idle_awhile" value="idle_awhile"
								 		<%=  (PlayWhile? "checked" : "") %> 
								 		style="margin: 0;" onchange="onIdleAwhileRadioChange();"/>
								</td>
								<td style="padding-left: 0px;">
								 	<s:text name="text_idle_playtime1"/>
								</td>
								<td style="padding-left: 0px;">
									 <input id="playCount" name="demandprogram.playCount"  maxLength="5" 
									 value="<%= (p.getPlayStyle()==DemandProgram.STYLE_COUNT || PlayForever ? "" : p.getPlayTime()) %>"
									 onpaste="return false" style="ime-mode:disabled;width:35px;text-align:center;"               
						             onkeypress="if ((event.keyCode<48 || event.keyCode>57) && event.keyCode!='.') event.returnValue=false;" />
					             </td>
					             <td style="padding-left: 0px;">
					             	<s:text name="text_idle_playtime2"/>
					             </td> 
					             <td style="padding-left: 0px;">
					             	<div id="playTimeErrorDiv" style="color:#FF0000;"></div>
					             </td> 
				             </tr>
		             	</table>
		            </td>
				</tr>
				<tr>
					<td class="bt" style="width: 40%"> </td>
					<td align="left">  
		             	<table border="0" cellspacing="0" cellpadding="0" >
							<tr>
								<td style="padding-left: 0px;padding-right: 3px;">
								 	<input type="radio" name="idle" id="idle_default"
								 		<%= (PlayDefault? "checked" : "") %>
								 		style="margin: 0;" onchange="onIdleDefaultRadioChange();" /> 
								</td>
								<td style="padding-left: 0px;">
								 	<s:text name="text_idle_playdefault"/>
								</td> 
				             </tr>
		             	</table>
		            </td>
				</tr>
				<tr>
					<td class="bt" style="width: 40%"> </td>
					<td align="left">  
		             	<table border="0" cellspacing="0" cellpadding="0" >
							<tr>
								<td style="padding-left: 0px;padding-right: 3px;">
								 	<input type="radio" name="idle" id="idle_forever" value="idle_forever" 
								 		<%= (PlayForever? "checked" : "") %>
								 		style="margin: 0;" onchange="onIdleForeverRadioChange();" /> 
								</td>
								<td style="padding-left: 0px;">
								 	<s:text name="text_idle_playforever"/>
								</td> 
				             </tr>
		             	</table>
		            </td>
				</tr>
				<tr>
					<td class="bt" style="width: 40%">
						<s:text name="resolution" />
					</td>
					<td align="left">
						<%=p.getWidth() %>x<%=p.getHeight() %>
					</td>
				</tr>
				<tr>
					<td class="bt" style="width: 40%">
						<s:text name="state" />
					</td>
					<td align="left">
						<% if(p.getState()==0){%><s:text name="play" /><% }else {%><s:text name="pause" /> <%}%>
					</td>
				</tr>				
				<tr>
					<td class="bt" style="width: 40%">
						<s:text name="text_createuser" />
					</td>
					<td align="left">
						<%=p.getCreateUser() %>
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

