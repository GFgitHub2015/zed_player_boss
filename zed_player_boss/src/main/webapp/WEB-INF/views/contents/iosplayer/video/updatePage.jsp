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

function process(errorFound){
	if(!checkDate()){
		return;
	}
	if(!errorFound){
		openConfirmWindow('提示信息','是否确认提交 ？','确定', 'formSubmit()','closeConfirmWindow()','取消');
	}
}

function formSubmit(){

	LoadingPic.FullScreenShow();
	document.form.submit();
}

function changeFileId(){
	var url = '<s:url action="getResourceInfo.action" />';
	var fileId = $("#fileId").val();
	if(!fileId){
		return;
	}
	var data = {"fileId": fileId};
	 $.ajax({
         type: "POST",
         url: url,
         data: data,
         dataType : "json",
         success:function(data){
        	 if(data){
            	 $("#videoName").val(data.fileName);
            	 $("#iconUrl").val(data.imgUrl);
            	 $("#resourceImg").attr('src',data.imgUrl); 
        		 $("#resourceImgLi").show();
        	 }else{
        		 layer.alert("影片ID无效!");
        		 $("#iconUrl").val('');
            	 $("#resourceImg").attr('src',""); 
        		 $("#resourceImgLi").hide();
        	 }
         },
         error : function (XMLHttpRequest, textStatus, errorThrown) {
        	 //alert(textStatus+"=="+errorThrown);
         }
	 }); 
}

function checkDate(){

	var imageUpload = $("#imageUpload").val();
	var iconUrl = $("#iconUrl").val();
	/* if(!imageUpload&&!iconUrl){
        layer.alert('请上传文件!');
        return false;
	} */
    
    return true;
}
</script>

</head>

<body>

<%@ include file="../../../include/navigation.jsp" %>

<form method="post" name="form" id="form" enctype="multipart/form-data"  action="<s:url action="addHotVideo"/>" onsubmit="return false;">
<s:token name="submittedToken"/>
	<input type="hidden" name="hotVideo.uid"           value="${hotVideo.uid}"             id="uid" />
	<input type="hidden" name="hotVideo.videoId"       value="${hotVideo.videoId}"             id="videoId" />
	<input type="hidden" name="hotVideo.videoName"     value="${hotVideo.videoName}"             id="videoName" />
	<input type="hidden" name="hotVideo.videoMemo"     value="${hotVideo.videoMemo}"             id="videoMemo" />
	<input type="hidden" name="hotVideo.videoState"    value="${hotVideo.videoState}"             id="videoState" />
	<input type="hidden" name="hotVideo.videoDuration" value="${hotVideo.videoDuration}"             id="videoDuration" />
	<input type="hidden" name="hotVideo.iconUrl"       value="${hotVideo.iconUrl}"             id="iconUrl" />
	<input type="hidden" name="hotVideo.createUser"    value="${hotVideo.createUser}"             id="createUser" />
	<input type="hidden" name="hotVideo.createTime"    value="${hotVideo.createTime}"             id="createTime" />
	<input type="hidden" name="hotVideo.updateUser"    value="${hotVideo.updateUser}"             id="updateUser" />
	<input type="hidden" name="hotVideo.updateTime"    value="${hotVideo.updateTime}"             id="updateTime" />
	<input type="hidden" name="hotVideo.startTime"     value="${hotVideo.startTime}"             id="startTime" />
	<input type="hidden" name="hotVideo.countryCode"   value="${hotVideo.countryCode}"             id="countryCode" />
    <div class="formbody">
    <div class="formtitle"><span>新增IOS热门影片推荐</span></div>
	    <ul class="forminfo">
	    <li>
	    	<label>影片URL<font class = "require">*</font></label>
	    	<input name="hotVideo.videoUrl" id="videoUrl" type="text" class="dfinput validate[required,minSize[1],maxSize[100]]"  maxlength="100"  /> 
	    </li>
    
	    <li>
	    	<label>影片海报<font class = "require">*</font></label>
	        <input name="imageUpload" id="imageUpload" type="file"  accept="image/*" />
	        <i class = "tips">图片尺寸300*400</i>
	    </li> 
    	<label>&nbsp;</label>
	    <input name="" type="submit" class="btn" value="保存"/>
	    <input name="" type="button" class="btn" value="返回" onclick="javascript:history.go(-1)"/>
	    </li>
    </ul>
	</form>
</body>
</html>
