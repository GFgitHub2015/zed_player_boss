<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../include/resource.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="<s:property value="themePath" />css/style.css" rel="stylesheet">
    <link href="<s:property value="themePath" />css/style-responsive.css" rel="stylesheet"> 
	<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />style.css"/>
	<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />validationEngine.jquery.css"/>
	<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />loading.css"/>
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />window.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />function.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.validationEngine-lang.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.validationEngine.min.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />loading.js"></script>
	<style>
		.require{color:#FF0000;padding-right:0 2px;}
		select option {height: 32px;line-height: 32px;text-indent: 10px;}
	</style>
<script type="text/javascript">

</script>

</head>

<body>

<%@ include file="../../../include/navigation.jsp" %>

<div class="formbody">
<div class="formtitle"><span>查看版本信息</span></div>
<form method="post" name="form" id="form" enctype="multipart/form-data" action="<s:url action="add"/>" onsubmit="return false;">
    <ul class="forminfo">
	    <li>
	    	<label style="width:120px;">版本编号<font class = "require">*</font></label>
	    	<input name="version.versionId" id="version.versionId" type="text" class="dfinput validate[required,minSize[1],maxSize[100],custom[onlyNumber]]" maxlength="100" value="${version.versionId }"/>
	    </li>
	    <li>
	    	<label style="width:120px;">版本号<font class = "require">*</font></label>
	    	<input name="version.versionCode" id="version.versionCode" type="text" class="dfinput validate[required,minSize[1]]"  maxlength="50"  value="${version.versionCode }"/>
	    </li>
	    <li>
	    	<label style="width:120px;">app包名<font class = "require">*</font></label>
	    	<input name="version.packageName" id="version.packageName" type="text" class="dfinput validate[required,minSize[1],maxSize[100]]" maxlength="100"  value="${version.packageName }"/>
	    </li>
		<li>
	    	<label style="width:120px;">是否强制升级<font class = "require">*</font></label>
            <c:choose>
            	<c:when test="${version.isMust=='1'}">  
	    		<input class="dfinput" name="version.isMust" type="text" value="普通升级"  />
	    		</c:when>
            	<c:when test="${version.isMust=='2'}">  
	    		<input class="dfinput" name="version.isMust" type="text" value="强制升级"  />
	    		</c:when>
	    		<c:otherwise>
	    		</c:otherwise>
            </c:choose>
    	</li>
    	<li>
	    	<label style="width:120px;">应用类型<font class = "require">*</font></label>
	    	<input name="version.appType" id="version.appType" type="text" class="dfinput validate[required,minSize[1],maxSize[11],custom[onlyNumber]]" maxlength="11"  value="${version.appType }"/>
	    </li>
    	<li>
	    	<label style="width:120px;">渠道<font class = "require">*</font></label>
	    	<input name="version.channel" id="version.channel" type="text" class="dfinput validate[required,minSize[1]]" maxlength="11" value="${version.channel }"/>
	    </li>
	    <li>
	    	<label style="width:120px;">描述<font class = "require">*</font></label>
	    	<input name="version.description" id="version.description" type="text" class="dfinput validate[required,minSize[1]]" maxlength="100"  value="${version.description }"/>
	    </li>
    	<li id = "url">
	        <label style="width:120px;">资源链接</label>
	        <input name="version.downloadUrl" id="version.downloadUrl" type="text" class="dfinput validate[required,minSize[1]]"  value="${version.downloadUrl }"/>
     	</li>
    	<li id = "url">
	        <label style="width:120px;">下载来源</label>
	        <c:choose>
	        	<c:when test="${version.source eq 1}">
			        <input name="version.source" id="version.source" type="text" class="dfinput validate[required,minSize[1]]"  value="GooglePlayer"/>
	        	</c:when>
	        	<c:when test="${version.source eq 2}">
			        <input name="version.source" id="version.source" type="text" class="dfinput validate[required,minSize[1]]"  value="官方渠道"/>
	        	</c:when>
	        </c:choose>
     	</li>
     	<li>
	    	<label>&nbsp;</label>
		    <input name="" type="button" class="btn" value="返回" onclick="javascript:history.go(-1)"/>
	    </li>
    </ul>
	</form>
   </div>
</body>
</html>
