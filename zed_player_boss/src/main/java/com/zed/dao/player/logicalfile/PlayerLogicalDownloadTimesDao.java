package com.zed.dao.player.logicalfile;

import java.io.Serializable;

import com.zed.dao.PageDao;
import com.zed.domain.player.logicalfile.PlayerLogicalDownloadTimes;
import com.zed.exception.AppValidationException;

public interface PlayerLogicalDownloadTimesDao<T extends Serializable> extends PageDao<PlayerLogicalDownloadTimes> {

	public PlayerLogicalDownloadTimes findByFileId(String fileId)  throws AppValidationException;
	
}
