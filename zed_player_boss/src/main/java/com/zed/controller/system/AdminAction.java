package com.zed.controller.system;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.domain.system.Admin;
import com.zed.domain.system.AdminRole;
import com.zed.domain.system.Role;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.listener.SystemConfig;
import com.zed.service.system.role.RoleService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;
import com.zed.system.page.Page;
import com.zed.util.MD5;

@Controller("adminAction")
@Scope(value = "prototype")
public class AdminAction extends BaseAction {

	private static final long serialVersionUID = 1L;
    private static final String ROLE_KEY_DEFAULT = SystemConfig.getProperty("role.key.default");
	private Page<HashMap> page = new Page<HashMap>();
	private Admin admin;
	private String adminId;
	private String oldPassword;
	private String adminPermission;
	private String roleId;
	private List<HashMap> allRole;
	private String beginTime; // 查询登录时间开始时间
	private String endTime; // 查询登录时间结束时间

	@javax.annotation.Resource
	protected RoleService roleService;

	public String list() {
		try {
			HashMap map = new HashMap();
			page.setSorting("last_login_time DESC"); // 排序
			if (admin != null) {
				if (admin.getAdminId() != null&& !("").equals(admin.getAdminId())) {
					map.put("adminId", admin.getAdminId());
				}
				if (admin.getRoleName() != null
						&& !("").equals(admin.getRoleName())) {
					map.put("roleName", admin.getRoleName());
				}
			}
			if (beginTime != null && !beginTime.equals("")) {
				map.put("beginTime", beginTime);
			}
			if (endTime != null && !endTime.equals("")) {
				map.put("endTime", endTime);
			}
			page.setParamsMap(map);
			page = adminService.findByPage(page);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("AdminAction[list] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String add() {
		Admin sessionAdmin = null;
		try {
			
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			Admin adminToCheck = adminService.findById(admin.getAdminId().toString().trim());
			if(adminToCheck != null){
				setErrorDispatch(GConstantAlert.GS_LTE2010, "");
				return ERROR;
			}
			
			String md5Pwd = MD5.getHexMD5(admin.getAdminPwd());
			admin.setAdminPwd(md5Pwd);
			if (admin!=null&&(admin.getAdminAge()==null||("").equals(admin.getAdminAge()))) {
				admin.setAdminAge("0");
			}
			admin.setCreatedBy(sessionAdmin.getAdminId());
			admin.setCreatedTime(new Timestamp(newdate.getTime()));
			admin.setUpdatedBy(sessionAdmin.getAdminId());
			admin.setUpdatedTime(new Timestamp(newdate.getTime()));
			adminService.add(admin);
			//设置默认角色
			Role role = roleService.findRoleByRoleKey(ROLE_KEY_DEFAULT);
			
			AdminRole adminRole = new AdminRole();
			adminRole.setCreatedBy(sessionAdmin.getAdminId());
			adminRole.setCreatedTime(new Date());
			adminRole.setUpdatedBy(sessionAdmin.getAdminId());
			adminRole.setUpdatedTime(new Date());
			adminRole.setAdminId(admin.getAdminId());
			adminRole.setRoleId(role.getRoleId());
			roleService.addAdminRole(admin.getAdminId(), adminRole);
			
			setSuccessDispatch(GConstantAlert.GS_INSERT_SUCCESS,GConstantRedirect.GS_ADMIN_LIST_ACTION);
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("AdminAction[add] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String addPage() {
		return SUCCESS;
	}

	public String updatePage() {
		try {
			admin = (Admin) adminService.findById(adminId);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("AdminAction[updatePage] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String update() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			admin.setUpdatedBy(sessionAdmin.getAdminId());
			admin.setUpdatedTime(new Timestamp(newdate.getTime()));
			adminService.update(admin);
			setSuccessDispatch(GConstantAlert.GS_UPDATE_SUCCESS,GConstantRedirect.GS_ADMIN_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("AdminAction[update] failed: ",ex);
			}
			return ERROR;
		}

	}

	public String delete() {
		try {
			String[] adminIds=adminId.split(",");
			adminService.delete(adminIds);
			setSuccessDispatch(GConstantAlert.GS_DELETE_SUCCESS,GConstantRedirect.GS_ADMIN_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("AdminAction[delete] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String detail() {
		try {
			admin = (Admin) adminService.findById(adminId);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("AdminAction[detail] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String adminRolePage() {
		try {
			admin = adminService.findById(adminId);
			allRole = roleService.findAll();
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("AdminAction[adminRolePage] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String allocationAdminRole() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			AdminRole adminRole = new AdminRole();
			adminRole.setCreatedBy(sessionAdmin.getAdminId());
			adminRole.setCreatedTime(new Date());
			adminRole.setUpdatedBy(sessionAdmin.getAdminId());
			adminRole.setUpdatedTime(new Date());
			adminRole.setAdminId(adminId);
			adminRole.setRoleId(Long.valueOf(roleId));
			roleService.addAdminRole(adminId, adminRole);

			setSuccessDispatch(GConstantAlert.GS_LTE0012,GConstantRedirect.GS_ADMIN_LIST_ACTION);
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(GConstantAlert.GS_LTE0010, "");
				Log.getLogger(this.getClass()).error("AdminAction[allocationAdminRole] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String resetPasswdPage(){
		try {
			admin = (Admin) adminService.findById(adminId);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("AdminAction[resetPasswdPage] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	public String resetPasswd() {
		Admin sessiondmin = null;
		try {
			sessiondmin = getSessionAdmin();
			Date newdate = new Date();
			String MD5Pwd = MD5.getHexMD5(admin.getAdminPwd());
			admin.setAdminPwd(MD5Pwd);
			admin.setUpdatedBy(sessiondmin.getAdminId());
			admin.setUpdatedTime(new Timestamp(newdate.getTime()));
			adminService.update(admin);
			setSuccessDispatch(GConstantAlert.GS_UPDATE_SUCCESS,GConstantRedirect.GS_ADMIN_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("AdminAction[resetPasswd] failed: ",ex);
			}
			return ERROR;
		}

	}
	
	public String personalUpdatePage() {
		try {
			Admin sessiondmin = getSessionAdmin();
			admin = adminService.findById(sessiondmin.getAdminId());
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("AdminAction[personalUpdatePage] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String personalUpdate() {
		Admin sessiondmin = null;
		try {
			sessiondmin = getSessionAdmin();
			Date newdate = new Date();
			admin.setUpdatedBy(sessiondmin.getAdminId());
			admin.setUpdatedTime(new Timestamp(newdate.getTime()));
			adminService.update(admin);
			setSuccessDispatch(GConstantAlert.GS_UPDATE_SUCCESS,GConstantRedirect.GS_ADMIN_PERSONAL_UPDATE_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("AdminAction[personalUpdate] failed: ",ex);
			}
			return ERROR;
		}

	}

	
	public String personalResetPasswdPage(){
		try {
			admin = getSessionAdmin();
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("AdminAction[personalResetPasswdPage] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	public String personalResetPasswd() {
		Admin sessiondmin = null;
		try {
			sessiondmin = getSessionAdmin();
			Date newdate = new Date();
			String oldPwd = MD5.getHexMD5(oldPassword);
			Admin administrator = adminService.findById(sessiondmin.getAdminId());
			String oldPwddb = null;
			if (null != administrator) {
				oldPwddb = administrator.getAdminPwd();
			}
			if (oldPwd.equals(oldPwddb)) {
				
				String md5Pwd = MD5.getHexMD5(admin.getAdminPwd());
				admin.setAdminPwd(md5Pwd);
				admin.setUpdatedBy(sessiondmin.getAdminId());
				admin.setUpdatedTime(new Timestamp(newdate.getTime()));
				adminService.update(admin);
				setSuccessDispatch(GConstantAlert.GS_UPDATE_SUCCESS,GConstantRedirect.GS_ADMIN_PERSONAL_RESETPWD_ACTION);
				return GConstantRedirect.GS_OK;
			}else{
				setErrorDispatch(GConstantAlert.GS_LTE0013,"");
				return ERROR;
			}
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("AdminAction[personalResetPasswd] failed: ",ex);
			}
			return ERROR;
		}

	}
	
	@Override
	public String doExecute() {
		return null;
	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getAdminPermission() {
		return adminPermission;
	}

	public void setAdminPermission(String adminPermission) {
		this.adminPermission = adminPermission;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public List<HashMap> getAllRole() {
		return allRole;
	}

	public void setAllRole(List<HashMap> allRole) {
		this.allRole = allRole;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
	

	
}
