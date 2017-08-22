<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../include/resource.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <!--common-->
    <link href="<s:property value="themePath" />css/style.css" rel="stylesheet">
    <link href="<s:property value="themePath" />css/style-responsive.css" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />style.css"/>
    <script type="text/javascript" src="<s:property value="jsPath" />jquery.js"></script>
    <script type="text/javascript" src="<s:property value="jsPath" />window.js"></script>
    <script type="text/javascript" src="<s:property value="jsPath" />function.js"></script>
</head>

<body>
<%@ include file="../../../include/navigation.jsp" %>

<div class="mainindex">
    <form id = "form" action="<s:url action="userTalk" />" method="post" name="form">
        <ul class="seachform">
            <li>
                <label>反馈内容</label>
                <input name="playerUserTalk.text" type="text" class="scinput form-control" maxlength="50" value = "${playerUserTalk.text}"/>
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
                <th><input name = "selectCheckbox" id = "selectCheckbox" type="checkbox" value=""/></th>
                <th width="10%">用户编号</th>
                <th width="20%">用户昵称</th>
                <th width="30%">反馈内容</th>
                <th width="15%">联系方式</th>
                <th width="20%">反馈时间</th>
            </tr>
            </thead>
            <tbody>
            <s:iterator value="page.result" var="list">
                <tr>
                    <td><input name="checkbox_list" id="checkbox_list" type="checkbox" onclick="selectCheckBoxList()" value="<s:property value="#list.resource_id" />"/></td>
                    <td><s:property value="#list.accountId" /></td>
                    <td><s:property value="#list.nickName" /></td>
                    <td><s:property value="#list.text" /></td>
                    <td><s:property value="#list.contact" /></td>
                    <td><fmt:formatDate value="${list.createTime}" type="both"/></td>
                </tr>
            </s:iterator>
            </tbody>
        </table>
        <%@ include file="../../../include/page.jsp" %>
    </form>

</div>

</body>
</html>
