package com.zed.dao.player.screen;

import com.zed.dao.PageDao;
import com.zed.domain.player.screen.PlayerScreen;

import java.util.List;

/**
 * Created by yuw on 2016/12/15.
 */
public interface PlayerScreenDao extends PageDao<PlayerScreen> {
    /**
     * 获取当前时间最近的一条纪录
     * @return
     * @throws Exception
     */
    public PlayerScreen getNewPlayerScreen();

    public List<PlayerScreen> findAll();
}
