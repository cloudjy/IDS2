<%@page import="com.gnamp.server.model.*"%>
<%@page import="com.gnamp.server.handle.*"%>
<%@page import="com.opensymphony.xwork2.*"%>
<%@page import="com.gnamp.struts.language.*"%>


<% String uu = request.getScheme() + "://"
				  + request.getServerName() + ":" + request.getServerPort()
				  + request.getContextPath();

/* Map<String,Object> sessionMap = ActionContext.getContext().getSession();

if(!sessionMap.containsKey(Locales.SESSION_LOCALE)){
	sessionMap.put(Locales.SESSION_LOCALE, Locale.CHINA);
}
else{
	System.out.print("11111111111");
	sessionMap.put(Locales.SESSION_LOCALE, Locale.ENGLISH);
} */

%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">


<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=8" />
<style type="text/css">
body { 
overflow-x:hidden; /*隐藏水平滚动条*/ 
overflow-y:hidden; /*隐藏垂直滚动条*/ 
} 
</style>

<link rel="stylesheet" type="text/css"
	href="<%=uu %>/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" />
<link rel="stylesheet"
	href="<%=uu %>/skins/default/css/jqtransform.css" media="all" />
    

<script src="<%=uu %>/js/jquery-1.7.2.min.js" language="javascript"></script>
<script type="text/javascript" src="<%=uu %>/js/jquery.jqtransform.js"></script>
<script type="text/javascript" src="<%=uu %>/js/jscroll.js"></script> 
<script type="text/javascript" src="<%=uu %>/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
<script type="text/javascript" src="<%=uu %>/js/ipub.models.js"></script> 

<link href="<%=uu %>/skins/default/css/css-<s:text name="langjs"/>.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" media="all"
	href="<%=uu %>/skins/default/css/niceforms-default.css" />
<script language="javascript" type="text/javascript"
	src="<%=uu %>/js/niceforms.js"></script>
<script src="js/gnamp.js" language="javascript"></script>
    <script src="js/deployJava.js" language="javascript"></script>
<script type="text/javascript">
	
var tid = <%=request.getParameter("TaskID") %>;
var pid = <%=request.getParameter("PrgmID") %>;
var pname = <%=request.getParameter("PName") %>;
var ttype = <%=request.getParameter("Type") %>;
var mid = <%=request.getParameter("MainID") %>;

var W = <%=request.getParameter("W") %>;
var H = <%=request.getParameter("H") %>;

