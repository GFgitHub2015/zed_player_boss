package com.zed.dao.iosplayer.slidershow;

import java.io.Serializable;
import java.util.List;

import com.zed.dao.PageDao;
import com.zed.domain.iosplayer.slidershow.IosPlayerSliderShow;
import com.zed.domain.player.slidershow.PlayerSliderShow;
import com.zed.exception.AppValidationException;

public interface IosPlayerSliderShowDao<T extends Serializable> extends PageDao<IosPlayerSliderShow> {
	
	public List<IosPlayerSliderShow> findBySortAndAreaCode(Integer sort, String areaCode) throws AppValidationException;
	public void updateStatusByUid(IosPlayerSliderShow playerSliderShow) throws AppValidationException;
}
