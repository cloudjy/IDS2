<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%> 
<%@ page language="java" import="com.gnamp.server.model.ContentType"%>
<%@ taglib uri="/struts-tags" prefix="s"%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache, must-revalidate" />
<meta http-equiv="expires" content="0" />

<style type="text/css">
body { 
overflow-x:hidden; /*隐藏水平滚动条*/ 
overflow-y:hidden; /*隐藏垂直滚动条*/ 
} 
</style>


   <link rel="stylesheet" type="text/css"
	href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" />
<link href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet"
	href="${theme}/skins/default/css/jqtransform.css" media="all" />
    
<script type="text/javascript" src="${theme}/js/jquery-1.7.2.min.js"></script>
<script language="javascript" type="text/javascript" src="${theme}/js/niceforms.js"></script>
<script type="text/javascript" src="${theme}/js/jquery.jqtransform.js"></script>
<script type="text/javascript" src="${theme}/js/jscroll.js"></script> 
<script type="text/javascript" src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
<script type="text/javascript" src="${theme}/js/ipub.models-<s:text name="langjs"/>.js"></script>
<link href="${theme}/skins/default/css/ipubs-dialog.css" rel="stylesheet" type="text/css" />

<script src="js/gnamp.js" language="javascript"></script>
<script src="../Pages/js/JScript.js"></script>
<%  
	String taskType = request.getAttribute("taskType").toString();
	String taskID = request.getAttribute("taskID").toString();
	String programID = request.getAttribute("programID").toString();
	String rectID = request.getAttribute("rectID").toString();

	String queryRectContentUrl = "";
	String addWebUrlUrl = "";
	String removeWebUrlUrl = "";
	String editPlayPropertyUrl = "";
	String fileUpUrl = "";
	String fileDownUrl = ""; 
				
	if (taskType.equals("loop")) {
		queryRectContentUrl = "looprect!querycontentlist"; 
		addWebUrlUrl = "looprect!toAddWebUrl"; 
		removeWebUrlUrl = "looprect!deletecontent"; 
		editPlayPropertyUrl = "looprect!toEditWebUrl";  
		fileUpUrl = "looprect!up"; 
		fileDownUrl = "looprect!down";  
	} else if (taskType.equals("demand")) {
		queryRectContentUrl = "demandrect!querycontentlist";
		addWebUrlUrl = "demandrect!toAddWebUrl";  
		removeWebUrlUrl = "demandrect!deletecontent"; 
		editPlayPropertyUrl = "demandrect!toEditWebUrl"; 
		fileUpUrl = "demandrect!up"; 
		fileDownUrl = "demandrect!down";  
	} else if (taskType.equals("plugin")) {
		queryRectContentUrl = "pluginrect!querycontentlist";
		addWebUrlUrl = "pluginrect!toAddWebUrl";
		removeWebUrlUrl = "pluginrect!deletecontent";
		editPlayPropertyUrl = "pluginrect!toEditWebUrl";
		fileUpUrl = "pluginrect!up";
		fileDownUrl = "pluginrect!down"; 
	}
%>

