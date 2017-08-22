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
	$('#form').bind('jqv.form.result', function(event, errorFound){process(errorFound)});
});

function changeType(){
	if($("#type").val() == "0")
    {  
        $("#url").show();
		$("#upLoadImg").hide();
		$("#upLoadPicture").removeAttr("value");
    }else{
		$("#url").hide();
		$("[id='recommendKeyWord.imgUrl']").removeAttr("value");
		$("#upLoadImg").show();
    }
}

function clearForm() {
	window.location = '<s:url action="updatePage" />?recommendKeyWordId=${recommendKeyWord.keywordId}';
}

function process(errorFound){
    if(!errorFound) {
    	openConfirmWindow('提示信息','是否确认修改 ？','确定', 'formSubmit()','closeConfirmWindow()','取消');
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
<form  method="post" name="form" id="form" enctype="multipart/form-data"  action="<s:url action="update"/>" onsubmit="return false;">
	<input type="hidden" name="page.pageNo" id="backPageNo" value="${page.pageNo}"/>
	<input type="hidden" name="recommendKeyWord.keywordId" id="recommendKeyWord.keywordId" value="${recommendKeyWord.keywordId}"/>
<%-- 	<input type="hidden" name="recommendKeyWord.areaCode" id="recommendKeyWord.areaCode" value="${recommendKeyWord.areaCode}"/> --%>
	<input type="hidden" name="recommendKeyWord.creater" id="recommendKeyWord.creater" value="${recommendKeyWord.creater}"/>
	<input type="hidden" name="recommendKeyWord.createTime" id="recommendKeyWord.createTime" value="${recommendKeyWord.createTime}"/>
    <div class="formbody">
    <div class="formtitle"><span>搜索推荐词信息</span></div>
	    <ul class="forminfo">
	    <c:if test="${not empty recommendKeyWord.imgUrl}">
		    <li>
		    	<label>&nbsp;</label>
		    	<div>
		   			<span>
			    		<img src="${recommendKeyWord.imgUrl}" width="165px" height="130px" style="margin-left:35px"></img>
			    	</span>
			    	<label>&nbsp;</label>
	<!-- 		    	<h2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;资源封面</h2> -->
		    	</div>
		    </li>
   		</c:if> 
	    <li>
	    	<label style="width:120px;">推荐词<font class = "require">*</font></label>
	    	<input name="recommendKeyWord.keyword" id="recommendKeyWord.keyword" type="text" class="dfinput validate[required,minSize[1],maxSize[100]]" value="${recommendKeyWord.keyword}" readonly="readonly" maxlength="100"/><i class = "tips">请输入关键字</i>
	    </li>
	   <%--  <li>
	    	<label style="width:120px;">地区编号<font class = "require">*</font></label>
	    	<select id="recommendKeyWord.areaCode" name="recommendKeyWord.areaCode" class="dayinput form-control">
				<s:iterator value="areaCodeList" var="areaCode">
					<option value="<s:property value="#areaCode"/>" <s:if test="%{#areaCode == #request.recommendKeyWord.areaCode}">selected="selected"</s:if>>
						<s:property value="#areaCode"/>
					</option>
				</s:iterator>
			</select>
	    </li> --%>
	    
	    <li>
	    	<label style="width:120px;">国家选择<font class = "require">*</font></label>
			<select name="recommendKeyWord.areaCode" id="recommendKeyWord.areaCode" class="dayinput form-control" style="width:500px;">
				<c:if test="${not empty playerCountryList}">
					<s:iterator value="playerCountryList" var="playerCountry">
						<option value="<s:property value="#playerCountry.countryCode"/>" <s:if test="%{#playerCountry.countryCode == recommendKeyWord.areaCode}">selected="selected"</s:if>>
							<s:property value="#playerCountry.countryNameZh"/>（<s:property value="#playerCountry.countryCode"/>）
						</option>
					</s:iterator>
				</c:if>
			</select>
	    </li>
	    <li>
	    	<label style="width:120px;">排序<font class = "require">*</font></label>
	    	<input name="recommendKeyWord.sort" id="recommendKeyWord.sort" type="text" class="dfinput validate[required,minSize[1],maxSize[10],custom[integer]]" value="${recommendKeyWord.sort}" maxlength="10"/><i class = "tips">请输入序号</i>
	    </li>
	    <li>
	    	<label style="width:120px;">状态</label>
	    	<cite>
	    		<input name="recommendKeyWord.status" type="radio" value="0" <c:if test="${recommendKeyWord.status eq '0'}">checked="checked"</c:if>/>&nbsp;&nbsp;禁用
	    		&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input name="recommendKeyWord.status" type="radio" value="1" <c:if test="${recommendKeyWord.status eq '1'}">checked="checked"</c:if>/>&nbsp;&nbsp;启用		
	    	</cite>
	    </li>    
	    <li><label style="width:120px;">描述</label><input name="recommendKeyWord.description" id="recommendKeyWord.description" type="text" class="dfinput validate[required,minSize[1]]" value="${recommendKeyWord.description}" /><i class = "tips">请输入描述信息</i></li>
	    <li>
		    <label style="width:120px;">上传类型</label>
		   	<select id="type" name="type" class="dayinput form-control" style="width:120px;" onchange="changeType()">
	             <option value="0" <c:if test = "${type == '0'}">selected="selected"</c:if>>资源链接</option>
				 <option value="1" <c:if test = "${type == '1'}">selected="selected"</c:if>>本地上传</option>
			</select>	
      </li>
	    <li id = "url">
	        <label style="width:120px;">资源链接</label>
	        <input name=recommendKeyWord.imgUrl id="recommendKeyWord.imgUrl" value="${recommendKeyWord.imgUrl}" type="text" class="dfinput validate[required,minSize[1]]"/><i class = "tips">请输入图片资源链接地址</i>
     	</li>
    	<li id = "upLoadImg">
	        <label style="width:120px;">本地上传</label>
	        <input name="upLoadPicture" type="file" value="" />
	        <i class = "tips">图片大小不能超过2M,支持jpg,jpeg,png,gif,bmp格式</i>
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
