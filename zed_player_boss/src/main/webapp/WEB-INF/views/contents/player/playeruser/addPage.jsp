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
$(document).ready(function(e) {
	$("#iconUrlli").hide();
	$("#nickNameli").hide();
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

function getUserInfo(){
	var url = '<s:url action="getUserInfo.action" />';
	var userId = $("[id='playerUser.userId']").val();
	if(!userId){
		return;
	}
	var data = {"userId": userId};
	 $.ajax({
         type: "POST",
         url: url,
         data: data,
         dataType : "json",
         success:function(data){
        	 if(data){
            	 $("#iconUrl").attr("src",data.iconUrl);
            	 $("#nickName").val(data.nickName);
        		 $("#iconUrlli").show();
        		 $("#nickNameli").show();
        	 }else{
//         		 layer.alert("用户不存在!");
 				$("#iconUrl").attr("src","");
            	 $("#nickName").val("");
        		 $("#iconUrlli").hide();
        		 $("#nickNameli").hide();
        	 }
         },
         error : function (XMLHttpRequest, textStatus, errorThrown) {
         }
	 }); 
}

function formSubmit(){
	LoadingPic.FullScreenShow();
	document.form.submit();
}

</script>

</head>

<body>

<%@ include file="../../../include/navigation.jsp" %>

<div class="formbody">
<div class="formtitle"><span>添加网盘用户</span></div>
<form method="post" name="form" id="form" action="<s:url action="add"/>" onsubmit="return false;">
<s:token name="submittedToken"/>
    <ul class="forminfo">
    	<li id="iconUrlli">
	    	<label>&nbsp;</label>
	    	<div>
	   			<span>
		    		<img id="iconUrl" src="" width="165px" height="130px" style="margin-left:35px"></img>
		    	</span>
		    	<label>&nbsp;</label>
	    	</div>
	    </li> 
	    <li>
	    	<label style="width:120px;">网盘用户编号<font class = "require">*</font></label>
	    	<input name="playerUser.userId" id="playerUser.userId" type="text" class="dfinput validate[required,minSize[1],maxSize[32],ajax[isUserIdExist]]" maxlength="32" onblur="getUserInfo()"/><i class = "tips">请输入用户编号</i>
	    </li>
	    <li id="nickNameli">
	    	<label style="width:120px;">网盘用户昵称<font class = "require">*</font></label>
	    	<input name="nickName" id="nickName" type="text" class="dfinput" readonly="readonly" />
	    </li>
	    <li>
	    	<label style="width:120px;">网盘用户签名</label>
	    	<input name="playerUser.sign" id="playerUser.sign" type="text" class="dfinput" maxlength="1024"/><i class = "tips">请输入网盘用户签名</i>
	    </li>
	    <li>
	    	<label style="width:120px;">是否启用<font class = "require">*</font></label>
	    	<cite>
	    		<input name="playerUser.status" type="radio" value="0" checked="checked"/>&nbsp;&nbsp;禁用
	    		&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input name="playerUser.status" type="radio" value="1"/>&nbsp;&nbsp;启用
	    	</cite>
    	</li>
	    <li>
	    	<label style="width:120px;">网盘用户角色<font class = "require">*</font></label>
	    	<cite>
	    		<input name="playerUser.userRoleStatus" type="radio" value="0" checked="checked"/>&nbsp;&nbsp;未通过审核
	    		&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input name="playerUser.userRoleStatus" type="radio" value="1"/>&nbsp;&nbsp;高级用户
	    		&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input name="playerUser.userRoleStatus" type="radio" value="2"/>&nbsp;&nbsp;站长
	    		&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input name="playerUser.userRoleStatus" type="radio" value="3"/>&nbsp;&nbsp;运营用户
	    		&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input name="playerUser.userRoleStatus" type="radio" value="4"/>&nbsp;&nbsp;特殊用户
	    		&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input name="playerUser.userRoleStatus" type="radio" value="9"/>&nbsp;&nbsp;普通用户
	    	</cite>
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
