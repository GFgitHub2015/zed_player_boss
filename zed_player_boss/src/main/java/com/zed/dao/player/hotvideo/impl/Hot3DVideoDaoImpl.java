package com.zed.dao.player.hotvideo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.hotvideo.Hot3DVideoDao;
import com.zed.domain.player.hotvideo.Hot3DVideo;
import com.zed.exception.AppValidationException;

/**
 * @date : 2016年12月28日 下午4:31:13
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 热门影片推荐
*/
@Repository("hot3DVideoDaoImpl")
public class Hot3DVideoDaoImpl<T> extends AbstractPlayerPageDao<Hot3DVideo> implements Hot3DVideoDao<Hot3DVideo> {
	/**
	 * @date : 2016年12月28日 下午4:48:15
	 * @author : Iris.Xiao
	 * @param uid
	 * @return
	 * @description : 根据VidioID来查询
	*/
	public List<Hot3DVideo> getHotViedeoByVideoId(String videoId)  throws AppValidationException{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("fileId", videoId);
		return this.findList("getHotViedeoByVideoId", map);
	}
	


}
