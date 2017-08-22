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
    <div class="formtitle"><span>网盘用户信息</span></div>
    <ul class="forminfo">
    <c:if test="${not empty playerUser.iconUrl}">
	    <li>
	    	<label>&nbsp;</label>
	    	<div>
	   			<span>
		    		<img src="${playerUser.iconUrl}" width="165px" height="130px" style="margin-left:35px"></img>
		    	</span>
		    	<label>&nbsp;</label>
	    	</div>
	    </li>
   	</c:if> 
    <li><label>网盘用户编号</label><label class="height32" style= "width:200px;">${playerUser.userId}</label></li>
    <li><label>网盘用户昵称</label><label class="height32" style= "width:200px;">${playerUser.nickName}</label></li>
    <c:if test="${not empty playerUser.sign}">
    	<li><label>网盘用户签名</label><label class="height32" style= "width:200px;">${playerUser.sign}</label></li>
    </c:if> 
    <li>
    	<label>是否启用</label>
    	<cite>
    		<c:choose>
    			<c:when test="${playerUser.status == '1'}">
    				启用
    			</c:when>
    			<c:when test="${playerUser.status == '0'}">
    				禁用
    			</c:when>
    			<c:otherwise>
    			</c:otherwise>
    		</c:choose>
    	</cite>
    </li>     
    <li>
    	<label>网盘用户角色</label>
    	<cite>
    		<c:choose>
	    		<c:when test="${playerUser.userRoleStatus == '0'}">
	   				未通过审核
	   			</c:when>
	   			<c:when test="${playerUser.userRoleStatus == '1'}">
	   				高级用户
	   			</c:when>
	   			<c:when test="${playerUser.userRoleStatus == '2'}">
	   				站长
	   			</c:when>
	   			<c:when test="${playerUser.userRoleStatus == '3'}">
	   				运营用户
	   			</c:when>
	   			<c:when test="${playerUser.userRoleStatus == '4'}">
	   				特殊用户
	   			</c:when>
	   			<c:when test="${playerUser.userRoleStatus == '9'}">
	   				普通用户
	   			</c:when>
	   			<c:otherwise>
	   			</c:otherwise>
   			</c:choose>
    	</cite>
    </li>
    <c:if test="${not empty playerUser.iFollowCount}">
    	<li><label>关注数</label><label class="height32" style= "width:200px;">${playerUser.iFollowCount}</label></li>
    </c:if> 
    <c:if test="${not empty playerUser.followMeCount}">
    	<li><label>被关注数</label><label class="height32" style= "width:200px;">${playerUser.followMeCount}</label></li>
    </c:if> 
    <c:if test="${not empty playerUser.shareCount}">
    	<li><label>资源分享数</label><label class="height32" style= "width:200px;">${playerUser.shareCount}</label></li>
    </c:if> 
    <li>
    	<input name="" type="button" class="btn" value="返回" onclick="javascript:history.go(-1)"/>
    </li>
    </ul>
    
    
    </div>
</form>
</body>
</html>
