if (typeof JSON !== 'object') {
	JSON = {};

	(function() {
		'use strict';

		function f(n) {
			// Format integers to have at least two digits.
			return n < 10 ? '0' + n : n;
		}

		if (typeof Date.prototype.toJSON !== 'function') {

			Date.prototype.toJSON = function() {

				return isFinite(this.valueOf()) ? this.getUTCFullYear() + '-'
						+ f(this.getUTCMonth() + 1) + '-'
						+ f(this.getUTCDate()) + 'T' + f(this.getUTCHours())
						+ ':' + f(this.getUTCMinutes()) + ':'
						+ f(this.getUTCSeconds()) + 'Z' : null;
			};

			String.prototype.toJSON = Number.prototype.toJSON = Boolean.prototype.toJSON = function() {
				return this.valueOf();
			};
		}

		var cx = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g, escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g, gap, indent, meta = { // table
																																																																							// of
																																																																							// character
																																																																							// substitutions
			'\b' : '\\b',
			'\t' : '\\t',
			'\n' : '\\n',
			'\f' : '\\f',
			'\r' : '\\r',
			'"' : '\\"',
			'\\' : '\\\\'
		}, rep;

		function quote(string) {

			// If the string contains no control characters, no quote
			// characters, and no
			// backslash characters, then we can safely slap some quotes around
			// it.
			// Otherwise we must also replace the offending characters with safe
			// escape
			// sequences.

			escapable.lastIndex = 0;
			return escapable.test(string) ? '"'
					+ string.replace(escapable, function(a) {
						var c = meta[a];
						return typeof c === 'string' ? c : '\\u'
								+ ('0000' + a.charCodeAt(0).toString(16))
										.slice(-4);
					}) + '"' : '"' + string + '"';
		}

		function str(key, holder) {

			// Produce a string from holder[key].

			var i, // The loop counter.
			k, // The member key.
			v, // The member value.
			length, mind = gap, partial, value = holder[key];

			// If the value has a toJSON method, call it to obtain a replacement
			// value.

			if (value && typeof value === 'object'
					&& typeof value.toJSON === 'function') {
				value = value.toJSON(key);
			}

			// If we were called with a replacer function, then call the
			// replacer to
			// obtain a replacement value.

			if (typeof rep === 'function') {
				value = rep.call(holder, key, value);
			}

			// What happens next depends on the value's type.

			switch (typeof value) {
			case 'string':
				return quote(value);

			case 'number':

				// JSON numbers must be finite. Encode non-finite numbers as
				// null.

				return isFinite(value) ? String(value) : 'null';

			case 'boolean':
			case 'null':

				// If the value is a boolean or null, convert it to a string.
				// Note:
				// typeof null does not produce 'null'. The case is included
				// here in
				// the remote chance that this gets fixed someday.

				return String(value);

				// If the type is 'object', we might be dealing with an object
				// or an array or
				// null.

			case 'object':

				// Due to a specification blunder in ECMAScript, typeof null is
				// 'object',
				// so watch out for that case.

				if (!value) {
					return 'null';
				}

				// Make an array to hold the partial results of stringifying
				// this object value.

				gap += indent;
				partial = [];

				// Is the value an array?

				if (Object.prototype.toString.apply(value) === '[object Array]') {

					// The value is an array. Stringify every element. Use null
					// as a placeholder
					// for non-JSON values.

					length = value.length;
					for (i = 0; i < length; i += 1) {
						partial[i] = str(i, value) || 'null';
					}

					// Join all of the elements together, separated with commas,
					// and wrap them in
					// brackets.

					v = partial.length === 0 ? '[]' : gap ? '[\n' + gap
							+ partial.join(',\n' + gap) + '\n' + mind + ']'
							: '[' + partial.join(',') + ']';
					gap = mind;
					return v;
				}

				// If the replacer is an array, use it to select the members to
				// be stringified.

				if (rep && typeof rep === 'object') {
					length = rep.length;
					for (i = 0; i < length; i += 1) {
						if (typeof rep[i] === 'string') {
							k = rep[i];
							v = str(k, value);
							if (v) {
								partial.push(quote(k) + (gap ? ': ' : ':') + v);
							}
						}
					}
				} else {

					// Otherwise, iterate through all of the keys in the object.

					for (k in value) {
						if (Object.prototype.hasOwnProperty.call(value, k)) {
							v = str(k, value);
							if (v) {
								partial.push(quote(k) + (gap ? ': ' : ':') + v);
							}
						}
					}
				}

				// Join all of the member texts together, separated with commas,
				// and wrap them in braces.

				v = partial.length === 0 ? '{}' : gap ? '{\n' + gap
						+ partial.join(',\n' + gap) + '\n' + mind + '}' : '{'
						+ partial.join(',') + '}';
				gap = mind;
				return v;
			}
		}

		// If the JSON object does not yet have a stringify method, give it one.

		if (typeof JSON.stringify !== 'function') {
			JSON.stringify = function(value, replacer, space) {

				// The stringify method takes a value and an optional replacer,
				// and an optional
				// space parameter, and returns a JSON text. The replacer can be
				// a function
				// that can replace values, or an array of strings that will
				// select the keys.
				// A default replacer method can be provided. Use of the space
				// parameter can
				// produce text that is more easily readable.

				var i;
				gap = '';
				indent = '';

				// If the space parameter is a number, make an indent string
				// containing that
				// many spaces.

				if (typeof space === 'number') {
					for (i = 0; i < space; i += 1) {
						indent += ' ';
					}

					// If the space parameter is a string, it will be used as
					// the indent string.

				} else if (typeof space === 'string') {
					indent = space;
				}

				// If there is a replacer, it must be a function or an array.
				// Otherwise, throw an error.

				rep = replacer;
				if (replacer
						&& typeof replacer !== 'function'
						&& (typeof replacer !== 'object' || typeof replacer.length !== 'number')) {
					throw new Error('JSON.stringify');
				}

				// Make a fake root object containing our value under the key of
				// ''.
				// Return the result of stringifying the value.

				return str('', {
					'' : value
				});
			};
		}

		// If the JSON object does not yet have a parse method, give it one.

		if (typeof JSON.parse !== 'function') {
			JSON.parse = function(text, reviver) {

				// The parse method takes a text and an optional reviver
				// function, and returns
				// a JavaScript value if the text is a valid JSON text.

				var j;

				function walk(holder, key) {

					// The walk method is used to recursively walk the resulting
					// structure so
					// that modifications can be made.

					var k, v, value = holder[key];
					if (value && typeof value === 'object') {
						for (k in value) {
							if (Object.prototype.hasOwnProperty.call(value, k)) {
								v = walk(value, k);
								if (v !== undefined) {
									value[k] = v;
								} else {
									delete value[k];
								}
							}
						}
					}
					return reviver.call(holder, key, value);
				}

				// Parsing happens in four stages. In the first stage, we
				// replace certain
				// Unicode characters with escape sequences. JavaScript handles
				// many characters
				// incorrectly, either silently deleting them, or treating them
				// as line endings.

				text = String(text);
				cx.lastIndex = 0;
				if (cx.test(text)) {
					text = text.replace(cx, function(a) {
						return '\\u'
								+ ('0000' + a.charCodeAt(0).toString(16))
										.slice(-4);
					});
				}

				// In the second stage, we run the text against regular
				// expressions that look
				// for non-JSON patterns. We are especially concerned with '()'
				// and 'new'
				// because they can cause invocation, and '=' because it can
				// cause mutation.
				// But just to be safe, we want to reject all unexpected forms.

				// We split the second stage into 4 regexp operations in order
				// to work around
				// crippling inefficiencies in IE's and Safari's regexp engines.
				// First we
				// replace the JSON backslash pairs with '@' (a non-JSON
				// character). Second, we
				// replace all simple value tokens with ']' characters. Third,
				// we delete all
				// open brackets that follow a colon or comma or that begin the
				// text. Finally,
				// we look to see that the remaining characters are only
				// whitespace or ']' or
				// ',' or ':' or '{' or '}'. If that is so, then the text is
				// safe for eval.

				if (/^[\],:{}\s]*$/
						.test(text
								.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g,
										'@')
								.replace(
										/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g,
										']')
								.replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {

					// In the third stage we use the eval function to compile
					// the text into a
					// JavaScript structure. The '{' operator is subject to a
					// syntactic ambiguity
					// in JavaScript: it can begin a block or an object literal.
					// We wrap the text
					// in parens to eliminate the ambiguity.

					j = eval('(' + text + ')');

					// In the optional fourth stage, we recursively walk the new
					// structure, passing
					// each name/value pair to a reviver function for possible
					// transformation.

					return typeof reviver === 'function' ? walk({
						'' : j
					}, '') : j;
				}

				// If the text is not JSON parseable, then a SyntaxError is
				// thrown.

				throw new SyntaxError('JSON.parse');
			};
		}
	}());
}
// /////////////////////////////////gnamp.js//////////////////////////////

