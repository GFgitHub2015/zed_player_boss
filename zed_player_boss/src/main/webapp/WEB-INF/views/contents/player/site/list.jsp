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
					<label>网站标题</label>
					<input id="title" name="title" type="text" class="scinput form-control" maxlength="128" value = "${title}"/>
				</li>
				<li>
					<label>状态</label>
					<select name="status" id="status" class="dayinput form-control" style="width:150px;">
						<option value="" selected="selected" <c:if test = "${status == ''}">selected="selected"</c:if>>请选择</option>
						<option value="1" <c:if test = "${status == '1'}">selected="selected"</c:if>>启用</option>
						<option value="0" <c:if test = "${status == '0'}">selected="selected"</c:if>>禁用</option>
					</select>
				</li>
				<li>
					<select name="areaCode" id="areaCode" class="dayinput form-control" style="width:500px;">
							<option value="" <c:if test = "${areaCode == ''}">selected="selected"</c:if>>请选择</option>
							<c:if test="${not empty playerCountryList}">
								<s:iterator value="playerCountryList" var="playerCountry">
									<option value="<s:property value="#playerCountry.countryCode"/>" <s:if test='%{#request.areaCode == #playerCountry.countryCode}'>selected="selected"</s:if>>
										<s:property value="#playerCountry.countryNameZh"/>（<s:property value="#playerCountry.countryCode"/>）
									</option>
								</s:iterator>
							</c:if>
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
					<th><input name = "selectCheckbox" id = "selectCheckbox" type="checkbox" value=""/></th>
					<th>网站图标</th>
					<th>网站标题</th>
					<th>网站链接</th>
					<th>排序</th>
					<th>是否启用</th>
					<th>所属国家</th>
<!-- 					<th>创建人</th>
					<th>创建时间</th>
					<th>修改人</th>
					<th>修改时间</th> -->
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="page.result" var="list">
					<tr>
								<td><input name="checkbox_list" id="checkbox_list" type="checkbox" onclick="selectCheckBoxList()" value="<s:property value="#list.site_navigate_id" />"/></td>
								<td><img src="${list.img_url}" style="width:30px; height: 30px;"/></td>
								<td><s:property value="#list.title" /></td>
								<td><s:property value="#list.site_url" /></td>
								<td><s:property value="#list.sort" /></td>
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
									<s:iterator value="playerCountryList" var="playerCountry">
										<s:if test="%{#playerCountry.countryCode == #list.country_code}">
											<s:property value="#playerCountry.countryNameZh"/>（<s:property value="#playerCountry.countryCode"/>）
										</s:if>
									</s:iterator>
								</td>
							<%-- 	<td><s:property value="#list.creater" /></td>
								<td>
									<fmt:formatDate value="${list.create_time}" type="both"/>
								</td>
								<td><s:property value="#list.updater" /></td>
								<td>
									<fmt:formatDate value="${list.update_time}" type="both"/>
								</td> --%>
								<td>									
									<s:iterator value="buttonMenuList" var="buttonRight">
										<s:if test="%{#buttonRight.resourceLevel == 5}">
											<s:if test="%{#buttonRight.resourceKey == 'menu_player_site_table_status_righ'}">
												<s:if test="#list.status == 1">
													<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}?status=0&siteNavigateId=','${list.site_navigate_id}')"  class="tablelink">禁用</a>
												</s:if>
												<s:elseif test="#list.status == 0">
													<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}?status=1&siteNavigateId=','${list.site_navigate_id}')"  class="tablelink">启用</a>
												</s:elseif>
											</s:if>
											<s:else>
												<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}','${list.site_navigate_id}')"  class="tablelink">${buttonRight.resourceName}</a>
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
