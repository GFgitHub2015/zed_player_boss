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
					<input name="title" type="text" class="scinput form-control" maxlength="150" value = "${title}"/>
				</li>
				<li>
					<label>状态</label>
					<select name="status" id="status" class="dayinput form-control" style="width:150px;">
						<option value="" selected="selected" <c:if test = "${status == ''}">selected="selected"</c:if>>请选择</option>
						<option value="1" <c:if test = "${status == '1'}">selected="selected"</c:if>>调度中</option>
						<option value="2" <c:if test = "${status == '2'}">selected="selected"</c:if>>调度成功</option>
						<option value="3" <c:if test = "${status == '3'}">selected="selected"</c:if>>调度失败</option>
						<option value="4" <c:if test = "${status == '4'}">selected="selected"</c:if>>调度暂停</option>
						<option value="5" <c:if test = "${status == '5'}">selected="selected"</c:if>>调度停止</option>
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
					<th>任务标识</th>
					<th>推送目标类型</th>
					<th>开始时间</th>
					<th>结束时间</th>
					<th>调度超时时间(以小时为单位)</th>
					<th>重复次数</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="page.result" var="result">
					<tr>
<%-- 								<td><input name="checkbox_list" id="checkbox_list" type="checkbox" onclick="selectCheckBoxList()" value="<s:property value="#result.scheduleId" />"/></td> --%>
								<td><s:property value="#result.taskId" /></td>
								<td>
							    	<c:choose>
							   			<c:when test="${result.targetType == '1'}">
							   				指定用户推送
							   			</c:when>
							   			<c:when test="${result.targetType == '2'}">
							   				按查询条件推送
							   			</c:when>
							   			<c:when test="${result.targetType == '3'}">
							   				按主题推送
							   			</c:when>
							   			<c:when test="${result.targetType == '4'}">
							   				按主题条件推送
							   			</c:when>
							   			<c:otherwise>
							   			</c:otherwise>
							   		</c:choose>
								</td>
								<td>
									<fmt:formatDate value="${result.createTime}" type="both"/>
								</td>
								<td>
									<fmt:formatDate value="${result.endTime}" type="both"/>
								</td>
								<td>
									<s:property value="#result.scheduleTimeout" />
								</td>
								<td>
									<s:property value="#result.retries" />
								</td>
								<td>
									<c:choose>
						    			<c:when test="${result.status == '1'}">
						    				调度中
						    			</c:when>
						    			<c:when test="${result.status == '2'}">
						    				调度成功
						    			</c:when>
						    			<c:when test="${result.status == '3'}">
						    				调度失败
						    			</c:when>
						    			<c:when test="${result.status == '4'}">
						    				调度暂停
						    			</c:when>
						    			<c:when test="${result.status == '5'}">
						    				调度停止
						    			</c:when>
						    			<c:otherwise>
						    			</c:otherwise>
						    		</c:choose>
								</td>
								<td>									
									<s:iterator value="buttonMenuList" var="buttonRight">
										<s:if test="%{#buttonRight.resourceLevel == 5}">
											<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}','${result.scheduleId}')"  class="tablelink">${buttonRight.resourceName}</a>
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
