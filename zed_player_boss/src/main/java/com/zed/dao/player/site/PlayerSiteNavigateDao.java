package com.zed.dao.player.site;

import java.io.Serializable;
import java.util.List;

import com.zed.dao.PageDao;
import com.zed.domain.player.site.PlayerSiteNavigate;
import com.zed.exception.AppValidationException;

public interface PlayerSiteNavigateDao<T extends Serializable> extends PageDao<PlayerSiteNavigate> {

	//修改状态
	public void updateStatus(PlayerSiteNavigate playerSiteNavigate) throws AppValidationException;
	
	public List<PlayerSiteNavigate> findAll() throws AppValidationException;
	
	public List<PlayerSiteNavigate> findByIds(String [] ids) throws AppValidationException;
}
