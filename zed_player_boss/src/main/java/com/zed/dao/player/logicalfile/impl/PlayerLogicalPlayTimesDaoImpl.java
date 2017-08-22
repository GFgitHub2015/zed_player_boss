package com.zed.dao.player.logicalfile.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.logicalfile.PlayerLogicalPlayTimesDao;
import com.zed.domain.player.logicalfile.PlayerLogicalPlayTimes;
import com.zed.exception.AppValidationException;

@Repository("playerLogicalPlayTimesDao")
public class PlayerLogicalPlayTimesDaoImpl<T> extends AbstractPlayerPageDao<PlayerLogicalPlayTimes> implements PlayerLogicalPlayTimesDao<PlayerLogicalPlayTimes> {

	@Override
	public PlayerLogicalPlayTimes findByFileId(String fileId) throws AppValidationException {
		return findById("findByFileId", fileId);
	}

	@Override
	public Set<String> findTopFileIds() throws AppValidationException {
		Set<String> result = new HashSet<String>();
		List<PlayerLogicalPlayTimes> list = findList("findTopFileIds");
		for (PlayerLogicalPlayTimes playerLogicalPlayTimes : list) {
			result.add(playerLogicalPlayTimes.getFileId());
		}
		return result;
	}

}
