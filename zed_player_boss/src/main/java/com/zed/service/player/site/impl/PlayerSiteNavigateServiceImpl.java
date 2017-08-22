package com.zed.service.player.site.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.dao.player.site.PlayerSiteNavigateDao;
import com.zed.domain.player.site.PlayerSiteNavigate;
import com.zed.exception.AppValidationException;
import com.zed.redis.player.site.PlayerSiteNavigateRedisDao;
import com.zed.service.player.site.PlayerSiteNavigateService;
import com.zed.system.page.Page;

@Service("playerSiteNavigateService")
public class PlayerSiteNavigateServiceImpl implements PlayerSiteNavigateService {
	
	@Resource(name="playerSiteNavigateDao")
	private PlayerSiteNavigateDao playerSiteNavigateDao;
	
	@Resource(name="playerSiteNavigateRedisDao")
	private PlayerSiteNavigateRedisDao playerSiteNavigateRedisDao;

	@Override
	public PlayerSiteNavigate findById(String playerSiteNavigateId) throws AppValidationException {
		return (PlayerSiteNavigate) playerSiteNavigateDao.findById(playerSiteNavigateId);
	}

	@Override
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException {
		return playerSiteNavigateDao.findByPage(page);
	}

	@Override
	public void add(PlayerSiteNavigate playerSiteNavigate) throws AppValidationException {
		playerSiteNavigateRedisDao.deleteByCountryCodes(playerSiteNavigate.getCountryCode());
		playerSiteNavigateDao.add(playerSiteNavigate);		
		/*if (playerSiteNavigate.getStatus()==PlayerSiteNavigate.SiteStatus.START.getStatus()) {
			playerSiteNavigateRedisDao.addPlayerSiteNavigate(playerSiteNavigate);
		}*/
	}

	@Override
	public void update(PlayerSiteNavigate playerSiteNavigate) throws AppValidationException {
		PlayerSiteNavigate psn = this.findById(playerSiteNavigate.getSiteNavigateId());
		playerSiteNavigateRedisDao.deleteByCountryCodes(psn.getCountryCode());
		
		playerSiteNavigateDao.update(playerSiteNavigate);
//		if (playerSiteNavigate.getStatus()==PlayerSiteNavigate.SiteStatus.START.getStatus()) {
//			playerSiteNavigateRedisDao.addPlayerSiteNavigate(playerSiteNavigate);
//		} else {
		playerSiteNavigateRedisDao.deleteByCountryCodes(playerSiteNavigate.getCountryCode());
//		}
	}

	@Override
	public void delete(String[] playerSiteNavigateIds) throws AppValidationException {
		List<PlayerSiteNavigate> listTodelete = playerSiteNavigateDao.findByIds(playerSiteNavigateIds);
		Set<String> areaCodeSet = new HashSet<String>();
		for (PlayerSiteNavigate playerSiteNavigate : listTodelete) {
			areaCodeSet.add(playerSiteNavigate.getCountryCode());
		}
		
		playerSiteNavigateDao.delete(playerSiteNavigateIds);
		if (!areaCodeSet.isEmpty()) {
			String[] areaCodeArray = areaCodeSet.toArray(new String[areaCodeSet.size()]);
			playerSiteNavigateRedisDao.deleteByCountryCodes(areaCodeArray);
		}
	}

	@Override
	public void updateStatus(PlayerSiteNavigate playerSiteNavigate) throws AppValidationException {
		playerSiteNavigateDao.updateStatus(playerSiteNavigate);
		if (playerSiteNavigate.getStatus()==PlayerSiteNavigate.SiteStatus.START.getStatus()) {
			playerSiteNavigateRedisDao.addPlayerSiteNavigate(playerSiteNavigate);
		} else {
			playerSiteNavigateRedisDao.deleteByCountryCodes(playerSiteNavigate.getCountryCode());
		}
	}

	@Override
	public List<PlayerSiteNavigate> findAll() throws AppValidationException {
		return playerSiteNavigateDao.findAll();
	}

}
