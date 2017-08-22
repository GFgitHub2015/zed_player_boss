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
	var videoUrl = $("#videoUrl").val();
	if(!videoUrl){
		layer.alert("URL格式有误!");
		return;
	}
	//https://www.youtube.com/watch?v=4ck_bFc2s2g
	if(videoUrl.indexOf("watch?v=")<0){
		layer.alert("URL格式有误!");
		return;
	}
	if(!errorFound){
		openConfirmWindow('提示信息','是否确认提交 ？','确定', 'formSubmit()','closeConfirmWindow()','取消');
	}
}

function formSubmit(){

	var url = '<s:url action="getYouTubeVideoDetail.action" />';
	var videoId = $("#videoUrl").val();
	videoId = videoId.substr(videoId.indexOf("watch?v=")+8);
	if(videoId.indexOf("&")>0){
		videoId = videoId.substr(0,videoId.indexOf("&"));
	}
	LoadingPic.FullScreenShow();
	var data = {"videoId": videoId};
	 $.ajax({
         type: "POST",
         url: url,
         data: data,
         dataType : "json",
         success:function(data){
        	 LoadingPic.FullScreenHide();
        	 if(data&&data.videoName){
        		 if(data.liveBroadcastContent=='live'){
        			 closeConfirmWindow();
            		 layer.alert("影片为直播类型!");
            		 return;
        		 }
        		 $("#videoId").val(data.videoId);
            	 $("#videoName").val(data.videoName);
            	 $("#iconUrl").val(data.iconUrl);
            	 $("#videoMemo").val(data.videoMemo);
            	 $("#videoDuration").val(data.videoDuration);
        		document.form.submit();
        	 }else if(data&&data.videoId){
        		 closeConfirmWindow();
        		 layer.alert("影片Url无效!");
        	 }else {
        		 closeConfirmWindow();
        		 layer.alert("影片已经存在!");
        	 }
         },
         error : function (XMLHttpRequest, textStatus, errorThrown) {
        	 LoadingPic.FullScreenHide();
         }
	 }); 
}


</script>

</head>

<body>

<%@ include file="../../../include/navigation.jsp" %>

<form method="post" name="form" id="form"   action="<s:url action="addVideo"/>" onsubmit="return false;">
<s:token name="submittedToken"/>
	<input type="hidden" name="video.uid"           value="${video.uid}"             id="uid" />
	<input type="hidden" name="video.videoId"       value="${video.videoId}"             id="videoId" />
	<input type="hidden" name="video.videoName"     value="${video.videoName}"             id="videoName" />
	<input type="hidden" name="video.videoMemo"     value="${video.videoMemo}"             id="videoMemo" />
	<input type="hidden" name="video.videoState"    value="${video.videoState}"             id="videoState" />
	<input type="hidden" name="video.videoDuration" value="${video.videoDuration}"             id="videoDuration" />
	<input type="hidden" name="video.iconUrl"       value="${video.iconUrl}"             id="iconUrl" />
	<input type="hidden" name="video.createUser"    value="${video.createUser}"             id="createUser" />
	<input type="hidden" name="video.createTime"    value="${video.createTime}"             id="createTime" />
	<input type="hidden" name="video.updateUser"    value="${video.updateUser}"             id="updateUser" />
	<input type="hidden" name="video.updateTime"    value="${video.updateTime}"             id="updateTime" />
	<input type="hidden" name="video.startTime"     value="${video.startTime}"             id="startTime" />
	<input type="hidden" name="video.countryCode"   value="${video.countryCode}"             id="countryCode" />
	<input type="hidden" name="video.dimensionType"   value="2"             id="dimensionType" />
	<input type="hidden" name="video.recommendType"   value="1"             id="recommendType" />
    <div class="formbody">
    <div class="formtitle"><span>新增IOS热门影片推荐</span></div>
	    <ul class="forminfo">
	    <li>
	    	<label>影片URL<font class = "require">*</font></label>
	    	<input name="video.videoUrl" id="videoUrl" type="text" class="dfinput validate[required,minSize[1],maxSize[100]]"  maxlength="100"  onchange="changeFileId()" /> 
	    </li>
    	<label>&nbsp;</label>
	    <input name="" type="submit" class="btn" value="保存"/>
	    <input name="" type="button" class="btn" value="返回" onclick="javascript:history.go(-1)"/>
	    </li>
    </ul>
	</form>
</body>
</html>
