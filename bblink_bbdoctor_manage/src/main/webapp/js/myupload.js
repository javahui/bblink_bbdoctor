	     $(document).on("click", "#selectipconfirm", function(){
		      if($("input[class='selectOneB']:checked").length==0){
				elementShowPopover($("li[for^='batch-upload']"),"提示","您没有选择服务器",1000);
				return false;
			   }
		        var value='';
		        var iparr = new Array();
		        var oldvalue = $("#serverip").val();
		         $("input[type='checkbox']:checked").each(function(){
				    value += $(this).val()+'|';
			      });
	                value = value.replace(/(^\|*)|(\|*$)/g, "");
					value = dealipshow(value,oldvalue);//去重复
					var iphtml = '|';
					iparr = value.split('|');
				    for(i in iparr){
					 iphtml += '<span class="help-inline"><label class="control-label">'+iparr[i]+'</label></span>';
					}
					$("#showselectip").html('').append(iphtml);
					$("#serverip").attr('value',value);
				    var _content = "";
				    var  type= $("#executeWay").val();
			          $.ajax({
					  type: "POST",
					  url: "${cp}/batch/write",
					  data:"host="+iparr+"&type="+type,
					  async: false,
					  success: function(data){
						_content = data;
					 }
	                });
		           //隐藏层	
		            $("a.btn").popover('hide');
        
	       });
	    
	    //删除已经选中的ip 用于删除
	    //<span class="help-inline"><img src="${cp}/images/erase.png" class="delselectip ml5 pointer"/></span>
	     //$(document).on("click", ".delselectip", function(){
	     	   //alert($(this).parent().html());
	         	//var value = $(this).parent().find(".control-label").html();
				//var ipvalue = $("#serverip").val();
				//ipvalue = dealipshow(value,ipvalue);
				//$("#serverip").attr('value',ipvalue);
				//$(this).parent().remove();
			//});
			
	  //显示|删除 去重复
      function dealipshow(ipvalue,value){
			if(ipvalue == ''){
			   return value ;
			}
			var iparr = ipvalue.split('|');
			for(i in iparr){
			if(value == iparr[i]){
			   delete iparr[i];
			  }
			}
			var newipvalue ='';
			for(n in iparr){
			newipvalue +=iparr[n]+'|';
			}
			newipvalue = newipvalue.replace('||','|');
			return newipvalue.replace(/(^\|*)|(\|*$)/g, "");
        } 
	  
	     //全选
		 $(document).on("click", "#ipall", function(e){
		        $(".selectOneB").attr('checked','checked');
		        if (e && e.stopPropagation) { 
			        e.stopPropagation();  
			    }else{  
			        window.event.cancelBubble = true;  
			   }  
		  });
		   //全不选
		  $(document).on("click", "#ipcancel", function(e){
				$(".selectOneB").attr('checked',false);
				if (e && e.stopPropagation) { 
			        e.stopPropagation();  
			    }else{  
			        window.event.cancelBubble = true;  
			   }  
		  });
        //弹出层
	    $("a.btn").popover({
		placement: "right",
		html: true,
		title: "选择服务器", 
		content: function(){
			var _content = "";
			$.ajax({
				type: "GET",
				url: "${cp}/batch/showServer",
				async: false,
				success: function(data){
					_content = data;
				}
			});
			return _content;
		    
		}
	
	});
	$(function(){
	    //文件上传
		$('#fileupload').fileupload({
			dataType: 'json',
	        url: '${cp}/batch/upload/save',
	        progressall: function (e, data) {
		        var progress = parseInt(data.loaded / data.total * 100, 10);
		        $('#progress .bar').css(
		            'width',
		            progress + '%'
		        );
		    },
	        done:function(e, data){
	              //回调函数 返回json 对象 >>解析
	             $.each(data.files, function(i, item) {
	                  var fileName=item.name;
	                  var suffix=fileName.substring(fileName.lastIndexOf(".")+1)
                      $("#srcPath").val(fileName);
                      $("#suffix").val(suffix);
                });
		    }  
	    });
	     //表单提交
	     $("#up-batch-form").validate({
			rules:{
			    srcPath:{
	    			required: true,
	    		},
	    		operateType:{
	    			required: true,
	    		},
			    executeWay:{
	    			required: true,
	    		},
	    		executeCycle:{
	    			required: true,
	    		},
	    		command:{
	    			required: true,
	    			minlength: 1,  
				    maxlength:500
	    		},
	    		state:{
	    			required: true,
	    		},
	    		createTime:{
	    			required: true,
	    		}
	    	},
	    	submitHandler:function(form){
	    		navTabSubmit(form);
			}
		})
       //获取下拉列表
       $("#executeWay").change(function(){
           var way=$("#executeWay").val();
           if(way==1){
              $("#cycle").css('display','none'); 
              $("#taskByDay").css('display','none'); 
              $("#taskByWeek").css('display','none');
              $("#taskByMonth").css('display','none'); 
           }else{
              $("#cycle").css('display','block'); 
         
           }
       });		
		
		//自定义时间调度任务
       $("#executeCycle").change(function(){
           var way=$("#executeCycle").val();
           if(way==1){
              $("#taskByDay").css('display','block'); 
           }else{
              $("#taskByDay").css('display','none'); 
           }
           if(way==2){
              $("#taskByWeek").css('display','block'); 
           }else{
              $("#taskByWeek").css('display','none'); 
           }
           if(way==3){
              $("#taskByMonth").css('display','block'); 
           }else{
              $("#taskByMonth").css('display','none'); 
           }
       });	
	    
	});
