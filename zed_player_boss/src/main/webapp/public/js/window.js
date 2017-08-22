/**
 * confirm window 
 * @param title
 * @param content
 * @param sureButtonName
 * @param sureFunction
 * @param cancalFunction
 * @param cancleButtonName
 */
function openConfirmWindow(title,content,sureButtonName, sureFunction,cancalFunction,cancleButtonName){
	showConfirmWindow(title,content,sureButtonName, sureFunction,cancalFunction,cancleButtonName)
	$(".tip").fadeIn(200);
}

function closeConfirmWindow(){
	$(".tip").fadeOut(200);
}

function showConfirmWindow(title,content,sureButtonName,sureFunction ,cancalFunction,cancleButtonName){
	windowInfo = '<div class="tip">'+
	'<div class="tiptop"><span>'+title+'</span><a><i></i></a></div>'+
	'<div class="tipinfo">'+
	'<span></span>'+
	'<div class="tipright">'+
	'<p>'+content+'</p>'+
	'<cite></cite>'+
	'</div>'+
	'</div>'+
	'<div class="tipbtn">'+
	'<input name="" type="button"  class="sure" value="'+sureButtonName+'" onclick="'+sureFunction+'"/>&nbsp;&nbsp;&nbsp;&nbsp;'+
	'<input name="" type="button"  class="cancel" value="'+cancleButtonName+'" onclick="'+cancalFunction+'" />'+
	'</div>'+
	'</div>';
	
	$("body").append(windowInfo);
	
	$(".tiptop a").click(function(){
		  $(".tip").fadeOut(200);
	});
		  
}


function openConfirmWindow(title,content,sureButtonName, sureFunction,cancalFunction,cancleButtonName){
	showConfirmWindow(title,content,sureButtonName, sureFunction,cancalFunction,cancleButtonName)
	$(".tip").fadeIn(200);
}

function closeConfirmWindow(){
	$(".tip").fadeOut(200);
}


/**
 * alert window
 * @param title
 * @param content
 * @param sureButtonName
 * @param sureFunction
 * @param cancalFunction
 * @param cancleButtonName
 */
function showAlertWindow(title,content,sureButtonName){
	windowInfo = '<div class="tipAlert">'+
	'<div class="tiptop"><span>'+title+'</span><a><i class=""></i></a></div>'+
	'<div class="tipinfo">'+
	'<span></span>'+
	'<div class="tipright">'+
	'<p>'+content+'</p>'+
	'<cite></cite>'+
	'</div>'+
	'</div>'+
	'<div class="tipbtn">'+
	'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="" type="button"  class="sure" value="'+sureButtonName+'" onclick="closeAlertWindow()"/>'+
	'</div>'+
	'</div>';
	
	$("body").append(windowInfo);
	
	$(".tiptop a").click(function(){
		  $(".tipAlert").fadeOut(200);
	});
}

function openAlertmWindow(title,content,sureButtonName){
	showAlertWindow(title,content,sureButtonName)
	$(".tipAlert").fadeIn(200);
}

function closeAlertWindow(){
	$(".tipAlert").fadeOut(200);
}
