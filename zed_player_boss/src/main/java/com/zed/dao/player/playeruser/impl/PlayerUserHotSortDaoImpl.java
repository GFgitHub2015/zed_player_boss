package com.zed.dao.player.playeruser.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.playeruser.PlayerUserHotSortDao;
import com.zed.domain.player.playeruserhotsort.PlayerUserHotSort;
import com.zed.exception.AppValidationException;

@Repository("playerUserHotSortDao")
public class PlayerUserHotSortDaoImpl<T> extends AbstractPlayerPageDao<PlayerUserHotSort> implements PlayerUserHotSortDao<PlayerUserHotSort>{

	@Override
	public PlayerUserHotSort findByUserId(String userId) throws AppValidationException{
		return findById("findByUserId", userId);
	}

	@Override
	public void deleteByUserId(String[] userIds) throws AppValidationException{
		delete("deleteByUserId", userIds);
	}

	@Override
	public void updateSort(PlayerUserHotSort playerUserHotSort) throws AppValidationException{
		update("updateSort", playerUserHotSort);
	}

	@Override
	public PlayerUserHotSort findByCountryCodeWithSort(String countryCode, Integer sort)
			throws AppValidationException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("countryCode", countryCode);
		param.put("sort", sort);
		return find("findByCountryCodeWithSort", param);
	}

}
