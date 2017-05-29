<%@page import="com.gnamp.struts.action.BaseAction"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
 <div class="mid1">
        <div class="menu">
            <ul>
            	<%-- --%>		
            	<li class="m1">
					<div id="shouye">
						<a href="index.action"><s:text name="index" /></a>
					</div>
				</li>
				<li class="m3">
					<div id="wenjian">
						<a href="file.action"><s:text name="header_file" /></a>
					</div>
				</li>
				<li class="m4">
					<div id="bofang">
						<a href="looptask.action"><s:text name="header_player" /></a>
					</div>
				</li>
				<li class="m2">
					<div id="zhongduan">
						<a href="terminal.action"><s:text name="header_terminel" /></a>
					</div>
				</li>
				<%-- 		
				<li class="m8">
					<div id="muban">
						<a href="templet.action"><s:text name="header_templet" /></a>
					</div>
				</li>  
				--%>
				<li class="m5">
					<div id="jiankong">
						<a href="monitor.action"><s:text name="header_monitor" /></a>
					</div>
				</li>
				<li class="m6">
					<div id="quanxian">
						<a href="user.action"><s:text name="header_permissions" /></a>
					</div>
				</li>
				<li class="m7">
					<div id="tongji">
						<a href="playlog.action" ><s:text name="header_reports" /></a>
					</div>
				</li>
				<li class="m_bangzhu">
					<div id="bangzhu">
						<a href="help.action" ><s:text name="header_help" /></a>
					</div>
				</li>
			</ul>
            <div class="menuR">
            	<span style="display:inline-block;font-size: 13px;"><%=session.getAttribute(BaseAction.SESSION_USER_NAME) %></span>
               <% if(!session.getAttribute(BaseAction.SESSION_SHORT_NAME).equals("test")) {%>
                <a href="javascript:changepassword();" id="config"><img src="${theme}//skins/default/images/menuButton1.gif" /></a>
                <%} %>
                <a title="<s:text name="exit_system" />" href="javascript:suretoexit()"><img src="${theme}/skins/default/images/menuButton2.gif" /></a>
            </div>
        </div>
    </div>
    <script>
    $(function(){
		$("#config").bind("click",showconfig);
    });
    function suretoexit(){
    	confirm("<s:text name="suretoexit"/>",function(){
    		location.href = "logout.action";
    	});
    }
    function showconfig(){
    	$("#configpanelz").toggle("last");
    }
    function changepassword(){
    	showIPUBDialogStand("changepassword.action",{title:"<s:text name="changepassword"/>",width:400,height:270});
    }
    $(document).ready(function() {
		$('.menu ul>li').hover(function() {
			$(this).children('div').addClass('on');
		}, function() {
			$(this).children('div').removeClass('on');
		});
	});
    </script>
