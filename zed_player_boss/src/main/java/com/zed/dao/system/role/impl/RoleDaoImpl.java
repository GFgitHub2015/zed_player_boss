package com.zed.dao.system.role.impl;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zed.dao.system.base.abstractdao.AbstractPageDao;
import com.zed.dao.system.role.RoleDao;
import com.zed.domain.system.Role;
import com.zed.exception.AppValidationException;


@Repository
public class RoleDaoImpl<T> extends AbstractPageDao<Role> implements RoleDao<T>{

	@Override
	public Role findByAdminId(String adminId) throws AppValidationException{
		return find("findByAdminId", adminId);
	}
	
	@Override
	public List<Map> findAll() throws AppValidationException{
		return findMap("findAll");
	}
	
	@Override
	public Role findRoleByRoleKey(String roleKey) throws AppValidationException {
		return find("findRoleByRoleKey", roleKey);
	}

	@Override
	public List<Role> findRoleByParams(Map<String, Object> map)
			throws AppValidationException {
		return findList("findRoleByParams", map);
	}
	
}
