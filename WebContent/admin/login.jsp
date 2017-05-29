<%@page import="com.gnamp.struts.filter.CookieContext"%>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="x-ua-compatible" content="ie=7" />
    <%@include file="/build.jsp" %>
    <%@include file="/template/Admin/admintitle.jsp" %>
    
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
       		location.href = "<s:url encode="true"/>?request_locale="+language;
        }
        
    </script>
</head>
<body style="height: 800px;">

    <!-- login 开始-->
    <div class="loginBg">
        <div class="mid2">
            <label class="logo"><%=com.gnamp.struts.filter.ContextListener.product %><s:text name="logo_a"/></label>
            <ul class="loginMenu">
               <%
            		if (com.gnamp.struts.filter.Context.isMultiLanguageVersion() ){
            	%>
                <li onclick="changeLanguage('zh_CN')">简体中文</li>

                <li onclick="changeLanguage('zh_TW')">繁體中文</li>

                <li onclick="changeLanguage('en_US')">English</li>
                
                <li onclick="changeLanguage('es_ESP')">Spanish</li>
                <%
            		}
                %>
                <%-- <li><s:text name="skin"/></li> --%>
            </ul>
            
           <%
			String userName = "";
			String autoSave = "false";
			String password = "";
			Cookie [] cookies = request.getCookies();
			if(cookies!=null&&cookies.length>0){
				for(Cookie key : cookies){
					if(CookieContext.COOKIE_ADMIN_USER_NAME.equals(key.getName())){
						userName = key.getValue();
					}else if(CookieContext.COOKIE_ADMIN_PASS_WORD.equals(key.getName())){
						password = key.getValue();
					}else if(CookieContext.COOKIE_ADMIN_AUTO_SAVE.equals(key.getName())){
						autoSave = key.getValue();
					}
				}
			}
			%>

			<div class="loginFlag"></div> 
            <div class="loginBox" id="loginBox">
             <input name="uid" type="text" class="loginText1" value="<%=userName %>" placeholder="<s:text name="username"/>" title="<s:text name="username"/>"/>
             <input name="pwd" type="password" class="loginText2" value="<%=password %>" placeholder="<s:text name="password"/>" title="<s:text name="password"/>"/>
             <input name="code" type="text" class="loginText4" value="" maxlength="4" placeholder="<s:text name="validateNumber"/>" title="<s:text name="validateNumber"/>"/>

                <img src="#" class="code" title="<s:text name="login_clickrefresh"/>" width="60" height="30" alt="input validate code" onclick="refresh()" />
                 <p>
            <input name="autoSave" type="checkbox" class="fx"  <%="true".equals(autoSave)?"checked":"" %>/>
            <span>&nbsp;<s:text name="remeberme"/></span>
            </p>
            </div>
            <a class="loginBtn" href="javascript:login()"  title="<s:text name="login"/>"></a>
			
            <div class="foot" style="position: absolute;font-family:'微软雅黑'; top: 510px; width: 960px; text-align: center;line-height: 30px">
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
     $(function(){
    	 refresh();	 
     });
     
    </script>
</body>
</html>
