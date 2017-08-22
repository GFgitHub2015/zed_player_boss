package com.zed.dao.system.adminrole;


import com.zed.dao.PageDao;
import com.zed.domain.system.AdminRole;
import com.zed.exception.AppValidationException;


public interface AdminRoleDao<T> extends PageDao<AdminRole>{

	/**
	 * 根据用户ID删除该用户所有角色
	 */
	public void deleteAdminRole(String adminId) throws AppValidationException;
	
	/**
	 * 保存用户角色
	 */
	public void addAdminRole(AdminRole adminRole) throws AppValidationException;
}
