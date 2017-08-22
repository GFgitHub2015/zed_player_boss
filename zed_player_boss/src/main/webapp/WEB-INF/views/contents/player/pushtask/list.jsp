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
					<label>标题</label>
					<input id="title" name="title" type="text" class="scinput form-control" maxlength="150" value = "${title}"/>
				</li>
				<li>
					<label>内容</label>
					<input id="content" name="content" type="text" class="scinput form-control" maxlength="150" value = "${content}"/>
				</li>
				<li>
					<label>状态</label>
					<select name="status" id="status" class="dayinput form-control" style="width:150px;">
						<option value="" <c:if test = "${status == ''}">selected="selected"</c:if>>请选择</option>
						<option value="0" <c:if test = "${status == '0'}">selected="selected"</c:if>>待推送</option>
						<option value="1" <c:if test = "${status == '1'}">selected="selected"</c:if>>推送中</option>
						<option value="2" <c:if test = "${status == '2'}">selected="selected"</c:if>>已完成</option>
	<%-- 					<option value="3" <c:if test = "${status == '3'}">selected="selected"</c:if>>推送失败</option>
						<option value="4" <c:if test = "${status == '4'}">selected="selected"</c:if>>推送暂停</option>
						<option value="5" <c:if test = "${status == '5'}">selected="selected"</c:if>>推送停止</option>
						<option value="-1" <c:if test = "${status == '-1'}">selected="selected"</c:if>>已删除</option> --%>
					</select>
				</li>
				<li>
					<label>任务类型</label>
					<select name="type" id="type" class="dayinput form-control" style="width:150px;">
						<option value="" <c:if test = "${type == ''}">selected="selected"</c:if>>请选择</option>
						<option value="1001" <c:if test = "${type == '1001'}">selected="selected"</c:if>>影片推荐</option>
						<option value="1002" <c:if test = "${type == '1002'}">selected="selected"</c:if>>活动推荐</option>
					</select>
				</li>
				<li>
					<label>目标类型</label>
					<select name="targetType" id="targetType" class="dayinput form-control" style="width:150px;">
						<option value=""  <c:if test = "${targetType == ''}">selected="selected"</c:if>>请选择</option>
						<option value="1" <c:if test = "${targetType == '1'}">selected="selected"</c:if>>指定用户推送</option>
						<option value="2" <c:if test = "${targetType == '2'}">selected="selected"</c:if>>按条件推送</option>
						<option value="3" <c:if test = "${targetType == '3'}">selected="selected"</c:if>>按主题推送</option>
						<option value="4" <c:if test = "${targetType == '4'}">selected="selected"</c:if>>按主题条件推送</option>
					</select>
				</li>
				<li>
					<label>提示类型</label>
					<select name="reminderType" id="reminderType" class="dayinput form-control" style="width:150px;">
						<option value="" selected="selected" <c:if test = "${reminderType == ''}">selected="selected"</c:if>>请选择</option>
						<option value="0" <c:if test = "${reminderType == '0'}">selected="selected"</c:if>>所有</option>
						<option value="1" <c:if test = "${reminderType == '1'}">selected="selected"</c:if>>震动</option>
						<option value="2" <c:if test = "${reminderType == '2'}">selected="selected"</c:if>>响铃</option>
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
<!-- 					<th><input name = "selectCheckbox" id = "selectCheckbox" type="checkbox" value=""/></th> -->
					<th>推送范围</th>
					<th>消息标题</th>
					<th>提醒方式</th>
					<th>发送</th>
					<th>发送数量</th>
					<th>消息状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="page.result" var="list">
						<tr>
								<td onclick="window.location.href='<s:url action='detail' />?taskId=${list.task_id}'">
									<c:choose>
							   			<c:when test="${list.target == '1'}">
							   				所有人
							   			</c:when>
							   			<c:when test="${list.target == '2'}">
							   				部分人
							   			</c:when>
							   			<c:when test="${list.target == '3'}">
							   				测试
							   			</c:when>
							   			<c:otherwise>
							   			</c:otherwise>
							   		</c:choose>
								</td>
								<td onclick="window.location.href='<s:url action='detail' />?taskId=${list.task_id}'"><s:property value="#list.title" /></td>
								<td onclick="window.location.href='<s:url action='detail' />?taskId=${list.task_id}'">
									<c:choose>
							   			<c:when test="${list.reminder_type == '0'}">
							   				所有
							   			</c:when>
							   			<c:when test="${list.reminder_type == '1'}">
							   				震动
							   			</c:when>
							   			<c:when test="${list.reminder_type == '2'}">
							   				响铃
							   			</c:when>
							   			<c:otherwise>
							   			</c:otherwise>
							   		</c:choose>
								</td>
								<td onclick="window.location.href='<s:url action='detail' />?taskId=${list.task_id}'">
								
									<c:if test="${fn:length(list.content)>10}">
										<c:out value="${fn:substring(list.content,0,10)}..." />
									</c:if>
									<c:if test="${fn:length(list.content)<=10}">
										<c:out value="${list.content}" />
									</c:if>
<%-- 								<s:property value="#list.content" /> --%>
								</td>
								<td onclick="window.location.href='<s:url action='detail' />?taskId=${list.task_id}'"><s:property value="#list.open_count" /></td>
								<td onclick="window.location.href='<s:url action='detail' />?taskId=${list.task_id}'">
									<c:choose>
						    		<%-- 	<c:when test="${list.status == '-1'}">
						    				已删除
						    			</c:when> --%>
						    			<c:when test="${list.status == '0'}">
						    				待推送
						    			</c:when>
						    			<c:when test="${list.status == '1'}">
						    				推送中
						    			</c:when>
						    			<c:when test="${list.status == '2'}">
						    				已完成
						    			</c:when>
						    			<%-- <c:when test="${list.status == '3'}">
						    				推送失败
						    			</c:when>
						    			<c:when test="${list.status == '4'}">
						    				推送暂停
						    			</c:when>
						    			<c:when test="${list.status == '5'}">
						    				推送停止
						    			</c:when> --%>
						    			<c:otherwise>
						    			</c:otherwise>
						    		</c:choose>
								</td>
								<td>									
									<s:iterator value="buttonMenuList" var="buttonRight">
										<s:if test="%{#buttonRight.resourceLevel == 5}">
											<s:if test="%{#buttonRight.resourceKey == 'menu_pushtask_table_update_right'}">
												<s:if test="#list.status == 0">
													<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}','${list.task_id}')"  class="tablelink">${buttonRight.resourceName}</a>
												</s:if>
											</s:if>
											<s:elseif test="%{#buttonRight.resourceKey == 'menu_pushtask_table_delete_right'}">
												<s:if test="#list.status == 0">
													<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}','${list.task_id}')"  class="tablelink">${buttonRight.resourceName}</a>
												</s:if>
												<s:elseif test="#list.status == 2">
													<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}','${list.task_id}')"  class="tablelink">${buttonRight.resourceName}</a>
												</s:elseif>
											</s:elseif>
											<s:elseif test="%{#buttonRight.resourceKey == 'menu_pushtask_table_reuse_right'}">
												<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}','${list.task_id}')"  class="tablelink">${buttonRight.resourceName}</a>
											</s:elseif>
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
