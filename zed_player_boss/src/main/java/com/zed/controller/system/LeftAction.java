package com.zed.controller.system;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantRedirect;
import com.zed.util.TreeObject;
import com.zed.util.TreeUtil;

@Controller("leftAction")
@Scope(value="prototype")
public class LeftAction  extends BaseAction {

	private static final long serialVersionUID = 1L;

	private List<TreeObject> leftMenuList;
	private String partentId;
	public String doExecute() throws Exception {

		try{			
			leftMenuList = TreeUtil.getChildResourcesByParentId(getSessionMenu(), Integer.valueOf(partentId));
			return SUCCESS;
		}
		catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, GConstantRedirect.GS_LOGOFF_ACTION);
				Log.getLogger(this.getClass()).error("LeftAction[doExecute] failed: ",ex);
			}			
			return autoLogOffBySessionTimeOut();
		}
	}

	public List<TreeObject> getLeftMenuList() {
		return leftMenuList;
	}

	public void setLeftMenuList(List<TreeObject> leftMenuList) {
		this.leftMenuList = leftMenuList;
	}

	public String getPartentId() {
		return partentId;
	}

	public void setPartentId(String partentId) {
		this.partentId = partentId;
	}
	
	
}
