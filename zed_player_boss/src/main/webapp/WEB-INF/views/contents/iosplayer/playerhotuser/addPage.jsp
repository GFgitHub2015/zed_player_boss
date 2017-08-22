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
    <script type="text/javascript" src="<s:property value="jsPath" />My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />layer/layer.js"></script>
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

function process(errorFound) {
	if(!errorFound){
		openConfirmWindow('提示信息','是否确认提交 ？','确定', 'formSubmit()','closeConfirmWindow()','取消');
	}
}
function formSubmit(){
	LoadingPic.FullScreenShow();
	document.form.submit();
}
//检查GBox用户是否已经存在热门用户
function changeUserId() {
	var userId = $.trim($("#userId").val());
    var data = {"playerRelationHotUser.userId": userId};
    var url  = '<s:url action="isExist.action" />';
    $.ajax({
        type: "POST",
        url: url,
        data: data,
        dataType : "json",
        success:function(data) {
        if(data) {
        	layer.alert('该热门用户已经存在！');
        	$("#userId").val("");
        } 
        },
        error : function (XMLHttpRequest, textStatus, errorThrown) {
        }
	 }); 
}
</script>
</head>

<body>

<%@ include file="../../../include/navigation.jsp" %>

<form method="post" name="form" id="form" enctype="multipart/form-data"  action="<s:url action="addGBoxUser"/>" onsubmit="return false;">
<s:token name="submittedToken"/>
    <div class="formbody">
    <div class="formtitle"><span>新增GBox热门用户</span></div>
	    <ul class="forminfo">
	    <li>
	    	<label>用户编号<font class = "require">*</font></label>
	    	<input name="playerRelationHotUser.userId" id="userId" type="text" class="dfinput validate[required,minSize[1],maxSize[100]]" maxlength="100" onblur="changeUserId()"/> 
	    </li>
    	<label>&nbsp;</label>
	    <input name="" type="submit" class="btn" value="保存"/>
	    <input name="" type="button" class="btn" value="返回" onclick="javascript:history.go(-1)"/>
	    </li>
    </ul>
	</form>
</body>
</html>
