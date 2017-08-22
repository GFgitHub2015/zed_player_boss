package com.zed.dao.player.push.impl;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.push.PlayerPushTaskDao;
import com.zed.domain.player.push.PlayerPushTask;

/**
 * @date : 2017年2月15日 上午11:34:02
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 推送
*/
@Repository("playerPushTaskDao")
public class PlayerPushTaskDaoImpl<T> extends AbstractPlayerPageDao<PlayerPushTask> implements PlayerPushTaskDao<PlayerPushTask> {

}
