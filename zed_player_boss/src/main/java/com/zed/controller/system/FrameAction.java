package com.zed.controller.system;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.domain.system.Admin;
import com.zed.domain.system.Resource;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.service.system.resource.ResourceService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstant;
import com.zed.system.GConstantRedirect;
import com.zed.util.CommUtil;
import com.zed.util.TreeObject;
import com.zed.util.TreeUtil;


@Controller("frameAction")
@Scope(value="prototype")
public class FrameAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	
	private String pageToken;
	private List<TreeObject> topMenuList;
	private List<TreeObject> leftMenuList;
	private String partentId;
	@javax.annotation.Resource
	protected ResourceService resourceService;
	
	public String doExecute() throws Exception {
		Admin localSessionAdmin = null;
		try {

			localSessionAdmin = getSessionAdmin();
						
			//create pagetoken for workArea page validate
			pageToken = String.valueOf(new Date().getTime());
			localSessionAdmin.setPageToken(pageToken);
			//get menu
			List<Resource> resourceList =resourceService.getAdminResource(localSessionAdmin.getAdminId());

			setSessionMenu(resourceList);
			
			topMenuList = TreeUtil.getChildResourcesByParentId(getSessionMenu(), 999);
			
			leftMenuList = TreeUtil.getChildResourcesByParentId(getSessionMenu(), Integer.valueOf(CommUtil.isEmpty(partentId)?"1001":partentId));
						
			return SUCCESS;
		}
		catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, GConstantRedirect.GS_LOGOFF_ACTION);
				Log.getLogger(this.getClass()).error("FrameAction[doExecute] failed: ",ex);
			}
			return GConstantRedirect.GS_ERROR_NOT_LOGIN;
		}
	}
	
	private void preventRefresh() throws AppValidationException {
		HttpSession session = request.getSession(false);
		if( session.getAttribute(GConstant.GS_SESSION_PREVENT_REFRESH) != null&& "true".equals(session.getAttribute(GConstant.GS_SESSION_PREVENT_REFRESH)) )
			throw new AppValidationException(ExceptionConstants.SYSTEMERROR, GConstantRedirect.GS_LOGOFF_ACTION);
		else
			session.setAttribute(GConstant.GS_SESSION_PREVENT_REFRESH, "true");
	}

	
	public String getPageToken() {
		return pageToken;
	}

	public List<TreeObject> getTopMenuList() {
		return topMenuList;
	}

	public void setTopMenuList(List<TreeObject> topMenuList) {
		this.topMenuList = topMenuList;
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
