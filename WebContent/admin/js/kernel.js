$(function() {
			$p = new page("kernel!jsonlist");
			$("#first").bind("click", $p.first);
			$("#previous").bind("click", $p.previous);
			$("#next").bind("click", $p.next);
			$("#last").bind("click", $p.last);
			$p.totalDataID = $("#totalData");
			$p.ajax();
		});

function refresh() {
	$p.ajax();
}
function edit() {
	var editVersion = $("#mytab input:checked").attr("value");
	if (typeof(editVersion) != "undefined") {
		showIPUBDialogStand("kernel!preedit?kernel.kernelVersion="
						+ editVersion, {
					width : 500,
					height : 330,
					title : langMap.kernelMap.editFirmware
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
				ajaxCallback("kernel!kernelUsed", $.param(param, true),
						function(result) {
							if (result.success) {
								removeKernel();
							} else {
								if (result.message != undefined) {
									confirm(result.message, removeKernel);
								} else {
									alert(langMap.kernelMap.firmwareFailed);
								}

							}
						});
			});
}

function removeKernel() {
	var param = {
		"versions" : []
	};
	$("#mytab input:checked").each(function(i, o) {
				param.versions.push(o.value);
			});
	ajaxCallback("kernel!remove", $.param(param, true), function(result) {
				if (result.success) {
					refresh();
				} else {
					if (result.message != undefined) {
						alert(result.message);
					} else {
						alert(langMap.kernelMap.removeFails);
					}
				}
			});
}

function upload() {
	showIPUBDialogStand("kernelupload.action", {
				width : 680,
				height : 540,
				title : langMap.kernelMap.uploadfirmware,
				close : function() {
					refresh();
				}
			});
}
function jupload() {
	showIPUBDialogStand("jkernelupload.action", {
				width : 680,
				height : 540,
				title : langMap.kernelMap.uploadfirmware,
				close : function() {
					refresh();
				}
			});
}
function showlist(result) {
	try {
		if (!result.success) {
			alert(result.message);
			return false;
		}
		var contextHTML = "";
		$(result.data).each(function(i, f) {
			contextHTML += "<tr>" + "<td style='width:30px'>"
					+ "<input type=\"checkbox\" value=\"" + f.kernelVersion
					+ "\">" + "</td>" + "<td style='width:170px'>"
					+ f.kernelVersion + "</td>" + "<td style='width:190px'>"
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
		$("#mytab tbody").html(contextHTML);
		ipubs.models.jscroll_ee();
	} catch (e) {
		alert(e.message);
	}
}
