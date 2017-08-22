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
var checkSubmitFlg = false;
function changeType(){
// 	console.log("picked == > "+$("[id='playerTaskParams.picked']").val());
	if($("[id='playerTaskParams.picked']").val() == "1")
    {  
        $("[id='playerTaskParams.pushTime']").show();
        $("[id='playerTaskParams.pushTime']").addClass('validate[required]');
    }else{
		$("[id='playerTaskParams.pushTime']").hide();
		$("[id='playerTaskParams.pushTime']").removeAttr("value");
		$("[id='playerTaskParams.pushTime']").removeClass("validate[required]");
    }
}
$(document).ready(function(e) {
	changeType();
	$('#form').validationEngine('attach', { 
	    promptPosition: 'centerRight', 
	    scroll: false
	  });	  
	$('#form').bind('jqv.form.result', function(event, errorFound){process(errorFound)});
	
	
	/* 切换目标用户 */
	$("[id='playerTaskParams.target']").on('change',function(){
		var targetItem=$(this).find('option:selected'),
			targetValue=targetItem.val(); 
		switch (targetValue){
			case '1':

				$('.js-region-select').css({"display":'none'}).find("[id='playerTaskParams.topicType']").removeClass('validate[required]');
				$('.js-user-id').css({"display":'none'}).find("[id='playerTaskParams.userId']").removeClass('validate[required,minSize[1],maxSize[32],ajax[isUserExist]]');
			break;	
			case '2':
			 
				$('.js-user-id').css({"display":'none'}).find("[id='playerTaskParams.userId']").removeClass('validate[required,minSize[1],maxSize[32],ajax[isUserExist]]');
				$('.js-region-select').css({"display":'inline-block'}).find("[id='playerTaskParams.topicType']").addClass('validate[required]');
			break;	
			case '3':
			 
				$('.js-region-select').css({"display":'none'}).find("[id='playerTaskParams.topicType']").removeClass('validate[required]');
				$('.js-user-id').css({"display":'block'}).find("[id='playerTaskParams.userId']").addClass('validate[required,minSize[1],maxSize[32],ajax[isUserExist]]');
			break;	
			default:
				$('.js-region-select').css({"display":'none'}).find("[id='playerTaskParams.topicType']").removeClass('validate[required]');
				$('.js-user-id').css({"display":'none'}).find("[id='playerTaskParams.userId']").removeClass('validate[required,minSize[1],maxSize[32],ajax[isUserExist]]');		 
		}
	});
	
	/* 切换后续动作*/
	$("input[name='playerTaskParams.operation']").on('change',function(){
		if($("input[name='playerTaskParams.operation']:eq(1)").attr("checked")){
			$('.js-video-type').css({'display':'block'});
			$("[id='playerTaskParams.type']").addClass('validate[required]');
		}else if($("input[name='playerTaskParams.operation']:eq(0)").attr("checked")){
			$('.js-video-type').css({'display':'none'});
			$("[id='playerTaskParams.type']").removeAttr("value");
			$("[id='playerTaskParams.type']").removeClass('validate[required]');
			$("[id='playerTaskParams.fileId']").attr("value","");
			$("[id='playerTaskParams.url']").attr("value","");
			$('.js-video-url').css({"display":'none'}).find("[id='playerTaskParams.url']").removeClass('validate[required,minSize[1],maxSize[250],custom[url]]');
			$('.js-video-id').css({"display":'none'}).find("[id='playerTaskParams.fileId']").removeClass('validate[required,minSize[1],maxSize[32],ajax[isFileIdExist]]');
		}else{
			$('.js-video-type').css({'display':'none'});
			$("[id='playerTaskParams.type']").removeAttr("value");
			$("[id='playerTaskParams.type']").removeClass('validate[required]');
			$("[id='playerTaskParams.fileId']").attr("value","");
			$("[id='playerTaskParams.url']").attr("value","");
			$('.js-video-url').css({"display":'none'}).find("[id='playerTaskParams.url']").removeClass('validate[required,minSize[1],maxSize[250],custom[url]]');
			$('.js-video-id').css({"display":'none'}).find("[id='playerTaskParams.fileId']").removeClass('validate[required,minSize[1],maxSize[32],ajax[isFileIdExist]]');
		}
	});
	
	/* 资源类型切换 */
	$(document).on('change',"[id='playerTaskParams.type']",function(){
		var typeItem=$(this).find('option:selected'),
		typeValue=typeItem.val(); 
		switch (typeValue){
			case '1002':		 
				$("[id='playerTaskParams.fileId']").attr("value","");
				$('.js-video-id').css({"display":'none'}).find("[id='playerTaskParams.fileId']").removeClass('validate[required,minSize[1],maxSize[32],ajax[isFileIdExist]]');
				$('.js-video-url').css({"display":'block'}).find("[id='playerTaskParams.url']").addClass('validate[required,minSize[1],maxSize[250],custom[url]]');
			break;	
			case '1001':
				$("[id='playerTaskParams.url']").attr("value","");
				$('.js-video-url').css({"display":'none'}).find("[id='playerTaskParams.url']").removeClass('validate[required,minSize[1],maxSize[250],custom[url]]');
				$('.js-video-id').css({"display":'block'}).find("[id='playerTaskParams.fileId']").addClass('validate[required,minSize[1],maxSize[32],ajax[isFileIdExist]]');
			break;	
			default:
				$("[id='playerTaskParams.fileId']").attr("value","");
				$("[id='playerTaskParams.url']").attr("value","");
				$('.js-video-url').css({"display":'none'}).find("[id='playerTaskParams.url']").removeClass('validate[required,minSize[1],maxSize[250],custom[url]]');
				$('.js-video-id').css({"display":'none'}).find("[id='playerTaskParams.fileId']").removeClass('validate[required,minSize[1],maxSize[32],ajax[isFileIdExist]]');
			 
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
// 		console.log(allCheckedBoxName);
		var selectedValue = "playerTaskParams."+subCheckedBoxName;
		$("[id='"+selectedValue+"']").val(checkbox_value.join(","));
// 		console.log(selectedValue+" = "+$("[id='"+selectedValue+"']").val());
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
// 		console.log(allCheckedBoxName);
		$("[id='"+allCheckedBoxName+"']").attr("checked",flag);
		var selectedValue = "playerTaskParams."+subCheckedBoxName;
		$("[id='"+selectedValue+"']").val(checkbox_value.join(","));
// 		console.log(selectedValue+" = "+$("[id='"+selectedValue+"']").val());
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
	
});

function process(errorFound){
	if(!errorFound){
		openConfirmWindow('提示信息','是否确认提交 ？','确定', 'formSubmit()','closeConfirmWindow()','取消');
	}
}

function formSubmit(){
	if(checkSubmitFlg==true){
		openAlertmWindow('提示信息','请求已经处理提交!','确定');
	}else{
		checkSubmitFlg = true;
		LoadingPic.FullScreenShow();
		document.form.submit();
	}
}

function getMinDate(){
	var url = '<s:url action="getMinDate.action" />';
	var countryCode = $("[id='playerTaskParams.areaCode']").val();
	if(!countryCode){
		openAlertmWindow('提示信息','请选择国家码!','确定');
		return;
	}
// 	console.log( "countryCode="+countryCode);
	var data = {"countryCode": countryCode};
	 $.ajax({
         type: "POST",
         url: url,
         data: data,
         dataType : "json",
         success:function(data){
        	 if(data){
            	 $("[id='playerTaskParams.pushTime']").attr('onfocus',"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'"+data.minDate+"'})");
//             	 console.log( $("[id='playerTaskParams.pushTime']").attr('onfocus'));
        	 }
         },
         error : function (XMLHttpRequest, textStatus, errorThrown) {
        	 //alert(textStatus+"=="+errorThrown);
         }
	 }); 
}

</script>

</head>

<body>

<%@ include file="../../../include/navigation.jsp" %>

<div class="formbody">
<div class="formtitle"><span>新增消息推送</span></div>
<form method="post" name="form" id="form" enctype="multipart/form-data" action="<s:url action="add"/>" onsubmit="return false;">
<input type="hidden" name="playerTaskParams.channel" id="playerTaskParams.channel">
<input type="hidden" name="playerTaskParams.language" id="playerTaskParams.language">
<input type="hidden" name="playerTaskParams.version" id="playerTaskParams.version">
    <ul class="forminfo">
	    <li>
	    	<label style="width:120px;">消息标题<font class = "require">*</font></label>
	    	<input name="playerTaskParams.title" id="pushTask.title" type="text" class="dfinput validate[required,minSize[1],maxSize[150]]" maxlength="150"/><i class = "tips">请输入消息标题</i>
	    </li>
	    <li>
	    	<label style="width:120px;">消息内容<font class = "require">*</font></label>
	    	<input name="playerTaskParams.content" id="pushTask.content" type="text" class="dfinput validate[required,minSize[1],maxSize[2500]]" maxlength="2500"/><i class = "tips">请输入消息内容</i>
	    </li>
	    <li>
	    	<label style="width:120px;">消息备注<font class = "require">*</font></label>
	    	<input name="playerTaskParams.description" id="pushTask.description" type="text" class="dfinput validate[required,minSize[1],maxSize[250]]" maxlength="250"/><i class = "tips">请输入消息备注</i>
	    </li>
	    <li>
	    	<label style="width:120px;">图标<font class = "require">*</font></label>
	    	<cite>
	    		<input name="playerTaskParams.icon" type="radio" value="0" checked="checked"/>&nbsp;&nbsp;默认
	    		<!-- &nbsp;&nbsp;&nbsp;&nbsp;
	    		<input name="playerTaskParams.icon" type="radio" value="1"/>&nbsp;&nbsp;自定义 -->
	    	</cite>
    	</li>
    	<li>
	    	<label style="width:120px;">目标国家<font class = "require">*</font></label>
			<select name="playerTaskParams.areaCode" id="playerTaskParams.areaCode" class="dayinput form-control validate[required]" style="width:500px;">
				<option value="" selected="selected" >请选择</option>
				<c:if test="${not empty playerCountryList}">
					<s:iterator value="playerCountryList" var="playerCountry">
						<option value="<s:property value="#playerCountry.countryCode"/>">
							<s:property value="#playerCountry.countryNameZh"/>（<s:property value="#playerCountry.countryCode"/>）
						</option>
					</s:iterator>
				</c:if>
			</select>
	    </li>
	    <li>
	    	<label style="width:120px;">目标用户<font class = "require">*</font></label>
	    	<select name="playerTaskParams.target" id="playerTaskParams.target" class="dayinput form-control" style="width:150px;display:inline-block;margin-right:30px;">
				<option value="1" selected="selected">所有人</option>
				<option value="2">部分人</option>
				<option value="3">测试</option>
			</select>
			<div class="js-region-select">
				<select name="playerTaskParams.topicType" id="playerTaskParams.topicType" class="dayinput form-control" style="width:150px;vertical-align: top;display:inline-block;margin-right:20px;">
					<option value="" selected="selected">请选择</option>
					<option value="1">版本</option>
					<option value="2">渠道</option>
					<option value="3">语言</option>
				</select>
				<div class="li-select-lang li-select-lang1" >
		    		  		 
					<div class="dl-list-wrap">
						<dl class="dl-list">
					 		<dt><input type="checkbox" class="check-all" name="language-all" id="language-all"><span>语言</span> <em class="toggle-btn"></em></dt>
					 		<div class="dd-wrap">
					 			<s:iterator value="languageList" var="language">
						 			<dd><input name="language" type="checkbox" class="check-item" value="${language}"><span>${language}</span></dd>
					 			</s:iterator>
					 		</div>	 		
					 	</dl>
					</div>
		    	</div>
		    	<div class="li-select-lang li-select-channel1">
		    		 	 
					<div class="dl-list-wrap">
						<dl class="dl-list" >
					 		<dt><input type="checkbox" class="check-all" name="channel-all" id="channel-all"><span>渠道</span> <em class="toggle-btn"></em></dt>
					 		<div class="dd-wrap">
					 			<s:iterator value="channelList" var="channel">
						 			<dd><input name="channel" type="checkbox" class="check-item" value="${channel}"><span>${channel}</span></dd>
					 			</s:iterator>
					 		</div>
					 		
					 	</dl>
					</div>
		    	</div>
		    	<div class="li-select-lang li-select-version1">
		    		 		 
					<div class="dl-list-wrap">
						<dl class="dl-list">
					 		<dt><input type="checkbox" class="check-all" name="version-all" id="version-all"><span>版本</span> <em class="toggle-btn"></em></dt>
					 		<div class="dd-wrap">
					 			<s:iterator value="appVersionList" var="appVersion">
						 			<dd><input name="version" type="checkbox" class="check-item" value="${appVersion}"><span>${appVersion}</span></dd>
					 			</s:iterator>
					 		</div>
					 	</dl>
					</div>
		    	</div>
    	
			</div>	
	    		
    	</li>
    	<li class="js-user-id" style="display:none;">
    		<label style="width:120px;">用户ID<font class = "require">*</font></label>
			<input name="playerTaskParams.userId" id="playerTaskParams.userId" type="text" class="dfinput" maxlength="32"/><i class = "tips">请输入用户ID</i>
    	</li>
	    <li>
	    	<label style="width:120px;">后续动作<font class = "require">*</font></label>
	    	<cite>
	    		<input name="playerTaskParams.operation" type="radio" value="0" checked="checked"/>&nbsp;&nbsp;打开应用
	    		&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input name="playerTaskParams.operation" type="radio" value="1"/>&nbsp;&nbsp;打开页面
	    	</cite>
	    	
    	</li>
	    <li class="js-video-type" style="display:none;">
	    	<label style="width:120px;">类型<font class = "require">*</font></label>
	    	<select name="playerTaskParams.type" id="playerTaskParams.type" class="dayinput form-control" style="width:150px;">
				<option value="" selected="selected">请选择</option>
				<option value="1001">影片</option>
				<option value="1002">活动</option>
			</select>
	    	
	    </li>
	    <li class="js-video-url" style="display:none;">
	    	<label style="width:120px;">url<font class = "require">*</font></label>
	    	<input name="playerTaskParams.url" id="playerTaskParams.url" type="text" class="dfinput" maxlength="250"/><i class = "tips">请输入url，例：http://xxx</i>
	    </li>
	    <li class="js-video-id" style="display:none;">  	
	    	<label style="width:120px;">影片ID<font class = "require">*</font></label>
	    	<input name="playerTaskParams.fileId" id="playerTaskParams.fileId" type="text" class="dfinput" maxlength="250"/><i class = "tips">请输入影片ID</i>
	    </li>
	    <li>
	    	<label style="width:120px;">提醒方式<font class = "require">*</font></label>
	    	<cite>
	    		<input name="playerTaskParams.reminderType" type="radio" value="0" checked="checked"/>&nbsp;&nbsp;全部
	    		&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input name="playerTaskParams.reminderType" type="radio" value="1"/>&nbsp;&nbsp;震动
	    		<input name="playerTaskParams.reminderType" type="radio" value="2"/>&nbsp;&nbsp;响铃
	    	</cite>
    	</li>
 		<li>
	    	<label style="width:120px;">推送时间<font class = "require">*</font></label>
	    	<select name="playerTaskParams.picked" id="playerTaskParams.picked" class="dayinput form-control validate[required]" style="width:150px;display:inline-block;margin-right:30px;" onchange="changeType()">
				<option value="" selected="selected">请选择</option>
				<option value="1">定时推送</option>
				<option value="0">立即推送</option>
			</select>
	    	<input  name="playerTaskParams.pushTime" id="playerTaskParams.pushTime" class="Wdate" style="width: 200px;"  readonly="readonly" onclick="getMinDate();"/>
	    </li>
	    <li>
	    	<label>&nbsp;</label>
		    <input name="" type="submit" class="btn" value="保存"/>
		    <input name="" type="button" class="btn" value="返回" onclick="javascript:history.go(-1)"/>
	    </li>
    </ul>
	</form>
   </div>
</body>
</html>
