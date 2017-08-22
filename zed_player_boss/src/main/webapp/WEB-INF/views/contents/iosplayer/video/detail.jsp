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
<input type="hidden" name="hotVideo.uid" id="uid" value="${hotVideo.uid}"/>
	<input type="hidden" name="hotVideo.videoState" id="videoState" value="-1" title="固定值,修改之后就是下架"/>
	<input type="hidden" name="hotVideo.createUser" id="createUser" value="${hotVideo.createUser}"/>
	<input type="hidden" name="hotVideo.createTime" id="createTime" value="${hotVideo.createTime}"/>
    <div class="formbody">
    <div class="formtitle"><span>修改热门影片推荐</span></div>
	    <ul class="forminfo">
	    <li>
	    	<label>片名<font class = "require">*</font></label>
	    	<input name="hotVideo.videoName" id="videoName" type="text" class="dfinput validate[required,minSize[1],maxSize[100]]" value="${hotVideo.videoName}"maxlength="100"  /> 
	    </li>
	    <li>
	    	<label>影片ID<font class = "require">*</font><font class = "require">*</font></label>
	    	<input name="hotVideo.fileId" id="fileId" type="text" class="dfinput validate[required,minSize[1],maxSize[100]]"  value="${hotVideo.fileId}"/> 
	    </li>
	    <li>
    	<label>国家</label>
   		<s:iterator value="playerCountryList" var="playerCountry">
			<s:if test="%{#playerCountry.countryCode == hotVideo.countryCode}">
   				<cite>
					<s:property value="#playerCountry.countryNameZh"/>（<s:property value="#playerCountry.countryCode"/>）
   				</cite>
			</s:if>
		</s:iterator>
    	</li>
        <li>
            <label>标签类型<font class = "require">*</font></label>
            <label>
	           <c:choose>
	               <c:when test="${hotVideo.tagType == 1}">
	                   最热
	               </c:when>
	               <c:when test="${hotVideo.tagType ==2}">
	                   最新
	               </c:when>
	               <c:otherwise>
					自动判定
	                </c:otherwise>
	           </c:choose>
			</label>
        </li>
	    <li style="display: none;">
	    	<label>播放次数<font class = "require">*</font></label>
	    	<input name="hotVideo.playTime" id="playTime" type="text" class="dfinput validate[required,minSize[1],maxSize[11],custom[number]]" value="${hotVideo.playTime}"/> 
	    </li>
	    <li>
	    	<label>影片海报<font class = "require">*</font></label>
	    	<img src="${hotVideo.iconUrl }" alt="影片海报" />
	    </li> 
	    <li>
	    	<label>上线时间<font class = "require">*</font></label>
	    	<input  name="hotVideo.startTime" class="dfinput " value='<fmt:formatDate value="${hotVideo.startTime}" pattern="yyyy-MM-dd HH:mm"/>' style="width: 150px;"  readonly="readonly"   />
	    </li>
	    <li>
	    	<label>下线时间<font class = "require">*</font></label>
	    	<input  name="hotVideo.endTime" class="dfinput" value='<fmt:formatDate value="${hotVideo.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>' style="width: 150px;"  readonly="readonly"  />
	    </li>
	    <li><label>推荐理由<font class = "require">*</font></label>
	    	<textarea rows="10" cols="100"   name="hotVideo.hotReason" id="hotReason"  >${hotVideo.hotReason}</textarea>
	     </li>   
    <li>
    	<input name="" type="button" class="btn" value="返回" onclick="javascript:history.go(-1)"/>
    </li>
    </ul>
    
    
    </div>
</form>
</body>
</html>
