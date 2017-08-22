package com.zed.dao.player.user.impl;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.user.IPlayerUserFeedBackDao;
import com.zed.domain.player.user.PlayerUserFeedBack;

/**
 * @date : 2017年2月15日 上午9:31:27
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 用户信息反馈
*/
@Repository("playerUserFeedBackDaoImpl")
public class PlayerUserFeedBackDaoImpl extends AbstractPlayerPageDao<PlayerUserFeedBack> implements IPlayerUserFeedBackDao<PlayerUserFeedBack> {

}
