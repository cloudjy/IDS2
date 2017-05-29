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
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">


<head>
<meta name="google" value="notranslate" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=8" />
<style type="text/css">
body { 
overflow-x:hidden; /* 隐藏水平滚动替 */ 
overflow-y:hidden; /* 隐藏垂直滚动替 */ 
} 
</style>

<link rel="stylesheet" type="text/css"
	href="<%=uu %>/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" />
<link rel="stylesheet"
	href="<%=uu %>/skins/default/css/jqtransform.css" media="all" />
    

<script src="<%=uu %>/js/jquery-1.7.2.min.js" language="javascript"></script>
<script type="text/javascript" src="<%=uu %>/js/jquery.jqtransform.js"></script>
<script type="text/javascript" src="<%=uu %>/js/jscroll.js"></script> 
<script type="text/javascript" src="<%=uu %>/js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=uu %>/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
<script type="text/javascript" src="${theme}/js/ipub.models-<s:text name="langjs"/>.js"></script>
<link href="${theme}/skins/default/css/ipubs-dialog.css" rel="stylesheet" type="text/css" />

<link href="<%=uu %>/skins/default/css/css-<s:text name="langjs"/>.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" media="all"
	href="<%=uu %>/skins/default/css/niceforms-default.css" />
<script language="javascript" type="text/javascript"
	src="<%=uu %>/js/niceforms.js"></script>
<script src="js/gnamp.js" language="javascript"></script>

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


