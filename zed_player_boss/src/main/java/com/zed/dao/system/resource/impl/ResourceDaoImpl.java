package com.zed.dao.system.resource.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.zed.dao.system.base.abstractdao.AbstractPageDao;
import com.zed.dao.system.resource.ResourceDao;
import com.zed.domain.system.Resource;
import com.zed.exception.AppValidationException;
import com.zed.system.SqlId;


@Repository
public class ResourceDaoImpl<T> extends AbstractPageDao<Resource> implements ResourceDao<T>{

	@Override
	public List<Resource> findAll() throws AppValidationException{
		return findList(SqlId.SQL_FIND_ALL);
	}
	
	@Override
	public List<Resource> findResourceByAdminId(String adminId) throws AppValidationException{
		return findList("findResourceByAdminId",adminId);
	}
	
	@Override
	public void deleteRoleRescour(String roleId) throws AppValidationException{
		delete("deleteRoleRescour",roleId);
	}
	
	@Override
	public List<Resource> findListByRoleId(String roleId) throws AppValidationException{
		return findList("findListByRoleId",roleId);
	}
}
