package com.zed.controller.player.webmasterstat;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.api.player.playeruser.bean.PlayerUserBean;
import com.zed.controller.system.BaseAction;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.service.player.webmaster.WebMasterStatService;
import com.zed.system.ExceptionConstants;
import com.zed.system.page.Page;
import com.zed.util.CommUtil;

@Controller("webMasterStatAction")
@Scope("prototype")
public class WebMasterStatAction extends BaseAction {
	private static final long serialVersionUID = -4060007908727096106L;
	private Page<HashMap> page = new Page<HashMap>();
	private String userId;
	private String userRoleStatus;
	@Resource(name = "webMasterStatService")
	private WebMasterStatService webMasterStatService;
	
	public String list(){
		try {
			HashMap map = new HashMap();
			page.setSorting(" UPDATE_TIME desc, CREATE_TIME desc");
			if (!CommUtil.isEmpty(userId)) {
				map.put("userId", userId);
			}
			if (!CommUtil.isEmpty(userRoleStatus)) {
				map.put("userRoleStatus", userRoleStatus);
			}
			map.put("status", PlayerUserBean.Status.START.getStatus());
			page.setParamsMap(map);
			page = webMasterStatService.findTrancodingStatByPage(page);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("WebMasterStatAction[list] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	public String doExecute() throws Exception {
		return null;
	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserRoleStatus() {
		return userRoleStatus;
	}

	public void setUserRoleStatus(String userRoleStatus) {
		this.userRoleStatus = userRoleStatus;
	}
	
}
