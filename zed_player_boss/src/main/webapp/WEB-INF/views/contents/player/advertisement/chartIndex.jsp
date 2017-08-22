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
    <script type="text/javascript" src="<s:property value="jsPath" />layer/layer.js"></script>
    <script type="text/javascript" src="<s:property value="jsPath" />echarts/echarts.min.js"></script>
<script type="text/javascript">

$(function(){
	searchData('${channel}','${startDate}','${endDate}','${adPlatform}');
});

function changeOption(){
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var channel = $("#channel").val();
	var adPlatform = $("#adPlatform").val();
	searchData(channel,startDate,endDate,adPlatform);
}

function searchData(channel,startDate,endDate,adPlatform){
	var url = '<s:url action="chartStatAjax.action" />';
	var data ={};
	data.startDate = startDate;
	data.endDate = endDate;
	data.channel = channel;
	data.adPlatform = adPlatform;
	$.ajax({
		type: "POST",
        url: url,
        data: data,
        dataType : "json",
        success:function(data){
			if(data&&data.code=='success'){
				var result = data.data;
				createEchart(result.dates,result.adRequests,result.earnings,result.adImpressions,result.ecpms);
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

function createEchart(dates,adRequests,earnings,adImpressions,ecpms){
	// 基于准备好的dom，初始化echarts实例
       var myChart = echarts.init(document.getElementById('echartsDiv'));
       // 指定图表的配置项和数据
       var option = {
           title: {
               text: ''
           },
           tooltip: {},
           legend: {
               selected: {
                   '请求量' : false,
                   '展示' : false,
                   'ECPM' : false
               },
               selectedMode : 'single',
               y : 'bottom',
        	   data:["收益","请求量","展示","ECPM"]
           },
           calculable : true,
           xAxis: {
               data: dates
           },
           yAxis: {},
           series: [{
               name: '收益',
               showAllSymbol : true,
               smooth : true,
               type: 'line',
               data: earnings,
               markPoint: {
                   data: [
                       {type: 'max', name: 'max'},
                       {type: 'min', name: 'min'}
                   ]
               }
           },{
               name: '请求量',
               type: 'line',
               smooth: true,
               data: adRequests,
               markPoint: {
                   data: [
                       {type: 'max', name: 'max'},
                       {type: 'min', name: 'min'}
                   ]
               }
           },{
               name: '展示',
               type: 'line',
               data: adImpressions,
               markPoint: {
                   data: [
                       {type: 'max', name: 'max'},
                       {type: 'min', name: 'min'}
                   ]
               }
           },{
               name: 'ECPM',
               type: 'line',
               data: ecpms,
               markPoint: {
                   data: [
                       {type: 'max', name: 'max'},
                       {type: 'min', name: 'min'}
                   ]
               }
           }]
       };

       // 使用刚指定的配置项和数据显示图表。
       myChart.setOption(option);
}
</script>
    
</head>

<body>
<%@ include file="../../../include/navigation.jsp" %>

<div class="mainindex">
    <form id = "form" action="<s:url action="chartStatAjax" />" method="post" name="form">
        <ul class="seachform">
            <li>
                <label>开始时间</label>
                <input  name="startDate" id="startDate" class="Wdate" value="${startDate }" style="width: 150px;"  value="${startDate }"   readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
            </li>
            <li>
                <label>结束时间</label>
                <input  name="endDate" id="endDate" class="Wdate" value="${endDate }" style="width: 150px;" value="${endDate }"  readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
            </li>
            <li>
                <label>站长</label>
                <select  name="channel" id="channel"  class="dayinput form-control" style="width: 150px;" >
					<option value="">请选择..</option>
					<c:forEach var="appinfo" items="${appInfos }">
						<option value="${appinfo.channel }" <c:if test="${channel==appinfo.channel }">selected="selected"</c:if>>${appinfo.appname }</option>
					</c:forEach>
                </select>
            </li>
            <li>
                <label>平台</label>
                <select  name="adPlatform" id="adPlatform"  class="dayinput form-control" style="width: 150px;" >
					<option value="">请选择..</option>
					<c:forEach var="platform" items="${adPlatforms }">
						<option value="${platform }" <c:if test="${platform==adPlatform }">selected="selected"</c:if>>${platform }</option>
					</c:forEach>
                </select>
            </li>
            <li>
                <label>&nbsp;</label>
                <input type="button" class="scbtn btn" value="查询" onclick="changeOption()" />
            </li>
            <li>
                <label>&nbsp;</label>
                <input type="button" class="scbtn btn" value="返回" onclick="javascript:history.back(-1)" />
            </li>
        </ul>
        <s:include value="../../../include/buttonTop.jsp"/>
        <div id="echartsDiv" style="width: 85%;height: 600px;"></div>
		<%@ include file="../../../include/buttonRight.jsp" %>
        <%@ include file="../../../include/page.jsp" %>
    </form>

</div>

</body>
</html>
