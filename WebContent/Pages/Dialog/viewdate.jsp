<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="com.gnamp.server.model.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	LoopTask loop = (LoopTask) request.getAttribute("looptask");

   Calendar ca = Calendar.getInstance(); 
   ca.add(Calendar.DATE, 7);// 7为增加的天数，可以改变的
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
	$(function(){
		queryall();
	});
	
		function queryall() {
			var param = {
					"looptask.taskId" : <%= loop.getTaskId() %>
    			};   
			 chkary = new Array();
			ajaxCallback("date!view", param, function(msg){
				if (msg.success) {
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
							var rbstyle = "border-left:#dfdfdf 1px solid;border-top:#dfdfdf 1px solid;";
							var ltstyle_in = "border-left:#dfdfdf 1px solid;";
							
							$.each(msg.data,function(i, p) {																						      
							      content += " <tr>"
										+ " <td style=\""+(i>0? rbstyle : rbstyle)+"width: 120px;\">"
										+ " &nbsp; " + (new Date(Date.parse(p.loopDateRange.startDate.replace(/-/g,   "/")))).format('yyyy-MM-dd')
										+ " </td>"	
										+ " <td style=\""+(i>0? rbstyle : rbstyle)+"width: 110px;\">"
										+ (new Date(Date.parse(p.loopDateRange.stopDate.replace(/-/g,   "/")))).format('yyyy-MM-dd')
										+ " </td>"		
										+ " <td style=\""+(i>0? rbstyle : rbstyle)+"\">"
										+ "<table  cellspacing=\"0\" cellpadding=\"0\" >";
										$.each(p.xmlLoopDateRangeDays,function(j, d) {
								content +="<tr style=\"height:15px;\">";
											
												content +="<td style=\""+(j>0? rbstyle : ltstyle_in)+"width: 110px;\">"+getWeek((new Date(Date.parse(p.loopDateRange.startDate.replace(/-/g,   "/")))), (j+1))+"</td>";
											
											content +="<td style=\""+(j>0? rbstyle : ltstyle_in)+"\">"+
												 "<table  cellspacing=\"0\" cellpadding=\"0\" >";
													if(d.xmlLoopTimeRanges!=null && d.xmlLoopTimeRanges != undefined){
														$.each(d.xmlLoopTimeRanges,function(u, t) {
												content += "<tr>"+
															 "<td style=\""+(u>0? rbstyle : ltstyle_in)+"width: 110px;\">"+gettime(t.loopTimeRange.startTime)+"</td>"+
															 "<td style=\""+(u>0? rbstyle : ltstyle_in)+"width: 110px;\">"+gettime(t.loopTimeRange.stopTime+1)+"</td>"+
															 "<td style=\""+(u>0? rbstyle : ltstyle_in)+"width: 1000px;\">";
															  /* "<table  cellspacing=\"0\" cellpadding=\"0\" >"; */
																	if(t.loopTimeRangePrograms!=null && t.loopTimeRangePrograms != undefined){
																		var ptemp="";
																		$.each(t.loopTimeRangePrograms,function(l, r) {
															/* 	content += "<tr style=\"height:15px;\">"+
																			 "<td style=\""+(l>0? rbstyle : ltstyle_in)+"width: 1000px;height:5px;\">"+(r.playCount>0?(r.programName+"x"+r.playCount):(r.programName))+""+"</td>"+
																			"</tr>";		*/
																			
																	ptemp += ((l>0 ? "," : "") +(r.playCount>0?((r.programName+"x"+r.playCount)) : r.programName));
																		}
																       );
																	content += "<span title=\""+ptemp+"\">"+ (ptemp.length>34 ? (ptemp.substring(0,34)+"...") : ptemp)+"</span>";
																	}
														   /*content += "</table>"+  */
															 content += "</td>"+
															"</tr>";																
													     }
												       );
													}
										content += "</table>"+ 
											 "</td>"+
											"</tr>";
										}
										);
								content += "</table>"  
										+ " </td>"	
									 	+ " </tr>";		
									});
						   }
						
						$("#mytab").html(content);
						
						$('.shujTb > tbody >tr:odd').addClass('alt');
						
						
						ipubs.models.jscroll_ee();
					} catch (e) {
						alert(e.message);
					}
				} else {
					alert(msg.success);
				}
			});
			
			
			
		}
		
		function getWeek(strdate, n){	
			var ws = "";
			var show_day=new Array('<s:text name="mon" />','<s:text name="tues" />','<s:text name="weds" />','<s:text name="thurs" />','<s:text name="fri" />','<s:text name="sat" />','<s:text name="sun" />');
			var day = strdate.getDay();
			
			if(day==0)day=7;
			
			if(show_day[day-1]=='<s:text name="mon" />'){
				show_day=new Array('<s:text name="mon" />','<s:text name="tues" />','<s:text name="weds" />','<s:text name="thurs" />','<s:text name="fri" />','<s:text name="sat" />','<s:text name="sun" />');
			}
			else if(show_day[day-1]=='<s:text name="tues" />'){
				show_day=new Array('<s:text name="tues" />','<s:text name="weds" />','<s:text name="thurs" />','<s:text name="fri" />','<s:text name="sat" />','<s:text name="sun" />','<s:text name="mon" />');
			}
			else if(show_day[day-1]=='<s:text name="weds" />'){
				show_day=new Array('<s:text name="weds" />','<s:text name="thurs" />','<s:text name="fri" />','<s:text name="sat" />','<s:text name="sun" />','<s:text name="mon" />','<s:text name="tues" />');
			}
			else if(show_day[day-1]=='<s:text name="thurs" />'){
				show_day=new Array('<s:text name="thurs" />','<s:text name="fri" />','<s:text name="sat" />','<s:text name="sun" />','<s:text name="mon" />','<s:text name="tues" />','<s:text name="weds" />');
			}
			else if(show_day[day-1]=='<s:text name="fri" />'){
				show_day=new Array('<s:text name="fri" />','<s:text name="sat" />','<s:text name="sun" />','<s:text name="mon" />','<s:text name="tues" />','<s:text name="weds" />','<s:text name="thurs" />');
			}
			else if(show_day[day-1]=='<s:text name="sat" />'){
				show_day=new Array('<s:text name="sat" />','<s:text name="sun" />','<s:text name="mon" />','<s:text name="tues" />','<s:text name="weds" />','<s:text name="thurs" />','<s:text name="fri" />');
			}
			else if(show_day[day-1]=='<s:text name="sun" />'){
				show_day=new Array('<s:text name="sun" />','<s:text name="mon" />','<s:text name="tues" />','<s:text name="weds" />','<s:text name="thurs" />','<s:text name="fri" />','<s:text name="sat" />');
			}		

			ws = show_day[n-1];
			
			return ws;
		}
		
		function gettime(time){
		       //时间为：2011-7-7 17:16:55
		// var d = new Date(time*1000);
         var hour = ( parseInt(time/3600) < 10 ? ("0"+parseInt(time/3600)) : parseInt(time/3600));
         var minute = ( parseInt((time%3600)/60) < 10 ? ("0"+ parseInt((time%3600)/60)) :  parseInt((time%3600)/60));
		 var second = ( parseInt(time%3600)%60 < 10 ? ("0"+ parseInt(time%3600)%60) :  parseInt(time%3600)%60 );
         return  hour + ":" + minute + ":" +second; 
	  }