<script type="text/javascript">
	
	var tid = <%=request.getParameter("TaskID") %>;
	var pid = <%=request.getParameter("PrgmID") %>;
	var pname = <%=request.getParameter("PName") %>;
	var ttype = <%=request.getParameter("Type") %>;
	var mid = <%=request.getParameter("MainID") %>;
	
	var W = <%=request.getParameter("W") %>;
	var H = <%=request.getParameter("H") %>;  
	
	 /* 包装函数函数 */
    var taskType = ttype;
    var taskID = tid;
    var layoutID = pid;
    
    var PreViewURL="";

    var xmlCode = "UTF-8";
   
    var pName=pname;	
    
    var isEdit = <%=request.getParameter("isEdit") %>; 
    
    var layoutXmlServer = null;

	function flexComplete(){ 
		showContent(tid, pid, pname, ttype, mid);
	} 
    
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
        setTimeout(function(){$("#lblMsg").html("");}, 2000); 
    } 
     
	var mainrectID = mid;     
	function showContent(tid, pid, pname, ttype, mid){
		var content="";    
  		// $("#ssd").remove();
	  	if(isEdit){ 
	  		content +="<div style=\"position: absolute; margin-top: 18px;  margin-left: 170px;\"><span><s:text name="prompt_edit_layout" /></span></div>";
	  		content +="<ul style=\"margin-top:-12px;\" class=\"tab1\"> ";
	  		content +="<li style=\"margin-left:18px;\" onclick=\"editlayout()\" class=\"hover\"><s:text name="tab_edit_layout" /></li> ";
	  		content +="<li onclick=\"editcontent()\"><s:text name="tab_edit_content" /></li> "; 
	  		content +="</ul>";  		
		} else{	
	  		content +="<div style=\"position: absolute; margin-top: 18px;  margin-left: 170px;\"><span><s:text name="prompt_edit_content" /></span></div>";
			content +="<ul style=\"margin-top:-12px;\" class=\"tab1\"> ";
	  		content +="<li style=\"margin-left:18px;\" onclick=\"editlayout()\"><s:text name="tab_edit_layout" /></li> ";
	  		content +="<li onclick=\"editcontent()\" class=\"hover\"><s:text name="tab_edit_content" /></li> "; 
	  		content +="</ul>";  
		} 

	  	var font_family= "font-family:\'微软雅黑\', \'微軟雅黑\', \'Calibri\', \'Arial\', \'sans-serif\';";
		
		//预览按钮
		content +="<input id=\"view\" type=\"button\" onclick=\"viewprogram();\" value=\"<s:text name="preview" />\" style=\"" + font_family +"\" />&nbsp;";
	  	 
    
		if(isEdit){
			content +="<s:if test="#session.current_privilege.editlayout"><input id=\"reset\" type=\"button\" onclick=\"r();\"  value=\"<s:text name="reset" />\" style=\"" + font_family +"\" /> ";
			content +="<input id=\"save\" type=\"button\" onclick=\"s();\" value=\"<s:text name="save" />\" style=\"" + font_family +"\" /> </s:if>";
			content +="<span id=\"space\"></span>";
		} else{
			<s:if test="#session.current_privilege.editcontent">
			content +="<input id=\"Button1\"  type=\"button\" value=\"<s:text name="refresh" />\" onclick=\"r();\" style=\"" + font_family +"\" />";
			content +="<input id=\"Button2\"  type=\"button\" value=\"<s:text name="bgmusic" />\" onclick=\"musicClick();\" style=\"" + font_family +"\" />";
			content +="<input id=\"ButtonTTS\"  type=\"button\" value=\"<s:text name="tts_text" />\" onclick=\"ttsButtonClick();\" style=\"" + font_family +"\" />";
			</s:if>
			content +="<span id=\"rect_selector_span\"></span>";   		
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
    		
		taskType=ttype;
		taskID=tid;
		layoutID=pid;
		pName=pname;  		
		// mainrectID = mid;
		  
		loadLayout(); 
    }
    	
    function rectSelectorChange() { 
    	var id = $("#rect_selector").val();  
    	var type = $("#rect_selector").find("option:selected").attr("rectType"); 
    	rectClick(id, type);
    	$("#rect_selector").val("0");
    	return false;
    }
    
     function rectTypeString(type) { 
         if (type == <%= RectType.IMAGE %>) { 
         	return "imagerect";
         } else if (type == <%= RectType.VIDEO %>) { 
         	return "videorect";
         } else if (type == <%= RectType.SLSTATIC_TEXT %>) { 
         	return "marqueerect";
         } else if (type == <%= RectType.SLFLIP_TEXT %>) { 
         	return "marqueerect";
         } else if (type == <%= RectType.SLSCROLL_TEXT %>) { 
         	return "marqueerect";
         } else if (type == <%= RectType.MLSTATIC_TEXT %>) { 
         	return "marqueerect";
         } else if (type == <%= RectType.MLFLIP_TEXT %>) { 
         	return "marqueerect";
         } else if (type == <%= RectType.MLSCROLL_TEXT %>) { 
         	return "marqueerect";
         } else if (type == <%= RectType.HYBRID %>) {  
         	return "hybridrect";
         } else if (type == <%= RectType.LOGO %>) { 
         	return "logorect";
         } else if (type == <%= RectType.WEB %>) { 
         	return "webrect";
         } else if (type == <%= RectType.FLASH %>) {  
         	return "flashrect";
         } else if (type == <%= RectType.AVIN %>) {  
          	return "avinrect";
         } else if (type == <%= RectType.CAMERA %>) {  
           	return "camerarect";
         } else {
         	return "";
         }
     }
     
     function setMainRectHtml(xmlString) {
		try {
			var xml;
			if($.browser.msie){
				xml = new ActiveXObject("Microsoft.XMLDOM");
				xml.async = false;
				xml.loadXML(xmlString);
			}else{ 
				xml = new DOMParser().parseFromString(xmlString, "text/xml");
			} 
			
			var selectHtml = " &nbsp; <s:text name="main" />"; 
			<s:if test="#session.current_privilege.editprog">
			selectHtml +="<select id=\"mainrect\" name=\"mainrect\" onchange=\"changemainrect()\">"; 
			</s:if>
			<s:else>
			selectHtml +="<select id=\"mainrect\" name=\"mainrect\" DISABLED >";  
			</s:else>
			
			var optionHtml ="<option value=\"0\"><s:text name="default" /></option>";
			var xmlNrogram = $(xml).find("program");
			mainrectID = xmlNrogram.attr("mainRectId");
			xmlNrogram.children().each(function(i, node){ 
				var nodeName = node.nodeName; 
				var isMainable = false;
		        if (nodeName == "imagerect" || nodeName == "videorect"  || 
		        		nodeName == "hybridrect" || nodeName == "devicerect" || 
		         		nodeName == "webrect" || nodeName == "flashrect" || 
		         		nodeName == "avinrect"){
		         	isMainable = true;
		        } else if (nodeName == "marqueerect") {
		        	var marqueeType = $(this).attr("marqueetype");
		        	if (marqueeType == 1 || marqueeType == 2 || // SLFlipTRect=>1, SLScrollTRect=>2
		        			marqueeType == 4 ||  marqueeType == 5 ) { // MLFlipTRect=>4, MLScrollTRect=>5
		         		isMainable = true;
		        	}
		        }
		        if (isMainable) {
					var jNode = $(this); 
					var rectid = jNode.attr("rectid");
					var rectname = jNode.attr("rectname"); 					
					optionHtml += "<option value=\""+rectid+"\" " + (mainrectID == rectid ? "selected" : "") + ">"
							+ rectname + "</option>";
				} 
			});
			selectHtml += optionHtml;
			selectHtml +="</select>"; 
			$("#space").empty();
			$("#space").append(selectHtml); 
		} catch (err) {
			alert("" + err.message);
		}
     }
     
     function setRectSelectHtml(xmlString) {
		try {
			var xml;
			if($.browser.msie){
				xml = new ActiveXObject("Microsoft.XMLDOM");
				xml.async = false;
				xml.loadXML(xmlString);
			}else{ 
				xml = new DOMParser().parseFromString(xmlString, "text/xml");
			} 
			 
			var selectHtml =" &nbsp; <s:text name="rectselect" />"; 
			selectHtml +="<select id=\"rect_selector\" name=\"rect_selector\" onchange=\"rectSelectorChange()\">";
					
			var optionHtml = "<option value=\"0\" selected><s:text name="selectnull" /></option>"
			$(xml).find("program").children().each(function(i, node){ 
				var nodeName = node.nodeName; 
		        if (nodeName == "imagerect" || nodeName == "videorect"  || 
		        		nodeName == "marqueerect" || 
		        		nodeName == "hybridrect" || nodeName == "logorect" || 
		         		nodeName == "webrect" || nodeName == "flashrect" ){
					var jNode = $(this); 
					var rectid = jNode.attr("rectid");
					var rectname = jNode.attr("rectname"); 
					optionHtml += "<option value=\"" + rectid + "\" " +
							"rectType=\"" + nodeName +"\">" + rectname + "</option>";
		         } 
			});
			selectHtml += optionHtml;
			selectHtml +="</select>"; 
			$("#rect_selector_span").empty();
			$("#rect_selector_span").append(selectHtml);
		} catch (err) {
			alert("" + err.message);
		}
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
 						if (isEdit) {
 							setMainRectHtml(msg.data);
 							layoutXmlServer = getAppletLayout(); 
 						} else {
 							setRectSelectHtml(msg.data); 
 						}
	                    // showMessage("<s:text name="success" />");
 					} else {
 						alert(msg.data);
 					}
				} else {
					alert(msg.data);
				}
			});  
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
 						if (isEdit) {
 							setMainRectHtml(msg.data);
 							layoutXmlServer = getAppletLayout(); 
 						} else {
 							setRectSelectHtml(msg.data); 
 						}
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
		if(isEditMode)
			return;   
    	var url = "";
	    if(taskType=='loop') 
	    	url = "looprect"; 
	    else if(taskType=='demand') 
	    	url = "demandrect"; 
	    else if(taskType == 'plugin')
	    	url = "pluginrect";
    	      
         if (type == "imagerect") {
        	
        	var str = '<div id="jdw" name="jdw" style="background:black;POSITION: absolute;left:0px;top:0px;z-index:1559;width:2012px;height:2012px;"><iframe style="width:100%;border:0px;height:100%;"></iframe></div>';
        	       	
        	 $("body").prepend(str);
        	 
            showIPUBDialogStand(""+url+"!imageSelect?taskID="
						+ taskID + "&programID="
						+ layoutID + "&rectID="+rectID,
						{title:"<s:text name="title_edit_contents" />",width:980,height:600,beforeClose:c1,draggable: false}); 	 
			  
         } else if (type == "videorect") {       
        	 
        	 var str = '<div id="jdw" name="jdw" style="background:black;POSITION: absolute;left:0px;top:0px;z-index:1559;width:2012px;height:2012px;"><iframe style="width:100%;border:0px;height:100%;"></iframe></div>';
 	       	
        	 $("body").prepend(str);
        	 
        	 showIPUBDialogStand(""+url+"!videoSelect?taskID="
						+ taskID + "&programID="
						+ layoutID + "&rectID="+rectID,
						{title:"<s:text name="title_edit_contents" />",width:980,height:600,beforeClose:c1,draggable: false});	
			
				
         } else if (type == "marqueerect") {           	
        	
        	 var str = '<div id="jdw" name="jdw" style="background:black;POSITION: absolute;left:0px;top:0px;z-index:1559;width:2012px;height:2012px;"><iframe style="width:100%;border:0px;height:100%;"></iframe></div>';
  	       	
        	 $("body").prepend(str);
        	
        	 showIPUBDialogStand(""+url+"!textSelect?taskID="
						+ taskID + "&programID="
						+ layoutID + "&rectID="+rectID,
						{title:"<s:text name="title_edit_contents" />",width:980,height:600,beforeClose:c1,draggable: false});
				
         } else if (type == "hybridrect") {           	
     	
	    	 var str = '<div id="jdw" name="jdw" style="background:black;POSITION: absolute;left:0px;top:0px;z-index:1559;width:2012px;height:2012px;"><iframe style="width:100%;border:0px;height:100%;"></iframe></div>';
		       	
	    	 $("body").prepend(str);
	    	
	    	 showIPUBDialogStand(""+url+"!contentSelect?taskID="
						+ taskID + "&programID="
						+ layoutID + "&rectID="+rectID,
						{title:"<s:text name="title_edit_contents" />",width:980,height:600,beforeClose:c1,draggable: false});
				
         }else if (type == "logorect") {
          	
          	var str = '<div id="jdw" name="jdw" style="background:black;POSITION: absolute;left:0px;top:0px;z-index:1559;width:2012px;height:2012px;"><iframe style="width:100%;border:0px;height:100%;"></iframe></div>';
          	       	
          	 $("body").prepend(str);
          	 
              showIPUBDialogStand(""+url+"!imageSelect?taskID="
  						+ taskID + "&programID="
  						+ layoutID + "&rectID="+rectID+"&logo=logo",
  						{title:"<s:text name="title_edit_contents" />",width:980,height:600,beforeClose:c1,draggable: false}); 	 
  			  
           } else if (type == "webrect") {

             	var str = '<div id="jdw" name="jdw" style="background:black;POSITION: absolute;left:0px;top:0px;z-index:1559;width:2012px;height:2012px;"><iframe style="width:100%;border:0px;height:100%;"></iframe></div>';
             	       	
             	 $("body").prepend(str);
             	 
                 showIPUBDialogStand(""+url+"!webUrlSelect?taskID="
     						+ taskID + "&programID="
     						+ layoutID + "&rectID="+rectID,
     						{title:"<s:text name="title_edit_contents" />",width:980,height:600,beforeClose:c1,draggable: false}); 	 
           } else if (type == "flashrect") {

             	var str = '<div id="jdw" name="jdw" style="background:black;POSITION: absolute;left:0px;top:0px;z-index:1559;width:2012px;height:2012px;"><iframe style="width:100%;border:0px;height:100%;"></iframe></div>';
             	       	
             	 $("body").prepend(str);
             	 
                 showIPUBDialogStand(""+url+"!flashSelect?taskID="
     						+ taskID + "&programID="
     						+ layoutID + "&rectID="+rectID,
     						{title:"<s:text name="title_edit_contents" />",width:980,height:600,beforeClose:c1,draggable: false}); 	 
           }         
     }


     function c1(){ 
     	var dlg_iframe = $("div.ipub-dialog-pannel > iframe");
     	if (dlg_iframe)
     		dlg_iframe.attr("src", "");	
     	$("div[id='jdw']").remove();
     	return true;
    }
     
    function musicClick() { 
    	if(isEditMode)
    		return; 
    	var url = ""; 
    	if(taskType=='loop') 
    		url = "looprect"; 
    	if(taskType=='demand') 
    		url = "demandrect"; 
    	if(taskType == 'plugin') 
    		url = "pluginrect";
    	
    	var str = '<div id="jdw" name="jdw" style="background:black;POSITION: absolute;left:0px;top:0px;z-index:1559;width:2012px;height:2012px;"><iframe style="width:100%;border:0px;height:100%;"></iframe></div>';
    	$("body").prepend(str);  
    	showIPUBDialogStand(""+url+"!musicSelect?taskID=" + taskID + "&programID=" + layoutID, 
    			{title:"",width:980,height:600,beforeClose:c1,draggable: false});  
    }
    function ttsButtonClick() { 
    	if(isEditMode)
    		return; 
    	var url = ""; 
    	if(taskType=='loop') 
    		url = "looptts"; 
    	if(taskType=='demand') 
    		url = "demandtts"; 
    	if(taskType == 'plugin') 
    		url = "plugintts";
    	
    	var str = '<div id="jdw" name="jdw" style="background:black;POSITION: absolute;left:0px;top:0px;z-index:1559;width:2012px;height:2012px;"><iframe style="width:100%;border:0px;height:100%;"></iframe></div>';
    	$("body").prepend(str);  
    	showIPUBDialogStand(""+url+"!ttsTextSelect?taskId=" + taskID + "&programId=" + layoutID, 
    			{title:"",width:980,height:600,beforeClose:c1,draggable: false});  
    }
   /////////////////////////////////////////图片预览开始///////////////////       
   var selecttype = "";

	function bgImageUrlCookieName(rectID) {
		return "__" + taskType + "_" + taskID + "_" + layoutID + "_" + rectID + "__";
	}
	
	function imageSelect(rectID,type){
		if(!isEdit) 
			return null;
		var url;
		
		if (type == "videorect") { 
			url = "looprect!videopreview";
		} else if(type=="imagerect") {
			url = "looprect!imagepreview";
		} else{
			return null;
		} 

		PreViewURL = window.showModalDialog(url,"","center=yes;dialogWidth=945px;dialogHeight=500px;help=no;status=no");  
		if (PreViewURL ) {
			try {
				if (rectID && rectID != 0) {
					$.cookie(bgImageUrlCookieName(rectID), PreViewURL, {expires: 7});
				}
			} catch (err) {
				alert("" + err.message);
			}
		} else {
			if (rectID && rectID != 0)
				$.cookie(bgImageUrlCookieName(rectID), null);
		}
		
		return  PreViewURL;
	} 
    

	function rectFirstImageUrl(rectID, rectType, width, height){ 
		var url = null
		if (isEdit) {  
			var cookieUrl = $.cookie(bgImageUrlCookieName(rectID)); 
			if (cookieUrl) {
				url = cookieUrl;
			}
		} else if (rectType == "videorect" || rectType == "imagerect" || 
				rectType == "hybridrect" || rectType == "logorect"){
	    	if(taskType=="loop"){ 
	    		url = "<%= uu %>/Pages/looprect!rectPreviewImage";
	    	} else if(taskType=="demand"){ 
	    		url = "<%= uu %>/Pages/demandrect!rectPreviewImage";
	    	} else  if(taskType=="plugin"){ 
	    		url = "<%= uu %>/Pages/pluginrect!rectPreviewImage";
	    	} else {
				return null;
	    	} 
			url += "?taskID=" + taskID + "&programID=" + layoutID + "&rectID=" + rectID + 
					"&requestW=" + width + "&requestH=" + height + "&XXX_TIME_XXX=" + (new Date().getTime());  
		}
		return url;
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
						isEdit = true;
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
		  return document['layoutApplet'].getLayoutXml(xmlCode);
         //return document.getElementById('layoutApplet').getLayoutXml(xmlCode);
     }

	function setAppletLayout(data, isEdit) {
		document['layoutApplet'].setLayoutXml(data, xmlCode, isEdit); 
		if (isEdit) {
			setFonts(); 
			setInteractivePrograms();
		}
	}
     
     function setFonts(){
    	 ajaxCallback("font.action", {}, function(result){
    		 
    		 if(result.success){
    			 document['layoutApplet'].setFonts(result.data);
    			 document['layoutApplet'].setDefaultFont(""); //result.fontName
    		 }else{
    			 Alert(result.message);
    		 }
    		  
    	 });
     }
     
     function setInteractivePrograms(){  
		ajaxCallback("demandprogram!demandlist", {"demandprogram.taskId" : taskID}, function(result){
    		 if(result.success){
    			 document['layoutApplet'].setTaskPrograms(result.data, layoutID); 
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
						showContent(taskID, layoutID, pName, taskType, mainrectID); 
						layoutXmlServer = null;
					}, function (){}); 
				return;
			}
		}
		
		isEdit = false;
		showContent(taskID, layoutID, pName, taskType, mainrectID); 
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
					showContent(taskID, layoutID, pName, taskType, mainrectID); 
					}, function (){}); 
				return;
			}
		}
		
		isEdit = true;
		layoutXmlServer = null;
		showContent(taskID, layoutID, pName, taskType, mainrectID); 
	}
</script>
</head>
<body style="text-align: center;">	
    <div id="content" align="center" style="vertical-align:middle; z-index:-100; width:100%; text-align:center; margin-left: auto; margin-right: auto;">
      <form id="form1" class="niceform">
      <div id="ssd">
      
      </div>          
     <%-- <applet width="960" height="540" archive="<%=request.getScheme() + "://"
  			+ request.getServerName() + ":" + request.getServerPort()
  			+ request.getContextPath() %>/applet/gnamp.jar" code="gnamp.layoutctrl.LayoutCtrl" name="layoutApplet" id="layoutApplet">
		<param name="RectClicked" value="rectClick" /> 
		<param name="ImageSelect" value="imageSelect" /> 
  	</applet>   --%>	
        
       <div id="flashContent">
			<a href="http://get.adobe.com/flashplayer" target="_black"><img src="./../skins/default/images/flash_128.jpg"/></a>
		</div>
  	</form>
	</div>
</body>
</html>
