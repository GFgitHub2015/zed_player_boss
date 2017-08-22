package com.zed.redis.player.hotvideo;

import java.util.List;
import java.util.Map;

import com.zed.common.util.redis.RedisKey;
import com.zed.domain.player.hotvideo.HotVideo;

/**
 * @date : 2016年12月29日 上午9:44:04
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 热门推荐
*/
public interface IHotVideoRedisDao {

	/**
	 * @date : 2016年12月28日 下午4:48:15
	 * @author : Iris.Xiao
	 * @param uid
	 * @return
	 * @description : 详情
	*/
	public HotVideo getHotVideo(String uid);

	/**
	 * @date : 2016年12月28日 下午4:49:37
	 * @author : Iris.Xiao
	 * @param uid
	 * @description : 删除
	*/
	public void deleteHotVideo(String uid);

	/**
	 * @date : 2016年12月28日 下午4:49:37
	 * @author : Iris.Xiao
	 * @param uid
	 * @description : 添加
	*/
	public void addHotVideo(HotVideo hotVideo);

	/**
	 * @date : 2016年12月28日 下午4:49:37
	 * @author : Iris.Xiao
	 * @param uid
	 * @description : 添加到热门列表和活动的排序结果中
	*/
	public void addHotVideoAndPmtList(HotVideo hotVideo);
	
	/**
	 * @date : 2016年12月28日 下午4:49:37
	 * @author : Iris.Xiao
	 * @param uid
	 * @description :删除热门列表和活动的排序结果
	*/
	public void deleteHotVideoAndPmtList(HotVideo hotVideo);
	
	/**
	 * @date : 2017年1月3日 上午10:09:58
	 * @author : Iris.Xiao
	 * @param hotVideo
	 * @description : 刷新播放次数次数top榜
	*/
	public void updateHotVideoTopList(List<Map> list, String countryCode);
	
	/**
	 * @date : 2017年1月3日 下午2:39:55
	 * @author : Iris.Xiao
	 * @param fileId
	 * @description :  删除fileid>uid文件id查找热门影片id
	*/
	public void deleteHotVideoByFileId(String fileId);

	/**
	 * @date : 2017年1月3日 下午2:41:52
	 * @author : Iris.Xiao
	 * @param hotVideo
	 * @description : 添加fileid>uid文件id查找热门影片id
	*/
	public void addHotVideoByFileId(HotVideo hotVideo);
	/**
	 * @date : 2017年1月3日 下午2:54:33
	 * @author : Iris.Xiao
	 * @param fileId
	 * @return
	 * @description : 
	*/
	public HotVideo getHotVideoByFileId(String fileId);
}
