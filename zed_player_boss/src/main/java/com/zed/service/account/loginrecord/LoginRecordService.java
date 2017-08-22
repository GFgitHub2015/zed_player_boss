package com.zed.service.account.loginrecord;

import java.util.HashMap;

import com.zed.domain.account.loginrecord.LoginRecord;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface LoginRecordService {
	
	//分页查询
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException;
	//根据loginRecordId获取对象
	public LoginRecord findById(String loginRecordId) throws AppValidationException;

}
