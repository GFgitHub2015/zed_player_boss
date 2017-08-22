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
					<label>版本编号</label>
					<input name="versionId" type="text" class="scinput form-control" maxlength="50" value = "${versionId}"/>
				</li>
				<li>
					<label>版本号</label>
					<input name="versionCode" type="text" class="scinput form-control" maxlength="50" value = "${versionCode}"/>
				</li>
				<li>
					<label>app包名</label>
					<input name="packageName" type="text" class="scinput form-control" maxlength="100" value = "${packageName}"/>
				</li>
				<li>
					<label>应用类型</label>
					<select name="appType" id="appType" class="dayinput form-control" style="width:150px;">
						<option value="" selected="selected" <c:if test = "${appType == ''}">selected="selected"</c:if>>请选择</option>
						<option value="1" <c:if test = "${appType == '1'}">selected="selected"</c:if>>GP版</option>
						<option value="2" <c:if test = "${appType == '2'}">selected="selected"</c:if>>官网版</option>
						<option value="3" <c:if test = "${appType == '3'}">selected="selected"</c:if>>其它</option>
					</select>
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
					<label>&nbsp;</label>
					<input type="submit" class="scbtn btn" value="查询" onclick="initPageNo()"/>
				</li>
		</ul>
		<s:include value="../../../include/buttonTop.jsp"/>
		<table class="tablelist">
			<thead>
				<tr>
<!-- 					<th><input name = "selectCheckbox" id = "selectCheckbox" type="checkbox" value=""/></th> -->
					<th>编号</th>
					<th>版本号</th>
					<th>app包名</th>
<!-- 					<th>MD5</th> -->
<!-- 					<th>下载地址</th> -->
<!-- 					<th>应用类型</th> -->
					<th>渠道</th>
					<th>状态</th>
					<th>是否强制升级</th>
<!-- 					<th>修改时间</th> -->
					<th>修改人</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="page.result" var="list">
					<tr>
<%-- 								<td><input name="checkbox_list" id="checkbox_list" type="checkbox" onclick="selectCheckBoxList()" value="<s:property value="#list.keyword_id" />"/></td> --%>
								<td><s:property value="#list.version_id" /></td>
								<td><s:property value="#list.version_code" /></td>
								<td><s:property value="#list.packagename" /></td>
								<%-- <td title="${list.MD5 }">
						            <c:choose>
						            	<c:when test="${fn:length(list.MD5)>'40'}">  
							    				${fn:substring(list.MD5,0,40)}...  
							    		</c:when>
							    		<c:otherwise>
							    				${list.MD5 }
							    		</c:otherwise>
						            </c:choose>
								</td>
								<td title="${list.download_url }"> ${fn:substring(list.download_url ,0,30 )}...</td> --%>
<%-- 								<td><s:property value="#list.appType" /></td> --%>
								<td><s:property value="#list.channel" /></td>
								<%-- <td <c:if test = "${list.appType == '1'}">style="color:#FF0000;"</c:if>>
						    		<c:choose>
						    			<c:when test="${list.appType == '1'}">
						    				google版
						    			</c:when>
						    			<c:when test="${list.appType == '2'}">
						    				官网版本
						    			</c:when>
						    			<c:otherwise>
						    				其他
						    			</c:otherwise>
						    		</c:choose>
								</td> --%>
								<td <c:if test = "${list.status == '1'}">style="color:#FF0000;"</c:if>>
						    		<c:choose>
						    			<c:when test="${list.status == '0'}">
						    				不可下载
						    			</c:when>
						    			<c:when test="${list.status == '1'}">
						    				可下载
						    			</c:when>
						    			<c:otherwise>
						    			</c:otherwise>
						    		</c:choose>
								</td>
								<td <c:if test = "${list.is_must == '1'}">style="color:#FF0000;"</c:if>>
						    		<c:choose>
						    			<c:when test="${list.is_must == '2'}">
						    				是
						    			</c:when>
						    			<c:when test="${list.is_must == '1'}">
						    				否
						    			</c:when>
						    			<c:otherwise>
						    			</c:otherwise>
						    		</c:choose>
								</td>
<%-- 								<td><fmt:formatDate value="${list.update_time}" type="both"/></td> --%>
								<td><s:property value="#list.editer" /></td>
								<td>													
									<s:iterator value="buttonMenuList" var="buttonRight">
										<s:if test="%{#buttonRight.resourceLevel == 5}">
											<s:if test="%{#buttonRight.resourceKey == 'menu_player_content_rw_table_status_righ'}">
												<s:if test="#list.status == 1">
													<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}?status=0&packageName=${list.packagename}&versionId=','${list.version_id}')"  class="tablelink">禁用</a>
												</s:if>
												<s:elseif test="#list.status == 0">
													<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}?status=1&packageName=${list.packagename}&versionId=','${list.version_id}')"  class="tablelink">启用</a>
												</s:elseif>
											</s:if>
											<s:else>
												<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}','${list.version_id}&packageName=${list.packagename}&channel=${list.channel }')"  class="tablelink">${buttonRight.resourceName}</a>
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
