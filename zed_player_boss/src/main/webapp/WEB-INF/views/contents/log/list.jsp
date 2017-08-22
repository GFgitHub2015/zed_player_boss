<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../include/resource.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>日志列表</title>
<!--common-->
    <link href="<s:property value="themePath" />css/style.css" rel="stylesheet">
    <link href="<s:property value="themePath" />css/style-responsive.css" rel="stylesheet"> 
<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />style.css"/>
<script type="text/javascript" src="<s:property value="jsPath" />jquery.js"></script>
<script type="text/javascript" src="<s:property value="jsPath" />My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<%@ include file="../../include/navigation.jsp" %>
<div class="mainindex">
	<form action="<s:url action="list" />" method="post" id = "form" name = "form">
		<ul class="seachform">
			<li>
				<label>操作人：</label>
				<input name="operationLog.createdBy" type="text" class="scinput form-control" maxlength="12" value = "${operationLog.createdBy}"/>
			</li>
			<li>
				<label>开始时间：</label>
				<input name="beginTime" class="Wdate form-control" value="${beginTime}"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			</li>
			<li>
				<label>结束时间：</label>
				<input name="endTime" class="Wdate form-control" value="${endTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			</li>
			<li>
				<label>&nbsp;</label>
				<input type="submit" class="scbtn btn" value="查询" onclick="initPageNo()"/>
			</li>
		</ul>
		<table class="tablelist">
			<thead>
				<tr>
					<th>IP</th>
					<th>菜单</th>
					<th>操作内容</th>
					<th>操作人</th>
					<th>操作时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="ol" varStatus="status" items="${page.result}">
					<tr>
						<td>${ol.ip}</td>
						<td>${ol.operationMenu}</td>
						<td title = "${ol.content}">${ol.content}</td>
						<td>${ol.createdBy}</td>
						<td><fmt:formatDate value="${ol.operationTime}" type="both"/></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<%@ include file="../../include/page.jsp" %>
	</form>
</div>				
</body>
</html>