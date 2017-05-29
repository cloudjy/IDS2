/**
 * 标准文件导入
 */
(function() {
	var paths = ['../js/jquery.jqtransform.js', '../js/jscroll.js',
			'../js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js',
			'js/gnamp.js'], baseURL = './';
	for (var i = 0, pi; pi = paths[i++];) {
		document.write('<script type="text/javascript" src="' + baseURL + pi
				+ '"></script>');
	}
})();
