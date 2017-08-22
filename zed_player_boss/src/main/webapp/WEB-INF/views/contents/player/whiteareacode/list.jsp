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
</head>

<body>
	<%@ include file="../../../include/navigation.jsp" %>
    
    <div class="mainindex">
	<form id = "form" action="<s:url action="list" />" method="post" name="form">
		<ul class="seachform">
				<li>
					<label>地区</label>
					<select name="areaCode" id="areaCode" class="dayinput form-control" style="width:150px;">
						<option value="" <c:if test = "${areaCode == ''}">selected="selected"</c:if>>请选择</option>
						<option value="244" <c:if test ="${areaCode =='244'}">selected="selected"</c:if>>安哥拉（244）</option>
						<option value="93" <c:if test ="${areaCode =='93'}">selected="selected"</c:if>>阿富汗（93）</option>
						<option value="355" <c:if test ="${areaCode =='355'}">selected="selected"</c:if>>阿尔巴尼亚（355）</option>
						<option value="213" <c:if test ="${areaCode =='213'}">selected="selected"</c:if>>阿尔及利亚（213）</option>
						<option value="376" <c:if test ="${areaCode =='376'}">selected="selected"</c:if>>安道尔共和国（376）</option>
						<option value="1264" <c:if test ="${areaCode =='1264'}">selected="selected"</c:if>>安圭拉岛（1264）</option>
						<option value="1268" <c:if test ="${areaCode =='1268'}">selected="selected"</c:if>>安提瓜和巴布达（1268）</option>
						<option value="54" <c:if test ="${areaCode =='54'}">selected="selected"</c:if>>阿根廷（54）</option>
						<option value="374" <c:if test ="${areaCode =='374'}">selected="selected"</c:if>>亚美尼亚（374）</option>
						<option value="247" <c:if test ="${areaCode =='247'}">selected="selected"</c:if>>阿森松（247）</option>
						<option value="61" <c:if test ="${areaCode =='61'}">selected="selected"</c:if>>澳大利亚（61）</option>
						<option value="43" <c:if test ="${areaCode =='43'}">selected="selected"</c:if>>奥地利（43）</option>
						<option value="994" <c:if test ="${areaCode =='994'}">selected="selected"</c:if>>阿塞拜疆（994）</option>
						<option value="1242" <c:if test ="${areaCode =='1242'}">selected="selected"</c:if>>巴哈马（1242）</option>
						<option value="973" <c:if test ="${areaCode =='973'}">selected="selected"</c:if>>巴林（973）</option>
						<option value="880" <c:if test ="${areaCode =='880'}">selected="selected"</c:if>>孟加拉国（880）</option>
						<option value="1246" <c:if test ="${areaCode =='1246'}">selected="selected"</c:if>>巴巴多斯（1246）</option>
						<option value="375" <c:if test ="${areaCode =='375'}">selected="selected"</c:if>>白俄罗斯（375）</option>
						<option value="32" <c:if test ="${areaCode =='32'}">selected="selected"</c:if>>比利时（32）</option>
						<option value="501" <c:if test ="${areaCode =='501'}">selected="selected"</c:if>>伯利兹（501）</option>
						<option value="229" <c:if test ="${areaCode =='229'}">selected="selected"</c:if>>贝宁（229）</option>
						<option value="1441" <c:if test ="${areaCode =='1441'}">selected="selected"</c:if>>百慕大群岛（1441）</option>
						<option value="591" <c:if test ="${areaCode =='591'}">selected="selected"</c:if>>玻利维亚（591）</option>
						<option value="267" <c:if test ="${areaCode =='267'}">selected="selected"</c:if>>博茨瓦纳（267）</option>
						<option value="55" <c:if test ="${areaCode =='55'}">selected="selected"</c:if>>巴西（55）</option>
						<option value="673" <c:if test ="${areaCode =='673'}">selected="selected"</c:if>>文莱（673）</option>
						<option value="359" <c:if test ="${areaCode =='359'}">selected="selected"</c:if>>保加利亚（359）</option>
						<option value="226" <c:if test ="${areaCode =='226'}">selected="selected"</c:if>>布基纳法索（226）</option>
						<option value="95" <c:if test ="${areaCode =='95'}">selected="selected"</c:if>>缅甸（95）</option>
						<option value="257" <c:if test ="${areaCode =='257'}">selected="selected"</c:if>>布隆迪（257）</option>
						<option value="237" <c:if test ="${areaCode =='237'}">selected="selected"</c:if>>喀麦隆（237）</option>
						<option value="1" <c:if test ="${areaCode =='1'}">selected="selected"</c:if>>加拿大（1）</option>
						<option value="1345" <c:if test ="${areaCode =='1345'}">selected="selected"</c:if>>开曼群岛（1345）</option>
						<option value="236" <c:if test ="${areaCode =='236'}">selected="selected"</c:if>>中非共和国（236）</option>
						<option value="235" <c:if test ="${areaCode =='235'}">selected="selected"</c:if>>乍得（235）</option>
						<option value="56" <c:if test ="${areaCode =='56'}">selected="selected"</c:if>>智利（56）</option>
						<option value="86" <c:if test ="${areaCode =='86'}">selected="selected"</c:if>>中国（86）</option>
						<option value="57" <c:if test ="${areaCode =='57'}">selected="selected"</c:if>>哥伦比亚（57）</option>
						<option value="242" <c:if test ="${areaCode =='242'}">selected="selected"</c:if>>刚果（242）</option>
						<option value="682" <c:if test ="${areaCode =='682'}">selected="selected"</c:if>>库克群岛（682）</option>
						<option value="506" <c:if test ="${areaCode =='506'}">selected="selected"</c:if>>哥斯达黎加（506）</option>
						<option value="53" <c:if test ="${areaCode =='53'}">selected="selected"</c:if>>古巴（53）</option>
						<option value="357" <c:if test ="${areaCode =='357'}">selected="selected"</c:if>>塞浦路斯（357）</option>
						<option value="420" <c:if test ="${areaCode =='420'}">selected="selected"</c:if>>捷克（420）</option>
						<option value="45" <c:if test ="${areaCode =='45'}">selected="selected"</c:if>>丹麦（45）</option>
						<option value="253" <c:if test ="${areaCode =='253'}">selected="selected"</c:if>>吉布提（253）</option>
						<option value="1890" <c:if test ="${areaCode =='1890'}">selected="selected"</c:if>>多米尼加共和国（1890）</option>
						<option value="593" <c:if test ="${areaCode =='593'}">selected="selected"</c:if>>厄瓜多尔（593）</option>
						<option value="20" <c:if test ="${areaCode =='20'}">selected="selected"</c:if>>埃及（20）</option>
						<option value="503" <c:if test ="${areaCode =='503'}">selected="selected"</c:if>>萨尔瓦多（503）</option>
						<option value="372" <c:if test ="${areaCode =='372'}">selected="selected"</c:if>>爱沙尼亚（372）</option>
						<option value="251" <c:if test ="${areaCode =='251'}">selected="selected"</c:if>>埃塞俄比亚（251）</option>
						<option value="679" <c:if test ="${areaCode =='679'}">selected="selected"</c:if>>斐济（679）</option>
						<option value="358" <c:if test ="${areaCode =='358'}">selected="selected"</c:if>>芬兰（358）</option>
						<option value="33" <c:if test ="${areaCode =='33'}">selected="selected"</c:if>>法国（33）</option>
						<option value="594" <c:if test ="${areaCode =='594'}">selected="selected"</c:if>>法属圭亚那（594）</option>
						<option value="241" <c:if test ="${areaCode =='241'}">selected="selected"</c:if>>加蓬（241）</option>
						<option value="220" <c:if test ="${areaCode =='220'}">selected="selected"</c:if>>冈比亚（220）</option>
						<option value="995" <c:if test ="${areaCode =='995'}">selected="selected"</c:if>>格鲁吉亚（995）</option>
						<option value="49" <c:if test ="${areaCode =='49'}">selected="selected"</c:if>>德国（49）</option>
						<option value="233" <c:if test ="${areaCode =='233'}">selected="selected"</c:if>>加纳（233）</option>
						<option value="350" <c:if test ="${areaCode =='350'}">selected="selected"</c:if>>直布罗陀（350）</option>
						<option value="30" <c:if test ="${areaCode =='30'}">selected="selected"</c:if>>希腊（30）</option>
						<option value="1809" <c:if test ="${areaCode =='1809'}">selected="selected"</c:if>>格林纳达（1809）</option>
						<option value="1671" <c:if test ="${areaCode =='1671'}">selected="selected"</c:if>>关岛（1671）</option>
						<option value="502" <c:if test ="${areaCode =='502'}">selected="selected"</c:if>>危地马拉（502）</option>
						<option value="224" <c:if test ="${areaCode =='224'}">selected="selected"</c:if>>几内亚（224）</option>
						<option value="592" <c:if test ="${areaCode =='592'}">selected="selected"</c:if>>圭亚那（592）</option>
						<option value="509" <c:if test ="${areaCode =='509'}">selected="selected"</c:if>>海地（509）</option>
						<option value="504" <c:if test ="${areaCode =='504'}">selected="selected"</c:if>>洪都拉斯（504）</option>
						<option value="852" <c:if test ="${areaCode =='852'}">selected="selected"</c:if>>香港（852）</option>
						<option value="36" <c:if test ="${areaCode =='36'}">selected="selected"</c:if>>匈牙利（36）</option>
						<option value="354" <c:if test ="${areaCode =='354'}">selected="selected"</c:if>>冰岛（354）</option>
						<option value="91" <c:if test ="${areaCode =='91'}">selected="selected"</c:if>>印度（91）</option>
						<option value="62" <c:if test ="${areaCode =='62'}">selected="selected"</c:if>>印度尼西亚（62）</option>
						<option value="98" <c:if test ="${areaCode =='98'}">selected="selected"</c:if>>伊朗（98）</option>
						<option value="964" <c:if test ="${areaCode =='964'}">selected="selected"</c:if>>伊拉克（964）</option>
						<option value="353" <c:if test ="${areaCode =='353'}">selected="selected"</c:if>>爱尔兰（353）</option>
						<option value="972" <c:if test ="${areaCode =='972'}">selected="selected"</c:if>>以色列（972）</option>
						<option value="39" <c:if test ="${areaCode =='39'}">selected="selected"</c:if>>意大利（39）</option>
						<option value="225" <c:if test ="${areaCode =='225'}">selected="selected"</c:if>>科特迪瓦（225）</option>
						<option value="1876" <c:if test ="${areaCode =='1876'}">selected="selected"</c:if>>牙买加（1876）</option>
						<option value="81" <c:if test ="${areaCode =='81'}">selected="selected"</c:if>>日本（81）</option>
						<option value="962" <c:if test ="${areaCode =='962'}">selected="selected"</c:if>>约旦（962）</option>
						<option value="855" <c:if test ="${areaCode =='855'}">selected="selected"</c:if>>柬埔寨（855）</option>
						<option value="327" <c:if test ="${areaCode =='327'}">selected="selected"</c:if>>哈萨克斯坦（327）</option>
						<option value="254" <c:if test ="${areaCode =='254'}">selected="selected"</c:if>>肯尼亚（254）</option>
						<option value="82" <c:if test ="${areaCode =='82'}">selected="selected"</c:if>>韩国（82）</option>
						<option value="965" <c:if test ="${areaCode =='965'}">selected="selected"</c:if>>科威特（965）</option>
						<option value="331" <c:if test ="${areaCode =='331'}">selected="selected"</c:if>>吉尔吉斯坦（331）</option>
						<option value="856" <c:if test ="${areaCode =='856'}">selected="selected"</c:if>>老挝（856）</option>
						<option value="371" <c:if test ="${areaCode =='371'}">selected="selected"</c:if>>拉脱维亚（371）</option>
						<option value="961" <c:if test ="${areaCode =='961'}">selected="selected"</c:if>>黎巴嫩（961）</option>
						<option value="266" <c:if test ="${areaCode =='266'}">selected="selected"</c:if>>莱索托（266）</option>
						<option value="231" <c:if test ="${areaCode =='231'}">selected="selected"</c:if>>利比里亚（231）</option>
						<option value="218" <c:if test ="${areaCode =='218'}">selected="selected"</c:if>>利比亚（218）</option>
						<option value="423" <c:if test ="${areaCode =='423'}">selected="selected"</c:if>>列支敦士登（423）</option>
						<option value="370" <c:if test ="${areaCode =='370'}">selected="selected"</c:if>>立陶宛（370）</option>
						<option value="352" <c:if test ="${areaCode =='352'}">selected="selected"</c:if>>卢森堡（352）</option>
						<option value="853" <c:if test ="${areaCode =='853'}">selected="selected"</c:if>>澳门（853）</option>
						<option value="261" <c:if test ="${areaCode =='261'}">selected="selected"</c:if>>马达加斯加（261）</option>
						<option value="265" <c:if test ="${areaCode =='265'}">selected="selected"</c:if>>马拉维（265）</option>
						<option value="60" <c:if test ="${areaCode =='60'}">selected="selected"</c:if>>马来西亚（60）</option>
						<option value="960" <c:if test ="${areaCode =='960'}">selected="selected"</c:if>>马尔代夫（960）</option>
						<option value="223" <c:if test ="${areaCode =='223'}">selected="selected"</c:if>>马里（223）</option>
						<option value="356" <c:if test ="${areaCode =='356'}">selected="selected"</c:if>>马耳他（356）</option>
						<option value="1670" <c:if test ="${areaCode =='1670'}">selected="selected"</c:if>>马里亚那群岛（1670）</option>
						<option value="596" <c:if test ="${areaCode =='596'}">selected="selected"</c:if>>马提尼克（596）</option>
						<option value="230" <c:if test ="${areaCode =='230'}">selected="selected"</c:if>>毛里求斯（230）</option>
						<option value="52" <c:if test ="${areaCode =='52'}">selected="selected"</c:if>>墨西哥（52）</option>
						<option value="373" <c:if test ="${areaCode =='373'}">selected="selected"</c:if>>摩尔多瓦（373）</option>
						<option value="377" <c:if test ="${areaCode =='377'}">selected="selected"</c:if>>摩纳哥（377）</option>
						<option value="976" <c:if test ="${areaCode =='976'}">selected="selected"</c:if>>蒙古（976）</option>
						<option value="1664" <c:if test ="${areaCode =='1664'}">selected="selected"</c:if>>蒙特塞拉特岛（1664）</option>
						<option value="212" <c:if test ="${areaCode =='212'}">selected="selected"</c:if>>摩洛哥（212）</option>
						<option value="258" <c:if test ="${areaCode =='258'}">selected="selected"</c:if>>莫桑比克（258）</option>
						<option value="264" <c:if test ="${areaCode =='264'}">selected="selected"</c:if>>纳米比亚（264）</option>
						<option value="674" <c:if test ="${areaCode =='674'}">selected="selected"</c:if>>瑙鲁（674）</option>
						<option value="977" <c:if test ="${areaCode =='977'}">selected="selected"</c:if>>尼泊尔（977）</option>
						<option value="599" <c:if test ="${areaCode =='599'}">selected="selected"</c:if>>荷属安的列斯（599）</option>
						<option value="31" <c:if test ="${areaCode =='31'}">selected="selected"</c:if>>荷兰（31）</option>
						<option value="64" <c:if test ="${areaCode =='64'}">selected="selected"</c:if>>新西兰（64）</option>
						<option value="505" <c:if test ="${areaCode =='505'}">selected="selected"</c:if>>尼加拉瓜（505）</option>
						<option value="227" <c:if test ="${areaCode =='227'}">selected="selected"</c:if>>尼日尔（227）</option>
						<option value="234" <c:if test ="${areaCode =='234'}">selected="selected"</c:if>>尼日利亚（234）</option>
						<option value="850" <c:if test ="${areaCode =='850'}">selected="selected"</c:if>>朝鲜（850）</option>
						<option value="47" <c:if test ="${areaCode =='47'}">selected="selected"</c:if>>挪威（47）</option>
						<option value="968" <c:if test ="${areaCode =='968'}">selected="selected"</c:if>>阿曼（968）</option>
						<option value="92" <c:if test ="${areaCode =='92'}">selected="selected"</c:if>>巴基斯坦（92）</option>
						<option value="507" <c:if test ="${areaCode =='507'}">selected="selected"</c:if>>巴拿马（507）</option>
						<option value="675" <c:if test ="${areaCode =='675'}">selected="selected"</c:if>>巴布亚新几内亚（675）</option>
						<option value="595" <c:if test ="${areaCode =='595'}">selected="selected"</c:if>>巴拉圭（595）</option>
						<option value="51" <c:if test ="${areaCode =='51'}">selected="selected"</c:if>>秘鲁（51）</option>
						<option value="63" <c:if test ="${areaCode =='63'}">selected="selected"</c:if>>菲律宾（63）</option>
						<option value="48" <c:if test ="${areaCode =='48'}">selected="selected"</c:if>>波兰（48）</option>
						<option value="689" <c:if test ="${areaCode =='689'}">selected="selected"</c:if>>法属玻利尼西亚（689）</option>
						<option value="351" <c:if test ="${areaCode =='351'}">selected="selected"</c:if>>葡萄牙（351）</option>
						<option value="1787" <c:if test ="${areaCode =='1787'}">selected="selected"</c:if>>波多黎各（1787）</option>
						<option value="974" <c:if test ="${areaCode =='974'}">selected="selected"</c:if>>卡塔尔（974）</option>
						<option value="262" <c:if test ="${areaCode =='262'}">selected="selected"</c:if>>留尼旺（262）</option>
						<option value="40" <c:if test ="${areaCode =='40'}">selected="selected"</c:if>>罗马尼亚（40）</option>
						<option value="7" <c:if test ="${areaCode =='7'}">selected="selected"</c:if>>俄罗斯（7）</option>
						<option value="1758" <c:if test ="${areaCode =='1758'}">selected="selected"</c:if>>圣卢西亚（1758）</option>
						<option value="1784" <c:if test ="${areaCode =='1784'}">selected="selected"</c:if>>圣文森特岛（1784）</option>
						<option value="684" <c:if test ="${areaCode =='684'}">selected="selected"</c:if>>东萨摩亚（美)（684）</option>
						<option value="685" <c:if test ="${areaCode =='685'}">selected="selected"</c:if>>西萨摩亚（685）</option>
						<option value="378" <c:if test ="${areaCode =='378'}">selected="selected"</c:if>>圣马力诺（378）</option>
						<option value="239" <c:if test ="${areaCode =='239'}">selected="selected"</c:if>>圣多美和普林西比（239）</option>
						<option value="966" <c:if test ="${areaCode =='966'}">selected="selected"</c:if>>沙特阿拉伯（966）</option>
						<option value="221" <c:if test ="${areaCode =='221'}">selected="selected"</c:if>>塞内加尔（221）</option>
						<option value="248" <c:if test ="${areaCode =='248'}">selected="selected"</c:if>>塞舌尔（248）</option>
						<option value="232" <c:if test ="${areaCode =='232'}">selected="selected"</c:if>>塞拉利昂（232）</option>
						<option value="65" <c:if test ="${areaCode =='65'}">selected="selected"</c:if>>新加坡（65）</option>
						<option value="421" <c:if test ="${areaCode =='421'}">selected="selected"</c:if>>斯洛伐克（421）</option>
						<option value="386" <c:if test ="${areaCode =='386'}">selected="selected"</c:if>>斯洛文尼亚（386）</option>
						<option value="677" <c:if test ="${areaCode =='677'}">selected="selected"</c:if>>所罗门群岛（677）</option>
						<option value="252" <c:if test ="${areaCode =='252'}">selected="selected"</c:if>>索马里（252）</option>
						<option value="27" <c:if test ="${areaCode =='27'}">selected="selected"</c:if>>南非（27）</option>
						<option value="34" <c:if test ="${areaCode =='34'}">selected="selected"</c:if>>西班牙（34）</option>
						<option value="94" <c:if test ="${areaCode =='94'}">selected="selected"</c:if>>斯里兰卡（94）</option>
						<option value="1758" <c:if test ="${areaCode =='1758'}">selected="selected"</c:if>>圣卢西亚（1758）</option>
						<option value="1784" <c:if test ="${areaCode =='1784'}">selected="selected"</c:if>>圣文森特（1784）</option>
						<option value="249" <c:if test ="${areaCode =='249'}">selected="selected"</c:if>>苏丹（249）</option>
						<option value="597" <c:if test ="${areaCode =='597'}">selected="selected"</c:if>>苏里南（597）</option>
						<option value="268" <c:if test ="${areaCode =='268'}">selected="selected"</c:if>>斯威士兰（268）</option>
						<option value="46" <c:if test ="${areaCode =='46'}">selected="selected"</c:if>>瑞典（46）</option>
						<option value="41" <c:if test ="${areaCode =='41'}">selected="selected"</c:if>>瑞士（41）</option>
						<option value="963" <c:if test ="${areaCode =='963'}">selected="selected"</c:if>>叙利亚（963）</option>
						<option value="886" <c:if test ="${areaCode =='886'}">selected="selected"</c:if>>台湾省（886）</option>
						<option value="992" <c:if test ="${areaCode =='992'}">selected="selected"</c:if>>塔吉克斯坦（992）</option>
						<option value="255" <c:if test ="${areaCode =='255'}">selected="selected"</c:if>>坦桑尼亚（255）</option>
						<option value="66" <c:if test ="${areaCode =='66'}">selected="selected"</c:if>>泰国（66）</option>
						<option value="228" <c:if test ="${areaCode =='228'}">selected="selected"</c:if>>多哥（228）</option>
						<option value="676" <c:if test ="${areaCode =='676'}">selected="selected"</c:if>>汤加（676）</option>
						<option value="1809" <c:if test ="${areaCode =='1809'}">selected="selected"</c:if>>特立尼达和多巴哥（1809）</option>
						<option value="216" <c:if test ="${areaCode =='216'}">selected="selected"</c:if>>突尼斯（216）</option>
						<option value="90" <c:if test ="${areaCode =='90'}">selected="selected"</c:if>>土耳其（90）</option>
						<option value="993" <c:if test ="${areaCode =='993'}">selected="selected"</c:if>>土库曼斯坦（993）</option>
						<option value="256" <c:if test ="${areaCode =='256'}">selected="selected"</c:if>>乌干达（256）</option>
						<option value="380" <c:if test ="${areaCode =='380'}">selected="selected"</c:if>>乌克兰（380）</option>
						<option value="971" <c:if test ="${areaCode =='971'}">selected="selected"</c:if>>阿拉伯联合酋长国（971）</option>
						<option value="44" <c:if test ="${areaCode =='44'}">selected="selected"</c:if>>英国（44）</option>
						<option value="1" <c:if test ="${areaCode =='1'}">selected="selected"</c:if>>美国（1）</option>
						<option value="598" <c:if test ="${areaCode =='598'}">selected="selected"</c:if>>乌拉圭（598）</option>
						<option value="233" <c:if test ="${areaCode =='233'}">selected="selected"</c:if>>乌兹别克斯坦（233）</option>
						<option value="58" <c:if test ="${areaCode =='58'}">selected="selected"</c:if>>委内瑞拉（58）</option>
						<option value="84" <c:if test ="${areaCode =='84'}">selected="selected"</c:if>>越南（84）</option>
						<option value="967" <c:if test ="${areaCode =='967'}">selected="selected"</c:if>>也门（967）</option>
						<option value="381" <c:if test ="${areaCode =='381'}">selected="selected"</c:if>>南斯拉夫（381）</option>
						<option value="27" <c:if test ="${areaCode =='27'}">selected="selected"</c:if>>南非（27）</option>
						<option value="263" <c:if test ="${areaCode =='263'}">selected="selected"</c:if>>津巴布韦（263）</option>
						<option value="243" <c:if test ="${areaCode =='243'}">selected="selected"</c:if>>扎伊尔（243）</option>
						<option value="260" <c:if test ="${areaCode =='260'}">selected="selected"</c:if>>赞比亚（260）</option>
					</select>
				</li>
				<li>
					<label>&nbsp;</label>
					<input type="submit" class="scbtn btn" value="查询" onclick="initPageNo()"/>
				</li>
		</ul>
		<s:include value="../../../include/buttonTop.jsp"/>
		<table class="tablelist">
			<thead>
				<tr>
					<th><input name = "selectCheckbox" id = "selectCheckbox" type="checkbox" value=""/></th>
					<th>地区编号白名单</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="page.result" var="list">
					<tr>
								<td><input name="checkbox_list" id="checkbox_list" type="checkbox" onclick="selectCheckBoxList()" value="<s:property value="#list.white_areacode_id" />"/></td>
