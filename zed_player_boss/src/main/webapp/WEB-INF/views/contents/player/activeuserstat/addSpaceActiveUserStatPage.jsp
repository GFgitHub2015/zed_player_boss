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
    <script type="text/javascript" src="<s:property value="jsPath" />My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.validationEngine-lang.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.validationEngine.min.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />loading.js"></script>
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

	var url = '<s:url action="existsSpaceActiveUserStat.action" />';
	var statDate = $("#statDate").val();
	var channel = $("#channel").val();
	var data = {"channel": channel,"statDate":statDate};
	 $.ajax({
        type: "POST",
        url: url,
        data: data,
        dataType : "json",
        success:function(data){
        	 if(data&&data.result==false){
        			if(!errorFound){
        				openConfirmWindow('提示信息','是否确认提交 ？','确定', 'formSubmit()','closeConfirmWindow()','取消');
        			}
	       	 }else{
	       		 layer.alert("已经存在");
	       	 } 
        },
        error : function (XMLHttpRequest, textStatus, errorThrown) {
       	 //alert(textStatus+"=="+errorThrown);
        }
	 }); 
}

function formSubmit(){
	LoadingPic.FullScreenShow();
	document.form.submit();
}

function checkDate(){
	var statDate = $("#statDate").val();
	if(!statDate){
        layer.alert('请输入日期!');
        return false;
	}
    return true;
}
</script>

</head>

<body>

<%@ include file="../../../include/navigation.jsp" %>

<form method="post" name="form" id="form"  action="<s:url action="addSpaceActiveUserStat"/>" onsubmit="return false;">
<s:token name="submittedToken"/>
	<input type="hidden" name="activeUser.channel" id="channel"  value="${channel}"/>
	<input type="hidden" name="channel"  value="${channel}"/>
    <div class="formbody">
    <div class="formtitle"><span>新增站长活跃数据</span></div>
	    <ul class="forminfo">
	    <li>
	    	<label>日期<font class = "require">*</font></label>
	    	<input  name="activeUser.statDate" id="statDate" class="Wdate" value='${statDate }' style="width: 150px;"  readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
	    </li>
	    <li>
	    	<label>日增数<font class = "require">*</font></label>
	    	<input name="activeUser.adgActiveCount" id="adgActiveCount" type="number" class="dfinput validate[required]" value="0"  /> 
	    </li>
	    <li>
	    	<label>活跃数<font class = "require">*</font></label>
	    	<input name="activeUser.activeCount" id="activeCount" type="number" class="dfinput validate[required]" value="0"  /> 
	    </li>
        
	    	<label>&nbsp;</label>
		    <input name="" type="submit" class="btn" value="保存"/>
		    <input name="" type="button" class="btn" value="返回" onclick="javascript:history.go(-1)"/>
	    </li>
    </ul>
	</form>
</body>
</html>
