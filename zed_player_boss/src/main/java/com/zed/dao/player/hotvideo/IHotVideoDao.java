package com.zed.dao.player.hotvideo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.zed.dao.PageDao;
import com.zed.domain.player.hotvideo.HotVideo;
import com.zed.exception.AppValidationException;

/**
 * @date : 2016年12月28日 下午4:30:17
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 热门影片推荐
*/
public interface IHotVideoDao<T extends Serializable> extends PageDao<HotVideo> {

	/**
	 * @date : 2016年12月28日 下午4:48:15
	 * @author : Iris.Xiao
	 * @param uid
	 * @return
	 * @description : 根据VidioID来查询
	*/
	public List<HotVideo> getHotViedeoByVideoId(String videoId)  throws AppValidationException;
	/**
	 * @date : 2017年1月3日 上午10:45:33
	 * @author : Iris.Xiao
	 * @param params
	 * @return
	 * @description : 得到hot榜
	*/
	public List<Map> getTopHotVideoList(Map<String,Object> params)  throws AppValidationException;
	
	/**
	 * @date : 2017年1月19日 下午6:13:33
	 * @author : Iris.Xiao
	 * @param params
	 * @return
	 * @description : 找到最新的影片 默认36小时
	*/
	public List<Map> getNewHotVideoList(Map<String,Object> params)  throws AppValidationException;
	
	/**
	 * getHotVideoCount:查询热门影片数量)
	 * @author: Dell 
	 * @param hotVideo
	 * @return
	 * @throws AppValidationException
	 */
	int getHotVideoCount(HotVideo hotVideo) throws AppValidationException;
}
