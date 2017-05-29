<%@page import="com.gnamp.server.model.Terminal"%>
<%@ page language="java" pageEncoding="utf-8"%>

<div class="configBox">  
    <div class="tcWindow">
		<div class="tcBt">
			<img src="${theme}/skins/default/images/tcBt1.gif" width="5" height="35"
				class="left" />
			<h2> 
				<%
				Terminal terminal = (Terminal)request.getAttribute("terminal");
				if(terminal != null && terminal.getDeviceId() != 0 ) {
					String id = String.format("%012X", terminal.getDeviceId());
					String name = terminal.getDeviceName();
					boolean online = (terminal.getOnlineState() != 0);
					if (online) {
				%>  
					<s:text name="current_terminal" />:&nbsp;<%=name%>&nbsp;(<s:text name="online" />)
				<% } else {%>  
					<s:text name="current_terminal" />:&nbsp;<%=name%>&nbsp;(<s:text name="offline" />)
				<% } 
				} 
				%>
			</h2>
			<img src="${theme}/skins/default/images/tcBt3.gif" class="right" />
		</div>
	    <ul class="configItem" id="ul_item">
	       <li id="li_volume"><a href="setting!device?item=volume&selectIDs=${selectIDs}"><s:text name="item_volume" /></a></li>
	       <li id="li_brightness"><a href="setting!device?item=brightness&selectIDs=${selectIDs}"><s:text name="item_brightness" /></a></li>
	       <li id="li_power"><a href="setting!device?item=power&selectIDs=${selectIDs}"><s:text name="item_power" /></a></li>
	       <li id="li_standby"><a href="setting!device?item=standby&selectIDs=${selectIDs}"><s:text name="item_standby" /></a></li>
	       <li id="li_download"><a href="setting!device?item=download&selectIDs=${selectIDs}"><s:text name="item_download" /></a></li>
	       <li id="li_demand"><a href="setting!device?item=demand&selectIDs=${selectIDs}"><s:text name="item_demand" /></a></li>
	       <li id="li_password"><a href="setting!device?item=password&selectIDs=${selectIDs}"><s:text name="item_password" /></a></li>
	       <li id="li_advanced"><a href="setting!device?item=advanced&selectIDs=${selectIDs}"><s:text name="item_advanced" /></a></li>
	    </ul> 
		<div class="tcNr" style="height:450px;">  
			<div style="position:relative;left:50px;width:600px;padding-top:15px;"> 