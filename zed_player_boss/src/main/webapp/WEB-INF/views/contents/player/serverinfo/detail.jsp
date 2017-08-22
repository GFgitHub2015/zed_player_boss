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
    <div class="formtitle"><span>服务器详细信息</span></div>
    <ul class="forminfo">
    <li>
    	<label>地区</label>
    	<cite>
    		<c:choose>
    			<c:when test="${serverInfo.areaCode == '91'}">
    				印度
    			</c:when>
    			<c:when test="${serverInfo.areaCode == '86'}">
    				中国
    			</c:when>
    			<c:when test="${serverInfo.areaCode == '62'}">
    				印度尼西亚
    			</c:when>
    			<c:when test="${serverInfo.areaCode == '66'}">
    				泰国
    			</c:when>
    			<c:when test="${serverInfo.areaCode == '84'}">
    				越南
    			</c:when>
    			<c:otherwise>
    			</c:otherwise>
    		</c:choose>
    	</cite>
    </li>     
    <li><label>服务器地址</label><label class="height32" style= "width:200px;">${serverInfo.serverAddress}</label></li>
    <li><label>类型</label>
    	<cite>
    		<c:choose>
    			<c:when test="${serverInfo.origin == '1'}">
    				直播
    			</c:when>
    			<c:when test="${serverInfo.origin == '2'}">
    				Player
    			</c:when>
    			<c:otherwise>
    			</c:otherwise>
    		</c:choose>
    	</cite>
    </li>
    <li><label>创建人</label><label class="height32" style= "width:200px;">${serverInfo.creater}</label></li>
    <li>
        <label>创建时间</label>
        <label style = "width:200px;"><fmt:formatDate value="${serverInfo.createTime}" type="both"/></label>
    </li>
    <li><label>修改人</label><label class="height32" style= "width:200px;">${serverInfo.updater}</label></li>
    <li>
        <label>修改时间</label>
        <label style = "width:200px;"><fmt:formatDate value="${serverInfo.updateTime}" type="both"/></label>
    </li>
    <li>
    	<input name="" type="button" class="btn" value="返回" onclick="javascript:history.go(-1)"/>
    </li>
    </ul>
    
    
    </div>
</form>
</body>
</html>
