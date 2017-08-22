package com.zed.dao.player.playerhotuser;

import java.io.Serializable;
import java.util.List;

import com.zed.dao.PageDao;
import com.zed.domain.player.playerhotuser.PlayerRelationHotUser;
import com.zed.exception.AppValidationException;

public interface IPlayerRelationHotUserDao <T extends Serializable> extends PageDao<PlayerRelationHotUser> {
	
	PlayerRelationHotUser getLastSort() throws AppValidationException;
	
	PlayerRelationHotUser findByUserId(String userId) throws AppValidationException;
	
	void updateSortByUserId(List<PlayerRelationHotUser> playerHotUsers);
		
	List<PlayerRelationHotUser> findBySort(Long sort);
	
}
