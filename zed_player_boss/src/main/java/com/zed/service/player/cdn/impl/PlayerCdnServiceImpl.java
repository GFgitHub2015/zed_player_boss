package com.zed.service.player.cdn.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.dao.player.cdn.PlayerCdnDao;
import com.zed.domain.player.cdn.PlayerCdn;
import com.zed.exception.AppValidationException;
import com.zed.service.player.cdn.PlayerCdnService;
import com.zed.system.page.Page;
@Service("playerCdnService")
public class PlayerCdnServiceImpl implements PlayerCdnService {
	@Resource(name="playerCdnDao")
	private PlayerCdnDao playerCdnDao;
	@Override
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException {
		return playerCdnDao.findByPage(page);
	}

	@Override
	public PlayerCdn findById(String cdnId) throws AppValidationException {
		return (PlayerCdn) playerCdnDao.findById(cdnId);
	}

	@Override
	public void update(PlayerCdn playerCdn) throws AppValidationException {
		playerCdnDao.update(playerCdn);
	}

	@Override
	public void delete(String... cdnIds) throws AppValidationException {
		playerCdnDao.delete(cdnIds);
	}

	@Override
	public void updateStatus(PlayerCdn playerCdn) throws AppValidationException {
		playerCdnDao.updateStatus(playerCdn);
	}

	@Override
	public void add(PlayerCdn playerCdn) throws AppValidationException {
		playerCdnDao.add(playerCdn);
	}

	@Override
	public PlayerCdn getPlayerCdn() throws AppValidationException {
		return playerCdnDao.findByStatus();
	}

}
