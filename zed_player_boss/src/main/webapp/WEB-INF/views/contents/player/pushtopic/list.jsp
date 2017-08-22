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
					<label>类型</label>
					<select name="topicType" id="topicType" class="dayinput form-control" style="width:150px;">
						<option value="" selected="selected" <c:if test = "${topicType == ''}">selected="selected"</c:if>>请选择</option>
						<option value="1" <c:if test = "${topicType == '1'}">selected="selected"</c:if>>版本</option>
						<option value="2" <c:if test = "${topicType == '2'}">selected="selected"</c:if>>渠道</option>
						<option value="3" <c:if test = "${topicType == '3'}">selected="selected"</c:if>>语言</option>
					</select>
				</li>
				<li>
					<label>状态</label>
					<select name="status" id="status" class="dayinput form-control" style="width:150px;">
						<option value="" selected="selected" <c:if test = "${status == ''}">selected="selected"</c:if>>请选择</option>
						<option value="-1" <c:if test = "${status == '-1'}">selected="selected"</c:if>>废弃（删除）</option>
						<option value="1" <c:if test = "${status == '1'}">selected="selected"</c:if>>有效</option>
					</select>
				</li>
				<li><label>国家码</label>
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
					<th>标识</th>
					<th>国家码</th>
					<th>类型</th>
					<th>类型对应的值</th>
					<th>名称</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="page.result" var="list">
					<tr>
								<td><input name="checkbox_list" id="checkbox_list" type="checkbox" onclick="selectCheckBoxList()" value="<s:property value="#list.topicId" />"/></td>
								<td><s:property value="#list.topicId" /></td>
								<td>
									<s:iterator value="playerCountryList" var="playerCountry">
										<s:if test="%{#playerCountry.countryCode == #list.countryCode}">
											<s:property value="#playerCountry.countryNameZh"/>（<s:property value="#playerCountry.countryCode"/>）
										</s:if>
									</s:iterator>
								</td>
								<td>
									<c:choose>
						    			<c:when test="${list.topicType == '1'}">
						    				版本
						    			</c:when>
						    			<c:when test="${list.topicType == '2'}">
						    				渠道
						    			</c:when>
						    			<c:when test="${list.topicType == '3'}">
						    				语言
						    			</c:when>
						    			<c:otherwise>
						    			</c:otherwise>
						    		</c:choose>
								</td>
								<td><s:property value="#list.typeValue" /></td>
								<td><s:property value="#list.topicName" /></td>
								<td>
									<c:choose>
						    			<c:when test="${list.status==1}">
						    				有效
						    			</c:when>
						    			<c:when test="${list.status==-1}">
						    				废弃（删除）
						    			</c:when>
						    			<c:otherwise>
						    			</c:otherwise>
						    		</c:choose>
								</td>
								<td>									
									<s:iterator value="buttonMenuList" var="buttonRight">
										<s:if test="%{#buttonRight.resourceLevel == 5}">
											<s:if test="%{#list.status==1}">
												<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}','${list.topicId}')"  class="tablelink">${buttonRight.resourceName}</a>
											</s:if>
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
