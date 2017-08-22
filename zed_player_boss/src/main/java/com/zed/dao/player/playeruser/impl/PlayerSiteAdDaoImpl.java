package com.zed.dao.player.playeruser.impl;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.playeruser.PlayerSiteAdDao;
import com.zed.domain.player.playeruser.PlayerSiteAd;
import org.springframework.stereotype.Repository;

/**
 * Created by Sumail on 2017/6/22.
 */
@Repository("playerSiteAdDao")
public class PlayerSiteAdDaoImpl extends AbstractPlayerPageDao<PlayerSiteAd> implements PlayerSiteAdDao<PlayerSiteAd> {

}
