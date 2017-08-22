package com.zed.dao.system.resourcerole.impl;


import org.springframework.stereotype.Repository;

import com.zed.dao.system.base.abstractdao.AbstractPageDao;
import com.zed.dao.system.resourcerole.ResourceRoleDao;
import com.zed.domain.system.ResourceRole;
import com.zed.exception.AppValidationException;


@Repository
public class ResourceRoleDaoImpl<T> extends AbstractPageDao<ResourceRole> implements ResourceRoleDao<T>{

	@Override
	public void addResourceRole(ResourceRole resourceRole) throws AppValidationException{
		add("addResourceRole",resourceRole);
	}
	
}
