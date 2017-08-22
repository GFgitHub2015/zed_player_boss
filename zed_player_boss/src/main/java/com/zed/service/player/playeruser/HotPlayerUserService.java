package com.zed.service.player.playeruser;

import java.util.HashMap;
import java.util.Map;

import com.zed.system.page.Page;
import com.zed.util.ConstantType;

/**
 * @date : 2017年2月10日 下午4:24:01
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 热门用户
*/
public interface HotPlayerUserService {
	
	public Page<HashMap> findByPage(Page<HashMap> page);
	

	/**
	 * @param user
	 * @description : 启用禁用热门用户
	*/
	public void updateStatus(String userId ,String curUserId, ConstantType.HotUserStatus status);
	
	
	/**
	 * @param user
	 * @description : 批量启用禁用热门用户
	*/
	public void updateStatusBatch(String[] userIds ,String curUserId, ConstantType.HotUserStatus status);
	
	/**
	 * 修改热门用户排序
	 * @param userId
	 * @param sort
	 */
	public void updateHotUserSort(String userId, Integer sort);
	//更新所有的热门用户
	public void updateAllHotUser();
	
	//获取每个国家所拥有的公开资源
	public Map<String, Object> getShareCountsWithCountryCodes();
}
