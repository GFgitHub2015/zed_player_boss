package com.zed.redis.iosplayer.video;

import com.zed.domain.iosplayer.video.IosPlayerVideo;

/**
 * @date : 2017年5月22日 下午2:38:07
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 
*/
public interface IosPlayerVideoRedisDao {
	/**
	 * @date : 2017年5月17日 上午9:51:15
	 * @author : Iris.Xiao
	 * @description : 删除已经排序的页码
	*/
	public void deletePageList(String countryCode);

	/**
	 * @date : 2017年5月17日 上午9:51:15
	 * @author : Iris.Xiao
	 * @description : 删除已经排序的页码
	*/
	public void deletePageList(String countryCode,IosPlayerVideo video);

	/**
	 * @date : 2017年5月17日 上午9:51:15
	 * @author : Iris.Xiao
	 * @description : 删除已经排序的页码
	*/
	public void delete3dPageList(String countryCode);
	/**
	 * @date : 2017年5月17日 上午9:51:15
	 * @author : Iris.Xiao
	 * @description : 删除已经排序的页码
	*/
	public void delete3dPageList(String countryCode,IosPlayerVideo video);
	
	/**
	 * @date : 2017年5月17日 上午9:51:15
	 * @author : Iris.Xiao
	 * @description : 删除已经排序的页码
	*/
	public void deleteVideo(IosPlayerVideo video);

	/**
	 * @date : 2017年5月17日 上午9:51:15
	 * @author : Iris.Xiao
	 * @description : 删除活动
	*/
	public void deletePmt(String uid);
}
