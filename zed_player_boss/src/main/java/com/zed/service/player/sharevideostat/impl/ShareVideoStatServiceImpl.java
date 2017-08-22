package com.zed.service.player.sharevideostat.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.dao.player.sharevideostat.ShareVideoStatDao;
import com.zed.domain.player.sharevideostat.ShareVideoStat;
import com.zed.redis.player.sharevideostat.ShareVideoStatRedisDao;
import com.zed.service.player.sharevideostat.ShareVideoStatService;
import com.zed.system.page.Page;

/**
 * @date : 2017年4月6日 下午1:52:23
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 影片分享统计
*/
@Service("shareVideoStatService")
public class ShareVideoStatServiceImpl implements ShareVideoStatService{

	@Resource(name="shareVideoStatDao")
	private ShareVideoStatDao<ShareVideoStat> shareVideoStatDao;
	@Resource(name="shareVideoStatRedisDao")
	private ShareVideoStatRedisDao shareVideoStatRedisDao;
	
	/**
	 * @date : 2017年4月6日 下午2:13:47
	 * @author : Iris.Xiao
	 * @param page
	 * @return
	 * @description : 查
	*/
	public Page<ShareVideoStat> findByPage(Page<ShareVideoStat> page){
		shareVideoStatDao.findByPage(page);
		return page;
	}

	/**
	 * @date : 2017年4月6日 下午2:13:47
	 * @author : Iris.Xiao
	 * @param page
	 * @return
	 * @description : 查
	*/
	public Page<ShareVideoStat> findPageByDate(Page<ShareVideoStat> page){
		return shareVideoStatDao.findPageByDate(page);
	}
	
	/**
	 * @date : 2017年4月6日 下午2:13:47
	 * @author : Iris.Xiao
	 * @param page
	 * @return
	 * @description : 查
	*/
	public Page<ShareVideoStat> findPageByDateDetail(Page<ShareVideoStat> page){
		return shareVideoStatDao.findPageByDateDetail(page);
	}
	
	/**
	 * @date : 2017年4月6日 下午2:13:47
	 * @author : Iris.Xiao
	 * @param page
	 * @return
	 * @description : 计算总和
	*/
	public ShareVideoStat  listTotal(Map<String,Object> params){
		return shareVideoStatDao.listTotal(params);
	}
}
