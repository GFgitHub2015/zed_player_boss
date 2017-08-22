package com.zed.controller.system;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantRedirect;

@Controller("mainAction")
@Scope(value="prototype")
public class MainAction  extends BaseAction {

	private static final long serialVersionUID = 1L;
	
//	private String minimunNumber = SystemConfig.getProperty("firstpage.minimun.number");
	
	public String doExecute() throws Exception {
	
		
//		try{
		
			return SUCCESS;
		/*}
		catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, GConstantRedirect.GS_LOGOFF_ACTION);
				Log.getLogger(this.getClass()).error("MainAction[doExecute] failed: ",ex);
			}
			return ERROR;
		}*/
	}

}
