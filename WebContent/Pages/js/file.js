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
var view = viewtype.view;

$(function() {

			$p = page("file!query", globeParam(), showlist);

			$.fn.zTree.init($("#treefile"), setting, null);

			$("#type").bind("change", querychangeclear);
			$("#filecheckstate").bind("change", querychangeclear);
			$("#vender").bind("change", querychangeclear);
			$("#includeChildren").bind("change", queryfiles);
			$("#searchFile").bind("click", queryfiles);

			loadRoot();

			$("#audit").bind("click", audit);
			$("#edit").bind("click", edit);
			$("#download").bind("click", download);
			$("#del").bind("click", del);
			$("#check").bind("click", check);

			$("#addNode").bind("click", addNode);
			$("#editNode").bind("click", editNode);
			$("#removeNode").bind("click", removeNode);

			$("#namesort").bind("click", namesort);
			$("#checktimesort").bind("click", checktimesort);
			$("#sizesort").bind("click", sizesort);
			$("#titlecheckstate").bind("click", titleCheckStateSort);

			$("#fileupload").bind("click", fileUpload);
			$("#jfileupload").bind("click", jfileUpload);
			$("#mms").bind("click", mms);

			$("#first").bind("click", $p.first);
			$("#previous").bind("click", $p.previous);
			$("#next").bind("click", $p.next);
			$("#last").bind("click", $p.last);

		});

