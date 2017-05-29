<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="com.gnamp.server.model.SourceCategory"%>
<%@ page language="java" import="com.gnamp.server.model.SystemSource"%>
<%@ page language="java" import="com.gnamp.struts.vo.CategoryXMLConfig"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=8" />

<style type="text/css">
body {
	overflow-x: hidden; /*隐藏水平滚动条*/
	overflow-y: hidden; /*隐藏垂直滚动条*/
}
</style>
<link href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" />
<script src="${theme}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript"
	src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
<script language="javascript" type="text/javascript"
	src="${theme}/js/niceforms.js"></script>
<link rel="stylesheet" type="text/css" media="all"
	href="${theme}/skins/default/css/niceforms-default.css" />
<script src="${theme}/js/ipub.models-<s:text name="langjs"/>.js"
	type="text/javascript"></script>
<link href="${theme}/skins/default/css/ipubs-dialog.css"
	rel="stylesheet" type="text/css" />



<script type="text/javascript" src="${theme}/js/jquery.jqtransform.js"></script>
<script type="text/javascript" src="${theme}/js/jscroll.js"></script> 

<script src="js/gnamp.js" language="javascript"></script>
<script src="../Pages/js/JScript.js"></script>
<script src="js/gnamp.js" language="javascript"></script>
<script src="js/JScript.js" language="javascript"></script>
<script type="text/javascript">
 SELECT_ELEMENT = "#ee input[type=checkbox]";
