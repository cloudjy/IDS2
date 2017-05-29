<%@page import="com.gnamp.server.model.*"%>
<%@page import="com.gnamp.server.handle.*"%>


<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">


<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=8" />
<%@include file="/template/Public/title.jsp" %>

<style>
 html { overflow: scroll; } 
</style>

<link rel="stylesheet" type="text/css" 
	href="${theme}/js/jquery-ui-1.8.21.custom/css/vader/jquery-ui-1.8.21.custom.css" />
<link href="${theme}/skins/default/css/css-<s:text name="langjs"/>.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet"
	href="${theme}/skins/default/css/jqtransform.css" media="all" />
    
<script type="text/javascript" src="${theme}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${theme}/js/jquery.jqtransform.js"></script>
<script type="text/javascript" src="${theme}/js/jscroll.js"></script> 
<script type="text/javascript" src="${theme}/js/jquery-ui-1.8.21.custom/js/jquery-ui-1.8.21.custom.min.js"></script>
<script type="text/javascript" src="${theme}/js/ipub.models-<s:text name="langjs"/>.js"></script>
<link href="${theme}/skins/default/css/ipubs-dialog.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">   
    $(function() {
    	$("#bofang").addClass("hover");
    });
        /*第一种形式 第二种形式 更换显示样式*/
        function setTab(name, cursel, n) {
        	if(cursel==1){
        		window.location.href="looptask";
        	}
        	if(cursel==2){
        		window.location.href="demandtask";
        	}
        	if(cursel==3){
        		window.location.href="plugintask";
        	}          
        }
        $(function () {
            $('form').jqTransform({ imgPath: '${theme}/skins/default/images/' });
        });
        </script>



<script src="js/JScript.js"></script> 
<script src="js/gnamp.js"></script>

