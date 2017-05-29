<%@page import="com.gnamp.server.model.*"%>
<%@page import="com.gnamp.server.handle.*"%>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="x-ua-compatible" content="ie=8" />

<link rel="stylesheet" type="text/css" href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" />
<link rel="stylesheet" type="text/css" href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" />
<link rel="stylesheet" type="text/css" href="${theme}/skins/default/css/jqtransform.css" media="all" />
<link rel="stylesheet" type="text/css" href="${theme}/skins/default/css/zTreeStyle.css" />
<link rel="stylesheet" type="text/css"  href="${theme}/skins/default/css/ipubs-dialog.css"/>

<script type="text/javascript" src="${theme}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${theme}/js/jquery.jqtransform.js"></script>
<script type="text/javascript" src="${theme}/js/jscroll.js"></script>
<script type="text/javascript" src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
<script type="text/javascript" src="${theme}/js/ipub.models-<s:text name="langjs"/>.js"></script>
<script type="text/javascript" src="${theme}/js/jquery.ztree.core-3.2.min.js"></script>
<script type="text/javascript" src="${theme}/js/jquery.ztree.exedit-3.2.min.js"></script>
<script type="text/javascript" src="${theme}/js/jquery.ztree.excheck-3.2.min.js"></script>

<script src="js/gnamp.js"></script>
<script src="js/JScript.js"></script>
<%@ include file="js/selectterminaljs.jsp"%>

<script type="text/javascript">

	$(function() {
		$('form').jqTransform({
			imgPath : '${theme}/skins/default/images/'
		});
	});	
</script>
<script type="text/javascript">
var remember=null;
	function del(msg) {
		 remember = new Array();

		var arr = $("input[class='devid']"); //document.getElementsByTagName('input');
		var ok = 0;
		var selectID = 0;
		var selectIDs = "";
		for ( var i = 0; i < arr.length; i++) {
			var abc = arr[i];
			if (abc.type == "checkbox") {
				if (abc.checked == true) {
					ok++;
					selectID = abc.value;
					selectIDs += selectID.toString() + ",";

					remember.push(abc.value);
				}
			}
		}

		if (ok > 0) {
			
		} else {
			alert("<s:text name="select_terminal"/>");
			return false;
		}

	}

	

	function view(devid) {
		var gid = 0;
		if (selectedId != null) {
			gid = selectedId;
		}		
		showIPUBDialogStand("terminal!ToEvent?terminal.groupId=" + gid
				+ "&terminal.deviceId=" + devid,{title:"<s:text name="terminallog"/>",width:896,height:566});
	}


	var selectIDs = "";
	function saveid()
	{
		 remember = new Array();

			var arr = $("input[class='devid']"); //document.getElementsByTagName('input');
			var ok = 0;
			var selectID = 0;
			for ( var i = 0; i < arr.length; i++) {
				var abc = arr[i];
				if (abc.type == "checkbox") {
					if (abc.checked == true) {
						ok++;
						selectID = abc.value;
						selectIDs += selectID.toString() + ",";

						remember.push(abc.value);
					}
				}
			}

			if (ok > 0) {
				
			} else {
				alert("<s:text name="select_terminal"/>");
				return false;
			}
			
			callParentFunction("finished", selectIDs);
			return closeIFrame();
	}
	
	function clearid(){
		selectIDs = "";
		callParentFunction("finished", selectIDs);
		return closeIFrame();
	}
</script>


<!-- 滚动栏样式2 开始-->
	<script> 
