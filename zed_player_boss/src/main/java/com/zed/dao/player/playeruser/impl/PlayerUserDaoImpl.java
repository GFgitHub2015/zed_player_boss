package com.zed.dao.player.playeruser.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.playeruser.PlayerUserDao;
import com.zed.domain.player.playeruser.PlayerUser;
import com.zed.exception.AppValidationException;

@Repository("playerUserDao")
public class PlayerUserDaoImpl<T> extends AbstractPlayerPageDao<PlayerUser> implements PlayerUserDao<PlayerUser>  {

	@Override
	public List<PlayerUser> findAllHotUser() throws AppValidationException {
		return findList("findAllHotUser");
	}

}
