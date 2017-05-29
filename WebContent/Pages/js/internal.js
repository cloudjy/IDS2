var az = true;
var selectedId = 0;
var selectedtreeNode = null;
var namesortflag = "asc", checktimeflag = "asc", sizeflag = "asc", titlecheckstate = "asc";
var currentSortFlag = "desc";
var currentSortName = "check_time";

var viewtype = {
	"list" : "list",
	"view" : "view"
};
var view = viewtype.list;
var treeInternal = new tree({
			HTMLID : "treeInternal",
			AJAX_URL : "internal!list?az=" + az,
			treeClick : internalClick
		});
$(function() {

			treeInternal.reloadTree();
			$p = page("internal!query", globeParam(), showlist);

			$("#first").bind("click", $p.first);
			$("#previous").bind("click", $p.previous);
			$("#next").bind("click", $p.next);
			$("#last").bind("click", $p.last);

		});
function internalClick(e, treeId, treeNode) {

	selectedId = treeNode.id;
	selectedtreeNode = treeNode;
	$("#uploadcateid").val(selectedId);
	$p.currentPageIndex = 1;
	querychangeclear();

	var zTree = $.fn.zTree.getZTreeObj("treeInternal");
	zTree.expandNode(treeNode);
}
function querychangeclear() {
	$("#search").val("");
	queryfiles();
}
var obb = null;
var isfinished = false;

function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	if (obb != null)
		obb();
	isfinished = true;
	return false;
};

function beforeDrag(treeId, treeNodes) {
	return false;
}

function queryfiles() {
	sortfield("desc", "check_time");
	return false;
}

function filter(treeId, parentNode, childNodes) {
	if (!childNodes)
		return null;
	for (var i = 0, l = childNodes.length; i < l; i++) {
		childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
	}
	return childNodes;
}

function rechange() {
	$p.currentPageIndex = 1;
	queryfiles();
}

function showlist(result) {
	if (view == viewtype.list) {
		showlistfunc(result);
	} else if (view == viewtype.view) {
		showviewfunc(result);
	}
}

function changeview(viewparam) {
	view = viewparam;
	queryfiles();
}

function categorysort() {
	az = !az;
	treeInternal.reloadTree();
	queryfiles();
}

function getType(typeid) {
	if (typeid == undefined) {
		typeid = 0;
	}
	if (typeid == 3) {
		return "文字";
	} else if (typeid == 1) {
		return "图片";
	} else if (typeid == 2) {
		return "视频";
	} else if (typeid == -1) {
		return "音频";
	}
}

function showviewfunc(result) {
	try {
		SELECT_ELEMENT = "#ee input";
		var content = "";
		content += "<div><ul class=\"picLb\" style=\"overflow:hidden\">";
		if (result.data != undefined) {
			$.each(result.data, function(i, f) {
				var filename = (f.name.length > 9
						? f.name.substr(0, 9)
						: f.name);

				var checked = $("input[name=selectsource][value=" + f.sourceId
						+ "]").attr("checked");
				var checkedvalue = checked ? "checked" : "";

				content += "<li>";
				content += (f.type != 2
						? "<a href=\"javascript:void(0)\" target='_blank'><img src=\"..//skins/default/images/picBk.png\"class=\"picBk\" /></a>"
						: "<img src=\"../skins/default/images/picBk.png\"class=\"picBk\" />");
				content += "<img src=\"img/tag.jpg\"  width=\"80\" height=\"60\" />";
				content += "<input type=\"checkbox\" name=\"selectsource\" id=\""
						+ f.name
						+ "\" value=\""
						+ f.sourceId
						+ "\" "
						+ checkedvalue + "/>";
				content += "<span title=\"" + f.name + "\">" + filename
						+ "</span>";
				content += "</li>";

			});
		}
		content += "</ul></div>";
		$("#mytab").remove();
		$("#shujBt").remove();

		$("#ee").html(content);

		ipubs.models.jscroll_ee();
	} catch (e) {
		alert(e.message);
	}
}

