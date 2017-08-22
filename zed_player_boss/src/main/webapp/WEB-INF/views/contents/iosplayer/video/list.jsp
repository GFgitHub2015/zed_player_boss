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
function addPage(){
	var url = '<s:url action="addPage" />' ;
	window.location=url;
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

</script>
    
</head>

<body>
<%@ include file="../../../include/navigation.jsp" %>

<div class="mainindex">
    <form id = "form" action="<s:url action="list" />" method="post" name="form">
		<input type="hidden" name="sortedColumnName" id="sortedColumnName"  value="${sortedColumnName }">
		<input type="hidden" name="sortedColumnValue" id="sortedColumnValue"  value="${sortedColumnValue }">
        <ul class="seachform">
            <li>
                <label>影片Url</label>
                <input  name="videoUrl" type="text"  placeholder="请输入影片Url" class="scinput form-control" value = "${videoUrl}"/>
            </li>
            <li>
                <label>片名</label>
                <input  name="videoName" type="text"  placeholder="请输入影片名称" class="scinput form-control" value = "${videoName}"/>
            </li>
            <li>
                <label>状态</label>
                <select name="videoState"  class="scinput form-control" >
                	<option value="">请选择</option>
                	<option value="1" <c:if test = "${videoState == '1'}">selected="selected"</c:if>>有效</option>
                	<option value="-1" <c:if test = "${videoState == '-1'}">selected="selected"</c:if>>无效</option>
                </select>
            </li>
            <li>
                <label>&nbsp;</label>
                <input type="submit" class="scbtn btn" value="查询"  />
                <input type="button" class="scbtn btn" value="新增推荐"  onclick="addPage()"/>
            </li>
        </ul>
        <s:include value="../../../include/buttonTop.jsp"/>
        <table class="tablelist">
            <thead>
            <tr>
                <th>海报</th>
                <th>视频链接</th>
                <th>视频标题</th>
                <th>添加日期</th>
                <th>创建人</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <s:iterator value="page.result" var="list" status="st">
                <tr>
                    <td><img src="${list.iconUrl}" style="width:35px; height: 35px;"/></td>
                    <td>${list.videoUrl }</td>
                    <td title="${list.videoName }">${fn:substring(list.videoName ,0,40 )}..</td>
                    <td><fmt:formatDate value="${list.startTime}" pattern="yyyy-MM-dd HH:mm"/> </td>
                    <td>${list.createUser }</td>
                    <td>
						<c:choose>
							<c:when test="${list.videoState == '1'}">
								<lable style="color: blue;">有效</lable>
							</c:when>
							<c:when test="${list.videoState == '-1'}">
								<lable style="color: red;">无效</lable>
							</c:when>
							<c:otherwise>
								
							</c:otherwise>
						</c:choose>
					</td>
                    <td>
						<s:iterator value="buttonMenuList" var="buttonRight">
							<s:if test="%{#buttonRight.resourceLevel == 5}">
								<s:if test="%{#buttonRight.resourceKey == 'menu_ios_player_hotvideo_state'}">
									<s:if test="#list.videoState == 1">
										<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}?videoState=-1&uid=','${list.uid}')"  class="tablelink">下架</a>
									</s:if>
									<s:elseif test="#list.videoState == -1||#list.videoState == 0">
										<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}?videoState=1&uid=','${list.uid}')"  class="tablelink">上架</a>
									</s:elseif>
								</s:if>
								<s:else>
										<a  href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}','${list.uid}')"   class="tablelink">${buttonRight.resourceName}</a>
								</s:else>
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
