<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="com.gnamp.server.model.Source"%>
<%@ page language="java" import="com.gnamp.server.model.SystemSource"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

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
        var istag = false;
        var isbuidin = false;
		function changedir(callfunc){	
		   if($("#ddlfl").val() == "0"){
				if($("#text").val() == '' || $("#text").val() == 'undefined'){
					alert("<s:text name="contentnull" />");
					return;
				}
		   }
			
			var surl = '';
		    if(<%=request.getAttribute("taskType").toString().equals("loop") %>){
		    	surl = 'looprect!AddText';
		    }
		    if(<%=request.getAttribute("taskType").toString().equals("demand") %>){
		    	surl = 'demandrect!AddText';
		    }
		    if(<%=request.getAttribute("taskType").toString().equals("plugin") %>){
		    	surl = 'pluginrect!AddText';
		    }
			if(!istag){
				$.post(surl,
						{
					"rectText.taskId":<%=request.getAttribute("taskID").toString() %>,
					"rectText.programId":<%=request.getAttribute("programID").toString() %>,
					"rectText.rectId":<%=request.getAttribute("rectID").toString() %>,
					"istag": istag,
					"rectText.text":$("#text").val()
			            },
		    	function(txt) {
			      callfunc(txt);
			    });
			}else{
				var sourceID=0;
				if(($("#ddltag").val() == "0" && $("#ddlfl").val() == "1") || ($("#ddlsystag").val() == "0" && $("#ddlfl").val() == "2")){
					alert("<s:text name="selecttag" />");
					return;
				}else{
					if($("#ddlfl").val() == "1"){
						sourceID = $("#ddltag").val();
						isbuidin = false;
					}
					if($("#ddlfl").val() == "2"){
						sourceID = $("#ddlsystag").val();
						isbuidin = true;
					}
				}
				
				
				$.post(surl,
						{
					"rectText.taskId":<%=request.getAttribute("taskID").toString() %>,
					"rectText.programId":<%=request.getAttribute("programID").toString() %>,
					"rectText.rectId":<%=request.getAttribute("rectID").toString() %>,
					"istag": istag,
					"sourceID":sourceID,
					"isbuidin":isbuidin
			            },
		    	function(txt) {
			      callfunc(txt);
			    });
			}
	}

		function executeFunction(txt) {
			if(txt.success){
				callParentFunction("added", 'ok');
				return closeIFrame();
			}else{
				alert(txt.message);
			}
		}
	function tagchange(){
		
	}
	function systagchange(){
		
	}
	function flchange(){
		if($("#ddlfl").val() == "0"){
			$("#text").removeAttr("disabled");
			istag = false;
		}
		else{
			
			if($("#ddlfl").val() == "1"){
				$("#ddlsystag").css('display','none'); 
				$("#ddltag").css('display','block'); 
			}
			if($("#ddlfl").val() == "2"){
				$("#ddlsystag").css('display','block'); 
				$("#ddltag").css('display','none'); 
			}
			
			$("#text").attr("disabled","disabled");
			istag = true;
		}
	}
</script>
 <style>
 .NFTextareaRight{
    padding-right:3px;
    padding-bottom:0px;
 }
 </style>
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
			<td><s:text name="content" /></td>
			<td>
			<!-- <input type="text" style="width: 250px;" name="text" id="text" /> -->
				<textarea name="text" id="text"
                              cols="50" rows="3"></textarea>
				</td>
		</tr>
		<%-- <tr>
			<td><s:text name="选择标签" /></td>
			<td>		
			   <div style="position: relative; margin: auto;">		
			   <div style="float: left; margin-right: 5px;">			
			<select  size="1" id="ddlfl" onchange="flchange();"
				name="ddlfl">
					<option value="0">
						<s:text name="不使用标签" />
					</option>
					<option value="1">
						<s:text name="自定义标签" />
					</option>
					<option value="2">
						<s:text name="内置标签" />
					</option>
			</select>
			</div>
			<div style="float:left;">	
				<select style="display: none;"  size="1" id="ddltag" onchange="tagchange();"
				   name="ddltag">
					<option value="0">
						<s:text name="select" />
					</option>		
					<%
						if (request.getAttribute("tags") != null) {
							List<Source> list = (List<Source>) request.getAttribute("tags");

							for (int i = 0; i < list.size(); i++) {
					%>
					<option title="<%= list.get(i).getName()%>" value="<%=list.get(i).getSourceId() %>" ><%=(list.get(i).getName().length()>10 ? (list.get(i).getName().substring(0, 10)+"...") : list.get(i).getName()) %></option>
					<%
						}
						}
					%>			
			    </select>
			</div>
			<div style="float:left;">	
			    <select  style="display: none;"  size="1" id="ddlsystag" onchange="systagchange();"
				   name="ddlsystag">
					<option value="0">
						<s:text name="select" />
					</option>		
					<%
						if (request.getAttribute("systags") != null) {
							List<SystemSource> list = (List<SystemSource>) request.getAttribute("systags");

							for (int i = 0; i < list.size(); i++) {
					%>
				<option title="<%= list.get(i).getName()%>" value="<%=list.get(i).getSourceId() %>" ><%=(list.get(i).getName().length()>10 ? (list.get(i).getName().substring(0, 10)+"...") : list.get(i).getName()) %></option>
					<%
						}
						}
					%>			
			    </select>
			    </div>
			    </div>
		    </td>
		</tr> --%>
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
</body>
</html>

