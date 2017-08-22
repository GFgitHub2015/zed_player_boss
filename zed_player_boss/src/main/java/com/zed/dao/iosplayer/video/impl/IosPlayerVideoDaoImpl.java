package com.zed.dao.iosplayer.video.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zed.dao.iosplayer.video.IosPlayerVideoDao;
import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.domain.iosplayer.video.IosPlayerVideo;

/**
 * @date : 2017年5月9日 下午3:04:06
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 
*/
@Repository("iosPlayerVideoDao")
public class IosPlayerVideoDaoImpl<T> extends AbstractPlayerPageDao<IosPlayerVideo> implements IosPlayerVideoDao<IosPlayerVideo> {

	/**
	 * @date : 2017年5月9日 下午2:40:46
	 * @author : Iris.Xiao
	 * @param videoId
	 * @return
	 * @throws Exception
	 * @description : 根据videoid查找
	*/
	public List<IosPlayerVideo> getVideoByVideoId(String videoId) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("videoId", videoId);
		return this.findList("getVideoByVideoId", map);
	}
}
