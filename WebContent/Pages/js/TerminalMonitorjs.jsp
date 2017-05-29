<%@ page import="com.gnamp.struts.filter.Context" %>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">

var viewstate = 1;

var newCount = 1;
var selectedtreeNode = null;

/**
 * 文件名,审核时间,文件大小的默认排序方式
 * @type String
 */
var nameflag = "asc", idflag = "asc", logontimeflag = "asc",

	curlooptaskflag = "asc",assignlooptaskflag = "asc",
	curdemandtaskflag = "asc",assigndemandtaskflag = "asc",
	curplugintaskflag = "asc",assignplugintaskflag = "asc",
	lastpatrolflag = "asc",
	curappflag = "asc",assignappflag = "asc",
	curkelflag = "asc",assignkelflag = "asc",
	curconfigflag = "asc",assignconfigflag = "asc",
	
    logofftimeflag = "asc", onlinestateflag = "asc";
    
var currentPageFlag = 1;
var totalPageFlag = 1;
/**
 * 一页显示多少行
 * @type Number
 */
var pageSize = 10;
/**
 * 当前选择的排序方式
 */
var currentSortFlag = "asc";
/**
 * 当前选择的排序列
 */
var currentSortName = "DEV_ID";


$(function() {
	var Nodes = [ {
		id : 0,
		pId : 0,
		name : '<s:text name="root_group"/>',
		open : true,
		expand : true,
		isParent : true,
		children:[]//这个空属性必须有
	} ];
	
	$("#v1").attr({style:"vertical-align:middle; filter: alpha(opacity=100); opacity: 1;"});
	
//	$.fn.zTree.init($("#treeterminal"), setting, Nodes);
	
	/**
	 * 在页面加载的时候，调用异步访问服务器
	 * 得到根目录下所有目录，然后设置根目录下面的所有的PID都是根目录的ID
	 */
	/*ajaxCallback("group!list",{"id":0},setNode);
	function setNode(result){
		$(result.data).each(function(i,f){
			Nodes[0].children.pid = 0;
			f.pid=0;
		});
		Nodes[0].children=result.data;
		$.fn.zTree.init($("#treeterminal"), setting,Nodes);
		zTree.setting.edit.drag.isMove = isMove;
	}*/
	
	$.fn.zTree.init($("#treeterminal"), setting,null);
	/* $("#treeterminal_1_a").click(); */
	$p =new page("terminal!query",null,showlist);
	$p.currentSortName = "name";
	queryterminals();
	
	/* 条件筛选事件绑定 */
	$("#includeChildren").bind("change", queryterminals);
	$("#searchTerminal").bind("click", queryterminals);
	
	$("#rd1").bind("change", tttime);
	$("#rd2").bind("change", tttime);

	function tttime(){
		//ajaxCallback("terminal!query",globeParam(),showlist);
		queryterminals();
	}
	


	
	$("#first").bind("click",$p.first);
	$("#previous").bind("click",$p.previous);
	$("#next").bind("click",$p.next);
	$("#last").bind("click",$p.last);
	
	
	/**
	 * 节点增删改
	 */
	$("#addNode").bind("click", addNode);
	$("#editNode").bind("click", editNode);
	$("#removeNode").bind("click", removeNode);
	
});

function changeviewstate(s){
	for(var i=1;i<=8;i++){
		if(i==s){
			$("#v"+s).removeAttr("style");
			$("#v"+s).attr({style:"vertical-align:middle; filter: alpha(opacity=100); opacity: 1;"});
		}
		else
		{
			$("#v"+i).removeAttr("style");
		}
	}
	 
	
	viewstate = s;
	queryterminals();
	
	// style="vertical-align:middle; filter: alpha(opacity=100); opacity: 1;"
}

/**
 * 排序
 */
function namesort() {
	nameflag = (nameflag == "asc" ? "desc" : "asc");
	sortfield(nameflag,"NAME");
}
function idsort() {
	idflag = (idflag == "asc" ? "desc" : "asc");
	sortfield(idflag,"DEV_ID");
}
function logontimesort() {
	logontimeflag = (logontimeflag == "asc" ? "desc" : "asc");
	sortfield(logontimeflag,"LOGON_TIME");
}
function logofftimesort() {
	logofftimeflag = (logofftimeflag == "asc" ? "desc" : "asc");
	sortfield(logofftimeflag,"LOGOFF_TIME");
}
function onlinestatesort() {
	onlinestateflag = (onlinestateflag == "asc" ? "desc" : "asc");
	sortfield(onlinestateflag,"ONLINE_STATE");
}

