// / <reference path="jquery-1.3.2-vsdoc2.js" />

// 多选操作助手

if (!CheckHelper) {

	var CheckHelper = {

		checkAll : function(element) {
			$('.' + element).each(function(index) {
						this.checked = true;
					});
		},
		cleanAll : function(element) {
			$('.' + element).each(function(index) {
						this.checked = false;
					});
		},
		reverseAll : function(element) {
			$('.' + element).each(function(index) {
						this.checked = !this.checked;
					});
		}
	}

}
/*
 * //cookies 助手
 * 
 * if (!CookiesHelper) { var CookiesHelper = { //只可以有一个Cookies setCookie:
 * function (c_name, value, expiredays) { var exdate = new Date();
 * exdate.setDate(exdate.getDate() + expiredays); document.cookie = c_name + "=" +
 * escape(value) + ((expiredays == null) ? "" : ";expires=" +
 * exdate.toGMTString()); }, //获取Cookies，此处有Bug getCookies: function (c_name) {
 * if (document.cookie.length > 0) { var c_start =
 * document.cookie.indexOf(c_name + "="); if (c_start != -1) { c_start = c_start +
 * c_name.constructor + 1; var c_end = document.cookie.indexOf(";", c_start); if
 * (c_end == -1) c_end = document.cookie.length; return
 * unescape(document.cookie.substr(c_start, c_end)); } } return ""; } };
 *  } 客户端确认操作助手 if (!ConfirmHelper) {
 * 
 * var ConfirmHelper = { sureDelete: function () {
 * 
 * return confirm('sure to delete？');
 *  },
 * 
 * 
 * sureVerify: function () { return confirm('sure to check?'); },
 * 
 * sureModify: function () { return confirm('sure to modify'); },
 * 
 * sureInsert: function () { return confirm('sure to add new item?'); }
 *  } }
 * 
 * 
 * 模式对话框操作帮助器 //if (!DialogHelper) { // var DialogHelper = { // ShowDialog:
 * function(divID) { // $(divID).dialog({ bigframe: true, modal: true }); // } // }
 * //}
 * 
 * if (!DialogHelper) { var DialogHelper = { ShowIFrameDialog: function (params) {
 * if (params == null) return; if (params.url == null) return; var url =
 * params.url;
 * 
 * var divContent = String.format('<div><iframe class="dialog" src="{0}"/></div>',
 * url);
 * 
 * var dialog = $(divContent).dialog({ modal: true, autoOpen: false, width:
 * params.width, height: params.width }); dialog.dialog('open'); } }; }
 * 
 * 弹出窗口选择背景文件 function OpenFileList(url, clientID, imgControlID, imagePath) {
 * var retVal = window.showModalDialog(url, '',
 * 'dialogWidth:800px;dialogHeight:600px;status:yes;resizable:Yes;center:Yes;');
 * if (retVal) { $('#' + clientID).val(retVal); //设置预览图的图像 $('#' +
 * imgControlID).attr("src", imagePath + retVal); } else { } }
 * 
 * if (!MessageHelper) { var MessageHelper = { 提示助手 （删除、编辑） sureReady: function
 * (cssChecker, msg) { var selectFiles = $(cssChecker + ':checked'); if
 * (selectFiles.length > 0) { if (msg != null) { return confirm(msg); } else {
 * return true; } } else { alert("choose list item to operator"); return false; } }
 *  } }
 * 
 * 
 * 
 * function Querystring(qs) { // optionally pass a querystring to parse
 * this.params = {};
 * 
 * if (qs == null) qs = location.search.substring(1, location.search.length); if
 * (qs.length == 0) return;
 *  // Turn <plus> back to <space> // See:
 * http://www.w3.org/TR/REC-html40/interact/forms.html#h-17.13.4.1 qs =
 * qs.replace(/\+/g, ' '); var args = qs.split('&'); // parse out name/value
 * pairs separated via &
 *  // split out each name=value pair for (var i = 0; i < args.length; i++) {
 * var pair = args[i].split('='); var name = decodeURIComponent(pair[0]);
 * 
 * var value = (pair.length == 2) ? decodeURIComponent(pair[1]) : name;
 * 
 * this.params[name] = value; } }
 * 
 * Querystring.prototype.get = function (key, default_) { var value =
 * this.params[key]; return (value != null) ? value : default_; }
 * 
 * Querystring.prototype.contains = function (key) { var value =
 * this.params[key]; return (value != null); }
 * 
 * 
 * if (!Messages) { var Messages = { sureDeleteMessage: "sure delete?",
 * sureVerifyMessage: "sure Verify?" } }
 */