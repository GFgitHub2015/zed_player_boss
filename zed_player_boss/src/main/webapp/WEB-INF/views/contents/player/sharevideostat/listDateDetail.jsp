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
    <script type="text/javascript" src="<s:property value="jsPath" />echarts/echarts.min.js"></script>
    
<script type="text/javascript">

function changeSorted(){
	var checkedCol = $('i[name="sortColumn"][sorted="true"]');
	var sortedColumnName = checkedCol.attr("id");
	var sortedColumnValue = checkedCol.attr("orderby");
	if(sortedColumnName){
		$("#sortedColumnName").val(sortedColumnName);
		$("#sortedColumnValue").val(sortedColumnValue);
	}else{
		$("#sortedColumnName").val("");
		$("#sortedColumnValue").val("");
	}
	layer.load(2, {
		  shade: [0.7,'#fff'] //0.1透明度的白色背景
	});
	document.form.submit();
}
function back(){
	var url = '<s:url action="listDate.action" />';
	$("#channel").val("");
	$("#form").attr("action",url);
	document.form.submit();
}

$(function(){
	createEchart(${dateList},${pvList},${uvList},${shareList},${downloadList});
});

function createEchart(dateList,pvList,uvList,shareList,downloadList){
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
                   '独立访客' : false,
                   '分享次数' : false,
                   '下载次数' : false
               },
               selectedMode : 'single',
               y : 'bottom',
        	   data:["浏览量","独立访客","分享次数","下载次数"]
           },
           calculable : true,
           xAxis: {
               data: dateList
           },
           yAxis: {},
           series: [{
               name: '浏览量',
               showAllSymbol : true,
               smooth : true,
               type: 'line',
               data: pvList,
               markPoint: {
                   data: [
                       {type: 'max', name: 'max'},
                       {type: 'min', name: 'min'}
                   ]
               }
           },{
               name: '独立访客',
               type: 'line',
               smooth: true,
               data: uvList,
               markPoint: {
                   data: [
                       {type: 'max', name: 'max'},
                       {type: 'min', name: 'min'}
                   ]
               }
           },{
               name: '分享次数',
               type: 'line',
               data: shareList,
               markPoint: {
                   data: [
                       {type: 'max', name: 'max'},
                       {type: 'min', name: 'min'}
                   ]
               }
           },{
               name: '下载次数',
               type: 'line',
               data: downloadList,
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
    <form id = "form" action="<s:url action="listDateDetail" />" method="post" name="form">
		<input type="hidden" name="sortedColumnName" id="sortedColumnName"  value="${sortedColumnName }">
		<input type="hidden" name="sortedColumnValue" id="sortedColumnValue"  value="${sortedColumnValue }">
        <ul class="seachform">
            <li>
                <label>开始时间</label>
                <input  name="startDate" id="startDate" class="Wdate" value="${startDate }" style="width: 100px;"  value="${startDate }"   readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
            </li>
            <li>
                <label>结束时间</label>
                <input  name="endDate" id="endDate" class="Wdate" value="${endDate }" style="width: 100px;" value="${endDate }"  readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
            </li>
            <li>
                <label>渠道</label>
                <select  name="channel" id="channel"  class="dayinput form-control" style="width: 150px;" >
					<option value="">请选择..</option>
					<c:forEach var="appinfo" items="${appInfos }">
						<option value="${appinfo.channel }" <c:if test="${channel==appinfo.channel }">selected="selected"</c:if>>${appinfo.appname }</option>
					</c:forEach>
					<option value="GOOGLEPLAY" <c:if test="${channel=='GOOGLEPLAY' }">selected="selected"</c:if>>GOOGLEPLAY</option>
					<option value="Zillion" <c:if test="${channel=='Zillion' }">selected="selected"</c:if>>Zillion</option>
                </select>
            </li>
            <li>
                <label>&nbsp;</label>
                <input type="submit" class="scbtn btn" value="查询" onclick="initPageNo()"/>
                <input type="submit" class="scbtn btn" value="返回" onclick="back()"/>
            </li>
        </ul>
        <s:include value="../../../include/buttonTop.jsp"/>
        
        <div id="echartsDiv" style="width: 85%;height: 600px;"></div>
        <table class="tablelist">
            <thead>
            <tr>
                <th>日期</th>
                <th>浏览量 </th>
                <th>独立访客 </th>
                <th>下载点击次数 </th>
                <th>分享次数 </th>
            </tr>
            </thead>
            <tbody>
            <s:iterator value="page.result" var="list" status="st">
                <tr>
                    <td>${list.statDate }</td>
                    <td>${list.sharePv }</td>
                    <td>${list.shareUv }</td>
                    <td>${list.downloadCount }</td>
                    <td>${list.shareCount }</td>
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
