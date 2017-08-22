package com.zed.dao.server.config.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.zed.dao.server.base.abstractdao.AbstractServerPageDao;
import com.zed.dao.server.config.PlayerConfigDao;
import com.zed.domain.server.config.PlayerConfig;

@Repository("playerConfigDao")
public class PlayerConfigDaoImpl<T>  extends AbstractServerPageDao<PlayerConfig> implements PlayerConfigDao<PlayerConfig> {

	@Override
	public List<PlayerConfig> findAll() {
		return findList("findAll");
	}

	@Override
	public List<PlayerConfig> findAllByIds(String[] ids) {
		return findList("findAllByIds",ids);
	}

	@Override
	public PlayerConfig findByVersionAndPackageName(String version, String packageName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("version", version);
		params.put("packageName", packageName);
		return find("findByVersionAndPackageName", params);
	}

}
