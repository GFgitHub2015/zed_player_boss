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
     <style type="">
    	.dimensiontext{
    		cursor: pointer;
    	}
    	.flagtext{
    		cursor: pointer;
    	}
    </style>
    <script type="text/javascript">
	function addPage() {
		window.location = '<s:url action="addPage" />';
	}
	function addYouTbPage() {
		window.location = '<s:url action="addYouTbPage" />';
	}
	function showChangeDimension(uid) {
		$("#span-"+uid).hide();
		$("#select-"+uid).show();
	}
	function changeDimension(uid) {
		var url = '<s:url action="updateDimension.action" />';
		var dimensionType = $("#select-"+uid).val();
		var data = {"playerRelationVideo.uid": uid, "playerRelationVideo.dimensionType":dimensionType};
		LoadingPic.FullScreenShow();
		 $.ajax({
	         type: "POST",
	         url: url,
	         data: data,
	         dataType : "json",
	         success:function(data) {
	        	 LoadingPic.FullScreenHide();
	        	 if(data && data.result == 'true') {
	        		$("#span-"+uid).html(dimensionType+"D");
					$(".dimensionselect").hide();
	 				$(".dimensiontext").show();
	        	 } else {
	        		 layer.alert("修改出错!");
	        	 }
	         },
	         error:function (XMLHttpRequest, textStatus, errorThrown) {
	        	 LoadingPic.FullScreenHide();
	    		 layer.alert("修改出错!");
	         }
		 }); 
	}
	function showChangeFlag(uid) {
		$("#spanf-"+uid).hide();
		$("#selectf-"+uid).show();
	}
	function changeFlag(uid) {
		var url = '<s:url action="updateDimension.action" />';
		var flag = $("#selectf-"+uid).val();
		var data = {"playerRelationVideo.uid": uid, "playerRelationVideo.flag":flag};
		LoadingPic.FullScreenShow();
		 $.ajax({
	         type: "POST",
	         url: url,
	         data: data,
	         dataType : "json",
	         success:function(data) {
	        	 LoadingPic.FullScreenHide();
	        	 if(data && data.result == 'true') {
	        		 if(flag == 1) {
	        			 $("#spanf-"+uid).html("<lable style='color: red;'>NEW</lable>");
	        		 } else if(flag == 2) {
	        			 $("#spanf-"+uid).html("<lable style='color: blue;'>HOT</lable>");
	        		 } else {
	        			 $("#spanf-"+uid).html("请选择");
	        		 }
					 $(".flagselect").hide();
	 				 $(".flagtext").show();
	        	 } else {
	        		 layer.alert("修改出错!");
	        	 }
	         },
	         error:function (XMLHttpRequest, textStatus, errorThrown) {
	        	 LoadingPic.FullScreenHide();
	    		 layer.alert("修改出错!");
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
            <li>
                <label>片名:</label>
                <input  name="playerRelationVideo.fileName" type="text"  placeholder="请输入影片名称" class="scinput form-control" value="${playerRelationVideo.fileName}"/>
            </li>
            <li>
                <label>来源:</label>
                <select name="playerRelationVideo.origin"  class="scinput form-control" >
                	<option value="" selected="selected">请选择</option>
                	<option value=0 <c:if test = "${playerRelationVideo.origin == 0}">selected="selected"</c:if>>GBox</option>
                	<option value=1 <c:if test = "${playerRelationVideo.origin == 1}">selected="selected"</c:if>>YouTube</option>
                </select>
            </li>
            <li>
                <label>状态:</label>
                <select  name="playerRelationVideo.status"  class="scinput form-control" >
                	<option  value="" selected="selected">请选择</option>
                	<option  value="1" <c:if test = "${playerRelationVideo.status == 1}">selected="selected"</c:if>>上架</option>
                	<option value="0" <c:if test = "${playerRelationVideo.status == 0}">selected="selected"</c:if>>下架</option>
                </select>
            </li>
            <li>
                <label>模式:</label>
                <select  name="playerRelationVideo.dimensionType"  class="scinput form-control" >
                	<option  value="" selected="selected">请选择</option>
                	<option  value="2" <c:if test = "${playerRelationVideo.dimensionType == 2}">selected="selected"</c:if>>2D</option>
                	<option value="3" <c:if test = "${playerRelationVideo.dimensionType == 3}">selected="selected"</c:if>>3D</option>
                </select>
            </li>
            <li>
                <label>&nbsp;</label>
                <input type="submit" class="scbtn btn" value="查询"/>
                <input type="button" class="btn" value="新增GBox影片" style="width: 120px" onclick="addPage()"/>
                <input type="button" class="btn" value="新增YouTube影片" style="width: 150px" onclick="addYouTbPage()"/>
            </li>
        </ul>
        <s:include value="../../../include/buttonTop.jsp"/>
        <table class="tablelist">
            <thead>
            <tr>
            	<th><input name = "selectCheckbox" id = "selectCheckbox" type="checkbox" value=""/></th>
                <th>海报</th>
                <th>影片名</th>
                <th>状态</th>
                <th>来源</th>
                <th>类型</th>
                <th>最新/最热</th> 
                <th>上下架时间</th>
<!--                 <th>创建人</th> -->
<!--                 <th>创建时间</th>  -->
<!--                 <th>修改人</th> -->
<!--                 <th>修改时间</th>  -->
                <th>国家地区码</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <s:iterator value="page.result" var="list" status="st">
                <tr>
                	<td><input name="checkbox_list" id="checkbox_list" type="checkbox" onclick="selectCheckBoxList()" value="<s:property value="#list.uid" />"/></td>
                    <td><img src="${list.iconUrl}" style="width:35px; height: 35px;"/></td>
                    <td>
                    	<s:if test="#list.fileName.length() > 30">
                    		<span title="<s:property value="#list.fileName"/>"><s:property value="#list.fileName.substring(0, 30)"/></span>
						</s:if>
						<s:else>
							 <s:property value="#list.fileName"/>
						</s:else>
					</td>
					<td>
                   		<s:if test="#list.status == 1"><lable style="color: blue;">上架</lable></s:if>
						<s:else><lable style="color: red;">下架</lable></s:else>
                    </td>
                    <td><s:if test="#list.origin == 0">GBox</s:if><s:elseif test="#list.origin == 1">YouTube</s:elseif></td>
                    <td>
                  	  <s:property value="#list.dimensionType"/>D
                    <td>
                     <s:if test="#list.dimensionType == 2">
                   		<s:if test="#list.flag == 1"><lable style="color: red;">NEW</lable></s:if>
	                    <s:elseif test="#list.flag == 2"><lable style="color: blue;">HOT</lable></s:elseif>  
                     </s:if>
                     <s:else>
                     </s:else>
                    </td>
                    <td>
                    	<fmt:formatDate value="${list.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/> 至
                    	<fmt:formatDate value="${list.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
<%--                     <td><s:property value="#list.createUser"/></td> --%>
<%--                     <td><fmt:formatDate value="${list.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td> --%>
<%--                      <td><s:property value="#list.updateUser"/></td> --%>
<%--                     <td><fmt:formatDate value="${list.updateTime}" pattern="yyyy-MM-dd HH:mm:ss	"/></td> --%>
                    <td>
						<s:iterator value="playerCountryList" var="playerCountry">
							<s:if test="%{#playerCountry.countryCode == #list.countryCode}">
								<s:property value="#playerCountry.countryNameZh"/>（<s:property value="#playerCountry.countryCode"/>）
							</s:if>
						</s:iterator>
					</td>
                    <td>
						<s:iterator value="buttonMenuList" var="buttonRight">
							<s:if test="%{#buttonRight.resourceLevel == 5}">
								<s:if test="%{#buttonRight.resourceKey == 'menu_player_video3_status'}">
									<s:if test="#list.status == 1">
										<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}?playerRelationVideo.status=0&playerRelationVideo.uid=','${list.uid}')"  class="tablelink">下架</a>
									</s:if> 
									<s:else>
										<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}?playerRelationVideo.status=1&playerRelationVideo.uid=','${list.uid}')"  class="tablelink">上架</a>
									</s:else>
								</s:if>
								<s:elseif test="%{#buttonRight.resourceKey == 'menu_player_video_update' and #list.status != 1}">
									<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}?playerRelationVideo.uid=','${list.uid}')" class="tablelink">${buttonRight.resourceName}</a>
								</s:elseif>
								<s:elseif test="%{#buttonRight.resourceKey == 'menu_player_video_delete'}">
									<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}?playerRelationVideo.uid=','${list.uid}')" class="tablelink">${buttonRight.resourceName}</a>
								</s:elseif>
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
