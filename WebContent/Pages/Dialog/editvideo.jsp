<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="com.gnamp.server.model.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%
RectVideo r = (RectVideo)request.getAttribute("rectVideo");
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
    		var param = $('#form1').serialize();    	
    		
    		var surl = "";
	        if(<%=request.getAttribute("taskType").toString().equals("loop") %>){
	        	surl = "looprect!ModifyVideo";
	        }
	        if(<%=request.getAttribute("taskType").toString().equals("demand") %>){
	        	surl = "demandrect!ModifyVideo";
	        }
	        if(<%=request.getAttribute("taskType").toString().equals("plugin") %>){
	        	surl = "pluginrect!ModifyVideo";
	        }
    		
    		$.ajax({
    			type : "post",
    			dataType : "json",
    			url : surl,
    			data : param,
    			complete : function() {
    				//compliteCallback();
    			},
    			success : function(msg) {    				
    				if(msg.success){
    				  //alert(msg.data);
    					callParentFunction("edited", 'ok');
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
				return closeIFrame();
	    } 		   
         </script>

	</head>
	<body>
	<%
	
	if(r==null){
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
		    <input type="hidden" value="<%=r.getTaskId() %>" name="rectVideo.taskId" />	
		    <input type="hidden" value="<%=r.getProgramId() %>" name="rectVideo.programId" />
		    <input type="hidden" value="<%=r.getRectId() %>" name="rectVideo.rectId" />
		    <input type="hidden" value="<%=r.getVideoId() %>" name="rectVideo.videoId" />
		    <input type="hidden" value="<%=r.getFileId() %>" name="rectVideo.fileId" />
		    <input type="hidden" value="<%=r.getFileName() %>" name="rectVideo.fileName" />
		    <input type="hidden" value="<%=r.getSequenceNum() %>" name="rectVideo.sequenceNum" />
		    <input value="<%= request.getAttribute("selectIDs") %>" name="selectIDs" type="hidden" />
		    <input value="<%= request.getAttribute("fileIDs") %>" name="fileIDs" type="hidden" /> 
			<table width="100%" border="0" cellspacing="2" cellpadding="0"
					class="bdTab">	
				<tr>
					<td class="bt">
						<s:text name="name" />
					</td>
					<td>						
					<% if(request.getAttribute("selectIDs") != null && request.getAttribute("selectIDs").toString().split(",").length > 1 ) {%>
						<%}else{ %>
						<%=r.getFileName() %>
						<%} %>
					</td>
				</tr>
				<tr>
					<td class="bt">
						<s:text name="task_play_playtime" />
					</td>
					<td>
						<input type="text" value="<%=r.getPlayTime() %>" id="rectVideo.playTime" name="rectVideo.playTime" />
						&nbsp;&nbsp;<span><s:text name="second"/></span>
					</td>
				</tr>
				<tr>
					<td class="bt">
						<s:text name="task_play_scalestyle" />
					</td>
					<td><select size="1" id="rectVideo.scaleStyle" name="rectVideo.scaleStyle">
						 <option value="3" <% if(r.getScaleStyle()==3){ %> selected <%} %> ><s:text name="stfull" /></option>
						 <option value="1" <% if(r.getScaleStyle()==1){ %> selected <%} %> ><s:text name="stscale" /></option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="bt">
						<s:text name="rotation" />
					</td>
					<td><select size="1" id="rectVideo.rotation" name="rectVideo.rotation">
						 <option value="0" <% if(r.getRotation()==0){ %> selected <%} %> ><s:text name="on" /></option>
						 <option value="1" <% if(r.getRotation()==1){ %> selected <%} %> ><s:text name="off" /></option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="bt">
						<s:text name="task_size"/>
					</td>
					<td>
					<%=request.getAttribute("filesize") %>
					</td>
				</tr>
				<tr>
					<td class="bt">
						<s:text name="task_location"/>
					</td>
					<td>
					<%=request.getAttribute("filelocation") %>
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

