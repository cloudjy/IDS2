$(function() {

			$p = new page("dust!list");
			$("#first").bind("click", $p.first);
			$("#previous").bind("click", $p.previous);
			$("#next").bind("click", $p.next);
			$("#last").bind("click", $p.last);
			$("#searchFile").bind("click", refresh);
			refresh();

		});

function showlist(result) {
	try {
		if (!result.success) {
			alert(result.message != undefined
					? result.message
					: langMap.dustMap.queryFail);
			return false;
		}
		var contextHTML = "";
		var eeStrut = "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"shujTb\" id=\"mytab\"><thead></thead><tbody></tbody></table>";
		$("#ee").html(eeStrut);
		$(result.data).each(function(i, f) {
			contextHTML += "<tr>" + "<td style='width:70px'>"
					+ "<input type=\"checkbox\" cstmid=\"" + f.cstmId
					+ "\" fileid=\"" + f.fileId + "\">" + "</td>"
					+ "<td  style='width:500px'>" + f.fileName + "</td>"
					+ "<td >" + f.size + "</td>" + "</tr>";
		});

		$("#mytab tbody").html(contextHTML);

		ipubs.models.jscroll_ee();
	} catch (e) {
		alert(e.message);
	}
	return false;
}

function refresh() {
	$p.param = {
		"file.type" : $("#type").val(),
		"file.fileName" : $("#search").val()
	};
	$p.ajax();
}
function reduction() {
	var param = {
		"dusts" : []
	};
	$("#mytab input:checked").each(function(i, o) {
				var file = {
					"cstmid" : $(o).attr("cstmid"),
					"fileid" : $(o).attr("fileid")
				};
				param.dusts.push($.param(file, false));
			});
	if (param.dusts.length > 0) {
		ajaxCallback("dust!reduction", $.param(param, true), function(result) {
					if (result.success) {
						refresh();
					} else {
						if (result.message) {
							alert(result.message);
						} else if (result.data) {
							alert(langMap.dustMap.fileResotreFail);
						}

					}
				});
	} else {
		alert(langMap.globalMap.selectItemsError);
	}
}
function remove() {
	var param = {
		"dusts" : []
	};
	$("#mytab input:checked").each(function(i, o) {
				var file = {
					"cstmid" : $(o).attr("cstmid"),
					"fileid" : $(o).attr("fileid")
				};
				param.dusts.push($.param(file, false));
			});
	if (param.dusts.length > 0) {
		confirm(langMap.globalMap.sureToDelete, function() {
					ajaxCallback("dust!remove", $.param(param, true), function(
									result) {
								if (result.success) {
									refresh();
								}
							});
				});
	} else {
		alert(langMap.globalMap.selectItemsError);
	}

}
