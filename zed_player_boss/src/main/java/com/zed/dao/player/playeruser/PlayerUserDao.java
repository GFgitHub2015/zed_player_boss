package com.zed.dao.player.playeruser;

import java.io.Serializable;
import java.util.List;

import com.zed.dao.PageDao;
import com.zed.domain.player.playeruser.PlayerUser;
import com.zed.exception.AppValidationException;

public interface PlayerUserDao<T extends Serializable> extends PageDao<PlayerUser> {
	
	public List<PlayerUser> findAllHotUser() throws AppValidationException;
	
}
