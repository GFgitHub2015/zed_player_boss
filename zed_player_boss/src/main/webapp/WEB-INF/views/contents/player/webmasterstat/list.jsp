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
					<label>用户编号</label>
					<input id="userId" name="userId" type="text" class="scinput form-control" maxlength="128" value = "${userId}"/>
				</li>
				<li>
					<label>网盘用户角色</label>
					<select name="userRoleStatus" id="userRoleStatus" class="dayinput form-control" style="width:150px;">
						<option value="" selected="selected" <c:if test = "${userRoleStatus == ''}">selected="selected"</c:if>>请选择</option>
<%-- 						<option value="0" <c:if test = "${userRoleStatus == '0'}">selected="selected"</c:if>>未通过审核</option> --%>
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
					<th>用户头像</th>
					<th>用户编号</th>
					<th>用户昵称</th>
					<th>手机号</th>
					<th>待转码</th>
					<th>转码中</th>
					<th>转码完成</th>
					<th>转码失败</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="page.result" var="list">
					<tr>
								<td><c:if test = "${not empty list.icon_url}"><img src="${list.icon_url}" style="width:30px; height: 30px;"/></c:if></td>
								<td><s:property value="#list.user_id" /></td>
								<td><s:property value="#list.nick_name" /></td>
								<td><s:property value="#list.phone" /></td>
								<td><s:property value="#list.wait_transcoding" /></td>
								<td><s:property value="#list.transcoding" /></td>
								<td><s:property value="#list.transcode_success" /></td>
								<td><s:property value="#list.transcode_faild" /></td>
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
