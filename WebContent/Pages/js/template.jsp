
<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">

function getDialogParam(){
	return {
        modal: true,
        resizable: false,
        draggable: true,
        width: 400,
        height: 280,
        zIndex: 19564,
    };
} 

function finished(result){
	if(result == 'ok'){
		querytemplates(remember);
	}
}

var leftstr = "";
var selectedtreeNode = null;
var currentPageFlag = 1;
var totalPageFlag = 1;
var pageSize = 10;


$(function() {
	$.fn.zTree.init($("#treetemplate"), setting,null);
	//loadSys();
	loadLeftRoot(false); 
	
	if(issystem){
		$("#terminaldiv").hide();
		$("#todoterminaldiv").show();
	}
	else{
		$("#todoterminaldiv").hide();
		$("#terminaldiv").show();
	}
	
	//querytemplates();	
});


function loadRoot(){	
	loadLeftRoot(true);
}

function loadLeftRoot(showList){
	currentPageFlag=1;
	var treeObj = $.fn.zTree.getZTreeObj("treetemplate");
	var nodes = treeObj.getNodes();
	if (nodes.length>0) {
		treeObj.selectNode({id:-1});
	}
	leftstr="";

	if (showList) {
		querytemplates();
	}
}

function loadSys(){
	querysystemtemplates();
}

