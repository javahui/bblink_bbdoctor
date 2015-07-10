<!DOCTYPE html>
<html>
<head>
<#include "/base/head_meta.ftl"/>
<#import "/base/spring.ftl" as s/>
</head>
<body>
<div class="iframe_header">
    <ul class="iframe_nav">
        <li><a href="#">库存管理</a>&gt;</li> <li class="active">新增余货入库</li>
    </ul>
</div>
<form id="dataForm" action="save.do" method="post">
	<input type="hidden" name="stockInStatus" id="stockInStatus">
	<div class="iframe_content">
		<div class="p_box">
			<table class="s_table">
				<tr>
					<td class="s_label">表单填写人：<font color="red">*</font></td>
					<td>
						<input type="hidden" id="formUserId" name="formUserId" > 
						<input type="text" id="formUser" class="searchInput validate[required,maxSize[30]]" autocomplete="off"/>
					</td>
					<td class="s_label">填写日期：<font color="red">*</font></td>
					<td>
						<input type="text" id="formTime" name="formTime" class="W8 Wdate validate[required]" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
					</td>
				</tr>
				<tr>    
					<td class="s_label">入库人：<font color="red">*</font></td>
					<td>
						<input type="hidden" id="stockInUserId" name="stockInUserId" > 
						<input type="text" id="stockInUser" class="validate[required,maxSize[30]]"/>
					</td>
					<td class="s_label">入库日期：<font color="red">*</font></td>
					<td>
						<input type="text" name="stockInTime" class="W8 Wdate validate[required]" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
					</td>	
			   </tr>
			</table>
	</div>		

	<div class="iframe-content">
	<div class="p_box">
		<table class="p_table table_center">
			<tr>
	        	<th></th>
	        	<th width="25px">编号</th>
	        	<th width="">供应商</th>
	        	<th width="">商品名称</th>
	        	<th width="">型号</th>
	        	<th width="">数量</th>
	        	<th width="">退货人</th>
	        	<th width="">退货医院</th>
	        	<th width="">退货地址</th>
	        	<th width="">快递单号</th>
	        	<th width="">备注</th>
			</tr>
			<tr>
				<td width="25px"><a href="#" onclick="delTr(this)">-</a></td>
				<td width="25px" class="num">1</td>
				<td>
					<select name="detail[0].supplierId" style="width: 75px" onchange="selectSupplier(this)" class="validate[required]">
	                  	<option value="">请选择</option>
						<#list supplierList as item>
	                  		<option value="${item.supplierId}">${item.supplierName}</option>
	                	</#list>
                	</select>
				</td>
				<td> <select name="detail[0].goodsId" style="width: 120px" onchange="selectGoods(this)" class="validate[required]"></select> </td>
				<td> <select name="detail[0].goodsModelId" style="width: 150px" class="validate[required]"></select> </td>
				<td> <input name="detail[0].stockInAmount" style="width: 50px" type="text" class="validate[required,min[1],max[999999],custom[integer]]"/></td>
				<td> <input name="detail[0].returnPerson" style="width: 100px" type="text" class="validate[maxSize[30]]"/></td>
				<td> <input name="detail[0].returnHos" type="text" class="validate[required,maxSize[100]]"/></td>
            	<td> <input name="detail[0].returnAddr" type="text" class="validate[maxSize[100]]"/></td>
            	<td> <textarea name="detail[0].expressNo" class="validate[maxSize[500]]"></textarea> </td>
				<td> <textarea name="detail[0].stockInRemark" class="validate[maxSize[500]]"></textarea> </td>
			</tr>
			<tr>
				<td width="25px"><a href="#" class="trAdd">+</a></td>
				<td colspan="20"> </td>
			</tr>
		</table>
	</div>
	</div>
	
	<div class="operate mt10">
		<a class="btn btn_cc1" onclick="save()">保存</a>
		<a class="btn btn_cc1" onclick="saveIn()">保存并入库</a>
		<a class="btn btn_cc1" id="btnReturn" onclick="history.go(-1)">返回</a>
	</div>
</form>

