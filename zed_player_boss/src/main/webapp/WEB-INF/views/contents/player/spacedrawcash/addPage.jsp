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
	<style >
	    .input-group-addon{
	        display: none !important;
	    }
	    .input-group-btn{
	        display: none !important;
	    }
        .appname_span{
            display: inline-block;
            width: 140px;
			margin-left: 10px;
        }
        .admount_span{
            display: inline-block;
            width: 80px;
        }
        
        .rewardamount_span{
            display: inline-block;
            width: 80px;
        }
        .dropdown-menu{
        	width: 350px;
			max-height: 400px;
			overflow: auto;
        }
		.dropdown-menu li:hover {
			background-color: #F5F5F5 !important;
		}
		.disable_div{
            display:none;
			color: darkgrey;
			cursor: not-allowed;
		}
		.enable_div{
			cursor: pointer;
			color: black;
		}
	</style>
<script type="text/javascript">

$(document).ready(function(e) {
	$('#form').validationEngine('attach', { 
	    promptPosition: 'centerRight', 
	    scroll: false
	  });	  
	$('#form').bind('jqv.form.result', function(event, errorFound){process(errorFound)});
    changeSourceType();
});

function changeSourceType(){
    var sourceType = $("#sourceType").val();
    $("#channel").val("");
    $("#btntext").text("请选择..!");
    $(".channel_amount_li").each(function (index, domEle) {
        var adAmount = $(domEle).attr("adAmount");
        var rewardAmount = $(domEle).attr("rewardAmount");
        var channel = $(domEle).attr("channel");
        //console.log("channel"+channel+",rewardAmount:"+rewardAmount+",adAmount:"+adAmount+",sourceType:"+sourceType);
        if(sourceType=="14"){//广告收入
            if(adAmount>100){
                $("#"+channel+"_disable_div").hide();
                $("#"+channel+"_enable_div").show();
            }else{
                $("#"+channel+"_disable_div").show();
                $("#"+channel+"_enable_div").hide();
            }
        }else if(sourceType=="12"){//奖励收入
            if(rewardAmount>200){
                $("#"+channel+"_disable_div").hide();
                $("#"+channel+"_enable_div").show();
            }else{
                $("#"+channel+"_disable_div").show();
                $("#"+channel+"_enable_div").hide();
            }
        }
    });
}

function process(errorFound){
	if(!errorFound){
		var channel = $("#channel").val();
        var memo = $("#memo").val();
        var amount = $("#amount").val();
		if(!channel){
			layer.alert('请选择站长!');
			return;
		}
        if(!memo){
            layer.alert('请输入备注!');
            return;
        }
        if(parseInt(amount)>parseInt(maxAmount)){
            layer.alert('提现超出范围!');
            return;
        }
		layer.confirm('是否确认提交？', {
			  btn: ['确定','取消'] //按钮
			}, function(){
		    	console.log("111")
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
var maxAmount=999999;
function changeChannel(channel,appname,adAmount,rewardAmount){
    if(!channel){
        $("#channel").val("");
        $("#btntext").text("请选择..!");
        return;
    }
    var sourceType = $("#sourceType").val();
    if(sourceType=="14"){//广告收入
        maxAmount=adAmount;
    }else if(sourceType=="12"){//奖励收入
        maxAmount=rewardAmount;
    }else{
        maxAmount=999999;
	}
	$("#maxAmountDiv").show();
    $("#maxAmountSpan").html(maxAmount);
	$("#channel").val(channel);
	$("#btntext").text(appname);
}


</script>

</head>

<body>

<%@ include file="../../../include/navigation.jsp" %>

<form method="post" name="form" id="form" class="form-horizontal"  action="<s:url action="addSpaceDrawCash.action"/>" onsubmit="return false;">
		<div class="formbody">
			<div class="formtitle">
				<span>新增提现记录</span>
			</div>
			<div class="form-group">
                <label class="col-md-1 control-label"  >收入来源<font class = "require">*</font></label>
                <div class="col-sm-2">
                    <select name="spaceDrawCash.sourceType" id="sourceType" class="form-control" style="width: 250px;" onchange="changeSourceType()">
                       <option value="14">广告收入</option>
                       <option value="12">奖励收入</option>
                    </select>
                </div>
            </div>
			<div class="form-group">
				<label class="col-md-1 control-label"  >站长<font class = "require">*</font></label>
				<div class="col-sm-2">
		    		<input name="spaceDrawCash.channel" id="channel" type="hidden" ></input>
                    <div class="btn-group">
                      <button type="button" class="btn btn-default dropdown-toggle" style="width: 250px;" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <span id="btntext">请选择</span><span class="caret"></span>
                      </button>
                      <ul class="dropdown-menu" >
                        <li>
							<div onclick="changeChannel()">
								<span class="appname_span">应用名称</span>
								<span class="admount_span">广告收入</span>
								<span class="rewardamount_span">奖励收入</span>
							</div>
                        </li>
                        <c:forEach var="appinfo" items="${channelAmounts }">
                            <li class="channel_amount_li" channel="${appinfo.channel }" adAmount="${appinfo.adAmount}" rewardAmount="${appinfo.rewardAmount}">
                                <div class ="disable_div" onclick="changeChannel()" id="${appinfo.channel }_disable_div">
                                <span class="appname_span">${appinfo.appname }</span>
                                <span class="admount_span">${appinfo.adAmount}</span>
                                <span class="rewardamount_span">${appinfo.rewardAmount}</span>
                                </div>
                                <div class ="enable_div" id="${appinfo.channel }_enable_div"
									 onclick="changeChannel('${appinfo.channel }','${appinfo.appname }','${appinfo.adAmount }','${appinfo.rewardAmount }')">
                                <span class="appname_span">${appinfo.appname }</span>
                                <span class="admount_span">${appinfo.adAmount}</span>
                                <span class="rewardamount_span">${appinfo.rewardAmount}</span>
                                </div>
                            </li>
                        </c:forEach>
                      </ul>
                    </div>
					<div>
						广告收入提现需大于$100,奖励提现需大于$200
					</div>
				</div>
			</div>

			<div class="form-group">
				<label class="col-md-1 control-label"  >提现金额<font class = "require">*</font></label>
				<div class="col-sm-2">
					<div>
						<input name="spaceDrawCash.amount" id="amount" oninput="if(value.length>7)value=value.slice(0,7)"  type="number" class="form-control dfinput validate[required,minSize[1],maxSize[100]]"  value="0" />
					</div>
					<span id="maxAmountDiv"  style="display: none;">最大提现金额:<span id="maxAmountSpan" ></span></span>
				</div>

			</div>
			<div class="form-group" id="memodiv" >
				<label class="col-md-1 control-label"  >备注<font class = "require">*</font></label>
				<div class="col-sm-2">
					<input name="spaceDrawCash.memo" id="memo" maxlength="500"  type="text" class="form-control dfinput validate[required,minSize[1],maxSize[100]]"   />
				</div>
			</div>
			<div class="form-group">
			<label class="col-md-1 control-label"  ></label>
				<div class="col-sm-2">
					<input class="btn btn-info" type="submit" value="保存">
					<input class="btn btn-info" type="reset" value="重置">
				</div>
			</div>
		</div>
	</form>
</body>
</html>
