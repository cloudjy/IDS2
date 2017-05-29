<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=7" />
<link rel="stylesheet" type="text/css"
	href="js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" />
<link href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet"
	href="skins/default/css/jqtransform.css" media="all" />
<script type="text/javascript"
	src="${theme}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript"
	src="js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
<script type="text/javascript"
	src="js/jquery.jqtransform.js"></script>


<script type="text/javascript" src="js/ipub.models.js"></script>
<script type="text/javascript">
        $(function () {
            $('form').jqTransform({ imgPath: 'skins/default/images/'});
            $("#wenjian").addClass("hover");
	});
</script>
<script type="text/javascript" src="js/jscroll.js"></script>
</head>

<body>
	<!-- menu 开始-->
	<%@ include file="/admin/header.jsp" %>
	<!-- menu 结束-->

	<div class="topBj1"></div>

	<div class="mid1">
		<div class="bt1">
			<h2><s:text name="filelist"/></h2>
			<form class="topSearchMk jqtransform">
				<input name="search" type="text" id="search"/>
				<button id="searchFile"></button>
				<p>
					<input  name="lov2" type="checkbox" value="11" id="includeChildren"
						class="checkbox" /><label><s:text name="containdir"/></label>
				</p>
			</form>
		</div>
		<div class="nr1">
			<!-- 左侧 开始-->
			<div class="left1">

			</div>
			<!-- 左侧 结束-->
			<!-- 右侧 开始-->
			<div class="right1">
				<div id="ee">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="shujTb" id="mytab">
                        <thead></thead>
                		<tbody></tbody>
                    </table>
				</div>


				<!-- 右侧底部menu 开始-->
				<!-- 右侧底部menu 结束-->
			</div>
			<!-- 右侧 结束-->
			<div class="clearit"></div>
		</div>
		<div class="di1"></div>
	</div>
	<%@ include file="/buttom.jsp" %>
	<!-- 导航hover-->
	<script>
		$(document).ready(function() {
			$('.menu ul>li').hover(function() {
				$(this).children('div').addClass('on');
			}, function() {
				$(this).children('div').removeClass('on');
			});
		});
	</script>
	<!-- 导航二级菜单-->
	<script type="text/javascript">
		$(function() {
			function hos(hoTit, on, hoCon) {
				$(hoTit).hover(function() {
					$(this).eq(0).addClass(on);
					var index = $(hoTit).index(this);
					$(hoCon).eq(index).show();
				}, function() {
					$(this).eq(0).removeClass(on);
					var index = $(hoTit).index(this);
					$(hoCon).eq(index).hide();
				});
			}
			hos(".hoTit", "hover", ".hoCon");
		});
	</script>
	<!-- 分页按钮效果 -->
	<script>
		$(function() {
			$(".page1 img").hover(function() {
				$(this).css("opacity", "1");
			}, function() {
				$(this).css("opacity", "0.6");
			});
		});
	</script>
	<!-- 表格间隔变色 -->
	<script>
		$(document).ready(function() {
			// $('tr:odd').addClass('alt');
			$('tr:nth-child(even)').addClass('alt');

			$('td:contains(Henry)').nextAll().andSelf().addClass('highlight');//.siblings()
			$('th').parent().addClass('table-heading');
		});
	</script>

</body>
</html>
