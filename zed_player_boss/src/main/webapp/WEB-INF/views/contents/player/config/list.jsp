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
					<label>版本号</label>
					<input id="version" name="version" type="text" class="scinput form-control" maxlength="10" value = "${version}"/>
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
					<th>版本号</th>
					<th>app包名</th>
					<th>google审核状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="page.result" var="list">
					<tr>
								<td><input name="checkbox_list" id="checkbox_list" type="checkbox" onclick="selectCheckBoxList()" value="<s:property value="#list.config_id" />"/></td>
								<td><s:property value="#list.version" /></td>
								<td><s:property value="#list.package_name" /></td>
								<td>
						    		<c:choose>
						    			<c:when test="${list.review_status == '0'}">
						    				审核中
						    			</c:when>
						    			<c:when test="${list.review_status == '1'}">
						    				审核通过
						    			</c:when>
						    			<c:otherwise>
						    			</c:otherwise>
						    		</c:choose>
								</td>
								<td>													
									<s:iterator value="buttonMenuList" var="buttonRight">
										<s:if test="%{#buttonRight.resourceLevel == 5}">
											<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}','${list.config_id}')"  class="tablelink">${buttonRight.resourceName}</a>
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
