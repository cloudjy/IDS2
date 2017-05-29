<%@page import="com.gnamp.server.model.Source"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
<script type="text/javascript">	
<%Source source = (Source)request.getAttribute("source");%>
		var catId = <%=source.getCatId()%>;
		var sourceId = <%=source.getSourceId()%>;
		var type = <%=source.getType()%>
		$(function(){
			var param = {
					"source.sourceId":sourceId,
					"source.catId":catId,
					"source.type":type,
					"page.currentPage":1,
					"page.pageSize":20
    			};
					$p = new page("tag!queryFileTagList",param,showlist);	
					$p.ajax();
					
					$("#first").bind("click",$p.first);
					$("#previous").bind("click",$p.previous);
					$("#next").bind("click",$p.next);
					$("#last").bind("click",$p.last);
		});
		
		function addFile(){
			showIPUBDialogStand("tag!uploadFileTagUpload.action?source.sourceId="+sourceId+"&source.catId="+catId+"&source.type="+type, 
					{title:"",width:680,height:540,close:function(){queryFileTags();}});
		}
		
		function queryFileTags() {
	    	$p.ajax();
		}
		
		var contextlist = {data:[],searchObject:function(id){
			if(this.data==undefined||this.data.length == 0||id==undefined){
				return;
			}
			for(var o = 0,obj = this.data[o];obj=this.data[o++];){
				if(obj!=null&&obj.id==id){
					return obj;
				}
			}
		}};
		
		function showlist(result){
			var content = "";
			contextlist.data = [];
			if (result && result.data && result.data.length > 0) {
				$.each(result.data, function(i, f) {
					
					contextlist.data.push({"id":f.name,"text":f.name});
				
					var checked = $("input[name=wordtext][value='"+f.name+"']").attr("checked");
			        content += "<tr>";
			        content += "<td style=\"width: 12px;\">";
			        content += "<label>";
			        content += "<input type=\"checkbox\" value=\""+f.name+"\"  name=\"wordtext\"  "+ (checked ? " checked ":" ") +"class=\"textfileid\" />";
			        content += "</label>";
			        content += "</td>";
			        content += "<td style=\"width: 700px;\">";
			        content += (f.desc.length>50?f.desc.substr(0,50)+"...":f.desc);
			        content += "</td>"; 
			        content += "<td>";
			        content += ((result.page.currentPage-1)*result.page.pageSize+1+i);
			        content += "</td>";				
			        content += "</tr>";
				});
			}
			$("#mytab").html(content);
			
			$('.shujTb > tbody >tr:odd').addClass('alt');
			
			/**
			 * 重置滚动条
			 */
			ipubs.models.jscroll_ee();
		}
    	
   	function edited(result){
   		if(result=='ok'){
   			queryFileTags();
   		}
   	}
   	
 	
   	function removete(){
   		
   		if($("input[name=wordtext]:checked").length==0){
   			return;
   		}
   		
   		var param = {
   				"source.sourceId":sourceId,
				"source.catId":catId,
				"source.type":type,
				"filetagnames":[]
			};
			$("input[name=wordtext]:checked").each(function(i,o) {
					param.filetagnames.push(o.value);
			});
   		
   		confirm(langMap.globalMap.sureToDelete,function(){
   			ajaxCallback("tag!removeFileTag.action",  $.param(param,true), function(result){
				if(result&&result.success){
					queryFileTags();
				}else{
					alert(langMap.globalMap.deleteItemFail);
				}
			});
   			
   		});
   	}
   	
 	function added(result){
   		if(result=='ok'){
   			queryFileTags();
   		}
   	}
 	
 	function moveSequence(upordown){
 		var id = $("input[name=wordtext]:checked").val();
 		if(id==undefined){
 			return;
 		}
   			ajaxCallback("tag!moveFileTag.action", {
				"source.sourceId":sourceId,
				"source.catId":catId,
				"source.type":type,
				"fileTagData.name":id,
				"upordown":upordown
			}, function(result){
				if(result&&result.success){
					queryFileTags();
				}
			});
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
                        <td class="first" style="width: 550px;"><s:text name="content" /></td>
                        <td style="width: 110px;">
                            			<s:text name="seq" /></td>
                    </tr>
      </table>

	
	<div style="height: 335px; overflow: auto; border-right: #ccc solid 2px;">
		<table id="mytab" width="100%" border="0"
		 cellspacing="0" cellpadding="0" class="shujTb shujTb2">
                      
      </table>
      </div>
			 <div style="height:30px; background:#d5dcde;">
			     <div class="page1 page2" style="width:570px; overflow:hidden; padding-top:5px; float:right;">
					 <%@ include file="/template/Public/page.jsp" %>
                  </div>
			 </div>
			 <div class="rightMenu" style=" height:20px; margin:0px; padding:5px;">
                    <ul class="left rightMenuShort">
                    <li>   <a href="javascript:selectedAll()"><s:text name="selectall" /></a></li>
                        <li>         <a  href="javascript:deselect()"><s:text name="invertselection" /></a> </li>
                            <li>     <a  href="javascript:cancel()"><s:text name="cancel" /></a></li>
                       
                    </ul>
      </div>
				<div class="rightMenu" style=" height:20px; margin:0px; padding:5px;">
                    <ul class="left rightMenuShort">
                     <li><a href="javascript:void(0)" onclick="addFile()"><s:text name="create" /></a></li>
                        <li><a href="javascript:void(0)" onclick="removete()"
	     	               ><s:text name="delete" /></a></li>
                        <li><a href="javascript:void(0)" onclick="moveSequence(true);"
		><s:text name="up" /></a></li>
                        <li><a href="javascript:void(0)" onclick="moveSequence(false);"
		><s:text name="down" /></a></li>
                    </ul>
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

