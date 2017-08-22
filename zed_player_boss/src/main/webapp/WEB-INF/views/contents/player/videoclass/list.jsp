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
	$(document).ready(function(e) {
		var copysort, copydescription, copyfixed, copyrecommended;
		$('table tbody').find('tr').each(function(){
			$(this).find('td:last a:first').click(function(e) {
				var $input = $('<input type="text" style="width:30px;text-align:center;padding: 6px 6px;" class="scinput form-control" maxlength="2"/>');
				var $inputOfDescription = $('<input type="text" style="width:100px;text-align:center;padding: 6px 6px;" class="scinput form-control" maxlength="512"/>');
				var $selectFixed = $('<select class="dayinput form-control" style="width:70px;text-align:center;padding: 0px 0px;margin-left: 6px;margin-right: -25px;"><option value="0" selected="selected">不固定</option><option value="1">固定</option></select>');
				var $selectRecommended = $('<select class="dayinput form-control" style="width:70px;text-align:center;padding: 0px 0px;margin-left: 6px;margin-right: -25px;"><option value="0" selected="selected">不推荐</option><option value="1">推荐</option></select>');
				if (e.target.name === '_edit') {
					var id;
					var description;
					var fixed;
					$(this).parent().prevAll('td').each(function(i, obj) {
						id = $(this).parent().find("input[name='class_id']").val();
						var classKey = $(this).parent().find("input[name='class_key']").val();
						if ($(this).attr("name")==='classSort') {
							var sort = $(this).text();
							this.copysort = $(this).text();
							$input.val(sort).attr("id","key_"+id).attr("classKey",classKey);
							$(this).html($input);
						}
						if ($(this).attr("name")==='description') {
							description = $(this).text();
							this.copydescription = $(this).text();
							$inputOfDescription.val(description).attr("id","description_"+id);
							$(this).html($inputOfDescription);
						}
						var classFixed = $(this).parent().find("input[name='class_fixed']").val();
						if ($(this).attr("name")==='fixed') {
							$selectFixed.attr("id","fixed_"+id);
						 	var count=$selectFixed.find("option").length;
						 	this.copyfixed = $(this).text();
							for(var i=0;i<count;i++){        
								if($selectFixed.get(0).options[i].value == classFixed){    
									$selectFixed.get(0).options[i].selected = true;    
						            break;    
						        }
						    }
							$(this).html($selectFixed);
						}
						var classRecommended = $(this).parent().find("input[name='class_recommended']").val();
						if ($(this).attr("name")==='recommended') {
							$selectRecommended.attr("id","recommended_"+id);
						 	var count=$selectRecommended.find("option").length;
						 	this.copyrecommended = $(this).text();
							for(var i=0;i<count;i++){        
								if($selectRecommended.get(0).options[i].value == classRecommended){    
									$selectRecommended.get(0).options[i].selected = true;    
						            break;    
						        }
						    }
							$(this).html($selectRecommended);
						}
					});
					$("#"+"description_"+id).focus();
					$(this).attr('name', '_save').text("保存");
				}else if(e.target.name === '_save') {
					var id;
					var sort;
					var description;
					var fixed;
					var recommended;
					$(this).parent().prevAll('td').each(function(i, obj) {
						id = $(this).parent().find("input[name='class_id']").val();
						if ($(this).attr("name")==='classSort') {
							sort = $("#key_"+id).val();
							$("#key_"+id).remove();
							$(this).html(sort);
						}
						if ($(this).attr("name")==='description') {
							description = $("#description_"+id).val();
							$("#description_"+id).remove();
							$(this).html(description);
						}
						if ($(this).attr("name")==='fixed') {
							fixed = $("#fixed_"+id).val();
							var fixedText = $("#fixed_"+id).find("option:selected").text();
							$("#fixed_"+id).remove();
							$(this).html(fixedText);
						}
						if ($(this).attr("name")==='recommended') {
							recommended = $("#recommended_"+id).val();
							var recommendedText = $("#recommended_"+id).find("option:selected").text();
							$(this).html(recommendedText);
						}
					});
					if(parseInt(sort) < 4) {
						layer.alert('YouTube来源无法推荐到前三位，请重新设置!');
						$(this).parent().prevAll('td').each(function(i, obj) {
							id = $(this).parent().find("input[name='class_id']").val();
							if ($(this).attr("name")==='classSort') {
								$(this).html(this.copysort);
							}
							if ($(this).attr("name")==='description') {
								$(this).html(this.copydescription);
							}
							if ($(this).attr("name")==='fixed') {
								$(this).html(this.copyfixed);
							}
							if ($(this).attr("name")==='recommended') {
								$(this).html(this.copyrecommended);
							}
						});
						$(this).attr('name', '_edit').text("编辑");
						return false;
					}
					isExist(id,sort,description,fixed,recommended);
					$(this).attr('name', '_edit').text("编辑");
				}
			});
			
			$(this).find('td:last a:last').click(function(e) {
				if(e.target.name === '_delete'){
					$(this).parent().prevAll('td').each(function(i, obj) {
						var id = $(this).parent().find("input[name='class_id']").val();
						deleteClass(id);
					});
				}
			});
		})
		
	});
	
	function isExist(id, sort, description, fixed, recommended){
		console.log("id = "+id+" , sort = "+sort+" , description = "+description+" , fixed = "+fixed+" , recommended = "+recommended);
	    var data = {"sort": sort, "classId":id, "description":description};
	    var url  = '<s:url action="isExist.action" />';
	    $.ajax({
	        type: "POST",
	        url: url,
	        data: data,
	        dataType : "json",
	        success:function(data){
	       	 if(data){
	       		 if(data.sortFlag&&!data.descriptionFlag){
	       			layer.open({
		          		  content: '该分类展示位已存在，是否修改 ？',
		          		  btn: ['确定', '取消'],
		          		  yes: function(index, layero){
		          			updateClass(id, sort, description, fixed, recommended);
		          			layer.close(index);
		          		  },
		          		  cancel: function(index, layero){ 
// 		          			location.reload();
		          		     layer.close(index);
		          		  } 
		          		});
	       		 }else if(!data.sortFlag&&data.descriptionFlag){
	       			layer.open({
		          		  content: '该分类名称已存在！',
		          		  btn: ['确定'],
		          		  yes: function(index, layero){
		          			location.reload();
		          		  } 
		          		});
	       		 }else{
	       			updateClass(id, sort, description, fixed, recommended);
	             }
	       	 }
	        },
	        error : function (XMLHttpRequest, textStatus, errorThrown) {
	        }
		 }); 
		}
		
		function updateClass(id, sort, description, fixed, recommended){
			var data = {"sort": sort, "classId":id,"description":description,"fixed":fixed,"recommended":recommended};
		    var url  = '<s:url action="update.action" />';
		    $.ajax({
		    	type: "POST",
		        url: url,
		        data: data,
		        dataType : "json",
		        success:function(data){
		       	 if(data){
		       		 if(data.flag){
		       			layer.open({
		          		  content: '插入成功，是否刷新当前页 ？',
		          		  btn: ['确定', '取消'],
		          		  yes: function(index, layero){
		          				layer.close(index);
		         			  	location.reload();
		          		  },
		          		  cancel: function(index, layero){ 
		          		    	layer.close(index);
	         			  } 
		         		});
		       		 }else{
		       			layer.alert('添加失败！');
		       			location.reload();
		             }
		       	 }
		        },
		        error : function (XMLHttpRequest, textStatus, errorThrown) {
		        }
			 }); 
			}
		
		function deleteClass(id){
			layer.open({
        		  content: '您确认删除该条信息吗 ？',
        		  btn: ['确定','取消'],
        		  yes: function(index, layero){
        			  var data = {"classId":id};
        			    var url  = '<s:url action="delete.action" />';
        			    $.ajax({
        			    	type: "POST",
        			        url: url,
        			        data: data,
        			        dataType : "json",
        			        success:function(data){
        			       	 if(data){
        			       		 if(data.flag){
        			       			layer.open({
        			          		  content: '删除成功！',
        			          		  btn: ['确定'],
        			          		  yes: function(index, layero){
        			         			  	location.reload();
        			          		  }
        			         		});
        			       		 }else{
        			       			layer.alert('删除失败！');
        			       			location.reload();
        			             }
        			       	 }
        			        },
        			        error : function (XMLHttpRequest, textStatus, errorThrown) {
        			        }
        				 });
        		  },
          		  cancel: function(index, layero){ 
        		    	layer.close(index);
   			  	  } 
       		});
		}
			
		function back(){
			window.location = '<s:url action="list" />';
		}
		
		function alertMsg(){
		 	layer.msg('无法操作！', {
		        time: 2000 //2s后自动关闭
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
					<label>关键字:</label>
					<input id="description" name="description" type="text" class="scinput form-control" maxlength="32" value = "${description}" placeholder="请输入关键字搜索"/>
				</li>
				<li>
					<label>&nbsp;</label>
					<input type="submit" class="scbtn btn" value="查询"/>
				</li>
		</ul>
		<s:include value="../../../include/buttonTop.jsp"/>
		<table class="tablelist">
			<thead>
				<tr>
<!-- 					<th><input name = "selectCheckbox" id = "selectCheckbox" type="checkbox" value=""/></th> -->
					<th>描述</th>
					<th>展示位</th>
					<th>来源</th>
					<th>状态</th>
					<th>ClassKey</th>
					<th>是否固定</th>
					<th>是否推荐</th>
					<th>创建人</th>
					<th>创建时间</th>
					<th>修改人</th>
					<th>修改时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="page.result" var="list">
						<tr>
								<input name="class_id" type="hidden" value="<s:property value="#list.class_id" />"/>
								<input name="class_key" type="hidden" value="<s:property value="#list.class_key" />"/>
								<input name="class_fixed" type="hidden" value="<s:property value="#list.fixed" />"/>
								<input name="class_recommended" type="hidden" value="<s:property value="recommended" />"/>
<%-- 								<td name="checkBox"><input name="checkbox_list" id="checkbox_list" type="checkbox" onclick="selectCheckBoxList()" value="<s:property value="#list.class_id" />"/></td> --%>
								<td name="description"><s:property value="#list.description" /></td>
								<td name="classSort"><s:property value="#list.sort" /></td>
								<td><s:if test="#list.origin == 1">player</s:if><s:else>站长APP</s:else></td>
								<td><s:if test="#list.status == 1">启用</s:if><s:else>禁用</s:else></td>
								<td><s:property value="#list.class_key" /></td>
								<td name="fixed"><s:if test="#list.fixed == 1">固定</s:if><s:else>不固定</s:else></td>
								<td name="recommended"><s:if test="#list.recommended == 1">推荐</s:if><s:else>不推荐</s:else></td>
								<td><s:property value="#list.creater" /></td>
								<td><fmt:formatDate value="${list.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td><s:property value="#list.updater" /></td>
								<td><fmt:formatDate value="${list.update_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td name="operator">
									<c:choose>
						    			<c:when test="${fn:startsWith(list.class_key, 'player-')}">
						    				<a href="javascript:alertMsg();" class="tablelink" style="background: #c0c0c0;">编辑</a>
											<a href="javascript:alertMsg();" class="tablelink" style="background: #c0c0c0;">删除</a>
						    			</c:when>
						    			<c:otherwise>
							    			<a href="#" class="tablelink" name="_edit">编辑</a>
											<a href="#" class="tablelink" name="_delete">删除</a>
						    			</c:otherwise>
						    		</c:choose>
								</td>
						</tr>		
				</s:iterator>
			</tbody>
		</table>
		<%@ include file="../../../include/buttonRight.jsp" %>
<%-- 		<%@ include file="../../../include/page.jsp" %> --%>
	</form>
	
</div>

</body>
</html>
