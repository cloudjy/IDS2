var IPUB = {}; 
var lang = 'cn'; 
IPUB.I18N = {}; 

IPUB.I18N['cn'] = {
	'globalMap' : {
		'total' : '总共：',
		'number' : '个',
		'sureToDelete' : '是否删除？',
		'sureToDeleteTree' : '是否删除文件夹？',
		'sureToDeleteFile' : '是否删除选定文件？',
		'selectItemsError' : '请选择列表项！',
		'deleteItemFail' : '删除条目失败！',
		'sureToAudit' : '是否审核？',
		'description' : '描述',
		'close' : '关闭',
		'updateSuccess' : '更新成功',
		'updateFail' : '更新失败',
		'audited' : '已审核',
		'unAudited' : '未审核',
		'sure' : '确定',
		'dialog' : '对话框',
		'notice' : '操作提示',
		'cancel' : '取消'
	},
	'configMap' : {
		'saveBeanSuccess' : '保存配置成功',
		'saveBeanFailed' : '保存配置失败',
		'newConfig' : '新建配置',
		'selectConfigsError' : '请选择配置！',
		'version' : '版本：',
		'status' : '； 状态：',
		'editConfig' : '编辑配置'
	},
	'fileMap' : {
		'root' : '根文件夹',
		'selectEditGroup' : '请选择文件夹！',
		'selectTreeError' : '请选择文件夹！',
		'selectFileError' : '请选择文件！',
		'select' : '选择',
		'fileName' : '文件名',
		'fileSize' : '文件大小(KB)',
		'auditTime' : '审核时间',
		'auditState' : '审核状态',
		'preview' : '预览',
		'pageIndexNotInteger' : '转到页必须是整数',
		'editGroup' : '编辑文件夹属性',
		'fileUpload' : '文件上传',
		'createGroup' : '新建文件夹',
		'filePreview' : '文件预览',
		'deleteRootError' : '无法删除根文件夹',
		'editFile' : '编辑文件属性'
	},
	'roleMap' : {
		'noSelectRoleError' : '请选择角色',
		'createRole' : '新建角色',
		'deleteFail' : '删除失败',
		'editRole' : '编辑角色',
		'sureToDeleteRole' : '是否删除选定的角色？'
	},
	'tagMap' : {
		'tagUesdDeleteError' : '正在使用，无法删除!',
		'exchangeRate' : '汇率',
		'stock' : '股票',
		'editText' : '编辑文字',
		'text' : '文字',
		'picture' : '图片',
		'video' : '视频',
		'audio' : '音频',
		'editPicture' : '编辑图片',
		'editVideo' : '编辑视频',
		'editRss' : '编辑RSS',
		'rss' : 'RSS',
		'state' : '状态',
		'type' : '类型',
		'content' : '内容',
		'name' : '名称',
		'edit' : '编辑',
		'open' : '开启',
		'pause' : '关闭',
		'addTag' : '添加RSS',
		'category' : '类别',
		'view' : '查看',
		'viewText' : '查看文字',
		'updateItemFail' : '更新RSS内容失败！', 
		'selectEditTag' : '请选择RSS源！'
	},
	'userMap' : {
		'noSelectUserError' : '请选择帐户',
		'createUser' : '新建帐户',
		'editUser' : '编辑帐户',
		'sureToDeleteUser' : '是否删除选定的帐户？'
	},
	'appMap' : {
		'version' : '版本',
		'upload' : '上传者',
		'timestamp' : '上传时间',
		'size' : '文件大小',
		'editSoftware' : '编辑程序',
		'softwareFailed' : '检验应用程序使用失败！',
		'removeFails' : '删除程序失败！',
		'uploadSoftware' : '上传程序'
	},
	'customMap' : {
		'corporationName' : '客户名称',
		'coporationID' : '客户域',
		'deviceCount' : '终端数量',
		'createCorporation' : '新建客户',
		'resetPassword' : '重置密码？'
	},
	'dustMap' : {
		'queryFail' : '查询数据失败',
		'fileResotreFail' : '文件还原失败'
	},
	'kernelMap' : {
		'editFirmware' : '编辑固件',
		'firmwareFailed' : '检验固件使用失败！',
		'removeFails' : '删除程序失败！',
		'uploadfirmware' : '上传固件'
	},
	'weatherMap' : {
		'cityName' : '城市名称',
		'province' : '城市分组'
	}
};
var langMap = IPUB.I18N[lang];

