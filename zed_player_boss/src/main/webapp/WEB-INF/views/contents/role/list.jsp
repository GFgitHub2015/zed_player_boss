<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../include/resource.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
	<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />style.css"/>
	<!--common-->
    <link href="<s:property value="themePath" />css/style.css" rel="stylesheet">
    <link href="<s:property value="themePath" />css/style-responsive.css" rel="stylesheet">  
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />window.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />function.js"></script>
</head>
<body>

	<%@ include file="../../include/navigation.jsp" %>
    
    <div class="mainindex">
	
	<form id = "form" action="<s:url action="list" />" method="post" name="form">
		<ul class="seachform">
				<li>
					<label>角色名称</label>
					<input name="role.roleName" type="text" class="scinput form-control" maxlength="50" value = "${role.roleName}"/>
				</li>
				<li>
			    	<label>是否禁用</label>    	
			    	<select name="role.enable" class="dayinput form-control" style="width:120px;">
						<option value="" selected="selected" <c:if test = "${role.enable == ''}">selected="selected"</c:if>>全部</option>
						<option value="1" <c:if test = "${role.enable == '1'}">selected="selected"</c:if>>正常</option>
						<option value="0" <c:if test = "${role.enable == '0'}">selected="selected"</c:if>>禁用</option>
					</select>
			    	</li>    
				<li>
					<label>&nbsp;</label>
					<input type="submit" class="scbtn btn" value="查询"/>
				</li>
		</ul>
		
		<s:include value="../../include/buttonTop.jsp"/>
		
		<table class="tablelist">
			<thead>
				<tr>
					<th><input name = "selectCheckbox" id = "selectCheckbox" type="checkbox" value=""/></th>
					<th>角色名称</th>
					<th>角色Key</th>
					<th>是否禁用</th>
					<th>创建人</th>
					<th>创建时间</th>
					<th>修改人</th>
					<th>修改时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="page.result" var="list">
					<tr>
								<td><input name="checkbox_list" id="checkbox_list" type="checkbox" onclick="selectCheckBoxList()" value="<s:property value="#list.role_id" />"/></td>
								<td><s:property value="#list.role_name" /></td>
								<td><s:property value="#list.role_key" /></td>
								<td>									
									<c:choose>
						    			<c:when test="${list.enable == '0'}">
						    				<font style="color: red">禁用</font>
						    			</c:when>
						    			<c:otherwise>
						    				正常
						    			</c:otherwise>
						    		</c:choose>					        
								</td>
								<td><s:property value="#list.created_by" /></td>
								<td><fmt:formatDate value="${list.created_time}" type="both"/></td>
								<td><s:property value="#list.updated_by" /></td>
								<td><fmt:formatDate value="${list.updated_time}" type="both"/></td>
								<td>
									<s:iterator value="buttonMenuList" var="buttonRight">
										<s:if test="%{#buttonRight.resourceLevel == 5}">
											<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}','${list.role_id}')"  class="tablelink">${buttonRight.resourceName}</a>
										</s:if>
									</s:iterator>
								</td>
								
						</tr>				
				</s:iterator>
			</tbody>
		</table>
		<%@ include file="../../include/buttonRight.jsp" %>
		<%@ include file="../../include/page.jsp" %>
	</form>
	
</div>

</body>
</html>
