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
	<script src="js/role.js"></script>
	<script type="text/javascript">
        $(function () { 
            $("#quanxian").addClass("hover");
        });
    </script>
</head>
<body>
    <!-- menu 开始-->
   <%@ include file="/header.jsp" %>
    <!-- menu 结束-->

    <div class="topBj1"></div>

    <div class="mid1">
        <div class="bt1">
            <ul class="tab1">
                <li id="one1" onclick="location.href='user.action'"><s:text name="userlist"/></li>
                <li id="one2" onclick="location.href='role.action'" class="hover"><s:text name="rolelist"/></li>
            </ul>
        </div>
        <div class="nr1">
            <!-- 左侧 开始-->
            <div class="left1">
            <div class="leftMenu">
               <div class="dumascroll" style="height:85%">
                  	<form class="jqtransform" >
	                    <ul id="con_one_1" class="hover leftMenu2 leftMenu3">
	                    </ul>
                    </form>
                </div>
			</div>
                <div class="leftButtonBox">
                <s:if test="#session.current_privilege.newrole">
                	<a href="javascript:add()"><s:text name="create"/></a>
                </s:if>
                <s:if test="#session.current_privilege.editrole">
                	<a href="javascript:update()"><s:text name="edit"/></a>
                </s:if>
                <s:if test="#session.current_privilege.delrole">
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
                        <td class="first" style="padding-left: 20px;"><s:text name="rolepermissions"/></td>
                    </tr>
                </table>
                <div id="ee">
                  	<div class="memberBt"><s:text name="filemanager" /></div>
                    <div class="memberNr">
						<input name="privilege" type="checkbox" value="2"   class="privilegeID"  /><label for="1"><s:text name="newcatalog" /></label>
						<input name="privilege" type="checkbox" value="1"   class="privilegeID" /><label for="0"><s:text name="editdir" /></label>
						<input name="privilege" type="checkbox" value="3"   class="privilegeID"  /><label for="2"><s:text name="deldir" /></label>
						<input name="privilege" type="checkbox" value="4"   class="privilegeID"  /><label for="3"><s:text name="uploadfile" /></label>
						<input name="privilege" type="checkbox" value="7"   class="privilegeID"  /><label for="6"><s:text name="editfile" /></label>
						<input name="privilege" type="checkbox" value="6"   class="privilegeID"  /><label for="5"><s:text name="delfiles" /></label>
					</div>
					<div class="memberNr">
						<input name="privilege" type="checkbox" value="5"   class="privilegeID"  /><label for="4"><s:text name="auditfile" /></label>
					</div>
					
	 				<div class="memberBt"><s:text name="manageplay" /></div>
	  				<div class="memberNr" >
						<input name="privilege" type="checkbox" value="12"   class="privilegeID"  /><label for="11"><s:text name="newtask" /></label>
						<input name="privilege" type="checkbox" value="8"   class="privilegeID"  /><label for="7"><s:text name="edittask" /></label>
						<input name="privilege" type="checkbox" value="13"   class="privilegeID"  /><label for="12"><s:text name="deltask" /></label>
						<input name="privilege" type="checkbox" value="9"   class="privilegeID"  /><label for="8"><s:text name="auditcarousel" /></label>
						<input name="privilege" type="checkbox" value="10"   class="privilegeID"  /><label for="9"><s:text name="aditdemand" /></label>
						<input name="privilege" type="checkbox" value="11"   class="privilegeID"  /><label for="10"><s:text name="auditspot" /></label>
					</div>
					<div class="memberNr">
						<input name="privilege" type="checkbox" value="14"   class="privilegeID"  /><label for="13"><s:text name="newprog" /></label>
						<input name="privilege" type="checkbox" value="16"   class="privilegeID"  /><label for="15"><s:text name="editprog" /></label>
						<input name="privilege" type="checkbox" value="15"   class="privilegeID"  /><label for="14"><s:text name="delprog" /></label>
						<input name="privilege" type="checkbox" value="17"   class="privilegeID"  /><label for="16"><s:text name="editlayout" /></label>
						<input name="privilege" type="checkbox" value="18"   class="privilegeID"  /><label for="17"><s:text name="editcontent" /></label>
						<input name="privilege" type="checkbox" value="21"   class="privilegeID"  /><label for="18"><s:text name="taskstrategy" /></label>
					</div> 
					
					<%-- 
					<div class="memberBt"><s:text name="groupmanager" /></div>
	  				<div class="memberNr" >
						<input name="privilege" type="checkbox" value="23"   class="privilegeID"  /><label for="22"><s:text name="newgroup" /></label>
						<input name="privilege" type="checkbox" value="22"   class="privilegeID"  /><label for="21"><s:text name="editgroup" /></label>
						<input name="privilege" type="checkbox" value="24"   class="privilegeID"  /><label for="23"><s:text name="delgroup" /></label>
						<input name="privilege" type="checkbox" value="25"   class="privilegeID"  /><label for="24"><s:text name="groupcarousel" /></label>
						<input name="privilege" type="checkbox" value="26"   class="privilegeID"  /><label for="25"><s:text name="groupdamand" /></label>
						<input name="privilege" type="checkbox" value="27"   class="privilegeID"  /><label for="26"><s:text name="groupspots" /></label>
					</div> 
					<div  class="memberNr">
						<input name="privilege" type="checkbox" value="28"   class="privilegeID"  /><label for="27"><s:text name="groupconfig" /></label>
						<input name="privilege" type="checkbox" value="29"   class="privilegeID"  /><label for="28"><s:text name="groupfirmware" /></label>
						<input name="privilege" type="checkbox" value="30"   class="privilegeID"  /><label for="29"><s:text name="groupproc" /></label>
					</div>
					
					<div class="memberBt"><s:text name="termmanager" /></div>
	  				<div class="memberNr" >
						<input name="privilege" type="checkbox" value="33"   class="privilegeID"  /><label for="32"><s:text name="newterminal" /></label>
						<input name="privilege" type="checkbox" value="32"   class="privilegeID"  /><label for="31"><s:text name="editterminal" /></label>
						<input name="privilege" type="checkbox" value="34"   class="privilegeID"  /><label for="33"><s:text name="delterminal" /></label>
						<input name="privilege" type="checkbox" value="35"   class="privilegeID"  /><label for="34"><s:text name="speccarousel" /></label>
						<input name="privilege" type="checkbox" value="36"   class="privilegeID"  /><label for="35"><s:text name="specdemand" /></label>
						<input name="privilege" type="checkbox" value="37"   class="privilegeID"  /><label for="36"><s:text name="specspots" /></label>
					</div>
					<div class="memberNr" >
						<input name="privilege" type="checkbox" value="38"   class="privilegeID"  /><label for="37"><s:text name="specprog" /></label>
						<input name="privilege" type="checkbox" value="39"   class="privilegeID"  /><label for="38"><s:text name="specfirmware" /></label>
						<input name="privilege" type="checkbox" value=40   class="privilegeID"  /><label for="39"><s:text name="specconfig" /></label>
					</div>
					
					<div class="memberBt"><s:text name="configmanager" /></div>
	  				<div class="memberNr" > 
						<input name="privilege" type="checkbox" value="41"   class="privilegeID"  /><label for="40"><s:text name="newconfig" /></label>
						<input name="privilege" type="checkbox" value="44"   class="privilegeID"  /><label for="43"><s:text name="editconfig" /></label>
						<input name="privilege" type="checkbox" value="42"   class="privilegeID"  /><label for="41"><s:text name="delconfig" /></label>
						<input name="privilege" type="checkbox" value="45"   class="privilegeID"  /><label for="44"><s:text name="updateconfig" /></label>
						<input name="privilege" type="checkbox" value="43"   class="privilegeID"  /><label for="42"><s:text name="auditconfig" /></label>
					</div>
					--%>

					<div class="memberBt"><s:text name="termmanager" /></div>
	  				<div class="memberNr" >
						<input name="privilege" type="checkbox" value="33"   class="privilegeID"  /><label for="32"><s:text name="newterminal" /></label>
						<input name="privilege" type="checkbox" value="32"   class="privilegeID"  /><label for="31"><s:text name="editterminal" /></label>
						<input name="privilege" type="checkbox" value="34"   class="privilegeID"  /><label for="33"><s:text name="delterminal" /></label>
						<input name="privilege" type="checkbox" value="35"   class="privilegeID"  /><label for="34"><s:text name="speccarousel" /></label>
						<input name="privilege" type="checkbox" value="36"   class="privilegeID"  /><label for="35"><s:text name="specdemand" /></label>
						<input name="privilege" type="checkbox" value="37"   class="privilegeID"  /><label for="36"><s:text name="specspots" /></label>
					</div> 
	  				<div class="memberNr" >
						<input name="privilege" type="checkbox" value="23"   class="privilegeID"  /><label for="22"><s:text name="newgroup" /></label>
						<input name="privilege" type="checkbox" value="22"   class="privilegeID"  /><label for="21"><s:text name="editgroup" /></label>
						<input name="privilege" type="checkbox" value="24"   class="privilegeID"  /><label for="23"><s:text name="delgroup" /></label> 
					</div>   
					 
					<div class="memberBt"><s:text name="prompt_monitor" /></div>
	  				<div class="memberNr" > 
						<input name="privilege" type="checkbox" value="41"   class="privilegeID"  /><label for="40"><s:text name="monitor" /></label>
					</div>
					
					<div class="memberBt"><s:text name="safemanage" /></div>
	  				<div class="memberNr" >
						<input name="privilege" type="checkbox" value="47"   class="privilegeID"  /><label for="46"><s:text name="newuser" /></label>
						<input name="privilege" type="checkbox" value="46"   class="privilegeID"  /><label for="45"><s:text name="edituser" /></label>
						<input name="privilege" type="checkbox" value="48"   class="privilegeID"  /><label for="47"><s:text name="deluser" /></label>
						<input name="privilege" type="checkbox" value="49"   class="privilegeID"  /><label for="48"><s:text name="assignroles" /></label>
					</div>
					<div class="memberNr" >
						<input name="privilege" type="checkbox" value="50"   class="privilegeID"  /><label for="49"><s:text name="newrole" /></label>
						<input name="privilege" type="checkbox" value="51"   class="privilegeID"  /><label for="50"><s:text name="editrole" /></label>
						<input name="privilege" type="checkbox" value="52"   class="privilegeID"  /><label for="51"><s:text name="delrole" /></label>
						<input name="privilege" type="checkbox" value="53"   class="privilegeID"  /><label for="52"><s:text name="assignpermission" /></label>
					</div>
				
                </div>
 
              <div class="rightMenu" style="height: 20px">
              	<ul class="left rightMenuLong" >
              	  <s:if test="#session.current_privilege.assignpermission">
              	   <li onclick="javascript:updateRole();"><s:text name="update"/></li>
              	  </s:if>
              	 </ul>
              </div>
              <div style="font-size:12px;padding-left:20px;"><s:text name="bottom_prompt_role_manager"/></div>
              
                
              <!-- 右侧底部menu 开始-->
              <div class="bottomMenu">
                <div class="rightSelect">
                     	<label onclick="selectedAll()"><s:text name="selectall"/></label> | 
                     	<label onclick="deselect()"><s:text name="invertselection"/></label> | 
                     	<label onclick="cancel()"><s:text name="cancel"/></label>
                  </div> 
              </div>
            </div>
            <!-- 右侧 结束-->
            <div class="clearit"></div>
        </div>
        <div class="di1"></div>
    </div>

  <%@include file="/buttom.jsp" %>
</body>
</html>
