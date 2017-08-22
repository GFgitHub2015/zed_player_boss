package com.zed.service.player.spaceactiveuser;

import com.zed.domain.player.spaceactiveuser.PlayerSpaceActiveUser;
import com.zed.domain.system.Admin;
import com.zed.system.page.Page;

/**
 * @date : 2017年5月10日 下午2:56:00
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 
*/
public interface PlayerSpaceActiveUserService {

	/**
	 * @date : 2017年5月10日 下午3:31:42
	 * @author : Iris.Xiao
	 * @param page
	 * @description : 某个站长的活跃用户数据
	*/
	public void findByPage(Page<PlayerSpaceActiveUser> page);
	
	/**
	 * @date : 2017年5月11日 下午2:13:10
	 * @author : Iris.Xiao
	 * @param list
	 * @description : 查询统计数据,昨天数据,七天数据和30天数据,自动添加数据,自动生成前30天数据,以便sql计算平均值
	*/
	public void addActiveUserAuto(Page<PlayerSpaceActiveUser> page,Admin sessionAdmin);
	
	/**
	 * @date : 2017年5月10日 下午3:34:58
	 * @author : Iris.Xiao
	 * @param activeUser
	 * @description : 增
	*/
	public void addSpaceActiveUserStat(PlayerSpaceActiveUser activeUser);
	
	/**
	 * @date : 2017年5月10日 下午3:34:52
	 * @author : Iris.Xiao
	 * @param activeUser
	 * @description : 改
	*/
	public void updateSpaceActiveUserStat(PlayerSpaceActiveUser activeUser);
	/**
	 * @date : 2017-06-16 14:38:27
	 * @author : Akai
	 * @param activeUser
	 * @description : 改
	*/
	public void updateAdDataAdgActiveCount(PlayerSpaceActiveUser activeUser);
	
	/**
	 * @date : 2017年5月10日 下午3:34:44
	 * @author : Iris.Xiao
	 * @param uid
	 * @description : 删
	*/
	public void deleteSpaceActiveUserStat(String uid);
	

	/**
	 * @date : 2017年5月10日 下午3:43:17
	 * @author : Iris.Xiao
	 * @param statDate
	 * @param channel
	 * @return
	 * @description : 判断站长某日期数据是否存在
	*/
	public boolean existsSpaceActiveUserStat(String statDate,String channel);
	
	/**
	 * @date : 2017年5月11日 上午11:46:21
	 * @author : Iris.Xiao
	 * @param channel
	 * @return
	 * @description : 得到最小日期
	*/
	public String getMinStatDate(String channel);
}
