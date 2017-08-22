package com.zed.redis.server.config;

import com.zed.domain.server.config.PlayerConfig;

public interface PlayerConfigRedisDao {
	
	public void addPlayerConfig(PlayerConfig playerConfig);
	
	public void deletePlayerConfigList(String ...versions);
	
}
