package com.zed.dao.system.adminlogin.impl;

import org.springframework.stereotype.Repository;

import com.zed.dao.system.adminlogin.AdminLoginDao;
import com.zed.dao.system.base.abstractdao.AbstractPageDao;
import com.zed.domain.system.AdminLogin;
import com.zed.exception.AppValidationException;

@Repository
public class AdminLoginDaoImpl<T> extends AbstractPageDao<AdminLogin> implements AdminLoginDao{
    
	@Override
	public AdminLogin findLastLoginTime(String adminId) throws AppValidationException{
		return findById("findLastLoginTime", adminId);
	}
}
                                