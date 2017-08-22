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
    
    <div class="formtitle"><span>管理员信息</span></div>
    
    <ul class="forminfo">
    <li><label>管理员编号</label><label class="height32">${admin.adminId}</label></li>
    <li><label>年龄</label><label class="height32">${admin.adminAge}</label></li>
    <li>
    	<label>性别</label>
    	<cite>
    		<c:choose>
    			<c:when test="${admin.adminSex == '0'}">
    				男
    			</c:when>
    			<c:otherwise>
    				女
    			</c:otherwise>
    		</c:choose>
    	</cite>
    </li>
    <li><label>手机号码</label><label class="height32">${admin.adminPhone}</label></li>
    <li><label>邮箱</label><label class="height32">${admin.adminMail}</label></li>    
    
    <li>
    <input name="" type="button" class="btn" value="返回" onclick="process();"/>
    </li>
    </ul>
    
    
    </div>
</form>
</body>
</html>
