package com.zed.controller.system;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.domain.system.Admin;
import com.zed.domain.system.Resource;
import com.zed.domain.system.Role;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.service.system.resource.ResourceService;
import com.zed.service.system.role.RoleService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;
import com.zed.system.page.Page;
import com.zed.util.TreeObject;
import com.zed.util.TreeUtil;

import net.sf.json.JSONArray;

@Controller("roleAction")
@Scope(value="prototype")
public class RoleAction  extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private Page<HashMap> page = new Page<HashMap>();
	private Role role;
	private String roleId;
	List<TreeObject> node;
	private List<String> resourceId = new ArrayList<String>();
	
	@javax.annotation.Resource
	protected RoleService roleService;
	@javax.annotation.Resource
	protected ResourceService resourceService;
	
	public String list(){
		try {
			HashMap map = new HashMap();
			page.setSorting("updated_time DESC");					//排序
			if(role!=null){
				if(role.getRoleName()!=null&&!("").equals(role.getRoleName())){
					map.put("roleName", role.getRoleName());
				}
				if(role.getEnable()!=null&&!("").equals(role.getEnable())){
					map.put("enable", role.getEnable());
				}
			}
			page.setParamsMap(map);
			page = roleService.findByPage(page);
			return SUCCESS;
		} catch (Exception ex) {			
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("RoleAction[list] failed: ",ex);
			}
			return ERROR;
		}		
	}
	
	public String add(){
		Admin sesionAdmin = null;
		try {
			sesionAdmin = getSessionAdmin();
			Date newdate = new Date();
			
			Map<String, Object> map = new HashMap<String, Object>();
			String roleName = role.getRoleName().toString().trim();
			String roleKey = role.getRoleKey().toString().trim();
			map.put("roleName", roleName);
			map.put("roleKey", roleKey);
			List<Role> roleFindByNameAndKey = roleService.findRoleByParams(map);
			if (!roleFindByNameAndKey.isEmpty()) {
				setResultDispatch(GConstantAlert.GS_LTE2010,GConstantRedirect.GS_ROLE_LIST_ACTION);
				return GConstantRedirect.GS_RESULT;
			}
			
			role.setCreatedBy(sesionAdmin.getAdminId());
			role.setCreatedTime(newdate);
			roleService.add(role);
			setSuccessDispatch(GConstantAlert.GS_INSERT_SUCCESS, GConstantRedirect.GS_ROLE_LIST_ACTION);
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {			
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("RoleAction[add] failed: ",ex);
			}
			return ERROR;
		}		
	}
	
	public String addPage(){
		return SUCCESS;
	}
	
	public String updatePage(){
		try {
			role = (Role)roleService.findById(roleId);
			return SUCCESS;
		} catch (Exception ex) {			
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("RoleAction[updatePage] failed: ",ex);
			}
			return ERROR;
		}		
	}
	
	public String update()
	{
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			
			Map<String, Object> map = new HashMap<String, Object>();
			String roleName = role.getRoleName().toString().trim();
			String roleKey = role.getRoleKey().toString().trim();
			map.put("roleName", roleName);
			map.put("roleKey", roleKey);
			List<Role> roleFindByNameAndKey = roleService.findRoleByParams(map);
			Map<Long, Object> mapToCheck = new HashMap<Long, Object>();
			for (Role roleToCheck : roleFindByNameAndKey) {
				if (!(role.getRoleId().compareTo(roleToCheck.getRoleId())==0)) {
					mapToCheck.put(role.getRoleId(), role);
				}
			}
			if (!mapToCheck.isEmpty()) {
				setResultDispatch(GConstantAlert.GS_LTE2010,GConstantRedirect.GS_ROLE_LIST_ACTION);
				return GConstantRedirect.GS_RESULT;
			}
			
			role.setUpdatedBy(sessionAdmin.getAdminId());
			role.setUpdatedTime(newdate);
			roleService.update(role);			
			setSuccessDispatch(GConstantAlert.GS_UPDATE_SUCCESS, GConstantRedirect.GS_ROLE_LIST_ACTION);
			return GConstantRedirect.GS_OK;
			
		} catch (Exception ex) {			
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("RoleAction[update] failed: ",ex);
			}
			return ERROR;
		}

	}
	
	public String delete(){
		try {
			Admin sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			String[] roleIds=roleId.split(",");
			List<String> ids=Arrays.asList(roleIds);
			List<String> roleNames=new ArrayList<String>();
			for (String rId : ids) {
				Role rl=roleService.findById(rId);
				roleNames.add(rl.getRoleName());
			}
			roleService.delete(roleIds);
			setSuccessDispatch(GConstantAlert.GS_DELETE_SUCCESS, GConstantRedirect.GS_ROLE_LIST_ACTION);
			return GConstantRedirect.GS_OK;
			
		} catch (Exception ex) {			
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR,"");
				Log.getLogger(this.getClass()).error("RoleAction[delete] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	public String detail()
	{
		try {
			role = (Role)roleService.findById(roleId);
			return SUCCESS;
		} catch (Exception ex) {			
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("RoleAction[detail] failed: ",ex);
			}
			return ERROR;
		}		
	}
	
	public String rolePermissionPage(){
		try {
			List<Resource> rs =resourceService.findAll();

			node = TreeUtil.getChildResourcesByParentId(rs, 999);
						
			return SUCCESS;
		} catch (Exception ex) {			
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("RoleAction[rolePermissionPage] failed: ",ex);
			}
			return ERROR;
		}		
	}
	
	public String getRoleResource(){
		String result ="";
		JSONArray json = new JSONArray();
		try {
			List<Resource>  resourceList = resourceService.findListByRoleId(roleId);
			json.addAll(resourceList);
			result = json.toString();//给result赋值，传递给页面
		} catch (Exception ex) {			
			Log.getLogger(this.getClass()).error("RoleAction[getRoleResource] failed: ",ex);
//			ex.printStackTrace();
		}		
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html"); // 火狐浏览器必须加上这句
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(result);//直接write到前台
		} catch (IOException e) {
			e.printStackTrace();
		}  
		
		return NONE;
	}
		
	
	public String allocationRolePermission(){
		Map<String, Object> map = new HashMap<String, Object>();
		Admin sessionAdmin = null;
		try {
			if (null != resourceId && resourceId.size() > 0) {
				
				sessionAdmin = getSessionAdmin();
				String createdBy = sessionAdmin.getAdminId();
			    Date createdTime = new Date();
			    String updatedBy = sessionAdmin.getAdminId();
			    Date updatedTime = new Date();
//			    DatabaseContextHolder.setDbType(DatabaseContextHolder.DATA_SOURCE_DEFUALT);
				resourceService.addRoleResource(roleId, resourceId,createdBy, createdTime, updatedBy,updatedTime);
			}
			
			setSuccessDispatch(GConstantAlert.GS_LTE0012, GConstantRedirect.GS_ROLE_LIST_ACTION);
			return GConstantRedirect.GS_OK;
		}  catch (Exception ex) {			
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(GConstantAlert.GS_LTE0010,"");
				Log.getLogger(this.getClass()).error("RoleAction[allocationRolePermission] failed: " ,ex);
			}
			return ERROR;
		}
	}
	
	@Override
	public String doExecute(){
		return null;
	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public List<TreeObject> getNode() {
		return node;
	}

	public void setNode(List<TreeObject> node) {
		this.node = node;
	}

	public List<String> getResourceId() {
		return resourceId;
	}

	public void setResourceId(List<String> resourceId) {
		this.resourceId = resourceId;
	}
	
	
	
}
