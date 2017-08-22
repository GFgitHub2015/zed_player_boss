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
    <div class="formtitle"><span>热门网站信息</span></div>
    <ul class="forminfo">
    <c:if test="${not empty playerSiteNavigate.imgUrl}">
	    <li>
	    	<label>&nbsp;</label>
	    	<div>
	   			<span>
		    		<img src="${playerSiteNavigate.imgUrl}" width="165px" height="130px" style="margin-left:35px"></img>
		    	</span>
		    	<label>&nbsp;</label>
<!-- 		    	<h2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;资源封面</h2> -->
	    	</div>
	    </li>
   	</c:if> 
    <li><label>网站标题</label><label class="height32" style= "width:200px;">${playerSiteNavigate.title}</label></li>
    <li><label>网站链接</label><label class="height32" style= "width:200px;">${playerSiteNavigate.siteUrl}</label></li>
    <li><label>排序</label><label class="height32" style= "width:200px;">${playerSiteNavigate.sort}</label></li>
    <li><label>备注</label><label class="height32" style= "width:200px;">${playerSiteNavigate.remark}</label></li>
    <li>
    	<label>是否启用</label>
    	<cite>
    		<c:choose>
    			<c:when test="${playerSiteNavigate.status == '1'}">
    				启用
    			</c:when>
    			<c:when test="${playerSiteNavigate.status == '0'}">
    				禁用
    			</c:when>
    			<c:otherwise>
    			</c:otherwise>
    		</c:choose>
    	</cite>
    </li>
    <li>
    	<label>国家</label>
   		<s:iterator value="playerCountryList" var="playerCountry">
			<s:if test="%{#playerCountry.countryCode == playerSiteNavigate.countryCode}">
   				<cite>
					<s:property value="#playerCountry.countryNameZh"/>（<s:property value="#playerCountry.countryCode"/>）
   				</cite>
			</s:if>
		</s:iterator>
    </li>
    <li>
        <label>创建时间</label>
        <label style = "width:200px;"><fmt:formatDate value="${playerSiteNavigate.createTime}" type="both"/></label>
    </li>
    <li><label>创建人</label><label class="height32" style= "width:200px;">${playerSiteNavigate.creater}</label></li>
    <li>
        <label>创建时间</label>
        <label style = "width:200px;"><fmt:formatDate value="${playerSiteNavigate.updateTime}" type="both"/></label>
    </li>
    <li><label>修改人</label><label class="height32" style= "width:200px;">${playerSiteNavigate.updater}</label></li>
    <li>
    	<input name="" type="button" class="btn" value="返回" onclick="javascript:history.go(-1)"/>
    </li>
    </ul>
    
    
    </div>
</form>
</body>
</html>
