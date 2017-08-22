<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../include/resource.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<!-- common -->
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
	<script type="text/javascript" src="<s:property value="jsPath" />My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />loading.js"></script>
	<style>
		.require{color:#FF0000;padding-right:0 2px;}
		select option {height: 32px;line-height: 32px;text-indent: 10px;}
	</style>
<script type="text/javascript">
$(document).ready(function(e) {
	$('#form').validationEngine('attach', { 
	    promptPosition: 'centerRight', 
	    scroll: false
	  });	  
	$('#form').bind('jqv.form.result', function(event, errorFound){process(errorFound)});
});

function clearForm() {
	window.location = '<s:url action="updatePage" />?topicId=${playerPushTopic.topicId}';
}

function process(errorFound){
    if(!errorFound) {
    	openConfirmWindow('提示信息','是否确认修改 ？','确定', 'formSubmit()','closeConfirmWindow()','取消');
    }
}

function formSubmit(){
	LoadingPic.FullScreenShow();
	document.form.submit();
}

</script>
</head>
<body>
<%@ include file="../../../include/navigation.jsp" %>
<form  method="post" name="form" id="form" action="<s:url action="update"/>" onsubmit="return false;">
<s:token name="submittedToken"/>
	<input type="hidden" name="playerPushTopic.topicId" id="playerPushTopic.topicId" value="${playerPushTopic.topicId}"/>
    <div class="formbody">
    <div class="formtitle"><span>修改主题信息</span></div>
	    <ul class="forminfo">
	    <li>
	    	<label style="width:120px;">主题ID<font class = "require">*</font></label>
	    	<input name="playerPushTopic.topicId" id="playerPushTopic.topicId" type="text" readonly="readonly" class="dfinput" value="${playerPushTopic.topicId}"/>
	    </li>
	    <li>
	    	<label style="width:120px;">名称<font class = "require">*</font></label>
	    	<input name="playerPushTopic.topicName" id="playerPushTopic.topicName" type="text" class="dfinput validate[required,minSize[1],maxSize[100],custom[topicTytle]]" maxlength="100" value="${playerPushTopic.topicName}"/><i class = "tips">请输入主题名称，例：/topics/主题ID</i>
	    </li>
	    <li>
	    	<label style="width:120px;">类型<font class = "require">*</font></label>
	    	<select name="playerPushTopic.topicType" id="playerPushTopic.topicType" class="dayinput form-control" style="width:150px;">
				<option value="" selected="selected" <c:if test = "${playerPushTopic.topicType == ''}">selected="selected"</c:if>>请选择</option>
				<option value="1" <c:if test = "${playerPushTopic.topicType == '1'}">selected="selected"</c:if>>版本</option>
				<option value="2" <c:if test = "${playerPushTopic.topicType == '2'}">selected="selected"</c:if>>渠道</option>
				<option value="3" <c:if test = "${playerPushTopic.topicType == '3'}">selected="selected"</c:if>>语言</option>
			</select>
	    </li>
		<li>
	    	<label style="width:120px;">类型对应的值<font class = "require">*</font></label>
	    	<input name="playerPushTopic.typeValue" id="playerPushTopic.typeValue" type="text" class="dfinput validate[required,minSize[1],maxSize[1024]]" maxlength="1024" value="${playerPushTopic.typeValue}"/><i class = "tips">请输入主题类型对应的值</i>
	    </li>
	    <li>
	    	<label style="width:120px;">国家选择<font class = "require">*</font></label>
			<select name="playerPushTopic.countryCode" id="playerPushTopic.countryCode" class="dayinput form-control" style="width:500px;">
				<c:if test="${not empty playerCountryList}">
					<s:iterator value="playerCountryList" var="playerCountry">
						<option value="<s:property value="#playerCountry.countryCode"/>" <s:if test="%{#playerCountry.countryCode == playerPushTopic.countryCode}">selected="selected"</s:if>>
							<s:property value="#playerCountry.countryNameZh"/>（<s:property value="#playerCountry.countryCode"/>）
						</option>
					</s:iterator>
				</c:if>
			</select>
	    </li>
	    <li><label>&nbsp;</label>
		    <input name="" type="submit" class="btn" value="修改"/>
		    <input name="" type="button" class="btn" value="重置" onclick="clearForm();"/>
	    </li>
	    </ul>
    </div>
</form>
</body>
</html>