<!-- 用以js动态新增一行详细子记录的html模板 -->
<div class="trInfo" style="display: none">
    <table>
        <tr>
        	<td width="25px"><a href="#" onclick="delTr(this)">-</a></td>
            <td width="25px" class="num">1</td>
            <td>
                <select name="detail[0].supplierId" style="width: 75px" onchange="selectSupplier(this)" class="validate[required]">
                    <option value="">请选择</option>
					<#list supplierList as item>
	                	<option value="${item.supplierId}">${item.supplierName}</option>
					</#list>
                </select>
            </td>
            <td> <select name="detail[0].goodsId" style="width: 120px" onchange="selectGoods(this)" class="validate[required]"></select> </td>
            <td> <select name="detail[0].goodsModelId" style="width: 150px" class="validate[required]"></select> </td>
            <td> <input name="detail[0].stockInAmount" style="width: 50px" type="text" class="validate[required,min[1],max[999999],custom[integer]]"/></td>
            <td> <input name="detail[0].returnPerson" style="width: 100px" style="width: 50px" type="text" class="validate[maxSize[30]]"/></td>
            <td> <input name="detail[0].returnHos" type="text" class="validate[required,maxSize[100]]"/></td>
            <td> <input name="detail[0].returnAddr" type="text" class="validate[maxSize[100]]"/></td>
            <td> <textarea name="detail[0].expressNo" class="validate[maxSize[500]]"></textarea> </td>
            <td> <textarea name="detail[0].stockInRemark" class="validate[maxSize[500]]"></textarea> </td>
        </tr>
    </table>
</div>

<#include "/base/validationEngine.ftl"/>
<script type="text/javascript" src="${rc.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${rc.contextPath}/js/jquery.jsonSuggest.min.js"></script>
<script>
$(document).ready(function(){
	var d = new Date();
	$("#formTime").val( d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate());
	$("#formUser").jsonSuggest({
		url:"${rc.contextPath}/erp/stockInBuy/getUser.do",
		maxResults: 10,
		minCharacters:1,
		onSelect:function(item){
			$("#formUserId").val(item.id);
		}
	});
	$("#stockInUser").jsonSuggest({
		url:"${rc.contextPath}/erp/stockInBuy/getUser.do",
		maxResults: 10,
		minCharacters:1,
		onSelect:function(item){
			$("#stockInUserId").val(item.id);
		}
	});
});

function selectSupplier(obj){
	var supplierId = $(obj).val();
	var goodsSelect=$(obj).parent().next().find("select");
	var modelSelect=$(obj).parent().next().next().find("select");
	$.get(
		"${rc.contextPath}/erp/stockInBuy/getGoodsBySupplierId.do",
		{supplierId: supplierId},
		function(data){
			goodsSelect.empty();
			modelSelect.empty();
			goodsSelect.append("<option value=''>请选择</option>"); 
			for(var i=0; i < data.length; i++){
				goodsSelect.append("<option value="+data[i].goodsId+">" + data[i].goodsName +"</option>"); 
			}
		}
	);
}
function selectGoods(obj){
	var goodsId = $(obj).val();
	var modelSelect=$(obj).parent().next().find("select");
	$.get(
		"${rc.contextPath}/erp/stockInBuy/getModelByGoodsId.do",
		{goodsId: goodsId},
		function(data){
			modelSelect.empty();
			modelSelect.append("<option value=''>请选择</option>"); 
			for(var i=0; i < data.length; i++){
				modelSelect.append("<option value="+data[i].modelId+">" + data[i].modelName +"</option>"); 
			}
		}
	);
}

function save(){
	if( $(".table_center tr").length == 2){
		alert("至少输入一条入库记录");
		return;
	}
	if(confirm('是否确认保存')){
		$("#stockInStatus").val("no");
		$("#dataForm").submit();
	}
}

function saveIn(){
	if( $(".table_center tr").length == 2){
		alert("至少输入一条入库记录");
		return;
	}
	if(confirm('是否确认保存并入库')){
		$("#stockInStatus").val("yes");
		$("#dataForm").submit();
	}
}

function delTr(obj){
	$(obj).parent().parent().remove();
}

function trInfo(){
	var tr = $(".trInfo"),
	trAdd = $(".trAdd");
	trAdd.on("click",function(){
		var i = trAdd.parent().parent().prev().find(".num").html();
		if(i == null){
			i=0;
		}
		i = parseInt(i);
		tr.find("select,input,textarea").each(function(){
			$(this).attr("name",$(this).attr("name").replace(/\[[0-9]+\]/g,"["+i+"]"));
		});
		tr.find(".num").html(i+1);
		var html = $(".trInfo tbody").html();
		trAdd.parent().parent().before(html);
    })
}
trInfo();
</script>
</body>
</html>