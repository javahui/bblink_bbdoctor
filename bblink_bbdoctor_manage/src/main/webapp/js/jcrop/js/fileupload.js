$(window).load(
		function() {
			var cutter = new jQuery.UtrialAvatarCutter();
			var objectId = $("#objectId").val();
			var objectType = $("#objectType").val();
			//var resultImg = $("#resultImg").attr("src");
			/**
			 * 选择图片
			 */
			$("#imageFile").uploadify({
				'uploader' : GLOBAL_CONTEXT+'/js/jcrop/js/uploadify.swf',
				'script' : GLOBAL_CONTEXT+'/hos/expert/uploadExpertImage.do?expertId='+objectId, // 默认用户ID为1
				'cancelImg' : GLOBAL_CONTEXT+'/js/jcrop/js/cancel.png',
				'queueID' : 'fileList', // 和存放队列的DIV的id一致
				'fileDataName' : 'file', // type为file的input的name值
				'auto' : false, // 是否自动开始,默认为不自动上传
				'multi' : false, // 是否支持多文件上传
				'buttonText' : '选择文件', // 按钮上的文字
				'simUploadLimit' : 1, // 一次同步上传的文件数目
				'sizeLimit' : 3145728, // 设置单个文件大小限制 3MB
				'queueSizeLimit' : 1, // 队列中同时存在的文件个数限制
				'fileDesc' : '支持格式:jpg/jpeg', // 如果配置了以下的'fileExt'属性，那么这个属性是必须的
				'fileExt' : '*.jpg;*.jpeg;',// 允许的格式
				'displayData' : 'percentage',// 进度条显示百分比
				/**
				 * 文件上传完成时触发 event:事件对象 queueID:文件队列中项目的唯一ID fileObj:一个含有文件信息的对象
				 * response:由后台上传脚本返回的文本值 date:一个含有关于上传和文件队列主要信息的对象
				 */
				onComplete : function(event, queueID, fileObj, response, data) {
					// 预览图片
					//$("#viewImg").show();
					//$("#imgTable").show();
					//$("#resultImg").removeAttr("src");
					$("#userimg").attr("src", response);
					$("#preview").attr("src", response);
				},
				onError : function(event, queueID, fileObj) {
					alert("文件:" + fileObj.name + "上传失败");
				},
				onCancel : function(event, queueID, fileObj) {

				}
			});

			// 载入图片时初始化jcrop
			$("#userimg").bind("load", function() {
				cutter.init();
			});

			$("#submit").bind("click",function() {
						var x = $("#x").val();
						var y = $("#y").val();
						var w = $("#w").val();
						var h = $("#h").val();
						var img = $("#userimg").attr("src");
						var index = countInstances(img, '/', 4);
						// 图片路劲
						img = img.substring(index);
						$.ajax({
							cache : false,
							type : 'post',
							url : GLOBAL_CONTEXT+'/hos/expert/saveExpertImage.do',
							data : 'x=' + x + '&y=' + y + '&w=' + w
									+ '&h=' + h + '&img=' + img,
							success : function(result) {
								parent.location.href=GLOBAL_CONTEXT+'/hos/expert/edit.do?expertId='+objectId;
								parent.$('.panel-tool-close').click();
								
								//window.location.href=GLOBAL_CONTEXT+'/hos/expert/edit.do?expertId='+objectId;
							}
						});
					});

			/**
			 * 点击上传按钮
			 */
			$("#uploadFile").click(function() {
				$("#imageFile").uploadifyUpload();
			});
			/**
			 * 取消上传
			 */
			$("#cancelUpload").click(function() {
				$("#imageFile").uploadifyClearQueue();
			});

			function countInstances(mainStr, subStr, index) {
				var count = 0;
				var offset = 0;
				do {
					offset = mainStr.indexOf(subStr, offset);
					if (offset != -1) {
						count++;
						offset += subStr.length;
					}
				} while (offset != -1 && count < index)
				return offset;
			}

		});
