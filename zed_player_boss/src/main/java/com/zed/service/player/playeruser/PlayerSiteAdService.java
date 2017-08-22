package com.zed.service.player.playeruser;

import com.zed.domain.player.playeruser.PlayerSiteAd;

/**
 * Created by Sumail on 2017/6/22.
 */
public interface PlayerSiteAdService {

    PlayerSiteAd findSiteAdByUserId(String userId);

    void add(PlayerSiteAd playerSiteAd);

    void update(PlayerSiteAd playerSiteAd);

}
