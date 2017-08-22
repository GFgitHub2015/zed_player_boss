package com.zed.service.player.spacereward.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.common.util.CommUtil;
import com.zed.common.util.DateUtil;
import com.zed.dao.player.spacereward.SpaceRewardDao;
import com.zed.domain.player.spacereward.SpaceReward;
import com.zed.service.player.spacereward.SpaceRewardService;
import com.zed.system.page.Page;

/**
 * @date : 2017年7月27日 上午9:36:59
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 网盘奖励
*/
@Service("spaceRewardService")
public class SpaceRewardServiceImpl implements SpaceRewardService {
	
	@Resource(name="spaceRewardDao")
	private SpaceRewardDao<SpaceReward> spaceRewardDao;

	/**
	 * @date : 2017年7月27日 上午9:39:04
	 * @author : Iris.Xiao
	 * @param page
	 * @return
	 * @description : 查
	*/
	public Page<SpaceReward> findByPage(Page<SpaceReward> page){
		spaceRewardDao.findByPage(page);
		return page;
	}

	/**
	 * @date : 2017年7月27日 上午9:39:04
	 * @author : Iris.Xiao
	 * @param page
	 * @return
	 * @description : 增
	*/
	public void addSpaceReward(SpaceReward rewardData){
		String channels = rewardData.getChannel();
		if(CommUtil.isEmpty(channels)){
			return;
		}
		String[] channelsArr = channels.split(",");
		Timestamp curtime = new Timestamp(System.currentTimeMillis());
		List<String> rewardIds = new ArrayList<String>();
		String rewardId="";
		for (String channel : channelsArr) {
			channel = channel.trim();
			SpaceReward reward = new SpaceReward();
			rewardId = CommUtil.getUUID();
			rewardIds.add(rewardId);
			reward.setId(rewardId);
			reward.setChannel(channel);
			reward.setEarnings(rewardData.getEarnings());
			reward.setSourceType(rewardData.getSourceType());
			reward.setCreateTime(curtime);
			reward.setCreateUser(rewardData.getCreateUser());
			reward.setUpdateUser(rewardData.getCreateUser());
			reward.setUpdateTime(curtime);
			reward.setMemo(rewardData.getMemo());
			reward.setPaymentId(CommUtil.getUUID());
			reward.setState("1");
			spaceRewardDao.add(reward);
		}
		spaceRewardDao.addPayementByRewardIds(rewardIds);
	}

	/**
	 * @date : 2017年7月27日 上午9:39:04
	 * @author : Iris.Xiao
	 * @param page
	 * @return
	 * @description : 删
	*/
	public void deleteSpaceReward(SpaceReward reward){
		if(reward==null){
			return;
		}
		spaceRewardDao.deletePayment(reward.getPaymentId());
		spaceRewardDao.delete(reward.getId());
	}

	/**
	 * @date : 2017年7月27日 上午9:39:04
	 * @author : Iris.Xiao
	 * @param page
	 * @return
	 * @description : 得到
	*/
	public SpaceReward getSpaceReward(String id){
		return spaceRewardDao.findById(id);
	}
	
	/**
	 * @date : 2017年7月27日 下午2:41:15
	 * @author : Iris.Xiao
	 * @return
	 * @description : 得到所有徽章列表
	*/
	public List<SpaceReward> findBadgeList(){
		return spaceRewardDao.findBadgeList();
	}
}
