<link type="text/css" rel="stylesheet" href="${rc.contextPath}/js/validationEngine/validationEngine.jquery.css" />
<script type="text/javascript" src="${rc.contextPath}/js/validationEngine/jquery.validationEngine-zh_CN.js"></script> 
<script type="text/javascript" src="${rc.contextPath}/js/validationEngine/jquery.validationEngine.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var validateForm = $("form:first");
	validateForm.validationEngine();
});
</script>