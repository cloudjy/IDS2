tree = function(_option) {
	if (_option == null || _option.HTMLID == undefined
			|| _option.AJAX_URL == undefined) {
		return;
	}
	this.HTMLID = _option.HTMLID;
	this.AJAX_URL = _option.AJAX_URL;
	this.TREE = function() {
		return $.fn.zTree.getZTreeObj(this.HTMLID);
	};
	this.selectTree = function() {
		var zTree = $.fn.zTree.getZTreeObj(this.HTMLID);
		var selectedtreeNode = zTree.getSelectedNodes();
		if (selectedtreeNode == undefined || selectedtreeNode.length == 0) {
			return {};
		} else {
			return selectedtreeNode[0];
		}
	};
	this.reloadTree = function() {
		var setting = {
			async : {
				enable : true,
				url : this.AJAX_URL,
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
				onClick : _option.treeClick || this.treeClick,
				onAsyncSuccess : zTreeOnAsyncSuccess
			}
		};
		$.fn.zTree.init($("#" + this.HTMLID), setting, null);
	};

	this.selectedID = function() {
		zTree = $.fn.zTree.getZTreeObj(this.HTMLID);
		return zTree.getSelectedNodes()[0];
	};
	this.treeClick = function(e, treeId, treeNode) {
		alert("默认点击事件，请重写treeClick");
	};
	this.addNode = function(url, option) {
		if (url == undefined || option == undefined) {
			return;
		}
		showIPUBDialogStand(url, option);
	};
	this.addNoded = function(result) {
		try {
			zTreeTemp = $.fn.zTree.getZTreeObj(this.HTMLID);
			selectedtreeNode = zTreeTemp.getSelectedNodes();
			if (selectedtreeNode == undefined || selectedtreeNode.length == 0) {
				id = 0;
			} else {
				if (selectedtreeNode[0] != undefined
						&& selectedtreeNode[0].id != undefined)
					id = selectedtreeNode[0].id;
			}

			zTreeTemp.addNodes(id == 0 ? 0 : zTreeTemp.getNodeByParam("id", id,
							null), {
						id : result.categoryId,
						pId : id,
						name : result.categoryName
					});
			return true;
		} catch (e) {
		}
	};
	this.editNode = function(url, option) {
		if (url == undefined || option == undefined) {
			return;
		}
		showIPUBDialogStand(url, option);
	};
	this.editNoded = function(result, executed) {
		if (result == "" || result == undefined) {
			return;
		}
		if (result.categoryName == undefined && result.parentId == undefined) {
			return;
		}
		var zTree = $.fn.zTree.getZTreeObj(this.HTMLID);
		var selectedtreeNode = zTree.getSelectedNodes();
		selectedtreeNode[0].name = result.categoryName;
		selectedtreeNode[0].parentId = result.parentId;
		zTree.moveNode(zTree.getNodeByParam("id", result.parentId),
				selectedtreeNode[0], "inner", false);
		zTree.updateNode(selectedtreeNode[0]);
		if (executed) {
			executed();
		}

	};
	this.removeNode = function(_option) {
		if (_option == undefined || _option.url == undefined) {
			alert("没有URL");
			return;
		}
		if (selectedId == 0 || selectedId == undefined) {
			alert(langMap.fileMap.deleteRootError);
			return;
		}
		confirm(langMap.globalMap.sureToDelete, function() {
					ajaxCallback(_option.url, _option.param, _option.callback
									|| tree.removeNoded);
				});
	};
	this.removeNoded = function(result) {
		if (result == undefined) {
			return;
		}
		if (!result.success) {
			if (result.message != undefined) {
				alert(result.message);
			}
			return;
		}
		zTree = $.fn.zTree.getZTreeObj(this.HTMLID);
		selectedtreeNode = zTree.getSelectedNodes();
		zTree.removeNode(selectedtreeNode);
	};
};