<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="com.gnamp.server.model.Category"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><s:text name="selectbackground" /> &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp; </title>
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

var namesortflag = "asc";
var currentSortFlag = "desc";
var currentSortName = "check_time";

function namesort() {
	namesortflag = (namesortflag == "asc" ? "desc" : "asc");
	sortfield(namesortflag,"name");
}
function sortfield(flag,name) {
	
	currentSortFlag = flag;
	currentSortName = name;
	
	query();
}

$(document).ready(function () {
    $("#includeChildren").click(function () {
       // alert(this.checked + " i be hiteid");
    });
    
    $("#includeChildren").change(function () {      
       query();
    });

    $("input[name=rd]").click(function () {
    	ttstyle();
    });
});


	function ttstyle() {	
		
		if ($('input:radio[name="rd"]:checked').val() == 1)
			queryfilesview();
		else
			queryfiles();
	}

	function nodeclick(pid, obj) {
		var selfid =<%=((Category)request.getAttribute("category")).getCategoryId()%>;
		var groupSelector = $("#groupSelector");
		if ($(obj).is('select')) {
			pid = (groupSelector.length > 0) ? groupSelector.val() : 0;
		}
		var param = {
			"categoryId" : pid,
			"selfid" : selfid
		};
		var surl = "";
		if (<%=request.getAttribute("taskType").toString().equals("loop") %>) {
			surl = "looprect!categorySelector";
		}
		if (<%=request.getAttribute("taskType").toString().equals("demand") %>) {
			surl = "demandrect!categorySelector";
		}
		if (<%=request.getAttribute("taskType").toString().equals("plugin") %>) {
			surl = "pluginrect!categorySelector";
		}
		else{
			surl = "templet!categorySelector";
		}
		$
				.ajax({
					type : "post",
					dataType : "json",
					url : surl,
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

										optionHtml += "<option value=\"0\"><s:text name="select" /></option>";
										$
												.each(
														msg.data,
														function(i, g) {
															optionHtml += "<option value=\""+g.categoryId+"\">"
																	+ g.categoryName
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
										optionHtml += "<span>-></span><div style=\"display:inline-block;\"><select size=\"1\" name=\"groupSelector\" onchange=\"nodeclick(0,this)\" id=\"groupSelector\">";
										optionHtml += "<option value=\"0\"><s:text name="select" /></option>";
										$
												.each(
														msg.data,
														function(i, g) {
															optionHtml += "<option title=\""+g.categoryName+"\" value=\""+g.categoryId+"\">"
																	+ (g.categoryName.length>4?g.categoryName.substring(0, 4)+"..." : g.categoryName)
																	+ "</option>";
														});

										optionHtml += "</select></div>";

										$(obj).after(optionHtml);
									}
								}

							} catch (e) {
								alert(e.message);
							}
						} else {
							alert(msg.success);
						}

						if ($('input:radio[name="rd"]:checked').val() == 1)
							queryfilesview();
						else
							queryfiles();

					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						window.top.location.href='/';
					}
				});

		return false;

	}

	function query() {
		if ($('input:radio[name="rd"]:checked').val() == 1)
			queryfilesview();
		else
			queryfiles();
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
				<%--  <img
					src="${theme}/skins/default/images/closed.gif"
					style="float: right; padding-right: 10px;" />   --%>
					<h2></h2>
					</td>
				<td style="width: 5px;"><img
					src="${theme}/skins/default/images/tcBt3.png" width="5"
					height="35" class="right" /></td>
			</tr>
		</table>
		<div class="tcNr">
			<form method="post" class="niceform">
				<div class="tcBt2">
					<table border="0" cellspacing="0" cellpadding="0"
						style="width: 910px;">
						<tr>
							<td>&nbsp;<s:text name="filetype" /></td>
							<td>							
							<select size="1" name="select" id="select" disabled="disabled">
							     <option value="0">
										<s:text name="image" />
								</option>
							</select>
							</td>
							<td><s:text name="filegroup"/></td>
							<td style="width:500px;">
							<span id="sp"> <a
									class="root" href="#" id="0" onclick="nodeclick(0,this)"><s:text
											name="root" /></a> <%
							    List<Category> categorypath =	(List<Category>)(request.getAttribute("categorypath"));
							       if(categorypath != null && categorypath.size()>0){
							    	for(Category g : categorypath){             
							        		 if(g.getCategoryId()>0){
							            	  out.print("<span id=\""+g.getCategoryId()+"s\">-></span>");
							            	  out.print("<a href=\"#\" id=\""+g.getCategoryId()+"\" " + 
							            	   "onclick=\"nodeclick("+g.getCategoryId()+",this)\" >"   + g.getCategoryName() + 
							            	   "</a>");          
							    		   }
							       	   }
							       }
						    
					                List<Category> brother = (List<Category>)(request.getAttribute("brother"));
					                if(brother != null && brother.size()>0){                	
					                	 out.print("<span id=\""+((Category)request.getAttribute("category")).getCategoryId()+"s\">-></span>");
					                	 out.print("<select size=\"1\" name=\"groupSelector\" onchange=\"nodeclick(0,this)\" id=\"groupSelector\">");
					                	 out.print("<option value=\"0\">");
					                	 %> <s:text name="select" /> <%
					                	 out.print("</option>");
									
						             	for(Category g : brother){	                		 				 							
									       out.print("<option title=\""+g.getCategoryName()+"\" value=\""+g.getCategoryId()+"\">"
											+ (g.getCategoryName().length()>4?g.getCategoryName().substring(0, 4)+"..." : g.getCategoryName()) + "</option>");
						             	}
					             	   out.print("</select>");
					                }
					         %> <span id="selector"></span>
							</span>
							</td>
							<td><input type="checkbox" name="interests[]"
								id="includeChildren" value="true" />
								</td>
							<td> 
							<s:text name="child" /></td>
							<td><input style="margin-bottom:6px;" type="radio" onclick="ttstyle()" name="rd"
								id="rd1" checked="checked" value="1" /></td>
							<td><s:text name="view" /></td>
							<td><input style="margin-bottom:6px;" type="radio" onclick="ttstyle()" name="rd"
								id="rd2" value="2" /></td>
							<td><s:text name="list" /></td>
						</tr>
					</table>
				</div>
			</form>


			<div>
				<div style="width: 910px; height: 400px;">
					<div 
						style="width: 910px; height: 360px; overflow: auto;">
						<ul class="picLb2" id="mytab">

						</ul>
					</div>
					<div class="rightMenu"
						style="height: 20px; margin: 0px; padding-top: 10px; padding-left: 20px;">
						<ul class="left rightMenuShort">
					<li> <a onclick="return CheckHelper.checkAll('fileid');return false;" class="CheckLink"><s:text name="selectall" /></a> </li>
                    <li> <a onclick="return CheckHelper.reverseAll('fileid');return false;" class="CheckLink"><s:text name="invertselection" /></a> </li> 
                    <li> <a onclick="return CheckHelper.cleanAll('fileid');return false;" class="CheckLink"><s:text name="cancel" /></a></li>
							<li>
						<a onclick="view();"
							style="cursor: hand; cursor: pointer;"><s:text name="see"/></a> 
							 </li>
							 <li>
						<a onclick="okselect(); return false;"
							style="cursor: hand; cursor: pointer;"><s:text name="text_select"/></a> 
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
<script type="text/javascript">

	function queryfiles() {
		
		var param = {
			"fileparam.categoryId" : ($("#sp a:last")).attr("id"),
			"fileparam.fileType" : 0,
			"fileparam.fileVendor" : '',
			"fileparam.searchKey" : '',
			"fileparam.checkState" : 1,
			"fileparam.includeChildren" : $("#includeChildren").attr("checked") == "checked" ? "true"
					: "false",
			"page.currentPage" : 1,
			"page.pageSize" :<%=Short.MAX_VALUE %>,
			"oManaer" : currentSortFlag,
			"oName" : currentSortName
		};

		$.ajax({
					type : "post",
					dataType : "json",
					url : "file!query",
					data : param,
					complete : function() {
					},
					success : function(msg) {
						try {
							var content = "";
							
							content += "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"shujBt shujBt2\">";
							content += "<tr>";
								content += "<td id=\"namesort\" onclick='namesort();' class=\"first\" style=\"cursor: pointer;width: 650px;\"><s:text name="name" /></td>";
								content += "<td style=\"width: 50px;\"><s:text name="preview" /></td>";
								content += "</tr>";
									content += "</table>";
										content += "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"shujTb shujTb2\">";
							
							$.each(
											msg.data,
											function(i, f) {
													
														content += "<tr id=\""
															+ f.fileId
															+ "\">"
															+ "<td style=\"width:12px;\"><lable><input type=\"checkbox\" value=\""+f.fileId+"\" name=\""+f.fileId+"\" id=\"" 
															+  f.fileId
															+  "\" class=\"fileid\" />"
															+ "</label></td>"
															+ "<td title=\""+f.fileName+"\" style=\"width:765px;\">"
															+  f.fileName
															+ "</td>"
							                           +"<td><a href=\"file!preview?file.fileId="
														+ f.fileId
														+ "\" target='_blank'>"
														+"<img src=\"${theme}/skins/default/images/zoom.png\" width=\"18\" height=\"18\" border=\"0\" />"
														+"</a></td>"
							                        +"</tr> ";

														
											});

			                  content+="</table>";     
			                  
							$("#mytab").html(content);
							
							/**
							 * 重置滚动条
							 */
							ipubs.models.jscroll_ee();
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

	if ($('input:radio[name="rd"]:checked').val() == 1)
		queryfilesview();
	else
		queryfiles();


	function queryfilesview() {
      
		var param = {
			"fileparam.categoryId" : ($("#sp a:last")).attr("id"),
			"fileparam.fileType" : 0,
			"fileparam.fileVendor" : '',
			"fileparam.searchKey" : '',
			"fileparam.checkState" : 1,
			"fileparam.includeChildren" : $("#includeChildren").attr("checked") == "checked" ? "true"
					: "false",
			"page.currentPage" : 1,
			"page.pageSize" :<%=Short.MAX_VALUE %>,
			"oManaer" : currentSortFlag,
			"oName" : currentSortName
		};

		$
				.ajax({
					type : "post",
					dataType : "json",
					url : "file!query",
					data : param,
					complete : function() {
					},
					success : function(msg) {
						try {
							var content = "";
							$
									.each(
											msg.data,
											function(i, f) {
												content += "<li  title=\""+f.fileName+"\">";
												content += "<a href=\"file!preview?file.fileId="
														+ f.fileId
														+ "\" target='_blank' >";

														/* 	content += "<img width=\"94\" height=\"86\" src=\"file!showview?file.fileId="
																	+ f.fileId + "\">"; */
																	
																	 content += "<div style=\"width:94px;height:86px;\">";
															            content += "<img style=\"max-width:94px; max-height:86px\" src=\"file!showview?file.fileId="+f.fileId+"\"  />";
															            content += "</div>";
												content += "</a>";
												content += "<input type=\"checkbox\" value=\""+f.fileId+"\" name=\""+f.fileId+"\" id=\"" 
													+  f.fileId
													+  "\" class=\"fileid\" />";
												content += "<span>"
														+ (f.fileName.length > 8 ? f.fileName
																.substr(0, 8)
																+ "..."
																: f.fileName)
														+ "</span>  ";
												content += "</li>";

											});
							$("#mytab").html(content);
							/**
							 * 重置滚动条
							 */
							ipubs.models.jscroll_ee();
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

	

		
	
	function view(){
		
		var arr = $("input[class='fileid']");  //document.getElementsByTagName('input');
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
    		var surl = "";
			if (<%=request.getAttribute("taskType").toString().equals("loop") %>) {
				surl = "looprect!view";
			}
			if (<%=request.getAttribute("taskType").toString().equals("demand") %>) {
				surl = "demandrect!view";
			}
			if (<%=request.getAttribute("taskType").toString().equals("plugin") %>) {
				surl = "pluginrect!view";
			}
			else
			{
			  surl = "templet!view";
			}
			
			showIPUBDialogStand("" + surl + "" + "?taskID="	+<%= request.getAttribute("taskID") %>
            + "&programID="+<%= request.getAttribute("programID") %>
			+ "&selectIDs="	+ selectIDs.substring(0, selectIDs.length - 1), {
				title : "",
				width : 564,
				height : 250
			});
		}
		else{
			alert("<s:text name="items" />");
			return false;
		}	    			
	}
	function okselect(){
		var arr = $("input[class='fileid']");  //document.getElementsByTagName('input');
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
					"fileId" : selectID
				     };
				$.ajax({
					type : "post",
					dataType : "json",
					url : "looprect!backgroundpreview",
					data : param,
					complete : function() {
					},
					success : function(msg) {
						if (msg.success) {			
							/*  callParentFunction("closepreview", "http://www.baidu.com/img/baidu_sylogo1.gif");
	    					return closeIFrame(); */ 
							window.parent.returnValue = ""+msg.data+"";
	    					window.parent.close();
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
		else{
			alert("<s:text name="items" />");
			return false;
		}	    			
	}
</script>
</html>

