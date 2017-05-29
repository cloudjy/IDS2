<%@page import="com.gnamp.struts.filter.Context"%>
<%@page import="com.gnamp.struts.filter.CookieContext"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%
	boolean INPUT_DOMAIN = false;
	Object domainLogin = request.getParameter("domain_login");
	INPUT_DOMAIN = (domainLogin != null && domainLogin.toString().trim().equalsIgnoreCase("true"));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="x-ua-compatible" content="ie=7" />
    <%@include file="/build.jsp" %>
    <%@include file="/template/Public/title.jsp" %>
    <link rel="stylesheet" type="text/css" href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" />
    <link rel="stylesheet" type="text/css" href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" />
    <link rel="stylesheet" type="text/css" href="${theme}/skins/default/css/jqtransform.css" />
  	<link rel="stylesheet" type="text/css"  href="${theme}/skins/default/css/ipubs-dialog.css"/>
  	
  	
    <script type="text/javascript" src="${theme}/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="${theme}/js/jquery.jqtransform.js"></script>
    <script type="text/javascript" src="${theme}/js/jquery.placeholder.js"></script>
    <script type="text/javascript" src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
    <script type="text/javascript" src="${theme}/js/ipub.models-<s:text name="langjs"/>.js"></script>
    <script type="text/javascript" src="js/login.js"></script>
    <style type="text/css">
        .ui-inputbg { background: red;}
    </style>
    <script type="text/javascript">
		
        $(function () {
            $('.ipub-logout').click(function () { location.href = "/login.html"; });
            $('.jqtransform').jqTransform();
        });
        
        function changeLanguage(language){ 
       		location.href = "<s:url encode="true"/>?request_locale="+language+ 
       				"<%=INPUT_DOMAIN? "&domain_login=true" : ""%>"; 
        }
        
    </script>
    
    
	<style type="text/css"> 
		#bubble_tooltip{
			position:absolute;
			left: 650px; 
			top: 420px; 
			width: 150px; 
			height: 60px;
			background-image: url('${theme}/skins/default/images/loginError.png');
			font-family: "微软雅黑", "微軟雅黑", "Calibri", "Arial"; 
			font-size: 12px;color:#FF0000;
		}  
	</style> 
	
