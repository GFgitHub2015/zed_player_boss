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
		.require{color:#FF0000;padding-right:2px;}
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
	if(!errorFound){
		var channel = $("#appInfo").val();
		var countryCode = $("#countryCode").val();
		if(!countryCode&&!channel){
			layer.alert("请填写渠道值!");
			return;
		}
		var paramName = $("#paramName").val();
		var paramNames = $("#paramNames").val();
		if(!paramName&&!paramNames){
			layer.alert("请填写参数名!");
			return;
		}
		existsSysParam();
	}
}

function formSubmit(){

	LoadingPic.FullScreenShow();
	document.form.submit();
}

function existsSysParam(){
	var url = '<s:url action="existsSysParam.action" />';
	var paramName = $("#paramName").val();
	var countryCode = $("#countryCode").val();
	var data = {"paramName": paramName,"countryCode":countryCode};
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

function changechannel(){
	var channel = $("#appInfo").val();
	if(!channel){
		$("#otherChannel").show();
	}else{
		$("#otherChannel").hide();
	}
	$("#countryCode").val(channel);
}
function changeParams(){
	var paramNames = $("#paramNames").val();
	if(!paramNames){
		$("#otherParams").show();
		$("#paramDec").val("");
	}else{
		$("#otherParams").hide();
		$("#paramDec").val($("#paramNames").find("option:selected").text());
	}
	//根据不同参数给出不同的输入框
	$("#paramValueDiv").html("");//先清空一次
	var defaultflag = true;
	if(paramNames){//给出不同的输入框
        defaultflag=false;
        var inputhtml ="<select name='sysParam.paramValue' class='dayinput form-control' style='width: 250px;' id='paramValue'></select>";
        $("#paramValueDiv").html(inputhtml);
        if(paramNames=='openMode'){
			$("#paramValue").html("<option value='recommend'>推荐模式(recommend)</option><option value='simple'>简单模式(simple)</option>");
		}else if(paramNames=='auditFlag'){
            $("#paramValue").html("<option value='open'>打开(open)</option><option value='close'>关闭(close)</option>");
		}else if(paramNames=='adOpenFlag'){
            $("#paramValue").html("<option value='1'>打开</option><option value='0'>关闭</option>");
        }else{
            defaultflag=true;
		}
	}
	if(defaultflag==true){
	    var inputhtml ="<input name='sysParam.paramValue' id='paramValue' type='text' class='dfinput validate[required,minSize[1],maxSize[200]]'  />";
        $("#paramValueDiv").html(inputhtml);
	}
	$("#paramName").val(paramNames);
	$("#orderBy").val($("#paramNames").get(0).selectedIndex);
}
</script>

</head>

<body>

<%@ include file="../../../include/navigation.jsp" %>
<form  method="post" name="form" id="form"   action="<s:url action="addSysParam"/>" onsubmit="return false;">
<s:token name="submittedToken"/>
	<input type="hidden" name="page.pageNo" id="backPageNo" value="${page.pageNo}"/>
	<input type="hidden" name="sysParam.paramId" id="paramId" value="${sysParam.paramId}"/>
	<input type="hidden" name="sysParam.createUser" id="createUser" value="${sysParam.createUser}"/>
	<input type="hidden" name="sysParam.createTime" id="createTime" value="${sysParam.createTime}"/>
    <div class="formbody">
    <div class="formtitle"><span>新增系统参数配置</span></div>
	    <ul class="forminfo">
	    <li style="display: none;">
	    	<label>参数类别<font class = "require">*</font></label>
	    	<input name="sysParam.paramType" id="paramType" type="text" class="dfinput validate[required,minSize[1],maxSize[100]]" value="paramType" maxlength="100"  /> 
	    </li>
	    <li>
	    	<label>参数名称<font class = "require">*</font></label>
	    	<select name="paramNames" id="paramNames" onchange="changeParams()"  class="dayinput form-control" style="width: 250px;">
				<option value="">请选择..</option>
				<c:forEach items="${paramNameValue}" var="nameValue" > 
					<option value="${nameValue.key }" >${nameValue.value }</option>
				</c:forEach> 
				<option value="">其它</option>
	    	</select>
	    </li>
	    <li id="otherParams" style="display: none;">
	    	<label>其它参数<font class = "require">*</font></label>
	    	<input name="sysParam.paramName" id="paramName" type="text" class="dfinput validate[required,minSize[1],maxSize[100]]"  value="${sysParam.paramName}"/> 
	    </li>
	    <li>
	    	<label>参数值<font class = "require">*</font></label>
			<div id="paramValueDiv">
				<input name="sysParam.paramValue" id="paramValue" type="text" class="dfinput validate[required,minSize[1],maxSize[200]]"  />
			</div>
	    </li>
	    <li >
	    	<label>渠道<font class = "require">*</font></label>
	    	<select name="appInfo" id="appInfo" onchange="changechannel()"  class="dayinput form-control" style="width: 250px;">
				<option value="">请选择..</option>
	    		<c:forEach var="appinfo" items="${appInfos }">
						<option value="${appinfo.channel }" >${appinfo.appname }</option>
				</c:forEach>
				<option value="">其它</option>
	    	</select>
	    </li>
	    <li style="display: none;" id="otherChannel">
	    	<label>其它渠道<font class = "require">*</font></label>
	    	<input name="sysParam.countryCode" id="countryCode" type="text" class="dfinput validate[required,maxSize[100]]"  value="${sysParam.countryCode}"/> 
	    </li>
	    <li  style="display: none;">
	    	<label>排序</label>
	    	<input name="sysParam.orderBy" id="orderBy" type="number" class="dfinput validate[maxSize[100],custom[onlyNumber]]"  value="${sysParam.orderBy}"/> 
	    </li>
	    <li style="display: none;"><label>参数描述</label>
	    	<input name="sysParam.paramDec" id="paramDec" type="text" class="dfinput validate[maxSize[100]]"  value="${sysParam.paramDec}"/> 
		</li>    
	    <li><label>&nbsp;</label>
		    <input name="" type="submit" class="btn" value="保存"/>
		    <input name="" type="button" class="btn" value="重置" onclick="clearForm();"/>
	    </li>
	    </ul>
    </div>
</form>
</body>
</html>
