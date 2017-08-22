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
					<label>服务器地址</label>
					<input id="serverAddress" name="serverAddress" type="text" class="scinput form-control" maxlength="128" value = "${serverAddress}"/>
				</li>
				<li>
					<label>类型</label>
					<select name="origin" id="origin" class="dayinput form-control" style="width:150px;">
						<option value="" selected="selected" <c:if test = "${origin == ''}">selected="selected"</c:if>>请选择</option>
						<option value="1" <c:if test = "${origin == '1'}">selected="selected"</c:if>>直播</option>
						<option value="2" <c:if test = "${origin == '2'}">selected="selected"</c:if>>Player</option>
					</select>
				</li>
				<li>
					<label>地区</label>
					<select name="areaCode" id="areaCode" class="dayinput form-control" style="width:150px;">
						<option value="" selected="selected" <c:if test = "${areaCode == ''}">selected="selected"</c:if>>请选择</option>
						<option value="62" <c:if test = "${areaCode == '62'}">selected="selected"</c:if>>印度尼西亚（62）</option>
						<option value="66" <c:if test = "${areaCode == '66'}">selected="selected"</c:if>>泰国（66）</option>
						<option value="84" <c:if test = "${areaCode == '84'}">selected="selected"</c:if>>越南（84）</option>
						<option value="86" <c:if test = "${areaCode == '86'}">selected="selected"</c:if>>中国（86）</option>
						<option value="91" <c:if test = "${areaCode == '91'}">selected="selected"</c:if>>印度（91）</option>
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
					<th>地区</th>
					<th>服务器地址</th>
					<th>类型</th>
					<th>创建时间</th>
					<th>创建人</th>
					<th>修改时间</th>
					<th>修改人</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="page.result" var="list">
						<tr>
								<td><input name="checkbox_list" id="checkbox_list" type="checkbox" onclick="selectCheckBoxList()" value="<s:property value="#list.info_id" />"/></td>
								<td>
						    		<c:choose>
						    			<c:when test="${list.area_code == '91'}">
						    				印度
						    			</c:when>
						    			<c:when test="${list.area_code == '86'}">
						    				中国
						    			</c:when>
						    			<c:when test="${list.area_code == '62'}">
						    				印度尼西亚
						    			</c:when>
						    			<c:when test="${list.area_code == '66'}">
						    				泰国
						    			</c:when>
						    			<c:when test="${list.area_code == '84'}">
						    				越南
						    			</c:when>
						    			<c:otherwise>
						    			</c:otherwise>
						    		</c:choose>
								</td>
								<td><s:property value="#list.server_address" /></td>
								<td>
						    		<c:choose>
						    			<c:when test="${list.origin == '1'}">
						    				直播
						    			</c:when>
						    			<c:when test="${list.origin == '2'}">
						    				Player
						    			</c:when>
						    			<c:otherwise>
						    			</c:otherwise>
						    		</c:choose>
								</td>
								<td><fmt:formatDate value="${list.create_time}" type="both"/></td>
								<td><s:property value="#list.creater" /></td>
								<td><fmt:formatDate value="${list.update_time}" type="both"/></td>
								<td><s:property value="#list.updater" /></td>
								<td>													
									<s:iterator value="buttonMenuList" var="buttonRight">
										<s:if test="%{#buttonRight.resourceLevel == 5}">
											<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}','${list.info_id}')"  class="tablelink">${buttonRight.resourceName}</a>
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