</script>
</head>
<body>

<!-- 弹出窗口 开始-->
<div class="tcWindow" style="width:930px;">
    <table border="0" cellpadding="0" cellspacing="0" class="tcBt">
      <tr><td style="width:5px;"><img src="${theme}/skins/default/images/tcBt1.png" width="5" height="35" class="left" /></td>
    <td class="bg">
   
					<h2></h2>
    </td><td  style="width:5px;"><img src="${theme}/skins/default/images/tcBt3.png" width="5" height="35" class="right"/></td>
    </tr></table>
	<div class="tcNr">
	<form action="">
	
	</form>
	<div style="clear:both; height:5px; overflow:hidden;"></div>
     <div>
     		 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="shujBt shujBt2">
                    <tr>                        
                        <td class="first" style="width: 110px;"><s:text name="startdate" /></td>
                        <td style="width: 100px;"><s:text name="enddate" /></td>
                        <td style="width: 100px;"><s:text name="weekdays" /></td>
                        <td style="width: 62px;"><s:text name="starttime" /></td>
                        <td style="width: 65px;"><s:text name="endtime" /></td>
                        <td><s:text name="prgms" /></td>
                    </tr>
      </table>
	<div style="height: 410px; overflow: auto; border-right: #ccc solid 2px;">
	  <table id="mytab" style="border-bottom:#dfdfdf 1px solid;" width="100%" border="0"
		 cellspacing="0" cellpadding="0" class="">
                      
      </table>
      
      </div>
      
			 
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

