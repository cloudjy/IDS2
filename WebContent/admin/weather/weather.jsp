<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=8" />
<%@include file="/template/Admin/admintitle.jsp" %>
<style>
 html { overflow: scroll; } 
</style>
<link rel="stylesheet" type="text/css"	href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" />
<link href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" rel="stylesheet"	type="text/css" />
<link rel="stylesheet"	href="${theme}/skins/default/css/jqtransform.css" media="all" />
<script type="text/javascript" src="${theme}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${theme}/js/jquery.jqtransform.js"></script>
<script type="text/javascript" src="${theme}/js/jscroll.js"></script>
<script type="text/javascript" src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="${theme}/skins/default/css/ipubs-dialog.css" />
<script type="text/javascript" src="${theme}/js/ipub.models-<s:text name="langjs"/>.js"></script>
<link href="${theme}/skins/default/css/zTreeStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${theme}/js/jquery.ztree.core-3.2.min.js"></script>
<script type="text/javascript" src="${theme}/js/jquery.ztree.exedit-3.2.min.js"></script>
<script type="text/javascript" src="${theme}/js/jquery.ztree.excheck-3.2.min.js"></script>
<script type="text/javascript" src="js/gnamp.js"></script>
<script type="text/javascript" src="js/weather.js"></script>
<script type="text/javascript">
        $(function () {
            $('form').jqTransform({ imgPath: '${theme}/skins/default/images/'});
            $("#weathermana").addClass("hover");
	   });
        
        function readu(){
        	var param = {
        			"u" : "c"
        		     };
            $.ajax({
    			type : "post",
    			dataType : "json",
    			url : "weather!readU",
    			data : param,
    			complete : function() {
    			},
    			success : function(msg) {
    				if (msg.success) {
    					if(msg.data=="f")
    						 $('input:radio[value="f"]').attr("checked", "checked");
    					else
    						$('input:radio[value="c"]').attr("checked", "checked");  					
    					 
    				} else {
    					alert(msg.data);
    				}
    			},
    			error : function(XMLHttpRequest, textStatus,
    					errorThrown) {
    				window.top.location.href='/';
    			}
    		});
        }
        
        function add(){
        	showIPUBDialogStand("weather!preadd",{title:"<s:text name="text_createcity"/>",width:400,height:250});
        }
        
        function refresh(){
        	$.fn.zTree.init($("#treeweather"), setting,null);
			loadRoot();
			
        	//queryweathers();
        }
        
        function edit(){
         var id = 0;
    		$("#mytab tbody input:checked").each(function(i,f){
    		 id = f.value;    	
    		});
    		if(id==0){
    			alert('<s:text name="alert_selectitemerror"/>');
    		    return;
    		}
    		if("<s:text name="lang"/>"=="cn"){
    			showIPUBDialogStand("weather!preedit?id="+id,{title:"<s:text name="title_editweather"/>",width:800,height:280});
    		}else{
    			showIPUBDialogStand("weather!preedit?id="+id,{title:"<s:text name="title_editweather"/>",width:850,height:280});
    		}
        		
        }
  

        function doSubmit(){
        	var param = {
        			"id" : 0
        		     };

        		$.ajax({
        			type : "post",
        			dataType : "json",
        			url : "weather!queryWeather2",
        			data : param,
        			complete : function() {
        			},
        			success : function(msg) {
        				if (msg.success) {
        					alert(msg.data);
        				} else {        					
        					alert(msg.data);
        				}
        			},
        			error : function(XMLHttpRequest, textStatus,
        					errorThrown) {
        			
        				window.top.location.href='/';
        			}
        		});
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
<script>
function ttstyle() {
	var param = {
			"u" : $('input:radio[name="rd"]:checked').val()
		     };
		$.ajax({
			type : "post",
			dataType : "json",
			url : "weather!writeu",
			data : param,
			complete : function() {
			},
			success : function(msg) {
				if (msg.success) {
					//alert("成功！！！");
				} else {
					alert(msg.data);
				}
			},
			error : function(XMLHttpRequest, textStatus,
					errorThrown) {
				window.top.location.href='/';
			}
		});
}
</script>
</head>

<body>
	<!-- menu 开始-->
	<%@ include file="/admin/header.jsp" %>
	<!-- menu 结束-->

	<div class="topBj1"></div>

	<div class="mid1">
		<div class="bt1">
			<h2><s:text name="text_provinceslist"/></h2>
		<%-- 	<form class="topSearchMk2 topSearchMk3" style="margin-top: -10px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
				    <td style="width: 50px;text-align:right;"><s:text name="f"/></td>
                    <td style="width: 83px;">
                      <input style="margin-bottom: 6px;" type="radio"
								onclick="ttstyle()" name="rd" id="rd1" checked="checked"
								value="f" />
					</td>
                    <td style="width: 50px;text-align:right;"><s:text name="c"/></td>
                    <td style="width: 83px;">
                       	<input style="margin-bottom: 6px;" type="radio"
								onclick="ttstyle()" name="rd" id="rd2" checked="checked"
								value="c" />
					</td>
                </tr>
            </table>
			</form> --%>
		</div>
		<div class="nr1">
			<div class="left1">
				<div class="leftMenu">
					<div class="leftMenuBta hover" onclick="loadRoot()"><s:text name="text_allprovinces"/></div>
					<!-- <div class="leftMenuBox"> -->
						<!-- <ul id="treeweather" class="ztree"></ul> -->
						<div class="leftMenuGd dumascroll" id="groupListDiw" style="height:306px;">
						   <div id="dumaScrollAreaId_1Area" class="dumascroll_area">
								<ul id="treeweather" class="ztree"></ul>
							</div>
							<div id="dumaScrollAreaId_1Bar" class="dumascroll_bar">
								<div class="dumascroll_arrow_up"></div>
								<div class="dumascroll_handle"></div>
								<div class="dumascroll_arrow_down"></div>
							</div>
						</div>
					<!-- </div> -->
				</div>

			</div>
			<!-- 左侧 开始-->
			<!-- 左侧 结束-->
			<!-- 右侧 开始-->
			<div class="right1">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="shujBt" id="shujBt">
				</table>
				<div id="ee">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="shujTb" id="mytab">
                        <thead>
                        </thead>
                		<tbody></tbody>
                    </table>
				</div>


				<div class="rightMenu">
					<ul class="left rightMenuLong">
					    <li><a href="javascript:add();"><s:text name="new"/></a></li>
						<li><a href="javascript:edit();"><s:text name="text_edit"/></a></li>
						<li><a href="javascript:removecity();"><s:text name="delete"/></a></li>
						<li><s:text name="fd"/><input style="margin-bottom: 2px;" type="radio"
								onclick="ttstyle()" name="rd" id="rd1" checked="checked"
								value="f" /></li>
						<li><s:text name="cd"/><input style="margin-bottom: 2px;" type="radio"
								onclick="ttstyle()" name="rd" id="rd2" checked="checked"
								value="c" /></li>
					</ul>
				</div>

				<!-- 右侧底部menu 开始-->
				<div class="bottomMenu">
					<div class="rightSelect">
						
							<label onclick="selectedAll()" for="chk_sel_all" ><s:text name="selectall"/></label> | 
							<label onclick="deselect()" for="chk_sel_invert" >
									<s:text name="invertselection"/>
							</label> | 
							<label onclick="cancel()">
									<s:text name="cancel" />
							</label>
					</div>
					<a href="#"> <img src="${theme}/skins/default/images/refresh3.gif"
						border="0" class="ref" /></a>
					<div class="page1 page2" style="width:520px;">
						<%@ include file="/template/pagehy.jsp" %>
					</div>
				</div>
				
				<!-- 右侧底部menu 结束-->
			</div>
			<!-- 右侧 结束-->
			<div class="clearit"></div>
		</div>
		<div class="di1"></div>
	</div>
	<%@ include file="/buttom.jsp" %>
</body>
</html>
