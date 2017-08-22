package com.zed.dao.player.screen.impl;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.screen.PlayerScreenDao;
import com.zed.domain.player.screen.PlayerScreen;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yuw on 2016/12/15.
 */
@Repository("playerScreenDao")
public class PlayerScreenDaoImpl extends AbstractPlayerPageDao<PlayerScreen> implements PlayerScreenDao {
    @Override
    public PlayerScreen getNewPlayerScreen() {
        return this.find("getNewPlayerScreen");
    }

    @Override
    public List<PlayerScreen> findAll() {
        return findList("findAll");
    }
}
