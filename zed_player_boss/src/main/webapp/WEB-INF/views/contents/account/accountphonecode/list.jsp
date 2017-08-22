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
					<label>手机号</label>
					<input id="phone" name="phone" type="text" class="scinput form-control" maxlength="128" value = "${phone}"/>
				</li>
				<li>
					<label>国家地区码</label>
					<input id="areaCode" name="areaCode" type="text" class="scinput form-control" maxlength="128" value = "${areaCode}"/>
				</li>
				<li>
					<label>状态</label>
					<select name="status" id="status" class="dayinput form-control" style="width:150px;">
						<option value="" selected="selected" <c:if test = "${status == ''}">selected="selected"</c:if>>请选择</option>
						<option value="0" <c:if test = "${status == '0'}">selected="selected"</c:if>>无效验证码(已过期)</option>
						<option value="-1" <c:if test = "${status == '-1'}">selected="selected"</c:if>>已使用验证码</option>
						<option value="1" <c:if test = "${status == '1'}">selected="selected"</c:if>>待验证</option>
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
					<th>地区编号</th>
					<th>手机号</th>
					<th>验证码</th>
					<th>是否有效</th>
					<th>创建时间</th>
					<th>过期时间</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="page.result" var="list">
					<tr>
								<td><s:property value="#list.area_code" /></td>
								<td><s:property value="#list.phone" /></td>
								<td><s:property value="#list.phone_code" /></td>
								<td>
									<c:choose>
						    			<c:when test="${list.status == '1'}">
						    				待验证
						    			</c:when>
						    			<c:when test="${list.status == '0'}">
						    				无效验证码(已过期)
						    			</c:when>
						    			<c:when test="${list.status == '-1'}">
						    				已使用验证码
						    			</c:when>
						    			<c:otherwise>
						    				
						    			</c:otherwise>
						    		</c:choose>
								</td>
								<td><fmt:formatDate value="${list.create_time}" type="both"/></td>
								<td><fmt:formatDate value="${list.expire_time}" type="both"/></td>
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
