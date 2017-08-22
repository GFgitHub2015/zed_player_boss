package com.zed.service.player.spacereward;

import java.util.List;

import com.zed.domain.player.spacereward.SpaceReward;
import com.zed.system.page.Page;

/**
 * @date : 2017年7月27日 上午9:36:40
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 网盘奖励
*/
public interface SpaceRewardService {

	/**
	 * @date : 2017年7月27日 上午9:39:04
	 * @author : Iris.Xiao
	 * @param page
	 * @return
	 * @description : 查
	*/
	public Page<SpaceReward> findByPage(Page<SpaceReward> page);
	

	/**
	 * @date : 2017年7月27日 上午9:39:04
	 * @author : Iris.Xiao
	 * @param page
	 * @return
	 * @description : 增
	*/
	public void addSpaceReward(SpaceReward reward);
	

	/**
	 * @date : 2017年7月27日 上午9:39:04
	 * @author : Iris.Xiao
	 * @param page
	 * @return
	 * @description : 删
	*/
	public void deleteSpaceReward(SpaceReward reward);
	
	/**
	 * @date : 2017年7月27日 上午9:39:04
	 * @author : Iris.Xiao
	 * @param page
	 * @return
	 * @description : 得到
	*/
	public SpaceReward getSpaceReward(String id);
	
	/**
	 * @date : 2017年7月27日 下午2:41:15
	 * @author : Iris.Xiao
	 * @return
	 * @description : 得到所有徽章列表
	*/
	public List<SpaceReward> findBadgeList();
}
