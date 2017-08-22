package com.zed.redis.server.modelinfo;

import java.util.List;

import com.zed.domain.server.modelinfo.PlayerModelInfo;


public interface PlayerModelInfoRedisDao {
	
	public void addPlayerModelInfo(PlayerModelInfo playerModelInfo);
	
	public void addPlayerModelInfoList(List<PlayerModelInfo> playerModelInfoList);
	
	public void deletePlayerModelInfoList(String ...models);
	
	public void deleteAll();
	
	
	

}
