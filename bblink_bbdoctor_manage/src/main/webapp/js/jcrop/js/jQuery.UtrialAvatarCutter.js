jQuery.UtrialAvatarCutter = function() {

	var api = null;

	var sel = this;

	this.reload = function(img_url) {
		sel.init();
	};

	this.init = function() {
		if (api != null) {
			// 重新加载时移除旧的jcrop
			api.destroy();
		}
		api = $.Jcrop('#userimg', {
			allowSelect:true,
			allowMove:true,
			allowResize:true,
			cornerHandles:true,
			sideHandles:true,
			getScaleFactor:[1,1.4],
			dragEdges : true,
			aspectRatio : 0.72,// 裁剪框1:1
			setSelect : [ 20, 20, 100, 139],
			onSelect : showPreview,
			onChange : showPreview
		});

	};

	var showPreview = function(coords) {
		var rx = 100 / coords.w;
		var ry = 139 / coords.h;
		var ow = $("#userimg").width();
		var oh = $("#userimg").height();
		$('#preview').css({
			width : Math.round(rx * ow) + 'px',
			height : Math.round(ry * oh) + 'px',
			marginLeft : '-' + Math.round(rx * coords.x) + 'px',
			marginTop : '-' + Math.round(ry * coords.y) + 'px'
		});
		$("#w").attr("value", Math.round(coords.w));
		$("#h").attr("value", Math.round(coords.h));
		$("#x").attr("value", Math.round(coords.x));
		$("#y").attr("value", Math.round(coords.y));

	};

};