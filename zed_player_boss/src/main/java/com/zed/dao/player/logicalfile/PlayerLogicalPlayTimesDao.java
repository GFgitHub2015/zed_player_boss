package com.zed.dao.player.logicalfile;

import java.io.Serializable;
import java.util.Set;

import com.zed.dao.PageDao;
import com.zed.domain.player.logicalfile.PlayerLogicalPlayTimes;
import com.zed.exception.AppValidationException;

public interface PlayerLogicalPlayTimesDao<T extends Serializable> extends PageDao<PlayerLogicalPlayTimes> {

	public PlayerLogicalPlayTimes findByFileId(String fileId) throws AppValidationException;
	
	public Set<String> findTopFileIds() throws AppValidationException;
}
