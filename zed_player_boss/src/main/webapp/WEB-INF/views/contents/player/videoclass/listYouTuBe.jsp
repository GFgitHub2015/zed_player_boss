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
		function isExist(key,keyName){
		    var data = {"classKey": key,"description":keyName};
		    var url  = '<s:url action="isExist.action" />';
		    $.ajax({
		        type: "POST",
		        url: url,
		        data: data,
		        dataType : "json",
		        success:function(data){
		       	 if(data){
		       		 if(data.data){
		       			layer.alert('该分类已存在！');
		       		 }else{
		       			layer.open({
		          		  content: '是否确认添加 ？',
		          		  btn: ['确定', '取消'],
		          		  yes: function(index, layero){
		       				addClass(key,keyName);
		          			layer.close(index);
		          		  },
		          		  cancel: function(index, layero){ 
		          		      layer.close(index);
		          			} 
		          		});
		             }
		       	 }
		        },
		        error : function (XMLHttpRequest, textStatus, errorThrown) {
		        }
			 }); 
		}
		
		function addClass(key,keyName){
			var data = {"classKey": key,"description":keyName};
		    var url  = '<s:url action="add.action" />';
		    $.ajax({
		    	type: "POST",
		        url: url,
		        data: data,
		        dataType : "json",
		        success:function(data){
		       	 if(data){
		       		 if(data.flag){
// 		       			layer.alert('添加成功！');
		       			location.reload();
		       		 }else{
		       			layer.alert('添加失败！');
		             }
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
	</script>
</head>

<body>
	<%@ include file="../../../include/navigation.jsp" %>
    <div class="mainindex">
    <li style="padding-bottom: 10px">
	    <input name="" type="button" class="btn" value="返回" onclick="javascript:back()"/>
    </li>
	<form id = "form" action="<s:url action="listYouTuBe" />" method="post" name="form">
<!-- 		<ul class="seachform"> -->
<!-- 				<li> -->
<%-- 					<input id="className" name="className" type="text" class="scinput form-control" maxlength="32" value = "${className}" placeholder="输入关键字搜索"/> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<label>&nbsp;</label> -->
<!-- 					<input type="submit" class="scbtn btn" value="查询" onclick="initPageNo()"/> -->
<!-- 				</li> -->
<!-- 		</ul> -->
		<table class="tablelist">
			<thead>
				<tr>
					<th>分类名称</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="mapList" var="map">
							<tr>
								<td><s:property value="#map.keyName" /></td>
								<td>									
									<s:if test="#map.isRecommond == 1">
										<a href="javascript:isExist('${map.key}','${map.keyName}');"  class="tablelink">推荐</a>
									</s:if>
									<s:else>
										<a href="javascript:alertMsg();" class="tablelink" style="background: #c0c0c0;">已推荐</a>
									</s:else>
								</td>
							</tr>				
				</s:iterator>
			</tbody>
		</table>
		<%@ include file="../../../include/page.jsp" %>
	</form>
	</div>
</body>
</html>
