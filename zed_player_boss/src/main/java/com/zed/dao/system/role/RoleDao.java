package com.zed.dao.system.role;


import java.util.List;
import java.util.Map;

import com.zed.dao.PageDao;
import com.zed.domain.system.Role;
import com.zed.exception.AppValidationException;


public interface RoleDao<T> extends PageDao<Role>{

	/**
	 * 根据用户ID获取角色
	 */
	public Role findByAdminId(String adminId) throws AppValidationException;
	
	/**
	 * 获取所有角色
	 */
	public List<Map> findAll() throws AppValidationException;
	
	/**
	 * 根据roleKey获取角色
	 */
	public Role findRoleByRoleKey(String roleKey) throws AppValidationException;
	/**
	 * 根据参数查询角色对象集合
	 */
	public List<Role> findRoleByParams(Map<String, Object> map) throws AppValidationException;
}
