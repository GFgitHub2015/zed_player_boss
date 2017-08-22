package com.zed.dao.player.hotvideo;

import java.io.Serializable;
import java.util.List;

import com.zed.dao.PageDao;
import com.zed.domain.player.hotvideo.Hot3DVideo;
import com.zed.exception.AppValidationException;

/**
 * @date : 2016年12月28日 下午4:30:17
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 热门影片推荐
*/
public interface Hot3DVideoDao<T extends Serializable> extends PageDao<Hot3DVideo> {

	/**
	 * @date : 2016年12月28日 下午4:48:15
	 * @author : Iris.Xiao
	 * @param uid
	 * @return
	 * @description : 根据VidioID来查询
	*/
	public List<Hot3DVideo> getHotViedeoByVideoId(String videoId)  throws AppValidationException;
}
