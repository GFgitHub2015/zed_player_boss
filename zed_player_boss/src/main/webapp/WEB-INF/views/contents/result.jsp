<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../include/resource.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />style.css"/>
<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />alert.css"/>
<script type="text/javascript" src="<s:property value="jsPath" />jquery.js"></script>
<script type="text/javascript" src="<s:property value="jsPath" />function.js"></script>


<script language="javascript">
	$(function(){
    $('.sysResult').css({'position':'absolute','left':($(window).width()-490)/2});
	$(window).resize(function(){  
    $('.sysResult').css({'position':'absolute','left':($(window).width()-490)/2});
    })  
});
</script> 

</head>

<body>
    <div class="place">
    </div>
    
    <div  class="sysResult">
		<s:set name="pageUrl" value="%{(''.equals(#request.nextAction)||null==#request.nextAction)?'':(#request.nextAction)}"/>
		
		<s:if test="(!''.equals(#request.message)&&null!=#request.message)">									
<!-- 	    	<h2>系统处理成功！</h2> -->
			<h2 style="width:1000px;">${request.message}</h2>
		</s:if>
		<s:else>
	    </s:else>
		

		<s:if test="(''.equals(#pageUrl)||null==#pageUrl)">
			<div class="reindex"><a href="javascript:window.history.back(-1)">返回</a></div>
		</s:if>		
		<s:else>
			<div class="reindex"><a href="javascript:successRedirect('<s:property value="#pageUrl"/>')">确定</a></div>
	    </s:else>
		
    </div>


</body>
</html>