package com.zed.service.player.promotioninfo.impl;

import com.zed.dao.player.promotioninfo.PlayerLotteryActivityDao;
import com.zed.domain.player.playerpromotioninfo.PlayerLotteryActivity;
import com.zed.exception.AppValidationException;
import com.zed.service.player.promotioninfo.PlayerLotteryActivityService;
import com.zed.system.page.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Sumail on 2016/12/22.
 */
@Service("playerLotteryActivityService")
public class PlayerLotteryActivityServiceImpl implements PlayerLotteryActivityService {

    @Resource
    private PlayerLotteryActivityDao playerLotteryActivityDao;

    @Override
    public Page<PlayerLotteryActivity> findByPage(Page<PlayerLotteryActivity> page) throws AppValidationException {
        return playerLotteryActivityDao.findByPage(page);
    }
}
