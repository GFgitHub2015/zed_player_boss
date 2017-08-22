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
					<label>菜单名称</label>
					<input name="resource.resourceName" type="text" class="scinput form-control" maxlength="50" value = "${resource.resourceName}"/>
				</li>
				<li>
		    		<label>菜单等级</label>
			    	<select name="resourceLevel" class="dayinput form-control">
			    		<option value="" selected="selected" <c:if test = "${resourceLevel == ''}">selected="selected"</c:if>>请选择</option>
						<option value="1" <c:if test = "${resourceLevel == '1'}">selected="selected"</c:if>>一级菜单</option>
						<option value="2" <c:if test = "${resourceLevel == '2'}">selected="selected"</c:if>>二级菜单</option>
						<option value="3" <c:if test = "${resourceLevel == '3'}">selected="selected"</c:if>>三级菜单</option>
						<option value="4" <c:if test = "${resourceLevel == '4'}">selected="selected"</c:if>>顶部按钮</option>
						<option value="5" <c:if test = "${resourceLevel == '5'}">selected="selected"</c:if>>操作按钮</option>
					</select>
		    	</li>
				<li>
					<label>&nbsp;</label>
					<input type="submit" class="btn" value="查询" onclick="initPageNo()"/>
				</li>
		</ul>
		<s:include value="../../include/buttonTop.jsp"/>
		<table class="tablelist">
			<thead>
				<tr>
					<th><input name = "selectCheckbox" id = "selectCheckbox" type="checkbox" value=""/></th>
					<th >菜单名称</th>
					<th>菜单Key</th>
					<th>菜单地址</th>
					<th>菜单等级</th>
					<th>上级菜单</th>
					<th>菜单顺序</th>
					<th>是否启用</th>
					<th>方法名称</th>					
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
								<td><input name="checkbox_list" id="checkbox_list" type="checkbox" onclick="selectCheckBoxList()" value="<s:property value="#list.resource_id" />"/></td>
								<td><s:property value="#list.resource_name" /></td>
								<td>
									<c:if test="${fn:length(list.resource_key)>10}">
										<c:out value="${fn:substring(list.resource_key,0,10)}..." />
									</c:if>
									<c:if test="${fn:length(list.resource_key)<=10}">
										<c:out value="${list.resource_key}" />
									</c:if>
								</td>
								<td>
									<c:if test="${fn:length(list.resource_url)>10}">
										<c:out value="${fn:substring(list.resource_url,0,10)}..." />
									</c:if>
									<c:if test="${fn:length(list.resource_url)<=10}">
										<c:out value="${list.resource_url}" />
									</c:if>
								</td>
								<td>
						    		<c:choose>
						    			<c:when test="${list.resource_level == '1'}">
						    				一级菜单
						    			</c:when>
						    			<c:when test="${list.resource_level == '2'}">
						    				二级菜单
						    			</c:when>
						    			<c:when test="${list.resource_level == '3'}">
						    				三级菜单
						    			</c:when>
						    			<c:when test="${list.resource_level == '4'}">
						    				顶部按钮
						    			</c:when>
						    			<c:when test="${list.resource_level == '5'}">
						    				操作按钮
						    			</c:when>
						    			<c:otherwise>
						    			</c:otherwise>
						    		</c:choose>
								</td>
								<td><s:property value="#list.partent_name" /></td>
								<td><s:property value="#list.resource_order" /></td>
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
								<td><s:property value="#list.function_name" /></td>
								<td><s:property value="#list.created_by" /></td>
								<td><fmt:formatDate value="${list.created_time}" type="both"/></td>
								<td><s:property value="#list.updated_by" /></td>
								<td><fmt:formatDate value="${list.updated_time}" type="both"/></td>
								<td>																	
									<s:iterator value="buttonMenuList" var="buttonRight">
										<s:if test="%{#buttonRight.resourceLevel == 5}">
											<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}','${list.resource_id}')"  class="tablelink">${buttonRight.resourceName}</a>
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
