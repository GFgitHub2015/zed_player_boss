package com.zed.service.system.role.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zed.dao.system.adminrole.AdminRoleDao;
import com.zed.dao.system.adminrole.impl.AdminRoleDaoImpl;
import com.zed.dao.system.role.impl.RoleDaoImpl;
import com.zed.domain.system.AdminRole;
import com.zed.domain.system.Role;
import com.zed.exception.AppValidationException;
import com.zed.service.system.role.RoleService;
import com.zed.system.page.Page;


@Service
@SuppressWarnings("unchecked")
public class RoleServiceImpl implements RoleService {
	
    @Autowired
	private RoleDaoImpl roleDao;
    @Autowired
    private AdminRoleDao adminRoleDao;
		
    @Override
	public Role findById(String roleId)  throws AppValidationException {
		return (Role)roleDao.findById(roleId);	
	}
		
    @Override
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException{
		return roleDao.findByPage(page);
	}
	
    @Override
	public void add(Role role) throws AppValidationException{
		roleDao.add(role);
	}
	
    @Override
	public void update(Role role) throws AppValidationException{
		roleDao.update(role);
	}
	
    @Override
	public void delete(String[] roleId) throws AppValidationException {
		roleDao.delete(roleId);	
	}
	
    @Override
	public Role findByAdminId(String adminId) throws AppValidationException {
		return roleDao.findByAdminId(adminId);
	}
	
    @Override
	public List<HashMap> findAll() throws AppValidationException{
		return roleDao.findAll();
	}
	
    @Override
	public void addAdminRole(String adminId, AdminRole adminRole) throws AppValidationException{
		adminRoleDao.deleteAdminRole(adminId);
		adminRoleDao.addAdminRole(adminRole);
	}

    @Override
	public Role findRoleByRoleKey(String roleKey) throws AppValidationException {
		return (Role)roleDao.findRoleByRoleKey(roleKey);
	}

    @Override
	public List<Role> findRoleByParams(Map<String, Object> map)
			throws AppValidationException {
		return roleDao.findRoleByParams(map);
	}
	


}
