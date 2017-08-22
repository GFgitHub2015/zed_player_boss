package com.zed.service.player.logicalfile;

import com.zed.domain.player.logicalfile.PlayerLogicalPlayTimes;
import com.zed.exception.AppValidationException;

public interface PlayerLogicalPlayTimesService {
	
	/**
	 * 根据逻辑文件fileId获取播放次数对象
	 * @param sourceFileId
	 * @return
	 */
	public PlayerLogicalPlayTimes getPlayerPlayTimesByFileId(String fileId) throws AppValidationException;
	
	/**
	 * 
	 * @param fileId
	 * @return
	 * @throws AppValidationException
	 */
	public boolean isTheTopTenFile(String fileId) throws AppValidationException;

}
