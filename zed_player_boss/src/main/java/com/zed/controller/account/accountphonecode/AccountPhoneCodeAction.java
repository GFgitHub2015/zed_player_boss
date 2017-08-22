package com.zed.controller.account.accountphonecode;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.controller.system.BaseAction;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.service.account.accountphonecode.AccountPhoneCodeService;
import com.zed.system.ExceptionConstants;
import com.zed.system.page.Page;
import com.zed.util.CommUtil;

@Controller("accountPhoneCodeAction")
@Scope(value = "prototype")
public class AccountPhoneCodeAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private Page<HashMap> page = new Page<HashMap>();
	private String phone;
	private String status;
	private String areaCode;
	
	@Autowired
	private AccountPhoneCodeService accountPhoneCodeService;
	
	public String list() {
		try {
			HashMap map = new HashMap();
			page.setSorting(" create_time desc"); // 排序
			if (!CommUtil.isEmpty(phone)) {
				map.put("phone", phone);
			}
			if (!CommUtil.isEmpty(status)) {
				map.put("status", status);
			}
			if (!CommUtil.isEmpty(areaCode)) {
				map.put("areaCode", areaCode);
			}
			page.setParamsMap(map);
			page = accountPhoneCodeService.findByPage(page);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("AccountPhoneCodeAction[list] failed: ",ex);
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
