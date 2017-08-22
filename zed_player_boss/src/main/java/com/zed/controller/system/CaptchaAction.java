package com.zed.controller.system;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantRedirect;


@Controller("captchaAction")
@Scope(value="prototype")
public class CaptchaAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
			
	public String doExecute() throws Exception {
		try{
			return SUCCESS;
		}
		catch (Exception ex) {			
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, GConstantRedirect.GS_LOGIN_ACTION);
				Log.getLogger(getClass()).error("CaptchaAction[doExecute] failed: " ,ex);
			}
			return GConstantRedirect.GS_ERROR_NOT_LOGIN;
		}
	}
	
}
