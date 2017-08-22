package com.zed.dao.player.sharevideostat;

import java.io.Serializable;
import java.util.Map;

import com.zed.dao.PageDao;
import com.zed.domain.player.sharevideostat.ShareVideoStat;
import com.zed.system.page.Page;

/**
 * @date : 2017年4月6日 下午1:49:17
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 影片分享统计
*/
public interface ShareVideoStatDao<T extends Serializable> extends PageDao<ShareVideoStat> {

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
