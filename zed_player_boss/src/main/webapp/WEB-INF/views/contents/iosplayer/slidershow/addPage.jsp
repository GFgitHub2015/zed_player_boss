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
	<script type="text/javascript" src="<s:property value="jsPath" />layer/layer.js"></script>
	<style>
		.require{color:#FF0000;padding-right:0 2px;}
		select option {height: 32px;line-height: 32px;text-indent: 10px;}
	</style>
<script type="text/javascript">
var checkSubmitFlg = false;

$(document).ready(function(e) {
	changeType();
	$('#form').validationEngine('attach', { 
	    promptPosition: 'centerRight', 
	    scroll: false
	  });	  
	
	$("[name='playerSliderShow.type']").trigger('change');
	
	$("input[name='playerSliderShow.type']").on('change',function(){
		changeType();
	});
	
	
	$('#form').bind('jqv.form.result', function(event, errorFound){process(errorFound)});
});

function changeType(){
	$("[name='playerSliderShow.value']").removeAttr("value");
	if($("input[name='playerSliderShow.type']:eq(0)").attr("checked")){
		$("#videovalue").removeAttr("value");
		$("#videovalue").removeClass('validate[required,minSize[1],maxSize[1024],ajax[isFileIdExist]]');
		$("#linkvalue").addClass('validate[required,minSize[1],maxSize[1024],custom[url]]');
		$("#video").hide();
        $("#link").show();
	}else if($("input[name='playerSliderShow.type']:eq(1)").attr("checked")){
		$("#linkvalue").removeAttr("value");
		$("#linkvalue").removeClass('validate[required,minSize[1],maxSize[1024],custom[url]]');
		$("#videovalue").removeClass('validate[required,minSize[1],maxSize[1024],ajax[isFileIdExist]]');
		$("#videovalue").addClass('validate[required,minSize[1],maxSize[1024]]');
		$("#link").hide();
		$("#video").show();
	}else{
		$("#videovalue").removeAttr("value");
		$("#videovalue").removeClass('validate[required,minSize[1],maxSize[1024],ajax[isFileIdExist]]');
		$("#linkvalue").removeAttr("value");
		$("#linkvalue").removeClass('validate[required,minSize[1],maxSize[1024],custom[url]]');
		$("#video").hide();
		$("#link").hide();
	}
}

function process(errorFound){
	getData();
	if(!checkDate()){
		return;
	}
	checkData(errorFound)
}

function formSubmit(){
	if(checkSubmitFlg==true){
		layer.alert('请求已经处理提交!');
	}else{
		checkSubmitFlg = true;
		LoadingPic.FullScreenShow();
		document.form.submit();
	}
}

function getData(){
	var value;
	if($("[name='playerSliderShow.type']:checked").val() == "0")
    {  
		value = $("#linkvalue").val();
    }else{
		value = $("#videovalue").val();
    }
	console.log("value = "+value);
	$("[id='playerSliderShow.value']").val(value);
}

function checkData(errorFound){
	
// 	var pssValue = $("[id='playerSliderShow.value']").val(value);
// 	if(!pssValue){
//         layer.alert('请选择类型');
//         return;
//     }
    var file=$("[name='upLoadPicture']");
     if($.trim(file.val())==''){
    	 layer.alert("请选择文件");
         return;
    }
   /** var areaCode = $('#areaCode').val();
    if(!areaCode){
        layer.alert('请选择国家');
        return;
    }*/
    var sort = $("[id='playerSliderShow.sort']").find("option:selected").text();
    var data = {"sort":sort};
    var url  = '<s:url action="isExist.action" />';
    $.ajax({
        type: "POST",
        url: url,
        data: data,
        dataType : "json",
        success:function(data){
       	 if(data&&data.data){
       		layer.open({
      		  content: '展示位已存在，是否替换现有展示位轮播图？',
      		  btn: ['确定', '取消'],
      		  yes: function(index, layero){
      			layer.close(index);
      			if(!errorFound) {
      				formSubmit();
      			}
      		  },
      		  cancel: function(index, layero){ 
      		      layer.close(index);
      			} 
      		});
       	 }else{
       		if(!errorFound) {
       			layer.open({
            		  content: '是否确认保存？',
            		  btn: ['确定', '取消'],
            		  yes: function(index, layero){
            			layer.close(index);
            			if(!errorFound) {
            				formSubmit();
            			}
            		  },
            		  cancel: function(index, layero){ 
            		      layer.close(index);
            			} 
            		});
       	    }
       	 }
        },
        error : function (XMLHttpRequest, textStatus, errorThrown) {
       	 //alert(textStatus+"=="+errorThrown);
        }
	 }); 
}

function checkDate(){
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
    	layer.alert('开始时间不能大于结束时间')
        return false;
    }
    return true;
}

