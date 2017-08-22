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
</head>

<body>
	<%@ include file="../../../include/navigation.jsp" %>
    
    <div class="mainindex">
	<form id = "form" action="<s:url action="list" />" method="post" name="form">
		<ul class="seachform">
				<li>
					<label>网盘用户编号</label>
					<input id="userId" name="userId" type="text" class="scinput form-control" maxlength="32" value = "${userId}"/>
				</li>
				<li>
					<label>网盘用户状态</label>
					<select name="status" id="status" class="dayinput form-control" style="width:150px;">
						<option value="" selected="selected" <c:if test = "${status == ''}">selected="selected"</c:if>>请选择</option>
						<option value="1" <c:if test = "${status == '1'}">selected="selected"</c:if>>启用</option>
						<option value="0" <c:if test = "${status == '0'}">selected="selected"</c:if>>禁用</option>
					</select>
				</li>
				<li>
					<label>网盘用户角色</label>
					<select name="userRoleStatus" id="userRoleStatus" class="dayinput form-control" style="width:150px;">
						<option value="" selected="selected" <c:if test = "${userRoleStatus == ''}">selected="selected"</c:if>>请选择</option>
						<option value="0" <c:if test = "${userRoleStatus == '0'}">selected="selected"</c:if>>未通过审核</option>
						<option value="1" <c:if test = "${userRoleStatus == '1'}">selected="selected"</c:if>>高级用户</option>
						<option value="2" <c:if test = "${userRoleStatus == '2'}">selected="selected"</c:if>>站长</option>
						<option value="3" <c:if test = "${userRoleStatus == '3'}">selected="selected"</c:if>>运营用户</option>
						<option value="4" <c:if test = "${userRoleStatus == '4'}">selected="selected"</c:if>>特殊用户</option>
						<option value="9" <c:if test = "${userRoleStatus == '9'}">selected="selected"</c:if>>普通用户</option>
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
					<th>手机号</th>
					<th>网盘用户角色</th>
					<th>关注数</th>
					<th>被关注数</th>
					<th>已分享资源数</th>
					<th>资源总数</th>
					<th>是否启用</th>
					<th>站长申请状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="page.result" var="list">
					<tr>
								<td><input name="checkbox_list" id="checkbox_list" type="checkbox" onclick="selectCheckBoxList()" value="<s:property value="#list.user_id" />"/></td>
								<td><c:if test = "${not empty list.icon_url}"><img src="${list.icon_url}" style="width:30px; height: 30px;"/></c:if></td>
								<td><s:property value="#list.user_id" /></td>
								<td><s:property value="#list.nick_name" /></td>
								<td><s:property value="#list.phone" /></td>
								<td>
									<c:choose>
						    			<c:when test="${list.user_role_status == '0'}">
						    				未通过审核
						    			</c:when>
						    			<c:when test="${list.user_role_status == '1'}">
						    				高级用户
						    			</c:when>
						    			<c:when test="${list.user_role_status == '2'}">
						    				站长
						    			</c:when>
						    			<c:when test="${list.user_role_status == '3'}">
						    				运营用户
						    			</c:when>
						    			<c:when test="${list.user_role_status == '4'}">
						    				特殊用户
						    			</c:when>
						    			<c:when test="${list.user_role_status == '9'}">
						    				普通用户
						    			</c:when>
						    			<c:otherwise>
						    			</c:otherwise>
						    		</c:choose>
								</td>
								<td><s:property value="#list.i_follow_count" /></td>
								<td><s:property value="#list.follow_me_count" /></td>
								<td><s:property value="#list.share_count" /></td>
								<td><%-- <s:property value="#list.files_count" /> --%>--</td>
								<td <c:if test = "${list.status == '0'}">style="color:#FF0000;"</c:if>>
									<c:choose>
						    			<c:when test="${list.status == '0'}">
						    				禁用
						    			</c:when>
						    			<c:when test="${list.status == '1'}">
						    				启用
						    			</c:when>
						    			<c:otherwise>
						    			</c:otherwise>
						    		</c:choose>
								</td>
								<td <c:if test = "${list.apply_status == '0'}">style="color:#FF0000;"</c:if>>
								
									<c:choose>
						    			<c:when test="${list.apply_status == '0'}">
						    				未处理
						    			</c:when>
						    			<c:when test="${list.apply_status == '1'}">
						    				已处理
						    			</c:when>
						    			<c:otherwise>
						    			</c:otherwise>
						    		</c:choose>
								</td>
								<td>									
									<s:iterator value="buttonMenuList" var="buttonRight">
										<s:if test="%{#buttonRight.resourceLevel == 5}">
											<s:if test="%{#buttonRight.resourceKey == 'menu_playeruser_table_status_righ'}">
												<s:if test="#list.status == 1">
													<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}?status=0&userId=','${list.user_id}')"  class="tablelink">禁用</a>
												</s:if>
												<s:elseif test="#list.status == 0">
													<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}?status=1&userId=','${list.user_id}')"  class="tablelink">启用</a>
												</s:elseif>
												<s:else>
													<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}?status=0&userId=','${list.user_id}')"  class="tablelink">禁用</a>
												</s:else>
											</s:if>
											<s:else>
												<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}','${list.user_id}')"  class="tablelink">${buttonRight.resourceName}</a>
											</s:else>
										</s:if>
									</s:iterator>
								<%-- 	<a href="javascript:share('/boss/playeruser/updateShareFileStatus.action?shareStatus=1&userId=','${list.user_id}')"  class="tablelink">一键分享</a> --%>
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
