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
    <script type="text/javascript" src="<s:property value="jsPath" />My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />layer/layer.js"></script>
</head>
<body>
	<%@ include file="../../../include/navigation.jsp" %>
<form  method="post" name="form" id="form" action="<s:url action="list"/>">
    <div class="formbody">
    <div class="formtitle"><span>调度详情</span></div>
    <ul class="forminfo">
    <li><label>调度ID</label><label class="height32" style= "width:200px;">${pushTaskSchedule.scheduleId}</label></li>
    <li><label>任务ID</label><label class="height32" style= "width:200px;">${pushTaskSchedule.taskId}</label></li>
    <li><label>开始时间</label><label class="height32" style= "width:200px;"><fmt:formatDate value="${pushTaskSchedule.createTime}" type="both"/></label></li>
    <li><label>结束时间</label><label class="height32" style= "width:200px;"><fmt:formatDate value="${pushTaskSchedule.endTime}" type="both"/></label></li>
    <li><label>超时时间</label><label class="height32" style= "width:200px;">${pushTaskSchedule.scheduleTimeout}</label></li>
    <li><label>重试次数</label><label class="height32" style= "width:200px;">${pushTaskSchedule.retries}</label></li>
    <li><label>查询sql</label>
    	<textarea rows="10" cols="100"   name="pushTaskSchedule.targetStatement" id="targetStatement"  >${pushTaskSchedule.targetStatement}</textarea>
    </li>
    <li><label>推送目标类型</label>
    	<cite>
	    	<c:choose>
	   			<c:when test="${pushTaskSchedule.targetType == '1'}">
	   				指定用户推送
	   			</c:when>
	   			<c:when test="${pushTaskSchedule.targetType == '2'}">
	   				按查询条件推送
	   			</c:when>
	   			<c:when test="${pushTaskSchedule.targetType == '3'}">
	   				按主题推送
	   			</c:when>
	   			<c:when test="${pushTaskSchedule.targetType == '4'}">
	   				按主题条件推送
	   			</c:when>
	   			<c:otherwise>
	   			</c:otherwise>
	   		</c:choose>
    	</cite>
    </li> 
    <li><label>消息文本</label>
		<textarea rows="10" cols="100"   name="pushTaskSchedule.content" id="content"  >${pushTaskSchedule.content}</textarea>
	</li>
    <li><label>主题名称</label>
    	<textarea rows="10" cols="100"   name="pushTaskSchedule.topicName" id="topicName"  >${pushTaskSchedule.topicName}</textarea>
    </li>
    <li><label>主题推送条件</label>
    	<textarea rows="10" cols="100"   name="pushTaskSchedule.condition" id="condition"  >${pushTaskSchedule.condition}</textarea>
    </li>
    <li><label>消息扩展属性</label>
    	<textarea rows="10" cols="100"   name="pushTaskSchedule.messageComment" id="messageComment"  >${pushTaskSchedule.messageComment}</textarea>
    </li>
    <li><label>消息标题</label><label class="height32" style= "width:800px;">${pushTaskSchedule.title}</label></li>
    <li><label>消息提醒类型</label>
    	<cite>
	    	<c:choose>
	   			<c:when test="${pushTaskSchedule.reminderType == '0'}">
	   				所有
	   			</c:when>
	   			<c:when test="${pushTaskSchedule.reminderType == '1'}">
	   				震动
	   			</c:when>
	   			<c:when test="${pushTaskSchedule.reminderType == '2'}">
	   				响铃
	   			</c:when>
	   			<c:otherwise>
	   			</c:otherwise>
	   		</c:choose>
    	</cite>
    </li>
    <li>
    	<label>状态</label>
    	<cite>
	    	<c:choose>
	   			<c:when test="${pushTaskSchedule.status == '1'}">
	   				调度中
	   			</c:when>
	   			<c:when test="${pushTaskSchedule.status == '2'}">
	   				调度成功
	   			</c:when>
	   			<c:when test="${pushTaskSchedule.status == '3'}">
	   				调度失败
	   			</c:when>
	   			<c:when test="${pushTaskSchedule.status == '4'}">
	   				调度暂停
	   			</c:when>
	   			<c:when test="${pushTaskSchedule.status == '5'}">
	   				调度停止
	   			</c:when>
	   			<c:otherwise>
	   			</c:otherwise>
	   		</c:choose>
    	</cite>
    </li>     
    <li>
    	<input name="" type="button" class="btn" value="返回" onclick="javascript:history.go(-1)"/>
    </li>
    </ul>
    
    
    </div>
</form>
</body>
</html>
