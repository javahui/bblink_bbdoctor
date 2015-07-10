function getPhotoSizeAndType(obj){
    var photoExt=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();//获得文件后缀名
    var re_text = /\jpg|\bmp|\jpeg|\png|\gif/i; 
    if(photoExt.search(re_text) == -1){
        alert("上传图片格式错误");
        obj.form.reset();
        return false;
    }
    var fileSize = 0;
    var isIE = /msie/i.test(navigator.userAgent) && !window.opera;            
    if (isIE && !obj.files) {          
         var filePath = obj.value;            
         var fileSystem = new ActiveXObject("Scripting.FileSystemObject");   
         var file = fileSystem.GetFile (filePath);               
         fileSize = file.Size;         
    }else {  
         fileSize = obj.files[0].size;     
    } 
    fileSize=Math.round(fileSize/1024*100)/100; //单位为KB
    if(fileSize>=10){
        alert("照片最大尺寸为10KB，请重新上传!");
        return false;
    }
}