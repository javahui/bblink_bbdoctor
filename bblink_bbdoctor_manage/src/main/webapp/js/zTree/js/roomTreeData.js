var setting = {
		view: {
			addHoverDom: addHoverDom,
			removeHoverDom: removeHoverDom,
			selectedMulti: false,
			showLine: true,
			expandSpeed:"slow",
			dblClickExpand: false
		},
		edit: {
			enable: true,
			editNameSelectAll: true,
			showRemoveBtn: showRemoveBtn,
			removeTitle:"删除科室",
			showRenameBtn: true,
			renameTitle:"修改科室名称"
		},
		data: {
			simpleData: {
				enable: true,
				idKey:"id",
				pIdKey:"pId"
			}
		},
		callback: {
			 beforeClick:beforeClick,
			 beforeDrag: beforeDrag,
			 beforeEditName: beforeEditName,
			 beforeRemove: beforeRemove,
			 beforeRename: beforeRename,
			 onRemove: onRemove,
			 onRename: onRename
		}
	};

	var log, className = "dark";
	function beforeDrag(treeId, treeNodes) {
		return false;
	}
	function beforeEditName(treeId, treeNode) {
		className = (className === "dark" ? "":"dark");
		showLog("[ "+getTime()+" beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
		var zTree = $.fn.zTree.getZTreeObj("roomTree");
		zTree.selectNode(treeNode);
		return confirm("确定编辑该科室吗？");
	}
	function beforeRemove(treeId, treeNode) {
		
		if(confirm("确认删除科室--"+treeNode.name+"--吗？")){
			var contextPath = $("#contextPath").val();
			$.post(contextPath+"/tree/deleteRoom.do",{roomId:treeNode.id});
		}else{
			return false;
		}
	}
	function onRemove(e, treeId, treeNode) {
		//刷新树
	}
	//编辑
	function beforeRename(treeId, treeNode, newName, isCancel) {
		if (newName.length == 0) {
			alert("科室名称不能为空!");
			var zTree = $.fn.zTree.getZTreeObj("roomTree");
			zTree.cancelEditName(treeNode.name);
			return false;
		}
		var ePath=$("#contextPath").val();
		$.post(ePath+"/tree/updateRoom.do",{roomId:treeNode.id,roomName:newName});
	}
	function onRename(e, treeId, treeNode, isCancel) {
		//刷新树
	}
	function showRemoveBtn(treeId, treeNode) {
		return !treeNode.isParent;
	}
	function showRenameBtn(treeId, treeNode) {
		return !treeNode.isFirstNode;
	}
	function showLog(str) {
		if (!log) log = $("#log");
		log.append("<li class='"+className+"'>"+str+"</li>");
		if(log.children("li").length > 8) {
			log.get(0).removeChild(log.children("li")[0]);
		}
	}
	
	function getTime() {
		var now= new Date(),
		h=now.getHours(),
		m=now.getMinutes(),
		s=now.getSeconds(),
		ms=now.getMilliseconds();
		return (h+":"+m+":"+s+ " " +ms);
	}
	
	var newCount = 1;
	function addHoverDom(treeId, treeNode) {
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
			+ "' title='新增科室' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_"+treeNode.tId);
		if (btn) btn.bind("click", function(){
			//var roomName = prompt("请输入科室名称");
			var roomName = "";
			$.extend($.messager.defaults,{  
			    ok:"确定",  
			    cancel:"取消"  
			});
			$.messager.prompt('新增', '请输入科室名称', function(r){
				if (r){
					roomName = r;
					if(roomName==null){
						return;
					}
					else if(roomName==""){
						alert("科室名称不能为空！");
						return;
					}else{
						var cPath=$("#contextPath").val();
						var hosId=$("#hosId").val();
						var roomLevel = treeNode.level+1;
						var zTree =  $.fn.zTree.getZTreeObj("roomTree");
						$.post(cPath+"/tree/addRoomNode.do",
							{parentId:treeNode.id,hosId:hosId,roomLevel:roomLevel,roomName:roomName},
							function(data){
							if($.trim(data)!=null){
								zTree.addNodes(treeNode,{id:data.id,pId:data.pId,name:data.name},true);
							}
						});
					}
				}
			});
		});
	};
	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_"+treeNode.tId).unbind().remove();
	};
	
	function selectAll() {
		var zTree = $.fn.zTree.getZTreeObj("roomTree");
		zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
	};
	function beforeClick(treeId,treeNode){
			return true; 
	};
	var zNodes={};	
	
	$(document).ready(function(){
		var contextPath = $("#contextPath").val();
		var roomId = $("#roomId").val();
		$.ajax({
			url : contextPath+"/tree/viewRoomTree.do",
			type:"post",
			data:{rootId:roomId},
			dataType:"json",
			async:false,
			success:function(data){
                zNodes = data;
			}
		});
		$.fn.zTree.init($("#roomTree"), setting, zNodes);
		var treeObj = $.fn.zTree.getZTreeObj("roomTree"); 
		treeObj.expandAll(true); 
	});