<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../include/resource.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />style.css" />
<script type="text/javascript" src="<s:property value="jsPath" />jquery.js"></script>
<script type="text/javascript" src="<s:property value="jsPath" />function.js"></script>

<script type="text/javascript">
	//left menu resource key,use to control the left init
	var secondeMenuKeyInitKey = "";
	var thirdMenuKeyInitKey = "";
	var mainMenuName = "";
	
	$(function() {
		//顶部导航切换
		$(".nav li a").click(function() {
			$(".nav li a.selected").removeClass("selected")
			$(this).addClass("selected");
		});
	})

	$(document).ready(function() {
		//init left menu
		mainMenuName = $("#mainMenu_0").attr("menuName");
		$("#mainMenu_0").attr("class","selected");
		top.leftFrame.location = $("#mainMenu_0").attr("href");
		
	});

	function changeMenu(mainMenuKey,secondeMenuKey,thirdMenuKey) {
		$("#mainMenu_0").attr("class","");
		//$("a[name="+mainMenuKey+"]").click();
		$("a[name="+mainMenuKey+"]").attr("class","selected");
		top.leftFrame.location =$("a[name="+mainMenuKey+"]").attr("href");
		secondeMenuKeyInitKey = secondeMenuKey;
		thirdMenuKeyInitKey = thirdMenuKey;		
	}
	
	function mainMenuClick(url,menuName){
		top.leftFrame.location= url;
		mainMenuName = menuName;
	}
	function getCount(){
		/*
	    $.ajax({
	        type: "POST",
	        url: '<s:url action="getMsgCount"/>',
	        success: function(data) {
	        	$('#messageCount').html(data);
	        }
	    });*/
	}
</script>

</head>


<body style="background:#1e2332;">
<!-- <body style="background:#fff;" onload="setInterval('getCount()', 1000*60*3);"> -->


	<div class="topleft">
<%-- 		<img src="<s:property value="imagePath" />logo.png" /> --%>
		<a href="#"><img src="<s:property value="themePath" />images/logo.png" alt=""></a>
	</div>

	<ul class="nav">
		<c:forEach items="${topMenuList}" var="menu" varStatus="status">
					<li><a id="mainMenu_${status.index}"
						name="${menu.resourceKey}"
						href="${menu.resourceUrl}${menu.resourceId}"
						onclick="mainMenuClick('${menu.resourceUrl}${menu.resourceId}','${menu.resourceName}')" menuName="${menu.resourceName}"
						target="leftFrame">
<%-- 							<img src="${menu.resourceImage}" title="${menu.resourceName}"/> --%>
						<h2>${menu.resourceName}</h2>
					</a></li>
		</c:forEach>
	</ul>

	<div class="topright">
		<ul>
<!-- 			<li> -->
<!-- 				<a id="menu3_3" name="menu3_3" href="javascript:void(0)" -->
<!-- 					onclick="changeMenu('menu_platform_main','menu_platform_msg_user','menu_platform_msg_user_receive')" -->
<!-- 					target="rightFrame"> -->
<!-- 					<i class="fa fa-envelope-o"></i> -->
<%-- 					<span id="messageCount">${messageCount}</span> --%>
<!-- 				</a> -->
<!-- 			</li> -->
			<li class="quit"><a
				href="javascript:systemLogOff('<s:url action="logoff" />')">退出</a></li>
		</ul>
	</div>

</body>
</html>