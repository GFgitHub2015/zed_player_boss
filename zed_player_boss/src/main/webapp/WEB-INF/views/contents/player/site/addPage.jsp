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
var checkSubmitFlg = false;
function changeType(){
	$("[id='playerSiteNavigate.imgUrl']").removeAttr("value");
	$("#upLoadPicture").removeAttr("value");
	if($("#type").val() == "0")
    {  
        $("#url").show();
		$("#upLoadImg").hide();
    }else{
		$("#url").hide();
		$("#upLoadImg").show();
    }
}

$(document).ready(function(e) {
	changeType();
	$('#form').validationEngine('attach', { 
	    promptPosition: 'centerRight', 
	    scroll: false
	  });	  
	$('#form').bind('jqv.form.result', function(event, errorFound){process(errorFound)});
});

function process(errorFound){
	if(!errorFound){
		openConfirmWindow('提示信息','是否确认提交 ？','确定', 'formSubmit()','closeConfirmWindow()','取消');
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
<style>
</style>
<div class="formtitle"><span>添加热门网站</span></div>
<form method="post" name="form" id="form" enctype="multipart/form-data" action="<s:url action="add"/>" onsubmit="return false;">
	<input type="hidden" name="areaCode" id="areaCode" />
    <ul class="forminfo">
	    <li>
	    	<label style="width:120px;">网站标题<font class = "require">*</font></label>
	    	<input name="playerSiteNavigate.title" id="playerSiteNavigate.title" type="text" class="dfinput validate[required,minSize[1],maxSize[32]]" maxlength="32"/><i class = "tips">请输入网站标题</i>
	    </li>
	    <li>
	    	<label style="width:120px;">网站链接<font class = "require">*</font></label>
	    	<input name="playerSiteNavigate.siteUrl" id="playerSiteNavigate.siteUrl" type="text" class="dfinput validate[required,minSize[1],maxSize[1024]]" maxlength="1024"/><i class = "tips">请输入网站链接</i>
	    </li>
	    <li>
	    	<label style="width:120px;">排序<font class = "require">*</font></label>
	    	<input name="playerSiteNavigate.sort" id="playerSiteNavigate.sort" type="text" class="dfinput validate[required,minSize[1],maxSize[11],custom[number]]" maxlength="11"/><i class = "tips">请输入网站排序</i>
	    </li>
	    <li>
	    	<label style="width:120px;">备注<font class = "require">*</font></label>
	    	<input name="playerSiteNavigate.remark" id="playerSiteNavigate.remark" type="text" class="dfinput" maxlength="1024"/><i class = "tips">请输入网站备注</i>
	    </li>
	    <li>
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
	    </li>
	    <li>
	    	<label style="width:120px;">是否启用<font class = "require">*</font></label>
	    	<cite>
	    		<input name="playerSiteNavigate.status" type="radio" value="0" checked="checked"/>&nbsp;&nbsp;禁用
	    		&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input name="playerSiteNavigate.status" type="radio" value="1"/>&nbsp;&nbsp;启用
	    	</cite>
    	</li>
		<li>
		    <label style="width:120px;">上传类型</label>
		   	<select id="type" name="type" class="dayinput form-control" style="width:120px;" onchange="changeType()">
	             <option value="0" <c:if test = "${type == '0'}">selected="selected"</c:if>>资源链接</option>
				 <option value="1" <c:if test = "${type == '1'}">selected="selected"</c:if>>本地上传</option>
			</select>	
		</li>
    	<li id = "url">
	        <label style="width:120px;">资源链接</label>
	        <input name="playerSiteNavigate.imgUrl" id="playerSiteNavigate.imgUrl" type="text" class="dfinput validate[required,minSize[1]]"/><i class = "tips">请输入图片资源链接地址</i>
     	</li>
    	<li id = "upLoadImg">
	        <label style="width:120px;">本地上传</label>
	        <input name="upLoadPicture" type="file" value="" />
	        <i class = "tips">图片大小不能超过2M,支持jpg,jpeg,png,gif,bmp格式</i>
     	</li>
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
