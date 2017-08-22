package com.zed.dao.player.sharevideostat.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.sharevideostat.ShareVideoStatDao;
import com.zed.domain.player.sharevideostat.ShareVideoStat;
import com.zed.system.page.Page;

/**
 * @date : 2017年4月6日 下午1:49:50
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 影片分享统计
*/
@Repository("shareVideoStatDao")
public class ShareVideoStatDaoImpl<T> extends AbstractPlayerPageDao<ShareVideoStat> implements ShareVideoStatDao<ShareVideoStat>{
	/**
	 * @date : 2017年4月6日 下午2:13:47
	 * @author : Iris.Xiao
	 * @param page
	 * @return
	 * @description : 查
	*/
	public Page<ShareVideoStat> findPageByDate(Page<ShareVideoStat> page){
		return this.findByPage("findPageByDate", "findPageByDateCount", page);
	}
	
	/**
	 * @date : 2017年4月6日 下午2:13:47
	 * @author : Iris.Xiao
	 * @param page
	 * @return
	 * @description : 查
	*/
	public Page<ShareVideoStat> findPageByDateDetail(Page<ShareVideoStat> page){
		return this.findByPage("findPageByDateDetail", "findPageByDateDetailCount", page);
	}

	/**
	 * @date : 2017年4月6日 下午2:13:47
	 * @author : Iris.Xiao
	 * @param page
	 * @return
	 * @description : 计算总和
	*/
	public ShareVideoStat  listTotal(Map<String,Object> params){
		return (ShareVideoStat) this.findOne("listTotal", params);
	}
}
