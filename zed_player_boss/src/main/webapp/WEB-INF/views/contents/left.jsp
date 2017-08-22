<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../include/resource.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />style.css"/>
<script type="text/javascript" src="<s:property value="jsPath" />jquery.js"></script>
<script type="text/javascript" src="<s:property value="jsPath" />function.js"></script>
<script type="text/javascript">
	$(function(){	
		//导航切换
		$(".menuson li").click(function(){
			$(".menuson li.active").removeClass("active")
			$(this).addClass("active");
		});
		$('.leftmenu .title').click(function(){
			var $ul = $(this).next('ul');
			$('dd').find('ul').slideUp();
			if($ul.is(':visible')){
				$(this).next('ul').slideUp();
				$(this).removeClass("active");
				$(this).find("i").removeClass("fa-chevron-down").addClass("fa-chevron-right");
			}else{
				$(this).next('ul').slideDown();
				$(".leftmenu .title").removeClass("active");
				$(this).addClass("active");
				$(".leftmenu .title i").removeClass("fa-chevron-down").addClass("fa-chevron-right");
				$(this).find("i").removeClass("fa-chevron-right").addClass("fa-chevron-down");
			}
		});
		
		 /* ------- */
	      $('.second').click(function () {
	        var $secondul = $(this).find('.third');
	        if ($secondul.is(':hidden')) {
	          $(".second .third").slideUp();
	          $(this).find('.third').slideDown();
	        } else {
	          $(".second .third").slideUp();
	        }
	      });
	});
	
	
	var mainList;
	$(document).ready(function(){
		//init right menu
		if(top.topFrame.secondeMenuKeyInitKey != ""){	
			var firstSecond = $("#menu3_0").closest('dd').find("div").attr("name");
			
			if(firstSecond != top.topFrame.secondeMenuKeyInitKey){
				$("#menu3_0").closest('dd').find("ul").slideUp();
			}
			
			$("a[name="+top.topFrame.thirdMenuKeyInitKey+"]").closest('ul').slideDown();
			$("div[name="+top.topFrame.secondeMenuKeyInitKey+"]").attr("class","title active");
			$("div[name="+top.topFrame.secondeMenuKeyInitKey+"]").find("i").attr("class","fa fa-chevron-down");
			$("a[name="+top.topFrame.thirdMenuKeyInitKey+"]").click();			
			
			top.topFrame.secondeMenuKeyInitKey = "";
			top.topFrame.thirdMenuKeyInitKey = "";
		}else{
			$("#menu3_0").click();	
			
			//set seconde menu ccs
			$("#menu3_0").closest('dd').find("div").attr("class","title active");
			$("#menu3_0").closest('dd').find("div").find("i").attr("class","fa fa-chevron-down");
		
		}
			
	});
	
	function rightMenu(obj,menu3Url,menu2Name,menu3Name,menu3Id){
		mainList = top.topFrame.mainMenuName+" > "+menu2Name+" > "+menu3Name;
		
		if(menu3Url.endWith(".action")){
			top.rightFrame.location=menu3Url+"?buttonMenuPartentId="+menu3Id;	
		}
		else{
			top.rightFrame.location=menu3Url+"&buttonMenuPartentId="+menu3Id;	
		}	
	}
	
</script>
</head>

<body style="background:#1e2332;">
		
				<div class="lefttop">
				  <dl>
 			      <dt><%-- <img src="${imagePath}headportrait.png" alt=""/> --%></dt> 
			      <dd>
				      <div>
				      	<span>你好,${sessionAdmin.adminId}</span>
				      </div>
			      </dd>
			    </dl>
				</div>
				
				<dl class="leftmenu">
					<c:forEach items="${leftMenuList}" var="secondeMenu">
						<dd>						
							<div class="title" name="${secondeMenu.resourceKey}">
								${secondeMenu.resourceName}
								<i class="fa fa-chevron-right"></i>
							</div>								
								<ul class="menuson">
									<c:forEach items="${secondeMenu.children}" var="thirdMenu" varStatus="status">
										<li><a id="menu3_${status.index}" name="${thirdMenu.resourceKey}" href="javascript:void(0)" onclick="rightMenu(this,'${thirdMenu.resourceUrl}','${secondeMenu.resourceName}','${thirdMenu.resourceName}','${thirdMenu.resourceId}')" target="rightFrame">${thirdMenu.resourceName}</a><i></i></li>
									</c:forEach>
								</ul>
						</dd>	
					</c:forEach>		
				</dl>

</body>
</html>
