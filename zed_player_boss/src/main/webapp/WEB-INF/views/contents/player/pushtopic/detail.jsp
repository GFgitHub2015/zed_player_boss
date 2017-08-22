<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../include/resource.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="<s:property value="themePath" />css/style.css" rel="stylesheet">
    <link href="<s:property value="themePath" />css/style-responsive.css" rel="stylesheet"> 
	<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />style.css"/>
	<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />validationEngine.jquery.css"/>
	<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />loading.css"/>
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />window.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />function.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.validationEngine-lang.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.validationEngine.min.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />loading.js"></script>
    <script type="text/javascript" src="<s:property value="jsPath" />My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />layer/layer.js"></script>
</head>
<body>
	<%@ include file="../../../include/navigation.jsp" %>
<form  method="post" name="form" id="form" action="<s:url action="list"/>">
    <div class="formbody">
    <div class="formtitle"><span>主题信息详情</span></div>
    <ul class="forminfo">
    <li><label>主题ID</label><label class="height32" style= "width:200px;">${playerPushTopic.topicId}</label></li>
    <li><label>名称</label><label class="height32" style= "width:200px;">${playerPushTopic.topicName}</label></li>
    <li>
    	<label>类型</label>
    	<cite>
    		<c:choose>
    			<c:when test="${playerPushTopic.topicType == '1'}">
    				版本
    			</c:when>
    			<c:when test="${playerPushTopic.topicType == '2'}">
    				渠道
    			</c:when>
    			<c:when test="${playerPushTopic.topicType == '3'}">
    				语言
    			</c:when>
    			<c:otherwise>
    			</c:otherwise>
    		</c:choose>
    	</cite>
    </li>     
    <li><label>类型对应的值</label><label class="height32" style= "width:200px;">${playerPushTopic.typeValue}</label></li>
    <li>
    	<label>国家</label>
		<s:iterator value="playerCountryList" var="playerCountry">
			<s:if test="%{#playerCountry.countryCode == playerPushTopic.countryCode}">
				<cite>
					<s:property value="#playerCountry.countryNameZh"/>（<s:property value="#playerCountry.countryCode"/>）
				</cite>
			</s:if>
		</s:iterator>
    </li>
    <li>
    	<input name="" type="button" class="btn" value="返回" onclick="javascript:history.go(-1)"/>
    </li>
    </ul>
    
    
    </div>
</form>
</body>
</html>
