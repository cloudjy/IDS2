<%@page import="com.gnamp.server.model.*"%>
<%@page import="com.gnamp.server.handle.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=8" />
<%@include file="/template/Admin/admintitle.jsp" %>
<style>
 html { overflow: scroll; } 
</style>
<link rel="stylesheet" type="text/css" href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" />
	<link href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="${theme}/skins/default/css/jqtransform.css" media="all" />
	<link rel="stylesheet" type="text/css" media="all" href="${theme}/skins/default/css/niceforms-default.css" />
    
    <script type="text/javascript" src="${theme}/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="${theme}/js/jquery.jqtransform.js"></script>
      <link rel="stylesheet" type="text/css" media="all" href="${theme}/skins/default/css/ipubs-dialog.css" />
    <script type="text/javascript" src="${theme}/js/ipub.models-<s:text name="langjs"/>.js"></script>
    <script type="text/javascript" src="${theme}/js/jscroll.js"></script>
    <script type="text/javascript" src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
    
    <script language="javascript" type="text/javascript" src="${theme}/js/niceforms.js"></script>
	<script type="text/javascript" src="js/gnamp.js"></script>

<script type="text/javascript">
        $(function () {
            $('form').jqTransform({ imgPath: 'skins/default/images/'});
            $("#userlog").addClass("hover");
            
                  	
			
			
            cstmchange();
	});
        
        function cstmchange(){
        	//alert($("#cstm").val());
        	var param = {
        			"strCstmID" : $("#cstm").val()
        		};
        		
        		$p = new page("userlog!list?strCstmID="+$("#cstm").val(),param,query);
        		$("#first").bind("click",$p.first);
    			$("#previous").bind("click",$p.previous);
    			$("#next").bind("click",$p.next);
    			$("#last").bind("click",$p.last);
    			
        		$p.ajax();
        		return false;
        }
        
    	function query(result) {    		
    		try {    			
    			var content = "";
    		
    			$.each(result.data, function(i, u) {
    				 content += 
				 			"<tr>"+
							"<td style=\"width:200px;\">"+u.time+"</td>"+
							"<td style=\"width:100px;\">"+u.userName+"</td>"+
							"<td style=\"width:100px;\">"+u.option+"</td>"+
							"<td>"+u.event+"</td>"+
							"</tr>";  
    					        
    			});    			
    			
    			$("#mytab").html(content);
				  $('form').jqTransform({ imgPath: 'skins/default/images/' });
    			$('.shujTb > tbody >tr:odd').addClass('alt');
    			/**
    			 * 重置滚动条
    			 */
    			ipubs.models.jscroll_ee();
    		} catch (e) {
    			alert(e.message);
    		}
    	}
        

    	
    	function clearevent(){
    		
    		if($("#cstm").val()=="-1"){
    			alert("<s:text name="domainnameerror"/>");
    			return;
    		}
    		
    		
    		confirm("<s:text name="isclear"/>",function(){
    			var param = {
						"strCstmID" : $("#cstm").val()
					};
					$.ajax({
						type : "post",
						dataType : "json",
						url : "userlog!clearevent",
						data : param,
						complete : function() {
						},
						success : function(msg) {
							if (msg.success) {
								cstmchange();
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
</script>
	
</head>

<body>
	<!-- menu 开始-->
	<%@ include file="/admin/header.jsp" %>
	<!-- menu 结束-->

	<div class="topBj1"></div>

	<div class="mid1">
		<div class="bt1">
			<h2><s:text name="log"/></h2>
			<ul class="tab1">
            </ul>
			<form class="topSearchMk2 topSearchMk3" style="margin-top: -10px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                	<td style="width:80px;"><s:text name="domainnameerror"/></td>
				 	<td style="width:100px;float:left;margin-top: 10px;" class="lbButton">
                       	<select id="cstm" name="cstm" onchange="cstmchange();">									
									<% List<Customer> cstmlist = (List<Customer>)request.getAttribute("cstmlist"); 
									   if(cstmlist != null && cstmlist.size()>0){
										   for(int i=0;i<cstmlist.size();i++){
									%>
					                     <option value="<%=cstmlist.get(i).getCstmId() %>"><%=cstmlist.get(i).getShortName()%></option>
					                <%} 
					                }%>
						</select>
                    </td>
                </tr>
            	</table>
			</form>
		</div>
		<div class="nr1">
			<div class="left1">
				<div class="leftMenu">
					<div class="leftMenuBox">
					</div>
				</div>

			</div>
			<!-- 右侧 开始-->
			<div class="right1">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="shujBt" id="shujBt">
					<tr>
					<td style="width:200px;" class="first"><s:text name="time"/></td>
					<td style="width:100px;"><s:text name="user"/></td>
					<td style="width:100px;"><s:text name="action"/></td>
					<td><s:text name="event"/></td>
					</tr>
				</table>
				<div id="ee">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="shujTb" id="mytab">
                       
                    </table>
				</div>


				<div class="rightMenu">

					<ul class="left rightMenuLong">
						<li><a onclick="javascript: clearevent();"><s:text name="clearlog"/></a></li>
					</ul>
				</div>

				<!-- 右侧底部menu 开始-->
				<div class="bottomMenu">
					<div class="rightSelect">
						
							<label onclick="selectedAll()" for="chk_sel_all" ><s:text name="selectall"/></label> | 
							<label onclick="deselect()" for="chk_sel_invert" >
									<s:text name="invertselection"/>
							</label> | 
							<label onclick="cancel()">
									<s:text name="cancel" />
							</label>
					</div>
					<a href="#"> <img src="${theme}/skins/default/images/refresh3.gif"
						border="0" class="ref" /></a>
					<div class="page1 page2" style="width:520px;">
								<span><s:text name="p_altogether" /><b class="orange1"><span
							id="totalData"></span></b>
					<s:text name="p_article" /></span>
					<span><s:text name="p_first" /><b id="currentWithTotal"></b>
					<s:text name="p_page" /></span>
					<span><s:text name="p_everypage" /> <select name="pagesize"
						onchange="$p.changepagesize(this.value)">
							<!-- option value="10">10</option -->
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="<%=Integer.MAX_VALUE %>">
								<s:text name="p_all" />
							</option>
					</select> </span>
					<span><s:text name="p_goto" /> <select name="pageIndex"
						id="pageIndex" onchange="$p.gotoPage(this.value)">
							<option value="1">1</option>
					</select> </span>
					<img src="${theme}/skins/default/images/pageB1.gif" id="first" />
					<img src="${theme}/skins/default/images/pageB2.gif" id="previous" />
					<img src="${theme}/skins/default/images/pageB3.gif" id="next" />
					<img src="${theme}/skins/default/images/pageB4.gif" id="last" />
					</div>
				</div>
				
				<!-- 右侧底部menu 结束-->
			</div>
			<!-- 右侧 结束-->
			<div class="clearit"></div>
		</div>
		<div class="di1"></div>
	</div>
	<%@ include file="/buttom.jsp" %>
</body>
</html>
