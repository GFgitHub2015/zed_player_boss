package com.zed.service.server.config.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.dao.server.config.PlayerConfigDao;
import com.zed.domain.server.config.PlayerConfig;
import com.zed.exception.AppValidationException;
import com.zed.redis.server.config.PlayerConfigRedisDao;
import com.zed.service.server.config.PlayerConfigService;
import com.zed.system.page.Page;

@Service("playerConfigService")
public class PlayerConfigServiceImpl implements PlayerConfigService {
	
	@Resource(name="playerConfigRedisDao")
	private PlayerConfigRedisDao playerConfigRedisDao;
	
	@Resource(name="playerConfigDao")
	private PlayerConfigDao playerConfigDao;

	@Override
	public PlayerConfig findById(String id) throws AppValidationException {
		return (PlayerConfig) playerConfigDao.findById(id);
	}

	@Override
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException {
		return playerConfigDao.findByPage(page);
	}

	@Override
	public void add(PlayerConfig playerConfig) throws AppValidationException {
		playerConfigDao.add(playerConfig);
	}

	@Override
	public void update(PlayerConfig playerConfig) throws AppValidationException {
		playerConfigDao.update(playerConfig);
		playerConfigRedisDao.deletePlayerConfigList(playerConfig.getVersion());
	}

	@Override
	public void delete(String[] ids) throws AppValidationException {
		List<PlayerConfig> list = playerConfigDao.findAllByIds(ids);
		Set<String> versionList = new HashSet<String>();
		if (list!=null&&!list.isEmpty()&&list.size()>0) {
			for (PlayerConfig playerConfig : list) {
				versionList.add(playerConfig.getVersion());
			}
			playerConfigRedisDao.deletePlayerConfigList(versionList.toArray(new String[versionList.size()]));
		}
		playerConfigDao.delete(ids);	
	}

	@Override
	public Boolean isExist(String version, String packageName) throws AppValidationException {
		PlayerConfig playerConfig = playerConfigDao.findByVersionAndPackageName(version, packageName);
		if (playerConfig!=null) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

}
