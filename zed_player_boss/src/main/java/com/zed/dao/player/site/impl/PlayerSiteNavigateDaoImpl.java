package com.zed.dao.player.site.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.site.PlayerSiteNavigateDao;
import com.zed.domain.player.site.PlayerSiteNavigate;
import com.zed.exception.AppValidationException;

@Repository("playerSiteNavigateDao")
public class PlayerSiteNavigateDaoImpl<T> extends AbstractPlayerPageDao<PlayerSiteNavigate> implements PlayerSiteNavigateDao<PlayerSiteNavigate> {

	@Override
	public void updateStatus(PlayerSiteNavigate playerSiteNavigate) throws AppValidationException {
		update(playerSiteNavigate);
	}

	@Override
	public List<PlayerSiteNavigate> findAll() throws AppValidationException {
		return findList("findAll");
	}

	@Override
	public List<PlayerSiteNavigate> findByIds(String[] ids) throws AppValidationException {
		return findList("findByIds", ids);
	}

}
