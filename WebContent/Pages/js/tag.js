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
			AJAX_URL : "tagcate!tags?az=" + az,
			treeClick : internalClick
		});
$(function() {
			systemSourceCates();
			treeInternal.reloadTree();
			$p = new page("tag!query", globeParam(), showlist);

			$("#type").bind("change", querychangeclear);
			$("#filecheckstate").bind("change", querychangeclear);
			$("#vender").bind("change", querychangeclear);
			$("#includeChildren").bind("change", querychangeclear);
			$("#add").bind("click", add);
			$("#edit").bind("click", edit);
			$("#delete").bind("click", del);

			$(".addTagNode").bind("click", addTagNode);
			$(".editTagNode").bind("click", editTagNode);
			$(".removeTagNode").bind("click", removeTagNode);

			$("#exportTagNode").bind("click", exportTagNode);

			$("#first").bind("click", $p.first);
			$("#previous").bind("click", $p.previous);
			$("#next").bind("click", $p.next);
			$("#last").bind("click", $p.last);
		});

function tagFilterShow() {
	$(".internalFilter").hide();
	$(".tagFilter").show();
}

function internalFilterShow() {
	$(".tagFilter").hide();
	$(".internalFilter").show();
}

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

function removeTagNode() {
	if (treeInternal.selectTree().id == undefined) {
		alert(langMap.fileMap.deleteRootError);
		return;
	}
	var zTree = $.fn.zTree.getZTreeObj(treeInternal.HTMLID);
	var selectedtreeNode = zTree.getSelectedNodes();
	treeInternal.removeNode({
				url : "tagcate!remove",
				param : {
					"source.categoryId" : selectedtreeNode[0].id
				},
				callback : function(result) {
					if (result == undefined) {
						return;
					}
					if (!result.success) {
						if (result.message != undefined) {
							alert(result.message);
						}
						return;
					}
					zTree.removeNode(selectedtreeNode[0]);
				}
			});
}

function beforeDrag(treeId, treeNodes) {
	return false;
}

function queryfiles() {
	$p = new page("tag!query", globeParam(), showlist);
	sortfield("desc", "check_time");
	return false;
}

function rechange() {
	$internal.currentPageIndex = 1;
	// $p.currentPageIndex = 1;
	// queryfiles();
	$internal.ajax();
}

function del() {
	if ($("input[name=selectsource]:checked").length < 1) {
		alert(langMap.tagMap.selectEditTag);
		return;
	}
	var param = {
		"sourcelist" : []
	};
	$("input[name=selectsource]:checked").each(function(i, o) {
				param.sourcelist.push(o.value);
			});
	if (param.length == 0) {
		return;
	}
	ajaxCallback("tag!source_isused", $.param(param, true), function(
					isUsedResult) {
				if (isUsedResult.success) {
					var alertText = "";
					if (isUsedResult.data == "true") {
						alertText = langMap.tagMap.tagUesdDeleteError;
						alert(alertText);
						return;
					} else {
						alertText = langMap.globalMap.sureToDelete;
					}
					confirm(alertText, function() {
								ajaxCallback("tag!remove",
										$.param(param, true), queryfiles);
							});
				}

			});

}
function edit() {
	var params = {
		"sourcelist" : []
	};
	$("input[name=selectsource]:checked").each(function() {
				params.sourcelist.push(this.value);
			});

	if (params.sourcelist.length > 0) {
		showIPUBDialogStand("tag!edittag?" + $.param(copyParam({
									"currentCateId" : selectedId
								}, params), true) + "", {
					title : langMap.tagMap.edit,
					width : 500,
					height : 300
				});
	} else {
		alert(langMap.tagMap.selectEditTag);
	}

}
function selectLoad() {
	$("#uploadcateid").val(0);
	var treeObj = $.fn.zTree.getZTreeObj(treeInternal.HTMLID);
	var nodes = treeObj.getNodes();
	if (nodes.length > 0) {
		treeObj.selectNode({
					id : 0
				});
	}
	queryfiles();
}
function add() {
	var id = treeInternal.selectTree().id;
	if (id == undefined) {
		id = 0;
	}
	showIPUBDialogStand("tag!addtag?source.catId=" + id, {
				title : langMap.tagMap.addTag,
				width : 500,
				height : 300
			});
}

function addTagNode() {
	var id = treeInternal.selectTree().id;
	if (id == undefined) {
		id = 0;
	}
	showIPUBDialogStand("tagcate!pre_add?id=" + id, {
				title : langMap.fileMap.createGroup,
				width : 400,
				height : 190
			});
}