var dlg = ".ipub-dialog-pannel";
var gnamp_width = 400;
var gnamp_height = 280;
function IPUBDialog() {
	return $(dlg);
}

/*function callParentFunction(functionName, param) {
	if (functionName == null || functionName == undefined) {
		return closeWindow();
	}
	eval("window.parent." + functionName + "(" + JSON.stringify(param) + ")");
	
}*/
         
function callParentFunction(functionName, param) {
	if (functionName == null || functionName == undefined) {
		return closeWindow();
	}

	try {
		var jso = JSON.stringify(param);
		window.location.href = 'javascript:window.parent.' + functionName + "("
				+ jso + ");";
	} catch (e) {
		alert(e.message);
	}
}

function closeIFrame() {
	window.parent.closeIPUBDialog();
	return false;
}

function closeIPUBDialog() {
	IPUBDialog().dialog("close");
}
function copyParam(destination, source) {

	for (var property in source) {
		destination[property] = source[property];
	}
	return destination;
}
function showIPUBDialogStand(url, dlgparam) {

	var dlgDiv = $("<div class=\"ipub-dialog-pannel\"><iframe style=\"width:99%;height:98%;border: 0px;\" src= \""
			+ url + "\"></iframe></div>");
	dlgDiv.dialog(reStandParam(dlgparam));
	return dlgDiv;
}

