<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../include/resource.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%-- <link rel="shortcut icon" href="<s:property value="imagePath" />favicon.ico"/> --%>
<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />style.css"/>
<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />validationEngine.jquery.css"/>

<script type="text/javascript" src="<s:property value="jsPath" />jquery.js"></script>
<script type="text/javascript" src="<s:property value="jsPath" />function.js"></script>
<script type="text/javascript" src="<s:property value="jsPath" />jquery.validationEngine-lang.js"></script>
<script type="text/javascript" src="<s:property value="jsPath" />jquery.validationEngine.min.js"></script>


<script language="javascript">
$(function(){
    $('.loginbox').css({'position':'absolute','left':($(window).width()-570)/2});
	$(window).resize(function(){  
    $('.loginbox').css({'position':'absolute','left':($(window).width()-570)/2});
    })  
});

$(document).ready(
	function(e) {	
		$('#formLogin').validationEngine('attach', { 
			  promptPosition: 'centerRight', 
			  scroll: false
		});
		$('#formLogin').bind('jqv.form.result', function(event, errorFound){processLogin(errorFound)});
});

</script> 
</head>
<body class="login">
<form method="post" name="formLogin" id="formLogin" onsubmit="return false;">	
    <div class="loginbody">
    <span class="systemlogo"></span>
    <div class="loginbox">    
	    <ul>
		    <li><input name="loginAccount" id="loginAccount" type="text" class="validate[required] loginuser" value="" onclick="JavaScript:this.value=''" placeholder="请输入您的帐号"/></li>
		    <li><input name="loginPassword" id="loginPassword" type="password" class="validate[required] loginpwd" value="" onclick="JavaScript:this.value=''" placeholder="请输入您的密码"/></li>
		    <li>
			    <input type="text" value="" class="verifycode" id="loginCaptcha" name="loginCaptcha" placeholder="请输入验证码"/>
			    <label class="verifycodeimg"><img id="img" src="captcha.action" onclick="change()"/></label>
			    <label><a href="javascript:change()">看不清楚换一张</a></label>
		    </li>
		    <li><input name="loginBtn" id="loginBtn" type="submit" class="loginbtn" value="登录"/></li>
		    <li><%@ include file="../include/footer.jsp" %></li>
		</ul>    
    </div>
	</div>
</form>

<form method="post" name="formProcess" id="formProcess" onsubmit="return false;" action="<s:url action="loginProcess" />" >
    <input type="hidden" name="adminId" maxlength="16" value="" />
    <input type='hidden' name="encPwd" id="encPwd" value="" />
    <input type="hidden" name="errorCode" value="" />
    <input type="hidden" name="captcha" value="" />
</form>



<script type="text/javascript" src="<s:property value="jsPath" />RSA.js"></script>
<script type="text/javascript" src="<s:property value="jsPath" />Barrett.js"></script>
<script type="text/javascript" src="<s:property value="jsPath" />BigInt.js"></script>

<script>
function processLogin(errorFound){
    if (!errorFound) {
        setMaxDigits(130);
        var key = new RSAKeyPair("<s:property value="exponent"/>","","<s:property value="module"/>");
        var encPwd=encryptedString(key, document.getElementById("loginPassword").value);
        console.log("encPwd : "+encPwd);
        document.formProcess.adminId.value=trim(document.formLogin.loginAccount.value);
        document.formProcess.encPwd.value=encPwd;
        document.formProcess.captcha.value=document.formLogin.loginCaptcha.value;
        document.formProcess.submit();
    }
}



function change(){
    document.getElementById("img").src = "captcha.action?"+Math.random();
}
</script>
</body>
</html>