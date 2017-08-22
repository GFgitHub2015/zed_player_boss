package com.zed.service.player.screen;

import com.zed.domain.player.screen.PlayerScreen;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

import java.util.List;

/**
 * Created by yuw on 2016/12/15.
 */
public interface PlayerScreenService {

    public PlayerScreen findById(String id) throws AppValidationException;

    public void add(PlayerScreen playerScreen) throws AppValidationException;

    public void update(PlayerScreen playerScreen) throws AppValidationException;

    public void delete(String[] ids) throws AppValidationException;

    public void updateStatus(String id,int status) throws AppValidationException;

    public Page<PlayerScreen> findByPage(Page<PlayerScreen> page) throws AppValidationException;

    public PlayerScreen getNewPlayerScreen() throws AppValidationException;

    public List<PlayerScreen> findAll() throws AppValidationException;
}