var setting = {
	async : {
		enable : true,
		url : "templet!list",
		autoParam : ["id", "name=n", "level=lv"],
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
		enable: false,
		showRemoveBtn: false,
		showRenameBtn: false
	},
	view : {		
		selectedMulti : false
	},
	callback : {
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
	var treeObj  = $.fn.zTree.getZTreeObj("treetemplate");
	
	leftstr = treeObj.getSelectedNodes()[0]==undefined?'':treeObj.getSelectedNodes()[0].name;
	
	selectedtreeNode = treeNode;
	currentPageFlag=1;
	querytemplates();
}

function onClick(e, treeId, treeNode) {
	var treeObj  = $.fn.zTree.getZTreeObj("treetemplate");
	leftstr = treeObj.getSelectedNodes()[0]==undefined?'':treeObj.getSelectedNodes()[0].name;
	//alert(leftstr);
	selectedtreeNode = treeNode;
	currentPageFlag=1;
	querytemplates();
	
	var zTree = $.fn.zTree.getZTreeObj("treetemplate");
	zTree.expandNode(treeNode);
}

function beforeDrag(treeId, treeNodes) {
	return false;
}
function querytemplates() {
	
	$("#todoterminaldiv").hide();
	$("#terminaldiv").show();
	
	var param = {
			"leftstr" : leftstr
		};	
	
	var url =  encodeURI(encodeURI("templet!templets"));
	
	$p = page(url,param,showlistfunc);
	$p.ajax();
	
	$("#first").bind("click",$p.first);
	$("#previous").bind("click",$p.previous);
	$("#next").bind("click",$p.next);
	$("#last").bind("click",$p.last);
	
	return false;
}


var issystem = false;
function querysystemtemplates() {
	
	//$(".right1 div[id='ee']").empty();
	
	issystem = true;
	$("#terminaldiv").hide();
	$("#todoterminaldiv").show();
	  
	var param = {
			"leftstr" : ''
		};	
	
	var url =  encodeURI(encodeURI("templet!systemtemplets"));
	
 	$p1 = page(url,param,showsystemlistfunc);
 	$p1.totalDataID = "totalData1";
 	$p1.currentWithTotalID= "currentWithTotal1";
 	
	$("#first1").bind("click",$p1.first);
	$("#previous1").bind("click",$p1.previous);
	$("#next1").bind("click",$p1.next);
	$("#last1").bind("click",$p1.last);
	//$p1.ajax();
 	
	$p1.ajax();
	
	return false; 
	
}

function removete(){
	if($("#mytab tbody input:checked").length==0){
		alert("<s:text name="no_select_template" />");
		return;
	}
	confirm("<s:text name="isdelete"/>",function(){
		var param = {"ts":[]};
		$("#mytab tbody input:checked").each(function(i,f){
			param.ts.push(f.value);
		});
		ajaxCallback("templet!remove",$.param(param,true),function(result){
			if(result.success){
				$.fn.zTree.init($("#treetemplate"), setting,null);
				loadRoot();
			}
		});
		
	});
}

function filter(treeId, parentNode, childNodes) {
	if (!childNodes)
		return null;
	for (var i = 0, l = childNodes.length; i < l; i++) {
		childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
	}
	return childNodes;
}

function  changepagesize(){
	currentPageFlag = 1;
	changepagesize( $("select[name=pagesize]").val());
}

/* function removeNode() {
	if(selectedId==0||selectedId==undefined){
		alert(langMap.Map.deleteRootError);
		return;
	}
	if (confirm(getRemoveNode()+ selectedtreeNode.name)) {
		ajaxCallback("filecate!remove", 
					{"cate.categoryId" : selectedtreeNode.id},
					removedNode);
	}
} */






function showlistfunc(result){
	try {
		var content = "";	    
		$.each(result.data, function(i, f) {
			
			var checked = false;
			if (remember) {
				for (var i = 0; i < remember.length; i++) {
					if (remember[i] == f.tempId) {
						checked = true;
						break;
					}
				}
			}
			
			content += "<tr>"
						+  "<td style=\"width:20px;\"><input "+ (checked? " checked ":" ") + " type=\"checkbox\" class=\"tempId\" name=\"selectfile\" value="+f.tempId+" /></td>"
						+  "<td style=\"width:155px;overflow: hidden;text-overflow: ellipsis;\" title=\""+f.category+"\">"+f.category+"</td>"
						+  "<td style=\"width:185px;overflow: hidden;text-overflow: ellipsis;\" title=\""+f.tempName+"\">"+f.tempName+"</td>"
						+  "<td style=\"width:185px;overflow: hidden;text-overflow: ellipsis;\" title=\""+f.tempDesc+"\">"+f.tempDesc+"</td>"
						+  "<td style=\"width:120px;overflow: hidden;text-overflow: ellipsis;\" title=\""+f.width+"\">"+f.width+"x"+f.height+"</td>"

						+ " <td style=\"width: 74px;\">"
						+ " <a class=\"blue1\" onclick=\"showContent('"+f.tempId+"','"+f.tempName+"','"+f.mainRect+"')\" href=\"#\"><s:text name="edit" /></a>"
						+ " </td>"
					+  " </tr>";
				        
		});
		
		$("#mytab").html(content);

		$('.shujTb > tbody >tr:odd').addClass('alt');
		ipubs.models.jscroll_ee();
	} catch (e) {
		alert(e.message);
	}
}

function showsystemlistfunc(result){
	try {
		var content = "";	    
		 $.each(result.data, function(i, f) {
			
			content += "<tr>"
				+  "<td style=\"width:185px;overflow: hidden;text-overflow: ellipsis;\" title=\""+f.category+"\">"+f.category+"</td>"
				+  "<td style=\"width:185px;overflow: hidden;text-overflow: ellipsis;\" title=\""+f.tempName+"\">"+f.tempName+"</td>"
				+  "<td style=\"width:185px;overflow: hidden;text-overflow: ellipsis;\" title=\""+f.tempDesc+"\">"+f.tempDesc+"</td>"
				+  "<td style=\"width:120px;overflow: hidden;text-overflow: ellipsis;\" title=\""+f.width+"\">"+f.width+"x"+f.height+"</td>"					 						
						
				+ " <td style=\"width: 74px;\">"
				+ " <a class=\"blue1\" onclick=\"view('"+f.tempId+"','"+f.tempName+"','"+f.width+"','"+f.height+"')\" href=\"#\" style=\"white-space: nowrap;\"><s:text name="preview" /></a>"
				+ " </td>"
						
				+  " </tr>";
		  }); 
		  
		$("#mytodotab").html(content);

		$('.shujTb > tbody >tr:odd').addClass('alt');
		ipubs.models.jscroll_ee2();
	} catch (e) {
		alert(e.message);
	}
}

function gotoPageByIndex(){
	var pageIndex = $("input[name=pageIndex]").val();
	if(isNaN(pageIndex)){
		alert(pageIndexNotInteger());
	}
	if(pageIndex==undefined||pageIndex.trim()==""||pageIndex==0){
		pageIndex=1;
	}
	if(pageIndex>totalPageFlag){
		pageIndex=totalPageFlag;
	}
	else if(pageIndex<1){
		pageIndex=1;
	}
	gotoPage(pageIndex);
	
}

function setTotalFlag(flag){
	totalPageFlag = flag;
	$("#pageIndex").html(generic(totalPageFlag));
}
function generic(index){
	var page_index_result = "";
	for(var i=1;i<=index;i++){
		page_index_result +="<a href='javascript:gotoPage("+i+")'>"+i+"</a>&nbsp;";
	}
	return page_index_result;
}


function selectedAll(){
	$s.selectedAll($("#mytab > tbody input[type=checkbox]"));
}
function cancel(){
	$s.cancel($("#mytab > tbody input[type=checkbox]"));
}

function deselect(){
	$s.deSelected($("#mytab > tbody input[type=checkbox]"));
}
	
var $s = {
	selectedAll:function(object){object.attr("checked",true);},
	cancel:function(object){object.attr("checked",false);},
	deSelected:function(object)
		{object.each(function(i,o){o.checked=!o.checked;});
	}
};

function showfile(result) {
	alert(result.data);
}


function gotoPage(index){
	var treeObj  = $.fn.zTree.getZTreeObj("treetemplate");
	
	currentPageFlag = index;
		
	ajaxCallback("templet!templets",{"leftstr" : treeObj.getSelectedNodes()[0]==undefined?'':treeObj.getSelectedNodes()[0].name},showlistfunc);
}


function first(){
	gotoPage(1);
}
function previous(){
	gotoPage(currentPageFlag-1<=1?1:currentPageFlag-1);
}
function next(){
	gotoPage(currentPageFlag+1>=totalPageFlag?totalPageFlag:currentPageFlag+1);
}
function last(){
	gotoPage(totalPageFlag);
}
</script>
