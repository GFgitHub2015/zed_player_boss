package com.zed.dao.player.black;

import java.io.Serializable;

import com.zed.dao.PageDao;
import com.zed.domain.player.black.PlayerBlackAreaCode;
import com.zed.exception.AppValidationException;

public interface PlayerBlackAreaCodeDao<T extends Serializable> extends PageDao<PlayerBlackAreaCode>{
	/**
	 * 根据地区编号获取黑名单记录
	 * @param areaCode
	 * @return
	 */
	public PlayerBlackAreaCode findByAreaCode(String areaCode) throws AppValidationException;
}
