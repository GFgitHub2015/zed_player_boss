<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../include/resource.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<!-- common -->
	<link href="<s:property value="themePath" />css/style.css" rel="stylesheet">
    <link href="<s:property value="themePath" />css/style-responsive.css" rel="stylesheet"> 
	<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />style.css"/>
	<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />validationEngine.jquery.css"/>
	<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />loading.css"/>
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />window.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />function.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.validationEngine-lang.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.validationEngine.min.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />loading.js"></script>
	<style>
		.require{color:#FF0000;padding-right:0 2px;}
		select option {height: 32px;line-height: 32px;text-indent: 10px;}
	</style>
<script type="text/javascript">
$(document).ready(function(e) {
	$('#form').validationEngine('attach', { 
	    promptPosition: 'centerRight', 
	    scroll: false
	  });	  
	$('#form').bind('jqv.form.result', function(event, errorFound){process(errorFound)});
	var promoType='${playerPromotionInfo.promoType}';
	if(promoType=='1'){
		$("#recommendFlag").show();
	}
});

function validate(){
	return true;
}

function clearForm() {
	window.location = '<s:url action="updatePage" />?infoId=${playerPromotionInfo.uid}';
}

function process(errorFound){
	if(!checkDate()){
		return;
	}
    if(!errorFound) {
    	openConfirmWindow('提示信息','是否确认修改 ？','确定', 'formSubmit()','closeConfirmWindow()','取消');
    }
}

function formSubmit(){
	if(!checkDate()){
		return;
	}
	LoadingPic.FullScreenShow();
	document.form.submit();
}
var downTime = '${playerPromotionInfo.downTime}';
var upTime = '${playerPromotionInfo.upTime}';
//这里以后可以添加视频活动
function changePromoType(flag){
	if(flag==0){
		$("#recommendFlag").hide();
		$("#upTime").val("");
		$("#downTime").val("");
	}else if(flag==1){
		$("#recommendFlag").show();
		$("#upTime").val(upTime);
		$("#downTime").val(downTime);
	}
}

function checkDate(){
    var dateStart = $('#startTime').val();
    var dateEnd= $('#endTime').val();
    if(dateStart){
        dateStart = dateStart.replace(/-/g,'');
    }else{
        layer.alert('请选择开始时间');
        return false;
    }
    if(dateEnd){
        dateEnd = dateEnd.replace(/-/g,'');
    }else{
    	layer.alert('请选择结束时间');
        return false;
    }
    if(dateStart > dateEnd){
    	layer.alert('开始时间不能大于结束时间')
        return false;
    }
    //如果是热门推荐再判断上下架时间
    var promoType = $("input[name='playerPromotionInfo.promoType']:checked").val();
    if(promoType=='1'){
    	var upTime = $('#upTime').val();
        var downTime= $('#downTime').val();
        if(upTime){
        	upTime = upTime.replace(/-/g,'');
        }else{
            layer.alert('请选择上架开始时间');
            return false;
        }
        if(downTime){
        	downTime = downTime.replace(/-/g,'');
        }else{
        	layer.alert('请选择下架结束时间');
            return false;
        }
        if(upTime > downTime){
        	layer.alert('上架开始时间不能大于下架结束时间')
            return false;
        }
        //再判断上下架时间要大于活动开始结束时间
        if(upTime>dateStart){
        	layer.alert('上架时间不能大于活动开始时间')
            return false;
        }
        if(downTime<dateEnd){
        	layer.alert('下架时间不能小于活动结束时间')
            return false;
        }
    }
    return true;
}
</script>
</head>
<body>
<%@ include file="../../../include/navigation.jsp" %>
<form method="post" name="form" id="form" enctype="multipart/form-data" action="<s:url action="update"/>" onsubmit="return false;">
<s:token name="submittedToken"/>
	<input type="hidden" name="page.pageNo" id="backPageNo" value="${page.pageNo}"/>
	<input type="hidden" name="playerPromotionInfo.uid" id="playerPromotionInfo.uid" value="${playerPromotionInfo.uid}"/>
	<input type="hidden" name="playerPromotionInfo.countryCode" id="playerPromotionInfo.countryCode" value="0"/>
    <div class="formbody">
    <div class="formtitle"><span>修改活动推送信息</span></div>
	    <ul class="forminfo">
		<li>
			<label style="width: 120px;"> </label>
			<img src="${playerPromotionInfo.imageUrl}" alt="活动配图" />
		</li>
		<li>
			<label style="width: 120px;">配图<font class = "require">*</font></label>
			<input type="file" name="imageUpload" accept="image/*"/>
		</li>
	    <li>
	    	<label style="width:120px;">活动标题<font class = "require">*</font></label>
	    	<input name="playerPromotionInfo.title" id="playerPromotionInfo.title" type="text" class="dfinput validate[required,minSize[1]]"  value="${playerPromotionInfo.title}"/><i class = "tips">请输入活动标题</i>
	    </li>
	    <li>
	    	<label style="width:120px;">活动内容<font class = "require">*</font></label>
	    	<textarea rows="4" cols="70" name="playerPromotionInfo.content" id="playerPromotionInfo.content" class="validate[required,minSize[1],maxSize[1024]]" maxlength="1024">${playerPromotionInfo.content }</textarea><i class = "tips">请输入活动内容</i>
	    </li>
		<li>
	    	<label style="width:120px;">活动链接<font class = "require">*</font></label>
	    	<input name="playerPromotionInfo.link" id="playerPromotionInfo.link" type="text" class="dfinput validate[required,minSize[1],maxSize[1024]]" maxlength="1024" value="${playerPromotionInfo.link}"/><i class = "tips">请输入活动链接</i>
	    </li>
	    <li><label style="width:120px;">开始时间<font class = "require">*</font></label>
	    	<input name="playerPromotionInfo.startTime" id="startTime" style="width:150px;" class="Wdate" value='<fmt:formatDate value="${playerPromotionInfo.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>' readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
	    </li>
	    <li><label style="width:120px;">结束时间<font class = "require">*</font></label>
	    	<input name="playerPromotionInfo.endTime" id="endTime" style="width:150px;" class="Wdate" value='<fmt:formatDate value="${playerPromotionInfo.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>'  readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
	    </li>
	    <li>
	    	<label style="width:120px;">是否启用</label>
	    	<cite>
	    		<input name="playerPromotionInfo.status" type="radio" value="0" <c:if test="${playerPromotionInfo.status eq '0'}">checked="checked"</c:if>/>&nbsp;&nbsp;禁用
	    		&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input name="playerPromotionInfo.status" type="radio" value="1" <c:if test="${playerPromotionInfo.status eq '1'}">checked="checked"</c:if>/>&nbsp;&nbsp;启用		
	    	</cite>
	    </li>
	    <li>
	    	<label  style="width:120px;">是否热门推荐<font class = "require">*</font></label>
	    	<cite>
	    		<input name="playerPromotionInfo.promoType" type="radio" value="0" checked="checked" onclick="changePromoType(0)"<c:if test="${playerPromotionInfo.promoType eq '0'}">checked="checked"</c:if>/>&nbsp;&nbsp;否
	    		&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input name="playerPromotionInfo.promoType" type="radio" value="1" onclick="changePromoType(1)" <c:if test="${playerPromotionInfo.promoType eq '1'}">checked="checked"</c:if>/>&nbsp;&nbsp;是
	    	</cite>
    	</li>
    	<div id="recommendFlag" style="display: none;">
		    <li><label style="width:120px;">上架开始时间<font class = "require">*</font></label>
		    	<input name="playerPromotionInfo.upTime" id="upTime" style="width:150px;" class="Wdate validate[required]" value='<fmt:formatDate value="${playerPromotionInfo.upTime}" pattern="yyyy-MM-dd HH:mm:ss"/>'   readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
		    </li>
		    <li><label style="width:120px;">上架结束时间<font class = "require">*</font></label>
		    	<input name="playerPromotionInfo.downTime"  id="downTime"  style="width:150px;" class="Wdate validate[required]" value='<fmt:formatDate value="${playerPromotionInfo.downTime}" pattern="yyyy-MM-dd HH:mm:ss"/>'    readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
		    </li>
		    <li>
		    	<label  style="width:120px;">是否置顶<font class = "require">*</font></label>
		    	<cite>
		    		<input name="playerPromotionInfo.topFlag" type="radio" value="0" checked="checked" <c:if test="${playerPromotionInfo.topFlag eq '0'}">checked="checked"</c:if> />&nbsp;&nbsp;否
		    		&nbsp;&nbsp;&nbsp;&nbsp;
		    		<input name="playerPromotionInfo.topFlag" type="radio" value="1" <c:if test="${playerPromotionInfo.topFlag eq '1'}">checked="checked"</c:if> />&nbsp;&nbsp;是
		    	</cite>
	    	</li>
    	</div>
	    <li><label>&nbsp;</label>
		    <input name="" type="submit" class="btn" value="修改"/>
		    <input name="" type="button" class="btn" value="重置" onclick="clearForm();"/>
	    </li>
	    </ul>
    </div>
</form>
</body>
</html>
