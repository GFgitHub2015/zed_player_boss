package com.zed.service.system.role;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zed.domain.system.AdminRole;
import com.zed.domain.system.Role;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface RoleService{

	public Role findById(String roldId) throws AppValidationException;
		
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException;

	public void add(Role role) throws AppValidationException;
	
	public void update(Role role) throws AppValidationException;
	
	public void delete(String[] roldId) throws AppValidationException;
	
	/**
	 * 根据用户ID获取角色
	 */
	public Role findByAdminId(String adminId) throws AppValidationException;
	
	/**
	 * 获取所有角色
	 */
	public List<HashMap> findAll() throws AppValidationException;
	
	/**
	 * 保存用户角色
	 */
	public void addAdminRole(String adminId, AdminRole adminRole) throws AppValidationException;
	/**
	 * 根据role_key获取角色
	 */
	public Role findRoleByRoleKey(String roleKey) throws AppValidationException;
	/**
	 * 根据参数查询角色对象集合
	 */
	public List<Role> findRoleByParams(Map<String, Object> map) throws AppValidationException;
}
