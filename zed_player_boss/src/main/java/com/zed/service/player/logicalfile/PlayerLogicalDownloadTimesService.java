package com.zed.service.player.logicalfile;

import com.zed.domain.player.logicalfile.PlayerLogicalDownloadTimes;
import com.zed.exception.AppValidationException;

public interface PlayerLogicalDownloadTimesService {
	
	/**
	 * 根据逻辑文件fileId获取下载次数对象
	 * @param sourceFileId
	 * @return
	 */
	public PlayerLogicalDownloadTimes getPlayerDownloadTimesByFileId(String fileId) throws AppValidationException;
}
