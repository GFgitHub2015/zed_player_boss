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
					<label>用户IP</label>
					<input id="loginIp" name="loginIp" type="text" class="scinput form-control" maxlength="128" value = "${loginIp}"/>
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
					<th>用户编号</th>
					<th>登录地址</th>
					<th>登录IP</th>
					<th>登录类型</th>
					<th>登录来源</th>
					<th>经纬度</th>
					<th>创建时间</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="page.result" var="list">
					<tr>
								<td><s:property value="#list.user_id" /></td>
								<td><s:property value="#list.login_site" /></td>
								<td><s:property value="#list.login_ip" /></td>
								<td>
									<c:choose>
						    			<c:when test="${list.login_type == '1'}">
						    				Live
						    			</c:when>
						    			<c:when test="${list.login_type == '2'}">
						    				Player
						    			</c:when>
						    			<c:otherwise>
						    				未知
						    			</c:otherwise>
						    		</c:choose>
								</td>
								<td><s:property value="#list.login_origin" /></td>
								<td><s:property value="#list.lon" />,<s:property value="#list.lat" /></td>
								<td><fmt:formatDate value="${list.create_time}" type="both"/></td>
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
