<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../include/resource.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
	<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />style.css"/>
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.js"></script>

<script type="text/javascript">
$(document).ready(function(){

});


function process(){
	document.form.submit();
}

</script>

</head>

<body>

	<%@ include file="../../include/navigation.jsp" %>

<form  method="post" name="form" id="form" action="<s:url action="list"/>">
 
    <div class="formbody">
    
    <div class="formtitle"><span>角色信息</span></div>
    
    <ul class="forminfo">
    <li><label>角色名称</label><label class="height32">${role.roleName}</label></li>
    <li><label>角色Key</label><label class="height32">${role.roleKey}</label></li>
    <li>
    	<label>是否禁用</label>
    	<cite>
    		<c:choose>
    			<c:when test="${role.enable == '0'}">
    				禁用
    			</c:when>
    			<c:otherwise>
    				正常
    			</c:otherwise>
    		</c:choose>
    	</cite>
    </li>
   
    <li><label>描述</label><label class="height32">${role.description}</label></li>
    
    <li>
    <input name="" type="button" class="btn" value="返回" onclick="process();"/>
    </li>
    </ul>
    
    
    </div>
</form>
</body>
</html>
