package com.zed.controller.system;

import java.security.interfaces.RSAPublicKey;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.service.system.rsa.RSAService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantRedirect;

@Controller("loginAction")
@Scope(value="prototype")
public class LoginAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	
	private String module;
	private String exponent;
			
	@Resource
	protected RSAService rsaService;

	public String doExecute() throws Exception {
		try{
			String sessId = request.getSession().getId();
			//generate rsa key for key pad encrypt
			RSAPublicKey rsaKey = rsaService.getPublicKey(request);
			if(rsaKey!=null){
				module = rsaKey.getModulus().toString(16);
				exponent = rsaKey.getPublicExponent().toString(16);
			}
			
			return SUCCESS;
		}
		catch (Exception ex) {			
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, GConstantRedirect.GS_LOGIN_ACTION);
				Log.getLogger(this.getClass()).error("LogonAction[doExecute] failed: ",ex);
			}
			return GConstantRedirect.GS_ERROR_NOT_LOGIN;
		}
	}	
	
	public String getModule() {
		return module;
	}

	public String getExponent() {
		return exponent;
	}
}
