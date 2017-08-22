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
    <div class="formtitle"><span>待爬词信息</span></div>
    <ul class="forminfo">
    <li><label>待爬词</label><label class="height32" style= "width:200px;">${playerSpiderKeyWord.keyword}</label></li>    
     <li>
    	<label>状态</label>
    	<cite>
    		<c:choose>
    			<c:when test="${playerSpiderKeyWord.status == '0'}">
    				待爬
    			</c:when>
    			<c:when test="${playerSpiderKeyWord.status == '1'}">
    				爬去成功
    			</c:when>
    			<c:when test="${playerSpiderKeyWord.status == '3'}">
    				爬取不到资源
    			</c:when>
    			<c:when test="${playerSpiderKeyWord.status == '-1'}">
    				废弃
    			</c:when>
    			<c:otherwise>
    			</c:otherwise>
    		</c:choose>
    	</cite>
    </li>     
    <li>
        <label>创建时间</label>
        <label style = "width:200px;"><fmt:formatDate value="${playerSpiderKeyWord.createTime}" type="both"/></label>
    </li>
    <li>
        <label>修改时间</label>
        <label style = "width:200px;"><fmt:formatDate value="${playerSpiderKeyWord.updateTime}" type="both"/></label>
    </li>
    <li><label>修改人</label><label class="height32" style= "width:200px;">${playerSpiderKeyWord.updater}</label></li>
    <li>
    	<input name="" type="button" class="btn" value="返回" onclick="javascript:history.go(-1)"/>
    </li>
    </ul>
    
    
    </div>
</form>
</body>
</html>
