package com.zed.service.player.player;

import java.util.Map;

import com.zed.exception.AppValidationException;

public interface PlayerVideoSourceFileService {
	
	public Map<Integer, Object> findTransCodeStatByUserId(String userId) throws AppValidationException;

}
