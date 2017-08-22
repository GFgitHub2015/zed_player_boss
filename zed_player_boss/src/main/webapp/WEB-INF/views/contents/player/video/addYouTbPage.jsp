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
		var fileUrl = $("#fileUrl").val();
		if(!fileUrl){
			layer.alert("URL格式有误!");
			return;
		}
		if(fileUrl.indexOf("watch?v=")<0){
			layer.alert("URL格式有误!");
			return;
		}
		var countryCode =$("#countryCode").val();
		if(!countryCode){
			layer.alert("请选择国家!");
			return;
		}
		if(!checkDate()){
			return;
		}
		if(!errorFound){
			openConfirmWindow('提示信息','是否确认提交 ？','确定', 'formSubmit()','closeConfirmWindow()','取消');
		}
	}
	function checkDate() {
	    var dateStart = $('#startTime').val();
	    var dateEnd= $('#endTime').val();
	    if(dateStart) {
	        dateStart = dateStart.replace(/-/g,'');
	    } else {
	        layer.alert('请选择上架时间');
	        return false;
	    }
	    if(dateEnd) {
	        dateEnd = dateEnd.replace(/-/g,'');
	    } else {
	    	layer.alert('请选择下架时间');
	        return false;
	    }
	    if(dateStart > dateEnd) {
	    	layer.alert('上架时间不能大于下架时间');
	        return false;
	    }
	    return true;
	}
	function formSubmit(){
		var url = '<s:url action="getYouTubeVideoDetail.action" />';
		var fileId = $("#fileUrl").val();
		fileId = fileId.substr(fileId.indexOf("watch?v=")+8);
		if(fileId.indexOf("&") > 0){
			fileId = fileId.substr(0, fileId.indexOf("&"));
		}
		LoadingPic.FullScreenShow();
		var data = {"playerRelationVideo.fileId": fileId};
		 $.ajax({
	         type: "POST",
	         url: url,
	         data: data,
	         dataType : "json",
	         success:function(data) {
	        	 LoadingPic.FullScreenHide();
	        	 if(data && data.fileName) {
	        		 if(data.type == 'live'){
	        			 closeConfirmWindow();
	            		 layer.alert("影片为直播类型!");
	            		 return;
	        		 }
	        		 $("#fileId").val(data.fileId);
	            	 $("#fileName").val(data.fileName);
	            	 $("#description").val(data.description);
	            	 $("#iconUrl").val(data.iconUrl);
	            	 $("#dimensionType").val(data.dimensionType);
	        		document.form.submit();
	        	 } else {
	        		 closeConfirmWindow();
	        		 layer.alert("影片已经存在!");
	        	 }
	         },
	         error : function (XMLHttpRequest, textStatus, errorThrown) {
	        	 LoadingPic.FullScreenHide();
	         }
		 }); 
	}
	function moveOption(e1, e2) {
		try{
			for(var i=0;i<e1.options.length;i++){
				if(e1.options[i].selected){
					var e = e1.options[i];
					e2.options.add(new Option(e.text, e.value));
					e1.remove(i);
					i=i-1
				}
			}
			document.form.countryCode.value=getvalue(document.form.selectedAreaCode);
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
	</script>
</head>
<body>
	<%@ include file="../../../include/navigation.jsp" %>
	<form method="post" name="form" id="form" action="<s:url action="addYouTubeVideo"/>" onsubmit="return false;">
		<s:token name="submittedToken"/>
		<input type="hidden" name="playerRelationVideo.fileId"        value="${playerRelationVideo.fileId}"             id="fileId" />
		<input type="hidden" name="playerRelationVideo.fileName"      value="${playerRelationVideo.fileName}"           id="fileName" />
		<input type="hidden" name="playerRelationVideo.description"   value="${playerRelationVideo.description}"        id="description" />
		<input type="hidden" name="playerRelationVideo.iconUrl"       value="${playerRelationVideo.iconUrl}"            id="iconUrl" />
		<input type="hidden" name="playerRelationVideo.dimensionType" value="${playerRelationVideo.dimensionType}"      id="dimensionType" />
		<input type="hidden" name="playerRelationVideo.countryCode"   id="countryCode" />
	    <div class="formbody">
	    <div class="formtitle"><span>新增YouTube影片</span></div>
		    <ul class="forminfo">
			    <li>
			    	<label>视频链接<font class = "require">*</font></label>
			    	<input name="" id="fileUrl" type="text" class="dfinput validate[required,minSize[1],maxSize[100]]"  maxlength="100"  /> 
			    </li>
			     <li id="country">
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
		    	<li id="type">
		            <label>标签类型<font class = "require">*</font></label>
		            <input type="radio" name="playerRelationVideo.flag" value="-1" checked="checked"/>无标签
		            <input type="radio" name="playerRelationVideo.flag" value="2"  />最热
		            <input type="radio" name="playerRelationVideo.flag" value="1"/>最新
		        </li>
			    <li>
			    	<label>上架时间<font class = "require">*</font></label>
			    	<input  name="playerRelationVideo.startTime" id="startTime" class="Wdate" style="width: 150px;"  readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
			    </li>
			    <li>
			    	<label>下架时间<font class = "require">*</font></label>
			    	<input  name="playerRelationVideo.endTime"  id="endTime" class="Wdate" style="width: 150px;"  readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
			    </li>
		    	<label>&nbsp;</label>
		    	<input name="" type="submit" class="btn" value="确定"/>
			    <input name="" type="button" class="btn" value="返回" onclick="javascript:history.go(-1)"/>
			    </li>
	   		 </ul>
	  	</div>
	</form>
</body>
</html>
