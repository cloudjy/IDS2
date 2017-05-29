<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="com.gnamp.server.model.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	LoopTask loop = (LoopTask) request.getAttribute("looptask");

   Calendar ca = Calendar.getInstance(); 
   ca.add(Calendar.MONTH, 1);// 7为增加的天数，可以改变的
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<style type="text/css">
body {
	overflow-x: hidden; /*隐藏水平滚动条*/
	overflow-y: hidden; /*隐藏垂直滚动条*/
}

.wraper {
	/* position: relative; */
}

.list {
	width: 200px;
	height: 200px;
	overflow: auto;
	position: absolute;
	border: 1px solid #617775;
	display: none;
	background: none repeat scroll 0 0 #ffffff;
	float: left;
	z-index: 1000000;
}

.list ul li {
	padding-left: 5px;
	padding-top: 2px;
	padding-bottom: 2px;
	height: 20px;
	/* border-top: 1px solid black;  */
}

ul {
	list-style: none outside none;
}
</style>


<script src="Dialog/My97DatePicker/WdatePicker.js" language="javascript"></script>

<link rel="stylesheet" type="text/css"
	href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" />
<link href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" href="${theme}/skins/default/css/jqtransform.css"
	media="all" />

<script type="text/javascript" src="${theme}/js/jquery-1.7.2.min.js"></script>
<script language="javascript" type="text/javascript"
	src="${theme}/js/niceforms.js"></script>
<script type="text/javascript" src="${theme}/js/jquery.jqtransform.js"></script>
<script type="text/javascript" src="${theme}/js/jscroll.js"></script>
<script type="text/javascript"
	src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
<script type="text/javascript"
	src="${theme}/js/ipub.models-<s:text name="langjs"/>.js"></script>
<link href="${theme}/skins/default/css/ipubs-dialog.css"
	rel="stylesheet" type="text/css" />

<script src="js/gnamp.js" language="javascript"></script>
<script src="../Pages/js/JScript.js"></script>

