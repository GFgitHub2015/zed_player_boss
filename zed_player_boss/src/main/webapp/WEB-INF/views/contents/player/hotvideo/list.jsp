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
function addPage(){
var url = '<s:url action="addPage" />' ;
window.location=url;
}

$(function(){
	//更新排序图标
	var playSort = '${playSort}';
	var downSort = '${downSort}';
	if(playSort=='asc'){
		$("#playSort").attr("class","fa fa-sort-up");
	}else if(playSort=='desc'){
		$("#playSort").attr("class","fa fa-sort-down");
	}
	if(downSort=='asc'){
		$("#downSort").attr("class","fa fa-sort-up");
	}else if(downSort=='desc'){
		$("#downSort").attr("class","fa fa-sort-down");
	}
});
//播放次数排序
function togglePlaySort(){
	if ($("#playSort").attr("class")=="fa fa-sort"){
		$("#playSort").attr("class","fa fa-sort-down");
		$("#playSortBtn").val("desc");
	  } else if ($("#playSort").attr("class")=="fa fa-sort-down") {
		$("#playSort").attr("class","fa fa-sort-up");
		$("#playSortBtn").val("asc");
	  }else{
		$("#playSort").attr("class","fa fa-sort");
		$("#playSortBtn").val("");
	  }
	document.form.submit();
}
//下载次数排序
function toggleDownSort(){
	if ($("#downSort").attr("class")=="fa fa-sort"){
		$("#downSort").attr("class","fa fa-sort-down");
		$("#downSortBtn").val("desc");
	  } else if ($("#downSort").attr("class")=="fa fa-sort-down") {
		$("#downSort").attr("class","fa fa-sort-up");
		$("#downSortBtn").val("asc");
	  }else{
		$("#downSort").attr("class","fa fa-sort");
		$("#downSortBtn").val("");
	  }
	document.form.submit();
}

function goSubtitlePage(fileId) {
	window.location = '<s:url action="subtitlePage" />?fileId=' + fileId;
}
</script>
    
</head>

<body>
<%@ include file="../../../include/navigation.jsp" %>

