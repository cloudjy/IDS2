<%@page import="com.gnamp.server.model.*"%>
<%@page import="com.gnamp.server.handle.*"%>
<%@page import="com.opensymphony.xwork2.*"%>
<%@page import="com.gnamp.struts.language.*"%>


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
<script type="text/javascript" src="${theme}/js/ipub.models-<s:text name="langjs"/>.js"></script>
<link href="${theme}/skins/default/css/ipubs-dialog.css" rel="stylesheet" type="text/css" />

<link href="<%=uu %>/skins/default/css/css-<s:text name="langjs"/>.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" media="all"
	href="<%=uu %>/skins/default/css/niceforms-default.css" />
<script language="javascript" type="text/javascript"
	src="<%=uu %>/js/niceforms.js"></script>
<script src="js/gnamp.js" language="javascript"></script>
<script type="text/javascript">
	
var tid = <%=request.getParameter("TempID") %>;
var pname = <%=request.getParameter("PName") %>;
var mid = <%=request.getParameter("MainID") %>;

$(function() {
	  // showContent(tid, pname, mid);
});
function flexComplete(){
	  showContent(tid, pname, mid);
}
	
	 /* 包装函数函数 */
    var tempID = tid;
    
    var PreViewURL="";

    var xmlCode = "UTF-8";
    var isEdit = true;     
   
    var pName=pname;
    
    var layoutXmlServer = null;	
    
     function showMessage(msg) {            
          $("#lblMsg").html(msg);
         setTimeout(function () {
         	 $("#lblMsg").html("");
         }, 2000); 
     }
     
   var mainrectID = mid;     
  function showContent(tid, pname, mid){	
    	 
  	 var content="";    
  	// $("#ssd").remove();
	  	 if(isEdit){   
	  		 
	  		content +="<ul style=\"margin-top:-12px;\" class=\"tab1\"> ";
	  		content +="<li style=\"margin-left:5px;\" onclick=\"editlayout()\" class=\"hover\"><s:text name="tab_edit_layout" /></li> ";
	  		content +="<li onclick=\"editcontent()\"><s:text name="tab_edit_content" /></li> ";
	  		content +="</ul>";  		
		}
		else{	
			content +="<ul style=\"margin-top:-12px;\" class=\"tab1\"> ";
	  		content +="<li style=\"margin-left:5px;\" onclick=\"editlayout()\"><s:text name="tab_edit_layout" /></li> ";
	  		content +="<li onclick=\"editcontent()\" class=\"hover\"><s:text name="tab_edit_content" /></li> ";
	  		content +="</ul>";  
		} 
    
  	  if(isEdit){
	    	content +="<s:if test="#session.current_privilege.editlayout"><input id=\"reset\" type=\"button\" onclick=\"r();\"  value=\"<s:text name="reset" />\" /> ";
		    content +="<input id=\"save\" type=\"button\" onclick=\"s();\" value=\"<s:text name="save" />\" /> </s:if>";	  		
		}
		else{
			content +="<s:if test="#session.current_privilege.editcontent"><input id=\"Button1\"  type=\"button\" value=\"<s:text name="refresh" />\" onclick=\"r();\" />";
			content +="<input id=\"Button2\"  type=\"button\" value=\"<s:text name="bgmusic" />\" onclick=\"musicClick();\" /></s:if>";
			content +="<span id=\"space\"></span>";	  		
		}     
  	
  	 content +="<span id=\"lblMsg\"></span>";  
  			
  			 $("#ssd").html(content);
    		

 		    NF = new Array();
    		resizeTest = -1;
    		var niceforms = document.getElementsByTagName('form'); 
		   for (var q = 0; q < niceforms.length; q++) { 
		       niceforms[q].unload = function (){};
		    }
    		onresize();    
    		
    		tempID=tid;
    		pName=pname;  		
    		mainrectID = mid;
    		  
  		    loadLayout();
  		 
  		    
  		//显示主播放区开始
    		var param = {						
       				"tempID" : tid
       			     };
    	       var url = "templet!getAllMainrects";
    	       
    	   	ajaxCallback(url, param, function(msg){
    	   		if (msg.success) {
						 var optionHtml = ""; 
						 if(msg.data != null && msg.data.length>0){	
  								<s:if test="#session.current_privilege.editprog">
								optionHtml +=" &nbsp; <s:text name="main" /><select id=\"mainrect\" name=\"mainrect\" onchange=\"changemainrect();\">";
								optionHtml +="<option value=\"0\"><s:text name="default" /></option>";
								$.each(msg.data, function(i, g) {		 							
									optionHtml += "<option " + (mid == g.rectId ? "selected" : " ") + " value=\""+g.rectId+"\">"
											+ g.rectName + "</option>";
								});
								optionHtml +="</select>"; 
								</s:if>
  								<s:else>
  								optionHtml +=" &nbsp; <s:text name="main" /><select id=\"mainrect\" name=\"mainrect\" DISABLED>";
								optionHtml +="<option value=\"0\"><s:text name="default" /></option>";
								optionHtml +="</select>";   
  								</s:else> 									
							//	optionHtml ="<form id=\"form2\" class=\"topSearchMk2\">" + optionHtml +  "</form>";  
								$("#space").after(optionHtml);
						 }
					} else {
						alert(msg.data);
					}
			});
    	       
    	}	

     function loadFromServer() { 
    	
    	  var url = "templet!GetTodoTemplateLayout";
                     
         	var param = {						
     				"tempID" : tempID
     			     };
         	
        	ajaxCallback(url, param, function(msg){
				if (msg.success) {
					if (msg.success) {
 						setAppletLayout(msg.data, isEdit);
	                    if (isEdit)
 							layoutXmlServer = getAppletLayout();
	                    showMessage("<s:text name="success" />");
 					} else {
 						alert(msg.data);
 					}
				} else {
					alert(msg.data);
				}
			});
     }

     function saveToServer() {
    	 var url = "templet!SaveTodoTemplateLayout";
	       
                 var data = getAppletLayout();  
             	var param = {					
	        				"tempID" : tempID,						
	        				"xmldata" : data
	        			     };
             	
            	ajaxCallback(url, param, function(msg){
            		if (msg.success) {
                        showMessage("<s:text name="savesuccess" />");
                        setAppletLayout(msg.data,isEdit);
                        if (isEdit)
 							layoutXmlServer = getAppletLayout();
					} else {
						showMessage("<s:text name="savefail" />");
						//alert(msg.data);
					}
				});
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
    	 
     if(isEditMode)return;        
         if (type == "imagerect") {
        	
        	var str = '<div id="jdw" name="jdw" style="background:black;POSITION: absolute;left:0px;top:0px;z-index:1559;width:2012px;height:2012px;"><iframe style="width:100%;border:0px;height:100%;"></iframe></div>';
        	       	
        	 $("body").prepend(str);
        	 
            showIPUBDialogStand("templet!imageSelect?tempID="
						+ tempID + "&rectID="+rectID,{title:"",width:980,height:600,beforeClose:c1}); 	 
			  
         } 
        /*  else if (type == "videorect") {       
        	 
        	 var str = '<div id="jdw" name="jdw" style="background:black;POSITION: absolute;left:0px;top:0px;z-index:1559;width:2012px;height:2012px;"><iframe style="width:100%;border:0px;height:100%;"></iframe></div>';
 	       	
        	 $("body").prepend(str);
        	 
        	 showIPUBDialogStand(""+url+"!videoSelect?taskID="
						+ taskID + "&programID="
						+ layoutID + "&rectID="+rectID,{title:"",width:980,height:600,beforeClose:c1});	
			
				
         } else if (type == "marqueerect") {           	
        	
        	 var str = '<div id="jdw" name="jdw" style="background:black;POSITION: absolute;left:0px;top:0px;z-index:1559;width:2012px;height:2012px;"><iframe style="width:100%;border:0px;height:100%;"></iframe></div>';
  	       	
        	 $("body").prepend(str);
        	
        	 showIPUBDialogStand(""+url+"!textSelect?taskID="
						+ taskID + "&programID="
						+ layoutID + "&rectID="+rectID,{title:"",width:980,height:600,beforeClose:c1});
				
         }     */   
         
         
     }


     function c1(){    	
     	 $("div[id='jdw']").remove();
     	return true;
    }
     
    function musicClick() {
    /*  if(isEditMode)return; 
     
     var str = '<div id="jdw" name="jdw" style="background:black;POSITION: absolute;left:0px;top:0px;z-index:1559;width:2012px;height:2012px;"><iframe style="width:100%;border:0px;height:100%;"></iframe></div>';
     	
	 $("body").prepend(str);
	
     
 	 showIPUBDialogStand("templet!musicSelect?taskID="
				+ taskID + "&programID="
				+ layoutID,{title:"",width:980,height:600,beforeClose:c1}); */
	
		
     }
   /////////////////////////////////////////图片预览开始///////////////////       
   var selecttype = "";
   function imageSelect(rectID,type){
     if(isEditMode)return;
         var url="";
        
        if (type == "videorect") { 
             url = "templet!videopreview";
         } else if(type=="imagerect") {
       	     url = "templet!imagepreview";
         }   else{
        	 return;
         }
         
         PreViewURL = window.showModalDialog(url,"","center=yes;dialogWidth=945px;dialogHeight=500px;help=no;status=no");  
          
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
    	 
		var param = {					
				"template.tempId" : tempID,						
				"template.mainRect" : $("#mainrect").val()
			     };
			$.ajax({
				type : "post",
				dataType : "json",
				url : "templet!changemainrect",
				data : param,
				complete : function() {
				},
				success : function(msg) {
					if (msg.success) {

						isEdit = false;
						mainrectID = msg.data;
						showContent(tempID, pName, msg.data);
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
         return document['layoutApplet'].getLayoutXml(xmlCode);
     }

     function setAppletLayout(data, isEdit) {  
    	 document['layoutApplet'].setLayoutXml(data, xmlCode, isEdit);
    	 setFonts();
     }
     
     function setFonts(){
    	 ajaxCallback("font.action", {}, function(result){
    		 
    		 if(result.success){
    			 document['layoutApplet'].setFonts(result.data);	 
    			 document['layoutApplet'].setDefaultFont(result.fontName);
    		 }else{
    			 Alert(result.message);
    		 }
    		  
    	 });
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
		if (layoutXmlServer != null) { 
			var layoutXmlEdit = getAppletLayout();
			if (layoutXmlEdit != layoutXmlServer) {
				confirm("<s:text name="layout_exit_prompt"/>",  function (){
						isEdit = false;
						showContent(tempID, pName, mainrectID);  
						layoutXmlServer = null;
					}, function (){}); 
				return;
			}
		}
		
		isEdit = false;
		showContent(tempID, pName, mainrectID);
		layoutXmlServer = null;
	}
	
	function editlayout()
	{ 
		if (layoutXmlServer != null) {
			var layoutXmlEdit = getAppletLayout();
			if (layoutXmlEdit != layoutXmlServer) {
				confirm("<s:text name="layout_refresh_prompt"/>",  function (){
					isEdit = true;
					layoutXmlServer = null;
					showContent(tempID, pName, mainrectID);  
					}, function (){}); 
				return;
			}
		}
		
		isEdit = true;
		layoutXmlServer = null;
		showContent(tempID, pName, mainrectID); 
	}
</script>
</head>
<body style="text-align: center;">	
    <div id="content" align="center" style="vertical-align:middle; z-index:-100; width:100%; text-align:center; margin-left: auto; margin-right: auto;">
      <form id="form1" class="niceform">
      <div id="ssd">
      
      </div>     	
      
      <!-- 
  	<script type="text/javascript">
        var attributes = {
            width: 960,
            height: 540,
            archive: "<%=request.getScheme() + "://"
  			+ request.getServerName() + ":" + request.getServerPort()
  			+ request.getContextPath() %>/applet/<s:text name="langgnamp" />",
            code: "gnamp.layoutctrl.LayoutCtrl",
            name: "layoutApplet",
            id: "layoutApplet"
        };

        var parameters = {
        		RectClicked:"rectClick",
        		ImageSelect:"imageSelect"
        };
      
        deployJava.runApplet(attributes, parameters, '1.6'); 
        
    </script>	
     -->
    
    <script type="text/javascript" src="../flex/layout/<s:text name="langjs"/>/swfobject.js"></script>
        <script type="text/javascript">
            // For version detection, set to min. required Flash Player version, or 0 (or 0.0.0), for no version detection. 
            var swfVersionStr = "11.1.0";
            // To use express install, set to playerProductInstall.swf, otherwise the empty string. 
            var xiSwfUrlStr = "playerProductInstall.swf";
            var flashvars = {"RequestW":1920,"RequestH":1080,"ProgramID":13500000000};
            var params = {};
            params.quality = "high";
            params.bgcolor = "#ffffff";
            params.allowscriptaccess = "sameDomain";
            params.allowfullscreen = "true";
            params.wmode="transparent";
            var attributes = {};
            attributes.id = "layoutApplet";
            attributes.name = "layoutApplet";
            attributes.align = "middle";
            swfobject.embedSWF(
                "../flex/layout/<s:text name="langjs"/>/ipublayout.swf", "flashContent", 
                "960", "540", 
                swfVersionStr, xiSwfUrlStr, 
                flashvars, params, attributes);
            // JavaScript enabled so display the flashContent div in case it is not replaced with a swf object.
            swfobject.createCSS("#flashContent", "display:block;text-align:left;");
        </script>
        
          <div id="flashContent">
			<a href="http://get.adobe.com/flashplayer" target="_black"><img src="./../skins/default/images/flash_128.jpg"/></a>
		</div>
  	</form>
	</div>
</body>
</html>
