<%@page import="com.gnamp.server.model.Terminal"%>
<%@page import="com.gnamp.terminal.config.DeviceSetting"%>  
<%@page import="com.gnamp.terminal.config.EthernetConfig"%>  
<%@page import="com.gnamp.terminal.config.WifiConfig"%>  
<%@page import="com.gnamp.terminal.config.Phone3GConfig"%>  
<%@page import="com.gnamp.terminal.config.PPPOEConfig"%>  
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
				&nbsp;
				<% 
				DeviceSetting curSetting = (DeviceSetting)request.getAttribute("currentSetting"); 
				if (curSetting != null && curSetting.mNetwork != null ) { 
					if (curSetting.mNetwork instanceof EthernetConfig) { %>  
						<s:text name="item_ethernet" />  
					<%} else if (curSetting.mNetwork instanceof WifiConfig) { %>  
						<s:text name="item_wifi" />
					<%} else if (curSetting.mNetwork instanceof Phone3GConfig) { %>  
						<s:text name="item_3g" />
					<%} else if (curSetting.mNetwork instanceof PPPOEConfig) { %>  
						<s:text name="item_pppoe" />
					<%}
				} 
				%>
			</h2>
			<img src="${theme}/skins/default/images/tcBt3.gif" class="right" />
		</div>
	    <ul class="configItem" id="ul_item">
	       <li id="li_ethernet"><a href="setting!network?item=ethernet&selectIDs=${selectIDs}"><s:text name="item_ethernet" /></a></li>
	       <li id="li_wifi"><a href="setting!network?item=wifi&selectIDs=${selectIDs}"><s:text name="item_wifi" /></a></li>
	       <li id="li_3g"><a href="setting!network?item=3g&selectIDs=${selectIDs}"><s:text name="item_3g" /></a></li>
	       <li id="li_pppoe"><a href="setting!network?item=pppoe&selectIDs=${selectIDs}"><s:text name="item_pppoe" /></a></li>
	       <li id="li_server"><a href="setting!network?item=server&selectIDs=${selectIDs}"><s:text name="item_server" /></a></li> 
	       <li id="li_ap"><a href="setting!network?item=ap&selectIDs=${selectIDs}"><s:text name="item_ap" /></a></li> 
	    </ul> 
		<div class="tcNr" style="height:490px;"> 
			<div style="position:relative;left:40px;width:600px;padding-top:15px;">