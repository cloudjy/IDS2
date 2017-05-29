<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="com.gnamp.server.model.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%
City city = (City)request.getAttribute("city");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css"	href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" />
<link rel="stylesheet" type="text/css" media="all" href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" />
<link rel="stylesheet" type="text/css" media="all" href="${theme}/skins/default/css/niceforms-default.css" />
<script src="${theme}/js/jquery-1.7.2.min.js" language="javascript"></script>
<script language="javascript" type="text/javascript" src="${theme}/js/niceforms.js"></script>
<script type="text/javascript" src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="${theme}/skins/default/css/ipubs-dialog.css" />
<script type="text/javascript" src="${theme}/js/ipub.models-<s:text name="langjs"/>.js"></script>
<script src="js/gnamp.js" language="javascript"></script>

<script type="text/javascript">
function doSubmit() {   	
	var param = $('#form1').serialize();    	
	
	ajaxCallback("weather!edit",param,function(result){
			if(result.success){
				//callParentFunction("refresh");
				//closeIFrame();
			}else{
				/* if(result.message!=null){
					alert("<s:text name="additemfail"/>");	
				} */
				return false;
			}
		}
	);
	
	/* $.ajax({
		type : "post",
		dataType : "json",
		url : "weather!edit",
		data : param,
		complete : function() {
			//compliteCallback();
		},
		success : function(msg) {    				
			if(msg.success){
			  //alert(msg.data);
				//callParentFunction("finished", 'ok');
				//return closeIFrame();	
			}
			else{
				alert(msg.data);
			}
			//callback(msg);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			
			 window.top.location.href='/';
		}
	});
	return false; */
}
function updateCity() {   	
	ajaxCallback("weather!updateCity",
			{"id":"<%=city.getCityId()%>"},
			function(msg){
			if(msg.success){
				$.each(msg.data,function(i,o){
					if(i==0){
						$("input[name='wtoday.weatherText']").val(o.weatherText);
						$("input[name='wtoday.low']").val(o.low);
						$("input[name='wtoday.high']").val(o.high);
						$("select[name='wtoday.weatherType']").val(o.weatherType);
					}else if(i==1){
						$("input[name='wtomorrow.weatherText']").val(o.weatherText);
						$("input[name='wtomorrow.low']").val(o.low);
						$("input[name='wtomorrow.high']").val(o.high);
						$("select[name='wtomorrow.weatherType']").val(o.weatherType);
					}else if(i==2){
						$("input[name='wafter.weatherText']").val(o.weatherText);
						$("input[name='wafter.low']").val(o.low);
						$("input[name='wafter.high']").val(o.high);
						$("select[name='wafter.weatherType']").val(o.weatherType);
				}
				});
			}else{
				
				return false;
			}
		}
	);
}
</script>