<%-- 								<td><s:property value="#list.white_areacode_id" /></td> --%>
								<td>
									<c:choose>
						    			<c:when test="${list.area_code =='244'}">安哥拉（244）</c:when>
										<c:when test="${list.area_code =='93'}">阿富汗（93）</c:when>
										<c:when test="${list.area_code =='355'}">阿尔巴尼亚（355）</c:when>
										<c:when test="${list.area_code =='213'}">阿尔及利亚（213）</c:when>
										<c:when test="${list.area_code =='376'}">安道尔共和国（376）</c:when>
										<c:when test="${list.area_code =='1264'}">安圭拉岛（1264）</c:when>
										<c:when test="${list.area_code =='1268'}">安提瓜和巴布达（1268）</c:when>
										<c:when test="${list.area_code =='54'}">阿根廷（54）</c:when>
										<c:when test="${list.area_code =='374'}">亚美尼亚（374）</c:when>
										<c:when test="${list.area_code =='247'}">阿森松（247）</c:when>
										<c:when test="${list.area_code =='61'}">澳大利亚（61）</c:when>
										<c:when test="${list.area_code =='43'}">奥地利（43）</c:when>
										<c:when test="${list.area_code =='994'}">阿塞拜疆（994）</c:when>
										<c:when test="${list.area_code =='1242'}">巴哈马（1242）</c:when>
										<c:when test="${list.area_code =='973'}">巴林（973）</c:when>
										<c:when test="${list.area_code =='880'}">孟加拉国（880）</c:when>
										<c:when test="${list.area_code =='1246'}">巴巴多斯（1246）</c:when>
										<c:when test="${list.area_code =='375'}">白俄罗斯（375）</c:when>
										<c:when test="${list.area_code =='32'}">比利时（32）</c:when>
										<c:when test="${list.area_code =='501'}">伯利兹（501）</c:when>
										<c:when test="${list.area_code =='229'}">贝宁（229）</c:when>
										<c:when test="${list.area_code =='1441'}">百慕大群岛（1441）</c:when>
										<c:when test="${list.area_code =='591'}">玻利维亚（591）</c:when>
										<c:when test="${list.area_code =='267'}">博茨瓦纳（267）</c:when>
										<c:when test="${list.area_code =='55'}">巴西（55）</c:when>
										<c:when test="${list.area_code =='673'}">文莱（673）</c:when>
										<c:when test="${list.area_code =='359'}">保加利亚（359）</c:when>
										<c:when test="${list.area_code =='226'}">布基纳法索（226）</c:when>
										<c:when test="${list.area_code =='95'}">缅甸（95）</c:when>
										<c:when test="${list.area_code =='257'}">布隆迪（257）</c:when>
										<c:when test="${list.area_code =='237'}">喀麦隆（237）</c:when>
										<c:when test="${list.area_code =='1'}">加拿大（1）</c:when>
										<c:when test="${list.area_code =='1345'}">开曼群岛（1345）</c:when>
										<c:when test="${list.area_code =='236'}">中非共和国（236）</c:when>
										<c:when test="${list.area_code =='235'}">乍得（235）</c:when>
										<c:when test="${list.area_code =='56'}">智利（56）</c:when>
										<c:when test="${list.area_code =='86'}">中国（86）</c:when>
										<c:when test="${list.area_code =='57'}">哥伦比亚（57）</c:when>
										<c:when test="${list.area_code =='242'}">刚果（242）</c:when>
										<c:when test="${list.area_code =='682'}">库克群岛（682）</c:when>
										<c:when test="${list.area_code =='506'}">哥斯达黎加（506）</c:when>
										<c:when test="${list.area_code =='53'}">古巴（53）</c:when>
										<c:when test="${list.area_code =='357'}">塞浦路斯（357）</c:when>
										<c:when test="${list.area_code =='420'}">捷克（420）</c:when>
										<c:when test="${list.area_code =='45'}">丹麦（45）</c:when>
										<c:when test="${list.area_code =='253'}">吉布提（253）</c:when>
										<c:when test="${list.area_code =='1890'}">多米尼加共和国（1890）</c:when>
										<c:when test="${list.area_code =='593'}">厄瓜多尔（593）</c:when>
										<c:when test="${list.area_code =='20'}">埃及（20）</c:when>
										<c:when test="${list.area_code =='503'}">萨尔瓦多（503）</c:when>
										<c:when test="${list.area_code =='372'}">爱沙尼亚（372）</c:when>
										<c:when test="${list.area_code =='251'}">埃塞俄比亚（251）</c:when>
										<c:when test="${list.area_code =='679'}">斐济（679）</c:when>
										<c:when test="${list.area_code =='358'}">芬兰（358）</c:when>
										<c:when test="${list.area_code =='33'}">法国（33）</c:when>
										<c:when test="${list.area_code =='594'}">法属圭亚那（594）</c:when>
										<c:when test="${list.area_code =='241'}">加蓬（241）</c:when>
										<c:when test="${list.area_code =='220'}">冈比亚（220）</c:when>
										<c:when test="${list.area_code =='995'}">格鲁吉亚（995）</c:when>
										<c:when test="${list.area_code =='49'}">德国（49）</c:when>
										<c:when test="${list.area_code =='233'}">加纳（233）</c:when>
										<c:when test="${list.area_code =='350'}">直布罗陀（350）</c:when>
										<c:when test="${list.area_code =='30'}">希腊（30）</c:when>
										<c:when test="${list.area_code =='1809'}">格林纳达（1809）</c:when>
										<c:when test="${list.area_code =='1671'}">关岛（1671）</c:when>
										<c:when test="${list.area_code =='502'}">危地马拉（502）</c:when>
										<c:when test="${list.area_code =='224'}">几内亚（224）</c:when>
										<c:when test="${list.area_code =='592'}">圭亚那（592）</c:when>
										<c:when test="${list.area_code =='509'}">海地（509）</c:when>
										<c:when test="${list.area_code =='504'}">洪都拉斯（504）</c:when>
										<c:when test="${list.area_code =='852'}">香港（852）</c:when>
										<c:when test="${list.area_code =='36'}">匈牙利（36）</c:when>
										<c:when test="${list.area_code =='354'}">冰岛（354）</c:when>
										<c:when test="${list.area_code =='91'}">印度（91）</c:when>
										<c:when test="${list.area_code =='62'}">印度尼西亚（62）</c:when>
										<c:when test="${list.area_code =='98'}">伊朗（98）</c:when>
										<c:when test="${list.area_code =='964'}">伊拉克（964）</c:when>
										<c:when test="${list.area_code =='353'}">爱尔兰（353）</c:when>
										<c:when test="${list.area_code =='972'}">以色列（972）</c:when>
										<c:when test="${list.area_code =='39'}">意大利（39）</c:when>
										<c:when test="${list.area_code =='225'}">科特迪瓦（225）</c:when>
										<c:when test="${list.area_code =='1876'}">牙买加（1876）</c:when>
										<c:when test="${list.area_code =='81'}">日本（81）</c:when>
										<c:when test="${list.area_code =='962'}">约旦（962）</c:when>
										<c:when test="${list.area_code =='855'}">柬埔寨（855）</c:when>
										<c:when test="${list.area_code =='327'}">哈萨克斯坦（327）</c:when>
										<c:when test="${list.area_code =='254'}">肯尼亚（254）</c:when>
										<c:when test="${list.area_code =='82'}">韩国（82）</c:when>
										<c:when test="${list.area_code =='965'}">科威特（965）</c:when>
										<c:when test="${list.area_code =='331'}">吉尔吉斯坦（331）</c:when>
										<c:when test="${list.area_code =='856'}">老挝（856）</c:when>
										<c:when test="${list.area_code =='371'}">拉脱维亚（371）</c:when>
										<c:when test="${list.area_code =='961'}">黎巴嫩（961）</c:when>
										<c:when test="${list.area_code =='266'}">莱索托（266）</c:when>
										<c:when test="${list.area_code =='231'}">利比里亚（231）</c:when>
										<c:when test="${list.area_code =='218'}">利比亚（218）</c:when>
										<c:when test="${list.area_code =='423'}">列支敦士登（423）</c:when>
										<c:when test="${list.area_code =='370'}">立陶宛（370）</c:when>
										<c:when test="${list.area_code =='352'}">卢森堡（352）</c:when>
										<c:when test="${list.area_code =='853'}">澳门（853）</c:when>
										<c:when test="${list.area_code =='261'}">马达加斯加（261）</c:when>
										<c:when test="${list.area_code =='265'}">马拉维（265）</c:when>
										<c:when test="${list.area_code =='60'}">马来西亚（60）</c:when>
										<c:when test="${list.area_code =='960'}">马尔代夫（960）</c:when>
										<c:when test="${list.area_code =='223'}">马里（223）</c:when>
										<c:when test="${list.area_code =='356'}">马耳他（356）</c:when>
										<c:when test="${list.area_code =='1670'}">马里亚那群岛（1670）</c:when>
										<c:when test="${list.area_code =='596'}">马提尼克（596）</c:when>
										<c:when test="${list.area_code =='230'}">毛里求斯（230）</c:when>
										<c:when test="${list.area_code =='52'}">墨西哥（52）</c:when>
										<c:when test="${list.area_code =='373'}">摩尔多瓦（373）</c:when>
										<c:when test="${list.area_code =='377'}">摩纳哥（377）</c:when>
										<c:when test="${list.area_code =='976'}">蒙古（976）</c:when>
										<c:when test="${list.area_code =='1664'}">蒙特塞拉特岛（1664）</c:when>
										<c:when test="${list.area_code =='212'}">摩洛哥（212）</c:when>
										<c:when test="${list.area_code =='258'}">莫桑比克（258）</c:when>
										<c:when test="${list.area_code =='264'}">纳米比亚（264）</c:when>
										<c:when test="${list.area_code =='674'}">瑙鲁（674）</c:when>
										<c:when test="${list.area_code =='977'}">尼泊尔（977）</c:when>
										<c:when test="${list.area_code =='599'}">荷属安的列斯（599）</c:when>
										<c:when test="${list.area_code =='31'}">荷兰（31）</c:when>
										<c:when test="${list.area_code =='64'}">新西兰（64）</c:when>
										<c:when test="${list.area_code =='505'}">尼加拉瓜（505）</c:when>
										<c:when test="${list.area_code =='227'}">尼日尔（227）</c:when>
										<c:when test="${list.area_code =='234'}">尼日利亚（234）</c:when>
										<c:when test="${list.area_code =='850'}">朝鲜（850）</c:when>
										<c:when test="${list.area_code =='47'}">挪威（47）</c:when>
										<c:when test="${list.area_code =='968'}">阿曼（968）</c:when>
										<c:when test="${list.area_code =='92'}">巴基斯坦（92）</c:when>
										<c:when test="${list.area_code =='507'}">巴拿马（507）</c:when>
										<c:when test="${list.area_code =='675'}">巴布亚新几内亚（675）</c:when>
										<c:when test="${list.area_code =='595'}">巴拉圭（595）</c:when>
										<c:when test="${list.area_code =='51'}">秘鲁（51）</c:when>
										<c:when test="${list.area_code =='63'}">菲律宾（63）</c:when>
										<c:when test="${list.area_code =='48'}">波兰（48）</c:when>
										<c:when test="${list.area_code =='689'}">法属玻利尼西亚（689）</c:when>
										<c:when test="${list.area_code =='351'}">葡萄牙（351）</c:when>
										<c:when test="${list.area_code =='1787'}">波多黎各（1787）</c:when>
										<c:when test="${list.area_code =='974'}">卡塔尔（974）</c:when>
										<c:when test="${list.area_code =='262'}">留尼旺（262）</c:when>
										<c:when test="${list.area_code =='40'}">罗马尼亚（40）</c:when>
										<c:when test="${list.area_code =='7'}">俄罗斯（7）</c:when>
										<c:when test="${list.area_code =='1758'}">圣卢西亚（1758）</c:when>
										<c:when test="${list.area_code =='1784'}">圣文森特岛（1784）</c:when>
										<c:when test="${list.area_code =='684'}">东萨摩亚（美)（684）</c:when>
										<c:when test="${list.area_code =='685'}">西萨摩亚（685）</c:when>
										<c:when test="${list.area_code =='378'}">圣马力诺（378）</c:when>
										<c:when test="${list.area_code =='239'}">圣多美和普林西比（239）</c:when>
										<c:when test="${list.area_code =='966'}">沙特阿拉伯（966）</c:when>
										<c:when test="${list.area_code =='221'}">塞内加尔（221）</c:when>
										<c:when test="${list.area_code =='248'}">塞舌尔（248）</c:when>
										<c:when test="${list.area_code =='232'}">塞拉利昂（232）</c:when>
										<c:when test="${list.area_code =='65'}">新加坡（65）</c:when>
										<c:when test="${list.area_code =='421'}">斯洛伐克（421）</c:when>
										<c:when test="${list.area_code =='386'}">斯洛文尼亚（386）</c:when>
										<c:when test="${list.area_code =='677'}">所罗门群岛（677）</c:when>
										<c:when test="${list.area_code =='252'}">索马里（252）</c:when>
										<c:when test="${list.area_code =='27'}">南非（27）</c:when>
										<c:when test="${list.area_code =='34'}">西班牙（34）</c:when>
										<c:when test="${list.area_code =='94'}">斯里兰卡（94）</c:when>
										<c:when test="${list.area_code =='1758'}">圣卢西亚（1758）</c:when>
										<c:when test="${list.area_code =='1784'}">圣文森特（1784）</c:when>
										<c:when test="${list.area_code =='249'}">苏丹（249）</c:when>
										<c:when test="${list.area_code =='597'}">苏里南（597）</c:when>
										<c:when test="${list.area_code =='268'}">斯威士兰（268）</c:when>
										<c:when test="${list.area_code =='46'}">瑞典（46）</c:when>
										<c:when test="${list.area_code =='41'}">瑞士（41）</c:when>
										<c:when test="${list.area_code =='963'}">叙利亚（963）</c:when>
										<c:when test="${list.area_code =='886'}">台湾省（886）</c:when>
										<c:when test="${list.area_code =='992'}">塔吉克斯坦（992）</c:when>
										<c:when test="${list.area_code =='255'}">坦桑尼亚（255）</c:when>
										<c:when test="${list.area_code =='66'}">泰国（66）</c:when>
										<c:when test="${list.area_code =='228'}">多哥（228）</c:when>
										<c:when test="${list.area_code =='676'}">汤加（676）</c:when>
										<c:when test="${list.area_code =='1809'}">特立尼达和多巴哥（1809）</c:when>
										<c:when test="${list.area_code =='216'}">突尼斯（216）</c:when>
										<c:when test="${list.area_code =='90'}">土耳其（90）</c:when>
										<c:when test="${list.area_code =='993'}">土库曼斯坦（993）</c:when>
										<c:when test="${list.area_code =='256'}">乌干达（256）</c:when>
										<c:when test="${list.area_code =='380'}">乌克兰（380）</c:when>
										<c:when test="${list.area_code =='971'}">阿拉伯联合酋长国（971）</c:when>
										<c:when test="${list.area_code =='44'}">英国（44）</c:when>
										<c:when test="${list.area_code =='1'}">美国（1）</c:when>
										<c:when test="${list.area_code =='598'}">乌拉圭（598）</c:when>
										<c:when test="${list.area_code =='233'}">乌兹别克斯坦（233）</c:when>
										<c:when test="${list.area_code =='58'}">委内瑞拉（58）</c:when>
										<c:when test="${list.area_code =='84'}">越南（84）</c:when>
										<c:when test="${list.area_code =='967'}">也门（967）</c:when>
										<c:when test="${list.area_code =='381'}">南斯拉夫（381）</c:when>
										<c:when test="${list.area_code =='27'}">南非（27）</c:when>
										<c:when test="${list.area_code =='263'}">津巴布韦（263）</c:when>
										<c:when test="${list.area_code =='243'}">扎伊尔（243）</c:when>
										<c:when test="${list.area_code =='260'}">赞比亚（260）</c:when>
						    			<c:otherwise>未知
						    			</c:otherwise>
						    		</c:choose>
								</td>
								<td>									
									<s:iterator value="buttonMenuList" var="buttonRight">
										<s:if test="%{#buttonRight.resourceLevel == 5}">
											<a href="javascript:${buttonRight.functionName}('${projectURLPrefix}${buttonRight.resourceUrl}','${list.white_areacode_id}')"  class="tablelink">${buttonRight.resourceName}</a>
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
