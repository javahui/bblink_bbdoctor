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
			removeTitle:"删除菜单",
			showRenameBtn: true,
			renameTitle:"修改菜单名称"
		},
		data: {
			simpleData: {
				enable: true,
				idKey:"id",
				pIdKey:"pId"
			}
		},
		/*async: {  
	        enable: true,  
	        url:"${rc.contextPath }/sys/org/getSunOrgInfo.do",  
	        autoParam:["id"]
	    },*/
		callback: {
			// beforeClick:beforeClick,
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
		var zTree = $.fn.zTree.getZTreeObj("authorityTree");
		zTree.selectNode(treeNode);
		return confirm("确定编辑该菜单吗？");
	}
	function beforeRemove(treeId, treeNode) {
		
		if(confirm("确认删除菜单--"+treeNode.name+"--吗？")){
			var contextPath = $("#contextPath").val();
			$.post(contextPath+"/tree/deleteOrg.do?orgId="+treeNode.id);
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
			alert("菜单名称不能为空.");
			//setTimeout(function(){zTree.editName(treeNode)}, 10);
			var zTree = $.fn.zTree.getZTreeObj("authorityTree");
			zTree.cancelEditName(treeNode.name);
			return false;
		}
		var ePath=$("#contextPath").val();
		$.post(ePath+"/tree/updateOrg.do?orgId="+treeNode.id+"&orgName="+newName);
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
			+ "' title='新增菜单' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_"+treeNode.tId);
		if (btn) btn.bind("click", function(){
			var orgName = prompt("请输入菜单名称");
			if(orgName==null){
				return;
			}
			else if(orgName==""){
				alert("菜单名称不能为空！");
			}else{
				var cPath=$("#contextPath").val();
				var orgType=$("#orgType").val();
				var hosId=$("#hosId").val();
				var orgLevel = treeNode.level+1;
				var zTree =  $.fn.zTree.getZTreeObj("authorityTree");
				$.post(cPath+"/tree/addOrgNode.do?parentId="+treeNode.id+"&hosId="+hosId+"&orgLevel="+orgLevel+"&orgName="+orgName+"&orgType="+orgType,function(data){
					if($.trim(data)!=null){
						zTree.addNodes(treeNode,{id:data.id,pId:data.pId,name:data.name},true);
					}
					
				});
			}
		});
	};
	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_"+treeNode.tId).unbind().remove();
	};
	
	function selectAll() {
		var zTree = $.fn.zTree.getZTreeObj("authorityTree");
		zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
	};
	function beforeClick(treeId,treeNode){
		if(!treeNode.isParent){ 
			alert("!!!!!"); 
			return false; 
		}else{ 
			return true; 
		} 
	};
	var zNodes={};	
	
	$(document).ready(function(){
		var contextPath = $("#contextPath").val();
		var orgId = $("#orgId").val();
		$.ajax({
			url : contextPath+"/tree/viewOrgTree.do?rootId="+orgId,
			type:"post",
			dataType:"json",
			async:false,
			success:function(data){
                zNodes = data;
			}
		});
		$.fn.zTree.init($("#authorityTree"), setting, zNodes);
		var treeObj = $.fn.zTree.getZTreeObj("authorityTree"); 
		treeObj.expandAll(true); 
	});