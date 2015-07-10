/**
 *
 *	Plugin: Jquery.webox
 *	Developer: Blank
 *	Version: 1.0 Beta
 *	Update: 2012.07.08
 *
**/
/**
 *   @param  iframeID 		iframe的id或者iframeElement[Doc Object]
 *   @param  iframeWrapper	用于包装iframe的元素
 */

function scrollIframeForIOS(iframe, iframeWrapper)
{
    if(!navigator.userAgent.match(/iPad|iPhone/i)) return false;

    var touchY = 0,
        touchX = 0;

    iframe = typeof(iframe)=="string" ? document.getElementById(iframe) : iframe;

    iframe.onload = function(){

        var ifrWin = iframe.contentWindow,
            ifrDoc = ifrWin.document;

        // iframe的上一级节点
        iframeWrapper = iframeWrapper||ifrWin.frameElement.parentNode;

        // 记录手指按下的位置
        ifrDoc.body.addEventListener("touchstart", function( event ){
            touchX = event.targetTouches[0].pageX;
            touchY = event.targetTouches[0].pageY;
        });

        ifrDoc.body.addEventListener("touchmove", function( event ){
            e.preventDefault(); // 阻止整个页面拖动
            iframeWrapper.scrollLeft += (touchX - event.targetTouches[0].pageX);
            iframeWrapper.scrollTop  += (touchY - event.targetTouches[0].pageY);
        });
    }

    return true;
};


$.extend({
	webox:function(option){
		var _x,_y,m,allscreen=false;
		if(!option){
			alert('options can\'t be empty');
			return;
		};
		if(!option['html']&&!option['iframe']){
			alert('html attribute and iframe attribute can\'t be both empty');
			return;
		};
		option['parent']='webox';
		option['locked']='locked';
		$(document).ready(function(e){
			$('.webox').remove();
			$('.background').remove();
			//var width=option['width']?option['width']:0;
			//var height=option['height']?option['height']:0;


            $('body').append('<div class="webox" id="iframeBox" style=" -webkit-overflow-scrolling:touch; overflow:auto;width:100%;height:100px;display:none; background: #666"><h1 id="locked" onselectstart="return false;"><a class="span" href="javascript:void(0);"></a></h1>'+(option['iframe']?'<iframe class="w_iframe" src="'+option['iframe']+'" frameborder="0" width="100%" height= "100%" scrolling="yes" allowtransparency="true" style="border:none;overflow-x:hidden;"></iframe>':option['html']?option['html']:'')+'</div>');


            var winHeight = $(window).height();
            var headHeight = $("#z_head").height();
            $(".w_iframe").height(winHeight - headHeight);
            $(".webox").height(winHeight - headHeight);
            $(".webox").css({'top': headHeight});
            $(".w_iframe").css({'top': headHeight});





            if(navigator.userAgent.indexOf('MSIE 7')>0||navigator.userAgent.indexOf('MSIE 8')>0){
				$('.webox').css({'filter':'progid:DXImageTransform.Microsoft.gradient(startColorstr=#55000000,endColorstr=#55000000)'});
			}
			$('.webox').css({display:'block'});
			$("#z_head").click(function(){
				$('.webox').css({display:'none'});
			});
			})
	}
});