</script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#includeChildren").change(function() {
			query();
		});
		
		$(".NFCheck").attr("style", "top:52px; left:765px;");  
	});

	var namesortflag = "asc",
    checktimeflag = "asc",
    sizeflag = "asc",
    titlecheckstate = "asc";
    var currentSortFlag = "desc";
    var currentSortName = "check_time";

    $(function() {
        $p = new page("tag!query", globeParam(), showlistfunc);

       /*  $("#namesort").bind("click", namesort);
        $("#checktimesort").bind("click", checktimesort);
        $("#sizesort").bind("click", sizesort);
        $("#titlecheckstate").bind("click", titleCheckStateSort); */

        $("#first").bind("click", $p.first);
        $("#previous").bind("click", $p.previous);
        $("#next").bind("click", $p.next);
        $("#last").bind("click", $p.last);
        
        querytags();

    });
    
	function nodeclick(pid, obj) {
		var selfid = <%=((SourceCategory)request.getAttribute("sourcecategory")).getCategoryId()%>;
		
		var groupSelector = $("#groupSelector");
		if ($(obj).is('select')) {
			pid = (groupSelector.length > 0) ? groupSelector.val() : 0;
		}
		var param = {
			"categoryId" : pid,
			"selfid" : selfid
		};

		var surl = "";
		if (<%=request.getAttribute("taskType").toString().equals("loop") %>) {
			surl = "looprect!sourcecategorySelector";
		}
		if (<%=request.getAttribute("taskType").toString().equals("demand") %>) {
			surl = "demandrect!sourcecategorySelector";
		}
		if (<%=request.getAttribute("taskType").toString().equals("plugin") %>) {
			surl = "pluginrect!sourcecategorySelector";
		}

		$.ajax({
					type : "post",
					dataType : "json",
					url : surl,
					data : param,
					complete : function() {
					},
					success : function(msg) {
						if (msg.success) {
							try {

								if ($(obj).is('select')) {

									var oldId = groupSelector.val();
									var oldText = groupSelector.find(
											"option:selected").text();

									groupSelector.html("");

									groupSelector.before("<a href=\"javascript:void(0)\" id=\""
													+ oldId
													+ "\" onclick=\"nodeclick("
													+ oldId
													+ ",this)\" >"
													+ oldText
													+ "</a><span id=\""+oldId+"s\">-></span>");

									if (msg.data != null && msg.data.length > 0) {
										var optionHtml = "";

										optionHtml += "<option value=\"0\"><s:text name="select" /></option>";
										$.each(
														msg.data,
														function(i, g) {
															optionHtml += "<option value=\""+g.categoryId+"\">"
																	+ g.categoryName
																	+ "</option>";
														});

										groupSelector.html(optionHtml);
									} else {
										$("#" + oldId + "s").remove();
										groupSelector.remove();
									}

								} else {
									$(obj).nextAll().remove();

									var optionHtml = "";
									if (msg.data != null && msg.data.length > 0) {
										optionHtml += "<span>-></span><select name=\"groupSelector\" onchange=\"nodeclick(0,this)\" id=\"groupSelector\">";
										optionHtml += "<option value=\"0\"><s:text name="select" /></option>";
										$
												.each(
														msg.data,
														function(i, g) {
															optionHtml += "<option title=\""+g.categoryName+"\" value=\""+g.categoryId+"\">"
																	+ (g.categoryName.length > 4 ? g.categoryName
																			.substring(
																					0,
																					4)
																			+ "..."
																			: g.categoryName)
																	+ "</option>";
														});

										optionHtml += "</select>";

										$(obj).after(optionHtml);
									}
								}

							} catch (e) {
								alert(e.message);
							}
						} else {
							alert(msg.success);
						}
						query();
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						 window.top.location.href = '/';
					}
				});
		return false;
	}
  function query() {
		querytags();
	}
	
  function globeParam(inparam) {
	    var param = {
	        "source.catId": ($("#sp a:last")).attr("id"),
	        "sourceType": "image",
	        "includeChildren": $("#includeChildren").attr("checked") ? true : false
	    };
	    if (typeof(inparam) != undefined) {
	        copyParam(param, inparam);
	    }
	    return param;
	}
  
  function querytags(){
	  $p = new page("tag!query", globeParam(), showlistfunc);
	    sortfield("desc", "check_time");
	    return false;
  }
  function sortfield(flag, name) {
	    $p.currentSortManaer = flag;
	    $p.currentSortName = name;
	    $p.ajax($p.globeParam(globeParam()));
	}
  function showlistfunc(result) {
	    try {
	        SELECT_ELEMENT = "#mytab > tbody input[type=checkbox]";

	        
	        var content = "";
	        var thead = "<tr>"  
	        + "<td  class=\"first\" style=\"cursor: pointer;width:80px;\">" + langMap.tagMap.state + "</td>" 
	        + "<td id=\"namesort\" onclick='namesort()' style=\"cursor: pointer; width:380px;\" >" + langMap.fileMap.fileName + "</td>" 
	        + "<td id=\"namesort\"  style=\"cursor: pointer; width:180px;\" >" + "ID" + "</td>" 
	        + "<td id=\"namesort\"  style=\"cursor: pointer;\" >" + langMap.tagMap.type + "</td>" 	        
	        + "</tr>";
	        $.each(result.data, function(i, f) {
	        	var checked = $("input[name=selectsource][value="+f.sourceId+"]").attr("checked");
				var checkedvalue = checked?"checked":"";
	            content += "<tr>";
	            content += "<td style=\"width: 30px;\"><input type=\"checkbox\" class=\"sourceid\" name=\"selectsource\" id=\"" + f.name + "\" value=\"" + f.sourceId + "\" "+checkedvalue+" />" +
	            "</td>";
	            content += "<td style=\"width: 20px;\"><img  src=\"" +
	            (f.state==0?"../skins/default/images/btn2.png\"":"../skins/default/images/btn3.png\"")+
	            		" height=\"15px\" title=\""+(f.state==0?langMap.tagMap.open:langMap.tagMap.close)+"\" width=\"15px\"></td>";
	            content += "<td style=\"width:380px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;\" title=\"" + f.name + "\">" + f.name + "</td>" ;
	            content += "<td style=\"width:180px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;\">" + f.sourceId + "</td>" ;
	            content += "<td style=\"overflow: hidden;text-overflow: ellipsis;white-space: nowrap;\">" + getType(f.type) + "</td>" ;
	            content += " </tr>";

	        });
	        $("#shujBt").remove();
	        $("#ee").html("");
	        $("#ee").before("<table class=\"shujBt\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" id=\"shujBt\"></table>");
	        $("#ee").prepend("<table id=\"mytab\"  style=\"width: 100%;table-layout: fixed;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"shujTb\"><thead></thead><tbody></tbody></table>");
	        $("#shujBt").html(thead);
	        $("#mytab tbody").html(content);

	        $('.shujTb > tbody >tr:odd').addClass('alt');

	        /**
	         * 重置滚动条
	         */
	        ipubs.models.jscroll_ee();
	    } catch (e) {
	        alert(e.message);
	    }
	}
  
  function getType(typeid){
	  if (typeid == undefined) {
			typeid = 0;
		}
		if (typeid == 3) {
			return langMap.tagMap.text;
		} else if (typeid == 1) {
			return langMap.tagMap.picture;
		} else if (typeid == 2) {
			return langMap.tagMap.video;
		} else if (typeid == 4) {
			return langMap.tagMap.audio;
		}else if (typeid == 5) {
			return langMap.tagMap.rss;
		}
	}
  
  var obb = null;
  var isfinished = false;
  
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
  			// this.handle.style.display != "none" ? this.handle.style.top = this.area.scrollTop/(this.area.scrollHeight - this.area.offsetHeight) * (this.bar.offsetHeight - this.handleHeight - this.arrowUpHeight - this.arrowDownHeight) + this.arrowUpHeight + "px" : null; 
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
  	
  	
  	function add(callfunc){
  		var arr = $("#ee input[type='checkbox']");  //document.getElementsByTagName('input');
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
 				}
 			}
 		}

 		  var istag = true;
 	      
 		if (ok > 0) { 			
 			
 			var surl = '';
		    if(<%=request.getAttribute("taskType").toString().equals("loop") %>){
		    	surl = 'looprect!addimagefile';
		    }
		    if(<%=request.getAttribute("taskType").toString().equals("demand") %>){
		    	surl = 'demandrect!addimagefile';
		    }
		    if(<%=request.getAttribute("taskType").toString().equals("plugin") %>){
		    	surl = 'pluginrect!addimagefile';
		    }
 			
				var param = {
						"selectIDs" : selectIDs.substring(0, selectIDs.length - 1),
						"taskID":<%=request.getAttribute("taskID").toString() %>,
						"programID":<%=request.getAttribute("programID").toString() %>,
						"rectID":<%=request.getAttribute("rectID").toString() %>,
						"istag": istag
					     };
				
				ajaxCallback(surl, param, function(txt){					
					 callfunc(txt);
				});
 		}else{
 			alert("<s:text name="items" />");
			return false;
 		}
  	}
  	function executeFunction(txt) {
		if(txt.success){
			callParentFunction("added", 'ok');
			return closeIFrame();
		}else{
			alert(txt.message);
		}
	}	