var duma = {
	$:function(o){ if(document.getElementById(o)) {return document.getElementById(o);} },
	getStyle:function(o) { return o.currentStyle ||document.defaultView.getComputedStyle(o,null); },
	getOffset:function(o) {
		var t = o.offsetTop,h = o.offsetHeight;
		while(o = o.offsetParent) { t += o.offsetTop; }
		return { top:t, height:h };
	},
	bind:function(o,eType,fn) {
		if(o.addEventListener) { o.addEventListener(eType,fn,false); }
		else if(o.attachEvent) { o.attachEvent("on" + eType,fn); }
		else { o["on" + eType] = fn; }
	},
	stopEvent:function(e) {
		e = e || window.event;
		e.stopPropagation && (e.preventDefault(),e.stopPropagation()) || (e.cancelBubble = true,e.returnValue = false);
	},
	setCookie:function(c_name,value,expiredays) {
		var exdate = new Date();
		exdate.setDate(exdate.getDate() + expiredays);
		document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString());
	},
	getCookie:function(c_name) {
		if(document.cookie.length > 0) {
			c_start = document.cookie.indexOf(c_name + "=");
			if(c_start != -1) { 
				c_start = c_start + c_name.length + 1; 
				c_end = document.cookie.indexOf(";",c_start);
				if(c_end == -1) { c_end = document.cookie.length; }
				return unescape(document.cookie.substring(c_start,c_end));
			} 
		}
		return "";
	}
};
duma.BeautifyScrollBar = function(obj,arrowUpCss,arrowUpActiveCss,handleCss,handleActiveCss,arrowDownCss,arrowDownActiveCss) {
	this.arrowUpInterval;
	this.arrowDownInterval;
	this.barMouseDownInterval;
	this.relY;
	this.id = obj;
	this.obj = duma.$(this.id);
	this.setObjCss();		//预先设置父容器的css定位才能让接下来的属性设置起作用
	//this.obj.innerHTML = '<div id="' + obj + 'Area" class="dumascroll_area">' + this.obj.innerHTML + '</div><div id="' + obj + 'Bar" class="dumascroll_bar"><div class="dumascroll_arrow_up"></div><div class="dumascroll_handle"></div><div class="dumascroll_arrow_down"></div></div>';
	this.area = duma.$("dumaScrollAreaId_1Area");
	this.bar = duma.$("dumaScrollAreaId_1Bar");
	this.barPos;
	this.arrowUp = this.bar.getElementsByTagName("div")[0];
	this.arrowUpCss = arrowUpCss;
	this.arrowUpActiveCss = arrowUpActiveCss;
	this.handle = this.bar.getElementsByTagName("div")[1];
	this.handleCss = handleCss;
	this.handleActiveCss = handleActiveCss;
	this.arrowDown = this.bar.getElementsByTagName("div")[2];
	this.arrowDownCss = arrowDownCss;
	this.arrowDownActiveCss = arrowDownActiveCss;
	this.handleMinHeight = 15;
	this.arrowUpHeight = parseInt(duma.getStyle(this.arrowUp).height);
	this.arrowDownHeight = parseInt(duma.getStyle(this.arrowDown).height);
	this.areaScrollHeight = this.area.scrollHeight;
	this.handleHeight = parseInt(this.area.offsetHeight/this.area.scrollHeight * (this.bar.offsetHeight - this.arrowUpHeight - this.arrowDownHeight));
};
duma.BeautifyScrollBar.prototype = {
	setObjCss:function() {
		duma.getStyle(this.obj).position == "static" ? this.obj.style.position = "relative" : duma.getStyle(this.obj).backgroundColor == "transparent" ? this.obj.style.backgroundColor = "transparent" : null;		//若容器本来就没有设position，则初始化为relative；若容器原来未设置背景色，则初始化为白色；
	},
	sethandle:function() {				//当内容超多时设置拖拽条子的最小高度
		this.handle.style.top = this.arrowUpHeight + "px";
		if(this.handleHeight > this.handleMinHeight) {
			this.handleHeight < this.bar.offsetHeight - this.arrowUpHeight - this.arrowDownHeight ? this.handle.style.height = this.handleHeight + "px" : this.handle.style.display = "none";
		}
		else { this.handleHeight = this.handleMinHeight; this.handle.style.height = this.handleMinHeight + "px"; }
	},
	setBarPos:function() {				//将当前滚动的距离值存入cookie
		this.barPos = this.area.scrollTop + "";
		duma.setCookie(this.id + "CookieName",this.barPos,1);
	},
	getBarPos:function() {
		this.barPos = duma.getCookie(this.id + "CookieName");
		if(this.barPos!=null && this.barPos!="") {
			this.area.scrollTop = this.barPos;
			this.areaScroll();
		}
	},
	clearArrowUpInterval:function() { clearInterval(this.arrowUpInterval); },
	clearArrowDownInterval:function() { clearInterval(this.arrowDownInterval); },
	clearBarMouseDownInterval:function() { clearInterval(this.barMouseDownInterval); },
	areaScroll:function() {
		 /* this.handle.style.display != "none" ? this.handle.style.top = this.area.scrollTop/(this.area.scrollHeight - this.area.offsetHeight) * (this.bar.offsetHeight - this.handleHeight - this.arrowUpHeight - this.arrowDownHeight) + this.arrowUpHeight + "px" : null;  */
	},
	areakeydown:function(e) {			//支持键盘上下按键
		var that = this;
		document.onkeydown = function(event) {
			var e = event || window.event,
			ek = e.keyCode || e.which;
			if(ek == 40) { that.area.scrollTop += 25; }
			else if(ek == 38) { that.area.scrollTop -= 25; }
			if(that.area.scrollTop > 0 && that.area.scrollTop < that.area.scrollHeight - that.area.offsetHeight){ duma.stopEvent(e); }
			that.setBarPos();
		};
	},
	handleMove:function(e) {
		var e = e || window.event;
		this.area.scrollTop = (e.clientY - this.relY - this.arrowUpHeight)/(this.bar.offsetHeight - this.handleHeight - this.arrowUpHeight - this.arrowDownHeight)*(this.area.scrollHeight - this.area.offsetHeight);
		this.setBarPos();
	},
	handleMouseDown:function(e) {
		var that = this,e = e || window.event;
		that.relY = e.clientY - that.handle.offsetTop;
		that.handle.setCapture ? that.handle.setCapture() : null;
		that.handle.className = that.handleActiveCss;
		document.onmousemove = function(event) { that.handleMove(event); };
		document.onmouseup = function() {
			that.handle.className = that.handleCss;
			that.handle.releaseCapture ? that.handle.releaseCapture() : null;
			document.onmousemove = null;
		};
	},
	barScroll:function(e) {
		var e = e || window.event,eDir;		//设置滚轮事件,e.wheelDelta与e.detail分别兼容IE、W3C，根据返回值的正负来判断滚动方向
		if(e.wheelDelta) { eDir = e.wheelDelta/120; }
		else if(e.detail) { eDir = -e.detail/3; }
		eDir > 0 ? this.area.scrollTop -= 80 : this.area.scrollTop += 80;	//步长设80像素比较接近window滚动条的滚动速度
		if(this.area.scrollTop > 0 && this.area.scrollTop < this.area.scrollHeight - this.area.offsetHeight){ duma.stopEvent(e); }
		this.setBarPos();
	},
	barDown:function(e) {
		var e = e || window.event,that = this,
			eY = e.clientY,
			mStep = this.bar.offsetHeight,
			documentScrollTop = document.documentElement.scrollTop || document.body.scrollTop,
			hOffset = duma.getOffset(this.handle),
			bOffset = duma.getOffset(this.bar);
		if(documentScrollTop + eY < hOffset.top) { this.barMouseDownInterval = setInterval(function(e){
			that.area.scrollTop -= that.area.offsetHeight;
			if(that.area.scrollTop <= (eY + documentScrollTop - bOffset.top - that.arrowUpHeight)/(that.bar.offsetHeight - that.arrowUpHeight - that.arrowDownHeight) * that.area.scrollHeight) { that.clearBarMouseDownInterval(); }
			that.setBarPos();
		},80); }
		else if(documentScrollTop + eY > hOffset.top + hOffset.height) { this.barMouseDownInterval = setInterval(function(){
			that.area.scrollTop += that.area.offsetHeight;
			if(that.area.scrollTop >= (eY + documentScrollTop - bOffset.top - that.arrowUpHeight - hOffset.height)/(that.bar.offsetHeight - that.arrowUpHeight - that.arrowDownHeight) * that.area.scrollHeight) { that.clearBarMouseDownInterval(); }
			that.setBarPos();
		},80); }
		duma.stopEvent(e);
	},
	arrowUpMouseDown:function(e) {
		var that = this;
		this.arrowUpInterval = setInterval(function(){ that.area.scrollTop -= 25; that.setBarPos(); },10);
		this.arrowUp.className = this.arrowUpActiveCss;
		duma.stopEvent(e);
	},
	arrowUpMouseUp:function() { this.clearArrowUpInterval(); this.arrowUp.className = this.arrowUpCss; },
	arrowUpMouseOut:function() { this.clearArrowUpInterval(); this.arrowUp.className = this.arrowUpCss; },
	arrowDownMouseDown:function(e) {
		var that = this;
		this.arrowDownInterval = setInterval(function(){ that.area.scrollTop += 25; that.setBarPos(); },10);
		this.arrowDown.className = this.arrowDownActiveCss;
		duma.stopEvent(e);
	},
	arrowDownMouseUp:function() { this.clearArrowDownInterval(); this.arrowDown.className = this.arrowDownCss; },
	arrowDownMouseOut:function() { this.clearArrowDownInterval(); this.arrowDown.className = this.arrowDownCss; },
	run:function(){
		var that = this;
		this.sethandle();
		this.areaScroll();
		this.getBarPos();
		this.area.onscroll = function(){that.areaScroll();};
		this.area.onmouseover = this.bar.onmouseover = function(event){that.areakeydown(event);};
		this.area.onmouseout = this.bar.onmouseout = function(){document.onkeydown = null;};
		this.handle.onmousedown = function(event){that.handleMouseDown(event);};
		this.bar.onmousedown = function(event){that.barDown(event);};
		this.bar.onmouseup = function(){that.clearBarMouseDownInterval();};
		this.bar.onmouseout = function(){that.clearBarMouseDownInterval();};
		this.arrowUp.onmousedown = function(event){that.arrowUpMouseDown(event);};
		this.arrowUp.onmouseup = function(){that.arrowUpMouseUp();};
		this.arrowUp.onmouseout = function(){that.arrowUpMouseOut();};
		this.arrowDown.onmousedown = function(event){that.arrowDownMouseDown(event);};
		this.arrowDown.onmouseup = function(){that.arrowDownMouseUp();};
		this.arrowDown.onmouseout = function(){that.arrowDownMouseOut();};
		duma.bind(this.obj,"mousewheel",function(event){that.barScroll(event);});
		duma.bind(this.obj,"DOMMouseScroll",function(event){that.barScroll(event);});
	}
};
duma.BeautifyScrollBar.init = function() {
	var scrollObj = document.getElementById("groupListDiw");
	if (scrollObj)
		new duma.BeautifyScrollBar(scrollObj.id,"dumascroll_arrow_up","dumascroll_arrow_up_a","dumascroll_handle","dumascroll_handle_a","dumascroll_arrow_down","dumascroll_arrow_down_a").run();
};

