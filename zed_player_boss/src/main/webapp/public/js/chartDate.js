//根据不同类型查询报表
function searchChartByType(){
	   var beginTime = $('#beginTime').val().replace(/-/g,'');
	   var endTime = $('#endTime').val().replace(/-/g,'');
	   if(endTime != '' && beginTime == ''){
	     compareTime(beginTime, endTime);
	   }else if(endTime == '' && beginTime != ''){
		   var myDate = new Date();
		   var year = myDate.getFullYear();
		   var month = (myDate.getMonth()+1) < 10 ?"0" + (myDate.getMonth()+1):(myDate.getMonth()+1);
		   var day = myDate.getDate() < 10 ?"0" + myDate.getDate():myDate.getDate();
		   if($('#type').val() == 'year'){
			   endTime = year;
			   $("#endTime").val(endTime);
		   }else if($('#type').val() == 'month'){
			   endTime = year + month;
			   $("#endTime").val(year + "-" + month);
	       }else if($('#type').val() == 'day'){
	    	   $("#endTime").val(year + "-" + month + "-" + day);
	       }
	     return compareTime(beginTime, endTime);
	   }else if(endTime == '' && beginTime == ''){
		    return true;
	   }else if(endTime != '' && beginTime != ''){
		   return compareTime(beginTime, endTime);
	   }
}

function compareTime(beginTime, endTime){
	if(endTime != '' && beginTime != '' && parseInt(endTime) < parseInt(beginTime)){
       $('#chartSearchError').text('开始时间不能大于结束时间');
       return false;
   }else if($('#type').val() == 'year' && !validateTime($('#type').val() ,beginTime, endTime)){
       $('#chartSearchError').text('年份最多能查询10年');
       return false;
   }else if($('#type').val() == 'month' && !validateTime($('#type').val() ,beginTime, endTime)){
         $('#chartSearchError').text('月份最多能查询12个月');
         return false;
   }else if($('#type').val() == 'day' && !validateTime($('#type').val() ,beginTime, endTime)){
         $('#chartSearchError').text('天数最多能查询30天');
         return false;
   }else{
       $('#chartSearchError').text('');
       return true;
   }
}

//修改时间控件格式
function changeJsControlDateFormat(){
   if($('#type').val() == 'year'){
	   $('#beginTime').attr('onfocus',"WdatePicker({dateFmt:'yyyy',maxDate:'%y'})").attr('value','');
	   $('#endTime').attr('onfocus',"WdatePicker({dateFmt:'yyyy',maxDate:'%y'})").attr('value','');
   }else if($('#type').val() == 'month'){
	   $('#beginTime').attr('onfocus',"WdatePicker({dateFmt:'yyyy-MM',maxDate:'%y-%M'})").attr('value','');
	   $('#endTime').attr('onfocus',"WdatePicker({dateFmt:'yyyy-MM',maxDate:'%y-%M'})").attr('value','');
   }else{
	   $('#beginTime').attr('onfocus',"WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})").attr('value','');
	   $('#endTime').attr('onfocus',"WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})").attr('value','');
   }
   $('#chartSearchError').text('');
}

//修改时间控件格式
function loadJsControlDateFormat(){
   if($('#type').val() == 'year'){
     $('#beginTime').attr('onfocus',"WdatePicker({dateFmt:'yyyy',maxDate:'%y'})");
     $('#endTime').attr('onfocus',"WdatePicker({dateFmt:'yyyy',maxDate:'%y'})");
   }else if($('#type').val() == 'month'){
     $('#beginTime').attr('onfocus',"WdatePicker({dateFmt:'yyyy-MM',maxDate:'%y-%M'})");
     $('#endTime').attr('onfocus',"WdatePicker({dateFmt:'yyyy-MM',maxDate:'%y-%M'})");
   }else{
     $('#beginTime').attr('onfocus',"WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})");
     $('#endTime').attr('onfocus',"WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})");
   }
   $('#chartSearchError').text('');
}

function validateTime(type ,beginTime, endTime){
	   if(type == 'year'){
		   if(beginTime == '' && endTime != ''){
			   $("#beginTime").val(parseInt(endTime) - 9);
			   return true;
		   }else if(endTime != '' && beginTime != ''){
			   if(parseInt(endTime) - parseInt(beginTime) >= 10){
				   return false;
			   }else{
				   return true;
			   }
	     }
	   }else if(type == 'month'){
	      if(beginTime == '' && endTime != ''){
	    	   var year = parseInt(endTime.substring(0,4)) - 1;
	         var month = parseInt(endTime.substring(4,6)) + 1;
	         $("#beginTime").val(year + "-"+(month < 10?("0"+month):month));
	         return true;
	      }else{
	    	  var year = parseInt(endTime.substring(0,4)) - parseInt(beginTime.substring(0,4));
	        var month = parseInt(endTime.substring(4,6)) - parseInt(beginTime.substring(4,6));
	    	  if(year * 12 + month >= 12){
            return false;
          }else{
            return true;
          }
	      }
	   }else if(type == 'day'){
	    var newDate = '';
	    var newyear = parseInt(endTime.substring(0,4));
	    var newmonth = parseInt(endTime.substring(4,6));
	    var newday = parseInt(endTime.substring(6,8));
	    var yearFlag = false;
	    //判断闰年平年
	    if(newyear % 4 == 0 && newyear % 100 != 0){
	      yearFlag = true;
	    }else if(newyear % 100 == 0 && newyear % 400 == 0){
	      yearFlag = true;
	    }
	    for(var i = 1;i < 30;i++){
	      if(newmonth != 1 && newday == 1){
	        newmonth = newmonth - 1;
	        if(newmonth == 1 || newmonth == 3 || newmonth == 5 || newmonth == 7 || newmonth == 8 || newmonth == 10 || newmonth == 12){
	          newday = 31;
	        }else if(newmonth == 4 || newmonth == 6 || newmonth == 9 || newmonth == 11){
	          newday = 30;
	        }else if(newmonth == 2 && yearFlag){
	          newday = 29;
	        }else if(newmonth == 2 && !yearFlag){
	          newday = 28;
	        }
	      }else if(newmonth == 1 && newday == 1){
	        newyear = newyear - 1;
	        newmonth = 12;
	        newday = 31;
	      }else{
	        newday = newday - 1;
	      }
	    }
	    newDate = newDate + newyear;
	    if(newmonth < 10){
	      newDate = newDate + "0" + newmonth;
	    }else{
	      newDate = newDate + newmonth;
	    }
	    if(newday < 10){
	      newDate = newDate + "0" + newday;
	    }else{
	      newDate = newDate + newday;
	    }
	    if(beginTime == ''){
	       $("#beginTime").val(newyear+"-"+(newmonth<10?"0"+newmonth:newmonth)+"-"+(newday<10?"0"+newday:newday));
	       return true;
	    }else{
	    	  //比较日期大小
	        var time1 = parseInt(newDate);
	        var time2 = parseInt(beginTime);
	        if(time1 > time2){
	            return false;
          }else{
            return true;
          }
	    }
	   }
	}