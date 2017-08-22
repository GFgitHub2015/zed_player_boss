<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../include/resource.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!--common-->
    <link href="<s:property value="themePath" />css/style.css" rel="stylesheet"/>
    <link href="<s:property value="themePath" />css/style-responsive.css" rel="stylesheet"/> 
	<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />style.css"/>
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />window.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />function.js"></script>
<style type="">
.btn{
width: 110px !important;
}
</style>
<script type="text/javascript">
//批量禁用
function batchDisable(url){
	updateHotUserStatus(url,"DISABLE");
}
//批量禁用全部
function batchDisableAll(url){
	updateHotUserStatus(url,"DISABLE_ALL");
}
//批量启用
function batchEnable(url){
	updateHotUserStatus(url,"ENABLE");
}
//批量启用全部
function batchEnableAll(url){
	updateHotUserStatus(url,"ENABLE");
}

function updateHotUserStatus(url , status){
	if($("#selectCheckbox").val() == ""){
		openAlertmWindow('提示信息','请选择记录。','确认');
	}else{
		url = url+"?status="+status+"&userId="+$("#selectCheckbox").val();
		openConfirmWindow('提示信息','是否确认修改？','确认', 'updateProcess(\''+url+'\')','closeConfirmWindow()','取消')
	}
}
</script>
</head>

