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
		    	<select name="channel" id="channel"  class="dayinput form-control" style="width: 150px;">
					<option value="">请选择..</option>
		    		<c:forEach var="appinfo" items="${appInfos }">
							<option value="${appinfo.channel }"  <c:if test="${channel==appinfo.channel }">selected="selected"</c:if> >${appinfo.appname }</option>
					</c:forEach>
					<option value="">其它</option>
		    	</select>
            </li>
		    <li style="display: none;" id="otherChannel">
		    	<label>其它渠道</label>
		    	<input name="countryCode" id="countryCode" type="text" placeholder="请输入渠道"  class="scinput form-control"  value="${countryCode}"/> 
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
                <input type="submit" class="scbtn btn" value="查询" onclick="initPageNo()"/>
                <input type="button" class="scbtn btn" value="新增"  onclick="addPage()"/>
            </li>
        </ul>
        <s:include value="../../../include/buttonTop.jsp"/>
        <table class="tablelist">
            <thead>
            <tr>
                <th >渠道</th>
                <th >创建人</th>
                <th >日期</th>
                <th>类型</th>
                <th>金额</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <s:iterator value="page.result" var="list" status="st">
                <tr>
                    <td>${list.appname }</td>
                    <td>${list.createUser }</td>
                    <td><fmt:formatDate value="${list.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td >
						<c:if test="${list.iconUrl!=null&&list.iconUrl!='' }">
				 			<img width="30" height="30" alt="${list.iconTitle }"  title="${list.iconTitle }" src="${publicAttr.imagePath }${list.iconUrl}">
				 		</c:if>
				 		${list.sourceTypeName }
                    </td>
                    <td>
                    	<c:if test="${list.paymentIdExists!=null&&list.paymentIdExists!='' }">
				 			${list.earnings }
				 		</c:if>
						<c:if test="${list.paymentIdExists==null||list.paymentIdExists=='' }">
				 			<font color="red">${list.earnings }</font>
				 		</c:if>
                    </td>
                    
					<td>													
						<s:iterator value="buttonMenuList" var="buttonRight">
							<s:if test="%{#buttonRight.resourceLevel == 5}">
								<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}','${list.id}')"  class="tablelink">${buttonRight.resourceName}</a>
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
