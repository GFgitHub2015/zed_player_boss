package com.zed.dao.player.base.impl;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;

import com.zed.dao.system.base.abstractdao.AbstractBaseDao;

public class PlayerBaseDaoImpl<T> extends AbstractBaseDao<T> {
	@Resource(name="playerSqlSessionTemplate")
	private SqlSession playerSqlSessionTemplate;

	@Override
	protected SqlSession getSqlSessionTemplate() {
		return playerSqlSessionTemplate;
	}
	
	
}
