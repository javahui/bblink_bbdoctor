/**
 * dataGrid格式化日期
 * @param val
 * @param row
 * @return
 */
function formatterdate(val, row) {
	if(null!=val && ""!=$.trim(val)){
	    var date = new Date(val);
	    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()+' '+date.getHours()+':'+date.getMinutes();
	}
	return "";
}

/**
 * dataGrid格式化日期
 * @param val
 * @param row
 * @return
 */
function formatterdate2(val, row) {
	if(null!=val && ""!=$.trim(val)){
	    var date = new Date(val);
	    return date.getFullYear() + '-' + (date.getMonth() + 1);
	}
	return "";
}
/**
 * 弹出页面
 * @param iframeId 
 * @param winId div id
 * @param url
 * @param title
 * @return
 */
function popWin(iframeId,winId,url,title){
	$("#"+iframeId).attr("src",url);
	$("#"+winId).dialog({
		'title':title,
		'fitColumns':true,	//适应父容器的大小
		'modal':true, 		//是否是模式对话框 
		'resizable':true		//可调整大小
	});
	$("#"+winId).dialog('open'); 
}

/**
 * 关闭弹出页面
 * @param winId
 * @return
 */
function popWinClose(winId)
{
	$("#"+winId).dialog('close');
}
