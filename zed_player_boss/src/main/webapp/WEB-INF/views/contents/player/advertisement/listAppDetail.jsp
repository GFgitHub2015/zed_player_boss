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
	}
	.scaleinput{
		width: 40px;
	}
	.trcolor{
		background-color: #F0F8FF;
	}
</style>
<script type="text/javascript">
$(function(){
	$(document).click(function(){
		var did = event.srcElement.className;
		if(did.indexOf('scaleinput')<0){
			var visibleInput = $("#siteNameInput");
			if(visibleInput){
				var oldval=visibleInput.attr("oldvalue");
				var curval = visibleInput.val();
				if(oldval!=curval){
					changeSiteName(visibleInput.attr("siteId"),curval);
				}else{
					 $("#siteNameInput").parent().html(oldval);
				}
			}
			
		}
	});
})

function changeSiteName(uid,siteName){
	var url = '<s:url action="updateSiteName.action" />';
	var data ={"siteId" : uid,"siteName":siteName};
	$.ajax({
		type: "POST",
        url: url,
        data: data,
        dataType : "json",
        success:function(data){
			if(data&&data.code=='success'){
		    	$("#siteNameInput").parent().html(siteName);
	       	}else{
				layer.alert("修改失败!");
		    	$("#siteNameInput").parent().html(siteName);
			}
	},
    error : function (XMLHttpRequest, textStatus, errorThrown) {
    	$("#siteNameInput").parent().html(siteName);
    }
	});
}


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
	
	var url = '<s:url action="listAppSiteDetailAjax.action" />';
	var data ={"adPlatform" : adPlatform};
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var channel = $("#channel").val();
	var sortedColumnName = $("#sortedColumnName").val();
	var sortedColumnValue = $("#sortedColumnValue").val();
	data.startDate = startDate;
	data.endDate = endDate;
	data.channel = channel;
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
           		trHtml +="<td>&nbsp;&nbsp;&nbsp;&nbsp;<span  ondblclick='showChangeSiteName(this)' siteId='"+resultJson[index].siteId+"' >"+resultJson[index].siteTypeName+"</span></td>";
           		trHtml +="<td>"+resultJson[index].earnings+" ("+resultJson[index].earningsScale+")</td>";
           		trHtml +="<td>"+resultJson[index].adRequests+"</td>";
           		trHtml +="<td>"+resultJson[index].adResponse+"</td>";
           		trHtml +="<td>"+resultJson[index].adImpressions+" ("+resultJson[index].adImpressionsScale+")</td>";
           		trHtml +="<td>"+resultJson[index].clicks+"</td>";
           		trHtml +="<td>"+(resultJson[index].fillRate*100).toFixed(1)+"%</td>";//填充率
           		trHtml +="<td>"+(resultJson[index].ctr*100).toFixed(1)+"%</td>";//点击率
           		trHtml +="<td>"+resultJson[index].ecpm+"</td>";//ecpm
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

