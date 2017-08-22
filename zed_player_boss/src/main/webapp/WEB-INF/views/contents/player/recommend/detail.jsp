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
    <div class="formtitle"><span>搜索推荐词信息</span></div>
    <ul class="forminfo">
    <c:if test="${not empty recommendKeyWord.imgUrl}">
	    <li>
	    	<label>&nbsp;</label>
	    	<div>
	   			<span>
		    		<img src="${recommendKeyWord.imgUrl}" width="165px" height="130px" style="margin-left:35px"></img>
		    	</span>
		    	<label>&nbsp;</label>
<!-- 		    	<h2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;资源封面</h2> -->
	    	</div>
	    </li>
   	</c:if> 
    <li><label>推荐词</label><label class="height32" style= "width:200px;">${recommendKeyWord.keyword}</label></li>    
    <li>
    	<label>国家</label>
   		<s:iterator value="playerCountryList" var="playerCountry">
			<s:if test="%{#playerCountry.countryCode == recommendKeyWord.areaCode}">
   				<cite>
					<s:property value="#playerCountry.countryNameZh"/>（<s:property value="#playerCountry.countryCode"/>）
   				</cite>
			</s:if>
		</s:iterator>
    </li>
    <li><label>排序</label><label class="height32" style= "width:200px;">${recommendKeyWord.sort}</label></li>
     <li>
    	<label>状态</label>
    	<cite>
    		<c:choose>
    			<c:when test="${recommendKeyWord.status == '0'}">
    				禁用
    			</c:when>
    			<c:when test="${recommendKeyWord.status == '1'}">
    				启用
    			</c:when>
    			<c:otherwise>
    			</c:otherwise>
    		</c:choose>
    	</cite>
    </li>     
    <li>
        <label>创建时间</label>
        <label style = "width:200px;"><fmt:formatDate value="${recommendKeyWord.createTime}" type="both"/></label>
    </li>
    <li><label>创建人</label><label class="height32" style= "width:200px;">${recommendKeyWord.creater}</label></li>
    <li>
        <label>修改时间</label>
        <label style = "width:200px;"><fmt:formatDate value="${recommendKeyWord.updateTime}" type="both"/></label>
    </li>
    <li><label>修改人</label><label class="height32" style= "width:200px;">${recommendKeyWord.updater}</label></li>
    <li><label>描述</label><label class="height32" style= "width:200px;">${recommendKeyWord.description}</label></li>
    <li>
    	<input name="" type="button" class="btn" value="返回" onclick="javascript:history.go(-1)"/>
    </li>
    </ul>
    
    
    </div>
</form>
</body>
</html>