</head>
<body style="height: 800px;">	
    <!-- login 开始-->
    <div class="loginBg">
        <div class="mid2">
        <label class="logo"><%=com.gnamp.struts.filter.ContextListener.product %><s:text name="logo_a"/></label>
            <ul class="loginMenu">
            	<% if(Context.isMultiLanguageVersion()){ %>
	                <li onclick="changeLanguage('zh_CN')"><%= Context.isAlcoholVersion()? "ZH_CN" : "简体中文" %></li>
	                
	                <li onclick="changeLanguage('zh_TW')"><%= Context.isAlcoholVersion()? "Zh_TW" : "繁體中文" %></li>
	
	                <li onclick="changeLanguage('en_US')">English</li>
	                
	                <li onclick="changeLanguage('es_ESP')">Spanish</li>
                <% } %>
               <%--  <li><s:text name="skin"/></li> --%>
            </ul>
			<%
			String cstm = "";
			String userName = "";
			String autoSave = "false";
			String autoLogin = "false";
			String password = "";
			Cookie [] cookies = request.getCookies();
			if(cookies!=null&&cookies.length>0){
				for(Cookie key : cookies){
					if(CookieContext.COOKIE_CSTM_NAME.equals(key.getName())){
						cstm = key.getValue();
					}else if(CookieContext.COOKIE_USER_NAME.equals(key.getName())){
						userName = key.getValue();
					}else if(CookieContext.COOKIE_PASS_WORD.equals(key.getName())){
						password = key.getValue();
					}else if(CookieContext.COOKIE_AUTO_SAVE.equals(key.getName())){
						autoSave = key.getValue();
					}else if(CookieContext.COOKIE_AUTO_LOGIN.equals(key.getName())){
						autoLogin = key.getValue();
					}
				}
			}

	     	String allowAutoLogin = request.getAttribute("allowAutoLogin") == null? 
	     			"false" : request.getAttribute("allowAutoLogin").toString(); 
	     	boolean doAutoLogin = ( "true".equalsIgnoreCase(allowAutoLogin) && 
	     			"true".equalsIgnoreCase(autoSave) && "true".equalsIgnoreCase(autoLogin));
			%>
			<div class="loginFlag"></div> 
            <div class="loginBox" id="loginBox">
             <input name="uid" type="text" class="loginText1" value="<%=userName%>" placeholder="<s:text name="username"/>" title="<s:text name="username"/>" onfocus="hideErrorBox();"/>
             <input name="pwd" type="password" class="loginText2" value="<%=password%>" placeholder="<s:text name="password"/>" title="<s:text name="password"/>" onfocus="hideErrorBox();"/>
             <input name="domain" type="text" class="loginText3" value="<%=cstm%>" placeholder="<s:text name="region"/>" title="<s:text name="region"/>" onfocus="hideErrorBox();"/>
             <input name="code" type="text" class="loginText4" value="" maxlength="4" placeholder="<s:text name="validateNumber"/>"  title="<s:text name="validateNumber"/>" onfocus="hideErrorBox();"/>
                <img src="#" class="code" width="60" height="30"  title="<s:text name="login_clickrefresh"/>" alt="input validate code" onclick="refresh()" />
            <p style="margin-left: 50px;">
            <input name="autoSave" type="checkbox" class="fx"  <%="true".equalsIgnoreCase(autoSave)?"checked":"" %> onfocus="hideErrorBox();"/>
            <span>&nbsp;<s:text name="remeberme"/></span>
            <input name="autoLogin" type="checkbox" class="fx"  <%="true".equalsIgnoreCase(autoLogin)?"checked":"" %> onfocus="hideErrorBox();"/>
            <span>&nbsp;<s:text name="autologin"/></span>
            <input name="domainInput" type="hidden"  value="<%=INPUT_DOMAIN?"true":""%>"/>
            </p>
            	
            </div>
            
			<div id="bubble_tooltip" style="display:none;"> 
				<div id="bubble_tooltip_content" style="padding-top:25px;text-align: center;"></div>
			</div>
			
            <a class="loginBtn" href="javascript:login()" title="<s:text name="login"/>"></a>
			
			
            <div class="foot"  style="top: 510px; width: 960px; text-align: center;position: absolute;line-height: 30px;">
             <%=com.gnamp.struts.filter.ContextListener.product %><s:text name="logo_a"/>&nbsp;<%=com.gnamp.struts.filter.ContextListener.version %>&nbsp;<%=RELEASE_DATE_SVN %>
             <p style="color:#ADADAD"><s:text name="proposal"/></p>
            </div>
        </div>

    </div>
    <!-- login 结束-->

    <script type="text/javascript">
   		
   	$("input").keydown(function(event){
   		switch(event.keyCode) {
			case 13:
				login();
				break;
	   	}
   	});
     function resizewindow() {
         if ($(document).height() > $(document.body).height()) {
             var h = parseInt(($(document).height() - $(document.body).height()) / 2);
             $(".loginBg").css('margin-top', h + 'px');
         }
     };
     $(window).resize(function () {
         resizewindow();
     });
     resizewindow();
     <%  if (doAutoLogin) { %>  
		var param = {
			     "shortName" : $("input[name='domain']").val(), 
			     "user.userName" : $("input[name='uid']").val(), 
			     "user.password" : $("input[name=pwd]").val()
		}; 
		$.post("login!autoLoginVerifyCode",param,function(msg){
		     if(msg.success && msg.data && msg.data.length == 4){
		         $("input[name=code]").val(msg.data);
		         login();
		     } else {
		         window.refresh();
		     }
		}); 
     <%  } else {  %>
     	window.refresh();
     <%  } %> 


     <% if (!INPUT_DOMAIN) { %> 
		$("input[name=domain]").hide();
    	$("input[name=domain]").val("demo");
    	$(".loginBox").css("padding-top", "45px");
    <% 
     }	
    %> 
     
    </script>
</body>
</html>
