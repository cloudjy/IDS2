<%@ page language="java" pageEncoding="utf-8"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-cache" /> 
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="1970-01-01 00:00:00" /> 

<style type="text/css"> 
body {overflow-x: hidden;overflow-y: hidden;}

.configBox{ width:725px; overflow:hidden; margin:0 auto;} 
.configItem { 
	height: 32px;
	background-color: #FFFFFF;
	border-right: #5F5F5F solid 1px;
	border-left: #5F5F5F solid 1px;
	border-bottom:1px solid #999;
	font: 12px normal "微软雅黑", "微軟雅黑", "Calibri", "Arial", "sans-serif";} 
.configItem li {height:31px;line-height:31px;float:left;border:1px solid #999;border-left:none;margin-bottom: -1px;background: #e0e0e0;overflow: hidden;position: relative;}
.configItem li a {text-decoration: none;color: #000;font-size: 14px; display: block; padding: 0 10px; border: 1px solid #fff; outline: none;}
.configItem li a:hover {background: #ccc;}	
.configItem .curItem,.configItem .curItem a:hover{background: #fff;border-bottom: 1px solid #fff;} 

.currentLink {display:none;float:center;border:1px solid #999;background: #e0e0e0;}
.currentDiv {position: absolute; display:none;z-index=100;background-color: #FFFFFF; border: #5F5F5F solid 1px;font-size: 16px;}
</style> 
<link rel="stylesheet" type="text/css" href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" />
<link rel="stylesheet" type="text/css" href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" />
<link rel="stylesheet" type="text/css" href="${theme}/skins/default/css/jqtransform.css" media="all" />
<link rel="stylesheet" type="text/css" href="${theme}/skins/default/css/zTreeStyle.css" />
<link rel="stylesheet" type="text/css" href="${theme}/skins/default/css/ipubs-dialog.css"/>
<link rel="stylesheet" type="text/css" href="${theme}/skins/default/css/niceforms-default.css" />

<script type="text/javascript" src="${theme}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${theme}/js/jquery.jqtransform.js"></script>
<script type="text/javascript" src="${theme}/js/jscroll.js"></script>
<script type="text/javascript" src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
<script type="text/javascript" src="${theme}/js/ipub.models-<s:text name="langjs"/>.js"></script>
<script type="text/javascript" src="${theme}/js/jquery.ztree.core-3.2.min.js"></script>
<script type="text/javascript" src="${theme}/js/jquery.ztree.exedit-3.2.min.js"></script>
<script type="text/javascript" src="${theme}/js/jquery.ztree.excheck-3.2.min.js"></script>
<script type="text/javascript" src="${theme}/js/niceforms.js"></script>

<script src="${theme}/Pages/Dialog/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${theme}/Pages/js/gnamp.js"></script> 


<script type="text/javascript"> 

function trimEmpty(str) {
	return str? str.replace(/(^\s*)|(\s*$)/g, "") : "";
}

function parseHHmm(text) {  
	var pattern = /^\s*(([0-1]{0,1}[0-9])|(2[0-3])):[0-5]{0,1}[0-9]\s*$/;
	if (text && text.match(pattern)) {
		var textArray = text.replace(/\s*/g, "").split(":");
		return parseInt(textArray[0], 10)*3600 +  parseInt(textArray[1], 10)*60; 
	} else {
		return -1;
	}
}

function formatHHmm(second) {
	var hh = "" + ((second-(second%3600))/3600);
	while (hh.length < 2) {
		hh = "0" + hh;
	}
	
	var mm = "" + ((second%3600)/60);
	while (mm.length < 2) {
		mm = "0" + mm;
	}
	return hh + ":" + mm;
} 

// [00:00,15:00][15:00,18:00]
function parseTimeBounds(configText) {
	
	var timeBoundInts = new Array();
	
	if (!configText)
		return timeBoundInts; 
	
	configText = configText.replace(/\s*/g, "");
	configText = configText.replace(/\]\[/g, "|"); 
	configText = configText.replace(/\];\[/g, "|"); 
	configText = configText.replace(/\[/g, ""); 
	configText = configText.replace(/\]/g, ""); 

	configText = configText.replace(/\-/g, ","); 
	
	var allBounds = configText.split(/\|/g);
	if (allBounds) { 
		for (var i=0; i<allBounds.length; ++i) {
			try {
				var cols = allBounds[i].split(/\,/g);
				var start = parseHHmm(cols[0]);
				var end = parseHHmm(cols[1]); 

				if (start >= 0 && end > start ) {
					timeBoundInts.push({start:start, end:end});
				}
			} catch (exp) {
			}
		}
	}

	return timeBoundInts;
}

// [00:00,15:00,8][15:00,18:00,10]
function parseTimeBoundInts(configText, scope) {
	
	var timeBoundInts = new Array();
	
	if (!configText)
		return timeBoundInts;
	
	configText = configText.replace(/\s*/g, "");
	configText = configText.replace(/\]\[/g, "|"); 
	configText = configText.replace(/\];\[/g, "|"); 
	configText = configText.replace(/\[/g, ""); 
	configText = configText.replace(/\]/g, ""); 

	configText = configText.replace(/\-/g, ","); 
	
	var allBounds = configText.split(/\|/g);
	if (allBounds) { 
		for (var i=0; i<allBounds.length; ++i) {
			try {
				var cols = allBounds[i].split(/\,/g);
				var start = parseHHmm(cols[0]);
				var end = parseHHmm(cols[1]);
				var val = parseInt(cols[2], 10);

				if (start >= 0 && end > start
						&& (!scope || scope(val))) {
					timeBoundInts.push({start:start, end:end, value:val});
				}
			} catch (exp) {
			}
		}
	}

	return timeBoundInts;
} 

</script>