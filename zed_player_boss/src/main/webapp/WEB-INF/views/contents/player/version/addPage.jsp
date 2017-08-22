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
	<style>
		.require{color:#FF0000;padding-right:0 2px;}
		select option {height: 32px;line-height: 32px;text-indent: 10px;}
	</style>
<script type="text/javascript">
var addPage = {};
var checkSubmitFlg = false;
addPage.clearForm = function (){
	$("[name='version.versionId'").val("");
	$("[name='version.versionCode'").val("");
	$("[name='version.packageName'").val("");
	$("[name='version.channel'").val("");
	$("[name='version.version.downloadUrl'").val("");
	$("[name='version.upLoadApp'").val("");
}
addPage.showTypeUrl = function (){
    $("#url").show();
	$("#upLoadApk").hide();
	addPage.clearForm();
}
addPage.showTypeUpload = function (){
	$("#url").hide();
	$("#upLoadApk").show();
	addPage.clearForm();
};
addPage.changeType = function (){
	$("[id='version.downloadUrl']").removeAttr("value");
	$("#upLoadApp").removeAttr("value");
	
	if($("#type").val() == "0"){
		addPage.showTypeUrl();
    }else{
    	addPage.showTypeUpload();
    }
};

addPage.setFormWithFileName = function(fileName){
	var strFileName=fileName.replace(/^.+?\\([^\\]+?)(\.[^\.\\]*?)?$/gi,"$1");  //正则表达式获取文件名，不带后缀
	var array = strFileName.split("_");
	//console.log(array);
	$("[name='version.channel'").val(array[0] + "_" + array[1]);
	$("[name='version.versionCode'").val(array[3]);
	$("[name='version.versionId'").val(array[4]);
	$("[name='version.packageName'").val(array[5]);
	$("[name='version.autoUpLoadAppFileName'").val( array[2] + "_" + array[3] + "." + fileName.replace(/.+\./, "").toLowerCase()  );
	//console.log( $("[name='version.autoUpLoadAppFileName'").val() );
};


$(document).ready(function(e) {
	addPage.changeType();
	$('#type').change( function (){ addPage.changeType(); } );
	$("[name='upLoadApp']").change( function (){
		var fileName = $(this).attr('value');
		addPage.setFormWithFileName(fileName);
	});
	
	$('#form').validationEngine('attach', { 
	    promptPosition: 'centerRight', 
	    scroll: false
	  });	  
	$('#form').bind('jqv.form.result', function(event, errorFound){process(errorFound)});
});

function process(errorFound){
	if(!errorFound){
	// 判断版本号ID是否已经添加过
	$.ajax({
		type: "POST",
		url: "findMaxId.action",
		data: {"versionId":$("[name='version.versionId'").val(), "channel":$("[name='version.channel'").val()},
		success: function(msg){
			var obj = JSON.parse(msg);
			if(obj.data["result"] == "1"){
				openAlertmWindow('提示信息','同渠道下版本编号不能重复','确定');
			}else{
				openConfirmWindow('提示信息','是否确认提交 ？','确定', 'formSubmit()','closeConfirmWindow()','取消');
			}
		}
	});
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

<%@ include file="../../../include/navigation.jsp" %>

<div class="formbody">
<div class="formtitle"><span>添加版本信息</span></div>
<form method="post" name="form" id="form" enctype="multipart/form-data" action="<s:url action="add"/>" onsubmit="return false;">
    <ul class="forminfo">
    	<li>
		    <label style="width:120px;">上传类型</label>
		   	<select id="type" name="type" class="dayinput form-control" style="width:120px;">
	             <option value="0" >资源链接</option>
				 <option value="1" selected="selected">本地上传</option>
			</select>	
      	</li>
    	<li id = "url">
	        <label style="width:120px;">资源链接</label>
	        <input name="version.downloadUrl" id="version.downloadUrl" type="text" class="dfinput validate[required,minSize[1]]"/><i class = "tips">请输入apk链接地址</i>
     	</li>
    	<li id = "upLoadApk">
	        <label style="width:120px;">本地上传</label>
	        <input id="upLoadApp" name="upLoadApp" type="file" value="" />
	        <i class = "tips">apk大小不能超过25M,只支持apk格式</i>
     	</li>
	    <li>
	    	<label style="width:120px;">版本编号<font class = "require">*</font></label>
	    	<input name="version.versionId" id="version.versionId" type="text" class="dfinput validate[required,minSize[1],maxSize[100],custom[onlyNumber]]" maxlength="100"/><i class = "tips">请输入版本编号</i>
	    </li>
	    <li>
	    	<label style="width:120px;">版本号<font class = "require">*</font></label>
	    	<input name="version.versionCode" id="version.versionCode" type="text" class="dfinput validate[required,minSize[1]]"  maxlength="50"/><i class = "tips">请输入版本号,如:v2.0.0</i>
	    </li>
	    <li>
	    	<label style="width:120px;">app包名<font class = "require">*</font></label>
	    	<input name="version.packageName" id="version.packageName" type="text" class="dfinput validate[required,minSize[1],maxSize[100]]" maxlength="100"/><i class = "tips">请输入app包名</i>
	    </li>
    	<li>
	    	<label style="width:120px;">渠道<font class = "require">*</font></label>
		   	<input name="version.channel" id="version.channel" type="text" class="dfinput validate[required,minSize[1],maxSize[100]]" maxlength="100"/>
	    </li>
    	<li>
	    	<label style="width:120px;">下载来源<font class = "require">*</font></label>
	    	<select id="version.source" name="version.source" class="dayinput form-control" style="width:120px;">
				 <option value="2" selected="selected">官方渠道</option>
	             <option value="1" >GooglePlayer</option>
			</select>
	    </li>
	    <li class="hide">
	    	<label style="width:120px;">描述<font class = "require">*</font></label>
	    	<input name="version.description" id="version.description" type="text" class="dfinput validate[required,minSize[1]]" maxlength="100"/><i class = "tips">请输入描述信息</i>
	    </li>
		<li>
	    	<label style="width:120px;">是否强制升级<font class = "require">*</font></label>
	    	<cite>
	    		<input name="version.isMust" type="radio" value="1" checked="checked"/>&nbsp;&nbsp;普通升级
	    		&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input name="version.isMust" type="radio" value="2"/>&nbsp;&nbsp;强制升级
	    	</cite>
    	</li>
    	<li class="hide">
	    	<label style="width:120px;">应用类型<font class = "require">*</font></label>
	    	<input name="version.appType" id="version.appType" type="text" value="1" class="dfinput validate[required,minSize[1],maxSize[11],custom[onlyNumber]]" maxlength="11"/><i class = "tips">请输入应用类型</i>
	    </li>
	    <li class="hide">
	    	<input name="version.autoUpLoadAppFileName" id="version.autoUpLoadAppFileName" type="text" value="1" /><i class = "tips">请输入应用类型</i>
	    </li>
	    
	    <!-- 	<li>
	    	<label style="width:120px;">状态<font class = "require">*</font></label>
	    	<cite>
	    		<input name="version.status" type="radio" value="0" checked="checked"/>&nbsp;&nbsp;旧版本
	    		&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input name="version.status" type="radio" value="1"/>&nbsp;&nbsp;最新版本
	    	</cite>
    	</li> -->
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
