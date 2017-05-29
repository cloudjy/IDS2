/*
 * Author Vic Lee Vision 1.0.0.0 ipubs 是一个全局对象，封装所有的业务功能和模块
 * 
 * 
 * 
 * 
 * 
 */

var ipubs = ipubs || {};
ipubs.alert = window.alert;
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
		$("#ee").jscroll && $("#ee").jscroll({
					W : "10px",
					BgUrl : "url(./../../skins/default/images/s_bg2.gif)",
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
					W : "8px",
					BgUrl : "url(./../../skins/default/images/s_bg2.gif)",
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
		var title = options.title || "对话框";
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
	// 对话框
	f.models.alert = function(options) {
		var message = options.message || options || arguments[0];
		var width = options.width || 300;
		var height = options.height || "auto";
		var title = options.title || "对话框";
		try {

			var html = '<div  class="ipubs-dialog-content"><img src="skins/default/images/b10.gif" /> '
					+ message + '</div>';

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
	// 对话框
	f.models.confirm = function(options, btnYes, btnNo) {

		var message = options.message || options || arguments[0];

		var width = options.width || 300;
		var height = options.height || "auto";
		var title = options.title || "对话框";

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

			var html = '<div class="ipub-dialog-alert-cnt"><img src="skins/default/images/b10.gif" /> '
					+ message + '</div>';
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
				f.models.jscroll_ee();
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
