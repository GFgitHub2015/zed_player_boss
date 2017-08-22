package com.zed.dao.player.spacereward.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.spacereward.SpaceRewardDao;
import com.zed.domain.player.spacereward.SpaceReward;

/**
 * @date : 2017年7月27日 上午9:27:22
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 
*/
@Repository("spaceRewardDao")
public class SpaceRewardDaoImpl  extends AbstractPlayerPageDao<SpaceReward> implements SpaceRewardDao<SpaceReward>{

	/**
	 * @date : 2017年7月27日 下午2:41:15
	 * @author : Iris.Xiao
	 * @return
	 * @description : 得到所有徽章列表
	*/
	public List<SpaceReward> findBadgeList(){
		Map<String,Object> map = new HashMap<String,Object>();
		return this.findList("findBadgeList", map);
	}
	
	
	/**
	 * @date : 2017年7月28日 上午10:29:53
	 * @author : Iris.Xiao
	 * @param rewardIds
	 * @description : 添加到钱包
	*/
	public void addPayementByRewardIds(List<String> rewardIds){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("rewardIds", rewardIds);
		this.update("addPayementByRewardIds", map);
	}

	/**
	 * @date : 2017年8月1日 下午2:00:58
	 * @author : Iris.Xiao
	 * @param paymentId
	 * @description : 删除钱包的奖励
	*/
	public void deletePayment(String paymentId){

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("paymentId", paymentId);
		this.update("deletePayment", map);
	}
}
