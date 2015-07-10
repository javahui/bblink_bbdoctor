$(window).load(
		function() {
			var objectId = $("#objectId").val();
			/**
			 * 选择图片
			 */
			$("#attachmentFile").uploadify({
				'uploader' : GLOBAL_CONTEXT+'/js/jcrop/js/uploadify.swf?random='+(new Date()).getTime(),
				'script' : GLOBAL_CONTEXT+'/hos/contract/uploadAttachment.do?contractId='+objectId, // 默认用户ID为1
				'cancelImg' : GLOBAL_CONTEXT+'/js/jcrop/js/cancel.png',
				'queueID' : 'fileList', // 和存放队列的DIV的id一致
				'fileDataName' : 'attachmentFile', // type为file的input的name值
				'auto' : false, // 是否自动开始,默认为不自动上传
				'multi' : true, // 是否支持多文件上传
				'buttonText' : '选择文件', // 按钮上的文字
				'simUploadLimit' : 5, // 一次同步上传的文件数目
				'sizeLimit' : 10485760, // 设置单个文件大小限制 3MB
				'queueSizeLimit' : 5, // 队列中同时存在的文件个数限制
				'fileDesc' : '支持格式:doc/docx/txt/pdf', // 如果配置了以下的'fileExt'属性，那么这个属性是必须的
				'fileExt' : '*.doc;*.docx;*.txt;*.pdf',// 允许的格式
				'displayData' : 'percentage',// 进度条显示百分比
				/**
				 * 文件上传完成时触发 event:事件对象 queueID:文件队列中项目的唯一ID fileObj:一个含有文件信息的对象
				 * response:由后台上传脚本返回的文本值 date:一个含有关于上传和文件队列主要信息的对象
				 */
				onComplete : function(event, queueID, fileObj, response, data) {
					$("#resultList").append('<li id=data-'+response+'>'+ fileObj.name+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;上传成功<input type="button" onclick="deleteFile('+response+')" value="删除"/></li>');
				},
				onError : function(event, queueID, fileObj) {
					alert("文件:" + fileObj.name + "上传失败");
				},
				onCancel : function(event, queueID, fileObj) {

				}
				
			});
			/**
			 * 点击上传按钮
			 */
			
			$("#uploadFile").click(function() {
				$("#attachmentFile").uploadifyUpload();
			});
			/**
			 * 取消上传
			 */
			$("#cancelUpload").click(function() {
				$("#attachmentFile").uploadifyClearQueue();
			});
			
			
			
		});
		function deleteFile(id){
			if(confirm("您确认要删除吗?")){
				var url =GLOBAL_CONTEXT+'/hos/contract/deleteAttachment.do';
					$.ajax({
						url : url,
						type:"post",
						data:{attachmentId:id},
						dataType:"json",
						success:function(data){
							$("#data-"+id).remove();
						}
					});
				}
		}

