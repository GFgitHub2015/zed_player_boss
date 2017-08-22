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
	<script type="text/javascript">
		function addClass(userId, title, iconUrl) {
			var data = {"playerRelationHotUser.userId": userId, "playerRelationHotUser.title": title, "playerRelationHotUser.iconUrl": iconUrl};
		    var url  = '<s:url action="add.action" />';
		    $.ajax({
		    	type: "POST",
		        url: url,
		        data: data,
		        dataType : "json",
		        success:function(data) {
			       	 if(data) {
			       		location.reload();
		       		 } else {
		       			layer.alert('添加失败！');
		             }
		        },
		        error : function (XMLHttpRequest, textStatus, errorThrown) {
		        }
			 }); 
		}
		function back(){
			window.location = '<s:url action="list" />';
		}
		function alertMsg(){
		 	layer.msg('已添加，请勿重复点击添加！', {
		        time: 2000, //2s后自动关闭
		        btn: ['知道了']
		      });
		}
		function findYouTubeHotUser() {
			if($.trim($("#search").val()) == "") {
				layer.alert('请输入需要查询的频道名称！');
				return false;
			}
			$("#q").val($("#search").val());
			$("#form").submit();
		}
		//分页查询 
		function pageToken(pageToken) {
			$("#page").val(pageToken);
			$("#form").submit();
		}
	</script>
</head>

<body>
	<%@ include file="../../../include/navigation.jsp" %>
    <div class="mainindex">
	<form id = "form" method="post" name="form">
		<input id="page" name="youtubeParam.pageToken" type="hidden" />
		<input id="q" name="youtubeParam.q" type="hidden" value="${youtubeParam.q}"/>
 		<ul class="seachform"> 
				<li> 
				<input id="search" type="text" class="form-control" maxlength="50" value="${youtubeParam.q}" placeholder="请输入频道名称进行搜索"/>
				</li> 
				<li> 
					<label>&nbsp;</label> 
						<input type="button" class="scbtn btn" value="查询"  onclick="findYouTubeHotUser()" /> 
						<label>&nbsp;</label> 
					<li>
					    <input type="button" class="btn" value="返回" onclick="javascript:back()"/>
				    </li>
				</li> 
 		</ul> 
		<table class="tablelist">
			<thead>
				<tr>
					<th>频道名称</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="youTubePage.result" var="list">
					<tr>
						<td><s:property value="#list.title" /></td>
						<td>									
							<s:if test="#list.isRecommond == 0">
								<a href="javascript:addClass('${list.userId}', '${list.title}', '${list.iconUrl}');"  class="tablelink">推荐</a>
							</s:if>
							<s:else>
								<a href="javascript:alertMsg();" class="tablelink" style="background: #c0c0c0;">已推荐</a>
							</s:else>
						</td>
					</tr>				
				</s:iterator>
			</tbody>
		</table>
		<div style="float:right; padding-top: 10px">
			<span>
				<s:if test="youTubePage.prevPageToken != null and youTubePage.prevPageToken != ''">
					<input type="button" value="上一页" onclick="pageToken('<s:property value="youTubePage.prevPageToken"/>')" class="scbtn btn"/>
				</s:if>
			</span>
		    <span style="padding-left: 20px">
		    	<s:if test="youTubePage.nextPageToken != null and youTubePage.nextPageToken != ''">
					<input type="button" value="下一页" onclick="pageToken('<s:property value="youTubePage.nextPageToken"/>')" class="scbtn btn"/>
				</s:if>
		    </span>
		</div>
	</form>
	</div>
</body>
</html>
