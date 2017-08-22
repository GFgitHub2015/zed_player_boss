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

function changeDataType(obj){
	var url = '<s:url action="listDate.action" />';
	var dtype = $(obj).val();
	if(dtype==2){
		$("#form").attr("action",url);
		document.form.submit();
	}
}
function changechannel(){
	var channel = $("#appInfo").val();
	$("#channel").val(channel);
}
</script>
    
</head>

<body>
<%@ include file="../../../include/navigation.jsp" %>

<div class="mainindex">
    <form id = "form" action="<s:url action="list" />" method="post" name="form">
		<input type="hidden" name="sortedColumnName" id="sortedColumnName"  value="${sortedColumnName }">
		<input type="hidden" name="sortedColumnValue" id="sortedColumnValue"  value="${sortedColumnValue }">
		<input type="hidden" name="channel" id="channel"   value="${channel}"/> 
        <ul class="seachform">
            <li>
                <label>搜索方式</label>
                <select class="dayinput form-control" onchange="changeDataType(this)" style="width: 80px;">
                	<option value="1">影片</option>
                	<option value="2">日期</option>
                </select>
            </li>
            <li>
                <label>开始时间</label>
                <input  name="startDate" id="startDate" class="Wdate" value="${startDate }" style="width: 100px;"   readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
            </li>
            <li>
                <label>结束时间</label>
                <input  name="endDate" id="endDate" class="Wdate" value="${endDate }" style="width: 100px;"  readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
            </li>
            <li>
                <label>影片ID</label>
                <input  name="fileId" type="text"  placeholder="请输入影片ID" class="scinput form-control" value = "${fileId}"/>
            </li>
            <li>
                <label>片名</label>
                <input  name="fileName" type="text"  placeholder="请输入影片名称" class="scinput form-control" value = "${fileName}"/>
            </li>
            <li>
                <label>渠道</label>
                <select  name="appInfo" id="appInfo"  class="dayinput form-control" style="width: 150px;"  onchange="changechannel()">
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
            </li>
        </ul>
        <s:include value="../../../include/buttonTop.jsp"/>
        <table class="tablelist">
            <thead>
            <tr>
                <th>影片ID</th>
                <th>片名</th>
                <th style="display: none;">渠道</th>
                <th>应用名</th>
                <th>浏览量<i id="pvSort" name="sortColumn" class="fa fa-sort"></i></th>
                <th>独立访客<i id="uvSort" name="sortColumn" class="fa fa-sort"></i></th>
                <th>下载点击次数<i id="downSort" name="sortColumn" class="fa fa-sort"></i></th>
                <th>分享次数<i id="shareCountSort" name="sortColumn" class="fa fa-sort"></i></th>
            </tr>
            </thead>
            <tbody>
            <s:iterator value="page.result" var="list" status="st">
                <tr>
                    <td>${list.fileId }</td>
                    <c:choose>
                    	<c:when test="${fn:length((list.fileName)) >50}">
                    		<td title="${list.fileName }">${fn:substring(list.fileName ,0,40 )}..</td>
                    	</c:when>
                    	<c:otherwise>
                    		<td >${list.fileName }</td>
                    	</c:otherwise>
                    </c:choose>
                    <td style="display: none;">${list.channel }</td>
                    <td>${list.appname==null?list.channel:list.appname }</td>
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
