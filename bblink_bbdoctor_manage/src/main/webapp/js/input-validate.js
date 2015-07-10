//使用此方法验证所有text textarea 是否含有非法字符
$(document).ready(function(){
   $("input[type='text']").each(function(i){
         $(this).keyup(function() { checkIllegalChar($(this)); });
   });
   $("textarea").each(function(i){
        $(this).keyup(function() { checkIllegalChar($(this)); });
    });
});

function checkIllegalChar(obj){
    var value = obj.val();
    if(!checkChar(value)){
         obj.val(value.substring(0,value.length-1));
         obj.focus();
     }
}

//检查输入中的非法字符
function checkChar(InString) {
    var RefString = "<";
    var RefString2 = "%";
    var RefString3 = "\"";
    var RefString4 = ">";
    var RefString5 = "~";
    var RefString6 = "&";
  //  var RefString7 = ".";
    var RefString8 = "'";
    for (var Count = 0; Count < InString.length; Count++) {
       TempChar = InString.substring(Count, Count + 1);
        if ((RefString.indexOf(TempChar, 0) == 0) || (RefString2.indexOf(TempChar, 0) == 0) || (RefString3.indexOf(TempChar, 0) == 0) || (RefString4.indexOf(TempChar, 0) == 0) || (RefString5.indexOf(TempChar, 0) == 0) || (RefString6.indexOf(TempChar, 0) == 0) || (RefString8.indexOf(TempChar, 0) == 0)) {
             alert("您的输入中含有非法字符\"<\",\"\"\",\"%\",\"> \",\"~\",\"&\",\"?\",\"'\",请重新输入!");
             return (false);
        }
    }
    return (true);
} 