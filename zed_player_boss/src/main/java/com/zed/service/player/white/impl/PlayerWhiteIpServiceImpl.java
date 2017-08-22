package com.zed.service.player.white.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.dao.player.white.PlayerWhiteIpDao;
import com.zed.domain.player.white.PlayerWhiteIp;
import com.zed.exception.AppValidationException;
import com.zed.redis.player.white.PlayerWhiteIpRedisDao;
import com.zed.service.player.white.PlayerWhiteIpService;
import com.zed.system.page.Page;

@Service("playerWhiteIpService")
public class PlayerWhiteIpServiceImpl implements PlayerWhiteIpService {
	
	@Resource(name="playerWhiteIpDao")
	private PlayerWhiteIpDao playerWhiteIpDao;
	
	@Resource(name="playerWhiteIpRedisDao")
	private PlayerWhiteIpRedisDao playerWhiteIpRedisDao;

	@Override
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException {
		return playerWhiteIpDao.findByPage(page);
	}

	@Override
	public PlayerWhiteIp findByIp(String ip) throws AppValidationException {
		return playerWhiteIpDao.findByIp(ip);
	}

	@Override
	public void add(PlayerWhiteIp playerWhiteIp) throws AppValidationException {
		playerWhiteIpDao.add(playerWhiteIp);
		playerWhiteIpRedisDao.addPlayerWhiteIp(playerWhiteIp);
	}

	/*@Override
	public void update(PlayerWhiteIp playerWhiteIp) throws AppValidationException {
		playerWhiteIpDao.update(playerWhiteIp);		
	}*/

	@Override
	public void delete(String... ips) throws AppValidationException {
		playerWhiteIpDao.delete(ips);
		playerWhiteIpRedisDao.deletePlayerWhiteIp();
	}

}
