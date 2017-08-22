<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../include/resource.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<!-- common -->
	<link href="<s:property value="themePath" />css/style.css" rel="stylesheet">
    <link href="<s:property value="themePath" />css/style-responsive.css" rel="stylesheet"> 
	<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />style.css"/>
	<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />validationEngine.jquery.css"/>
	<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />loading.css"/>
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />window.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />function.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.validationEngine-lang.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.validationEngine.min.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />loading.js"></script>
	<style>
		.require{color:#FF0000;padding-right:0 2px;}
		select option {height: 32px;line-height: 32px;text-indent: 10px;}
	</style>
<script type="text/javascript">
$(document).ready(function(e) {
	$('#form').validationEngine('attach', { 
	    promptPosition: 'centerRight', 
	    scroll: false
	  });	  
	$('#form').bind('jqv.form.result', function(event, errorFound){process(errorFound)});
});

function validate(){
	return true;
}

function clearForm() {
	window.location = '<s:url action="updatePage" />?model=${playerModelInfo.model}';
}

function process(errorFound){
    if(!errorFound) {
    	openConfirmWindow('提示信息','是否确认修改 ？','确定', 'formSubmit()','closeConfirmWindow()','取消');
    }
}

function formSubmit(){
	LoadingPic.FullScreenShow();
	document.form.submit();
}

</script>
</head>
<body>
<%@ include file="../../../include/navigation.jsp" %>
<form  method="post" name="form" id="form" action="<s:url action="update"/>" onsubmit="return false;">
<s:token name="submittedToken"/>
    <div class="formbody">
    <div class="formtitle"><span>修改机型适配播放模式信息</span></div>
	    <ul class="forminfo">
	    <li>
	    	<label style="width:120px;">机型</label>
	    	<input name="playerModelInfo.model" id="playerModelInfo.model" type="text" readonly="readonly" class="dfinput" value="${playerModelInfo.model}"/>
	    </li>
	    <li>
			<label style="width:120px;">播放模式</label>
			<select name="playerModelInfo.dimension" id="playerModelInfo.dimension" class="dayinput form-control" style="width:150px;">
				<option value="2" <c:if test = "${playerModelInfo.dimension == '2'}">selected="selected"</c:if>>2D</option>
				<option value="3" <c:if test = "${playerModelInfo.dimension == '3'}">selected="selected"</c:if>>3D</option>
			</select>
		</li>
	    <li><label>&nbsp;</label>
		    <input name="" type="submit" class="btn" value="修改"/>
		    <input name="" type="button" class="btn" value="重置" onclick="clearForm();"/>
	    </li>
	    </ul>
    </div>
</form>
</body>
</html>
