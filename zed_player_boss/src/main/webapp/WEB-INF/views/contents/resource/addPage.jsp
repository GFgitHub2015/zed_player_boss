<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../include/resource.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

	<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />validationEngine.jquery.css"/>
	<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />loading.css"/>
	<!--common-->
    <link href="<s:property value="themePath" />css/style.css" rel="stylesheet">
    <link href="<s:property value="themePath" />css/style-responsive.css" rel="stylesheet">  
    <link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />style.css"/>
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />window.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />function.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.validationEngine-lang.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.validationEngine.min.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />loading.js"></script>
	
	<style>
		.require{color:#FF0000;padding-right:0 2px;}
		select option {height: 32px;line-height: 32px;text-indent: 10px;}
	</style>

<script type="text/javascript">
var checkSubmitFlg = false;
$(document).ready(function(e) {
	
	$('#form').validationEngine('attach', { 
	    promptPosition: 'centerRight', 
	    scroll: false
	  });	  
	
	$('#form').bind('jqv.form.result', function(event, errorFound){process(errorFound)});
});



function clearForm() {
	window.location = '<s:url action="addPage" />';
}

function process(errorFound){
	if(!errorFound){
		openConfirmWindow('提示信息','是否确认添加？','确定', 'formSubmit()','closeConfirmWindow()','取消');
	}
}
function formSubmit(){
	if(checkSubmitFlg==true){
		openAlertmWindow('提示信息','请求已经处理提交!','确定');
	}else{
		checkSubmitFlg = true;
		LoadingPic.FullScreenShow();
		document.form.submit();
	}
}

</script>

</head>

<body>

	<%@ include file="../../include/navigation.jsp" %>

<form  method="post" name="form" id="form" action="<s:url action="add"/>" enctype="multipart/form-data" onsubmit="return false;">
    <div class="formbody">
    <div class="formtitle"><span>菜单信息</span></div>
    <ul class="forminfo">
    <li><label>菜单名称<font class = "require">*</font></label><input name="resource.resourceName" id="resource.resourceName" type="text" class="dfinput validate[required,minSize[1],maxSize[50]]" maxlength="50"/><i class = "tips">字符长度不超过50</i></li>
    <li><label>菜单Key<font class = "require">*</font></label><input name="resource.resourceKey" id="resource.resourceKey" type="text" class="dfinput validate[required,minSize[1],maxSize[100]]" maxlength="100"/><i class = "tips">字符长度不超过50</i></li>
<!--     <li> -->
<!--         <label>菜单图片</label> -->
<!--         <input name="upLoadPicture" type="file" value="" /> -->
<!--         <i class = "tips">一级菜单和顶部按钮需要上出图片,图片大小不能超过2M,支持jpg,jpeg,png,gif,bmp格式</i> -->
<!--      </li> -->
    <li><label>菜单地址</label><input name="resource.resourceUrl" id="resource.resourceUrl" type="text" class="dfinput" value="" maxlength="200"/><i class = "tips">输入有效的菜单地址，字符长度不超过200,二级菜单为非必填</i></li>
    <li><label>菜单顺序</label><input name="resource.resourceOrder" id="resource.resourceOrder" type="text" class="dfinput validate[custom[onlyNumber]]" maxlength="5"/></li>    
    <li>
    	<label>菜单等级<font class = "require">*</font></label>    	
    	<select name="resource.resourceLevel" class="dayinput form-control" style="width:120px;">
			<option value="1">一级菜单</option>
			<option value="2">二级菜单</option>
			<option value="3">三级菜单</option>
			<option value="4">顶部按钮</option>
			<option value="5">操作按钮</option>
		</select>
    </li>
    <li>
    	<label>是否禁用<font class = "require">*</font></label>
    	<cite>
    		<input name="resource.enable" type="radio" value="0" checked="checked"/>&nbsp;&nbsp;禁用
    		&nbsp;&nbsp;&nbsp;&nbsp;
    		<input name="resource.enable" type="radio" value="1"/>&nbsp;&nbsp;正常
    	</cite>
    </li>
    <li><label>方法名称</label><input name="resource.functionName" id="resource.functionName" type="text" class="dfinput validate[minSize[1],maxSize[50]]" maxlength="50"/><i class = "tips">事件方法名称(如果是按钮请写按钮控件的js方法)</i></li>
    <li><label>描述<font class = "require">*</font></label><input name="resource.description" id="resource.description" type="text" class="dfinput validate[required,minSize[1],maxSize[200]]" maxlength="200"/><i class = "tips">描述字符长度不超过200</i></li>        
    <li>
    	<label>上级菜单</label>
    	<select name="resource.partentId" class="dayinput form-control" style="width:180px;">
			<option value="999">无</option>
			<c:forEach var="menu" items="${resourceMenu}">
					<option value="${menu.resourceId}">${menu.resourceName}</option>
					<c:forEach items="${menu.children}" var="secondeMenu">
						<option value="${secondeMenu.resourceId}">&nbsp;&nbsp;&nbsp;${secondeMenu.resourceName}</option>
							<c:forEach items="${secondeMenu.children}" var="thirdMenu">
								<option value="${thirdMenu.resourceId}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${thirdMenu.resourceName}</option>
							</c:forEach>
					</c:forEach>				
			</c:forEach>
		</select>
    </li>    
    
    
    <li><label>&nbsp;</label>
    <input name="" type="submit" class="btn" value="保存"/>
    <input name="" type="button" class="btn" value="重置" onclick="clearForm();"/>
    </li>
    </ul>
    
    </div>
    

</form>
</body>
</html>
