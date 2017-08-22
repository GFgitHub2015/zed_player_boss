<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page autoFlush="false" buffer="500kb"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 

<s:set name="cssPath" value="#request.publicAttr.cssPath"/>
<s:set name="imagePath" value="#request.publicAttr.imagePath"/>
<s:set name="jsPath" value="#request.publicAttr.jsPath"/>
<s:set name="lang" value="%{getText('lang.folder')}"/>
<s:set name="editorPath" value="#request.publicAttr.editorPath"/>
<s:set name="themePath" value="#request.publicAttr.themePath"/>

<%@ include file="../../../title.jsp" %>