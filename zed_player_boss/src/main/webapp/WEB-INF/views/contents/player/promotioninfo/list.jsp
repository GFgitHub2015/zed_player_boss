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
	<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />loading.css"/>
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />window.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />function.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />loading.js"></script>

<script type="text/javascript">
var commitUrl ="";
var commitParam="";
var topFlagIds="${topFlagIds}";
function updateTopFlag(url ,param){
	commitUrl = url;
	commitParam = param;
	if(topFlagIds!=''){
		openConfirmWindow('提示信息','已有置顶记录,是否确认修改 ？','确定', 'updateTopFlagCommit()','closeConfirmWindow()','取消');
	}else{
		updateTopFlagCommit();
	}
}
function updateTopFlagCommit(){
	LoadingPic.FullScreenShow();
	window.location=commitUrl+commitParam+"&topFlagIds="+topFlagIds;
}
</script>
</head>

<body>
	<%@ include file="../../../include/navigation.jsp" %>
    
    <div class="mainindex">
	<form id = "form" action="<s:url action="list" />" method="post" name="form">
		<ul class="seachform">
				<li>
					<label>活动标题</label>
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
					<th>配图</th>
					<th>国家码</th>
					<th>活动标题</th>
					<th>活动链接</th>
					<th>活动起止时间</th>
					<th>上架起止时间</th>
					<th>状态</th>
					<th>位置状态</th>
					<th>创建时间</th>
					<th>是否启用</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="page.result" var="list">
					<tr>
								<td><input name="checkbox_list" id="checkbox_list" type="checkbox" onclick="selectCheckBoxList()" value="<s:property value="#list.id" />"/></td>
								<td><img src="<s:property value="#list.image_url" />" alt="" width="35px" /></td>
								 <td>
									<s:iterator value="playerCountryList" var="playerCountry">
										<s:if test="%{#playerCountry.countryCode == #list.country_code}">
											<s:property value="#playerCountry.countryNameZh"/>（<s:property value="#playerCountry.countryCode"/>）
										</s:if>
									</s:iterator>
								</td>
								<td title="${list.title }"> ${fn:substring(list.title ,0,30 )}</td>
								<td title="${list.link }"> ${fn:substring(list.link ,0,30 )}</td>
								<td>
								<fmt:formatDate value="${list.start_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
								至
								<fmt:formatDate value="${list.end_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td>
								<c:if test="${list.up_time !=null}">
									<fmt:formatDate value="${list.up_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
									至
									<fmt:formatDate value="${list.down_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</c:if>
								</td>
								<td>
									<c:set var="nowDate" value="<%=System.currentTimeMillis()%>"></c:set>
									<c:choose>
						    			<c:when test="${nowDate-list.start_time.getTime()<0}">
						    				即将开始
						    			</c:when>
						    			<c:when test="${nowDate-list.end_time.getTime()>0}">
						    				已结束
						    			</c:when>
						    			<c:otherwise>
						    				<b style="color:red;">进行时</b>
						    			</c:otherwise>
						    		</c:choose>
								</td>
								<td>
									<c:choose>
						    			<c:when test="${list.top_flag == '1'}">
						    				置顶
						    			</c:when>   
						    			<c:when test="${list.top_flag == '0'}">
						    				其他
						    			</c:when>
						    			<c:otherwise>
						    				其他
						    			</c:otherwise>
						    		</c:choose>
								</td>
								<td><fmt:formatDate value="${list.create_time}" type="both"/></td>
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
										<s:if test="#buttonRight.resourceLevel == 5">
											<s:if test="#buttonRight.resourceKey == 'menu_player_promotioninfo_table_status_righ'">
												<c:choose>
									    			<c:when test="${list.status == '1'}">
									    				<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}?status=0&infoId=','${list.id}')"  class="tablelink">禁用</a>
									    			</c:when>   
									    			<c:when test="${list.status == '0'}">
									    				<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}?status=1&infoId=','${list.id}')"  class="tablelink">启用</a>
									    			</c:when>
									    			<c:otherwise>
									    			</c:otherwise>
									    		</c:choose>
											</s:if>
											<s:elseif test="#buttonRight.resourceKey == 'menu_player_promotioninfo_table_top_right'">
												<c:choose>
									    			<c:when test="${list.status == '1'}">
														<s:if test="#list.top_flag == 1">
															<a href="javascript:updateTopFlag('${projectURLPrefix}${buttonRight.resourceUrl}?infoId=${list.id}&topFlag=','0')"  class="tablelink">取消置顶</a>
														</s:if>
														<s:else>
															<a href="javascript:updateTopFlag('${projectURLPrefix}${buttonRight.resourceUrl}?infoId=${list.id}&topFlag=','1')"  class="tablelink">置顶</a>
														</s:else>
													</c:when>   
									    			<c:when test="${list.status == '0'}">
									    			</c:when>
									    			<c:otherwise>
									    			</c:otherwise>
									    		</c:choose>
											</s:elseif>
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
