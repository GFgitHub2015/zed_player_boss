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
<style type="text/css">
	.fbold{
		color: black;
		font-size: 16px;
	}
	.scaleinput{
		width: 40px;
	}
	.trcolor{
		background-color: #F0F8FF;
	}
	.tipslabel{
		width: 120px;
	}
	.tip{width:250px !important;height:200px !important;display:none;background-color:#f6f7f7;color:#333333;line-height:18px;border:1px solid #e1e3e2;}  
</style>
<script type="text/javascript">
//获取鼠标坐标  
function mousePosition(ev){   
    ev = ev || window.event;   
    if(ev.pageX || ev.pageY){   
        return {x:ev.pageX, y:ev.pageY};   
    }   
    return {   
        x:ev.clientX + document.body.scrollLeft - document.body.clientLeft,   
        y:ev.clientY + document.body.scrollTop - document.body.clientTop   
    };   
}  
$(function(){
		$(document).click(function(){ 
			var did = event.srcElement.className;
			var mousePos = mousePosition(event);  
	        var  xOffset = -280;  
	        var  yOffset = 25;  
			//alert(channelId);
			if(did=='fa fa-file-text'){
				var channelId = $(event.srcElement).parent().attr("channel");
				var appname = $(event.srcElement).parent().attr("appname");
				if(!channelId){
					return;
				}
				var url = '<s:url action="getAppScales.action" />';
				var data ={"channel" : channelId};
				$.ajax({
					type: "POST",
			        url: url,
			        data: data,
			        dataType : "json",
			        success:function(data){
						if(data&&data.data){
							var params= data.data;
							$("#appnameText").html(appname);
							$("#scaleChannel").val(channelId);
							$("#earningsScale").val((params.earningsScale*100).toFixed(0));
							$("#ecpmMin").val(params.ecpmMin);
							$("#ecpmMax").val(params.ecpmMax);
					        $("#tooltip").css("display","block").css("position","absolute").css("top",(mousePos.y - yOffset) + "px").css("left",(mousePos.x + xOffset) + "px"); 
						} 
				},
			    error : function (XMLHttpRequest, textStatus, errorThrown) {
			         $("#tooltip").css("display","none");  
			    }
				}); 
				
			}else if(!did){
		         $("#tooltip").css("display","none");  
			}
		});
})
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

function platformDetail(adPlatform){
	if(!adPlatform){
		return;
	}
	var iconClass = $("#"+adPlatform+"-icon").attr("class");
	var details = $("."+adPlatform+"-tr").length;
	if(iconClass=="fa fa-chevron-right"){
		$("#"+adPlatform+"-icon").attr("class","fa fa-chevron-down");
		if(details>0){
			$("."+adPlatform+"-tr").show("normal");
			return;
		}
	}else{
		$("#"+adPlatform+"-icon").attr("class","fa fa-chevron-right");
		$("."+adPlatform+"-tr").hide("normal");
		return;
	}
	
	var url = '<s:url action="listAppStatAjax.action" />';
	var data ={"adPlatform" : adPlatform};
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var appName = $("#appName").val();
	var sortedColumnName = $("#sortedColumnName").val();
	var sortedColumnValue = $("#sortedColumnValue").val();
	data.startDate = startDate;
	data.endDate = endDate;
	data.appName = appName;
	data.sortedColumnName = sortedColumnName;
	data.sortedColumnValue = sortedColumnValue;
	$.ajax({
		type: "POST",
        url: url,
        data: data,
        dataType : "json",
        success:function(data){
			if(data&&data.data){
           	 var resultJson = data.data;
           	 var trHtml = "";
           	 for (var index = 0; index < resultJson.length; index++) {
           		trHtml += "<tr class='"+adPlatform+"-tr trcolor' style='display: none;'>";
           		trHtml +="<td><a href=\"javascript:appDetail('"+resultJson[index].uid+"')\"  >&nbsp;&nbsp;&nbsp;&nbsp;"+resultJson[index].adPlatform+"</a></td>";
           		trHtml +="<td>"+resultJson[index].earnings+" ("+resultJson[index].earningsScale+")</td>";
           		trHtml +="<td>"+resultJson[index].adRequests+"</td>";
           		trHtml +="<td>"+resultJson[index].adResponse+"</td>";
           		trHtml +="<td>"+resultJson[index].adImpressions+" ("+resultJson[index].adImpressionsScale+")</td>";
           		trHtml +="<td>"+resultJson[index].clicks+"</td>";
           		trHtml +="<td>"+(resultJson[index].fillRate*100).toFixed(1)+"%</td>";//填充率
           		trHtml +="<td>"+(resultJson[index].ctr*100).toFixed(1)+"%</td>";//点击率
           		trHtml +="<td>"+resultJson[index].ecpm+"</td>";//ecpm
           		trHtml +="<td><a href=\"javascript:showChart('"+adPlatform+"','"+resultJson[index].uid+"')\"  <i class='fa fa-line-chart'></i></a></td>";
           		trHtml +="<td><a appname='"+resultJson[index].adPlatform+"' channel='"+resultJson[index].uid+"' adPlatform='"+adPlatform+"' id='"+resultJson[index].uid+"-"+adPlatform+"-scale' class='scalespan'   ><i class='fa fa-file-text'></i></a></td>";//比例原值
           		//trHtml +="<input channel='"+resultJson[index].uid+"' oldvalue='"+resultJson[index].scale*100+"'   type='number' class='scaleinput' style='display:none;'  value='"+resultJson[index].scale*100+"'></td>";
           		trHtml +="</tr>";
			}
            $("#"+adPlatform+"-total").after(trHtml);
			$("."+adPlatform+"-tr").show("normal");
       	 }else{
			$("#"+adPlatform+"-total").after("<tr class='"+adPlatform+"-tr' style='display: none;'><td>空</td></tr>");
			$("."+adPlatform+"-tr").show("normal");
		}
	},
    error : function (XMLHttpRequest, textStatus, errorThrown) {
   	 //alert(textStatus+"=="+errorThrown);
    }
	}); 
}

function changeScale(){
	var url = '<s:url action="updateAppScale.action" />';
	var channelId = $("#scaleChannel").val();
	var earningsScale = $("#earningsScale").val();
	var ecpmMin = $("#ecpmMin").val();
	var ecpmMax = $("#ecpmMax").val();
	var data ={"channel" : channelId};
	data.earningsScale = earningsScale/100;
	data.ecpmMin = ecpmMin;
	data.ecpmMax = ecpmMax;
	$.ajax({
		type: "POST",
        url: url,
        data: data,
        dataType : "json",
        success:function(data){
			if(data&&data.code=='success'){
		         $("#tooltip").css("display","none");  
	       	}else{
				layer.alert("修改失败!");
		         $("#tooltip").css("display","none");  
			}
	},
    error : function (XMLHttpRequest, textStatus, errorThrown) {
		layer.alert("修改失败!");
        $("#tooltip").css("display","none");  
    }
	}); 
}

function changeDataType(obj){
	var url = '<s:url action="listAppStat.action" />';
	var dtype = $(obj).val();
	if(dtype==2){
		$("#form").attr("action",url);
		document.form.submit();
	}
}

function showChart(adPlatform,channel){
	var url = '<s:url action="chartIndex.action" />';
	$("#adPlatform").val(adPlatform);
	$("#channel").val(channel);
	$("#form").attr("action",url);
	document.form.submit();
}


function appDetail(channel){
	var url = '<s:url action="listAppDetail.action" />';
	$("#channel").val(channel);
	$("#form").attr("action",url);
	document.form.submit();
}

</script>
    
</head>

<body>
<%@ include file="../../../include/navigation.jsp" %>

<div class="mainindex">
    <form id = "form" action="<s:url action="listPlatformStat" />" method="post" name="form">
		<input type="hidden" name="sortedColumnName" id="sortedColumnName"  value="${sortedColumnName }">
		<input type="hidden" name="sortedColumnValue" id="sortedColumnValue"  value="${sortedColumnValue }">
		<input type="hidden" name="channel" id="channel"  >
		<input type="hidden" name="adPlatform" id="adPlatform"  >
        <ul class="seachform">
            <li>
                <label>搜索方式</label>
                <select class="dayinput form-control" onchange="changeDataType(this)" style="width: 150px;">
                	<option value="1">广告平台</option>
                	<option value="2">站长</option>
                </select>
            </li>
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
                <input  name="appName" id="appName" type="text"  placeholder="查找站长" class="scinput form-control" value = "${appName}"/>
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
                <th>广告平台</th>
                <th>收益 (站长)<i id="earningsSort" name="sortColumn" class="fa fa-sort"></i></th>
                <th>请求<i id="adRequestsSort" name="sortColumn" class="fa fa-sort"></i></th>
                <th>响应<i id="adResponseSort" name="sortColumn" class="fa fa-sort"></i></th>
                <th>展示次数 (站长)<i id="adImpressionsSort" name="sortColumn" class="fa fa-sort"></i></th>
                <th>点击次数<i id="clicksSort" name="sortColumn" class="fa fa-sort"></i></th>
                <th>填充率</th>
                <th>点击率</th>
                <th>ECPM</th>
                <th>趋势</th>
                <th>比例</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                    <td><span class="fbold">总和</span></td>
                    <td><span class="fbold">${adDataTotal.earnings }</span> (${adDataTotal.earningsScale })</td>
                    <td><span class="fbold">${adDataTotal.adRequests }</span></td>
                    <td><span class="fbold">${adDataTotal.adResponse }</span></td>
                    <td><span class="fbold">${adDataTotal.adImpressions }</span> (${adDataTotal.adImpressionsScale })</td>
                    <td><span class="fbold">${adDataTotal.clicks }</span></td>
                    <td>--</td>
                    <td>--</td>
                    <td>--</td>
                    <td>
						<c:if test="${adDataTotal != null}">
						<a href="javascript:showChart()" <i class="fa fa-line-chart"></i></a>
						</c:if>
					</td>
                    <td>--</td>
                </tr>
            <s:iterator value="page.result" var="list" status="st">
                <tr id="${list.adPlatform }-total">
                    <td><span class="fbold">${list.adPlatform } </span><a href="javascript:platformDetail('${list.adPlatform }')"  ><i id="${list.adPlatform }-icon" class="fa fa-chevron-right"></i></a></td>
                    <td>${list.earnings } (${list.earningsScale })</td>
                    <td>${list.adRequests }</td>
                    <td>${list.adResponse }</td>
                    <td>${list.adImpressions } (${list.adImpressionsScale })</td>
                    <td>${list.clicks }</td>
                    <td><fmt:formatNumber type="number" value="${list.fillRate * 100 }" pattern="0.0" maxFractionDigits="3"/>%</td>
                    <td><fmt:formatNumber type="number" value="${list.ctr * 100 }" pattern="0.0" maxFractionDigits="3"/>%</td>
                    <td>${list.ecpm }</td>
                    <td><a href="javascript:showChart('${list.adPlatform }','')"  <i class="fa fa-line-chart"></i></a></td>
                    <td>--</td>
                </tr>
            </s:iterator>
            </tbody>
        </table>
		<%@ include file="../../../include/buttonRight.jsp" %>
        <%@ include file="../../../include/page.jsp" %>
    </form>
	
<div id="tooltip" class="tip">
<form id = "formscale" action="<s:url action="listPlatformStat" />" method="post" name="formscale">
	<input  name="scaleChannel" id="scaleChannel" type="hidden"  />
	<div id="appnameText" style="text-align: center;font-size: 18px; background-color:  #e1e1e1;height: 25px;padding: 3px; "></div>
	<div style="padding: 5px;margin-top: 5px;">
	<ul class="seachform">
            <li>
                <label class="tipslabel">收入比例(%): </label>
               <input  name="earningsScale" id="earningsScale" type="number"  style="width: 80px"  class="scinput form-control"  />
            </li>
	</ul>
	<ul class="seachform">
            <li>
                <label class="tipslabel">CPM最小值 :</label>
               <input  name="ecpmMin" id="ecpmMin" type='number'  style="width: 80px"  class="scinput form-control"  />
            </li>
	</ul>
	<ul class="seachform">
            <li>
                <label class="tipslabel">CPM最大值 :</label>
               <input  name="ecpmMax" id="ecpmMax" type='number'  style="width: 80px" class="scinput form-control"  />
            </li>
	</ul>
	<ul class="seachform">
            <li>
                <label  class="ss" style="width: 70px">&nbsp;</label>
                <input type="button" class="scbtn btn" value="确认" onclick="changeScale()" />
            </li>
	</ul>
	</div>
	
</form>
</div>
</div>
</body>
</html>
