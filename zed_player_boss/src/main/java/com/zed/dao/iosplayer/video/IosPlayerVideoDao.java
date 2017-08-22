package com.zed.dao.iosplayer.video;

import java.io.Serializable;
import java.util.List;

import com.zed.dao.PageDao;
import com.zed.domain.iosplayer.video.IosPlayerVideo;

/**
 * @date : 2017年5月9日 下午3:03:26
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 
*/
public interface IosPlayerVideoDao <T extends Serializable> extends PageDao<IosPlayerVideo>{
	/**
	 * @date : 2017年5月9日 下午2:40:46
	 * @author : Iris.Xiao
	 * @param videoId
	 * @return
	 * @throws Exception
	 * @description : 根据videoid查找
	*/
	public List<IosPlayerVideo> getVideoByVideoId(String videoId) throws Exception;
}
