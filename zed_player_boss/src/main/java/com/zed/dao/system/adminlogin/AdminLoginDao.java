package com.zed.dao.system.adminlogin;

import com.zed.dao.PageDao;
import com.zed.domain.system.AdminLogin;
import com.zed.exception.AppValidationException;

public interface AdminLoginDao extends PageDao<AdminLogin>{
	
	/**
	 * 根据管理员ID获取管理员登录信息
	 */
	public AdminLogin findLastLoginTime(String adminId) throws AppValidationException;
}
