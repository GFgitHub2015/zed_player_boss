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
    <div class="formtitle"><span>活动推送信息</span></div>
    <ul class="forminfo">
    <li><label>活动标题</label><label class="height32" style= "width:200px;">${playerPromotionInfo.title}</label></li>
    <li><label>状态</label>
    	<cite>
    		<c:set var="nowDate" value="<%=System.currentTimeMillis()%>"></c:set>
			<c:choose>
    			<c:when test="${nowDate-playerPromotionInfo.startTime.getTime()<0}">
    				即将开始
    			</c:when>
    			<c:when test="${nowDate-playerPromotionInfo.endTime.getTime()>0}">
    				已结束
    			</c:when>
    			<c:otherwise>
    				进行时
    			</c:otherwise>
    		</c:choose>
    	</cite>
    </li>  
    <li><label>活动链接</label><label class="height32" style= "width:200px;">${playerPromotionInfo.link}</label></li>
    <li><label>开始时间</label><label class="height32" style= "width:200px;">${playerPromotionInfo.startTime}</label></li>
    <li><label>结束时间</label><label class="height32" style= "width:200px;">${playerPromotionInfo.endTime}</label></li>
    <li>
    	<label>是否启用</label>
    	<cite>
    		<c:choose>
    			<c:when test="${playerPromotionInfo.status == '1'}">
    				启用
    			</c:when>
    			<c:when test="${playerPromotionInfo.status == '0'}">
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
			<s:if test="%{#playerCountry.countryCode == playerPromotionInfo.countryCode}">
   				<cite>
					<s:property value="#playerCountry.countryNameZh"/>（<s:property value="#playerCountry.countryCode"/>）
   				</cite>
			</s:if>
		</s:iterator>
    </li>
    <li>
	    	<label>是否热门推荐</label>
	    	<cite>
    		<c:choose>
    			<c:when test="${playerPromotionInfo.promoType == '1'}">
    				是
    			</c:when>
    			<c:otherwise>否
    			</c:otherwise>
    		</c:choose>
    		</cite>
    	</li>
    	<div id="recommendFlag"  <c:if test="${playerPromotionInfo.promoType  != '1'}">style='display: none;' </c:if>>
	    <li><label>上架开始时间</label><label class="height32" style= "width:200px;">${playerPromotionInfo.upTime}</label></li>
	    <li><label>上架结束时间</label><label class="height32" style= "width:200px;">${playerPromotionInfo.downTime}</label></li>
		    <li>
		    	<label>是否置顶</label>
		    	<cite>
	    		<c:choose>
	    			<c:when test="${playerPromotionInfo.topFlag == '1'}">
	    				是
	    			</c:when>
	    			<c:when test="${playerPromotionInfo.topFlag == '0'}">
	    				否
	    			</c:when>
	    			<c:otherwise>
	    			</c:otherwise>
	    		</c:choose>
	    		</cite>
	    	</li>
    	</div>
    <li>
    	<input name="" type="button" class="btn" value="返回" onclick="javascript:history.go(-1)"/>
    </li>
    </ul>
    
    
    </div>
</form>
</body>
</html>
