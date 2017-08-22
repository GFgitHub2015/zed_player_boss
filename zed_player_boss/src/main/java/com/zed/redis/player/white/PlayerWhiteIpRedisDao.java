package com.zed.redis.player.white;

import com.zed.domain.player.white.PlayerWhiteIp;

public interface PlayerWhiteIpRedisDao {
	
	/**
	 * 添加ip到白名单
	 * @param playerWhiteIp
	 */
	public void addPlayerWhiteIp(PlayerWhiteIp playerWhiteIp);
	
	/**
	 * 删除白名单的ip
	 * @param playerWhiteIp
	 */
	public void deletePlayerWhiteIp();

}
