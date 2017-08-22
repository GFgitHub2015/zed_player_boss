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
					<label>文件ID</label>
					<input name="fileId" type="text"  class="scinput form-control" style="width: 280px;" maxlength="100" value = "${fileId}"/>
				</li>
				<li>
					<label>文件名</label>
					<input name="fileName" type="text"  class="scinput form-control" style="width: 380px;" maxlength="100" value = "${fileName}"/>
				</li>
				<li>
					<label>网盘用户编号</label>
					<input name="userId" type="text"  class="scinput form-control" maxlength="100" style="width: 200px;" value = "${userId}"/>
				</li>
				<%-- <li>
					<label>状态</label>
					<select name="status" id="status" class="dayinput form-control" style="width:150px;">
						<option value="" selected="selected" <c:if test = "${status == ''}">selected="selected"</c:if>>请选择</option>
						<option value="0" <c:if test = "${status == '0'}">selected="selected"</c:if>>禁用</option>
						<option value="1" <c:if test = "${status == '1'}">selected="selected"</c:if>>启用</option>
					</select>
				</li> --%>
				<li>
					<select name="areaCode" id="areaCode" class="dayinput form-control" style="width:200px;">
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
		<h5>共${videoCount}视频/${subtitleCount}字幕</h5>
		<table class="tablelist">
			<thead>
				<tr>
					<th><input name = "selectCheckbox" id = "selectCheckbox" type="checkbox" value=""/></th>
					<th>资源封面</th>
					<th>资源ID</th>
					<th>所有人</th>
					<th>资源名称</th>
					<th>播放模式</th>
					<th>使用状态</th>
					<th>公开的国家</th>
					<th>公开状态</th>
					<th>播放时长</th>
					<th>视频文件大小</th>
					<th>播放次数/下载次数</th>
					<th>字幕数/已下载</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="page.result" var="list">
					<tr title="${list.name}">
								<td><input name="checkbox_list" id="checkbox_list" type="checkbox" onclick="selectCheckBoxList()" value="<s:property value="#list.fileId" />"/></td>
								<td><c:if test="${not empty list.imgUrl}"><a href="${list.imgUrl}" target="_blank"></c:if><c:if test="${empty list.imgUrl}"><a href="javascript:void(1);"></c:if><img src="${list.imgUrl}" style="width:30px; height: 30px;"/></a></td>
								<td><s:property value="#list.fileId" /></td>
								<td><s:property value="#list.nickName" /></td>
								<td>
									<c:if test="${fn:length(list.fileName)>50}">
										<c:out value="${fn:substring(list.fileName,0,50)}..." />
									</c:if>
									<c:if test="${fn:length(list.fileName)<=50}">
										<c:out value="${list.fileName}" />
									</c:if>
								</td>
								<td>
						    		<c:choose>
						    			<c:when test="${list.dimension == 2}">
						    				2D
						    			</c:when>
						    			<c:when test="${list.dimension == 3}">
						    				3D
						    			</c:when>
						    			<c:otherwise>
						    			</c:otherwise>
						    		</c:choose>
								</td>
								<td <c:if test = "${list.status == 0}">style="color:#FF0000;"</c:if>>
						    		<c:choose>
						    			<c:when test="${list.status == 0}">
						    				禁用
						    			</c:when>
						    			<c:when test="${list.status == 1}">
						    				启用
						    			</c:when>
						    			<c:otherwise>
						    			</c:otherwise>
						    		</c:choose>
								</td>
								<%-- <td>
									<c:forTokens items="${list.countryCode}" varStatus="cc" delims=" ">
										<s:iterator value="playerCountryList" var="playerCountry">
											<s:if test="%{#playerCountry.countryCode == #cc}">
												<s:property value="#playerCountry.countryNameZh"/>（<s:property value="#playerCountry.countryCode"/>）
											</s:if>
										</s:iterator>
									</c:forTokens>
								</td> --%>
								<td><s:property value="#list.countryCode" /></td>
								<td>
						    		<c:choose>
						    			<c:when test="${list.shareStatus == 0}">
						    				未公开
						    			</c:when>
						    			<c:when test="${list.shareStatus == 1}">
						    				已公开
						    			</c:when>
						    			<c:otherwise>
						    			</c:otherwise>
						    		</c:choose>
								</td>
								<td><s:property value="#list.duration" /></td>
								<td><s:property value="#list.fileSize" /></td>
								<td><s:property value="#list.playTimes" />/<s:property value="#list.downloadTimes" /></td>
								<td><a href="javascript:void(0)" onclick="goSubtitlePage('${list.fileId}');"><s:property value="#list.subtitleCount" />/<s:property value="#list.subtitleFileCount" /></a></td>
								<td>
									<s:iterator value="buttonMenuList" var="buttonRight">
										<s:if test="%{#buttonRight.resourceLevel == 5}">
											<s:if test="%{#buttonRight.resourceKey == 'menu_player_logicalfile_table_status_righ'}">
												<s:if test="#list.status == 1">
													<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}?status=0&fileId=','${list.fileId}')"  class="tablelink">禁用</a>
												</s:if>
												<s:elseif test="#list.status == 0">
													<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}?status=1&fileId=','${list.fileId}')"  class="tablelink">启用</a>
												</s:elseif>
											</s:if>
											<s:else>
												<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}','${list.fileId}')"  class="tablelink">${buttonRight.resourceName}</a>
											</s:else>
										</s:if>
									</s:iterator>
									<a href="${list.sd}"  target="_blank" class="tablelink">标清</a>
									<a href="${list.hd}"  target="_blank" class="tablelink">高清</a>
								</td>
						</tr>				
				</s:iterator>
			</tbody>
		</table>
		<%@ include file="../../../include/buttonRight.jsp" %>
		<%@ include file="../../../include/page.jsp" %>
	</form>
	
</div>
<script type="text/javascript">
	function goSubtitlePage(fileId) {
		window.location = '<s:url action="updatePage" />?fileId=' + fileId;
	}
</script>
</body>
</html>
