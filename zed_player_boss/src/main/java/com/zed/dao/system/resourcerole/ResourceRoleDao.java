package com.zed.dao.system.resourcerole;

import com.zed.dao.BaseDao;
import com.zed.domain.system.ResourceRole;
import com.zed.exception.AppValidationException;

public interface ResourceRoleDao<T> extends BaseDao<ResourceRole>{
	
	/**
	 * 保存资源角色
	 * @param resourceRoles
	 */
	public void addResourceRole(ResourceRole resourceRole) throws AppValidationException;
	
}