$(function() {
	   showContent(tid, pid, pname, ttype, mid);
	   
	  /*  $("#Button2").focus(function () {
		   musicClick();
		}); */
	   
});

	
	 /* 包装函数函数 */
    var taskType = ttype;
    var taskID = tid;
    var layoutID = pid;
    
    var PreViewURL="";

    var xmlCode = "UTF-8";
    var isEdit = true;     
   
    var pName=pname;	


    
  //预览 tasktype TaskID, ProgramID, W, H
    function viewprogram(){   
    	var str = '<div id="jdw" name="jdw" style="background:black;POSITION: absolute;left:0px;top:0px;z-index:1559;width:2012px;height:2012px;"><iframe style="width:100%;border:0px;height:100%;"></iframe></div>';
     	
   	    $("body").prepend(str);
   	 
    	if(ttype=="loop"){
    		 showIPUBDialogStand('looprect!showprogram?TaskID=' + taskID + '&ProgramID=' + layoutID + '&RequestW=' + W +
    	                '&RequestH=' + H,{title:"<s:text name="programpreview"/>",width:1012,height:618,beforeClose:c1,draggable: false});		
    	  
    	}
    	if(ttype=="demand"){
    		 showIPUBDialogStand('demandrect!showprogram?taskID=' + taskID + '&programID=' + layoutID + '&RequestW=' + W +
    	                '&RequestH=' + H,{title:"<s:text name="programpreview"/>",width:1012,height:618,beforeClose:c1,draggable: false});		
    	  
    	}
    	if(ttype=="plugin"){
    		 showIPUBDialogStand('pluginrect!showprogram?taskID=' + taskID + '&programID=' + layoutID + '&RequestW=' + W +
    	                '&RequestH=' + H,{title:"<s:text name="programpreview"/>",width:1012,height:618,beforeClose:c1,draggable: false});		
    	  
    	} 
  }
    
    
     function showMessage(msg) {            
          $("#lblMsg").html(msg);
         setTimeout(function () {
         	 $("#lblMsg").html("");
         }, 2000); 
     }
     
     var mainrectID = mid;     
  function showContent(tid, pid, pname, ttype, mid){	
    	 
  	 var content="";    
  	// $("#ssd").remove();
	  	 if(isEdit){   
	  		content +="<ul style=\"margin-top:-12px;\" class=\"tab1\"> ";
	  		content +="<li style=\"margin-left:18px;\" onclick=\"editlayout()\" class=\"hover\"><s:text name="tab_edit_layout" /></li> ";
	  		content +="<li onclick=\"editcontent()\"><s:text name="tab_edit_content" /></li> ";
	  		content +="</ul>";  		
		}
		else{	
			content +="<ul style=\"margin-top:-12px;\" class=\"tab1\"> ";
	  		content +="<li style=\"margin-left:18px;\" onclick=\"editlayout()\"><s:text name="tab_edit_layout" /></li> ";
	  		content +="<li onclick=\"editcontent()\" class=\"hover\"><s:text name="tab_edit_content" /></li> ";
	  		content +="</ul>";  
		} 
       
	  	 //预览按钮
		  content +="<input id=\"view\" type=\"button\" onclick=\"viewprogram();\" value=\"<s:text name="preview" />\" />&nbsp;";
	  	 
    
  	  if(isEdit){
	    	content +="<s:if test="#session.current_privilege.editlayout"><input id=\"reset\" type=\"button\" onclick=\"r();\"  value=\"<s:text name="reset" />\" /> ";
		    content +="<input id=\"save\" type=\"button\" onclick=\"s();\" value=\"<s:text name="save" />\" /> </s:if>";	  		
		}
		else{
			content +="<s:if test="#session.current_privilege.editlayout"><input id=\"Button1\"  type=\"button\" value=\"<s:text name="refresh" />\" onclick=\"r();\" /> ";
			content +="<input id=\"Button2\"  type=\"button\" value=\"<s:text name="bgmusic" />\" onclick=\"musicClick();\" /> </s:if> ";
			content +="<span id=\"space\"></span>";	  		
		}     
  	
  	 content +="<span id=\"lblMsg\"></span>";  
  	
    		<%-- content +="<applet width=\"960\" height=\"540\" archive=\"<%=request.getScheme() + "://"
  				+ request.getServerName() + ":" + request.getServerPort()
  				+ request.getContextPath() %>/applet/gnamp.jar\" code=\"gnamp.layoutctrl.LayoutCtrl\" name=\"layoutApplet\" id=\"layoutApplet\"> ";
  				content +="<param name=\"RectClicked\" value=\"rectClick\"> ";
  				content +="<param name=\"ImageSelect\" value=\"imageSelect\"> ";
  				content +=" </applet>";  --%>
  				
  				
  			 //	content ="<form id=\"form1\" class=\"niceform\">" + content +  "</form>";   
  
  			 $("#ssd").html(content);
    		//$("#content").html(content);

 		    NF = new Array();
    		resizeTest = -1;
    		var niceforms = document.getElementsByTagName('form'); 
		   for (var q = 0; q < niceforms.length; q++) { 
		       niceforms[q].unload = function (){};
		    }
    		onresize();  
    		
    		
    		
    		    		
    	
    		
    		taskType=ttype;
    		taskID=tid;
    		layoutID=pid;
    		pName=pname;  		
    		mainrectID = mid;
    		  
  		    loadLayout();
  		 
  		    
  		//显示主播放区开始
    		var param = {
       				"programID" : pid,						
       				"taskID" : tid
       			     };
    	       var url = "";
    	       if(taskType=='loop')
    	    	   url = "looprect!getAllMainrects";
    	       if(taskType=='demand')
    	    	   url = "demandrect!getAllMainrects";
    	       if(taskType == 'plugin')
    	    	   url = "pluginrect!getAllMainrects";
    	       
    	   	ajaxCallback(url, param, function(msg){
    	   		if (msg.success) {
						 var optionHtml = ""; 
						 if(msg.data != null && msg.data.length>0){	
								optionHtml +=" &nbsp; <s:text name="main" /><select id=\"mainrect\" name=\"mainrect\" onchange=\"changemainrect()\">";
								optionHtml +="<option value=\"0\"><s:text name="default" /></option>";
								$.each(msg.data, function(i, g) {		 							
									optionHtml += "<option " + (mid == g.rectId ? "selected" : " ") + " value=\""+g.rectId+"\">"
											+ g.rectName + "</option>";
								});
					
								optionHtml +="</select>";  									
							//	optionHtml ="<form id=\"form2\" class=\"topSearchMk2\">" + optionHtml +  "</form>";  
								$("#space").after(optionHtml);
						 }
					} else {
						alert(msg.data);
					}
			});
    	       
       			/* $.ajax({
       				type : "post",
       				dataType : "json",
       				url : url,
       				data : param,
       				complete : function() {
       				},
       				success : function(msg) {
       					if (msg.success) {
       						 var optionHtml = ""; 
  							 if(msg.data != null && msg.data.length>0){	
  									optionHtml +=" &nbsp; <s:text name="main" /><select id=\"mainrect\" name=\"mainrect\" onchange=\"changemainrect()\">";
  									optionHtml +="<option value=\"0\"><s:text name="default" /></option>";
  									$.each(msg.data, function(i, g) {		 							
  										optionHtml += "<option " + (mid == g.rectId ? "selected" : " ") + " value=\""+g.rectId+"\">"
  												+ g.rectName + "</option>";
  									});
  						
  									optionHtml +="</select>";  									
  								//	optionHtml ="<form id=\"form2\" class=\"topSearchMk2\">" + optionHtml +  "</form>";  
  									$("#space").after(optionHtml);
  							 }
       					} else {
       						alert(msg.data);
       					}
       					
			      //    $('form').jqTransform({ imgPath: 'skins/default/images/' });
       				},
       				error : function(XMLHttpRequest, textStatus,
       						errorThrown) {
       					window.top.location.href='/';
       				}
       			});       			 */
    		//结束
  		    
    	}	

     function loadFromServer() { 
    	
    	  var url = "";
	       if(taskType=='loop')
	    	   url = "looprect!GetTodoProgramLayout";
	       if(taskType=='demand')
	    	   url = "demandrect!GetTodoProgramLayout";
	       if(taskType == 'plugin')
	    	   url = "pluginrect!GetTodoProgramLayout";
                     
         	var param = {
     				"programID" : layoutID,						
     				"taskID" : taskID
     			     };
         	
        	ajaxCallback(url, param, function(msg){
				if (msg.success) {
					if (msg.success) {
 						setAppletLayout(msg.data, isEdit);
	                        showMessage("<s:text name="success" />");
 					} else {
 						alert(msg.data);
 					}
				} else {
					alert(msg.data);
				}
			});
         	
     			/* $.ajax({
     				type : "post",
     				dataType : "json",
     				url : url,
     				data : param,
     				complete : function() {
     				},
     				success : function(msg) {
     					if (msg.success) {
     						setAppletLayout(msg.data, isEdit);
 	                        showMessage("<s:text name="success" />");
     					} else {
     						alert(msg.data);
     					}
     				},
     				error : function(XMLHttpRequest, textStatus,
     						errorThrown) {
     					window.top.location.href='/';
     				}
     			}); */
     			
             
     }

     function saveToServer() {
    	 var url = "";
	       if(taskType=='loop')
	    	   url = "looprect!SaveTodoProgramLayout";
	       if(taskType=='demand')
	    	   url = "demandrect!SaveTodoProgramLayout";
	       if(taskType == 'plugin')
	    	   url = "pluginrect!SaveTodoProgramLayout";
	       
                 var data = getAppletLayout();  
             	var param = {
	        				"programID" : layoutID,						
	        				"taskID" : taskID,						
	        				"xmldata" : data
	        			     };
             	
            	ajaxCallback(url, param, function(msg){
            		if (msg.success) {
                        showMessage("<s:text name="savesuccess" />");
                        setAppletLayout(msg.data,isEdit);
					} else {
						showMessage("<s:text name="savefail" />");
						//alert(msg.data);
					}
				});
             	
	        			/* $.ajax({
	        				type : "post",
	        				dataType : "json",
	        				url : url,
	        				data : param,
	        				complete : function() {
	        				},
	        				success : function(msg) {
	        					if (msg.success) {
	    	                        showMessage("<s:text name="savesuccess" />");
	    	                        setAppletLayout(msg.data,isEdit);
	        					} else {
	        						showMessage("<s:text name="savefail" />");
	        						//alert(msg.data);
	        					}
	        				},
	        				error : function(XMLHttpRequest, textStatus,
	        						errorThrown) {
	        					window.top.location.href='/';
	        				}
	        			});             */    
             
     }
  
     
     var isEditMode=false;

     var rectClickProcessing = false;
     function rectClick(rectID,type) {
     	if (!rectClickProcessing)
     		setTimeout("rectClickProcess(" + rectID +  ",\"" + type+ "\")", 0);
     }
     
     function rectClickProcess(rectID,type) {
     	rectClickProcessing = true;
     	rectClickInnerProcess(rectID,type);
     	rectClickProcessing = false;
     }

     function rectClickInnerProcess(rectID,type) {
    	 
    	 var url = "";
	       if(taskType=='loop')
	    	   url = "looprect";
	       if(taskType=='demand')
	    	   url = "demandrect";
	       if(taskType == 'plugin')
	    	   url = "pluginrect";
    	 
     if(isEditMode)return;        
         if (type == "imagerect") {
        	
        	var str = '<div id="jdw" name="jdw" style="background:black;POSITION: absolute;left:0px;top:0px;z-index:1559;width:2012px;height:2012px;"><iframe style="width:100%;border:0px;height:100%;"></iframe></div>';
        	       	
        	 $("body").prepend(str);
        	 
            showIPUBDialogStand(""+url+"!imageSelect?taskID="
						+ taskID + "&programID="
						+ layoutID + "&rectID="+rectID,{title:"",width:980,height:600,beforeClose:c1,draggable: false}); 	 
			  
         } else if (type == "videorect") {       
        	 
        	 var str = '<div id="jdw" name="jdw" style="background:black;POSITION: absolute;left:0px;top:0px;z-index:1559;width:2012px;height:2012px;"><iframe style="width:100%;border:0px;height:100%;"></iframe></div>';
 	       	
        	 $("body").prepend(str);
        	 
        	 showIPUBDialogStand(""+url+"!videoSelect?taskID="
						+ taskID + "&programID="
						+ layoutID + "&rectID="+rectID,{title:"",width:980,height:600,beforeClose:c1,draggable: false});	
			
				
         } else if (type == "marqueerect") {           	
        	
        	 var str = '<div id="jdw" name="jdw" style="background:black;POSITION: absolute;left:0px;top:0px;z-index:1559;width:2012px;height:2012px;"><iframe style="width:100%;border:0px;height:100%;"></iframe></div>';
  	       	
        	 $("body").prepend(str);
        	
        	 showIPUBDialogStand(""+url+"!textSelect?taskID="
						+ taskID + "&programID="
						+ layoutID + "&rectID="+rectID,{title:"",width:980,height:600,beforeClose:c1,draggable: false});
				
         } else if (type == "hybridrect") {           	
     	
	    	 var str = '<div id="jdw" name="jdw" style="background:black;POSITION: absolute;left:0px;top:0px;z-index:1559;width:2012px;height:2012px;"><iframe style="width:100%;border:0px;height:100%;"></iframe></div>';
		       	
	    	 $("body").prepend(str);
	    	
	    	 showIPUBDialogStand(""+url+"!contentSelect?taskID="
						+ taskID + "&programID="
						+ layoutID + "&rectID="+rectID,{title:"",width:980,height:600,beforeClose:c1,draggable: false});
				
         }        
     }


     function c1(){    	
     	 $("div[id='jdw']").remove();
     	return true;
    }
     
    function musicClick() {
     if(isEditMode)return;      
     
     var url = "";
     if(taskType=='loop')
  	   url = "looprect";
     if(taskType=='demand')
  	   url = "demandrect";
     if(taskType == 'plugin')
  	   url = "pluginrect";
     
     var str = '<div id="jdw" name="jdw" style="background:black;POSITION: absolute;left:0px;top:0px;z-index:1559;width:2012px;height:2012px;"><iframe style="width:100%;border:0px;height:100%;"></iframe></div>';
     	
	 $("body").prepend(str);
	
     
 	 showIPUBDialogStand(""+url+"!musicSelect?taskID="
				+ taskID + "&programID="
				+ layoutID,{title:"",width:980,height:600,beforeClose:c1,draggable: false});
	
		
     }
   /////////////////////////////////////////图片预览开始///////////////////       
   var selecttype = "";
   function imageSelect(rectID,type){
     if(isEditMode)return;
         var url;
        
        if (type == "videorect") { 
             url = "looprect!videopreview";
         } else if(type=="imagerect") {
       	     url = "looprect!imagepreview";
         }   
         
        	// var str = '<div id="jdw" name="jdw" style="background:black;POSITION: absolute;left:0px;top:0px;z-index:1559;width:2012px;height:2012px;"><iframe style="width:100%;border:0px;height:100%;"></iframe></div>';
        	       	
        	// $("body").prepend(str);
        	
       /*    showIPUBDialogStand(""+url+"?taskID="
			+ taskID + "&programID="
			+ layoutID + "&rectID="+rectID,{title:"背景预览",width:980,height:600,beforeClose:c1}); 
      	   */
           PreViewURL = window.showModalDialog(url,"","center=yes;dialogWidth=945px;dialogHeight=500px;help=no;status=no");  
          
		  //showMessage(PreViewURL);		
		
		//selecttype = type;
		
        return  PreViewURL;
     } 
     //////////////////////////图片预览结束///////////////////////////////////	 
     
     function closepreview(result){    	
    	 if(result != null){
    		 PreViewURL = result;    		 
    	 }    	 
     }
  
 
   function changemainrect()
   { 	
	   var url = "";
	   var param = null;
	     if(taskType=='loop'){
		  	   url = "loopprogram";
		  	  param = {
						"loopprogram.programId" : layoutID,						
						"loopprogram.taskId" : taskID,						
						"loopprogram.mainRectId" : $("#mainrect").val()
					     };
	     }
	     if(taskType=='demand'){
		  	   url = "demandprogram";
		  	  param = {
						"demandprogram.programId" : layoutID,						
						"demandprogram.taskId" : taskID,						
						"demandprogram.mainRectId" : $("#mainrect").val()
					     };
	     }
	     if(taskType == 'plugin'){
		  	   url = "pluginprogram";
		  	  param = {
						"pluginprogram.programId" : layoutID,						
						"pluginprogram.taskId" : taskID,						
						"pluginprogram.mainRectId" : $("#mainrect").val()
					     };
	     }
    	 
		
		
		
			$.ajax({
				type : "post",
				dataType : "json",
				url : ""+url+"!changemainrect",
				data : param,
				complete : function() {
				},
				success : function(msg) {
					if (msg.success) {

						isEdit = false;
						mainrectID = msg.data;
						showContent(taskID, layoutID, pName, taskType, msg.data);
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
 
	 function getAppletLayout() {
         return document.getElementById('layoutApplet').getLayoutXml(xmlCode);
     }

     function setAppletLayout(data, isEdit) {  
         document.getElementById('layoutApplet').setLayoutXml(data, xmlCode, isEdit);
     }
     
     function loadLayout() {
    	 loadFromServer();
     }
     	  
      function saveLayout() { 
         saveToServer();  
     } 
	 
	function s()
	{  
		saveToServer(); 
	}
	function r()
	{ 
        loadFromServer();
	}
	function editcontent()
	{  
		 isEdit = false;
		showContent(taskID, layoutID, pName, taskType, mainrectID); 
	}
	function editlayout()
	{ 
		 isEdit = true;
		showContent(taskID, layoutID, pName, taskType, mainrectID); 
	}
</script>
</head>
<body style="text-align: center;">	
    <div id="content" align="center" style="vertical-align:middle; z-index:-100; width:100%; text-align:center; margin-left: auto; margin-right: auto;">
      <form id="form1" class="niceform">
      <div id="ssd">
      
      </div>          
    <applet width="960" height="540" archive="<%=request.getScheme() + "://"
  			+ request.getServerName() + ":" + request.getServerPort()
  			+ request.getContextPath() %>/applet/gnamp.jar" code="gnamp.layoutctrl.LayoutCtrl" name="layoutApplet" id="layoutApplet">
		<param name="RectClicked" value="rectClick" /> 
		<param name="ImageSelect" value="imageSelect" /> 
  	</form>
	</div>
</body>
</html>
