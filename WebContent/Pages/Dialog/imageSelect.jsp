<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="com.gnamp.server.model.Category"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<meta http-equiv="x-ua-compatible" content="ie=8" />



<style type="text/css">
body {
	overflow-x: hidden; /*隐藏水平滚动条*/
	overflow-y: hidden; /*隐藏垂直滚动条*/
}
</style>

<link href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" />
<script src="${theme}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript"
	src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
<script language="javascript" type="text/javascript"
	src="${theme}/js/niceforms.js"></script>
<link rel="stylesheet" type="text/css" media="all"
	href="${theme}/skins/default/css/niceforms-default.css" />
<script src="${theme}/js/ipub.models-<s:text name="langjs"/>.js"
	type="text/javascript"></script>
<link href="${theme}/skins/default/css/ipubs-dialog.css"
	rel="stylesheet" type="text/css" />



<script src="js/gnamp.js" language="javascript"></script>
<script src="../Pages/js/JScript.js"></script>

<% 
	String _actionName = "looprect";
	String _taskType = request.getAttribute("taskType").toString();
	if (_taskType.equalsIgnoreCase("loop")) {
		_actionName = "looprect";
	} else if ( _taskType.equalsIgnoreCase("demand")) {
		_actionName = "demandrect";
	} else if ( _taskType.equalsIgnoreCase("plugin")) {
		_actionName = "pluginrect";
	}
%> 

