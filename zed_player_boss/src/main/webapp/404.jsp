<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="title.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%-- <link rel="shortcut icon" href="<%=request.getContextPath() %>/public/images/favicon.ico"/> --%>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/public/css/style.css"/>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/public/css/alert.css"/>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/js/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/js/function.js"></script>


<script language="javascript">
	$(function(){
    $('.sysError').css({'position':'absolute','left':($(window).width()-490)/2});
	$(window).resize(function(){  
    $('.sysError').css({'position':'absolute','left':($(window).width()-490)/2});
    })  
});  
</script> 

</head>

<body>
    
    <div class="sysError">
    	<h2>页面不存在！</h2>
		
		<div class="reindex"><a href="<%=request.getContextPath() %>/logoff.action">返回登录页面</a></div>
		
    </div>

</body>
</html>