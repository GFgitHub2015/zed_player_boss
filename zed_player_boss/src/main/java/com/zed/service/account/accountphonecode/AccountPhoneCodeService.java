package com.zed.service.account.accountphonecode;

import java.util.HashMap;

import com.zed.domain.account.accountphonecode.AccountPhoneCode;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface AccountPhoneCodeService {

	//分页查询
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException;
	//根据code获取对象
	public AccountPhoneCode findById(String code) throws AppValidationException;
}
