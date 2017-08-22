package com.zed.dao.player.spacereward;

import java.util.List;

import com.zed.dao.PageDao;
import com.zed.domain.player.spacereward.SpaceReward;

/**
 * @date : 2017年7月27日 上午9:27:06
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 网盘奖励
*/
public interface SpaceRewardDao <T> extends PageDao<SpaceReward> {
	/**
	 * @date : 2017年7月27日 下午2:41:15
	 * @author : Iris.Xiao
	 * @return
	 * @description : 得到所有徽章列表
	*/
	public List<SpaceReward> findBadgeList();
	
	/**
	 * @date : 2017年7月28日 上午10:29:53
	 * @author : Iris.Xiao
	 * @param rewardIds
	 * @description : 添加到钱包
	*/
	public void addPayementByRewardIds(List<String> rewardIds);
	
	/**
	 * @date : 2017年8月1日 下午2:00:58
	 * @author : Iris.Xiao
	 * @param paymentId
	 * @description : 删除钱包的奖励
	*/
	public void deletePayment(String paymentId);
}
