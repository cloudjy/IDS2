<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="com.gnamp.server.model.ContentFlash"%>
<%@ taglib uri="/struts-tags" prefix="s"%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="0" />


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

<%  
	Object attrObject = null;
	attrObject = request.getAttribute("contentFlash");
	ContentFlash mContentFlash = null;
	if (attrObject != null && attrObject instanceof ContentFlash) 
		mContentFlash = (ContentFlash)attrObject;
	else
		return;
		
	attrObject = request.getAttribute("taskType"); 
	String taskType = attrObject != null? attrObject.toString() : null;

	String savePlayPropertyUrl = "";
	if (taskType.equals("loop")) {  
		savePlayPropertyUrl = "looprect!saveFlashPlayProperty";
	} else if (taskType.equals("demand")) { 
	    savePlayPropertyUrl = "demandrect!saveFlashPlayProperty";
	} else if (taskType.equals("plugin")) {  
		savePlayPropertyUrl = "pluginrect!saveFlashPlayProperty";
	}
%>
		
<script src="js/gnamp.js" language="javascript"></script>

		<script type="text/javascript">		
        function doSubmit() {   	
    		var param = $('#form1').serialize();   
    		$.ajax({
    			type : "post",
    			dataType : "json",
    			url : "<%= savePlayPropertyUrl %>",
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
	    	 callParentFunction("edited", 'ok');
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
		<form id="form1" class="niceform">	
		    <input type="hidden" value="<%=mContentFlash.getTaskId() %>" name="contentFlash.taskId" />	
		    <input type="hidden" value="<%=mContentFlash.getProgramId() %>" name="contentFlash.programId" />
		    <input type="hidden" value="<%=mContentFlash.getRectId() %>" name="contentFlash.rectId" />
		    <input type="hidden" value="<%=mContentFlash.getContentId() %>" name="contentFlash.contentId" />
		    <input type="hidden" value="<%=mContentFlash.getFileId() %>" name="contentFlash.fileId" />
		    <input type="hidden" value="<%=mContentFlash.getFileName() %>" name="contentFlash.fileName" />
		    <input type="hidden" value="<%=mContentFlash.getSequenceNum() %>" name="contentFlash.sequenceNum" />
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
						<%= mContentFlash.getFileName() %>
						<%} %>
					</td>
				</tr>	
				<tr>
					<td class="bt">
						<s:text name="task_play_playtime" />
					</td>
					<td>
						<input type="text" value="<%=mContentFlash.getPlayTime() %>" id="contentFlash.playTime" name="contentFlash.playTime" />
					</td>
				</tr>
				<!-- 
				<tr>
					<td class="bt">
						<s:text name="task_play_effect" />
					</td>
					<td><select size="1" id="contentFlash.swapStyle" name="contentFlash.swapStyle">	
						 <option value="0" <% if(mContentFlash.getSwapStyle()==0){ %> selected <%} %> ><s:text name="none" /></option>
						 <option value="1" <% if(mContentFlash.getSwapStyle()==1){ %> selected <%} %> ><s:text name="random" /></option>
						 <option value="2" <% if(mContentFlash.getSwapStyle()==2){ %> selected <%} %> ><s:text name="cutinbottom" /></option>
						 <option value="3" <% if(mContentFlash.getSwapStyle()==3){ %> selected <%} %> ><s:text name="cutintop" /></option>
						 <option value="4" <% if(mContentFlash.getSwapStyle()==4){ %> selected <%} %> ><s:text name="cutinleft" /></option>
						 <option value="5" <% if(mContentFlash.getSwapStyle()==5){ %> selected <%} %> ><s:text name="cutinright" /></option>
						 <option value="6" <% if(mContentFlash.getSwapStyle()==6){ %> selected <%} %> ><s:text name="hori" /></option>
						 <option value="7" <% if(mContentFlash.getSwapStyle()==7){ %> selected <%} %> ><s:text name="vert" /></option>
						 <option value="8" <% if(mContentFlash.getSwapStyle()==8){ %> selected <%} %> ><s:text name="grid" /></option>
						 <option value="9" <% if(mContentFlash.getSwapStyle()==9){ %> selected <%} %> ><s:text name="sup" /></option>
						 <option value="10" <% if(mContentFlash.getSwapStyle()==10){ %> selected <%} %> ><s:text name="sbottom" /></option>
						 <option value="11" <% if(mContentFlash.getSwapStyle()==11){ %> selected <%} %> ><s:text name="sleft" /></option>
						 <option value="12" <% if(mContentFlash.getSwapStyle()==12){ %> selected <%} %> ><s:text name="sright" /></option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="bt">
						<s:text name="task_play_effecttime" />
					</td>
					<td>
						<input type="text" value="<%=mContentFlash.getSwapTime() %>" id="contentFlash.swapTime" name="contentFlash.swapTime" />
					</td>
				</tr>
				<tr>
					<td class="bt">
						<s:text name="task_play_scalestyle" />
					</td>
					<td><select size="1" id="contentFlash.scaleStyle" name="contentFlash.scaleStyle">
						 <option value="3" <% if(mContentFlash.getScaleStyle()==3){ %> selected <%} %> ><s:text name="stfull" /></option>
						 <option value="2" <% if(mContentFlash.getScaleStyle()==2){ %> selected <%} %> ><s:text name="stcenter" /></option>
						 <option value="1" <% if(mContentFlash.getScaleStyle()==1){ %> selected <%} %> ><s:text name="stscale" /></option>
						</select>
					</td>
				</tr>
				 -->
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

