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
    $('.sysOK').css({'position':'absolute','left':($(window).width()-490)/2});
	$(window).resize(function(){  
    $('.sysOK').css({'position':'absolute','left':($(window).width()-490)/2});
    })
    var pageUrl='${request.nextAction}';
    var pageNo = '${page.pageNo}';
    var attrs = $("#attrs").val();
    if(!pageUrl){
    	$("#pageLink").attr("href","javascript:window.history.back(-1)");
    	$("#pageLink").html("返回");
    }else if(pageNo){
    	if(pageUrl.indexOf("?")>1){
    		pageUrl = pageUrl+"&page.pageNo="+pageNo;
    	}else{
    		pageUrl = pageUrl+"?page.pageNo="+pageNo;
    	}
		if(attrs){
    		pageUrl = pageUrl+attrs;
    	}
    	$("#pageLink").attr("href","javascript:successRedirect('"+pageUrl+"')");
    }else{
    	if(attrs){
        	if(pageUrl.indexOf("?")>1){
        		pageUrl = pageUrl+attrs;
        	}else{
        		attrs = attrs.substr(1,attrs.length);
        		pageUrl = pageUrl+attrs;
        	}
    	}
    	$("#pageLink").attr("href","javascript:successRedirect('"+pageUrl+"')");
    }
});  
</script> 

</head>

<body>
<input id="attrs" type="hidden" value="<c:forEach var="attr" items="${otherAttrs}" >&${attr.key}=${attr.value}</c:forEach>"/>

    <div class="place">
    </div>
    <div class="sysOK">
		<s:set name="pageUrl" value="%{(''.equals(#request.nextAction)||null==#request.nextAction)?'':(#request.nextAction)}"/>
		
		<s:if test="(''.equals(#request.successCode)||null==#request.successCode)">									
	    	<h2>系统处理成功！</h2>
		</s:if>
		<s:else>
			<h2 style="width:1000px;">${request.successCode}</h2>
	    </s:else>
		
	<div class="reindex"><a id="pageLink"  href="#">确定</a></div>
		
    </div>


</body>
</html>