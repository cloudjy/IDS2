var dlg = ".ipub-dialog-pannel";
var gnamp_width = 400;
var gnamp_height = 280;
function IPUBDialog() {
	return $(dlg);
}

function callParentFunction(functionName, param) {
	if (functionName == null || functionName == undefined) {
		return closeWindow();
	}
	location.href = 'javascript:window.parent.' + functionName + "("
			+ JSON.stringify(param) + ")";
}

function closeIFrame() {
	window.parent.closeIPUBDialog();
	return false;
}

function closeIPUBDialog() {
	IPUBDialog().dialog("close");
}
function copyParam(destination, source) {

	for (var property in source) {
		destination[property] = source[property];
	}
	return destination;
}
function showIPUBDialogStand(url, dlgparam) {
	$("<div class=\"ipub-dialog-pannel\"><iframe style=\"width:99%;height:98%;border: 0px;\" src= \""
			+ url + "\"></iframe></div>").dialog(reStandParam(dlgparam));
	return $(this);
}

function standParam() {
	return defaultParam();
}
function reStandParam(param) {
	return copyParam(standParam(), param);
}
function customParam(param) {
	return copyParam(closeParam(), param);
}
function defaultParam() {
	return {
		title : {},
		modal : true,
		resizable : true,
		draggable : true,
		width : gnamp_width,
		height : gnamp_height,
		zIndex : 19564
	};
}

function closeParam() {
	return null; /*
					 * ""; {buttons: {"关闭":function(){
					 * $(this).dialog("close");}}};
					 */
}

page = function(url, param, callback) {
	pageConfig = {
		currentSortManaer : "asc",
		currentSortName : "",
		currentPageIndex : 1,
		totalPageIndex : 1,
		pageSize : 20,
		param : {},
		url : "",
		totalDataID : totalData,
		pageIndexID : "pageIndex",
		currentWithTotalID : "currentWithTotal",
		callback : function(result) {
			showlist(result);
		},
		first : function() {
			pageConfig.gotoPage(1);
		},
		previous : function() {
			pageConfig.gotoPage(pageConfig.currentPageIndex - 1 <= 1
					? 1
					: pageConfig.currentPageIndex - 1);
		},
		next : function() {
			pageConfig
					.gotoPage(pageConfig.currentPageIndex + 1 >= pageConfig.totalPageIndex
							? pageConfig.totalPageIndex
							: pageConfig.currentPageIndex + 1);
		},
		last : function() {
			pageConfig.gotoPage(pageConfig.totalPageIndex);
		},
		gotoPage : function(pageindex) {
			pageConfig.currentPageIndex = pageindex;
			pageConfig.ajax();
		},
		config : function(url, param, callback) {
			this.param = param;
			if (url != undefined)
				this.url = url;
			if (callback != undefined)
				this.callback = callback;
			pageConfig.ajax();
		},
		ajax : function() {
			ajaxCallback(this.url, pageConfig.globeParam(this.param), function(
							msg) {
						if (!msg.success) {
							if (msg.message != undefined) {
								alert(msg.message);
							} else {
								alert("查询数据失败");
							}
							return false;
						}
						pageConfig.callback(msg);
						if (msg.page != undefined) {
							pageConfig.setTotalFlag(msg.page.totalPages);
							$(pageConfig.totalDataID).html(msg.page.totalRows);
							pageConfig.refreshGotoOption(msg.page.totalPages);
						}
					});
		},
		generic : function() {
			var page_index_result = "";
			for (var i = 1; i <= pageConfig.totalPageIndex; i++) {
				page_index_result += "<a href='javascript:pageConfig.gotoPage("
						+ i + ")'>" + i + "</a>&nbsp;";
			}
			return page_index_result;
		},
		setTotalFlag : function(flag) {
			pageConfig.totalPageIndex = flag;

			$("#" + pageConfig.currentWithTotalID)
					.html(pageConfig.currentPageIndex + "/"
							+ pageConfig.totalPageIndex);
			$("#" + pageConfig.pageIndexID).html(pageConfig
					.refreshGotoOption(pageConfig.totalPageIndex));

		},
		globeParam : function(inparam) {
			var param = {
				"page.currentPage" : pageConfig.currentPageIndex,
				"page.pageSize" : pageConfig.pageSize,
				"oManaer" : pageConfig.currentSortManaer,
				"oName" : pageConfig.currentSortName
			};
			if (typeof(inparam) != undefined) {
				copyParam(param, inparam);
			}
			return param;
		},
		refreshGotoOption : function(length) {
			if (length <= 1) {
				$("select[name=" + pageConfig.pageIndexID + "] option")
						.remove();
				$("select[name=" + pageConfig.pageIndexID + "]")
						.append("<option value='" + 1 + "'>" + 1 + "</option>");
				return;
			}
			$("select[name=" + pageConfig.pageIndexID + "] option").remove();
			for (var i = 1; i <= length; i++) {
				$("select[name=" + pageConfig.pageIndexID + "]")
						.append("<option value='" + i + "'>" + i + "</option>");
			}
			$("select[name=" + pageConfig.pageIndexID + "]")
					.val(pageConfig.currentPageIndex);
		},
		changepagesize : function(pagesize) {
			pageConfig.pageSize = pagesize;
			pageConfig.ajax();
		}
	};
	pageConfig.url = url || pageConfig.url;
	pageConfig.callback = callback || pageConfig.callback;

	return pageConfig;
};

function ajaxCallback(url, param, callback) {
	var args = arguments;
	$.ajax({
		type : "post",
		dataType : "json",
		url : url,
		data : param,
		beforeSend : function() {
			$("body")
					.append("<div id='shadow' style='height: 100%;width: 100%;position: absolute;top: 0px;'>"
							+ "<img style='position: absolute;left: 50%;top: 50%;z-index: 999;' src='../skins/default/images/updating.gif'/></div>");
		},
		complete : function() {
			$("#shadow").hide();
		},
		success : function(msg) {
			$("#shadow").remove();
			if (args.length > 3) {
				eval((function() {
							var argsparam = "";
							for (var i = 3; i < args.length; i++) {
								argsparam += "args[" + i + "],";
							}
							argsparam = argsparam.substring(0, argsparam.length
											- 1);
							return callback.name + "(msg," + argsparam + ");";
						})());
			} else {
				callback(msg);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			window.top.location.href = '/';
		}
	});
}

var SELECT_ELEMENT = "#mytab > tbody input[type=checkbox]";
function selectedAll() {
	$(SELECT_ELEMENT).selectall();
}
function cancel() {
	$(SELECT_ELEMENT).cancel();
}
function deselect() {
	$(SELECT_ELEMENT).deselect();
}

// jquery 全选 反选 取消
$.fn.extend({
			selectall : function() {
				this.attr("checked", true);
			},
			deselect : function() {
				this.each(function(i, o) {
							o.checked = !o.checked;
						});
			},
			cancel : function() {
				this.attr("checked", false);
			}
		});
/**
 * 表格隔行变色
 */
$(function() {
			$('tr:nth-child(even)').addClass('alt');
			$('td:contains(Henry)').nextAll().andSelf().addClass('highlight');
			$('th').parent().addClass('table-heading');
		});
