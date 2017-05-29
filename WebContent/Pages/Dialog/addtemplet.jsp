<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="com.gnamp.server.model.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<link href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" rel="stylesheet" type="text/css" />
<script src="${theme}/js/jquery-1.7.2.min.js"></script>
<script language="javascript" type="text/javascript" src="${theme}/js/niceforms.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="${theme}/skins/default/css/niceforms-default.css" />

<link rel="stylesheet" type="text/css"
	href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" /> 
<script src="${theme}/js/ipub.models-<s:text name="langjs"/>.js" type="text/javascript"></script>
<script type="text/javascript"
	src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>   
<link href="${theme}/skins/default/css/ipubs-dialog.css" rel="stylesheet" type="text/css" />
<script src="js/gnamp.js" language="javascript"></script>
<script type="text/javascript">
		function changedir(callfunc){	
			$("#nameErrorDiv").showError("");
			$("#categoryErrorDiv").showError("");
			if(!$("#tempName").val()){
				//alert("<s:text name="namenull" />");
				$("#nameErrorDiv").showError("<s:text name="namenull"/>");
				return;
			}
			if(!$("#category").val()){
				//alert("<s:text name="catnull" />");
				$("#categoryErrorDiv").showError("<s:text name="namenull"/>");
				return;
			}

			 var now = $('#fbl option:selected').val();
		        if (now == "") return;
		        var w = $('.cssW');
		        var h = $('.cssH');
		        var res = new String(now).split('x');
		        w.val(res[0]);
		        h.val(res[1]);
		        
		if(!istemplet)	{	
		      $.post('templet!add',
						{"template.tempName":$("#tempName").val(),
					     "template.tempDesc":$("#description").val(),
					     "istemplet":istemplet,
						 "template.width":w.val(),
						 "template.height":h.val(),
						 "template.category":$("#category").val()
			            },
		    	function(txt) {
			      callfunc(txt);
			});
		}else{
			 $.post('templet!add',
						{"template.tempName":$("#tempName").val(),
					     "template.tempDesc":$("#description").val(),
					     "istemplet":istemplet,
						 "tempID":$("#ddltemplate").val(),
						 "template.category":$("#category").val()
			            },
		    	function(txt) {
			      callfunc(txt);
			});
			}
	      
	}
 
	function executeFunction(result) {
		if (result.success) {
			callParentFunction("added", 'ok');
			return closeIFrame();
		} else {
			if (result.data && result.message) { 
				if (result.data == "NameCannotNull" || result.data == "NameExist") {
					$("#nameErrorDiv").showError(result.message); 
				} else if (result.data == "CategoryCannotNull") {
					$("#categoryErrorDiv").showError(result.message); 
				} else {
					alert(result.message); 
				}
			} else if (result.message) {
				alert(result.message); 
			}	else {
				alert("<s:text name="additemfail"/>");
			}	 
		}
	}
	
	   //处理分辨率
    function resolutionChanged() {
        var now = $('#fbl option:selected').val();
        if (now == "") return;
        var w = $('.cssW');
        var h = $('.cssH');
        var res = new String(now).split('x');
        w.val(res[0]);
        h.val(res[1]);
    }

    var istemplet = false;
 function QuerySystemByCategory() {    	
    
	 	if($("#ddlsystemcategory").val()==""){ 
    		$("#fbl").css('display','block'); 
    		istemplet = false;
    		$("#lblmsg").html("");
    	}
    	else
   		{    		
    		$("#fbl").css('display','none'); 
    		istemplet = true;
   		}
	 
	    var str = "";
		var param = {
			"leftstr" : $("#ddlsystemcategory").val()
		};

		$.ajax({
			type : "post",
			dataType : "json",
			url : "templet!QuerySystemTemplateByCategory",
			data : param,
			complete : function() {
			},
			success : function(msg) {
				if (msg.success) {
					try {
						var content = "";
						var hidd = "";
						$.each(msg.data, function(i, temp) {

							if(i==0 && istemplet){
								str = temp.width+"x"+temp.height;
								$("#lblmsg").html(str);
							}
							
							content += "<option value=\""+temp.tempId+"\">"
									+ temp.tempName + "</option>";
						    hidd += "<input type=\"hidden\" value=\""+(temp.width+"x"+temp.height)+"\" id=\""+temp.tempId+"\" />";
						});
						$("#ddltemplate").html(content);
						$("#lblhidd").html(hidd);
					} catch (e) {
						alert(e.message);
					}
				} else {
					alert(msg.success);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				window.top.location.href='/';
			}
		});
		return false;
	}
 function ddltemplatechange(){    	
     $("#lblmsg").html( $("#"+$("#ddltemplate").val()+"").val()    );
   }
</script>
</head>
<body> 
  	<!-- 弹出窗口 开始-->
	<div class="tcWindow">
		<div class="tcBt">
			<img src="${theme}/skins/default/images/tcBt1.gif" width="5" height="35"
				class="left" />
			<h2></h2>
			<img src="${theme}/skins/default/images/tcBt3.gif" class="right" />
		</div>
		<div class="tcNr">
			<form action="#" method="post" class="niceform">
 <input class="cssW" name="width" id="width" type="hidden" />
 <input class="cssH" name="height" id="height" type="hidden" />
	<table width="100%" border="0" cellspacing="2" cellpadding="0"
					class="bdTab">
		<tr>
			<td class="bt" style="width: 30%"><s:text name="name" /></td>
			<td>
				<input type="text" name="tempName" id="tempName" />
				<div id="nameErrorDiv" style="color:#FF0000;"></div>
			</td>
		</tr>
		<tr>
			<td  class="bt"><s:text name="desc" /></td>
			<td><input type="text" 
				name="description" id="description" /></td>
		</tr>
		<tr>
			<td  class="bt"><s:text name="category" /></td>
			<td>
				<input type="text" name="category" id="category" />
				<div id="categoryErrorDiv" style="color:#FF0000;"></div>
			</td>
		</tr>
		<tr>
		 <td class="bt"><s:text name="used" /></td>
		 <td>
		    <select size="1"
				name="category" onchange="QuerySystemByCategory();" id="ddlsystemcategory"
				style="margin-right:5px;">
					<option value="" >
						<s:text name="no" />
					</option>
					<%
						if (request.getAttribute("systemtempletcategorys") != null) {
							List<String> list = (List<String>) request.getAttribute("systemtempletcategorys");

							for (int i = 0; i < list.size(); i++) {
					%>
					<option value="<%=list.get(i)%>" ><%=list.get(i)%></option>
					<%
						}
						}
					%>
			</select>
			<select onchange ="ddltemplatechange();" size="1" id="ddltemplate" 
				name="ddltemplate">
					<option value="0">
						<s:text name="select" />
					</option>
			</select>
			 </td>
		</tr>
		<tr>
			<td class="bt"><s:text name="resolution" /></td>
			<td> <select size="1" id="fbl" onchange="resolutionChanged()">
             <option value="720x480">720x480</option>
              <option value="720x576">720x576</option>
			 <option value="800x480">800x480</option>
              <option value="800x600">800x600</option>
              <option value="1024x600">1024x600</option>
              <option value="1024x768">1024x768</option>
              <option value="1280x720">1280x720</option>
              <option value="1280x800">1280x800</option>
              <option value="1280x1024">1280x1024</option>
              <option value="1366x768">1366x768</option>
              <option value="1440x900">1440x900</option>
              <option value="1680x1050">1680x1050</option>
             <option selected="selected" value="1920x1080">1920x1080</option>
             
              <option value="480x720">480x720</option>
               <option value="480x800">480x800</option>
              <option value="576x720">576x720</option>
               <option value="600x800">600x800</option>
              <option value="600x1024">600x1024</option>
              <option value="720x1280">720x1280</option>
             <option value="768x1024">768x1024</option>
              <option value="768x1366">768x1366</option>
              <option value="800x1280">800x1280</option>
              <option value="900x1440">900x1440</option>
              <option value="1024x1280">1024x1280</option>
              <option value="1050x1680">1050x1680</option>
              <option value="1080x1920">1080x1920</option>
			</select><span id="lblmsg"></span>
			<span style="display:none" id="lblhidd"></span>
			</td>
		</tr>
		<tr>
				<td class="bt">&nbsp;</td>
						<td><input type="button" value="<s:text name="yes"/>"
							onclick="changedir(executeFunction)" style="line-height: 13px;" />&nbsp;&nbsp;&nbsp;&nbsp;<input
							type="button" onclick="closeIFrame();" value="<s:text name="cancel" />" /></td>
		</tr>
	</table>
	</form>
	
		</div>
		<div class="tcDi">
			<img src="${theme}/skins/default/images/tcDi1.gif" class="left" /><img
				src="${theme}/skins/default/images/tcDi2.gif" class="right" />
		</div>
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
	
<script type="text/javascript">	
	$("input").keydown(function(event){
   		switch(event.keyCode) {
			case 13:
				changedir(executeFunction);
				return false;
				break;
	   	}
   	});
</script>

</body>
</html>

