<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../include/resource.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
	<%-- <link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />style.css"/> --%>
	<!--common-->
    <link href="<s:property value="themePath" />css/style.css" rel="stylesheet">
    <link href="<s:property value="themePath" />css/style-responsive.css" rel="stylesheet">  
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />window.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />function.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />My97DatePicker/WdatePicker.js"></script>
	<script src="<s:property value="themePath" />js/jquery-ui-1.9.2.custom.min.js"></script>
	<script src="<s:property value="themePath" />js/jquery-migrate-1.2.1.min.js"></script>
	<script src="<s:property value="themePath" />js/bootstrap.min.js"></script>
</head>

<body>

	<%@ include file="../../include/navigation.jsp" %>
    
    <div class="mainindex">
	
	<form id = "form" action="<s:url action="list" />" method="post" name="form">
		<p> <strong style="font-size:14px;">条件搜索：</strong>  <a class="fa fa-chevron-down" href="#collacon1" data-toggle="collapse" data-target="#collacon1"></a></p>
		<div class="collapse" id="collacon1">
			<ul class="seachform">
				<li>
					<label>管理员编号</label>
					<input name="admin.adminId" type="text" class="scinput form-control" value = "${admin.adminId}" maxlength="20"/>
				</li>
				<li>
					<label>所属角色</label>
					<input name="admin.roleName" type="text" class="scinput form-control" value = "${admin.roleName}" maxlength="50"/>
				</li>
				<li>
					<label>开始时间</label>
					<input name="beginTime" class="Wdate form-control" value="${beginTime}"   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
				</li>
				<li>
					<label>结束时间</label>
					<input name="endTime" class="Wdate form-control" value="${endTime}"   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
				</li>
				<li>
					<label>&nbsp;</label>
					<input type="submit" class="scbtn btn" value="查询" onclick="initPageNo()"/>
				</li>
			</ul>
		</div>
		
	
		<s:include value="../../include/buttonTop.jsp"/>
		
		<table class="tablelist">
			<thead>
				<tr>
					<th><input name = "selectCheckbox" id = "selectCheckbox" type="checkbox" value=""/></th>
					<th>管理员编号</th>
					<th>所属角色</th>
					<th>手机号码</th>
					<th>邮箱</th>
					<th>上次登录时间</th>
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
								<td><input name="checkbox_list" id="checkbox_list" type="checkbox" onclick="selectCheckBoxList()" value="<s:property value="#list.admin_id" />"/></td>
								<td><s:property value="#list.admin_id" /></td>
								<td><s:property value="#list.role_name" /></td>
								<td><s:property value="#list.admin_phone" /></td>
								<td><s:property value="#list.admin_mail" /></td>
								<td><fmt:formatDate value="${list.last_login_time}" type="both"/></td>
								<td><s:property value="#list.created_by" /></td>
								<td><fmt:formatDate value="${list.created_time}" type="both"/></td>
								<td><s:property value="#list.updated_by" /></td>
								<td><fmt:formatDate value="${list.updated_time}" type="both"/></td>
								<td>								
									<%-- <s:include value="../../include/buttonRight.jsp">
										<s:param name="key_id" value="#list.admin_id"/>
									</s:include> --%>
									
									<s:iterator value="buttonMenuList" var="buttonRight">
										<s:if test="%{#buttonRight.resourceLevel == 5}">
											<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}','${list.admin_id}')"  class="tablelink">${buttonRight.resourceName}</a>
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