var _window_alert = window.alert;
var ipubs = ipubs || {};
ipubs.alert = function(m){_window_alert(m);};
(function(f) {
	// models 对象封系统常用的业务功能
	f.options = {
		appPath : '/jw'
	};
	f.models = ipubs.models || {};
	f.models.logout = function() { // login out
		location.href = f.options.appPath + "/login.html";
	};
	f.models.treeInit = function(cnt) {
		$(cnt).find('.leftMenuBta').each(function(j, el) {
					$(el).click(function() {
								$(cnt).find('.leftMenuBta')
										.removeClass('hover');
								$(cnt).find('.leftMenuBta + .leftMenuBox')
										.hide();
								$(el).addClass('hover');
								$(el).next('.leftMenuBox').show();

							});
				});
		$(cnt).find('img').each(function(i, e) {
			$(e).click(function() {
				var src = $(e).attr('src').toLowerCase();
				var item = $(e).parent().parent().next();
				if (item.hasClass('leftMenuBox')) {
					if (/leftmenu3/ig.test(src)) {
						$(e).attr('src', src.replace('leftmenu3', 'leftmenu2'));
						item.hide();

					} else {
						$(e).attr('src', src.replace('leftmenu2', 'leftmenu3'));
						item.show();
					}
				}
			});
		});
	};

	f.models.jscroll_ee = function() {
		$("#ee > .jscroll-c").css({
					top : 0
				});
		$("#ee").jscroll && $("#ee").jscroll({
					W : "10px",/*
								 * BgUrl: "url(skins/default/images/s_bg2.gif)" ,
								 */
					Bg : "right 0 repeat-y",
					Bar : {
						Bd : {
							Out : "#d4d4d4",
							Hover : "#aaa"
						},
						Bg : {
							Out : "-45px 0 repeat-y",
							Hover : "-45px 0 repeat-y",
							Focus : "-45px 0 repeat-y"
						}
					},
					Btn : {
						btn : true,
						uBg : {
							Out : "0 0",
							Hover : "0px 0",
							Focus : "0px 0"
						},
						dBg : {
							Out : "0 -8px",
							Hover : "0px -8px",
							Focus : "0px -8px"
						}
					},
					Fn : function() {
					}
				});
	};

	f.models.jscroll_ee2 = function() {
		$("#ee2 > .jscroll-c").css({
					top : 0
				});
		$("#ee2").jscroll && $("#ee2").jscroll({
					W : "10px",/*
								 * BgUrl: "url(skins/default/images/s_bg2.gif)" ,
								 */
					Bg : "right 0 repeat-y",
					Bar : {
						Bd : {
							Out : "#d4d4d4",
							Hover : "#aaa"
						},
						Bg : {
							Out : "-45px 0 repeat-y",
							Hover : "-45px 0 repeat-y",
							Focus : "-45px 0 repeat-y"
						}
					},
					Btn : {
						btn : true,
						uBg : {
							Out : "0 0",
							Hover : "0px 0",
							Focus : "0px 0"
						},
						dBg : {
							Out : "0 -8px",
							Hover : "0px -8px",
							Focus : "0px -8px"
						}
					},
					Fn : function() {
					}
				});
	};

	f.models.jscroll_leftlist = function() {
		$("#leftlist").jscroll && $("#leftlist").jscroll({
					W : "8px", /*
								 * BgUrl: "url(skins/default/images/s_bg2.gif)" ,
								 */
					Bg : "right 0 repeat-y",
					Bar : {
						Bd : {
							Out : "#d4d4d4",
							Hover : "#aaa"
						},
						Bg : {
							Out : "-45px 0 repeat-y",
							Hover : "-45px 0 repeat-y",
							Focus : "-45px 0 repeat-y"
						}
					},
					Btn : {
						btn : true,
						uBg : {
							Out : "0 0",
							Hover : "0px 0",
							Focus : "0px 0"
						},
						dBg : {
							Out : "0 -8px",
							Hover : "0px -8px",
							Focus : "0px -8px"
						}
					},
					Fn : function() {
					}
				});
	};
	// 对话框
	f.models.showDialog = function(options) {
		var url = options.url || options;
		// /var message = options.message || "";
		var width = options.width || 1000;
		var height = options.height || parseInt($(window).height() * 0.8);
		var title = options.title || IPUB.I18N[lang].globalMap.dialog;
		try {

			var dlg = $('<div class="ipub-dialog-pannel"><iframe style="border:0px none #fff;margin:auto;width:99%;height:99%;"  width="'
					+ width
					+ '"  height="'
					+ height
					+ '" src="'
					+ url
					+ '" class="ipub-dialog-pannel"></iframe></div>').dialog({
						title : title,
						modal : true,
						resizable : false,
						draggable : false,
						width : width,
						height : height,
						zIndex : 1560,
						dialogClass : "ipubs-dialog",
						buttons : {
							"关闭" : function() {
								$(this).dialog("close");
							}
						}
					});
		} catch (ex) {
			// return false;
			alert(ex);
		}
		return false;
	};
	// 操作提示
	f.models.alert = function(options) {
		if (options == undefined) {
			return;
		}
		var message = options.message || options || arguments[0];
		var width = options.width || 300;
		var height = options.height || "auto";

		var title = options.title || IPUB.I18N[lang].globalMap.notice;
		try {

			var html = '<div  class="ipubs-dialog-content"><div class=\"dialog-content\"  />  <span class=\"message-content\">'
					+ message + '</span></div>';

			var dlg = $('<div class="ipub-dialog-pannel">' + html + '</div>')
					.dialog({
								title : title,
								modal : true,
								resizable : false,
								draggable : false,
								width : width,
								height : height || "auto",
								minHeight : 0,
								zIndex : 1564,
								dialogClass : "ipubs-dialog-alert",
								buttons : {
									"确定" : function() {
										$(this).dialog("close");
									}
								}
							});
		} catch (ex) {
			// return false;
			// ipubs.alert(ex);
		}
		return false;
	};
	// 操作提示
	f.models.confirm = function(options, btnYes, btnNo) {

		var message = options.message || options || arguments[0];

		var width = options.width || 300;
		var height = options.height || "auto";
		var title = options.title || IPUB.I18N[lang].globalMap.notice;

		var btnyes = options.btnyes || function() {
		};
		var btnno = options.btnno || function() {
		};
		if (typeof options == "string") {
			message = options;
			btnyes = btnYes || btnyes;
			btnno = btnNo || btnno;
		}

		try {
			var html = '<div class="ipub-dialog-alert-cnt"><div class=\"dialog-content\" /> <span class=\"message-content\">'
					+ message + '</span></div>';
			var dlg = $('<div class="ipub-dialog-pannel">' + html + '</div>')
					.dialog({
								title : title,
								modal : true,
								resizable : false,
								draggable : false,
								width : width,
								height : height || "auto",
								minHeight : 0,
								zIndex : 1564,
								dialogClass : "ipubs-dialog-confirm",
								buttons : {
									"确定" : function() {
										btnyes();
										$(this).dialog("close");
									},
									"取消" : function() {
										btnno();
										$(this).dialog("close");
									}
								}
							});
		} catch (ex) {
			// return false;
			// ipubs.alert(ex);
		}
		return false;
	};
	f.units = {
		windowResize : function() {

			var res = function() {
				var h = $(this).height() > 660 ? $(this).height() - 220 : 440;
				$('div.nr1 .right1:first').height(h);
				$('div.nr1 .right2:first').height(h);
				$('div#ee').height(h - 100);

				$('div#ee2').height(h - 100);

				f.models.jscroll_ee();
				f.models.jscroll_ee2();

				var lh = $('div.nr1 .right1:first').height() - 45;
				$('div#leftlist').height(lh);
				f.models.jscroll_leftlist();
			};
			if ($('div.nr1 .right1:first').length > 0
					|| $('div.nr1 .right2:first').length > 0) {
				$(window).resize(function() {
							res();
						});
				res();
			}
		},
		bindCheckBoxButton : function() {

			$(".bottomMenu .rightSelect input").change(function(e) {

				if ($(this).hasClass('chk-sel-all')) {
					var a = this;
					var isInvert = $(".bottomMenu .rightSelect input.chk-sel-invert")[0].checked;
					$('.jscroll-c input[type=checkbox]').each(function(i, e2) {
								$(e2)[0].checked = a.checked == (!isInvert);
							});
				}
				if ($(this).hasClass('chk-sel-invert')) {
					$('.jscroll-c input[type=checkbox]').each(function(i, e2) {
								$(e2)[0].checked = !$(e2)[0].checked;
							});
				}
					// $(e.target).click(function () { });
			});
			$(".bottomMenu .rightSelect .chk-cancle").click(function(e) {
						$(".bottomMenu .rightSelect input").each(
								function(i, e2) {
									e2.checked = false;
								});
						$('.jscroll-c input[type=checkbox]').each(
								function(i, e2) {
									e2.checked = false;
								});
					});
		}
	};
	// // 验证接口,
	f.validator = {
		// 登录验证
		login : function() {
			location.href = f.options.appPath + "/index.html";
			return false;
		}
	};
	// 初始化ipubs对象参数，绑定事件
	f.init = function(options) {
		f.options = options || {
			appPath : '/jw'
		};
		$(document).ready(function() {
			$(".ipub-showDialog").click(function() {
						var url = $(this).attr("href").toString();
						var title = $(this).attr("title") || $(this).text();
						f.models.showDialog({
									url : url,
									title : title
								});
						return false;
					});
			$(".ipub-logout").click(function() {
						f.models.logout();
					});
			f.models.treeInit('.leftMenu');
			f.units.bindCheckBoxButton();
			f.models.jscroll_ee();
			f.models.jscroll_leftlist();
			f.units.windowResize();
				// $('form').jqTransform && $('form').jqTransform({ imgPath:
				// 'skins/default/images/' });
				// window.alert=f.models.alert;
				// window.confirm=f.models.confirm;
			});
	};
	f.init();
})(ipubs);

window.confirm = ipubs.models.confirm;
window.alert = ipubs.models.alert;
