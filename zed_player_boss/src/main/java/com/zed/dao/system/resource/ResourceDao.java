package com.zed.dao.system.resource;

import java.util.List;

import com.zed.dao.BaseDao;
import com.zed.domain.system.Resource;
import com.zed.exception.AppValidationException;

public interface ResourceDao<T> extends BaseDao<Resource>{

	/**
	 * 获取菜单的父子关系
	 */
	public List<Resource> findAll() throws AppValidationException;
	
	
	/**
	 * 根据用户ID获取权限
	 */
	public List<Resource> findResourceByAdminId(String adminId) throws AppValidationException;
	
	/**
	 * 根据角色ID删除资源角色
	 */
	public void deleteRoleRescour(String roleId) throws AppValidationException;
	
	/**
	 * 根据角色ID获取权限
	 */
	public List<Resource> findListByRoleId(String roleId) throws AppValidationException;
}
