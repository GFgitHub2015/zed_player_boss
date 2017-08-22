package com.zed.dao.player.white;

import java.io.Serializable;

import com.zed.dao.PageDao;
import com.zed.domain.player.white.PlayerWhiteAreaCode;
import com.zed.exception.AppValidationException;

public interface PlayerWhiteAreaCodeDao<T extends Serializable> extends PageDao<PlayerWhiteAreaCode>{
	/**
	 * 根据地区编号获取白名单记录
	 * @param areaCode
	 * @return
	 */
	public PlayerWhiteAreaCode findByAreaCode(String areaCode) throws AppValidationException;
}
