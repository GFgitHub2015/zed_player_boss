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
    
<script type="text/javascript">
function addPage(){
var url = '<s:url action="addPage" />' ;
window.location=url;
}

function changechannel(){
	var channel = $("#appInfo").val();
	if(!channel){
		$("#otherChannel").show();
	}else{
		$("#otherChannel").hide();
	}
	$("#countryCode").val(channel);
}

function createUrl(){
	var url = '<s:url action="createBannerDefaultUrl.action" />';
	var countryCode = $("#countryCode").val();
	if(!countryCode){
		layer.alert("请选择渠道!");
		return;
	}
	var data = {"countryCode":countryCode};
	 $.ajax({
         type: "POST",
         url: url,
         data: data,
         dataType : "json",
         success:function(data){
			if(data&&data.data){
				var result = data.data;
				layer.alert(result.url);
        		 return;
        	 }
         },
         error : function (XMLHttpRequest, textStatus, errorThrown) {
        	 //alert(textStatus+"=="+errorThrown);
         }
	 }); 
}
</script>
    
</head>

<body>
<%@ include file="../../../include/navigation.jsp" %>

<div class="mainindex">
    <form id = "form" action="<s:url action="list" />" method="post" name="form">
        <ul class="seachform">
            <li style="display: none;">
                <label>参数类别</label>
                <input  name="paramType" type="text"  placeholder="请输入参数类别" class="scinput form-control" value = "${paramType}"/>
            </li>
            <li>
                <label>渠道</label>
		    	<select name="appInfo" id="appInfo" onchange="changechannel()"  class="dayinput form-control" style="width: 150px;">
					<option value="">请选择..</option>
		    		<c:forEach var="appinfo" items="${appInfos }">
							<option value="${appinfo.channel }" >${appinfo.appname }</option>
					</c:forEach>
					<option value="">其它</option>
		    	</select>
            </li>
		    <li style="display: none;" id="otherChannel">
		    	<label>其它渠道</label>
		    	<input name="countryCode" id="countryCode" type="text" placeholder="请输入渠道"  class="scinput form-control"  value="${countryCode}"/> 
		    </li>
            <li>
                <label>参数名称</label>
                <input  name="paramName" type="text"  placeholder="请输入参数名称" class="scinput form-control" value = "${paramName}"/>
            </li>
            <li>
                <label>&nbsp;</label>
                <input type="submit" class="scbtn btn" value="查询" onclick="initPageNo()"/>
                <input type="button" class="scbtn btn" value="新增"  onclick="addPage()"/>
            </li>
            <li style="float: right;">
                <label>&nbsp;</label>
                <input type="button" class="scbtn btn" style="width: 200px;" value="生成banner下载页"  onclick="createUrl()"/>
            </li>
        </ul>
        <s:include value="../../../include/buttonTop.jsp"/>
        <table class="tablelist">
            <thead>
            <tr>
                <th style="display: none;" >参数类别</th>
                <th>参数名称</th>
                <th>描述</th>
                <th>参数值</th>
                <th >渠道</th>
                <th >应用名</th>
                <th>修改人</th>
                <th>修改时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <s:iterator value="page.result" var="list" status="st">
                <tr>
                    <td style="display: none;" >${list.paramType }</td>
                    <td>${list.paramName }</td>
                    <td>${list.paramDec }</td>
                    <td title="<s:property value="#list.paramValue"/>">
						<s:if test="%{#list.paramValue.length()>20}">
							<s:property value="#list.paramValue.substring(0,20)+'...'" />
						</s:if>
						<s:else>
							<s:property value="#list.paramValue"/>
						</s:else>
                    </td>
                    <td>${list.countryCode }</td>
                    <td>${list.appname }</td>
                    <td>${list.updateUser }</td>
                    <td><fmt:formatDate value="${list.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td>
						<s:iterator value="buttonMenuList" var="buttonRight">
							<s:if test="%{#buttonRight.resourceLevel == 5}">
								<a  href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}','${list.paramId}')"   class="tablelink">${buttonRight.resourceName}</a>
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
