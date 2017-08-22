package com.zed.dao.iosplayer.slidershow.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zed.dao.iosplayer.slidershow.IosPlayerSliderShowDao;
import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.domain.iosplayer.slidershow.IosPlayerSliderShow;
import com.zed.exception.AppValidationException;

@Repository("iosPlayerSliderShowDao")
public class IosPlayerSliderShowDaoImpl<T> extends AbstractPlayerPageDao<IosPlayerSliderShow> implements IosPlayerSliderShowDao<IosPlayerSliderShow> {

	@Override
	public List<IosPlayerSliderShow> findBySortAndAreaCode(Integer sort, String areaCode) throws AppValidationException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sort", sort);
		params.put("areaCode", areaCode);
		params.put("origin", IosPlayerSliderShow.Origin.PLAYER.getOrigin());
		params.put("status", IosPlayerSliderShow.Status.START.getStatus());
		return findList("findBySortAndAreaCode", params);
	}

	public void updateStatusByUid(IosPlayerSliderShow iosPlayerSliderShow) throws AppValidationException {
		this.update("updateStatusByUid", iosPlayerSliderShow);
	}

}
