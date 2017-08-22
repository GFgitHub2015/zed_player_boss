package com.zed.dao.player.slidershow;

import java.io.Serializable;
import java.util.List;

import com.zed.dao.PageDao;
import com.zed.domain.player.slidershow.PlayerSliderShow;
import com.zed.exception.AppValidationException;

public interface PlayerSliderShowDao<T extends Serializable> extends PageDao<PlayerSliderShow> {
	
	public List<PlayerSliderShow> findBySortAndAreaCode(Integer sort, String areaCode) throws AppValidationException;
	public void updateStatusByUid(PlayerSliderShow playerSliderShow) throws AppValidationException;
}
