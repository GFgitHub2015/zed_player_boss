package com.zed.dao.player.spaceactiveuser;

import java.io.Serializable;
import java.util.Map;

import com.zed.dao.PageDao;
import com.zed.domain.player.spaceactiveuser.PlayerSpaceActiveUser;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

/**
 * @date : 2017年5月10日 上午11:30:19
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 站长活跃用户
*/
public interface PlayerSpaceActiveUserDao <T extends Serializable> extends PageDao<PlayerSpaceActiveUser>{

	/**
	 * @date : 2017年5月10日 下午3:28:55
	 * @author : Iris.Xiao
	 * @param page
	 * @return
	 * @throws AppValidationException
	 * @description : 查询统计数据,昨天数据,七天数据和30天数据
	*/
	public Page<PlayerSpaceActiveUser> findMasterStat(Page<PlayerSpaceActiveUser> page) throws AppValidationException;
	
	/**
	 * @date : 2017年5月11日 上午11:46:21
	 * @author : Iris.Xiao
	 * @param channel
	 * @return
	 * @description : 得到最小日期
	*/
	public String getMinStatDate(String channel)  throws AppValidationException;
	/**
	 * @date : 2017年6月12日 下午3:11:48
	 * @author : Iris.Xiao
	 * @param activeUser
	 * @description : 
	*/
	public void updateAdDataActiveCount(Map<String,Object> params);
	/**
	 * @date : 2017-06-16 14:37:02
	 * @author : Akai
	 * @param activeUser
	 * @description : 
	*/
	public void updateAdgAdDataActiveCount(Map<String,Object> params);
}
