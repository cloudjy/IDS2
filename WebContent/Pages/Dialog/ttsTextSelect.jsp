<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page language="java" import="com.gnamp.server.model.TTSText"%>
<%@ page language="java" import="com.gnamp.server.handle.TTSUtil"%>
<%@ taglib uri="/struts-tags" prefix="s"%> 

<% 
	String _actionName = "looptts";
	String _taskType = request.getAttribute("taskType").toString();
	if (_taskType.equalsIgnoreCase("loop")) {
		_actionName = "looptts";
	} else if ( _taskType.equalsIgnoreCase("demand")) {
		_actionName = "demandtts";
	} else if ( _taskType.equalsIgnoreCase("plugin")) {
		_actionName = "plugintts";
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

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

<link rel="stylesheet" type="text/css"  href="${theme}/skins/default/css/niceforms-default.css" />

<script src="js/gnamp.js" language="javascript"></script>
<script src="../Pages/js/JScript.js"></script>

<script type="text/javascript">	
					
		$(function(){
			var param = {
					"taskId" : <%=request.getAttribute("taskId")%>,
					"programId" : <%=request.getAttribute("programId")%>, 
    			};
    		
    		var surl = "<%= _actionName %>!queryTTSTextList"; 
			$p = new page(surl,param,showlist);	
			$p.ajax();
			
			$("#first").bind("click",$p.first);
			$("#previous").bind("click",$p.previous);
			$("#next").bind("click",$p.next);
			$("#last").bind("click",$p.last);
		});
		 

		function trimEmpty(str) {
			return str? str.replace(/(^\s*)|(\s*$)/g, "") : "";
		}
		
		var AllVoiceName = {
			// "xiaoyan" : "[普通话][小燕][青年女声]", 
		
			<% 	
			java.util.Locale locale = null;
			Object i18nLocle = session.getAttribute(com.gnamp.struts.filter.Context.SESSION_LOCALE);
			java.util.Locale requestLocale = request.getLocale();
			if (null != i18nLocle && i18nLocle instanceof java.util.Locale) {
				locale = (java.util.Locale)i18nLocle;
			} else {
				locale = null != requestLocale? requestLocale : java.util.Locale.CHINA;
			}
			String jsMap = ""; 
			java.util.Set<java.util.Map.Entry<String, String>> entrySet = TTSUtil.allVoiceNames(locale).entrySet();
			for(java.util.Map.Entry<String, String> pair : entrySet){
				String key = StringEscapeUtils.escapeJavaScript(pair.getKey());
				String value = StringEscapeUtils.escapeJavaScript(pair.getValue().toString()); 
				jsMap += "\t\t\"" + key + "\" : \"" + value + "\", \n"; 
			}   
			%> 
			<%=jsMap%>
		};
		var escapeJQueryDiv =  $("<div/>"); 
		function voiceNameHtml(key) { 
			var val = AllVoiceName[trimEmpty(key)];
			return val? escapeJQueryDiv.text(val).html() : "";
		} 
		function playCountHtml(count) { 
			return !count || count <= 0 || count == <%= Integer.MAX_VALUE %>? "<s:text name="tts_playmode_repeat"/>" : ""+count;
		}

		function audtionHtml(tts) {
			return "<img style=\"cursor: pointer; padding-left:0px;\" title=\"<s:text name="tts_audtion" />\"" +  
					"onclick=\"onTTSAudtion(\'" + tts.textId + "\')\" " + 
					"src=\"${theme}/skins/default/images/pageB3.gif\" height=\"15px\" width=\"15px\">";
		}
		
		function onTTSAudtion(textId) { 
			var surl = "<%= _actionName %>!ttsTextAudtion"; 
		    var param = "ttsText.taskId="+<%=request.getAttribute("taskId")%> + 
		    		"&ttsText.programId="+<%=request.getAttribute("programId")%> + 
    				"&ttsText.textId=" + textId; 
		    var dlgDiv = showIPUBDialogStand(""+surl+"?"+param, {
		    	title:"",width:500,height:100,
		    	beforeClose:function(){ 
			     	if (dlgDiv && dlgDiv.children("iframe")) { 
			     		dlgDiv.children("iframe").attr("src", "");	
			    	} 
			     	return true;
		    	}
		    });
		} 

		function queryTTSTexts() { 
	        $p.ajax();
	        return false;
		}

		function showlist(result){
			var content = "";
			$.each(result.data, function(i, f) {

				var checked = false;
				if (remember) {
					for ( var i = 0; i < remember.length; i++) {
						if (remember[i] == f.textId) {
							checked = true;	
							break;
						}
					}
				}
				var text = escapeJQueryDiv.text(f.text).html();
				var voicer = voiceNameHtml(f.voiceName);
				
		        content += "<tr>";
		        content += "<td style=\"width: 12px;\">";
		        content += "<label>";
		        content += "<input type=\"checkbox\" value=\""+f.textId+"\"  name=\""+f.textId+"\"  "+ (checked ? " checked ":" ") +"  id=\"" 
						+  f.textId
						+  "\" class=\"textfileid\" />";
		        content += "</label></td>";
		        content += "<td style=\"width: 456px;\" title=\""+ text +"\">"
		        		+ "<ul style=\"width:456px;overflow:hidden;text-overflow:ellipsis;\"><nobr>"
		        		+ text 
		        		+ "</nobr></ul>" 
		        		+ "</td>";	
                content += "<td style=\"width: 50px;\">"+f.sequenceNum+"</td>";
                content += "<td style=\"width: 150px;\" title=\""+ voicer + "\">" 
		        		+ "<ul style=\"width:150px;overflow:hidden;text-overflow:ellipsis;\"><nobr>" 
                		+ voicer
		        		+ "</nobr></ul>" 
		        		+ "</td>";
                content += "<td style=\"width: 50px;\">"+f.speed+"</td>";
                content += "<td style=\"width: 80px;\">"+playCountHtml(f.playCount)+"</td>"; 
                content += "<td style=\"width: 30px;\">" + audtionHtml(f) + "</td>"; 
                content += " </tr>"; 
			});							
			$("#mytab").html(content);
			
			$('.shujTb > tbody >tr:odd').addClass('alt');
			
			/**
			 * 重置滚动条
			 */
			ipubs.models.jscroll_ee();
		}
			
    	
	function add() {      	   
		var surl = "<%= _actionName %>!ttsTextToAdd"; 
	    var param = "ttsText.taskId="+<%=request.getAttribute("taskId")%> + 
	    		"&ttsText.programId="+<%=request.getAttribute("programId")%>; 
	    showIPUBDialogStand(""+surl+"?"+param, {title:"",width:583,height:400}); 
	} 
    	
	var remember = null;
	function edited(result){
		if(result=='ok'){
			queryTTSTexts(remember);
		}
	}
	function added(result){
		if(result=='ok'){
			queryTTSTexts(false);
		}
	}
    
    
	function elementsOperate(op) {
		var opArray = { 
			"delete":"<%= _actionName %>!ttsTextDelete", 
			"edit":"<%= _actionName %>!ttsTextToEdit", 
			"up":"<%= _actionName %>!ttsTextUp", 
			"down":"<%= _actionName %>!ttsTextdown", 
			"toFirst":"<%= _actionName %>!ttsTextToFirst", 
			"toLast":"<%= _actionName %>!ttsTextToLast", 
		};  
		if (!op || op.length == 0 || !opArray[op])
			return false;  
		var opUrl = opArray[op];
			
		remember = new Array();

		var arr = $("input[class='textfileid']"); //document.getElementsByTagName('input'); 
		var selectIds = "";
		var contenttype = 0;
		for ( var i = 0; i < arr.length; i++) {
			var abc = arr[i];
			if (abc.type == "checkbox" && abc.checked == true) {  
    			contenttype = abc.name;
				selectIds += abc.value.toString() + ","; 
				remember.push(abc.value); 
				if (op == "edit")
					break;
			}
		}

		if (remember.length <= 0) { 
			alert("<s:text name="no_select_playitem" />");
			return false;
		}
		if (op == "delete") { 
			confirm('<s:text name="is_delete_playitem" />', function() {
					var param = {
						"selectIds" : selectIds.substring(0, selectIds.length - 1),
						"taskId" : <%= request.getAttribute("taskId") %>,
						"programId" : <%= request.getAttribute("programId") %>, 
					}; 
					ajaxCallback(opUrl, param, function(msg) {
						if (msg.success) {
							queryTTSTexts(false);
						} else {
							alert(msg.data);
						}
					}); 
				}
			);
		} else if (op == "edit") {
			var modifyTextUrl = opUrl + 
					"?ttsText.taskId=" + <%= request.getAttribute("taskId") %> + 
					"&ttsText.programId=" + <%= request.getAttribute("programId") %> + 
					"&ttsText.textId="+remember[0];
	    	showIPUBDialogStand(modifyTextUrl, {title:"<s:text name="title_edit_contents_attribute"/>", width:590, height:400});
		} else if (op == "up" || op == "down" || op == "toFirst" || op == "toLast") {
			var param = {
				"ttsText.taskId" : <%= request.getAttribute("taskId") %>,
				"ttsText.programId" : <%= request.getAttribute("programId") %>,
				"ttsText.textId" : remember[0]
			};  

			$.ajax({
				type : "post",
				dataType : "json",
				url : opUrl,
				data : param,
				complete : function() {
				},
				success : function(msg) {
					if (msg.success) {
						queryTTSTexts(remember);
					} else {
						alert(msg.data);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					window.top.location.href = '/';
				}
			});
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
			    <td class="first" style="width: 478px;"><s:text name="tts_content" /></td>
			    <td style="width: 50px;"> <s:text name="seq" /></td>
			    <td style="width: 150px;"> <s:text name="tts_voicename" /></td>
			    <td style="width: 50px;"> <s:text name="tts_speed" /></td>
			    <td style="width: 80px;"> <s:text name="tts_playcount" /></td>
			    <td style="width: 30px;"> <s:text name="tts_audtion" /></td>
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
                     <!-- li><a href="javascript:void(0)" onclick="add()"><s:text name="create" /></a></li -->
                     <form id="only_css_form" class="niceform" style ="float:left;"> 
						<input type="button" onclick="add();" value="<s:text name="add" />" />  
					 </form>
                     <ul class="left rightMenuShort">
                        <li><a href="javascript:void(0)" onclick="elementsOperate('edit')"><s:text name="edit" /></a></li>
                        <li><a href="javascript:void(0)" onclick="elementsOperate('delete')"><s:text name="delete" /></a></li>
                        <li><a href="javascript:void(0)" onclick="elementsOperate('up')"><s:text name="up" /></a></li>
                        <li><a href="javascript:void(0)" onclick="elementsOperate('down')"><s:text name="down" /></a></li>
                        <li><a href="javascript:void(0)" onclick="elementsOperate('toFirst')"><s:text name="toFirst" /></a></li>
                        <li><a href="javascript:void(0)" onclick="elementsOperate('toLast')"><s:text name="toLast" /></a></li>
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

