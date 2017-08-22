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
	window.location = '<s:url action="updatePage" />?paramId=${sysParam.paramId}';
}

function process(errorFound){
    if(!errorFound) {
    	existsSysParam();
    }
}


function formSubmit(){

	LoadingPic.FullScreenShow();
	document.form.submit();
}

function existsSysParam(){
	var url = '<s:url action="existsSysParam.action" />';
	var paramId = $("#paramId").val();
	var paramName = $("#paramName").val();
	var countryCode = $("#countryCode").val();
	var data = {"paramName": paramName,"countryCode":countryCode,"paramId":paramId};
	 $.ajax({
         type: "POST",
         url: url,
         data: data,
         dataType : "json",
         success:function(data){
        	 if(data.exists){
        		 layer.alert("参数已经存在!");
        		 return;
        	 }
            openConfirmWindow('提示信息','是否确认提交 ？','确定', 'formSubmit()','closeConfirmWindow()','取消');
         },
         error : function (XMLHttpRequest, textStatus, errorThrown) {
        	 //alert(textStatus+"=="+errorThrown);
         }
	 }); 
}
</script>
</head>
<body>
<%@ include file="../../../include/navigation.jsp" %>
<form  method="post" name="form" id="form"   action="<s:url action="updateSysParam"/>" onsubmit="return false;">
<s:token name="submittedToken"/>
	<input type="hidden" name="page.pageNo" id="backPageNo" value="${page.pageNo}"/>
	<input type="hidden" name="sysParam.paramId" id="paramId" value="${sysParam.paramId}"/>
	<input type="hidden" name="sysParam.createUser" id="createUser" value="${sysParam.createUser}"/>
	<input type="hidden" name="sysParam.createTime" id="createTime" value="${sysParam.createTime}"/>
    <div class="formbody">
    <div class="formtitle"><span>修改系统参数配置</span></div>
	    <ul class="forminfo">
	    <li  style="display: none;">
	    	<label>参数类别<font class = "require">*</font></label>
	    	<input name="sysParam.paramType" readonly="readonly" id="paramType" type="text" class="dfinput validate[required,minSize[1],maxSize[100]]" value="${sysParam.paramType}" maxlength="100"  />
	    </li>
	    <li>
	    	<label>参数名称<font class = "require">*</font></label>
	    	<input name="sysParam.paramName" readonly="readonly" id="paramName" type="text" class="dfinput validate[required,minSize[1],maxSize[100]]"  value="${sysParam.paramName}"/>
	    </li>
	    <li>
	    	<label>参数值<font class = "require">*</font></label>

			<c:choose>
				<c:when test="${sysParam.paramName eq 'openMode'}">
					<select  name="sysParam.paramValue" id="paramValue" class='dayinput form-control' style='width: 250px;'>
						<option value='recommend' <c:if test="${sysParam.paramValue eq 'recommend'}">selected= "selected"</c:if> >推荐模式(recommend)</option>
						<option value='simple' <c:if test="${sysParam.paramValue eq 'simple'}"  >selected= "selected"</c:if> >简单模式(simple)</option>
					</select>
				</c:when>
				<c:when test="${sysParam.paramName eq 'auditFlag'}">
					<select  name="sysParam.paramValue" id="paramValue" class='dayinput form-control' style='width: 250px;'>
						<option value='open' <c:if test="${sysParam.paramValue eq 'open'}"  >selected= "selected"</c:if>>打开(open)</option>
						<option value='close' <c:if test="${sysParam.paramValue eq 'close'}"  >selected= "selected"</c:if>>关闭(close)</option>
					</select>
				</c:when>
				<c:when test="${sysParam.paramName eq 'adOpenFlag'}">
					<select  name="sysParam.paramValue" id="paramValue" class='dayinput form-control' style='width: 250px;'>
						<option value='1'  <c:if test="${sysParam.paramValue eq '1'}"  >selected= "selected"</c:if>>打开</option>
						<option value='0'  <c:if test="${sysParam.paramValue eq '0'}"  >selected= "selected"</c:if>>关闭</option>
					</select>
				</c:when>
				<c:otherwise>
					<input name="sysParam.paramValue" id="paramValue" type="text" class="dfinput validate[required,minSize[1],maxSize[200]]"  value="${sysParam.paramValue}"/>
				</c:otherwise>

			</c:choose>
	    </li>
	    <li >
	    	<label>渠道<font class = "require">*</font></label>
	    	<input name="sysParam.countryCode" readonly="readonly" id="countryCode" type="text" class="dfinput validate[required,maxSize[100]]"  value="${sysParam.countryCode}"/>
	    </li>
	    <li style="display: none;">
	    	<label>排序</label>
	    	<input name="sysParam.orderBy" id="orderBy" type="text" class="dfinput validate[maxSize[100],custom[onlyNumber]]"  value="${sysParam.orderBy}"/> 
	    </li>
	    <li style="display: none;"><label>参数描述</label>
	    	<input name="sysParam.paramDec" id="paramDec" type="text" class="dfinput validate[maxSize[100]]"  value="${sysParam.paramDec}"/> 
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
