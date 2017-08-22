package com.zed.dao.player.spiderkeyword.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.spiderkeyword.PlayerSpiderKeyWordDao;
import com.zed.domain.player.spiderkeyword.PlayerSpiderKeyWord;
import com.zed.exception.AppValidationException;

@Repository("playerSpiderKeyWordDao")
public class PlayerRecommendKeyWordDaoImpl<T> extends AbstractPlayerPageDao<PlayerSpiderKeyWord> implements PlayerSpiderKeyWordDao<PlayerSpiderKeyWord> {


	@Override
	public List<PlayerSpiderKeyWord> findAllByIds(String[] ids) throws AppValidationException {
		return findList("findAllByIds", ids);
	}

	@Override
	public void updateStatus(PlayerSpiderKeyWord playerSpiderKeyWord) throws AppValidationException {
		update("updateStatus", playerSpiderKeyWord);
	}

}