function querychangeclear() {
	$("#search").val("");
	queryfiles();
}
function loadRoot() {
	selectedId = 0;
	$("#uploadcateid").val(selectedId);
	var treeObj = $.fn.zTree.getZTreeObj("treefile");
	var nodes = treeObj.getNodes();
	if (nodes.length > 0) {
		treeObj.selectNode({
					id : -1
				});
	}
	querychangeclear();

}
var setting = {
	async : {
		enable : true,
		url : "filecate!tree?az=" + az,
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
		enable : false
	},
	view : {
		dblClickExpand : false,
		selectedMulti : false
	},
	callback : {
		onClick : onClick,
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

function fileUpload() {
	var url = "file!preuploadfile?categoryId=" + selectedId + "&autoCheck="
			+ ($("#autocheck").attr("checked") == "checked" ? true : false);
	showIPUBDialogStand(url, {
				width : 680,
				height : 540,
				title : langMap.fileMap.fileUpload,
				close : function() {
					queryfiles();
				}
			});

}
function jfileUpload() {
	var url = "file!prejuploadfile?categoryId=" + selectedId + "&autoCheck="
			+ ($("#autocheck").attr("checked") == "checked" ? true : false);
	showIPUBDialogStand(url, {
				width : 680,
				height : 540,
				title : langMap.fileMap.fileUpload,
				close : function() {
					queryfiles();
				}
			});

}
function mms() {
	var url = "file!premms?categoryId=" + selectedId + "&autoCheck="
			+ ($("#autocheck").attr("checked") == "checked" ? true : false);
	showIPUBDialogStand(url, {
				width : 680,
				height : 240,
				title : "MMS",
				close : function() {
					queryfiles();
				}
			});

}

function onDblClick(e, treeId, treeNode) {
	selectedId = treeNode.id;
	selectedtreeNode = treeNode;
	$("#uploadcateid").val(selectedId);
	$p.currentPageIndex = 1;
	queryfiles();
}

function refreshFileAndVender() {
	ajaxCallback("file!venders", null, function(result) {
				showvender(result);
				queryfiles();
			});
}

function onClick(e, treeId, treeNode) {

	selectedId = treeNode.id;
	selectedtreeNode = treeNode;
	$("#uploadcateid").val(selectedId);
	$p.currentPageIndex = 1;
	querychangeclear();

	var zTree = $.fn.zTree.getZTreeObj("treefile");
	zTree.expandNode(treeNode);
}
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

function audit() {
	if ($("input[name=selectfile]:checked").length < 1) {
		alert(langMap.fileMap.selectFileError);
		return;
	}
	confirm(langMap.globalMap.sureToAudit, function() {
				var param = {
					"filelist" : []
				};
				$("input[name=selectfile]:checked").each(function(i, f) {
							param.filelist.push(f.value);
						});
				if (param.filelist.length > 0) {
					ajaxCallback("file!auditfile", $.param(param, true),
							function(result) {
								if (result.success) {
									queryfiles();
								} else if (result.message) {
									alert(result.message);
								}
							});
				}
			});
}
function rechange() {
	$p.currentPageIndex = 1;
	queryfiles();
}
function del() {

	if ($("input[name=selectfile]:checked").length < 1) {
		alert(langMap.fileMap.selectFileError);
		return;
	}
	confirm(langMap.globalMap.sureToDeleteFile, function() {
				var param = {
					"filelist" : []
				};
				$("input[name=selectfile]:checked").each(function(i, o) {
							param.filelist.push(o.value);
						});
				ajaxCallback("file!fileUsed", $.param(param, true), function(
								result) {
							if (result.success != undefined) {
								if (result.success) {
									ajaxCallback("file!remove", $.param(param,
													true), queryfiles);
								} else {
									if (result.message != undefined) {
										confirm(result.message, function() {
													ajaxCallback(
															"file!remove",
															$
																	.param(
																			param,
																			true),
															queryfiles);
												});
									}
								}
							}
						});
			});
}

function removeNode() {
	if (selectedId == 0 || selectedId == undefined) {
		alert(langMap.fileMap.deleteRootError);
		return;
	}

	confirm(langMap.globalMap.sureToDeleteTree, function() {
				ajaxCallback("filecate!remove", {
							"cate.categoryId" : selectedtreeNode.id
						}, removedNode);
			});
}

function edit() {
	var params = {
		"filelist" : []
	};
	$("input[name=selectfile]:checked").each(function() {
				params.filelist.push(this.value);
			});

	if (params.filelist.length > 0) {
		if (params.filelist.length == 1) {
			showIPUBDialogStand("file!preedit?" + $.param(copyParam({
										"currentCateId" : selectedId
									}, params), true) + "", {
						title : langMap.fileMap.editFile,
						width : 680,
						height : 460
					});
		} else {
			showIPUBDialogStand("file!preedit?" + $.param(copyParam({
										"currentCateId" : selectedId
									}, params), true) + "", {
						title : langMap.fileMap.editFile,
						width : 450,
						height : 220
					});
		}

	} else {
		alert(langMap.fileMap.selectFileError);
	}

}
function download() {
	if ($("input[name=selectfile]:checked").length < 1) {
		alert(langMap.fileMap.selectFileError);
		return;
	}
	$("input[name=selectfile]:checked").each(function() {
				location.href = 'file!download?file.fileId=' + this.value;
			});
}
function check() {
	var param = {
		"filelist" : []
	};
	$("input[name=selectfile]:checked").each(function() {
				param.filelist.push(this.value);
			});
	if ($("input[name=selectfile]:checked").length < 1) {
		alert(langMap.fileMap.selectFileError);
		return;
	}
	if (param.filelist.length > 0) {
		ajaxCallback('file!checkfile', $.param(param, true), function(txt) {
					if (txt.success) {
						alert(txt.data);
					} else {
						if (txt.message != null) {
							alert(txt.message);
						}
					}
				});
	}
}

function addNode() {
	var id = selectedId;
	if (id == undefined) {
		id = 0;
	}
	showIPUBDialogStand("filecate!pre_add?id=" + id, {
				title : langMap.fileMap.createGroup,
				width : 400,
				height : 190
			});
}
function addNodeed(result) {
	try {
		var id = selectedId;
		if (id == undefined) {
			id = 0;
		}
		var zTree = $.fn.zTree.getZTreeObj("treefile");
		zTree.addNodes(id == 0 ? 0 : zTree.getNodeByParam("id", id, null), {
					id : result.categoryId,
					pId : result.parentId,
					name : result.categoryName
				});

		return true;
	} catch (e) {
	}
}

function editNode() {
	if (selectedId == 0) {
		alert(langMap.fileMap.selectEditGroup);
		return;
	}
	showIPUBDialogStand("filecate!pre_edit?id=" + selectedId, {
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
	var isdelete = false;
	var zTree = $.fn.zTree.getZTreeObj("treefile");
	var selectedtreeNode = zTree.getSelectedNodes();

	if (selectedtreeNode[0].parentId != result.parentId) {
		if (result.parentId != 0) {
			if (zTree.getNodeByParam("id", result.parentId) != null) {
				if (zTree.getNodeByParam("id", result.parentId).open == false) {
					zTree.moveNode(zTree.getNodeByParam("id", result.parentId),
							selectedtreeNode[0], "inner", false);
					// zTree.expandNode(zTree.getNodeByParam("id",result.parentId),
					// true);
					// zTree.removeNode(selectedtreeNode[0]);
					isdelete = true;
				} else {
					zTree.moveNode(zTree.getNodeByParam("id", result.parentId),
							selectedtreeNode[0], "inner", false);
				}

			} else {
				zTree.removeNode(selectedtreeNode[0]);
				isdelete = true;
			}
		} else {
			zTree.moveNode(null, selectedtreeNode[0], "inner", false);
		}
	}
	if (!isdelete) {
		selectedtreeNode[0].name = result.categoryName;
		selectedtreeNode[0].parentId = result.parentId;

		zTree.updateNode(selectedtreeNode[0]);
	}

	refreshVender();
	queryfiles();
}
function refreshVender() {
	ajaxCallback("file!venders", null, showvender);
}

function removedNode(result) {
	if (!result.success) {
		if (result.message != undefined) {
			alert(result.message);
		}
		return;
	}
	var zTree = $.fn.zTree.getZTreeObj("treefile");
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
}
function changeview(viewparam) {
	view = viewparam;
	queryfiles();
}

function previewFile(id) {
	showIPUBDialogStand("file!preview?file.fileId=" + id, {
				width : document.body.clientWidth,
				height : document.body.clientHeight,
				title : langMap.fileMap.filePreview
			});

}

function categorysort() {
	az = !az;
	$("#category_sort").html(az? "A-Z" : "Z-A");
	var setting = {
		async : {
			enable : true,
			url : "filecate!tree?az=" + az,
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
			enable : false
		},
		view : {
			dblClickExpand : false,
			selectedMulti : false
		},
		callback : {
			onClick : onClick,
			onAsyncSuccess : zTreeOnAsyncSuccess
		}
	};
	$.fn.zTree.init($("#treefile"), setting, null);
	queryfiles();
}

function showviewfunc(result) {
	try {
		SELECT_ELEMENT = "#ee input";
		var content = "";
		content += "<div><ul class=\"picLb\" style=\"overflow:hidden\">";
		$.each(result.data, function(i, f) {
			
					var divHtml = "<div style=\"position:relative\">" +
							"<div style=\"position:absolute;left:0;top:0\">" +
							"<img src=\"file!showview?file.fileId="+ f.fileId + "\"/>" +
							"</div>" +
							"<div style=\"position:absolute;left:0;top:0;\">"  +
							(f.checkState != 1? 
									"<img " +
									// "style=\"height:100%; width:100%;\" " +
									" src=\"/skins/default/images/btn4.png\"" +
									"/>" : "") + 
							"</div>" +
 							"</div>";
			
					var checked = $("input[name=selectfile][value=" + f.fileId
							+ "]").attr("checked");
					var checkedvalue = checked ? "checked" : "";
					var filename = (f.fileName.length > 6 ? f.fileName.substr(
							0, 6)
							+ "..." : f.fileName);
					content += "<li>";
					/* content += (f.flag != 2 && f.type != 2
							? "<a href=\"file!preview?file.fileId=" + f.fileId
									+ "\" target='_blank'><div>"
									+ "<img src=\"file!showview?file.fileId="
									+ f.fileId + "\"  />" + "</div></a>"
							: "<div><img src=\"file!showview?file.fileId="
									+ f.fileId + "\"  /></div>"); */

					content += (f.flag != 2 && f.type != 2? 
							"<a href=\"file!preview?file.fileId=" + f.fileId + "\" target='_blank'>" 
									 + divHtml + "</a>"
							: divHtml); 

					content += "<input type=\"checkbox\" name=\"selectfile\" id=\""
							+ f.fileId
							+ "\" value=\""
							+ f.fileId
							+ "\" "
							+ checkedvalue + " />";
					content += "<span title=\"" + f.fileName + "\" " 
							+ (f.checkState != 1? "style=\"color: #FF0000\" " : "")
							+ ">" + filename + "</span>"; 
					content += "</li>";

				});
		content += "</ul></div>";
		$("#mytab").remove();
		$("#shujBt").remove();

		$("#ee_bottom_button").css("margin-top", "35px"); 
		$("#ee").html(content);
		ipubs.models.jscroll_ee();
	} catch (e) {
		alert(e.message);
	}
}
/**
 * ShiftKey 定义的全局变量
 * 
 * var reIni = false; var iniKey = 0; var flagMuti = false;
 * 
 */
function getState(checkState) {
	if (checkState == 1) {
		return langMap.globalMap.audited;
	} else {
		return langMap.globalMap.unAudited;
	}
}

function showlistfunc(result) {
	try {
		SELECT_ELEMENT = "#mytab > tbody input[type=checkbox]";

		var content = "";
		var thead = "<tr>"
				+ "<td id=\"namesort\" onclick='namesort()'  class=\"first\" style=\"cursor: pointer; width:230px;\" >"
				+ langMap.fileMap.fileName
				+ "</td>"
				+ "<td  id=\"sizesort\" onclick='sizesort()'  style=\"cursor: pointer;width:80px;\">"
				+ langMap.fileMap.fileSize
				+ "</td>"
				+ "<td  id=\"checktimesort\" onclick='checktimesort()'  style=\"cursor: pointer;width:175px;\">"
				+ langMap.fileMap.auditTime
				+ "</td>"
				+ "<td  id=\"titlecheckstate\" onclick='titleCheckStateSort()'  style=\"cursor: pointer;width:70px;white-space:nowrap;line-height: normal;\">"
				+ langMap.fileMap.auditState + "</td>"
				+ "<td style=\"width:67px;\">" + langMap.fileMap.preview
				+ "</td>" + "</tr>";
		$.each(result.data, function(i, f) {
			var checked = $("input[name=selectfile][value=" + f.fileId + "]")
					.attr("checked");
			var checkedvalue = checked ? "checked" : "";
			content += "<tr>"
					+ "<td style=\"width:12px;\"><input type=\"checkbox\" name=\"selectfile\" value=\""
					+ f.fileId
					+ "\" "
					+ checkedvalue
					+ " /></td>"
					+ "<td style=\"width:180px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;" 
					+ (f.checkState != 1? "color: #FF0000;" : "") + "\" " 
					+ " title=\"" + f.fileName + "\">"
					+ f.fileName
					+ "</td>"
					+ "<td style=\"width:80px;\">"
					+ (Math.round((f.size / 1024) * 100) / 100)
					+ "</td>"
					+ "<td style=\"word-break: keep-all; white-space:nowrap;width:175px;\"  class=\"center\">"
					+ f.checkTime
					+ "</td>"
					+ "<td style=\"width:58px;\" class=\"center\" title=\""
					+ getState(f.checkState)
					+ "\"><img src=\"../"
					+ (f.checkState == 1
							? "skins/default/images/btn1.png"
							: "skins/default/images/btn4.png")
					+ "\" width=\"18\" height=\"18\" /></td>"
					+ "<td style=\"width:67px;\" class=\"center\">"
					+ (f.flag != 2 && f.type != 2
							? "<a href=\"file!preview?file.fileId="
									+ f.fileId
									+ "\" target='_blank'><img src=\"../skins/default/images/pictures_folder.png\" width=\"18\" height=\"18\"></a>"
							: "<img src=\"../skins/default/images/pictures_folder.png\" width=\"18\" height=\"18\">")
					+ "</td>" + " </tr>";

		});
		$("#shujBt").remove(); 
		$("#ee_bottom_button").css("margin-top", ""); 
		$("#ee").html("");
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

function checktimesort() {
	checktimeflag = (checktimeflag == "asc" ? "desc" : "asc");
	sortfield(checktimeflag, "check_time");
}
function titleCheckStateSort() {
	titlecheckstate = (titlecheckstate == "asc" ? "desc" : "asc");
	sortfield(titlecheckstate, "check_state");
}
function sizesort() {
	sizeflag = (sizeflag == "asc" ? "desc" : "asc");
	sortfield(sizeflag, "size");
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
	var param = {
		"fileparam.categoryId" : selectedId,
		"fileparam.fileType" : $("#type").val(),
		"fileparam.fileVendor" : $("#vender").val(),
		"fileparam.searchKey" : $("#search").val(),
		"fileparam.checkState" : $("#filecheckstate").val(),
		"fileparam.includeChildren" : $("#includeChildren").attr("checked") == "checked"
				? "true"
				: "false"

	};
	if (typeof(inparam) != undefined) {
		copyParam(param, inparam);
	}
	return param;
}
