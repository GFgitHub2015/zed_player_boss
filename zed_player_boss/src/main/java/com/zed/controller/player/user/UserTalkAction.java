package com.zed.controller.player.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.controller.system.BaseAction;
import com.zed.domain.player.user.PlayerUserTalk;
import com.zed.service.player.user.PlayerUserTalkService;
import com.zed.system.page.Page;

/**
 * Created by yuw on 2016/12/6.
 */
@Controller("userTalkAction")
@Scope("prototype")
public class UserTalkAction extends BaseAction {

    private Page page;

    private PlayerUserTalk playerUserTalk;

    @Autowired
    private PlayerUserTalkService playerUserTalkService;

    @Override
    public String doExecute() throws Exception {

        if(null == page){
            page = new Page();
        }
        if(null != playerUserTalk){
            page.setObject(playerUserTalk);
        }
        page = playerUserTalkService.findByPage(page);
        return SUCCESS;
    }

    public PlayerUserTalk getPlayerUserTalk() {
        return playerUserTalk;
    }

    public void setPlayerUserTalk(PlayerUserTalk playerUserTalk) {
        this.playerUserTalk = playerUserTalk;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
