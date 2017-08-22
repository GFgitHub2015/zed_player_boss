package com.zed.dao.common.base.impl;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;

import com.zed.dao.system.base.abstractdao.AbstractBaseDao;

public class CommonBaseDaoImpl<T> extends AbstractBaseDao<T> {
	@Resource(name="commonSqlSessionTemplate")
	private SqlSession commonSqlSessionTemplate;

	@Override
	protected SqlSession getSqlSessionTemplate() {
		return commonSqlSessionTemplate;
	}
	
	
}
