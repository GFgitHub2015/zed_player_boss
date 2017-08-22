package com.zed.dao.account.loginrecord.impl;

import org.springframework.stereotype.Repository;

import com.zed.dao.account.base.abstractdao.AbstractAccountPageDao;
import com.zed.dao.account.loginrecord.LoginRecordDao;
import com.zed.domain.account.loginrecord.LoginRecord;

@Repository("loginRecordDao")
public class LoginRecordDaoImpl<T> extends AbstractAccountPageDao<LoginRecord> implements LoginRecordDao<LoginRecord> {


}
