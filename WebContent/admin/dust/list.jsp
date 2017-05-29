<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8"%>
<%@ page language="java" import="com.gnamp.server.model.File"%>
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
	<link rel="stylesheet" type="text/css" href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" />
	<link rel="stylesheet" type="text/css" href="${theme}/skins/default/css/jqtransform.css" media="all" />
	<link rel="stylesheet" type="text/css" media="all" href="${theme}/skins/default/css/ipubs-dialog.css" />
	
<style>
.rightSelect label{
	cursor:pointer;
}
</style>

<script type="text/javascript" src="${theme}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${theme}/js/jquery.jqtransform.js"></script>
 <script type="text/javascript" src="${theme}/js/jscroll.js"></script>
<script type="text/javascript" src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
<script type="text/javascript" src="${theme}/js/ipub.models-<s:text name="langjs"/>.js"></script>
<script type="text/javascript" src="js/gnamp.js"></script>
<script type="text/javascript" src="js/dust.js"></script>
<script type="text/javascript">
        $(function () {
            $('form').jqTransform({ imgPath: 'skins/default/images/'});
            $("#recoveryfiles").addClass("hover");
	});
</script>
</head>

<body>
	<!-- menu 开始-->
	<%@ include file="/admin/header.jsp" %>
	<!-- menu 结束-->

	<div class="topBj1"></div>

	<div class="mid1">
		<div class="bt1">
		  <h2><s:text name="recylist"/></h2>
			<ul class="tab1">
              
            </ul>
			<form class="topSearchMk2 topSearchMk3" style="margin-top: -10px;" onsubmit="return false;">
				<table style="width:470px" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    
                    <td style="width: 50px; text-align:right;" ><s:text name="filetype"/></td>
                    <td style="width: 83px;">
                        <select id="type" name="file.type"  onchange="refresh()">
									<option value="-1"><s:text name="fileall"/></option>
									<option value="<%= File.IMAGE %>"><s:text name="filepicture"/></option>
									<option value="<%= File.VIDEO %>"><s:text name="filevedio"/></option>
									<option value="<%= File.AUDIO %>"><s:text name="fileaudio"/></option>
									<!-- option value="<%= File.FLASH %>"><s:text name="fileflash"/></option -->
							</select>
					</td>
			     	<td style="width: 40px; text-align:right;"><s:text name="vender"/></td>
                    <td style="width: 87px;">
                        <select id="vender" name="file.vender"  onchange="refresh()">
								<option value=""><s:text name="fileall"/></option>
						</select>
					</td>
			     	 <td  class="search" style="width:110px; overflow:hidden;">
                     <div class="searchL">
					    <input id="search" name="search" type="text" style="width:110px;"/></div>
					     <div class="searchR" style="width:25px;">
					     <button id="searchFile"></button></div>
			     	</td>
                </tr>
            </table>
			</form>
		</div>
		<div class="nr1">
			<!-- 左侧 开始-->
			<!-- 左侧 结束-->
			<!-- 右侧 开始-->
			<div class="right1">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="shujBt" id="shujBt">
						<thead></thead>
                		<tbody>
								<td class="first" style="width:590px"><s:text name="name"/></td>
								<td><s:text name="size"/></td>
                		</tbody>
					
				</table>
				<div id="ee">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="shujTb" id="mytab">
                       <thead>
                        </thead>
                		<tbody></tbody>
                    </table>
				</div>


				<div class="rightMenu">

					<ul class="left rightMenuLong">
						<li><a href="javascript:remove()"><s:text name="delete"/></a></li>
						<li><a href="javascript:reduction()"><s:text name="reduction"/></a></li>
					</ul>
				</div>

					<!-- 右侧底部menu 开始-->
				<div class="bottomMenu">
					<div class="rightSelect" style="width:200px;">
						
							<label onclick="selectedAll()" for="chk_sel_all" ><s:text name="selectall"/></label> | 
							<label onclick="deselect()" for="chk_sel_invert" >
									<s:text name="invertselection"/>
							</label> | 
							<label onclick="cancel()">
									<s:text name="cancel" />
							</label>
					</div>
					<a href="#"> <img src="${theme}/skins/default/images/refresh3.gif" border="0" class="ref" /></a>
					<div class="page1 page2" style="width:520px;">
						<%@ include file="/template/Public/page.jsp" %>
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
