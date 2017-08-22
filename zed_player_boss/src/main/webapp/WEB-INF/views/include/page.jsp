<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<script>
$(document).ready(function(e) {
	$("[id='page.pageNo']").bind('keypress',function(event){ 
        if(event.keyCode == 13){
       		jumpPage($("[id='page.pageNo']").val());
        }  
    });
});
function jumpPage(pageNo) {
	$("[id='page.pageNo']").val(pageNo);
	$("#form").submit();
}

</script>
<c:if test="${page.totalCount > 0}">
	<c:set var="offset" value="4"/>
	<c:set var="begin" value="${page.pageNo-offset}"/>
	<c:set var="end" value="${page.pageNo+offset}"/>
	<c:if test="${page.pageNo-offset<=0}">
		<c:set var="end" value="${end+offset-page.pageNo+1}"/>
	</c:if>
	<c:if test="${end>=page.totalPage}">
		<c:set var="beginOffset" value="${end-page.totalPage}"/>
		<c:set var="begin" value="${begin-beginOffset}"/>
		<c:set var="end" value="${page.totalPage}"/>
	</c:if>
	<c:if test="${begin<1}"><c:set var="begin" value="1"/></c:if>
	
	<div class="pagin">
		<div class="message">
			<div class="all-count">共<i class="blue">${page.totalCount}</i>条记录&nbsp; </div>
		  	<i class="blue input-num" style="text-align:center;">第&nbsp;<input name="page.pageNo" id="page.pageNo" value="${page.pageNo}" type="text" style="border:1px solid #DDD;text-align:center;width:30px;"/>&nbsp;页/共<i class="blue">${page.totalPage}</i>页 </i>
		</div>
		<ul class="paginList">
			<li class="paginItem"><a href="javascript:jumpPage(${page.prePage});"><i class="fa fa-angle-left"></i> </a></li>
			<c:forEach begin="${begin}" end="${end}" var="pno">
				<c:if test="${page.pageNo==pno}"><li class="paginItem current"><a href="javascript:jumpPage(${pno});">${pno}</a></li></c:if>
				<c:if test="${page.pageNo!=pno}"><li class="paginItem"><a href="javascript:jumpPage(${pno})">${pno}</a></li></c:if>
			</c:forEach>
			<li class="paginItem"><li class="paginItem"><a href="javascript:jumpPage(${page.nextPage});"><i class="fa fa-angle-right"></i> </a></li>
		</ul>
	</div>
</c:if>
<c:if test="${page.totalCount == 0}"><div style = "height: 28px;line-height: 28px;">暂无记录</div></c:if>
