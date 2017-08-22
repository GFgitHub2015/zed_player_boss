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
					<label>待爬词</label>
					<input name="keyword" type="text" class="scinput form-control" maxlength="50" value = "${keyword}"/>
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
					<th>待爬词</th>
					<th>状态</th>
					<th>创建时间</th>
					<th>修改时间</th>
					<th>修改人</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="page.result" var="list">
					<tr>
								<td><input name="checkbox_list" id="checkbox_list" type="checkbox" onclick="selectCheckBoxList()" value="<s:property value="#list.keyword_id" />"/></td>
								<td><s:property value="#list.keyword" /></td>
								<td <c:if test = "${list.status == '0'}">style="color:#FF0000;"</c:if>>
						    		<c:choose>
						    			<c:when test="${list.status == '0'}">
						    				待爬
						    			</c:when>
						    			<c:when test="${list.status == '1'}">
						    				爬去成功
						    			</c:when>
						    			<c:when test="${list.status == '3'}">
						    				爬取不到资源
						    			</c:when>
						    			<c:when test="${list.status == '-1'}">
						    				废弃
						    			</c:when>
						    			<c:otherwise>
						    			</c:otherwise>
						    		</c:choose>
								</td>
								<%-- <td>
									<c:choose>
				    					<c:when test="${not empty areaCodeList}">
											<s:iterator value="areaCodeList" var="areaCodeNumber">
												<c:if test="${areaCodeNumber eq list.area_code}">${areaCodeNumber}</c:if>
											</s:iterator>
				    					</c:when>
						    			<c:otherwise>
						    			</c:otherwise>
				    				</c:choose>
								</td> --%>
								<td><fmt:formatDate value="${list.create_time}" type="both"/></td>
								<td><fmt:formatDate value="${list.update_time}" type="both"/></td>
								<td><s:property value="#list.updater" /></td>
								<td>													
									<%-- <s:include value="../../../include/buttonRight.jsp">
										<s:param name="key_id" value="#list.keyword_id" />
									</s:include> --%>
								</td>
						</tr>				
				</s:iterator>
			</tbody>
		</table>
		<%@ include value="../../../include/buttonRight.jsp" %>
		<%@ include file="../../../include/page.jsp" %>
	</form>
	
</div>

</body>
</html>
