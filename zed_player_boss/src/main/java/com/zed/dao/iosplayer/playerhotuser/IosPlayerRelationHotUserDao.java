package com.zed.dao.iosplayer.playerhotuser;

import java.io.Serializable;
import java.util.List;

import com.zed.dao.PageDao;
import com.zed.domain.iosplayer.playerhotuser.IosPlayerRelationHotUser;
import com.zed.exception.AppValidationException;

public interface IosPlayerRelationHotUserDao <T extends Serializable> extends PageDao<IosPlayerRelationHotUser> {
	
	IosPlayerRelationHotUser getLastSort() throws AppValidationException;
	
	IosPlayerRelationHotUser findByUserId(String userId) throws AppValidationException;
	
	void updateSortByUserId(List<IosPlayerRelationHotUser> playerHotUsers);
		
	List<IosPlayerRelationHotUser> findBySort(Long sort);
	
}