</head>
<body>

	<!-- 弹出窗口 开始-->
	<div class="tcWindow">
		<div class="tcBt">
			<img src="${theme}/skins/default/images/tcBt1.gif" width="5" height="35"
				class="left" />
			<h2><% if(city != null) {%><%=city.getProvince() %>：<%=city.getCityName() %><%} %></h2>
			<img src="${theme}/skins/default/images/tcBt3.gif" class="right" />
		</div>
		<div class="tcNr">
	<form id="form1" class="niceform">	
	      <input type="hidden" value="${city.cityId }" name="city.cityId" id="city.cityId" />	
		  <input type="hidden" value="${city.cityName }" name="city.cityName" id="city.cityName" />			
			<table width="100%" border="0" cellspacing="2" cellpadding="0"
					class="bdTab">
				<tr>
					<td></td>
					<td><s:text name="text_typeweather"/></td>
					<td><s:text name="text_icontype"/></td>
					<td><s:text name="text_lowesttemperation"/></td>
					<td><s:text name="text_maxtempration"/></td>
				</tr>
				<tr>
					<td ><s:text name="text_today"/></td>
					<td >
                     <input id="wtoday.weatherText" name="wtoday.weatherText" value="<%= request.getAttribute("wtoday") == null ? "" : ((Weather)request.getAttribute("wtoday")).getWeatherText() %>" type="text" />
                    </td>
					<td >
					<%
					Weather w = null;
					w = (Weather)request.getAttribute("wtoday");
					int type = 0;
					if(w != null){
						type = w.getWeatherType(); 
					}
					%>
					<select id="wtoday.weatherType" name="wtoday.weatherType" size="1">
					<%-- <s:text name="sunny"/> --%>
					    <option <%if(type==0){ %> selected <%} %> value="0"><s:text name="png0"/></option>
						<option <%if(type==1){ %> selected <%} %> value="1"><s:text name="png1"/></option>
						<option <%if(type==2){ %> selected <%} %> value="2"><s:text name="png2"/></option>
						<option <%if(type==3){ %> selected <%} %> value="3"><s:text name="png3"/></option>
						<option <%if(type==4){ %> selected <%} %> value="4"><s:text name="png4"/></option>
						<option <%if(type==5){ %> selected <%} %> value="5"><s:text name="png5"/></option>
						<option <%if(type==6){ %> selected <%} %> value="6"><s:text name="png6"/></option>
						<option <%if(type==7){ %> selected <%} %> value="7"><s:text name="png7"/></option>
						<option <%if(type==8){ %> selected <%} %> value="8"><s:text name="png8"/></option>
						<option <%if(type==9){ %> selected <%} %> value="9"><s:text name="png9"/></option>
						<option <%if(type==10){ %> selected <%} %> value="10"><s:text name="png10"/></option>
						<option <%if(type==11){ %> selected <%} %> value="11"><s:text name="png11"/></option>
						<option <%if(type==12){ %> selected <%} %> value="12"><s:text name="png12"/></option>
						<option <%if(type==13){ %> selected <%} %> value="13"><s:text name="png13"/></option>
						<option <%if(type==14){ %> selected <%} %> value="14"><s:text name="png14"/></option>
						<option <%if(type==15){ %> selected <%} %> value="15"><s:text name="png15"/></option>
						<option <%if(type==16){ %> selected <%} %> value="16"><s:text name="png16"/></option>
						<option <%if(type==17){ %> selected <%} %> value="17"><s:text name="png17"/></option>
						 <option <%if(type==18){ %> selected <%} %> value="18"><s:text name="png18"/></option>
						<option <%if(type==19){ %> selected <%} %> value="19"><s:text name="png19"/></option>
						<option <%if(type==20){ %> selected <%} %> value="20"><s:text name="png20"/></option>
						<option <%if(type==21){ %> selected <%} %> value="21"><s:text name="png21"/></option>
						<option <%if(type==22){ %> selected <%} %> value="22"><s:text name="png22"/></option>
						<option <%if(type==23){ %> selected <%} %> value="23"><s:text name="png23"/></option>
						<option <%if(type==24){ %> selected <%} %> value="24"><s:text name="png24"/></option>
						<option <%if(type==25){ %> selected <%} %> value="25"><s:text name="png25"/></option>
						<option <%if(type==26){ %> selected <%} %> value="26"><s:text name="png26"/></option>
						<option <%if(type==27){ %> selected <%} %> value="27"><s:text name="png27"/></option>
						<option <%if(type==28){ %> selected <%} %> value="28"><s:text name="png28"/></option>
						<option <%if(type==29){ %> selected <%} %> value="29"><s:text name="png29"/></option>
						<option <%if(type==30){ %> selected <%} %> value="30"><s:text name="png30"/></option>
						<option <%if(type==31){ %> selected <%} %> value="31"><s:text name="png31"/></option>
						<option <%if(type==32){ %> selected <%} %> value="32"><s:text name="png32"/></option>
						<option <%if(type==33){ %> selected <%} %> value="33"><s:text name="png33"/></option>
						<option <%if(type==34){ %> selected <%} %> value="34"><s:text name="png34"/></option>
						<option <%if(type==35){ %> selected <%} %> value="35"><s:text name="png35"/></option>
						<option <%if(type==36){ %> selected <%} %> value="36"><s:text name="png36"/></option>
						<option <%if(type==37){ %> selected <%} %> value="37"><s:text name="png37"/></option>
						<option <%if(type==38){ %> selected <%} %> value="38"><s:text name="png38"/></option>
						<option <%if(type==39){ %> selected <%} %> value="39"><s:text name="png39"/></option>
						<option <%if(type==40){ %> selected <%} %> value="40"><s:text name="png40"/></option>
						<option <%if(type==41){ %> selected <%} %> value="41"><s:text name="png41"/></option>
						<option <%if(type==42){ %> selected <%} %> value="42"><s:text name="png42"/></option>
						<option <%if(type==43){ %> selected <%} %> value="43"><s:text name="png43"/></option>
						<option <%if(type==44){ %> selected <%} %> value="44"><s:text name="png44"/></option>
						<option <%if(type==45){ %> selected <%} %> value="45"><s:text name="png45"/></option>
						<option <%if(type==46){ %> selected <%} %> value="46"><s:text name="png46"/></option>
						<option <%if(type==47){ %> selected <%} %> value="47"><s:text name="png47"/></option>
					</select>
					</td>
					<td >
					<input id="wtoday.low" name="wtoday.low" value="<%= request.getAttribute("wtoday") == null ? "" : ((Weather)request.getAttribute("wtoday")).getLow() %>" type="text" />
					</td>
					<td >
					<input id="wtoday.high" name="wtoday.high" value="<%= request.getAttribute("wtoday") == null ? "" : ((Weather)request.getAttribute("wtoday")).getHigh() %>" type="text" />
					</td>
				</tr>
				
				<tr>
					<%
					w = (Weather)request.getAttribute("wtomorrow");
					type = 0;
					if(w != null){
						type = w.getWeatherType(); 
					}
					%>
					<td ><s:text name="text_tomorrow"/></td>
					<td >
                     <input id="wtomorrow.weatherText" name="wtomorrow.weatherText" value="<%= request.getAttribute("wtomorrow") == null ? "" : ((Weather)request.getAttribute("wtomorrow")).getWeatherText() %>" type="text" />
                    </td>
					<td >
					
					<select id="wtomorrow.weatherType" name="wtomorrow.weatherType" size="1">
					  		    <option <%if(type==0){ %> selected <%} %> value="0"><s:text name="png0"/></option>
						<option <%if(type==1){ %> selected <%} %> value="1"><s:text name="png1"/></option>
						<option <%if(type==2){ %> selected <%} %> value="2"><s:text name="png2"/></option>
						<option <%if(type==3){ %> selected <%} %> value="3"><s:text name="png3"/></option>
						<option <%if(type==4){ %> selected <%} %> value="4"><s:text name="png4"/></option>
						<option <%if(type==5){ %> selected <%} %> value="5"><s:text name="png5"/></option>
						<option <%if(type==6){ %> selected <%} %> value="6"><s:text name="png6"/></option>
						<option <%if(type==7){ %> selected <%} %> value="7"><s:text name="png7"/></option>
						<option <%if(type==8){ %> selected <%} %> value="8"><s:text name="png8"/></option>
						<option <%if(type==9){ %> selected <%} %> value="9"><s:text name="png9"/></option>
						<option <%if(type==10){ %> selected <%} %> value="10"><s:text name="png10"/></option>
						<option <%if(type==11){ %> selected <%} %> value="11"><s:text name="png11"/></option>
						<option <%if(type==12){ %> selected <%} %> value="12"><s:text name="png12"/></option>
						<option <%if(type==13){ %> selected <%} %> value="13"><s:text name="png13"/></option>
						<option <%if(type==14){ %> selected <%} %> value="14"><s:text name="png14"/></option>
						<option <%if(type==15){ %> selected <%} %> value="15"><s:text name="png15"/></option>
						<option <%if(type==16){ %> selected <%} %> value="16"><s:text name="png16"/></option>
						<option <%if(type==17){ %> selected <%} %> value="17"><s:text name="png17"/></option>
						 <option <%if(type==18){ %> selected <%} %> value="18"><s:text name="png18"/></option>
						<option <%if(type==19){ %> selected <%} %> value="19"><s:text name="png19"/></option>
						<option <%if(type==20){ %> selected <%} %> value="20"><s:text name="png20"/></option>
						<option <%if(type==21){ %> selected <%} %> value="21"><s:text name="png21"/></option>
						<option <%if(type==22){ %> selected <%} %> value="22"><s:text name="png22"/></option>
						<option <%if(type==23){ %> selected <%} %> value="23"><s:text name="png23"/></option>
						<option <%if(type==24){ %> selected <%} %> value="24"><s:text name="png24"/></option>
						<option <%if(type==25){ %> selected <%} %> value="25"><s:text name="png25"/></option>
						<option <%if(type==26){ %> selected <%} %> value="26"><s:text name="png26"/></option>
						<option <%if(type==27){ %> selected <%} %> value="27"><s:text name="png27"/></option>
						<option <%if(type==28){ %> selected <%} %> value="28"><s:text name="png28"/></option>
						<option <%if(type==29){ %> selected <%} %> value="29"><s:text name="png29"/></option>
						<option <%if(type==30){ %> selected <%} %> value="30"><s:text name="png30"/></option>
						<option <%if(type==31){ %> selected <%} %> value="31"><s:text name="png31"/></option>
						<option <%if(type==32){ %> selected <%} %> value="32"><s:text name="png32"/></option>
						<option <%if(type==33){ %> selected <%} %> value="33"><s:text name="png33"/></option>
						<option <%if(type==34){ %> selected <%} %> value="34"><s:text name="png34"/></option>
						<option <%if(type==35){ %> selected <%} %> value="35"><s:text name="png35"/></option>
						 <option <%if(type==36){ %> selected <%} %> value="36"><s:text name="png36"/></option>
						<option <%if(type==37){ %> selected <%} %> value="37"><s:text name="png37"/></option>
						<option <%if(type==38){ %> selected <%} %> value="38"><s:text name="png38"/></option>
						<option <%if(type==39){ %> selected <%} %> value="39"><s:text name="png39"/></option>
						<option <%if(type==40){ %> selected <%} %> value="40"><s:text name="png40"/></option>
						<option <%if(type==41){ %> selected <%} %> value="41"><s:text name="png41"/></option>
						<option <%if(type==42){ %> selected <%} %> value="42"><s:text name="png42"/></option>
						<option <%if(type==43){ %> selected <%} %> value="43"><s:text name="png43"/></option>
						<option <%if(type==44){ %> selected <%} %> value="44"><s:text name="png44"/></option>
						<option <%if(type==45){ %> selected <%} %> value="45"><s:text name="png45"/></option>
						<option <%if(type==46){ %> selected <%} %> value="46"><s:text name="png46"/></option>
						<option <%if(type==47){ %> selected <%} %> value="47"><s:text name="png47"/></option>
					</select>
					</td>
					<td >
					<input id="wtomorrow.low" name="wtomorrow.low" value="<%= request.getAttribute("wtomorrow") == null ? "" : ((Weather)request.getAttribute("wtomorrow")).getLow() %>" type="text" />
					</td>
					<td >
					<input id="wtomorrow.high" name="wtomorrow.high" value="<%= request.getAttribute("wtomorrow") == null ? "" : ((Weather)request.getAttribute("wtomorrow")).getHigh() %>" type="text" />
					</td>
				</tr>
				
			 	<tr>
					<td ><s:text name="text_dayaftertomorrow"/></td>
					<td >
                      <input id="wafter.weatherText" name="wafter.weatherText" value="<%= request.getAttribute("wafter") == null ? "" : ((Weather)request.getAttribute("wafter")).getWeatherText() %>" type="text" />
                    </td>
					<td >
					<%
					w = (Weather)request.getAttribute("wafter");
					type = 0;
					if(w != null){
						type = w.getWeatherType(); 
					}
					%>
					<select id="wafter.weatherType" name="wafter.weatherType" size="1">
					     		    <option <%if(type==0){ %> selected <%} %> value="0"><s:text name="png0"/></option>
						<option <%if(type==1){ %> selected <%} %> value="1"><s:text name="png1"/></option>
						<option <%if(type==2){ %> selected <%} %> value="2"><s:text name="png2"/></option>
						<option <%if(type==3){ %> selected <%} %> value="3"><s:text name="png3"/></option>
						<option <%if(type==4){ %> selected <%} %> value="4"><s:text name="png4"/></option>
						<option <%if(type==5){ %> selected <%} %> value="5"><s:text name="png5"/></option>
						<option <%if(type==6){ %> selected <%} %> value="6"><s:text name="png6"/></option>
						<option <%if(type==7){ %> selected <%} %> value="7"><s:text name="png7"/></option>
						<option <%if(type==8){ %> selected <%} %> value="8"><s:text name="png8"/></option>
						<option <%if(type==9){ %> selected <%} %> value="9"><s:text name="png9"/></option>
						<option <%if(type==10){ %> selected <%} %> value="10"><s:text name="png10"/></option>
						<option <%if(type==11){ %> selected <%} %> value="11"><s:text name="png11"/></option>
						<option <%if(type==12){ %> selected <%} %> value="12"><s:text name="png12"/></option>
						<option <%if(type==13){ %> selected <%} %> value="13"><s:text name="png13"/></option>
						<option <%if(type==14){ %> selected <%} %> value="14"><s:text name="png14"/></option>
						<option <%if(type==15){ %> selected <%} %> value="15"><s:text name="png15"/></option>
						<option <%if(type==16){ %> selected <%} %> value="16"><s:text name="png16"/></option>
						<option <%if(type==17){ %> selected <%} %> value="17"><s:text name="png17"/></option>
						 <option <%if(type==18){ %> selected <%} %> value="18"><s:text name="png18"/></option>
						<option <%if(type==19){ %> selected <%} %> value="19"><s:text name="png19"/></option>
						<option <%if(type==20){ %> selected <%} %> value="20"><s:text name="png20"/></option>
						<option <%if(type==21){ %> selected <%} %> value="21"><s:text name="png21"/></option>
						<option <%if(type==22){ %> selected <%} %> value="22"><s:text name="png22"/></option>
						<option <%if(type==23){ %> selected <%} %> value="23"><s:text name="png23"/></option>
						<option <%if(type==24){ %> selected <%} %> value="24"><s:text name="png24"/></option>
						<option <%if(type==25){ %> selected <%} %> value="25"><s:text name="png25"/></option>
						<option <%if(type==26){ %> selected <%} %> value="26"><s:text name="png26"/></option>
						<option <%if(type==27){ %> selected <%} %> value="27"><s:text name="png27"/></option>
						<option <%if(type==28){ %> selected <%} %> value="28"><s:text name="png28"/></option>
						<option <%if(type==29){ %> selected <%} %> value="29"><s:text name="png29"/></option>
						<option <%if(type==30){ %> selected <%} %> value="30"><s:text name="png30"/></option>
						<option <%if(type==31){ %> selected <%} %> value="31"><s:text name="png31"/></option>
						<option <%if(type==32){ %> selected <%} %> value="32"><s:text name="png32"/></option>
						<option <%if(type==33){ %> selected <%} %> value="33"><s:text name="png33"/></option>
						<option <%if(type==34){ %> selected <%} %> value="34"><s:text name="png34"/></option>
						<option <%if(type==35){ %> selected <%} %> value="35"><s:text name="png35"/></option>
						 <option <%if(type==36){ %> selected <%} %> value="36"><s:text name="png36"/></option>
						<option <%if(type==37){ %> selected <%} %> value="37"><s:text name="png37"/></option>
						<option <%if(type==38){ %> selected <%} %> value="38"><s:text name="png38"/></option>
						<option <%if(type==39){ %> selected <%} %> value="39"><s:text name="png39"/></option>
						<option <%if(type==40){ %> selected <%} %> value="40"><s:text name="png40"/></option>
						<option <%if(type==41){ %> selected <%} %> value="41"><s:text name="png41"/></option>
						<option <%if(type==42){ %> selected <%} %> value="42"><s:text name="png42"/></option>
						<option <%if(type==43){ %> selected <%} %> value="43"><s:text name="png43"/></option>
						<option <%if(type==44){ %> selected <%} %> value="44"><s:text name="png44"/></option>
						<option <%if(type==45){ %> selected <%} %> value="45"><s:text name="png45"/></option>
						<option <%if(type==46){ %> selected <%} %> value="46"><s:text name="png46"/></option>
						<option <%if(type==47){ %> selected <%} %> value="47"><s:text name="png47"/></option>
					</select>
					</td>
					<td >
					<input id="wafter.low" name="wafter.low" value="<%= request.getAttribute("wafter") == null ? "" : ((Weather)request.getAttribute("wafter")).getLow() %>" type="text" />
					</td>
					<td >
					<input id="wafter.high" name="wafter.high" value="<%= request.getAttribute("wafter") == null ? "" : ((Weather)request.getAttribute("wafter")).getHigh() %>" type="text" />
					</td>
				</tr> 			
				<tr style="background-color: #ffffff;">
					<td></td>
					<td></td>
					<td ><input type="button" onclick="doSubmit();" id="sub"
			 value="<s:text name="yes"/>"/>&nbsp;&nbsp;&nbsp;&nbsp;
			 <input type="button" id="close" onclick="closeIFrame();" 
			 value="<s:text name="cancel"/>"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id="close" onclick="updateCity();" 
			 value="<s:text name="update"/>"/>
					</td>
					<td></td>
					<td></td>
				</tr>
			</table>
	</form>
		</div>
		<div class="tcDi">
			<img src="${theme}/skins/default/images/tcDi1.gif" class="left" /><img
				src="${theme}/skins/default/images/tcDi2.gif" class="right" />
		</div>
	</div>
	<!-- 弹出窗口 结束-->
	
	<script type="text/javascript">	
		$("input").keydown(function(event){
	   		switch(event.keyCode) {
				case 13:
					doSubmit();
					return false;
					break;
		   	}
	   	});
	</script>

</body>
</html>

