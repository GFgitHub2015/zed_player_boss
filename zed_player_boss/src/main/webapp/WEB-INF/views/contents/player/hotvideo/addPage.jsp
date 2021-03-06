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
	if(!imageUpload&&!iconUrl){
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

<!--
function moveOption(e1, e2){
	try{
		for(var i=0;i<e1.options.length;i++){
			if(e1.options[i].selected){
				var e = e1.options[i];
				e2.options.add(new Option(e.text, e.value));
				e1.remove(i);
				i=i-1
			}
		}
		document.form.areaCode.value=getvalue(document.form.selectedAreaCode);
	}
	catch(e){}
}
function getvalue(geto){
	var allvalue = "";
	for(var i=0;i<geto.options.length;i++){
		allvalue +=geto.options[i].value + ",";
	}
	return allvalue;
}
//-->
</script>

</head>

<body>

<%@ include file="../../../include/navigation.jsp" %>

<form method="post" name="form" id="form" enctype="multipart/form-data"  action="<s:url action="addHotVideo"/>" onsubmit="return false;">
<s:token name="submittedToken"/>
	<input type="hidden" name="hotVideo.uid" id="uid" value="${hotVideo.uid}"/>
	<input type="hidden" name="hotVideo.videoState" id="videoState" value="0"  />
	<input type="hidden" name="hotVideo.createUser" id="createUser" value="${hotVideo.createUser}"/>
	<input type="hidden" name="hotVideo.createTime" id="createTime" value="${hotVideo.createTime}"/>
	<input type="hidden" name="iconUrl" id="iconUrl"  />
	<input type="hidden" name="areaCode" id="areaCode" />
    <div class="formbody">
    <div class="formtitle"><span>新增热门影片推荐</span></div>
	    <ul class="forminfo">
	    <li>
	    	<label>片名<font class = "require">*</font></label>
	    	<input name="hotVideo.videoName" id="videoName" type="text" class="dfinput validate[required,minSize[1],maxSize[100]]" value="${hotVideo.videoName}"maxlength="100"  /> 
	    </li>
	    <li>
	    	<label>影片ID<font class = "require">*</font></label>
	    	<input name="hotVideo.fileId" id="fileId" type="text" class="dfinput validate[required,minSize[1],maxSize[100]]"  value="${hotVideo.fileId}" onblur="changeFileId()"/> 
	    </li>
        <li>
            <label>标签类型<font class = "require">*</font></label>
            <input type="radio" name="hotVideo.tagType" value="0" checked="checked"/>自动判定
            <input type="radio" name="hotVideo.tagType" value="1"  />最热
            <input type="radio" name="hotVideo.tagType" value="2"/>最新
        </li>
	    <li style="display: none;">
	    	<label>播放次数<font class = "require">*</font></label>
	    	<input name="hotVideo.playTime" id="playTime" type="text" class="dfinput validate[required,minSize[1],maxSize[11],custom[number]]" value="0"/> 
	    </li>
	    <li id="resourceImgLi" style="display: none;">
	    	<img id="resourceImg"  src="" alt="影片海报" />
	    </li> 
	    
	    <li>
	    	<label>影片海报<font class = "require">*</font></label>
	        <input name="imageUpload" id="imageUpload" type="file"  accept="image/*"/>
	        <i class = "tips">图片尺寸300*400</i>
	    </li> 
	    <li>
	    	<label>国家选择<font class = "require">*</font></label>
			<select multiple name="unSelectedAreaCode" id="unSelectedAreaCode" class="dayinput form-control unselected" ondblclick="moveOption(document.form.unSelectedAreaCode, document.form.selectedAreaCode)">
				<c:if test="${not empty playerCountryList}">
					<s:iterator value="playerCountryList" var="playerCountry">
						<option value="<s:property value="#playerCountry.countryCode"/>">
							<s:property value="#playerCountry.countryNameZh"/>（<s:property value="#playerCountry.countryCode"/>）
						</option>
					</s:iterator>
				</c:if>
			</select>
			<div class="country-btn-wrap" style="float:left;padding-top: 26px;">
				<input value="&gt;&gt;" type="button" class="btn" onclick="moveOption(document.form.unSelectedAreaCode, document.form.selectedAreaCode)"><br/>
				<input value="&lt;&lt;" type="button" class="btn" onclick="moveOption(document.form.selectedAreaCode, document.form.unSelectedAreaCode)">
			</div>
			<select multiple id="selectedAreaCode" name="selectedAreaCode" class="selected" ondblclick="moveOption(document.form.selectedAreaCode, document.form.unSelectedAreaCode)">
			</select>
	    </li>
	    <li>
	    	<label>上线时间<font class = "require">*</font></label>
	    	<input  name="hotVideo.startTime" id="startTime" class="Wdate" value='<fmt:formatDate value="${hotVideo.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>' style="width: 150px;"  readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
	    </li>
	    <li>
	    	<label>下线时间<font class = "require">*</font></label>
	    	<input  name="hotVideo.endTime"  id="endTime" class="Wdate" value='<fmt:formatDate value="${hotVideo.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>' style="width: 150px;"  readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
	    </li>
	    <li><label>推荐理由<font class = "require">*</font></label>
	    	<textarea rows="10" cols="100" name="hotVideo.hotReason" class="validate[required]"  id="hotReason"  >${hotVideo.hotReason}</textarea>
	     </li>    
	    	<label>&nbsp;</label>
		    <input name="" type="submit" class="btn" value="保存"/>
		    <input name="" type="button" class="btn" value="返回" onclick="javascript:history.go(-1)"/>
	    </li>
    </ul>
	</form>
</body>
</html>
