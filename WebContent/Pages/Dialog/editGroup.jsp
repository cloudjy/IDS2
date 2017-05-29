<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="com.gnamp.server.model.Group"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

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
<%
	if(request.getAttribute("group")==null){
		out.print("the page is not found parameter id");
		return;
	}
%>
<script type="text/javascript">

	function changedir(callfunc){
		//alert($("a[class='parentID']").attr("id"));
		var parentid = ($("#sp a:last")).attr("id");
          //alert(parentid);

		var dirValue = $("#dir").val();
		if(dirValue==''||dirValue== 'undefined'){
			//alert("<s:text name="dirnameisnull"/>");
			$("#nameErrorDiv").showError("<s:text name="name_cannot_null"/>");
			return;
		}
		if($("#dir").val().length<0||$("#dir").val().length>12){
			alert("<s:text name="grouplengmaxerror"/>");
			$("#nameErrorDiv").showError("<s:text name="grouplengmaxerror"/>");
			return;
		}
		$.post('group!edit' ,
				{"group.groupName": $("#dir").val(),
			    "group.parentId": parentid,
				"group.groupId": "<%=((Group)request.getAttribute("group")).getGroupId()%>"
						}, function(txt) {
							callfunc(txt);
						});
	}

	function closewindow(result) {
		/* if(result.success){
			var ret = new Object();  
			ret.value1 = $("#dir").val();
			ret.value2 = ($("#sp a:last")).attr("id");
			window.returnValue = ret; // $("#dir").val();
		}else{			
				alert(result.data);
		}
		window.close(); */

		if (result.success) {
			//window.returnValue = result.data;
			/* var ret = new Object();  
			ret.value1 = $("#dir").val();
			ret.value2 = ($("#sp a:last")).attr("id"); */
			
			callParentFunction("editNodeed", result.data);
			return closeIFrame();
		} else {
			// alert("<s:text name="edititemfail"/>"); 
			if (result.data  && result.message && 
					(result.data == "NameCannotNull" || result.data == "NameExist") ) { 
				$("#nameErrorDiv").showError(result.message); 
			} else if (result.message) {
				alert(result.message); 
			}	else {
				alert("<s:text name="edititemfail"/>");
			}  
		}
	}

	function nodeclick(pid, obj) {
		var selfid =<%=((Group)request.getAttribute("group")).getGroupId()%>;
		var groupSelector = $("#groupSelector");
		if ($(obj).is('select')) {
			pid = (groupSelector.length > 0) ? groupSelector.val() : 0;
		}
		var param = {
			"id" : pid,
			"selfid" : selfid
		};
		$.ajax({
					type : "post",
					dataType : "json",
					url : "group!groupSelector",
					data : param,
					complete : function() {
					},
					success : function(msg) {
						if (msg.success) {
							try {

								if ($(obj).is('select')) {

									var oldId = groupSelector.val();
									var oldText = groupSelector.find(
											"option:selected").text();

									groupSelector.html("");

									groupSelector
											.before("<a href=\"#\" id=\""
													+ oldId
													+ "\" onclick=\"nodeclick("
													+ oldId
													+ ",this)\" >"
													+ oldText
													+ "</a><span id=\""+oldId+"s\">-></span>");

									if (msg.data != null && msg.data.length > 0) {
										var optionHtml = "";

										optionHtml += "<option value=\"0\"><s:text name="select"/></option>";
										$.each(
														msg.data,
														function(i, g) {
															optionHtml += "<option value=\""+g.groupId+"\">"
																	+ g.groupName
																	+ "</option>";
														});

										groupSelector.html(optionHtml);
									} else {
										$("#" + oldId + "s").remove();
										groupSelector.remove();
									}

								} else {
									$(obj).nextAll().remove();

									var optionHtml = "";
									if (msg.data != null && msg.data.length > 0) {
										optionHtml += "<span>-></span><select size=\"1\" name=\"groupSelector\" onchange=\"nodeclick(0,this)\" id=\"groupSelector\">";
										optionHtml += "<option value=\"0\"><s:text name="select"/></option>";
										$.each(
														msg.data,
														function(i, g) {
															optionHtml += "<option value=\""+g.groupId+"\">"
																	+ g.groupName
																	+ "</option>";
														});

										optionHtml += "</select>";

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
						<td class="bt" style="width: 30%"><s:text name="name" /></td>
						<td>
							<input type="text" name="dir" id="dir"
								value="<%=((Group)request.getAttribute("group")).getGroupName()%>" />
							<div id="nameErrorDiv" style="color:#FF0000;"></div>
						</td>
					</tr>
					<tr>
						<td class="bt"><s:text name="parentdir" /></td>
						<td><span id="sp" style="color: blue"> <a class="root"
								href="#" id="0" onclick="nodeclick(0,this)"><s:text
										name="root_group" /></a> <% 
            	List<Group> grouppath =	(List<Group>)(request.getAttribute("grouppath"));
                if(grouppath != null && grouppath.size()>0){
             	for(Group g : grouppath){
             		 if(g.getGroupId()>0){       		  
                 	   out.print("<span id=\""+g.getGroupId()+"s\">-></span>" + 
                 			   "<a href=\"#\" id=\""+g.getGroupId()+"\" " + 
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
                	 %> <s:text name="select" /> <%
                	 out.print("</option>");
				
	             	for(Group g : brother){	                		 				 							
				       out.print("<option value=\""+g.getGroupId()+"\">"
						+ g.getGroupName() + "</option>");
	             	}
             	   out.print("</select>");
                }
         %> <span id="selector"></span>
						</span></td>
					</tr>
					<tr>
						
						<td class="bt">
						 &nbsp;
						</td>
			          	<td><input type="button"
							onclick="changedir(closewindow)" id="sub"
							value="<s:text name="yes"/>" style="line-height: 13px;" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" id="close" onclick="closeIFrame()"
							value="<s:text name="cancel"/>" /></td>
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
					changedir(closewindow);
					return false;
					break;
		   	}
	   	});
	</script>

</body>
</html>