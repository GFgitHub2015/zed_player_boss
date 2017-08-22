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
    <script type="text/javascript" src="<s:property value="jsPath" />My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<s:property value="jsPath" />sorticon.js"></script>
    <script type="text/javascript" src="<s:property value="jsPath" />layer/layer.js"></script>
<style type="">
	.scaleinput{
		width: 80px;
	}
</style>
<script type="text/javascript">

function showChangeScaleInput(obj){
	$(obj).hide();
	$(obj).next().show();
}

function changeScale(channelId,scale){
	var url = '<s:url action="updateAppScale.action" />';
	var data ={"channel" : channelId,"activeCountScale":scale};
	$.ajax({
		type: "POST",
        url: url,
        data: data,
        dataType : "json",
        success:function(data){
			if(data&&data.code=='success'){
				window.location.reload();
	       	}else{
				layer.alert("修改失败!");
			}
	},
    error : function (XMLHttpRequest, textStatus, errorThrown) {
		$(".scaleinput").hide();
		$(".scalespan").show();
    }
	}); 
}
$(function(){
		$(document).click(function(){
			var did = event.srcElement.className;
			if(did!='scaleinput'){
				var visibleInput = $(".scaleinput:visible");
				if(visibleInput.length>0){
					var oldval=visibleInput.attr("oldvalue");
					var curval = visibleInput.val();
					if(oldval!=curval){
						changeScale(visibleInput.attr("channel"),curval);
					}else{
						$(".scaleinput").hide();
						$(".scalespan").show();
					}
				}
			}
		});
})
</script>
    
</head>

<body>
<%@ include file="../../../include/navigation.jsp" %>

<div class="mainindex">
    <form id = "form" action="<s:url action="masterStatList" />" method="post" name="form">
        <ul class="seachform">
            <li>
                <label>站长</label>
                <input  name="appName" type="text"   class="scinput form-control" value = "${appName}"/>
            </li>
            <li>
                <label>&nbsp;</label>
                <input type="submit" class="scbtn btn" value="查询" onclick="initPageNo()"/>
            </li>
        </ul>
        <s:include value="../../../include/buttonTop.jsp"/>
        <table class="tablelist">
            <thead>
            <tr>
                <th>站长</th>
                <th>昨日活跃用户</th>
                <th>过去7天平均活跃用户数</th>
                <th>过去30天平均活跃用户数</th>
                <th>站长显示比例</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <s:iterator value="page.result" var="list" status="st">
                <tr>
                    <td>${list.appname }</td>
                    <td>${list.activeCount }</td>
                    <td>${list.sevenStat }</td>
                    <td>${list.thirtyStat }</td>
                    <td>
						<span title='双击修改' class='scalespan'  ondblclick='showChangeScaleInput(this)'>${list.activeCountScale*100 }%</span>
           				<input channel='${list.channel }' oldvalue="${list.activeCountScale*100 }"   type='number' class='scaleinput' style='display:none;'  value='${list.activeCountScale*100 }' />
					</td>
                    <td>
						<s:iterator value="buttonMenuList" var="buttonRight">
							<s:if test="%{#buttonRight.resourceLevel == 5}">
								<a  href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}','${list.channel}')"   class="tablelink">${buttonRight.resourceName}</a>
							</s:if>
						</s:iterator>
                    </td>
                </tr>
            </s:iterator>
            </tbody>
        </table>
		<%@ include file="../../../include/buttonRight.jsp" %>
        <%@ include file="../../../include/page.jsp" %>
    </form>

</div>

</body>
</html>
