package com.zed.service.system.adminlogin;

import com.zed.domain.system.AdminLogin;
import com.zed.exception.AppValidationException;

public interface AdminLoginService {

	public void add(AdminLogin adminLogin) throws AppValidationException;
	
	/**
	 * 根据用户ID获取用户登录信息
	 */
	public AdminLogin findLastLoginTime(String adminId) throws AppValidationException;
}
