package com.zed.service.account.loginrecord.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.dao.account.loginrecord.LoginRecordDao;
import com.zed.domain.account.loginrecord.LoginRecord;
import com.zed.exception.AppValidationException;
import com.zed.service.account.loginrecord.LoginRecordService;
import com.zed.system.page.Page;

@Service("loginRecordService")
public class LoginRecordServiceImpl implements LoginRecordService {
	
	@Resource(name="loginRecordDao")
	private LoginRecordDao loginRecordDao;

	@Override
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException {
		return loginRecordDao.findByPage(page);
	}

	@Override
	public LoginRecord findById(String loginRecordId) throws AppValidationException {
		return (LoginRecord) loginRecordDao.findById(loginRecordId);
	}

}
