package com.zed.controller.account.loginrecord;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.controller.system.BaseAction;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.service.account.loginrecord.LoginRecordService;
import com.zed.system.ExceptionConstants;
import com.zed.system.page.Page;
import com.zed.util.CommUtil;

@Controller("loginRecordAction")
@Scope(value = "prototype")
public class LoginRecordAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private Page<HashMap> page = new Page<HashMap>();
	private String userId;
	private String loginIp;
	
	@Autowired
	private LoginRecordService loginRecordService;
	
	public String list() {
		try {
			HashMap map = new HashMap();
			page.setSorting(" create_time desc"); // 排序
			if (!CommUtil.isEmpty(userId)) {
				map.put("userId", userId);
			}
			if (!CommUtil.isEmpty(loginIp)) {
				map.put("loginIp", loginIp);
			}
			page.setParamsMap(map);
			page = loginRecordService.findByPage(page);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("LoginRecordAction[list] failed: ",ex);
			}
			return ERROR;
		}
	}


	@Override
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
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	
}
