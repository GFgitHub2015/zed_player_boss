package com.zed.service.player.player;

import java.util.Collection;
import java.util.Set;

import com.zed.exception.AppValidationException;

public interface PlayerVideoService {
	
	public Set<String> findResIdListByHisIds(Collection<String> hisIds) throws AppValidationException;

}
