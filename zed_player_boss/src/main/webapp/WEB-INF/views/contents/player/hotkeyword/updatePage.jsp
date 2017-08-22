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
	window.location = '<s:url action="updatePage" />?hotKeyWordId=${hotKeyWord.keywordId}';
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
	<input type="hidden" name="hotKeyWord.keywordId" id="hotKeyWord.keywordId" value="${hotKeyWord.keywordId}"/>
	<input type="hidden" name="hotKeyWord.areaCode" id="hotKeyWord.areaCode" value="${hotKeyWord.areaCode}"/>
	<input type="hidden" name="hotKeyWord.creater" id="hotKeyWord.creater" value="${hotKeyWord.creater}"/>
	<input type="hidden" name="hotKeyWord.createTime" id="hotKeyWord.createTime" value="${hotKeyWord.createTime}"/>
    <div class="formbody">
    <div class="formtitle"><span>搜索热词信息</span></div>
	    <ul class="forminfo">
	    <li>
	    	<label>关键字<font class = "require">*</font></label>
	    	<input name="hotKeyWord.keyword" id="hotKeyWord.keyword" type="text" class="dfinput validate[required,minSize[1],maxSize[100]]" value="${hotKeyWord.keyword}"maxlength="100" readonly="readonly"/><i class = "tips">请输入关键字</i>
	    </li>
	    <li>
	    	<label>排序<font class = "require">*</font></label>
	    	<input name="hotKeyWord.sort" id="hotKeyWord.sort" type="text" class="dfinput validate[required,minSize[1],maxSize[11],custom[number]]" value="${hotKeyWord.sort}"/><i class = "tips">请输入序号</i>
	    </li>
	    <li>
	    	<label>状态</label>
	    	<cite>
	    		<input name="hotKeyWord.status" type="radio" value="0" <c:if test="${hotKeyWord.status eq '0'}">checked="checked"</c:if>/>&nbsp;&nbsp;禁用
	    		&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input name="hotKeyWord.status" type="radio" value="1" <c:if test="${hotKeyWord.status eq '1'}">checked="checked"</c:if>/>&nbsp;&nbsp;启用		
	    	</cite>
	    </li>    
	    <li><label>描述</label><input name="hotKeyWord.description" id="hotKeyWord.description" type="text" class="dfinput validate[required,minSize[1]]" value="${hotKeyWord.description}" /><i class = "tips">请输入描述信息</i></li>    
	    <li><label>&nbsp;</label>
		    <input name="" type="submit" class="btn" value="修改"/>
		    <input name="" type="button" class="btn" value="重置" onclick="clearForm();"/>
	    </li>
	    </ul>
    </div>
</form>
</body>
</html>
