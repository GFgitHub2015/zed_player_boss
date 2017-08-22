package com.zed.dao.player.slidershow.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.slidershow.PlayerSliderShowDao;
import com.zed.domain.player.slidershow.PlayerSliderShow;
import com.zed.exception.AppValidationException;

@Repository("playerSliderShowDao")
public class PlayerSliderShowDaoImpl<T> extends AbstractPlayerPageDao<PlayerSliderShow> implements PlayerSliderShowDao<PlayerSliderShow> {

	@Override
	public List<PlayerSliderShow> findBySortAndAreaCode(Integer sort, String areaCode) throws AppValidationException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sort", sort);
		params.put("areaCode", areaCode);
		params.put("origin", PlayerSliderShow.Origin.PLAYER.getOrigin());
		params.put("status", PlayerSliderShow.Status.START.getStatus());
		return findList("findBySortAndAreaCode", params);
	}

	public void updateStatusByUid(PlayerSliderShow playerSliderShow) throws AppValidationException {
		this.update("updateStatusByUid", playerSliderShow);
	}

}
