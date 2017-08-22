package com.zed.redis.player.logicalfile;

import com.zed.domain.player.logicalfile.PlayerLogicalPlayTimes;

public interface PlayerLogicalPlayTimesRedisDao {
	
	//更新播放次数
	public void zIncrease(String fileId);
	
	public PlayerLogicalPlayTimes getPlayerLogicalPlayTimes(String fileId);
	
	public void addPlayerLogicalPlayTimes(PlayerLogicalPlayTimes playerLogicalPlayTimes);
	
	//是否存在
	public Boolean exist(String fileId);
	
}
