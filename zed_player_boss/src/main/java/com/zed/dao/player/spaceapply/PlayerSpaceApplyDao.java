package com.zed.dao.player.spaceapply;

import java.io.Serializable;

import com.zed.dao.PageDao;
import com.zed.domain.player.spaceapply.PlayerSpaceApply;
import com.zed.exception.AppValidationException;

public interface PlayerSpaceApplyDao<T extends Serializable> extends PageDao<PlayerSpaceApply> {
	
	public PlayerSpaceApply findByUserId(String userId) throws AppValidationException;

}