if(!isfinished)
	obb = duma.BeautifyScrollBar.init;
else
	duma.BeautifyScrollBar.init();
	
//setTimeout(cctv, 5000);
//function cctv(){
  //duma.bind(window,"load",duma.BeautifyScrollBar.init); 
	//duma.BeautifyScrollBar.init();
//} 
</script>
<!-- 滚动栏样式2 结束-->

	



</head>
<body  style="overflow-x: hidden;overflow-y: hidden;">

	<div class="topBj1"></div>



	<div class="mid1">

		<div class="bt1">
		  <%--  <h2><s:text name="grouplist" /></h2> --%>
			 
			<ul class="tab1">
                <li class="hover"><s:text name="grouplist" /></li>
            </ul>
			 
			<form style="margin-top:-10px;" class="topSearchMk2 topSearchMk3">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" style="z-index:1000; position:relative; width:462px; float:right">
			  <tr>
			    <td width="33">
			    <input style="background: none; margin-top: 0px; margin-right: -10px;"  type="radio" name="rd" id="rd1" checked="checked" value="1" /></td>
			    <td width="61"><label><s:text name="relatively" /></label></td>
			    <td width="20"><input style="background: none; margin-top: 0px; margin-right: -10px;"  type="radio" name="rd" id="rd2" value="2" /></td>
			    <td width="64"><label style="margin-right:10px;"><s:text name="absolutely" /></label></td>
			    <td width="159" class="search" style="width:145px; overflow:hidden;">
			    <div class="searchL">
			    <input id="search" name="Input" type="text" style="width:110px;"/></div>
			     <div class="searchR"><button id="searchTerminal"></button></div></td>
			    <td width="30" style="width:25px;"><div style="margin-top:-5px;">
		       <input name="lov2" type="checkbox" class="checkbox" id="includeChildren"
						value="true" /></div></td>
			    <td width="101" style="width:95px;"><s:text name="containdir" /></td>
			  </tr>
			</table>


			</form>
		</div>

		<div class="nr1">

			<!-- 左侧 开始-->

			<div class="left1">

				<div class="leftMenu">
					<div class="leftMenuBta hover">
					 <div style="width:120px; cursor: pointer;" onclick="loadRoot()"><s:text name="rootgroup" /></div>
				  <%--    <span style="z-index:2012" onclick="groupsort();">A-Z</span> --%>
					</div>
					<!-- <div class="leftMenuBox"> -->
					   <div class="leftMenuGd dumascroll" id="groupListDiw" style="height:250px;">
						   <div id="dumaScrollAreaId_1Area" class="dumascroll_area">
								<ul id="treeterminal" class="ztree"></ul>
							</div>
							<div id="dumaScrollAreaId_1Bar" class="dumascroll_bar">
								<div class="dumascroll_arrow_up"></div>
								<div class="dumascroll_handle"></div>
								<div class="dumascroll_arrow_down"></div>
							</div>
						</div>
					<!-- </div>	 -->
					<!-- <div  class="leftMenuBta">
						
					</div> -->
					  <div class="leftMenuBox">                      
                      </div>
					
				</div>
				<div class="leftButtonBox">				
	                 <a href="#" onclick="groupsort();">A-Z</a>
				</div>
				<div class="leftButtonBox">				  
			  
				</div>

			</div>

			<!-- 左侧 结束-->

			<!-- 右侧 开始-->

			<div class="right1" id="terminaldiv">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="shujBt shujBt2">
				    	<tr>
					 	<%-- 	<td align="center" class="first" style="width: 50px;"><s:text name="selected" /></td> --%>
							<td id="idsort" class="first" style="cursor: pointer; width: 100px;"><s:text name="devid" /></td>
							<td id="namesort" style="cursor: pointer; width: 170px;"><s:text name="devicename" /></td>
							<td id="logontimesort" style="cursor: pointer; width: 130px;"><s:text name="logontime" /></td>							
							<%-- <td style="width: 120px;"><s:text name="process" /></td>
							<td style="cursor: pointer; width: 50px;"><s:text name="taskstate" /></td>
							<td style="cursor: pointer; width: 50px;">软件状态</td> --%>
							<td id="onlinestatesort" style="cursor: pointer; width: 50px;"><s:text name="onlinestate" /></td>
						 </tr>
				</table>

				<div id="ee">
					<table id="mytab" width="100%" border="0" cellspacing="0"
						cellpadding="0" class="shujTb shujTb2">
						
					</table>

				</div>

				<div class="rightMenu">

				<ul class="left rightMenuLong">
						
						<li><a style="cursor: pointer;" 
							onclick="saveid();return false;"
							class="chk-cancle"><s:text name="text_confirm_select"/></a></li>
						
						<li><a style="cursor: pointer;" 
							onclick="clearid();return false;"
							class="chk-cancle"><s:text name="emptychoose"/></a></li>
							 
					</ul>

					<ul class="right rightMenuLong">
					   
					</ul>
				</div>



				<!-- 右侧底部menu 开始-->

				<div class="bottomMenu">
					<div class="rightSelect">
						<label style="cursor: pointer;" 
							onclick="return CheckHelper.checkAll('devid');return false;"><s:text name="selectall" /></label> | 
						<label style="cursor: pointer;" 
							onclick="return CheckHelper.reverseAll('devid');return false;"><s:text name="invertselection" /></label> | 
						<label style="cursor: pointer;" 
							onclick="return CheckHelper.cleanAll('devid');return false;"
							class="chk-cancle"><s:text name="cancel" /></label>
				
					</div>
					
					<a href="#"> <img onclick="queryterminals();"
						src="${theme}/skins/default/images/refresh3.gif" border="0"
						class="ref" /></a>

					<div class="page1 page2">
				   		<span><s:text name="p_altogether"/><b class="orange1"><span id="totalData"></span></b><s:text name="p_article"/></span> 
						<span><s:text name="p_first"/><b id="currentWithTotal"></b><s:text name="p_page"/></span>
						<span><s:text name="p_everypage"/>
						   <select name="pagesize" onchange="$p.changepagesize(this.value)">
						     <!-- option value="10">10</option -->
						     <option value="20">20</option>
						     <option value="50">50</option>
						     <option value="<%=Integer.MAX_VALUE %>"><s:text name="p_all"/></option>
						   </select> 
					  	 </span>
					   	<span><s:text name="p_goto"/>
						   <select name="pageIndex" id="pageIndex" onchange="$p.gotoPage(this.value)">
						     <option value="1">1</option>
						   </select> 
					  	 </span>
                        <img src="${theme}/skins/default/images/pageB1.gif" id="first" />
                        <img src="${theme}/skins/default/images/pageB2.gif" id="previous"/>
                        <img src="${theme}/skins/default/images/pageB3.gif" id="next"/>
                        <img src="${theme}/skins/default/images/pageB4.gif" id="last"/>
					</div>					

				</div>

				<!-- 右侧底部menu 结束-->

			</div>

			<!-- 右侧 结束-->

			<div class="clearit"></div>

		</div>

		<div class="di1"></div>

	</div>
</body>
</html>