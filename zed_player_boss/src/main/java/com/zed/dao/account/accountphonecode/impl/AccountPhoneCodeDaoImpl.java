package com.zed.dao.account.accountphonecode.impl;

import org.springframework.stereotype.Repository;

import com.zed.dao.account.accountphonecode.AccountPhoneCodeDao;
import com.zed.dao.account.base.abstractdao.AbstractAccountPageDao;
import com.zed.domain.account.accountphonecode.AccountPhoneCode;

@Repository("accountPhoneCodeDao")
public class AccountPhoneCodeDaoImpl<T> extends AbstractAccountPageDao<AccountPhoneCode> implements AccountPhoneCodeDao<AccountPhoneCode> {


}
