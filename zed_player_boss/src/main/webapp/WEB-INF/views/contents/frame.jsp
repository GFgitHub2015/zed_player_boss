<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../include/resource.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%-- <link rel="shortcut icon" href="<s:property value="imagePath" />favicon.ico"/> --%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<s:property value="jsPath" />jquery.js"></script>

    <script type="text/javascript">
    <!--
    	var pageToken = <s:property value="pageToken" />;
    //-->
    </script>
</head>

<frameset rows="50,*" cols="*" frameborder="no" border="0" framespacing="0">
  <frame src="<s:url action="top" />" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" title="topFrame" />
  <frameset cols="222,*" frameborder="no" border="0" framespacing="0">
    <frame src="" name="leftFrame" id="leftFrame" title="leftFrame" />
    <frame src="" name="rightFrame" id="rightFrame" title="rightFrame" />
  </frameset>
</frameset>
<noframes><body>
</body></noframes>
</html>