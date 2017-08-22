package com.zed.dao.player.playeruser;

import java.io.Serializable;

import com.zed.dao.PageDao;
import com.zed.domain.player.playeruserhotsort.PlayerUserHotSort;
import com.zed.exception.AppValidationException;

public interface PlayerUserHotSortDao <T extends Serializable> extends PageDao<PlayerUserHotSort>{
	//根据用户id获取热门用户排序记录
	public PlayerUserHotSort findByUserId(String userId) throws AppValidationException;
	
	public void deleteByUserId(String []userIds) throws AppValidationException;
	//修改热门用户排序
	public void updateSort(PlayerUserHotSort playerUserHotSort) throws AppValidationException;
	//根据用户id获取热门用户排序记录
	public PlayerUserHotSort findByCountryCodeWithSort(String countryCode, Integer sort) throws AppValidationException;
	
	

}
