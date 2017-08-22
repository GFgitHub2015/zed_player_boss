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
	window.location = '<s:url action="updatePage" />?infoId=${serverInfo.infoId}';
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
	<input type="hidden" name="serverInfo.infoId" id="serverInfo.infoId" value="${serverInfo.infoId}"/>
    <div class="formbody">
    <div class="formtitle"><span>修改服务器信息</span></div>
	    <ul class="forminfo">
	    <li>
	    	<label style="width:120px;">服务器地址<font class = "require">*</font></label>
	    	<input name="serverInfo.serverAddress" id="serverInfo.serverAddress" type="text" class="dfinput validate[required,minSize[1],maxSize[1024]]" maxlength="1024" value="${serverInfo.serverAddress}"/><i class = "tips">请输入服务器地址</i>
	    </li>
	    <li>
			<label style="width:120px;">地区</label>
			<select name="serverInfo.areaCode" id="serverInfo.areaCode" class="dayinput form-control" style="width:150px;">
				<option value="62" <c:if test = "${serverInfo.areaCode == '62'}">selected="selected"</c:if>>印度尼西亚（62）</option>
				<option value="66" <c:if test = "${serverInfo.areaCode == '66'}">selected="selected"</c:if>>泰国（66）</option>
				<option value="84" <c:if test = "${serverInfo.areaCode == '84'}">selected="selected"</c:if>>越南（84）</option>
				<option value="86" <c:if test = "${serverInfo.areaCode == '86'}">selected="selected"</c:if>>中国（86）</option>
				<option value="91" <c:if test = "${serverInfo.areaCode == '91'}">selected="selected"</c:if>>印度（91）</option>
			</select>
		</li>
		<li>
	    	<label style="width:120px;">地区名称<font class = "require">*</font></label>
	    	<input name="serverInfo.areaName" id="serverInfo.areaName" type="text" class="dfinput validate[required,minSize[1],maxSize[32]]" maxlength="32" value="${serverInfo.areaName}"/><i class = "tips">请输入地区名称</i>
	    </li>
	    <li>
			<label style="width:120px;">类型</label>
			<select name="serverInfo.origin" id="serverInfo.origin" class="dayinput form-control" style="width:150px;">
				<option value="1" <c:if test = "${serverInfo.origin == '1'}">selected="selected"</c:if>>直播</option>
				<option value="2" <c:if test = "${serverInfo.origin == '2'}">selected="selected"</c:if>>Player</option>
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
