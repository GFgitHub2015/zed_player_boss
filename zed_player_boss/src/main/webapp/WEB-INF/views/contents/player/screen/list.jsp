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
	<script type="text/javascript" src="<s:property value="jsPath" />My97DatePicker/WdatePicker.js"></script>
</head>

<body>
	<%@ include file="../../../include/navigation.jsp" %>
    
    <div class="mainindex">
	<form id = "form" action="<s:url action="list" />" method="post" name="form">
		<ul class="seachform">
				<li>
					<label>开始时间</label>
					<input  name="startDate" class="Wdate" value="${startDate}"  readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
				</li>
				<li>
					<label>结束时间</label>
					<input  name="endDate" class="Wdate" value="${endDate}"  readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
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
					<th>配图</th>
					<th>链接</th>
					<th>类型</th>
					<th>活动起止时间</th>
					<th>状态</th>
					<th>创建时间</th>
					<th>是否启用</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="page.result" var="list">
					<tr>
						<td><input name="checkbox_list" id="checkbox_list" type="checkbox" onclick="selectCheckBoxList()" value="<s:property value="#list.id" />"/></td>
						<td><img src="<s:property value="#list.imageUrl" />" alt="" width="35px" /></td>
						<td><s:property value="#list.link" /></td>
						<td>
							<c:choose>
								<c:when test="${list.linkType == 1}">
									活动
								</c:when>
								<c:otherwise>
									电影
								</c:otherwise>
							</c:choose>
						</td>
						<td>
						<fmt:formatDate value="${list.startTime}" type="both"/>
						至
						<fmt:formatDate value="${list.endTime}" type="both"/>
						</td>
						<td>
							<c:set var="nowDate" value="<%=System.currentTimeMillis()%>"></c:set>
							<c:choose>
								<c:when test="${nowDate-list.startTime.getTime()<0}">
									即将开始
								</c:when>
								<c:when test="${nowDate-list.endTime.getTime() > 0}">
									已结束
								</c:when>
								<c:otherwise>
									<b style="color:red;">进行时</b>
								</c:otherwise>
							</c:choose>
						</td>
						<td><fmt:formatDate value="${list.createTime}" type="both"/></td>
						<td>
							<c:choose>
								<c:when test="${list.status == '1'}">
									启用
								</c:when>
								<c:when test="${list.status == '0'}">
									禁用
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<s:iterator value="buttonMenuList" var="buttonRight">
										<s:if test="%{#buttonRight.resourceLevel == 5}">
											<s:if test="%{#buttonRight.resourceKey == 'menu_player_screen_table_status_righ'}">
												<s:if test="#list.status == 1">
													<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}?status=0&infoId=','${list.id}')"  class="tablelink">禁用</a>
												</s:if>
												<s:elseif test="#list.status == 0">
													<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}?status=1&infoId=','${list.id}')"  class="tablelink">启用</a>
												</s:elseif>
											</s:if>
											<s:else>
												<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}','${list.id}')"  class="tablelink">${buttonRight.resourceName}</a>
											</s:else>
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
