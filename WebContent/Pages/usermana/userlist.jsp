<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="ss" uri="/struts-dojo-tags" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
			"yyyy-MM-dd mm:hh:ss");
	java.util.Date currentTime = new java.util.Date();//得到当前系统时间 
	String str_date1 = formatter.format(currentTime); //将日期时间格式化
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="x-ua-compatible" content="ie=8" />
   	<%@include file="/template/Public/title.jsp" %>
   <style>
 html { overflow: scroll; } 
</style>
  	<link rel="stylesheet" type="text/css" href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" />
	<link href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="${theme}/skins/default/css/jqtransform.css" media="all" />
	<link rel="stylesheet" type="text/css"  href="${theme}/skins/default/css/ipubs-dialog.css"/>
    
    <script type="text/javascript" src="${theme}/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="${theme}/js/jquery.jqtransform.js"></script>
    <script type="text/javascript" src="${theme}/js/jscroll.js"></script>
    <script type="text/javascript" src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
    <script type="text/javascript" src="${theme}/js/ipub.models-<s:text name="langjs"/>.js"></script>
	<script type="text/javascript" src="js/gnamp.js"></script>
	<script type="text/javascript" src="js/duma.js"></script>
	
    <script type="text/javascript">
        /*第一种形式 第二种形式 更换显示样式*/
        function setTab(name, cursel, n) {
            for (var i = 1; i <= n; i++) {
                var menu = document.getElementById(name + i);
                var con = document.getElementById("con_" + name + "_" + i);
                menu.className = i == cursel ? "hover" : "";
                con.style.display = i == cursel ? "block" : "none";
            }
        }
        $(function () {
            $('form').jqTransform({ imgPath: '${theme}/skins/default/images/' });
            $("#quanxian").addClass("hover");
        });
    </script>
	<script src="js/user.js"></script>
</head>
<body>
    <!-- menu 开始-->
   <%@ include file="/header.jsp" %>
    <!-- menu 结束-->

    <div class="topBj1"></div>

    <div class="mid1">
      <div class="bt1">
            <ul class="tab1">
                <li id="one1" onclick="location.href='user.action'"  class="hover"><s:text name="userlist"/></li>
                <li id="one2" onclick="location.href='role.action'"><s:text name="rolelist"/></li>
            </ul>
     </div>
      <div class="nr1">
          <!-- 左侧 开始-->
          <div class="left1">
          	 <div class="leftMenu">
              <div class="dumascroll" style="height:85%;">
              	<form class="jqtransform" >
                   <ul id="con_one_1" class="leftMenu2 leftMenu3">
                   </ul>
                  </form>
              </div>
			</div>
              <div class="leftButtonBox">
                <s:if test="#session.current_privilege.newuser">
              		<a href="javascript:add()"><s:text name="create"/></a>
 				</s:if>
                <s:if test="#session.current_privilege.edituser">
              		<a href="javascript:update()"><s:text name="edit"/></a>
               	</s:if>
                <s:if test="#session.current_privilege.deluser">
	            	<a href="javascript:del()"><s:text name="delete"/></a>
               	</s:if>
              <a href="javascript:sortlist()">A-Z</a>
              </div>
          </div>
          <!-- 左侧 结束-->
          <!-- 右侧 开始-->
          <div class="right1">
              <table width="100%" border="0" cellspacing="0" cellpadding="0" class="shujBt">
                  <tr>
                      <td class="first" style="width: 232px;"><s:text name="role_name"/></td>
                      <td style=" width: 232px;"><s:text name="role_descp"/></td>
                      <td ><s:text name="role_select"/></td>
                  </tr>
              </table>
              <div id="ee">
              	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="shujTb" id="mytab">
                      <thead></thead>
              		<tbody></tbody>
                  </table>
              </div>
              
              <div class="rightMenu" style="height: 20px">
              	<ul class="left rightMenuLong" >
              	  <s:if test="#session.current_privilege.assignroles">
              	   <li onclick="javascript:updateRole();"><s:text name="update"/></li>
              	  </s:if>
              	 </ul>
              </div>
              <div style="font-size:12px;padding-left:20px;"><s:text name="bottom_prompt_user_manager"/></div>
              
              <!-- 右侧底部menu 开始-->
              <div class="bottomMenu">
                <div class="rightSelect">
                     	<label onclick="selectedAll()"><s:text name="selectall"/></label> | 
                     	<label onclick="deselect()"><s:text name="invertselection"/></label> | 
                     	<label onclick="cancel()"><s:text name="cancel"/></label>
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
    <!-- 导航二级菜单-->
    <script type="text/javascript">
        $(function () {
            function hos(hoTit, on, hoCon) {
                $(hoTit).hover(function () {
                    $(this).eq(0).addClass(on);
                    var index = $(hoTit).index(this);
                    $(hoCon).eq(index).show();
                }, function () {
                    $(this).eq(0).removeClass(on);
                    var index = $(hoTit).index(this);
                    $(hoCon).eq(index).hide();
                });
            }
            hos(".hoTit", "hover", ".hoCon");
        });

</script>

</body>
</html>
