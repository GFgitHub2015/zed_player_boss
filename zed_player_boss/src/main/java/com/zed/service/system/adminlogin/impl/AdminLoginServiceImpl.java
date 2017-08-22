package com.zed.service.system.adminlogin.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zed.dao.system.adminlogin.impl.AdminLoginDaoImpl;
import com.zed.domain.system.AdminLogin;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.service.system.adminlogin.AdminLoginService;


@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    @Autowired
	private AdminLoginDaoImpl adminLoginDao;
			
    @Override
	public void add(AdminLogin adminLogin) throws AppValidationException{
		adminLoginDao.add(adminLogin);
	}	
	
	@Override
	public AdminLogin findLastLoginTime(String adminId) throws AppValidationException{
		try{
			return (AdminLogin)adminLoginDao.findLastLoginTime(adminId);
		}catch (Exception ex) {
			Log.getLogger(this.getClass()).error("AdminLoginDaoImpl[findLastLoginTime] failed: ",ex);
			return null;
		}	
	}
}