function changestate(sourceid, stateid) {
	var zTree = $.fn.zTree.getZTreeObj("treeInternal");
	var selectedtreeNode = zTree.getSelectedNodes();
	var id = 0;
	if (selectedtreeNode != undefined && selectedtreeNode.length != 0) {
		id = selectedtreeNode[0].id;
	}
	ajaxCallback("internal!changeState.action", {
				"id" : id,
				"sourceid" : sourceid,
				"state" : stateid
			}, queryfiles);
}
function showlistfunc(result) {
	try {
		SELECT_ELEMENT = "#mytab > tbody input[type=checkbox]";

		var content = "";
		var thead = "<tr>"
				+ "<td  id=\"sizesort\" class=\"first\" onclick='sizesort()'  style=\"cursor: pointer;width:80px;\">"
				+ "状态"
				+ "</td>"
				+ "<td id=\"namesort\" onclick='namesort()' style=\"cursor: pointer; width:230px;\" >"
				+ "名称"
				+ "</td>"
				+ "<td id=\"namesort\" onclick='namesort()'  style=\"cursor: pointer; width:230px;\" >"
				+ "类型"
				+ "</td>"
				+ "<td id=\"namesort\" onclick='namesort()' style=\"cursor: pointer; width:230px;\" >"
				+ "内容" + "</td>" + "</tr>";
		if (result != undefined && result.data != undefined) {
			$.each(result.data, function(i, f) {
				var checked = $("input[name=selectsource][value=" + f.sourceId
						+ "]").attr("checked");
				var checkedvalue = checked ? "checked" : "";

				content += "<tr>";
				content += "<td style=\"width: 66px;\"><input type=\"checkbox\" name=\"selectsource\" id=\""
						+ f.name
						+ "\" value=\""
						+ f.sourceId
						+ "\" "
						+ checkedvalue + " />";
				/*
				 * + "<img
				 * onclick=\"changestate('"+f.sourceId+"','"+(f.state==0?1:0)+"')\" " +
				 * (f.state==0?"src=\"../skins/default/images/btn2.png\"
				 * ":"src=\"../skins/default/images/btn3.png\" ")+
				 * "height=\"15px\" title=\"" + (f.state==0?"服务开启":"服务暂停")+ "\"
				 * width=\"15px\"></td>";
				 */
				content += "<td style=\"width:180px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;\">"
						+ f.name + "</td>";
				content += "<td style=\"width:180px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;\">"
						+ getType(f.type) + "</td>";
				content += "<td style=\"width:180px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;\"><a class='blue1' href='javascript:selectTextContext("
						+ f.sourceId + "," + f.catId + ")'>查看</a></td>";
				content += " </tr>";

			});
		}
		$("#shujBt").remove();
		$("#ee").html("");
		$("#ee")
				.before("<table class=\"shujBt\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" id=\"shujBt\"></table>");
		$("#ee")
				.prepend("<table id=\"mytab\"  style=\"width: 100%;table-layout: fixed;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"shujTb\"><thead></thead><tbody></tbody></table>");
		$("#shujBt").html(thead);
		$("#mytab tbody").html(content);

		$('.shujTb > tbody >tr:odd').addClass('alt');

		/**
		 * 重置滚动条
		 */
		ipubs.models.jscroll_ee();
	} catch (e) {
		alert(e.message);
	}
}
function selectTextContext(sourceid, catid) {
	showIPUBDialogStand("internal!select_text.action?internaltag.sourceId="
					+ sourceid + "&internaltag.catId=" + catid, {
				"title" : "查看文字",
				width : 470,
				height : 280,
				resizable : false
			});
}
function sortfield(flag, name) {
	$p.currentSortManaer = flag;
	$p.currentSortName = name;
	$p.ajax($p.globeParam(globeParam()));
}

function changepagesize() {
	$p.currentPageIndex = 1;
	$p.changepagesize($("select[name=pagesize]").val());
}

function globeParam(inparam) {
	var zTree = $.fn.zTree.getZTreeObj("treeInternal");
	var selectedtreeNode = zTree.getSelectedNodes();
	var id = 0;
	if (selectedtreeNode != undefined && selectedtreeNode.length != 0) {
		id = selectedtreeNode[0].id;
	}
	var param = {
		"id" : id
		,
	};
	if (typeof(inparam) != undefined) {
		copyParam(param, inparam);
	}
	return param;
}
