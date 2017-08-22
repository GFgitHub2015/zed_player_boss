package com.zed.dao.player.cdn;

import java.io.Serializable;

import com.zed.dao.PageDao;
import com.zed.domain.player.cdn.PlayerCdn;
import com.zed.exception.AppValidationException;

public interface PlayerCdnDao<T extends Serializable> extends PageDao<PlayerCdn> {

	//修改状态
	public void updateStatus(PlayerCdn playerCdn) throws AppValidationException;
	
	public PlayerCdn findByStatus() throws AppValidationException;
	
}
