package com.zed.dao.server.config;

import java.io.Serializable;
import java.util.List;

import com.zed.dao.PageDao;
import com.zed.domain.server.config.PlayerConfig;

public interface PlayerConfigDao<T extends Serializable> extends PageDao<PlayerConfig> {
	
	public List<PlayerConfig> findAll();
	
	public List<PlayerConfig> findAllByIds(String []ids);
	
	public PlayerConfig findByVersionAndPackageName(String version, String packageName);

}
