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
</head>

<body>
<%@ include file="../../../include/navigation.jsp" %>

<div class="mainindex">
    <form id = "form" action="<s:url action="list" />" method="post" name="form">
        <ul class="seachform">
            <li>
                <label>开始时间</label>
                <input  name="params.startDate" class="Wdate" value="${params.startDate}" style="width: 150px;"  readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
            </li>
            <li>
                <label>结束时间</label>
                <input  name="params.endDate" class="Wdate" value="${params.endDate}" style="width: 150px;"  readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
            </li>
            <li>
                <label>获奖凭证号</label>
                <input  name="params.keywords" type="text" class="scinput form-control" value = "${params.keywords}"/>
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
                <th>序号</th>
                <th>联系方式</th>
                <th>email</th>
                <th>获奖凭证号</th>
                <th>对接人</th>
                <th>问题1</th>
                <th>问题2</th>
                <th>问题3</th>
                <th>问题4</th>
                <th>问题5</th>
                <th>问题6</th>
                <th>创建时间</th>
            </tr>
            </thead>
            <tbody>
            <s:iterator value="page.result" var="list" status="st">
                <tr>
                    <td><s:property value="#st.index + 1"/> </td>
                    <td><s:property value="#list.contact"/> </td>
                    <td><s:property value="#list.email" /></td>
                    <td><s:property value="#list.voucherNo" /></td>
                    <td><s:property value="#list.manager" /></td>
                    <td>
                        <s:iterator value="#list.answerList" var="answer">
                            <s:if test='#answer.index == "1"'>
                                <s:property value="#answer.value" />
                            </s:if>
                        </s:iterator>
                    </td>
                    <td>
                        <s:iterator value="#list.answerList" var="answer">
                        <s:if test='#answer.index == "2"'>
                            <s:property value="#answer.value" />
                        </s:if>
                    </s:iterator>
                    </td>
                    <td>
                        <s:iterator value="#list.answerList" var="answer">
                        <s:if test='#answer.index == "3"'>
                            <s:property value="#answer.value" />
                        </s:if>
                    </s:iterator>
                    </td>
                    <td>
                        <s:iterator value="#list.answerList" var="answer">
                            <s:if test='#answer.index == "4"'>
                                <s:property value="#answer.value" />
                            </s:if>
                        </s:iterator>
                    </td>
                    <td>
                        <s:iterator value="#list.answerList" var="answer">
                            <s:if test='#answer.index == "5"'>
                                <s:property value="#answer.value" />
                            </s:if>
                        </s:iterator>
                    </td>
                    <td>
                        <s:iterator value="#list.answerList" var="answer">
                            <s:if test='#answer.index == "6"'>
                                <s:property value="#answer.value" />
                            </s:if>
                        </s:iterator>
                    </td>
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
