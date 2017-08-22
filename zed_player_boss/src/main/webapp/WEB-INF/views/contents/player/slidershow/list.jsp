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
	<script type="text/javascript" src="<s:property value="jsPath" />layer/layer.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />loading.js"></script>
</head>
<script type="text/javascript">
function checkData(url, status, sliderShowId, areaCode, sort) { 
	//只有当欧式启动时才会验证
	if(status == 0) {
		setStatus(url+"playerSliderShow.status="+status+"&playerSliderShow.sort="+sort+"&playerSliderShow.areaCode="+areaCode+"&playerSliderShow.sliderShowId=", sliderShowId);
	} else {
	    var data = {"areaCode": areaCode, "sort":sort, "sliderShowId":sliderShowId};
	    var validateUrl  = '<s:url action="isExist.action" />';
	    $.ajax({
	        type: "POST",
	        url: validateUrl,
	        data: data,
	        dataType : "json",
	        success:function(data) {
		       	if(data && data.data) {
		       		layer.open({
		      		  content: data.areaCodeNames+'地区展示位已存在，启用将会替换现有展示位轮播图？',
		      		  btn: ['确定', '取消'],
		      		  yes: function(index, layero){
		      			layer.close(index);
	      				setStatus(url+"playerSliderShow.status="+status+"&playerSliderShow.sort="+sort+"&playerSliderShow.areaCode="+areaCode+"&playerSliderShow.sliderShowId=", sliderShowId);
		      		  },
		      		  cancel: function(index, layero){ 
		      		      layer.close(index);
		      			} 
		      		});
		       	} else {
		       		setStatus(url+"playerSliderShow.status="+status+"&playerSliderShow.sort="+sort+"&playerSliderShow.areaCode="+areaCode+"&playerSliderShow.sliderShowId=", sliderShowId);
		       	}
	        },
	        error : function (XMLHttpRequest, textStatus, errorThrown) {
	        }
		 }); 
	}
}
</script>
<body>
	<%@ include file="../../../include/navigation.jsp" %>
    <div class="mainindex">
	<form id = "form" action="<s:url action="list" />" method="post" name="form">
		<ul class="seachform">
			<li>
				<label>描述:</label>
				<input id="description" name="description" type="text" class="scinput form-control" maxlength="32" value = "${description}" placeholder="请输入关键字搜索"/>
			</li>
			<li><label>国家码:</label>
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
		<table class="tablelist">
			<thead>
				<tr>
					<th><input name = "selectCheckbox" id = "selectCheckbox" type="checkbox" value=""/></th>
					<th>轮播图Url</th>
					<th>类型</th>
					<th>状态</th>
					<th>来源</th>
					<th>国家码</th>
					<th>展示位</th>
					<th>描述</th>
					<th>开始时间</th>
					<th>结束时间</th>
					<th>创建人</th>
					<th>最后更新时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="page.result" var="list">
					<tr>
								<td><input name="checkbox_list" id="checkbox_list" type="checkbox" onclick="selectCheckBoxList()" value="<s:property value="#list.slider_show_id" />"/></td>
								<td><img src="${list.img_url}" style="width:30px; height: 30px;"/></td>
								<td><s:if test="#list.type == 1">影片</s:if><s:else>链接</s:else></td>
								<td><s:if test="#list.status == 1"><span style="color:blue;">启用</span></s:if><s:else><span style="color:red;">禁用</span></s:else></td>
								<td><s:property value="#list.origin" /></td>
								<td>
									<s:iterator value="playerCountryList" var="playerCountry">
										<s:if test="%{#playerCountry.countryCode == #list.area_code}">
											<s:property value="#playerCountry.countryNameZh"/>（<s:property value="#playerCountry.countryCode"/>）
										</s:if>
									</s:iterator>
								</td>
								<td><s:property value="#list.sort" /></td>
								<td><s:property value="#list.description" /></td>
								<td><fmt:formatDate value="${list.start_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td><fmt:formatDate value="${list.end_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td><s:property value="#list.creater" /></td>
								<td><fmt:formatDate value="${list.last_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td>									
									<s:iterator value="buttonMenuList" var="buttonRight">
										<s:if test="%{#buttonRight.resourceLevel == 5}">
											<s:if test="%{#buttonRight.resourceKey == 'menu_slidershow_table_status_right'}">
												<s:if test="#list.status == 1">
													<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}?','0','${list.slider_show_id}','${list.area_code}','${list.sort}')"  class="tablelink">禁用</a>
												</s:if>
												<s:elseif test="#list.status == 0">
													<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}?','1','${list.slider_show_id}','${list.area_code}','${list.sort}')"  class="tablelink">启用</a>
												</s:elseif>
											</s:if>
											<s:else>
												<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}','${list.slider_show_id}')"  class="tablelink">${buttonRight.resourceName}</a>
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
