package com.zed.service.player.push;

import com.zed.domain.player.push.PlayerTaskParams;
import com.zed.exception.AppValidationException;

public interface PlayerTaskParamsService {
	/**
	 * 根据任务id获取任务相关数据
	 * @param taskId
	 * @return
	 * @throws AppValidationException
	 */
	public PlayerTaskParams findByTaskId(String taskId) throws AppValidationException;
	
	/**
	 * 添加任务数据
	 * @param playerTaskParams
	 * @throws AppValidationException
	 */
	public void addPlayerTaskParams(PlayerTaskParams playerTaskParams) throws AppValidationException;
	
	/**
	 * 根据任务id删除任务数据
	 * @param taskId
	 * @throws AppValidationException
	 */
	public void deletePlayerTaskParams(String ...taskIds) throws AppValidationException;
	
	/**
	 * 修改任务相关数据信息
	 * @param playerTaskParams
	 */
	public void updatePlayerTaskParams(PlayerTaskParams playerTaskParams);
	
}
