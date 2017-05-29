/**
 * 页面加载时，绑定事件，以及一些初始化操作
 */
$(function() {
			$p = new page("app!jsonlist");
			$("#first").bind("click", $p.first);
			$("#previous").bind("click", $p.previous);
			$("#next").bind("click", $p.next);
			$("#last").bind("click", $p.last);
			$p.ajax();
		});

function refresh() {
	$p.ajax();
}
/**
 * ajax形式控制文件列表显示的数据
 * 
 * @param {}
 *            result
 */
function showlist(result) {
	try {
		if (!result.success) {
			alert(result.message);
			return false;
		}
		var contextdTML = "";
		$(result.data).each(function(i, f) {
			contextdTML += "<tr>" + "<td style='width:30px'>"
					+ "<input type=\"checkbox\" value=\"" + f.appVersion
					+ "\">" + "</td>" + "<td style='width:170px'>"
					+ f.appVersion + "</td>" + "<td style='width:190px'>"
					+ f.description + "</td>" + "<td style='width:39px'>"
					+ f.importUser + "</td>" + "<td style='width:136px'>"
					+ f.importTime + "</td>" + "<td style='width:53px'>"
					+ f.size + "</td>" + "</tr>";
		});
		var contextdead = "<td class=\"first\" style='width:230px'>"
				+ langMap.appMap.version + "</td><td style='width:190px'>"
				+ langMap.globalMap.description
				+ "</td><td style='width:39px'>" + langMap.appMap.upload
				+ "</td><td style='width:136px'>" + langMap.appMap.timestamp
				+ "</td><td style='width:53px'>" + langMap.appMap.size
				+ "</td>";
		$("#shujBt").html(contextdead);
		$("#mytab").html(contextdTML);
		ipubs.models.jscroll_ee();
	} catch (e) {
		alert(e.message);
	}
}

function edit() {
	var editVersion = $("#mytab input:checked").attr("value");
	if (typeof(editVersion) != "undefined") {
		showIPUBDialogStand("app!preedit?app.appVersion=" + editVersion, {
					width : 500,
					height : 330,
					title : langMap.appMap.editSoftware
				});
	} else {
		alert(langMap.globalMap.selectItemsError);
	}
}

function remove() {
	if ($("#mytab input:checked").length == 0) {
		alert(langMap.globalMap.selectItemsError);
		return;
	}
	confirm(langMap.globalMap.sureToDelete, function() {
				var param = {
					"versions" : []
				};
				$("#mytab input:checked").each(function(i, o) {
							param.versions.push(o.value);
						});

				ajaxCallback("app!appUsed", $.param(param, true), function(
								result) {
							if (result.success) {
								removeApp();
							} else {
								if (result.message != undefined) {
									confirm(result.message, removeApp);
								} else {
									alert(langMap.appMap.softwareFailed);
								}
							}
						});
			});
}

function removeApp() {
	var param = {
		"versions" : []
	};
	$("#mytab input:checked").each(function(i, o) {
				param.versions.push(o.value);
			});
	ajaxCallback("app!remove", $.param(param, true), function(result) {
				if (result.success) {
					refresh();
				} else {
					if (result.message != undefined) {
						alert(result.message);
					} else {
						alert(langMap.appMap.removeFails);
					}
				}
			});
}
function upload() {
	showIPUBDialogStand("appupload.action", {
				width : 680,
				height : 540,
				title : langMap.appMap.uploadSoftware,
				close : function() {
					refresh();
				}
			});
}
function jupload() {
	showIPUBDialogStand("jappupload.action", {
				width : 680,
				height : 540,
				title : langMap.appMap.uploadSoftware,
				close : function() {
					refresh();
				}
			});
}
