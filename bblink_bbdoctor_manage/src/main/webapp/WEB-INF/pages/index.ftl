<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>贝联ERP管理系统</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link rel="stylesheet" href="${rc.contextPath}/css/ui-common.css">
<link rel="stylesheet" href="${rc.contextPath}/css/ui-components.css">
<link rel="stylesheet" href="${rc.contextPath}/css/ui-panel.css">
<style>
	.panel_aside a{color:#0D80FC; font-weight:700; font-size:14px;}
	.ul_oper_list li a{color:#333; font-weight:100;}
</style>
</head>
<body>
<!-- 顶部导航\\ -->
<div class="topbar">
	<img alt="logo" src="${rc.contextPath}/img/logo_new.png">
	<p>
		亲，欢迎您:<span>${userName}</span>
		[<a class="B" href="/bblink_sys/sys/user/intoChangePwd.do?userName=${userName}" target="iframeMain">修改密码</a>]　
		[<a class="B" href="javascript:void(0);" onclick="doLogout();">退出系统</a>]
	</p>
</div><!-- //顶部导航 -->

<!-- 边栏\\ -->
<div id="panel_aside" class="panel_aside">
	<span id="oper_aside" class="icon-arrow-left"></span>
    <span id="oper_set" class="icon-set"></span>
	<div class="aside_box">
		<li class="oper_item"><a target="iframeMain" ><span class="icon-tag"></span>库存管理</a>
            <ul class="ul_oper_list"> 
            	<li class="oper_item"><a target="iframeMain" href="/bblink_erp/supp/query.do"><span class="icon-tag"></span>供应商管理</a></li>
            	<li class="oper_item"><a target="iframeMain" href="/bblink_erp/goods/query.do"><span class="icon-tag"></span>商品管理</a></li>
            	<li class="oper_item"><a target="iframeMain" href="/bblink_erp/goodsModel/query.do"><span class="icon-tag"></span>商品型号管理</a></li>
            	<li class="oper_item"><a target="iframeMain" href="/bblink_erp/erp/stockInBuy/list.do"><span class="icon-tag"></span>采购入库</a></li>
            	<li class="oper_item"><a target="iframeMain" href="/bblink_erp/erp/stockInRemain/list.do"><span class="icon-tag"></span>余货入库</a></li>
            	<li class="oper_item"><a target="iframeMain" href="/bblink_erp/stockOut/list.do?page=0"><span class="icon-tag"></span>出库管理</a></li>
            	<li class="oper_item"><a target="iframeMain" href="/bblink_erp/stockReturn/list.do?page=0"><span class="icon-tag"></span>退货管理</a></li>
            	<li class="oper_item"><a target="iframeMain" href="/bblink_erp/erp/statistics/list.do"><span class="icon-tag"></span>库存统计</a></li>
			</ul>
		</li>
	</div>
</div><!-- //边栏 -->

<div id="panel_control" class="panel_control"></div>

<!-- 工作区\\ -->
<div id="panel_main" class="panel_main" align="center">
	<iframe id="iframeMain" name="iframeMain" src="${rc.contextPath}/welcome.do" frameborder="0" style="height:100%; background:#fff;"></iframe>
	<div class="scoll-mask"></div>
</div><!-- //工作区 -->

<!-- 底部\\ -->
<div class="footer"></div><!-- //底部 -->

<script src="${rc.contextPath}/js/jquery-1.8.3.min.js"></script>
<script src="${rc.contextPath}/js/panel-custom.js"></script>
<script>
function doLogout(){
	window.location.href = "http://login.bblink.cn/logout?service=http://login.bblink.cn/login?service=http://hos.bblink.cn";
}
</script>
</body>
</html>

