<%@page import="com.gnamp.server.model.*"%>
<%@page import="com.gnamp.server.handle.*"%>

<% String uu = request.getScheme() + "://"
				  + request.getServerName() + ":" + request.getServerPort()
				  + request.getContextPath();

%>

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
</style>


<link rel="stylesheet" type="text/css" href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" />
<link rel="stylesheet" type="text/css" href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" />
<link rel="stylesheet" type="text/css" href="${theme}/skins/default/css/jqtransform.css" media="all" />
<link rel="stylesheet" type="text/css"  href="${theme}/skins/default/css/ipubs-dialog.css"/>

<script type="text/javascript" src="${theme}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${theme}/js/jquery.jqtransform.js"></script>
<script type="text/javascript" src="${theme}/js/jscroll.js"></script>
<script type="text/javascript" src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
<script type="text/javascript" src="${theme}/js/ipub.models-<s:text name="langjs"/>.js"></script>

<script type="text/javascript" src="js/gnamp.js"></script>
    
<script src="Dialog/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

	
<script type="text/javascript">
var isShow =false;
$(function() {
	$("#tongji").addClass("hover");
  	var param = {
			"begindate" : $("#begindate").val(),
			"enddate" : $("#enddate").val(),
			"playstyle": $("#playmode").val(),
			"selectIDs" : (selectIDs.length > 1 ? selectIDs.substring(0, selectIDs.length - 1) : selectIDs),
			"fileIDs" : (fileIDs.length > 1 ? fileIDs.substring(0, fileIDs.length - 1) : fileIDs),
			"checkhour" : $("#checkhour").attr("checked") == "checked" ? "true"
					: "false"
		};
			
	$p = new page("playlog!monthStatistic",param,showlist);	
	//$p.ajax();    	
	
	$("#first").bind("click",$p.first);
	$("#previous").bind("click",$p.previous);
	$("#next").bind("click",$p.next);
	$("#last").bind("click",$p.last);
});

$(function () {
    $('form').jqTransform({ imgPath: '${theme}/skins/default/images/' });
});

function hi(o) { 
	if(o=='playstatistic') {
		window.location.href="playlog";
	} else if(o=='playbackmonth') {
		window.location.href="playlog!month";
	} else if(o=='keylog') {
    	window.location.href="keylog";
	} else if(o=='pipelog') {
		window.location.href="keylog!pipelogs";
    }
}

function query(){
	var param = {
			"begindate" : $("#begindate").val(),
			"enddate" : $("#enddate").val(),
			"playstyle": $("#playmode").val(),
			"selectIDs" : (selectIDs.length > 1 ? selectIDs.substring(0, selectIDs.length - 1) : selectIDs),
			"fileIDs" : (fileIDs.length > 1 ? fileIDs.substring(0, fileIDs.length - 1) : fileIDs),
			"checkhour" : $("#checkhour").attr("checked") == "checked" ? "true"
					: "false"
		};
	$p.ajax(param);
	return false;
} 
        
function sizeX_xMB(size) {	  
	var uint_1M = 1024*1024;  
	size = (size/uint_1M).toString();
	var pos = size.indexOf(".");
	if (pos > 0) { 
		size = size.substr(0,pos+2);
	} 
	return "" + size + "MB";
}

var escapeJQueryDiv =  $("<div/>");
function html_escape(_text) {
	return escapeJQueryDiv.text(_text).html();
}

