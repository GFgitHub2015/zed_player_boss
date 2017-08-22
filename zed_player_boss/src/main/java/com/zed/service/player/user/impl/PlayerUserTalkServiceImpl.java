package com.zed.service.player.user.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zed.common.util.StringUtil;
import com.zed.dao.player.user.PlayerUserTalkDao;
import com.zed.domain.account.account.Account;
import com.zed.domain.player.user.PlayerUserTalk;
import com.zed.exception.AppValidationException;
import com.zed.service.account.account.AccountService;
import com.zed.service.player.user.PlayerUserTalkService;
import com.zed.system.page.Page;

/**
 * Created by yuw on 2016/12/6.
 */
@Service("playerUserTalkService")
public class PlayerUserTalkServiceImpl implements PlayerUserTalkService {

    @Autowired
    private PlayerUserTalkDao playerUserTalkDao;
    @Resource(name="accountService")
    private AccountService accountService;

    @Override
    public Page<PlayerUserTalk> findByPage(Page<PlayerUserTalk> page) throws AppValidationException {
        Page<PlayerUserTalk> listByPage = playerUserTalkDao.findListByPage(page);
        if(null != listByPage){
            List<PlayerUserTalk> result = listByPage.getResult();
            if(null != result){
                for(PlayerUserTalk talk : result){
                    if(StringUtil.isNotEmpty(talk.getAccountId())){
                        Account account = accountService.findById(talk.getAccountId());
                        if(null != account){
                            talk.setNickName(account.getNickName());
                        }
                    }
                }
            }
        }
        return listByPage;
    }

    @Override
    public PlayerUserTalk findByTalkId(String talkId) {
        return playerUserTalkDao.findByTalkId(talkId);
    }
}
