package com.zed.redis.player.logicalfile;

import com.zed.domain.player.logicalfile.PlayerLogicalDownloadTimes;

public interface PlayerLogicalDownloadTimesRedisDao {
	
	//更新下载次数
	public void zIncrease(String fileId);
	
	public PlayerLogicalDownloadTimes getPlayerLogicalDownloadTimes(String fileId);
	
	public void addPlayerLogicalDownloadTimes(PlayerLogicalDownloadTimes playerLogicalDownloadTimes);
	
	//是否存在
	public Boolean exist(String fileId);
	
}
