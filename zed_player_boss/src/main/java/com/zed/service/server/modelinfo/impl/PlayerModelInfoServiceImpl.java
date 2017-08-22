package com.zed.service.server.modelinfo.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.dao.server.modelinfo.PlayerModelInfoDao;
import com.zed.domain.server.modelinfo.PlayerModelInfo;
import com.zed.exception.AppValidationException;
import com.zed.redis.server.modelinfo.PlayerModelInfoRedisDao;
import com.zed.service.server.modelinfo.PlayerModelInfoService;
import com.zed.system.page.Page;

@Service("playerModelInfoService")
public class PlayerModelInfoServiceImpl implements PlayerModelInfoService {
	
	@Resource(name="playerModelInfoRedisDao")
	private PlayerModelInfoRedisDao playerModelInfoRedisDao;
	
	@Resource(name="playerModelInfoDao")
	private PlayerModelInfoDao playerModelInfoDao;

	@Override
	public PlayerModelInfo findById(String model) throws AppValidationException {
		return (PlayerModelInfo) playerModelInfoDao.findById(model);
	}

	@Override
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException {
		return playerModelInfoDao.findByPage(page);
	}

	@Override
	public void add(PlayerModelInfo playerModelInfo) throws AppValidationException {
		playerModelInfoDao.add(playerModelInfo);
		playerModelInfoRedisDao.addPlayerModelInfo(playerModelInfo);
	}

	@Override
	public void update(PlayerModelInfo playerModelInfo) throws AppValidationException {
		playerModelInfoDao.update(playerModelInfo);
		playerModelInfoRedisDao.addPlayerModelInfo(playerModelInfo);
	}

	@Override
	public void delete(String[] models) throws AppValidationException {
		playerModelInfoDao.delete(models);		
		playerModelInfoRedisDao.deletePlayerModelInfoList(models);
	}

	@Override
	public Boolean updateAll() throws AppValidationException {
		Boolean flag = Boolean.FALSE;
		
		List<PlayerModelInfo> list = playerModelInfoDao.findAll();
		if (!flag&&!list.isEmpty()&&list.size()>0) {
			playerModelInfoRedisDao.deleteAll();
			flag = Boolean.TRUE;
		}
		if (flag) {
			playerModelInfoRedisDao.addPlayerModelInfoList(list);
		}
		return flag;
	}

	@Override
	public Boolean isExist(String model) throws AppValidationException {
		List<PlayerModelInfo> list = playerModelInfoDao.findAll();
		for (PlayerModelInfo playerModelInfo : list) {
			if (model.equals(playerModelInfo.getModel())) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

}
