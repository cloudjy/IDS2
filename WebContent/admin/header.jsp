<%@page import="com.gnamp.struts.admin.action.AdminBaseAction"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!-- menu 开始-->
 <div class="mid1">
        <div class="menu">
            <ul>
				<li class="m2">
					<div id="custmana">
						<a href="customer.action"><s:text name="custmana" /></a>
					</div>
				</li>
				<li class="m3">
					<div id="app">
						<a href="app.action"><s:text name="progmana" /></a>
					</div>
				</li>
				<li class="m4">
					<div id="firmmana">
						<a href="kernel.action"><s:text name="firmmana" /></a>
					</div>
				</li>
				<li class="m5">
					<div id="weathermana">
						<a href="weather.action"><s:text name="weathermana" /></a>
					</div>
				</li>
				<li class="m6">
					<div id="recoveryfiles">
						<a href="dust.action"><s:text name="recoveryfiles" /></a>
					</div>
				</li>
				<li class="m7">
					<div id="userlog">
						<a href="userlog!prelist"><s:text name="userlog" /></a>
					</div>
				</li>
				<!-- li class="m8 configtemp">
					<div  id="configtemp">
						<a href="template.action"><s:text name="configtemp" /></a>
					</div>
				</li -->
			</ul>
             <div class="menuR">
            	<span style="display:inline-block;font-size: 13px;"><%=session.getAttribute(AdminBaseAction.SESSION_USER_NAME) %></span>
                <a href="javascript:changepassword();" id="config"><img src="${theme}//skins/default/images/menuButton1.gif" /></a>
                <a href="javascript:suretoexit()" ><img src="${theme}/skins/default/images/menuButton2.gif" /></a>
            </div>
        </div>
    </div>

<!-- menu 结束-->
<script type="text/javascript">
function changepassword(){
	showIPUBDialogStand("changepassword.action",{title:"<s:text name="text_changepassword"/>",width:400,height:270});
}
function suretoexit(){
	confirm("<s:text name="suretoexit"/>",function(){
		location.href = "admin-logout.action";
	});
}
$(document).ready(function() {
	$('.menu ul>li').hover(function() {
		$(this).children('div').addClass('on');
	}, function() {
		$(this).children('div').removeClass('on');
	});
});
</script>



