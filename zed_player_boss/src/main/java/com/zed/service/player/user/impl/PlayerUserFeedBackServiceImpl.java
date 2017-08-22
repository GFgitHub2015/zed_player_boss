package com.zed.service.player.user.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.dao.player.user.IPlayerUserFeedBackDao;
import com.zed.domain.player.user.PlayerUserFeedBack;
import com.zed.service.player.user.IPlayerUserFeedBackService;
import com.zed.system.page.Page;

/**
 * @date : 2017年2月15日 上午9:35:59
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 用户信息反馈
*/
@Service("playerUserFeedBackServiceImpl")
public class PlayerUserFeedBackServiceImpl implements IPlayerUserFeedBackService {
	
    @Resource(name="playerUserFeedBackDaoImpl")
    private IPlayerUserFeedBackDao<PlayerUserFeedBack> playerUserFeedBackDao;
    
	/**
	 * @date : 2017年2月15日 上午9:39:52
	 * @author : Iris.Xiao
	 * @param page
	 * @description : 查询列表
	*/
	public void findByPage(Page<PlayerUserFeedBack> page){
		playerUserFeedBackDao.findByPage(page);
    }
}
