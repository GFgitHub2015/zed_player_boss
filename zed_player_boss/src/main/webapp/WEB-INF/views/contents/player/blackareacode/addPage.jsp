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
	<script type="text/javascript" src="<s:property value="jsPath" />My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />loading.js"></script>
	<style>
		.require{color:#FF0000;padding-right:0 2px;}
		select option {height: 32px;line-height: 32px;text-indent: 10px;}
	</style>
<script type="text/javascript">
$(document).ready(function(e) {
	$('#form').validationEngine('attach', { 
	    promptPosition: 'centerRight', 
	    scroll: false
	  });	  
	$('#form').bind('jqv.form.result', function(event, errorFound){process(errorFound)});
});

function process(errorFound){
	if(!errorFound){
		openConfirmWindow('提示信息','是否确认提交 ？','确定', 'formSubmit()','closeConfirmWindow()','取消');
	}
}

function formSubmit(){
	LoadingPic.FullScreenShow();
	document.form.submit();
}

</script>

</head>

<body>

<%@ include file="../../../include/navigation.jsp" %>

<div class="formbody">
<div class="formtitle"><span>添加地区编号白名单</span></div>
<form method="post" name="form" id="form" action="<s:url action="add"/>" onsubmit="return false;">
<s:token name="submittedToken"/>
    <ul class="forminfo">
	    <li>
	    	<label style="width:120px;">地区编号<font class = "require">*</font></label>
	    	<select id="areaCode" name="areaCode" class="dayinput form-control validate[required,ajax[isBlackAreaCodeExist]]">
				<option value="86" selected="selected">中国（86）</option>
				<option value="244">安哥拉（244）</option>
				<option value="93">阿富汗（93）</option>
				<option value="355">阿尔巴尼亚（355）</option>
				<option value="213">阿尔及利亚（213）</option>
				<option value="376">安道尔共和国（376）</option>
				<option value="1264">安圭拉岛（1264）</option>
				<option value="1268">安提瓜和巴布达（1268）</option>
				<option value="54">阿根廷（54）</option>
				<option value="374">亚美尼亚（374）</option>
				<option value="247">阿森松（247）</option>
				<option value="61">澳大利亚（61）</option>
				<option value="43">奥地利（43）</option>
				<option value="994">阿塞拜疆（994）</option>
				<option value="1242">巴哈马（1242）</option>
				<option value="973">巴林（973）</option>
				<option value="880">孟加拉国（880）</option>
				<option value="1246">巴巴多斯（1246）</option>
				<option value="375">白俄罗斯（375）</option>
				<option value="32">比利时（32）</option>
				<option value="501">伯利兹（501）</option>
				<option value="229">贝宁（229）</option>
				<option value="1441">百慕大群岛（1441）</option>
				<option value="591">玻利维亚（591）</option>
				<option value="267">博茨瓦纳（267）</option>
				<option value="55">巴西（55）</option>
				<option value="673">文莱（673）</option>
				<option value="359">保加利亚（359）</option>
				<option value="226">布基纳法索（226）</option>
				<option value="95">缅甸（95）</option>
				<option value="257">布隆迪（257）</option>
				<option value="237">喀麦隆（237）</option>
				<option value="1" >加拿大（1）</option>
				<option value="1345">开曼群岛（1345）</option>
				<option value="236">中非共和国（236）</option>
				<option value="235">乍得（235）</option>
				<option value="56">智利（56）</option>
				<option value="57">哥伦比亚（57）</option>
				<option value="242">刚果（242）</option>
				<option value="682">库克群岛（682）</option>
				<option value="506">哥斯达黎加（506）</option>
				<option value="53">古巴（53）</option>
				<option value="357">塞浦路斯（357）</option>
				<option value="420">捷克（420）</option>
				<option value="45">丹麦（45）</option>
				<option value="253">吉布提（253）</option>
				<option value="1890">多米尼加共和国（1890）</option>
				<option value="593">厄瓜多尔（593）</option>
				<option value="20">埃及（20）</option>
				<option value="503">萨尔瓦多（503）</option>
				<option value="372">爱沙尼亚（372）</option>
				<option value="251">埃塞俄比亚（251）</option>
				<option value="679">斐济（679）</option>
				<option value="358">芬兰（358）</option>
				<option value="33">法国（33）</option>
				<option value="594">法属圭亚那（594）</option>
				<option value="241">加蓬（241）</option>
				<option value="220">冈比亚（220）</option>
				<option value="995">格鲁吉亚（995）</option>
				<option value="49">德国（49）</option>
				<option value="233">加纳（233）</option>
				<option value="350">直布罗陀（350）</option>
				<option value="30">希腊（30）</option>
				<option value="1809">格林纳达（1809）</option>
				<option value="1671">关岛（1671）</option>
				<option value="502">危地马拉（502）</option>
				<option value="224">几内亚（224）</option>
				<option value="592">圭亚那（592）</option>
				<option value="509">海地（509）</option>
				<option value="504">洪都拉斯（504）</option>
				<option value="852">香港（852）</option>
				<option value="36">匈牙利（36）</option>
				<option value="354">冰岛（354）</option>
				<option value="91">印度（91）</option>
				<option value="62">印度尼西亚（62）</option>
				<option value="98">伊朗（98）</option>
				<option value="964">伊拉克（964）</option>
				<option value="353">爱尔兰（353）</option>
				<option value="972">以色列（972）</option>
				<option value="39">意大利（39）</option>
				<option value="225">科特迪瓦（225）</option>
				<option value="1876">牙买加（1876）</option>
				<option value="81">日本（81）</option>
				<option value="962">约旦（962）</option>
				<option value="855">柬埔寨（855）</option>
				<option value="327">哈萨克斯坦（327）</option>
				<option value="254">肯尼亚（254）</option>
				<option value="82">韩国（82）</option>
				<option value="965">科威特（965）</option>
				<option value="331">吉尔吉斯坦（331）</option>
				<option value="856">老挝（856）</option>
				<option value="371">拉脱维亚（371）</option>
				<option value="961">黎巴嫩（961）</option>
				<option value="266">莱索托（266）</option>
				<option value="231">利比里亚（231）</option>
				<option value="218">利比亚（218）</option>
				<option value="423">列支敦士登（423）</option>
				<option value="370">立陶宛（370）</option>
				<option value="352">卢森堡（352）</option>
				<option value="853">澳门（853）</option>
				<option value="261">马达加斯加（261）</option>
				<option value="265">马拉维（265）</option>
				<option value="60">马来西亚（60）</option>
				<option value="960">马尔代夫（960）</option>
				<option value="223">马里（223）</option>
				<option value="356">马耳他（356）</option>
				<option value="1670">马里亚那群岛（1670）</option>
				<option value="596">马提尼克（596）</option>
				<option value="230">毛里求斯（230）</option>
				<option value="52">墨西哥（52）</option>
				<option value="373">摩尔多瓦（373）</option>
				<option value="377">摩纳哥（377）</option>
				<option value="976">蒙古（976）</option>
				<option value="1664">蒙特塞拉特岛（1664）</option>
				<option value="212">摩洛哥（212）</option>
				<option value="258">莫桑比克（258）</option>
				<option value="264">纳米比亚（264）</option>
				<option value="674">瑙鲁（674）</option>
				<option value="977">尼泊尔（977）</option>
				<option value="599">荷属安的列斯（599）</option>
				<option value="31">荷兰（31）</option>
				<option value="64">新西兰（64）</option>
				<option value="505">尼加拉瓜（505）</option>
				<option value="227">尼日尔（227）</option>
				<option value="234">尼日利亚（234）</option>
				<option value="850">朝鲜（850）</option>
				<option value="47">挪威（47）</option>
				<option value="968">阿曼（968）</option>
				<option value="92">巴基斯坦（92）</option>
				<option value="507">巴拿马（507）</option>
				<option value="675">巴布亚新几内亚（675）</option>
				<option value="595">巴拉圭（595）</option>
				<option value="51">秘鲁（51）</option>
				<option value="63">菲律宾（63）</option>
				<option value="48">波兰（48）</option>
				<option value="689">法属玻利尼西亚（689）</option>
				<option value="351">葡萄牙（351）</option>
				<option value="1787">波多黎各（1787）</option>
				<option value="974">卡塔尔（974）</option>
				<option value="262">留尼旺（262）</option>
				<option value="40">罗马尼亚（40）</option>
				<option value="7">俄罗斯（7）</option>
				<option value="1758">圣卢西亚（1758）</option>
				<option value="1784">圣文森特岛（1784）</option>
				<option value="684">东萨摩亚（美)（684）</option>
				<option value="685">西萨摩亚（685）</option>
				<option value="378">圣马力诺（378）</option>
				<option value="239">圣多美和普林西比（239）</option>
				<option value="966">沙特阿拉伯（966）</option>
				<option value="221">塞内加尔（221）</option>
				<option value="248">塞舌尔（248）</option>
				<option value="232">塞拉利昂（232）</option>
				<option value="65">新加坡（65）</option>
				<option value="421">斯洛伐克（421）</option>
				<option value="386">斯洛文尼亚（386）</option>
				<option value="677">所罗门群岛（677）</option>
				<option value="252">索马里（252）</option>
				<option value="27">南非（27）</option>
				<option value="34">西班牙（34）</option>
				<option value="94">斯里兰卡（94）</option>
				<option value="1758">圣卢西亚（1758）</option>
				<option value="1784">圣文森特（1784）</option>
				<option value="249">苏丹（249）</option>
				<option value="597">苏里南（597）</option>
				<option value="268">斯威士兰（268）</option>
				<option value="46">瑞典（46）</option>
				<option value="41">瑞士（41）</option>
				<option value="963">叙利亚（963）</option>
				<option value="886">台湾省（886）</option>
				<option value="992">塔吉克斯坦（992）</option>
				<option value="255">坦桑尼亚（255）</option>
				<option value="66">泰国（66）</option>
				<option value="228">多哥（228）</option>
				<option value="676">汤加（676）</option>
				<option value="1809">特立尼达和多巴哥（1809）</option>
				<option value="216">突尼斯（216）</option>
				<option value="90">土耳其（90）</option>
				<option value="993">土库曼斯坦（993）</option>
				<option value="256">乌干达（256）</option>
				<option value="380">乌克兰（380）</option>
				<option value="971">阿拉伯联合酋长国（971）</option>
				<option value="44">英国（44）</option>
				<option value="1" >美国（1）</option>
				<option value="598">乌拉圭（598）</option>
				<option value="233">乌兹别克斯坦（233）</option>
				<option value="58">委内瑞拉（58）</option>
				<option value="84">越南（84）</option>
				<option value="967">也门（967）</option>
				<option value="381">南斯拉夫（381）</option>
				<option value="27">南非（27）</option>
				<option value="263">津巴布韦（263）</option>
				<option value="243">扎伊尔（243）</option>
				<option value="260">赞比亚（260）</option>
			</select>
	    </li>
	    <li>
	    	<label>&nbsp;</label>
		    <input name="" type="submit" class="btn" value="保存"/>
		    <input name="" type="button" class="btn" value="返回" onclick="javascript:history.go(-1)"/>
	    </li>
    </ul>
	</form>
   </div>
</body>
</html>
