package com.zed.dao.player.playeruser.impl;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.playeruser.IHotPlayerUserDao;
import com.zed.domain.player.playeruser.HotPlayerUser;

/**
 * @date : 2017年2月10日 下午4:26:58
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 热门用户
*/
@Repository("hotPlayerUserDaoImpl")
public class HotPlayerUserDaoImpl extends AbstractPlayerPageDao<HotPlayerUser> implements IHotPlayerUserDao<HotPlayerUser> {
	
}
