<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../include/resource.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />style.css"/>
    <script type="text/javascript" src="<s:property value="jsPath" />jquery.js"></script>
</head>
<body>
<%@ include file="../../../include/navigation.jsp" %>
<form  method="post" name="form" id="form" action="<s:url action="list"/>">
    <div class="formbody">
        <div class="formtitle"><span>闪屏信息</span></div>
        <ul class="forminfo">
            <li><label>闪屏图片</label><label class="height32" style= "width:200px;">
                <img src="${playerScreen.imageUrl}" width="250px" height="350px" />
            </label>
            </li>
            <li><label>状态</label>
                <cite>
                    <c:set var="nowDate" value="<%=System.currentTimeMillis()%>"></c:set>
                    <c:choose>
                        <c:when test="${nowDate-playerScreen.startTime.getTime()<0}">
                            即将开始
                        </c:when>
                        <c:when test="${nowDate-playerScreen.endTime.getTime()>0}">
                            已结束
                        </c:when>
                        <c:otherwise>
                            进行时
                        </c:otherwise>
                    </c:choose>
                </cite>
            </li>
            <li>
                <label>是否启用</label>
                <cite>
                    <c:choose>
                        <c:when test="${playerScreen.status == '1'}">
                            启用
                        </c:when>
                        <c:when test="${playerScreen.status == '0'}">
                            禁用
                        </c:when>
                        <c:otherwise>
                        </c:otherwise>
                    </c:choose>
                </cite>
            </li>
            <li><label>链接</label><label class="height32" style= "width:200px;">${playerScreen.link}</label></li>
            <li>
                <label>链接类型</label>
                <label class="height32" style= "width:200px;">
                    <c:choose>
                        <c:when test="${playerScreen.linkType == 1}">
                            活动
                        </c:when>
                        <c:when test="${playerScreen.linkType ==2}">
                            电影
                        </c:when>
                        <c:otherwise>

                        </c:otherwise>
                    </c:choose>
                </label>
            </li>
            <li><label>开始时间</label><label class="height32" style= "width:200px;"><fmt:formatDate value="${playerScreen.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/> </label></li>
            <li><label>结束时间</label><label class="height32" style= "width:200px;"><fmt:formatDate value="${playerScreen.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/> </label></li>
            <li>
                <input name="" type="button" class="btn" value="返回" onclick="javascript:history.go(-1)"/>
            </li>
        </ul>


    </div>
</form>
</body>
</html>
