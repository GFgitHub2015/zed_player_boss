package com.zed.dao.server.modelinfo.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zed.dao.server.base.abstractdao.AbstractServerPageDao;
import com.zed.dao.server.modelinfo.PlayerModelInfoDao;
import com.zed.domain.server.modelinfo.PlayerModelInfo;

@Repository("playerModelInfoDao")
public class PlayerModelInfoDaoImpl<T>  extends AbstractServerPageDao<PlayerModelInfo> implements PlayerModelInfoDao<PlayerModelInfo> {

	@Override
	public List<PlayerModelInfo> findAll() {
		return findList("findAll");
	}

}
