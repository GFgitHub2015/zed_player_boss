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
    
<script type="text/javascript">
function openEdit(statDate,activeCount){
	$(".labelText").show();
	$(".labelInput").hide();
	$("#"+statDate+"-label").hide();
	$("#"+statDate+"-label-input").show();
	$("#"+statDate+"-label-input").focus();
}
function closeEdit(statDate,uid){
	var url = '<s:url action="updateSpaceActiveUserStat.action" />';
	var activeCount = $("#"+statDate+"-input").val();
	if(!activeCount){
		activeCount=0;
	}
	var data = {"activeCount": activeCount,"uid":uid};
	 $.ajax({
         type: "POST",
         url: url,
         data: data,
         dataType : "json",
         success:function(data){
        	 if(data&&data.result==true){
     			$("#"+statDate+"-label").html(activeCount);
        		$("#"+statDate+"-label").show();
        		$("#"+statDate+"-label-input").hide();
        	 }
         },
         error : function (XMLHttpRequest, textStatus, errorThrown) {
        	 //alert(textStatus+"=="+errorThrown);
         }
	 }); 
}

function addPage(){
var url = '<s:url action="addSpaceActiveUserStatPage" />?channel=${channel }' ;
window.location=url;
}

function backPage(){
var url = '<s:url action="masterStatList" />' ;
window.location=url;
}


$(function(){
	$(".col_adgActiveCount").editable(function(value, settings){
		if ($(this).attr("befor-adgActiveCount") == value)return value;//未改动
		
		var th_el = $(this);
		var url = '<s:url action="updateSpaceAdgActiveUserStat.action" />';
		var data = {"adgActiveCount": parseInt(value),"uid":$(this).attr('uid')};
		console.log(value,$(this).attr('uid'));
		 $.ajax({
	         async: false,
			 type: "POST",
	         url: url,
	         data: data,
	         dataType : "json",
	         success:function(data){
	        	 $(th_el).attr("befor-adgActiveCount",value);
	         },
	         error : function (XMLHttpRequest, textStatus, errorThrown) {
	         }
		 }); 
		 
		 result = $(th_el).attr("befor-adgActiveCount");
		 return result;
	},
	{
	    type:'text',//实时编辑input框的type
	    onblur:'submit'
	});
});

</script>
    
</head>

<body>
<%@ include file="../../../include/navigation.jsp" %>

<div class="mainindex">
    <form id = "form" action="<s:url action="singleMasterStatList" />" method="post" name="form">
    	<input type="hidden" name="channel" value="${channel }"/>
    	<div class="formtitle"><span>${appName } &nbsp;数据列表</span></div>
        <ul class="seachform">
            <li>
                <label>开始时间</label>
                <input  name="startDate" type="text"   class="Wdate" readonly="readonly" value = "${startDate}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
            </li>
            <li>
                <label>结束时间</label>
                <input  name="endDate" type="text"   class="Wdate"  readonly="readonly" value = "${endDate}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
            </li>
            <li>
                <label>&nbsp;</label>
                <input type="submit" class="scbtn btn" value="查询"  />
                <label>&nbsp;</label>
                <input type="button" class="scbtn btn" value="新增"  onclick="addPage()"/>
                <label>&nbsp;</label>
                <input type="button" class="scbtn btn" value="返回" onclick="backPage()" />
            </li>
        </ul>
        <s:include value="../../../include/buttonTop.jsp"/>
        <table class="tablelist">
            <thead>
            <tr>
                <th style="width: 50%">日期</th>
                <th>日增活跃</th>
                <th>活跃</th>
            </tr>
            </thead>
            <tbody>
            <s:iterator value="page.result" var="list" status="st">
                <tr>
                    <td>${list.statDate }</td>
                    <td class="col_adgActiveCount" uid="${list.uid }" befor-adgActiveCount="${list.adgActiveCount }" >${list.adgActiveCount }</td>
                    <td onclick="openEdit('${list.statDate }','${list.activeCount }')" style="cursor: pointer;">
	                    <label class="labelText" id="${list.statDate}-label">${list.activeCount }</label>
	                    <label class="labelInput" style="display: none;" id="${list.statDate}-label-input">
	                    	<input  type="number" value="${list.activeCount }" id="${list.statDate }-input" onblur="closeEdit('${list.statDate }','${list.uid }')"/>
	                    </label>
                    </td>
                </tr>
            </s:iterator>
            </tbody>
        </table>
		<%@ include file="../../../include/buttonRight.jsp" %>
        <%@ include file="../../../include/page.jsp" %>
    </form>

    <script type="text/javascript" src="<s:property value="jsPath" />jquery.jeditable.js"></script>
</div>

</body>
</html>
