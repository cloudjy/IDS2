<%@page import="com.gnamp.server.model.Source"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="com.gnamp.server.model.Category"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css"
	href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" />
<link href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${theme}/skins/default/css/jqtransform.css"
	media="all" />

<script type="text/javascript" src="${theme}/js/jquery-1.7.2.min.js"></script>
<script language="javascript" type="text/javascript"
	src="${theme}/js/niceforms.js"></script>
<script type="text/javascript" src="${theme}/js/jquery.jqtransform.js"></script>
<script type="text/javascript" src="${theme}/js/jscroll.js"></script>
<script type="text/javascript"
	src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
<script type="text/javascript"
	src="${theme}/js/ipub.models-<s:text name="langjs"/>.js"></script>
<link href="${theme}/skins/default/css/ipubs-dialog.css"
	rel="stylesheet" type="text/css" />

<script src="js/gnamp.js" language="javascript"></script>
<script src="../Pages/js/JScript.js"></script>
<script type="text/javascript">
	
<%Source source = (Source) request.getAttribute("source");%>
	var catId =<%=source.getCatId()%>;
	var sourceId =<%=source.getSourceId()%>;
	$(function() {
		var param = {
			"source.sourceId" : sourceId,
			"source.catId" : catId,
			"page.currentPage" : 1,
			"page.pageSize" : 20
		};
		$p = new page("tag!queryRssTagList", param, showlist);
		$p.ajax();

		$("#first").bind("click", $p.first);
		$("#previous").bind("click", $p.previous);
		$("#next").bind("click", $p.next);
		$("#last").bind("click", $p.last);
	});

	function addText() {
		showIPUBDialogStand("tag!addRss.action?source.sourceId=" + sourceId
				+ "&source.catId=" + catId, {
			title : "",
			width : 583,
			height : 215
		});
	}

	function querytextfiles() {
		$p.ajax();
	}

	var contextlist = {
		data : [],
		searchObject : function(id) {
			if (this.data == undefined || this.data.length == 0
					|| id == undefined) {
				return;
			}
			for ( var o = 0, obj = this.data[o]; obj = this.data[o++];) {
				if (obj != null && obj.id == id) {
					return obj;
				}
			}
		}
	};

	function showlist(result) {
		var content = "";
		contextlist.data = [];
		if (result && result.data && result.data.length > 0) {
			$.each(result.data, function(i, f) {
					contextlist.data.push({
						"id" : f.id,
						"text" : f.url
					});
					var checked = $("input[name=wordtext][value=" + f.id + "]")
							.attr("checked");
					content += "<tr>";
					content += "<td style=\"width: 12px;\">";
					content += "<label>";
					content += "<input type=\"checkbox\" value=\"" + f.id
							+ "\"  name=\"wordtext\"  "
							+ (checked ? " checked " : " ")
							+ "class=\"textfileid\" />";
					content += "</label></td>";
					content += "<td style=\"width: 700px;\">"
							+ (f.url.length > 50 ? f.url.substr(0, 50) + "..."
									: f.url) + "</td>";
					content += "<td>"
							+ ((result.page.currentPage - 1)
									* result.page.pageSize + 1 + i) + "</td>"
							+ " </tr>";
			});
		}
		$("#mytab").html(content);

		$('.shujTb > tbody >tr:odd').addClass('alt');

		/**
		 * 重置滚动条
		 */
		ipubs.models.jscroll_ee();
	}

	var remember = null;
	function edited(result) {
		if (result == 'ok') {
			querytextfiles();
		}
	}

	function removete() {

		if ($("input[name=wordtext]:checked").length == 0) {
			return;
		}
		confirm(langMap.globalMap.sureToDelete, function() {

			var param = {
				"source.sourceId" : sourceId,
				"source.catId" : catId,
				"rssids" : []
			};
			$("input[name=wordtext]:checked").each(function(i, o) {
				param.rssids.push(o.value);
			});

			ajaxCallback("tag!removeRssTag.action", $.param(param, true),
					function(result) {
						if (result && result.success) {
							querytextfiles();
						} else {
							alert(langMap.globalMap.deleteItemFail);
						}
					});

		});
	}

	function edit() {
		if ($("input[name=wordtext]:checked").length > 0) {
			showIPUBDialogStand("tag!preEditRssTag.action?source.sourceId="
					+ sourceId + "&source.catId=" + catId + "&rss.id="
					+ $("input[name=wordtext]:checked").val(), {
				title : "",
				width : 583,
				height : 215
			});
		}
	}
	function added(result) {
		if (result == 'ok') {
			querytextfiles();
		}
	}

	function moveSequence(upordown) {
		var id = $("input[name=wordtext]:checked").val();
		if (id == undefined) {
			return;
		}
		var swapSequence = contextlist.searchObject(id).sequence;
		ajaxCallback("tag!moveRssTag.action", {
			"source.sourceId" : sourceId,
			"source.catId" : catId,
			"rss.id" : id,
			"upordown" : upordown
		}, function(result) {
			if (result && result.success) {
				querytextfiles();
			}
		});
	}

	function updateRss() {
		if($("input[name=wordtext]:checked").length==0){
			return;
		}
		var param = {
			"source.sourceId" : sourceId,
			"source.catId" : catId,
			"rssids" : []
		};
		$("input[name=wordtext]:checked").each(function(i, o) {
			param.rssids.push(o.value);
		});

		ajaxCallback("tag!updateRssXml.action", $.param(param, true), function(
				result) {
			if (result && result.success) {
				querytextfiles();
			}
		});
	}
	
	function viewRss(){
		if($("input[name=wordtext]:checked").length==0){
			return;
		}
		showIPUBDialogStand("tag!showCustomRss.action?source.sourceId="
				+ sourceId + "&source.catId=" + catId + "&rss.id="
				+ $("input[name=wordtext]:checked").val(), {
			title : "",
			width : 470,
			height : 280
		});
	}
