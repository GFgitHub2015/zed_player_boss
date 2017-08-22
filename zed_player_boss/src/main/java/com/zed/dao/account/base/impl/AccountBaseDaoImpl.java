package com.zed.dao.account.base.impl;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;

import com.zed.dao.system.base.abstractdao.AbstractBaseDao;

public class AccountBaseDaoImpl<T> extends AbstractBaseDao<T> {
	@Resource(name="accountSqlSessionTemplate")
	private SqlSession accountSqlSessionTemplate;

	@Override
	protected SqlSession getSqlSessionTemplate() {
		return accountSqlSessionTemplate;
	}
	
	
}
