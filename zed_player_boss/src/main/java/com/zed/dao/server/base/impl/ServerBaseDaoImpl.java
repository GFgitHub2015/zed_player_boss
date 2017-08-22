package com.zed.dao.server.base.impl;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;

import com.zed.dao.system.base.abstractdao.AbstractBaseDao;

public class ServerBaseDaoImpl<T> extends AbstractBaseDao<T> {
	@Resource(name="serverSqlSessionTemplate")
	private SqlSession serverSqlSessionTemplate;

	@Override
	protected SqlSession getSqlSessionTemplate() {
		return serverSqlSessionTemplate;
	}
	
	
}
