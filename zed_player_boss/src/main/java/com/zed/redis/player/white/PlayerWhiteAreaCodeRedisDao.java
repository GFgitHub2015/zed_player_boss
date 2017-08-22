package com.zed.redis.player.white;

import com.zed.domain.player.white.PlayerWhiteAreaCode;

public interface PlayerWhiteAreaCodeRedisDao {

	/**
	 * 添加地区编号白名单
	 * @param playerWhiteAreaCode
	 */
	public void addPlayerWhiteAreaCode(PlayerWhiteAreaCode playerWhiteAreaCode);
	
	/**
	 * 删除白名单上的地区编号
	 * @param playerWhiteAreaCode
	 */
	public void deletePlayerWhiteAreaCode();
}
