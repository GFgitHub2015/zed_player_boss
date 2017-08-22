package com.zed.service.account.accountphonecode.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.dao.account.accountphonecode.AccountPhoneCodeDao;
import com.zed.domain.account.accountphonecode.AccountPhoneCode;
import com.zed.exception.AppValidationException;
import com.zed.service.account.accountphonecode.AccountPhoneCodeService;
import com.zed.system.page.Page;

@Service("accountPhoneCodeService")
public class AccountPhoneCodeServiceImpl implements AccountPhoneCodeService {
	@Resource(name="accountPhoneCodeDao")
	private AccountPhoneCodeDao accountPhoneCodeDao;
	@Override
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException {
		return accountPhoneCodeDao.findByPage(page);
	}

	@Override
	public AccountPhoneCode findById(String code) throws AppValidationException {
		return (AccountPhoneCode) accountPhoneCodeDao.findById(code);
	}

}
