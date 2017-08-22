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
	window.location = '<s:url action="updatePage" />?userId=${playerUser.userId}';
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
	<input type="hidden" name="playerUser.createTime" id="playerUser.createTime" value="${playerUser.createTime}"/>
	<input type="hidden" name="playerUser.iFollowCount" id="playerUser.iFollowCount" value="${playerUser.iFollowCount}"/>
	<input type="hidden" name="playerUser.followMeCount" id="playerUser.followMeCount" value="${playerUser.followMeCount}"/>
	<input type="hidden" name="playerUser.shareCount" id="playerUser.shareCount" value="${playerUser.shareCount}"/>
    <div class="formbody">
    <div class="formtitle"><span>修改网盘用户</span></div>
	    <ul class="forminfo">
	    <c:if test="${not empty playerUser.iconUrl}">
	    <li>
	    	<label>&nbsp;</label>
	    	<div>
	   			<span>
		    		<img src="${playerUser.iconUrl}" width="165px" height="130px" style="margin-left:35px"></img>
		    	</span>
		    	<label>&nbsp;</label>
	    	</div>
	    </li>
   		</c:if> 
	    <li>
	    	<label style="width:120px;">网盘用户编号<font class = "require">*</font></label>
	    	<input name="playerUser.userId" id="playerUser.userId" type="text" class="dfinput" maxlength="32" readonly="readonly" value="${playerUser.userId}"/>
	    </li>
	    <li>
	    	<label style="width:120px;">网盘用户昵称<font class = "require">*</font></label>
	    	<input name="playerUser.nickName" id="playerUser.nickName" type="text" class="dfinput" readonly="readonly" value="${playerUser.nickName}"/>
	    </li>
	    <li>
	    	<label style="width:120px;">网盘用户签名</label>
	    	<input name="playerUser.sign" id="playerUser.sign" type="text" class="dfinput" maxlength="1024" value="${playerUser.sign}"/>
	    </li>
	    <li>
	    	<label style="width:120px;">是否启用<font class = "require">*</font></label>
	    	<cite>
	    		<input name="playerUser.status" type="radio" value="0" <c:if test = "${playerUser.status == '0'}">checked="checked"</c:if>/>&nbsp;&nbsp;禁用
	    		&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input name="playerUser.status" type="radio" value="1" <c:if test = "${playerUser.status == '1'}">checked="checked"</c:if>/>&nbsp;&nbsp;启用
	    	</cite>
    	</li>
	    <li>
	    	<label style="width:120px;">网盘用户角色<font class = "require">*</font></label>
	    	<cite>
	    		<input name="playerUser.userRoleStatus" type="radio" value="0" <c:if test = "${playerUser.userRoleStatus == '0'}">checked="checked"</c:if>/>&nbsp;&nbsp;未通过审核
	    		&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input name="playerUser.userRoleStatus" type="radio" value="1" <c:if test = "${playerUser.userRoleStatus == '1'}">checked="checked"</c:if>/>&nbsp;&nbsp;高级用户
	    		&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input name="playerUser.userRoleStatus" type="radio" value="2" <c:if test = "${playerUser.userRoleStatus == '2'}">checked="checked"</c:if>/>&nbsp;&nbsp;站长
	    		&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input name="playerUser.userRoleStatus" type="radio" value="3" <c:if test = "${playerUser.userRoleStatus == '3'}">checked="checked"</c:if>/>&nbsp;&nbsp;运营用户
	    		&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input name="playerUser.userRoleStatus" type="radio" value="4" <c:if test = "${playerUser.userRoleStatus == '4'}">checked="checked"</c:if>/>&nbsp;&nbsp;特殊用户
	    		&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input name="playerUser.userRoleStatus" type="radio" value="9" <c:if test = "${playerUser.userRoleStatus == '9'}">checked="checked"</c:if>/>&nbsp;&nbsp;普通用户
	    	</cite>
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