function standParam() {
	return defaultParam();
}
function reStandParam(param) {
	return copyParam(standParam(), param);
}
function customParam(param) {
	return copyParam(closeParam(), param);
}
function defaultParam() {
	return {
		title : {},
		modal : true,
		resizable : true,
		draggable : true,
		width : gnamp_width,
		height : gnamp_height,
		zIndex : 19564
	};
}

function closeParam() {
	return null; /*
					 * ""; {buttons: {"关闭":function(){
					 * $(this).dialog("close");}}};
					 */
}
function tt(callback) {
	eval((function() {
				return "callback(arguments[1]);";
			})());
}
function ajaxCallback(url, param, callback) {
	var args = arguments;
	$.ajax({
		type : "post",
		dataType : "json",
		url : url,
		data : param,
		beforeSend : function() {
			$("body")
					.append("<div id='shadow' style='height: 100%;width: 100%;position: absolute;top: 0px;'>"
							+ "<img style='position: absolute;left: 50%;top: 50%;z-index: 999;' src='../skins/default/images/updating.gif'/></div>");
		},
		complete : function() {
			$("#shadow").hide();
		},
		success : function(msg) {
			$("#shadow").remove();
			if (args.length > 3) {
				eval((function() {
							var argsparam = "";
							for (var i = 3; i < args.length; i++) {
								argsparam += "args[" + i + "],";
							}
							argsparam = argsparam.substring(0, argsparam.length
											- 1);
							var callbackname = callback.name
									|| "showObjectList";
							return callbackname + "(msg," + argsparam + ");";
						})());
			} else {
				if (callback != undefined) {
					callback(msg);
				}
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			if (!IS_DEBUG) {
				window.top.location.href = '/';
			}

		}
	});
}

page = function(url, param, callback) {
	pageConfig = {
		currentSortManaer : "asc",
		currentSortName : "",
		currentPageIndex : 1,
		totalPageIndex : 1,
		pageSize : 20,
		pageSizeID : "",
		param : {},
		url : "",
		totalDataID : "totalData",
		pageIndexID : "pageIndex",
		currentWithTotalID : "currentWithTotal",
		callback : function(result) {
			showlist(result);
		},
		first : function() {
			pageConfig.gotoPage(1);
		},
		previous : function() {
			pageConfig.gotoPage(pageConfig.currentPageIndex - 1 <= 1
					? 1
					: pageConfig.currentPageIndex - 1);
		},
		next : function() {
			pageConfig
					.gotoPage(pageConfig.currentPageIndex + 1 >= pageConfig.totalPageIndex
							? pageConfig.totalPageIndex
							: pageConfig.currentPageIndex + 1);
		},
		last : function() {
			pageConfig.gotoPage(pageConfig.totalPageIndex);
		},
		gotoPage : function(pageindex) {
			pageConfig.currentPageIndex = pageindex;
			pageConfig
					.ajax(copyParam(pageConfig.param, pageConfig.globeParam()));
		},
		config : function(url, param, callback) {
			this.param = param;
			if (url != undefined)
				this.url = url;
			if (callback != undefined)
				this.callback = callback;
			pageConfig.gotoPage(1);
		},
		ajax : function(ajaxParam) {

			ajaxCallback(this.url, ajaxParam
							|| pageConfig.globeParam(pageConfig.param),
					function(msg) {

						pageConfig.param = ajaxParam
								|| (pageConfig.param || {});
						if(msg && msg != "undefined"){
							if (!msg.success) {
								if (msg.message && msg.message != undefined) {
									alert(msg.message);
								} else {
									alert("Query Data Error");
								}
								return false;
							}
						}
						pageConfig.callback(msg);
						if (msg.page != undefined) {
							pageConfig.setTotalFlag(msg.page.totalPages);
							$("#" + pageConfig.totalDataID)
									.html(msg.page.totalRows);
							pageConfig.refreshGotoOption(msg.page.totalPages);
						}
					});
		},
		setTotalFlag : function(flag) {
			pageConfig.totalPageIndex = flag;

			$("#" + pageConfig.currentWithTotalID)
					.html(pageConfig.currentPageIndex + "/"
							+ pageConfig.totalPageIndex);
			$("#" + pageConfig.pageIndexID).html(pageConfig
					.refreshGotoOption(pageConfig.totalPageIndex));

		},
		globeParam : function(inparam) {
			var param = {
				"page.currentPage" : pageConfig.currentPageIndex,
				"page.pageSize" : pageConfig.pageSize,
				"oManaer" : pageConfig.currentSortManaer,
				"oName" : pageConfig.currentSortName
			};

			if (typeof(inparam) != "undefined") {
				copyParam(param, inparam);
			}
			return param;
		},
		refreshGotoOption : function(length) {
			if (length == undefined) {
				length = 0;
			}
			if (length <= 1) {
				$("select[name=" + pageConfig.pageIndexID + "] option")
						.remove();
				$("select[name=" + pageConfig.pageIndexID + "]")
						.append("<option value='" + 1 + "'>" + 1 + "</option>");
				return;
			}
			$("select[name=" + pageConfig.pageIndexID + "] option").remove();
			for (var i = 1; i <= length; i++) {
				$("select[name=" + pageConfig.pageIndexID + "]")
						.append("<option value='" + i + "'>" + i + "</option>");
			}
			$("select[name=" + pageConfig.pageIndexID + "]")
					.val(pageConfig.currentPageIndex);
		},
		changepagesize : function(pagesize) {
			pageConfig.pageSize = pagesize;
			pageConfig.gotoPage(1);
		}
	};
	pageConfig.url = url || pageConfig.url;
	pageConfig.param = param || pageConfig.globeParam(param);
	pageConfig.callback = callback || pageConfig.callback;

	return pageConfig;
};

var pageObject = function(_option) {
	config = {
		currentWithTotal : _option.currentWithTotal || 1
	};
	var context = "<span>总共<b class=\"orange1\"><span id=\"totalData\"></span></b>条</span>"
			+ "<span>第<b class=\"currentWithTotal\">"
			+ config.currentWithTotal
			+ "</b>页</span>"
			+ "<span>"
			+ "每页<select name=\"pagesize\">"
			+ "<option value=\"10\">10</option>"
			+ "<option value=\"20\">20</option>"
			+ "<option value=\"50\">50</option>"
			+ "<option value=\"-1\">全部</option>"
			+ "</select>"
			+ " </span>"
			+ "<span>转到"
			+ "<select name=\"pageIndex\" class=\"pageIndex\">"
			+ "<option value=\"1\">1</option>"
			+ "</select>"
			+ " </span>"
			+ "<img src=\"/skins/default/images/pageB1.gif\" id=\"first\" />"
			+ "<img src=\"/skins/default/images/pageB2.gif\" id=\"previous\" />"
			+ "<img src=\"/skins/default/images/pageB3.gif\" id=\"next\" />"
			+ "<img src=\"/skins/default/images/pageB4.gif\" id=\"last\" />";

};

var SELECT_ELEMENT = "#mytab > tbody input[type=checkbox]";
function selectedAll() {
	$(SELECT_ELEMENT).selectall();
}
function cancel() {
	$(SELECT_ELEMENT).cancel();
}
function deselect() {
	$(SELECT_ELEMENT).deselect();
}
/**
 * 表格隔行变色
 */
$(function() {
			$('tr:nth-child(even)').addClass('alt');
			$('td:contains(Henry)').nextAll().andSelf().addClass('highlight');
			$('th').parent().addClass('table-heading');
		});

// jquery 全选 反选 取消
$.fn.extend({
			selectall : function() {
				this.attr("checked", true);
			},
			deselect : function() {
				this.each(function(i, o) {
							o.checked = !o.checked;
						});
			},
			cancel : function() {
				this.attr("checked", false);
			},
			showError : function showError(message) {   
				// this.text(message?  message: ""); 
				var img = "&nbsp;&nbsp;<img style=\"width:16px;height:16px;padding:0px;\" " + 
						"src=\"/skins/default/images/btn4.png\"/>";
				this.css("line-height", "normal");
				this.html(message?  img: "");
				return false;
			}
		});
