package com.zed.dao.player.logicalfile.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.logicalfile.PlayerLogicalFileDao;
import com.zed.domain.player.logicalfile.PlayerLogicalFile;
import com.zed.exception.AppValidationException;

@Repository("playerLogicalFileDao")
public class PlayerLogicalFileDaoImpl<T> extends AbstractPlayerPageDao<PlayerLogicalFile> implements PlayerLogicalFileDao<PlayerLogicalFile> {

	@Override
	public void updateStatus(PlayerLogicalFile playerLogicalFile) throws AppValidationException {
		update("updateStatus", playerLogicalFile);
	}

	@Override
	public List<PlayerLogicalFile> findListByIds(String[] fileIds) throws AppValidationException {
		return findList("findListByIds", fileIds);
	}
	
	public void updateStatusBatchByUserId(List<PlayerLogicalFile> playerLogicalFiles) throws AppValidationException {
		this.updateAll("updateStatusBatchByUserId", playerLogicalFiles);
	}
	
}
