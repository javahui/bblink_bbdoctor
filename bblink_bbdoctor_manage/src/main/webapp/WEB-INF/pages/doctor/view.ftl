<!DOCTYPE html>
<html>
<head>
<#include "/base/head_meta.ftl"/>
<#import "/base/spring.ftl" as s/>
</head>
<body>
<div class="iframe_header">
    <ul class="iframe_nav">
        <li><a href="#">诊后随访</a>&gt;</li> <li class="active">查看</li>
    </ul>
</div>
<form id="dataForm" action="update.do" method="post">
	<div class="iframe_content">
		<div class="p_box">
			<table class="s_table">
				<tr>
					<td class="s_label">表单填写人：<font color="red">*</font></td>
					<td>
						<input type="text"  value="${record.formUserName}" readonly="readonly"/>
					</td>
					<td class="s_label">填写日期：<font color="red">*</font></td>
					<td>
						<input type="text" name="formTime" class="W8 Wdate" value="${stockIn.formTime}" readonly="readonly"/>
					</td>
				</tr>
				<tr>    
					<td class="s_label">入库人：<font color="red">*</font></td>
					<td>
						<input type="text" value="${stockIn.stockInUserName}" readonly="readonly"/>
					</td>
					<td class="s_label">入库日期：<font color="red">*</font></td>
					<td>
						<input type="text" name="stockInTime" class="W8 Wdate"  value="${stockIn.stockInTime}" readonly="readonly"/>
					</td>	
			   </tr>
			</table>
	</div>		

	
	<div class="operate mt10">
		<a class="btn btn_cc1" id="btnReturn" onclick="history.go(-1)">返回</a>
	</div>
</form>
</body>
</html>