package com.zed.service.player.screen.impl;

import com.zed.dao.player.screen.PlayerScreenDao;
import com.zed.domain.player.screen.PlayerScreen;
import com.zed.exception.AppValidationException;
import com.zed.service.player.screen.PlayerScreenService;
import com.zed.system.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yuw on 2016/12/15.
 */
@Service("playerScreenService")
public class PlayerScreenServiceImpl implements PlayerScreenService {

    @Autowired
    private PlayerScreenDao playerScreenDao;

    @Override
    public PlayerScreen findById(String id) throws AppValidationException {
        return playerScreenDao.findById(id);
    }

    @Override
    public void add(PlayerScreen playerScreen) throws AppValidationException {
        playerScreenDao.add(playerScreen);
    }

    @Override
    public void update(PlayerScreen playerScreen) throws AppValidationException {
        playerScreenDao.update(playerScreen);
    }

    @Override
    public void delete(String[] ids) throws AppValidationException {
        playerScreenDao.delete(ids);
    }

    @Override
    public void updateStatus(String id, int status) throws AppValidationException {
        PlayerScreen playerScreen = new PlayerScreen();
        playerScreen.setId(id);
        playerScreen.setStatus(status);
        playerScreenDao.update(playerScreen);
    }

    @Override
    public Page<PlayerScreen> findByPage(Page<PlayerScreen> page) throws AppValidationException {
        return playerScreenDao.findByPage(page);
    }

    @Override
    public PlayerScreen getNewPlayerScreen() throws AppValidationException {
        return playerScreenDao.getNewPlayerScreen();
    }

    @Override
    public List<PlayerScreen> findAll() throws AppValidationException {
        return playerScreenDao.findAll();
    }
}
