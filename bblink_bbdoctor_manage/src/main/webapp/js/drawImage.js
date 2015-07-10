
var flag=false;
function DrawImage(ImgD){
   var image=new Image();
   image.src=ImgD.src;
   if(image.width>0 && image.height>0){
    flag=true;
    if(image.width/image.height>= 200/160){
	     if(image.width>200){  
		     ImgD.width=200;
		     ImgD.height=(image.height*200)/image.width;
	     }else{
		     ImgD.width=image.width;  
		     ImgD.height=image.height;
	     }
     }
    else{
	     if(image.height>160){  
		     ImgD.height=160;
		     ImgD.width=(image.width*160)/image.height;     
	     }else{
		     ImgD.width=image.width;  
		     ImgD.height=image.height;
	     }
     }
    }
} 