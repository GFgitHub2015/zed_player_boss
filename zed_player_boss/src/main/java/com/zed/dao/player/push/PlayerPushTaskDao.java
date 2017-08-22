package com.zed.dao.player.push;

import java.io.Serializable;

import com.zed.dao.PageDao;
import com.zed.domain.player.push.PlayerPushTask;

/**
 * @date : 2017年2月15日 上午11:31:33
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 推送
*/
public interface PlayerPushTaskDao<T extends Serializable> extends PageDao<PlayerPushTask>  {

}
