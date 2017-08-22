package com.zed.controller.system;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.domain.system.Admin;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantRedirect;
import com.zed.util.TreeObject;
import com.zed.util.TreeUtil;

@Controller("topAction")
@Scope(value="prototype")
public class TopAction  extends BaseAction {

	private static final long serialVersionUID = 1L;

	private List<TreeObject> topMenuList;
	private Admin sessionAdmin;
		
	public String doExecute() throws Exception {

		try{		
			sessionAdmin=getSessionAdmin();
			topMenuList = TreeUtil.getChildResourcesByParentId(getSessionMenu(), 999);
			return SUCCESS;
		}
		catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, GConstantRedirect.GS_LOGOFF_ACTION);
				Log.getLogger(this.getClass()).error("TopAction[doExecute] failed: ",ex);
			}
			return autoLogOffBySessionTimeOut();
		}
	}

	public List<TreeObject> getTopMenuList() {
		return topMenuList;
	}

	public void setTopMenuList(List<TreeObject> topMenuList) {
		this.topMenuList = topMenuList;
	}

	public Admin getSessionAdmin() {
		return sessionAdmin;
	}

	public void setSessionAdmin(Admin sessionAdmin) {
		this.sessionAdmin = sessionAdmin;
	}

}
