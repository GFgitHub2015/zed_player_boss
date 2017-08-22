<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../include/resource.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%-- <link rel="shortcut icon" href="<s:property value="imagePath" />favicon.ico"/> --%>
<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />style.css"/>
<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />alert.css"/>
<script type="text/javascript" src="<s:property value="jsPath" />jquery.js"></script>
<script type="text/javascript" src="<s:property value="jsPath" />function.js"></script>


<script language="javascript">
$(function(){
    $('.loginbox').css({'position':'absolute','left':($(window).width()-570)/2});
	$(window).resize(function(){  
    $('.loginbox').css({'position':'absolute','left':($(window).width()-570)/2});
	})
	

}); 


</script> 
</head>
<body class="login">
    <div class="loginbody">
       <span class="systemlogo"></span>
    	<div class="loginbox"> 
		  <div class="sysError">
	    	<s:set name="pageLogoff" value="%{@com.zed.system.GConstantRedirect@GS_LOGOFF_ACTION}"/>
			<s:set name="pageLogn" value="%{@com.zed.system.GConstantRedirect@GS_LOGIN_ACTION}"/>
			<s:set name="pageUrl" value="%{(''.equals(#request.prevAction)||null==#request.prevAction)?(#pageLogoff):(#request.prevAction)}"/>
			<s:if test="(#pageUrl).indexOf(#pageLogoff)!=-1 || (#pageUrl).indexOf(#pageLogn)!=-1">								
		    	<h2>系统错误！</h2>
			</s:if>
			<s:else>
				<h2>${request.errorCode}</h2>
		    </s:else>
			
			
			<s:if test="(#pageUrl).indexOf(#pageLogoff)!=-1 || (#pageUrl).indexOf(#pageLogn)!=-1">									
		    	<div class="reindex"><a href="<s:property value="#pageUrl"/>">返回首页</a></div>
			</s:if>		
			<s:elseif test="(''.equals(#pageUrl)||null==#pageUrl)">
				<div class="reindex"><a href="javascript:window.history.back(-1)">返回</a></div>
			</s:elseif>		
			<s:else>
				<div class="reindex"><a href="<s:property value="#pageUrl"/>">返回</a></div>
		    </s:else>
			
	    	</div>
	    </div>
    
	</div>

</body>
</html>

