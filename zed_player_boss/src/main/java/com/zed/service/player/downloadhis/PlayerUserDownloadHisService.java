package com.zed.service.player.downloadhis;

import java.util.Set;

import com.zed.exception.AppValidationException;
 
public interface PlayerUserDownloadHisService {
	
	/**
	 * 根据用户的userId获取用户的下载的原点资源的记录信息
	 * @param userId
	 * @return
	 * @throws AppValidationException
	 */
	public Set<String> findHisIdByUserId(String userId) throws AppValidationException;

}
