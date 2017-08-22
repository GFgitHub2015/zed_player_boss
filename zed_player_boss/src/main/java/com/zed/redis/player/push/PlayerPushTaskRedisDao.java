package com.zed.redis.player.push;

import com.zed.domain.player.push.PlayerTaskParams;

public interface PlayerPushTaskRedisDao {
	/**
	 * 保存任务相关数据信息
	 * @param playerTaskParams
	 */
	public void addPlayerTaskParams(PlayerTaskParams playerTaskParams);
	
	/**
	 * 获取任务相关数据信息
	 * @param taskId
	 * @return
	 */
	public PlayerTaskParams getByTaskId(String taskId);
	
	/**
	 * 删除任务相关数据缓存信息
	 */
	public void deleteByTaskId(String ...taskId);
	
}
