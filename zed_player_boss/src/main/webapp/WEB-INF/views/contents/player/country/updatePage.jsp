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
	window.location = '<s:url action="updatePage" />?countryId=${playerCountry.countryId}';
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

<div class="formbody">
<div class="formtitle"><span>修改国家码字典信息</span></div>
<form method="post" name="form" id="form" action="<s:url action="update"/>" onsubmit="return false;">
<input type="hidden" name="playerCountry.countryId" id="playerCountry.countryId" value="${playerCountry.countryId}"/>
<s:token name="submittedToken"/>
    <ul class="forminfo">
	    <li>
	    	<label style="width:120px;">国家码</label>
	    	<input name="playerCountry.countryCode" id="playerCountry.countryCode" type="text" readonly="readonly" class="dfinput" value="${playerCountry.countryCode}" maxlength="32"/>
	    </li>
	    <li>
	    	<label style="width:120px;">国家中文名称<font class = "require">*</font></label>
	    	<input name="playerCountry.countryNameZh" id="playerCountry.countryNameZh" type="text" class="dfinput validate[required,minSize[1],maxSize[32],custom[chinese]]" value="${playerCountry.countryNameZh}" maxlength="32"/><i class = "tips">请输入中文名称</i>
	    </li>
	    <li>
	    	<label style="width:120px;">国家英文名称<font class = "require">*</font></label>
	    	<input name="playerCountry.countryNameEn" id="playerCountry.countryNameEn" type="text" class="dfinput validate[required,minSize[1],maxSize[1024],custom[onlyLetter]]" value="${playerCountry.countryNameEn}" maxlength="1024"/><i class = "tips">请输入英文名称</i>
	    </li>
	    <li>
	    	<label style="width:120px;">国际时区ID<font class = "require">*</font></label>
			<select name="playerCountry.zoneTimeId" id="playerCountry.zoneTimeId" class="dayinput form-control" style="width:500px;">
				<c:if test="${not empty zoneList}">
					<s:iterator value="zoneList" var="zone">
						<option value="<s:property value="#zone"/>" <s:if test="%{#zone == playerCountry.zoneTimeId}">selected="selected"</s:if>>
							<s:property value="#zone"/>
						</option>
					</s:iterator>
				</c:if>
			</select>
	    </li>
	     <li>
	    	<label style="width:120px;">alpha-2<font class = "require">*</font></label>
	    	<input name="playerCountry.alpha2" id="playerCountry.alpha2" type="text" class="dfinput validate[required,minSize[2],maxSize[2],custom[onlyLetter]]" value="${playerCountry.alpha2}" maxlength="2"/><i class = "tips">请输入alpha-2</i>
	    </li>
	    <li>
	    	<label style="width:120px;">alpha-3<font class = "require">*</font></label>
	    	<input name="playerCountry.alpha3" id="playerCountry.alpha3" type="text" class="dfinput validate[required,minSize[3],maxSize[3],custom[onlyLetter]]" value="${playerCountry.alpha3}" maxlength="3"/><i class = "tips">请输入alpha-3</i>
	    </li>
	    <li>
	    	<label style="width:120px;">UN numeric-3<font class = "require">*</font></label>
	    	<input name="playerCountry.numeric3" id="playerCountry.numeric3" type="text" class="dfinput validate[required,minSize[3],maxSize[3],custom[onlyNumber]" value="${playerCountry.numeric3}" maxlength="3"/><i class = "tips">请输入UN numeric-3</i>
	    </li>
	    <li>
	    	<label style="width:120px;">是否启用<font class = "require">*</font></label>
	    	<cite>
	    		<input name="playerCountry.status" type="radio" value="0" <c:if test = "${playerCountry.status == '0'}">checked="checked"</c:if>/>&nbsp;&nbsp;禁用
	    		&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input name="playerCountry.status" type="radio" value="1"<c:if test = "${playerCountry.status == '1'}">checked="checked"</c:if>/>&nbsp;&nbsp;启用
	    	</cite>
    	</li>
	    <li>
	    	<label>&nbsp;</label>
		    <input name="" type="submit" class="btn" value="保存"/>
		    <input name="" type="button" class="btn" value="返回" onclick="javascript:history.go(-1)"/>
	    </li>
    </ul>
	</form>
   </div>
</body>
</html>
