package com.zed.dao.account.account.impl;

import org.springframework.stereotype.Repository;

import com.zed.dao.account.account.AccountDao;
import com.zed.dao.account.base.abstractdao.AbstractAccountPageDao;
import com.zed.domain.account.account.Account;
import com.zed.exception.AppValidationException;

@Repository("accountDao")
public class AccountDaoImpl<T> extends AbstractAccountPageDao<Account> implements AccountDao<Account> {

/*	@Override
	public Long findByNickNameOrPhone(String nickName, String phone) throws AppValidationException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nickName", nickName);
		params.put("phone", phone);
		return Long.parseLong(findOne("findByNickName", params).toString());
	}*/

	@Override
	public Account findByNickName(String nickName) throws AppValidationException {
		return find("findByNickName", nickName);
	}

	@Override
	public Account findByPhone(String phone) throws AppValidationException {
		return find("findByPhone", phone);
	}

}
