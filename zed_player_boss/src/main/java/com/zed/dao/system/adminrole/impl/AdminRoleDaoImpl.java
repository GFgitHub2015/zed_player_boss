package com.zed.dao.system.adminrole.impl;



import org.springframework.stereotype.Repository;

import com.zed.dao.system.adminrole.AdminRoleDao;
import com.zed.dao.system.base.abstractdao.AbstractPageDao;
import com.zed.domain.system.AdminRole;
import com.zed.exception.AppValidationException;


@Repository
public class AdminRoleDaoImpl<T> extends AbstractPageDao<AdminRole> implements AdminRoleDao<T>{

	@Override
	public void deleteAdminRole(String adminId) throws AppValidationException{
		delete("deleteAdminRole", adminId);
	}
	
	@Override
	public void addAdminRole(AdminRole adminRole) throws AppValidationException{
		add("addAdminRole", adminRole);
	}

}
