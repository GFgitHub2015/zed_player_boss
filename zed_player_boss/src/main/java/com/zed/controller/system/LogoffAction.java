package com.zed.controller.system;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstant;
import com.zed.system.GConstantRedirect;

@Controller("logoffAction")
@Scope(value="prototype")
public class LogoffAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	public String doExecute() throws Exception {
		try{
			//check session if session is null
			if(request.getSession(false) == null ||request.getSession(false).getAttribute(GConstant.GS_SESSION_ADMIN_KEY)==null){
				request.getSession().invalidate();
				return SUCCESS;
			}			
	
			request.getSession().invalidate();

			return SUCCESS;
		}
		catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, GConstantRedirect.GS_LOGIN_ACTION);
				Log.getLogger(this.getClass()).error("LogoffAction[doExecute] failed. ",ex);
			}
			
			request.getSession().invalidate();
			
			return GConstantRedirect.GS_ERROR_NOT_LOGIN;
		}
	}
	
}
