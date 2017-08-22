package com.zed.service.player.user;

import com.zed.domain.player.user.PlayerUserFeedBack;
import com.zed.system.page.Page;

/**
 * @date : 2017年2月15日 上午9:35:25
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 用户信息反馈
*/
public interface IPlayerUserFeedBackService {
	/**
	 * @date : 2017年2月15日 上午9:39:52
	 * @author : Iris.Xiao
	 * @param page
	 * @description : 查询列表
	*/
	public void findByPage(Page<PlayerUserFeedBack> page);
}
