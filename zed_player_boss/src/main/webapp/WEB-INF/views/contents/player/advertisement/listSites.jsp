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

function changeSiteName(uid,siteName){
	var url = '<s:url action="updateSiteName.action" />';
	var data ={"uid" : uid,"siteName":siteName};
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


function changeChannel(uid,channel,channelText){
	var url = '<s:url action="updateSiteChannel.action" />';
	var data ={"uid" : uid,"channel":channel};
	$.ajax({
		type: "POST",
        url: url,
        data: data,
        dataType : "json",
        success:function(data){
			if(data&&data.code=='success'){
				$("#channelSelect").parent().attr("channel",channel);
		    	$("#channelSelect").parent().html(channelText);
	       	}else{
				layer.alert("修改失败!");
		    	$("#channelSelect").parent().html(channelText);
			}
	},
    error : function (XMLHttpRequest, textStatus, errorThrown) {
    	$("#channelSelect").parent().html(channelText);
    }
	});
}

$(function(){
		$(document).click(function(){
			var did = event.srcElement.className;
			if(did.indexOf('scaleinput')<0){
				var visibleInput = $("#siteNameInput");
				if(visibleInput){
					var oldval=visibleInput.attr("oldvalue");
					var curval = visibleInput.val();
					if(oldval!=curval){
						changeSiteName(visibleInput.attr("uid"),curval);
					}else{
						 $("#siteNameInput").parent().html(oldval);
					}
				}
			}
			if(did.indexOf('channelSelect')<0){
				var visibleInput = $("#channelSelect");
				if(visibleInput){
					var oldval=visibleInput.attr("oldvalue");
					var oldText=visibleInput.attr("oldText");
					var curval = visibleInput.val();
					var channelText = $('#channelSelect option:selected').text();
					if(oldval!=curval&&curval){
						changeChannel(visibleInput.attr("uid"),curval,channelText);
					}else{
						 $("#channelSelect").parent().html(oldText);
					}
				}
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

function showChangeSiteName(obj){
	var uid = $(obj).attr("uid");
	var oldValue = $.trim($(obj).html());
	$(obj).html("");
	$(obj).append("<input uid='"+uid+"' id ='siteNameInput' oldValue='"+oldValue+"' type='text' style='width:200px;' class='scinput form-control scaleinput' value='"+oldValue+"' >");
}

function showChangeChannel(obj){
	var uid = $(obj).attr("uid");
	var oldValue = $(obj).attr("channel");
	var oldText = $.trim($(obj).html());
	$(obj).html("");
	var channelClone = $("#channel").html();
	$(obj).append("<select uid='"+uid+"' id ='channelSelect' oldValue='"+oldValue+"' oldText='"+oldText+"' style='width:150px;' class='dayinput form-control channelSelect'  ></select>");
	$("#channelSelect").append(channelClone);
}
</script>
    
</head>

<body>
<%@ include file="../../../include/navigation.jsp" %>

<div class="mainindex">
    <form id = "form" action="<s:url action="listSites" />" method="post" name="form">
		<input type="hidden" name="sortedColumnName" id="sortedColumnName"  value="${sortedColumnName }">
		<input type="hidden" name="sortedColumnValue" id="sortedColumnValue"  value="${sortedColumnValue }">
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
                <label>广告位名称</label>
                <input  name="siteName" id="siteName" type="text"  placeholder="查找广告位" class="scinput form-control" value = "${siteName}"/>
            </li>
            <li style="display: none;">
                <label>广告位类型</label>
                <input  name="siteTypeName" id="siteTypeName" type="text"  placeholder="查找广告位" class="scinput form-control" value = "${siteTypeName}"/>
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
                <th style="display: none;">渠道<i id="channelSort" name="sortColumn" class="fa fa-sort"></i></th>
                <th style="width: 30%">站长<i id="appNameSort" name="sortColumn" class="fa fa-sort"></i></th>
                <th style="display: none;">广告位原名<i id="siteTypeSort" name="sortColumn" class="fa fa-sort"></i></th>
                <th>siteID</th>
                <th>广告位</th>
            </tr>
            </thead>
            <tbody>
            <s:iterator value="page.result" var="list" status="st">
                <tr>
                    <td style="display: none;" >${list.channel }</td>
                    <td  ondblclick="showChangeChannel(this)" uid="${list.uid }" channel="${list.channel }" >${list.appName==null?list.channel:list.appName}</td>
                    <td style="display: none;">${list.siteName }</td>
                    <td>${list.siteId }</td>
                    <td ondblclick="showChangeSiteName(this)" uid="${list.uid }" >
                    	${list.siteTypeName }
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
