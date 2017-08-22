package com.zed.redis.player.playeruser;

import java.util.Set;

/**
 * @date : 2017年2月13日 上午11:19:33
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 热门用户
*/
public interface IHotPlayerUserRedisDao {
	
	/**
	 * @date : 2017年2月13日 上午11:21:47
	 * @author : Iris.Xiao
	 * @param userId
	 * @description : 禁用
	*/
	public void disableHotUser(String userId);

	/**
	 * @date : 2017年2月13日 上午11:21:47
	 * @author : Iris.Xiao
	 * @param userId
	 * @description : 启用
	*/
	public void enableHotUser(String userId);
	
	/**
	 * @date : 2017年2月13日 上午11:25:06
	 * @author : Iris.Xiao
	 * @param userId
	 * @return
	 * @description : 是否已经禁用
	*/
	public boolean disabledFlag(String userId);
	
	/**
	 * @date : 2017年2月13日 上午11:25:06
	 * @author : Iris.Xiao
	 * @param userId
	 * @return
	 * @description : 得到所有禁用的
	*/
	public Set<String> getAll();
}