</script>
</head>
<body>
	<!-- 弹出窗口 开始-->
	<div class="tcWindow">
		<table border="0" cellpadding="0" cellspacing="0" class="tcBt">
			<tr>
				<td style="width: 5px;"><img
					src="${theme}/skins/default/images/tcBt1.png" width="5" height="35"
					class="left" /></td>
				<td class="bg">
					<%--  <img
					src="${theme}/skins/default/images/closed.gif"
					style="float: right; padding-right: 10px;" />   --%>
					<h2></h2>
				</td>
				<td style="width: 5px;"><img
					src="${theme}/skins/default/images/tcBt3.png" width="5" height="35"
					class="right" /></td>
			</tr>
		</table>
		<div class="tcNr">
			<form method="post" class="niceform">
				<div class="tcBt2">
					<table border="0" cellspacing="0" cellpadding="0" style="table-layout: fixed;">
						<tr>
							<td></td>
							<td>
							
							</td>
							<td></td>
							<td style="width: 750px;">
							<span id="sp"> <a
									class="root" href="javascript:void(0)" id="0"
									onclick="nodeclick(0,this)"><s:text name="customer" /></a> <%
								      List<SourceCategory> categorypath =	(List<SourceCategory>)(request.getAttribute("sourcecategorypath"));
								       if(categorypath != null){
								    	for(SourceCategory g : categorypath){             
								        		 if(g.getCategoryId()>0){
								            	  out.print("<span id=\""+g.getCategoryId()+"s\">-></span>");
								            	  out.print("<a href=\"javascript:void(0)\" id=\""+g.getCategoryId()+"\" " + 
								            	   "onclick=\"nodeclick("+g.getCategoryId()+",this)\" >"   + g.getCategoryName() + 
								            	   "</a>");          
								    		   }
								       	   }
								       }
							    
						                List<SourceCategory> brother = (List<SourceCategory>)(request.getAttribute("sourcebrother"));
						                if(brother != null){                	
						                	 out.print("<span id=\""+((SourceCategory)request.getAttribute("sourcecategory")).getCategoryId()+"s\">-></span>");
						                	 out.print("<select size=\"1\" name=\"groupSelector\" onchange=\"nodeclick(0,this)\" id=\"groupSelector\">");
						                	 out.print("<option value=\"0\">");
						                	 %> <s:text name="select" /> <%
						                	 out.print("</option>");
										
							             	for(SourceCategory g : brother){	                		 				 							
										       out.print("<option title=\""+g.getCategoryName()+"\" value=\""+g.getCategoryId()+"\">"
										    		   + (g.getCategoryName().length()>4?g.getCategoryName().substring(0, 4)+"..." : g.getCategoryName()) + "</option>");
							             	}
						             	   out.print("</select>");
						                }
                                       %> <span id="selector"></span>
							</span>
							</td>
							<td style="width:20px;" valign="middle" align="right">
							<span id="check">
							 <input type="checkbox" onchange="query()"
								id="includeChildren" value="true" /> 
								</span>
								</td>
							<td style="width:115px;">
							<span id="child">
							 <s:text name="child" /> </span>
							</td>
						</tr>
					</table>
				</div>
			</form>

			<div id="ee" style="height: 290px; width:100%"></div>

			<div style="height: 30px; background: #d5dcde;">
				<div class="page1 page2"
					style="width: 570px; overflow: hidden; padding-top: 5px;">
					<%@ include file="/template/Public/page.jsp"%>
				</div>
			</div>
			<div class="rightMenu"
						style="height: 20px; margin: 0px; padding: 5px;">
						<ul class="left rightMenuShort">
							<li>
							  <a onclick="return CheckHelper.checkAll('sourceid');return false;" class="CheckLink"><s:text name="selectall" /></a> 
							  </li>
							  <li>
                                 <a onclick="return CheckHelper.reverseAll('sourceid');return false;" class="CheckLink"><s:text name="invertselection" /></a>  
                                 </li>
                               <li>  <a onclick="return CheckHelper.cleanAll('sourceid');return false;" class="CheckLink"><s:text name="cancel" /></a></li>
                                <li>  <a onclick="return add(executeFunction);return false;" class="CheckLink"><s:text name="add" /></a></li>
						
						</ul>
					</div>
			<div style="clear: both; height: 1px; overflow: hidden;"></div>

		</div>
		<table border="0" cellpadding="0" cellspacing="0" class="tcDi">
			<tr>
				<td style="width: 5px;"><img
					src="${theme}/skins/default/images/tcDi1.png" width="5" height="8"
					class="left" /></td>
				<td class="bg"></td>
				<td style="width: 5px;"><img
					src="${theme}/skins/default/images/tcDi2.png" width="5" height="8"
					class="right" /></td>
			</tr>
		</table>
	</div>
	<!-- 弹出窗口 结束-->
	<!-- 表格间隔变色 -->
	<script>
		// Give classes to even and odd table rows for zebra striping.
		$(document).ready(function() {
			// $('tr:odd').addClass('alt');
			$('tr:nth-child(even)').addClass('alt');

			$('td:contains(Henry)').nextAll().andSelf().addClass('highlight');//.siblings()
			$('th').parent().addClass('table-heading');
		});
	</script>
</body>
</html>

