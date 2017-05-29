// / <reference path="jquery-1.3.2-vsdoc2.js" />

// 多选操作助手

if (!CheckHelper) {

	var CheckHelper = {

		checkAll : function(element) {
			$('.' + element).each(function(index) {
						this.checked = true;
					})
		},
		cleanAll : function(element) {
			$('.' + element).each(function(index) {
						this.checked = false;
					})
		},
		reverseAll : function(element) {
			$('.' + element).each(function(index) {
						this.checked = !this.checked;
					})
		}
	}

}

// cookies 助手

if (!CookiesHelper) {
	var CookiesHelper = {
		// 只可以有一个Cookies
		setCookie : function(c_name, value, expiredays) {
			var exdate = new Date();
			exdate.setDate(exdate.getDate() + expiredays);
			document.cookie = c_name
					+ "="
					+ escape(value)
					+ ((expiredays == null) ? "" : ";expires="
							+ exdate.toGMTString())
		},
		// 获取Cookies，此处有Bug
		getCookies : function(c_name) {
			if (document.cookie.length > 0) {
				var c_start = document.cookie.indexOf(c_name + "=");
				if (c_start != -1) {
					c_start = c_start + c_name.constructor + 1;
					var c_end = document.cookie.indexOf(";", c_start);
					if (c_end == -1)
						c_end = document.cookie.length;
					return unescape(document.cookie.substr(c_start, c_end));
				}
			}
			return "";
		}
	};

}