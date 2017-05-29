$(function() {
			refreshData();
		});

function del() {
	var selectedConfig = $("#con_one_1 input:checked");
	if (selectedConfig.length == 0) {
		alert(langMap.configMap.selectConfigsError);
		return;
	}
	if (confirm(langMap.globalMap.sureToDelete, function() {
				var data = {
					"configs" : []
				};
				$("#con_one_1 input:checked").each(function(i, f) {
							data.configs.push(f.value);
						});
				ajaxCallback("config!deleteBean", $.param(data, true),
						function(result) {
							if (!result.success) {
								alert(langMap.globalMap.deleteItemFail);
							}
							$(".niceform input").val("");
							refreshData();
						});
			}))
		;

}
function audit() {
	var $config = $("input[name=config]:checked");
	var param = {
		"configs" : []
	};
	$config.each(function(i, o) {
				if ($(o).attr("state") == "1") {
					param.configs.push(o.value);
				}
			});

	if ($config.length < 1) {
		alert(langMap.configMap.selectConfigsError);
		return;
	} else if (param.configs.length < 1) {
		return;
	}
	confirm(langMap.globalMap.sureToAudit, function() {
				ajaxCallback("config!checkbean", $.param(param, true),
						function() {
							refreshRemeber();
							refreshData();

						});
			});

}
var remeber = null;
Array.prototype.indexOf = function(item) {
	for (var i = 0; i < this.length; i++) {
		if (this[i] == item)
			return i;
	}
	return -1;
};

function refreshRemeber() {
	remeber = new Array();
	$("input[name=config]:checked").each(function() {
				remeber.push(parseInt(this.value));
			});
}
highLight = -1;
function showlist(result) {
	try {
		highLight = parseInt($($(".relative&.hover input")).val());
		var content = "";
		if (result.data == null || result.data == undefined) {
			return false;
		}
		$("#one1").attr(
				"title",
				langMap.configMap.total + result.data.length
						+ langMap.configMap.number);

		$.each(result.data, function(i, f) {
			var checked = (remeber != null && remeber.indexOf(f.configId) != -1
					? true
					: false);
			content += "<li  class='relative "
					+ (highLight == f.configId ? "hover" : " ")
					+ "' title=\""
					+ f.configName
					+ " "
					+ langMap.configMap.version
					+ ""
					+ f.version
					+ langMap.configMap.status
					+ (f.modifyState == 0
							? langMap.globalMap.audited
							: langMap.globalMap.unAudited)
					+ "\">"
					+ "<div class='fenye2left-wapper'><input type='checkbox' state=\""
					+ f.modifyState
					+ "\" name='config'  class='checkbox' "
					+ (checked ? "checked" : "")
					+ " value=\""
					+ f.configId
					+ "\"/></div>"
					+ "<span onclick=\"editConfig("
					+ f.configId
					+ ",this)\" class='fenye2left'>"
					+ (f.modifyState == 0
							? "<img src=\"../skins/default/images/b15.png\" />"
							: "<img src=\"../skins/default/images/b16.png\" />")

					+ (f.modifyState == 0
							? (f.configName.length > 20 ? f.configName
									.substring(0, 20) : f.configName)
							: ("<font style=\"color:red\">"
									+ (f.configName.length > 20 ? f.configName
											.substring(0, 20) : f.configName) + "</font>"))
					+ "</span>" + "</li>";
		});

		$("#con_one_1").html(content);

		$('.jqtransform').jqTransform({
					imgPath : 'skins/default/images/'
				});

		duma.bind(window, "load", duma.BeautifyScrollBar.init);

		if (($("#con_one_1 input:first")).attr("name") != undefined && !remeber) {
			$($(".relative>span")[0]).trigger("click");
		}
	} catch (e) {
	}
}

function currentConfigID() {
	return $("#con_one_1 > li&.hover input[type=checkbox]").val();
}
function editConfig(id, node) {
	$("#con_one_1 > li").removeClass("hover");
	$(node.parentNode).addClass("hover");
	ajaxCallback("config!loadbean", {
				"config.configId" : id
			}, showBean);
}

function add() {

	showIPUBDialogStand("config!preadd", {
				width : 500,
				height : 240,
				title : langMap.configMap.newConfig
			});
}
var az = "desc";
var sortfield = "name";
function refreshData() {
	ajaxCallback("config!list", {
				"az" : az
			}, showlist);
}

function sortlist() {
	az = az == "asc" ? "desc" : "asc";
	ajaxCallback("config!list", {
				"az" : az,
				"oName" : sortfield
			}, showlist);
}
function editBean(id) {
	var result = window
			.showModalDialog("config!preeditbean?id=" + id, window,
					"dialogWidth=1000px;dialogHeight=600px;status:no;resizable:yes;center:yes");
	if (result == null || result == undefined) {
		return;
	}
	if (result) {
		refreshRemeber();
		refreshData();
	}
}

var currentBean = null;
var xml = null;
function showBean(result) {
	if (!result.success) {
		alert(result.message);
		return false;
	}
	currentBean = result.data;
	var xmlText = result.data.todoXml;

	if (typeof xmlText == "string") {
		try {
			xml = new ActiveXObject("Microsoft.XMLDOM");
			xml.async = false;
			xml.loadXML(xmlText);
		} catch (e) {
			xml = xmlText;
		}

	} else {
		xml = xmlText;
	}
	$(".peizTb input[type=text]").val("");
	$.each($(xml).find("item"), function(i, o) {
				$("input[name=" + $(o).attr("name") + "]").val($(o)
						.attr("value"));
			});

}

function setItem(item) {
	var childItem = $(item).find("item");
	var name = $(item).attr("name");
	if (childItem.length == 0) {

		var textValue = $("input[name=" + name + "]").val();
		return (new Function("return {\"" + name + "\":\"" + textValue + "\"}"))();
	} else {
		var childParam = {};
		$(childItem).each(function(i, o) {

					copyParam(childParam, setItem(o));
				});
		return childParam;
	}
	return;
}

function saveBean() {
	var paramTODO = {};
	$(".peizTb input").each(function(i, o) {
				copyParam(paramTODO, setItem(o));
			});
	var param = {
		"config.configId" : currentConfigID()
	};
	ajaxCallback("config!saveBean", copyParam(param, paramTODO), function(
					result) {
				if (result.success) {
					alert(langMap.configMap.saveBeanSuccess);
					refreshRemeber();
					refreshData();
				} else {
					alert(langMap.configMap.saveBeanFailed);
				}
			});
}

function update() {
	var selectedConfig = $("#con_one_1 input:checked");
	if (selectedConfig.length == 0) {
		alert(langMap.configMap.selectConfigsError);
		return;
	}
	showIPUBDialogStand("config!preedit?id=" + selectedConfig[0].value, {
				width : 550,
				height : 350,
				title : langMap.configMap.editConfig
			});
}
