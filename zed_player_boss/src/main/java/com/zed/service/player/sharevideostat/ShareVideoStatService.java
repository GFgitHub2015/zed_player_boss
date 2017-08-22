package com.zed.service.player.sharevideostat;

import java.util.Map;

import com.zed.domain.player.sharevideostat.ShareVideoStat;
import com.zed.system.page.Page;

/**
 * @date : 2017年4月6日 下午1:52:00
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 影片分享统计
*/
public interface ShareVideoStatService {
	/**
	 * @date : 2017年4月6日 下午2:13:47
	 * @author : Iris.Xiao
	 * @param page
	 * @return
	 * @description : 查
	*/
	public Page<ShareVideoStat> findByPage(Page<ShareVideoStat> page);
	
	/**
	 * @date : 2017年4月6日 下午2:13:47
	 * @author : Iris.Xiao
	 * @param page
	 * @return
	 * @description : 查
	*/
	public Page<ShareVideoStat> findPageByDate(Page<ShareVideoStat> page);
	
	/**
	 * @date : 2017年4月6日 下午2:13:47
	 * @author : Iris.Xiao
	 * @param page
	 * @return
	 * @description : 查
	*/
	public Page<ShareVideoStat> findPageByDateDetail(Page<ShareVideoStat> page);
	/**
	 * @date : 2017年4月6日 下午2:13:47
	 * @author : Iris.Xiao
	 * @param page
	 * @return
	 * @description : 计算总和
	*/
	public ShareVideoStat  listTotal(Map<String,Object> params);
}
