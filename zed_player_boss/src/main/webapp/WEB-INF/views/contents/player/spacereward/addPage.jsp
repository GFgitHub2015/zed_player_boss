<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../include/resource.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<!-- common -->
	<link href="<s:property value="themePath" />css/style.css" rel="stylesheet">
    <link href="<s:property value="themePath" />css/style-responsive.css" rel="stylesheet"> 
    <link href="<s:property value="themePath" />js/bootstrap-select/css/bootstrap-multiselect.css" rel="stylesheet">
	<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />basic.css"/>
	<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />validationEngine.jquery.css"/>
	<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />loading.css"/>
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />window.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />function.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.validationEngine-lang.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.validationEngine.min.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />loading.js"></script>
    <script type="text/javascript" src="<s:property value="jsPath" />My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />layer/layer.js"></script>
	<script type="text/javascript" src="<s:property value="themePath" />js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<s:property value="themePath" />js/bootstrap-select/js/bootstrap-multiselect.js"></script>
	<style>
	</style>
<script type="text/javascript">

$(document).ready(function(e) {
	$('#form').validationEngine('attach', { 
	    promptPosition: 'centerRight', 
	    scroll: false
	  });	  
	$('#form').bind('jqv.form.result', function(event, errorFound){process(errorFound)});
	//$('#channel').selectpicker();    .multiselect();
	$('#channel').multiselect({
		maxHeight: 300,
		buttonWidth: '250px',
		nonSelectedText: '请选择!',
		includeSelectAllOption: true,
		selectAllText : '选择全部',
		//enableFiltering: true,
		buttonText: function(options, select) {
			 var labels = [];
	            options.each(function () {
	                labels.push($(this).text());
	            });
	            if(labels.length==0){
	            	return "请选择..";
	            }
	            return labels.join(',');
        }
	});

});

function process(errorFound){
	if(!errorFound){

		var sourceType = $("#sourceType").val();
		var channel = $("#channel").val();
		var memo = $("#memo").val();
		if(!channel){
			layer.alert('请选择站长!');
			return;
		}
		if(!sourceType){
			layer.alert('请选择徽章!');
			return;
		}
		if(sourceType=='12'&&!memo){
			layer.alert('请输入备注!');
			return;
		}
		layer.confirm('是否确认提交？', {
			  btn: ['确定','取消'] //按钮
			}, function(){
				formSubmit()
			}, function(){
			 
			});
	}
}

var commited = false;
function formSubmit(){
    if(commited==true){
        return;
    }
    commited=true;
    LoadingPic.FullScreenShow();
    document.form.submit();
}
function changeSourceType(type,earnings,text){
	$("#sourceType").val(type);
	$("#earnings").val(earnings);
	$("#btntext").text(text);
	if(type=='12'){//选择其他
		$("#memodiv").show();
		$("#earnings").val(0);
		//$("#earnings").attr("readOnly","");
		$("#earnings").removeAttr("readonly");
	}else{
		$("#memo").val('');
		$("#memodiv").hide();
	}
}
</script>

</head>

<body>

<%@ include file="../../../include/navigation.jsp" %>

<form method="post" name="form" id="form" class="form-horizontal"  action="<s:url action="addSpaceReward.action"/>" onsubmit="return false;">
		<div class="formbody">
			<div class="formtitle">
				<span>新增奖励</span>
			</div>
			<div class="form-group">
				<label class="col-md-1 control-label"  >站长<font class = "require">*</font></label>
				<div class="col-sm-2">
					<select name="rewardData.channel" id="channel"  style="display:none;" multiple="multiple" class="form-control" style="width: 150px;">
		    		<c:forEach var="appinfo" items="${appInfos }">
							<option value="${appinfo.channel }" >${appinfo.appname }</option>
					</c:forEach>
		    		</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-1 control-label"  >徽章<font class = "require">*</font></label>
				<div class="col-sm-2">
					<input name="rewardData.sourceType" id="sourceType" type="hidden" ></input>
			    	<div class="btn-group">
					  <button type="button" class="btn btn-default dropdown-toggle" style="width: 250px;" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					    <span id="btntext">请选择</span><span class="caret"></span>
					  </button>
					  <ul class="dropdown-menu">
					    <c:forEach var="gadge" items="${gadges }">
					    		<li>
					    			<a href="javascript:changeSourceType('${gadge.sourceType }','${gadge.earnings }','${gadge.sourceTypeName }')">
					    			<c:if test="${gadge.iconUrl!=null&&gadge.iconUrl!='' }">
							 			<img width="30" height="30" alt="${gadge.iconTitle }"  title="${gadge.iconTitle }" src="${publicAttr.imagePath }${gadge.iconUrl}">
							 		</c:if>
					    			${gadge.sourceTypeName }
					    			</a>
					    		</li>
						</c:forEach>
					  </ul>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-1 control-label"  >金额<font class = "require">*</font></label>
				<div class="col-sm-2">
					<input name="rewardData.earnings" id="earnings" oninput="if(value.length>7)value=value.slice(0,7)"  type="number" class="form-control dfinput validate[required,minSize[1],maxSize[100]]"  readonly="readonly" value="0" />
				</div>
			</div>
			<div class="form-group" id="memodiv" style="display: none;">
				<label class="col-md-1 control-label"  >备注<font class = "require">*</font></label>
				<div class="col-sm-2">
					<input name="rewardData.memo" id="memo" maxlength="500"  type="text" class="form-control dfinput validate[required,minSize[1],maxSize[100]]"   />
				</div>
			</div>
			<div class="form-group">
			<label class="col-md-1 control-label"  ></label>
				<div class="col-sm-2">
					<input class="btn btn-info" type="submit" value="保存">
				</div>
			</div>
		</div>
	</form>
</body>
</html>