function addNodeed(result) {
	try {
		var id = treeInternal.selectTree().id;
		if (id == undefined) {
			id = 0;
		}
		var zTree = $.fn.zTree.getZTreeObj("treeInternal");
		zTree.addNodes(id == 0 ? 0 : zTree.getNodeByParam("id", id, null), {
					id : result.categoryId,
					pId : id,
					name : result.categoryName
				});
		return true;
	} catch (e) {
	}
}

function editTagNode() {
	var id = treeInternal.selectTree().id;
	if (id == undefined || id == 0) {
		alert(langMap.fileMap.selectEditGroup);
		return;
	}
	showIPUBDialogStand("tagcate!pre_edit?id=" + id, {
				width : 500,
				height : 250,
				title : langMap.fileMap.editGroup
			});
}

function editNodeed(result) {
	if (result == "" || result == undefined) {
		return;
	}
	/**
	 * 如果没有更改目录的信息，就不用刷新
	 */
	if (result.categoryName == undefined && result.parentId == undefined) {
		return;
	}
	var zTree = $.fn.zTree.getZTreeObj("treestand");
	var selectedtreeNode = zTree.getSelectedNodes();
	selectedtreeNode[0].name = result.categoryName;
	selectedtreeNode[0].parentId = result.parentId;
	zTree.moveNode(zTree.getNodeByParam("id", result.parentId),
			selectedtreeNode[0], "inner", false);
	zTree.updateNode(selectedtreeNode[0]);
	queryfiles();
}

function removedNode(result) {
	if (!result.success) {
		if (result.message != undefined) {
			alert(result.message);
		}
		return;
	}
	var zTree = $.fn.zTree.getZTreeObj("treestand");
	zTree.removeNode(selectedtreeNode);
	selectedtreeNode.id = 0;
	selectedId = 0;
}

function namesort() {
	namesortflag = (namesortflag == "asc" ? "desc" : "asc");
	sortfield(namesortflag, "name");
}

function showlist(result) {
	if (view == viewtype.list) {
		showlistfunc(result);
	} else if (view == viewtype.view) {
		showviewfunc(result);
	}
	$(".rightMenu").show();
	$("#bottom_prompt_custom").show();
	$("#bottom_prompt_internal").hide();  
	tagFilterShow();
}

function changeview(viewparam) {
	view = viewparam;
	queryfiles();
}

function previewFile(id) {
	showIPUBDialogStand("file!preview?file.fileId=" + id, {
				width : document.body.clientWidth,
				height : document.body.clientHeight,
				title : langMap.fileMap.preview
			});

}

function categorysort() {
	az = !az;
	treeInternal.AJAX_URL = "tagcate!tags?az=" + az;
	treeInternal.reloadTree();
	queryfiles();
}

function showviewfunc(result) {
	try {
		SELECT_ELEMENT = "#ee input";
		var content = "";
		content += "<div><ul class=\"picLb\" style=\"overflow:hidden\">";
		$.each(result.data, function(i, f) {
			var checked = $("input[name=selectsource][value=" + f.sourceId
							+ "]").attr("checked");
			var checkedvalue = checked ? "checked" : "";

			var filename = (f.name.length > 9 ? f.name.substr(0, 9) : f.name);
			content += "<li>";
			content += (f.type != 2
					? "<a href=\"javascript:void(0)\" target='_blank'><img src=\"..//skins/default/images/picBk.png\"class=\"picBk\" /></a>"
					: "<img src=\"../skins/default/images/picBk.png\"class=\"picBk\" />");
			content += "<img src=\"img/tag.jpg\"  width=\"80\" height=\"60\" />";
			content += "<input type=\"checkbox\" name=\"selectsource\" id=\""
					+ f.name + "\" value=\"" + f.sourceId + "\" "
					+ checkedvalue + " />";
			content += "<span title=\"" + f.name + "\">" + filename + "</span>";
			content += "</li>";

		});
		content += "</ul></div>";
		$("#mytab").remove();
		$("#shujBt").remove();

		$("#ee").html(content);

		ipubs.models.jscroll_ee();
	} catch (e) {
		alert(e.message);
	}
}

function getType(typeid) {
	if (typeid == undefined) {
		typeid = 0;
	}
	if (typeid == 3) {
		return langMap.tagMap.text;
	} else if (typeid == 1) {
		return langMap.tagMap.picture;
	} else if (typeid == 2) {
		return langMap.tagMap.video;
	} else if (typeid == 4) {
		return langMap.tagMap.audio;
	} else if (typeid == 5) {
		return langMap.tagMap.rss;
	}
}
function filter(treeId, parentNode, childNodes) {
	if (!childNodes)
		return null;
	for (var i = 0, l = childNodes.length; i < l; i++) {
		childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
	}
	return childNodes;
}

