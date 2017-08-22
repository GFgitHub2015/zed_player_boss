<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../include/resource.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<!-- common -->
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
		.input-file {
			display:inline-block;
		    border:1px solid #dedede;
			width:50px;
			height:27px;
			line-height:27px;
			text-align:center;
			overflow:hidden;
			position:relative;
			top:8px;
		}
		.input-file:hover {
			background:#379b29;
			color:#fff;
		}
		.input-file input {
			opacity:0;
			filter:alpha(opacity=0);
			font-size:100px;
			position:absolute;
			top:0;
			right:0;
		}
		div.btn_right {float: right; margin: 8px 0px;}
	</style>
<script type="text/javascript">
var checkSubmitFlg = false;
$(document).ready(function(e) {
	$('#form').validationEngine('attach', { 
	    promptPosition: 'centerRight', 
	    scroll: false
	  });	  
	$('#form').bind('jqv.form.result', function(event, errorFound){process(errorFound)});
	
	var $tbody = $('#subtitleTable tbody');
	
	function gotoTop(obj, container) {
		var curObj = $(obj).parent().parent();
		var curIdx = curObj.find('td:eq(0)>span').text().trim();
		if (curIdx == '1') {
			return;
		}
		var $container = $(container);
		var firstObj = $container.find('tr:eq(0)');
		var firstIdx = firstObj.find('td:eq(0)').text().trim();
		firstObj.before(curObj);
		$container.find('tr').each(function(j, trObj) {
			if (j === curIdx) {
				return false;
			}
			$(trObj).find('td:eq(0)>span').text(j + 1);
			$(trObj).find('input[type="hidden"]').each(function() {
				$(this).attr('name', $(this).attr('name').replace(/\[\d\]/, '[' + j + ']'));
			});
		});
	}
	
	function delSubtitle(obj) {
		var $trObj = $(obj).parent().parent();
		var idx = parseInt($trObj.find('td:eq(0) span').text().trim());
		$trObj.nextAll('tr').each(function(i, self) {
			$(self).find('td:eq(0)>span').text(idx + i);
			$(self).find('input[type="hidden"]').each(function() {
				$(this).attr('name', $(this).attr('name').replace(/\[\d\]/, '[' + (idx + i - 1) + ']'));
			});
		});
		var $delSubtitleId = $trObj.find('input[type="hidden"][name="videoSubtitleBeans[' + (idx - 1) + '].id"]');
		if ($delSubtitleId.length > 0) {
			var $delSubtitleIdsObj = $('#delSubtitleIds');
			var delSubtitleIds = $delSubtitleIdsObj.val() ? $delSubtitleIdsObj.val().split(',') : [];
			delSubtitleIds.push($delSubtitleId.val());
			$delSubtitleIdsObj.val(delSubtitleIds.join(','));
		}
		$trObj.remove();
	}
	
	$tbody.find('tr').each(function(i, obj) {
		$(obj).find('td:last a').click(function(e) {
			if (e.target.name === '_top') {
				gotoTop($(this), $tbody);
			} else if (e.target.name === '_edit') {
				$(this).parent().prevAll('td').each(function(j, tdObj) {
					if (j === 4) {
						var $input = $('<input type="text" width="80" class="validate[required,minSize[1],maxSize[128]]" maxlength="128" />'), sVal = $(tdObj).find('input[type="hidden"]').val();
						$input.val(sVal);
						$(tdObj).find('span').html($input);
					} else if (j === 3) {
						var $input = $('<input type="text" width="30" class="validate[required,minSize[1],maxSize[30]]" maxlength="30" />'), sVal = $(tdObj).find('input[type="hidden"]').val();
						$input.val(sVal);
						$(tdObj).find('span').html($input);
					}
				});
				$(this).attr('name', '_save').text("保存");
			} else if (e.target.name === '_save') {
				var validTdArr = [], validPass = true;
				$(this).parent().prevAll('td').each(function(j, tdObj) {
					if (j === 3 || j === 4) {
						validTdArr.push(tdObj);
						if (validPass) {
							validPass = !($(tdObj).find('input[type="text"]').validationEngine('validate'));
						}
					}
				});
				if (!validPass) {
					return ;
				}
				$.each(validTdArr, function() {
					var sText = $(this).find('input[type="text"]');
					$(this).find('span').text((sText.val().length > 50) ? sText.val().substring(0, 50) + '...' : sText.val());
					$(this).find('input[type=hidden]').val(sText.val());
					sText.remove();
				});
				$(this).attr('name', '_edit').text("编辑");
			} else if (e.target.name === '_del') {
				delSubtitle($(this));
			}
		});
	});
	
	$('#addSubtitle').click(function() {
		$tbody = $('#subtitleTable tbody');
		var idx = $tbody.find('tr').length;
		var template = '<tr><td><span>{idx}</span><input type="hidden" name="videoSubtitleBeans[{idxNo}].subtitleExtBean.orderNo" /></td><td><span><input type="text" width="80" class="validate[required,minSize[1],maxSize[128]]" maxlength="128" /></span><input type="hidden" name="videoSubtitleBeans[{idxNo}].fileName" /></td><td><span><input type="text" width="30" class="validate[required,minSize[1],maxSize[30]]" maxlength="30" /></span><input type="hidden" name="videoSubtitleBeans[{idxNo}].language" /></td><td><span><input type="text" width="30" class="validate[required,minSize[1],maxSize[10]]" maxlength="10" /></span><input type="hidden" name="videoSubtitleBeans[{idxNo}].format" /></td><td><span>用户上传</span><input type="hidden" name="videoSubtitleBeans[{idxNo}].origin" value="2" /></td><td>{createTime}</td><td>{oper}</td></tr>';
		if (idx === 1 && $tbody.find('tr>td').length === 1) {
			template = template.replace('{idx}', 1).replace('{createTime}', '').replaceAll('{idxNo}', 0).replace('{oper}', '<a href="javascript:void(0);" class="input-file">浏览...<input type="file" name="file" /><input type="hidden" name="fileIndex" value="0" /></a>&nbsp;<a href="javascript:void(0)" name="_top" class="tablelink">置顶</a>&nbsp;<a href="javascript:void(0)" name="_save" class="tablelink">保存</a>&nbsp;<a href="javascript:void(0)" name="_del" class="tablelink">删除</a>');
			$('#subtitleTable tbody').html(template);
		} else {
			template = template.replace('{idx}', (idx + 1)).replace('{createTime}', '').replaceAll('{idxNo}', idx).replace('{oper}', '<a href="javascript:void(0);" class="input-file">浏览...<input type="file" name="file" /><input type="hidden" name="fileIndex" value="' + idx + '" /></a>&nbsp;<a href="javascript:void(0)" name="_top" class="tablelink">置顶</a>&nbsp;<a href="javascript:void(0)" name="_save" class="tablelink">保存</a>&nbsp;<a href="javascript:void(0)" name="_del" class="tablelink">删除</a>');
			$('#subtitleTable tbody').append(template);
		}
		$tbody.find('tr:last td:last a').click(function(e) {
			if (e.target.name === '_top') {
				gotoTop($(this), $tbody);
			} else if (e.target.name === '_edit') {
				$(this).siblings('.input-file').show();
				$(this).parent().prevAll('td').each(function(i, obj) {
					var $input = $('<input type="text" width="30" />');
					if (i === 2) {
						$input = $('<input type="text" width="30" class="validate[required,minSize[1],maxSize[10]]" maxlength="10" />');
						sVal = $(obj).find('input[type="hidden"]').val();
						$input.val(sVal);
						$(obj).find('span').html($input);
					} else if (i === 3) {
						$input = $('<input type="text" width="30" class="validate[required,minSize[1],maxSize[30]]" maxlength="30" />');
						sVal = $(obj).find('input[type="hidden"]').val();
						$input.val(sVal);
						$(obj).find('span').html($input);
					} else if (i === 4) {
						$input = $('<input type="text" width="80" class="validate[required,minSize[1],maxSize[128]]" maxlength="128" />');
						sVal = $(obj).find('input[type="hidden"]').val();
						$input.val(sVal);
						$(obj).find('span').html($input);
					}
				});
				$(this).attr('name', '_save').text("保存");
			} else if (e.target.name === '_save') {
				var $inputfile = $(this).siblings('.input-file');
				if ($inputfile) {
					var fileName = $inputfile.find('input[type="file"]').val();
					if (fileName == '') {
						openAlertmWindow('提示信息','请选择字幕文件!','确定');
						return ;
					} else if (fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase() != 'srt') {
						openAlertmWindow('提示信息','字幕只支持srt格式!','确定');
						return ;
					}
				}
				$inputfile.hide();
				var validTdArr = [], validPass = true;
				$(this).parent().prevAll('td').each(function(j, tdObj) {
					if (j === 2 || j === 3 || j === 4) {
						validTdArr.push(tdObj);
						if (validPass) {
							validPass = !($(tdObj).find('input[type="text"]').validationEngine('validate'));
						}
					}
				});
				if (!validPass) {
					return ;
				}
				$.each(validTdArr, function() {
					var sText = $(this).find('input[type="text"]');
					$(this).find('span').text((sText.val().length > 50) ? sText.val().substring(0, 50) + '...' : sText.val());
					$(this).find('input[type=hidden]').val(sText.val());
					sText.remove();
				});
				$(this).attr('name', '_edit').text("编辑");
			} else if (e.target.name === '_del') {
				delSubtitle($(this));
			}
		}).change(function(e) {
			var $target = $(e.target);
			if (e.target.name === 'file') {
				var fileName = $target.val().split("\\").pop();
				var $tdObj = $target.parent().parent().parent().find('td:eq(1)');
				$tdObj.find('input[type="text"]').val(fileName);
				$tdObj.find('input[type="hidden"]').val(fileName);
			}
		});
	});
});

