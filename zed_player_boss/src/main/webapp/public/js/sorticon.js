
//加载完成时,改变图标状态
$(function(){
	/**
	 * 给i标签的name为sortColumn的添加图标切换功能
	 */
	$('i[name="sortColumn"]').each(function(i){
		   $(this).css("cursor","pointer");
	});
	$('i[name="sortColumn"]').click(function () {
		var cls = $(this).attr("class");
		$('i[name="sortColumn"]').attr("class","fa fa-sort");
		$('i[name="sortColumn"]').attr("orderby","");//排序清空
		$('i[name="sortColumn"]').attr("sorted","");//选中清空
		$(this).attr("sorted","true");//选中
		if (cls=="fa fa-sort"){
			$(this).attr("class","fa fa-sort-down");
			$(this).attr("orderby","desc");
		} else if (cls=="fa fa-sort-down") {
			$(this).attr("class","fa fa-sort-up");
			$(this).attr("orderby","asc");
		}else{
			$(this).attr("class","fa fa-sort");
			$(this).attr("orderby","");
		}
		//点击完之后执行方法
		if(typeof changeSorted === "function"){
			changeSorted();
		}
	});
	var sortedColumnName = $("#sortedColumnName").val();
	var sortedColumnValue = $("#sortedColumnValue").val();
	if(sortedColumnName&&sortedColumnValue){
		$("#"+sortedColumnName).attr("sorted","true");//选中
		if(sortedColumnValue=="asc"){
			$("#"+sortedColumnName).attr("class","fa fa-sort-up");
			$("#"+sortedColumnName).attr("orderby","asc");
		}else if (sortedColumnValue=="desc"){
			$("#"+sortedColumnName).attr("class","fa fa-sort-down");
			$("#"+sortedColumnName).attr("orderby","desc");
		}
	}
});