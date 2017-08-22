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
    <div class="formtitle"><span>用户基本信息</span></div>
    <ul class="forminfo">
    <c:if test="${not empty account.iconUrl}">
		    <li>
		    	<label>&nbsp;</label>
		    	<div>
		   			<span>
			    		<img src="${account.iconUrl}" width="165px" height="130px" style="margin-left:35px"></img>
			    	</span>
			    	<label>&nbsp;</label>
		    	</div>
		    </li>
   		</c:if> 
    <li><label style="width:120px;">用户编号</label><label class="height32" style= "width:200px;">${account.userId}</label></li>
    <li><label style="width:120px;">用户昵称</label><label class="height32" style= "width:200px;">${account.nickName}</label></li>
    <li><label style="width:120px;">用户手机号</label><label class="height32" style= "width:200px;">${account.phone}</label></li>
     <li>
    	<label style="width:120px;">用户性别</label>
    	<cite>
	    	<c:choose>
	   			<c:when test="${account.sex == '3'}">
	   				男
	   			</c:when>
	   			<c:when test="${account.sex == '4'}">
	   				女
	   			</c:when>
	   			<c:when test="${account.sex == '2'}">
	   				未知
	   			</c:when>
	   			<c:when test="${account.sex == '0'}">
	   				男（不可修改）
	   			</c:when>
	   			<c:when test="${account.sex == '1'}">
	   				女（不可修改）
	   			</c:when>
	   			<c:otherwise>
	   			</c:otherwise>
	   		</c:choose>
    	</cite>
    </li>     
    <li>
    	<label style="width:120px;">是否启用</label>
    	<cite>
    		<c:choose>
    			<c:when test="${account.status == '1'}">
    				启用
    			</c:when>
    			<c:when test="${account.status == '0'}">
    				禁用
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
