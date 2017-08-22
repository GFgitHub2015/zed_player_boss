package com.zed.service.system.adminsession.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zed.dao.system.adminsession.impl.AdminSessionDaoImpl;
import com.zed.domain.system.Admin;
import com.zed.domain.system.AdminSession;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.service.system.adminsession.AdminSessionService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantRedirect;


@Service
public class AdminSessionServiceImpl implements AdminSessionService {
	
	
    @Autowired
	private AdminSessionDaoImpl<AdminSession> adminSessionDao;	
	
    @Override
	public void save(AdminSession adminSession) throws AppValidationException{
		try{
			
			adminSessionDao.add(adminSession);
		}catch (Exception e) {
			Log.getLogger(this.getClass()).error("AdminSessionDaoImpl[add] failed: " , e);
			throw new AppValidationException(ExceptionConstants.SYSTEMERROR, GConstantRedirect.GS_LOGOFF_ACTION);
		}		
	}
	
    @Override
	public void update(AdminSession adminSession) throws AppValidationException{
		try{
			
			adminSessionDao.update(adminSession);
		}catch (Exception e) {
			Log.getLogger(this.getClass()).error("AdminSessionDaoImpl[update] failed: " , e);
			throw new AppValidationException(ExceptionConstants.SYSTEMERROR, GConstantRedirect.GS_LOGOFF_ACTION);
		}		
	}
	
    @Override
	public AdminSession getAdminSession(String adminId) throws AppValidationException{
		AdminSession toReturn = new AdminSession();
		try{
			
			toReturn = adminSessionDao.findById(adminId);
		}catch (Exception e) {
			Log.getLogger(this.getClass()).error("AdminSessionDaoImpl[findById] failed: " , e);
			throw new AppValidationException(ExceptionConstants.SYSTEMERROR, GConstantRedirect.GS_LOGOFF_ACTION);
		}		
		return toReturn;
	}
	
    @Override
	public boolean checkLogonByOthers(Admin admin) throws AppValidationException{
		boolean flag = true;
		try{
			AdminSession adminSession = getAdminSession(admin.getAdminId());
			if(adminSession.getSessionId().equals(admin.getSessionId())){
				flag = false; 
			}
		}catch (Exception e) {
			flag = true; 
			Log.getLogger(this.getClass()).error("AdminSessionDaoImpl[checkLogonByOthers] failed: " , e);
			throw new AppValidationException(ExceptionConstants.SYSTEMERROR, GConstantRedirect.GS_LOGOFF_ACTION);
		}	
		return flag;		
	}

}
