<%@page import="com.gnamp.server.model.*"%>
<%@page import="com.gnamp.server.handle.*"%>


<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<% String uu = request.getScheme() + "://"
				  + request.getServerName() + ":" + request.getServerPort()
				  + request.getContextPath();

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="x-ua-compatible" content="ie=8" />
    <%@include file="/template/Public/title.jsp" %>
    <style>
 html { overflow: scroll; } 
</style>
  <link rel="stylesheet" type="text/css"
	href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" />
    <link href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"  href="${theme}/skins/default/css/ipubs-dialog.css"/>
    <link href="${theme}/skins/default/css/jqtransform.css" media="all" rel="stylesheet" />
    <script type="text/javascript" src="${theme}/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="${theme}/js/jquery.jqtransform.js"></script>
    <script type="text/javascript" src="${theme}/js/jscroll.js"></script>
    <script type="text/javascript" src="${theme}/js/ipub.models-<s:text name="langjs"/>.js"></script>
    
    <script src="Dialog/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript" src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
<script src="js/gnamp.js"></script>
    <script type="text/javascript">
    var isShow = false;
    $(function() {
    	$("#tongji").addClass("hover");
    	
    	var param = {
    			"begindate" : $("#begindate").val(),
    			"enddate" : $("#enddate").val(),
    			"selectIDs" : (selectIDs.length >1 ? selectIDs.substring(0, selectIDs.length - 1) : selectIDs),
    			"checkhour" : $("#checkhour").attr("checked") == "checked" ? "true"
    					: "false"
    		};
    			
    	$p = new page("keylog!query",param,showlist);	
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
        
        function exportKeyLog(){
     	   if(!isShow){
     		   alert("<s:text name="interactivestatistis_text_obtainstatistical"/>");
     		   return;
     	   }
     		var param = {
        			"begindate" : $("#begindate").val(),
        			"enddate" : $("#enddate").val(),
        			"selectIDs" : (selectIDs.length >1 ? selectIDs.substring(0, selectIDs.length - 1) : selectIDs),
        			"checkhour" : $("#checkhour").attr("checked") == "checked" ? "true"
        					: "false"
        		};
     	   location.href="keylog!exportKeyLog?"+$.param(param);
        }
        
        function query(){
        	var param = {
        			"begindate" : $("#begindate").val(),
        			"enddate" : $("#enddate").val(),
        			"selectIDs" : (selectIDs.length >1 ? selectIDs.substring(0, selectIDs.length - 1) : selectIDs),
        			"checkhour" : $("#checkhour").attr("checked") == "checked" ? "true"
        					: "false"
        		};
        	$p.ajax(param);
        	return false;
        }

		function showlist(result){ 
			isShow = true;
        	try { 
            	var content = "";	 
            	Date.prototype.format = function(format)  { 
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
				     $.each( result.data, function(i, p) {  
					     var tmp=""; 
					     if((p.deviceId).toString(16).length<12){ 
						     for(var i=0;i<(12-(p.deviceId).toString(16).length);i++) 
							     tmp +="0";  
						 }  
						 content += " <tr id=\""
								+ p.deviceId
								+ "\">"     
								+ " <td style=\"width: 150px;\">"
								+ ((p.deviceId).toString(16).length<12? tmp+(p.deviceId).toString(16).toUpperCase():(p.deviceId).toString(16).toUpperCase())
								+ " </td>"	
								+ " <td style=\"width: 300px;\">"
								+ p.deviceName
								+ " </td>"										
								+ " <td style=\"width: 150px;\">"
								+ ($("#checkhour").attr("checked") != "checked" ? (new Date(Date.parse(p.dateTime.replace(/-/g,   "/")))).format('yyyy-MM-dd') : (new Date(Date.parse(p.dateTime.replace(/-/g,   "/")))).format('yyyy-MM-dd hh:00'))
								+ " </td>"	
								+ " <td>"
								+ p.keycount
								+ " </td>"	
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
        function upload(){
     	   showIPUBDialogStand("playlog!upload",{title:"<s:text name="importlog"/>",width:839,height:637});	
        }
        function selectTerminal(){
           showIPUBDialogStand("terminal!selectterminal",{title:"<s:text name="selectterminal"/>",width:1050,height:630});
        }
        
        var selectIDs = "";
        
        function finished(result){
        	selectIDs = result;    	
        	if(selectIDs.length>1){        		
        		$("#tcount").html(""+(selectIDs.substring(0, selectIDs.length - 1).split(",")).length+"");
        	}
        	else
        		$("#tcount").html("");
        }
    </script>

</head>
<body>
 <%@ include file="/header.jsp"%>   

    <div class="topBj1"></div>

    <div class="mid1">
        <div class="bt1">
        <h2><s:text name="statisticalreports"/></h2>
             <ul class="tab1">
            </ul>
            <form style="margin-top:-10px;" class="topSearchMk2">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="8%" style="text-align:right;"><s:text name="startenddate"/></td>
                    <td width="15%">
                        <input style="color:#a4a4a4" onfocus="WdatePicker({startDate:'%y-%M-01',dateFmt:'yyyy-MM-dd',lang:'<s:text name="lang" />',alwaysUseStartDate:true})" name="begindate" id="begindate" type="text" class="topSearchMk2Time" /></td>
                    <td align="center" width="4%">～</td>
                    <td width="15%">
                        <input style="color:#a4a4a4" onfocus="WdatePicker({startDate:'%y-%M-01',dateFmt:'yyyy-MM-dd',lang:'<s:text name="lang" />',alwaysUseStartDate:true})" name="enddate" id="enddate" type="text" class="topSearchMk2Time" /></td>
                    <td width="3%">
                        <div style="margin-top: -5px;">
                            <input name="checkhour" type="checkbox" value="11" id="checkhour" class="checkbox" style="margin-top: -15px; padding: 0px;" /></div>
                    </td>
                    <td width="12%"><s:text name="accuratetohour"/></td>
                    <td width="8%"></td>
                    <td width="13%">
                       </td>
                    <td width="22%">                        
                        <button onclick="selectTerminal();return false;"><s:text name="selectterminal"/></button>
                     <span id="tcount"></span>   
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
                        <li onclick="hi('playbackmonth')">
                            <img src="${theme}/skins/default/images/b12.png" /><s:text name="playbackmonth"/></li>
                        <li class="hover" onclick="hi('keylog')">
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
                        <td class="first" style="width: 150px;"><s:text name="terminalid"/></td>
                        <td style="width: 300px;"><s:text name="terminalname"/></td>
                        <td style="width: 150px;"><s:text name="period" /></td>
                        <td><s:text name="number" /></td>
                    </tr>
                </table>
             <div id="ee">
             	<table id="mytab" width="100%" border="0" cellspacing="0"
						cellpadding="0" class="shujTb shujTb2">
						
					</table>
              </div>
               <div class="rightMenu"  style="height: 20px;">
				<ul class="left rightMenuLong">				
					<li><a id="A5" onclick="query();"><s:text name="getstatistics" /></a></li>
					<li><a id="A6" href="javascript:exportKeyLog()"><s:text name="exportreport" /></a></li>
				</ul> 
      		  </div>
      		  <div style="font-size:12px;padding-left:20px;"><s:text name="bottom_prompt_interactive_statistic"/></div>
      		  
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