<script type="text/javascript">	
					
		$(function(){
			var param = {
					"taskID" : <%=request.getAttribute("taskID")%>,
					"programID" : <%=request.getAttribute("programID")%>,
					"rectID" : <%=request.getAttribute("rectID")%>
    			};
    		
    		var url = "<%= queryRectContentUrl %>"; 
			$p = new page(url,param,showlist);	
			$p.ajax();
			
			$("#first").bind("click",$p.first);
			$("#previous").bind("click",$p.previous);
			$("#next").bind("click",$p.next);
			$("#last").bind("click",$p.last);
		});
		
		
		function querytextfiles(remember) {
			var param = {
					"taskID" : <%=request.getAttribute("taskID")%>,
					"programID" : <%=request.getAttribute("programID")%>,
					"rectID" : <%=request.getAttribute("rectID")%>
    			};
    		
    		var url = "<%= queryRectContentUrl %>"; 
			
			$p.ajax();
	    	return false;
		}
		
		function showlist(result){
			var content = "";
			$.each(result.data, function(i, f) {

				var checked = false;
				if (remember) {
					for ( var i = 0; i < remember.length; i++) {
						if (remember[i] == f.contentId) {
							checked = true;	
							break;
						}
					}
				}					        
		        content += "<tr>";
		        	content += "<td style=\"width: 12px;\">";
		        	content += "<label>";
		        		content += "<input type=\"checkbox\" value=\""+f.contentId+"\"  name=\""+f.contentType+"\"  "+ (checked ? " checked ":" ") +"  id=\"" 
						+  f.contentId
						+  "\" class=\"textfileid\" />";
		        	content += "</label></td>";
		        			content += "<td title=\""+(f.contentType == <%= ContentType.WEB_URL %>?f.url:"")+"\" style=\"width: 550px;\">"
		        			+ (f.contentType == <%= ContentType.WEB_URL %> ? (f.url.length>80?f.url.substr(0,80)+"...":f.url) :  "") +"</td>";
                    content += "<td>"+f.sequenceNum+"</td>"	
                    content += "<td>"+f.playTime+"</td>"	
                    content += "<td>"+f.refreshTime+"</td>"				
			        +  " </tr>";
                  
			});							
			$("#mytab").html(content);
			
			$('.shujTb > tbody >tr:odd').addClass('alt');
			
			/**
			 * 重置滚动条
			 */
			ipubs.models.jscroll_ee();
		}
			
    	
       function add()  {      	   
    		var url = "<%= addWebUrlUrl %>";  
    		var param = "taskID=" + <%=request.getAttribute("taskID")%> + 
    			"&programID=" + <%=request.getAttribute("programID")%> + 
    			"&rectID=" + <%=request.getAttribute("rectID")%>;
    		
			showIPUBDialogStand(url+"?"+param, {title:"",width:600,height:250});	 
       } 
    	
       var remember = null;
   	function edited(result){
   		if(result=='ok'){
   			querytextfiles(remember);
   		}
   	}
 	function added(result){
   		if(result=='ok'){
   			querytextfiles(false);
   		}
   	}
       
    	function del(msg) {
    		 remember = new Array();
    		
    		var arr = $("input[class='textfileid']");  //document.getElementsByTagName('input');
    		var ok = 0;
    		var selectID = 0;
    		var selectIDs = "";
    		var contenttype = 0;
    		for ( var i = 0; i < arr.length; i++) {
    			var abc = arr[i];
    			if (abc.type == "checkbox") {
    				if (abc.checked == true) {
    					ok++;					
    				    selectID = abc.value;
    				    contenttype = abc.name;
    					selectIDs += selectID.toString() + ",";
    					remember.push(abc.value);
    				}
    			}
    		}
    		
    		if (ok > 0) {  //
    			if (msg != null && msg.length > 0 && msg != "up" && msg != "edit" && msg != "down") {
    	    				
    				confirm(msg,function(){
    					var param = {
        						"selectIDs" : selectIDs.substring(0, selectIDs.length - 1),	
        						"taskID" : <%=request.getAttribute("taskID")%>,
        						"programID" : <%=request.getAttribute("programID")%>,
        						"rectID" : <%=request.getAttribute("rectID")%>
        					     }; 
        					
    					$.ajax({
    						type : "post",
    						dataType : "json",
    						url : "<%= removeWebUrlUrl %>",
    						data : param,
    						complete : function() {
    						},
    						success : function(msg) {
    							if (msg.success) {    								
    								querytextfiles(false);
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
    			if (msg == "edit") {
    				
    				var url = "<%= editPlayPropertyUrl %>";  
			    	if(contenttype == <%= ContentType.WEB_URL %> )	{
				    	var param = "rectWebUrl.taskId=" + <%=request.getAttribute("taskID")%> + 
				    		"&rectWebUrl.programId=" + <%=request.getAttribute("programID")%> + 
				    		"&rectWebUrl.rectId=" + <%=request.getAttribute("rectID")%> + 
				    		"&rectWebUrl.contentId="+selectID;
			    		showIPUBDialogStand(url+"?"+param, {title:"",width:600,height:250});		
			    	} else {
			    		alert("<s:text name="no_select_playitem" />");
		    			return;
		    		} 
		    	}
    			if (msg == "up") {
    				var param = {
    						"programID" : <%=request.getAttribute("programID")%>,						
    						"taskID" : <%=request.getAttribute("taskID")%>,						
    						"rectID" : <%=request.getAttribute("rectID")%>,
    						"contentID" : selectID
    				}; 
    				
					$.ajax({
						type : "post",
						dataType : "json",
						url : "<%= fileUpUrl %>",
						data : param,
						complete : function() {
						},
						success : function(msg) {
							if (msg.success) {  	
		    					querytextfiles(remember);
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

    			if (msg == "down") {
    				var param = {
    						"programID" : <%=request.getAttribute("programID")%>,						
    						"taskID" : <%=request.getAttribute("taskID")%>,						
    						"rectID" : <%=request.getAttribute("rectID")%>,
    						"contentID" : selectID
    				}; 
    				
					$.ajax({
						type : "post",
						dataType : "json",
						url : "<%= fileDownUrl %>",
						data : param,
						complete : function() {
						},
						success : function(msg) {
							if (msg.success) {
								querytextfiles(remember);
							} else {
								alert(msg.data);
							}
						},
						error : function(XMLHttpRequest, textStatus, errorThrown) {
							window.top.location.href='/';
						}
					});
			}

		} else {
			alert("<s:text name="no_select_playitem" />");
			return false;
		}

	}
</script>
</head>
<body>
<!-- 弹出窗口 开始-->
<div class="tcWindow" style="width:900px;">
    <table border="0" cellpadding="0" cellspacing="0" class="tcBt">
      <tr><td style="width:5px;"><img src="${theme}/skins/default/images/tcBt1.png" width="5" height="35" class="left" /></td>
    <td class="bg">
   
					<h2></h2>
    </td><td  style="width:5px;"><img src="${theme}/skins/default/images/tcBt3.png" width="5" height="35" class="right"/></td>
    </tr></table>
	<div class="tcNr"> 
 
	<div>

		 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="shujBt shujBt2">
                    <tr>
                        <td class="first" style="width: 450px;"><s:text name="webUrl" /></td>
                        <td style="width: 50px;"><s:text name="seq" /></td>
                        <td style="width: 80px;"><s:text name="playTime" /></td>
                        <td style="width: 80px;"><s:text name="refreshTime" /></td>
                    </tr>
      </table>
      

	
	<div style="height: 335px; overflow: auto; border-right: #ccc solid 2px;">
		<table id="mytab" width="100%" border="0"
		 cellspacing="0" cellpadding="0" class="shujTb shujTb2">
                      
      </table>
      </div>
			 <div style="height:30px; background:#d5dcde;">
			     <div class="page1 page2" style="width:570px; overflow:hidden; padding-top:5px; float:right;">
			     
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
			 <div class="rightMenu" style=" height:20px; margin:0px; padding:5px;">
			 		<s:if test="#session.current_privilege.editcontent">
                    <ul class="left rightMenuShort">
                    <li>   <a onclick="return CheckHelper.checkAll('textfileid');return false;" class="CheckLink"><s:text name="selectall" /></a></li>
                        <li>         <a onclick="return CheckHelper.reverseAll('textfileid');return false;" class="CheckLink"><s:text name="invertselection" /></a> </li>
                            <li>     <a onclick="return CheckHelper.cleanAll('textfileid');return false;" class="CheckLink"><s:text name="cancel" /></a></li>
                       
                    </ul>
                    </s:if>
      </div>
				<div class="rightMenu" style=" height:20px; margin:0px; padding:5px;">
					<s:if test="#session.current_privilege.editcontent">
                    <ul class="left rightMenuShort">
                     	<li><a href="javascript:void(0)" onclick="add()"><s:text name="create" /></a></li>
                        <li><a href="javascript:void(0)" onclick="del('edit')"><s:text name="edit" /></a></li>
                        <li><a href="javascript:void(0)" onclick="del('<s:text name="is_delete_playitem" />')"
	     	               ><s:text name="delete" /></a></li>
                        <li><a href="javascript:void(0)" onclick="del('up')" ><s:text name="up" /></a></li>
                        <li><a href="javascript:void(0)" onclick="del('down')"><s:text name="down" /></a></li>
                    </ul>
                    </s:if>
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