function TypeEdit(typeid, catId, sourceId) {
	if (typeid == undefined) {
		typeid = 0;
	}
	if (typeid == 3) {
		showIPUBDialogStand("tag!editTag.action?source.sourceId=" + sourceId
						+ "&source.catId=" + catId + "&source.type=" + typeid,
				{
					"title" : langMap.globalMap.editText,
					width : 1000,
					height : 620,
					close : function() {
						queryfiles();
					}
				});
	} else if (typeid == 1) {
		showIPUBDialogStand("tag!editTag.action?source.sourceId=" + sourceId
						+ "&source.catId=" + catId + "&source.type=" + typeid,
				{
					"title" : langMap.globalMap.editPicture,
					width : 1000,
					height : 620,
					close : function() {
						queryfiles();
					}
				});
	} else if (typeid == 2) {
		showIPUBDialogStand("tag!editTag.action?source.sourceId=" + sourceId
						+ "&source.catId=" + catId + "&source.type=" + typeid,
				{
					"title" : langMap.globalMap.editVideo,
					width : 1000,
					height : 620,
					close : function() {
						queryfiles();
					}
				});
	} else if (typeid == 5) {
		showIPUBDialogStand("tag!editTag.action?source.sourceId=" + sourceId
						+ "&source.catId=" + catId + "&source.type=" + typeid,
				{
					"title" : langMap.globalMap.editRss,
					width : 1000,
					height : 620,
					close : function() {
						queryfiles();
					}
				});
	}
}

function changestate(sourceid, state) {
	var zTree = $.fn.zTree.getZTreeObj("treeInternal");
	var selectedtreeNode = zTree.getSelectedNodes();
	var id = 0;
	if (selectedtreeNode != undefined && selectedtreeNode.length != 0) {
		id = selectedtreeNode[0].id;
	}
	ajaxCallback("tag!changestate", {
				"source.catId" : id,
				"source.sourceId" : sourceid,
				"source.state" : state
			}, queryfiles);
}

