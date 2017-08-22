<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../include/resource.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />style.css"/>
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.js"></script>
</head>
<body>
	<%@ include file="../../../include/navigation.jsp" %>
<form  method="post" name="form" id="form" action="<s:url action="list"/>">
    <div class="formbody">
    <div class="formtitle"><span>配置信息</span></div>
    <ul class="forminfo">
    <li><label style="width:120px;">版本号</label><label class="height32" style= "width:200px;">${playerConfig.version}</label></li>
    <li><label style="width:120px;">app包名</label><label class="height32" style= "width:200px;">${playerConfig.packageName}</label></li>
    <li>
    	<label style="width:120px;">google审核状态</label>
    	<cite>
    		<c:choose>
    			<c:when test="${playerConfig.reviewStatus == '0'}">
    				审核中
    			</c:when>
    			<c:when test="${playerConfig.reviewStatus == '1'}">
    				审核通过
    			</c:when>
    			<c:otherwise>
    			</c:otherwise>
    		</c:choose>
    	</cite>
    </li>     
    <li>
    	<input name="" type="button" class="btn" value="返回" onclick="javascript:history.go(-1)"/>
    </li>
    </ul>
    
    
    </div>
</form>
</body>
</html>
