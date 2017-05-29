$(function() {
			refreshData();
		});

var selectedID = -1;

function del() {
	if ($("#con_one_1 input:checked").length == 0) {
		alert(langMap.userMap.noSelectUserError);
		return;
	}
	confirm(langMap.userMap.sureToDeleteUser, function() {
				var data = {
					"userid" : []
				};
				$("#con_one_1 input:checked").each(function(i, f) {
							data.userid.push(f.value);
						});
				ajaxCallback("user!del", $.param(data, true), function(result) {
							if (result.success) {
								cancel();
								refreshData();
							} else {
								alert(langMap.globalMap.deleteItemFail);
							}
						});
			});
}

function showlist(result) {
	try {
		var content = "";
		if (result.data == null || result.data == undefined) {
			return;
		}
		$("#one1").attr(
				"title",
				langMap.globalMap.total + result.data.length
						+ langMap.globalMap.number);
		$.each(result.data, function(i, f) {
			content += "<li class='relative'>"
					+ "<div class='fenye2left-wapper'><input type='checkbox'  class='checkbox' value='"
					+ f.id
					+ "'/></div>"
					+ "<span onclick=\"editUser("
					+ f.id
					+ ",this)\"  title=\""
					+ f.userName
					+ "\" class='fenye2left'><img src=\"../skins/default/images/b02.png\" />"

					+ (f.userName.length > 20
							? f.userName.substring(0, 20)
							: f.userName) + "</span>" + "</li>";

		});
		$("#con_one_1").html(content);
		$('form').jqTransform({
					imgPath : 'skins/default/images/'
				});
		defaultSelected();

		duma.bind(window, "load", duma.BeautifyScrollBar.init);

	} catch (e) {
		alert(e.message);
	}
}

function defaultSelected() {
	if ($(".hover&.relative").length == 0) {
		$($(".relative")[0]).find("span").click();
	}
}

var az = "asc";
function refreshData() {
	ajaxCallback("user!list", {
				"az" : az
			}, showlist);
}

function sortlist() {
	az = az == "desc" ? "asc" : "desc";
	refreshData();
}

function showrolelist(result) {
	if (result.success) {
		var html = "";
		$(result.data).each(function(i, f) {
			html += "<tr>";
			html += "<td style='width:200px'>" + f.roleName + "</td>";
			html += "<td style='width:200px'>" + f.description + "</td>";
			html += "<td style='width:200px'>";
			html += "<input type = 'checkbox' " + ((f.role) ? " checked " : "")
					+ "value=\"" + f.id + "\"/>";
			html += "</td>";
			html += "</tr>";
		});
		$("#mytab tbody").html(html);
		ipubs.models.jscroll_ee();
	}

}

function add() {
	showIPUBDialogStand("user!preadd", {
				width : 400,
				height : 320,
				title : langMap.userMap.createUser
			});
}
function editUser(id, node) {
	$("#con_one_1 > li").removeClass("hover");
	$(node.parentNode).addClass("hover");
	selectedID = id;
	ajaxCallback("user!roleList", {
				"user.userId" : id
			}, showrolelist);
}
function update() {
	if ($("#con_one_1 input:checked").length == 0) {
		alert(langMap.userMap.noSelectUserError);
		return;
	}
	showIPUBDialogStand("user!preeditattr?id="
					+ $("#con_one_1 input:checked")[0].value, {
				width : 450,
				height : 320,
				title : langMap.userMap.editUser
			});
}

function updateRole() {
	var param = {
		'user.userId' : selectedID,
		"roles" : []
	};
	$("#mytab tbody input:checked").each(function(i, f) {
				param.roles.push(f.value);
			});
	ajaxCallback("user!updateRole", $.param(param, true), function(result) {
				if (result.success) {
					alert(langMap.globalMap.updateSuccess);
				} else {
					if (result.message != undefined) {
						alert(result.message);
					} else {
						alert(langMap.globalMap.updateFail);
					}
				}

			});
}
