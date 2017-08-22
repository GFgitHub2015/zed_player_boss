package com.zed.dao.player.user;

import com.zed.dao.BaseDao;
import com.zed.domain.player.user.PlayerUserTalk;
import com.zed.system.page.Page;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuw on 2016/12/6.
 */
public interface PlayerUserTalkDao<T> extends BaseDao<PlayerUserTalk> {

    public Page<PlayerUserTalk> findListByPage(Page<PlayerUserTalk> page);

    public PlayerUserTalk findByTalkId(String talkId);
}
