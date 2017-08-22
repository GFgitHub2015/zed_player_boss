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

function process(errorFound){
    if(!errorFound) {        
        openConfirmWindow('提示信息','是否确认提交？','确定', 'formSubmit()','closeConfirmWindow()','取消');
    }
}

function formSubmit(){
	document.form.submit();
}

</script>

</head>

<body>

	<%@ include file="../../include/navigation.jsp" %>

<form  method="post" name="form" id="form" action="<s:url action="add"/>" onsubmit="return false;">
<s:token name="submittedToken"/>
    
    <div class="formbody">
    
    <div class="formtitle"><span>角色信息</span></div>
                    
    <ul class="forminfo">
    <li><label>角色名称<font class = "require">*</font></label><input name="role.roleName" id="role.roleName" type="text" class="dfinput validate[required,minSize[1],maxSize[50]]" maxlength="8"/><i class = "tips">字符长度不超过50</i></li>
    <li><label>角色Key<font class = "require">*</font></label><input name="role.roleKey" id="role.roleKey" type="text" class="dfinput validate[required,minSize[1],maxSize[50]]"/><i class = "tips">字符长度不超过50</i></li>
    <li>
    	<label>是否禁用<font class = "require">*</font></label>
    	<cite>
    		<input name="role.enable" type="radio" value="0" checked="checked"/>&nbsp;&nbsp;禁用
    		&nbsp;&nbsp;&nbsp;&nbsp;
    		<input name="role.enable" type="radio" value="1"/>&nbsp;&nbsp;正常
    	</cite>
    </li>
    	
    <li><label>描述<font class = "require">*</font></label><input name="role.description" id="role.description" type="text" class="dfinput validate[required,minSize[1],maxSize[200]]" /><i class = "tips">描述字符长度不超过200</i></li>
    
    <li><label>&nbsp;</label>
    <input name="" type="submit" class="btn" value="保存"/>
    <input name="" type="button" class="btn" value="返回" onclick="javascript:history.go(-1)"/>
    </li>
    </ul>
    
    </div>
    

</form>
</body>
</html>
