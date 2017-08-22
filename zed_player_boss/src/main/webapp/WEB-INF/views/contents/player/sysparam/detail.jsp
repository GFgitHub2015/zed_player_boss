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
<form  method="post" name="form" id="form"    >
<s:token name="submittedToken"/>
	<input type="hidden" name="page.pageNo" id="backPageNo" value="${page.pageNo}"/>
	<input type="hidden" name="sysParam.paramId" id="paramId" value="${sysParam.paramId}"/>
	<input type="hidden" name="sysParam.createUser" id="createUser" value="${sysParam.createUser}"/>
	<input type="hidden" name="sysParam.createTime" id="createTime" value="${sysParam.createTime}"/>
    <div class="formbody">
    <div class="formtitle"><span>修改系统参数配置</span></div>
	    <ul class="forminfo">
	    <li>
	    	<label>参数类别<font class = "require">*</font></label>
	    	<input name="sysParam.paramType" id="paramType" type="text" class="dfinput validate[required,minSize[1],maxSize[100]]" value="${sysParam.paramType}" maxlength="100"  /> 
	    </li>
	    <li>
	    	<label>参数名称<font class = "require">*</font></label>
	    	<input name="sysParam.paramName" id="paramName" type="text" class="dfinput validate[required,minSize[1],maxSize[100]]"  value="${sysParam.paramName}"/> 
	    </li>
	    <li>
	    	<label>参数值<font class = "require">*</font></label>
	    	<input name="sysParam.paramValue" id="paramValue" type="text" class="dfinput validate[required,minSize[1],maxSize[100]]"  value="${sysParam.paramValue}"/> 
	    </li>
	    <li>
	    	<label>国家码</label>
	    	<input name="sysParam.countryCode" id="countryCode" type="text" class="dfinput validate[maxSize[100]]"  value="${sysParam.countryCode}"/> 
	    </li>
	    <li>
	    	<label>排序</label>
	    	<input name="sysParam.orderBy" id="orderBy" type="text" class="dfinput validate[maxSize[100]]"  value="${sysParam.orderBy}"/> 
	    </li>
	    <li><label>参数描述</label>
	    	<input name="sysParam.paramDec" id="paramDec" type="text" class="dfinput validate[maxSize[100]]"  value="${sysParam.paramDec}"/> 
		</li>     
	    </ul>
    </div>
</form>
</body>
</html>
