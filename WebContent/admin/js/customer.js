$(function() {

			$p = new page("customer!list");
			$("#first").bind("click", $p.first);
			$("#previous").bind("click", $p.previous);
			$("#next").bind("click", $p.next);
			$("#last").bind("click", $p.last);
			$p.ajax();

		});

function del() {
	if ($("#mytab tbody input:checked").length == 0) {
		alert(langMap.globalMap.selectItemsError);
		return;
	}
	confirm(langMap.globalMap.sureToDelete, function() {
				var data = {
					"customers" : []
				};
				$("#mytab tbody input:checked").each(function(i, f) {
							data.customers.push(f.value);
						});
				ajaxCallback("customer!remove", $.param(data, true), function(
								result) {
							if (result.success) {
								$p.ajax();
							}
						});

			});
}
var nameManaer = descManaer = shortNameManaer = "asc";
function sortName() {
	$p.currentSortManaer = nameManaer = nameManaer == "desc" ? "asc" : "desc";
	$p.currentSortName = "name";
	refresh();
}

function sortDesc() {

	$p.currentSortManaer = descManaer = descManaer == "desc" ? "asc" : "desc";
	$p.currentSortName = "DESCP";
	refresh();
}

function sortShortName() {
	$p.currentSortManaer = shortNameManaer = shortNameManaer == "desc"? "asc" : "desc";
	$p.currentSortName = "SHORT_NAME";
	refresh();
}

function sortDeviceCount() {
	$p.currentSortManaer = shortNameManaer = shortNameManaer == "desc"? "asc" : "desc";
	$p.currentSortName = "DEV_COUNT";
	refresh();
}

function showlist(result) {
	try {
		$("#mytab tbody").html("");
		var content = "";
		if (result.data == null || result.data == undefined) {
			return false;
		}
		$.each(result.data, function(i, f) {
			content += "<tr id=\"" + f.cstmId + "\">"
					+ "<td style='width:30px'>" 
					+ "<input type=\"checkbox\" name=\"checkbox\" value=\"" + f.cstmId + "\" />" 
					+ "</td>" 
					+ "<td style='width:170px;padding-left:0px;'>" + f.cstmName + "</td>" 
					+ "<td style='width:200px'>" + f.description + "</td>" 
					+ "<td style='width:180px'>" + f.shortName + "</td>" 
					+ "<td style='width:99px'>" + f.deviceCount + "</td>"
					+ "</tr>";

		});
		var contextdead = "<td class=\"first\" onclick='sortName()'  style='width:200px;cursor: pointer'>"
				+ langMap.customMap.corporationName + "</td>" 
				+ "<td  onclick='sortDesc()' style='width:200px;cursor: pointer'>"
				+ langMap.globalMap.description + "</td>" 
				+ "<td  onclick='sortShortName()' style='width:180px;cursor: pointer'>"
				+ langMap.customMap.coporationID + "</td>" 
				+ "<td  onclick='sortDeviceCount()' style='width:99px;cursor: pointer'>"
				+ langMap.customMap.deviceCount + "</td>";
		$("#shujBt").html(contextdead);
		$("#mytab tbody").html(content);
		ipubs.models.jscroll_ee();
	} catch (e) {
		alert(e.message);
	}
}
function add() {
	showIPUBDialogStand("customer!preadd", {
				width : 400,
				height : 250,
				title : langMap.customMap.createCorporation
			});
}

function edit() {
	var editId = $("#mytab > tbody input:checked").attr("value");
	if (typeof(editId) != "undefined") {
		showIPUBDialogStand("customer!preedit?customer.cstmId="
						+ editId, {
					width : 500,
					height : 280,
					title :""
				});
	} else {
		alert(langMap.globalMap.selectItemsError);
	}

}

function update() {
	if ($("#mytab > tbody input:checked").length == 0) {
		alert(langMap.globalMap.selectItemsError);
		return false;
	}

	confirm(langMap.customMap.resetPassword, function() {
				$.each($("#mytab > tbody input:checked"), function(i, o) {
							ajaxCallback("customer!reset", {
										"customer.cstmId" : o.value
									}, function(result) {
										if (result != undefined
												&& result.success != undefined
												&& !result.success) {
											alert(result.message);
										}
									});
						});
			});

}

function refresh() {
	$p.ajax();
}
