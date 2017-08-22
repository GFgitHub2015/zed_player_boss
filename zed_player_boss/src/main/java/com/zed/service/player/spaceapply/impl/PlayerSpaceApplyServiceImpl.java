package com.zed.service.player.spaceapply.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.dao.player.spaceapply.PlayerSpaceApplyDao;
import com.zed.domain.player.spaceapply.PlayerSpaceApply;
import com.zed.exception.AppValidationException;
import com.zed.service.player.spaceapply.PlayerSpaceApplyService;


@Service("playerSpaceApplyService")
public class PlayerSpaceApplyServiceImpl implements PlayerSpaceApplyService {
	
	@Resource(name="playerSpaceApplyDao")
	private PlayerSpaceApplyDao<PlayerSpaceApply> playerSpaceApplyDao;

	@Override
	public PlayerSpaceApply findById(String id) throws AppValidationException{
		return playerSpaceApplyDao.findById(id);
	}

	@Override
	public void update(PlayerSpaceApply playerSpaceApply) throws AppValidationException{
		playerSpaceApplyDao.update(playerSpaceApply);		
	}

	@Override
	public void updateByIdWithStatus(String id, Integer status) throws AppValidationException{
		PlayerSpaceApply playerSpaceApply = findById(id);
		if (playerSpaceApply != null) {
			playerSpaceApply.setStatus(status);
			update(playerSpaceApply);
		}
	}

	@Override
	public PlayerSpaceApply findByUserId(String userId) throws AppValidationException {
		PlayerSpaceApply playerSpaceApply = playerSpaceApplyDao.findByUserId(userId);
		if (playerSpaceApply != null) {
			return playerSpaceApply;
		}
		return null;
	}

	@Override
	public void updateByUserIdWithStatus(String userId, Integer status) throws AppValidationException {
		PlayerSpaceApply playerSpaceApply = findByUserId(userId);
		if (playerSpaceApply != null) {
			playerSpaceApply.setStatus(status);
			update(playerSpaceApply);
		}
	}

}