function showlistfunc(result) {
	try {
		SELECT_ELEMENT = "#mytab > tbody input[type=checkbox]";

		var content = "";
		var thead = "<tr>"
				+ "<td  class=\"first\" style=\"cursor: pointer;width:80px;\">"
				+ langMap.tagMap.state
				+ "</td>"
				+ "<td id=\"namesort\" style=\"cursor: pointer; width:230px;\" >"
				+ langMap.tagMap.name
				+ "</td>"
				+ "<td style=\"cursor: pointer; width:130px;\" >"
				+ "ID"
				+ "</td>"
				+ "<td id=\"namesort\"  style=\"cursor: pointer; width:70px;\" >"
				+ langMap.tagMap.type + "</td>"
				+ "<td style=\"cursor: pointer; width:139px;\" >"
				+ langMap.tagMap.content + "</td>" + "</tr>";
		$.each(result.data, function(i, f) {
			var checked = $("input[name=selectsource][value=" + f.sourceId
							+ "]").attr("checked");
			var checkedvalue = checked ? "checked" : "";
			content += "<tr>";
			content += "<td style=\"width: 25px;\"><input type=\"checkbox\" name=\"selectsource\" id=\""
					+ f.name
					+ "\" value=\""
					+ f.sourceId
					+ "\" "
					+ checkedvalue
					+ " />"
					+ "</td><td style=\"width: 25px;\"><img onclick=\"changestate('"
					+ f.sourceId
					+ "','"
					+ (f.state == 0 ? 1 : 0)
					+ "')\" src=\""
					+ (f.state == 0
							? "../skins/default/images/btn2.png\""
							: "../skins/default/images/btn3.png\"")
					+ " height=\"15px\" title=\""
					+ (f.state == 0
							? langMap.tagMap.open
							: langMap.tagMap.close) + "\" width=\"15px\"></td>";
			content += "<td style=\"width:230px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;\" title=\""
					+ f.name + "\">" + f.name + "</td>";
			content += "<td style=\"width:130px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;\">"
					+ f.sourceId + "</td>";
			content += "<td style=\"width:70px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;\">"
					+ getType(f.type) + "</td>";
			content += "<td style=\"width:130px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;\" title=\""
					+ f.taskVersion
					+ "\"><a class='blue1' href='javascript:TypeEdit("
					+ f.type
					+ ","
					+ f.catId
					+ ","
					+ f.sourceId
					+ ")'>"
					+ langMap.tagMap.edit + "</a></td>";
			content += " </tr>";

		});
		$("#shujBt").remove();
		$("#ee").html("");
		$("#ee").before("<table id=\"shujBt\" class=\"shujBt\" style=\"width: 100%;table-layout: fixed;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"></table>");
		$("#ee").prepend("<table id=\"mytab\" class=\"shujTb\" style=\"width: 100%;table-layout: fixed;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><thead></thead><tbody></tbody></table>");
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

function sortfield(flag, name) {
	$p.currentSortManaer = flag;
	$p.currentSortName = name;
	$p.ajax($p.globeParam(globeParam()));
}

function changepagesize() {
	$p.currentPageIndex = 1;
	$p.changepagesize($("select[name=pagesize]").val());
}
function internalreload() {
	$internal = new page("internal!categorys", {
				"id" : $("#internalType").val(),
				"internaltag.name" : $("input[name=searchInternal]").val(),
				"page.pageSize" : $("select[name=pagesize]").val(),
				"page.currentPage" : $("select[name=pageIndex]").val()
			}, function(result) {
				showinternalfunc(result);
				$(".rightMenu").hide();
				$("#bottom_prompt_custom").hide();
				$("#bottom_prompt_internal").show(); 
				internalFilterShow();
			});
	$internal.ajax();
}

function selectTextContext(catid) {
	showIPUBDialogStand("internal!showcatgorycontext.action?internaltag.catId="
					+ catid, {
				"title" : langMap.globalMap.viewText,
				width : 470,
				height : 310,
				resizable : false
			});
}
var systemSourceCatesArray;
function systemSourceCates() {
	ajaxCallback("internal!systemSourceCates", null, function(result) {
				if (result.success) {
					systemSourceCatesArray = result.data;
				}
			});
}
function showCat(catid) {
	if (systemSourceCatesArray) {
		for (var i = 0; i < systemSourceCatesArray.length; i++) {
			if (systemSourceCatesArray[i].id == catid) {
				return systemSourceCatesArray[i].name;
			}
		}
	}
	return "";
}

function showinternalfunc(result) {
	try {
		SELECT_ELEMENT = "#mytab > tbody input[type=checkbox]";

		var content = "";
		var thead = "<tr>"
				+ "<td class=\"first\" style=\"cursor: pointer;width:80px;\">"
				+ langMap.tagMap.category + "</td>"
				+ "<td style=\"cursor: pointer; width:230px;\" >"
				+ langMap.tagMap.name + "</td>"
				+ "<td  style=\"cursor: pointer; width:230px;\" >"
				+ langMap.tagMap.type + "</td>"
				+ "<td style=\"cursor: pointer; width:139px;\" >"
				+ langMap.tagMap.content + "</td>" + "</tr>";
		if (result != undefined && result.data != undefined) {
			$.each(result.data, function(i, f) {
				content += "<tr>";
				content += "<td style=\"width:80px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;\" title=\""
						+ showCat(f.categoryid)
						+ "\">"
						+ showCat(f.categoryid)
						+ "</td>";
				content += "<td style=\"width:230px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;\">"
						+ f.name + "</td>";
				content += "<td style=\"width:230px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;\">"
						+ /* getType(f.type) */"文字" + "</td>";
				content += "<td style=\"width:139px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;\"><a class='blue1' href='javascript:selectTextContext("
						+ f.id + ")'>" + langMap.tagMap.view + "</a></td>";
				content += " </tr>";

			});
		}
		$("#shujBt").remove();
		$("#ee").html("");
		$("#ee").before("<table id=\"shujBt\" class=\"shujBt\" style=\"width: 100%;table-layout: fixed;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"></table>");
		$("#ee").prepend("<table id=\"mytab\" class=\"shujTb\" style=\"width: 100%;table-layout: fixed;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><thead></thead><tbody></tbody></table>");
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
function exportTagNode() {
	var param = {
		"sourcelist" : []
	};
	$("input[name=selectsource]:checked").each(function(i, o) {
				param.sourcelist.push(o.value);
			});
	if (param.sourcelist.length == 0) {
		alert(langMap.tagMap.selectEditTag);
		return;
	}
	location.href = "tag!exportXML.action?" + $.param(param, true);

}

function globeParam(inparam) {
	var zTree = $.fn.zTree.getZTreeObj("treeInternal");
	var selectedtreeNode = zTree.getSelectedNodes();
	var id = 0;
	if (selectedtreeNode != undefined && selectedtreeNode.length != 0) {
		id = selectedtreeNode[0].id;
	}
	var param = {
		"source.catId" : id,
		"source.name" : $("input[name=tagName]").val(),
		"page.pageSize" : $("select[name=pagesize]").val(),
		"page.currentPage" : $("select[name=pageIndex]").val(),
		"includeChildren" : $("#includeChildren").attr("checked") == "checked"
				? "true"
				: "false"
	};
	if (typeof(inparam) != undefined) {
		copyParam(param, inparam);
	}
	return param;
}