function showlist(result){ 
    isShow = true; 
    
	try {  
		var content = "";		
		
		Date.prototype.format = function(format) {
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
  	   if(result.data != null && result.data != undefined){ 
      	   $.each(result.data,  function(i, p) { 
	      	   var id= (p.deviceId).toString(16).toUpperCase(); 
	      	   while (id.length < 12)
	      	   		id = "0" + id;
		       var date1 = new Date(Date.parse(p.dateTime.replace(/-/g,   "/")));
		       var date31 = date1.getMonth() != 11? new Date(date1.getFullYear(), date1.getMonth()+1, 0) : 
		       		new Date(date1.getFullYear()+1, 0, 0);
		       var from_to = "" + date1.format('yyyy-MM-dd') + " ~ " +  date31.format('yyyy-MM-dd');
		       var file_size = (Math.round((p.size/1024)*100)/100);// sizeX_xMB(p.size);
		       var file_name = "<ul style=\"width: 150px;overflow:hidden;text-overflow:ellipsis;\"><nobr>" + html_escape(p.fileName) + "</nobr></ul>";
		       var dev_descp = "<ul style=\"width: 100px;overflow:hidden;text-overflow:ellipsis;\"><nobr>" + html_escape(p.deviceDescription) + "</nobr></ul>";
		       var dev_city = "<ul style=\"width: 50px;overflow:hidden;text-overflow:ellipsis;\"><nobr>" + html_escape(p.cityName) + "</nobr></ul>";


		       content += " <tr id=\"" + p.deviceId + "\">"    
					+ " <td style=\"width: 130px;\">" + from_to  + " </td>"	 	
					+ " <td style=\"width: 80px;\" title=\""+p.deviceName+"\">" + id + " </td>" 
					+ " <td style=\"width: 100px;\" title=\""+html_escape(p.deviceDescription)+"\">" + dev_descp + " </td>"  	
					+ " <td style=\"width: 50px;\" title=\""+html_escape(p.cityName)+"\">" + dev_city + " </td>"   
					+ " <td style=\"width: 150px;\" title=\""+html_escape(p.fileName)+"\">" + file_name + "</td>"	
					+ " <td style=\"width: 100px;\">" + file_size + " </td>"										
					+ " <td style=\"width: 50px;\">" + p.playCount + " </td>"							
					+ " <td>" + p.playTimes + " </td>" 
				    + " </tr>"; 
			}); 

		   if (result.data.length <= 0) {
    		   alert("<s:text name="have_no_statistic_data"/>");
		   }
		} 
		$("#mytab").html(content);

		$('.shujTb >tr:odd').addClass('alt');
		
		/**
		 * 重置滚动条
		 */
		ipubs.models.jscroll_ee();
						
				
     }catch(eer){
    	 alert(eer.message);
     }
}

function exportPlayLog(){
   if(!isShow){
	   alert("<s:text name="getstatiserror"/>");
	   return;
   }
   var param = {
		"begindate" : $("#begindate").val(),
		"enddate" : $("#enddate").val(),
		"playstyle": $("#playmode").val(),
		"selectIDs" : (selectIDs.length > 1 ? selectIDs.substring(0, selectIDs.length - 1) : selectIDs),
		"fileIDs" : (fileIDs.length > 1 ? fileIDs.substring(0, fileIDs.length - 1) : fileIDs),
		"checkhour" : $("#checkhour").attr("checked") == "checked" ? "true"
				: "false"
	};
   location.href="playlog!exportMonthStatistic?"+$.param(param);
}
function upload(){
   showIPUBDialogStand("playlog!upload",{title:"<s:text name="importlog"/>",width:839,height:637});	
}
function selectTerminal(){
   showIPUBDialogStand("terminal!selectterminal",{title:"<s:text name="title_select_terminal"/>",width:1050,height:630});
}
function selectFile(){
   showIPUBDialogStand("file!selectfile",{title:"<s:text name="title_select_file"/>",width:1050,height:630});
}


var selectIDs = "";
var fileIDs = "";
function finished(result){ 
   selectIDs = result; 
   var num = (!selectIDs || selectIDs.length <= 0)? 0 : 
       (selectIDs.substring(0, selectIDs.length - 1).split(",")).length;
   var text = num <= 0? "" : "<s:text name="numberofterminal"/>" + num;
   $("#tcount").html(text);
   $("#bt_select_terminal").attr("title", text);
}
function finishedfile(result){ 
   fileIDs = result; 
   var num = (!fileIDs || fileIDs.length <= 0)? 0 :  
       (fileIDs.substring(0, fileIDs.length - 1).split(",")).length;
   var text = num <= 0? "" : "<s:text name="numberoffile"/>" + num;
   $("#fcount").html(text);
   $("#bt_select_file").attr("title", text);  
}
</script>

</head>
<body>
 <%@ include file="/header.jsp"%>   

    <div class="topBj1"></div>

    <div class="mid1">
        <div class="bt1">
        <h2 id="one1" ><s:text name="statisticalreports"/></h2>
            <ul class="tab1"></ul> 
            <div id="tcount" style="position: absolute;margin-top: -10px;width: 200px;text-align: right;"></div>
            <div id="fcount" style="position: absolute;margin-top: -10px;width: 270px;text-align: right;"></div>
            <form style="margin-top:-10px;" class="topSearchMk2">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="28%"></td>
                    <td width="8%" style="text-align:right;"><s:text name="startenddate"/></td>
                    <td width="15%">
                        <input style="color:#a4a4a4" onfocus="WdatePicker({startDate:'%y-%M-01',dateFmt:'yyyy-MM-dd',lang:'<s:text name="lang" />',alwaysUseStartDate:true})" name="begindate" id="begindate" type="text" class="topSearchMk2Time" /></td>
                    <td align="center" width="4%">～</td>
                    <td width="15%">
                        <input style="color:#a4a4a4" onfocus="WdatePicker({startDate:'%y-%M-01',dateFmt:'yyyy-MM-dd',lang:'<s:text name="lang" />',alwaysUseStartDate:true})" name="enddate" id="enddate" type="text" class="topSearchMk2Time" /></td>
                    <td width="3%" style="display:none;">
                        <div style="margin-top: -5px;">
                            <input name="checkhour" type="checkbox" value="11" id="checkhour" class="checkbox" style="margin-top: -15px; padding: 0px;" /></div>
                    </td>
                    <td width="12%" style="display:none;"><s:text name="accuratetohour"/></td>
                    <td width="8%" style="text-align:right;display:none;"><s:text name="playmode"/></td>
                    <td width="13%" style="display:none;">
                        <select name="" id="playmode">
                            <option value="-1"><s:text name="all"/></option>
                            <option value="0"><s:text name="auto"/></option>
                            <option value="1"><s:text name="mutual"/></option>
                        </select>
                    </td>
                    <td width="2%"></td>
                    <td width="28%" style="white-space: nowrap;">
                        <button id="bt_select_file" onclick="selectFile(); return false;"><s:text name="selectfile"/></button>
                        <button id="bt_select_terminal" onclick="selectTerminal(); return false;"><s:text name="selectterminal"/></button>
                   </td>
                </tr>
            </table>
            </form>
        </div>
        <div class="nr1">
            <!-- 左侧 开始-->
            <div class="left1">
                <div>
                    <ul id="con_one_1" class="leftMenu2 leftMenu3 padd">
                        <li onclick="hi('playstatistic')">
                            <img src="${theme}/skins/default/images/b12.png" /><s:text name="playstatistic"/></li>
                        <li class="hover" onclick="hi('playbackmonth')">
                            <img src="${theme}/skins/default/images/b12.png" /><s:text name="playbackmonth"/></li>
                        <li onclick="hi('keylog')">
                            <img src="${theme}/skins/default/images/b14.png" /><s:text name="inductionstatistic"/></li>
                        <% if (com.gnamp.struts.filter.Context.isAlcoholVersion()) { %>
                        	<li onclick="hi('pipelog')"><img src="${theme}/skins/default/images/b13.png" /><s:text name="pipelog"/></li>
                        <% } %>
                    </ul>
                    <ul id="con_one_2" style="display: none"></ul>
                    <ul id="con_one_3" style="display: none"></ul>
                </div>
                  <div class="leftButtonBox2">
                   <s:if test="#session.current_privilege.editterminal">
					    <%--  <a href="#" onclick="upload()"><s:text name="importlog" /></a> --%>
					</s:if>
                </div>
            </div>
            <!-- 左侧 结束-->
            <!-- 右侧 开始-->
            <div class="right1">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="shujBt shujBt2">
                    <tr>
                        <td class="first" style="width: 130px;"><s:text name="period" /></td>
                        <td style="width: 80px;"><s:text name="player" /></td>
                        <td style="width: 100px;"><s:text name="device_descrp" /></td>
                        <td style="width: 50px;"><s:text name="setcity" /></td>
                        <td style="width: 150px;"><s:text name="listname" /></td>
                        <td style="width: 100px;"><s:text name="size" /></td>
                        <td style="width: 50px;"><s:text name="number" /></td> 
                        <td><s:text name="playbackduration" /></td> 
                    </tr>
                </table>
             <div id="ee">
                <table id="mytab" width="100%" border="0" cellspacing="0"
						cellpadding="0" class="shujTb shujTb2">
						
			   </table>
              </div>              
               <div class="rightMenu" style="height: 20px;">
				<ul class="left rightMenuLong">				
					<li><a id="A5" onclick="query();"><s:text name="getstatistics" /></a></li>
					<li><a id="A6" href="javascript:exportPlayLog()"><s:text name="exportreport" /></a></li>
				</ul> 
      		  </div>
      		  <div style="font-size:12px;padding-left:20px;"><s:text name="bottom_prompt_play_month"/></div>
      		  
      		<!-- 右侧底部menu 开始-->

				<div class="bottomMenu">

					<div class="rightSelect">
						
					</div>
					
					<a href="#"> <img onclick="query();"
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

   <%@ include file="/buttom.jsp"%>
</body>
</html>
