package com.zed.service.player.slidershow;

import java.util.HashMap;

import com.zed.domain.player.slidershow.PlayerSliderShow;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface PlayerSliderShowService {
	
	public PlayerSliderShow findById(String playerSliderShowId) throws AppValidationException;
	
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException;

	public void add(PlayerSliderShow playerSliderShow) throws AppValidationException;
	
	public void update(PlayerSliderShow playerSliderShow) throws AppValidationException;

	public void delete(String[] playerSliderShowIds) throws AppValidationException;
	
	public boolean getBySortAndAreaCode(String sliderShowId, Integer sort, String areaCode) throws AppValidationException;

}
