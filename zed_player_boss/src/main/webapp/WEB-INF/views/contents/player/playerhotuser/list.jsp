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
	<style type="">
		.btn{width: 110px !important;}
		.sort{cursor: pointer;}
	</style>
	<script type="text/javascript">
	//批量禁用
	function batchDisable(url) {
		updateHotUserStatus(url, "DISABLE");
	}
	//批量禁用全部
	function batchDisableAll(url) {
		updateHotUserStatus(url,"DISABLE_ALL");
	}
	//批量启用
	function batchEnable(url) {
		updateHotUserStatus(url,"ENABLE");
	}
	//批量启用全部
	function batchEnableAll(url){
		updateHotUserStatus(url,"ENABLE");
	}
	
	function updateHotUserStatus(url , status){
		if($("#selectCheckbox").val() == ""){
			openAlertmWindow('提示信息','请选择记录。','确认');
		}else{
			url = url+"?status="+status+"&playerRelationHotUser.userId="+$("#selectCheckbox").val();
			openConfirmWindow('提示信息','是否确认修改？','确认', 'updateProcess(\''+url+'\')','closeConfirmWindow()','取消')
		}
	}
	function showChangeSort(userid){
		$("#sort-"+userid).hide();
		$("#text-"+userid).show();
	}
	function changeSort(userId) {
		var url = '<s:url action="updateSortByUid.action" />';
		var sort = $.trim($("#text-" + userId).val());
		if(sort == null || sort == '') {
			layer.alert("展示位不能为空!");
			return false;
		}
		if(sort == 0) {
			layer.alert("展示位不能为零!");
			return false;
		}
		var data = {"playerRelationHotUser.userId" : userId, "playerRelationHotUser.sort" : sort};
		 $.ajax({
	         type: "POST",
	         url: url,
	         data: data,
	         dataType : "json",
	         success:function(data) {
	        	 if(data) {
		       		 if(data.flag) {
		       			$("#sort-"+userId).html(sort);
		        		$("#sort-"+userId).show();
						$("#text-"+userId).hide();
		       			layer.open({
		          		  content: '展示位修改成功，是否刷新当前页 ？',
		          		  btn: ['确定', '取消'],
		          		  yes: function(index, layero){
		          				layer.close(index);
		         			  	location.reload();
		          		  },
		          		  cancel: function(index, layero){ 
		          		    	layer.close(index);
	         			  } 
		         		});
		       		 } else {
		       			layer.alert('更改展示位失败！');
		       			location.reload();
		             }
		       	 }
	         },
	         error : function (XMLHttpRequest, textStatus, errorThrown) {
	        	 LoadingPic.FullScreenHide();
	    		 layer.alert("修改出错!");
	         }
		 }); 
	}
	</script>
