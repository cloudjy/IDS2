

function getDialogParam() {
	return {
		modal : true,
		resizable : false,
		draggable : true,
		width : 400,
		height : 280,
		zIndex : 19564
		,
	};
}
function finished(result) {
	alert(result);
}

var province = "";
var selectedtreeNode = null;
var currentPageFlag = 1;
var totalPageFlag = 1;
var pageSize = 20;

$(function() {
			$.fn.zTree.init($("#treeweather"), setting, null);
			loadRoot();
			readu();
			$("#first").bind("click", $p.first);
			$("#previous").bind("click", $p.previous);
			$("#next").bind("click", $p.next);
			$("#last").bind("click", $p.last);
			//$p.ajax();

		});

function loadRoot() {
	currentPageFlag = 1;
	pageSize = 20;
	$("select[name=pagesize]").val(pageSize);
	
	var treeObj = $.fn.zTree.getZTreeObj("treeweather");
	var nodes = treeObj.getNodes();
	if (nodes.length > 0) {
		treeObj.selectNode({
					id : -1
				});
	}
	province = "";

	queryweathers();
}
var setting = {
	async : {
		enable : true,
		url : "weather!list.action",
		autoParam : ["id", "name=n", "level=lv"],
		otherParam : {
			"otherParam" : "zTreeAsyncTest"
		},
		dataFilter : filter
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	edit : {
		drag : {
			isMove : true
		},
		enable : false,
		showRemoveBtn : false,
		showRenameBtn : false
	},
	view : {
		selectedMulti : false
	},
	callback : {
		onClick : onClick,
		beforeDrag : beforeDrag,
		onAsyncSuccess : zTreeOnAsyncSuccess
	}
};

var obb = null;
var isfinished = false;
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	if (obb != null)
		obb(); // duma.BeautifyScrollBar.init();

	isfinished = true;
	return false;
};

function onDblClick(e, treeId, treeNode) {
	var treeObj = $.fn.zTree.getZTreeObj("treeweather");

	province = treeObj.getSelectedNodes()[0] == undefined ? '' : treeObj
			.getSelectedNodes()[0].name;

	selectedtreeNode = treeNode;
	currentPageFlag = 1;
	queryweathers();
}

function onClick(e, treeId, treeNode) {
	var treeObj = $.fn.zTree.getZTreeObj("treeweather");
	province = treeObj.getSelectedNodes()[0] == undefined ? '' : treeObj
			.getSelectedNodes()[0].name;
    
	selectedtreeNode = treeNode;
	currentPageFlag = 1;

	pageSize = 20;
	$("select[name=pagesize]").val(pageSize);
	queryweathers();

	var zTree = $.fn.zTree.getZTreeObj("treeweather");
	zTree.expandNode(treeNode);
}

function beforeDrag(treeId, treeNodes) {
	return false;
}
function queryweathers() {

	var param = {
		"province" : province
	};
	var url = encodeURI(encodeURI("weather!citys?province=" + province));
	$p = page(url, param, showlistfunc);
	$p.ajax();
	return false;
}

function ajaxCallback(url, param, callback) {
	$.ajax({
				type : "post",
				dataType : "json",
				url : url,
				data : param,
				complete : function() {
				},
				success : function(msg) {
					callback(msg);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					window.top.location.href = '/';
				}
			});
}

function removecity() {
	if ($("#mytab tbody input:checked").length == 0) {
		alert(langMap.globalMap.selectItemsError);
		return;
	}
	confirm(langMap.globalMap.sureToDelete, function() {
				var param = {
					"citys" : []
				};
				$("#mytab tbody input:checked").each(function(i, f) {
							param.citys.push(f.value);
						});
				ajaxCallback("weather!remove", $.param(param, true), function(
								result) {
							if (result.success) {
								$.fn.zTree.init($("#treeweather"), setting,
										null);
								loadRoot();
							}
						});

			});
}

function filter(treeId, parentNode, childNodes) {
	if (!childNodes)
		return null;
	for (var i = 0, l = childNodes.length; i < l; i++) {
		childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
	}
	return childNodes;
}

function changepagesize() {
	currentPageFlag = 1;
	changepagesize($("select[name=pagesize]").val());
}