<script type="text/javascript">
	var namesortflag = "asc";
	var currentSortFlag = "desc";
	var currentSortName = "check_time";

	function namesort() {
		namesortflag = (namesortflag == "asc" ? "desc" : "asc");
		sortfield(namesortflag, "name");
	}
	function sortfield(flag, name) {

		currentSortFlag = flag;
		currentSortName = name;

		query();
	}

	$(document).ready(function() {
		$("#includeChildren").click(function() {
			// alert(this.checked + " i be hiteid");
		});

		$("#includeChildren").change(function() {
			query();
		});

		$("input[name=rd]").click(function() {
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
		var selfid = <%=((Category)request.getAttribute("category")).getCategoryId()%>;
		var groupSelector = $("#groupSelector");
		if ($(obj).is('select')) {
			pid = (groupSelector.length > 0) ? groupSelector.val() : 0;
		}
		var param = {
			"categoryId" : pid,
			"selfid" : selfid
		};
		var surl = "<%= _actionName %>!categorySelector"; 
		$.ajax({
				type : "post",
				dataType : "json",
				url : surl,
				data : param,
				global : false,
				complete : function() {},
				success : function(msg) {
					if (msg.success) {
						try { 
							if ($(obj).is('select')) { 
								var oldId = groupSelector.val();
								var oldText = groupSelector.find( "option:selected").text();

								groupSelector.html("");

								groupSelector.before("<a href=\"javascript:void(0)\" id=\""
										+ oldId
										+ "\" onclick=\"nodeclick("
										+ oldId
										+ ",this)\" >"
										+ oldText
										+ "</a><span id=\""+oldId+"s\">-></span>");

								if (msg.data != null && msg.data.length > 0) {
									var optionHtml = "";

									optionHtml += "<option value=\"0\"><s:text name="select" /></option>";
									$.each(msg.data, function(i, g) {
											optionHtml += "<option value=\""+g.categoryId+"\">"
													+ g.categoryName
													+ "</option>";
										}
									);

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
									$.each(msg.data, function(i, g) {
											optionHtml += "<option title=\""+g.categoryName+"\" value=\""+g.categoryId+"\">"
													+ (g.categoryName.length > 4 ? g.categoryName.substring(0,4)+ "...": g.categoryName)
													+ "</option>";
										}
									);

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
					window.top.location.href = '/';
				}
			}
		);

		return false;

	}

	function query() {
		if ($('input:radio[name="rd"]:checked').val() == 1)
			queryfilesview();
		else
			queryfiles();
	}

	function fileQuickUpload() {
		var url = "file!preuploadfile?categoryId=" + $("#sp a:last").attr("id") + "&autoCheck=true";
		showIPUBDialogStand(url, {
					width : 680,
					height : 540,
					title : langMap.fileMap.fileUpload,
					close : function() {
						query();
					}
				});
		return false;
	}
	
</script>
</head>
<body>
	<!-- 弹出窗口 开始-->
	<div class="tcWindow">
		<table border="0" cellpadding="0" cellspacing="0" class="tcBt">
			<tr>
				<td style="width: 5px;"><img
					src="${theme}/skins/default/images/tcBt1.png" width="5" height="35"
					class="left" /></td>
				<td class="bg">
					<%--  <img
					src="${theme}/skins/default/images/closed.gif"
					style="float: right; padding-right: 10px;" />   --%>
					<h2><s:text name="title_prompt_edit_playitem" /></h2>
				</td>
				<td style="width: 5px;"><img
					src="${theme}/skins/default/images/tcBt3.png" width="5" height="35"
					class="right" /></td>
			</tr>
		</table>
		<div class="tcNr">
			<form method="post" class="niceform">
				<div class="tcBt2">
					<table border="0" cellspacing="0" cellpadding="0"
						style="width: 910px;">
						<tr>
							<td style="white-space: nowrap;">&nbsp;<s:text name="filetype" /></td>
							<td><select size="1" name="select" id="select"
								disabled="disabled">
									<option value="0">
										<s:text name="image" />
									</option>
							</select></td>
							<td style="white-space: nowrap;"><s:text name="filegroup" /></td>
							<td style="width: 500px; white-space: nowrap;"><span id="sp"> <a
									class="root" href="javascript:void(0)" id="0"
									onclick="nodeclick(0,this)"><s:text name="root" /></a> <%
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
							</span></td>
							<td style="white-space: nowrap;"><input type="checkbox" name="interests[]"
								id="includeChildren" value="true" /></td>
							<td style="white-space: nowrap;"><s:text name="child" /></td>
							<td style="white-space: nowrap;"><input style="margin-bottom: 6px;" type="radio"
								onclick="ttstyle()" name="rd" id="rd1" checked="checked"
								value="1" /></td>
							<td style="white-space: nowrap;"><s:text name="view" /></td>
							<td style="white-space: nowrap;"><input style="margin-bottom: 6px;" type="radio"
								onclick="ttstyle()" name="rd" id="rd2" value="2" /></td>
							<td style="white-space: nowrap;"><s:text name="list" /></td>
						</tr>
					</table>
				</div>
			</form>


			<div>
				<div style="width: 310px; height: 400px; float: left;">
					<div
						style="width: 319px; height: 373px; overflow-y: auto; border-right: #ccc solid 2px;">
						<ul style="margin-top: -10px; overflow: hidden; zoom: 1;"
							class="picLb2" id="mytab">

						</ul>
					</div>
					<div class="rightMenu"
						style="height: 20px; margin: 0px; padding-top: 10px; padding-left: 20px;">
						<ul class="left rightMenuShort">
							<li><a
								onclick="return CheckHelper.checkAll('fileid');return false;"
								class="CheckLink"><s:text name="selectall" /></a></li>
							<li><a
								onclick="return CheckHelper.reverseAll('fileid');return false;"
								class="CheckLink"><s:text name="invertselection" /></a></li>
							<li><a
								onclick="return CheckHelper.cleanAll('fileid');return false;"
								class="CheckLink"><s:text name="cancel" /></a></li>
							<li><a onclick="view();"
								style="cursor: hand; cursor: pointer;"><s:text
										name="task_view" /></a></li>
						</ul>
					</div> 
					<div class="rightMenu"
						style="height: 20px; margin: 0px; padding-top: 0px; padding-left: 20px;">
						<s:if test="#session.current_privilege.uploadfile && #session.current_privilege.auditfile">
							<form id="only_css_form_0" class="niceform" style ="float:left;"> 
								<input type="button" onclick="fileQuickUpload();" value="<s:text name="file_quick_upload"/>" />  
							</form> 
						</s:if> 
					</div>
					<div style="font-size:12px;padding-left:20px;"><s:text name="prompt_file_area" /></div>
				</div>
				<div style="width: 590px; float: right; height: 440px;">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						class="shujBt shujBt2 shujBt3" style="table-layout:fixed;">
						<tr>
							<td style="width: 140px;" class="first"><s:text name="name" /></td>
							<td style="width: 50px;"><s:text name="seq" /></td>
							<td style="width: 50px;"><s:text name="task_play_playtime" /></td>
							<td style="width: 110px;"><s:text name="task_play_scalestyle" /></td>
							<td style="width: 110px;"><s:text name="task_play_effect" /></td>
							<td style="width: 70px;"><s:text name="task_play_effecttime" /></td>
						</tr>
					</table>
					<div style="height: 310px; overflow: auto;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							id="righttab" class="shujTb shujTb2 shujTb3" style="table-layout:fixed;">

						</table>
					</div>
					<div style="height: 30px; background: #d5dcde;">
						<div class="page1 page2"
							style="width: 570px; overflow: hidden; padding-top: 5px;">
							<%@include file="/template/pagehy.jsp"%>
						</div>
					</div>
					<div class="rightMenu"
						style="height: 20px; margin: 0px; padding: 5px;">
						<s:if test="#session.current_privilege.editcontent">
						<ul class="left rightMenuShort">
							<li><a
								onclick="return CheckHelper.checkAll('imagefileid');return false;"
								class="CheckLink"><s:text name="selectall" /></a></li>
							<li><a
								onclick="return CheckHelper.reverseAll('imagefileid');return false;"
								class="CheckLink"><s:text name="invertselection" /></a></li>
							<li><a
								onclick="return CheckHelper.cleanAll('imagefileid');return false;"
								class="CheckLink"><s:text name="cancel" /></a></li>

						</ul>
						</s:if>
					</div>
					<div class="rightMenu"
						style="height: 20px; margin: 0px; padding: 5px;">
						<s:if test="#session.current_privilege.editcontent">
						
							<!-- li><a onclick="addfile()"><s:text name="add" /></a></li -->
							<form id="only_css_form" class="niceform" style ="float:left;"> 
								<input type="button" onclick="addfile();" value="<s:text name="add" />" />  
							</form>
						
                    	 <ul class="left rightMenuLong">
						<% if(request.getAttribute("logo") != null && !request.getAttribute("logo").toString().equals("logo")) {%>
							<li><a href="javascript:void(0)" onclick="tag()"><s:text name="bt_add_imagetag" /></a></li>
					   <%} %>
					   	 </ul>
					     <ul class="left rightMenuShort">	
							<li><a onclick="elementsOperate('edit')"
								style="cursor: hand; cursor: pointer;"><s:text name="edit" /></a>
							</li>
							<li><a onclick="elementsOperate('delete')" 
								title="<s:text name="title_delete_content_button" />"
								style="cursor: hand; cursor: pointer;"><s:text name="delete" /></a>
							</li>
							<li><a onclick="elementsOperate('up')"
								style="cursor: hand; cursor: pointer;"><s:text name="up" /></a>
							</li>
							<li><a onclick="elementsOperate('down')"
								style="cursor: hand; cursor: pointer;"><s:text name="down" /></a>
							</li>
							<li><a onclick="elementsOperate('toFirst')"
								style="cursor: hand; cursor: pointer;"><s:text name="toFirst" /></a>
							</li>
							<li><a onclick="elementsOperate('toLast')"
								style="cursor: hand; cursor: pointer;"><s:text name="toLast" /></a>
							</li>
						</ul>
						</s:if>
						<div style="font-size:12px;padding-left:20px;text-align: right;"><s:text name="prompt_playitem_area" /></div>
					</div>
				</div>

			</div>


			<div style="clear: both; height: 1px; overflow: hidden;"></div>
		</div>
		<table border="0" cellpadding="0" cellspacing="0" class="tcDi">
			<tr>
				<td style="width: 5px;"><img
					src="${theme}/skins/default/images/tcDi1.png" width="5" height="8"
					class="left" /></td>
				<td class="bg"></td>
				<td style="width: 5px;"><img
					src="${theme}/skins/default/images/tcDi2.png" width="5" height="8"
					class="right" /></td>
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
			"fileparam.includeChildren" : $("#includeChildren").attr("checked") == "checked" ? "true" : "false",
			"page.currentPage" : 1,
			"page.pageSize" : <%=Short.MAX_VALUE %>,
			"oManaer" : currentSortFlag,
			"oName" : currentSortName
		};

		$.ajax({
				type : "post", 
				dataType : "json",
				url : "file!query",
				data : param,
				complete : function() {},
				success : function(msg) {
					try {
						var content = "";

						content += "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"shujBt shujBt2\">";
						content += "<tr>";
						content += "<td id=\"namesort\" onclick='namesort();' class=\"first\" style=\"cursor: pointer;width: 150px;\"><s:text name="name" /></td>";
						content += "<td style=\"width: 50px;\"><s:text name="preview" /></td>";
						content += "</tr>";
						content += "</table>";
						content += "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"shujTb shujTb2\">";

						$.each(msg.data, function(i, f) { 
								content += "<tr id=\"" + f.fileId + "\">"
										+ "<td style=\"width:12px;\"><lable><input type=\"checkbox\" value=\""+f.fileId+"\" name=\""+f.fileId+"\" id=\"" 
											+  f.fileId
											+  "\" class=\"fileid\" />"
										+ "</label></td>"
										+ "<td title=\""+f.fileName+"\" style=\"width:200px;\">"
										+ (f.fileName.length > 10 ? f.fileName.substr(0, 10) + "..." : f.fileName)
										+ "</td>"
										+ "<td><a href=\"file!preview?file.fileId=" + f.fileId + "\" target='_blank'>"
										+ "<img src=\"${theme}/skins/default/images/zoom.png\" width=\"18\" height=\"18\" border=\"0\" />"
										+ "</a>"
										+ "</td>"
										+ "</tr> "; 
							}
						);

						content += "</table>";

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
						window.top.location.href = '/';
				}
			}
		);
		return false;
	}

	if ($('input:radio[name="rd"]:checked').val() == 1)
		queryfilesview();
	else
		queryfiles();

	$(function() {
		var param = {
			"taskID" : <%= request.getAttribute("taskID") %>,
			"programID" : <%= request.getAttribute("programID") %>,
			"rectID" : <%= request.getAttribute("rectID") %> 
		};

		var surl = "<%= _actionName %>!querycontentlist"; 
		$p = new page(surl, param, showlist);
		$p.ajax();
		/* //queryimagefiles();  */

		$("#first").bind("click", $p.first);
		$("#previous").bind("click", $p.previous);
		$("#next").bind("click", $p.next);
		$("#last").bind("click", $p.last);
	});

	function queryfilesview() {

		var param = {
			"fileparam.categoryId" : ($("#sp a:last")).attr("id"),
			"fileparam.fileType" : 0,
			"fileparam.fileVendor" : '',
			"fileparam.searchKey" : '',
			"fileparam.checkState" : 1,
			"fileparam.includeChildren" : $("#includeChildren").attr("checked") == "checked" ? "true" : "false",
			"page.currentPage" : 1,
			"page.pageSize" : <%=Short.MAX_VALUE %> ,
			"oManaer" : currentSortFlag,
			"oName" : currentSortName
		};

		$.ajax({
				type : "post",
				dataType : "json",
				url : "file!query",
				data : param,
				complete : function() { },
				success : function(msg) {
					try {
						var content = "";
						$.each(msg.data,  function(i, f) {
								content += "<li  title=\""+f.fileName+"\">";
								content += "<a href=\"file!preview?file.fileId="
										+ f.fileId
										+ "\" target='_blank' >"; 
								content += "<div style=\"width:94px;height:86px;\">";
								content += "<img style=\"max-width:94px; max-height:86px\" src=\"file!showview?file.fileId="
										+ f.fileId + "\"  />";
								content += "</div>";

								content += "</a>";
								content += "<input type=\"checkbox\" value=\""+f.fileId+"\" name=\""+f.fileId+"\" " 
										+ "id=\""  +  f.fileId +  "\" class=\"fileid\" />";
								content += "<span>" 
										+ (f.fileName.length > 8 ? f.fileName.substr(0, 8) + "..." : f.fileName)
										+ "</span>  ";
								content += "</li>"; 
							}
						);
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
					window.top.location.href = '/';
				}
			}
		);
		return false;
	}

	function addfile() {
		var arr = $("input[class='fileid']"); //document.getElementsByTagName('input');
		var ok = 0;
		var selectID = 0;
		var selectIDs = "";
		for ( var i = 0; i < arr.length; i++) {
			var abc = arr[i];
			if (abc.type == "checkbox" && abc.checked == true) { 
				ok++;
				selectID = abc.value;
				selectIDs += selectID.toString() + ","; 
			}
		}

		if (ok > 0) {
			var param = {
				"selectIDs" : selectIDs.substring(0, selectIDs.length - 1),
				"taskID" : <%= request.getAttribute("taskID") %> ,
				"programID" : <%= request.getAttribute("programID") %> ,
				"rectID" : <%= request.getAttribute("rectID") %> 
			};

			var surl = "<%= _actionName %>!addimagefile"; 

			ajaxCallback(surl, param, function(msg) {
				if (msg.success) {
					queryimagefiles();
				} else {
					alert(msg.data);
				}
			});

		} else {
			alert('<s:text name="no_select_file" />');
		}
	}
	function queryimagefiles(remember) {
		var param = {
			"taskID" : <%= request.getAttribute("taskID") %>,
			"programID" : <%= request.getAttribute("programID") %>,
			"rectID" : <%= request.getAttribute("rectID") %> 
		};

		var surl = "<%= _actionName %>!querycontentlist"; 

		$p.ajax();
		return false;
	}

	var escapeJQueryDiv =  $("<div/>");
	function toHtml(textString) { 
		return textString? escapeJQueryDiv.text(textString).html() : "";
	}

	function showlist(result) {
		var content = "";
		$.each(result.data, function(i, f) { 
				var checked = false;
				if (remember) {
					for ( var i = 0; i < remember.length; i++) {
						if (remember[i] == f.contentId) {
							checked = true;
							break;
						}
					}
				}

				content += "<tr id=\""+f.imageId+"\">" 
						+ "<td style=\"width:140px;\" class=\"ellipsis\" title=\"" + toHtml(f.fileName) +"\">" 
						+ "<input type=\"checkbox\" value=\"" + f.contentId + "\" "
						+ "name=\"" + f.fileId + "\" " + (checked ? " checked " : "")
						+ "id=\"" + f.imageId + "\" class=\"imagefileid\" />"
						+ toHtml(f.fileName)
						+ "</td>"
						+ "<td style=\"width:50px;\">"
						+ f.sequenceNum
						+ "</td>"
						+ "<td style=\"width:50px;\">"
						+ f.playTime
						+ "</td>"
						+ "<td style=\"width:110px;\" title=\"" + toHtml(getScaleStyle(f.scaleStyle)) + "\">"
						+ toHtml(getScaleStyle(f.scaleStyle))
						+ "</td>" 
						+ "<td style=\"width:110px;\" title=\"" + toHtml(getSwapStyle(f.swapStyle)) + "\">"
						+ toHtml(getSwapStyle(f.swapStyle))
						+ "</td>"
						+ "<td style=\"width:70px;\">"
						+ f.swapTime
						+ "</td>" 
						+ " </tr>";

			}
		);
		$("#righttab").html(content);

	}

	var remember = null;
	function edited(result) {
		if (result == 'ok') {
			queryimagefiles(false);
		}
	}
	

	function added(result) {
		if (result == 'ok') {
			queryimagefiles(false);
		}
	}
	
	var remember = null;
	function elementsOperate(op) {
		var opArray = { 
			"delete":"<%= _actionName %>!deletecontent", 
			"edit":"<%= _actionName %>!ToModifyImage", 
			"up":"<%= _actionName %>!up", 
			"down":"<%= _actionName %>!down", 
			"toFirst":"<%= _actionName %>!toFirst", 
			"toLast":"<%= _actionName %>!toLast", 
		};  
		if (!op || op.length == 0 || !opArray[op])
			return false;  
		var opUrl = opArray[op];
			
		remember = new Array();

		var arr = $("input[class='imagefileid']"); //document.getElementsByTagName('input'); 
		var selectIDs = ""; 
		var fileIDs = "";
		for ( var i = 0; i < arr.length; i++) {
			var abc = arr[i];
			if (abc.type == "checkbox" && abc.checked == true) {  
				selectIDs += abc.value.toString() + ",";
				fileIDs += abc.name.toString() + ","; 
				remember.push(abc.value); 
			}
		}

		if (remember.length <= 0) { 
			alert("<s:text name="no_select_playitem" />");
			return false;
		}
		if (op == "delete") { 
			confirm('<s:text name="is_delete_playitem" />', function() {
					var param = {
						"selectIDs" : selectIDs.substring(0, selectIDs.length - 1),
						"taskID" : <%= request.getAttribute("taskID") %>,
						"programID" : <%= request.getAttribute("programID") %>,
						"rectID" : <%= request.getAttribute("rectID") %>,
						"fileIDs" : fileIDs.substring(0, fileIDs.length - 1)
					}; 
					ajaxCallback(opUrl, param, function(msg) {
						if (msg.success) {
							queryimagefiles(false);
						} else {
							alert(msg.data);
						}
					}); 
				}
			);
		} else if (op == "edit") {
			var modifyImageUrl = opUrl + 
					"?taskID=" + <%= request.getAttribute("taskID") %> + 
					"&programID=" + <%= request.getAttribute("programID") %> + 
					"&rectID=" + <%= request.getAttribute("rectID") %> + 
					"&fileIDs=" + fileIDs.substring(0, fileIDs.length - 1) + 
					"&selectIDs=" + selectIDs.substring(0, selectIDs.length - 1);
			showIPUBDialogStand(modifyImageUrl, {title:"<s:text name="title_edit_contents_attribute"/>", width:564, height:377}); 
		} else if (op == "up" || op == "down" || op == "toFirst" || op == "toLast") {
			var param = {
				"programID" : <%= request.getAttribute("programID") %>,
				"taskID" : <%= request.getAttribute("taskID") %>,
				"rectID" : <%= request.getAttribute("rectID") %> ,
				"contentID" : remember[0]
			};  

			$.ajax({
				type : "post",
				dataType : "json",
				url : opUrl,
				data : param,
				complete : function() {
				},
				success : function(msg) {
					if (msg.success) {
						queryimagefiles(remember);
					} else {
						alert(msg.data);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					window.top.location.href = '/';
				}
			});
		}  
	}

	function getScaleStyle(v) {
		var result = "<s:text name="stfull" />";
		switch (v) {
		case 3:
			result = "<s:text name="stfull" />";
			break;
		case 2:
			result = "<s:text name="stcenter" />";
			break;
		case 1:
			result = "<s:text name="stscale" />";
			break;
		default:
			result = "<s:text name="stfull" />";
			break;
		}
		return result;
	}
	function getSwapStyle(v) {
		var result = "<s:text name="none" />";
		switch (v) {
		case 0:
			result = "<s:text name="none" />";
			break;
		case 1:
			result = "<s:text name="random" />";
			break;
		case 2:
			result = "<s:text name="cutinbottom" />";
			break;
		case 3:
			result = "<s:text name="cutintop" />";
			break;
		case 4:
			result = "<s:text name="cutinleft" />";
			break;
		case 5:
			result = "<s:text name="cutinright" />";
			break;
		case 6:
			result = "<s:text name="hori" />";
			break;
		case 7:
			result = "<s:text name="vert" />";
			break;
		case 8:
			result = "<s:text name="grid" />";
			break;
		case 9:
			result = "<s:text name="sup" />";
			break;
		case 10:
			result = "<s:text name="sbottom" />";
			break;
		case 11:
			result = "<s:text name="sleft" />";
			break;
		case 12:
			result = "<s:text name="sright" />";
			break;
		default:
			result = "<s:text name="none" />";
			break;
		}
		return result;
	}

	function view() { 
		var arr = $("input[class='fileid']"); //document.getElementsByTagName('input'); 
		var selectIDs = "";
		for ( var i = 0; i < arr.length; i++) {
			var abc = arr[i];
			if (abc.type == "checkbox" && abc.checked == true) { 
				selectIDs += abc.value.toString() + ",";  
			}
		}

		if (selectIDs && selectIDs.length > 0) {
			var viewUrl = "<%= _actionName %>!view" +
					"?taskID=" + <%= request.getAttribute("taskID") %> + 
					"&programID=" + <%= request.getAttribute("programID") %> + 
					"&selectIDs=" + selectIDs.substring(0, selectIDs.length - 1);
			showIPUBDialogStand(viewUrl, { title:"", width:564, height:250});
		} else {
			alert("<s:text name="no_select_file" />");
			return false;
		}
	}

	function tag() {
		var tagUrl = "<%= _actionName %>!ToAddTag" + 
				"?rectText.taskId=" + <%=request.getAttribute("taskID")%> + 
				"&rectText.programId=" + <%=request.getAttribute("programID")%> + 
				"&rectText.rectId=" + <%=request.getAttribute("rectID")%> + 
				"&type=1"; 
		showIPUBDialogStand(tagUrl, {title:"", width:943, height:530});
	}
</script>
</html>

