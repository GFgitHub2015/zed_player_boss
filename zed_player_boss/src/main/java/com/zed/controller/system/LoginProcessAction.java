package com.zed.controller.system;

import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.domain.system.Admin;
import com.zed.domain.system.AdminLogin;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.service.system.adminlogin.AdminLoginService;
import com.zed.service.system.rsa.RSAService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantRedirect;
import com.zed.util.CommUtil;

@Controller("loginProcessAction")
@Scope(value="prototype")
public class LoginProcessAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private String adminId;
	private String encPwd;
	private String decPwd;
	
	private String captcha;
	
	@Resource
	protected RSAService rsaService;
	@Resource
	protected AdminLoginService adminLoginService;

	public String doExecute() throws Exception {
		HttpSession newSession = null;
		
		try{
			
			//rsa decryption
			decPwd = rsaService.decryptionByKeyPair(request, encPwd);
			if( decPwd == null){
				throw new AppValidationException(ExceptionConstants.SYSTEMERROR, GConstantRedirect.GS_LOGIN_ACTION);
			}
			
			//get admin info
			Admin admin= adminService.findById(adminId);
			
			if(admin==null){
				setResultDispatch(ExceptionConstants.ADMIN_NONE, GConstantRedirect.GS_LOGIN_ACTION);
				return GConstantRedirect.GS_RESULT;
			}
			//check password
			boolean loginFlag = adminService.checkPassWord(admin, decPwd);
			
			if (!loginFlag) {			
				setResultDispatch(ExceptionConstants.ADMIN_PIN_INCORRECT, GConstantRedirect.GS_LOGIN_ACTION);
				return GConstantRedirect.GS_RESULT;
			}
			
			String randCode = (String)request.getSession().getAttribute("randCode");
			if(!randCode.equals(captcha))
			{
				setResultDispatch(ExceptionConstants.INVALIDCAPTCHA, GConstantRedirect.GS_LOGIN_ACTION);
				return GConstantRedirect.GS_RESULT;
			}
			
			invalidateCurrentSession(request);
			newSession = createSession(request);
	
			setSessionAdmin(admin);
			
			//get last  login time
			AdminLogin adminLogin = adminLoginService.findLastLoginTime(adminId);
			if(adminLogin!=null){
				//update admin info
				admin.setLastLoginTime(adminLogin.getLoginTime());
				adminService.update(admin);
			}			
			
			//add login info
			AdminLogin newAdminLogin = new AdminLogin();
			Date newdate = new Date();
			newAdminLogin.setAdminId(adminId);
			newAdminLogin.setLoginTime(new Timestamp(newdate.getTime()));
			newAdminLogin.setLoginIp(CommUtil.getIp(request));
			adminLoginService.add(newAdminLogin);
								
			return SUCCESS;
		}
		catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, GConstantRedirect.GS_LOGOFF_ACTION);
				Log.getLogger(this.getClass()).error("LogonProcessAction[doExecute] failed: ",ex);
			}
			return GConstantRedirect.GS_ERROR_NOT_LOGIN;
		}
	}
	
	protected void invalidateCurrentSession(HttpServletRequest req)	{
		HttpSession lhs_Session = req.getSession(false);
		if(lhs_Session==null)
			return;
		lhs_Session.invalidate();
	}
	
	protected HttpSession createSession(HttpServletRequest ahsr_request) throws AppValidationException {
		HttpSession lhs_Session=ahsr_request.getSession(true);
		if(lhs_Session==null){
			throw new AppValidationException(ExceptionConstants.SYSTEMERROR, GConstantRedirect.GS_LOGIN_ACTION);
		}
		
		// if isLogon, just create new session and return
		if(!lhs_Session.isNew()){
			throw new AppValidationException(ExceptionConstants.SYSTEMERROR, GConstantRedirect.GS_LOGIN_ACTION);
		}
		return lhs_Session;
	}


	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getEncPwd() {
		return encPwd;
	}

	public void setEncPwd(String encPwd) {
		this.encPwd = encPwd;
	}


	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	
	

}
