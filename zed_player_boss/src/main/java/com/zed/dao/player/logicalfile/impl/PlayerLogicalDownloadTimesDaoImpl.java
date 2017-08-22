package com.zed.dao.player.logicalfile.impl;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.logicalfile.PlayerLogicalDownloadTimesDao;
import com.zed.domain.player.logicalfile.PlayerLogicalDownloadTimes;
import com.zed.exception.AppValidationException;

@Repository("playerLogicalDownloadTimesDao")
public class PlayerLogicalDownloadTimesDaoImpl<T> extends AbstractPlayerPageDao<PlayerLogicalDownloadTimes> implements PlayerLogicalDownloadTimesDao<PlayerLogicalDownloadTimes> {

	@Override
	public PlayerLogicalDownloadTimes findByFileId(String fileId) throws AppValidationException {
		return findById("findByFileId", fileId);
	}

}
