<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../include/resource.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
	<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />permission.css"/>
	<link type="text/css" rel="stylesheet" href="<s:property value="cssPath" />style.css"/>
	<script type="text/javascript" src="<s:property value="jsPath" />jquery.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />window.js"></script>
	<script type="text/javascript" src="<s:property value="jsPath" />function.js"></script>
	
  <script type="text/javascript">
  function allocation(){
	  if($('input[name="roleId"]:checked').val()==null){
			openAlertmWindow('提示信息','请选择一条记录进行设置。','确定');
	  }else{
		   document.form.adminId.value="${admin.adminId}"
		   document.form.submit();
	  }	  
  }
  
  function back() {
		window.location = '<s:url action="list" />';
	}
  
  </script>
  
  </head>
  
  <body>
  	<%@ include file="../../include/navigation.jsp" %>
<br/>
<br/>  
<form  method="post" name="form" id="form" action="<s:url action="allocationAdminRole"/>">
<input type="hidden" name="adminId" id="adminId" value=""/>

		<table class="ttab" height="100" width="70%" border="0" cellpadding="0" cellspacing="1"
			align="center" style="margin-left:20px;">
			<tr>
				<td height="30"
					 colspan="2">
					<div align="center">
					<font color="blue" size="3" ><b>分配角色</b></font>
					</div>
				</td>
			</tr>
			<tr>	
					<td height="30"width="20%" >
						<div align="right" class="STYLE1" >
								管理员编号
						</div>
					</td>
					<td >
						<div align="left" class="STYLE1"  style="padding-left:10px;">
						${admin.adminId}
						</div>
					</td>
			</tr>
			<tr>	
					<td height="30"width="20%" >
						<div align="right" class="STYLE1" >
								所属角色
						</div>
					</td>
					<td >
						<div align="left" class="STYLE1"  style="padding-left:10px;">
						<c:if test="${empty admin.roleName}">
						<font color="red">没有分配角色</font>
						</c:if>
						<c:if test="${not empty admin.roleName}">
						<font color="blue">${admin.roleName}</font>
						</c:if>
						</div>
					</td>
			</tr>
			<tr>
				<td colspan="2" style="width:100%">
					<table  width="100%">
				      <tr>
				        <td>
				        	<table class="tablelist">
								<thead>
									<tr>
										<th></th>
										<th>角色名</th>
										<th>是否禁用</th>
										<th>描述</th>
									</tr>
								</thead>
								<tbody>
									<s:iterator value="allRole" var="list">
										<tr>
											<td>
												<input type="radio" name="roleId" <c:if test="${list.role_key eq admin.roleKey}">checked="checked"</c:if>  value="<s:property value="#list.role_id" />" />
											</td>
											<td>
												<s:property value="#list.role_name" />
											</td>
											<td>
												<c:choose>
										    			<c:when test="${list.enable == '0'}">
										    				<font style="color: red">禁用</font>
										    			</c:when>
										    			<c:otherwise>
										    				正常
										    			</c:otherwise>
										    	</c:choose>							
											</td>
											<td><s:property value="#list.description" /></td>
											</tr>				
									</s:iterator>
								</tbody>
							</table>        
				        </td>
	      			</tr>
	    		 </table>
			</td>
		</tr>
		<tr>
					<td colspan="2" style="padding: 10px">
						<div align="center">			 				
			 				<input type="button" class="btn" value="保存" onclick="allocation();"/>
			 				<input name="backBt" id="backBt" type="button" class="btn" value="返回" onclick="back()"/>
		 				</div>
					</td>
		</tr>
		</table>
</form>
  </body>
</html>
