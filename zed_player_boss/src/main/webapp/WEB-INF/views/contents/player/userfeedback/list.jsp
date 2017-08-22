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

<script type="text/javascript">
$(function(){
	//更新排序图标
	var moviesCountSort = '${moviesCountSort}';
	if(moviesCountSort=='asc'){
		$("#moviesCountSort").attr("class","fa fa-sort-up");
	}else if(moviesCountSort=='desc'){
		$("#moviesCountSort").attr("class","fa fa-sort-down");
	}
});
//影片数量排序
function toggleMoviesCountSort(){
	if ($("#moviesCountSort").attr("class")=="fa fa-sort"){
		$("#moviesCountSort").attr("class","fa fa-sort-down");
		$("#moviesCountSortBtn").val("desc");
	  } else if ($("#moviesCountSort").attr("class")=="fa fa-sort-down") {
		$("#moviesCountSort").attr("class","fa fa-sort-up");
		$("#moviesCountSortBtn").val("asc");
	  }else{
		$("#moviesCountSort").attr("class","fa fa-sort");
		$("#moviesCountSortBtn").val("");
	  }
	document.form.submit();
}
</script>
</head>

<body>
<%@ include file="../../../include/navigation.jsp" %>

<div class="mainindex">
    <form id = "form" action="<s:url action="list" />" method="post" name="form">
    	<input  name="moviesCountSort" id="moviesCountSortBtn" type="hidden"  value = "${moviesCountSort}"/>
        <ul class="seachform">
            <li>
                <label>姓名</label>
                <input  name="uname" type="text"  placeholder="请输入姓名" class="scinput form-control" value = "${uname}"/>
            </li>
            <li>
                <label>国家</label>
                <select  name="country"  class="scinput form-control" >
                	<option  value="" >请选择</option>
                	<option  value="India" <c:if test = "${country == 'India'}">selected="selected"</c:if>>India</option>
                	<option  value="Thailand" <c:if test = "${country == 'Thailand'}">selected="selected"</c:if>>Thailand</option>
                	<option value="Malaysia" <c:if test = "${country == 'Malaysia'}">selected="selected"</c:if>>Malaysia</option>
                	<option value="Indonesia" <c:if test = "${country == 'Indonesia'}">selected="selected"</c:if>>Indonesia</option>
                	<option value="Others" <c:if test = "${country == 'Others'}">selected="selected"</c:if>>Others</option>
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
                <th>姓名</th>
                <th>国家</th>
                <th>联系方式</th>
                <th>视频数量<a href="#" onclick="toggleMoviesCountSort()"><i id="moviesCountSort" class="fa fa-sort"></i></a></th>
                <th>提交时间</th>
            </tr>
            </thead>
            <tbody>
            <s:iterator value="page.result" var="list" status="st">
                <tr>
                    <td>${list.uname }</td>
                    <td>${list.country }</td>
                    <td>${list.phone }</td>
                    <td>${list.moviesCount }</td>
                    <td><fmt:formatDate value="${list.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
