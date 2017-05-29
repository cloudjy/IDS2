<%@page import="com.gnamp.server.model.*"%>
<%@page import="com.gnamp.server.handle.*"%>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="x-ua-compatible" content="ie=8" />

<%@include file="/template/Public/title.jsp" %>

<style>
 html { overflow: scroll; } 
 .gaoliang{ vertical-align:middle; filter: alpha(opacity=100); opacity: 1;}	
</style>

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

<script language="javascript" type="text/javascript" src="${theme}/js/niceforms.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="${theme}/skins/default/css/niceforms-default.css" />

<script src="js/gnamp.js"></script>
<script src="js/JScript.js"></script>
<%@ include file="js/TerminalMonitorjs.jsp"%>

<script type="text/javascript">
	$(function() {
		$("#jiankong").addClass("hover");
	});
	$(function() {
		$('#head_search_form').jqTransform({
			imgPath : '${theme}/skins/default/images/'
		});
	});	
</script>
<script type="text/javascript"> 
	
	var autoRefreshTimer = null;
	function autoRefreshCheckChange() {
		if ($("#autorefresh").attr("checked") == "checked" ) {
			if (!autoRefreshTimer) 
				autoRefreshTimer = setInterval("shuaxin()", 5000); 
		} else  {
			if (autoRefreshTimer)
				clearInterval(autoRefreshTimer);
		} 
	}  
	
	function refreshTerminalList() {
		if ($("#autorefresh").attr("checked") == "checked" ){
			if (autoRefreshTimer)
				clearInterval(autoRefreshTimer);
			autoRefreshTimer = setInterval("shuaxin()", 5000);
		} 
		return shuaxin();
	}
	
	function importTer(){
		showIPUBDialogStand("terminalimport!importTer.action?groupId="+selectedId, {width:650,height:230,title:"<s:text name="importterminal"/>"
		,close:function(){
			queryterminals();
		}});	
		
		queryterminals();
	}
	
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
			if (msg  == "delete") {	 
				confirm("<s:text name="is_delete_device" />",function(){
					var param = {
							"selectIDs" : selectIDs.substring(0,
									selectIDs.length - 1)
						};
					
					ajaxCallback("terminal!DeleteTerminal", param, function(msg){
						if (msg.success) {
							queryterminals();
						} else {
							alert(msg.data);
						}
					});
					
						/* $.ajax({
							type : "post",
							dataType : "json",
							url : "terminal!DeleteTerminal",
							data : param,
							complete : function() {
							},
							success : function(msg) {
								if (msg.success) {
									queryterminals();
								} else {
									alert(msg.data);
								}
							},
							error : function(XMLHttpRequest, textStatus,
									errorThrown) {
								window.top.location.href='/';
							}
						}); */
				});
				
				
				
			}
			if (msg == "task") {
				var groupid = 0;
				try {
					groupid = selectedId == null ? 0 : selectedId;
				} catch (Exception) {
					groupid = 0;
				}
				
				showIPUBDialogStand("terminal!ToTerminalTask?selectIDs="
						+ selectIDs.substring(0, selectIDs.length - 1)
						+ "&terminal.groupId=" + groupid,
						{title:"<s:text name="terminaltask"/>",width:763,height:322});

			}
			if (msg == "edit") {
				var groupid = 0;
				try {
					groupid = selectedId == null ? 0 : selectedId;
				} catch (Exception) {
					groupid = 0;
				}
				showIPUBDialogStand("terminal!ToModifyTerminal?selectIDs="
						+ selectIDs.substring(0, selectIDs.length - 1)
						+ "&terminal.groupId=" + groupid,{title:"<s:text name="editterminaltitle"/>",width:610,height:315});

			}
			if (msg == "subtitle") {
				var groupid = 0;
				try {
					groupid = selectedId == null ? 0 : selectedId;
				} catch (Exception) {
					groupid = 0;
				}
				
				showIPUBDialogStand("terminal!ToSubtitle?selectIDs="
						+ selectIDs.substring(0, selectIDs.length - 1)
						+ "&terminal.groupId=" + groupid,{title:"<s:text name="text_subtitle_title" />",width:642,height:288});

			}
			if (msg == "upgrade") {
				var groupid = 0;
				try {
					groupid = selectedId == null ? 0 : selectedId;
				} catch (Exception) {
					groupid = 0;
				}
				
				showIPUBDialogStand("terminal!ToTerminalUpgrade?selectIDs="
						+ selectIDs.substring(0, selectIDs.length - 1)
						+ "&terminal.groupId=" + groupid,{title:"<s:text name="text_upgrade_title" />",width:834,height:283});

			}
			if (msg == "monitor") {
				var groupid = 0;
				try {
					groupid = selectedId == null ? 0 : selectedId;
				} catch (Exception) {
					groupid = 0;
				}			
				
				showIPUBDialogStand("terminal!ToMonitor?selectIDs="
						+ selectIDs.substring(0, selectIDs.length - 1)
						+ "&terminal.groupId=" + groupid,{title:"<s:text name="advanced_monitor" />",width:834,height:326});
			}
			if (msg == "report") {
				var groupid = 0;
				try {
					groupid = selectedId == null ? 0 : selectedId;
				} catch (Exception) {
					groupid = 0;
				}
							
				showIPUBDialogStand("terminal!ToReport?selectIDs="
						+ selectIDs.substring(0, selectIDs.length - 1) + "&terminal.groupId=" + groupid,{title:"<s:text name="text_terminalreport_title" />",width:996,height:567});
			}
			if (msg == "exp") {
				var groupid = 0;
				try {
					groupid = selectedId == null ? 0 : selectedId;
				} catch (Exception) {
					groupid = 0;
				}		
				
				showIPUBDialogStand("terminal!ToExp?selectIDs="
						+ selectIDs.substring(0, selectIDs.length - 1) + "&terminal.groupId=" + groupid,{title:"<s:text name="text_terminalexp_title" />",width:962,height:567});
			}
			if(msg == "see"){			
				
				
				 var param = {
						"terminal.deviceId" : selectID
					};
					$.ajax({
						type : "post",
						dataType : "json",
						url : "terminal!topreview",
						data : param,
						complete : function() {
						},
						success : function(msg) {							
							if (msg.success) {
								//window.open("terminal!preview?terminal.deviceId="+selectID, null, null, null);
						
								       
                            showIPUBDialogStand("terminal!preview?terminal.deviceId="+selectID,{title:"",width:1028,height:638});		
 
    
							} else {
								alert("<s:text name="noscreenshot" />");
							}
						},
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							window.top.location.href='/';
						}
					}); 
					
			}
		} else {
			alert("<s:text name="pleaseselectdevice"/>");
			return false;
		}

	}

	function add(pid) {
		if (pid == null) {
			alert('<s:text name="pleaseselectgroup"/>');
			return;
		}
		
		showIPUBDialogStand("terminal!ToAddTerminal?terminal.groupId="+ pid,{title:"<s:text name="createterminal"/>",width:512,height:283});
		
	/* 	$(".ui-widget-overlay").removeAttr("style");
    	//$(".ui-widget-overlay").attr({style:"width:800px; height:600px; z-index:19565"});
    	$(".ui-widget-overlay").attr({style:"z-index:19565"}); */
	}
	function added(result)
	{
		if(result=='ok'){
			queryterminals();
		}
	}
	function finished(result)
	{
		if(result=='ok'){
			queryterminals(remember);
		}
	}
	function addmore(pid) {
		if (pid == null) {
			alert('<s:text name="pleaseselectgroup"/>');
			return;
		}		
			
		showIPUBDialogStand("terminal!ToAddMore?terminal.groupId=" + pid,{title:"<s:text name="approvedterminal"/>",width:507,height:249});
	}
	function view(devid) {
		var gid = 0;
		if (selectedId != null) {
			gid = selectedId;
		}		
		showIPUBDialogStand("terminal!ToEvent?terminal.groupId=" + gid
				+ "&terminal.deviceId=" + devid,{title:"<s:text name="terminallog"/>",width:896,height:566});
	}

	//指定页面区域内容导入Excel
	function AllAreaExcel() {
		var oXL = new ActiveXObject("Excel.Application");
		var oWB = oXL.Workbooks.Add();
		var oSheet = oWB.ActiveSheet;
		var sel = document.body.createTextRange();
		sel.moveToElementText(mytab);
		//sel.moveToElementText(PrintB);
		sel.select();
		sel.execCommand("Copy");
		oSheet.Paste();
		oXL.Visible = true;
	}

	function setcity() {
		showIPUBDialogStand("terminal!ToGroupCity?terminalparam.groupId="
				+ selectedId,{title:"<s:text name="groupcity"/>",width:467,height:245});
	}
	function setsubtitle() {		
		showIPUBDialogStand("terminal!ToGroupSubtitle?terminalparam.groupId="
				+ selectedId,{title:"<s:text name="grouptitle"/>",width:467,height:245});
	}

	function exportexcel() {
		var param = {
			"terminalparam.groupId" : selectedId,
			"terminalparam.searchKey" : (($("#search").val() != null
					&& $("#search").val() != '' && $("#search").val() != undefined) ? /*parseInt($("#search").val(), 16)*/$(
					"#search").val()
					: $("#search").val()),
			"terminalparam.includeChildren" : $("#includeChildren").attr(
					"checked") == "checked" ? "true" : "false",
			"oManaer" : "asc",
			"oName" : "name"
		};
		location.href= 'terminal!crExcel?'+$.param(param);
		/* $.get('terminal!crExcel', param, function(txt) {
			//alert(txt.data);
		}); */
	}

	function querytodoterminal() {
		$("#terminaldiv").hide();
		$("#todoterminaldiv").show();
		
		ajaxCallback("todoterminal!todolist", null, function(msg){
			if (msg.success) {
				try {
					var content = "";
					if (msg.data != null && msg.data != undefined) {
						$
								.each(
										msg.data,
										function(i, p) {
											var tmp = "";
											if ((p.deviceId)
													.toString(16).length < 12) {
												for ( var i = 0; i < (12 - (p.deviceId)
														.toString(16).length); i++)
													tmp += "0";
											}
											content += "<tr style=\"word-break: keep-all\" class=\"gradeA\" id=\""
													+ p.deviceId
													+ "\">"
													+ "<td style=\"width: 12px;\"><input value=\""
													+ p.deviceId  
													+ "\" name=\""+p.deviceId+"\" " 
													+ " id=\""+p.deviceId+"\" class=\"tododevid\"  type=\"checkbox\"/></td>"
													+ "<td style=\"width: 75px;\">"
													+ ((p.deviceId)
															.toString(16).length < 12 ? tmp
															+ (p.deviceId)
																	.toString(
																			16)
																	.toUpperCase()
															: (p.deviceId)
																	.toString(
																			16)
																	.toUpperCase())
													+ "</td>"
													+ "<td style=\"width: 250px;\">"
													+ p.deviceName
													+ "</td>"
													+ "<td style=\"width: 200px;\">"
													+ p.description
													+ "</td>"
													+ "</tr>";

										});
					}
					
					$("#mytodotab").children().remove();
					$("#mytodotab").html(content);
					
					$('.shujTb > tbody >tr:odd').addClass('alt');
					
				} catch (e) {
					alert(e.message);
				}
			} else {
				alert(msg.success);
			}
		});

		return false;
	}
	
	function tododel(msg) {
		var arr = $("input[class='tododevid']"); //document.getElementsByTagName('input');
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
					if (msg != null && msg.length > 0 && msg == "edit")
						break;
				}
			}
		}

		if (ok > 0) {
			if (msg != null && msg.length > 0 && msg != "edit") {
				
				
				confirm(msg,function(){
					var param = {
							"selectIDs" : selectIDs.substring(0,
									selectIDs.length - 1)
						};
					
					ajaxCallback("todoterminal!delete", param, function(msg){
						if (msg.success) {
							querytodoterminal();
						} else {
							alert(msg.data);
						}
					});
					
					
				});
				
				
			}
			if (msg == "edit") {
				var groupid = 0;
				try {
					groupid = selectedId == null ? 0 : selectedId;
				} catch (Exception) {
					groupid = 0;
				}

		
				
				showIPUBDialogStand("todoterminal!ToModify?selectIDs="
						+ selectIDs.substring(0, selectIDs.length - 1),{title:"<s:text name="entryaudit"/>",width:616,height:319});

			}
		} else {
			alert("<s:text name="pleaseselectdevice"/>");
			return false;
		}

	}
	function audited(result)
	{
		if(result=='ok')
			querytodoterminal();
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

<script type="text/javascript"> 

	function standby() {	 
		var arr = $("input[class='devid'][type='checkbox']:checked"); //document.getElementsByTagName('input'); 
		if (!arr || arr.length <= 0) {
			alert("<s:text name="pleaseselectdevice"/>");
			return;
		}
		var selectIDs = "";
		for ( var i = 0; i < arr.length; i++) {
			selectIDs += arr[i].value.toString() + ",";  
		} 

		confirm('<s:text name="isstandby" />', function(){
				$.ajax({
					type : "post",
					dataType : "json",
					url : "terminal!standby",
					data : {"selectIDs": selectIDs},
					complete : function() {
					},
					success : function(msg) {
						if (msg.success) { 
						} else {
							alert(msg.data);
						}
					},
					error : function(XMLHttpRequest, textStatus,
							errorThrown) {
						window.top.location.href='/';
					}
				}); 
		}); 
		return false;
	}
	
	function wakeup() {	 
		var arr = $("input[class='devid'][type='checkbox']:checked"); //document.getElementsByTagName('input'); 
		if (!arr || arr.length <= 0) {
			alert("<s:text name="pleaseselectdevice"/>");
			return;
		}
		var selectIDs = "";
		for ( var i = 0; i < arr.length; i++) {
			selectIDs += arr[i].value.toString() + ",";  
		} 
		
	 	confirm('<s:text name="iswakeup" />', function(){ 
				$.ajax({
					type : "post",
					dataType : "json",
					url : "terminal!wakeup",
					data : {"selectIDs": selectIDs},
					complete : function() {
					},
					success : function(msg) {
						if (msg.success) { 
						} else {
							alert(msg.data);
						}
					},
					error : function(XMLHttpRequest, textStatus,
							errorThrown) {
						window.top.location.href='/';
					}
				}); 
			return false;
		}); 
	}
	
	function reboot() {		 
		var arr = $("input[class='devid'][type='checkbox']:checked"); //document.getElementsByTagName('input'); 
		if (!arr || arr.length <= 0) {
			alert("<s:text name="pleaseselectdevice"/>");
			return;
		}
		var selectIDs = "";
		for ( var i = 0; i < arr.length; i++) {
			selectIDs += arr[i].value.toString() + ",";  
		} 
		
	 	confirm('<s:text name="isreboot" />', function(){ 
				$.ajax({
					type : "post",
					dataType : "json",
					url : "terminal!reboot",
					data : {"selectIDs": selectIDs},
					complete : function() {
					},
					success : function(msg) {
						if (msg.success) { 
						} else {
							alert(msg.data);
						}
					},
					error : function(XMLHttpRequest, textStatus,
							errorThrown) {
						window.top.location.href='/';
					}
				});
			return false;
		}); 
	}
	
	function clearall() {	 
		var arr = $("input[class='devid'][type='checkbox']:checked"); //document.getElementsByTagName('input'); 
		if (!arr || arr.length <= 0) {
			alert("<s:text name="pleaseselectdevice"/>");
			return;
		}
		var selectIDs = "";
		for ( var i = 0; i < arr.length; i++) {
			selectIDs += arr[i].value.toString() + ",";  
		} 	
		
	 	confirm('<s:text name="isclear_device_contents" />', function(){ 
				$.ajax({
					type : "post",
					dataType : "json",
					url : "terminal!clear",
					data : {"selectIDs": selectIDs},
					complete : function() {
					},
					success : function(msg) {
						if (msg.success) { 
						} else {
							alert(msg.data);
						}
					},
					error : function(XMLHttpRequest, textStatus,
							errorThrown) {
						window.top.location.href='/';
					}
				});
			return false;
		}); 
	}

	function screenshot() { 
		var arr = $("input[class='devid'][type='checkbox']:checked"); //document.getElementsByTagName('input'); 
		if (!arr || arr.length <= 0) {
			alert("<s:text name="pleaseselectdevice"/>");
			return;
		}

		var selectIDs = "";
		for ( var i = 0; i < arr.length; i++) {
			selectIDs += arr[i].value.toString() + ",";  
		} 

		var waiteUrl = (arr.length != 1)? null : 
			"terminal!preview?terminal.deviceId="+ arr[0].value +  "&waiteImage=true"; 
 
	 	confirm('<s:text name="isscreenshot" />', function(){ 
				$.ajax({
					type : "post",
					dataType : "json",
					url : "terminal!screenshot",
					data : {"selectIDs": selectIDs},
					complete : function() {
					},
					success : function(msg) {
						if (msg.success) {  
							if (waiteUrl) {
								showIPUBDialogStand(waiteUrl,{title:"",width:1028,height:638});		
							}
						} else {
							alert(msg.data);
						}
					},
					error : function(XMLHttpRequest, textStatus,
							errorThrown) {
						window.top.location.href='/';
					}
				});
			return false;
		}); 
	}
	
	
	function deviceConfig() { 
		var arr = $("input[class='devid'][type='checkbox']:checked"); //document.getElementsByTagName('input'); 
		if (!arr || arr.length <= 0) {
			alert("<s:text name="pleaseselectdevice"/>");
			return;
		}
		var selectIDs = "";
		for ( var i = 0; i < arr.length; i++) {
			selectIDs += ((i==0? "" : ",") + arr[i].value.toString());  
		} 	
		var url = "${theme}/Pages/setting!device?selectIDs=" + selectIDs;
		showIPUBDialogStand(url, {title:"<s:text name="title_device_setting"/>",width:800,height:610});	
		return false;
	}
	
	function netConfig() { 
		var arr = $("input[class='devid'][type='checkbox']:checked"); //document.getElementsByTagName('input'); 
		if (!arr || arr.length <= 0) {
			alert("<s:text name="pleaseselectdevice"/>");
			return;
		}
		var selectIDs = "";
		for ( var i = 0; i < arr.length; i++) {
			selectIDs += ((i==0? "" : ",") + arr[i].value.toString());  
		} 	
		var url = "${theme}/Pages/setting!network?selectIDs=" + selectIDs;
		showIPUBDialogStand(url, {title:"<s:text name="title_network_setting"/>",width:750,height:650});	
		return false;
	}
</script> 
	

</head>
<body>


	<%@ include file="/header.jsp"%>


	<div class="topBj1"></div>



	<div class="mid1">

		<div class="bt1">
		 	<h2><s:text name="grouplist" /></h2>
			<ul class="tab1">
            </ul>
			 <ul class="topMenu" style="margin-top:0px;">
					   <li><a href="#"><img id="v1" onclick="changeviewstate(1); return false;" title="<s:text name="networkview"/>" src="${theme}/skins/default/images/b19.png" border="0" /></a></li>
					   <li style="display:none;"><a href="#"><img id="v2" onclick="changeviewstate(2); return false;" title="<s:text name="carouselview"/>" src="${theme}/skins/default/images/b20.png" border="0" /></a></li>
					   <li style="display:none;"><a href="#"><img id="v3" onclick="changeviewstate(3); return false;" title="<s:text name="demandview"/>" src="${theme}/skins/default/images/b21.png" border="0" /></a></li>
					   <li style="display:none;"><a href="#"><img id="v4" onclick="changeviewstate(4); return false;" title="<s:text name="spotsview"/>" src="${theme}/skins/default/images/b22.png" border="0" /></a></li>
					  <%--  <li><a href="#"><img id="v5" onclick="changeviewstate(5); return false;" title="<s:text name="Pipe view"/>" src="${theme}/skins/default/images/b23.png" border="0" /></a></li> --%>
					   <li><a href="#"><img id="v6" onclick="changeviewstate(6); return false;" title="<s:text name="programview"/>" src="${theme}/skins/default/images/b24.png" border="0" /></a></li>
					   <li><a href="#"><img id="v7" onclick="changeviewstate(7); return false;" title="<s:text name="kernelview"/>" src="${theme}/skins/default/images/b25.png" border="0" /></a></li>
					  <%--  <li><a href="#"><img id="v8" onclick="changeviewstate(8); return false;" title="<s:text name="configview"/>" src="${theme}/skins/default/images/b27.png" border="0" /></a></li> --%>
				       <li><a href="#"><img id="v5" onclick="changeviewstate(5); return false;" title="<s:text name="inspectionview"/>" src="${theme}/skins/default/images/b23.png" border="0" /></a></li>
					</ul>
			<form id="head_search_form" style="margin-top:-10px; width:450px;" class="topSearchMk2 topSearchMk3">
			<table border="0" cellspacing="0" cellpadding="0" style="z-index:1000; float:right">
			  <tr>
			    <td width="20">
			    <input style="background: none; margin-top: 0px; margin-right: -10px;"  type="radio" name="rd" id="rd1" checked="checked" value="1" /></td>
			    <td width="61"><label><s:text name="relatively" /></label></td>
			    <td width="20"><input style="background: none; margin-top: 0px; margin-right: -10px;"  type="radio" name="rd" id="rd2" value="2" /></td>
			    <td width="64"><label style="margin-right:10px;"><s:text name="absolutely" /></label></td>
			    <td width="145" class="search" style="overflow:hidden;">
			    <div class="searchL">
			    <input id="search" name="Input" type="text" style="width:110px;"/></div>
			     <div class="searchR"><button id="searchTerminal"></button></div></td>
			    <td width="5"></td>
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
					 <div style="width:120px; cursor: pointer;" onclick="loadRoot()"><s:text name="rootgroup"/></div>
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
					<div onclick="querytodoterminal()" class="leftMenuBta" style="display:none;">
						<s:text	name="blacklist" /> 
					</div>
					  <div class="leftMenuBox">                      
                      </div>
					
				</div>
				<div class="leftButtonBox" style="margin-top: -75px;">
				     <s:if test="#session.current_privilege.newgroup">
					    <a href="javascript:void(0)" id="addNode" style="display:none;"><s:text name="new" /></a>
						</s:if>
						<s:if test="#session.current_privilege.editgroup">
						 <a
						href="javascript:void(0)" id="editNode" style="display:none;" ><s:text name="edit" /></a>
						</s:if>
						<s:if test="#session.current_privilege.delgroup">
						<a
						href="javascript:void(0)" id="removeNode" style="display:none;"
						title="<s:text name="title_delete_group_button"/>"><s:text name="delete" /></a> 
						</s:if>
	                 <a href="#" onclick="groupsort();" style="display:none;">A-Z</a>
				</div>
				<div class="leftButtonBox" style="margin-top:0px; display:none;">				  
				<s:if test="#session.current_privilege.editterminal">
					<a href="#" title="<s:text name="subtitle"/>"
						onclick="setsubtitle()"><s:text name="setsubtitle" /></a> 
						<%-- </s:if> 
						<s:if test="#session.current_privilege.setweather"> --%>
						<a href="#" title="<s:text name="setcity"/>" onclick="setcity()"><s:text
							name="setcity" /></a>   
				</s:if>           
				</div>

			</div>

			<!-- 左侧 结束-->

			<!-- 右侧 开始-->

			<div class="right1" id="terminaldiv">
				<table id="tbt" width="100%" border="0" cellspacing="0" cellpadding="0"
					class="shujBt shujBt2">
				    	
				</table>

				<div id="ee">
					<table id="mytab" width="100%" border="0" cellspacing="0"
						cellpadding="0" class="shujTb shujTb2">
						
					</table>

				</div>

				<div class="rightMenu" style="height: 20px;">

					<ul class="left rightMenuShort" style="display:none;">
						<s:if test="#session.current_privilege.newterminal">
						<li onclick="javascript: add(selectedId);" ><s:text name="new" /></li>
						
						<li id="A2" onclick="javascript: addmore(selectedId);"><s:text name="batch" /></li>
						</s:if>
						<s:if test="#session.current_privilege.editterminal">
						<li onclick="javascript: del('edit');"><s:text name="edit" /></li>
						</s:if>
						<s:if test="#session.current_privilege.delterminal">
						<li onclick="javascript: del('delete');"><s:text name="delete" /></li>
						</s:if>
						<li onclick="javascript:importTer()"><s:text name="import" /></li>

						<li onclick="javascript: exportexcel(); " ><s:text name="export" /></li>
					</ul>
					
					<s:if test="#session.current_privilege.newconfig">
					<ul class="left rightMenuShort"> 
						<li onclick="javascript: clearall();"><s:text name="clear_device_contents" /></li>
						<li onclick="javascript: reboot();"><s:text name="reboot" /></li>
						<li onclick="javascript: standby();"><s:text name="standby" /></li>
						<li onclick="javascript: wakeup();"><s:text name="wakeup" /></li>
					</ul>
					<ul class="left rightMenuLong"> 
					</ul>
					<%-- 
					<ul class="left rightMenuShort">
					<form id="operate_form" class="niceform"> 
						<input type="button" onclick="screenshot();" value="<s:text name="screenshot" />" /> 
						<input type="button" onclick="clearall();" value="<s:text name="clear" />" />
						<input type="button" onclick="reboot();" value="<s:text name="reboot" />" />
						<input type="button" onclick="standby();" value="<s:text name="standby" />" />
						<input type="button" onclick="wakeup();" value="<s:text name="wakeup" />" />
						<input type="button" onclick="javascript: del('monitor');" value="<s:text name="scheduled_monitor" />" />
					</form>
					</ul>
					--%>

					<ul class="right rightMenuLong">
						<li onclick="javascript:del('monitor');"><s:text name="advanced_monitor" /></li> 
					    <li onclick="javascript: del('see');"><s:text name="viewscreenshot"/></li>
						<li onclick="javascript: screenshot();"><s:text name="screenshot" /></li>
						<li onclick="javascript: del('report');"><s:text name="terminalexport" /></li>
						<li onclick="javascript: del('exp');"><s:text name="button_problem" /></li>
						<li onclick="javascript: del('upgrade');"><s:text name="upgrade" /></li>
						<li onclick="netConfig();"><s:text name="net_config" /></li>
						<li onclick="deviceConfig();"><s:text name="device_config" /></li>
						<li onclick="javascript: del('monitor');" style="display:none;"><s:text name="terminalmonitor" /></li>						
						<li onclick="javascript: del('subtitle');" style="display:none;"><s:text name="subtitle" /></li>
						<li onclick="javascript: del('task');" style="display:none;"><font color="#FF0000" size="3">&#x25B4;</font><s:text name="terminaltask" /></li>
					</ul>
					</s:if>
				</div>
				<div style="font-size:12px;padding-left:20px;"><s:text name="bottom_prompt_terminal_monitor"/></div>



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
					
					<div style="width:130px;float:right;"> 
						<a href="#"> <img src="${theme}/skins/default/images/refresh3.gif" border="0"
							onclick="refreshTerminalList();" class="ref" />
						</a>
						<input id="autorefresh" name="autorefresh" type="checkbox" onchange="autoRefreshCheckChange();"/>
						<s:text name="autorefresh"/> 
					</div>

					<div style="width:480px;" class="page1 page2">	 
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


			<div class="right2" id="todoterminaldiv">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="shujBt shujBt2">
					<tr>
						<%-- <td class="first" style="width: 40px;"><s:text name="selected" /></td> --%>
						<td class="first" style="width: 100px;"><s:text name="devid" /></td>
						<td style="width: 250px;"><s:text name="devicename" /></td>
						<td style="width: 200px;"><s:text name="desc" /></td>
					</tr>
				</table>

				<div id="ee">
					<table id="mytodotab" width="100%" border="0" cellspacing="0"
						cellpadding="0" class="shujTb shujTb2">
						
					</table>
				</div>

				<div class="rightMenu" style="height:20px;">
					<ul class="left rightMenuLong">
						<li><a
							onclick="javascript: tododel('<s:text name="isdelete"/>');"><s:text name="text_delete" /></a></li>
						<li><a onclick="javascript: tododel('edit');"><s:text name="register" /></a></li>
					</ul>
					<ul class="right rightMenuLong">

					</ul>
				</div>
				<div style="font-size:12px;padding-left:20px;"><s:text name="bottom_prompt_terminal_todo"/></div>
				



				<!-- 右侧底部menu 开始-->

				<div class="bottomMenu">

					<div class="rightSelect">

						<label style="cursor: pointer;" 
							onclick="return CheckHelper.checkAll('tododevid');return false;"><s:text name="selectall" /></label> | 
						<label style="cursor: pointer;" 
							onclick="return CheckHelper.reverseAll('tododevid');return false;"><s:text name="invertselection" /></label> | 
						<label style="cursor: pointer;" 
							onclick="return CheckHelper.cleanAll('tododevid');return false;"
							class="chk-cancle"><s:text name="cancel" /></label>

					</div>
					<a href="#"> <img onclick="querytodoterminal();"
						src="${theme}/skins/default/images/refresh3.gif" border="0"
						class="ref" /></a>

					<div class="page1 page2">
						<%-- <img src="${theme}/skins/default/images/pageB1.gif" id="first" />
						<img src="${theme}/skins/default/images/pageB2.gif" id="previous"/>
						<img src="${theme}/skins/default/images/pageB3.gif" id="next"/>
						<img src="${theme}/skins/default/images/pageB4.gif" id="last" />
						<font><input name="pageIndex" type="text" /><s:text name="page"/>
						</font><img src="${theme}/skins/default/images/pageB5.gif" onclick="gotoPageByIndex()" />
						<span><s:text name="totalpage"/>:<b class="orange1"><span id="totalData"></span></b></span>
						<s:text name="apage"/>						
					
						<span id="currentWithTotal"></span> --%>
					</div>
				</div>

				<!-- 右侧底部menu 结束-->

			</div>



			<!-- 右侧 结束-->

			<div class="clearit"></div>

		</div>

		<div class="di1"></div>

	</div>

	<%@ include file="/buttom.jsp"%>
</body>
</html>