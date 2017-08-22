package com.zed.service.system.resource;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.zed.domain.system.Resource;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface ResourceService{

	public Resource findById(String resourceId) throws AppValidationException;
		
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException;
	
	/**
	 * 获取菜单的父子关系
	 */
	public List<Resource> findAll() throws AppValidationException;

	public void add(Resource resource) throws AppValidationException;
	
	public void update(Resource resource) throws AppValidationException;
	
	public void delete(String[] resourceId) throws AppValidationException;
		
	/**
	 * 根据用户ID获取权限
	 */
	public List<Resource> getAdminResource(String adminId) throws AppValidationException;
	
	/**
	 * 根据角色ID获取权限
	 */
	public List<Resource> findListByRoleId(String roleId) throws AppValidationException;
	
	/**
	 * 保存角色权限
	 */
	public void addRoleResource(String roleId,List<String> list,String createdBy, Date createdTime, String updatedBy,Date updatedTime) throws AppValidationException;
}
