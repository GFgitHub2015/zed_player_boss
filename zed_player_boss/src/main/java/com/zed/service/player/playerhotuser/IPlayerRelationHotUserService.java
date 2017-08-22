package com.zed.service.player.playerhotuser;

import com.zed.domain.params.YoutubeParams;
import com.zed.domain.player.playerhotuser.PlayerRelationHotUser;
import com.zed.domain.player.playerhotuser.YouTubeHotUser;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;
import com.zed.system.page.YouTubePage;

/**
 * @date : 2017年7月03日 下午4:10:01
 * @author : X.Long
 * @version : 1.0
 * @description : 
*/
public interface IPlayerRelationHotUserService {

	Page<PlayerRelationHotUser> findByPage(Page<PlayerRelationHotUser> page) throws AppValidationException;

	void updateStatusBatchByUserId(String[] userIds, Integer status) throws AppValidationException;
	
	YouTubePage<YouTubeHotUser> findYouTubeUserByName(YoutubeParams youtubeParam) throws AppValidationException;
	
	Long getLastSort() throws AppValidationException;
	
	void addHotUser(PlayerRelationHotUser playerHotUser);
	
	PlayerRelationHotUser findByUserId(String userId);
	
	void deleteByUId(String uid);

	void updateSortByUserId(String uid, Long sort);

}
