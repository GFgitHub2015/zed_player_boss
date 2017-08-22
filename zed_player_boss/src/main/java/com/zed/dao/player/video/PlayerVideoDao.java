package com.zed.dao.player.video;

import java.io.Serializable;
import java.util.List;

import com.zed.dao.PageDao;
import com.zed.domain.player.video.PlayerVideo;
import com.zed.exception.AppValidationException;

public interface PlayerVideoDao<T extends Serializable> extends PageDao<PlayerVideo> {

	//获取资源发布时的所有年份
	public List<String> findYears() throws AppValidationException;
	
	
	public List<String> findResIdListByHisIds(String[] hisIds) throws AppValidationException;
}
