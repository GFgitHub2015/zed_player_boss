package com.zed.service.iosplayer.slidershow;

import java.util.HashMap;

import com.zed.domain.iosplayer.slidershow.IosPlayerSliderShow;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface IosPlayerSliderShowService {
	
	public IosPlayerSliderShow findById(String playerSliderShowId) throws AppValidationException;
	
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException;

	public void add(IosPlayerSliderShow playerSliderShow) throws AppValidationException;
	
	public void update(IosPlayerSliderShow playerSliderShow) throws AppValidationException;

	public void delete(String[] playerSliderShowIds) throws AppValidationException;
	
	public boolean getBySortAndAreaCode(String sliderShowId, Integer sort, String areaCode) throws AppValidationException;

}