/* function back(){
	
	layer.open({
		  content: '是否放弃当前编辑内容？',
		  btn: ['确定', '取消'],
		  yes: function(index, layero){
			  history.go(-1);
		  },
		  cancel: function(index, layero){ 
		      layer.close(index);
			  return false; 
			} 
		});     
} */

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

<div class="formbody">
<div class="formtitle"><span>添加轮播图</span></div>
<form method="post" name="form" id="form" enctype="multipart/form-data" action="<s:url action="add"/>" onsubmit="return false;">
	<input type="hidden" name="areaCode" id="areaCode" />
	<input type="hidden" name="playerSliderShow.value" id="playerSliderShow.value" />
	<s:token name="submittedToken"/>
    <ul class="forminfo">
    	<li>
	        <label style="width:120px;">轮播图<font class = "require">*</font></label>
	        <input name="upLoadPicture" type="file" value="" style="margin-top: 7px;"/>
	        <i class = "tips">图片大小不能超过2M,支持jpg,jpeg,png,gif,bmp格式</i>
     	</li>
	    <!--  <li>
	    	<label style="width:120px;">国家选择<font class = "require">*</font></label>
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
	    </li>-->
	    <li>
	    	<label style="width:120px;">展示位<font class = "require">*</font></label>
	    	<select id="playerSliderShow.sort" name="playerSliderShow.sort" class="dayinput form-control validate[required]" style="width:120px;">
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="6">6</option>
				<option value="7">7</option>
				<option value="8">8</option>
				<option value="9">9</option>
				<option value="10">10</option>
			</select>
	    </li>
	    <li><label style="width:120px;">展示时间段<font class = "require">*</font></label>
	    	<input name="playerSliderShow.startTime" id="startTime" style="width:150px;" class="Wdate"  value="${playerSliderShow.startTime}" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>&nbsp;&nbsp;--&nbsp;&nbsp;
	    	<input name="playerSliderShow.endTime" id="endTime" style="width:150px;" class="Wdate" value="${playerSliderShow.endTime}" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
	    </li>
	    <li>
	    	<label style="width:120px;">类型<font class = "require">*</font></label>
	    	<cite>
	    		<input name="playerSliderShow.type" type="radio" value="0" checked="checked"/>&nbsp;&nbsp;链接
	    		&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input name="playerSliderShow.type" type="radio" value="1"/>&nbsp;&nbsp;视频
	    	</cite>
    	</li>
    	 <li id="link">
	    	<label style="width:120px;">链接<font class = "require">*</font></label>
	    	<input name="linkvalue" id="linkvalue" type="text" class="dfinput" maxlength="1024"/><i class = "tips">请输入链接:http(s)://xxx.xxx</i>
	    </li>
    	 <li id="video">
	    	<label style="width:120px;">视频<font class = "require">*</font></label>
	    	<input name="videovalue" id="videovalue" type="text" class="dfinput" maxlength="1024"/><i class = "tips">请输入视频id</i>
	    </li>
	    <li>
	    	<label style="width:120px;">备注</label>
    		<textarea rows="10" cols="100" name="playerSliderShow.description" id="playerSliderShow.description"  >${playerSliderShow.description}</textarea>
    	</li>
	    <li>
	    	<label style="width:120px;">&nbsp;</label>
		    <input name="" type="submit" class="btn" value="保存"/>
		    <input name="" type="button" class="btn" value="返回" onclick="javascript:history.go(-1)"/>
	    </li>
    </ul>
	</form>
   </div>
</body>
</html>
