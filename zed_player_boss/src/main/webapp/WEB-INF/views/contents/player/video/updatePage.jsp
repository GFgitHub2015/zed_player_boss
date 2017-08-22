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
	function process(errorFound) {
		if(!checkDate()) {
			return;
		}
		if(!errorFound){
			openConfirmWindow('提示信息','是否确认提交 ？','确定', 'formSubmit()','closeConfirmWindow()','取消');
		}
	}
	function checkDate() {
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
	function formSubmit() {
		LoadingPic.FullScreenShow();
		document.form.submit();
	}
	</script>
</head>
<body>
<%@ include file="../../../include/navigation.jsp" %>
<form method="post" name="form" id="form" enctype="multipart/form-data"  action="<s:url action="updateVideo"/>" onsubmit="return false;">
<s:token name="submittedToken"/>
	<input type="hidden" name="playerRelationVideo.uid" value="<s:property value="playerRelationVideo.uid"/>"/>
	<input type="hidden" name="playerRelationVideo.iconUrl" id="iconUrl" value="<s:property value="playerRelationVideo.iconUrl"/>" />
    <div class="formbody">
    	<div class="formtitle">
	    	<span>修改
		    	<s:if test="playerRelationVideo.origin == 0">
		          		GBox
				</s:if>
				<s:else>
					YouTube
				</s:else>
	  		影片</span>
  		</div>
	    <ul class="forminfo">
	     <li>
             <label>状态:</label>
             <select  name="playerRelationVideo.status"  class="scinput form-control" >
             	<option value="1" <c:if test = "${playerRelationVideo.status == 1}">selected="selected"</c:if>>上架</option>
             	<option value="0" <c:if test = "${playerRelationVideo.status == 0}">selected="selected"</c:if>>下架</option>
             </select>
         </li>
         <li id="resourceImgLi">
	    	<img id="resourceImg"  src="<s:property value="playerRelationVideo.iconUrl"/>" alt="影片海报" />
	    </li> 
	    <li>
	    	<label>影片海报:</label>
	        <input name="imageUpload" id="imageUpload" type="file"  accept="image/*"/>
	        <i class = "tips">图片尺寸300*400</i>
	    </li> 
	    <s:if test="playerRelationVideo.dimensionType == 2">
		    <li>
	            <label>标签类型:</label>
	            <c:if test="${playerRelationVideo.origin==0}">
	            <input type="radio" name="playerRelationVideo.flag" value="0" <c:if test="${playerRelationVideo.flag==0}">checked="checked"</c:if> />自动判定
	            </c:if>
	             <c:if test="${playerRelationVideo.origin==1}">
	            <input type="radio" name="playerRelationVideo.flag" value="-1" <c:if test="${playerRelationVideo.flag==-1}">checked="checked"</c:if> />无标签
	            </c:if>
	            <input type="radio" name="playerRelationVideo.flag" value="2" <c:if test="${playerRelationVideo.flag==2}">checked="checked"</c:if> />最热
	            <input type="radio" name="playerRelationVideo.flag" value="1" <c:if test="${playerRelationVideo.flag==1}">checked="checked"</c:if> />最新
	        </li>
        </s:if>
        <s:if test="playerRelationVideo.dimensionType == 2 || playerRelationVideo.origin == 1">
	        <li>
		    	<label>国家选择:</label>
				<select name="playerRelationVideo.countryCode" id="playerRelationVideo.countryCode" class="dayinput form-control" style="width:500px;">
					<c:if test="${not empty playerCountryList}">
						<s:iterator value="playerCountryList" var="playerCountry">
							<option value="<s:property value="#playerCountry.countryCode"/>" <s:if test="%{#playerCountry.countryCode ==playerRelationVideo.countryCode}">selected="selected"</s:if>>
								<s:property value="#playerCountry.countryNameZh"/>（<s:property value="#playerCountry.countryCode"/>）
							</option>
						</s:iterator>
					</c:if>
				</select>
		    </li>
	    </s:if>
        <li>
	    	<label>上架时间:</label>
	    	<input  name="playerRelationVideo.startTime" id="startTime" class="Wdate" value="<fmt:formatDate value="${playerRelationVideo.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" style="width: 150px;"  readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
	    </li>
	    <li>
	    	<label>下架时间:</label>
	    	<input  name="playerRelationVideo.endTime"  id="endTime" class="Wdate" value="<fmt:formatDate value="${playerRelationVideo.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"style="width: 150px;"  readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
	    </li>
	    <label>&nbsp;</label>
	    <input name="" type="submit" class="btn" value="保存"/>
	    <input name="" type="button" class="btn" value="返回" onclick="javascript:history.go(-1)"/>
    </ul>
	</form>
</body>
</html>
