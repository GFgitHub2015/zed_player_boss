package com.zed.dao.player.user.impl;


import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.user.PlayerUserTalkDao;
import com.zed.domain.player.user.PlayerUserTalk;
import com.zed.system.page.Page;

/**
 * Created by yuw on 2016/12/6.
 */
@Repository("playerUserTalkDao")
public class PlayerUserTalkDaoImpl<T> extends AbstractPlayerPageDao<PlayerUserTalk> implements PlayerUserTalkDao<PlayerUserTalk> {
    @Override
    public Page<PlayerUserTalk> findListByPage(Page<PlayerUserTalk> page) {
        return this.findByPage(page);
    }

    @Override
    public PlayerUserTalk findByTalkId(String talkId) {
//        return getSqlSessionTemplate().selectOne("mapper.PlayerUserTalk.findByTalkId", talkId);
        return this.findById("findByTalkId",talkId);
    }
}
