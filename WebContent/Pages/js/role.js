$(function() {
			SELECT_ELEMENT = "#ee input";
			refreshData();
		});

var selectedID = -1;

function del() {
	var selectedRoles = $("#con_one_1 input:checked");
	if (selectedRoles.length == 0) {
		alert(langMap.roleMap.noSelectRoleError);
		return;
	}
	confirm(langMap.roleMap.sureToDeleteRole, function() {
				var data = {
					"privileges" : []
				};
				$("#con_one_1 input:checked").each(function(i, f) {
							data.privileges.push(f.value);
						});
				ajaxCallback("role!del", $.param(data, true), function(result) {
							if (result.success) {
								cancel();
								refreshData();
							} else {
								if (typeof(result.message) != "undefined") {
									alert(result.message);
								} else {
									alert(langMap.globalMap.deleteItemFail);
								}
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
		$("#one2").attr(
				"title",
				langMap.globalMap.total + result.data.length
						+ langMap.globalMap.number);
		$.each(result.data, function(i, f) {
			content += "<li i class='relative'>"
					+ "<div class='fenye2left-wapper'><input type='checkbox' value='"
					+ f.id
					+ "'/></div>"
					+ "<span onclick=\"editRole("
					+ f.id
					+ ",this)\" title=\""
					+ f.roleName
					+ "\" class='fenye2left'><img src=\"../skins/default/images/b18.png\" />"

					+ (f.roleName.length > 20
							? f.roleName.substring(0, 20)
							: f.roleName) + "</span>" + "</li>";
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

function add() {
	showIPUBDialogStand("role!preadd", {
				width : 450,
				height : 220,
				title : langMap.roleMap.createRole
			});
}
function refreshData() {
	ajaxCallback("role!list", {
				"az" : az
			}, showlist);
}
az = "asc";
function sortlist() {
	az = az == "desc" ? "asc" : "desc";
	refreshData();
}
function editRole(id, node) {
	$("#con_one_1 > li").removeClass("hover");
	$(node.parentNode).addClass("hover");
	selectedID = id;
	ajaxCallback("role!permissions", {
				"role.roleId" : id
			}, selectCheckbox);
}

function selectCheckbox(result) {
	$("#ee input[type=checkbox]").each(function(i, o) {
				$(o).attr("checked", false);
			});
	if (result.success) {
		$.each(result.data, function(i, f) {
					$("#ee input[type=checkbox][value=" + f.id + "]").attr(
							"checked", true);
				});
	}
}

function updateRole() {
	var param = {
		'role.roleId' : selectedID,
		"privileges" : []
	};
	$("#ee input:checked").each(function(i, f) {
				param.privileges.push(f.value);
			});
	ajaxCallback("role!updatePermissions", $.param(param, true), function(
					result) {
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

function update() {
	var selectedRoles = $("#con_one_1 input:checked");
	if (selectedRoles.length == 0) {
		alert(langMap.roleMap.noSelectRoleError);
		return;
	}
	showIPUBDialogStand("role!preeditattr?id=" + selectedRoles[0].value, {
				width : 450,
				height : 300,
				title : langMap.roleMap.editRole
			});
}
