var totalPageFlag = -1;
var currentPageFlag = -1;
/**
 * 本页面通用的ajax
 * 
 * @param {}
 *            url
 * @param {}
 *            param
 * @param {data}
 *            callback
 * 
 */
function ajaxCallback(url, param, callback) {
	$.ajax({
				type : "post",
				dataType : "json",
				url : url,
				data : param,
				complete : function() {
				},
				success : function(msg) {
					callback(msg);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					window.top.location.href = '/';
				}
			});
}
/**
 * 
 * @param {}
 *            index 总页数
 * @return {}根据总页数生成页码和链接
 */
function generic(index, method) {
	var page_index_result = "";
	for (var i = 1; i <= index; i++) {
		page_index_result += "<a href='javascript:" + method + "(" + i + ")'>"
				+ i + "</a>&nbsp;";
	}
	return page_index_result;
}

/**
 * json相加的方法
 * 
 * @param {}
 *            json1
 * @param {}
 *            json2
 * @return {} 相加之后的json1
 */
function copyParam(destination, source) {
	for (var property in source) {
		destination[property] = source[property];
	}
	return destination;
}

/**
 * ***********************************************************************************
 * 分页js跳转
 * 
 * ***********************************************************************************
 */
/**
 * 
 * @param {}
 *            index 跳转到哪一页,然后设置当前页为这个页码
 */
function gotoPage(url, inparam, callback, index) {
	currentPageFlag = index;
	ajaxCallback(url, inparam, callback, currentPageFlag);
}

/**
 * 第一页
 */
function first() {
	gotoPage(1);
}
/**
 * 上一页
 */
function previous() {
	gotoPage(currentPageFlag - 1 <= 1 ? 1 : currentPageFlag - 1);
}
/**
 * 下一页
 */
function next() {
	gotoPage(currentPageFlag + 1 >= totalPageFlag
			? totalPageFlag
			: currentPageFlag + 1);
}
/**
 * 最后一页
 */
function last() {
	gotoPage(totalPageFlag);
}
