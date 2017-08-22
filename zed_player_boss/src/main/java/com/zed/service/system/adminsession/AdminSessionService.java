package com.zed.service.system.adminsession;

import com.zed.domain.system.Admin;
import com.zed.domain.system.AdminSession;
import com.zed.exception.AppValidationException;

public interface AdminSessionService {

	public void save(AdminSession adminSession) throws AppValidationException;
	
	public void update(AdminSession adminSession) throws AppValidationException;
	
	public AdminSession getAdminSession(String adminId) throws AppValidationException;

	public boolean checkLogonByOthers(Admin admin) throws AppValidationException;
}
