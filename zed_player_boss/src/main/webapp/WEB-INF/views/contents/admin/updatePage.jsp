<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../include/resource.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
	<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />style.css"/>
	<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />validationEngine.jquery.css"/>
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />window.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />function.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.validationEngine-lang.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.validationEngine.min.js"></script>
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
	window.location = '<s:url action="updatePage" />?adminId=${admin.adminId}';
}

function process(errorFound){
    if(!errorFound) {
    	openConfirmWindow('提示信息','是否确认修改 ？','确定', 'formSubmit()','closeConfirmWindow()','取消');
    }
}

function formSubmit(){
	document.form.submit();
}

</script>

</head>

<body>

	<%@ include file="../../include/navigation.jsp" %>

<form  method="post" name="form" id="form" action="<s:url action="update"/>" onsubmit="return false;">
<s:token name="submittedToken"/>
<input type="hidden" name="admin.adminId" id="admin.adminId" value="${admin.adminId}"/>
    
    <div class="formbody">
    
    <div class="formtitle"><span>管理员信息</span></div>
         
    <ul class="forminfo">
    <li><label>管理员编号</label><label style="height:32px">${admin.adminId}</label></li>
    <li><label>年龄</label><input name="admin.adminAge" id="admin.adminAge" type="text" class="dfinput" value="${admin.adminAge}"  maxlength="2"/></li>
    <li>
    	<label>性别</label>
    	<cite>
    		<input name="admin.adminSex" type="radio" value="0" <c:if test="${admin.adminSex eq '0'}">checked="checked"</c:if>/>&nbsp;&nbsp;男
    		&nbsp;&nbsp;&nbsp;&nbsp;
    		<input name="admin.adminSex" type="radio" value="1" <c:if test="${admin.adminSex eq '1'}">checked="checked"</c:if>/>&nbsp;&nbsp;女   		
    	</cite>
    </li>
    <li><label>手机号码<font class = "require">*</font></label><input name="admin.adminPhone" id="admin.adminPhone" type="text" class="dfinput validate[required,custom[phone],minSize[1],maxSize[30]]" value="${admin.adminPhone}"/><i class = "tips">请输入手机号码</i></li>
    <li><label>邮箱<font class = "require">*</font></label><input name="admin.adminMail" id="admin.adminMail" type="text" class="dfinput validate[required,custom[email],minSize[1],maxSize[40]]" value="${admin.adminMail}"/><i class = "tips">请输入字符长度不超过45位的邮件地址</i></li>    
    
    <li><label>&nbsp;</label>
    <input name="" type="submit" class="btn" value="修改"/>
    <input name="" type="button" class="btn" value="重置" onclick="clearForm();"/>
    </li>
    </ul>
    
    
    </div>
</form>
</body>
</html>
