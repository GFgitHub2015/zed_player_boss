package com.zed.service.player.user;

import com.zed.domain.player.user.PlayerUserTalk;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

import java.util.HashMap;

/**
 * Created by yuw on 2016/12/6.
 */
public interface PlayerUserTalkService {

    public Page<PlayerUserTalk> findByPage(Page<PlayerUserTalk> page) throws AppValidationException;

    public PlayerUserTalk findByTalkId(String talkId);
}