<div class="mainindex">
    <form id = "form" action="<s:url action="list" />" method="post" name="form">
    	<input  name="playSort" id="playSortBtn" type="hidden"  value = "${playSort}"/>
    	<input  name="downSort" id="downSortBtn"  type="hidden"  value = "${downSort}"/>
        <ul class="seachform">
            <li>
                <label>片名</label>
                <input  name="videoName" type="text"  placeholder="请输入影片名称" class="scinput form-control" value = "${videoName}"/>
            </li>
            <li>
                <label>状态</label>
                <select  name="videoState"  class="scinput form-control" >
                	<option value="" >请选择</option>
                	<option value="1" <c:if test = "${videoState == '1'}">selected="selected"</c:if>>已上架</option>
                	<option value="0" <c:if test = "${videoState == '0'}">selected="selected"</c:if>>待上架</option>
                	<option value="-1" <c:if test = "${videoState == '-1'}">selected="selected"</c:if>>已下架</option>
                </select>
            </li>
            <li><label>国家码</label>
				<select name="areaCode" id="areaCode" class="dayinput form-control" style="width:500px;">
						<option value="" <c:if test = "${areaCode == ''}">selected="selected"</c:if>>请选择</option>
						<c:if test="${not empty playerCountryList}">
							<s:iterator value="playerCountryList" var="playerCountry">
								<option value="<s:property value="#playerCountry.countryCode"/>" <s:if test='%{#request.areaCode == #playerCountry.countryCode}'>selected="selected"</s:if>>
									<s:property value="#playerCountry.countryNameZh"/>（<s:property value="#playerCountry.countryCode"/>）
								</option>
							</s:iterator>
						</c:if>
				</select>
			</li>
            <li>
                <label>&nbsp;</label>
                <input type="submit" class="scbtn btn" value="查询" onclick="initPageNo()"/>
                <input type="button" class="scbtn btn" value="新增推荐"  onclick="addPage()"/>
            </li>
        </ul>
        <s:include value="../../../include/buttonTop.jsp"/>
        <h5>共${hotVideoCount}视频/${subtitleCount}字幕</h5>
        <table class="tablelist">
            <thead>
            <tr>
            	<th><input name = "selectCheckbox" id = "selectCheckbox" type="checkbox" value=""/></th>
                <th>海报</th>
                <th>片名</th>
                <th style="display: none;">影片ID</th>
                <th>上下架时间</th>
                <th>国家</th>
                <th>最新/最热</th>
                <th>播放<a href="#" onclick="togglePlaySort()"><i id="playSort" class="fa fa-sort"></i></a></th>
                <th>下载<a href="#" onclick="toggleDownSort()"><i id="downSort" class="fa fa-sort"></i></a></th>
                <th>字幕数/已下载</th>
                <th style="display: none;">推荐理由</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <s:iterator value="page.result" var="list" status="st">
                <tr>
                	<td><input name="checkbox_list" id="checkbox_list" type="checkbox" onclick="selectCheckBoxList()" value="<s:property value="#list.uid" />"/></td>
                    <td><a href="<s:url action="detail" />?uid=${list.uid}"><img src="${list.iconUrl}" style="width:35px; height: 35px;"/></a></td>
                    <td>${list.videoName }</td>
                    <td  style="display: none;">${list.fileId }</td>
                    <td><fmt:formatDate value="${list.startTime}" pattern="yyyy-MM-dd HH:mm"/> 至
                    <fmt:formatDate value="${list.endTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                    <td>
						<s:iterator value="playerCountryList" var="playerCountry">
							<s:if test="%{#playerCountry.countryCode == #list.countryCode}">
								<s:property value="#playerCountry.countryNameZh"/>（<s:property value="#playerCountry.countryCode"/>）
							</s:if>
						</s:iterator>
					</td>
                    <td>
						<c:choose>
							<c:when test="${list.tagType == '1'}">
								<lable style="color: blue;">HOT</lable>
							</c:when>
							<c:when test="${list.tagType == '2'}">
								<lable style="color: red;">NEW</lable>
							</c:when>
							<c:otherwise>
								
							</c:otherwise>
						</c:choose>
					</td>
                    <td>${empty list.playTimesReal? 0 : list.playTimesReal }</td>
                    <td>${empty list.downTimes? 0:list.downTimes }</td>
                    <td><a href="javascript:void(0)" onclick="goSubtitlePage('${list.fileId}');">${empty list.subtitleCount? 0:list.subtitleCount }/${empty list.subtitleFileCount? 0:list.subtitleFileCount}</a></td>
                    <td style="display: none;" title="${list.hotReason }">${fn:substring(list.hotReason ,0,50 )}</td>
					<td>
						<c:set var="nowDate" value="<%=System.currentTimeMillis()%>"></c:set>
						
						<c:if test="${list.videoState == '1' }">
							<c:choose>
				    			<c:when test="${nowDate-list.startTime.getTime()<0}">
				    				未到上架时间
				    			</c:when>
				    			<c:when test="${nowDate-list.endTime.getTime()>0}">
									下架
				    			</c:when>
				    			<c:otherwise>
				    				上架
				    			</c:otherwise>
				    		</c:choose>
						</c:if>	
						<c:if test="${list.videoState == '-1' }">
							下架
						</c:if>	
						<c:if test="${list.videoState == '0' }">
							待上架
						</c:if>	
					</td>
                    <td>
						<s:iterator value="buttonMenuList" var="buttonRight">
							<s:if test="%{#buttonRight.resourceLevel == 5}">
								<s:if test="%{#buttonRight.resourceKey == 'menu_player_hotvideo_state'}">
									<s:if test="#list.videoState == 1">
										<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}?videoState=-1&uid=','${list.uid}')"  class="tablelink">下架</a>
									</s:if>
									<s:elseif test="#list.videoState == -1||#list.videoState == 0">
										<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}?videoState=1&uid=','${list.uid}')"  class="tablelink">上架</a>
									</s:elseif>
								</s:if>
								<s:else>
									<s:if test="#list.videoState == 1">
										
									</s:if>
									<s:elseif test="#list.videoState == -1||#list.videoState == 0">
										<a  href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}','${list.uid}')"   class="tablelink">${buttonRight.resourceName}</a>
									</s:elseif>
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
