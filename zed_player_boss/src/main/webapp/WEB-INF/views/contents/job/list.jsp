<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../include/resource.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>定时任务列表</title>
<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />style.css"/>
<script type="text/javascript" src="<s:property value="jsPath" />jquery.js"></script>

</head>
<body>
<%@ include file="../../include/navigation.jsp" %>
<div class="mainindex">
	<form id = "form">
		<s:include value="../../include/buttonTop.jsp"/>
		<table class="tablelist">
			<thead>
				<tr>
					<th>定时任务</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="map" var="item">
					<tr>
						<td><s:property value="#item.key" /></td>
						<td>
							<c:if test="${item.value=='start'}">运行中</c:if>
							<c:if test="${item.value=='stop'}">已停止</c:if>
						</td>
						<td>
							<s:set name="change_text_value">${item.value}</s:set>
							<s:include value="../../include/buttonRight.jsp">
							   <s:param name="key_id" value="#item.key"/>
							</s:include>	
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		<%@ include file="../../include/page.jsp" %>
	</form>
</div>				
</body>
</html>