var IPUB = {};
var lang = 'tw';
IPUB.I18N = {}; 

IPUB.I18N['tw'] = {
	"globalMap" : {
		"audited" : "已審核", 
		"cancel" : "取消", 
		"close" : "關閉", 
		"deleteItemFail" : "刪除項目失敗！", 
		"description" : "描述", 
		"dialog" : "對話方塊", 
		"notice" : "操作提示", 
		"number" : "個", 
		"selectItemsError" : "請選擇列表項目！", 
		"sure" : "確定", 
		"sureToAudit" : "是否審核？", 
		"sureToDelete" : "是否刪除？", 
		"sureToDeleteFile" : "是否刪除文件？", 
		"sureToDeleteTree" : "是否刪除目錄？", 
		"total" : "總共：", 
		"unAudited" : "未審核", 
		"updateFail" : "更新失敗", 
		"updateSuccess" : "更新成功"
	},  
	"configMap" : {
		"editConfig" : "編輯組態", 
		"newConfig" : "新增組態", 
		"saveBeanFailed" : "儲存組態失敗", 
		"saveBeanSuccess" : "儲存組態成功", 
		"selectConfigsError" : "請選擇組態！", 
		"status" : "； 狀態：", 
		"version" : "版本："
	},  
	"fileMap" : {
		"auditState" : "審核狀態", 
		"auditTime" : "審核時間", 
		"createGroup" : "新增目錄", 
		"deleteRootError" : "無法刪除根目錄", 
		"editFile" : "編輯文件", 
		"editGroup" : "編輯目錄", 
		"fileName" : "文件名", 
		"filePreview" : "文件預覽", 
		"fileSize" : "文件大小(KB)", 
		"fileUpload" : "文件上傳", 
		"pageIndexNotInteger" : "頁數必須是整數", 
		"preview" : "預覽", 
		"root" : "根目錄", 
		"select" : "選擇", 
		"selectEditGroup" : "請選擇目錄！", 
		"selectFileError" : "請選擇文件屬性！", 
		"selectTreeError" : "請選擇目錄！"
	},  
	"roleMap" : {
		"createRole" : "新增角色", 
		"deleteFail" : "刪除失敗", 
		"editRole" : "編輯角色", 
		"noSelectRoleError" : "請選擇角色", 
		"sureToDeleteRole" : "是否刪除選定的角色？"
	},  
	"tagMap" : {
		"addTag" : "新增RSS", 
		"audio" : "音訊", 
		"category" : "類別", 
		"content" : "內容", 
		"edit" : "編輯", 
		"editPicture" : "編輯圖片", 
		"editRss" : "編輯RSS", 
		"editText" : "編輯文字", 
		"editVideo" : "編輯視訊", 
		"exchangeRate" : "匯率", 
		"name" : "名稱", 
		"open" : "開啟", 
		"pause" : "關閉", 
		"picture" : "圖片", 
		"rss" : "RSS", 
		"selectEditTag" : "請選擇RSS源！", 
		"state" : "狀態", 
		"stock" : "股票", 
		"tagUesdDeleteError" : "正在使用，無法刪除!", 
		"text" : "文字", 
		"type" : "類型", 
		"updateItemFail" : "更新RSS內容失敗！", 
		"video" : "視訊", 
		"view" : "查看", 
		"viewText" : "查看文字"
	},  
	"userMap" : {
		"createUser" : "新增帳戶", 
		"editUser" : "編輯帳戶", 
		"noSelectUserError" : "請選擇帳戶", 
		"sureToDeleteUser" : "是否刪除選定的帳戶？"
	},  
	"appMap" : {
		"editSoftware" : "編輯程式", 
		"removeFails" : "刪除程式失敗！", 
		"size" : "文件大小", 
		"softwareFailed" : "檢驗應用程式使用失敗！", 
		"timestamp" : "上傳時間", 
		"upload" : "上傳者", 
		"uploadSoftware" : "上傳程式", 
		"version" : "版本"
	},  
	"customMap" : {
		"coporationID" : "客戶域", 
		"corporationName" : "客戶名稱", 
		'deviceCount' : '終端數量',
		"createCorporation" : "新增客戶", 
		"resetPassword" : "重設密碼？"
	},  
	"dustMap" : {
		"fileResotreFail" : "文件還原失敗", 
		"queryFail" : "查詢資料失敗"
	},  
	"kernelMap" : {
		"editFirmware" : "編輯軔體", 
		"firmwareFailed" : "校驗軔體使用失敗！", 
		"removeFails" : "刪除程式失敗！", 
		"uploadfirmware" : "上傳軔體"
	},  
	"weatherMap" : {
		"cityName" : "城市名稱", 
		"province" : "所屬區域"
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
							"關閉" : function() {
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
									"確定" : function() {
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
									"確定" : function() {
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
