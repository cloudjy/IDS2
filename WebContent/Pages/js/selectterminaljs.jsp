<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">


var newCount = 1;
var selectedtreeNode = null;

/**
 * 文件名,审核时间,文件大小的默认排序方式
 * @type String
 */
var nameflag = "asc", idflag = "asc", logontimeflag = "asc",
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
		name : "<s:text name="root_group"/>",
		open : true,
		expand : true,
		isParent : true,
		children:[]//这个空属性必须有
	} ];
	

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
		ajaxCallback("terminal!query",globeParam(),showlist);
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

	/**
	 * 排序
	 */
	$("#namesort").bind("click", namesort);
	$("#idsort").bind("click", idsort);
	$("#logontimesort").bind("click", logontimesort);
	$("#logofftimesort").bind("click", logofftimesort);
	$("#onlinestatesort").bind("click", onlinestatesort);
	
	
	$("#first").bind("click",first);
	$("#previous").bind("click",previous);
	$("#next").bind("click",next);
	$("#last").bind("click",last);
});


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



function queryterminals() {	
	var param = {
		"terminalparam.groupId" : selectedId,
		"terminalparam.searchKey" : (($("#search").val() != null && $("#search").val() != '' && $("#search").val() != undefined) ? /*parseInt($("#search").val(), 16)*/$("#search").val() : $("#search").val()),
		"terminalparam.includeChildren" : $("#includeChildren").attr("checked") == "checked" ? "true"
				: "false",
	};	
	
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
	showIPUBDialogStand("group!ToAddGroup?id=" + id,{title:"新建分组",width:400,height:185});
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
				pId : id,
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
	showIPUBDialogStand("group!ToModifyGroup?id=" + selectedtreeNode.id,{width:550,height:217,title:"编辑分组"});
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


	var zTree = $.fn.zTree.getZTreeObj("treeterminal");
	var selectedtreeNode = zTree.getSelectedNodes();
	
	selectedtreeNode[0].name = result.groupName;
	selectedtreeNode[0].parentId = result.parentId;
	zTree.moveNode(zTree.getNodeByParam("id",result.parentId),selectedtreeNode[0],"inner",false);
	zTree.updateNode(selectedtreeNode[0]);
	
}

function removeNode() {	
	var id = selectedId;
	if(id==undefined || id==0){
		alert("<s:text name="pleaseselectgroup"/>");
		  return;
	}
	if(selectedtreeNode.id>0){
		confirm("<s:text name="isdelete"/>",function(){
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
	try {
		var content = "";
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
							
							content += "<tr style=\"word-break: keep-all\" id=\""
									+ f.deviceId
									+ "\">"
									+ "<td style=\"width: 12px;\"><label><input value=\""
									+ f.deviceId  
									+ "\" name=\""+f.deviceId+"\" " 
									+ (checked? " checked ":" ") + " id=\""+(i+1)+"\"  onclick=\"javascript:return checkNext(this);\" class=\"devid\"  type=\"checkbox\"/></label></td>"
									+ "<td style=\"width: 75px;\">"
									+ "<span title=\""+(f.assignLoopTaskName=="" ? "" : "<s:text name="looptask"/> "+f.assignLoopTaskName)+(f.assignDemandTaskName=="" ? "" : " <s:text name="demandtask"/> "+f.assignDemandTaskName)+(f.assignPluginTaskName=="" ? "" : " <s:text name="plugintask"/> "+f.assignPluginTaskName)+"\" >"
									+ ((f.deviceId).toString(16).length<12? tmp+(f.deviceId).toString(16).toUpperCase():(f.deviceId).toString(16).toUpperCase())
									+ "</span>"
									+ "</td>"
									+ "<td title=\""+f.deviceName+"\" style=\"width: 170px;\">"
									+ (f.deviceName.length > 12 ? f.deviceName.substr(0,
											12)
											+ "..." : f.deviceName)
									+ "</td>"
									+ "<td style=\"word-break: keep-all; width:130px; white-space:nowrap\">"
									+  (($('input:radio[name="rd"]:checked').val()==1) ? GetDateDiff(f.logonTime) : f.logonTime)
									+ "</td>"								
									/* + "<td style=\"width: 120px;\">";									
									if(f.downloadTotal>0){																		
									content+= ( (f.downloadFinished / f.downloadTotal) == 1 ? "已完成" : ( (Math.round((f.downloadFinished / 1024 / 1024)*100)/100) +"/"+ (Math.round((f.downloadTotal / 1024 / 1024)*100)/100) ) );
									}
									else{
									content+=(( (f.loopTaskUpdated==1 || f.loopTaskUpdated==0)  && (f.demandTaskUpdated==1 || f.demandTaskUpdated==0) && (f.pluginTaskUpdated==1 ||f.pluginTaskUpdated==0) ) ? "已完成" : "");
									}
									content+= "</td>"
									+ "<td style=\"width: 50px;\">"
									+ (( (f.loopTaskUpdated==1 || f.loopTaskUpdated==0)  && (f.demandTaskUpdated==1 || f.demandTaskUpdated==0) && (f.pluginTaskUpdated==1 || f.pluginTaskUpdated==0) && (f.subtitleUpdated==1 || f.subtitleUpdated==0) ) ? "<s:text name="synchronized"/>" : "未同步")
									+ "</td>"
									+"</td>"
									+ "<td style=\"width: 50px;\">"
									+ (( (f.kernelUpdated==1 || f.kernelUpdated==0)  && (f.appUpdated==1 || f.appUpdated==0) && (f.configUpdated==1 || f.configUpdated==0) ) ? "<s:text name="synchronized"/>" : "未同步")
									+ "</td>" */
									+ "<td style=\"width: 50px;\">"
									+ (f.onlineState == 0 ? "<img src=\"${theme}/skins/default/images/btn5.png\" height=\"16px\" width=\"16px\" />"
											: "<img src=\"${theme}/skins/default/images/btn1.png\" height=\"16px\" width=\"16px\" />")
									+ "</td>"
									+ "</tr>";
						});
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
		"terminalparam.searchKey" : (($("#search").val() != null && $("#search").val() != '' && $("#search").val() != undefined) ? parseInt($("#search").val(), 16) : $("#search").val()),
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

function GetDateDiff(startTime) {  
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
 

     var result="";
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
     
     return result;

}  
</script>