</script>
</head>
<body>
	<!-- 弹出窗口 开始-->
	<div class="tcWindow" style="width: 900px;">
		<table border="0" cellpadding="0" cellspacing="0" class="tcBt">
			<tr>
				<td style="width: 5px;"><img
					src="${theme}/skins/default/images/tcBt1.png" width="5" height="35"
					class="left" /></td>
				<td class="bg">

					<h2></h2>
				</td>
				<td style="width: 5px;"><img
					src="${theme}/skins/default/images/tcBt3.png" width="5" height="35"
					class="right" /></td>
			</tr>
		</table>
		<div class="tcNr"> 
			<div>

				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="shujBt shujBt2">
					<tr>
						<td class="first" style="width: 550px;"><s:text
								name="rssaddress" /></td>
						<td style="width: 110px;"><s:text name="seq" /></td>
					</tr>
				</table>


				<div
					style="height: 335px; overflow: auto; border-right: #ccc solid 2px;">
					<table id="mytab" width="100%" border="0" cellspacing="0"
						cellpadding="0" class="shujTb shujTb2">

					</table>
				</div>
				<div style="height: 30px; background: #d5dcde;">
					<div class="page1 page2"
						style="width: 570px; overflow: hidden; padding-top: 5px; float: right;">
						<%@ include file="/template/Public/page.jsp"%>
					</div>
				</div>
				<div class="rightMenu"
					style="height: 20px; margin: 0px; padding: 5px;">
					<ul class="left rightMenuShort">
						<li><a
							onclick="return CheckHelper.checkAll('textfileid');return false;"
							class="CheckLink"><s:text name="selectall" /></a></li>
						<li><a
							onclick="return CheckHelper.reverseAll('textfileid');return false;"
							class="CheckLink"><s:text name="invertselection" /></a></li>
						<li><a
							onclick="return CheckHelper.cleanAll('textfileid');return false;"
							class="CheckLink"><s:text name="cancel" /></a></li>

					</ul>
				</div>
				<div class="rightMenu"
					style="height: 20px; margin: 0px; padding: 5px;">
					<ul class="left rightMenuShort">
						<li><a href="javascript:void(0)" onclick="addText()"><s:text
									name="create" /></a></li>
						<li><a href="javascript:void(0)" onclick="edit()"><s:text
									name="edit" /></a></li>
						<li><a href="javascript:void(0)" onclick="removete()"><s:text
									name="delete" /></a></li>
						<li><a href="javascript:void(0)"
							onclick="moveSequence(true);"><s:text name="up" /></a></li>
						<li><a href="javascript:void(0)"
							onclick="moveSequence(false);"><s:text name="down" /></a></li>
						<li><a href="javascript:void(0)" onclick="updateRss();"><s:text
									name="update" /></a></li>
						<li><a href="javascript:void(0)" onclick="viewRss();"><s:text
									name="view" /></a></li>
					</ul>
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

</html>

