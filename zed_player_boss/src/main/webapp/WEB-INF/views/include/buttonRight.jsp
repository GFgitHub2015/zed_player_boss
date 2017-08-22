<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="resource.jsp" %>

<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />style.css"/>
<script type="text/javascript" src="<s:property value="jsPath" />jquery.js"></script>
<script type="text/javascript" src="<s:property value="jsPath" />window.js"></script>
<script type="text/javascript" src="<s:property value="jsPath" />function.js"></script>
<script type="text/javascript" src="<s:property value="jsPath" />loading.js"></script>
<script type="text/javascript">
function getBackPageUrl(url){
	//修改状态后,返回到之前的页码
	var backPageNo = '${page.pageNo}';
	if(backPageNo){
		if(url.indexOf("?")>1){
			url =url+"&page.pageNo="+backPageNo;
		}else{
			url =url+"?page.pageNo="+backPageNo;
		}
	}
	return url;
}
/************** right button function **************/
//设置
function setStatus(url,id){
	LoadingPic.FullScreenShow();
//	window.location=url+id;
	window.location=getBackPageUrl(url+id);
}

//查看详情
function viewDetail(url,id){
	window.location = url+id;
}

//删除
function deleteOne(url,id){
	url = url+id;
	openConfirmWindow('提示信息','是否确认删除？','确认', 'deleteProcessRight(\''+url+'\')','closeConfirmWindow()','取消')
}

//修改
function updateOne(url,id){
	LoadingPic.FullScreenShow();
	window.location = getBackPageUrl(url+id);
}

//修改密码（管理员列表）
function reSetPasswdOne(url,id){
	window.location = url+id;
}

//分配角色（管理员列表）
function setRoleOne(url,id){
	window.location=url+id;
}

//设置权限（角色列表）
function setPermissionOne(url,id){
	window.location=url+id;
}

function recommend(url,id){
	window.location=url+id;
}

function list(url,id){
	window.location=url+id;
}
function play(url,id){
	window.open(url+id);
}
function share(url,id){
	window.open(url+id);
}

function playUrl(url,id){
	 $.ajax({
		type: 'POST',
		url: url+id,
	    dataType: "text",
	    success:function(data) {
	    	window.open(data);
	    },
	    error:function(){
	    	showAlertWindow("提示信息","播放失败！","确定");
	    }
	}); 
}

//设置广告
function setAdvertisement(url,id){
	LoadingPic.FullScreenShow();
	window.location = getBackPageUrl(url+id);
}


/************** common function **************/
//原来的方法deleteProcess和buttontop.jsp有冲突,改为deleteProcessRight
function deleteProcessRight(url){
	window.location =getBackPageUrl(url);
}


</script>