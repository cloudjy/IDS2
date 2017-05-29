<%@ page language="java" pageEncoding="utf-8"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<meta http-equiv="Cache-Control" content="no-cache" /> 
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="1970-01-01 00:00:00" /> 

<style type="text/css"> 
body {overflow-x: hidden;overflow-y: hidden;}

.configBox{ width:700px; overflow:hidden; margin:0 auto;} 
.configItem { 
	height: 32px;
	background-color: #FFFFFF;
	border-right: #5F5F5F solid 1px;
	border-left: #5F5F5F solid 1px;
	border-bottom:1px solid #999;
	font: 12px normal "微软雅黑", "微軟雅黑", "Calibri", "Arial", "sans-serif";} 
.configItem li {height:31px;line-height:31px;float:left;border:1px solid #999;border-left:none;margin-bottom: -1px;background: #e0e0e0;overflow: hidden;position: relative;}
.configItem li a {text-decoration: none;color: #000;font-size: 14px; display: block; padding: 0 20px; border: 1px solid #fff; outline: none;}
.configItem li a:hover {background: #ccc;}	
.configItem .curItem,.configItem .curItem a:hover{background: #fff;border-bottom: 1px solid #fff;}

.inputStatic {width:120px;}
.trServer {}
.inputServer {width:120px;}
.inputWifi {width:120px;}
</style> 
<link rel="stylesheet" type="text/css" href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" />
<link rel="stylesheet" type="text/css" href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" />
<link rel="stylesheet" type="text/css" href="${theme}/skins/default/css/jqtransform.css" media="all" />
<link rel="stylesheet" type="text/css" href="${theme}/skins/default/css/zTreeStyle.css" />
<link rel="stylesheet" type="text/css" href="${theme}/skins/default/css/ipubs-dialog.css"/>
<link rel="stylesheet" type="text/css" href="${theme}/skins/default/css/niceforms-default.css" />


<style type="text/css">  
.bdTab tr {white-space: nowrap;} 
.bdTab td {line-height: normal;} 
</style>

<script type="text/javascript" src="${theme}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${theme}/js/jquery.jqtransform.js"></script>
<script type="text/javascript" src="${theme}/js/jscroll.js"></script>
<script type="text/javascript" src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
<script type="text/javascript" src="${theme}/js/ipub.models-<s:text name="langjs"/>.js"></script>
<script type="text/javascript" src="${theme}/js/jquery.ztree.core-3.2.min.js"></script>
<script type="text/javascript" src="${theme}/js/jquery.ztree.exedit-3.2.min.js"></script>
<script type="text/javascript" src="${theme}/js/jquery.ztree.excheck-3.2.min.js"></script>
<script type="text/javascript" src="${theme}/js/niceforms.js"></script>
 
<script type="text/javascript" src="${theme}/Pages/js/gnamp.js"></script> 


<script type="text/javascript"> 

function validIpAddr(text) {
	var pattern = /^\s*([1-9]|([1-9][0-9])|(1[0-9][0-9])|(2[0-4][0-9])|(25[0-5]))\s*\.\s*([0-9]|([1-9][0-9])|(1[0-9][0-9])|(2[0-4][0-9])|(25[0-5]))\s*\.\s*([0-9]|([1-9][0-9])|(1[0-9][0-9])|(2[0-4][0-9])|(25[0-5]))\s*\.\s*([0-9]|([1-9][0-9])|(1[0-9][0-9])|(2[0-4][0-9])|(25[0-5]))\s*$/g;
	if (text && text.match(pattern)) {
		return text.replace(/\s*/g, "");  
	} else {
		return null;
	}
} 

</script>