<body>
	<%@ include file="../../../include/navigation.jsp" %>
    
    <div class="mainindex">
	<form id = "form" action="<s:url action="list" />" method="post" name="form">
		<ul class="seachform">
				<li>
					<label>用户编号</label>
					<input id="userId" name="userId" type="text" class="scinput form-control" maxlength="32" value = "${userId}"/>
				</li>
				<li>
					<label>用户状态</label>
					<select name="status" id="status" class="dayinput form-control" style="width:150px;">
						<option value="" selected="selected" <c:if test = "${status == ''}">selected="selected"</c:if>>请选择</option>
						<option value="1" <c:if test = "${status == '1'}">selected="selected"</c:if>>启用</option>
						<option value="0" <c:if test = "${status == '0'}">selected="selected"</c:if>>禁用</option>
					</select>
				</li>
				<li>
					<label>用户角色</label>
					<select name="userRoleStatus" id="userRoleStatus" class="dayinput form-control" style="width:150px;">
						<option value="" selected="selected" <c:if test = "${userRoleStatus == ''}">selected="selected"</c:if>>请选择</option>
						<option value="9" <c:if test = "${userRoleStatus == '9'}">selected="selected"</c:if>>普通用户</option>
						<option value="1" <c:if test = "${userRoleStatus == '1'}">selected="selected"</c:if>>高级用户</option>
						<option value="3" <c:if test = "${userRoleStatus == '3'}">selected="selected"</c:if>>运营用户</option>
						<option value="2" <c:if test = "${userRoleStatus == '2'}">selected="selected"</c:if>>站长</option>
					</select>
				</li>
				<li>
					<select name="areaCode" id="areaCode" class="dayinput form-control" style="width:500px;">
							<option value="" <c:if test = "${areaCode == ''}">selected="selected"</c:if>>请选择</option>
							<c:if test="${not empty playerCountryList}">
								<s:iterator value="playerCountryList" var="playerCountry">
									<option value="<s:property value="#playerCountry.countryCode"/>" <s:if test='%{#request.areaCode == #playerCountry.countryCode}'>selected="selected"</s:if>>
										<s:property value="#playerCountry.countryNameZh"/>（<s:property value="#playerCountry.countryCode"/>）
									</option>
								</s:iterator>
							</c:if>
					</select>
				</li>
				<li>
					<label>&nbsp;</label>
					<input type="submit" class="scbtn btn" value="查询" onclick="initPageNo()"/>
				</li>
		</ul>
		<s:include value="../../../include/buttonTop.jsp"/>
		<table class="tablelist">
			<thead>
				<tr>
					<th><input name = "selectCheckbox" id = "selectCheckbox" type="checkbox" value=""/></th>
					<th>用户头像</th>
					<th>用户编号</th>
					<th>用户昵称</th>
					<th>国家码</th>
					<th>手机号</th>
					<th>角色</th>
					<th>被关注数</th>
					<th>公开资源数</th>
					<th>排序</th>
					<th>资源总数</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="page.result" var="list">
					<tr>
								<td><input name="checkbox_list" id="checkbox_list" type="checkbox" onclick="selectCheckBoxList()" value="<s:property value="#list.userId" />"/></td>
								<td><c:if test = "${not empty list.iconUrl}"><img src="${list.iconUrl}" style="width:30px; height: 30px;"/></c:if></td>
								<td><s:property value="#list.userId" /></td>
								<td><s:property value="#list.nickName" /></td>
								<td>
									<s:iterator value="playerCountryList" var="playerCountry">
										<s:if test="%{#playerCountry.countryCode == #list.countryCode}">
											<s:property value="#playerCountry.countryNameZh"/>（<s:property value="#playerCountry.countryCode"/>）
										</s:if>
									</s:iterator>
								</td>
								<td><s:property value="#list.phone" /></td>
								<td>
									<c:choose>
						    			<c:when test="${list.userRoleStatus == '0'}">
						    				未通过审核
						    			</c:when>
						    			<c:when test="${list.userRoleStatus == '1'}">
						    				高级用户
						    			</c:when>
						    			<c:when test="${list.userRoleStatus == '2'}">
						    				站长
						    			</c:when>
						    			<c:when test="${list.userRoleStatus == '3'}">
						    				运营用户
						    			</c:when>
						    			<c:when test="${list.userRoleStatus == '4'}">
						    				特殊用户
						    			</c:when>
						    			<c:when test="${list.userRoleStatus == '9'}">
						    				普通用户
						    			</c:when>
						    			<c:otherwise>
						    			</c:otherwise>
						    		</c:choose>
								</td>
								<td><s:property value="#list.followMeCount" /></td>
								<td><s:property value="#list.shareCount" /></td>
								<td>
									<c:if test = "${not empty list.sort}"><s:property value="#list.sort" /></c:if>
								</td>
								<td>--</td>
								<td <c:if test = "${list.status == '0'}">style="color:#FF0000;"</c:if>>
									<c:choose>
						    			<c:when test="${list.userStatus == '0'}">
						    				账号禁用
						    			</c:when>
						    			<c:when test="${list.status == '0'}">
						    				半启用
						    			</c:when>
						    			<c:when test="${list.status == '1' or list.status ==null}">
						    				启用
						    			</c:when>
						    			<c:when test="${list.status == '-1' }">
						    				<lable style="color: blue;">禁用</lable>
						    			</c:when>
						    			<c:otherwise>
						    			</c:otherwise>
						    		</c:choose>
								</td>
								<td>									
									<s:iterator value="buttonMenuList" var="buttonRight">
										<s:if test="%{#buttonRight.resourceLevel == 5}">
											<s:if test="%{#buttonRight.resourceKey == 'menu_player_user_hot_status'}">
												<s:if test="%{#list.userStatus == 0 }">
													
												</s:if>
												<s:elseif test="%{#list.status == 1 || #list.status==null}">
													<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}?status=DISABLE&userId=','${list.userId}')"  class="tablelink">禁用</a>
												</s:elseif>
												<s:else>
													<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}?status=ENABLE&userId=','${list.userId}')"  class="tablelink">启用</a>
												</s:else>
											</s:if>
											<s:elseif test="%{#buttonRight.resourceKey == 'menu_player_user_hot_updateall'}">
												<s:if test="%{#list.userStatus == 0 }">
													
												</s:if>
												<s:elseif test="#list.status != -1">
													<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}?status=DISABLE_ALL&userId=','${list.userId}')"  class="tablelink">禁用全部</a>
												</s:elseif>
											</s:elseif>
											<s:else>
												<s:if test="%{#list.userStatus == 0 }">
													
												</s:if>
												<s:elseif test="%{#list.status == 1 || #list.status==null}">
													<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}','${list.userId}')"  class="tablelink">${buttonRight.resourceName}</a>
												</s:elseif>
											</s:else>
										</s:if>
									</s:iterator>
								</td>
						</tr>				
				</s:iterator>
			</tbody>
		</table>
		<%@ include file="../../../include/buttonRight.jsp" %>
		<%@ include file="../../../include/page.jsp" %>
	</form>
	
</div>

</body>
</html>