function changeScale(channelId,scale){
	var url = '<s:url action="updateAppScale.action" />';
	var data ={"channel" : channelId,"scale":scale};
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


function showChangeSiteName(obj){
	var siteId = $(obj).attr("siteId");
	var oldValue = $.trim($(obj).html());
	$(obj).html("");
	$(obj).append("<input siteId='"+siteId+"' id ='siteNameInput' oldValue='"+oldValue+"' type='text' style='width:200px;' class='scaleinput' value='"+oldValue+"' >");
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

function totalIconChange(){
	var iconClass = $("#total-icon").attr("class");
	if(iconClass=="fa fa-chevron-right"){
		$("#total-icon").attr("class","fa fa-chevron-down");
		$(".total-detail").show("normal");
	}else{
		$("#total-icon").attr("class","fa fa-chevron-right");
		$(".total-detail").hide("normal");
		return;
	}
}
</script>
    
</head>

<body>
<%@ include file="../../../include/navigation.jsp" %>

<div class="mainindex">
    <form id = "form" action="<s:url action="listAppDetail" />" method="post" name="form">
		<input type="hidden" name="sortedColumnName" id="sortedColumnName"  value="${sortedColumnName }">
		<input type="hidden" name="sortedColumnValue" id="sortedColumnValue"  value="${sortedColumnValue }">
		<input type="hidden" name="adPlatform" id="adPlatform"  >
        <ul class="seachform">
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
                <label>开始时间</label>
                <input  name="startDate" id="startDate" class="Wdate" value="${startDate }" style="width: 150px;"  value="${startDate }"   readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
            </li>
            <li>
                <label>结束时间</label>
                <input  name="endDate" id="endDate" class="Wdate" value="${endDate }" style="width: 150px;" value="${endDate }"  readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
            </li>
            <li>
                <label>&nbsp;</label>
                <input type="submit" class="scbtn btn" value="查询" />
            </li>
            <li>
                <label>&nbsp;</label>
                <input type="button" class="scbtn btn" value="返回" onclick="javascript:history.back(-1)" />
            </li>
        </ul>
        <s:include value="../../../include/buttonTop.jsp"/>
        <table class="tablelist">
            <thead>
            <tr>
                <th style="width: 20%">广告位</th>
                <th>收益<i id="earningsSort" name="sortColumn" class="fa fa-sort"></i></th>
                <th>请求<i id="adRequestsSort" name="sortColumn" class="fa fa-sort"></i></th>
                <th >响应<i id="adResponseSort" name="sortColumn" class="fa fa-sort"></i></th>
                <th>展示次数 (站长)<i id="adImpressionsSort" name="sortColumn" class="fa fa-sort"></i></th>
                <th>点击次数<i id="clicksSort" name="sortColumn" class="fa fa-sort"></i></th>
                <th>填充率</th>
                <th>点击率</th>
                <th>ECPM</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                    <td><span class="fbold">总和</span><a href="javascript:totalIconChange()"  ><i id="total-icon" class="fa fa-chevron-down"></i></a></td>
                    <td><span class="fbold">${adDataTotal.earnings }</span> (${adDataTotal.earningsScale })</td>
                    <td><span class="fbold">${adDataTotal.adRequests }</span></td>
                    <td ><span class="fbold">${adDataTotal.adResponse }</span></td>
                    <td><span class="fbold">${adDataTotal.adImpressions }</span> (${adDataTotal.adImpressionsScale })</td>
                    <td><span class="fbold">${adDataTotal.clicks }</span></td>
                    <td>--</td>
                    <td>--</td>
                    <td>--</td>
                </tr>
            <s:iterator value="siteTotal" var="list" status="st">
                <tr class="total-detail trcolor">
                    <td><span class="fbold">&nbsp;&nbsp;&nbsp;&nbsp;${list.adPlatform } </span></td>
                    <td>${list.earnings } (${list.earningsScale })</td>
                    <td>${list.adRequests }</td>
                    <td >${list.adResponse }</td>
                    <td>${list.adImpressions } (${list.adImpressionsScale })</td>
                    <td>${list.clicks }</td>
                    <td><fmt:formatNumber type="number" value="${list.fillRate * 100 }" pattern="0.0" maxFractionDigits="3"/>%</td>
                    <td><fmt:formatNumber type="number" value="${list.ctr * 100 }" pattern="0.0" maxFractionDigits="3"/>%</td>
                    <td>${list.ecpm }</td>
                </tr>
            </s:iterator>
            <s:iterator value="page.result" var="list" status="st">
                <tr id="${list.adPlatform }-total">
                    <td><span class="fbold">${list.adPlatform } </span><a href="javascript:platformDetail('${list.adPlatform }')"  ><i id="${list.adPlatform }-icon" class="fa fa-chevron-right"></i></a></td>
                    <td>${list.earnings } (${list.earningsScale })</td>
                    <td>${list.adRequests }</td>
                    <td >${list.adResponse }</td>
                    <td>${list.adImpressions } (${list.adImpressionsScale })</td>
                    <td>${list.clicks }</td>
                    <td><fmt:formatNumber type="number" value="${list.fillRate * 100 }" pattern="0.0" maxFractionDigits="3"/>%</td>
                    <td><fmt:formatNumber type="number" value="${list.ctr * 100 }" pattern="0.0" maxFractionDigits="3"/>%</td>
                    <td>${list.ecpm }</td>
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