</head>
<body>
	<%@ include file="../../../include/navigation.jsp" %>
    
    <div class="mainindex">
	<form id = "form" action="<s:url action="list" />" method="post" name="form">
		<ul class="seachform">
				<li>
					<label>用户编号：</label>
					<input id="userId" name="playerRelationHotUser.userId" type="text" class="scinput form-control" maxlength="32" style="width: 300px" value = "${playerRelationHotUser.userId}"/>
				</li>
				<li>
					<label>用户状态：</label>
					<select name="playerRelationHotUser.status" id="status" class="dayinput form-control" style="width:150px;">
						<option value="" selected="selected" <c:if test = "${playerRelationHotUser.status == ''}">selected="selected"</c:if>>请选择</option>
						<option value="1" <c:if test = "${playerRelationHotUser.status == '1'}">selected="selected"</c:if>>启用</option>
						<option value="0" <c:if test = "${playerRelationHotUser.status == '0'}">selected="selected"</c:if>>禁用</option>
						<option value="-1" <c:if test = "${playerRelationHotUser.status == '-1'}">selected="selected"</c:if>>禁用全部</option>
					</select>
				</li>
				<li>
					<label>用户来源：</label>
					<select name="playerRelationHotUser.origin" id="origin" class="dayinput form-control" style="width:150px;">
						<option value="" selected="selected" <c:if test = "${playerRelationHotUser.origin == null}">selected="selected"</c:if>>请选择</option>
						<option value="0" <c:if test = "${playerRelationHotUser.origin == 0}">selected="selected"</c:if>>GBox</option>
						<option value="1" <c:if test = "${playerRelationHotUser.origin == 1}">selected="selected"</c:if>>Youtube</option>
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
					<th>用户头像</th>
					<th>用户编号</th>
					<th>昵称</th>
					<th>来源</th>
					<th>状态</th>
					<th>排序</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="page.result" var="list">
					<tr>
						<td><input name="checkbox_list" id="checkbox_list" type="checkbox" onclick="selectCheckBoxList()" value="<s:property value="#list.userId" />"/></td>
						<td><img src="${list.iconUrl}" style="width:35px; height: 35px;"/></td>
						<td><s:property value="#list.userId" /></td>
						<td><s:property value="#list.title" /></td>
						<td><s:if test="#list.origin == 0">GBox</s:if><s:else>YouTube</s:else></td>
						<td><s:if test="#list.status == -1"><span style="color:#FF0000;">账号禁用</span></s:if><s:elseif test="#list.status == 0"><span style="color:#FF0000;">半启用</span></s:elseif><s:elseif test="#list.status == 1">启用</s:elseif></td>
						<td>
							<span class="sort" id="sort-${list.userId}" onclick="showChangeSort('${list.userId}')" >${list.sort}</span>
                   			<input type="text" onblur="changeSort('${list.userId}')" id="text-${list.userId}" value="${list.sort}" class="scinput form-control validate[required, minSize[1], maxSize[1000]]" style="display: none;"/>
						</td>
						<td>									
							<s:iterator value="buttonMenuList" var="buttonRight">
								<s:if test="%{#buttonRight.resourceLevel == 5}">
									<s:if test="%{#buttonRight.resourceKey == 'menu_player_hot_user_status' and #list.origin != 1}">
										<s:if test="%{#list.status == 1 || #list.status==null}">
											<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}?status=DISABLE&playerRelationHotUser.userId=${list.userId}')"  class="tablelink">禁用</a>
										</s:if>
										<s:elseif test="#list.origin != 1">
											<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}?status=ENABLE&playerRelationHotUser.userId=${list.userId}')"  class="tablelink">启用</a>
										</s:elseif>
									</s:if>
									<s:elseif test="%{#buttonRight.resourceKey == 'menu_player_hot_user_updateall' and #list.origin != 1}">
										<s:if test="%{#list.status != -1}">
											<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}?status=DISABLE_ALL&playerRelationHotUser.userId=${list.userId}')"  class="tablelink">禁用全部</a>
										</s:if>
									</s:elseif>
									<s:elseif test="%{#buttonRight.resourceKey != 'menu_player_hot_user_status' and #buttonRight.resourceKey != 'menu_player_hot_user_updateall'}">
										<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}?playerRelationHotUser.uid=',  '${list.uid}')"  class="tablelink">${buttonRight.resourceName}</a>
									</s:elseif>
								</s:if>
							</s:iterator>
						</td>
					</tr>	
				</s:iterator>
			</tbody>	
		</table>
		<%@ include file="../../../include/buttonRight.jsp" %>
		<%@ include file="../../../include/page.jsp" %>
		<div style="padding-top: 10%;padding-left: 15%">
			<b><font style="color: red;font-size: large">“禁用”会屏蔽热门用户，“禁用全部”不仅会屏蔽热门用户，还会使GBOX的用户资源搜索被屏蔽，但不影响YouTube用户的资源搜索</font></b>
		</div>
	</form>
</div>
<script type="text/javascript">
	$(document).ready(function() {  
		$(".toolbar").find("li:last-child").find("a").attr("style", "width: 138px !important;");
	});  
</script>
</body>
</html>
