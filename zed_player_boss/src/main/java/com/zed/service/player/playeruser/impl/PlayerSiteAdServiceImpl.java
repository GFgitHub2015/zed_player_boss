package com.zed.service.player.playeruser.impl;

import com.zed.dao.player.playeruser.PlayerSiteAdDao;
import com.zed.domain.player.playeruser.PlayerSiteAd;
import com.zed.service.player.playeruser.PlayerSiteAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Sumail on 2017/6/22.
 */
@Service("playerSiteAdService")
public class PlayerSiteAdServiceImpl implements PlayerSiteAdService {

    @Autowired
    private PlayerSiteAdDao<PlayerSiteAd> playerSiteAdDao;

    @Override
    public PlayerSiteAd findSiteAdByUserId(String userId) {
        return playerSiteAdDao.findById(userId);
    }

    @Override
    public void add(PlayerSiteAd playerSiteAd) {
        playerSiteAdDao.add(playerSiteAd);
    }

    @Override
    public void update(PlayerSiteAd playerSiteAd) {
        playerSiteAdDao.update(playerSiteAd);
    }
}
