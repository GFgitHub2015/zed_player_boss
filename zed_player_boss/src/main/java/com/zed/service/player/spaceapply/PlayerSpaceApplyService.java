package com.zed.service.player.spaceapply;

import com.zed.domain.player.spaceapply.PlayerSpaceApply;
import com.zed.exception.AppValidationException;

public interface PlayerSpaceApplyService {
	//通过id获取space申请记录
	public PlayerSpaceApply findById(String id) throws AppValidationException;
	//修改space申请记录
	public void update(PlayerSpaceApply playerSpaceApply) throws AppValidationException;
	//通过主键id修改状态
	public void updateByIdWithStatus(String id, Integer status) throws AppValidationException;
	//通过userId修改状态
	public void updateByUserIdWithStatus(String userId, Integer status) throws AppValidationException;
	//通过用户id获取space申请记录
	public PlayerSpaceApply findByUserId(String userId) throws AppValidationException;

}