<script type="text/javascript" src="js/duma.js"></script>
<script type="text/javascript">
	$(window).load(function() {
		try {
			QueryLoopTask();
		} catch (err) {
		}
	});
	
	function alertMessage(message){
		alert(message);
		// $("#messageBox").text(message);
		// $("#messageBox").show();
	}

	function addtask() {	
			showIPUBDialogStand("looptask!ToAddLoopTask",{title:"<s:text name="text_task_createtask"/>",width:495,height:220});		
	}
	function addtasked(result){
		if(result=='ok')
			QueryLoopTask();
	}
	var remember=null;
	function loop(msg) {
	   remember = new Array();
		
		var arr = $("#looptaskdiv input[type='checkbox']");  //document.getElementsByTagName('input');
		var ok = 0;
		var selectID = 0;
		var selectIDs = "";
		var tname ="";
		for ( var i = 0; i < arr.length; i++) {
			var abc = arr[i];
			if (abc.type == "checkbox") {
				if (abc.checked == true) {
					ok++;					
				    selectID = abc.value;				  
					selectIDs += selectID.toString() + ",";

					if ($(abc.parentNode).hasClass("hover")) {
						remember.unshift(abc.value);
					} else {
						remember.push(abc.value);
					}
				}
			}
		}
		
		if (ok > 0) {
			if (msg == 'delete') {
				
				confirm("<s:text name="is_delete_task" />",function(){
					var param = {
							"selectIDs" : selectIDs.substring(0, selectIDs.length - 1)
						     };
					
					ajaxCallback("looptask!delete", param, function(msg){
						if (msg.success) { 
							rememberloopprogram = null;
							$("#shujTb").html("");
							QueryLoopTask();
						} else {
							alertMessage(msg.data);
							QueryLoopTask();
						}
					});
					
						
				});
			}
			
			if (msg =="<s:text name="isaudit" />") {
				var id_params = "";
				var all_names = "";
				var arr = $("#looptask input[type='checkbox']");  
				var oo=0;
				for ( var i = 0; i < arr.length; i++) {
					var abc = arr[i];
					if (abc.type == "checkbox") {
						if (abc.checked == true) {								
						    if(abc.name=="0"){
						    	oo++;
						    } else {
						    	id_params = id_params + (id_params != ""?  "," : "" ) + abc.value;
						    	all_names = all_names + (all_names != ""?  "," : "") + abc.getAttribute("taskName");
						    } 
						}
					}
				}				
				
				if (id_params != "") { // oo<=0
					var confirm_text = "<s:text name="isaudit_task"/>"; // + " " + all_names + "?";
					confirm(confirm_text,function(){
					var param = { "selectIDs" : id_params };
					
					ajaxCallback("looptask!check", param, function(msg){
						if (msg.success) {
							az = null;
							QueryLoopTask(remember);
						} else {
							alertMessage(msg.data);
						}
					});					
						
				});
			 }
			}
			
			if (msg == "edit") { 
				/*var task_names = "";
				var chboxes = $("#looptask input[type='checkbox']");  
				for ( var i = 0; i < chboxes.length; i++) {  
					if (chboxes[i].checked == true ) {	 
					    task_names += ((task_names != ""?  "," : "") + chboxes[i].getAttribute("taskName"));
					} 
				}*/		
				showIPUBDialogStand("looptask!ToModifyLoopTask?looptask.taskId="
						+ selectID,{title:"<s:text name="text_edittask"/>",width:514,height:346});		

			}
			
			if (msg == "clone") {
				
				showIPUBDialogStand("looptask!ToCloneLoopTask?looptask.taskId="
						+ selectID,{title:"<s:text name="clone_company"/>",width:494,height:220});		

			}
			if (msg == "celue") {	
				showIPUBDialogStand("looptask!celue?looptask.taskId="
						+ selectID,{title: "<s:text name="datesequence"/>",width:1024,height:630,beforeClose:function(){
							remember = new Array();
							remember.push(taskid);
							QueryLoopTask(remember);
						}});				
			}
			if (msg == "jiaoyan") {	
				var param = {
						"looptask.taskId" : selectID
					     };
				
				ajaxCallback("looptask!jiaoyan", param, function(msg){
					if (msg.success) {
						alertMessage(msg.data);
						QueryLoopTask(remember);
					} else {
						alertMessage(msg.data);
					}
				}); 
			}			

			if (msg == "size") { 
				var url = "looptask!ToModifyLoopTask?looptask.taskId=" + selectID + "&onlyTaskSize=true";
				showIPUBDialogStand(url, {title:"<s:text name="title_task_info"/>",width:514,height:346});
			} 	
		} else {
			alertMessage("<s:text name="noselecttask" />");
			return false;
		}		
		
	} 

	function finished(result){
		
		if(result=='ok'){
			QueryLoopTask(remember);
		}
	}
	
	function sch(tid){
		showIPUBDialogStand("looptask!celue?looptask.taskId="
				+ tid,{title:"<s:text name="datesequence"/>",width:1024,height:630,beforeClose:function(){
					remember = new Array();
					remember.push(taskid);
					QueryLoopTask(remember);
				}});	
	}
	
	var az = null;
	function tasksort(){
		az = !az;
		$("#task_sort").html(az? "A-Z" : "Z-A");
		QueryLoopTask(remember);
	}
	
	function QueryLoopTask(remember) { 
		var param = ((az == null || az == undefined)? 
			{"id" : 0} : {"az" : az? true : false, "isSort": true, "id" : 0});
		
		ajaxCallback("looptask!looplist", param, function(msg){
			if (msg.success) {
				try {
					var content = "";
					var cc = 0;
					if(msg.data != null){
					$.each(
							msg.data,
							function(i, task) {	
								var checked = false;
								if (remember) {
									for ( var i = 0; i < remember.length; i++) {
										if (remember[i] == task.taskId) {
											checked = true;	
											break;
										}
									}
								}
								
						 		 content += "<li>"
								        +"<div id=\"looptaskdiv\" class=\"fenye2left-wapper\"> "  	
								        +"<input value=\"" + task.taskId + "\" "   
								        + 	" name=\"" + task.state + "\" "+ (checked ? " checked ":" ") 
								        + 	" id=\"" + task.taskId + "\" "   
								        + 	" taskId=\"" + task.taskId + "\" "  
								        + 	" taskName=\"" + task.taskName.replace(/(\'|\"|\\)/g, "\\" + "$1") + "\" " 
								        + 	" class=\"checkbox\"  type=\"checkbox\" />"
								    	+"</div>"					        	
								    	+"<span class=\"fenye2left\" "
								    	+" " 
										+ " name=\""+task.taskId+"\" " +" id=\""+task.taskId+"a\" onclick=\"showprogram("+task.taskId+", this, "+task.state+")\" title=\""
										+ task.taskName +"<s:text name="fenhao" /><s:text name="versiontip" />"+task.taskVersion+"<s:text name="fenhao" /><s:text name="statetip" />"+(task.state == 0 ? "<s:text name="task_status_checked" />" : "<s:text name="task_status_unchecked" />")
										+ "\" >"		
								    	+"<img style=\"margin-right:5px;\"  "	+ (task.state == 0 ? " src=\"${theme}/skins/default/images/b15.png\"" : " src=\"${theme}/skins/default/images/b16.png\"")
										+ "/>  "	
										+(task.state==0?(task.taskName.length>20?task.taskName.substring(0,20):task.taskName):("<font style=\"color:red\">"+(task.taskName.length>20?task.taskName.substring(0,20):task.taskName)+"</font>"))
										+ " "											
										+"</span>"
										+"</li>";  
										
										cc++;
							});
					}
					$("#looptask").html(content);
                     
					
					$("#one1").attr("title","<s:text name="total"/>"+cc+"<s:text name="number"/>");
					
		            $('form').jqTransform({ imgPath: '${theme}/skins/default/images/' });
				} catch (e) {
					alertMessage(e.message);
				}
			} else {
				alertMessage(msg.success);
			}	 

			var focus = null;
			if (remember && remember.length > 0) {
				focus = $("#looptask>li>span[onclick][name=\'" + remember[0]+"\'][id=\'" + remember[0]+"a\']");
			}  
			
			if(!focus || focus.length <= 0){
				focus = $("#looptask>li:first>span:first");
			}
			
			if(focus && focus.length > 0){  
				focus.click();
			}
		});
		return false;
	}
	
	var taskid=0; 
	
	function showprogram(tid, node, state)
	{	
		$("#looptask > li").removeClass("hover");
		$(node.parentNode).addClass("hover");
		
		$("#audit").css('color', state != 0? '#FC0F0F' : '#F0F0F0'); 

		if (taskid != tid)
			rememberloopprogram =null;
		taskid = tid;
				
		QueryLoopProgram(rememberloopprogram);
	}
	
	function padded(result){ 
		remember = new Array();
		remember.push(taskid);
		QueryLoopTask(remember);
	}
	
	function pedited(result){
		if(result=='ok'){ 
			remember = new Array();
			remember.push(taskid);
			QueryLoopTask(remember);
		}
	}
	

	function pcloneed(result){
		if(result=='ok') { 
			remember = new Array();
			remember.push(taskid);
			QueryLoopTask(remember);
		}
	}
	
	function addprogram() {			
		if(taskid==0 || taskid==null || taskid==undefined){
			alertMessage('<s:text name="noselecttask"/>');
			return;
		}
			showIPUBDialogStand("loopprogram!ToAdd?loopprogram.taskId="+taskid,{title:"<s:text name="newprogram"/>",width:539,height:300});		
		
	}
	
	var rememberloopprogram = null;
	function loopprogram(msg) {
		rememberloopprogram = new Array();
		
		var arr = $("input[class='loopprogramId']");  //document.getElementsByTagName('input');
		var ok = 0;
		var selectID = 0;
		var selectIDs = "";
		for ( var i = 0; i < arr.length; i++) {
			var abc = arr[i];
			if (abc.type == "checkbox") {
				if (abc.checked == true) {
					ok++;					
				    selectID = abc.value;
					selectIDs += selectID.toString() + ",";
					
					rememberloopprogram.push(abc.value);
				}
			}
		}
		
		if (ok > 0) {
			if (msg == "delete"  ) {
				confirm("<s:text name="is_delete_program" />" ,function(){
					var param = {
							"selectIDs" : selectIDs.substring(0, selectIDs.length - 1),						
							"loopprogram.taskId" : taskid
						     };
					
					ajaxCallback("loopprogram!delete", param, function(msg){
						if (msg.success) {  
							remember = new Array();
							remember.push(taskid);
							QueryLoopTask(remember);
						} else {
							alertMessage(msg.data);
						}
					});
					
					
				});
			}
			if (msg == "edit") {	
				showIPUBDialogStand("loopprogram!ToModify?loopprogram.taskId="
						+ taskid +"&loopprogram.programId="+selectID,{title:"<s:text name="editprogram"/>",width:565,height:319});		
				

			}
			
			if (msg == "clone") {					
				showIPUBDialogStand("loopprogram!ToClone?loopprogram.taskId="
						+ taskid +"&loopprogram.programId="+selectID,{title:"<s:text name="clone_program"/>",width:491,height:312});		

			}

			if (msg == "up") {
				var param = {
						"loopprogram.programId" : selectID,						
						"loopprogram.taskId" : taskid
					     };
				
				ajaxCallback("loopprogram!up", param, function(msg){
					if (msg.success) { 
						remember = new Array();
						remember.push(taskid);
						QueryLoopTask(remember);
					} else {
						alertMessage(msg.data);
					}
				});
			}

			if (msg == "down") {
				var param = {
						"loopprogram.programId" : selectID,						
						"loopprogram.taskId" : taskid
					     };
				
				ajaxCallback("loopprogram!down", param, function(msg){
					if (msg.success) {
						remember = new Array();
						remember.push(taskid);
						QueryLoopTask(remember);
					} else {
						alertMessage(msg.data);
					}
				});
			}
		  if(msg == "clear_schedule"){
				confirm("<s:text name="isclear_schedule" />",function(){
					var param = {
							"selectIDs" : selectIDs.substring(0, selectIDs.length - 1),						
							"loopprogram.taskId" : taskid
						     };
					
					ajaxCallback("loopprogram!clear", param, function(msg){
						if (msg.success) {
							remember = new Array();
							remember.push(taskid);
							QueryLoopTask(remember);
						} else {
							alertMessage(msg.data);
						}
					});
					
					
				});
		  }
			
		} else {
			alertMessage("<s:text name="noselectprogram" />");
			return false;
		}		
		
	}
	
	function changestate(tid, pid, state){
		state = (state==1? 0 : 1);
		var param = {
				"loopprogram.programId" : pid,						
				"loopprogram.taskId" : tid,						
				"loopprogram.state" : state
			     };
		$.ajax({
			type : "post",
			dataType : "json",
			url : "loopprogram!changestate",
			data : param,
			complete : function() {
			},
			success : function(msg) {
				if (msg.success) { 
					remember = new Array();
					remember.push(tid);
					rememberloopprogram = new Array();
					rememberloopprogram = new Array(); 
					var program_chkbox = $("input[class='loopprogramId']");  
					for ( var i = 0; i < program_chkbox.length; i++) { 
						if (program_chkbox[i].type == "checkbox" && program_chkbox[i].checked == true) { 
							rememberloopprogram.push(program_chkbox[i].value); 
						}
					}
					QueryLoopTask(remember);
				} else {
					alertMessage(msg.data);
				}
			},
			error : function(XMLHttpRequest, textStatus,
					errorThrown) {
				window.top.location.href='/';
			}
		});

	}
	
	 var childWin;
     function view(TaskID, ProgramID, W, H) {         
         showIPUBDialogStand('looprect!showprogram?TaskID=' + TaskID + '&ProgramID=' + ProgramID + 
    		'&RequestW=' + W + '&RequestH=' + H, 
    		{title:"<s:text name="programpreview"/>",width:1028,height:638,
    			beforeclose:function(){$("div.ipub-dialog-pannel > iframe").attr("src", "");}});			

     }

	function QueryLoopProgram(rememberloopprogram) {
		var param = {
			"loopprogram.taskId" : taskid
		};
		
		ajaxCallback("loopprogram!looplist", param, function(msg){
			if (msg.success) {
				try {
					var content = "";						
					if(msg.data != null && msg.data != undefined){
						$.each( msg.data, function(i, p) {
								var checked = false;
								if (rememberloopprogram) {
									for ( var i = 0; i < rememberloopprogram.length; i++) {
										if (rememberloopprogram[i] == p.programId) {
											checked = true;
											break;
										}
									}
								}										      
						      	content += " <tr id=\""
									+ p.programId
									+ "\">"
									+ " <td style=\"width: 105px;\"><input value=\""
									+ p.programId  
									+ "\" name=\"" + p.programId+"\" " 
									+ (checked? " checked ":" ") + " id=\""+p.programId+"\" class=\"loopprogramId\"  type=\"checkbox\" />"
									+ "&nbsp;" + (p.sequenceNum<10? "0" : "") + p.sequenceNum + "&nbsp;" 
									<s:if test="#session.current_privilege.editprog">
									+ (p.state == 0 ? ("<img style=\"cursor: pointer; padding-left:0px;\" onclick=\"changestate('"+p.taskId+"','"+p.programId+"','"+p.state+"')\" src=\"${theme}/skins/default/images/btn2.png\" height=\"15px\" title=\"<s:text name="play" />\" width=\"15px\" />")
											: ("<img style=\"cursor: pointer; padding-left:0px;\" onclick=\"changestate('"+p.taskId+"','"+p.programId+"','"+p.state+"')\" src=\"${theme}/skins/default/images/btn3.png\" title=\"<s:text name="pause" />\" height=\"15px\" width=\"15px\" />"))
									</s:if>
									<s:else>
									+ (p.state == 0 ? ("<img style=\"padding-left:0px;\" src=\"${theme}/skins/default/images/btn2.png\" height=\"15px\" title=\"<s:text name="play" />\" width=\"15px\" />")
											: ("<img style=\"padding-left:0px;\" src=\"${theme}/skins/default/images/btn3.png\" title=\"<s:text name="pause" />\" height=\"15px\" width=\"15px\" />"))
									</s:else>
									+ "&nbsp;" + (p.state == 0? "<s:text name="program_play" />" : "<s:text name="program_pause" />" ) 
								    + "</td>"
									+ " <td title=\""+p.programName+"\" style=\"width: 175px;\">"
									+ (p.programName.length > 15 ? (p.programName.substring(0,15)+"...") : p.programName)
									+ " </td>"										
									+ " <td style=\"width: 105px;\">"
									+ (p.width +"x"+ p.height)
									+ " </td>"									
									+ " <td style=\"width: 110px;white-space: nowrap;\">"
									+ " <a class=\"blue1\" onclick=\"showContent('"+p.taskId+"','"+p.programId+"','"+p.programName.replace(/(\'|\"|\\)/g, "\\" + "$1")+"','loop','"+p.mainRectId+"','"+p.width+"','"+p.height+"', true)\" href=\"#\"><s:text name="layout" /></a>"
									+ "&nbsp;&nbsp;"
									+ " <a class=\"blue1\" onclick=\"showContent('"+p.taskId+"','"+p.programId+"','"+p.programName.replace(/(\'|\"|\\)/g, "\\" + "$1")+"','loop','"+p.mainRectId+"','"+p.width+"','"+p.height+"', false)\" href=\"#\"><s:text name="content" /></a>"
									+ " </td>"
									+ " <td style=\"width: 50px; white-space: nowrap;\" title=\"<s:text name="title_schedule_link"/>\" >"
									<s:if test="#session.current_privilege.editprog">
									+ (p.used > 0 ? " <img style=\"cursor: pointer;\" align=\"middle\" id=\"img"+p.programId+"\" onmouseover=\"showdetail('"+p.taskId+"','"+p.programId+"')\" onclick=\"sch('"+p.taskId+"')\" src=\"${theme}/skins/default/images/timer.png\" height=\"15px\" title=\"\" width=\"15px\" />"
											: " <img style=\"cursor: pointer;\" align=\"middle\" onclick=\"sch('"+p.taskId+"')\" src=\"${theme}/skins/default/images/notimer.png\" title=\"\" height=\"15px\" width=\"15px\" />") 
									+ "&nbsp;&nbsp;<a class=\"blue1\" href=\"#\" onclick=\"sch('"+p.taskId+"')\" >" + 
										(p.used > 0? "<s:text name="have_schedule" />" : "<s:text name="no_schedule" />") + "</a>"
									</s:if>
									<s:else>
									+ (p.used > 0 ? " <img align=\"middle\" id=\"img"+p.programId+"\" onmouseover=\"showdetail('"+p.taskId+"','"+p.programId+"')\" src=\"${theme}/skins/default/images/timer.png\" height=\"15px\" title=\"\" width=\"15px\" />"
											: " <img align=\"middle\" src=\"${theme}/skins/default/images/notimer.png\" title=\"\" height=\"15px\" width=\"15px\" />")
									+ "&nbsp;&nbsp;" + (p.used > 0? "<s:text name="have_schedule" />" : "<s:text name="no_schedule" />")
									</s:else>
									+ "</td>"
									+" <td><a href=\"#\" "
									+ " onclick=\"javascript: return view('"
									+ p.taskId+"','"+p.programId+"','"+p.width+"','"+p.height+"');\""
									+ " >"
								    +" <img src=\"${theme}/skins/default/images/pictures_folder.png\" width=\"18\" height=\"18\" border=\"0\" /></a></td>"
								 	+ " </tr>";												
						});
					}	
		
					$("#shujTb").html(content);
		
					$('.shujTb >tr:odd').addClass('alt');
					
					/**
					 * 重置滚动条
					 */
					ipubs.models.jscroll_ee();
						
				} catch (e) {
					alertMessage(e.message);
				}
			} else {
				alertMessage(msg.success);
			}
		});
		return false;
	}
	
	function showdetail(tid, pid){
		
		var param = {
				"looptask.taskId" : tid,
				"prgmid" : pid
			};   
		
     try{
     $.ajax({
			type : "post",
			dataType : "json",
			url : "date!readallByprogramID",
			data : param,
			complete : function() {		
			  
			},
			success : function(msg) {                
				if (msg.success) {
					try {
						var content = "";		
						
						Date.prototype.format = function(format)
					       {
					        var o = {
					        "M+" : this.getMonth()+1, //month
					        "d+" : this.getDate(),    //day
					        "h+" : this.getHours(),   //hour
					        "m+" : this.getMinutes(), //minute
					        "s+" : this.getSeconds(), //second
					        "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
					        "S" : this.getMilliseconds() //millisecond
					        };
					        if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
					        (this.getFullYear()+"").substr(4 - RegExp.$1.length));
					        for(var k in o)if(new RegExp("("+ k +")").test(format))
					        format = format.replace(RegExp.$1,
					        RegExp.$1.length==1 ? o[k] :
					        ("00"+ o[k]).substr((""+ o[k]).length));
					        return format;
					       };
						        var cc = 0;
								if(msg.data != null && msg.data != undefined){
									$.each(
											msg.data,
											function(i, p) {	
												if(cc<=9){
											      content += 
											    	(new Date(Date.parse(p.startDate.replace(/-/g,   "/")))).format('yyyy-MM-dd')
													+" ~ "+(new Date(Date.parse(p.stopDate.replace(/-/g,   "/")))).format('yyyy-MM-dd')
													+"  "+gettime((p.startTime))
													+" ~ "+gettime((p.stopTime))
													+"  "
													//+ getall(true, p.programs)	
											        + " ";
												}
									      cc++;
											});
								     	}	
							
							
					} catch (e) {
						alertMessage(e.message);
					}
					$("#img"+pid+"").attr("title", content);
				} else {
					alertMessage(msg.success);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				window.top.location.href='/';
			}
		});
     }catch(eer){
    	 alertMessage(eer.message);
     }
     
		return false;
	}
	function getall(all, prgms){
		var temp = "";
		$.each(prgms,function(i, p) {
			temp+= (p.playCount>0 ? (p.programName+"x"+p.playCount+"") : p.programName)+"、";
		}
		);
		
		temp = temp.substring(0,temp.length-1);
		if(all){
			return temp;
		}else
		return (temp.length>8 ? (temp.substring(0,8)+"...") : temp ) ;
		
	}
	
	function gettime(time){
	       //时间为：2011-7-7 17:16:55
	 var d = new Date(time*1000);
     var hour = ( parseInt(time/3600) < 10 ? ("0"+parseInt(time/3600)) : parseInt(time/3600));
     var minute = ( parseInt((time%3600)/60) < 10 ? ("0"+ parseInt((time%3600)/60)) :  parseInt((time%3600)/60));
	 var second = ( parseInt(time%3600)%60 < 10 ? ("0"+ parseInt(time%3600)%60) :  parseInt(time%3600)%60 );
     return  hour + ":" + minute + ":" +second; 
  }
	
	function showContent(tid, pid, pname, ttype, mid, w, h, editLayout) {  
		var param = { 
			"programID" : pid,						
			"taskID" : tid 
		}; 
		
		var temp = "\\$";
		
		ajaxCallback("looprect!GetTodoProgramLayout", param, function(msg){
			var contentUrl = "looptask!content?TaskID=" + tid +"&PrgmID=" + pid + 
					"&PName='" + pname.replace(/(\'|\"|\\)/g, "\\" + "$1") + "'&MainID="+mid+"&W=" + w + "&H=" + h + 
					"&Type='loop'&isEdit=" + (editLayout? "true" : "false")  ;
			if (msg.success) {	 
			} else { 
			} 
			showIPUBDialogStand(contentUrl, {
					title:"<s:text name="curprogram"/> " + pname,
					width:1053,
					height:673,
					beforeClose:function(){
	   					var remember = new Array();
	   					remember.push(tid); 
	   					QueryLoopTask(remember); 
	   				}
	   		});	
		}); 
	} 
	 
</script>
</head>
<body>		
	<%@ include file="/header.jsp"%>   
	
	<div class="topBj1"></div>
	
    <div class="mid1">

        <div class="bt1">

            <ul class="tab1">

                <li title="" id="one1" onclick="setTab('one',1,4)" class="hover"><s:text name="looptask" /></li>

                <li id="one2" onclick="setTab('one',2,4)"><s:text name="demandtask" /></li>

                <li id="one3" onclick="setTab('one',3,4)"><s:text name="title_terminal_insert" /></li>

            </ul>

        </div>

        <div class="nr1">

            <!-- 左侧 开始-->

            <div class="left1">
				<div class="leftMenu" >
	                <div class="dumascroll" style="height:85%">
		 				<form class="jqtransform">
		                    <ul id="looptask" class="hover leftMenu2">
		                       
		                    </ul>
		  			   </form>
					</div>
					<ul id="con_one_2" style="display: none"></ul>
	
	                <ul id="con_one_3" style="display: none"></ul>
                </div>
                <div class="leftButtonBox" style="margin-top:-75px;">
	                <s:if test="#session.current_privilege.newtask">
	                <a href="javascript:void(0)" onclick="addtask();" ><font color="#FF0000" size="3">&#x25B4;</font><s:text name="create" /></a>
	                </s:if>
	                <s:if test="#session.current_privilege.newtask">
	                <a href="#" onclick="loop('clone')"><s:text name="clone" /></a>
	                </s:if>
	                <s:if test="#session.current_privilege.edittask">
	                <a href="#" onclick="loop('edit')"><s:text name="edit" /></a>
	                </s:if>
	                <s:if test="#session.current_privilege.deltask">
	                <a href="#" onclick="loop('delete')"><s:text name="delete" /></a>
	                </s:if>
	              </div>
	               <div class="leftButtonBox" style="margin-top:0px;">                
	                <a id="task_sort" name="task_sort" href="#" onclick="tasksort();">A-Z</a>
	                <a href="#" onclick="loop('jiaoyan')" title="<s:text name="title_chekc_button" />"><s:text name="verify" /></a>
	                <!-- 
	                <s:if test="#session.current_privilege.taskstrategy">	 	                
	                 <a href="#" onclick="loop('celue')"><s:text name="tactics" /></a>	
	                </s:if>
	                -->
	                <a id="task_size" name="task_size" href="#" onclick="loop('size');"><s:text name="task_property" /></a>
	                <s:if test="#session.current_privilege.auditcarousel"> 
	                <a id="audit" name="audit" href="#" onclick="loop('<s:text name="isaudit" />')"><s:text name="audit" /></a>
	                </s:if>
                </div>

            </div>

            <!-- 左侧 结束-->

            <!-- 右侧 开始-->

            <div class="right1">
        	 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="shujBt" style="table-layout: fixed;"> 
						 <tr>						
						 <td class="first" style="width: 105px; white-space: nowrap;"><s:text name="pstate" /></td>
						 <td style="width: 175px;"><s:text name="pname" /></td>
						 <td style="width: 105px;"><s:text name="resolution" /></td>						
						 <td style="width: 110px;"><s:text name="edit" /></td>						
						 <td style="width: 50px;"><s:text name="tactics" /></td>
						 <td style="white-space:nowrap;"><s:text name="preview" /></td>
			 			</tr>
						 </table>

						 <div id="ee">
						 <table id="shujTb" width="100%" border="0" cellspacing="0" cellpadding="0" class="shujTb" style="table-layout: fixed;">
						 
						 </table>
						 </div>
 					 <script type="text/javascript">
                    $(document).ready(function () {
                        $("#ee").jscroll({
                            W: "10px"
                         , BgUrl: "url(${theme}/skins/default/images/s_bg2.gif)"
                         , Bg: "right 0 repeat-y"
                         , Bar: {
                             Bd: { Out: "#d4d4d4", Hover: "#aaa" }
                               , Bg: { Out: "-45px 0 repeat-y", Hover: "-45px 0 repeat-y", Focus: "-45px 0 repeat-y" }
                         }
                               , Btn: {
                                   btn: true
                                     , uBg: { Out: "0 0", Hover: "0px 0", Focus: "0px 0" }
                                     , dBg: { Out: "0 -8px", Hover: "0px -8px", Focus: "0px -8px" }
                               }
                         , Fn: function () { }
                        });
                    });
				</script>

					 <div class="rightMenu" style="height:20px;">
								 <ul class="left rightMenuShort">
								 <s:if test="#session.current_privilege.newprog">
								 <li onclick="addprogram()"><font color="#FF0000" size="3">&#x25B4;</font><s:text name="create" /></li>
								 </s:if>
								 <s:if test="#session.current_privilege.newprog">
								 <li onclick="loopprogram('clone')"><s:text name="clone" /></li>
								 </s:if>
								 <s:if test="#session.current_privilege.editprog">
								 <li onclick="loopprogram('edit')"><s:text name="edit" /></li>
								 </s:if>
								 <s:if test="#session.current_privilege.delprog">
								 <li onclick="loopprogram('delete')"><s:text name="delete" /></li>
								 </s:if>
								 <s:if test="#session.current_privilege.editprog">
								 <li onclick="loopprogram('up')"><s:text name="up" /></li>
								 <li onclick="loopprogram('down')"><s:text name="down" /></li>
								 </s:if>
								 </ul>
								 <ul class="left rightMenuLong" style="margin-left: 20px;"> 
								 <s:if test="#session.current_privilege.editprog">
								 <li onclick="loopprogram('clear_schedule')"><s:text name="clear" /></li>
								  </s:if>
								 </ul>
					 </div>
					 <div id="messageBox" style="display:none;font-size:12px;color:#FF0000;padding-left: 20px;"></div>
					 <div style="font-size:12px;padding-left:20px;"><s:text name="bottom_prompt_task_manager"/></div>
	               
	                 <div class="bottomMenu">
	                   <div class="rightSelect">
		                 <label style="cursor: pointer;"  
		                 onclick="return CheckHelper.checkAll('loopprogramId');return false;"><s:text name="selectall" /></label> | 
		                 <label style="cursor: pointer;"  
		                 onclick="return CheckHelper.reverseAll('loopprogramId');return false;"><s:text name="invertselection" /></label> | 
		                 <label style="cursor: pointer;"  
		                 onclick="return CheckHelper.cleanAll('loopprogramId');return false;"
		                 class="chk-cancle"><s:text name="cancel" /></label>
	                   </div>
	                	 <a href="#">
	                	 <img src="${theme}/skins/default/images/refresh3.gif" border="0" class="ref" /></a>
	                	 <div class="page1 page2">   
	                	 </div>
	                </div>

            </div>
            <!-- 右侧 结束-->

            <div class="clearit"></div>

        </div>

        <div class="di1"></div>

    </div>

	<%@ include file="/buttom.jsp"%>
</body>
</html>
