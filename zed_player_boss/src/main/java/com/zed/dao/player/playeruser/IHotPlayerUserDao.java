package com.zed.dao.player.playeruser;

import java.io.Serializable;

import com.zed.dao.PageDao;
import com.zed.domain.player.playeruser.HotPlayerUser;

/**
 * @date : 2017年2月10日 下午4:26:04
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 热门用户
*/
public interface IHotPlayerUserDao <T extends Serializable> extends PageDao<HotPlayerUser> {

}
