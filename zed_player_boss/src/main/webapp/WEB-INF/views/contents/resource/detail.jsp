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
    
    <div class="formtitle"><span>菜单信息</span></div>
    
    <ul class="forminfo">
    <li><label>菜单名称</label><label class="height32" style= "width:200px;">${resource.resourceName}</label></li>
    <li><label>菜单Key</label><label class="height32" style= "width:200px;">${resource.resourceKey}</label></li>
<%--     <li><label>菜单图片</label><label class="height32" style= "width:200px;">${resource.resourceImage}</label></li> --%>
    <li><label>菜单地址</label><label class="height32" style= "width:200px;">${resource.resourceUrl}</label></li>    
    <li><label>菜单顺序</label><label class="height32" style= "width:200px;">${resource.resourceOrder}</label></li>    
    <li>
    	<label>菜单等级</label>
    	<cite>
    		<c:choose>
    			<c:when test="${resource.resourceLevel == '1'}">
    				一级菜单
    			</c:when>
    			<c:when test="${resource.resourceLevel == '2'}">
    				二级菜单
    			</c:when>
    			<c:when test="${resource.resourceLevel == '3'}">
    				三级菜单
    			</c:when>
    			<c:when test="${resource.resourceLevel == '4'}">
    				顶部按钮
    			</c:when>
    			<c:when test="${resource.resourceLevel == '5'}">
    				操作按钮
    			</c:when>
    			<c:otherwise>
    			</c:otherwise>
    		</c:choose>
    	</cite>
    </li>      
     <li>
    	<label>是否禁用</label>
    	<cite>
    		<c:choose>
    			<c:when test="${resource.enable == '0'}">
    				禁用
    			</c:when>
    			<c:otherwise>
    				正常
    			</c:otherwise>
    		</c:choose>
    	</cite>
    </li>  
    <li><label>方法名称</label><label class="height32" style= "width:50px;">${resource.functionName}</label></li>    
    <li><label>描述</label><label class="height32" style= "width:200px;">${resource.description}</label></li>    
    <li><label>上级菜单</label><label class="height32" style= "width:200px;">${resource.partentName}</label></li>
    
    <li>
    <input name="" type="button" class="btn" value="返回" onclick="process();"/>
    </li>
    </ul>
    
    
    </div>
</form>
</body>
</html>