function removeNode() {
	if (selectedId == 0 || selectedId == undefined) {
		alert(getDeleteGroupError());
		return;
	}
	if (confirm(getRemoveNode() + selectedtreeNode.name)) {
		ajaxCallback("filecate!remove", {
					"cate.categoryId" : selectedtreeNode.id
				}, removedNode);
	}
}

function ajax(url, param, callback) {
	$.ajax({
				type : "post",
				dataType : "json",
				url : url,
				data : param,
				complete : function() {
				},
				success : function(bean, textStatus) {
					callback(bean, textStatus);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					window.top.location.href = '/';
				}
			});
}

function showlistfunc(result) {
	try {
		$("#shujBt").remove();
		$("#ee").html("");
		var content = "";
		var thead = "<tr>"
				+ "<td id=\"namesort\"   class=\"first\" style=\"width:230px;\" >"
				+ langMap.weatherMap.cityName + "</td>"
				+ "<td  id=\"sizesort\"   style=\"width:80px;\">"
				+ langMap.weatherMap.province + "</td>" + "</tr>";
		$.each(result.data, function(i, f) {
			content += "<tr>"
					+ "<td style=\"width:12px;\"><input type=\"checkbox\" name=\"selectfile\" value="
					+ f.cityId
					+ " /></td>"
					+ "<td style=\"width:180px;overflow: hidden;text-overflow: ellipsis;\" title=\""
					+ f.cityName
					+ "\">"
					+ f.cityName
					+ "</td>"
					+ "<td style=\"width:80px;overflow: hidden;text-overflow: ellipsis;\" title=\""
					+ f.province + "\">" + f.province + "</td>" + " </tr>";

		});

		$("#ee")
				.before("<table class=\"shujBt\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" id=\"shujBt\"></table>");
		$("#ee")
				.prepend("<table id=\"mytab\"  style=\"width: 100%;table-layout: fixed;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"shujTb\"><thead></thead><tbody></tbody></table>");
		$("#shujBt").html(thead);

		$("#mytab tbody").html(content);

		$('.shujTb > tbody >tr:odd').addClass('alt');
		ipubs.models.jscroll_ee();
	} catch (e) {
		alert(e.message);
	}
}

function gotoPageByIndex() {
	var pageIndex = $("input[name=pageIndex]").val();
	if (isNaN(pageIndex)) {
		alert(pageIndexNotInteger());
	}
	if (pageIndex == undefined || pageIndex.trim() == "" || pageIndex == 0) {
		pageIndex = 1;
	}
	if (pageIndex > totalPageFlag) {
		pageIndex = totalPageFlag;
	} else if (pageIndex < 1) {
		pageIndex = 1;
	}
	gotoPage(pageIndex);

}

function setTotalFlag(flag) {
	totalPageFlag = flag;
	$("#pageIndex").html(generic(totalPageFlag));
}
function generic(index) {
	var page_index_result = "";
	for (var i = 1; i <= index; i++) {
		page_index_result += "<a href='javascript:gotoPage(" + i + ")'>" + i
				+ "</a>&nbsp;";
	}
	return page_index_result;
}

function selectedAll() {
	$s.selectedAll($("#mytab > tbody input[type=checkbox]"));
}
function cancel() {
	$s.cancel($("#mytab > tbody input[type=checkbox]"));
}

function deselect() {
	$s.deSelected($("#mytab > tbody input[type=checkbox]"));
}

var $s = {
	selectedAll : function(object) {
		object.attr("checked", true);
	},
	cancel : function(object) {
		object.attr("checked", false);
	},
	deSelected : function(object) {
		object.each(function(i, o) {
					o.checked = !o.checked;
				});
	}
};

function showfile(result) {
	alert(result.data);
}

function gotoPage(index) {
	var treeObj = $.fn.zTree.getZTreeObj("treeweather");

	currentPageFlag = index;

	ajaxCallback("weather!citys", {
				"province" : treeObj.getSelectedNodes()[0] == undefined
						? ''
						: treeObj.getSelectedNodes()[0].name
			}, showlistfunc);
}

function first() {
	gotoPage(1);
}
function previous() {
	gotoPage(currentPageFlag - 1 <= 1 ? 1 : currentPageFlag - 1);
}
function next() {
	gotoPage(currentPageFlag + 1 >= totalPageFlag
			? totalPageFlag
			: currentPageFlag + 1);
}
function last() {
	gotoPage(totalPageFlag);
}
