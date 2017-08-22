package com.zed.service.player.promotioninfo;

import com.zed.domain.player.playerpromotioninfo.PlayerLotteryActivity;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

/**
 * Created by Sumail on 2016/12/22.
 */
public interface PlayerLotteryActivityService {

    public Page<PlayerLotteryActivity> findByPage(Page<PlayerLotteryActivity> page) throws AppValidationException;
}
