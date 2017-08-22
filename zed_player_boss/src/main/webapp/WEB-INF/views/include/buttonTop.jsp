<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="resource.jsp" %>


<s:set name="buttonMenuList" value="@com.zed.util.CommUtil@getButtonMenuListByPartentId(#session.buttonMenuPartentId)"/>

<div class="form-group button-top clearfix">

	<ul class="toolbar"> 
		<c:forEach items="${buttonMenuList}" var="buttonTop">
			<c:choose>
			
				<c:when test="${buttonTop.resourceLevel == '4'}">
					<li class="click">						    
						    <a href="javascript:${buttonTop.functionName}('${buttonTop.resourceUrl}')" class="btn btn-info"> ${buttonTop.resourceName}</a>
					</li>
				</c:when>
				
				<c:otherwise>
				</c:otherwise>
						
			</c:choose>
		</c:forEach>
	 </ul>
</div>


<script type="text/javascript">

/************** top button function **************/

//新增
function addOne(url){
	window.location = url;
}

//删除
function deleteMore(url){
	if($("#selectCheckbox").val() == ""){
		openAlertmWindow('提示信息','请选择记录。','确认');
	}else{
		url = url+$("#selectCheckbox").val();
		openConfirmWindow('提示信息','是否确认删除？','确认', 'deleteProcess(\''+url+'\')','closeConfirmWindow()','取消')
	}	
}

//更新数据
function updateAllData(url){
	window.location = url;
}

//同步数据
function updateAllCache(url){
	window.location = url;
}

function updateAllStatus(url){
	if($("#selectCheckbox").val() == ""){
		openAlertmWindow('提示信息','请选择记录。','确认');
	}else{
		url = url+$("#selectCheckbox").val();
		openConfirmWindow('提示信息','是否确认修改？','确认', 'updateProcess(\''+url+'\')','closeConfirmWindow()','取消')
	}
}

function ajaxData(url){
	 $.ajax({
		type: 'GET',
		url: url,
		dataType : "json",
	    success:function(data) {
	    	if(data.flag){
	    		layer.open({
	          		  content: '数据初始化成功，是否刷新页面 ？',
	          		  btn: ['确定', '取消'],
	          		  yes: function(index, layero){
	          			location.reload();
	          		  },
	          		  cancel: function(index, layero){ 
	          		      layer.close(index);
	          			} 
	          		});
       			
       		 }else{
       			layer.alert('添加失败！');
             }
	    },
	    error:function(){
	    	layer.alert('添加失败！');
	    }
	}); 
}

/************** common function **************/
function deleteProcess(url){
	window.location = url;
}

function updateProcess(url){
	window.location = url;
}


</script>
