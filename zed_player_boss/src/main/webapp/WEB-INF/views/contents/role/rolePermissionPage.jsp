<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../include/resource.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
	<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />style.css"/>
	<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />permission.css"/>
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />window.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />function.js"></script>
	
</head>
<script type="text/javascript">
function firstMenu(menuObj){  
	  $("input[_menu1='menu2_"+menuObj.value+"']").each(function(){
	   $(this).attr("checked",menuObj.checked);
	  });
	  $("input[_menu1='menu3_"+menuObj.value+"']").each(function(){
		   $(this).attr("checked",menuObj.checked);
	  });
	  $("input[_menu1='menu4_"+menuObj.value+"']").each(function(){
		   $(this).attr("checked",menuObj.checked);
      });
};
function secondMenu(menuObj,menu1Id){  
// 	alert("menu1Id ="+menu1Id);
// 	alert("menuObj.checked ="+menuObj.checked);
	  $("input[_menu2='menu3_"+menuObj.value+"']").each(function(){
		   $(this).attr("checked",menuObj.checked);
	  });
	  $("input[_menu2='menu4_"+menuObj.value+"']").each(function(){
		   $(this).attr("checked",menuObj.checked);
     });
	  
	  if(menuObj.checked==true){
		  $("input[_menu1='menu1_"+menu1Id+"']").each(function(){
			   $(this).attr("checked",menuObj.checked);
		});
	  }
};
function thirdMenu(menuObj,menu1Id,menu2Id){  
	  $("input[_menu3='menu4_"+menuObj.value+"']").each(function(){
		   $(this).attr("checked",menuObj.checked);
     })
	  
	if(menuObj.checked==true){
		 $("input[_menu1='menu1_"+menu1Id+"']").each(function(){
			   $(this).attr("checked",menuObj.checked);
		});
		  $("input[_menu2='menu2_"+menu2Id+"']").each(function(){
			   $(this).attr("checked",menuObj.checked);
		});
	}
}
function buttonMenu(menuObj,menu1Id,menu2Id,menu3Id){  
	if(menuObj.checked==true){
		 $("input[_menu1='menu1_"+menu1Id+"']").each(function(){
			   $(this).attr("checked",menuObj.checked);
		});
		  $("input[_menu2='menu2_"+menu2Id+"']").each(function(){
			   $(this).attr("checked",menuObj.checked);
		});
		 $("input[_menu3='menu3_"+menu3Id+"']").each(function(){
			   $(this).attr("checked",menuObj.checked);
		 });
	}
}


function allocation(){
	  if("${roleId}" == ""){
			openAlertmWindow('提示信息','该用户还没有分配角色或用户的角色被删除了，请重新分配角色！','确定');
	  }else{
		   document.form.submit();
	  }	  
}

function back() {
		window.location = '<s:url action="list" />';
	}


</script>
<body>
  	<%@ include file="../../include/navigation.jsp" %>
<br/>
<br/>  
<form  method="post" name="form" id="form" action="<s:url action="allocationRolePermission"/>">
<input type="hidden" name="roleId" id="roleId" value="${roleId}">

<table class="ttab" height="100" width="70%" border="0" cellpadding="0" cellspacing="1" align="center" style="margin-left:20px;">
			<tr>
				<td height="30"
					 colspan="2">
					<div align="center">
					<font color="blue" size="3" ><b>分配权限</b></font>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
						<table id="mytable" cellspacing="0">
							 <tr>
							 	<th width="140px">一级菜单</th>
							 	<th>
							 		<label>二级菜单</label><label style="margin-left: 230px;">三级菜单</label><label style="float: right;margin-right: 150px;">顶部按钮/操作按钮</label>
							 	</th> 
							  </tr>
							  <c:forEach items="${node}" var="k">
							  <tr>
							    <th class="specalt" width="250px">
								    <input type="checkbox" name="resourceId" id="menu" _menu1="menu1_${k.resourceId}" onclick="firstMenu(this)" value="${k.resourceId}">
								    ${k.resourceName}
							    </th>
							    <th>
							    	<table class="specalt" style="width: 100%;height: 100%;">
							    		<c:forEach items="${k.children}" var="kc">
							    		<tr>
							    			<th class="specalt" width="250px">
							    				<input type="checkbox"  name="resourceId" id="menu" _menu1="menu2_${k.resourceId}" _menu2="menu2_${kc.resourceId}" onclick="secondMenu(this,'${k.resourceId}')"  value="${kc.resourceId}">
										    	${kc.resourceName}
							    			</th>
							    			<th>
							    				<c:if test="${not empty kc.children}">
							    					<table class="specalt" style="width: 100%;height: 100%;">
							    						<c:forEach items="${kc.children}" var="kcc">
							    						<tr>
							    							<th width="250px" class="specalt">
							    								<input type="checkbox"  name="resourceId" id="menu" _menu1="menu3_${k.resourceId}" _menu2="menu3_${kc.resourceId}"  _menu3="menu3_${kcc.resourceId}" onclick="thirdMenu(this,'${k.resourceId}','${kc.resourceId}')" value="${kcc.resourceId}">
												    			${kcc.resourceName}
							    							</th>
							    							<th>
							    								<c:if test="${not empty kcc.children}">
							    									<table  style="width: 100%;height: 100%;">
							    										<c:forEach items="${kcc.children}" var="kccc">
							    										<tr>
							    											<th class="specalt">
							    												<input type="checkbox"  name="resourceId" id="menu" _menu1="menu4_${k.resourceId}" _menu2="menu4_${kc.resourceId}"  _menu3="menu4_${kcc.resourceId}" _menu4="menu4_${kccc.resourceId}" onclick="buttonMenu(this,'${k.resourceId}','${kc.resourceId}','${kcc.resourceId}')" value="${kccc.resourceId}">
												    							${kccc.resourceName}
												    							(
												    							<c:choose>																	
																		    			<c:when test="${kccc.resourceLevel == '4'}">
																		    				顶部按钮
																		    			</c:when>
																		    			<c:when test="${kccc.resourceLevel == '5'}">
																		    				操作按钮
																		    			</c:when>
																		    			<c:otherwise>
																		    			</c:otherwise>
																		    	</c:choose>
												    							)
							    											</th>
							    										</tr>
							    										</c:forEach>
							    									</table>
							    								</c:if>
							    							</th>
							    						</tr>
							    						</c:forEach>
							    					</table>
							    				</c:if>
							    			</th>
							    		</tr>
							    		</c:forEach>    		
							    	</table>
							    </th>   
							  </tr>
							</c:forEach>
							</table>
		
				</td>
			</tr>	
			
			<tr>
					<td colspan="2" style="padding: 10px">
						<div align="center">			 				
			 				<input type="button" class="btn" value="保存" onclick="allocation();"/>
			 				<input name="backBt" id="backBt" type="button" class="btn" value="返回" onclick="back()"/>
		 				</div>
					</td>
		  </tr>

</table>
	</form>
	<script type="text/javascript">
	if('${roleId}'!=''){
		$.ajax({
			async : false,
			type : "POST",
			//data : $("#from").serialize(),
			url : '<s:url action="getRoleResource?roleId=" />${roleId}',
			dataType : 'json',
			success : function(json) {
				for(index in json){
					$("input:checkbox[value='"+json[index].resourceId+"']").attr('checked','true');
				}
			}
		});
	}
	</script>
</body>
</html>