<%@ page contentType="text/html; charset=UTF-8"%>
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li id="navigation_Menu" id="navigation_Menu"></li>
    </ul>
    </div>

<script type="text/javascript">

$(document).ready(function(){
	$("#navigation_Menu").text(window.parent.parent.frames["leftFrame"].mainList);
});

</script>