<script type="text/javascript">
var dex=0;
(function ($) {
    $.fn.extend({
        MultDropList: function (options) {
            var op = $.extend({ wraperClass: "wraper", width: "150px", height: "200px", data: "", selected: "" }, options);
            return this.each(function () {
                var $this = $(this); //指向TextBox
                var $hf = $(this).next(); //指向隐藏控件存
                var conSelector = "#" + $this.attr("id") + ",#" + $hf.attr("id");
                var $wraper = $(conSelector).wrapAll("<div><div></div></div>").parent().parent().addClass(op.wraperClass);

                var $list = $('<div class="list"></div>').appendTo($wraper);
                $list.css({ "width": op.width, "height": op.height });
                //控制弹出页面的显示与隐藏
                $this.click(function (e) {
                	// var tbDiv = $("#tableDiv"); 
                	var tbBottom = $(window).height(); //tbDiv.offset().top + tbDiv.outerHeight();
                	var inputTop = $this.offset().top;
                	var inputBottom = inputTop + $this.outerHeight(true);  
                	
                	var listHeight = $list.outerHeight(true);     
                	var listTop = (inputBottom + listHeight > tbBottom)? inputTop - listHeight : inputBottom; 
                	   
                	$list.css("top", "" + listTop + "px");
                    $("div .list").hide();
                    $list.toggle();
                    e.stopPropagation();
                });

                $(document).click(function () {
                	$("div .list").hide();
                });

                $list.filter("*").click(function (e) {
                    e.stopPropagation();
                });
                	               
                //加载默认数据	              
	            $list.append('<ul></ul>');
	          
                var $ul = $list.find("ul");

                //加载json数据
                var listArr = op.data.split("|");
                
                if(listArr.length>0){
                	if(eval("(" + listArr[0] + ")").k>100){
                		 $ul.append('<li class=\"il\"><input type="checkbox" class="selectAll" value="all" /><span id="all"><s:text name="all" /></span></li>');
                	}else{
                		 $ul.append('<li class=\"il\"><input type="checkbox" class="selectAll" value="all" /><span id="all"><s:text name="every" /></span></li>');
                	}
				}

                var escapeJQueryDiv =  $("<div/>"); 
                
                var jsonData;
                for (var i = 0; i < listArr.length; i++) {
                    jsonData = eval("(" + listArr[i] + ")");
                	var nameHtml = escapeJQueryDiv.text(jsonData.v).html();	                	
                    $ul.append('<li class=\"il\" ' + 
                            'style="width:130px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">' + 
                            '<input type="checkbox" value="' + jsonData.k + '" />' + 
                            '<span title="'+ nameHtml +'" >' + nameHtml + '</span></li>');
                }

                //加载勾选项
                var seledArr;
                if (op.selected.length > 0) {
                    seledArr = selected.split(",");
                }
                else {
                    seledArr = $hf.val().split(",");
                }

                $.each(seledArr, function (index) {
                    $("li input[value='" + seledArr[index] + "']", $ul).attr("checked", "checked");

                    var vArr = new Array();
                    $("input[class!='selectAll']:checked", $ul).each(function (index) {
                        vArr[index] = $(this).next().text();
                    });
                    $this.val(vArr.join(","));
                });
                //全部选择或全不选
                $("li:first input", $ul).click(function () {
                    if ($(this).attr("checked")) {
                        $("li input", $ul).attr("checked", "checked");
                    }
                    else {
                        $("li input", $ul).removeAttr("checked");
                    }
                });
                //点击其它复选框时，更新隐藏控件值,文本框的值
                $("input", $ul).click(function () {
                    var kArr = new Array();
                    var vArr = new Array();
                    $("input[class!='selectAll']:checked", $ul).each(function (index) {
                        kArr[index] = $(this).val();
                        vArr[index] = $(this).next().text();
                    });
                    $hf.val(kArr.join(","));
                    $this.val((vArr.join(",").length>10?(vArr.join(",").substring(0,10)+"...") : vArr.join(",")));
                    	                   
                    if(dex==3){
                      	$("#hfTest2").val(kArr.join(","));
                    } else if(dex==2){
                    	$("#hfTest1").val(kArr.join(","));    
                    } else if(dex=="batch_weekdays") {
                    	var tmp = (kArr.join(",").length>8?(kArr.join(",").substring(0,8)+"...") : kArr.join(","));
                    	$("#batch_weekdays").val(kArr.join(","));
                    } else{
                    	var tmp = (kArr.join(",").length>8?(kArr.join(",").substring(0,8)+"...") : kArr.join(","));
                    	$("#hfTest").val(kArr.join(","));
                    }                 
                });
            });
        }
    });
})(jQuery);

	$(document).ready(function() {
		$("#startdate").focus(function() {
			WdatePicker({
				startDate : '%y-%M-01',
				dateFmt : 'yyyy-MM-dd',
				lang : '<s:text name="lang" />',
				alwaysUseStartDate : true
			});
		});
		$("#stopdate").focus(function() {
			WdatePicker({
				startDate : '%y-%M-01',
				dateFmt : 'yyyy-MM-dd',
				lang : '<s:text name="lang" />',
				alwaysUseStartDate : true
			});
		});

		$("#starttime").focus(function() {
			WdatePicker({
				startDate : '%H-%n-%s',
				dateFmt : 'HH:mm:ss',
				lang : '<s:text name="lang" />',
				alwaysUseStartDate : true
			});
		});
		$("#stoptime").focus(function() {
			WdatePicker({
				startDate : '%H-%m-%s',
				dateFmt : 'HH:mm:ss',
				lang : '<s:text name="lang" />',
				alwaysUseStartDate : true
			});
		});

		$("#batch_startdate").focus(function() {
			WdatePicker({
				startDate : '%y-%M-01',
				dateFmt : 'yyyy-MM-dd',
				lang : '<s:text name="lang" />',
				alwaysUseStartDate : true
			});
		});
		$("#batch_stopdate").focus(function() {
			WdatePicker({
				startDate : '%y-%M-01',
				dateFmt : 'yyyy-MM-dd',
				lang : '<s:text name="lang" />',
				alwaysUseStartDate : true
			});
		});
		
		$("#starttime1").focus(function() {
			WdatePicker({
				startDate : '%H-%n-%s',
				dateFmt : 'HH:mm:ss',
				lang : '<s:text name="lang" />',
				alwaysUseStartDate : true
			});
		});
		$("#stoptime1").focus(function() {
			WdatePicker({
				startDate : '%H-%m-%s',
				dateFmt : 'HH:mm:ss',
				lang : '<s:text name="lang" />',
				alwaysUseStartDate : true
			});
		});

		var param = {"looptask.taskId" : <%= loop.getTaskId() %>};   		
		var surl = "date!readall";		
		$p = new page(surl,param,showlist);	
		$p.ajax();						
		$("#first").bind("click",$p.first);
		$("#previous").bind("click",$p.previous);
		$("#next").bind("click",$p.next);
		$("#last").bind("click",$p.last); 		
		
		 $("#txtTest").MultDropList({ data: $("#hfddlList").val() });
		 $("#batch_weekselect").MultDropList({ data: $("#hfddlList").val() });

		 ddldata(); 

		 defaultSeleteAllDay();
	});

	function defaultSeleteAllDay() {  
		 try {  
			 $("#txtTest").triggerHandler('focus');
			 $("#txtTest").parent().parent().find(  
					"ul li:first input:checkbox").attr('checked', true).triggerHandler('click'); 
		 } catch (exp1) {
			 ipubs.alert(exp1.message);
		 }
		 try {
			 $("#batch_weekselect").triggerHandler('focus');
		 	 $("#batch_weekselect").parent().parent().find(
				 	"ul li:first input:checkbox").attr('checked', true).triggerHandler('click'); 
		 } catch (exp2) {
			 ipubs.alert(exp2.message);
		 }  
		return false;
	}
	
	var prgms = "";	
	
	function clearselect(){
		try{
			var $ul = $(".list").find("ul");
			$("li input", $ul).removeAttr("checked");
			$("#txtTest").val("");
			$("#batch_weekselect").val("");
			$("#txtTestone").val("");
			$("#txtTesttwo").val("");
			
		}catch(e){
			
		}
	}
	
	function setvalue(str){
		dex = str;
	}
	
	function ddldata(){
		var param = {
				"loopprogram.taskId" : <%=loop.getTaskId() %>
			};
			ajaxCallback("loopprogram!looplist", param, function(msg){
				if (msg.success) {
					try {					
						if(msg.data != null && msg.data != undefined){ 
			                var programPrompt = '<s:text name="program_prompt"/>';   
							$.each(msg.data, function(i, p) {
								prgms += "{k:"+p.programId+",v:\""+ (programPrompt + p.programName) +"\"}|";																	
							 });
						   }
						if(prgms != null && prgms.length>0){
							prgms = prgms.substring(0, prgms.length-1);
						    $("#txtTestone").MultDropList({ data: prgms });
						    $("#txtTesttwo").MultDropList({ data: prgms });
						}
					} catch (e) {
						alert(e.message);
					}
				} else {
					alert(msg.success);
				}
			});
			return false;
	}
	
	function add(callfunc){
		if($("#startdate").val() == '' || $("#startdate").val() == 'undefined'
				|| $("#stopdate").val() == '' || $("#stopdate").val() == 'undefined'
				|| $("#hfTest").val() == '' || $("#hfTest").val() == 'undefined'
				|| $("#hfTest1").val() == '' || $("#hfTest1").val() == 'undefined'
					|| $("#starttime").val() == '' || $("#starttime").val() == 'undefined'
						|| $("#stoptime").val() == '' || $("#stoptime").val() == 'undefined'){
			alert("<s:text name="notnull" />");
			return;
		}	
		
		ajaxCallback('date!add',
					{
					"strategy.taskId":<%=loop.getTaskId() %>,
					"strategy.startDate":$("#startdate").val(),
					"strategy.stopDate":$("#stopdate").val(),
					"startspan":$("#starttime").val(),
					"stopspan":$("#stoptime").val(),
					"week":$("#hfTest").val(),
					"prgms":$("#hfTest1").val()
		            },
		            callfunc);	
	}
	
	function addmore(callfunc){
		if($("#batch_startdate").val() == '' || $("#batch_startdate").val() == 'undefined'
			|| $("#batch_stopdate").val() == '' || $("#batch_stopdate").val() == 'undefined'
			|| $("#batch_weekdays").val() == '' || $("#batch_weekdays").val() == 'undefined'
			|| $("#hfTest2").val() == '' || $("#hfTest2").val() == 'undefined'
				|| $("#starttime1").val() == '' || $("#starttime1").val() == 'undefined'
					|| $("#stoptime1").val() == '' || $("#stoptime1").val() == 'undefined'){
			alert("<s:text name="notnull" />");
			return;
		}	
	
		ajaxCallback('date!addmore',
				{
				"strategy.taskId":<%=loop.getTaskId() %>,
				"strategy.startDate":$("#batch_startdate").val(),
				"strategy.stopDate":$("#batch_stopdate").val(),
				"startspan":$("#starttime1").val(),
				"stopspan":$("#stoptime1").val(),
				"minute":$("#minute").val(),
				"second":$("#second").val(),
				"minute1":$("#minute1").val(),
				"second1":$("#second1").val(),
				"week":$("#batch_weekdays").val(),
				"prgms":$("#hfTest2").val(),
				"strCount": $("#strCount").val()
	            },
	            callfunc);	
	}
	
	function executeFunction(msg) {		
		if (msg.success) {			
			queryall(false);
			clearselect();
			defaultSeleteAllDay();
		} else {
			if(msg != null && msg.data != null){
				var temp = "";
				$.each(msg.data,function(i, p) {
					var arr = $("input[class='strategyId']");  
			   		for (var j = 0; j < arr.length; j++) {
			   			var abc = arr[j];
			   			if (abc.type == "checkbox") {
			   				if (abc.value==p.strategyId) {		    				   									
			   					temp+=abc.id+",";
			   				}
			   			}
			   		}
				});
				if(temp.length>1){
				  temp = temp.substring(0,temp.length-1);
				  alert("<s:text name="adderror" />"+temp+"<s:text name="ff" />");
				}else{
					alert(msg.data);
				}
			}
			else{
				alert(msg.data);
			}
			clearselect();
			defaultSeleteAllDay();
		};
	}
	
	var remember=null;
	var chkary = null;
	var l=0;
	$(function(){
		queryall(false);
	});
	
	function showlist(msg){
		 chkary = new Array();
		try {
			var content = "";		
			
			Date.prototype.format = function(format)
		       {
		        var o = {
		        "M+" : this.getMonth()+1, //month
		        "d+" : this.getDate(),    //day
		        "h+" : this.getHours(),   //hour
		        "m+" : this.getMinutes(), //minute
		        "s+" : this.getSeconds(), //second
		        "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
		        "S" : this.getMilliseconds() //millisecond
		        };
		        if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
		        (this.getFullYear()+"").substr(4 - RegExp.$1.length));
		        for(var k in o)if(new RegExp("("+ k +")").test(format))
		        format = format.replace(RegExp.$1,
		        RegExp.$1.length==1 ? o[k] :
		        ("00"+ o[k]).substr((""+ o[k]).length));
		        return format;
		       };
			
					if(msg.data != null && msg.data != undefined){
						$.each(
								msg.data,
								function(i, p) {
									l++;
									var checked = false;
									if (remember) {
										for ( var i = 0; i < remember.length; i++) {
											if (remember[i] == p.strategyId) {
												checked = true;
												break;
											}
										}
									}										      
						      content += " <tr id=\""
									+ p.strategyId
									+ "\">"
									+ " <td style=\"width: 12px;\"><label><input value=\""
									+ p.strategyId  
									+ "\" name=\""+p.strategyId+"\" " 
									+ (checked? " checked ":" ") + " id=\""+(i+1)+"\" class=\"strategyId\"  type=\"checkbox\"/></label></td>"														
									+ " <td style=\"width: 3px; align: left;\">"
									+ "<span style=\"margin-left:-5px;\" color=\"#000000\">"+(i+1)+"</span>"
									+ " </td>"
									+ " <td style=\"width: 170px;\">"
									+ "<input onfocus=\"WdatePicker({startDate : '%y-%M-01',dateFmt : 'yyyy-MM-dd',lang : '<s:text name="lang" />',alwaysUseStartDate : true});\" style=\"width: 70px; background:transparent;border:1px solid #FFFFFF; cursor: pointer;\" id=\"db"+i.toString()+"\" value=\""+(new Date(Date.parse(p.startDate.replace(/-/g,   "/")))).format('yyyy-MM-dd')+"\" />"
									+ " </td>"	
									+ " <td style=\"width: 190px;\">"
									+ "<input onfocus=\"WdatePicker({startDate : '%y-%M-01',dateFmt : 'yyyy-MM-dd',lang : '<s:text name="lang" />',alwaysUseStartDate : true});\" style=\"width: 70px; background:transparent;border:1px solid #ffffff; cursor: pointer;\" id=\"de"+i.toString()+"\" value=\""+(new Date(Date.parse(p.stopDate.replace(/-/g,   "/")))).format('yyyy-MM-dd')+"\" />"
									+ " </td>"		
									+ " <td id=\"td"+i+"\" style=\"width: 200px;\">"
									+ " <input onfocus=\"setvalue(1);\" id=\"txt"+i+"\" style=\"width: 140px; background:transparent;border:1px solid #ffffff; cursor: pointer;\" /> <input "
									+ " type=\"hidden\" id=\"week"+i.toString()+"\" />" //p.weekDays
									+ " </td>"	
									+ " <td style=\"width: 170px;\">"
									+ "<input onfocus=\"WdatePicker({startDate : '%y-%M-01',dateFmt : 'HH:mm:ss',lang : '<s:text name="lang" />',alwaysUseStartDate : true});\" style=\"width: 70px; background:transparent;border:1px solid #ffffff; cursor: pointer;\" id=\"tb"+i.toString()+"\" value=\""+gettime((p.startTime))+"\" />"
									+ " </td>"	
									+ " <td style=\"width: 170px;\">"
									+ "<input onfocus=\"WdatePicker({startDate : '%y-%M-01',dateFmt : 'HH:mm:ss',lang : '<s:text name="lang" />',alwaysUseStartDate : true});\" style=\"width: 70px; background:transparent;border:1px solid #ffffff; cursor: pointer;\" id=\"te"+i.toString()+"\" value=\""+gettime((p.stopTime+1))+"\" />"
									+ " </td>"		
									+" <td style=\"width: 235px;\" title=\""+getall(true, p.programs)+"\" >"													
									+"<input value=\""+getall(false, p.programs)+"\" style=\"width: 120px; background:transparent;border:1px solid #ffffff; cursor: pointer;\" id=\"prgm"+i.toString()+"\" onfocus=\"edit("+p.taskId+","+p.strategyId+");\" > "
									  
									+"</input>"
									+"</td>"	
									+" <td style=\"height:20px; width: 65px;\">"
									+" <div style=\"float: left; height:20px; margin-left:10px; margin:0px;\">";
									 var bros = navigator.appName;
									   
									   if(bros=="Microsoft Internet Explorer"){ //IE 
									
										   content += "<div class=\"rightMenu\" style=\"height:20px; margin-top:5px; padding:0px;\">";
									   }else{
										   content += "<div class=\"rightMenu\" style=\"height:20px; margin-top:0px; padding:0px;\">";
									   }
									   content += "<ul class=\"left rightMenuShort\">"
				                    + "<li>"
									+ "<a style=\"cursor: pointer;\" id=\"save"+i.toString()+"\" onclick=\"save("+p.strategyId+","+i.toString()+");\" ><s:text name="save" /></a>"
									+ "</li>"
									+ "</ul>"
									+ "</div>"
									+ "</div>"
									+"</td>"	
								 	+ " </tr>";		
								 	
								 	chkary.push(p.weekDays);
								});
					     	}	
					
					// $("#txtTest").MultDropList({ data: $("#hfddlList").val() });
					 
					$("#mytab").html(content);
					
					$('.shujTb > tbody >tr:odd').addClass('alt');
					
					/**
					 * 重置滚动条
					 */
					ipubs.models.jscroll_ee();
				
		} catch (e) {
			alert(e.message);
		}
		
		for(var o=l;o>=0;o--){				
			$("#txt"+o+"").MultDropList({ data: $("#hfddlList").val() });

			var tmp="";		
			var week="";
			
			//$("li input").attr("checked", false);
			
			if((0x01 & chkary[o])>0){
				tmp+="<s:text name="mon" />,";
				week+=(0x01).toString()+",";
				//$("li input[value='" + (0x01) + "']").attr("checked", "checked");
			}							
			if((0x01<<1 & chkary[o])>0){
				tmp+="<s:text name="tues" />,";
				week+=(0x01<<1).toString()+",";
				//$("li input[value='" + (0x01<<1) + "']").attr("checked", "checked");
			}
			if((0x01<<2 & chkary[o])>0){
				tmp+="<s:text name="weds" />,";
				week+=(0x01<<2).toString()+",";
				//$("li input[value='" + (0x01<<2) + "']").attr("checked", "checked");
			}
			if((0x01<<3 & chkary[o])>0){
				tmp+="<s:text name="thurs" />,";
				week+=(0x01<<3).toString()+",";
				//$("li input[value='" + (0x01<<3) + "']").attr("checked", "checked");
			}
			if((0x01<<4 & chkary[o])>0){
				tmp+="<s:text name="fri" />,";
				week+=(0x01<<4).toString()+",";
				//$("li input[value='" + (0x01<<4) + "']").attr("checked", "checked");
			}
			if((0x01<<5 & chkary[o])>0){
				tmp+="<s:text name="sat" />,";
				week+=(0x01<<5).toString()+",";
				//$("li input[value='" + (0x01<<5) + "']").attr("checked", "checked");
			}
			if((0x01<<6 & chkary[o])>0){
				tmp+="<s:text name="sun" />,";
				week+=(0x01<<6).toString()+",";
				//$("li input[value='" + (0x01<<6) + "']").attr("checked", "checked");
			}				
			$("#txt"+o+"").val(tmp.substring(0, tmp.length-1));	
			$("#week"+o+"").val(week.substring(0, week.length-1));	
			$("#td"+o+"").attr("title",tmp.substring(0, tmp.length-1));		
			$("#txt"+o+"").attr("title",tmp.substring(0, tmp.length-1));										
		}
	}
	
		function queryall(remember) {
			var param = {
					"looptask.taskId" : <%= loop.getTaskId() %>
				};   
			
			var surl = "date!readall";
	     
			$p.ajax();
	   		return false;
		}
		
		 function save(strategyId,id){
			 var param={
					 "strategy.taskId": <%= loop.getTaskId() %>,
					 "strategy.strategyId": strategyId,
					 "strategy.startDate": $("#db"+id+"").val(),
					 "strategy.stopDate": $("#de"+id+"").val(),
					 "startspan":$("#tb"+id+"").val(),
					 "stopspan":$("#te"+id+"").val(),
					 "week":$("#week"+id+"").val()
			 };
			 
			  $.ajax({
	    			type : "post",
	    			dataType : "json",
	    			url : "date!save",
	    			data : param,
	    			complete : function() {
	    				//compliteCallback();
	    			},
	    			success : function(msg) {    				
	    				if(msg.success){
	    					queryall(false);
	    					alert("<s:text name="savesucc" />");
	    				}
	    				else{
	    					if(msg != null && msg.data != null){
	    						var temp = "";
		    					$.each(msg.data,function(i, p) {
		    						var arr = $("input[class='strategyId']");  
		    				   		for (var j = 0; j < arr.length; j++) {
		    				   			var abc = arr[j];
		    				   			if (abc.type == "checkbox") {
		    				   				if (abc.value==p.strategyId) {		    				   									
		    				   					temp+=abc.id+",";
		    				   				}
		    				   			}
		    				   		}
		    					});
		    					if(temp.length>1){
		    					  temp = temp.substring(0,temp.length-1);
		    					  alert("<s:text name="saveerror" />"+temp+"<s:text name="ff" />");
		    					}else{
		    						alert("<s:text name="savefail" />");
		    					}
	    					}
	    					else{
		    					alert("<s:text name="savefail" />");
	    					}
	    					queryall(false);
	    				}
	    			},
	    			error : function(XMLHttpRequest, textStatus, errorThrown) {
	    				 window.top.location.href='/';
	    			}
	    		});
	    		return false;
		 }
		 
		 function edit(taskId, strategyId){
			 showIPUBDialogStand("date!preprgm"+"?strategy.taskId="+taskId+"&strategy.strategyId="+strategyId,{title:"",width:988,height:570,beforeClose:function(){
				 queryall(false);
				}});						
 			
		 }
		
		function getall(all, prgms){
			var temp = "";
			$.each(prgms,function(i, p) {
				temp+= (p.playCount>0 ? (p.programName+"x"+p.playCount+"") : p.programName)+"、";
			}
			);
			
			temp = temp.substring(0,temp.length-1);
			if(all){
				return temp;
			}else
			return (temp.length>8 ? (temp.substring(0,8)+"...") : temp ) ;
			
		}
		
		function gettime(time){
		       //时间为：2011-7-7 17:16:55
		 var d = new Date(time*1000);
         var hour = ( parseInt(time/3600) < 10 ? ("0"+parseInt(time/3600)) : parseInt(time/3600));
         var minute = ( parseInt((time%3600)/60) < 10 ? ("0"+ parseInt((time%3600)/60)) :  parseInt((time%3600)/60));
		 var second = ( parseInt(time%3600)%60 < 10 ? ("0"+ parseInt(time%3600)%60) :  parseInt(time%3600)%60 );
         return  hour + ":" + minute + ":" +second; 
	  }
	function del(msg) {
	   		var arr = $("input[class='strategyId']");  
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
	   		
	   		if (ok > 0) {  //
	   			if (msg != null && msg.length > 0) {
	   	    				
	   				confirm(msg,function(){
	   					var param = {
	       						"selectIDs" : selectIDs.substring(0, selectIDs.length - 1),	
	       						"strategy.taskId" : <%=loop.getTaskId() %>
	       					     };
	       				
	       					$.ajax({
	       						type : "post",
	       						dataType : "json",
	       						url : "date!delete",
	       						data : param,
	       						complete : function() {
	       						},
	       						success : function(msg) {
	       							if (msg.success) {    								
	       								queryall(false);
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
	   			}
		} else {
			alert("<s:text name="no_select_schedul" />");
			return false;
		}

	}
	
 	function view(){
 		showIPUBDialogStand("date!toview"+"?looptask.taskId="+<%=loop.getTaskId() %>,{title:"",width:988,height:570});		
 	}
	function xml(){
 		
	 	<%-- showIPUBDialogStand("date!xml"+"?looptask.taskId="+<%=loop.getTaskId() %>,{title:"",width:988,height:570});		
		 --%>
		 
				confirm("<s:text name="isclear" />",function(){
					var param = {							 					
							"looptask.taskId" : <%=loop.getTaskId() %>
						     };
					
					ajaxCallback("date!clear", param, function(msg){
						if (msg.success) {
							queryall(false);
						} else {
							alert(msg.data);
						}
					});
					
					
				});
		   
	}
</script>
</head>
<body>

<!-- 弹出窗口 开始-->
<div class="tcWindow" style="width:970px;">
    <table border="0" cellpadding="0" cellspacing="0" class="tcBt">
      <tr><td style="width:5px;"><img src="${theme}/skins/default/images/tcBt1.png" width="5" height="35" class="left" /></td>
    <td class="bg">
   
					<h2><s:text name="title_schedule_page"/></h2>
    </td><td  style="width:5px;"><img src="${theme}/skins/default/images/tcBt3.png" width="5" height="35" class="right"/></td>
    </tr></table>
	<div class="tcNr">
	<form action="">
	<input type="hidden" id="hfddlList"
		value='{k:0x01,v:"<s:text name="mon" />"}|{k:0x01 << 1,v:"<s:text name="tues" />"}|{k:0x01 << 2,v:"<s:text name="weds" />"}|{k:0x01 << 3,v:"<s:text name="thurs" />"}|{k:0x01 << 4,v:"<s:text name="fri" />"}|{k:0x01 << 5,v:"<s:text name="sat" />"}|{k:0x01 << 6,v:"<s:text name="sun" />"}' />
	<table style="width: 100%; height: 100%;" border="0" cellpadding="0"
		cellspacing="0" class="shujTb shujTb2"> 
	<tr>
		<td width="50%"> 
			<table style="width: 100%; height: 100%;" border="0" cellpadding="0"
				cellspacing="0" class="shujTb shujTb2">
				<tr class="alt">
				<td>
					<div style="position: relative; margin: auto;">
						<div style="float: left;"> 
							<s:text name="prgmlist" /> 
						</div>
						<div style="float: left; margin-left:5px; margin-top:3px;">
						  <input onfocus="setvalue(2);" id="txtTestone" style="width: 150px; BACKGROUND-IMAGE: url(./img/bselect.png); BACKGROUND-REPEAT: repeat;"></input> 
						  <input type="hidden" id="hfTest1" value="" />
						</div>
						<div class="rightMenu" style="float: left;height:20px; margin:0px; padding-top: 5px;padding-left: 3px;padding-right: 1px;">
	                    	<ul class="left rightMenuLong">
	                    		<li> <a onclick="add(executeFunction);"><s:text name="add_style1" /></a> </li>
	                    	</ul>
			            </div>	
						<div style="float: left;">
							<s:text name="this_schedule" /> 
						</div>	
					</div>
				</td>
				</tr>
				<tr class="alt">
					<td>
						<div style="position: relative; margin: auto;">
							<div style="float: left;">
								 &nbsp;<s:text name="dates" />
								 <s:text name="from" /> <input value="<%=new SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>" style="width: 70px;" id="startdate"></input> 
								 <s:text name="to" /> <input value="<%=new SimpleDateFormat("yyyy-MM-dd").format(ca.getTime()) %>" style="width: 70px;" id="stopdate"></input>
								 &nbsp;&nbsp;<s:text name="weeks" />
							</div> 
							<div style="float: left; margin-left:5px; margin-top:3px;">
								<input onfocus="setvalue(1);" id="txtTest" style="width: 150px; BACKGROUND-IMAGE: url(./img/bselect.png); BACKGROUND-REPEAT: repeat;"></input>
								<input type="hidden" id="hfTest" value="" />
							</div>
						</div>
					</td>
				</tr>
				<tr class="alt">
					<td>
						<div style="position: relative; margin: auto;">
							<div style="float: left;">
								 &nbsp;<s:text name="times" />
								 <s:text name="from" /> <input value="00:00:00" style="width: 70px;" id="starttime"></input> 
								 <s:text name="to" /> <input value="23:59:59" style="width: 70px;" id="stoptime"></input>
							</div> 
						</div>
					</td>
				</tr> 
				<tr class="alt">
					<td>
						<div style="position: relative; margin: auto;"> 
						</div>
					</td>
				</tr> 
			</table>
		</td>
		
		<td width="50%"> 
			<table style="width: 100%; height: 100%;" border="0" cellpadding="0"
				cellspacing="0" class="shujTb shujTb2"> 
				<tr class="alt">
				<td>
					<div style="position: relative; margin: auto;">
						<div style="float: left;"> 
							<s:text name="jmd" /> 
						</div>
						<div style="float: left; margin-left:5px; margin-top:3px;"> 
							<input onfocus="setvalue(3);" id="txtTesttwo" style="width: 150px; BACKGROUND-IMAGE: url(./img/bselect.png); BACKGROUND-REPEAT: repeat;"></input>
							<input type="hidden" id="hfTest2" value="" /> 
						</div>
						<div class="rightMenu" style="float: left;height:20px; margin:0px; padding-top: 5px;padding-left: 3px;padding-right: 1px;">
                   			<ul class="left rightMenuLong">
                    			<li> <a onclick="addmore(executeFunction);"><s:text name="add_style2" /></a> </li>
                    		</ul>
                    	</div>	
						<div style="float: left;">
							<s:text name="this_schedule" /> 
						</div>
					</div> 
				</td>
				</tr>
				<tr class="alt">
					<td>
						<div style="position: relative; margin: auto;">
							<div style="float: left;">
								 &nbsp;<s:text name="dates" />
								 <s:text name="from" /> <input value="<%=new SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>" style="width: 70px;" id="batch_startdate"></input> 
								 <s:text name="to" /> <input value="<%=new SimpleDateFormat("yyyy-MM-dd").format(ca.getTime()) %>" style="width: 70px;" id="batch_stopdate"></input>
								 &nbsp;&nbsp;<s:text name="weeks" />
							</div> 
							<div style="float: left; margin-left:5px; margin-top:3px;"> 
								<input onfocus="setvalue('batch_weekdays');" id="batch_weekselect" style="width: 150px; BACKGROUND-IMAGE: url(./img/bselect.png); BACKGROUND-REPEAT: repeat;"></input>
								<input type="hidden" id="batch_weekdays" value="" />
							</div>
						</div>
					</td>
				</tr>
				<tr class="alt">
					<td>
						<div style="position: relative; margin: auto;">
							<div style="float: left;">
								<div style="float: left;">
									&nbsp;<s:text name="times" />
									<s:text name="from" /> <input value="00:00:00" style="width: 70px;" id="starttime1"></input> 
									<s:text name="to" /> <input value="23:59:59" style="width: 70px;" id="stoptime1"></input>  
								</div>  
							</div>
						</div>
					</td>
				</tr>
				<tr class="alt">
					<td>
						<div style="position: relative; margin: auto;">
							<div style="float: left;">
								<div style="float: left;">
									<s:text name="each" /> <input id="minute" value="30" style="width: 25px;" onkeypress="if ((event.keyCode<48 || event.keyCode>57) && event.keyCode!='.') event.returnValue=false;"></input> 
									<s:text name="fen" /> <input id="second" value="00" style="width: 25px;" onkeypress="if ((event.keyCode<48 || event.keyCode>57) && event.keyCode!='.') event.returnValue=false;"></input>
									<s:text name="miao" /><s:text name="douhao" />
									<s:text name="bofang" /> <input id="minute1" value="15" style="width: 25px;" onkeypress="if ((event.keyCode<48 || event.keyCode>57) && event.keyCode!='.') event.returnValue=false;"></input>
									<s:text name="fen" /> <input id="second1"  value="00" style="width: 25px;" onkeypress="if ((event.keyCode<48 || event.keyCode>57) && event.keyCode!='.') event.returnValue=false;"></input>
									<s:text name="miao" />
									<s:text name="text_or" /> 
									<input id="strCount" value="<s:text name="buxian" />" style="width: 25px;" onkeypress="if ((event.keyCode<48 || event.keyCode>57) && event.keyCode!='.') event.returnValue=false;"></input>
									<s:text name="strcount" />
									<s:text name="first_come_prevail" /> 
								</div>  
							</div>
						</div>
					</td>
				</tr> 
			</table>
		</td>
	</tr>
	</table>
		
	</form>
	<div style="clear:both; height:5px; overflow:hidden;"></div>
     <div>
     		 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="shujBt shujBt2">
                    <tr>
                        <td class="first" style="width: 60px;"><s:text name="xuhao" /></td>
                        <td style="width: 240px;"><s:text name="startdate" /></td>
                        <td style="width: 250px;"><s:text name="enddate" /></td>
                        <td style="width: 340px;"><s:text name="weekdays" /></td>
                        <td style="width: 230px;"><s:text name="starttime" /></td>
                        <td style="width: 230px;"><s:text name="endtime" /></td>
                        <td style="width: 500px;"><s:text name="prgms" /></td> 
                    </tr>
      </table>
	<div id="tableDiv" style="height: 240px; overflow: auto; border-right: #ccc solid 2px;">
		<table id="mytab" width="100%" border="0"
		 cellspacing="0" cellpadding="0" class="shujTb shujTb2">
                      
      </table>
      
      </div>
      <div style="height:30px; background:#d5dcde;">
			     <div class="page1 page2" style="width:570px; overflow:hidden; padding-top:5px; float:right;">
					<%@include file="/template/pagehy.jsp" %>
                  </div> 
			 </div>
			 <div class="rightMenu" style=" height:20px; margin:0px; padding:5px;">
                    <ul class="left rightMenuShort">
                    <li>   <a onclick="return CheckHelper.checkAll('strategyId');return false;" class="CheckLink"><s:text name="selectall" /></a></li>
                        <li>         <a onclick="return CheckHelper.reverseAll('strategyId');return false;" class="CheckLink"><s:text name="invertselection" /></a> </li>
                            <li>     <a onclick="return CheckHelper.cleanAll('strategyId');return false;" class="CheckLink"><s:text name="cancel" /></a></li>
                       
                        <li><a onclick="del('<s:text name="is_delete_schedul" />');"
	     	               ><s:text name="delete" /></a></li>
                    </ul>
                     <ul class="right rightMenuLong">
                    	<li><a onclick="view();"><s:text name="viewreport" /></a></li>
                    	 <li><a onclick="xml();"><s:text name="clear" /></a></li>
                    </ul>
     		 </div>
				 <!-- <div class="rightMenu" style=" height:20px; margin:0px; padding:5px;">
                  
                </div> -->
     </div>

	<div style="clear:both; height:1px; overflow:hidden;"></div>   
	</div>
	<table border="0" cellpadding="0" cellspacing="0" class="tcDi">
  <tr><td style="width:5px;"><img src="${theme}/skins/default/images/tcDi1.png" width="5" height="8" class="left" /></td>
  <td class="bg"></td><td  style="width:5px;"><img src="${theme}/skins/default/images/tcDi2.png" width="5" height="8" class="right"/></td>
  </tr></table>
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