function clearForm() {
	window.location = '<s:url action="subtitlePage" />?fileId=${fileId}';
}

function process(errorFound){
    if(!errorFound) {
		if ($('#subtitleTable a[name="_save"]').length > 0) {
			openAlertmWindow('提示信息','请先保存字幕!','确定');
			return ;
		}
    	var $trObjs = $('#subtitleTable tbody>tr');
    	var len = $trObjs.length;
    	$trObjs.each(function(i, trObj) {
    		$(trObj).find('input[type="hidden"][name="videoSubtitleBeans[' + i + '].subtitleExtBean.orderNo"]').val(len - i);
    		$(trObj).find('input[type="hidden"][name="fileIndex"]').val(i);
    	});
    	openConfirmWindow('提示信息','是否确认修改 ？','确定', 'formSubmit()','closeConfirmWindow()','取消');
    }
}

function formSubmit(){
	if(checkSubmitFlg==true){
		openAlertmWindow('提示信息','请求已经处理提交!','确定');
	}else{
		checkSubmitFlg = true;
		LoadingPic.FullScreenShow();
		document.form.submit();
	}
}

</script>
</head>
<body>
<%@ include file="../../../include/navigation.jsp" %>
<form  method="post" name="form" id="form" enctype="multipart/form-data"  action="<s:url action="updateSubtitle"/>" onsubmit="return false;">
	<input type="hidden" name="fileId" id="fileId" value="${fileId}"/>
	<input type="hidden" name="resId" id="resId" value="${resId}"/>
    <div class="formbody">
    <div class="formtitle"><span>热门影片字幕信息</span></div>
    	<div class="btn_right">
    		<button type="button" class="btn" id="addSubtitle">新增字幕</button>
    	</div>
	    <ul class="forminfo">
	    <li>
			<input type="hidden" name="delSubtitleIds" id="delSubtitleIds" />
	    	<table class="tablelist" id="subtitleTable">
				<thead>
					<tr>
						<th>序号</th>
						<th>名称</th>
						<th>语言</th>
						<th>格式</th>
						<th>来源</th>
						<th>创建时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<s:if test="videoSubtitleBeans.size == 0">
						<tr><td colspan="7" align="center">没有找到字幕</td></tr>
					</s:if>
					<s:else>
					<s:iterator value="videoSubtitleBeans" var="list" status="st">
						<tr title="${fileName}">
									<td>
										<span><s:property value="#st.count" /></span>
										<input type="hidden" name="videoSubtitleBeans[${st.index}].id" value="${id}" />
										<input type="hidden" name="videoSubtitleBeans[${st.index}].subtitleExtBean.orderNo" value="${subtitleExtBean.orderNo}" />
									</td>
									<td>
										<span>
										<c:if test="${fn:length(fileName)>50}">
											<c:out value="${fn:substring(fileName,0,50)}..." />
										</c:if>
										<c:if test="${fn:length(fileName)<=50}">
											<c:out value="${fileName}" />
										</c:if>
										</span>
										<input type="hidden" name="videoSubtitleBeans[${st.index}].fileName" value="${fileName}" />
									</td>
									<td>
										<span><s:property value="language" /></span>
										<input type="hidden" name="videoSubtitleBeans[${st.index}].language" value="${language}" />
									</td>
									<td><s:property value="format" /></td>
									<td>
							    		<c:choose>
							    			<c:when test="${origin == 1}">
							    				后台添加
							    			</c:when>
							    			<c:when test="${origin == 2}">
							    				用户上传
							    			</c:when>
							    			<c:otherwise>
							    				其它
							    			</c:otherwise>
							    		</c:choose>
									</td>
									<td><fmt:formatDate value="${createTime}" type="both"/></td>
									<td>
										<a href="javascript:void(0)" name="_top" class="tablelink">置顶</a>
										<a href="javascript:void(0)" name="_edit" class="tablelink">编辑</a>
										<s:if test="filePath != null && !''.equals(filePath)">
											<a href="${filePath}"  target="_blank" class="tablelink">链接</a>
										</s:if>
										<a href="javascript:void(0)" name="_del" class="tablelink">删除</a>
									</td>
							</tr>				
					</s:iterator>
					</s:else>
				</tbody>
			</table>
	    </li>
	    <li><label>&nbsp;</label>
		    <input name="" type="submit" class="btn" value="修改"/>
		    <input name="" type="button" class="btn" value="重置" onclick="clearForm();"/>
	    </li>
	    </ul>
    </div>
</form>
</body>
</html>
