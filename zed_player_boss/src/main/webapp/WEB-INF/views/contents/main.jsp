<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../include/resource.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
	<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />style.css"/>
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.js"></script>

    <script type="text/javascript">

    </script>
</head>

<body style="background:#f8f8f8;">

	<%@ include file="../include/navigation.jsp" %>
    
    <div class="mainindex">
    
	    <div class="welinfo">
	    <span><img src="<s:property value="imagePath" />sun.png" /></span>
	    <p><span>${sessionAdmin.adminId}</span>，欢迎使用播放器管理系统</p>
	    </div>
	    
	    <div class="welinfo">
	    <span><img src="<s:property value="imagePath" />/time.png" /></span>
	    <i>您上次登录的时间<fmt:formatDate value="${sessionAdmin.lastLoginTime}" type="both"/></i>
	    </div>
	    
	   <div class="xline"></div>
    	<div class="box"></div>
<%--     	<%@ include file="../include/boxlist.jsp" %> --%>
    	<%@ include file="../include/reminder.jsp" %>
    </div>
</body>
</html>
