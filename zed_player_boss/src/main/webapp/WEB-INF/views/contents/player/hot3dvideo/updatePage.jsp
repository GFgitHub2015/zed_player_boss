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
	<script type="text/javascript" src="<s:property value="jsPath" />loading.js"></script>
    <script type="text/javascript" src="<s:property value="jsPath" />My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />layer/layer.js"></script>
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

function validate(){
	return true;
}

function clearForm() {
	window.location = '<s:url action="updatePage" />?uid=${hotVideo.uid}';
}

function process(errorFound){
	if(!checkDate()){
		return;
	}
    if(!errorFound) {
    	openConfirmWindow('提示信息','是否确认修改 ？','确定', 'formSubmit()','closeConfirmWindow()','取消');
    }
}


function formSubmit(){

	LoadingPic.FullScreenShow();
	document.form.submit();
}


function checkDate(){
	var imageUpload = $("#imageUpload").val();
	var imageUploadOld = $("#imageUploadOld").val();
	if(!imageUpload&&!imageUploadOld){
        layer.alert('请上传文件!');
        return false;
	}
    var dateStart = $('#startTime').val();
    var dateEnd= $('#endTime').val();
    if(dateStart){
        dateStart = dateStart.replace(/-/g,'');
    }else{
        layer.alert('请选择开始时间');
        return false;
    }
    if(dateEnd){
        dateEnd = dateEnd.replace(/-/g,'');
    }else{
    	layer.alert('请选择结束时间');
        return false;
    }
    if(dateStart > dateEnd){
    	layer.alert('开始时间不能大于结束时间');
        return false;
    }
    return true;
}

</script>
</head>
<body>
<%@ include file="../../../include/navigation.jsp" %>
<form  method="post" name="form" id="form" enctype="multipart/form-data"  action="<s:url action="updateHotVideo"/>" onsubmit="return false;">
<s:token name="submittedToken"/>
	<input type="hidden" name="page.pageNo" id="backPageNo" value="${page.pageNo}"/>
	<input type="hidden" name="hotVideo.uid" id="uid" value="${hotVideo.uid}"/>
	<input type="hidden" name="hotVideo.videoState" id="videoState" value="${hotVideo.videoState}"  />
	<input type="hidden" name="hotVideo.createUser" id="createUser" value="${hotVideo.createUser}"/>
	<input type="hidden" name="hotVideo.createTime" id="createTime" value="${hotVideo.createTime}"/>
	<input type="hidden" name="hotVideo.countryCode" id="hotVideo.countryCode"  value="${hotVideo.countryCode}" />
    <div class="formbody">
    <div class="formtitle"><span>修改3D热门影片推荐</span></div>
	    <ul class="forminfo">
	    <li>
	    	<label>片名<font class = "require">*</font></label>
	    	<input name="hotVideo.videoName" id="videoName" type="text" class="dfinput validate[required,minSize[1],maxSize[100]]" value="${hotVideo.videoName}"maxlength="100"  /> 
	    </li>
	    <li>
	    	<label>影片ID<font class = "require">*</font></label>
	    	<input name="hotVideo.fileId" id="fileId" type="text" class="dfinput validate[required,minSize[1],maxSize[100]]"  value="${hotVideo.fileId}"/> 
	    </li>
	    <li>
	    	<label>影片海报<font class = "require">*</font></label>
	    	<img src="${hotVideo.iconUrl }" alt="影片海报" />
	    </li> 
	    <li>
	        <input name="imageUpload" id="imageUpload" type="file"  accept="image/*"/>
	        <input name="iconUrl" id="imageUploadOld"  type="hidden"  value="${hotVideo.iconUrl }"/>
	        <i class = "tips">图片尺寸300*400</i>
	    </li> 
	    <li>
	    	<label>上线时间<font class = "require">*</font></label>
	    	<input  name="hotVideo.startTime" class="Wdate"  id="startTime"  value='<fmt:formatDate value="${hotVideo.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>' style="width: 150px;"  readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
	    </li>
	    <li>
	    	<label>下线时间<font class = "require">*</font></label>
	    	<input  name="hotVideo.endTime" id="endTime"  class="Wdate" value='<fmt:formatDate value="${hotVideo.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>' style="width: 150px;"  readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
	    </li>
	    <li><label>推荐理由<font class = "require">*</font></label>
	    	<textarea rows="10" cols="100" name="hotVideo.hotReason" id="hotReason"  >${hotVideo.hotReason}</textarea>
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
