package com.zed.redis.player.black;

import com.zed.domain.player.black.PlayerBlackAreaCode;

public interface PlayerBlackAreaCodeRedisDao {
	/**
	 * 添加地区编号黑名单
	 * @param playerBlackAreaCode
	 */
	public void addPlayerBlackAreaCode(PlayerBlackAreaCode playerBlackAreaCode);
	/**
	 * 判断该地区是否在黑名单上
	 * @param areaCode
	 * @return
	 */
	public Boolean isExist(String areaCode);
	
	/**
	 * 删除地区黑名单
	 */
	public void deletePlayerBlackAreaCode();
}