function curlooptasksort() {
	curlooptaskflag = (curlooptaskflag == "asc" ? "desc" : "asc");
	sortfield(curlooptaskflag,"CUR_LOOPTASK_NAME");
}
function assignlooptasksort() {
	assignlooptaskflag = (assignlooptaskflag == "asc" ? "desc" : "asc");
	sortfield(assignlooptaskflag,"ASSIGN_LOOPTASK_NAME");
}
function curdemandtasksort() {
	curdemandtaskflag = (curdemandtaskflag == "asc" ? "desc" : "asc");
	sortfield(curdemandtaskflag,"CUR_DEMANDTASK_NAME");
}
function assigndemandtasksort() {
	assigndemandtaskflag = (assigndemandtaskflag == "asc" ? "desc" : "asc");
	sortfield(assigndemandtaskflag,"ASSIGN_DEMANDTASK_NAME");
}
function curplugintasksort() {
	curplugintaskflag = (curplugintaskflag == "asc" ? "desc" : "asc");
	sortfield(curplugintaskflag,"CUR_PLUGINTASK_NAME");
}
function assignplugintasksort() {
	assignplugintaskflag = (assignplugintaskflag == "asc" ? "desc" : "asc");
	sortfield(assignplugintaskflag,"ASSIGN_PLUGINTASK_NAME");
}
function lastpatrolsort() {
	lastpatrolflag = (lastpatrolflag == "asc" ? "desc" : "asc");
	sortfield(lastpatrolflag,"LAST_PATROL");
}
function curappsort() {
	curappflag = (curappflag == "asc" ? "desc" : "asc");
	sortfield(curappflag,"CUR_APP");
}
function assignappsort() {
	assignappflag = (assignappflag == "asc" ? "desc" : "asc");
	sortfield(assignappflag,"ASSIGN_APP");
}
function curkelsort() {
	curkelflag = (curkelflag == "asc" ? "desc" : "asc");
	sortfield(curkelflag,"CUR_KERNEL");
}
function assignkelsort() {
	assignkelflag = (assignkelflag == "asc" ? "desc" : "asc");
	sortfield(assignkelflag,"ASSIGN_KERNEL");
}
function curconfigsort() {
	curconfigflag = (curconfigflag == "asc" ? "desc" : "asc");
	sortfield(curconfigflag,"CUR_CONFIG_NAME");
}
function assignconfigsort() {
	assignconfigflag = (assignconfigflag == "asc" ? "desc" : "asc");
	sortfield(assignconfigflag,"ASSIGN_CONFIG_NAME");
}


/* ajax 加载树以及terminallist */
var selectedId = 0;
var az = true;
var setting = {
	async : {
		enable : true,
		url : "group!terminal_tree_default?az="+az,
		autoParam : [ "id", "name=n", "level=lv" ],
		otherParam : {
			"otherParam" : "zTreeAsyncTest"
		},
		dataFilter : filter
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	edit:{
		drag:{isMove:true},
		enable: true,
		showRemoveBtn: false,
		showRenameBtn: false
	},
	view : {
		selectedMulti : false
	},
	callback : {
		onDblClick : onDblClick,
		onClick : onClick,
		beforeDrag : beforeDrag,
		onAsyncSuccess : zTreeOnAsyncSuccess
	}
};

var obb = null;
var isfinished = false;
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	if(obb != null )
		obb(); //duma.BeautifyScrollBar.init();		
		
	isfinished = true;		
	return false;
};


function onDblClick(e, treeId, treeNode) {
	selectedId = treeNode.id;
	selectedtreeNode = treeNode;
	$("#uploadgroupid").val(selectedId);
	/**
	 * 默认页
	 */
	currentPageFlag=1;

	$("#search").val("");
	queryterminals();
}

function onClick(e, treeId, treeNode) {
	selectedId = treeNode.id;
	selectedtreeNode = treeNode;

	
	$("#uploadgroupid").val(selectedId);
	
	currentPageFlag=1;
	$("#search").val("");
	queryterminals();
	var zTree = $.fn.zTree.getZTreeObj("treeterminal");
	zTree.expandNode(treeNode);
}

function loadRoot(){
	var treeObj = $.fn.zTree.getZTreeObj("treeterminal");
	var nodes = treeObj.getNodes();
	if (nodes.length>0) {
		treeObj.selectNode({id:-1});
	}
	
	selectedId = 0;
	$("#uploadgroupid").val(selectedId);
	currentPageFlag=1;
	
	$("#search").val("");
	queryterminals();
}

