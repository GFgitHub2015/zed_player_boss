package com.zed.dao.system.base.impl;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;

import com.zed.dao.system.base.abstractdao.AbstractBaseDao;

public class BaseDaoImpl<T> extends AbstractBaseDao<T> {

	@Resource(name="sqlSessionTemplate")
	private SqlSession sqlSessionTemplate;

	@Override
	protected SqlSession getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}
	
}
