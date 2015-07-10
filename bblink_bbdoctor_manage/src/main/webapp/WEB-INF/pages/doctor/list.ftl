<!DOCTYPE html>
<html>
<head>
<#include "/base/head_meta.ftl"/>
<#import "/base/spring.ftl" as s/>
<script type="text/javascript">
$(document).ready(function(){
	//绑定复选框(全选、取消全选)事件
	$("#checkallornot").click(function(){
		var checked = this.checked;
		 $(":checkbox[disabled!='disabled']").each(function(){ 
			 $(this).attr("checked",checked);
		 });
	});
});

//用表单的方式提交选中的recordIds
function submitCheckedRecordIds(actionUrl){
	var isChecked = false;
	$("input[name='ids']").each(function(){
		if (this.checked){
			isChecked = true;
		}
	});
	if (isChecked == false) {
		alert("未选择任何记录");
		return;
	}
	var form = $("#tableForm");
	form.attr('action', actionUrl);
	form.submit();
}
</script>
</head>
<body>
<div class="iframe_header">
    <ul class="iframe_nav">
        <li><a href="#">诊后随访</a>&gt;</li> <li class="active">医生信息管理</li>
    </ul>
</div>

<div class="iframe_search">
	<form method="get" action="list.do" id="searchForm">
		<table class="s_table">
			<tr>
				<td class="s_label">真实姓名：</td>
                <td>	
                	<input type="text" name="realName" value="${paramMap.realName}"/>
                 </td>
				<td class="s_label">加入日期：</td>
				<td>
					<input type="text" name="startTime" class="W8 Wdate" style="width: 100px"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${paramMap.startTime}" />-
                   	<input type="text" name="endTime" class="W8 Wdate" style="width: 100px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${paramMap.endTime}" />
				</td>
			</tr>

			<tr>
				<td class="s_label operate" style="width:100px;" colspan="5"> <a class="btn btn_cc1" onclick="$('#searchForm').submit()">查询</a></td>
		        <td class="s_label operate" style="width:100px;"> <a class="btn btn_cc1" href="add.do">新增</a></td>
		        <td class="s_label operate" style="width:100px;"> <a class="btn btn_cc1" href="#" onclick="submitCheckedRecordIds('xls.do')">报表导出</a></td>
			</tr>
        </table>	
	</form>
</div>

<!-- 主要内容显示区域 -->
<div class="iframe-content">
    <#if pageParam?? && pageParam.items?? &&  pageParam.items?size &gt; 0>
	<div class="p_box">
		<form id="tableForm">
		<table class="p_table table_center">
			<tr>
	        	<th width="10px"><input id="checkallornot" name="checkallornot"  type="checkbox"/></th>
	        	<th width="15px">ID</th>
	        	<th>姓名</th>
	        	<th>加入日期</th>
	        	<th>操作</th>
			</tr>
			<#list pageParam.items as item>
			<tr>
				<td><input type="checkbox" name="ids" value="${item.id}"></td>
				<td>${item.id}</td>
				<td>${item.realName}</td>
				<td>${item.createTime}</td>
				<td>
					 <a href="view.do?id=${item.id}">查看</a>
					 <#if item.stockInStatus == "no">
						 <a href="edit.do?id=${item.id}">修改</a>
						 <a href="delete.do?id=${item.id}" onclick="return confirm('确定删除?')">删除</a> 
					</#if>
					 <a href="xls.do?ids=${item.id}">导出</a> 
				</td>
			</tr>
			</#list>
		</table>
		</form>
		<div class="paging">${pageParam.pagination(11)}}</div>
		
	</div>
	<#else>
		<div class="no_data mt20"><i class="icon-warn32"></i>暂无相关数据，重新输入条件查询！</div>
    </#if>
</div>
<script type="text/javascript" src="${rc.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
</body>
</html>