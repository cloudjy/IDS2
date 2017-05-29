<%@page import="com.gnamp.server.model.*"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%

TodoTerminal todoterminal = (TodoTerminal)request.getAttribute("todoterminal");
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
    	if ($("#deviceName").val() == ''
			|| $("#deviceName").val() == 'undefined') {
		   alert("<s:text name="namenull"/>");
		   return; 
		}
        	
    	var parentid = ($("#sp a:last")).attr("id");
    	$("#gid").val(parentid);
		var param = $('#form1').serialize();    		
		$.ajax({
			type : "post",
			dataType : "json",
			url : "todoterminal!Modify",
			data : param,
			complete : function() {
				//compliteCallback();
			},
			success : function(msg) {    				
				if(msg.success){
				  //alert(msg.data);
			 		callParentFunction("audited", 'ok');
					return closeIFrame();
				} else{
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
		callParentFunction("audited", 'ok');
		return closeIFrame();
	} 	
     
    function QuerCityByProvince() {
    	var city_id = 0;
    	//alert(city_id);
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
 							var str = (city.cityId==city_id ? " selected ":" ");
 							//alert(str);
 							content += "<option " +str+" value=\""+city.cityId+"\">"
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
 	
 	function nodeclick(pid, obj) { 
 		var selfid = <%=((Group)request.getAttribute("group")).getGroupId()%>;
 		var groupSelector = $("#groupSelector"); 
 		if ($(obj).is('select')) { 
 			pid = (groupSelector.length > 0)? groupSelector.val() : 0;
    	}
		var param = {
			"terminal.groupId" : pid,
			"selfid" : selfid 
		};
 		$.ajax({
 			type : "post",
 			dataType : "json",
 			url : "terminal!groupSelector",
 			data : param,
 			complete : function() {},
 			success : function(msg) {
 				if (msg.success) { 
 					try {
 						if ($(obj).is('select')) { 
						 	var oldId = groupSelector.val();
						    var oldText = groupSelector.find("option:selected").text();
							  
							groupSelector.html("");  
							  
							groupSelector.before("<a href=\"#\" id=\""+oldId+"\" onclick=\"nodeclick("+oldId+",this)\" >" + oldText + "</a><span id=\""+oldId+"s\">-></span>");
								
							if(msg.data != null && msg.data.length>0){	
								  var optionHtml = "";													 		
							
									optionHtml +="<option value=\"0\"><s:text name="select" /></option>";
									$.each(msg.data, function(i, g) {		 							
										optionHtml += "<option value=\""+g.groupId+"\">"
												+ g.groupName + "</option>";
									});
									
								 groupSelector.html(optionHtml);
							} else {
								  $("#"+oldId+"s").remove();
								  groupSelector.remove();
							}
							
						} else {
							$(obj).nextAll().remove();
							
							var optionHtml = ""; 
							if(msg.data != null && msg.data.length>0) { 
								optionHtml +="<span>-></span><select size=\"1\" name=\"groupSelector\" onchange=\"nodeclick(0,this)\" id=\"groupSelector\">";
								optionHtml +="<option value=\"0\"><s:text name="select" /></option>";
								$.each(msg.data, function(i, g) { 
									optionHtml += "<option value=\""+g.groupId+"\">" + g.groupName + "</option>";
								});
								optionHtml +="</select>";
								
								$(obj).after(optionHtml);
							}
						} 
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
		  <input type="hidden" value="${todoterminal.deviceId }" name="todoterminal.deviceId" />	
		  <input type="hidden" id="gid" name="gid" />	
		  <input value="<%= request.getAttribute("selectIDs") %>" name="selectIDs" type="hidden" />
			<table width="100%" border="0" cellspacing="2" cellpadding="0"
					class="bdTab">
				<tr>
					<td class="bt">
						<s:text name="devid" />
					</td>
					<td>
					<% if(todoterminal != null && todoterminal.getDeviceId()>0 ){ %> 
					  <%
                         String tmp="";
						if( String.valueOf(todoterminal.getDeviceId()).length() <12){
							for(int i=0;i<(12-String.valueOf(todoterminal.getDeviceId()).length());i++)
							   tmp +="0"; 
						}	
					  %>
						<%= (Long.toHexString(Long.parseLong(String.valueOf((todoterminal.getDeviceId())))).toUpperCase().length()<12 ?
								(tmp+Long.toHexString(Long.parseLong(String.valueOf((todoterminal.getDeviceId())))).toUpperCase())
								: Long.toHexString(Long.parseLong(String.valueOf((todoterminal.getDeviceId())))).toUpperCase())
								%>	
					<%} %>
					</td>
				</tr>
				<tr>
					<td class="bt">
							<s:text name="devicename" />
					</td>
					<td>
					<% if(todoterminal != null && todoterminal.getDeviceId()>0 ){ %> 
						<input id="deviceName" type="text" value="${todoterminal.deviceName }"  name="todoterminal.deviceName" />
					<%} %>
					</td>
				</tr>
				<tr>
					<td class="bt">
							<s:text name="desc" />
					</td>
					<td>
					<% if(todoterminal != null && todoterminal.getDeviceId()>0 ){ %> 
						<input type="text" value="${todoterminal.description }" name="todoterminal.description" />
					<%} %>
					</td>
				</tr>
				<tr>
				<td class="bt">	<s:text name="grouplocation" /></td>
			 <td>	
				<span id="sp" style="color:blue">
				    <a class="root" href="#" id="0" onclick="nodeclick(0,this)">	<s:text name="root" /></a>
	    <% 
            	List<Group> grouppath =	(List<Group>)(request.getAttribute("grouppath"));
                if(grouppath != null && grouppath.size()>0){
             	for(Group g : grouppath){             
	             		 if(g.getGroupId()>0){
	                 	  out.print("<span id=\""+g.getGroupId()+"s\">-></span>");
	                 	  out.print("<a href=\"#\" id=\""+g.getGroupId()+"\" " + 
	                 	   "onclick=\"nodeclick("+g.getGroupId()+",this)\" >"   + g.getGroupName() + 
	                 	   "</a>");          
             		   }
                	}
                }
                
                List<Group> brother =	(List<Group>)(request.getAttribute("brother"));
                if(brother != null && brother.size()>0){                	
                	 out.print("<span id=\""+((Group)request.getAttribute("group")).getGroupId()+"s\">-></span>");
                	 out.print("<select size=\"1\" name=\"groupSelector\" onchange=\"nodeclick(0,this)\" id=\"groupSelector\">");
                	 out.print("<option value=\"0\">");
                	 %>
                		<s:text name="select" />
                	 <%
                	 out.print("</option>");
				
	             	for(Group g : brother){	                		 				 							
				       out.print("<option value=\""+g.getGroupId()+"\">"
						+ g.getGroupName() + "</option>");
	             	}
             	   out.print("</select>");
                }
         %>
				     <span id="selector"></span>
				</span>
			</td>
				</tr>
				<tr>
					<td class="bt">
							<s:text name="weather" />
					</td>
					<td>
					<s:text name="province" />
					<select size="1" name="province" onchange="QuerCityByProvince();" id="ddlprovince" style="width: 95px; heigth: 100px">
				<option value="">	<s:text name="select" /></option>						
				<% if(request.getAttribute("provincelist") !=null){ 
				  List<String> list = (List<String>)request.getAttribute("provincelist");				 
				  for(int i=0;i<list.size();i++){
				%>				
					<option value="<%=list.get(i) %>"><%=list.get(i) %></option>
				<%}
			  } %>
			  </select> 
					<s:text name="city" />
					<select size="1" id="ddlcity" style="width: 95px; heigth: 100px" name="cid">
						<option value="0">	<s:text name="select" /></option>
				</select>
					</td>
				</tr>
				<tr>
					<td class="bt">&nbsp;</td>
						<td><input type="button" value="<s:text name="yes"/>"
							onclick="doSubmit();" style="line-height: 13px;" />&nbsp;&nbsp;&nbsp;&nbsp;<input
							type="button" onclick="cancl();" value="<s:text name="cancel" />" />
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