function groupsort(){
	az = !az;
	 setting = {
			async : {
				enable : true,
				url : "group!terminal_tree_default?az="+az,
				autoParam : [ "id", "name=n", "level=lv" ],
				otherParam : {
					"otherParam" : "zTreeAsyncTest"
				},
				dataFilter : filter
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			edit:{
				drag:{isMove:true},
				enable: true,
				showRemoveBtn: false,
				showRenameBtn: false
			},
			view : {
				selectedMulti : false
			},
			callback : {
				onDblClick : onDblClick,
				onClick : onClick,
				beforeDrag : beforeDrag,
				onAsyncSuccess : zTreeOnAsyncSuccess
			}
		}; 
	
	$.fn.zTree.init($("#treeterminal"), setting,null);
	queryterminals();
}



function beforeDrag(treeId, treeNodes) {
	return false;
}
/*暂时这么用着*/
function shuaxin(){
	remember = null;
	queryterminals();
}

function queryterminals() {

	$("#todoterminaldiv").hide();
	$("#terminaldiv").show();
	
	var param = {
		"terminalparam.groupId" : selectedId,
		"terminalparam.searchKey" : (($("#search").val() != null && $("#search").val() != '' && $("#search").val() != undefined) ? /*parseInt($("#search").val(), 16)*/$("#search").val() : $("#search").val()),
		"terminalparam.includeChildren" : $("#includeChildren").attr("checked") == "checked" ? "true"
				: "false",
	};	
	if (viewstate == 1) {
		param["hardware"] = true;
	}
	
	$p.ajax($p.globeParam(param));
	return false;
}

function filter(treeId, parentNode, childNodes) {
	if (!childNodes)
		return null;
	for ( var i = 0, l = childNodes.length; i < l; i++) {
		childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
	}
	return childNodes;
}

function  changepagesize(){
	currentPageFlag = 1;
	changepagesize( $("select[name=pagesize]").val());
}

function addNode()
{
	var id = selectedId;
	if(id==undefined){
		id=0;
	}
	showIPUBDialogStand("group!ToAddGroup?id=" + id,{title:"<s:text name="newgroup"/>",width:400,height:190});
}

function addNodeed(result) {
	try{
		var id = selectedId;
		if(id==undefined){
			id=0;
		}
		var zTree = $.fn.zTree.getZTreeObj("treeterminal");
		zTree.addNodes(id==0?0:zTree.getNodeByParam("id",id,null), {
				id : result.groupId,
				pId : result.parentId/*id*/,
				name : result.groupName
				});
		return true;
	}catch(e){
		return false;
	}
}

function editNode() {
	var id = selectedId;
	if(id==undefined || id==0){
		alert("<s:text name="pleaseselectgroup"/>");
		  return;
	}
	showIPUBDialogStand("group!ToModifyGroup?id=" + selectedtreeNode.id,{width:550,height:217,title:"<s:text name="editgroup"/>"});
}
function editNodeed(result){
	if (result == "" || result == undefined) {
		return;
	}
	/**
	 * 如果没有更改目录的信息，就不用刷新
	 */
	if(result.groupName==undefined&&result.parentId==undefined){
		
		return;
	}

    var isdelete = false;
	var zTree = $.fn.zTree.getZTreeObj("treeterminal");
	var selectedtreeNode = zTree.getSelectedNodes();


	
	if(selectedtreeNode[0].parentId != result.parentId){
		if(result.parentId != 0){			
			if(zTree.getNodeByParam("id",result.parentId)!=null)
			{
			  if(zTree.getNodeByParam("id",result.parentId).open == false){
			    zTree.moveNode(zTree.getNodeByParam("id",result.parentId),selectedtreeNode[0],"inner",false);		
			    //zTree.expandNode(zTree.getNodeByParam("id",result.parentId), true);
			    //zTree.removeNode(selectedtreeNode[0]);
				  isdelete = true;
			  }
			  else{
				  zTree.moveNode(zTree.getNodeByParam("id",result.parentId),selectedtreeNode[0],"inner",false);		
			  }
			}
			else{
				zTree.removeNode(selectedtreeNode[0]);
				isdelete = true;
			}
		}
		else{
			zTree.moveNode(null,selectedtreeNode[0],"inner",false);
		}
	}
	if(!isdelete){
		selectedtreeNode[0].name = result.groupName;
		selectedtreeNode[0].parentId = result.parentId;
		
		zTree.updateNode(selectedtreeNode[0]);
	}
	
}

function removeNode() {	
	var id = selectedId;
	if(id==undefined || id==0){
		alert("<s:text name="pleaseselectgroup"/>");
		  return;
	}
	if(selectedtreeNode.id>0){
		confirm("<s:text name="isdelete_group"/>",function(){
			ajaxCallback("group!remove", {
				"group.groupId" : selectedtreeNode.id
			}, removedNode);
		});
	}
	
	
}
function removedNode(result) {	
	if (!result.success) {
		if (result.data != undefined) {
			alert(result.data);
		}
		return;
	}
	
	var zTree = $.fn.zTree.getZTreeObj("treeterminal");
	zTree.removeNode(selectedtreeNode);
	selectedtreeNode.id = 0;
	selectedId = 0;
	queryterminals();
}


/**
 * 
 * @param {} flag总共的页数
 * 设置总页数 并且调用 
 * @see generic(index)
 */
function setTotalFlag(flag){
	totalPageFlag = flag;
	$("#pageIndex").html(generic(totalPageFlag));
}
/**
 * 
 * @param {} index 总页数
 * @return {}根据总页数生成页码和链接
 */
function generic(index){
	var page_index_result = "";
	for(var i=1;i<=index;i++){
		page_index_result +="<a href='javascript:gotoPage("+i+")'>"+i+"</a>&nbsp;";
	}
	return page_index_result;
}


function sortfield(flag,name) {
/* 	currentSortFlag= flag;
	currentSortName = name;
	ajaxCallback("terminal!sort", globeParam(), showlist); */
	
	$p.currentSortManaer= flag;
	$p.currentSortName = name;
	queryterminals();
}

function showlist(result) {
	if(viewstate == 1) {
		return showlist_1(result);
	}
	
	try {
		
		var tbthtml = "";
		
		tbthtml+="<tr>";					 	
		tbthtml+="<td onclick=\"idsort();\" id=\"idsort\" class=\"first\" style=\"cursor: pointer; width: 105px;\"><s:text name="devid" /></td>";
		tbthtml+="<td onclick=\"namesort();\" id=\"namesort\" style=\"cursor: pointer; width: 170px;\"><s:text name="devicename" /></td>";
		if(viewstate ==1){
		tbthtml+="<td onclick=\"logontimesort();\" id=\"logontimesort\" style=\"cursor: pointer; width: 130px;\"><s:text name="logontime" /></td>";						
		tbthtml+="<td style=\"width: 120px;\"><s:text name="process" /></td>";
		}
		else if(viewstate ==2){
		tbthtml+="<td onclick=\"assignlooptasksort();\" id=\"assignlooptasksort\" style=\"cursor: pointer; width: 130px;\"><s:text name="designcarousel" /></td>";						
		tbthtml+="<td onclick=\"curlooptasksort();\" id=\"curlooptasksort\" style=\"cursor: pointer; width: 120px;\"><s:text name="currentcarousel" /></td>";
		}
		else if(viewstate ==3){
		tbthtml+="<td onclick=\"assigndemandtasksort();\" id=\"assigndemandtasksort\" style=\"cursor: pointer; width: 130px;\"><s:text name="designdemand" /></td>";						
		tbthtml+="<td onclick=\"curdemandtasksort();\" id=\"curdemandtasksort\" style=\"cursor: pointer; width: 120px;\"><s:text name="currentdemand" /></td>";
		}
		else if(viewstate ==4){
		tbthtml+="<td onclick=\"assignplugintasksort();\" id=\"assignplugintasksort\" style=\"cursor: pointer; width: 130px;\"><s:text name="designplugin" /></td>";						
		tbthtml+="<td onclick=\"curplugintasksort();\" id=\"curplugintasksort\" style=\"cursor: pointer; width: 120px;\"><s:text name="currentspots" /></td>";
		}
		else if(viewstate ==5){
		 tbthtml+="<td onclick=\"lastpatrolsort();\" id=\"lastpatrol\" style=\"cursor: pointer; width: 180px;\"><s:text name="text_terminalreport_recentcheck" /></td>";	
		 // tbthtml+="<td style=\"width: 70px;\"><s:text name="devicestatus" /></td>";   
		 <% if (Context.isAlcoholVersion()) { %>
		 	tbthtml+="<td style=\"width: 70px;\"><s:text name="pipe" /></td>"; 
		 <% } else {%>
		 	tbthtml+="<td style=\"width: 70px;\"><s:text name="devicestatus" /></td>"; 
		 <% } %> 
		}
		else if(viewstate ==6){
		tbthtml+="<td onclick=\"assignappsort();\" id=\"assignappsort\" style=\"cursor: pointer; width: 130px;\"><s:text name="setapp" /></td>";						
		tbthtml+="<td onclick=\"curappsort();\" id=\"curappsort\" style=\"cursor: pointer; width: 120px;\"><s:text name="text_upgrade_reported" /></td>";
		}
		else if(viewstate ==7){
		tbthtml+="<td onclick=\"assignkelsort();\" id=\"assignkelsort\" style=\"cursor: pointer; width: 130px;\"><s:text name="text_upgrade_assignedfirmware" /></td>";						
		tbthtml+="<td onclick=\"curkelsort();\" id=\"curkelsort\" style=\"cursor: pointer; width: 120px;\"><s:text name="text_upgrade_reportedfirmware" /></td>";
		}
		else if(viewstate ==8){
		tbthtml+="<td onclick=\"assignconfigsort();\" id=\"assignconfigsort\" style=\"cursor: pointer; width: 130px;\"><s:text name="text_upgrade_assignsetting" /></td>";						
		tbthtml+="<td onclick=\"curconfigsort();\" id=\"curconfigsort\" style=\"cursor: pointer; width: 120px;\"><s:text name="text_upgrade_reportconfig" /></td>";
		}
		tbthtml+="<td id=\"taskstate\" style=\"cursor: pointer; width: 50px;\"><s:text name="taskstate" /></td>";
		tbthtml+="<td id=\"softwarestate\" style=\"cursor: pointer; width: 50px;\"><s:text name="softwarestatus" /></td>";
		tbthtml+="<td onclick=\"onlinestatesort();\" id=\"onlinestatesort\" style=\"cursor: pointer; width: 50px;\"><s:text name="onlinestate" /></td>";
		tbthtml+="<td><s:text name="log" /></td>	";						
		tbthtml+="</tr>";
		
		$("#tbt").html(tbthtml);
		
		var content = "";
		var x = 0; var y = 0; var z = 0;
		var tot = 0;
		       $.each(
						result.data,
						function(i, f) {
							var checked = false;
							if (remember) {
								for (var i = 0; i < remember.length; i++) {
									if (remember[i] == f.deviceId) {
										checked = true;
										break;
									}
								}
							}
							var tmp="";
							if((f.deviceId).toString(16).length<12){
								for(var i=0;i<(12-(f.deviceId).toString(16).length);i++)
								tmp +="0"; 
							}								
							
							tot++;
							(( (f.loopTaskUpdated==1 || f.loopTaskUpdated==0)  && (f.demandTaskUpdated==1 || f.demandTaskUpdated==0) && (f.pluginTaskUpdated==1 || f.pluginTaskUpdated==0) && (f.subtitleUpdated==1 || f.subtitleUpdated==0) ) ? x++ : x+=0);
							(( (f.kernelUpdated==1 || f.kernelUpdated==0)  && (f.appUpdated==1 || f.appUpdated==0) && (f.configUpdated==1 || f.configUpdated==0) ) ? y++ : y+=0);
							(f.onlineState == 0 ? z+=0 : z++);
							
							content += "<tr style=\"word-break: keep-all\" id=\""
									+ f.deviceId
									+ "\">"
									+ "<td style=\"width: 12px;\"><label><input value=\""
									+ f.deviceId  
									+ "\" name=\""+f.deviceId+"\" " 
									+ (checked? " checked ":" ") + " id=\""+(i+1)+"\"  onclick=\"javascript:return checkNext(this);\" class=\"devid\"  type=\"checkbox\"/></label></td>"
									+ "<td style=\"width: 75px;\">"
									+ "<span title=\""+ taskTitleText(f)+"\" >"
									+ ((f.deviceId).toString(16).length<12? tmp+(f.deviceId).toString(16).toUpperCase():(f.deviceId).toString(16).toUpperCase())
									+ "</span>"
									+ "</td>"
									+ "<td title=\""+f.deviceName+"\" style=\"width: 170px;\">"
									+ "<ul style=\"width:170px;overflow:hidden;text-overflow:ellipsis;\"><nobr>" + f.deviceName + "</nobr></ul>"
									+ "</td>";
									
									if(viewstate==1){
										content+= "<td style=\"word-break: keep-all; width:130px; white-space:nowrap\">"
											+  (($('input:radio[name="rd"]:checked').val()==1) ? GetDateDiff(f.logonTime) : f.logonTime)
											+ "</td>"								
											+ "<td style=\"width: 120px;\">";
										content += downloadText(f);	 
										content+= "</td>";
									}
									else if(viewstate==2){
										content+= "<td style=\"word-break: keep-all; width:130px; white-space:nowrap\">"
										+ (f.assignLoopTaskName != undefined ? f.assignLoopTaskName : "")
										+ "</td>"								
										+ "<td style=\"width: 120px;\">"									
										+ f.curLoopTaskName 
										+ "</td>";
									}
									else if(viewstate==3){
										content+= "<td style=\"word-break: keep-all; width:130px; white-space:nowrap\">"
										+ (f.assignDemandTaskName != undefined ? f.assignDemandTaskName : "")
										+ "</td>"								
										+ "<td style=\"width: 120px;\">"									
										+ f.curDemandTaskName 
										+ "</td>";
									}
									else if(viewstate==4){
										content+= "<td style=\"word-break: keep-all; width:130px; white-space:nowrap\">"
										+ (f.assignPluginTaskName != undefined ? f.assignPluginTaskName : "")
										+ "</td>"								
										+ "<td style=\"width: 120px;\">"									
										+ f.curPluginTaskName 
										+ "</td>";
									}
									else if(viewstate==5){
										 content+= "<td style=\"word-break: keep-all; width:180px; white-space:nowrap\">"
										+ (($('input:radio[name="rd"]:checked').val()==1) ? GetDateDiff(f.lastPatrol) : f.lastPatrol)
										+ "</td>";	 									
										
										 content+= "<td style=\"word-break: keep-all; width:70px; white-space:nowrap\">"
											+ getmorestatus(f.aliveInterval)
											+ "</td>";  
									}
									else if(viewstate==6){
										content+= "<td style=\"word-break: keep-all; width:130px; white-space:nowrap\">"
										+ (f.assignAppVersion != undefined ? f.assignAppVersion : "")
										+ "</td>"								
										+ "<td style=\"width: 120px;\">"									
										+ f.curAppVersion 
										+ "</td>";
									}
									else if(viewstate==7){
										content+= "<td style=\"word-break: keep-all; width:130px; white-space:nowrap\">"
										+ (f.assignKernelVersion != undefined ? f.assignKernelVersion : "")
										+ "</td>"								
										+ "<td style=\"width: 120px;\">"									
										+ f.curKernelVersion 
										+ "</td>";
									}
									else if(viewstate==8){
										content+= "<td style=\"word-break: keep-all; width:130px; white-space:nowrap\">"
										+ (f.assignConfigName != undefined ? f.assignConfigName : "")
										+ "</td>"								
										+ "<td style=\"width: 120px;\">"									
										+ f.curConfigName 
										+ "</td>";
									}
									
								    content+= "<td style=\"width: 50px;\" title=\"" + taskTitleText(f) + "\" >"
									+ (( (f.loopTaskUpdated==1 || f.loopTaskUpdated==0)  && (f.demandTaskUpdated==1 || f.demandTaskUpdated==0) && (f.pluginTaskUpdated==1 || f.pluginTaskUpdated==0) && (f.subtitleUpdated==1 || f.subtitleUpdated==0) ) ? "<s:text name="synchronized"/>" : "<s:text name="unsynchronized"/>")
									+ "</td>"
									+"</td>"
									+ "<td style=\"width: 50px;\">"
									+ (( (f.kernelUpdated==1 || f.kernelUpdated==0)  && (f.appUpdated==1 || f.appUpdated==0) && (f.configUpdated==1 || f.configUpdated==0) ) ? "<s:text name="synchronized"/>" : "<s:text name="unsynchronized"/>")
									+ "</td>"
									+ "<td style=\"width: 50px;\">"
									+ (f.onlineState == 0 ? "<img src=\"${theme}/skins/default/images/btn5.png\" height=\"16px\" width=\"16px\" />"
											: "<img src=\"${theme}/skins/default/images/btn1.png\" height=\"16px\" width=\"16px\" />")
									+ (f.onlineState==0? "<s:text name="device_offline"/>" : "<s:text name="device_online"/>")
									+ "</td>"
									+ "<td><a "
									+ " onclick=\"javascript: view("
									+ f.deviceId
									+ ");\""
									+ " style=\"cursor: hand; cursor: pointer; color: blue;\"><s:text name="view"/></a></td>"
									+ "</tr>";
						});
		       
		       $("#taskstate").attr("title", ""+x+"/"+tot+"");  
		       $("#softwarestate").attr("title", ""+y+"/"+tot+"");  
		       $("#onlinestatesort").attr("title", ""+z+"/"+tot+"");  
		       
		setTotalFlag(result.page.totalPages);
		
		$("#currentWithTotal").html("<b class='orange1'>"+currentPageFlag+"</b>/"+totalPageFlag);
		$("#totalData").html(result.page.totalRows);		
		
		$("#mytab").children().remove();
		$("#mytab").html(content);
		
		$('.shujTb > tbody >tr:odd').addClass('alt');
		
		/**
		 * 重置滚动条
		 */
		ipubs.models.jscroll_ee();
		
	} catch (e) {
		alert(e.message);
	}
}

function taskTitleText(d) {  

	var loop = (!d || d.curLoopTaskId == 0)? "<s:text name="text_task_null"/>" : 
			(d.curLoopTaskName? d.curLoopTaskName : "<s:text name="text_task_unknown"/>");
	var demand = (!d || d.curDemandTaskId == 0)? "<s:text name="text_task_null"/>" : 
			(d.curDemandTaskName? d.curDemandTaskName : "<s:text name="text_task_unknown"/>");
	var plugin = (!d || d.curPluginTaskId == 0)? "<s:text name="text_task_null"/>" : 
			(d.curPluginTaskName? d.curPluginTaskName : "<s:text name="text_task_unknown"/>");		

	return  "<s:text name="text_cur_looptask"/>" +  loop + "&#X0D;&#X0A;" +
			"<s:text name="text_cur_demandtask"/>"  +  demand + "&#X0D;&#X0A;" +
			"<s:text name="text_cur_plugintask"/>"  +  plugin ;
}

function sizeX_xMB(size) {	  
	var uint_1M = 1024*1024;  
	size = (size/uint_1M).toString();
	var pos = size.indexOf(".");
	if (pos > 0) { 
		size = size.substr(0,pos+2);
	} 
	return "" + size + "MB";
}

function downloadText(d) {  
	if(d.downloadTotal>0){			 
		if (d.downloadFinished == d.downloadTotal) {
			return "<s:text name="finished"/>";
		} else { 
			// return "<s:text name="text_download_finished"/>" + sizeX_xMB(d.downloadFinished) + 
			//	"/<s:text name="text_download_total"/>"+ sizeX_xMB(d.downloadTotal);
			
			return sizeX_xMB(d.downloadFinished) +  "/"+ sizeX_xMB(d.downloadTotal);
		}
	} else{
		return ( (d.loopTaskUpdated == 1 || d.loopTaskUpdated == 0) && 
		 		(d.demandTaskUpdated == 1 || d.demandTaskUpdated == 0) && 
		 		(d.pluginTaskUpdated == 1 ||d.pluginTaskUpdated == 0) )? "<s:text name="finished"/>" : "";
	}
}

function taskUpdated(d) { 
	return ( d && (d.loopTaskUpdated==1 || d.loopTaskUpdated==0)  
			&& (d.demandTaskUpdated==1 || d.demandTaskUpdated==0) 
			&& (d.pluginTaskUpdated==1 || d.pluginTaskUpdated==0) 
			&& (d.subtitleUpdated==1 || d.subtitleUpdated==0) );
}

function binUpdated(d) { 
	return (d && (d.kernelUpdated==1 || d.kernelUpdated==0) 
			&& (d.appUpdated==1 || d.appUpdated==0) 
			&& (d.configUpdated==1 || d.configUpdated==0) );
}

function boardType(d) {  
	var hexId12 = (d && d.deviceId)? (d.deviceId).toString(16).toUpperCase() : "";
	hexId12 = hexId12.length >= 2? hexId12.substr(0, 2).toUpperCase() : hexId12;
	var cpuType = hexId12 == "A2"? "<s:text name="board_tp6u_2core"/>" : 
			 hexId12 == "A4"? "<s:text name="board_tp6q_4core"/>" : 
			 	hexId12 == "D2"? "<s:text name="board_dp3066_2core"/>" :
			 		hexId12 == "D4"? "<s:text name="board_dp3066_4core"/>" : 
				 		hexId12 == "D6"? "<s:text name="board_dp3288_4core"/>" : 
					 		hexId12 == "D8"? "<s:text name="board_dp3368_8core"/>" : "<s:text name="board_unknown"/>" ; 
	var licenseType = (d && d.licenseType)? d.licenseType : "";
	licenseType = licenseType.length >= 4? licenseType.substr(2, 4) : licenseType;
	return licenseType? cpuType + "-" + licenseType : cpuType;
}

function storageSpace(d) {   
	var s = !d || !d.storage? "" : 
			d.storage == "sdcard"? "<s:text name="storage_sdcard"/>" : 
				d.storage == "memory"? "<s:text name="storage_memory"/>" : 
					d.storage == "udisk"? "<s:text name="storage_udisk"/>" : 
						d.storage == "hddisk"? "<s:text name="storage_hddisk"/>" : "";
	var t = !d || d.totalSpace <= 0? "0.0" : (d.totalSpace/(1024*1024*1024)).toString() + ".0";
	var f = !d || d.freeSpace <= 0? "0.0" : (d.freeSpace/(1024*1024*1024)).toString() + ".0";

	t = t.substr(0,t.indexOf(".")+2);
	f = f.substr(0,f.indexOf(".")+2);
	// return s + "[<s:text name="text_space_free"/>" + f + 
	//		"G/<s:text name="text_space_total"/>" + t + "G]"; 
	return s + "(" + f + "G/" + t + "G)";
}

function resolution(d) { 
	return d && d.resolution? d.resolution : "";
}

function networkType(d) {
	return !d || !d.networkType? "" : 
			d.networkType == "ethernet"? "<s:text name="network_ethernet"/>" : 
				d.networkType == "wifi"? "<s:text name="network_wifi"/>" : 
					d.networkType == "mobile"? "<s:text name="network_3g"/>" : "<s:text name="network_unknown"/>";
}

function volume(d) {
	return d && (typeof(d.volume) != undefined) && 0 <= d.volume && d.volume <= 10? d.volume : "";
}

function showlist_1(result) {
	try {
		
		var tbthtml = "";
		
		tbthtml+="<tr>";					 	
		tbthtml+="<td onclick=\"idsort();\" id=\"idsort\" class=\"first\" style=\"cursor: pointer; width: 105px;\"><s:text name="devid" /></td>";
		tbthtml+="<td onclick=\"namesort();\" id=\"namesort\" style=\"cursor: pointer; width: 170px;\"><s:text name="devicename" /></td>";
 
		tbthtml+="<td style=\"width: 111px;\"><s:text name="storage_space" /></td>";						
		//tbthtml+="<td onclick=\"curappsort();\" id=\"curappsort\" style=\"cursor: pointer; width: 70px;\"><s:text name="device_version" /></td>";
		tbthtml+="<td style=\"width: 77px;\"><s:text name="board_type" /></td>";					
		tbthtml+="<td style=\"width: 77px;\"><s:text name="resolution" /></td>";					
		tbthtml+="<td style=\"width: 85px;\"><s:text name="currnet_network" /></td>";	  
		
		tbthtml+="<td onclick=\"onlinestatesort();\" id=\"onlinestatesort\" style=\"cursor: pointer; width: 50px;\"><s:text name="onlinestate" /></td>";
		tbthtml+="<td><s:text name="current_volume" /></td>	";						
		tbthtml+="</tr>";
		
		$("#tbt").html(tbthtml);
		
		var content = "";
		var x = 0; var y = 0; var z = 0;
		var tot = 0;
		
		$.each(result.data, function(i, f) {
				var checked = false;
				if (remember) {
					for (var i = 0; i < remember.length; i++) {
						if (remember[i] == f.deviceId) {
							checked = true;
							break;
						}
					}
				}
				var hexId12 = (f.deviceId).toString(16).toUpperCase();
				while (hexId12.length < 12){ 
					hexId12 = "0" + hexId12; 
				}								
				
				tot++;
				if (taskUpdated(f)) {
					x++;
				}
				if (binUpdated(f)) {
					y++;
				}
				if (f.onlineState != 0) {
					z++;
				}
				
				var selectHtml = "<input id=\""+(i+1)+"\" name=\""+f.deviceId+"\" class=\"devid\" " + 
						"type=\"checkbox\" " + (checked? "checked ":"") + "value=\"" + f.deviceId +"\" " + 
						"onclick=\"javascript:return checkNext(this);\" />"
				var nameHtml = "<ul style=\"width:170px;overflow:hidden;text-overflow:ellipsis;\">" + 
						"<nobr>" + f.deviceName + "</nobr></ul>";
						
				var onlineStateHtml = "<img src=\"${theme}/skins/default/images/" + 
						(f.onlineState==0? "btn5.png" : "btn1.png") + "\" height=\"16px\" width=\"16px\" />" + 
						(f.onlineState==0? "<s:text name="device_offline"/>" : "<s:text name="device_online"/>");
				
				content += "<tr style=\"word-break: keep-all\" id=\"" + f.deviceId  + "\">";
				content += "<td style=\"width: 12px;\"><label>" + selectHtml + "</label></td>";
				content += "<td style=\"width: 75px;\"><span title=\""+ taskTitleText(f) +"\" >" + hexId12 + "</span> </td>";
				content += "<td title=\""+f.deviceName+"\" style=\"width: 170px;\">"
						+ "<ul style=\"width:170px;overflow:hidden;text-overflow:ellipsis;\"><nobr>" + f.deviceName + "</nobr></ul>"
						+ "</td>"; 

						
				content += "<td style=\"width: 111px;\">" + storageSpace(f) + "</td>";						
				// content += "<td style=\"width: 70px;\">" + f.curAppVersion  + "</td>";					
				content += "<td style=\"width: 77px;\">" + boardType(f) + "</td>";					
				content += "<td style=\"width: 77px;\">" + resolution(f) + "</td>";					
				content += "<td style=\"width: 85px;\">" + networkType(f) + "</td>";	
					
			    content += "<td style=\"width: 50px;\">"  + onlineStateHtml + "</td>"
				content += "<td>" + volume(f) + " </td>"
				content += "</tr>";
			});
		       
		$("#taskstate").attr("title", ""+x+"/"+tot+"");  
		$("#softwarestate").attr("title", ""+y+"/"+tot+"");  
		$("#onlinestatesort").attr("title", ""+z+"/"+tot+"");  
		       
		setTotalFlag(result.page.totalPages);
		
		$("#currentWithTotal").html("<b class='orange1'>"+currentPageFlag+"</b>/"+totalPageFlag);
		$("#totalData").html(result.page.totalRows);		
		
		$("#mytab").children().remove();
		$("#mytab").html(content);
		
		$('.shujTb > tbody >tr:odd').addClass('alt');
		
		/**
		 * 重置滚动条
		 */
		ipubs.models.jscroll_ee();
		
	} catch (e) {
		alert(e.message);
	}
}

function copyParam(destination, source) {
    for (var property in source)  
    { 
    	destination[property] = source[property];  
    }
    return destination;  
} 

/**
 * ***********************************************************************************
 * 分页js跳转
 * 
 * ***********************************************************************************
 */
/**
 * 
 * @param {} index
 * 跳转到哪一页,然后设置当前页为这个页码
 */
function gotoPage(index,inparam){
	currentPageFlag = index;
		
	ajaxCallback("terminal!query",globeParam(),showlist);
}

function globeParam(inparam){
	var param = {			
		"terminalparam.groupId" : selectedId,
		"terminalparam.searchKey" : (($("#search").val() != null && $("#search").val() != '' && $("#search").val() != undefined) ? $("#search").val() : $("#search").val()),
		"terminalparam.includeChildren" : $("#includeChildren").attr("checked") == "checked" ? "true"
				: "false"
	};
	if(typeof(inparam) != undefined)
	{
		copyParam(param,inparam);
	}
	return param;
}


function checkNext(elet) {           
    /* var c = 1;
    var CheckCount = 32767; //short。Maxvalue
    for (var j = 1; j < CheckCount; j++) {
        if (document.getElementById(j).checked) {
            c = j; 
            break;
        } 
    }          
    for (var i = c; i < elet.id; i++) {
        if (event.shiftKey == true){
             document.getElementById(i).checked = elet.checked;          
        }
    } */
}

function getmorestatus(sv){
	var result = "";
	// 酒测加了一个 1
	if(sv<2)
		sv+=1;
	
	if(sv==2){
		result = "<s:text name="normal"/>";
	}else if(sv==1){
		result = "<%= Context.isAlcoholVersion()? "Empty" : "" %>";
	}else if(sv==0x50){
		result = "<s:text name="breakdown"/>";
	}else if(sv==0x53){
		result = "<s:text name="error"/>";
	}else if(sv==0x52){
		result = "<s:text name="nosignal"/>";
	}else if(sv==0x51){
		result = "<s:text name="normal"/>";
	}
	return result;
} 

function GetDateDiff(startTime) {  
	 
	  var result="<s:text name="never"/>";
	     
      if(startTime != "1970-01-01 00:00:00"){
	    	 
		 var mydate = new Date();
	
	     endTime =  mydate.getFullYear() + "-" + 
	     ((mydate.getMonth()+1) >10?(mydate.getMonth()+1):"0"+(mydate.getMonth()+1)) + "-" + 
	     (mydate.getDate()>10?mydate.getDate():"0"+mydate.getDate()) + " " +
	     (mydate.getHours()>10?mydate.getHours():"0"+mydate.getHours()) + ":" +
	     (mydate.getMinutes()>10?mydate.getMinutes():"0"+mydate.getMinutes()) + ":" + 
	     (mydate.getSeconds()>10?mydate.getSeconds():"0"+mydate.getSeconds());
	
	    // alert(startTime);
	
	     //将xxxx-xx-xx的时间格式，转换为 xxxx/xx/xx的格式   
	
	     startTime = startTime.replace(/\-/g, "/");  	            
	    
	     endTime = endTime.replace(/\-/g, "/");  	           
	
	     var sTime = new Date(startTime);      //开始时间  
	
	     var eTime = new Date(endTime);  //结束时间  
   
     
	     var day = parseInt((eTime.getTime() - sTime.getTime()) / parseInt(1000*3600*24));
	     var hour = parseInt((eTime.getTime() - sTime.getTime()) / parseInt(1000*3600));
	     var minute = parseInt((eTime.getTime() - sTime.getTime()) / parseInt(1000*60));
	     var second = parseInt((eTime.getTime() - sTime.getTime()) / parseInt(1000));
	     
	     if (day > 0)
	     {
	         if (day >= 365)
	             result = parseInt((day / 365).toString()).toString() + "<s:text name="year"/>";
	         else if (day > 30)
	             result = parseInt((day / 30).toString()).toString() + "<s:text name="month"/>";
	         else if (day > 7)
	             result = parseInt((day / 7).toString()).toString() + "<s:text name="week"/>";
	         else if (day > 0)
	             result = parseInt(day.toString()).toString() + "<s:text name="day"/>";
	     }
	     else
	     {
	         if (hour > 0)
	         {
	             result = hour.toString() + "<s:text name="hour"/>";
	         }
	         else
	         {
	             if (minute > 0)
	             {
	                 result = minute.toString() + "<s:text name="minute"/>";
	             }
	             else
	             {
	                 if (second > 0)
	                 {
	                     result = second.toString() + "<s:text name="second"/>";
	                 }
	                 else
	                 {
	                     result = "0<s:text name="second"/>";
	                 }
	             }
	         }
	     }
     }
     
     return result;

}  
</script>