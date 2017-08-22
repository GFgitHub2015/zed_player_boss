package com.zed.dao.version.base.impl;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;

import com.zed.dao.system.base.abstractdao.AbstractBaseDao;

public class VersionBaseDaoImpl<T> extends AbstractBaseDao<T> {
	@Resource(name="versionSqlSessionTemplate")
	private SqlSession versionSqlSessionTemplate;

	@Override
	protected SqlSession getSqlSessionTemplate() {
		return versionSqlSessionTemplate;
	}
	
	
}
