package com.zed.dao.player.white;

import java.io.Serializable;

import com.zed.dao.PageDao;
import com.zed.domain.player.white.PlayerWhiteIp;
import com.zed.exception.AppValidationException;

public interface PlayerWhiteIpDao<T extends Serializable> extends PageDao<PlayerWhiteIp>{
	/**
	 * 根据ip地址获取白名单记录
	 * @param ip
	 * @return
	 */
	public PlayerWhiteIp findByIp(String ip) throws AppValidationException;
}
