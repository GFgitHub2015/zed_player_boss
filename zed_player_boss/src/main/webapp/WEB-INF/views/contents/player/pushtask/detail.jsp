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
		.js-region-select{display:none;vertical-align: top;}
		.li-select-lang{display:none;vertical-align: top;}
		.li-select-lang .dl-list-wrap{width: 200px; position: relative;display:inline-block;border:1px solid #a7b5bc;}
		.li-select-lang .dl-list{padding: 30px 0 0;max-height: 200px;overflow: auto;}
		.li-select-lang .dl-list dt{line-height: 30px;border-bottom: 1px solid #a7b5bc;padding-left: 30px;position: absolute;top: 0;left: 0;z-index: 10; width: 100%;box-sizing:border-box; background: #fff;}
		.li-select-lang .dl-list dt .toggle-btn{position: absolute;right: 0px;height: 100%;width: 30px;padding:0; line-height: 30px;text-align: center;font-size: 20px;cursor: pointer;background: #eee;}
		.li-select-lang .dl-list dt .toggle-btn:hover{color:#212121;}
		.li-select-lang .dl-list dt .toggle-btn:after{content: '';display: block;width: 0;position: absolute;border-width: 6px 6px 0;border-style: solid;border-color: #333 transparent transparent;top: 12px;left: 9px;}
		.li-select-lang .dl-list dd{line-height: 30px;border-bottom: 1px solid #eee;padding-left: 30px;position: relative;}
		.li-select-lang .dl-list dd span{display: block;}
		.li-select-lang .dl-list dd:hover{background:#fff;}
		.li-select-lang .dl-list dd input[type="checkbox"],.dl-list dt input[type="checkbox"]{position: absolute;left: 5px;top: 10px;margin:0;}
	</style>
<script type="text/javascript">
function changeType(){
	console.log("picked == > "+$("[id='playerTaskParams.picked']").val());
	if($("[id='playerTaskParams.picked']").val() == "1")
    {  
        $("[id='playerTaskParams.pushTime']").show();
    }else{
		$("[id='playerTaskParams.pushTime']").hide();
		$("[id='playerTaskParams.pushTime']").removeAttr("value");
    }
}
$(document).ready(function(e) {
	changeType();
	$('#form').validationEngine('attach', { 
	    promptPosition: 'centerRight', 
	    scroll: false
	});
	$("[id='playerTaskParams.picked']").each(function(index,el){		 
		if($(el).prop('selected')){
			$("[id='playerTaskParams.picked']").trigger('change');
		}
		
	});
	/* 切换目标用户 */
	$("[id='playerTaskParams.target']").on('change',function(){
		var targetItem=$(this).find('option:selected'),
			targetValue=targetItem.val(); 
		switch (targetValue){
			case '1':
				$('.js-region-select').css({"display":'none'});
				$('.js-user-id').css({"display":'none'}).find("[id='playerTaskParams.userId']");
			break;
			case '2':
			 
				$('.js-user-id').css({"display":'none'}).find("[id='playerTaskParams.userId']");
				$('.js-region-select').css({"display":'inline-block'});
			break;	
			case '3':
			 
				$('.js-region-select').css({"display":'none'});
				$('.js-user-id').css({"display":'inline-block'}).find("[id='playerTaskParams.userId']");
			break;	
			default:
				$('.js-region-select').css({"display":'none'});
				$('.js-user-id').css({"display":'none'}).find("[id='playerTaskParams.userId']");
			 
		}
	});
	
	/* 切换后续动作*/
	$("input[name='playerTaskParams.operation']").on('change',function(){
		if($("input[name='playerTaskParams.operation']:eq(1)").attr("checked")){
			$('.js-video-type').css({'display':'block'});
		}else if($("input[name='playerTaskParams.operation']:eq(0)").attr("checked")){
			$('.js-video-type').css({'display':'none'});
			$("[id='playerTaskParams.fileId']").attr("value","");
			$("[id='playerTaskParams.url']").attr("value","");
			$('.js-video-url').css({"display":'none'}).find("[id='playerTaskParams.url']");
			$('.js-video-id').css({"display":'none'}).find("[id='playerTaskParams.fileId']");
		}else{
			$('.js-video-type').css({'display':'none'});
			$("[id='playerTaskParams.fileId']").attr("value","");
			$("[id='playerTaskParams.url']").attr("value","");
			$('.js-video-url').css({"display":'none'}).find("[id='playerTaskParams.url']");
			$('.js-video-id').css({"display":'none'}).find("[id='playerTaskParams.fileId']");
		}
	});
	
	/* 资源类型切换 */
	$(document).on('change',"[id='playerTaskParams.type']",function(){
		var typeItem=$(this).find('option:selected'),
		typeValue=typeItem.val(); 
		switch (typeValue){
			case '1002':		 
				$("[id='playerTaskParams.fileId']").attr("value","");
				$('.js-video-id').css({"display":'none'}).find("[id='playerTaskParams.fileId']");
				$('.js-video-url').css({"display":'block'}).find("[id='playerTaskParams.url']");
			break;
			case '1001':
				$("[id='playerTaskParams.url']").attr("value","");
				$('.js-video-url').css({"display":'none'}).find("[id='playerTaskParams.url']");
				$('.js-video-id').css({"display":'block'}).find("[id='playerTaskParams.fileId']");
			break;	
			default:
				$("[id='playerTaskParams.fileId']").attr("value","");
				$("[id='playerTaskParams.url']").attr("value","");
				$('.js-video-url').css({"display":'none'}).find("[id='playerTaskParams.url']");
				$('.js-video-id').css({"display":'none'}).find("[id='playerTaskParams.fileId']");
			 
		}
	});
	
	//自定义select+checkbox
	$('.dl-list dt .toggle-btn').on('click', function(event) {			 
		$(this).parent().next('.dd-wrap').slideToggle('400');
	});
	$('.check-all').on('change',function(event) {
		var flag = $(this).prop('checked');
		$(this).parents('.dl-list').find('.check-item').prop('checked', flag);
		var subCheckedBoxName=$(this).parents('.dl-list').find('.check-item').attr("name");
		
		var checkbox_value = [];
		$('input[name="'+subCheckedBoxName+'"]').each(function(){
			if($(this).attr("checked")){
				checkbox_value.push($(this).val());
			}
		});
		var allCheckedBoxName = subCheckedBoxName+"-all";
		console.log(allCheckedBoxName);
		var selectedValue = "playerTaskParams."+subCheckedBoxName;
		$("[id='"+selectedValue+"']").val(checkbox_value.join(","));
		console.log(selectedValue+" = "+$("[id='"+selectedValue+"']").val());
	});
	
	$('.check-item').on('change',function(event) {
		var flag = true;
		var subCheckedBoxName=$(this).attr("name");
		$('input[name="'+subCheckedBoxName+'"]:checkbox').each(function(){
			if (!this.checked) {
				flag = false;
			}
		});
		
		var checkbox_value = [];
		$('input[name="'+subCheckedBoxName+'"]').each(function(){
			if($(this).attr("checked")){
				checkbox_value.push($(this).val());
			}
		});
		var allCheckedBoxName = subCheckedBoxName+"-all";
		console.log(allCheckedBoxName);
		$("[id='"+allCheckedBoxName+"']").attr("checked",flag);
		var selectedValue = "playerTaskParams."+subCheckedBoxName;
		$("[id='"+selectedValue+"']").val(checkbox_value.join(","));
		console.log(selectedValue+" = "+$("[id='"+selectedValue+"']").val());
	});
	
	/* 切换目标条件 */
	$("[id='playerTaskParams.topicType']").on('change',function(){
		var targetItem=$(this).find('option:selected'),
			targetValue=targetItem.val(); 
		
		$('.li-select-lang').css({"display":'none'});
		switch (targetValue){
		case '1':
			$('.li-select-version1').css({"display":'inline-block'});
			$("[id='channel-all']").prop('checked', false);
			$("[id='channel-all']").parents('.dl-list').find('.check-item').prop('checked', false);
			$("[id='language-all']").prop('checked', false);
			$("[id='language-all']").parents('.dl-list').find('.check-item').prop('checked', false);
			$("[id='playerTaskParams.channel']").attr("value","");
			$("[id='playerTaskParams.language']").attr("value","");
		break;	
		case '2':
			$('.li-select-channel1').css({"display":'inline-block'});
			$("[id='version-all']").prop('checked', false);
			$("[id='version-all']").parents('.dl-list').find('.check-item').prop('checked', false);
			$("[id='language-all']").prop('checked', false);
			$("[id='language-all']").parents('.dl-list').find('.check-item').prop('checked', false);
			$("[id='playerTaskParams.version']").attr("value","");
			$("[id='playerTaskParams.language']").attr("value","");
		break;	
		case '3':
			$('.li-select-lang1').css({"display":'inline-block'});
			$("[id='channel-all']").prop('checked', false);
			$("[id='channel-all']").parents('.dl-list').find('.check-item').prop('checked', false);
			$("[id='version-all']").prop('checked', false);
			$("[id='version-all']").parents('.dl-list').find('.check-item').prop('checked', false);
			$("[id='playerTaskParams.channel']").attr("value","");
			$("[id='playerTaskParams.version']").attr("value","");
		break;	
			default:				 
	 
		}
	});
	
	
	/* 初始化选项界面 */
	
	function initShow(obj){
		if($(obj).attr('isset')!=''){
			$(obj).trigger('change');
		}
	}	
	$('.js-target-user select,.js-video-type select').each(function(index,el){
		initShow(el);
	});
	
	$("input[name='playerTaskParams.operation']").each(function(index,el){		 
		if($(el).prop('checked')){
			$("input[name='playerTaskParams.operation']").trigger('change');
		}
		
	});
		
});
function clearForm() {
	window.location = '<s:url action="pushtask/updatePage" />?taskId=${playerTaskParams.taskId}';
}

</script>
</head>
<body>
<%@ include file="../../../include/navigation.jsp" %>
<form  method="post" name="form" id="form" action="<s:url action="list"/>">
	<input type="hidden" name="playerTaskParams.taskId" id="playerTaskParams.taskId" disabled="disabled" value="${playerTaskParams.taskId}"/>
    <div class="formbody">
    <div class="formtitle"><span>消息推送详情</span></div>
	    <ul class="forminfo">
	    <li>
	    	<label style="width:120px;">消息标题</label>
	    	<input readonly="readonly" name="playerTaskParams.title" id="playerTaskParams.title" type="text" class="dfinput" value="${playerTaskParams.title}"/>
	    </li>
		<li>
	    	<label style="width:120px;">消息内容</label>
	    	<input readonly="readonly" name="playerTaskParams.content" id="playerTaskParams.content" type="text" class="dfinput" value="${playerTaskParams.content}"/>
	    </li>
	     <li>
	    	<label style="width:120px;">消息备注</label>
	    	<input readonly="readonly" name="playerTaskParams.description" id="pushTask.description" type="text" class="dfinput" value="${playerTaskParams.description}"/>
	    </li>
	    <li>
	    	<label style="width:120px;">图标</label>
	    	<cite>
	    		<input name="playerTaskParams.icon" type="radio" value="0" disabled="disabled" <c:if test = "${playerTaskParams.icon == '0'}">checked="checked"</c:if>/>&nbsp;&nbsp;默认
	    		<%-- &nbsp;&nbsp;&nbsp;&nbsp;
	    		<input name="playerTaskParams.icon" type="radio" value="1" disabled="disabled" <c:if test = "${playerTaskParams.icon == '1'}">checked="checked"</c:if>/>&nbsp;&nbsp;自定义 --%>
	    	</cite>
    	</li>
    	<li>
	    	<label style="width:120px;">目标国家</label>
			<select name="playerTaskParams.areaCode" id="playerTaskParams.areaCode" class="dayinput form-control" style="width:500px;" disabled="disabled">
				<c:if test="${not empty playerCountryList}">
					<s:iterator value="playerCountryList" var="playerCountry">
						<option value="<s:property value="#playerCountry.countryCode"/>" <s:if test="%{#playerCountry.countryCode == playerTaskParams.areaCode}">selected="selected"</s:if>>
							<s:property value="#playerCountry.countryNameZh"/>（<s:property value="#playerCountry.countryCode"/>）
						</option>
					</s:iterator>
				</c:if>
			</select>
	    </li>
	   <li class="js-target-user">
	    	<label style="width:120px;">目标用户</label> 
	    	<select name="playerTaskParams.target" id="playerTaskParams.target" class="dayinput form-control" style="width:150px;display:inline-block;margin-right:30px;" isset='${playerTaskParams.target}' disabled="disabled">
	    	   
				<option value="1" <c:if test = "${playerTaskParams.target == '1'}">selected="selected"</c:if>>所有人</option>
				<option value="2" <c:if test = "${playerTaskParams.target == '2'}">selected="selected"</c:if>>部分人</option>
				<option value="3" <c:if test = "${playerTaskParams.target == '3'}">selected="selected"</c:if>>测试</option>
			</select>
			<div class="js-region-select">
				<select name="playerTaskParams.topicType" id="playerTaskParams.topicType" class="dayinput form-control" style="width:150px;vertical-align: top;display:inline-block;margin-right:20px;" isset='${playerTaskParams.topicType}' disabled="disabled">
					<option value="" <c:if test = "${playerTaskParams.topicType == ''}">selected="selected"</c:if>>请选择</option>
					<option value="1" <c:if test = "${playerTaskParams.topicType == '1'}">selected="selected"</c:if>>版本</option>
					<option value="2" <c:if test = "${playerTaskParams.topicType == '2'}">selected="selected"</c:if>>渠道</option>
					<option value="3" <c:if test = "${playerTaskParams.topicType == '3'}">selected="selected"</c:if>>语言</option>
				</select>
				<div class="li-select-lang li-select-lang1" >
		    		  		 
					<div class="dl-list-wrap">
						<dl class="dl-list">
					 		<dt><input type="checkbox" class="check-all" name="language-all" id="language-all" disabled="disabled"><span>语言</span> <em class="toggle-btn"></em></dt>
					 		<div class="dd-wrap">
					 			<s:iterator value="languageList" var="language">
					 			<c:set var="flag" value="flase" scope="page"/>
						 			<c:forTokens items="${playerTaskParams.language}" varStatus="status" delims="," var="l">
						 				<c:if test="${language == l}"><c:set var="flag" value="true" scope="page"/></c:if>
						 			</c:forTokens>
							 			<dd><input name="language" type="checkbox" class="check-item" value="${language}" disabled="disabled" <c:if test="${flag}">checked</c:if>><span>${language}</span></dd>
					 			</s:iterator>
					 		</div>	 		
					 	</dl>
					</div>
		    	</div>
		    	<div class="li-select-lang li-select-channel1">
		    		 	 
					<div class="dl-list-wrap">
						<dl class="dl-list" >
					 		<dt><input type="checkbox" class="check-all" name="channel-all" id="channel-all" disabled="disabled"><span>渠道</span> <em class="toggle-btn"></em></dt>
					 		<div class="dd-wrap">
					 			<s:iterator value="channelList" var="channel">
					 				<c:set var="flag" value="flase" scope="page"/>
						 			<c:forTokens items="${playerTaskParams.channel}" varStatus="status" delims="," var="c">
						 			<c:if test="${channel == c}"><c:set var="flag" value="true" scope="page"/></c:if>
						 			</c:forTokens>
						 			<dd><input name="channel" type="checkbox" class="check-item" value="${channel}" disabled="disabled" <c:if test="${flag}">checked="checked"</c:if>><span>${channel}</span></dd>
					 			</s:iterator>
					 		</div>
					 		
					 	</dl>
					</div>
		    	</div>
		    	<div class="li-select-lang li-select-version1">
		    		 		 
					<div class="dl-list-wrap">
						<dl class="dl-list">
					 		<dt><input type="checkbox" class="check-all" name="version-all" id="version-all" disabled="disabled"><span>版本</span> <em class="toggle-btn"></em></dt>
					 		<div class="dd-wrap">
					 			<s:iterator value="appVersionList" var="appVersion">
					 				<c:set var="flag" value="flase" scope="page"/>
					 				<c:forTokens items="${playerTaskParams.version}" varStatus="status" delims="," var="v">
					 					<c:if test="${appVersion == v}"><c:set var="flag" value="true" scope="page"/></c:if>
				 					</c:forTokens>
					 				<dd><input name="version" type="checkbox" class="check-item" value="${appVersion}" disabled="disabled" <c:if test="${flag}">checked="checked"</c:if>><span>${appVersion}</span></dd>
					 			</s:iterator>
					 		</div>
					 	</dl>
					</div>
		    	</div>
    	
			</div>	
	    		
    	</li>
    	<li class="js-user-id" style="display:none;">
    		<label style="width:120px;">用户ID</label>
			<input name="playerTaskParams.userId" id="playerTaskParams.userId" type="text" class="dfinput" maxlength="32" value="${playerTaskParams.userId}" disabled="disabled"/>
    	</li>
	    <li>
	    	<label style="width:120px;">后续动作</label>
	    	<cite>
	    		<input name="playerTaskParams.operation" type="radio" value="0" disabled="disabled" <c:if test = "${playerTaskParams.operation == '0'}">checked="checked"</c:if>/>&nbsp;&nbsp;打开应用
	    		&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input name="playerTaskParams.operation" type="radio" value="1" disabled="disabled" <c:if test = "${playerTaskParams.operation == '1'}">checked="checked"</c:if>/>&nbsp;&nbsp;打开页面
	    	</cite>
	    	
    	</li>
	    <li class="js-video-type" style="display:none;">
	    	<label style="width:120px;">类型</label>
	    	<select name="playerTaskParams.type" id="playerTaskParams.type" class="dayinput form-control" style="width:150px;" isset="${playerTaskParams.type}" disabled="disabled">
				<option value="" <c:if test = "${playerTaskParams.type == ''}">selected="selected"</c:if>>请选择</option>
				<option value="1002" <c:if test = "${playerTaskParams.type == '1002'}">selected="selected"</c:if>>活动</option>
				<option value="1001" <c:if test = "${playerTaskParams.type == '1001'}">selected="selected"</c:if>>影片</option>
			</select>
	    	
	    </li>
	    <li class="js-video-url" style="display:none;">
	    	<label style="width:120px;">url</label>
	    	<input name="playerTaskParams.url" id="playerTaskParams.url" type="text" class="dfinput" maxlength="250" value="${playerTaskParams.url}" disabled="disabled"/>
	    </li>
	    <li class="js-video-id" style="display:none;">  	
	    	<label style="width:120px;">影片ID</label>
	    	<input name="playerTaskParams.fileId" id="playerTaskParams.fileId" type="text" class="dfinput" maxlength="250" value="${playerTaskParams.fileId}" disabled="disabled"/>
	    </li>
	    <li>
	    	<label style="width:120px;">提醒方式</label>
	    	<cite>
	    		<input name="playerTaskParams.reminderType" type="radio" value="0" disabled="disabled" <c:if test = "${playerTaskParams.reminderType == '0'}">checked="checked"</c:if>/>&nbsp;&nbsp;全部
	    		&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input name="playerTaskParams.reminderType" type="radio" value="1" disabled="disabled" <c:if test = "${playerTaskParams.reminderType == '1'}">checked="checked"</c:if>/>&nbsp;&nbsp;震动
	    		<input name="playerTaskParams.reminderType" type="radio" value="2" disabled="disabled" <c:if test = "${playerTaskParams.reminderType == '2'}">checked="checked"</c:if>/>&nbsp;&nbsp;响铃
	    	</cite>
    	</li>
 		<li>
	    	<label style="width:120px;">推送时间</label>
	    	<select name="playerTaskParams.picked" id="playerTaskParams.picked" class="dayinput form-control" disabled="disabled" style="width:150px;display:inline-block;margin-right:30px;" onchange="changeType()">
				<option value="" <c:if test = "${playerTaskParams.picked == ''}">selected="selected"</c:if>>请选择</option>
				<option value="1" <c:if test = "${playerTaskParams.picked == '1'}">selected="selected"</c:if>>定时推送</option>
				<option value="0" <c:if test = "${playerTaskParams.picked == '0'}">selected="selected"</c:if>>立即推送</option>
			</select>
	    	<input  name="playerTaskParams.pushTime" id="playerTaskParams.pushTime" disabled="disabled" class="Wdate" style="width: 200px;"  readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${playerTaskParams.pushTime}"/>
	    </li>
	    <li><label>&nbsp;</label>
		    <input name="" type="submit" class="btn" value="返回" onclick="javascript:history.go(-1);"/>
		    <input name="" type="button" class="btn" value="编辑" onclick="clearForm();"/>
	    </li>
	    </ul>
    </div>
</form>
</body>
</html>
