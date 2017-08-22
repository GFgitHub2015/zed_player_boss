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
	<script type="text/javascript" src="<s:property value="jsPath" />My97DatePicker/WdatePicker.js"></script>
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

function clearForm() {
	window.location = '<s:url action="updatePage" />?versionId=${version.versionId}&packageName=${version.packageName}';
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
	<input type="hidden" name="version.versionId" id="version.versionId" value="${version.versionId}"/>
	<input type="hidden" name="version.packageName" id="version.packageName" value="${version.packageName}"/>
	<input type="hidden" name="version.versionCode" id="version.versionCode" value="${version.versionCode}"/>
	<input type="hidden" name="version.createTime" id="version.createTime" value="${version.createTime}"/>
	<input type="hidden" name="version.updateTime" id="version.updateTime" value="${version.updateTime}"/>
	<input type="hidden" name="version.creater" id="version.creater" value="${version.creater}"/>
	<input type="hidden" name="version.editer" id="version.editer" value="${version.editer}"/>
	<input type="hidden" name="version.systemType" id="version.systemType" value="${version.systemType}"/>
	<input type="hidden" name="version.description" id="version.description" value="${version.description}"/>
	<input type="hidden" name="version.origin" id="version.origin" value="${version.origin}"/>
	<input type="hidden" name="version.md5" id="version.md5" value="${version.md5}"/>
    <div class="formbody">
    <div class="formtitle"><span>修改版本信息</span></div>
	    <ul class="forminfo">
	    <li>
	    	<label style="width:120px;">状态<font class = "require">*</font></label>
	    	<cite>
	    		<input name="version.status" type="radio" value="0" <c:if test="${version.status eq '0'}">checked="checked"</c:if>/>&nbsp;&nbsp;不可下载
	    		&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input name="version.status" type="radio" value="1" <c:if test="${version.status eq '1'}">checked="checked"</c:if>/>&nbsp;&nbsp;可下载
	    	</cite>
    	</li>
	    <li>
	    	<label style="width:120px;">应用类型<font class = "require">*</font></label>
	    	<input name="version.appType" id="version.appType" type="text" class="dfinput validate[required,minSize[1],maxSize[11],custom[onlyNumber]]" maxlength="11" value="${version.appType}"/><i class = "tips">请输入应用类型</i>
	    </li>
	    <li>
	    	<label style="width:120px;">下载来源<font class = "require">*</font></label>
	    	<select id="version.source" name="version.source" class="dayinput form-control" style="width:120px;">
				 <option value="2" <c:if test="${version.source eq 2}"> selected=selected </c:if>>官方渠道</option>
	             <option value="1" <c:if test="${version.source eq 1}"> selected=selected </c:if>>GooglePlayer</option>
			</select>
	    </li>
	    <li>
	    	<label style="width:120px;">下载地址<font class = "require">*</font></label>
	    	<input name="version.downloadUrl" id="version.downloadUrl" type="text" class="dfinput validate[required,minSize[1],maxSize[500]]" maxlength="500" value="${version.downloadUrl}"/><i class = "tips">请输入下载地址</i>
	    </li>
	    <li>
	    	<label style="width:120px;">是否强制升级<font class = "require">*</font></label>
	    	<input name="version.isMust" type="radio" value="1" <c:if test="${version.isMust eq 1}"> checked="checked"</c:if>/>&nbsp;&nbsp;普通升级
	    		&nbsp;&nbsp;&nbsp;&nbsp;
	    	<input name="version.isMust" type="radio" value="2" <c:if test="${version.isMust eq 2}"> checked="checked"</c:if>/>&nbsp;&nbsp;强制升级
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
