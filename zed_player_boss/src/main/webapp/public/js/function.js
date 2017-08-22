$(document).ready(function(){
	
	/**
	 * 全选或者全不选
	**/
	$("#selectCheckbox").bind('click',function(){
//		var flag = $(this).attr("checked");
//		if(flag){
//			$("input[name='checkbox_list']").attr("checked",flag);
//		}else{
//			$("input[name='checkbox_list']").removeAttr("checked");
//		}
		$("[name='checkbox_list']:checkbox").attr("checked",this.checked);
		selectCheckBoxList();
	});
	
	$("[name='checkbox_list']:checkbox").click(function(){
		var flag = true;
		$("[name='checkbox_list']:checkbox").each(function(){
			if (!this.checked) {
				flag = false;
			}
		});
		$("#selectCheckbox").attr("checked",flag);
		selectCheckBoxList();
	});
	
});

function selectCheckBoxList(){
	var checkbox_value = [];
	$('input[name="checkbox_list"]').each(function(){
		if($(this).attr("checked")){
			checkbox_value.push($(this).val());
		}
	});
	$("#selectCheckbox").val(checkbox_value.join(","));
}



function trim(str) {
	for (var i=0; (str.charAt(i)==' ') && i<str.length; i++);
	if (i == str.length) return '';
	var newstr = str.substr(i);
	for (var i=newstr.length-1; newstr.charAt(i)==' ' && i>=0; i--);
	newstr = newstr.substr(0,i+1);
	return newstr;
}

String.prototype.replaceAll = function (s1, s2) { 
    return this.replace(new RegExp(s1,"gm"),s2);
}

function systemLogOff(url){
	top.location = url;
}

function errorRedirect(url){
	top.location = url;
}

function successRedirect(url){
	window.location = url;
}


function showErrorMessage(divId, contentId, content, speed) {
	if(speed==undefined) speed=0;
	document.getElementById(contentId).innerHTML = content;
	$("#"+divId).show(speed);
}

function hiddenErrorMessage(divId, contentId, speed) {
	if(speed==undefined) speed=0;
	$("#"+divId).hide(speed);
	document.getElementById(contentId).innerHTML = ""; 
}


String.prototype.endWith=function(str){
	if(str==null||str==""||this.length==0||str.length>this.length)
	  return false;
	if(this.substring(this.length-str.length)==str)
	  return true;
	else
	  return false;
	return true;
	}
	String.prototype.startWith=function(str){
	if(str==null||str==""||this.length==0||str.length>this.length)
	  return false;
	if(this.substr(0,str.length)==str)
	  return true;
	else
	  return false;
	return true;
	}
	
	function initPageNo(){
		$("#pageNo").attr("value","1");
	}
